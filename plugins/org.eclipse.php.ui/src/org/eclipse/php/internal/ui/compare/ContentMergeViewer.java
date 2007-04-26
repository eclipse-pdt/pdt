/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.compare;

import java.util.ResourceBundle;

import org.eclipse.compare.*;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.internal.*;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * Description: this is exactly the  {@link org.eclipse.compare.contentmergeviewer} 
 * it was overriden since we need the private members  
 * @author Roy, 2007
 */
public abstract class ContentMergeViewer extends ContentViewer
					implements IPropertyChangeNotifier, ISavable {
	
	class SaveAction extends MergeViewerAction {
				
		SaveAction(boolean left) {
			super(true, false, false);
			Utilities.initAction(this, getResourceBundle(), "action.save."); //$NON-NLS-1$
		}
			
		public void run() {
			saveContent(getInput());
		}
	}
	
	/**
	 * Property names.
	 */
	private static final String ANCESTOR_ENABLED= ComparePreferencePage.INITIALLY_SHOW_ANCESTOR_PANE;	
	
	/* package */ static final int HORIZONTAL= 1;
	/* package */ static final int VERTICAL= 2;
	
	static final double HSPLIT= 0.5;
	static final double VSPLIT= 0.3;
	
	private class ContentMergeViewerLayout extends Layout {
		
		public Point computeSize(Composite c, int w, int h, boolean force) {
			return new Point(100, 100);
		}
		
		public void layout(Composite composite, boolean force) {
			
			// determine some derived sizes
			int headerHeight= fLeftLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y;
			Rectangle r= composite.getClientArea();
			
			int centerWidth= getCenterWidth();	
			int width1= (int)((r.width-centerWidth)*fHSplit);
			int width2= r.width-width1-centerWidth;
			
			int height1= 0;
			int height2= 0;
			if (fAncestorEnabled && fShowAncestor) {
				height1= (int)((r.height-(2*headerHeight))*fVSplit);
				height2= r.height-(2*headerHeight)-height1;
			} else {
				height1= 0;
				height2= r.height-headerHeight;
			}		
							
			int y= 0;
			
			if (fAncestorEnabled && fShowAncestor) {
				fAncestorLabel.setBounds(0, y, r.width, headerHeight);
				fAncestorLabel.setVisible(true);
				y+= headerHeight;
				handleResizeAncestor(0, y, r.width, height1);
				y+= height1;
			} else {
				fAncestorLabel.setVisible(false);
				handleResizeAncestor(0, 0, 0, 0);
			}
			
			fLeftLabel.getSize();	// without this resizing would not always work
			
			if (centerWidth > 3) {
				fLeftLabel.setBounds(0, y, width1+1, headerHeight);
				fDirectionLabel.setVisible(true);
				fDirectionLabel.setBounds(width1+1, y, centerWidth-1, headerHeight);
				fRightLabel.setBounds(width1+centerWidth, y, width2, headerHeight);
			} else {
				fLeftLabel.setBounds(0, y, width1, headerHeight);
				fDirectionLabel.setVisible(false);
				fRightLabel.setBounds(width1, y, r.width-width1, headerHeight);
			}
			
			y+= headerHeight;
			
			if (fCenter != null && !fCenter.isDisposed())
				fCenter.setBounds(width1, y, centerWidth, height2);
					
			handleResizeLeftRight(0, y, width1, centerWidth, width2, height2);
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
			fDirection= dir;
			fControl= c;
			fLiveResize= !(fControl instanceof Sash);
			updateCursor(c, dir);
			fControl.addMouseListener(this);
			fControl.addMouseMoveListener(this);
			fControl.addDisposeListener(
				new DisposeListener() {
					public void widgetDisposed(DisposeEvent e) {
						fControl= null;
					}
				}
			);
		}
				
		public void mouseDoubleClick(MouseEvent e) {
			if ((fDirection & HORIZONTAL) != 0)
				fHSplit= HSPLIT;
			if ((fDirection & VERTICAL) != 0)
				fVSplit= VSPLIT;
			fComposite.layout(true);
		}
		
		public void mouseDown(MouseEvent e) {
			Composite parent= fControl.getParent();
			
			Point s= parent.getSize();
			Point as= fAncestorLabel.getSize();
			Point ys= fLeftLabel.getSize();
			Point ms= fRightLabel.getSize();
			
			fWidth1= ys.x;
			fWidth2= ms.x;
			fHeight1= fLeftLabel.getLocation().y-as.y;
			fHeight2= s.y-(fLeftLabel.getLocation().y+ys.y);
			
			fX= e.x;
			fY= e.y;
			fIsDown= true;
		}
		
		public void mouseUp(MouseEvent e) {
			fIsDown= false;
			if (!fLiveResize)
				resize(e);
		}
		
		public void mouseMove(MouseEvent e) {
			if (fIsDown && fLiveResize)
				resize(e);
		}
		
		private void resize(MouseEvent e) {
			int dx= e.x-fX;
			int dy= e.y-fY;
		
			int centerWidth= fCenter.getSize().x;

			if (fWidth1 + dx > centerWidth && fWidth2 - dx > centerWidth) {
				fWidth1+= dx;
				fWidth2-= dx;
				if ((fDirection & HORIZONTAL) != 0)
					fHSplit= (double)fWidth1/(double)(fWidth1+fWidth2);
			}
			if (fHeight1 + dy > centerWidth && fHeight2 - dy > centerWidth) {
				fHeight1+= dy;
				fHeight2-= dy;
				if ((fDirection & VERTICAL) != 0)
					fVSplit= (double)fHeight1/(double)(fHeight1+fHeight2);
			}

			fComposite.layout(true);
			fControl.getDisplay().update();
		}
	}

	/** Style bits for top level composite */
	private int fStyles;
	private ResourceBundle fBundle;
	private CompareConfiguration fCompareConfiguration;
	private IPropertyChangeListener fPropertyChangeListener;
	private ICompareInputChangeListener fCompareInputChangeListener;
	private ListenerList fListenerList;
	boolean fConfirmSave= true;
	
	private double fHSplit= HSPLIT;		// width ratio of left and right panes
	private double fVSplit= VSPLIT;		// height ratio of ancestor and bottom panes
	
	private boolean fAncestorEnabled= true;	// show ancestor in case of conflicts
	/* package */ boolean fShowAncestor= false;	// if current input has conflicts
	private boolean fIsThreeWay= false;
	private ActionContributionItem fAncestorItem;
	
	private Action fCopyLeftToRightAction;	// copy from left to right
	private Action fCopyRightToLeftAction;	// copy from right to left

	MergeViewerAction fLeftSaveAction;
	MergeViewerAction fRightSaveAction;
	
	private IKeyBindingService fKeyBindingService;

	// SWT widgets
	/* package */ Composite fComposite;
	private CLabel fAncestorLabel;
	private CLabel fLeftLabel;
	private CLabel fRightLabel;
	/* package */ CLabel fDirectionLabel;
	/* package */ Control fCenter;
		
	//---- SWT resources to be disposed
	private Image fRightArrow;
	private Image fLeftArrow;
	private Image fBothArrow;
	Cursor fNormalCursor;
	private Cursor fHSashCursor;
	private Cursor fVSashCursor;
	private Cursor fHVSashCursor;

	//---- end
	
	/**
	 * Creates a new content merge viewer and initializes with a resource bundle and a
	 * configuration.
	 * 
	 * @param style SWT style bits
	 * @param bundle the resource bundle
	 * @param cc the configuration object
	 */
	protected ContentMergeViewer(int style, ResourceBundle bundle, CompareConfiguration cc) {
		
		fStyles= style & ~(SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT);	// remove BIDI direction bits
		fBundle= bundle;
		
		fAncestorEnabled= Utilities.getBoolean(cc, ANCESTOR_ENABLED, fAncestorEnabled);
		fConfirmSave= Utilities.getBoolean(cc, CompareEditor.CONFIRM_SAVE_PROPERTY, fConfirmSave);

		setContentProvider(new MergeViewerContentProvider(cc));
		
		fCompareInputChangeListener= new ICompareInputChangeListener() {
			public void compareInputChanged(ICompareInput input) {
				ContentMergeViewer.this.internalRefresh(input);
			}
		};
		
		fCompareConfiguration= cc;
		if (fCompareConfiguration != null) {
			fPropertyChangeListener= new IPropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent event) {
					ContentMergeViewer.this.propertyChange(event);
				}
			};
			fCompareConfiguration.addPropertyChangeListener(fPropertyChangeListener);
		}
			
		fLeftSaveAction= new SaveAction(true);
		fLeftSaveAction.setEnabled(false);
		fRightSaveAction= new SaveAction(false);
		fRightSaveAction.setEnabled(false);
	}
	
	//---- hooks ---------------------
	
	/**
	 * Returns the viewer's name.
	 *
	 * @return the viewer's name
	 */
	public String getTitle() {
		return Utilities.getString(getResourceBundle(), "title"); //$NON-NLS-1$
	}
	
	/**
	 * Creates the SWT controls for the ancestor, left, and right
	 * content areas of this compare viewer.
	 * Implementations typically hold onto the controls
	 * so that they can be initialized with the input objects in method
	 * <code>updateContent</code>.
	 *
	 * @param composite the container for the three areas
	 */
	abstract protected void createControls(Composite composite);

	/**
	 * Lays out the ancestor area of the compare viewer.
	 * It is called whenever the viewer is resized or when the sashes between
	 * the areas are moved to adjust the size of the areas.
	 *
	 * @param x the horizontal position of the ancestor area within its container
	 * @param y the vertical position of the ancestor area within its container
	 * @param width the width of the ancestor area
	 * @param height the height of the ancestor area
	 */
	abstract protected void handleResizeAncestor(int x, int y, int width, int height);
	
	/**
	 * Lays out the left and right areas of the compare viewer.
	 * It is called whenever the viewer is resized or when the sashes between
	 * the areas are moved to adjust the size of the areas.
	 *
	 * @param x the horizontal position of the left area within its container
	 * @param y the vertical position of the left and right area within its container
	 * @param leftWidth the width of the left area
	 * @param centerWidth the width of the gap between the left and right areas
	 * @param rightWidth the width of the right area
	 * @param height the height of the left and right areas
	 */
	abstract protected void handleResizeLeftRight(int x, int y, int leftWidth, int centerWidth,
			int rightWidth, int height);

	/**
	 * Contributes items to the given <code>ToolBarManager</code>.
	 * It is called when this viewer is installed in its container and if the container
	 * has a <code>ToolBarManager</code>.
	 * The <code>ContentMergeViewer</code> implementation of this method does nothing.
	 * Subclasses may reimplement.
	 *
	 * @param toolBarManager the toolbar manager to contribute to
	 */
	protected void createToolItems(ToolBarManager toolBarManager) {
		// empty implementation
	}

	/**
	 * Initializes the controls of the three content areas with the given input objects.
	 *
	 * @param ancestor the input for the ancestor area
	 * @param left the input for the left area
	 * @param right the input for the right area
	 */
	abstract protected void updateContent(Object ancestor, Object left, Object right);
		
	/**
	 * Copies the content of one side to the other side.
	 * Called from the (internal) actions for copying the sides of the viewer's input object.
	 * 
	 * @param leftToRight if <code>true</code>, the left side is copied to the right side;
	 * if <code>false</code>, the right side is copied to the left side
	 */
	abstract protected void copy(boolean leftToRight);

	/**
	 * Returns the byte contents of the left or right side. If the viewer
	 * has no editable content <code>null</code> can be returned.
	 *
	 * @param left if <code>true</code>, the byte contents of the left area is returned;
	 * 	if <code>false</code>, the byte contents of the right area
	 * @return the content as an array of bytes, or <code>null</code>
	 */
	abstract protected byte[] getContents(boolean left);

	//----------------------------
	
	/**
	 * Returns the resource bundle of this viewer.
	 *
	 * @return the resource bundle
	 */
	protected ResourceBundle getResourceBundle() {
		return fBundle;
	}
	
	/**
	 * Returns the compare configuration of this viewer,
	 * or <code>null</code> if this viewer does not yet have a configuration.
	 *
	 * @return the compare configuration, or <code>null</code> if none
	 */
	protected CompareConfiguration getCompareConfiguration() {
		return fCompareConfiguration;
	}
	
	/**
	 * The <code>ContentMergeViewer</code> implementation of this 
	 * <code>ContentViewer</code> method
	 * checks to ensure that the content provider is an <code>IMergeViewerContentProvider</code>.
	 * @param contentProvider the content provider to set. Must implement IMergeViewerContentProvider. 
	 */
	public void setContentProvider(IContentProvider contentProvider) {
		Assert.isTrue(contentProvider instanceof IMergeViewerContentProvider);
		super.setContentProvider(contentProvider);
	}

	/* package */ IMergeViewerContentProvider getMergeContentProvider() {
		return (IMergeViewerContentProvider) getContentProvider();
	}

	/**
	 * The <code>ContentMergeViewer</code> implementation of this 
	 * <code>Viewer</code> method returns the empty selection. Subclasses may override.
	 * @return empty selection.
	 */
	public ISelection getSelection() {
		return new ISelection() {
			public boolean isEmpty() {
				return true;
			}
		};
	}
	
	/*
	 * The <code>ContentMergeViewer</code> implementation of this 
	 * <code>Viewer</code> method does nothing. Subclasses may reimplement.
	 */
	public void setSelection(ISelection selection, boolean reveal) {
		// empty implementation
	}

	/* package */ void propertyChange(PropertyChangeEvent event) {
		
		String key= event.getProperty();

		if (key.equals(ANCESTOR_ENABLED)) {
			fAncestorEnabled= Utilities.getBoolean(getCompareConfiguration(), ANCESTOR_ENABLED, fAncestorEnabled);
			fComposite.layout(true);
			
			updateCursor(fLeftLabel, VERTICAL);
			updateCursor(fDirectionLabel, HORIZONTAL | VERTICAL);
			updateCursor(fRightLabel, VERTICAL);
			
			return;
		}
	}
	
	void updateCursor(Control c, int dir) {
		if (!(c instanceof Sash)) {
			Cursor cursor= null;
			switch (dir) {
			case VERTICAL:
				if (fAncestorEnabled) {
					if (fVSashCursor == null) fVSashCursor= new Cursor(c.getDisplay(), SWT.CURSOR_SIZENS);
					cursor= fVSashCursor;
				} else {
					if (fNormalCursor == null) fNormalCursor= new Cursor(c.getDisplay(), SWT.CURSOR_ARROW);
					cursor= fNormalCursor;
				}
				break;
			case HORIZONTAL:
				if (fHSashCursor == null) fHSashCursor= new Cursor(c.getDisplay(), SWT.CURSOR_SIZEWE);
				cursor= fHSashCursor;
				break;
			case VERTICAL + HORIZONTAL:
				if (fAncestorEnabled) {
					if (fHVSashCursor == null) fHVSashCursor= new Cursor(c.getDisplay(), SWT.CURSOR_SIZEALL);
					cursor= fHVSashCursor;
				} else {
					if (fHSashCursor == null) fHSashCursor= new Cursor(c.getDisplay(), SWT.CURSOR_SIZEWE);
					cursor= fHSashCursor;
				}
				break;
			}
			if (cursor != null)
				c.setCursor(cursor);
		}
	}

	void setAncestorVisibility(boolean visible, boolean enabled) {
		if (fAncestorItem != null) {
			Action action= (Action) fAncestorItem.getAction();
			if (action != null) {
				action.setChecked(visible);
				action.setEnabled(enabled);
			}
		}
		if (fCompareConfiguration != null)
			fCompareConfiguration.setProperty(ANCESTOR_ENABLED, new Boolean(visible));
	}

	//---- input
			 
	/* package */ boolean isThreeWay() {
		return fIsThreeWay;
	}
	
	/**
	 * Internal hook method called when the input to this viewer is
	 * initially set or subsequently changed.
	 * <p>
	 * The <code>ContentMergeViewer</code> implementation of this <code>Viewer</code>
	 * method tries to save the old input by calling <code>doSave(...)</code> and
	 * then calls <code>internalRefresh(...)</code>.
	 *
	 * @param input the new input of this viewer, or <code>null</code> if there is no new input
	 * @param oldInput the old input element, or <code>null</code> if there was previously no input
	 */
	protected final void inputChanged(Object input, Object oldInput) {
		
		if (input != oldInput)
			if (oldInput instanceof ICompareInput)
				((ICompareInput)oldInput).removeCompareInputChangeListener(fCompareInputChangeListener);
		
		boolean success= doSave(input, oldInput);
		
		if (input != oldInput)
			if (input instanceof ICompareInput)
				((ICompareInput)input).addCompareInputChangeListener(fCompareInputChangeListener);
		
		if (success) {
			setLeftDirty(false);
			setRightDirty(false);
		}

		if (input != oldInput)
			internalRefresh(input);
	}
	
	/**
	 * This method is called from the <code>Viewer</code> method <code>inputChanged</code>
	 * to save any unsaved changes of the old input.
	 * <p>
	 * The <code>ContentMergeViewer</code> implementation of this
	 * method calls <code>saveContent(...)</code>. If confirmation has been turned on
	 * with <code>setConfirmSave(true)</code>, a confirmation alert is posted before saving.
	 * </p>
	 * Clients can override this method and are free to decide whether
	 * they want to call the inherited method.
	 * @param newInput the new input of this viewer, or <code>null</code> if there is no new input
	 * @param oldInput the old input element, or <code>null</code> if there was previously no input
	 * @return <code>true</code> if saving was successful, or if the user didn't want to save (by pressing 'NO' in the confirmation dialog).
	 * @since 2.0
	 */
	protected boolean doSave(Object newInput, Object oldInput) {
		
		// before setting the new input we have to save the old
		if (fLeftSaveAction.isEnabled() || fRightSaveAction.isEnabled()) {
			
			// post alert
			if (fConfirmSave) {
				Shell shell= fComposite.getShell();
				
				MessageDialog dialog= new MessageDialog(shell,
					Utilities.getString(getResourceBundle(), "saveDialog.title"), //$NON-NLS-1$
					null, 	// accept the default window icon
					Utilities.getString(getResourceBundle(), "saveDialog.message"), //$NON-NLS-1$
					MessageDialog.QUESTION,
					new String[] {
						IDialogConstants.YES_LABEL,
						IDialogConstants.NO_LABEL,
					},
					0);		// default button index
									
				switch (dialog.open()) {	// open returns index of pressed button
				case 0:
					saveContent(oldInput);
					break;
				case 1:
					setLeftDirty(false);
					setRightDirty(false);
					break;
				case 2:
					throw new ViewerSwitchingCancelled();
				}
			} else
				saveContent(oldInput);
			return true;
		}
		return false;
	}
		
	/**
	 * Controls whether <code>doSave(Object, Object)</code> asks for confirmation before saving
	 * the old input with <code>saveContent(Object)</code>.
	 * @param enable a value of <code>true</code> enables confirmation
	 * @since 2.0
	 */
	public void setConfirmSave(boolean enable) {
		fConfirmSave= enable;
	}
	
	/* (non Javadoc)
	 * see Viewer.refresh
	 */
	public void refresh() {
		internalRefresh(getInput());
	}
	
	private void internalRefresh(Object input) {
		
		IMergeViewerContentProvider content= getMergeContentProvider();
		if (content != null) {
			Object ancestor= content.getAncestorContent(input);
			if (input instanceof ICompareInput)	
				fIsThreeWay= (((ICompareInput)input).getKind() & Differencer.DIRECTION_MASK) != 0;
			else
				fIsThreeWay= ancestor != null;
				
			if (fAncestorItem != null)
				fAncestorItem.setVisible(fIsThreeWay);
				
			boolean oldFlag= fShowAncestor;
			fShowAncestor= fIsThreeWay && content.showAncestor(input);
			
			if (fAncestorEnabled && oldFlag != fShowAncestor)
				fComposite.layout(true);
			
			ToolBarManager tbm= CompareViewerPane.getToolBarManager(fComposite.getParent());
			if (tbm != null) {
				updateToolItems();
				tbm.update(true);
				tbm.getControl().getParent().layout(true);
			}
			
			updateHeader();
									
			Object left= content.getLeftContent(input);
			Object right= content.getRightContent(input);
			updateContent(ancestor, left, right);
		}
	}
	
	//---- layout & SWT control creation
		
	/**
	 * Builds the SWT controls for the three areas of a compare/merge viewer.
	 * <p>
	 * Calls the hooks <code>createControls</code> and <code>createToolItems</code>
	 * to let subclasses build the specific content areas and to add items to
	 * an enclosing toolbar.
	 * <p>
	 * This method must only be called in the constructor of subclasses.
	 *
	 * @param parent the parent control
	 * @return the new control
	 */
	protected final Control buildControl(Composite parent) {
											
		fComposite= new Composite(parent, fStyles | SWT.LEFT_TO_RIGHT) { // we force a specific direction
			public boolean setFocus() {
				return internalSetFocus();
			}
		};
		fComposite.setData(CompareUI.COMPARE_VIEWER_TITLE, getTitle());
		
		hookControl(fComposite);	// hook help & dispose listener
		
		fComposite.setLayout(new ContentMergeViewerLayout());
		
		int style= SWT.SHADOW_OUT;
		fAncestorLabel= new CLabel(fComposite, style);
		
		fLeftLabel= new CLabel(fComposite, style);
		new Resizer(fLeftLabel, VERTICAL);
		
		fDirectionLabel= new CLabel(fComposite, style);
		fDirectionLabel.setAlignment(SWT.CENTER);
		new Resizer(fDirectionLabel, HORIZONTAL | VERTICAL);
		
		fRightLabel= new CLabel(fComposite, style);
		new Resizer(fRightLabel, VERTICAL);
		
		if (fCenter == null || fCenter.isDisposed())
			fCenter= createCenter(fComposite);
				
		createControls(fComposite);
		
		IWorkbenchPartSite ps= Utilities.findSite(fComposite);
		fKeyBindingService= ps != null ? ps.getKeyBindingService() : null;
						
		ToolBarManager tbm= CompareViewerPane.getToolBarManager(parent);
		if (tbm != null) {
			tbm.removeAll();
			
			// define groups
			tbm.add(new Separator("modes"));	//$NON-NLS-1$
			tbm.add(new Separator("merge"));	//$NON-NLS-1$
			tbm.add(new Separator("navigation"));	//$NON-NLS-1$
			
			CompareConfiguration cc= getCompareConfiguration();
		
			if (cc.isRightEditable()) {
				fCopyLeftToRightAction=
					new Action() {
						public void run() {
							copy(true);
						}
					};
				Utilities.initAction(fCopyLeftToRightAction, getResourceBundle(), "action.CopyLeftToRight."); //$NON-NLS-1$
				tbm.appendToGroup("merge", fCopyLeftToRightAction); //$NON-NLS-1$
				Utilities.registerAction(fKeyBindingService, fCopyLeftToRightAction, "org.eclipse.compare.copyAllLeftToRight");	//$NON-NLS-1$
			}
			
			if (cc.isLeftEditable()) {
				fCopyRightToLeftAction=
					new Action() {
						public void run() {
							copy(false);
						}
					};
				Utilities.initAction(fCopyRightToLeftAction, getResourceBundle(), "action.CopyRightToLeft."); //$NON-NLS-1$
				tbm.appendToGroup("merge", fCopyRightToLeftAction); //$NON-NLS-1$
				Utilities.registerAction(fKeyBindingService, fCopyRightToLeftAction, "org.eclipse.compare.copyAllRightToLeft");	//$NON-NLS-1$
			}
			
			Action a= new ChangePropertyAction(fBundle, fCompareConfiguration, "action.EnableAncestor.", ANCESTOR_ENABLED); //$NON-NLS-1$
			a.setChecked(fAncestorEnabled);
			fAncestorItem= new ActionContributionItem(a);
			fAncestorItem.setVisible(false);
			tbm.appendToGroup("modes", fAncestorItem); //$NON-NLS-1$
			
			createToolItems(tbm);
			updateToolItems();
			
			tbm.update(true);
		}
	
		return fComposite;
	}
	
	/* package */ boolean internalSetFocus() {
		return false;
	}
	
	/* package */ int getCenterWidth() {
		return 3;
	}
	
	/* package */ boolean getAncestorEnabled() {
		return fAncestorEnabled;
	}
	
	/* package */ Control createCenter(Composite parent) {
		Sash sash= new Sash(parent, SWT.VERTICAL);
		new Resizer(sash, HORIZONTAL);
		return sash;
	}
	
	/* package */ Control getCenter() {
		return fCenter;
	}
		
	/* 
	 * @see Viewer.getControl()
	 */
	public Control getControl() {
		return fComposite;
	}
	
	/*
	 * Called on the viewer disposal.
	 * Unregisters from the compare configuration.
	 * Clients may extend if they have to do additional cleanup.
	 */
	protected void handleDispose(DisposeEvent event) {
		
		if (fKeyBindingService != null) {
			if (fCopyLeftToRightAction != null)
				fKeyBindingService.unregisterAction(fCopyLeftToRightAction);
			if (fCopyRightToLeftAction != null)
				fKeyBindingService.unregisterAction(fCopyRightToLeftAction);
			fKeyBindingService= null;
		}
		
		Object input= getInput();	
		if (input instanceof ICompareInput)
			((ICompareInput)input).removeCompareInputChangeListener(fCompareInputChangeListener);
		
		if (fCompareConfiguration != null && fPropertyChangeListener != null) {
			fCompareConfiguration.removePropertyChangeListener(fPropertyChangeListener);
			fPropertyChangeListener= null;
		}

		fAncestorLabel= null;
		fLeftLabel= null;
		fDirectionLabel= null;
		fRightLabel= null;
		fCenter= null;
				
		if (fRightArrow != null) {
			fRightArrow.dispose();
			fRightArrow= null;
		}
		if (fLeftArrow != null) {
			fLeftArrow.dispose();
			fLeftArrow= null;
		}
		if (fBothArrow != null) {
			fBothArrow.dispose();
			fBothArrow= null;
		}

		if (fNormalCursor != null) {
			fNormalCursor.dispose();
			fNormalCursor= null;
		}
		if (fHSashCursor != null) {
			fHSashCursor.dispose();
			fHSashCursor= null;
		}
		if (fVSashCursor != null) {
			fVSashCursor.dispose();
			fVSashCursor= null;
		}
		if (fHVSashCursor != null) {
			fHVSashCursor.dispose();
			fHVSashCursor= null;
		}
		
		super.handleDispose(event);
  	}
  	
	/**
	 * Updates the enabled state of the toolbar items.
	 * <p>
	 * This method is called whenever the state of the items needs updating.
	 * <p>
	 * Subclasses may extend this method, although this is generally not required.
	 */
	protected void updateToolItems() {
										
		IMergeViewerContentProvider content= getMergeContentProvider();
		
		Object input= getInput();
		
		if (fCopyLeftToRightAction != null) {
			boolean enable= content.isRightEditable(input);
//			if (enable && input instanceof ICompareInput) {
//				ITypedElement e= ((ICompareInput) input).getLeft();
//				if (e == null)
//					enable= false;
//			}
			fCopyLeftToRightAction.setEnabled(enable);
		}
		
		if (fCopyRightToLeftAction != null) {
			boolean enable= content.isLeftEditable(input);
//			if (enable && input instanceof ICompareInput) {
//				ITypedElement e= ((ICompareInput) input).getRight();
//				if (e == null)
//					enable= false;
//			}
			fCopyRightToLeftAction.setEnabled(enable);
		}
	}
	
	/**
	 * Updates the headers of the three areas
	 * by querying the content provider for a name and image for
	 * the three sides of the input object.
	 * <p>
	 * This method is called whenever the header must be updated.
	 * <p>
	 * Subclasses may extend this method, although this is generally not required.
	 */
	protected void updateHeader() {
						
		IMergeViewerContentProvider content= getMergeContentProvider();
		Object input= getInput();

		if (fAncestorLabel != null) {
			fAncestorLabel.setImage(content.getAncestorImage(input));
			fAncestorLabel.setText(content.getAncestorLabel(input));
		}
		if (fLeftLabel != null) {
			fLeftLabel.setImage(content.getLeftImage(input));
			fLeftLabel.setText(content.getLeftLabel(input));
		}
		if (fRightLabel != null) {
			fRightLabel.setImage(content.getRightImage(input));
			fRightLabel.setText(content.getRightLabel(input));
		}
	}
	
//	private Image loadImage(String name) {
//		ImageDescriptor id= ImageDescriptor.createFromFile(ContentMergeViewer.class, name);
//		if (id != null)
//			return id.createImage();
//		return null;
//	}
		
	/*
	 * Calculates the height of the header.
	 */
	/* package */ int getHeaderHeight() {
		int headerHeight= fLeftLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y;
		headerHeight= Math.max(headerHeight, fDirectionLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y);		
		return headerHeight;
	}

	//---- merge direction
	
	/*
	 * Returns true if both sides are editable.
	 */
	/* package */ boolean canToggleMergeDirection() {
		IMergeViewerContentProvider content= getMergeContentProvider();
		Object input= getInput();
		return content.isLeftEditable(input) && content.isRightEditable(input);
	}
	
	//---- dirty state & saving state
	
	/* (non Javadoc)
	 * see IPropertyChangeNotifier.addPropertyChangeListener
	 */
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		if (fListenerList == null)
			fListenerList= new ListenerList();
		fListenerList.add(listener);
	}
	
	/* (non Javadoc)
	 * see IPropertyChangeNotifier.removePropertyChangeListener
	 */
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		if (fListenerList != null) {
			fListenerList.remove(listener);
			if (fListenerList.isEmpty())
				fListenerList= null;
		}
	}
	
	/* package */ void fireDirtyState(boolean state) {
		Utilities.firePropertyChange(fListenerList, this, CompareEditorInput.DIRTY_STATE, null, new Boolean(state));
	}
	
	/**
	 * Sets the dirty state of the left side of this viewer.
	 * If the new value differs from the old
	 * all registered listener are notified with
	 * a <code>PropertyChangeEvent</code> with the
	 * property name <code>CompareEditorInput.DIRTY_STATE</code>.
	 *
	 * @param dirty the state of the left side dirty flag
	 */
	protected void setLeftDirty(boolean dirty) {
		if (fLeftSaveAction.isEnabled() != dirty) {
			fLeftSaveAction.setEnabled(dirty);
			fireDirtyState(dirty);
		}
	}
	
	/**
	 * Sets the dirty state of the right side of this viewer.
	 * If the new value differs from the old
	 * all registered listener are notified with
	 * a <code>PropertyChangeEvent</code> with the
	 * property name <code>CompareEditorInput.DIRTY_STATE</code>.
	 *
	 * @param dirty the state of the right side dirty flag
	 */
	protected void setRightDirty(boolean dirty) {
		if (fRightSaveAction.isEnabled() != dirty) {
			fRightSaveAction.setEnabled(dirty);
			fireDirtyState(dirty);
		}
	}
	
	/*
	 * Save the viewers's content.
	 * Note: this method is for internal use only. Clients should not call this method. 
	 * @since 2.0
	 */
	public void save(IProgressMonitor pm) throws CoreException {
		saveContent(getInput());
	}
	
	/*
	 * Save modified content back to input elements via the content provider.
	 */
	/* package */ void saveContent(Object oldInput) {
				
		// write back modified contents
		IMergeViewerContentProvider content= (IMergeViewerContentProvider) getContentProvider();
		
		boolean leftEmpty= content.getLeftContent(oldInput) == null;
		boolean rightEmpty= content.getRightContent(oldInput) == null;

		if (fCompareConfiguration.isLeftEditable() && fLeftSaveAction.isEnabled()) {
			byte[] bytes= getContents(true);
			if (leftEmpty && bytes != null && bytes.length == 0)
				bytes= null;
			setLeftDirty(false);
			content.saveLeftContent(oldInput, bytes);
		}
		
		if (fCompareConfiguration.isRightEditable() && fRightSaveAction.isEnabled()) {
			byte[] bytes= getContents(false);
			if (rightEmpty && bytes != null && bytes.length == 0)
				bytes= null;
			setRightDirty(false);
			content.saveRightContent(oldInput, bytes);
		}
	}
}
