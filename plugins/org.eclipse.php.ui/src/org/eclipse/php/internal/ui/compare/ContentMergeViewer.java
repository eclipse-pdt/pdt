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

package org.eclipse.php.internal.ui.compare;

import java.util.ResourceBundle;

import org.eclipse.compare.*;
import org.eclipse.compare.contentmergeviewer.IFlushable;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.*;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

/**
 * An abstract compare and merge viewer with two side-by-side content areas and
 * an optional content area for the ancestor. The implementation makes no
 * assumptions about the content type.
 * <p>
 * <code>ContentMergeViewer</code>
 * <ul>
 * <li>implements the overall layout and defines hooks so that subclasses can
 * easily provide an implementation for a specific content type,
 * <li>implements the UI for making the areas resizable,
 * <li>has an action for controlling whether the ancestor area is visible or
 * not,
 * <li>has actions for copying one side of the input to the other side,
 * <li>tracks the dirty state of the left and right sides and send out
 * notification on state changes.
 * </ul>
 * A <code>ContentMergeViewer</code> accesses its model by means of a content
 * provider which must implement the <code>IMergeViewerContentProvider</code>
 * interface.
 * </p>
 * <p>
 * Clients may wish to use the standard concrete subclass
 * <code>TextMergeViewer</code>, or define their own subclass.
 * 
 * @see IMergeViewerContentProvider
 * @see TextMergeViewer
 */
public abstract class ContentMergeViewer extends ContentViewer implements
		IPropertyChangeNotifier, IFlushable, IFlushable2 {
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=330672
	org.eclipse.compare.contentmergeviewer.ContentMergeViewer cmv;

	class SaveAction extends MergeViewerAction {

		SaveAction(boolean left) {
			super(true, false, false);

			setText(PHPUIMessages.ContentMergeViewer_0);
			setToolTipText(PHPUIMessages.ContentMergeViewer_1);
			setDescription(PHPUIMessages.ContentMergeViewer_2);
		}

		public void run() {
			flush(null);
		}
	}

	/* package */static final int HORIZONTAL = 1;
	/* package */static final int VERTICAL = 2;

	static final double HSPLIT = 0.5;
	static final double VSPLIT = 0.3;

	private class ContentMergeViewerLayout extends Layout {

		public Point computeSize(Composite c, int w, int h, boolean force) {
			return new Point(100, 100);
		}

		public void layout(Composite composite, boolean force) {

			// determine some derived sizes
			int headerHeight = fLeftLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					true).y;
			Rectangle r = composite.getClientArea();

			int centerWidth = getCenterWidth();
			int width1 = (int) ((r.width - centerWidth) * getHorizontalSplitRatio());
			int width2 = r.width - width1 - centerWidth;

			int height1 = 0;
			int height2 = 0;
			if (fIsThreeWay && fAncestorVisible) {
				height1 = (int) ((r.height - (2 * headerHeight)) * fVSplit);
				height2 = r.height - (2 * headerHeight) - height1;
			} else {
				height1 = 0;
				height2 = r.height - headerHeight;
			}

			int y = 0;

			if (fIsThreeWay && fAncestorVisible) {
				fAncestorLabel.setBounds(0, y, r.width, headerHeight);
				fAncestorLabel.setVisible(true);
				y += headerHeight;
				handleResizeAncestor(0, y, r.width, height1);
				y += height1;
			} else {
				fAncestorLabel.setVisible(false);
				handleResizeAncestor(0, 0, 0, 0);
			}

			fLeftLabel.getSize(); // without this resizing would not always work

			if (centerWidth > 3) {
				fLeftLabel.setBounds(0, y, width1 + 1, headerHeight);
				fDirectionLabel.setVisible(true);
				fDirectionLabel.setBounds(width1 + 1, y, centerWidth - 1,
						headerHeight);
				fRightLabel.setBounds(width1 + centerWidth, y, width2,
						headerHeight);
			} else {
				fLeftLabel.setBounds(0, y, width1, headerHeight);
				fDirectionLabel.setVisible(false);
				fRightLabel
						.setBounds(width1, y, r.width - width1, headerHeight);
			}

			y += headerHeight;

			if (fCenter != null && !fCenter.isDisposed())
				fCenter.setBounds(width1, y, centerWidth, height2);

			handleResizeLeftRight(0, y, width1, centerWidth, width2, height2);
		}

		private double getHorizontalSplitRatio() {
			if (fHSplit < 0) {
				Object input = getInput();
				if (input instanceof ICompareInput) {
					ICompareInput ci = (ICompareInput) input;
					if (ci.getLeft() == null)
						return 0.1;
					if (ci.getRight() == null)
						return 0.9;
				}
				return HSPLIT;
			}
			return fHSplit;
		}
	}

	class Resizer extends MouseAdapter implements MouseMoveListener {

		Control fControl;
		int fX, fY;
		int fWidth1, fWidth2;
		int fHeight1, fHeight2;
		int fDirection;
		boolean fLiveResize;
		boolean fIsDown;

		public Resizer(Control c, int dir) {
			fDirection = dir;
			fControl = c;
			fLiveResize = !(fControl instanceof Sash);
			updateCursor(c, dir);
			fControl.addMouseListener(this);
			fControl.addMouseMoveListener(this);
			fControl.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					fControl = null;
				}
			});
		}

		public void mouseDoubleClick(MouseEvent e) {
			if ((fDirection & HORIZONTAL) != 0)
				fHSplit = -1;
			if ((fDirection & VERTICAL) != 0)
				fVSplit = VSPLIT;
			fComposite.layout(true);
		}

		public void mouseDown(MouseEvent e) {
			Composite parent = fControl.getParent();

			Point s = parent.getSize();
			Point as = fAncestorLabel.getSize();
			Point ys = fLeftLabel.getSize();
			Point ms = fRightLabel.getSize();

			fWidth1 = ys.x;
			fWidth2 = ms.x;
			fHeight1 = fLeftLabel.getLocation().y - as.y;
			fHeight2 = s.y - (fLeftLabel.getLocation().y + ys.y);

			fX = e.x;
			fY = e.y;
			fIsDown = true;
		}

		public void mouseUp(MouseEvent e) {
			fIsDown = false;
			if (!fLiveResize)
				resize(e);
		}

		public void mouseMove(MouseEvent e) {
			if (fIsDown && fLiveResize)
				resize(e);
		}

		private void resize(MouseEvent e) {
			int dx = e.x - fX;
			int dy = e.y - fY;

			int centerWidth = fCenter.getSize().x;

			if (fWidth1 + dx > centerWidth && fWidth2 - dx > centerWidth) {
				fWidth1 += dx;
				fWidth2 -= dx;
				if ((fDirection & HORIZONTAL) != 0)
					fHSplit = (double) fWidth1 / (double) (fWidth1 + fWidth2);
			}
			if (fHeight1 + dy > centerWidth && fHeight2 - dy > centerWidth) {
				fHeight1 += dy;
				fHeight2 -= dy;
				if ((fDirection & VERTICAL) != 0)
					fVSplit = (double) fHeight1
							/ (double) (fHeight1 + fHeight2);
			}

			fComposite.layout(true);
			fControl.getDisplay().update();
		}
	}

	/** Style bits for top level composite */
	private int fStyles;
	private ResourceBundle fBundle;
	private final CompareConfiguration fCompareConfiguration;
	private IPropertyChangeListener fPropertyChangeListener;
	private ICompareInputChangeListener fCompareInputChangeListener;
	private ListenerList fListenerList;
	boolean fConfirmSave = true;

	private double fHSplit = -1; // width ratio of left and right panes
	private double fVSplit = VSPLIT; // height ratio of ancestor and bottom
										// panes

	private boolean fIsThreeWay; // whether their is an ancestor
	private boolean fAncestorVisible; // whether the ancestor pane is visible
	private ActionContributionItem fAncestorItem;

	private Action fCopyLeftToRightAction; // copy from left to right
	private Action fCopyRightToLeftAction; // copy from right to left

	MergeViewerAction fLeftSaveAction;
	MergeViewerAction fRightSaveAction;

	private CompareHandlerService fHandlerService;

	// SWT widgets
	/* package */Composite fComposite;
	private CLabel fAncestorLabel;
	private CLabel fLeftLabel;
	private CLabel fRightLabel;
	/* package */CLabel fDirectionLabel;
	/* package */Control fCenter;

	// ---- SWT resources to be disposed
	private Image fRightArrow;
	private Image fLeftArrow;
	private Image fBothArrow;
	Cursor fNormalCursor;
	private Cursor fHSashCursor;
	private Cursor fVSashCursor;
	private Cursor fHVSashCursor;

	private ILabelProviderListener labelChangeListener = new ILabelProviderListener() {
		public void labelProviderChanged(LabelProviderChangedEvent event) {
			Object[] elements = event.getElements();
			for (int i = 0; i < elements.length; i++) {
				Object object = elements[i];
				if (object == getInput())
					updateHeader();
			}
		}
	};

	// ---- end

	/**
	 * Creates a new content merge viewer and initializes with a resource bundle
	 * and a configuration.
	 * 
	 * @param style
	 *            SWT style bits
	 * @param bundle
	 *            the resource bundle
	 * @param cc
	 *            the configuration object
	 */
	protected ContentMergeViewer(int style, ResourceBundle bundle,
			CompareConfiguration cc) {

		fStyles = style & ~(SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT); // remove
																	// BIDI
																	// direction
																	// bits
		fBundle = bundle;

		fAncestorVisible = Utilities.getBoolean(cc,
				ICompareUIConstants.PROP_ANCESTOR_VISIBLE, fAncestorVisible);
		fConfirmSave = Utilities.getBoolean(cc,
				CompareEditor.CONFIRM_SAVE_PROPERTY, fConfirmSave);

		setContentProvider(new MergeViewerContentProvider(cc));

		fCompareInputChangeListener = new ICompareInputChangeListener() {
			public void compareInputChanged(ICompareInput input) {
				if (input == getInput()) {
					handleCompareInputChange();
				}
			}
		};

		// Make sure the compare configuration is not null
		if (cc == null)
			fCompareConfiguration = new CompareConfiguration();
		else
			fCompareConfiguration = cc;
		fPropertyChangeListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				ContentMergeViewer.this.handlePropertyChangeEvent(event);
			}
		};
		fCompareConfiguration
				.addPropertyChangeListener(fPropertyChangeListener);

		fLeftSaveAction = new SaveAction(true);
		fLeftSaveAction.setEnabled(false);
		fRightSaveAction = new SaveAction(false);
		fRightSaveAction.setEnabled(false);

		// this is used to update the dirty status,if we use
		// org.eclipse.php.internal.ui.compare.ContentMergeViewer,we will get a
		// ClassCastException
		cmv = new org.eclipse.compare.contentmergeviewer.ContentMergeViewer(
				fStyles, fBundle, fCompareConfiguration) {

			@Override
			protected void createControls(Composite composite) {

			}

			@Override
			protected void handleResizeAncestor(int x, int y, int width,
					int height) {

			}

			@Override
			protected void handleResizeLeftRight(int x, int y, int leftWidth,
					int centerWidth, int rightWidth, int height) {

			}

			@Override
			protected void updateContent(Object ancestor, Object left,
					Object right) {

			}

			@Override
			protected void copy(boolean leftToRight) {

			}

			@Override
			protected byte[] getContents(boolean left) {
				return null;
			}

			@Override
			public boolean internalIsLeftDirty() {
				return ContentMergeViewer.this.isLeftDirty();
			}

			@Override
			public boolean internalIsRightDirty() {
				return ContentMergeViewer.this.isRightDirty();
			}
		};
	}

	// ---- hooks ---------------------

	/**
	 * Returns the viewer's name.
	 * 
	 * @return the viewer's name
	 */
	public String getTitle() {
		return Utilities.getString(getResourceBundle(), "title"); //$NON-NLS-1$
	}

	/**
	 * Creates the SWT controls for the ancestor, left, and right content areas
	 * of this compare viewer. Implementations typically hold onto the controls
	 * so that they can be initialized with the input objects in method
	 * <code>updateContent</code>.
	 * 
	 * @param composite
	 *            the container for the three areas
	 */
	abstract protected void createControls(Composite composite);

	/**
	 * Lays out the ancestor area of the compare viewer. It is called whenever
	 * the viewer is resized or when the sashes between the areas are moved to
	 * adjust the size of the areas.
	 * 
	 * @param x
	 *            the horizontal position of the ancestor area within its
	 *            container
	 * @param y
	 *            the vertical position of the ancestor area within its
	 *            container
	 * @param width
	 *            the width of the ancestor area
	 * @param height
	 *            the height of the ancestor area
	 */
	abstract protected void handleResizeAncestor(int x, int y, int width,
			int height);

	/**
	 * Lays out the left and right areas of the compare viewer. It is called
	 * whenever the viewer is resized or when the sashes between the areas are
	 * moved to adjust the size of the areas.
	 * 
	 * @param x
	 *            the horizontal position of the left area within its container
	 * @param y
	 *            the vertical position of the left and right area within its
	 *            container
	 * @param leftWidth
	 *            the width of the left area
	 * @param centerWidth
	 *            the width of the gap between the left and right areas
	 * @param rightWidth
	 *            the width of the right area
	 * @param height
	 *            the height of the left and right areas
	 */
	abstract protected void handleResizeLeftRight(int x, int y, int leftWidth,
			int centerWidth, int rightWidth, int height);

	/**
	 * Contributes items to the given <code>ToolBarManager</code>. It is called
	 * when this viewer is installed in its container and if the container has a
	 * <code>ToolBarManager</code>. The <code>ContentMergeViewer</code>
	 * implementation of this method does nothing. Subclasses may reimplement.
	 * 
	 * @param toolBarManager
	 *            the toolbar manager to contribute to
	 */
	protected void createToolItems(ToolBarManager toolBarManager) {
		// empty implementation
	}

	/**
	 * Initializes the controls of the three content areas with the given input
	 * objects.
	 * 
	 * @param ancestor
	 *            the input for the ancestor area
	 * @param left
	 *            the input for the left area
	 * @param right
	 *            the input for the right area
	 */
	abstract protected void updateContent(Object ancestor, Object left,
			Object right);

	/**
	 * Copies the content of one side to the other side. Called from the
	 * (internal) actions for copying the sides of the viewer's input object.
	 * 
	 * @param leftToRight
	 *            if <code>true</code>, the left side is copied to the right
	 *            side; if <code>false</code>, the right side is copied to the
	 *            left side
	 */
	abstract protected void copy(boolean leftToRight);

	/**
	 * Returns the byte contents of the left or right side. If the viewer has no
	 * editable content <code>null</code> can be returned.
	 * 
	 * @param left
	 *            if <code>true</code>, the byte contents of the left area is
	 *            returned; if <code>false</code>, the byte contents of the
	 *            right area
	 * @return the content as an array of bytes, or <code>null</code>
	 */
	abstract protected byte[] getContents(boolean left);

	// ----------------------------

	/**
	 * Returns the resource bundle of this viewer.
	 * 
	 * @return the resource bundle
	 */
	protected ResourceBundle getResourceBundle() {
		return fBundle;
	}

	/**
	 * Returns the compare configuration of this viewer, or <code>null</code> if
	 * this viewer does not yet have a configuration.
	 * 
	 * @return the compare configuration, or <code>null</code> if none
	 */
	protected CompareConfiguration getCompareConfiguration() {
		return fCompareConfiguration;
	}

	/**
	 * The <code>ContentMergeViewer</code> implementation of this
	 * <code>ContentViewer</code> method checks to ensure that the content
	 * provider is an <code>IMergeViewerContentProvider</code>.
	 * 
	 * @param contentProvider
	 *            the content provider to set. Must implement
	 *            IMergeViewerContentProvider.
	 */
	public void setContentProvider(IContentProvider contentProvider) {
		Assert.isTrue(contentProvider instanceof IMergeViewerContentProvider);
		super.setContentProvider(contentProvider);
	}

	/* package */IMergeViewerContentProvider getMergeContentProvider() {
		return (IMergeViewerContentProvider) getContentProvider();
	}

	/**
	 * The <code>ContentMergeViewer</code> implementation of this
	 * <code>Viewer</code> method returns the empty selection. Subclasses may
	 * override.
	 * 
	 * @return empty selection.
	 */
	public ISelection getSelection() {
		return new ISelection() {
			public boolean isEmpty() {
				return true;
			}
		};
	}

	/**
	 * The <code>ContentMergeViewer</code> implementation of this
	 * <code>Viewer</code> method does nothing. Subclasses may reimplement.
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection,
	 *      boolean)
	 */
	public void setSelection(ISelection selection, boolean reveal) {
		// empty implementation
	}

	/**
	 * Callback that is invoked when a property in the compare configuration (
	 * {@link #getCompareConfiguration()} changes.
	 * 
	 * @param event
	 *            the property change event
	 * @since 3.3
	 */
	protected void handlePropertyChangeEvent(PropertyChangeEvent event) {

		String key = event.getProperty();

		if (key.equals(ICompareUIConstants.PROP_ANCESTOR_VISIBLE)) {
			fAncestorVisible = Utilities
					.getBoolean(getCompareConfiguration(),
							ICompareUIConstants.PROP_ANCESTOR_VISIBLE,
							fAncestorVisible);
			fComposite.layout(true);

			updateCursor(fLeftLabel, VERTICAL);
			updateCursor(fDirectionLabel, HORIZONTAL | VERTICAL);
			updateCursor(fRightLabel, VERTICAL);

			return;
		}

		if (key.equals(ICompareUIConstants.PROP_IGNORE_ANCESTOR)) {
			setAncestorVisibility(false, !Utilities.getBoolean(
					getCompareConfiguration(),
					ICompareUIConstants.PROP_IGNORE_ANCESTOR, false));
			return;
		}
	}

	void updateCursor(Control c, int dir) {
		if (!(c instanceof Sash)) {
			Cursor cursor = null;
			switch (dir) {
			case VERTICAL:
				if (fAncestorVisible) {
					if (fVSashCursor == null)
						fVSashCursor = new Cursor(c.getDisplay(),
								SWT.CURSOR_SIZENS);
					cursor = fVSashCursor;
				} else {
					if (fNormalCursor == null)
						fNormalCursor = new Cursor(c.getDisplay(),
								SWT.CURSOR_ARROW);
					cursor = fNormalCursor;
				}
				break;
			case HORIZONTAL:
				if (fHSashCursor == null)
					fHSashCursor = new Cursor(c.getDisplay(), SWT.CURSOR_SIZEWE);
				cursor = fHSashCursor;
				break;
			case VERTICAL + HORIZONTAL:
				if (fAncestorVisible) {
					if (fHVSashCursor == null)
						fHVSashCursor = new Cursor(c.getDisplay(),
								SWT.CURSOR_SIZEALL);
					cursor = fHVSashCursor;
				} else {
					if (fHSashCursor == null)
						fHSashCursor = new Cursor(c.getDisplay(),
								SWT.CURSOR_SIZEWE);
					cursor = fHSashCursor;
				}
				break;
			}
			if (cursor != null)
				c.setCursor(cursor);
		}
	}

	private void setAncestorVisibility(boolean visible, boolean enabled) {
		if (fAncestorItem != null) {
			Action action = (Action) fAncestorItem.getAction();
			if (action != null) {
				action.setChecked(visible);
				action.setEnabled(enabled);
			}
		}
		getCompareConfiguration()
				.setProperty(ICompareUIConstants.PROP_ANCESTOR_VISIBLE,
						new Boolean(visible));
	}

	// ---- input

	/**
	 * Return whether the input is a three-way comparison.
	 * 
	 * @return whether the input is a three-way comparison
	 * @since 3.3
	 */
	protected boolean isThreeWay() {
		return fIsThreeWay;
	}

	/**
	 * Internal hook method called when the input to this viewer is initially
	 * set or subsequently changed.
	 * <p>
	 * The <code>ContentMergeViewer</code> implementation of this
	 * <code>Viewer</code> method tries to save the old input by calling
	 * <code>doSave(...)</code> and then calls <code>internalRefresh(...)</code>.
	 * 
	 * @param input
	 *            the new input of this viewer, or <code>null</code> if there is
	 *            no new input
	 * @param oldInput
	 *            the old input element, or <code>null</code> if there was
	 *            previously no input
	 */
	protected final void inputChanged(Object input, Object oldInput) {

		if (input != oldInput && oldInput != null) {
			ICompareInputLabelProvider lp = getCompareConfiguration()
					.getLabelProvider();
			if (lp != null)
				lp.removeListener(labelChangeListener);
		}

		if (input != oldInput && oldInput instanceof ICompareInput) {
			ICompareContainer container = getCompareConfiguration()
					.getContainer();
			container.removeCompareInputChangeListener(
					(ICompareInput) oldInput, fCompareInputChangeListener);
		}

		boolean success = doSave(input, oldInput);

		if (input != oldInput && input instanceof ICompareInput) {
			ICompareContainer container = getCompareConfiguration()
					.getContainer();
			container.addCompareInputChangeListener((ICompareInput) input,
					fCompareInputChangeListener);
		}

		if (input != oldInput && input != null) {
			ICompareInputLabelProvider lp = getCompareConfiguration()
					.getLabelProvider();
			if (lp != null)
				lp.addListener(labelChangeListener);
		}

		if (success) {
			setLeftDirty(false);
			setRightDirty(false);
		}

		if (input != oldInput)
			internalRefresh(input);
	}

	/**
	 * This method is called from the <code>Viewer</code> method
	 * <code>inputChanged</code> to save any unsaved changes of the old input.
	 * <p>
	 * The <code>ContentMergeViewer</code> implementation of this method calls
	 * <code>saveContent(...)</code>. If confirmation has been turned on with
	 * <code>setConfirmSave(true)</code>, a confirmation alert is posted before
	 * saving.
	 * </p>
	 * Clients can override this method and are free to decide whether they want
	 * to call the inherited method.
	 * 
	 * @param newInput
	 *            the new input of this viewer, or <code>null</code> if there is
	 *            no new input
	 * @param oldInput
	 *            the old input element, or <code>null</code> if there was
	 *            previously no input
	 * @return <code>true</code> if saving was successful, or if the user didn't
	 *         want to save (by pressing 'NO' in the confirmation dialog).
	 * @since 2.0
	 */
	protected boolean doSave(Object newInput, Object oldInput) {

		// before setting the new input we have to save the old
		if (isLeftDirty() || isRightDirty()) {

			if (Utilities.RUNNING_TESTS) {
				if (Utilities.TESTING_FLUSH_ON_COMPARE_INPUT_CHANGE) {
					flushContent(oldInput, null);
				}
			} else if (fConfirmSave) {
				// post alert
				Shell shell = fComposite.getShell();

				MessageDialog dialog = new MessageDialog(shell,
						Utilities.getString(getResourceBundle(),
								"saveDialog.title"), //$NON-NLS-1$
						null, // accept the default window icon
						Utilities.getString(getResourceBundle(),
								"saveDialog.message"), //$NON-NLS-1$
						MessageDialog.QUESTION, new String[] {
								IDialogConstants.YES_LABEL,
								IDialogConstants.NO_LABEL, }, 0); // default
																	// button
																	// index

				switch (dialog.open()) { // open returns index of pressed button
				case 0:
					flushContent(oldInput, null);
					break;
				case 1:
					setLeftDirty(false);
					setRightDirty(false);
					break;
				case 2:
					throw new ViewerSwitchingCancelled();
				}
			} else
				flushContent(oldInput, null);
			return true;
		}
		return false;
	}

	/**
	 * Controls whether <code>doSave(Object, Object)</code> asks for
	 * confirmation before saving the old input with
	 * <code>saveContent(Object)</code>.
	 * 
	 * @param enable
	 *            a value of <code>true</code> enables confirmation
	 * @since 2.0
	 */
	public void setConfirmSave(boolean enable) {
		fConfirmSave = enable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	public void refresh() {
		internalRefresh(getInput());
	}

	private void internalRefresh(Object input) {

		IMergeViewerContentProvider content = getMergeContentProvider();
		if (content != null) {
			Object ancestor = content.getAncestorContent(input);
			boolean oldFlag = fIsThreeWay;
			if (Utilities.isHunk(input)) {
				fIsThreeWay = true;
			} else if (input instanceof ICompareInput)
				fIsThreeWay = (((ICompareInput) input).getKind() & Differencer.DIRECTION_MASK) != 0;
			else
				fIsThreeWay = ancestor != null;

			if (fAncestorItem != null)
				fAncestorItem.setVisible(fIsThreeWay);

			if (fAncestorVisible && oldFlag != fIsThreeWay)
				fComposite.layout(true);

			Object left = content.getLeftContent(input);
			Object right = content.getRightContent(input);
			updateContent(ancestor, left, right);

			updateHeader();
			ToolBarManager tbm = CompareViewerPane.getToolBarManager(fComposite
					.getParent());
			if (tbm != null) {
				updateToolItems();
				tbm.update(true);
				tbm.getControl().getParent().layout(true);
			}

		}
	}

	// ---- layout & SWT control creation

	/**
	 * Builds the SWT controls for the three areas of a compare/merge viewer.
	 * <p>
	 * Calls the hooks <code>createControls</code> and
	 * <code>createToolItems</code> to let subclasses build the specific content
	 * areas and to add items to an enclosing toolbar.
	 * <p>
	 * This method must only be called in the constructor of subclasses.
	 * 
	 * @param parent
	 *            the parent control
	 * @return the new control
	 */
	protected final Control buildControl(Composite parent) {

		fComposite = new Composite(parent, fStyles | SWT.LEFT_TO_RIGHT) { // we
																			// force
																			// a
																			// specific
																			// direction
			public boolean setFocus() {
				return ContentMergeViewer.this.handleSetFocus();
			}
		};
		fComposite.setData(CompareUI.COMPARE_VIEWER_TITLE, getTitle());

		hookControl(fComposite); // hook help & dispose listener

		fComposite.setLayout(new ContentMergeViewerLayout());

		int style = SWT.SHADOW_OUT;
		fAncestorLabel = new CLabel(fComposite, style
				| Window.getDefaultOrientation());

		fLeftLabel = new CLabel(fComposite, style
				| Window.getDefaultOrientation());
		new Resizer(fLeftLabel, VERTICAL);

		fDirectionLabel = new CLabel(fComposite, style);
		fDirectionLabel.setAlignment(SWT.CENTER);
		new Resizer(fDirectionLabel, HORIZONTAL | VERTICAL);

		fRightLabel = new CLabel(fComposite, style
				| Window.getDefaultOrientation());
		new Resizer(fRightLabel, VERTICAL);

		if (fCenter == null || fCenter.isDisposed())
			fCenter = createCenterControl(fComposite);

		createControls(fComposite);

		fHandlerService = CompareHandlerService
				.createFor(getCompareConfiguration().getContainer(),
						fComposite.getShell());

		initializeToolbars(parent);

		return fComposite;
	}

	private void initializeToolbars(Composite parent) {
		ToolBarManager tbm = CompareViewerPane.getToolBarManager(parent);
		if (tbm != null) {
			tbm.removeAll();

			// define groups
			tbm.add(new Separator("modes")); //$NON-NLS-1$
			tbm.add(new Separator("merge")); //$NON-NLS-1$
			tbm.add(new Separator("navigation")); //$NON-NLS-1$

			CompareConfiguration cc = getCompareConfiguration();

			if (cc.isRightEditable()) {
				fCopyLeftToRightAction = new Action() {
					public void run() {
						copy(true);
					}
				};
				Utilities.initAction(fCopyLeftToRightAction,
						getResourceBundle(), "action.CopyLeftToRight."); //$NON-NLS-1$
				tbm.appendToGroup("merge", fCopyLeftToRightAction); //$NON-NLS-1$
				fHandlerService.registerAction(fCopyLeftToRightAction,
						"org.eclipse.compare.copyAllLeftToRight"); //$NON-NLS-1$
			}

			if (cc.isLeftEditable()) {
				fCopyRightToLeftAction = new Action() {
					public void run() {
						copy(false);
					}
				};
				Utilities.initAction(fCopyRightToLeftAction,
						getResourceBundle(), "action.CopyRightToLeft."); //$NON-NLS-1$
				tbm.appendToGroup("merge", fCopyRightToLeftAction); //$NON-NLS-1$
				fHandlerService.registerAction(fCopyRightToLeftAction,
						"org.eclipse.compare.copyAllRightToLeft"); //$NON-NLS-1$
			}

			final ChangePropertyAction a = new ChangePropertyAction(
					fBundle,
					getCompareConfiguration(),
					"action.EnableAncestor.", ICompareUIConstants.PROP_ANCESTOR_VISIBLE); //$NON-NLS-1$
			a.setChecked(fAncestorVisible);
			fAncestorItem = new ActionContributionItem(a);
			fAncestorItem.setVisible(false);
			tbm.appendToGroup("modes", fAncestorItem); //$NON-NLS-1$
			tbm.getControl().addDisposeListener(a);

			createToolItems(tbm);
			updateToolItems();

			tbm.update(true);
		}
	}

	/**
	 * Callback that is invoked when the control of this merge viewer is given
	 * focus. This method should return <code>true</code> if a particular widget
	 * was given focus and false otherwise. By default, <code>false</code> is
	 * returned. Subclasses may override.
	 * 
	 * @return whether particular widget was given focus
	 * @since 3.3
	 */
	protected boolean handleSetFocus() {
		return false;
	}

	/**
	 * Return the desired width of the center control. This width is used to
	 * calculate the values used to layout the ancestor, left and right sides.
	 * 
	 * @return the desired width of the center control
	 * @see #handleResizeLeftRight(int, int, int, int, int, int)
	 * @see #handleResizeAncestor(int, int, int, int)
	 * @since 3.3
	 */
	protected int getCenterWidth() {
		return 3;
	}

	/**
	 * Return whether the ancestor pane is visible or not.
	 * 
	 * @return whether the ancestor pane is visible or not
	 * @since 3.3
	 */
	protected boolean isAncestorVisible() {
		return fAncestorVisible;
	}

	/**
	 * Create the control that divides the left and right sides of the merge
	 * viewer.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the center control
	 * @since 3.3
	 */
	protected Control createCenterControl(Composite parent) {
		Sash sash = new Sash(parent, SWT.VERTICAL);
		new Resizer(sash, HORIZONTAL);
		return sash;
	}

	/**
	 * Return the center control that divides the left and right sides of the
	 * merge viewer. This method returns the control that was created by calling
	 * {@link #createCenterControl(Composite)}.
	 * 
	 * @see #createCenterControl(Composite)
	 * @return the center control
	 * @since 3.3
	 */
	protected Control getCenterControl() {
		return fCenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	public Control getControl() {
		return fComposite;
	}

	/**
	 * Called on the viewer disposal. Unregisters from the compare
	 * configuration. Clients may extend if they have to do additional cleanup.
	 * 
	 * @see org.eclipse.jface.viewers.ContentViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
	 */
	protected void handleDispose(DisposeEvent event) {

		if (fHandlerService != null)
			fHandlerService.dispose();

		Object input = getInput();
		if (input instanceof ICompareInput) {
			ICompareContainer container = getCompareConfiguration()
					.getContainer();
			container.removeCompareInputChangeListener((ICompareInput) input,
					fCompareInputChangeListener);
		}
		if (input != null) {
			ICompareInputLabelProvider lp = getCompareConfiguration()
					.getLabelProvider();
			if (lp != null)
				lp.removeListener(labelChangeListener);
		}

		if (fPropertyChangeListener != null) {
			fCompareConfiguration
					.removePropertyChangeListener(fPropertyChangeListener);
			fPropertyChangeListener = null;
		}

		fAncestorLabel = null;
		fLeftLabel = null;
		fDirectionLabel = null;
		fRightLabel = null;
		fCenter = null;

		if (fRightArrow != null) {
			fRightArrow.dispose();
			fRightArrow = null;
		}
		if (fLeftArrow != null) {
			fLeftArrow.dispose();
			fLeftArrow = null;
		}
		if (fBothArrow != null) {
			fBothArrow.dispose();
			fBothArrow = null;
		}

		if (fNormalCursor != null) {
			fNormalCursor.dispose();
			fNormalCursor = null;
		}
		if (fHSashCursor != null) {
			fHSashCursor.dispose();
			fHSashCursor = null;
		}
		if (fVSashCursor != null) {
			fVSashCursor.dispose();
			fVSashCursor = null;
		}
		if (fHVSashCursor != null) {
			fHVSashCursor.dispose();
			fHVSashCursor = null;
		}

		super.handleDispose(event);
	}

	/**
	 * Updates the enabled state of the toolbar items.
	 * <p>
	 * This method is called whenever the state of the items needs updating.
	 * <p>
	 * Subclasses may extend this method, although this is generally not
	 * required.
	 */
	protected void updateToolItems() {

		IMergeViewerContentProvider content = getMergeContentProvider();

		Object input = getInput();

		if (fCopyLeftToRightAction != null) {
			boolean enable = content.isRightEditable(input);
			// if (enable && input instanceof ICompareInput) {
			// ITypedElement e= ((ICompareInput) input).getLeft();
			// if (e == null)
			// enable= false;
			// }
			fCopyLeftToRightAction.setEnabled(enable);
		}

		if (fCopyRightToLeftAction != null) {
			boolean enable = content.isLeftEditable(input);
			// if (enable && input instanceof ICompareInput) {
			// ITypedElement e= ((ICompareInput) input).getRight();
			// if (e == null)
			// enable= false;
			// }
			fCopyRightToLeftAction.setEnabled(enable);
		}
	}

	/**
	 * Updates the headers of the three areas by querying the content provider
	 * for a name and image for the three sides of the input object.
	 * <p>
	 * This method is called whenever the header must be updated.
	 * <p>
	 * Subclasses may extend this method, although this is generally not
	 * required.
	 */
	protected void updateHeader() {

		IMergeViewerContentProvider content = getMergeContentProvider();
		Object input = getInput();

		// Only change a label if there is a new label available
		if (fAncestorLabel != null) {
			Image ancestorImage = content.getAncestorImage(input);
			if (ancestorImage != null)
				fAncestorLabel.setImage(ancestorImage);
			String ancestorLabel = content.getAncestorLabel(input);
			if (ancestorLabel != null)
				fAncestorLabel.setText(TextProcessor.process(ancestorLabel));
		}
		if (fLeftLabel != null) {
			Image leftImage = content.getLeftImage(input);
			if (leftImage != null)
				fLeftLabel.setImage(leftImage);
			String leftLabel = content.getLeftLabel(input);
			if (leftLabel != null)
				fLeftLabel.setText(TextProcessor.process(leftLabel));
		}
		if (fRightLabel != null) {
			Image rightImage = content.getRightImage(input);
			if (rightImage != null)
				fRightLabel.setImage(rightImage);
			String rightLabel = content.getRightLabel(input);
			if (rightLabel != null)
				fRightLabel.setText(TextProcessor.process(rightLabel));
		}
	}

	/*
	 * Calculates the height of the header.
	 */
	/* package */int getHeaderHeight() {
		int headerHeight = fLeftLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
				true).y;
		headerHeight = Math.max(headerHeight,
				fDirectionLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y);
		return headerHeight;
	}

	// ---- dirty state & saving state

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.compare.IPropertyChangeNotifier#addPropertyChangeListener
	 * (org.eclipse.jface.util.IPropertyChangeListener)
	 */
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		if (fListenerList == null)
			fListenerList = new ListenerList();
		fListenerList.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.compare.IPropertyChangeNotifier#removePropertyChangeListener
	 * (org.eclipse.jface.util.IPropertyChangeListener)
	 */
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		if (fListenerList != null) {
			fListenerList.remove(listener);
			if (fListenerList.isEmpty())
				fListenerList = null;
		}
	}

	private void fireDirtyState(boolean state) {
		Utilities.firePropertyChange(fListenerList, cmv,
				CompareEditorInput.DIRTY_STATE, null, new Boolean(state));
	}

	/**
	 * Sets the dirty state of the left side of this viewer. If the new value
	 * differs from the old all registered listener are notified with a
	 * <code>PropertyChangeEvent</code> with the property name
	 * <code>CompareEditorInput.DIRTY_STATE</code>.
	 * 
	 * @param dirty
	 *            the state of the left side dirty flag
	 */
	protected void setLeftDirty(boolean dirty) {
		if (isLeftDirty() != dirty) {
			fLeftSaveAction.setEnabled(dirty);
			// Only fire the event if the combined dirty state has changed
			if ((!isRightDirty() && !isLeftDirty())
					|| (!isRightDirty() && isLeftDirty()))
				fireDirtyState(dirty);
		}
	}

	/**
	 * Sets the dirty state of the right side of this viewer. If the new value
	 * differs from the old all registered listener are notified with a
	 * <code>PropertyChangeEvent</code> with the property name
	 * <code>CompareEditorInput.DIRTY_STATE</code>.
	 * 
	 * @param dirty
	 *            the state of the right side dirty flag
	 */
	protected void setRightDirty(boolean dirty) {
		if (isRightDirty() != dirty) {
			fRightSaveAction.setEnabled(dirty);
			// Only fire the event if the combined dirty state has changed
			if ((!isRightDirty() && !isLeftDirty())
					|| (isRightDirty() && !isLeftDirty()))
				fireDirtyState(dirty);
		}
	}

	/**
	 * Method from the old internal <code>ISavable</code> interface Save the
	 * viewers's content. Note: this method is for internal use only. Clients
	 * should not call this method.
	 * 
	 * @param monitor
	 *            a progress monitor
	 * @throws CoreException
	 * @deprecated use {@link IFlushable#flush(IProgressMonitor)}.
	 */
	public void save(IProgressMonitor monitor) throws CoreException {
		flush(monitor);
	}

	/**
	 * Flush any modifications made in the viewer into the compare input. This
	 * method calls {@link #flushContent(Object, IProgressMonitor)} with the
	 * compare input of the viewer as the first parameter.
	 * 
	 * @param monitor
	 *            a progress monitor
	 * @see org.eclipse.compare.contentmergeviewer.IFlushable#flush(org.eclipse.core.runtime.IProgressMonitor)
	 * @since 3.3
	 */
	public final void flush(IProgressMonitor monitor) {
		flushContent(getInput(), monitor);
	}

	/**
	 * Flush the modified content back to input elements via the content
	 * provider. The provided input may be the current input of the viewer or it
	 * may be the previous input (i.e. this method may be called to flush
	 * modified content during an input change).
	 * 
	 * @param input
	 *            the compare input
	 * @param monitor
	 *            a progress monitor or <code>null</code> if the method was call
	 *            from a place where a progress monitor was not available.
	 * @since 3.3
	 */
	protected void flushContentOld(Object input, IProgressMonitor monitor) {

		// write back modified contents
		IMergeViewerContentProvider content = (IMergeViewerContentProvider) getContentProvider();

		boolean leftEmpty = content.getLeftContent(input) == null;
		boolean rightEmpty = content.getRightContent(input) == null;

		if (getCompareConfiguration().isLeftEditable() && isLeftDirty()) {
			byte[] bytes = getContents(true);
			if (rightEmpty && bytes != null && bytes.length == 0)
				bytes = null;
			setLeftDirty(false);
			content.saveLeftContent(input, bytes);
		}

		if (getCompareConfiguration().isRightEditable() && isRightDirty()) {
			byte[] bytes = getContents(false);
			if (leftEmpty && bytes != null && bytes.length == 0)
				bytes = null;
			setRightDirty(false);
			content.saveRightContent(input, bytes);
		}
	}

	protected void flushContent(Object input, IProgressMonitor monitor) {
		flushLeftSide(input, monitor);
		flushRightSide(input, monitor);
	}

	void flushLeftSide(Object input, IProgressMonitor monitor) {
		IMergeViewerContentProvider content = (IMergeViewerContentProvider) getContentProvider();

		boolean rightEmpty = content.getRightContent(input) == null;

		if (getCompareConfiguration().isLeftEditable() && isLeftDirty()) {
			byte[] bytes = getContents(true);
			if (rightEmpty && bytes != null && bytes.length == 0)
				bytes = null;
			setLeftDirty(false);
			content.saveLeftContent(input, bytes);
		}
	}

	void flushRightSide(Object input, IProgressMonitor monitor) {
		IMergeViewerContentProvider content = (IMergeViewerContentProvider) getContentProvider();

		boolean leftEmpty = content.getLeftContent(input) == null;

		if (getCompareConfiguration().isRightEditable() && isRightDirty()) {
			byte[] bytes = getContents(false);
			if (leftEmpty && bytes != null && bytes.length == 0)
				bytes = null;
			setRightDirty(false);
			content.saveRightContent(input, bytes);
		}
	}

	/**
	 * @param monitor
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void flushLeft(IProgressMonitor monitor) {
		flushLeftSide(getInput(), monitor);
	}

	/**
	 * @param monitor
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void flushRight(IProgressMonitor monitor) {
		flushRightSide(getInput(), monitor);
	}

	/**
	 * Return the dirty state of the right side of this viewer.
	 * 
	 * @return the dirty state of the right side of this viewer
	 * @since 3.3
	 */
	protected boolean isRightDirty() {
		return fRightSaveAction.isEnabled();
	}

	/**
	 * Return the dirty state of the left side of this viewer.
	 * 
	 * @return the dirty state of the left side of this viewer
	 * @since 3.3
	 */
	protected boolean isLeftDirty() {
		return fLeftSaveAction.isEnabled();
	}

	/**
	 * Handle a change to the given input reported from an
	 * {@link org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener}
	 * . This class registers a listener with its input and reports any change
	 * events through this method. Subclasses may override.
	 * 
	 * @since 3.3
	 */
	protected void handleCompareInputChange() {
		// before setting the new input we have to save the old
		Object input = getInput();
		if (isLeftDirty() || isRightDirty()) {
			flushContent(input, null);
		}
		refresh();
	}
}
