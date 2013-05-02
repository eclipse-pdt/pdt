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
package org.eclipse.php.internal.ui.outline;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.preferences.CorePreferencesSupport;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.FakeType;
import org.eclipse.php.internal.core.typeinference.UseStatementElement;
import org.eclipse.php.internal.core.util.OutlineFilter;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class PHPOutlineContentProvider implements ITreeContentProvider {

	// outline tree viewer
	private TreeViewer fOutlineViewer;
	private ISourceModule fSourceModule;
	private IModelElement[] fUseStatements;

	public PHPOutlineContentProvider(TreeViewer viewer) {
		super();
		fOutlineViewer = viewer;

		// fix bug 251682 - auto-expand outline view
		fOutlineViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);

		inputChanged(fOutlineViewer, null, null);
	}

	// private Object[] NO_CLASS = new Object[] { new NoClassElement() };
	private ElementChangedListener fListener;

	public void dispose() {
		if (fListener != null) {
			DLTKCore.removeElementChangedListener(fListener);
			fListener = null;
		}
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof IParent) {
			IParent c = (IParent) parent;
			try {
				return OutlineFilter.filterChildrenForOutline(parent,
						c.getChildren());
			} catch (ModelException x) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=38341
				// don't log NotExist exceptions as this is a valid case
				// since we might have been posted and the element
				// removed in the meantime.
				if (DLTKCore.DEBUG || !x.isDoesNotExist()) {
					DLTKUIPlugin.log(x);
				}
			}
		}
		return PHPContentOutlineConfiguration.NO_CHILDREN;
	}

	private boolean isNamespaceSupported(IModelElement modelElement) {
		PHPVersion phpVersion = null;
		if (modelElement == null || modelElement.getScriptProject() == null) {
			String versionName = CorePreferencesSupport.getInstance()
					.getWorkspacePreferencesValue(
							PHPCoreConstants.PHP_OPTIONS_PHP_VERSION);
			phpVersion = PHPVersion.byAlias(versionName);
		} else {
			phpVersion = ProjectOptions.getPhpVersion(modelElement
					.getScriptProject().getProject());
		}
		return phpVersion.isGreaterThan(PHPVersion.PHP5);
	}

	public Object[] getElements(Object parent) {
		Object[] children = getChildren(parent);

		if (parent instanceof ISourceModule) {
			ISourceModule sourceModule = (ISourceModule) parent;

			if (isNamespaceSupported(sourceModule)) {
				// if namespaces are supported, add use statements node:
				Object[] newChildren = new Object[children.length + 1];
				newChildren[0] = new UseStatementsNode(sourceModule);
				System.arraycopy(children, 0, newChildren, 1, children.length);
				children = newChildren;
			}
		}
		return children;
	}

	public Object getParent(Object child) {
		if (child instanceof IModelElement) {
			IModelElement e = (IModelElement) child;
			return e.getParent();
		}
		return null;
	}

	public boolean hasChildren(Object parent) {

		if (parent instanceof IModelElement) {
			IModelElement me = (IModelElement) parent;
			if (me.getElementType() == IModelElement.FIELD
					|| me.getElementType() == IModelElement.METHOD) {
				return false;
			}
		}
		if (parent instanceof IParent) {
			IParent c = (IParent) parent;
			try {
				IModelElement[] children = OutlineFilter
						.filter(c.getChildren());
				return (children != null && children.length > 0);
			} catch (ModelException x) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=38341
				// don't log NotExist exceptions as this is a valid case
				// since we might have been posted and the element
				// removed in the meantime.
				if (DLTKUIPlugin.isDebug() || !x.isDoesNotExist()) {
					DLTKUIPlugin.log(x);
				}
			}
		}
		return false;
	}

	/*
	 * @see IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		// Check that the new input is valid
		boolean isCU = (newInput instanceof ISourceModule);
		// Add a listener if input is valid and there wasn't one
		if (isCU && fListener == null) {
			fListener = new ElementChangedListener();
			DLTKCore.addElementChangedListener(fListener);
		}
		// If the new input is not valid and there is a listener - remove it
		else if (!isCU && fListener != null) {
			DLTKCore.removeElementChangedListener(fListener);
			fListener = null;
		}
	}

	public boolean isDeleted(Object o) {
		return false;
	}

	/**
	 * The element change listener of the java outline viewer.
	 * 
	 * @see IElementChangedListener
	 */
	protected class ElementChangedListener implements IElementChangedListener {

		private int useStatementsCount;
		private int useStatementsCountNew;

		public void elementChanged(final ElementChangedEvent e) {

			final Control control = fOutlineViewer.getControl();
			if (control == null || control.isDisposed()) {
				return;
			}

			// update useStatements
			Job job = new Job(PHPUIMessages.PHPOutlineContentProvider_0) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {

					fUseStatements = getUseStatements();

					IWorkbenchWindow activeWorkbenchWindow = PlatformUI
							.getWorkbench().getActiveWorkbenchWindow();
					if (activeWorkbenchWindow != null) {
						IWorkbenchPage activePage = activeWorkbenchWindow
								.getActivePage();
						if (activePage != null) {
							IEditorPart activeEditor = activePage
									.getActiveEditor();
							if (activeEditor instanceof PHPStructuredEditor) {
								IModelElement base = ((PHPStructuredEditor) activeEditor)
										.getModelElement();

								if (isNamespaceSupported(base)) {
									ModuleDeclaration moduleDeclaration = SourceParserUtil
											.getModuleDeclaration((ISourceModule) base);
									UseStatement[] useStatements = ASTUtils
											.getUseStatements(
													moduleDeclaration,
													moduleDeclaration
															.sourceEnd());
									useStatementsCountNew = useStatements.length;
								}
							}
						}
					}
					IModelElementDelta delta = findElement(fSourceModule,
							e.getDelta());
					if ((delta != null || e.getType() == ElementChangedEvent.POST_CHANGE)
							&& fOutlineViewer != null
							&& fOutlineViewer.getControl() != null
							&& !fOutlineViewer.getControl()
									.isDisposed()) {
						Display d = control.getDisplay();
						if (d != null) {
							d.asyncExec(new Runnable() {
								public void run() {
									fOutlineViewer.refresh();
								}
							});
						}
					}
					return Status.OK_STATUS;
				}
			};
			job.setPriority(Job.DECORATE);
			job.setSystem(true);
			job.schedule();
		}

		protected IModelElementDelta findElement(IModelElement unit,
				IModelElementDelta delta) {

			if (delta == null || unit == null) {
				return null;
			}

			IModelElement element = delta.getElement();

			if (unit.equals(element)) {
				if (isPossibleStructuralChange(delta)) {
					return delta;
				}
				return null;
			}

			if (element.getElementType() > IModelElement.SOURCE_MODULE) {
				return null;
			}

			IModelElementDelta[] children = delta.getAffectedChildren();
			if (children == null || children.length == 0) {
				return null;
			}

			for (int i = 0; i < children.length; i++) {
				IModelElementDelta d = findElement(unit, children[i]);
				if (d != null) {
					return d;
				}
			}

			return null;
		}

		private boolean isPossibleStructuralChange(IModelElementDelta cuDelta) {
			int oldValue = useStatementsCount;
			useStatementsCount = useStatementsCountNew;
			if (oldValue != useStatementsCountNew) {
				return true;
			}

			if (cuDelta.getKind() != IModelElementDelta.CHANGED) {
				return true; // add or remove
			}
			int flags = cuDelta.getFlags();
			if ((flags & IModelElementDelta.F_CHILDREN) != 0) {
				return true;
			}
			return (flags & (IModelElementDelta.F_CONTENT | IModelElementDelta.F_FINE_GRAINED)) == IModelElementDelta.F_CONTENT;
		}
	}

	public class UseStatementsNode extends FakeType {

		public UseStatementsNode(ISourceModule sourceModule) {
			super((ModelElement) sourceModule,
					PHPUIMessages.PHPOutlineContentProvider_useStatementsNode,
					0, null); 
			fSourceModule = sourceModule;
		}

		public IModelElement[] getChildren() throws ModelException {
			if (fUseStatements == null)
				fUseStatements = getUseStatements();
			return fUseStatements;
		}

		public boolean hasChildren() throws ModelException {
			return getChildren().length > 0;
		}

		@Override
		public Object getElementInfo() throws ModelException {
			return null;
		}
	}

	private IModelElement[] getUseStatements() {
		// when rename a php file,we should return a empty array for the old
		// sourceModule,or execute SourceParserUtil.getModuleDeclaration()
		// will cache wrong ModuleDeclaration for the non-exist
		// sourceModule,so when we rename the php file back to its original
		// name will get the wrong ModuleDeclaration
		if (null == fSourceModule || !fSourceModule.exists()) {
			return new IModelElement[0];
		}
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(fSourceModule);
		if (moduleDeclaration == null)
			return new IModelElement[0];
		UseStatement[] useStatements = ASTUtils.getUseStatements(
				moduleDeclaration, moduleDeclaration.sourceEnd());
		List<UseStatementElement> elements = new LinkedList<UseStatementElement>();
		for (UseStatement useStatement : useStatements) {
			for (UsePart usePart : useStatement.getParts()) {
				elements.add(new UseStatementElement(
						(ModelElement) fSourceModule, usePart));
			}
		}
		return (UseStatementElement[]) elements
				.toArray(new UseStatementElement[elements.size()]);
	}

}
