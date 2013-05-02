package org.eclipse.php.internal.debug.ui.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.ui.*;

public class EvaluationContextManager implements IWindowListener,
		IDebugContextListener {

	private static EvaluationContextManager fgManager;

	/**
	 * System property indicating a stack frame is selected in the debug view
	 * with an <code>IJavaStackFrame</code> adapter.
	 */
	private static final String DEBUGGER_ACTIVE = PHPDebugUIPlugin.ID
			+ ".debuggerActive"; //$NON-NLS-1$
	/**
	 * System property indicating an element is selected in the debug view that
	 * is an instanceof <code>IJavaStackFrame</code> or <code>IJavaThread</code>
	 * .
	 */
	// private static final String INSTANCE_OF_IJAVA_STACK_FRAME =
	// PHPDebugUIPlugin.ID
	//			+ ".instanceof.IJavaStackFrame"; 
	/**
	 * System property indicating the frame in the debug view supports 'force
	 * return'
	 */
	// private static final String SUPPORTS_FORCE_RETURN = PHPDebugUIPlugin.ID
	//			+ ".supportsForceReturn"; 	
	/**
	 * System property indicating whether the frame in the debug view supports
	 * instance and reference retrieval (1.5 VMs and later).
	 */
	// private static final String SUPPORTS_INSTANCE_RETRIEVAL =
	// PHPDebugUIPlugin.ID
	//			+ ".supportsInstanceRetrieval"; 

	private Map fContextsByPage = null;

	private IWorkbenchWindow fActiveWindow;

	private EvaluationContextManager() {
		DebugUITools.getDebugContextManager().addDebugContextListener(this);
	}

	public static void startup() {
		Runnable r = new Runnable() {
			public void run() {
				if (fgManager == null) {
					fgManager = new EvaluationContextManager();
					IWorkbench workbench = PlatformUI.getWorkbench();
					IWorkbenchWindow[] windows = workbench
							.getWorkbenchWindows();
					for (int i = 0; i < windows.length; i++) {
						fgManager.windowOpened(windows[i]);
					}
					workbench.addWindowListener(fgManager);
					fgManager.fActiveWindow = workbench
							.getActiveWorkbenchWindow();
				}
			}
		};
		PHPDebugUIPlugin.getStandardDisplay().asyncExec(r);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWindowListener#windowActivated(org.eclipse.ui.
	 * IWorkbenchWindow)
	 */
	public void windowActivated(IWorkbenchWindow window) {
		fActiveWindow = window;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWindowListener#windowClosed(org.eclipse.ui.IWorkbenchWindow
	 * )
	 */
	public void windowClosed(IWorkbenchWindow window) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWindowListener#windowDeactivated(org.eclipse.ui.
	 * IWorkbenchWindow)
	 */
	public void windowDeactivated(IWorkbenchWindow window) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWindowListener#windowOpened(org.eclipse.ui.IWorkbenchWindow
	 * )
	 */
	public void windowOpened(IWorkbenchWindow window) {
	}

	/**
	 * Sets the evaluation context for the given page, and notes that a valid
	 * execution context exists.
	 * 
	 * @param page
	 * @param frame
	 */
	private void setContext(IWorkbenchPage page, IStackFrame frame,
			boolean instOf) {
		if (fContextsByPage == null) {
			fContextsByPage = new HashMap();
		}
		fContextsByPage.put(page, frame);
		System.setProperty(DEBUGGER_ACTIVE, "true"); //$NON-NLS-1$
		// if (frame.canForceReturn()) {
		//				System.setProperty(SUPPORTS_FORCE_RETURN, "true"); 
		// } else {
		//				System.setProperty(SUPPORTS_FORCE_RETURN, "false"); 
		// }
		// if
		// (((IJavaDebugTarget)frame.getDebugTarget()).supportsInstanceRetrieval()){
		//				System.setProperty(SUPPORTS_INSTANCE_RETRIEVAL, "true"); 
		// } else {
		//				System.setProperty(SUPPORTS_INSTANCE_RETRIEVAL, "false"); 
		// }
		// if (instOf) {
		//				System.setProperty(INSTANCE_OF_IJAVA_STACK_FRAME, "true"); 
		// } else {
		//				System.setProperty(INSTANCE_OF_IJAVA_STACK_FRAME, "false"); 
		// }
	}

	/**
	 * Removes an evaluation context for the given page, and determines if any
	 * valid execution context remain.
	 * 
	 * @param page
	 */
	private void removeContext(IWorkbenchPage page) {
		if (fContextsByPage != null) {
			fContextsByPage.remove(page);
			// if (fContextsByPage.isEmpty()) {
			//				System.setProperty(DEBUGGER_ACTIVE, "false"); 
			//				System.setProperty(INSTANCE_OF_IJAVA_STACK_FRAME, "false"); 
			//				System.setProperty(SUPPORTS_FORCE_RETURN, "false"); 
			//				System.setProperty(SUPPORTS_INSTANCE_RETRIEVAL, "false"); 
			// }
		}
	}

	/**
	 * Returns the evaluation context for the given part, or <code>null</code>
	 * if none. The evaluation context corresponds to the selected stack frame
	 * in the following priority order:
	 * <ol>
	 * <li>stack frame in the same page</li>
	 * <li>stack frame in the same window</li>
	 * <li>stack frame in active page of other window</li>
	 * <li>stack frame in page of other windows</li>
	 * </ol>
	 * 
	 * @param part
	 *            the part that the evaluation action was invoked from
	 * @return the stack frame that supplies an evaluation context, or
	 *         <code>null</code> if none
	 */
	public static IStackFrame getEvaluationContext(IWorkbenchPart part) {
		IWorkbenchPage page = part.getSite().getPage();
		IStackFrame frame = getContext(page);
		if (frame == null) {
			return getEvaluationContext(page.getWorkbenchWindow());
		}
		return frame;
	}

	private static IStackFrame getContext(IWorkbenchPage page) {
		if (fgManager != null) {
			if (fgManager.fContextsByPage != null) {
				return (IStackFrame) fgManager.fContextsByPage.get(page);
			}
		}
		return null;
	}

	/**
	 * Returns the evaluation context for the given window, or <code>null</code>
	 * if none. The evaluation context corresponds to the selected stack frame
	 * in the following priority order:
	 * <ol>
	 * <li>stack frame in active page of the window</li>
	 * <li>stack frame in another page of the window</li>
	 * <li>stack frame in active page of another window</li>
	 * <li>stack frame in a page of another window</li>
	 * </ol>
	 * 
	 * @param window
	 *            the window that the evaluation action was invoked from, or
	 *            <code>null</code> if the current window should be consulted
	 * @return the stack frame that supplies an evaluation context, or
	 *         <code>null</code> if none
	 * @return IJavaStackFrame
	 */
	public static IStackFrame getEvaluationContext(IWorkbenchWindow window) {
		List alreadyVisited = new ArrayList();
		if (window == null) {
			window = fgManager.fActiveWindow;
		}
		return getEvaluationContext(window, alreadyVisited);
	}

	private static IStackFrame getEvaluationContext(IWorkbenchWindow window,
			List alreadyVisited) {
		IWorkbenchPage activePage = window.getActivePage();
		IStackFrame frame = null;
		if (activePage != null) {
			frame = getContext(activePage);
		}
		if (frame == null) {
			IWorkbenchPage[] pages = window.getPages();
			for (int i = 0; i < pages.length; i++) {
				if (activePage != pages[i]) {
					frame = getContext(pages[i]);
					if (frame != null) {
						return frame;
					}
				}
			}

			alreadyVisited.add(window);

			IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
					.getWorkbenchWindows();
			for (int i = 0; i < windows.length; i++) {
				if (!alreadyVisited.contains(windows[i])) {
					frame = getEvaluationContext(windows[i], alreadyVisited);
					if (frame != null) {
						return frame;
					}
				}
			}
			return null;
		}
		return frame;
	}

	public void debugContextChanged(DebugContextEvent event) {
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
			IWorkbenchPart part = event.getDebugContextProvider().getPart();
			if (part != null) {
				IWorkbenchPage page = part.getSite().getPage();
				ISelection selection = event.getContext();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (ss.size() == 1) {
						Object element = ss.getFirstElement();
						if (element instanceof IAdaptable) {
							IStackFrame frame = (IStackFrame) ((IAdaptable) element)
									.getAdapter(IStackFrame.class);
							boolean instOf = element instanceof IStackFrame
									|| element instanceof IThread;
							if (frame != null) {
								// do not consider scrapbook frames
								// if
								// (frame.getLaunch().getAttribute(ScrapbookLauncher.SCRAPBOOK_LAUNCH)
								// == null) {
								setContext(page, frame, instOf);
								return;
								// }
							}
						}
					}
				}
				// no context in the given view
				removeContext(page);
			}
		}
	}

}
