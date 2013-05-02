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
package org.eclipse.php.internal.ui.functions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.ui.viewsupport.StatusBarUpdater;
import org.eclipse.jface.action.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.internal.ui.util.PHPManualFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Function composite for functions
 */
public class PHPFunctionsPart extends ViewPart implements IPartListener {

	protected IPreferenceStore fStore = PHPUiPlugin.getDefault()
			.getPreferenceStore();

	private TreeViewer fViewer;
	private PHPFunctionsContentProvider fContentProvider;
	private PHPFunctionsLabelProvider fLabelProvider;

	private Menu fContextMenu;
	private String fWorkingSetName;

	private ShowFunctionHelpAction showFunctionHelpAction;

	private boolean shouldRefresh = true;

	public PHPFunctionsPart() {
	}

	public void createPartControl(Composite parent) {
		fViewer = createViewer(parent);
		fViewer.setUseHashlookup(true);
		// sort entries alphabetically
		fViewer.setSorter(new PHPFunctionsSorter());
		setProviders();

		// filter out children for methods
		fViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (isMethodVariable(parentElement, element)) {
					return false;
				}
				return true;
			}
		});

		setUpPopupMenu();

		addDoubleClickListener();
		addMouseTrackListener();
		getSite().getPage().addPartListener(this);

		IStatusLineManager slManager = getViewSite().getActionBars()
				.getStatusLineManager();
		fViewer.addSelectionChangedListener(new StatusBarUpdater(slManager));

		fViewer.getTree().addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				Display.getCurrent().asyncExec(new Runnable() {

					public void run() {
						if (!fViewer.getTree().isDisposed()
								&& (shouldRefresh || fViewer.getTree()
										.getItems().length == 0)) {
							fViewer.refresh();
							shouldRefresh = false;
						}
					}

				});
			}

			public void focusLost(FocusEvent e) {
			}

		});
		updateTitle();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.PHP_FUNCTIONS_VIEW);
	}

	/**
	 * Checks whether the given element is a method variable.
	 * 
	 * @param parentElement
	 * @param element
	 * @return whether the given element is a method variable
	 */
	public static boolean isMethodVariable(Object parentElement, Object element) {
		if (parentElement instanceof IModelElement
				&& element instanceof IModelElement) {
			if (((IModelElement) parentElement).getElementType() == IModelElement.METHOD
					&& ((IModelElement) element).getElementType() == IModelElement.FIELD) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private void setUpPopupMenu() {
		showFunctionHelpAction = new ShowFunctionHelpAction();

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		fContextMenu = menuMgr.createContextMenu(fViewer.getTree());
		menuMgr.add(showFunctionHelpAction);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				ISelection selection = fViewer.getSelection();
				if (!selection.isEmpty()) {
					IStructuredSelection s = (IStructuredSelection) selection;
					String url = PHPManualFactory.getManual().getURLForManual(
							(IModelElement) s.getFirstElement());
					if (url != null) {
						showFunctionHelpAction.setEnabled(true);
						showFunctionHelpAction.setURL(url);
					} else {
						showFunctionHelpAction.setEnabled(false);
					}
				} else {
					showFunctionHelpAction.setEnabled(false);
				}
			}
		});

		fViewer.getTree().setMenu(fContextMenu);

		IWorkbenchPartSite site = getSite();
		site.setSelectionProvider(fViewer);

	}

	private void updateInputForCurrentEditor(final IEditorPart editorPart) {
		Job updateInput = new UIJob("Loading functions") { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(getName(), 1);

				try {
					// retrieves the project and the content
					IScriptProject scriptProject = getCurrentScriptProject();
					if (scriptProject == null
							|| !(scriptProject instanceof ScriptProject)) {
						return Status.CANCEL_STATUS;
					}

					Object newInput;
					try {
						IBuildpathContainer languageContainer = DLTKCore
								.getBuildpathContainer(
										new Path(
												LanguageModelInitializer.CONTAINER_PATH),
										scriptProject);
						IBuildpathEntry[] buildpathEntries = languageContainer
								.getBuildpathEntries();
						List<IProjectFragment> fragments = new LinkedList<IProjectFragment>();
						for (IBuildpathEntry buildpathEntry : buildpathEntries) {
							IProjectFragment fragment = ((ScriptProject) scriptProject)
									.getProjectFragment(buildpathEntry
											.getPath());
							if (fragment != null) {
								fragments.add(fragment);
							}
						}
						newInput = (IProjectFragment[]) fragments
								.toArray(new IProjectFragment[fragments.size()]);

					} catch (ModelException e) {
						return Status.CANCEL_STATUS;
					}

					Object currentInput = fViewer.getInput();

					// set the language settings as input to the content
					// provider
					if (!newInput.equals(currentInput)
							&& fViewer.getContentProvider() != null) {
						fViewer.setInput(newInput);
					}
					return Status.OK_STATUS;

				} catch (Exception e) {
					Logger.logException(e);
					return Status.CANCEL_STATUS;
				} finally {
					monitor.done();
				}
			}

			/**
			 * Gets the project: either by searching the current open editor or
			 * (if there is no open editor) by searching for the first opened
			 * php project
			 * 
			 * @return the selected project
			 * @throws CoreException
			 */
			private final IScriptProject getCurrentScriptProject()
					throws CoreException {
				final PHPStructuredEditor phpEditor = EditorUtility
						.getPHPStructuredEditor(editorPart);
				if (phpEditor != null) {
					return phpEditor.getProject();
				}

				final IProject[] projects = ResourcesPlugin.getWorkspace()
						.getRoot().getProjects();
				for (IProject project : projects) {
					if (PHPToolkitUtil.isPhpProject(project)) {
						return DLTKCore.create(project);
					}
				}
				return null;
			}

		};
		updateInput.schedule();
	}

	private TreeViewer createViewer(Composite composite) {
		PatternFilter patternFilter = new PatternFilter();
		// patternFilter.setIncludeLeadingWildcard(true);
		FilteredTree filteredTree = new FilteredTree(composite, SWT.SINGLE
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER, patternFilter, true);
		return filteredTree.getViewer();
	}

	private void addMouseTrackListener() {
		final Tree tree = fViewer.getTree();
		tree.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseHover(MouseEvent e) {
				TreeItem item = tree.getItem(new Point(e.x, e.y));
				if (item != null) {
					Object o = item.getData();
					if (o instanceof IModelElement) {
						tree.setToolTipText(fLabelProvider.getText(o));
					}
				}
			}

		});
	}

	private void addDoubleClickListener() {
		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IEditorPart editor = EditorUtility
						.getPHPStructuredEditor(getViewSite().getPage()
								.getActiveEditor());
				StructuredSelection selection = (StructuredSelection) fViewer
						.getSelection();
				if (editor != null && editor instanceof ITextEditor
						&& selection != null && !selection.isEmpty()) {
					if (selection.getFirstElement() instanceof IModelElement) {
						IModelElement codeData = (IModelElement) selection
								.getFirstElement();
						ITextEditor textEditor = (ITextEditor) editor;
						int caretPosition = ((ITextSelection) textEditor
								.getSelectionProvider().getSelection())
								.getOffset();
						IDocument document = textEditor.getDocumentProvider()
								.getDocument(textEditor.getEditorInput());
						try {
							document.replace(caretPosition, 0, codeData
									.getElementName());
						} catch (BadLocationException e) {
							Logger.logException(e);
						}
						textEditor.setFocus();
						textEditor
								.getSelectionProvider()
								.setSelection(
										new TextSelection(
												document,
												caretPosition
														+ codeData
																.getElementName()
																.length(), 0));
					}
				}
			}
		});
	}

	public void setFocus() {
		if (fViewer != null && !fViewer.getTree().isDisposed()) {
			fViewer.getTree().setFocus();
		}
	}

	private void setProviders() {
		if (fContentProvider == null) {
			fContentProvider = new PHPFunctionsContentProvider();
			fViewer.setContentProvider(fContentProvider);
		}

		if (fLabelProvider == null) {
			fLabelProvider = new PHPFunctionsLabelProvider();
			fViewer.setLabelProvider(fLabelProvider);

		}
	}

	public void dispose() {
		getSite().getPage().removePartListener(this);
		if (fContextMenu != null && !fContextMenu.isDisposed()) {
			fContextMenu.dispose();
		}
		if (fContentProvider != null) {
			fContentProvider.dispose();
		}
		if (fLabelProvider != null) {
			fLabelProvider.dispose();
		}
		super.dispose();
	}

	public void partActivated(IWorkbenchPart part) {
		if (part.equals(this)) {
			Display.getCurrent().asyncExec(new Runnable() {
				public void run() {
					setFocus();
				}

			});
		}

		part = EditorUtility.getPHPStructuredEditor(part);
		if (PHPFunctionsPart.this.getViewer().getTree().getVisible()
				&& part != null) {
			updateInputForCurrentEditor((IEditorPart) part);
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
		if (part.equals(this) && getViewer().getInput() == null) {
			updateInputForCurrentEditor(null);
			setFocus();
		}

		/*
		 * part = EditorUtility.getPHPStructuredEditor(part); if
		 * (PHPFunctionsPart.this.getViewer().getTree().getVisible() && part !=
		 * null) { updateInputForCurrentEditor((IEditorPart) part); }
		 */
	}

	public TreeViewer getViewer() {
		return fViewer;
	}

	public void collapseAll() {
		try {
			fViewer.getControl().setRedraw(false);
			fViewer.collapseToLevel(getViewPartInput(),
					AbstractTreeViewer.ALL_LEVELS);
		} finally {
			fViewer.getControl().setRedraw(true);
		}
	}

	public Object getViewPartInput() {
		if (fViewer != null) {
			return fViewer.getInput();
		}
		return null;
	}

	String getFrameName(Object element) {
		if (element instanceof IModelElement) {
			return ((IModelElement) element).getElementName();
		} else {
			return fLabelProvider.getText(element);
		}
	}

	String getToolTipText(Object element) {
		String result;
		if (!(element instanceof IResource)) {
			result = fLabelProvider.getText(element);
		} else {
			IPath path = ((IResource) element).getFullPath();
			if (path.isRoot()) {
				result = PHPUIMessages.PHPExplorer_title;
			} else {
				result = path.makeRelative().toString();
			}
		}

		if (fWorkingSetName == null)
			return result;

		String wsstr = NLS.bind(PHPUIMessages.PHPExplorer_toolTip,
				new String[] { fWorkingSetName });
		if (result.length() == 0)
			return wsstr;
		return NLS.bind(PHPUIMessages.PHPExplorer_toolTip2, new String[] {
				result, fWorkingSetName });
	}

	void updateTitle() {
		Object input = fViewer.getInput();
		if (input == null) {
			setContentDescription(""); //$NON-NLS-1$
			setTitleToolTip(""); //$NON-NLS-1$
		} else {
			String inputText = fLabelProvider.getText(input);
			;
			setContentDescription(inputText);
			setTitleToolTip(getToolTipText(input));
		}
	}

	class ShowFunctionHelpAction extends Action {
		private String url;

		public ShowFunctionHelpAction() {
			super(PHPUIMessages.PHPFunctionsPart_0); 
		}

		public void setURL(String url) {
			this.url = url;
		}

		public void run() {
			if (url != null) {
				PHPManualFactory.getManual().showFunctionHelp(url);
			}
		}
	}
}
