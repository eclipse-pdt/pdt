/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.hyperlink;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.DocumentAdapter;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.compiler.ast.nodes.Include;
import org.eclipse.php.internal.core.compiler.ast.nodes.InfixExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.wst.jsdt.web.ui.internal.Logger;

public class IncludeHyperlinkDetector extends AbstractHyperlinkDetector {

	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		PHPStructuredEditor editor = EditorUtility.getPHPEditor(textViewer);
		if (editor == null) {
			return null;
		}

		IModelElement input = org.eclipse.dltk.internal.ui.editor.EditorUtility
				.getEditorInputModelElement(editor, false);
		if (!(input instanceof ISourceModule)) {
			return null;
		}

		final int offset = region.getOffset();
		final String file[] = new String[1];
		final Region selectRegion[] = new Region[1];

		final ISourceModule sourceModule = (ISourceModule) input;
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);

		ASTVisitor visitor = new ASTVisitor() {
			boolean found = false;

			public boolean visit(Expression expr) throws ModelException {
				if (expr.sourceStart() < offset && expr.sourceEnd() > offset) {
					if (expr instanceof Include) {
						Expression fileExpr = ((Include) expr).getExpr();
						if (fileExpr instanceof InfixExpression) {
							InfixExpression ie = (InfixExpression) fileExpr;
							if (ie.getRight() instanceof Scalar) {
								fileExpr = ie.getRight();
							}
						}
						if (fileExpr instanceof Scalar) {
							String value = ((Scalar) fileExpr).getValue();
							file[0] = ASTUtils.stripQuotes(value);
							file[0] = file[0].trim();

							// only select file, without quotes or surrounding
							// whitespaces
							int startIdx = fileExpr.sourceStart()
									+ value.indexOf(file[0]);
							int length = file[0].length();
							selectRegion[0] = new Region(startIdx, length);
						}
						found = true;
						return false;
					}
				}
				return !found;
			}

			public boolean visitGeneral(ASTNode n) {
				return !found;
			}
		};

		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		if (file[0] != null) {
			if (!inclusive(region, selectRegion[0]))
				return null;

			Set<String> set = new HashSet<String>();
			set.add(sourceModule.getResource().getLocation().toOSString());
			ISourceModule includedSourceModule = FileNetworkUtility
					.findSourceModule(sourceModule, file[0], set);
			if (includedSourceModule == null) {
				includedSourceModule = createFakeSourceModule(file[0]);
			}
			if (includedSourceModule != null) {
				return new IHyperlink[] { new ModelElementHyperlink(
						selectRegion[0], includedSourceModule, new OpenAction(
								editor)) };
			} else {
				includedSourceModule = createFakeSourceModule(file[0]);

				return new IHyperlink[] { new ModelElementHyperlink(
						selectRegion[0], includedSourceModule, new OpenAction(
								editor)) };
			}
		}
		return null;
	}

	/**
	 * @return true if region1 is included in region2
	 */
	private boolean inclusive(IRegion region1, Region region2) {
		return (region1.getOffset() >= region2.getOffset())
				&& (region1.getOffset() + region1.getLength() <= region2
						.getOffset() + region2.getLength());
	}

	private ISourceModule createFakeSourceModule(String filePath) {
		// filePath = normalize(filePath);
		try {
			final IFileStore fileStore = EFS.getLocalFileSystem().getStore(
					new Path(filePath));
			IFileInfo fetchInfo = fileStore.fetchInfo();
			if (!fetchInfo.isDirectory() && fetchInfo.exists()) {
				IURIEditorInput editorInput = new FileStoreEditorInput(
						fileStore);
				final URI uri = editorInput.getURI();
				// final IFileStore fileStore = EFS.getStore(uri);
				final IPath path = URIUtil.toPath(uri);
				final String fileStoreName = fileStore.getName();
				if (fileStoreName == null || path == null)
					return null;

				WorkingCopyOwner woc = new WorkingCopyOwner() {
					/*
					 * @see
					 * org.eclipse.jdt.core.WorkingCopyOwner#createBuffer(org
					 * .eclipse .jdt.core.ICompilationUnit)
					 * 
					 * @since 3.2
					 */
					public IBuffer createBuffer(ISourceModule workingCopy) {
						return new DocumentAdapter(workingCopy, fileStore, path);
					}
				};

				IBuildpathEntry[] cpEntries = null;
				IScriptProject jp = findScriptProject(path);
				if (jp != null)
					cpEntries = jp.getResolvedBuildpath(true);

				if (cpEntries == null || cpEntries.length == 0)
					cpEntries = new IBuildpathEntry[] { ScriptRuntime
							.getDefaultInterpreterContainerEntry() };

				final ISourceModule cu = woc.newWorkingCopy(fileStoreName,
						cpEntries, new IProblemRequestor() {

							public void acceptProblem(IProblem problem) {
								// TODO Auto-generated method stub

							}

							public void beginReporting() {
								// TODO Auto-generated method stub

							}

							public void endReporting() {
								// TODO Auto-generated method stub

							}

							public boolean isActive() {
								// TODO Auto-generated method stub
								return false;
							}
						}, new NullProgressMonitor());

				// if (!isModifiable(editorInput))
				ScriptModelUtil.reconcile(cu);

				return cu;

			}
		} catch (CoreException ex) {
			return null;
		}
		return null;
	}

	private String normalize(String filePath) {
		// TODO Auto-generated method stub
		IPath path = new Path(filePath);
		List<String> segmentList = new ArrayList<String>();
		for (int i = path.segments().length - 1; i >= 0; i--) {
			String segment = path.segments()[i];
			int j = 0;
			while (segment.equals("..") && i - j > 0) {
				// i--;
				j++;
				segment = path.segments()[i - j];
			}
			if (i - 2 * j >= 0) {
				segment = path.segments()[i - 2 * j];
				segmentList.add(segment);
			}
		}
		IPath newPath = new Path(path.getDevice(), "");
		for (int i = segmentList.size() - 1; i >= 0; i--) {
			newPath.append(segmentList.get(i));
		}
		return null;
	}

	/**
	 * Fuzzy search for script project in the workspace that matches the given
	 * path.
	 * 
	 * @param path
	 *            the path to match
	 * @return the matching script project or <code>null</code>
	 * 
	 */
	private IScriptProject findScriptProject(IPath path) {
		if (path == null)
			return null;

		String[] pathSegments = path.segments();
		IScriptModel model = DLTKCore.create(DLTKUIPlugin.getWorkspace()
				.getRoot());
		IScriptProject[] projects;
		try {
			projects = model.getScriptProjects();
		} catch (ModelException e) {
			return null; // ignore - use default RE
		}
		for (int i = 0; i < projects.length; i++) {
			IPath projectPath = projects[i].getProject().getFullPath();
			String projectSegment = projectPath.segments()[0];
			for (int j = 0; j < pathSegments.length; j++)
				if (projectSegment.equals(pathSegments[j]))
					return projects[i];
		}
		return null;
	}

}
