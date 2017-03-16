/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.ui.corext.util.Resources;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.extract.NameSuggestVisitor;

/**
 * @author seva
 */
@SuppressWarnings("restriction")
public class RefactoringUtility {

	/**
	 * @param project
	 * @param file
	 * @throws Exception
	 */
	public static Program getProgramForFile(IProject project, final IFile file)
			throws Exception {
		// fix Mantis #0022461 - Refactoring does not seem to work properly if
		// the encoding of the file to be refactored is utf-8
		// The InputStreamReader must receive the file charset, otherwise
		// default charset is used
		final ISourceModule source = DLTKCore.createSourceModuleFrom(file);

		return ASTParser.newParser(source).createAST(new NullProgressMonitor());
	}

	/**
	 * @param file
	 */
	public static Program getProgramForFile(final IFile file) throws Exception {
		return getProgramForFile(file.getProject(), file);
	}

	/**
	 * @param expression
	 * @return For a given expression return a list of suggested variable names
	 */
	public static String[] getVariableNameSuggestions(
			Expression assignedExpression) {
		List<String> res = new ArrayList<String>();

		NameSuggestVisitor visitor = new NameSuggestVisitor();
		assignedExpression.accept(visitor);

		res = visitor.getSuggestions();

		return (String[]) res.toArray(new String[res.size()]);
	}

	public static RefactoringStatus checkNewElementName(String newName) {
		if (!isValidIdentifier(newName)) {
			return getFatalError(newName);
		}

		return new RefactoringStatus();
	}

	/**
	 * @param newName
	 * @return
	 */
	public static final RefactoringStatus getFatalError(String newName) {
		return RefactoringStatus
				.createFatalErrorStatus(PhpRefactoringCoreMessages.format(
						"RefactoringUtility.0", new Object[] { newName })); //$NON-NLS-1$
	}

	/**
	 * @param newName
	 * @return true of the given string is valid PHP identifier
	 */
	public static final boolean isValidIdentifier(String newName) {
		if (newName == null || newName.length() == 0
				|| !Character.isLetter(newName.charAt(0))
				&& newName.charAt(0) != '_') {
			return false;
		}

		final int length = newName.length();
		for (int i = 1; i < length; i++) {
			if (!Character.isJavaIdentifierPart(newName.charAt(i))) {
				return false;
			}
			if (newName.charAt(i) == '$') {
				// Seva: in addition to java rules, PHP doesn't allow dollar
				// signs inside element names
				return false;
			}
		}
		return true;
	}

	// -------- validateEdit checks ----

	public static RefactoringStatus validateModifiesFiles(
			IResource[] iResources, Object context) {
		RefactoringStatus result = new RefactoringStatus();
		IStatus status = Resources.checkInSync(iResources);
		if (!status.isOK())
			result.merge(RefactoringStatus.create(status));
		status = Resources.makeCommittable(iResources, context);
		if (!status.isOK()) {
			result.merge(RefactoringStatus.create(status));
			if (!result.hasFatalError()) {
				result.addFatalError(PhpRefactoringCoreMessages
						.getString("ExtractVariableRefactoring.1")); //$NON-NLS-1$
			}
		}
		return result;
	}

	public static IResource getResource(Object element) {
		if (element instanceof IPath) {
			return ResourcesPlugin.getWorkspace().getRoot()
					.findMember((IPath) element);
		}
		if (element instanceof IResource) {
			return (IResource) element;
		}
		if (element instanceof ISourceModule) {
			return ((ISourceModule) element).getPrimary().getResource();
		}
		if (element instanceof IModelElement) {
			return ((IModelElement) element).getResource();
		}
		if (element instanceof IAdaptable) {
			return (IResource) ((IAdaptable) element)
					.getAdapter(IResource.class);
		}
		return null;
	}

	public static IBuildpathEntry createNewBuildpathEntry(int bpeSource,
			IPath path) {
		switch (bpeSource) {
		case IBuildpathEntry.BPE_LIBRARY:
			return DLTKCore.newLibraryEntry(path);
		case IBuildpathEntry.BPE_PROJECT:
			return DLTKCore.newProjectEntry(path);
		case IBuildpathEntry.BPE_SOURCE:
			return DLTKCore.newSourceEntry(path);
		case IBuildpathEntry.BPE_CONTAINER:
			return DLTKCore.newContainerEntry(path);
		case IBuildpathEntry.BPE_VARIABLE:
			return DLTKCore.newVariableEntry(path);
		default:
			Assert.isTrue(false);
			return null;
		}
	}

	public static IBuildpathEntry createNewBuildpathEntry(
			IBuildpathEntry fEntryToChange, IPath path, IPath filePath,
			String newName) {
		switch (fEntryToChange.getEntryKind()) {
		case IBuildpathEntry.BPE_LIBRARY:
			return DLTKCore.newLibraryEntry(path,
					fEntryToChange.getAccessRules(),
					fEntryToChange.getExtraAttributes(),
					fEntryToChange.isExported(), fEntryToChange.isExternal());
		case IBuildpathEntry.BPE_PROJECT:
			return DLTKCore.newProjectEntry(path,
					fEntryToChange.getAccessRules(),
					fEntryToChange.combineAccessRules(),
					fEntryToChange.getExtraAttributes(),
					fEntryToChange.isExported());
		case IBuildpathEntry.BPE_SOURCE:
			IPath[] excludes = updatePathPatternes(
					fEntryToChange.getExclusionPatterns(),
					fEntryToChange.getPath(), filePath, newName);
			IPath[] includes = updatePathPatternes(
					fEntryToChange.getInclusionPatterns(),
					fEntryToChange.getPath(), filePath, newName);
			return DLTKCore.newSourceEntry(path, includes, excludes,
					fEntryToChange.getExtraAttributes());
		case IBuildpathEntry.BPE_CONTAINER:
			return DLTKCore.newContainerEntry(path,
					fEntryToChange.getAccessRules(),
					fEntryToChange.getExtraAttributes(),
					fEntryToChange.isExported());
		case IBuildpathEntry.BPE_VARIABLE:
			return DLTKCore.newVariableEntry(path,
					fEntryToChange.getAccessRules(),
					fEntryToChange.getExtraAttributes(),
					fEntryToChange.isExported());
		default:
			Assert.isTrue(false);
			return null;
		}
	}

	private static IPath[] updatePathPatternes(IPath[] updatingPaths,
			IPath entryPath, IPath filePath, String newName) {
		IPath[] paths = updatingPaths;
		IPath relativePath = filePath.makeRelativeTo(entryPath);
		ArrayList<IPath> excludeList = new ArrayList<IPath>();
		for (IPath path : paths) {
			if (!relativePath.isEmpty() && relativePath.isPrefixOf(path)) {
				int mattchedPath = path.matchingFirstSegments(relativePath);
				IPath truncatedPath = path.uptoSegment(mattchedPath);
				IPath remaingPath = path.removeFirstSegments(mattchedPath);
				if (mattchedPath == 0) {
					excludeList.add(truncatedPath.removeLastSegments(1).append(
							newName + "/")); //$NON-NLS-1$
				} else {
					excludeList.add(truncatedPath.removeLastSegments(1)
							.append(newName + "/") //$NON-NLS-1$
							.append(remaingPath.toString()));
				}
			} else {
				excludeList.add(path);
			}
		}

		return excludeList.toArray(new IPath[excludeList.size()]);
	}

	/**
	 * Figure out the type parent of the given element
	 * 
	 * @param node
	 * @return the type which the element belongs to, or null if can't find.
	 */
	public static TypeDeclaration getType(ASTNode node) {
		if (node == null) {
			return null;
		}

		ASTNode model = node;
		while (!(model instanceof TypeDeclaration)) {
			if (node == null) {
				return null;
			}
			ASTNode parent = model.getParent();
			if (parent == model) {
				return null;
			}
			model = parent;
			if (model instanceof Program) {
				return null;
			}
		}

		return (TypeDeclaration) model;
	}

	static public ISourceModule getSourceModule(IModelElement type) {
		IModelElement root = null;
		root = type;
		while (root != null && !(root instanceof ISourceModule)) {
			root = root.getParent();
		}

		if (root != null) {
			return ((ISourceModule) root);
		}
		return null;
	}

	public static ASTNode getTypeOrClassInstance(ASTNode node) {
		if (node == null) {
			return null;
		}

		ASTNode model = node;
		while (!(model instanceof TypeDeclaration)
				&& !(model instanceof ClassInstanceCreation)) {
			if (node == null) {
				return null;
			}
			ASTNode parent = model.getParent();
			if (parent == model) {
				return null;
			}
			if (parent instanceof TraitUseStatement) {
				return null;
			}
			model = parent;
			if (model instanceof Program) {
				return null;
			}
		}

		return model;
	}

}
