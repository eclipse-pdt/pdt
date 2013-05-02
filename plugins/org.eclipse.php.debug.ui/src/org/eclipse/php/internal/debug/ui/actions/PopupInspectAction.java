/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.core.internal.runtime.AdapterManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.*;
import org.eclipse.debug.ui.DebugPopup;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.InspectPopupDialog;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.zend.model.PHPStackFrame;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.watch.IWatchExpressionResultExtension;
import org.eclipse.php.internal.debug.ui.watch.PHPWatchExpressionDelegate;
import org.eclipse.php.internal.debug.ui.watch.XDebugWatchExpressionDelegate;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Action to do simple code evaluation. The evaluation is done in the UI thread
 * and the expression and result are displayed using the IDataDisplay.
 */
public class PopupInspectAction implements IWorkbenchWindowActionDelegate,
		IObjectActionDelegate, IEditorActionDelegate, IPartListener,
		IViewActionDelegate {

	public static final String ACTION_DEFININITION_ID = "org.eclipse.php.debug.ui.commands.Inspect"; //$NON-NLS-1$

	private IAction fAction;
	private IWorkbenchPart fTargetPart;
	private IWorkbenchWindow fWindow;
	private IRegion fRegion;

	/**
	 * Is the action waiting for an evaluation.
	 */
	private boolean fEvaluating;

	/**
	 * The new target part to use with the evaluation completes.
	 */
	private IWorkbenchPart fNewTargetPart = null;

	/**
	 * Used to resolve editor input for selected stack frame
	 */
	private IDebugModelPresentation fPresentation;

	private IExpression expression;

	private ITextEditor fTextEditor;
	private ISelection fSelectionBeforeEvaluation;

	private IWatchExpressionListener watchExpressionListener;

	public PopupInspectAction() {
		super();
	}

	/**
	 * Returns the 'object' context for this evaluation, or <code>null</code> if
	 * none. If the evaluation is being performed in the context of the
	 * variables view/inspector. Then perform the evaluation in the context of
	 * the selected value.
	 * 
	 * @return Java object or <code>null</code>
	 */
	protected String getObjectContext() {
		IWorkbenchPage page = PHPUiPlugin.getActivePage();
		IStructuredSelection selection = null;
		if (page != null) {
			ISelection sec = page.getSelection();
			if (sec instanceof IStructuredSelection) {
				selection = (IStructuredSelection) sec;
			}
		}
		if (selection instanceof TextSelection) {
			TextSelection textSelection = (TextSelection) selection;
			return textSelection.getText();
		}
		return null;
	}

	/**
	 * Finds the currently selected stack frame in the UI. Stack frames from a
	 * scrapbook launch are ignored.
	 */
	protected IStackFrame getStackFrameContext() {
		IWorkbenchPart part = getTargetPart();
		IStackFrame frame = null;
		if (part == null) {
			frame = EvaluationContextManager.getEvaluationContext(getWindow());
		} else {
			frame = EvaluationContextManager.getEvaluationContext(part);
		}
		return frame;
	}

	/**
	 * @see IEvaluationListener#evaluationComplete(IEvaluationResult)
	 */
	public void evaluationComplete(final IWatchExpressionResult result) {
		// if plug-in has shutdown, ignore - see bug# 8693
		if (PHPDebugUIPlugin.getDefault() == null) {
			return;
		}

		final IValue value = result.getValue();
		if (result.hasErrors() || value != null) {
			final Display display = PHPDebugUIPlugin.getStandardDisplay();
			if (display.isDisposed()) {
				return;
			}
			displayResult(result);
		}
	}

	protected void evaluationCleanup() {
		setEvaluating(false);
		setTargetPart(fNewTargetPart);
	}

	/**
	 * Display the given evaluation result.
	 */
	protected void displayResult(final IWatchExpressionResult result) {
		IWorkbenchPart part = getTargetPart();
		final StyledText styledText = getStyledText(part);
		if (styledText != null) {
			expression = new IExpression() {

				public String getModelIdentifier() {
					return getDebugTarget().getModelIdentifier();
				}

				public ILaunch getLaunch() {
					return getDebugTarget().getLaunch();
				}

				public Object getAdapter(Class adapter) {
					return AdapterManager.getDefault()
							.getAdapter(this, adapter);
				}

				public String getExpressionText() {
					return result.getExpressionText();
				}

				public IValue getValue() {
					return result.getValue();
				}

				public IDebugTarget getDebugTarget() {
					IValue value = getValue();
					if (value != null) {
						return getValue().getDebugTarget();
					} else if (result instanceof IWatchExpressionResultExtension) {
						return ((IWatchExpressionResultExtension) result)
								.getDebugTarget();
					}
					// An expression should never be created with a null value
					// *and*
					// a null result.
					return null;
				}

				public void dispose() {

				}
			};
			PHPDebugUIPlugin.getStandardDisplay().asyncExec(new Runnable() {
				public void run() {
					showPopup(styledText);
				}
			});
		}
		evaluationCleanup();
	}

	protected void showPopup(StyledText textWidget) {
		IWorkbenchPart part = getTargetPart();
		if (part instanceof ITextEditor) {
			fTextEditor = (ITextEditor) part;
			fSelectionBeforeEvaluation = getTargetSelection();
		}
		DebugPopup displayPopup = new InspectPopupDialog(getShell(),
				getPopupAnchor(textWidget), ACTION_DEFININITION_ID, expression) {
			IContextActivation contextActivation;

			@Override
			protected Control createDialogArea(Composite parent) {
				Control result = super.createDialogArea(parent);
				if (fTextEditor != null) {
					IContextService contextService = (IContextService) fTextEditor
							.getSite().getService(IContextService.class);
					contextActivation = contextService
							.activateContext("org.eclipse.php.debug.ui.xdebug"); //$NON-NLS-1$
				}
				return result;
			}

			public boolean close() {
				boolean returnValue = super.close();
				if (fTextEditor != null && fSelectionBeforeEvaluation != null) {
					fTextEditor.getSelectionProvider().setSelection(
							fSelectionBeforeEvaluation);
					fTextEditor = null;
					fSelectionBeforeEvaluation = null;
				}
				// if (fTextEditor != null) {
				// IContextService contextService = (IContextService)
				// fTextEditor
				// .getSite().getService(IContextService.class);
				// contextService.deactivateContext(contextActivation);
				// }
				return returnValue;
			}
		};
		displayPopup.open();
	}

	protected void run() {
		// eval in context of object or stack frame
		final String expression = getObjectContext();
		final IStackFrame stackFrame = getStackFrameContext();
		if (stackFrame == null) {
			return;
		}

		setNewTargetPart(getTargetPart());
		if (watchExpressionListener == null) {
			watchExpressionListener = new IWatchExpressionListener() {
				public void watchEvaluationFinished(
						IWatchExpressionResult result) {
					evaluationComplete(result);
				}
			};
		}
		if (stackFrame instanceof DBGpStackFrame) {

			new XDebugWatchExpressionDelegate().evaluateExpression(expression,
					stackFrame, watchExpressionListener);

		} else if (stackFrame instanceof PHPStackFrame) {

			new PHPWatchExpressionDelegate().evaluateExpression(expression,
					stackFrame, watchExpressionListener);
		}

	}

	protected ISelection getTargetSelection() {
		IWorkbenchPart part = getTargetPart();
		if (part != null) {
			ISelectionProvider provider = part.getSite().getSelectionProvider();
			if (provider != null) {
				return provider.getSelection();
			}
		}
		return null;
	}

	/**
	 * Resolve an editor input from the source element of the stack frame
	 * argument, and return whether it's equal to the editor input for the
	 * editor that owns this action.
	 */
	protected boolean compareToEditorInput(IStackFrame stackFrame) {
		ILaunch launch = stackFrame.getLaunch();
		if (launch == null) {
			return false;
		}
		ISourceLocator locator = launch.getSourceLocator();
		if (locator == null) {
			return false;
		}
		Object sourceElement = locator.getSourceElement(stackFrame);
		if (sourceElement == null) {
			return false;
		}
		IEditorInput sfEditorInput = getDebugModelPresentation()
				.getEditorInput(sourceElement);
		if (getTargetPart() instanceof IEditorPart) {
			return ((IEditorPart) getTargetPart()).getEditorInput().equals(
					sfEditorInput);
		}
		return false;
	}

	protected Shell getShell() {
		if (getTargetPart() != null) {
			return getTargetPart().getSite().getShell();
		}
		return PHPDebugUIPlugin.getActiveWorkbenchShell();
	}

	protected boolean textHasContent(String text) {
		if (text != null) {
			int length = text.length();
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					if (Character.isLetterOrDigit(text.charAt(i))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (PHPUiPlugin.getActivePage() != null) {
			setTargetPart(PHPUiPlugin.getActivePage().getActivePart());
		}
		run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		setAction(action);
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			action.setEnabled(textSelection.getLength() != 0);
		}
	}

	/**
	 * @see IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		disposeDebugModelPresentation();
		IWorkbenchWindow win = getWindow();
		if (win != null) {
			win.getPartService().removePartListener(this);
		}
	}

	/**
	 * @see IWorkbenchWindowActionDelegate#init(IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		setWindow(window);
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			setTargetPart(page.getActivePart());
		}
		window.getPartService().addPartListener(this);
	}

	protected IAction getAction() {
		return fAction;
	}

	protected void setAction(IAction action) {
		fAction = action;
	}

	/**
	 * Returns a debug model presentation (creating one if necessary).
	 * 
	 * @return debug model presentation
	 */
	protected IDebugModelPresentation getDebugModelPresentation() {
		if (fPresentation == null) {
			fPresentation = DebugUITools
					.newDebugModelPresentation(IPHPDebugConstants.ID_PHP_DEBUG_CORE);
		}
		return fPresentation;
	}

	/**
	 * Disposes this action's debug model presentation, if one was created.
	 */
	protected void disposeDebugModelPresentation() {
		if (fPresentation != null) {
			fPresentation.dispose();
		}
	}

	/**
	 * @see IEditorActionDelegate#setActiveEditor(IAction, IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		setAction(action);
		setTargetPart(targetEditor);
	}

	/**
	 * @see IPartListener#partActivated(IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		setTargetPart(part);
	}

	/**
	 * @see IPartListener#partBroughtToTop(IWorkbenchPart)
	 */
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	/**
	 * @see IPartListener#partClosed(IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		if (part == getTargetPart()) {
			setTargetPart(null);
		}
		if (part == getNewTargetPart()) {
			setNewTargetPart(null);
		}
	}

	/**
	 * @see IPartListener#partDeactivated(IWorkbenchPart)
	 */
	public void partDeactivated(IWorkbenchPart part) {
	}

	/**
	 * @see IPartListener#partOpened(IWorkbenchPart)
	 */
	public void partOpened(IWorkbenchPart part) {
	}

	/**
	 * @see IViewActionDelegate#init(IViewPart)
	 */
	public void init(IViewPart view) {
		setTargetPart(view);
	}

	protected IWorkbenchPart getTargetPart() {
		return fTargetPart;
	}

	protected void setTargetPart(IWorkbenchPart part) {
		if (isEvaluating()) {
			// do not want to change the target part while evaluating
			// see bug 8334
			setNewTargetPart(part);
		} else {
			fTargetPart = part;
		}
	}

	protected IWorkbenchWindow getWindow() {
		return fWindow;
	}

	protected void setWindow(IWorkbenchWindow window) {
		fWindow = window;
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		setAction(action);
		setTargetPart(targetPart);
	}

	protected IWorkbenchPart getNewTargetPart() {
		return fNewTargetPart;
	}

	protected void setNewTargetPart(IWorkbenchPart newTargetPart) {
		fNewTargetPart = newTargetPart;
	}

	protected boolean isEvaluating() {
		return fEvaluating;
	}

	protected void setEvaluating(boolean evaluating) {
		fEvaluating = evaluating;
	}

	/**
	 * Returns the selected text region, or <code>null</code> if none.
	 * 
	 * @return
	 */
	protected IRegion getRegion() {
		return fRegion;
	}

	/**
	 * Returns the styled text widget associated with the given part or
	 * <code>null</code> if none.
	 * 
	 * @param part
	 *            workbench part
	 * @return associated style text widget or <code>null</code>
	 */
	public static StyledText getStyledText(IWorkbenchPart part) {
		ITextViewer viewer = (ITextViewer) part.getAdapter(ITextViewer.class);
		StyledText textWidget = null;
		if (viewer == null) {
			Control control = (Control) part.getAdapter(Control.class);
			if (control instanceof StyledText) {
				textWidget = (StyledText) control;
			}
		} else {
			textWidget = viewer.getTextWidget();
		}
		return textWidget;
	}

	/**
	 * Returns an anchor point for a popup dialog on top of a styled text or
	 * <code>null</code> if none.
	 * 
	 * @param part
	 *            or <code>null</code>
	 * @return anchor point or <code>null</code>
	 */
	public static Point getPopupAnchor(StyledText textWidget) {
		if (textWidget != null) {
			Point docRange = textWidget.getSelectionRange();
			int midOffset = docRange.x + (docRange.y / 2);
			Point point = textWidget.getLocationAtOffset(midOffset);
			point = textWidget.toDisplay(point);

			GC gc = new GC(textWidget);
			gc.setFont(textWidget.getFont());
			int height = gc.getFontMetrics().getHeight();
			gc.dispose();
			point.y += height;
			return point;
		}
		return null;
	}
}
