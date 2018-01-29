/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;
import org.eclipse.php.internal.debug.ui.views.coverage.CodeCoverageSection;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.connection.PHPUnitConnectionListener;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;
import org.eclipse.php.phpunit.model.elements.*;
import org.eclipse.php.phpunit.ui.launch.PHPUnitLaunchAttributes;
import org.eclipse.php.phpunit.ui.view.actions.ScrollLockAction;
import org.eclipse.php.phpunit.ui.view.actions.ShowNextFailureAction;
import org.eclipse.php.phpunit.ui.view.actions.ShowPreviousFailureAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;

public class PHPUnitView extends ViewPart {

	public static final String NAME = PHPUnitPlugin.ID + ".PHPUnitView"; //$NON-NLS-1$

	static final int VIEW_ORIENTATION_AUTOMATIC = 2;

	static final int VIEW_ORIENTATION_HORIZONTAL = 1;

	static final int VIEW_ORIENTATION_VERTICAL = 0;

	private static PHPUnitView instance = null;

	public static synchronized void activateView(final boolean focus) {
		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window != null) {
				final IWorkbenchPage activePage = window.getActivePage();
				if (activePage == null)
					return;
				try {
					if (focus)
						instance = (PHPUnitView) activePage.showView(NAME);
					else {
						IViewPart foundView = activePage.findView(NAME);
						if (foundView != null) {
							instance = (PHPUnitView) foundView;
						} else {
							instance = (PHPUnitView) activePage.showView(NAME);
						}
					}
				} catch (final PartInitException e) {
					PHPUnitPlugin.log(e);
				}
			}
		});
	}

	public static PHPUnitView getDefault() {
		return instance;
	}

	boolean codeCoverageTabVisibile = false;

	final Image fCodeCoverageIcon = PHPUnitPlugin.createImage(CodeCoverageSection.CODE_COVERAGE_ICON_PATH);

	Image fOriginalViewImage;

	final Image fStackViewIcon = PHPUnitPlugin.createImage("eview16/stackframe.png");//$NON-NLS-1$

	final Image fDiffViewIcon = PHPUnitPlugin.createImage("eview16/diff.png");//$NON-NLS-1$

	boolean running = false;

	/**
	 * Whether the output scrolls and reveals tests as they are executed.
	 */
	protected boolean fAutoScroll = true;

	protected CounterPanel fCounterPanel;

	protected ProgressBar fProgressBar;

	// protected boolean fShowOnErrorOnly = false;
	//
	// protected Image fViewImage;

	private CTabFolder bottomTabFolder;

	private CTabItem codeCoverageTab;

	private CodeCoverageSection fCodeCoverageSection;

	private Action fCollapseAllAction;

	private Composite fCounterComposite;

	/**
	 * The current orientation; either <code>VIEW_ORIENTATION_HORIZONTAL</code>
	 * <code>VIEW_ORIENTATION_VERTICAL</code>.
	 */
	private int fCurrentOrientation;

	private Action fExpandAllAction;

	private Action fFailuresOnlyFilterAction;

	/**
	 * The tab that shows the stack trace of a failure
	 */
	// private DiffTrace fDiffTrace;
	private FailureTrace fFailureTrace;

	/**
	 * Is the UI disposed
	 */
	private boolean fIsDisposed = false;

	private Action fNextAction;

	/**
	 * The current orientation; either <code>VIEW_ORIENTATION_HORIZONTAL</code>
	 * <code>VIEW_ORIENTATION_VERTICAL</code>, or
	 * <code>VIEW_ORIENTATION_AUTOMATIC</code>.
	 */
	private int fOrientation = VIEW_ORIENTATION_AUTOMATIC;

	private Composite fParent;

	private Action fPreviousAction;

	private RerunLastAction fRerunLastTestAction;

	private SashForm fSashForm;

	private ScrollLockAction fScrollLockAction;

	private StopAction fStopAction;

	private TestViewer fTestViewer;

	private ToggleOrientationAction[] fToggleOrientationActions;

	private PHPUnitTestGroup input;

	private ILaunch launch;

	private PHPUnitConnectionListener listener;

	private IProject project;

	private DiffTrace fDiffTrace;

	public PHPUnitView() {
		super();
		if (instance == null) {
			instance = this;
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		fParent = parent;
		addResizeListener(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		parent.setLayout(gridLayout);

		configureToolBar();

		fCounterComposite = createProgressCountPanel(parent);
		fCounterComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		final SashForm sashForm = createSashForm(parent);
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		fOriginalViewImage = getTitleImage();
	}

	public void handleTestSelected(final PHPUnitElement testInfo) {
		if (testInfo == null) {
			showFailure(null);
		} else {
			showFailure(testInfo);
		}
	}

	public void processChangesInUI() {
		if (isDisposed()) {
			return;
		}
		refreshCounters();
		updateViewIcon();

		boolean hasErrorsOrFailures = false;

		if (input != null)
			hasErrorsOrFailures = input.getStatus() > PHPUnitTest.STATUS_PASS;
		fNextAction.setEnabled(hasErrorsOrFailures);
		fPreviousAction.setEnabled(hasErrorsOrFailures);

		boolean empty = true;
		if (input != null && input.getRunCount() > 0)
			empty = false;
		fExpandAllAction.setEnabled(!empty);
		fCollapseAllAction.setEnabled(!empty);
		fTestViewer.processChangesInUI();
	}

	public void refresh(final PHPUnitTestGroup root) {
		activateView(false);
		setInput(root);
		getSite().getShell().getDisplay().asyncExec(() -> {
			final PHPUnitView view = getDefault();
			view.processChangesInUI();
		});
	}

	public void refreshCounters() {
		int totalCount = 0;
		int runCount = 0;
		int errorCount = 0;
		int failureCount = 0;

		if (input != null) {
			runCount = input.getRunCount();
			totalCount = input.getTotalCount();
			errorCount = input.getStatusCount(PHPUnitTest.STATUS_ERROR);
			failureCount = input.getStatusCount(PHPUnitTest.STATUS_FAIL);
			fCounterPanel.setTotal(totalCount);
			fCounterPanel.setRunValue(runCount);
			fCounterPanel.setErrorValue(errorCount);
			fCounterPanel.setFailureValue(failureCount);

			fProgressBar.setMaximum(totalCount);
			fProgressBar.step(errorCount + failureCount > 0, runCount);
		} else if (input == null || input.getTotalCount() == 0) {
			fCounterPanel.reset();
			fProgressBar.step(false, 0);
			fProgressBar.reset();
		}

	}

	public void rerunTest(final int testId, java.util.List<String> filters, String title, final String launchMode) {
		try {
			// run the selected test using the previous fConfiguration
			// configuration
			final ILaunchConfiguration launchConfiguration = getLaunch().getLaunchConfiguration();
			if (launchConfiguration == null) {
				MessageDialog.openInformation(getSite().getShell(), PHPUnitMessages.PHPUnitView_Rerun_Error,
						PHPUnitMessages.PHPUnitView_Rerun_Error_Message);
				return;
			}
			final String configName = MessageFormat.format(PHPUnitMessages.PHPUnitView_Rerun_Config,
					new Object[] { title });
			final ILaunchConfigurationWorkingCopy tmp = launchConfiguration.getWorkingCopy();

			tmp.setAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_RERUN, configName);

			tmp.setAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_FILTER, filters);
			tmp.launch(launchMode, null);
		} catch (final CoreException e) {
			ErrorDialog.openError(getSite().getShell(), PHPUnitMessages.PHPUnitView_Cant_Rerun, e.getMessage(),
					e.getStatus());
		}
	}

	/**
	 * Starts the run test
	 */
	public void reset() {
		input = null;
		fTestViewer.registerActiveSession(null);
		fTestViewer.processChangesInUI();
		processChangesInUI();
	}

	public void selectNextFailure() {
		fTestViewer.selectFailure(true);
	}

	public void selectPreviousFailure() {
		fTestViewer.selectFailure(false);
	}

	public void setAutoScroll(final boolean scroll) {
		fAutoScroll = scroll;
	}

	public void setCodeCoverageTabVisible(final boolean visible) {
		Display.getDefault().asyncExec(() -> {
			if (!visible) {
				if (codeCoverageTab != null && !codeCoverageTab.isDisposed()) {
					codeCoverageTab.dispose();
				}
			} else if (codeCoverageTab == null || codeCoverageTab.isDisposed()) {
				createCodeCoverageTab(bottomTabFolder);
			}
		});
		codeCoverageTabVisibile = visible;
	}

	@Override
	public void setFocus() {
		if (fTestViewer != null)
			fTestViewer.getTestViewerControl().setFocus();
	}

	public void setInput(final PHPUnitTestGroup newInput) {
		final PHPUnitView view = getDefault();
		if (input != newInput) {
			input = newInput;
			view.fTestViewer.registerActiveSession(newInput);
		}

	}

	public void showCodeCoverage(final CodeCoverageData[] coveredFiles) {
		if (codeCoverageTabVisibile) {
			getSite().getShell().getDisplay().asyncExec(() -> fCodeCoverageSection.showCodeCoverage(coveredFiles));
		}
	}

	public void startRunning(final ILaunch launch, final PHPUnitConnectionListener listener) {
		running = true;
		setLaunch(launch);
		this.listener = listener;
		Display.getDefault().asyncExec(() -> reset());
		fRerunLastTestAction.setEnabled(false);
		fStopAction.setEnabled(true);
	}

	public void stop(final PHPUnitTestGroup root, final String message) {
		instance.stopRunning(false);
		if (PHPUnitMessageParser.getInstance().isInProgress()) {
			instance.fProgressBar.stopped();
		}
		instance.fRerunLastTestAction.setEnabled(true);

		PHPUnitTestCase currentTestCase = PHPUnitMessageParser.getInstance().getCurrentTestCase();
		if (currentTestCase != null) {
			PHPUnitTestException.addAbnormalException(currentTestCase);
			fTestViewer.registerAutoScrollTarget(currentTestCase);
			fTestViewer.registerFailedForAutoScroll(currentTestCase);
		}
		refresh(root);
		if (root.getRunCount() == 0 && currentTestCase == null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(() -> MessageDialog.openError(getSite().getShell(),
					PHPUnitMessages.PHPUnitWizard_Error_Title, message));
		}
	}

	/**
	 * Stops the currently running test and shuts down the RemoteTestRunner
	 */
	public void stopRunning(boolean terminateLaunch) {
		instance.fStopAction.setEnabled(false);
		listener.shutdown(terminateLaunch);
		if (input != null) {
			PHPUnitMessageParser.getInstance().setInProgress(false);
		}
		running = false;
	}

	void computeOrientation() {
		if (fOrientation != VIEW_ORIENTATION_AUTOMATIC) {
			fCurrentOrientation = fOrientation;
			setOrientation(fCurrentOrientation);
		} else {
			final Point size = fParent.getSize();
			if (size.x != 0 && size.y != 0)
				if (size.x > size.y) {
					setOrientation(VIEW_ORIENTATION_HORIZONTAL);
				} else {
					setOrientation(VIEW_ORIENTATION_VERTICAL);
				}
		}
	}

	CTabItem createTraceTab(final CTabFolder parent) {
		final CTabItem traceTab = new CTabItem(parent, SWT.NONE);
		traceTab.setText(PHPUnitMessages.PHPUnitView_Tab_Trace);
		traceTab.setImage(fStackViewIcon);
		final ViewForm traceForm = new ViewForm(parent, SWT.NONE);
		final ToolBar failureToolBar = new ToolBar(traceForm, SWT.FLAT | SWT.WRAP);
		traceForm.setTopCenter(failureToolBar);
		fFailureTrace = new FailureTrace(traceForm, this, failureToolBar);
		traceForm.setContent(fFailureTrace.getComposite());
		traceTab.setControl(traceForm);
		return traceTab;
	}

	CTabItem createDiffTab(final CTabFolder parent) {
		final CTabItem diffTab = new CTabItem(parent, SWT.NONE);
		diffTab.setText(PHPUnitMessages.PHPUnitView_Tab_Diff);
		diffTab.setImage(fDiffViewIcon);
		final ViewForm diffForm = new ViewForm(parent, SWT.NONE);
		final ToolBar failureToolBar = new ToolBar(diffForm, SWT.FLAT | SWT.WRAP);
		diffForm.setTopCenter(failureToolBar);
		fDiffTrace = new DiffTrace(diffForm, this);
		diffForm.setContent(fDiffTrace.getComposite());
		diffTab.setControl(diffForm);
		return diffTab;
	}

	void setShowFailuresOnly(final boolean failuresOnly) {
		setFilterAndLayout(failuresOnly);
	}

	protected void createBottomTabFolder(final ViewForm parent) {
		bottomTabFolder = SWTUtil.createTabFolder(parent);
		parent.setContent(bottomTabFolder);
		final CTabItem traceTab = createTraceTab(bottomTabFolder);

		createDiffTab(bottomTabFolder);
		setCodeCoverageTabVisible(codeCoverageTabVisibile);
		bottomTabFolder.setSelection(traceTab);
	}

	protected CTabItem createCodeCoverageTab(final CTabFolder parent) {
		codeCoverageTab = new CTabItem(parent, SWT.NONE);
		codeCoverageTab.setText(PHPUnitMessages.PHPUnitView_Tab_Coverage);
		codeCoverageTab.setImage(fCodeCoverageIcon);
		final ViewForm codeCoverageForm = createCodeCoverageForm(parent);
		codeCoverageTab.setControl(codeCoverageForm);
		codeCoverageTab.addDisposeListener(e -> fCodeCoverageSection.dispose());
		return codeCoverageTab;
	}

	protected Composite createProgressCountPanel(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		setCounterColumns(layout);

		fCounterPanel = new CounterPanel(composite);
		fCounterPanel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		fProgressBar = new ProgressBar(composite);
		fProgressBar.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		return composite;
	}

	protected void setLaunch(final ILaunch launch) {
		this.launch = launch;
		try {
			final String projectName = getDefault().getLaunch().getLaunchConfiguration()
					.getAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_PROJECT, (String) null);
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			project = workspaceRoot.getProject(projectName);

			final String codeCoverageAttributeValue = launch
					.getAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_COLLECT_CODE_COVERAGE);
			if (codeCoverageAttributeValue != null && !"profile".equals(launch.getLaunchMode())) //$NON-NLS-1$
				setCodeCoverageTabVisible(Integer.parseInt(codeCoverageAttributeValue) > 0);
			else
				setCodeCoverageTabVisible(false);
			fRerunLastTestAction.setLaunch(launch);
		} catch (final CoreException e) {
			PHPUnitPlugin.log(e);
		}
	}

	private void addResizeListener(final Composite parent) {
		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				computeOrientation();
			}
		});
	}

	private void configureToolBar() {
		final IActionBars actionBars = getViewSite().getActionBars();
		final IToolBarManager toolBar = actionBars.getToolBarManager();
		final IMenuManager viewMenu = actionBars.getMenuManager();

		fNextAction = new ShowNextFailureAction(this);
		fNextAction.setEnabled(false);
		actionBars.setGlobalActionHandler(ActionFactory.NEXT.getId(), fNextAction);

		fPreviousAction = new ShowPreviousFailureAction(this);
		fPreviousAction.setEnabled(false);
		actionBars.setGlobalActionHandler(ActionFactory.PREVIOUS.getId(), fPreviousAction);

		fStopAction = new StopAction();
		fStopAction.setEnabled(false);

		fRerunLastTestAction = new RerunLastAction();

		fFailuresOnlyFilterAction = new FailuresOnlyFilterAction();

		fScrollLockAction = new ScrollLockAction(this);
		fScrollLockAction.setChecked(!fAutoScroll);

		fToggleOrientationActions = new ToggleOrientationAction[] {
				new ToggleOrientationAction(this, VIEW_ORIENTATION_VERTICAL),
				new ToggleOrientationAction(this, VIEW_ORIENTATION_HORIZONTAL),
				new ToggleOrientationAction(this, VIEW_ORIENTATION_AUTOMATIC) };

		fExpandAllAction = new ExpandAllAction();
		fExpandAllAction.setEnabled(false);

		fCollapseAllAction = new CollapseAllAction();
		fCollapseAllAction.setEnabled(false);

		toolBar.add(fNextAction);
		toolBar.add(fPreviousAction);
		toolBar.add(fExpandAllAction);
		toolBar.add(fCollapseAllAction);
		toolBar.add(fFailuresOnlyFilterAction);
		toolBar.add(fScrollLockAction);
		toolBar.add(new Separator());
		toolBar.add(fRerunLastTestAction);
		toolBar.add(fStopAction);
		viewMenu.add(new Separator());

		final MenuManager layoutSubMenu = new MenuManager(PHPUnitMessages.PHPUnitView_Layout);
		for (ToggleOrientationAction fToggleOrientationAction : fToggleOrientationActions) {
			layoutSubMenu.add(fToggleOrientationAction);
		}
		viewMenu.add(layoutSubMenu);
		viewMenu.add(new Separator());

		actionBars.updateActionBars();
	}

	/**
	 * @param parent
	 * @return
	 */
	private ViewForm createCodeCoverageForm(final Composite parent) {
		final ViewForm codeCoverageForm = new ViewForm(parent, SWT.NONE);
		fCodeCoverageSection = new CodeCoverageSection(codeCoverageForm, this, null /* codeCoverageToolBar */);
		fCodeCoverageSection.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (parentElement instanceof IWorkspaceRoot && element instanceof ISourceModule) {
					return false;
				}
				return true;
			}
		});
		codeCoverageForm.setContent(fCodeCoverageSection.getComposite());
		return codeCoverageForm;
	}

	private SashForm createSashForm(final Composite parent) {
		fSashForm = new SashForm(parent, SWT.VERTICAL);

		final ViewForm top = new ViewForm(fSashForm, SWT.NONE);

		final Composite empty = new Composite(top, SWT.NONE);
		empty.setLayout(new Layout() {
			@Override
			protected Point computeSize(final Composite composite, final int wHint, final int hHint,
					final boolean flushCache) {
				return new Point(1, 1); // (0, 0) does not work with
				// super-intelligent ViewForm
			}

			@Override
			protected void layout(final Composite composite, final boolean flushCache) {
			}
		});
		top.setTopLeft(empty); // makes ViewForm draw the horizontal separator
		// line ...
		fTestViewer = new TestViewer(top, this);
		top.setContent(fTestViewer.getTestViewerControl());

		final ViewForm bottom = new ViewForm(fSashForm, SWT.NONE);

		createBottomTabFolder(bottom);

		fSashForm.setWeights(new int[] { 50, 50 });
		return fSashForm;
	}

	private void disposeImages() {
		fStackViewIcon.dispose();
		fCodeCoverageIcon.dispose();
	}

	private void postSyncRunnable(final Runnable r) {
		if (!isDisposed()) {
			getSite().getShell().getDisplay().syncExec(r);
		}
	}

	private void setCounterColumns(final GridLayout layout) {
		if (fCurrentOrientation == VIEW_ORIENTATION_HORIZONTAL) {
			layout.numColumns = 2;
		} else {
			layout.numColumns = 1;
		}
	}

	private void setFilterAndLayout(final boolean failuresOnly) {
		fFailuresOnlyFilterAction.setChecked(failuresOnly);
		fTestViewer.setShowFailuresOnly(failuresOnly);
	}

	private void setOrientation(final int orientation) {
		if (fSashForm == null || fSashForm.isDisposed())
			return;
		final boolean horizontal = orientation == VIEW_ORIENTATION_HORIZONTAL;
		fSashForm.setOrientation(horizontal ? SWT.HORIZONTAL : SWT.VERTICAL);
		for (ToggleOrientationAction fToggleOrientationAction : fToggleOrientationActions) {
			fToggleOrientationAction.setChecked(fOrientation == fToggleOrientationAction.getOrientation());
		}
		fCurrentOrientation = orientation;
		final GridLayout layout = (GridLayout) fCounterComposite.getLayout();
		setCounterColumns(layout);
		fParent.layout();
	}

	private void showFailure(final PHPUnitElement failure) {
		postSyncRunnable(() -> {
			if (!isDisposed()) {
				fDiffTrace.showFailure(failure);
				fFailureTrace.showFailure(failure);
			}
		});
	}

	private void updateViewIcon() {
		firePropertyChange(PROP_TITLE);
	}

	private class CollapseAllAction extends Action {
		public CollapseAllAction() {
			setText(PHPUnitMessages.PHPUnitView_Collapse_Name);
			setToolTipText(PHPUnitMessages.PHPUnitView_Collapse_ToolTip);

			ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
			setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL_DISABLED));
			setHoverImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL));
			setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL));
		}

		@Override
		public void run() {
			fTestViewer.collapseAll();
		}
	}

	private class ExpandAllAction extends Action {
		public ExpandAllAction() {
			setText(PHPUnitMessages.PHPUnitView_Expand_Name);
			setToolTipText(PHPUnitMessages.PHPUnitView_Expand_ToolTip);
			setDisabledImageDescriptor(PHPUnitPlugin.getImageDescriptor("dlcl16/expandall.png")); //$NON-NLS-1$
			setHoverImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/expandall.png")); //$NON-NLS-1$
			setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/expandall.png")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			fTestViewer.expandAll();
		}
	}

	private class FailuresOnlyFilterAction extends Action {
		public FailuresOnlyFilterAction() {
			super(PHPUnitMessages.PHPUnitView_Failures_Name, AS_CHECK_BOX);
			setToolTipText(PHPUnitMessages.PHPUnitView_Failures_Tooltip);
			setImageDescriptor(PHPUnitPlugin.getImageDescriptor("obj16/failures.png")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			setShowFailuresOnly(isChecked());
		}
	}

	public class RerunAction extends Action {
		protected ILaunchConfiguration fConfiguration;
		protected String fMode;
		private RerunLastAction fParent;

		public RerunAction(ILaunch launch, RerunLastAction parent) {
			if (launch != null) {
				fConfiguration = launch.getLaunchConfiguration();
				fMode = launch.getLaunchMode();
				String name = fConfiguration.getName();

				try {
					if (fConfiguration.hasAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_RERUN)) {
						name = fConfiguration.getAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_RERUN,
								fConfiguration.getName());
					}
				} catch (CoreException e) {
					Logger.logException(e);
				}
				setToolTipText(name);
				setText(name.replace('@', '#'));
			}
			fParent = parent;
			setHoverImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/relaunch.png")); //$NON-NLS-1$
			setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/relaunch.png")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			if (fConfiguration != null) {
				DebugUITools.launch(fConfiguration, fMode);
			}
			if (fParent != null) {
				fParent.setEnabled(false);
			}
		}

	}

	private class RerunLastAction extends RerunAction {
		private static final int HISTORY_DEPTH = 10;
		java.util.List<RerunAction> previousLaunches = new ArrayList<>();

		public RerunLastAction() {
			super(null, null);
			setToolTipText(PHPUnitMessages.PHPUnitView_Run_ToolTip);
			setEnabled(false);
			setMenuCreator(new IMenuCreator() {

				private Menu fMenu;

				@Override
				public void dispose() {
				}

				@Override
				public Menu getMenu(Control parent) {
					if (fMenu != null)
						fMenu.dispose();
					fMenu = new Menu(parent);
					for (Object element : previousLaunches) {
						RerunAction action = (RerunAction) element;
						IContributionItem item = new ActionContributionItem(action);
						item.fill(fMenu, -1);
					}
					return fMenu;
				}

				@Override
				public Menu getMenu(Menu parent) {
					return null;
				}
			});
		}

		public void setLaunch(ILaunch launch) {
			fConfiguration = launch.getLaunchConfiguration();
			fMode = launch.getLaunchMode();
			int i;
			for (i = 0; i < previousLaunches.size(); i++) {
				if (previousLaunches.get(i).fConfiguration.equals(fConfiguration)) {
					previousLaunches.remove(i);
					break;
				}
			}
			previousLaunches.add(0, new RerunAction(launch, this));
			for (i = HISTORY_DEPTH; i < previousLaunches.size(); ++i) {
				previousLaunches.remove(i);
			}
		}
	}

	private class StopAction extends Action {
		public StopAction() {
			setText(PHPUnitMessages.PHPUnitView_Stop_Name);
			setToolTipText(PHPUnitMessages.PHPUnitView_Stop_ToolTip);

			ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
			setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_STOP_DISABLED));
			setHoverImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
			setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
		}

		@Override
		public void run() {
			stopRunning(true);
		}
	}

	private class ToggleOrientationAction extends Action {
		private final int fActionOrientation;

		public ToggleOrientationAction(final PHPUnitView v, final int orientation) {
			super("", AS_RADIO_BUTTON); //$NON-NLS-1$
			if (orientation == PHPUnitView.VIEW_ORIENTATION_HORIZONTAL) {
				setText(PHPUnitMessages.PHPUnitView_Orient_Horizont);
				setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/th_horizontal.png")); //$NON-NLS-1$
			} else if (orientation == PHPUnitView.VIEW_ORIENTATION_VERTICAL) {
				setText(PHPUnitMessages.PHPUnitView_Orient_Vertical);
				setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/th_vertical.png")); //$NON-NLS-1$
			} else if (orientation == PHPUnitView.VIEW_ORIENTATION_AUTOMATIC) {
				setText(PHPUnitMessages.PHPUnitView_Orient_Auto);
				setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/th_automatic.png")); //$NON-NLS-1$
			}
			fActionOrientation = orientation;
		}

		public int getOrientation() {
			return fActionOrientation;
		}

		@Override
		public void run() {
			if (isChecked()) {
				fOrientation = fActionOrientation;
				computeOrientation();
			}
		}
	}

	@Override
	public synchronized void dispose() {
		fIsDisposed = true;
		setInput(null);

		disposeImages();
	}

	public PHPUnitElement getTestElement(final int testId) {
		if (testId != 0) {
			return null;
		}
		return PHPUnitElementManager.getInstance().findTest(testId);
	}

	public TestViewer getViewer() {
		return fTestViewer;
	}

	public boolean isAutoScroll() {
		return fAutoScroll;
	}

	public boolean isDisposed() {
		return fIsDisposed || fCounterPanel.isDisposed();
	}

	public PHPUnitTestGroup getInput() {
		return input;
	}

	public synchronized ILaunch getLaunch() {
		return launch;
	}

	public IProject getProject() {
		return project;
	}

}
