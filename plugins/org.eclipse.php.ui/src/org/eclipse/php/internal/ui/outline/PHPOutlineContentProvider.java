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

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.model.ImportDeclarationVisitor;
import org.eclipse.php.internal.core.preferences.CorePreferencesSupport;
import org.eclipse.php.internal.core.typeinference.FakeType;
import org.eclipse.php.internal.core.util.OutlineFilter;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
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
	private ISourceModule fInput;
	private IModelElement[] fUseStatements;
	private UseStatementsNode fUseStatementsNode;

	private ElementChangedListener fListener;
	private ProblemChangedListener fProblemListener;

	public PHPOutlineContentProvider(TreeViewer viewer) {
		super();
		fOutlineViewer = viewer;

		// fix bug 251682 - auto-expand outline view
		fOutlineViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);

		inputChanged(fOutlineViewer, null, null);
	}

	@Override
	public void dispose() {
		if (fListener != null) {
			DLTKCore.removeElementChangedListener(fListener);
			fListener = null;
		}
		if (fProblemListener != null) {
			PHPUiPlugin.getWorkspace().removeResourceChangeListener(fProblemListener);
			fProblemListener = null;
		}
	}

	@Override
	public Object[] getChildren(Object parent) {
		if (parent instanceof IParent) {
			IParent c = (IParent) parent;
			try {
				return OutlineFilter.filterChildrenForOutline(parent, c.getChildren());
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
					.getWorkspacePreferencesValue(PHPCoreConstants.PHP_OPTIONS_PHP_VERSION);
			phpVersion = PHPVersion.byAlias(versionName);
		} else {
			phpVersion = ProjectOptions.getPHPVersion(modelElement.getScriptProject().getProject());
		}
		return phpVersion.isGreaterThan(PHPVersion.PHP5);
	}

	@Override
	public Object[] getElements(Object parent) {
		Object[] children = getChildren(parent);

		if (parent instanceof ISourceModule) {
			ISourceModule sourceModule = (ISourceModule) parent;
			this.fSourceModule = sourceModule;

			if (isNamespaceSupported(sourceModule)) {
				// if namespaces are supported, add use statements node:
				Object[] newChildren = new Object[children.length + 1];
				fUseStatementsNode = new UseStatementsNode(sourceModule);
				newChildren[0] = fUseStatementsNode;
				System.arraycopy(children, 0, newChildren, 1, children.length);
				children = newChildren;
			}
		}
		return children;
	}

	@Override
	public Object getParent(Object child) {
		if (child instanceof IModelElement) {
			IModelElement e = (IModelElement) child;
			return e.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object parent) {
		if (parent instanceof IModelElement) {
			IModelElement me = (IModelElement) parent;
			if (me.getElementType() == IModelElement.FIELD) {
				IField field = (IField) me;
				try {
					if (field.exists()) {
						for (IModelElement child : field.getChildren()) {
							if (child.getElementType() == IModelElement.METHOD
									|| child.getElementType() == IModelElement.TYPE) {
								return true;
							}
						}
					}
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
				}
				return false;
			}
		}
		if (parent instanceof IParent) {
			IParent c = (IParent) parent;
			try {
				IModelElement[] children = OutlineFilter.filter(c.getChildren());
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
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		// Check that the new input is valid
		boolean isCU = (newInput instanceof ISourceModule);
		// Add a listener if input is valid and there wasn't one
		if (isCU && fListener == null) {
			fInput = (ISourceModule) newInput;
			fListener = new ElementChangedListener();
			DLTKCore.addElementChangedListener(fListener);

			fProblemListener = new ProblemChangedListener();
			PHPUiPlugin.getWorkspace().addResourceChangeListener(fProblemListener, IResourceChangeEvent.POST_CHANGE);
		}
		// If the new input is not valid and there is a listener - remove it
		else if (!isCU && fListener != null) {
			DLTKCore.removeElementChangedListener(fListener);
			fListener = null;

			PHPUiPlugin.getWorkspace().removeResourceChangeListener(fProblemListener);
			fProblemListener = null;

			fInput = null;
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

		@Override
		public void elementChanged(final ElementChangedEvent e) {
			final Control control = fOutlineViewer.getControl();
			if (control == null || control.isDisposed()) {
				return;
			}

			// filter event from different source module
			Set<ISourceModule> eventSources = new HashSet<ISourceModule>();
			collectSourceModules(e.getDelta(), eventSources);
			if (fInput == null || !eventSources.contains(fInput)) {
				return;
			}

			// update useStatements
			Job job = new Job(PHPUIMessages.PHPOutlineContentProvider_0) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					fUseStatements = getUseStatements(fSourceModule);

					IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					if (activeWorkbenchWindow != null) {
						IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
						if (activePage != null) {
							IEditorPart activeEditor = activePage.getActiveEditor();
							if (activeEditor instanceof PHPStructuredEditor) {
								IModelElement base = ((PHPStructuredEditor) activeEditor).getModelElement();

								if (isNamespaceSupported(base)) {
									IModelElement[] useStatements = getUseStatements((ISourceModule) base);
									useStatementsCountNew = useStatements.length;
								}
							}
						}
					}
					final IModelElementDelta delta = findElement(fSourceModule, e.getDelta());
					if ((delta != null || e.getType() == ElementChangedEvent.POST_CHANGE) && fOutlineViewer != null
							&& fOutlineViewer.getControl() != null && !fOutlineViewer.getControl().isDisposed()) {
						Display d = control.getDisplay();
						if (d != null) {
							d.asyncExec(new Runnable() {
								@Override
								public void run() {
									refresh(delta);
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

		private void collectSourceModules(IModelElementDelta delta, Set<ISourceModule> sourceModules) {
			if (delta.getElement() instanceof ISourceModule) {
				sourceModules.add((ISourceModule) delta.getElement());
			}
			for (IModelElementDelta child : delta.getAffectedChildren()) {
				collectSourceModules(child, sourceModules);
			}
		}

		private void refresh(IModelElementDelta delta) {
			if (delta == null) {
				return;
			}
			if (fOutlineViewer.getTree() == null
					|| (fOutlineViewer.getTree() != null && !fOutlineViewer.getTree().isDisposed())) {
				visitAndUpdate(delta);
			}
		}

		private void visitAndUpdate(IModelElementDelta delta) {
			if (delta.getElement() != null) {
				IModelElement modelElement = delta.getElement();
				switch (delta.getKind()) {
				case IModelElementDelta.ADDED:
					if (modelElement.getElementType() == IModelElement.IMPORT_CONTAINER) {
						IModelElement parent = new ImportContainerFinder().findParent((IImportContainer) modelElement);
						if (parent != null) {
							fOutlineViewer.add(parent, modelElement);
						}
					} else {
						fOutlineViewer.add(modelElement.getParent(), modelElement);
					}
					refreshUseStatements(modelElement);
					break;
				case IModelElementDelta.CHANGED:
					fOutlineViewer.update(modelElement, null);
					break;
				case IModelElementDelta.REMOVED:
					fOutlineViewer.remove(modelElement);
					refreshUseStatements(modelElement);
					break;
				}
			}
			for (IModelElementDelta child : delta.getAffectedChildren()) {
				visitAndUpdate(child);
			}
		}

		private void refreshUseStatements(IModelElement element) {
			if (fUseStatementsNode != null && (element.getElementType() == IModelElement.IMPORT_CONTAINER
					|| element.getElementType() == IModelElement.IMPORT_DECLARATION)) {
				fOutlineViewer.refresh(fUseStatementsNode);
			}
		}

		protected IModelElementDelta findElement(IModelElement unit, IModelElementDelta delta) {

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
			return (flags & (IModelElementDelta.F_CONTENT
					| IModelElementDelta.F_FINE_GRAINED)) == IModelElementDelta.F_CONTENT;
		}
	}

	private class ProblemChangedCollector implements IResourceDeltaVisitor {
		public Set<Object> toUpdate = new HashSet<Object>();

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource res = delta.getResource();
			if (res instanceof IProject && delta.getKind() == IResourceDelta.CHANGED) {
				IProject project = (IProject) res;
				if (!project.isAccessible()) {
					// only track open PHP projects
					return false;
				}
			}
			visitDelta(delta, res);
			return true;
		}

		private void visitDelta(IResourceDelta delta, IResource resource) {
			if (fInput == null) {
				return;
			}
			try {
				IResource inputResource = fInput.getCorrespondingResource();
				if (inputResource == null || !inputResource.equals(resource)) {
					return;
				}
			} catch (ModelException e) {
				return;
			}
			int kind = delta.getKind();
			if (kind == IResourceDelta.REMOVED || kind == IResourceDelta.ADDED || kind == IResourceDelta.CHANGED) {
				collectChangedElements(delta);
			}
		}

		private void collectChangedElements(IResourceDelta delta) {
			if ((delta.getFlags() & IResourceDelta.MARKERS) == 0) {
				return;
			}
			IMarkerDelta[] markerDeltas = delta.getMarkerDeltas();
			for (int i = 0; i < markerDeltas.length; i++) {
				if (markerDeltas[i].isSubtypeOf(IMarker.PROBLEM)) {
					Integer charStart = (Integer) markerDeltas[i].getAttribute(IMarker.CHAR_START);
					Integer charEnd = (Integer) markerDeltas[i].getAttribute(IMarker.CHAR_END);

					if (charStart != null && charEnd != null) {
						try {
							IModelElement element = fInput.getElementAt(charStart);
							collectToUpdate(element);
						} catch (ModelException e) {
							PHPUiPlugin.log(e);
						}
					}
				}
			}
		}

		private void collectToUpdate(IModelElement element) {
			if (element == null) {
				return;
			}
			toUpdate.add(element);

			// update elements from virtual 'use statements' node
			if (fUseStatementsNode != null && element.getElementType() == IModelElement.IMPORT_DECLARATION) {
				Object[] objects = getChildren(fUseStatementsNode);
				toUpdate.addAll(Arrays.asList(objects));
			}
			element = element.getParent();
			while (element != null && !(element instanceof ISourceModule)) {
				toUpdate.add(element);
				// find parent for container if is in namespace
				if (element instanceof IImportContainer) {
					IModelElement parent = new ImportContainerFinder().findParent((IImportContainer) element);
					if (parent != null) {
						toUpdate.add(parent);
					}
				}
				element = element.getParent();
			}
		}
	}

	private class ProblemChangedListener implements IResourceChangeListener {

		@Override
		public void resourceChanged(final IResourceChangeEvent event) {
			final Control control = fOutlineViewer.getControl();
			if (control == null || control.isDisposed()) {
				return;
			}

			Job job = new Job(PHPUIMessages.PHPOutlineContentProvider_0) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					final ProblemChangedCollector collector = new ProblemChangedCollector();
					try {
						IResourceDelta delta = event.getDelta();
						if (delta != null) {
							delta.accept(collector);
						}
					} catch (CoreException e) {
						PHPUiPlugin.log(e.getStatus());
						return Status.OK_STATUS;
					}

					if (!collector.toUpdate.isEmpty() && fOutlineViewer != null && fOutlineViewer.getControl() != null
							&& !fOutlineViewer.getControl().isDisposed()) {
						Display d = control.getDisplay();
						if (d != null) {
							final Object[] objects = collector.toUpdate.toArray();
							d.asyncExec(new Runnable() {
								@Override
								public void run() {
									fOutlineViewer.update(objects, null);
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

	}

	private class ImportContainerFinder {

		public IModelElement findParent(IImportContainer importContainer) {
			Object[] elements = getElements(fInput);
			for (Object element : elements) {
				IModelElement parent = findParent(element, importContainer);
				if (parent != null) {
					return parent;
				}
			}
			return null;
		}

		private IModelElement findParent(Object object, IImportContainer importContainer) {
			Object[] children = getChildren(object);
			for (Object child : children) {
				if (child == importContainer && object instanceof IModelElement) {
					return (IModelElement) object;
				}
				IModelElement parent = findParent(child, importContainer);
				if (parent != null) {
					return parent;
				}
			}
			return null;
		}
	}

	public class UseStatementsNode extends FakeType {

		public UseStatementsNode(ISourceModule sourceModule) {
			super((ModelElement) sourceModule, PHPUIMessages.PHPOutlineContentProvider_useStatementsNode, 0, null);
		}

		@Override
		public IModelElement[] getChildren() throws ModelException {
			if (fUseStatements == null)
				fUseStatements = getUseStatements(fSourceModule);
			return fUseStatements;
		}

		@Override
		public boolean hasChildren() throws ModelException {
			return getChildren().length > 0;
		}

		@Override
		public Object getElementInfo() throws ModelException {
			return null;
		}

		@Override
		public int getElementType() {
			return IModelElement.IMPORT_CONTAINER;
		}
	}

	private IModelElement[] getUseStatements(ISourceModule sourceModule) {
		// when rename a php file,we should return a empty array for the old
		// sourceModule,or execute SourceParserUtil.getModuleDeclaration()
		// will cache wrong ModuleDeclaration for the non-exist
		// sourceModule,so when we rename the php file back to its original
		// name will get the wrong ModuleDeclaration
		if (null == sourceModule || !sourceModule.exists()) {
			return new IModelElement[0];
		}

		final List<IModelElement> elements = new LinkedList<IModelElement>();
		try {
			sourceModule.accept(new ImportDeclarationVisitor() {

				@Override
				public void visitImport(IImportDeclaration importDeclaration) {
					elements.add(importDeclaration);
				}
			});
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return elements.toArray(new IModelElement[elements.size()]);
	}

}
