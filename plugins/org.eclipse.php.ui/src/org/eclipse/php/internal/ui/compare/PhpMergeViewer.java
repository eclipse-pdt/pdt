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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.IDocumentRange;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.contentmergeviewer.ITokenComparator;
import org.eclipse.compare.internal.*;
import org.eclipse.compare.rangedifferencer.IRangeComparator;
import org.eclipse.compare.rangedifferencer.RangeDifference;
import org.eclipse.compare.rangedifferencer.RangeDifferencer;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.wst.sse.core.internal.document.StructuredDocumentFactory;

/**
 * Description: Used to merge two inputs php sources   
 * @author Roy, 2007
 * @inspiredby org.eclipse.copmpare
 */
public class PhpMergeViewer extends ContentMergeViewer {

	private static final boolean DEBUG = false;

	private static final boolean FIX_47640 = true;

	private static final String[] GLOBAL_ACTIONS = { ActionFactory.UNDO.getId(), ActionFactory.REDO.getId(), ActionFactory.CUT.getId(), ActionFactory.COPY.getId(), ActionFactory.PASTE.getId(), ActionFactory.DELETE.getId(), ActionFactory.SELECT_ALL.getId(), ActionFactory.SAVE.getId() };
	private static final String[] TEXT_ACTIONS = { MergeSourceViewer.UNDO_ID, MergeSourceViewer.REDO_ID, MergeSourceViewer.CUT_ID, MergeSourceViewer.COPY_ID, MergeSourceViewer.PASTE_ID, MergeSourceViewer.DELETE_ID, MergeSourceViewer.SELECT_ALL_ID, MergeSourceViewer.SAVE_ID };

	private static final String BUNDLE_NAME = "org.eclipse.compare.contentmergeviewer.TextMergeViewerResources"; //$NON-NLS-1$

	// the following symbolic constants must match the IDs in Compare's plugin.xml
	private static final String INCOMING_COLOR = "INCOMING_COLOR"; //$NON-NLS-1$
	private static final String OUTGOING_COLOR = "OUTGOING_COLOR"; //$NON-NLS-1$
	private static final String CONFLICTING_COLOR = "CONFLICTING_COLOR"; //$NON-NLS-1$
	private static final String RESOLVED_COLOR = "RESOLVED_COLOR"; //$NON-NLS-1$

	// constants
	/** Width of left and right vertical bar */
	private static final int MARGIN_WIDTH = 6;
	/** Width of center bar */
	private static final int CENTER_WIDTH = 34;
	/** Width of birds eye view */
	private static final int BIRDS_EYE_VIEW_WIDTH = 12;
	/** Width of birds eye view */
	private static final int BIRDS_EYE_VIEW_INSET = 2;
	/** */
	private static final int RESOLVE_SIZE = 5;
	/** if true copying conflicts from one side to other concatenates both sides */
	private static final boolean APPEND_CONFLICT = true;

	/** line width of change borders */
	private static final int LW = 1;
	/** Selects between smartTokenDiff and mergingTokenDiff */
	private static final boolean USE_MERGING_TOKEN_DIFF = false;

	// determines whether a change between left and right is considered incoming or outgoing
	private boolean fLeftIsLocal;
	private boolean fShowCurrentOnly = false;
	private boolean fShowCurrentOnly2 = false;
	private int fMarginWidth = MARGIN_WIDTH;
	private int fTopInset;

	// Colors
	private RGB fBackground;
	private RGB fForeground;
	private boolean fPollSystemForeground = true;
	private boolean fPollSystemBackground = true;

	private RGB SELECTED_INCOMING;
	private RGB INCOMING;
	private RGB INCOMING_FILL;

	private RGB SELECTED_CONFLICT;
	private RGB CONFLICT;
	private RGB CONFLICT_FILL;

	private RGB SELECTED_OUTGOING;
	private RGB OUTGOING;
	private RGB OUTGOING_FILL;

	private RGB RESOLVED;

	private boolean fEndOfDocReached;
	private IDocumentListener fDocumentListener;

	private IPreferenceStore fPreferenceStore;
	private IPropertyChangeListener fPreferenceChangeListener;

	/** All diffs for calculating scrolling position (includes line ranges without changes) */
	private ArrayList fAllDiffs;
	/** Subset of above: just real differences. */
	private ArrayList fChangeDiffs;
	/** The current diff */
	private Diff fCurrentDiff;

	private HashMap fNewAncestorRanges = new HashMap();
	private HashMap fNewLeftRanges = new HashMap();
	private HashMap fNewRightRanges = new HashMap();

	private MergeSourceViewer fAncestor;
	private MergeSourceViewer fLeft;
	private MergeSourceViewer fRight;

	private int fLeftLineCount;
	private int fRightLineCount;

	private String fLeftEncoding;
	private String fRightEncoding;

	private boolean fInScrolling;

	private int fPts[] = new int[8]; // scratch area for polygon drawing

	private int fInheritedDirection; // inherited direction
	private int fTextDirection; // requested direction for embedded SourceViewer

	private boolean fIgnoreAncestor = false;
	private ActionContributionItem fIgnoreAncestorItem;
	private boolean fHighlightRanges;

	private boolean fShowPseudoConflicts = false;

	private boolean fUseSplines = true;
	private boolean fUseSingleLine = true;
	private boolean fUseResolveUI = true;

	private String fSymbolicFontName;

	private ActionContributionItem fNextItem; // goto next difference
	private ActionContributionItem fPreviousItem; // goto previous difference
	private ActionContributionItem fCopyDiffLeftToRightItem;
	private ActionContributionItem fCopyDiffRightToLeftItem;

	private IKeyBindingService fKeyBindingService;

	private boolean fSynchronizedScrolling = true;
	private boolean fShowMoreInfo = false;

	private MergeSourceViewer fFocusPart;

	private boolean fSubDoc = true;
	private IPositionUpdater fPositionUpdater;
	private boolean fIsMotif;
	private boolean fIsCarbon;

	private boolean fHasErrors;

	// SWT widgets
	private BufferedCanvas fAncestorCanvas;
	private BufferedCanvas fLeftCanvas;
	private BufferedCanvas fRightCanvas;
	private Canvas fScrollCanvas;
	private ScrollBar fVScrollBar;
	private Canvas fBirdsEyeCanvas;
	private Canvas fSummaryHeader;
	private HeaderPainter fHeaderPainter;

	// SWT resources to be disposed
	private Map fColors;
	private Cursor fBirdsEyeCursor;

	// points for center curves
	private double[] fBasicCenterCurve;

	private Button fCenterButton;
	private Diff fButtonDiff;

	class HeaderPainter implements PaintListener {

		private static final int INSET = BIRDS_EYE_VIEW_INSET;

		private RGB fIndicatorColor;
		private Color fSeparatorColor;

		public HeaderPainter() {
			fSeparatorColor = fSummaryHeader.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
		}

		/*
		 * Returns true on color change
		 */
		public boolean setColor(RGB color) {
			RGB oldColor = fIndicatorColor;
			fIndicatorColor = color;
			if (color == null)
				return oldColor != null;
			if (oldColor != null)
				return !color.equals(oldColor);
			return true;
		}

		private void drawBevelRect(GC gc, int x, int y, int w, int h, Color topLeft, Color bottomRight) {
			gc.setForeground(topLeft);
			gc.drawLine(x, y, x + w - 1, y);
			gc.drawLine(x, y, x, y + h - 1);

			gc.setForeground(bottomRight);
			gc.drawLine(x + w, y, x + w, y + h);
			gc.drawLine(x, y + h, x + w, y + h);
		}

		public void paintControl(PaintEvent e) {

			Point s = fSummaryHeader.getSize();

			if (fIndicatorColor != null) {
				Display d = fSummaryHeader.getDisplay();
				e.gc.setBackground(getColor(d, fIndicatorColor));
				int min = Math.min(s.x, s.y) - 2 * INSET;
				Rectangle r = new Rectangle((s.x - min) / 2, (s.y - min) / 2, min, min);
				e.gc.fillRectangle(r);
				if (d != null)
					drawBevelRect(e.gc, r.x, r.y, r.width - 1, r.height - 1, d.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW), d.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));

				e.gc.setForeground(fSeparatorColor);
				e.gc.setLineWidth(1);
				e.gc.drawLine(0 + 1, s.y - 1, s.x - 1 - 1, s.y - 1);
			}
		}
	}

	/*
	 * The position updater used to adapt the positions representing
	 * the child document ranges to changes of the parent document.
	 */
	class ChildPositionUpdater extends DefaultPositionUpdater {

		/*
		 * Creates the position updated.
		 */
		protected ChildPositionUpdater(String category) {
			super(category);
		}

		/*
		 * Child document ranges cannot be deleted other then by calling
		 * freeChildDocument.
		 */
		protected boolean notDeleted() {
			return true;
		}

		/*
		 * If an insertion happens at a child document's start offset, the
		 * position is extended rather than shifted. Also, if something is added 
		 * right behind the end of the position, the position is extended rather
		 * than kept stable.
		 */
		protected void adaptToInsert() {

			if (fPosition == fLeft.getRegion() || fPosition == fRight.getRegion()) {
				int myStart = fPosition.offset;
				int myEnd = fPosition.offset + fPosition.length;
				myEnd = Math.max(myStart, myEnd);

				int yoursStart = fOffset;
				int yoursEnd = fOffset + fReplaceLength - 1;
				yoursEnd = Math.max(yoursStart, yoursEnd);

				if (myEnd < yoursStart)
					return;

				if (myStart <= yoursStart)
					fPosition.length += fReplaceLength;
				else
					fPosition.offset += fReplaceLength;
			} else {
				super.adaptToInsert();
			}
		}
	}

	/*
	 * A Diff represents synchronized character ranges in two or three Documents.
	 * The MergeTextViewer uses Diffs to find differences in line and token ranges.
	 */
	/* package */class Diff {
		/** character range in ancestor document */
		Position fAncestorPos;
		/** character range in left document */
		Position fLeftPos;
		/** character range in right document */
		Position fRightPos;
		/** if this is a TokenDiff fParent points to the enclosing LineDiff */
		Diff fParent;
		/** if Diff has been resolved */
		boolean fResolved;
		int fDirection;
		boolean fIsToken = false;
		/** child token diffs */
		ArrayList fDiffs;
		boolean fIsWhitespace = false;

		/*
		 * Create Diff from two ranges and an optional parent diff.
		 */
		Diff(Diff parent, int dir, IDocument ancestorDoc, Position aRange, int ancestorStart, int ancestorEnd, IDocument leftDoc, Position lRange, int leftStart, int leftEnd, IDocument rightDoc, Position rRange, int rightStart, int rightEnd) {
			fParent = parent != null ? parent : this;
			fDirection = dir;

			fLeftPos = createPosition(leftDoc, lRange, leftStart, leftEnd);
			fRightPos = createPosition(rightDoc, rRange, rightStart, rightEnd);
			if (ancestorDoc != null)
				fAncestorPos = createPosition(ancestorDoc, aRange, ancestorStart, ancestorEnd);
		}

		Position getPosition(char type) {
			switch (type) {
				case 'A':
					return fAncestorPos;
				case 'L':
					return fLeftPos;
				case 'R':
					return fRightPos;
			}
			return null;
		}

		boolean isInRange(char type, int pos) {
			Position p = getPosition(type);
			return (pos >= p.offset) && (pos < (p.offset + p.length));
		}

		String changeType() {
			boolean leftEmpty = fLeftPos.length == 0;
			boolean rightEmpty = fRightPos.length == 0;

			if (fDirection == RangeDifference.LEFT) {
				if (!leftEmpty && rightEmpty)
					return CompareMessages.TextMergeViewer_changeType_addition;
				if (leftEmpty && !rightEmpty)
					return CompareMessages.TextMergeViewer_changeType_deletion;
			} else {
				if (leftEmpty && !rightEmpty)
					return CompareMessages.TextMergeViewer_changeType_addition;
				if (!leftEmpty && rightEmpty)
					return CompareMessages.TextMergeViewer_changeType_deletion;
			}
			return CompareMessages.TextMergeViewer_changeType_change;
		}

		Image getImage() {
			int code = Differencer.CHANGE;
			switch (fDirection) {
				case RangeDifference.RIGHT:
					code += Differencer.LEFT;
					break;
				case RangeDifference.LEFT:
					code += Differencer.RIGHT;
					break;
				case RangeDifference.ANCESTOR:
				case RangeDifference.CONFLICT:
					code += Differencer.CONFLICTING;
					break;
			}
			if (code != 0)
				return getCompareConfiguration().getImage(code);
			return null;
		}

		Position createPosition(IDocument doc, Position range, int start, int end) {
			try {
				int l = end - start;
				if (range != null) {
					int dl = range.length;
					if (l > dl)
						l = dl;
				} else {
					int dl = doc.getLength();
					if (start + l > dl)
						l = dl - start;
				}

				Position p = null;
				try {
					p = new Position(start, l);
				} catch (RuntimeException ex) {
					// silently ignored
				}

				try {
					doc.addPosition(IDocumentRange.RANGE_CATEGORY, p);
				} catch (BadPositionCategoryException ex) {
					// silently ignored
				}
				return p;
			} catch (BadLocationException ee) {
				// silently ignored
			}
			return null;
		}

		void add(Diff d) {
			if (fDiffs == null)
				fDiffs = new ArrayList();
			fDiffs.add(d);
		}

		boolean isDeleted() {
			if (fAncestorPos != null && fAncestorPos.isDeleted())
				return true;
			return fLeftPos.isDeleted() || fRightPos.isDeleted();
		}

		void setResolved(boolean r) {
			fResolved = r;
			if (r)
				fDiffs = null;
		}

		boolean isResolved() {
			if (!fResolved && fDiffs != null) {
				Iterator e = fDiffs.iterator();
				while (e.hasNext()) {
					Diff d = (Diff) e.next();
					if (!d.isResolved())
						return false;
				}
				return true;
			}
			return fResolved;
		}

		//	private boolean isIncoming() {
		//		switch (fDirection) {
		//		case RangeDifference.RIGHT:
		//			if (fLeftIsLocal)
		//				return true;
		//			break;
		//		case RangeDifference.LEFT:
		//			if (!fLeftIsLocal)
		//				return true;
		//			break;
		//		}
		//		return false;
		//	}

		private boolean isIncomingOrConflicting() {
			switch (fDirection) {
				case RangeDifference.RIGHT:
					if (fLeftIsLocal)
						return true;
					break;
				case RangeDifference.LEFT:
					if (!fLeftIsLocal)
						return true;
					break;
				case RangeDifference.CONFLICT:
					return true;
			}
			return false;
		}

		//	private boolean isUnresolvedIncoming() {
		//		if (fResolved)
		//			return false;
		//		return isIncoming();
		//	}

		private boolean isUnresolvedIncomingOrConflicting() {
			if (fResolved)
				return false;
			return isIncomingOrConflicting();
		}

		Position getPosition(MergeSourceViewer w) {
			if (w == fLeft)
				return fLeftPos;
			if (w == fRight)
				return fRightPos;
			if (w == fAncestor)
				return fAncestorPos;
			return null;
		}

		/*
		 * Returns true if given character range overlaps with this Diff.
		 */
		boolean overlaps(MergeSourceViewer w, int start, int end) {
			Position h = getPosition(w);
			if (h != null) {
				int ds = h.getOffset();
				int de = ds + h.getLength();
				if ((start < de) && (end >= ds))
					return true;
			}
			return false;
		}

		int getMaxDiffHeight(boolean withAncestor) {
			Point region = new Point(0, 0);
			int h = fLeft.getLineRange(fLeftPos, region).y;
			if (withAncestor)
				h = Math.max(h, fAncestor.getLineRange(fAncestorPos, region).y);
			return Math.max(h, fRight.getLineRange(fRightPos, region).y);
		}

		int getAncestorHeight() {
			Point region = new Point(0, 0);
			return fAncestor.getLineRange(fAncestorPos, region).y;
		}

		int getLeftHeight() {
			Point region = new Point(0, 0);
			return fLeft.getLineRange(fLeftPos, region).y;
		}

		int getRightHeight() {
			Point region = new Point(0, 0);
			return fRight.getLineRange(fRightPos, region).y;
		}
	}

	//---- MergeTextViewer

	/**
	 * Creates a text merge viewer under the given parent control.
	 *
	 * @param parent the parent control
	 * @param configuration the configuration object
	 */
	public PhpMergeViewer(Composite parent, CompareConfiguration configuration) {
		this(parent, SWT.NULL, configuration);
	}

	/**
	 * Creates a text merge viewer under the given parent control.
	 *
	 * @param parent the parent control
	 * @param style SWT style bits for top level composite of this viewer
	 * @param configuration the configuration object
	 */
	public PhpMergeViewer(Composite parent, int style, CompareConfiguration configuration) {
		super(style, ResourceBundle.getBundle(BUNDLE_NAME), configuration);

		int inheritedStyle = parent.getStyle();
		if ((inheritedStyle & SWT.LEFT_TO_RIGHT) != 0)
			fInheritedDirection = SWT.LEFT_TO_RIGHT;
		else if ((inheritedStyle & SWT.RIGHT_TO_LEFT) != 0)
			fInheritedDirection = SWT.RIGHT_TO_LEFT;
		else
			fInheritedDirection = SWT.NONE;

		if ((style & SWT.LEFT_TO_RIGHT) != 0)
			fTextDirection = SWT.LEFT_TO_RIGHT;
		else if ((style & SWT.RIGHT_TO_LEFT) != 0)
			fTextDirection = SWT.RIGHT_TO_LEFT;
		else
			fTextDirection = SWT.NONE;

		fSymbolicFontName = getClass().getName();

		String platform = SWT.getPlatform();
		fIsMotif = "motif".equals(platform); //$NON-NLS-1$
		fIsCarbon = "carbon".equals(platform); //$NON-NLS-1$

		if (fIsMotif)
			fMarginWidth = 0;

		Display display = parent.getDisplay();

		fPreferenceChangeListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				PhpMergeViewer.this.propertyChange(event);
			}
		};

		fPreferenceStore = configuration.getPreferenceStore();
		if (fPreferenceStore != null) {
			fPreferenceStore.addPropertyChangeListener(fPreferenceChangeListener);

			checkForColorUpdate(display);

			fLeftIsLocal = Utilities.getBoolean(configuration, "LEFT_IS_LOCAL", false); //$NON-NLS-1$
			fSynchronizedScrolling = fPreferenceStore.getBoolean(ComparePreferencePage.SYNCHRONIZE_SCROLLING);
			fShowMoreInfo = fPreferenceStore.getBoolean(ComparePreferencePage.SHOW_MORE_INFO);
			fShowPseudoConflicts = fPreferenceStore.getBoolean(ComparePreferencePage.SHOW_PSEUDO_CONFLICTS);
			//fUseSplines= fPreferenceStore.getBoolean(ComparePreferencePage.USE_SPLINES);
			fUseSingleLine = fPreferenceStore.getBoolean(ComparePreferencePage.USE_SINGLE_LINE);
			//fUseResolveUI= fPreferenceStore.getBoolean(ComparePreferencePage.USE_RESOLVE_UI);
		}

		fDocumentListener = new IDocumentListener() {

			public void documentAboutToBeChanged(DocumentEvent e) {
				// nothing to do
			}

			public void documentChanged(DocumentEvent e) {
				PhpMergeViewer.this.documentChanged(e);
			}
		};

		buildControl(parent);

		INavigatable nav = new INavigatable() {
			public boolean gotoDifference(boolean next) {
				return navigate(next, false, false);
			}
		};
		fComposite.setData(INavigatable.NAVIGATOR_PROPERTY, nav);

		fBirdsEyeCursor = new Cursor(parent.getDisplay(), SWT.CURSOR_HAND);

		JFaceResources.getFontRegistry().addListener(fPreferenceChangeListener);
		JFaceResources.getColorRegistry().addListener(fPreferenceChangeListener);
		updateFont();
	}

	private void updateFont() {
		Font f = JFaceResources.getFont(fSymbolicFontName);
		if (f != null) {
			if (fAncestor != null)
				fAncestor.setFont(f);
			if (fLeft != null)
				fLeft.setFont(f);
			if (fRight != null)
				fRight.setFont(f);
		}
	}

	private void checkForColorUpdate(Display display) {
		if (fPollSystemForeground) {
			RGB fg = display.getSystemColor(SWT.COLOR_LIST_FOREGROUND).getRGB();
			if (fForeground == null || !fg.equals(fForeground)) {
				fForeground = fg;
				updateColors(display);
			}
		}
		if (fPollSystemBackground) {
			RGB bg = display.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
			if (fBackground == null || !bg.equals(fBackground)) {
				fBackground = bg;
				updateColors(display);
			}
		}
	}

	/**
	 * Sets the viewer's background color to the given RGB value.
	 * If the value is <code>null</code> the system's default background color is used.
	 * @param background the background color or <code>null</code> to use the system's default background color
	 * @since 2.0
	 */
	public void setBackgroundColor(RGB background) {
		fPollSystemBackground = (background == null);
		fBackground = background;
		updateColors(null);
	}

	private RGB getBackground(Display display) {
		if (fBackground != null)
			return fBackground;
		if (display == null)
			display = fComposite.getDisplay();
		return display.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
	}

	/**
	 * Sets the viewer's foreground color to the given RGB value.
	 * If the value is <code>null</code> the system's default foreground color is used.
	 * @param foreground the foreground color or <code>null</code> to use the system's default foreground color
	 * @since 2.0
	 */
	public void setForegroundColor(RGB foreground) {
		fPollSystemForeground = (foreground == null);
		fForeground = foreground;
		updateColors(null);
	}

	private void updateColors(Display display) {

		if (display == null)
			display = fComposite.getDisplay();

		Color color = null;
		if (fBackground != null)
			color = getColor(display, fBackground);

		if (fAncestor != null)
			fAncestor.setBackgroundColor(color);
		if (fLeft != null)
			fLeft.setBackgroundColor(color);
		if (fRight != null)
			fRight.setBackgroundColor(color);

		ColorRegistry registry = JFaceResources.getColorRegistry();

		RGB bg = getBackground(display);
		SELECTED_INCOMING = registry.getRGB(INCOMING_COLOR);
		if (SELECTED_INCOMING == null)
			SELECTED_INCOMING = new RGB(0, 0, 255); // BLUE
		INCOMING = interpolate(SELECTED_INCOMING, bg, 0.6);
		INCOMING_FILL = interpolate(SELECTED_INCOMING, bg, 0.97);

		SELECTED_OUTGOING = registry.getRGB(OUTGOING_COLOR);
		if (SELECTED_OUTGOING == null)
			SELECTED_OUTGOING = new RGB(0, 0, 0); // BLACK
		OUTGOING = interpolate(SELECTED_OUTGOING, bg, 0.6);
		OUTGOING_FILL = interpolate(SELECTED_OUTGOING, bg, 0.97);

		SELECTED_CONFLICT = registry.getRGB(CONFLICTING_COLOR);
		if (SELECTED_CONFLICT == null)
			SELECTED_CONFLICT = new RGB(255, 0, 0); // RED
		CONFLICT = interpolate(SELECTED_CONFLICT, bg, 0.6);
		CONFLICT_FILL = interpolate(SELECTED_CONFLICT, bg, 0.97);

		RESOLVED = registry.getRGB(RESOLVED_COLOR);
		if (RESOLVED == null)
			RESOLVED = new RGB(0, 255, 0); // GREEN

		refreshBirdsEyeView();
		invalidateLines();

		updateAllDiffBackgrounds(display);
	}

	/**
	 * Invalidates the current presentation by invalidating the three text viewers.
	 * @since 2.0
	 */
	public void invalidateTextPresentation() {
		if (fAncestor != null)
			fAncestor.invalidateTextPresentation();
		if (fLeft != null)
			fLeft.invalidateTextPresentation();
		if (fRight != null)
			fRight.invalidateTextPresentation();
	}

	/**
	 * Configures the passed text viewer.
	 * This method is called after the three text viewers have been created for the
	 * content areas.
	 * The <code>TextMergeViewer</code> implementation of this method does nothing.
	 * Subclasses may reimplement to provide a specific configuration for the text viewer.
	 *
	 * @param textViewer the text viewer to configure
	 */
	protected void configureTextViewer(TextViewer textViewer) {
		// empty impl
	}

	/**
	 * Creates an <code>ITokenComparator</code> which is used to show the
	 * intra line differences.
	 * The <code>TextMergeViewer</code> implementation of this method returns a 
	 * tokenizer that breaks a line into words separated by whitespace.
	 * Subclasses may reimplement to provide a specific tokenizer.
	 * @param line the line for which to create the <code>ITokenComparator</code>
	 * @return a ITokenComparator which is used for a second level token compare.
	 */
	protected ITokenComparator createTokenComparator(String line) {
		return new TokenComparator(line);
	}

	/**
	 * Returns a document partitioner which is suitable for the underlying content type.
	 * This method is only called if the input provided by the content provider is a
	 * <code>IStreamContentAccessor</code> and an internal document must be created. This
	 * document is initialized with the partitioner returned from this method.
	 * <p>
	 * The <code>TextMergeViewer</code> implementation of this method returns 
	 * <code>null</code>. Subclasses may reimplement to create a partitioner for a 
	 * specific content type.
	 *
	 * @return a document partitioner, or <code>null</code>
	 */
	protected IDocumentPartitioner getDocumentPartitioner() {
		return new PHPStructuredTextPartitioner();
	}

	/**
	 * Called on the viewer disposal.
	 * Unregisters from the compare configuration.
	 * Clients may extend if they have to do additional cleanup.
	 * @param event
	 */
	protected void handleDispose(DisposeEvent event) {

		if (fKeyBindingService != null) {
			IAction a;
			if (fNextItem != null) {
				a = fNextItem.getAction();
				if (a != null)
					fKeyBindingService.unregisterAction(a);
			}
			if (fPreviousItem != null) {
				a = fPreviousItem.getAction();
				if (a != null)
					fKeyBindingService.unregisterAction(a);
			}
			if (fCopyDiffLeftToRightItem != null) {
				a = fCopyDiffLeftToRightItem.getAction();
				if (a != null)
					fKeyBindingService.unregisterAction(a);
			}
			if (fCopyDiffRightToLeftItem != null) {
				a = fCopyDiffRightToLeftItem.getAction();
				if (a != null)
					fKeyBindingService.unregisterAction(a);
			}
			fKeyBindingService = null;
		}

		Object input = getInput();
		DocumentManager.remove(getDocument2('A', input));
		DocumentManager.remove(getDocument2('L', input));
		DocumentManager.remove(getDocument2('R', input));

		if (DEBUG)
			DocumentManager.dump();

		if (fPreferenceChangeListener != null) {
			JFaceResources.getFontRegistry().removeListener(fPreferenceChangeListener);
			JFaceResources.getColorRegistry().removeListener(fPreferenceChangeListener);
			if (fPreferenceStore != null)
				fPreferenceStore.removePropertyChangeListener(fPreferenceChangeListener);
			fPreferenceChangeListener = null;
		}

		fLeftCanvas = null;
		fRightCanvas = null;
		fVScrollBar = null;
		fBirdsEyeCanvas = null;
		fSummaryHeader = null;

		unsetDocument(fAncestor);
		unsetDocument(fLeft);
		unsetDocument(fRight);

		if (fColors != null) {
			Iterator i = fColors.values().iterator();
			while (i.hasNext()) {
				Color color = (Color) i.next();
				if (!color.isDisposed())
					color.dispose();
			}
			fColors = null;
		}

		if (fBirdsEyeCursor != null) {
			fBirdsEyeCursor.dispose();
			fBirdsEyeCursor = null;
		}

		super.handleDispose(event);
	}

	//-------------------------------------------------------------------------------------------------------------
	//--- internal ------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------

	/*
	 * Creates the specific SWT controls for the content areas.
	 * Clients must not call or override this method.
	 */
	protected void createControls(Composite composite) {

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, ICompareContextIds.TEXT_MERGE_VIEW);

		// 1st row
		if (fMarginWidth > 0) {
			fAncestorCanvas = new BufferedCanvas(composite, SWT.NONE) {
				public void doPaint(GC gc) {
					paintSides(gc, fAncestor, fAncestorCanvas, false);
				}
			};
			fAncestorCanvas.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					setCurrentDiff2(handleMouseInSides(fAncestorCanvas, fAncestor, e.y), false);
				}
			});
		}

		fAncestor = createPart(composite);
		fAncestor.setEditable(false);

		fSummaryHeader = new Canvas(composite, SWT.NONE);
		fHeaderPainter = new HeaderPainter();
		fSummaryHeader.addPaintListener(fHeaderPainter);
		updateResolveStatus();

		// 2nd row
		if (fMarginWidth > 0) {
			fLeftCanvas = new BufferedCanvas(composite, SWT.NONE) {
				public void doPaint(GC gc) {
					paintSides(gc, fLeft, fLeftCanvas, false);
				}
			};
			fLeftCanvas.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					setCurrentDiff2(handleMouseInSides(fLeftCanvas, fLeft, e.y), false);
				}
			});
		}

		fLeft = createPart(composite);
		fLeft.getTextWidget().getVerticalBar().setVisible(!fSynchronizedScrolling);
		fLeft.addAction(MergeSourceViewer.SAVE_ID, fLeftSaveAction);

		fRight = createPart(composite);
		fRight.getTextWidget().getVerticalBar().setVisible(!fSynchronizedScrolling);
		fRight.addAction(MergeSourceViewer.SAVE_ID, fRightSaveAction);

		hsynchViewport(fAncestor, fLeft, fRight);
		hsynchViewport(fLeft, fAncestor, fRight);
		hsynchViewport(fRight, fAncestor, fLeft);

		if (fMarginWidth > 0) {
			fRightCanvas = new BufferedCanvas(composite, SWT.NONE) {
				public void doPaint(GC gc) {
					paintSides(gc, fRight, fRightCanvas, fSynchronizedScrolling);
				}
			};
			fRightCanvas.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					setCurrentDiff2(handleMouseInSides(fRightCanvas, fRight, e.y), false);
				}
			});
		}

		fScrollCanvas = new Canvas(composite, SWT.V_SCROLL);
		Rectangle trim = fLeft.getTextWidget().computeTrim(0, 0, 0, 0);
		fTopInset = trim.y;

		fVScrollBar = fScrollCanvas.getVerticalBar();
		fVScrollBar.setIncrement(1);
		fVScrollBar.setVisible(true);
		fVScrollBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int vpos = ((ScrollBar) e.widget).getSelection();
				scrollVertical(vpos, vpos, vpos, null);
				workaround65205();
			}
		});

		fBirdsEyeCanvas = new BufferedCanvas(composite, SWT.NONE) {
			public void doPaint(GC gc) {
				paintBirdsEyeView(this, gc);
			}
		};
		fBirdsEyeCanvas.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				setCurrentDiff2(handlemouseInBirdsEyeView(fBirdsEyeCanvas, e.y), true);
			}
		});
		fBirdsEyeCanvas.addMouseMoveListener(new MouseMoveListener() {

			private Cursor fLastCursor;

			public void mouseMove(MouseEvent e) {
				Cursor cursor = null;
				Diff diff = handlemouseInBirdsEyeView(fBirdsEyeCanvas, e.y);
				if (diff != null && diff.fDirection != RangeDifference.NOCHANGE)
					cursor = fBirdsEyeCursor;
				if (fLastCursor != cursor) {
					fBirdsEyeCanvas.setCursor(cursor);
					fLastCursor = cursor;
				}
			}
		});
	}

	private void hsynchViewport(final TextViewer tv1, final TextViewer tv2, final TextViewer tv3) {
		final StyledText st1 = tv1.getTextWidget();
		final StyledText st2 = tv2.getTextWidget();
		final StyledText st3 = tv3.getTextWidget();
		final ScrollBar sb1 = st1.getHorizontalBar();
		sb1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (fSynchronizedScrolling) {
					int max = sb1.getMaximum() - sb1.getThumb();
					double v = 0.0;
					if (max > 0)
						v = (float) sb1.getSelection() / (float) max;
					if (st2.isVisible()) {
						ScrollBar sb2 = st2.getHorizontalBar();
						st2.setHorizontalPixel((int) ((sb2.getMaximum() - sb2.getThumb()) * v));
					}
					if (st3.isVisible()) {
						ScrollBar sb3 = st3.getHorizontalBar();
						st3.setHorizontalPixel((int) ((sb3.getMaximum() - sb3.getThumb()) * v));
					}
					workaround65205();
				}
			}
		});
	}

	/**
	 * A workaround for bug #65205.
	 * On MacOS X a Display.update() is required to flush pending paint requests after
	 * programmatically scrolling. 
	 */
	private void workaround65205() {
		if (fIsCarbon && fComposite != null && !fComposite.isDisposed())
			fComposite.getDisplay().update();
	}

	private void setCurrentDiff2(Diff diff, boolean reveal) {
		if (diff != null && diff.fDirection != RangeDifference.NOCHANGE) {
			//fCurrentDiff= null;
			setCurrentDiff(diff, reveal);
		}
	}

	private Diff handleMouseInSides(Canvas canvas, MergeSourceViewer tp, int my) {

		int lineHeight = tp.getTextWidget().getLineHeight();
		int visibleHeight = tp.getViewportHeight();

		if (!fHighlightRanges)
			return null;

		if (fChangeDiffs != null) {
			int shift = tp.getVerticalScrollOffset() + (2 - LW);

			Point region = new Point(0, 0);
			Iterator e = fChangeDiffs.iterator();
			while (e.hasNext()) {
				Diff diff = (Diff) e.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				tp.getLineRange(diff.getPosition(tp), region);
				int y = (region.x * lineHeight) + shift;
				int h = region.y * lineHeight;

				if (y + h < 0)
					continue;
				if (y >= visibleHeight)
					break;

				if (my >= y && my < y + h)
					return diff;
			}
		}
		return null;
	}

	private Diff getDiffUnderMouse(Canvas canvas, int mx, int my, Rectangle r) {

		if (!fSynchronizedScrolling)
			return null;

		int lineHeight = fLeft.getTextWidget().getLineHeight();
		int visibleHeight = fRight.getViewportHeight();

		Point size = canvas.getSize();
		int w = size.x;

		if (!fHighlightRanges)
			return null;

		if (fChangeDiffs != null) {
			int lshift = fLeft.getVerticalScrollOffset();
			int rshift = fRight.getVerticalScrollOffset();

			Point region = new Point(0, 0);

			Iterator e = fChangeDiffs.iterator();
			while (e.hasNext()) {
				Diff diff = (Diff) e.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				fLeft.getLineRange(diff.fLeftPos, region);
				int ly = (region.x * lineHeight) + lshift;
				int lh = region.y * lineHeight;

				fRight.getLineRange(diff.fRightPos, region);
				int ry = (region.x * lineHeight) + rshift;
				int rh = region.y * lineHeight;

				if (Math.max(ly + lh, ry + rh) < 0)
					continue;
				if (Math.min(ly, ry) >= visibleHeight)
					break;

				int cx = (w - RESOLVE_SIZE) / 2;
				int cy = ((ly + lh / 2) + (ry + rh / 2) - RESOLVE_SIZE) / 2;
				if (my >= cy && my < cy + RESOLVE_SIZE && mx >= cx && mx < cx + RESOLVE_SIZE) {
					if (r != null) {
						int SIZE = fIsCarbon ? 30 : 20;
						r.x = cx + (RESOLVE_SIZE - SIZE) / 2;
						r.y = cy + (RESOLVE_SIZE - SIZE) / 2;
						r.width = SIZE;
						r.height = SIZE;
					}
					return diff;
				}
			}
		}
		return null;
	}

	private Diff handlemouseInBirdsEyeView(Canvas canvas, int my) {
		int yy, hh;

		Point size = canvas.getSize();

		int virtualHeight = fSynchronizedScrolling ? getVirtualHeight() : getRightHeight();
		if (virtualHeight < getViewportHeight())
			return null;

		int y = 0;
		if (fAllDiffs != null) {
			Iterator e = fAllDiffs.iterator();
			for (int i = 0; e.hasNext(); i++) {
				Diff diff = (Diff) e.next();
				int h = fSynchronizedScrolling ? diff.getMaxDiffHeight(fShowAncestor) : diff.getRightHeight();
				if (useChange(diff.fDirection) && !diff.fIsWhitespace) {

					yy = (y * size.y) / virtualHeight;
					hh = (h * size.y) / virtualHeight;
					if (hh < 3)
						hh = 3;

					if (my >= yy && my < yy + hh)
						return diff;
				}
				y += h;
			}
		}
		return null;
	}

	private void paintBirdsEyeView(Canvas canvas, GC gc) {

		Color c;
		Rectangle r = new Rectangle(0, 0, 0, 0);
		int yy, hh;

		Point size = canvas.getSize();

		int virtualHeight = fSynchronizedScrolling ? getVirtualHeight() : getRightHeight();
		if (virtualHeight < getViewportHeight())
			return;

		Display display = canvas.getDisplay();
		int y = 0;
		if (fAllDiffs != null) {
			Iterator e = fAllDiffs.iterator();
			for (int i = 0; e.hasNext(); i++) {
				Diff diff = (Diff) e.next();
				int h = fSynchronizedScrolling ? diff.getMaxDiffHeight(fShowAncestor) : diff.getRightHeight();

				if (useChange(diff.fDirection) && !diff.fIsWhitespace) {

					yy = (y * size.y) / virtualHeight;
					hh = (h * size.y) / virtualHeight;
					if (hh < 3)
						hh = 3;

					c = getColor(display, getFillColor(diff));
					if (c != null) {
						gc.setBackground(c);
						gc.fillRectangle(BIRDS_EYE_VIEW_INSET, yy, size.x - (2 * BIRDS_EYE_VIEW_INSET), hh);
					}
					c = getColor(display, getStrokeColor(diff));
					if (c != null) {
						gc.setForeground(c);
						r.x = BIRDS_EYE_VIEW_INSET;
						r.y = yy;
						r.width = size.x - (2 * BIRDS_EYE_VIEW_INSET) - 1;
						r.height = hh;
						if (diff == fCurrentDiff || (fCurrentDiff != null && diff == fCurrentDiff.fParent)) {
							gc.setLineWidth(2);
							r.x++;
							r.y++;
							r.width--;
							r.height--;
						} else {
							gc.setLineWidth(1);
						}
						gc.drawRectangle(r);
					}
				}

				y += h;
			}
		}
	}

	private void refreshBirdsEyeView() {
		if (fBirdsEyeCanvas != null)
			fBirdsEyeCanvas.redraw();
	}

	/*
	 * Called whenever setFocus() is called on the ContentViewer's top level SWT Composite.
	 * This implementation sets the focus to the first enabled text widget.
	 */
	/* package */boolean internalSetFocus() {
		if (fFocusPart == null) {
			if (fLeft != null && fLeft.getEnabled()) {
				fFocusPart = fLeft;
			} else if (fRight != null && fRight.getEnabled()) {
				fFocusPart = fRight;
			} else if (fAncestor != null && fAncestor.getEnabled()) {
				fFocusPart = fAncestor;
			}
		}
		if (fFocusPart != null) {
			StyledText st = fFocusPart.getTextWidget();
			if (st != null)
				return st.setFocus();
		}
		return false; // could not set focus
	}

	class HoverResizer extends Resizer {
		Canvas fCanvas;

		public HoverResizer(Canvas c, int dir) {
			super(c, dir);
			fCanvas = c;
		}

		public void mouseMove(MouseEvent e) {
			if (!fIsDown && fUseSingleLine && showResolveUI() && handleMouseMoveOverCenter(fCanvas, e.x, e.y))
				return;
			super.mouseMove(e);
		}
	}

	/*
	 * Creates the central Canvas.
	 * Called from ContentMergeViewer.
	 */
	/* package */Control createCenter(Composite parent) {
		if (fSynchronizedScrolling) {
			final Canvas canvas = new BufferedCanvas(parent, SWT.NONE) {
				public void doPaint(GC gc) {
					paintCenter(this, gc);
				}
			};
			if (fUseResolveUI) {

				new HoverResizer(canvas, HORIZONTAL);

				fCenterButton = new Button(canvas, fIsCarbon ? SWT.FLAT : SWT.PUSH);
				if (fNormalCursor == null)
					fNormalCursor = new Cursor(canvas.getDisplay(), SWT.CURSOR_ARROW);
				fCenterButton.setCursor(fNormalCursor);
				fCenterButton.setText("<"); //$NON-NLS-1$
				fCenterButton.pack();
				fCenterButton.setVisible(false);
				fCenterButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						fCenterButton.setVisible(false);
						if (fButtonDiff != null) {
							setCurrentDiff(fButtonDiff, false);
							copy(fCurrentDiff, false, fCurrentDiff.fDirection == RangeDifference.CONFLICT);
						}
					}
				});
			} else {
				new Resizer(canvas, HORIZONTAL);
			}

			return canvas;
		}
		return super.createCenter(parent);
	}

	private boolean handleMouseMoveOverCenter(Canvas canvas, int x, int y) {
		Rectangle r = new Rectangle(0, 0, 0, 0);
		Diff diff = getDiffUnderMouse(canvas, x, y, r);
		if (diff != null && !diff.isUnresolvedIncomingOrConflicting())
			diff = null;
		if (diff != fButtonDiff) {
			if (diff != null) {
				if (fLeft.isEditable()) {
					fButtonDiff = diff;
					fCenterButton.setText("<"); //$NON-NLS-1$
					String tt = fCopyDiffRightToLeftItem.getAction().getToolTipText();
					fCenterButton.setToolTipText(tt);
					fCenterButton.setBounds(r);
					fCenterButton.setVisible(true);
				} else if (fRight.isEditable()) {
					fButtonDiff = diff;
					fCenterButton.setText(">"); //$NON-NLS-1$
					String tt = fCopyDiffLeftToRightItem.getAction().getToolTipText();
					fCenterButton.setToolTipText(tt);
					fCenterButton.setBounds(r);
					fCenterButton.setVisible(true);
				} else
					fButtonDiff = null;
			} else {
				fCenterButton.setVisible(false);
				fButtonDiff = null;
			}
		}
		return fButtonDiff != null;
	}

	/*
	 * Returns width of central canvas.
	 * Overridden from ContentMergeViewer.
	 */
	/* package */int getCenterWidth() {
		if (fSynchronizedScrolling)
			return CENTER_WIDTH;
		return super.getCenterWidth();
	}

	private int getDirection() {
		switch (fTextDirection) {
			case SWT.LEFT_TO_RIGHT:
			case SWT.RIGHT_TO_LEFT:
				if (fInheritedDirection == fTextDirection)
					return SWT.NONE;
				return fTextDirection;
		}
		return fInheritedDirection;
	}

	/*
	 * Creates and initializes a text part.
	 */
	private MergeSourceViewer createPart(Composite parent) {

		final MergeSourceViewer part = new MergeSourceViewer(parent, getDirection(), getResourceBundle());
		final StyledText te = part.getTextWidget();

		if (!fConfirmSave)
			part.hideSaveAction();

		te.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				paint(e, part);
			}
		});
		te.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				handleSelectionChanged(part);
			}
		});
		te.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				//syncViewport(part);
				handleSelectionChanged(part);
			}
		});

		te.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fe) {
				fFocusPart = part;
				connectGlobalActions(fFocusPart);
			}

			public void focusLost(FocusEvent fe) {
				connectGlobalActions(null);
			}
		});

		part.addViewportListener(new IViewportListener() {
			public void viewportChanged(int verticalPosition) {
				syncViewport(part);
			}
		});

		Font font = JFaceResources.getFont(fSymbolicFontName);
		if (font != null)
			te.setFont(font);

		if (fBackground != null) // not default
			te.setBackground(getColor(parent.getDisplay(), fBackground));

		configureTextViewer(part);

		return part;
	}

	private void connectGlobalActions(MergeSourceViewer part) {
		IActionBars actionBars = Utilities.findActionBars(fComposite);
		if (actionBars != null) {
			for (int i = 0; i < GLOBAL_ACTIONS.length; i++) {
				IAction action = null;
				if (part != null) {
					action = part.getAction(TEXT_ACTIONS[i]);
					if (action == null && TEXT_ACTIONS[i].equals(MergeSourceViewer.SAVE_ID)) {
						if (part == fLeft)
							action = fLeftSaveAction;
						else
							action = fRightSaveAction;
					}
				}
				actionBars.setGlobalActionHandler(GLOBAL_ACTIONS[i], action);
			}
			actionBars.updateActionBars();
		}
	}

	ITypedElement getLeg(char type, Object input) {
		if (input instanceof ICompareInput) {
			switch (type) {
				case 'A':
					return ((ICompareInput) input).getAncestor();
				case 'L':
					return ((ICompareInput) input).getLeft();
				case 'R':
					return ((ICompareInput) input).getRight();
			}
		}
		return null;
	}

	IDocument getDocument(char type, Object input) {
		ITypedElement te = getLeg(type, input);
		if (te instanceof IDocument)
			return (IDocument) te;
		if (te instanceof IDocumentRange)
			return ((IDocumentRange) te).getDocument();
		if (te instanceof IStreamContentAccessor)
			return DocumentManager.get(te);
		return null;
	}

	IDocument getDocument2(char type, Object input) {
		IDocument doc = getDocument(type, input);
		if (doc != null)
			return doc;

		if (input instanceof IDiffElement) {
			IDiffContainer parent = ((IDiffElement) input).getParent();
			return getDocument(type, parent);
		}
		return null;
	}

	/*
	 * Returns true if the given inputs map to the same documents
	 */
	boolean sameDoc(char type, Object newInput, Object oldInput) {
		IDocument newDoc = getDocument2(type, newInput);
		IDocument oldDoc = getDocument2(type, oldInput);
		return newDoc == oldDoc;
	}

	/**
	 * Overridden to prevent save confirmation if new input is sub document of current input.
	 * @param newInput the new input of this viewer, or <code>null</code> if there is no new input
	 * @param oldInput the old input element, or <code>null</code> if there was previously no input
	 * @return <code>true</code> if saving was successful, or if the user didn't want to save (by pressing 'NO' in the confirmation dialog).
	 * @since 2.0
	 */
	protected boolean doSave(Object newInput, Object oldInput) {

		if (oldInput != null && newInput != null) {
			// check whether underlying documents have changed.
			if (sameDoc('A', newInput, oldInput) && sameDoc('L', newInput, oldInput) && sameDoc('R', newInput, oldInput)) {
				if (DEBUG)
					System.out.println("----- Same docs !!!!"); //$NON-NLS-1$
				return false;
			}
		}

		if (DEBUG)
			System.out.println("***** New docs !!!!"); //$NON-NLS-1$

		IDocument aDoc = getDocument2('A', oldInput);
		DocumentManager.remove(aDoc);
		IDocument lDoc = getDocument2('L', oldInput);
		DocumentManager.remove(lDoc);
		IDocument rDoc = getDocument2('R', oldInput);
		DocumentManager.remove(rDoc);

		if (DEBUG)
			DocumentManager.dump();

		return super.doSave(newInput, oldInput);
	}

	private ITypedElement getParent(char type) {
		Object input = getInput();
		if (input instanceof IDiffElement) {
			IDiffContainer parent = ((IDiffElement) input).getParent();
			return getLeg(type, parent);
		}
		return null;
	}

	/*
	 * Initializes the text viewers of the three content areas with the given input objects.
	 * Subclasses may extend.
	 */
	protected void updateContent(Object ancestor, Object left, Object right) {

		boolean emptyInput = (ancestor == null && left == null && right == null);

		Object input = getInput();

		Position leftRange = null;
		Position rightRange = null;

		// if one side is empty use container
		if (FIX_47640 && !emptyInput && (left == null || right == null)) {
			if (input instanceof IDiffElement) {
				IDiffContainer parent = ((IDiffElement) input).getParent();
				if (parent instanceof ICompareInput) {
					ICompareInput ci = (ICompareInput) parent;

					if (ci.getAncestor() instanceof IDocumentRange || ci.getLeft() instanceof IDocumentRange || ci.getRight() instanceof IDocumentRange) {

						if (left instanceof IDocumentRange)
							leftRange = ((IDocumentRange) left).getRange();
						if (right instanceof IDocumentRange)
							rightRange = ((IDocumentRange) right).getRange();

						ancestor = ci.getAncestor();
						left = ci.getLeft();
						right = ci.getRight();
					}
				}
			}
		}

		int n = 0;
		if (left != null)
			n++;
		if (right != null)
			n++;
		fHighlightRanges = n > 1;

		// clear stuff
		fCurrentDiff = null;
		fChangeDiffs = null;
		fAllDiffs = null;
		fEndOfDocReached = false;
		fHasErrors = false; // start with no errors

		CompareConfiguration cc = getCompareConfiguration();
		IMergeViewerContentProvider cp = getMergeContentProvider();

		if (cp instanceof MergeViewerContentProvider) {
			MergeViewerContentProvider mcp = (MergeViewerContentProvider) cp;
			mcp.setAncestorError(null);
			mcp.setLeftError(null);
			mcp.setRightError(null);
		}

		// Get encodings from streams. If an encoding is null, abide by the other one
		// Defaults to workbench encoding only if both encodings are null
		fLeftEncoding = getEncoding(left);
		fRightEncoding = getEncoding(right);
		if (fLeftEncoding == null && fRightEncoding == null) {
			fLeftEncoding = fRightEncoding = ResourcesPlugin.getEncoding();
		} else if (fLeftEncoding == null) {
			fLeftEncoding = fRightEncoding;
		} else if (fRightEncoding == null) {
			fRightEncoding = fLeftEncoding;
		}

		// set new documents
		setDocument(fLeft, 'L', left, fLeftEncoding);
		fLeftLineCount = fLeft.getLineCount();

		setDocument(fRight, 'R', right, fRightEncoding);
		fRightLineCount = fRight.getLineCount();

		setDocument(fAncestor, 'A', ancestor, fLeftEncoding);

		updateHeader();
		updateControls();
		updateToolItems();

		if (!fHasErrors)
			doDiff();

		fRight.setEditable(cc.isRightEditable() && cp.isRightEditable(input));
		fLeft.setEditable(cc.isLeftEditable() && cp.isLeftEditable(input));

		invalidateLines();
		updateVScrollBar();
		refreshBirdsEyeView();

		if (!fHasErrors && !emptyInput && !fComposite.isDisposed()) {
			Diff selectDiff = null;
			if (FIX_47640) {
				if (leftRange != null)
					selectDiff = findDiff('L', leftRange);
				else if (rightRange != null)
					selectDiff = findDiff('R', rightRange);
			}
			if (selectDiff != null)
				setCurrentDiff(selectDiff, true);
			else
				selectFirstDiff();
		}
	}

	private Diff findDiff(char c, Position range) {

		MergeSourceViewer v;
		int start = range.getOffset();
		int end = start + range.getLength();
		if (c == 'L')
			v = fLeft;
		else if (c == 'R')
			v = fRight;
		else
			return null;

		if (fChangeDiffs != null) {
			Iterator iter = fChangeDiffs.iterator();
			while (iter.hasNext()) {
				Diff diff = (Diff) iter.next();
				if (diff.isDeleted() || diff.fDirection == RangeDifference.NOCHANGE)
					continue;
				if (diff.overlaps(v, start, end))
					return diff;
			}
		}
		return null;
	}

	private static String getEncoding(Object o) {
		if (o instanceof IEncodedStreamContentAccessor) {
			try {
				return ((IEncodedStreamContentAccessor) o).getCharset();
			} catch (CoreException e) {
				// silently ignored
			}
		}
		return null;
	}

	private void updateDiffBackground(Diff diff) {

		if (!fHighlightRanges)
			return;

		if (diff == null || diff.fIsToken)
			return;

		if (fShowCurrentOnly && !isCurrentDiff(diff))
			return;

		Color c = getColor(null, getFillColor(diff));
		if (c == null)
			return;

		if (isThreeWay())
			fAncestor.setLineBackground(diff.fAncestorPos, c);
		fLeft.setLineBackground(diff.fLeftPos, c);
		fRight.setLineBackground(diff.fRightPos, c);
	}

	private void updateAllDiffBackgrounds(Display display) {
		if (fChangeDiffs != null) {
			boolean threeWay = isThreeWay();
			Iterator iter = fChangeDiffs.iterator();
			while (iter.hasNext()) {
				Diff diff = (Diff) iter.next();
				Color c = getColor(display, getFillColor(diff));
				if (threeWay)
					fAncestor.setLineBackground(diff.fAncestorPos, c);
				fLeft.setLineBackground(diff.fLeftPos, c);
				fRight.setLineBackground(diff.fRightPos, c);
			}
		}
	}

	boolean isCurrentDiff(Diff diff) {
		if (diff == null)
			return false;
		if (diff == fCurrentDiff)
			return true;
		if (fCurrentDiff != null && fCurrentDiff.fParent == diff)
			return true;
		return false;
	}

	/*
	 * Called whenever one of the documents changes.
	 * Sets the dirty state of this viewer and updates the lines.
	 * Implements IDocumentListener.
	 */
	private void documentChanged(DocumentEvent e) {

		IDocument doc = e.getDocument();

		if (doc == fLeft.getDocument()) {
			setLeftDirty(true);
		} else if (doc == fRight.getDocument()) {
			setRightDirty(true);
		}

		updateLines(doc);
	}

	/*
	 * This method is called if a range of text on one side is copied into an empty sub-document
	 * on the other side. The method returns the position where the sub-document is placed into the base document.
	 * This default implementation determines the position by using the text range differencer.
	 * However this position is not always optimal for specific types of text.
	 * So subclasses (which are aware of the type of text they are dealing with) 
	 * may override this method to find a better position where to insert a newly added
	 * piece of text.
	 * @param type the side for which the insertion position should be determined: 'A' for ancestor, 'L' for left hand side, 'R' for right hand side.
	 * @param input the current input object of this viewer
	 * @since 2.0
	 */
	protected int findInsertionPosition(char type, ICompareInput input) {

		ITypedElement other = null;
		char otherType = 0;

		switch (type) {
			case 'A':
				other = input.getLeft();
				otherType = 'L';
				if (other == null) {
					other = input.getRight();
					otherType = 'R';
				}
				break;
			case 'L':
				other = input.getRight();
				otherType = 'R';
				if (other == null) {
					other = input.getAncestor();
					otherType = 'A';
				}
				break;
			case 'R':
				other = input.getLeft();
				otherType = 'L';
				if (other == null) {
					other = input.getAncestor();
					otherType = 'A';
				}
				break;
		}

		if (other instanceof IDocumentRange) {
			IDocumentRange dr = (IDocumentRange) other;
			Position p = dr.getRange();
			Diff diff = findDiff(otherType, p.offset);
			if (diff != null) {
				switch (type) {
					case 'A':
						if (diff.fAncestorPos != null)
							return diff.fAncestorPos.offset;
						break;
					case 'L':
						if (diff.fLeftPos != null)
							return diff.fLeftPos.offset;
						break;
					case 'R':
						if (diff.fRightPos != null)
							return diff.fRightPos.offset;
						break;
				}
			}
		}
		return 0;
	}

	private void setError(char type, String message) {
		IMergeViewerContentProvider cp = getMergeContentProvider();
		if (cp instanceof MergeViewerContentProvider) {
			MergeViewerContentProvider mcp = (MergeViewerContentProvider) cp;
			switch (type) {
				case 'A':
					mcp.setAncestorError(message);
					break;
				case 'L':
					mcp.setLeftError(message);
					break;
				case 'R':
					mcp.setRightError(message);
					break;
			}
		}
		fHasErrors = true;
	}

	/*
	 * Returns true if a new Document could be installed.
	 */
	private boolean setDocument(MergeSourceViewer tp, char type, Object o, String encoding) {

		if (tp == null)
			return false;

		IDocument newDoc = null;
		Position range = null;

		if (o instanceof IDocumentRange) {
			newDoc = ((IDocumentRange) o).getDocument();
			range = ((IDocumentRange) o).getRange();

		} else if (o instanceof IDocument) {
			newDoc = (IDocument) o;

		} else if (o instanceof IStreamContentAccessor) {

			newDoc = DocumentManager.get(o);
			if (newDoc == null) {
				IStreamContentAccessor sca = (IStreamContentAccessor) o;
				String s = null;
				if (encoding == null)
					encoding = ResourcesPlugin.getEncoding();

				try {
					s = Utilities.readString(sca.getContents(), encoding);
				} catch (CoreException ex) {
					setError(type, ex.getMessage());
				}

				// addition to the document
				final PhpSourceParser phpSourceParser = new PhpSourceParser();
				newDoc = StructuredDocumentFactory.getNewStructuredDocumentInstance(phpSourceParser);
				newDoc.set(s);
				// newDoc = new  Document(s != null ? s : ""); //$NON-NLS-1$
				// end of addition
				
				DocumentManager.put(o, newDoc);
				IDocumentPartitioner partitioner = getDocumentPartitioner();
				if (partitioner != null) {
					newDoc.setDocumentPartitioner(partitioner);
					partitioner.connect(newDoc);
				}
			}
		} else if (o == null) { // deletion on one side

			ITypedElement parent = getParent(type); // we try to find an insertion position within the deletion's parent

			if (parent instanceof IDocumentRange) {
				newDoc = ((IDocumentRange) parent).getDocument();
				newDoc.addPositionCategory(IDocumentRange.RANGE_CATEGORY);
				Object input = getInput();
				range = getNewRange(type, input);
				if (range == null) {
					int pos = 0;
					if (input instanceof ICompareInput)
						pos = findInsertionPosition(type, (ICompareInput) input);
					range = new Position(pos, 0);
					try {
						newDoc.addPosition(IDocumentRange.RANGE_CATEGORY, range);
					} catch (BadPositionCategoryException ex) {
						// silently ignored
						if (DEBUG)
							System.out.println("BadPositionCategoryException: " + ex); //$NON-NLS-1$
					} catch (BadLocationException ex) {
						// silently ignored
						if (DEBUG)
							System.out.println("BadLocationException: " + ex); //$NON-NLS-1$
					}
					addNewRange(type, input, range);
				}
			} else if (parent instanceof IDocument) {
				newDoc = ((IDocumentRange) o).getDocument();
			}
		}

		boolean enabled = true;
		if (newDoc == null) {
			//System.out.println("setDocument: create new Document");
			newDoc = new Document(""); //$NON-NLS-1$
			enabled = false;
		}

		IDocument oldDoc = tp.getDocument();

		if (newDoc != oldDoc) {

			// got a new document

			unsetDocument(tp);

			if (newDoc != null) {
				newDoc.addPositionCategory(IDocumentRange.RANGE_CATEGORY);
				if (fPositionUpdater == null)
					fPositionUpdater = new ChildPositionUpdater(IDocumentRange.RANGE_CATEGORY);
				else
					newDoc.removePositionUpdater(fPositionUpdater);
				newDoc.addPositionUpdater(fPositionUpdater);
			}

			// install new document
			if (newDoc != null) {

				tp.setRegion(range);
				if (fSubDoc) {
					if (range != null) {
						IRegion r = normalizeDocumentRegion(newDoc, toRegion(range));
						tp.setDocument(newDoc, r.getOffset(), r.getLength());
					} else
						tp.setDocument(newDoc);
				} else
					tp.setDocument(newDoc);

				tp.rememberDocument(newDoc);
				newDoc.addDocumentListener(fDocumentListener);
				//LeakTester.add(newDoc);
			}

		} else { // same document but different range

			tp.setRegion(range);
			if (fSubDoc) {
				if (range != null) {
					IRegion r = normalizeDocumentRegion(newDoc, toRegion(range));
					tp.setVisibleRegion(r.getOffset(), r.getLength());
				} else
					tp.resetVisibleRegion();
			} else
				tp.resetVisibleRegion();
		}

		tp.setEnabled(enabled);

		return enabled;
	}

	private Position getNewRange(char type, Object input) {
		switch (type) {
			case 'A':
				return (Position) fNewAncestorRanges.get(input);
			case 'L':
				return (Position) fNewLeftRanges.get(input);
			case 'R':
				return (Position) fNewRightRanges.get(input);
		}
		return null;
	}

	private void addNewRange(char type, Object input, Position range) {
		switch (type) {
			case 'A':
				fNewAncestorRanges.put(input, range);
				break;
			case 'L':
				fNewLeftRanges.put(input, range);
				break;
			case 'R':
				fNewRightRanges.put(input, range);
				break;
		}
	}

	private void unsetDocument(MergeSourceViewer tp) {
		IDocument oldDoc = tp.getDocument();
		if (oldDoc == null) {
			oldDoc = tp.getRememberedDocument();
			//		if (oldDoc != null)
			//			System.err.println("TextMergeViewer.unsetDocument: would leak");
		}
		if (oldDoc != null) {
			tp.rememberDocument(null);
			// de-install old positions
			if (fPositionUpdater != null)
				oldDoc.removePositionUpdater(fPositionUpdater);
			try {
				oldDoc.removePositionCategory(IDocumentRange.RANGE_CATEGORY);
			} catch (BadPositionCategoryException ex) {
				// NeedWork
			}

			oldDoc.removeDocumentListener(fDocumentListener);
			//LeakTester.remove(oldDoc);
		}
	}

	/**
	 * Returns the contents of the underlying document as an array of bytes using the current workbench encoding.
	 * 
	 * @param left if <code>true</code> the contents of the left side is returned; otherwise the right side
	 * @return the contents of the left or right document or null
	 */
	protected byte[] getContents(boolean left) {
		MergeSourceViewer v = left ? fLeft : fRight;
		if (v != null) {
			IDocument d = v.getDocument();
			if (d != null) {
				String contents = d.get();
				if (contents != null) {
					byte[] bytes;
					try {
						bytes = contents.getBytes(left ? fLeftEncoding : fRightEncoding);
					} catch (UnsupportedEncodingException ex) {
						// use default encoding
						bytes = contents.getBytes();
					}
					return bytes;
				}
			}
		}
		return null;
	}

	private IRegion normalizeDocumentRegion(IDocument doc, IRegion region) {

		if (region == null || doc == null)
			return region;

		int maxLength = doc.getLength();

		int start = region.getOffset();
		if (start < 0)
			start = 0;
		else if (start > maxLength)
			start = maxLength;

		int length = region.getLength();
		if (length < 0)
			length = 0;
		else if (start + length > maxLength)
			length = maxLength - start;

		return new Region(start, length);
	}

	protected final void handleResizeAncestor(int x, int y, int width, int height) {
		if (width > 0) {
			Rectangle trim = fLeft.getTextWidget().computeTrim(0, 0, 0, 0);
			int scrollbarHeight = trim.height;
			if (Utilities.okToUse(fAncestorCanvas))
				fAncestorCanvas.setVisible(true);
			if (fAncestor.isControlOkToUse())
				fAncestor.getTextWidget().setVisible(true);

			if (fAncestorCanvas != null) {
				fAncestorCanvas.setBounds(x, y, fMarginWidth, height - scrollbarHeight);
				x += fMarginWidth;
				width -= fMarginWidth;
			}
			fAncestor.getTextWidget().setBounds(x, y, width, height);
		} else {
			if (Utilities.okToUse(fAncestorCanvas))
				fAncestorCanvas.setVisible(false);
			if (fAncestor.isControlOkToUse()) {
				StyledText t = fAncestor.getTextWidget();
				t.setVisible(false);
				t.setBounds(0, 0, 0, 0);
				if (fFocusPart == fAncestor) {
					fFocusPart = fLeft;
					fFocusPart.getTextWidget().setFocus();
				}
			}
		}
	}

	/*
	 * Lays out everything.
	 */
	protected final void handleResizeLeftRight(int x, int y, int width1, int centerWidth, int width2, int height) {

		if (fBirdsEyeCanvas != null)
			width2 -= BIRDS_EYE_VIEW_WIDTH;

		Rectangle trim = fLeft.getTextWidget().computeTrim(0, 0, 0, 0);
		int scrollbarHeight = trim.height + trim.x;

		Composite composite = (Composite) getControl();

		int leftTextWidth = width1;
		if (fLeftCanvas != null) {
			fLeftCanvas.setBounds(x, y, fMarginWidth, height - scrollbarHeight);
			x += fMarginWidth;
			leftTextWidth -= fMarginWidth;
		}

		fLeft.getTextWidget().setBounds(x, y, leftTextWidth, height);
		x += leftTextWidth;

		if (fCenter == null || fCenter.isDisposed())
			fCenter = createCenter(composite);
		fCenter.setBounds(x, y, centerWidth, height - scrollbarHeight);
		x += centerWidth;

		if (!fSynchronizedScrolling) { // canvas is to the left of text
			if (fRightCanvas != null) {
				fRightCanvas.setBounds(x, y, fMarginWidth, height - scrollbarHeight);
				fRightCanvas.redraw();
				x += fMarginWidth;
			}
			// we draw the canvas to the left of the text widget
		}

		int scrollbarWidth = 0;
		if (fSynchronizedScrolling && fScrollCanvas != null) {
			trim = fLeft.getTextWidget().computeTrim(0, 0, 0, 0);
			scrollbarWidth = trim.width + 2 * trim.x;
		}
		int rightTextWidth = width2 - scrollbarWidth;
		if (fRightCanvas != null)
			rightTextWidth -= fMarginWidth;
		fRight.getTextWidget().setBounds(x, y, rightTextWidth, height);
		x += rightTextWidth;

		if (fSynchronizedScrolling) {
			if (fRightCanvas != null) { // canvas is to the right of the text
				fRightCanvas.setBounds(x, y, fMarginWidth, height - scrollbarHeight);
				x += fMarginWidth;
			}
			if (fScrollCanvas != null)
				fScrollCanvas.setBounds(x, y, scrollbarWidth, height - scrollbarHeight);
		}

		if (fBirdsEyeCanvas != null) {
			int verticalScrollbarButtonHeight = scrollbarWidth;
			int horizontalScrollbarButtonHeight = scrollbarHeight;
			if (fIsCarbon) {
				verticalScrollbarButtonHeight += 2;
				horizontalScrollbarButtonHeight = 18;
			}
			if (fSummaryHeader != null)
				fSummaryHeader.setBounds(x + scrollbarWidth, y, BIRDS_EYE_VIEW_WIDTH, verticalScrollbarButtonHeight);
			y += verticalScrollbarButtonHeight;
			fBirdsEyeCanvas.setBounds(x + scrollbarWidth, y, BIRDS_EYE_VIEW_WIDTH, height - (2 * verticalScrollbarButtonHeight + horizontalScrollbarButtonHeight));
		}

		// doesn't work since TextEditors don't have their correct size yet.
		updateVScrollBar();
		refreshBirdsEyeView();
	}

	/*
	 * Track selection changes to update the current Diff.
	 */
	private void handleSelectionChanged(MergeSourceViewer tw) {
		Point p = tw.getSelectedRange();
		Diff d = findDiff(tw, p.x, p.x + p.y);
		updateStatus(d);
		setCurrentDiff(d, false); // don't select or reveal
	}

	private static IRegion toRegion(Position position) {
		if (position != null)
			return new Region(position.getOffset(), position.getLength());
		return null;
	}

	//---- the differencing

	private static int maxWork(IRangeComparator a, IRangeComparator l, IRangeComparator r) {
		int ln = l.getRangeCount();
		int rn = r.getRangeCount();
		if (a != null) {
			int an = a.getRangeCount();
			return (2 * Math.max(an, ln)) + (2 * Math.max(an, rn));
		}
		return 2 * Math.max(ln, rn);
	}

	/**
	 * Perform a two level 2- or 3-way diff.
	 * The first level is based on line comparison, the second level on token comparison.
	 */
	private void doDiff() {

		fAllDiffs = new ArrayList();
		fChangeDiffs = new ArrayList();
		fCurrentDiff = null;

		IDocument aDoc = null;
		IDocument lDoc = fLeft.getDocument();
		IDocument rDoc = fRight.getDocument();
		if (lDoc == null || rDoc == null)
			return;

		Position aRegion = null;
		Position lRegion = fLeft.getRegion();
		Position rRegion = fRight.getRegion();

		boolean threeWay = isThreeWay();

		if (threeWay && !fIgnoreAncestor) {
			aDoc = fAncestor.getDocument();
			aRegion = fAncestor.getRegion();
		}

		fAncestor.resetLineBackground();
		fLeft.resetLineBackground();
		fRight.resetLineBackground();

		boolean ignoreWhiteSpace = Utilities.getBoolean(getCompareConfiguration(), CompareConfiguration.IGNORE_WHITESPACE, false);

		DocLineComparator sright = new DocLineComparator(rDoc, toRegion(rRegion), ignoreWhiteSpace);
		DocLineComparator sleft = new DocLineComparator(lDoc, toRegion(lRegion), ignoreWhiteSpace);
		DocLineComparator sancestor = null;
		if (aDoc != null)
			sancestor = new DocLineComparator(aDoc, toRegion(aRegion), ignoreWhiteSpace);

		if (!fSubDoc && rRegion != null && lRegion != null) {
			// we have to add a diff for the ignored lines

			int astart = 0;
			int as = 0;
			if (aRegion != null) {
				astart = aRegion.getOffset();
				as = Math.max(0, astart - 1);
			}
			int ys = Math.max(0, lRegion.getOffset() - 1);
			int ms = Math.max(0, rRegion.getOffset() - 1);

			if (as > 0 || ys > 0 || ms > 0) {
				Diff diff = new Diff(null, RangeDifference.NOCHANGE, aDoc, aRegion, 0, astart, lDoc, lRegion, 0, lRegion.getOffset(), rDoc, rRegion, 0, rRegion.getOffset());
				fAllDiffs.add(diff);
			}
		}

		final ResourceBundle bundle = getResourceBundle();

		final Object[] result = new Object[1];
		final DocLineComparator sa = sancestor, sl = sleft, sr = sright;
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
				String progressTitle = Utilities.getString(bundle, "compareProgressTask.title"); //$NON-NLS-1$
				monitor.beginTask(progressTitle, maxWork(sa, sl, sr));
				try {
					result[0] = RangeDifferencer.findRanges(monitor, sa, sl, sr);
				} catch (OutOfMemoryError ex) {
					System.gc();
					throw new InvocationTargetException(ex);
				}
				if (monitor.isCanceled()) { // canceled
					throw new InterruptedException();
				}
				monitor.done();
			}
		};
		IProgressService progressService = PlatformUI.getWorkbench().getProgressService();

		RangeDifference[] e = null;
		try {
			progressService.run(true, true, runnable);
			e = (RangeDifference[]) result[0];
		} catch (InvocationTargetException ex) {
			String title = Utilities.getString(bundle, "tooComplexError.title"); //$NON-NLS-1$
			String format = Utilities.getString(bundle, "tooComplexError.format"); //$NON-NLS-1$
			String msg = MessageFormat.format(format, new Object[] { Integer.toString(progressService.getLongOperationTime() / 1000) });
			MessageDialog.openError(fComposite.getShell(), title, msg);
			e = null;
		} catch (InterruptedException ex) {
			// 
		}

		if (e == null) {
			// we create a NOCHANGE range for the whole document
			Diff diff = new Diff(null, RangeDifference.NOCHANGE, aDoc, aRegion, 0, aDoc != null ? aDoc.getLength() : 0, lDoc, lRegion, 0, lDoc.getLength(), rDoc, rRegion, 0, rDoc.getLength());

			fAllDiffs.add(diff);
		} else {
			for (int i = 0; i < e.length; i++) {
				String a = null, s = null, d = null;
				RangeDifference es = e[i];

				int kind = es.kind();

				int ancestorStart = 0;
				int ancestorEnd = 0;
				if (sancestor != null) {
					ancestorStart = sancestor.getTokenStart(es.ancestorStart());
					ancestorEnd = getTokenEnd2(sancestor, es.ancestorStart(), es.ancestorLength());
				}

				int leftStart = sleft.getTokenStart(es.leftStart());
				int leftEnd = getTokenEnd2(sleft, es.leftStart(), es.leftLength());

				int rightStart = sright.getTokenStart(es.rightStart());
				int rightEnd = getTokenEnd2(sright, es.rightStart(), es.rightLength());

				Diff diff = new Diff(null, kind, aDoc, aRegion, ancestorStart, ancestorEnd, lDoc, lRegion, leftStart, leftEnd, rDoc, rRegion, rightStart, rightEnd);

				fAllDiffs.add(diff); // remember all range diffs for scrolling

				if (ignoreWhiteSpace) {
					if (sancestor != null)
						a = extract2(aDoc, sancestor, es.ancestorStart(), es.ancestorLength());
					s = extract2(lDoc, sleft, es.leftStart(), es.leftLength());
					d = extract2(rDoc, sright, es.rightStart(), es.rightLength());

					if ((a == null || a.trim().length() == 0) && s.trim().length() == 0 && d.trim().length() == 0) {
						diff.fIsWhitespace = true;
						continue;
					}
				}

				if (useChange(kind)) {
					fChangeDiffs.add(diff); // here we remember only the real diffs
					updateDiffBackground(diff);

					if (s == null)
						s = extract2(lDoc, sleft, es.leftStart(), es.leftLength());
					if (d == null)
						d = extract2(rDoc, sright, es.rightStart(), es.rightLength());

					if (s.length() > 0 && d.length() > 0) {
						if (a == null && sancestor != null)
							a = extract2(aDoc, sancestor, es.ancestorStart(), es.ancestorLength());
						if (USE_MERGING_TOKEN_DIFF)
							mergingTokenDiff(diff, aDoc, a, rDoc, d, lDoc, s);
						else
							simpleTokenDiff(diff, aDoc, a, rDoc, d, lDoc, s);
					}
				}
			}
		}

		if (!fSubDoc && rRegion != null && lRegion != null) {
			// we have to add a diff for the ignored lines

			int aEnd = 0;
			int aLen = 0;
			if (aRegion != null && aDoc != null) {
				aEnd = aRegion.getOffset() + aRegion.getLength();
				aLen = aDoc.getLength();
			}
			Diff diff = new Diff(null, RangeDifference.NOCHANGE, aDoc, aRegion, aEnd, aLen, lDoc, lRegion, lRegion.getOffset() + lRegion.getLength(), lDoc.getLength(), rDoc, rRegion, rRegion.getOffset() + rRegion.getLength(), rDoc.getLength());
			fAllDiffs.add(diff);
		}
	}

	private Diff findDiff(char type, int pos) {

		IDocument aDoc = null;
		IDocument lDoc = fLeft.getDocument();
		IDocument rDoc = fRight.getDocument();
		if (lDoc == null || rDoc == null)
			return null;

		Position aRegion = null;
		Position lRegion = null;
		Position rRegion = null;

		boolean threeWay = isThreeWay();

		if (threeWay && !fIgnoreAncestor)
			aDoc = fAncestor.getDocument();

		boolean ignoreWhiteSpace = Utilities.getBoolean(getCompareConfiguration(), CompareConfiguration.IGNORE_WHITESPACE, false);

		DocLineComparator sright = new DocLineComparator(rDoc, toRegion(rRegion), ignoreWhiteSpace);
		DocLineComparator sleft = new DocLineComparator(lDoc, toRegion(lRegion), ignoreWhiteSpace);
		DocLineComparator sancestor = null;
		if (aDoc != null)
			sancestor = new DocLineComparator(aDoc, toRegion(aRegion), ignoreWhiteSpace);

		final ResourceBundle bundle = getResourceBundle();

		final Object[] result = new Object[1];
		final DocLineComparator sa = sancestor, sl = sleft, sr = sright;
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
				String progressTitle = Utilities.getString(bundle, "compareProgressTask.title"); //$NON-NLS-1$
				monitor.beginTask(progressTitle, maxWork(sa, sl, sr));
				try {
					result[0] = RangeDifferencer.findRanges(monitor, sa, sl, sr);
				} catch (OutOfMemoryError ex) {
					System.gc();
					throw new InvocationTargetException(ex);
				}
				if (monitor.isCanceled()) { // canceled
					throw new InterruptedException();
				}
				monitor.done();
			}
		};
		IProgressService progressService = PlatformUI.getWorkbench().getProgressService();

		RangeDifference[] e = null;
		try {
			progressService.run(true, true, runnable);
			e = (RangeDifference[]) result[0];
		} catch (InvocationTargetException ex) {
			String title = Utilities.getString(bundle, "tooComplexError.title"); //$NON-NLS-1$
			String format = Utilities.getString(bundle, "tooComplexError.format"); //$NON-NLS-1$
			String msg = MessageFormat.format(format, new Object[] { Integer.toString(progressService.getLongOperationTime() / 1000) });
			MessageDialog.openError(fComposite.getShell(), title, msg);
			e = null;
		} catch (InterruptedException ex) {
			// 
		}

		if (e != null) {
			for (int i = 0; i < e.length; i++) {
				RangeDifference es = e[i];

				int kind = es.kind();

				int ancestorStart = 0;
				int ancestorEnd = 0;
				if (sancestor != null) {
					ancestorStart = sancestor.getTokenStart(es.ancestorStart());
					ancestorEnd = getTokenEnd2(sancestor, es.ancestorStart(), es.ancestorLength());
				}

				int leftStart = sleft.getTokenStart(es.leftStart());
				int leftEnd = getTokenEnd2(sleft, es.leftStart(), es.leftLength());

				int rightStart = sright.getTokenStart(es.rightStart());
				int rightEnd = getTokenEnd2(sright, es.rightStart(), es.rightLength());

				Diff diff = new Diff(null, kind, aDoc, aRegion, ancestorStart, ancestorEnd, lDoc, lRegion, leftStart, leftEnd, rDoc, rRegion, rightStart, rightEnd);

				if (diff.isInRange(type, pos))
					return diff;
			}
		}

		return null;
	}

	/*
	 * Returns true if kind of change should be shown.
	 */
	private boolean useChange(int kind) {
		if (kind == RangeDifference.NOCHANGE)
			return false;
		if (kind == RangeDifference.ANCESTOR)
			return fShowPseudoConflicts;
		return true;
	}

	private int getTokenEnd(ITokenComparator tc, int start, int count) {
		if (count <= 0)
			return tc.getTokenStart(start);
		int index = start + count - 1;
		return tc.getTokenStart(index) + tc.getTokenLength(index);
	}

	private static int getTokenEnd2(ITokenComparator tc, int start, int length) {
		return tc.getTokenStart(start + length);
	}

	/*
	 * Returns the content of lines in the specified range as a String.
	 * This includes the line separators.
	 *
	 * @param doc the document from which to extract the characters
	 * @param start index of first line
	 * @param length number of lines
	 * @return the contents of the specified line range as a String
	 */
	private String extract2(IDocument doc, ITokenComparator tc, int start, int length) {
		int count = tc.getRangeCount();
		if (length > 0 && count > 0) {

			//		
			//		int startPos= tc.getTokenStart(start);
			//		int endPos= startPos;
			//		
			//		if (length > 1)
			//			endPos= tc.getTokenStart(start + (length-1));
			//		endPos+= tc.getTokenLength(start + (length-1));
			//			

			int startPos = tc.getTokenStart(start);
			int endPos;

			if (length == 1) {
				endPos = startPos + tc.getTokenLength(start);
			} else {
				endPos = tc.getTokenStart(start + length);
			}

			try {
				return doc.get(startPos, endPos - startPos);
			} catch (BadLocationException e) {
				// silently ignored
			}

		}
		return ""; //$NON-NLS-1$
	}

	/*
	 * Performs a token based 3-way diff on the character range specified by the given baseDiff.
	 */
	private void simpleTokenDiff(final Diff baseDiff, IDocument ancestorDoc, String a, IDocument rightDoc, String d, IDocument leftDoc, String s) {

		int ancestorStart = 0;
		ITokenComparator sa = null;
		if (ancestorDoc != null) {
			ancestorStart = baseDiff.fAncestorPos.getOffset();
			sa = createTokenComparator(a);
		}

		int rightStart = baseDiff.fRightPos.getOffset();
		ITokenComparator sm = createTokenComparator(d);

		int leftStart = baseDiff.fLeftPos.getOffset();
		ITokenComparator sy = createTokenComparator(s);

		RangeDifference[] e = RangeDifferencer.findRanges(sa, sy, sm);
		for (int i = 0; i < e.length; i++) {
			RangeDifference es = e[i];
			int kind = es.kind();
			if (kind != RangeDifference.NOCHANGE) {

				int ancestorStart2 = ancestorStart;
				int ancestorEnd2 = ancestorStart;
				if (ancestorDoc != null) {
					ancestorStart2 += sa.getTokenStart(es.ancestorStart());
					ancestorEnd2 += getTokenEnd(sa, es.ancestorStart(), es.ancestorLength());
				}

				int leftStart2 = leftStart + sy.getTokenStart(es.leftStart());
				int leftEnd2 = leftStart + getTokenEnd(sy, es.leftStart(), es.leftLength());

				int rightStart2 = rightStart + sm.getTokenStart(es.rightStart());
				int rightEnd2 = rightStart + getTokenEnd(sm, es.rightStart(), es.rightLength());

				Diff diff = new Diff(baseDiff, kind, ancestorDoc, null, ancestorStart2, ancestorEnd2, leftDoc, null, leftStart2, leftEnd2, rightDoc, null, rightStart2, rightEnd2);

				// ensure that token diff is smaller than basediff
				int leftS = baseDiff.fLeftPos.offset;
				int leftE = baseDiff.fLeftPos.offset + baseDiff.fLeftPos.length;
				int rightS = baseDiff.fRightPos.offset;
				int rightE = baseDiff.fRightPos.offset + baseDiff.fRightPos.length;
				if (leftS != leftStart2 || leftE != leftEnd2 || rightS != rightStart2 || rightE != rightEnd2) {
					diff.fIsToken = true;
					// add to base Diff
					baseDiff.add(diff);
				}
			}
		}
	}

	/*
	 * Performs a "smart" token based 3-way diff on the character range specified by the given baseDiff.
	 * It is "smart" because it tries to minimize the number of token diffs by merging them.
	 */
	private void mergingTokenDiff(Diff baseDiff, IDocument ancestorDoc, String a, IDocument rightDoc, String d, IDocument leftDoc, String s) {
		ITokenComparator sa = null;
		int ancestorStart = 0;
		if (ancestorDoc != null) {
			sa = createTokenComparator(a);
			ancestorStart = baseDiff.fAncestorPos.getOffset();
		}

		int rightStart = baseDiff.fRightPos.getOffset();
		ITokenComparator sm = createTokenComparator(d);

		int leftStart = baseDiff.fLeftPos.getOffset();
		ITokenComparator sy = createTokenComparator(s);

		RangeDifference[] r = RangeDifferencer.findRanges(sa, sy, sm);
		for (int i = 0; i < r.length; i++) {
			RangeDifference es = r[i];
			// determine range of diffs in one line
			int start = i;
			int leftLine = -1;
			int rightLine = -1;
			try {
				leftLine = leftDoc.getLineOfOffset(leftStart + sy.getTokenStart(es.leftStart()));
				rightLine = rightDoc.getLineOfOffset(rightStart + sm.getTokenStart(es.rightStart()));
			} catch (BadLocationException e) {
				// silently ignored
			}
			i++;
			for (; i < r.length; i++) {
				es = r[i];
				try {
					if (leftLine != leftDoc.getLineOfOffset(leftStart + sy.getTokenStart(es.leftStart())))
						break;
					if (rightLine != rightDoc.getLineOfOffset(rightStart + sm.getTokenStart(es.rightStart())))
						break;
				} catch (BadLocationException e) {
					// silently ignored
				}
			}
			int end = i;

			// find first diff from left
			RangeDifference first = null;
			for (int ii = start; ii < end; ii++) {
				es = r[ii];
				if (useChange(es.kind())) {
					first = es;
					break;
				}
			}

			// find first diff from mine
			RangeDifference last = null;
			for (int ii = end - 1; ii >= start; ii--) {
				es = r[ii];
				if (useChange(es.kind())) {
					last = es;
					break;
				}
			}

			if (first != null && last != null) {

				int ancestorStart2 = 0;
				int ancestorEnd2 = 0;
				if (ancestorDoc != null) {
					ancestorStart2 = ancestorStart + sa.getTokenStart(first.ancestorStart());
					ancestorEnd2 = ancestorStart + getTokenEnd(sa, last.ancestorStart(), last.ancestorLength());
				}

				int leftStart2 = leftStart + sy.getTokenStart(first.leftStart());
				int leftEnd2 = leftStart + getTokenEnd(sy, last.leftStart(), last.leftLength());

				int rightStart2 = rightStart + sm.getTokenStart(first.rightStart());
				int rightEnd2 = rightStart + getTokenEnd(sm, last.rightStart(), last.rightLength());
				Diff diff = new Diff(baseDiff, first.kind(), ancestorDoc, null, ancestorStart2, ancestorEnd2 + 1, leftDoc, null, leftStart2, leftEnd2 + 1, rightDoc, null, rightStart2, rightEnd2 + 1);
				diff.fIsToken = true;
				baseDiff.add(diff);
			}
		}
	}

	//---- update UI stuff

	private void updateControls() {

		boolean leftToRight = false;
		boolean rightToLeft = false;

		updateStatus(fCurrentDiff);
		updateResolveStatus();

		if (fCurrentDiff != null) {
			IMergeViewerContentProvider cp = getMergeContentProvider();
			if (cp != null) {
				rightToLeft = cp.isLeftEditable(getInput());
				leftToRight = cp.isRightEditable(getInput());
			}
		}

		if (fDirectionLabel != null) {
			if (fHighlightRanges && fCurrentDiff != null && isThreeWay() && !fIgnoreAncestor) {
				fDirectionLabel.setImage(fCurrentDiff.getImage());
			} else {
				fDirectionLabel.setImage(null);
			}
		}

		if (fCopyDiffLeftToRightItem != null)
			((Action) fCopyDiffLeftToRightItem.getAction()).setEnabled(leftToRight);
		if (fCopyDiffRightToLeftItem != null)
			((Action) fCopyDiffRightToLeftItem.getAction()).setEnabled(rightToLeft);

		boolean enableNavigation = false;
		if (fCurrentDiff == null && fChangeDiffs != null && fChangeDiffs.size() > 0)
			enableNavigation = true;
		else if (fChangeDiffs != null && fChangeDiffs.size() > 1)
			enableNavigation = true;
		else if (fCurrentDiff != null && fCurrentDiff.fDiffs != null)
			enableNavigation = true;
		else if (fCurrentDiff != null && fCurrentDiff.fIsToken)
			enableNavigation = true;

		if (fNextItem != null) {
			IAction a = fNextItem.getAction();
			a.setEnabled(enableNavigation);
		}
		if (fPreviousItem != null) {
			IAction a = fPreviousItem.getAction();
			a.setEnabled(enableNavigation);
		}
	}

	private void updateResolveStatus() {

		RGB rgb = null;

		if (showResolveUI()) {
			// we only show red or green if there is at least one incoming or conflicting change
			int incomingOrConflicting = 0;
			int unresolvedIncoming = 0;
			int unresolvedConflicting = 0;

			if (fChangeDiffs != null) {
				Iterator e = fChangeDiffs.iterator();
				while (e.hasNext()) {
					Diff d = (Diff) e.next();
					if (d.isIncomingOrConflicting() /* && useChange(d.fDirection) && !d.fIsWhitespace */) {
						incomingOrConflicting++;
						if (!d.fResolved) {
							if (d.fDirection == RangeDifference.CONFLICT) {
								unresolvedConflicting++;
								break; // we can stop here because a conflict has the maximum priority
							}
							unresolvedIncoming++;
						}
					}
				}
			}

			if (incomingOrConflicting > 0) {
				if (unresolvedConflicting > 0)
					rgb = SELECTED_CONFLICT;
				else if (unresolvedIncoming > 0)
					rgb = SELECTED_INCOMING;
				else
					rgb = RESOLVED;
			}
		}

		if (fHeaderPainter.setColor(rgb))
			fSummaryHeader.redraw();
	}

	private void updateStatus(Diff diff) {

		if (!fShowMoreInfo)
			return;

		IActionBars bars = Utilities.findActionBars(fComposite);
		if (bars == null)
			return;
		IStatusLineManager slm = bars.getStatusLineManager();
		if (slm == null)
			return;

		String diffDescription;

		if (diff == null) {
			diffDescription = CompareMessages.TextMergeViewer_diffDescription_noDiff_format;
		} else {

			if (diff.fIsToken) // we don't show special info for token diffs
				diff = diff.fParent;

			String format = CompareMessages.TextMergeViewer_diffDescription_diff_format;
			diffDescription = MessageFormat.format(format, new String[] { getDiffType(diff), // 0: diff type
				getDiffNumber(diff), // 1: diff number
				getDiffRange(fLeft, diff.fLeftPos), // 2: left start line
				getDiffRange(fRight, diff.fRightPos) // 3: left end line
				});
		}

		String format = CompareMessages.TextMergeViewer_statusLine_format;
		String s = MessageFormat.format(format, new String[] { getCursorPosition(fLeft), // 0: left column
			getCursorPosition(fRight), // 1: right column
			diffDescription // 2: diff description
			});

		slm.setMessage(s);
	}

	private void clearStatus() {

		IActionBars bars = Utilities.findActionBars(fComposite);
		if (bars == null)
			return;
		IStatusLineManager slm = bars.getStatusLineManager();
		if (slm == null)
			return;

		slm.setMessage(null);
	}

	private String getDiffType(Diff diff) {
		String s = ""; //$NON-NLS-1$
		switch (diff.fDirection) {
			case RangeDifference.LEFT:
				s = CompareMessages.TextMergeViewer_direction_outgoing;
				break;
			case RangeDifference.RIGHT:
				s = CompareMessages.TextMergeViewer_direction_incoming;
				break;
			case RangeDifference.CONFLICT:
				s = CompareMessages.TextMergeViewer_direction_conflicting;
				break;
		}
		String format = CompareMessages.TextMergeViewer_diffType_format;
		return MessageFormat.format(format, new String[] { s, diff.changeType() });
	}

	private String getDiffNumber(Diff diff) {
		// find the diff's number
		int diffNumber = 0;
		if (fChangeDiffs != null) {
			Iterator e = fChangeDiffs.iterator();
			while (e.hasNext()) {
				Diff d = (Diff) e.next();
				diffNumber++;
				if (d == diff)
					break;
			}
		}
		return Integer.toString(diffNumber);
	}

	private String getDiffRange(MergeSourceViewer v, Position pos) {
		Point p = v.getLineRange(pos, new Point(0, 0));
		int startLine = p.x + 1;
		int endLine = p.x + p.y;

		String format;
		if (endLine < startLine)
			format = CompareMessages.TextMergeViewer_beforeLine_format;
		else
			format = CompareMessages.TextMergeViewer_range_format;
		return MessageFormat.format(format, new String[] { Integer.toString(startLine), Integer.toString(endLine) });
	}

	/*
	 * Returns a description of the cursor position.
	 * 
	 * @return a description of the cursor position
	 */
	private String getCursorPosition(MergeSourceViewer v) {
		if (v != null) {
			StyledText styledText = v.getTextWidget();

			IDocument document = v.getDocument();
			if (document != null) {
				int offset = v.getVisibleRegion().getOffset();
				int caret = offset + styledText.getCaretOffset();

				try {

					int line = document.getLineOfOffset(caret);

					int lineOffset = document.getLineOffset(line);
					int occurrences = 0;
					for (int i = lineOffset; i < caret; i++)
						if ('\t' == document.getChar(i))
							++occurrences;

					int tabWidth = styledText.getTabs();
					int column = caret - lineOffset + (tabWidth - 1) * occurrences;

					String format = CompareMessages.TextMergeViewer_cursorPosition_format;
					return MessageFormat.format(format, new String[] { Integer.toString(line + 1), Integer.toString(column + 1) });

				} catch (BadLocationException x) {
					// silently ignored
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	protected void updateHeader() {

		super.updateHeader();

		updateControls();
	}

	/*
	 * Creates the two items for copying a difference range from one side to the other 
	 * and adds them to the given toolbar manager.
	 */
	protected void createToolItems(ToolBarManager tbm) {

		IWorkbenchPartSite ps = Utilities.findSite(fComposite);
		fKeyBindingService = ps != null ? ps.getKeyBindingService() : null;

		final String ignoreAncestorActionKey = "action.IgnoreAncestor."; //$NON-NLS-1$
		Action ignoreAncestorAction = new Action() {
			public void run() {
				setIgnoreAncestor(!fIgnoreAncestor);
				Utilities.initToggleAction(this, getResourceBundle(), ignoreAncestorActionKey, fIgnoreAncestor);
			}
		};
		ignoreAncestorAction.setChecked(fIgnoreAncestor);
		Utilities.initAction(ignoreAncestorAction, getResourceBundle(), ignoreAncestorActionKey);
		Utilities.initToggleAction(ignoreAncestorAction, getResourceBundle(), ignoreAncestorActionKey, fIgnoreAncestor);

		fIgnoreAncestorItem = new ActionContributionItem(ignoreAncestorAction);
		fIgnoreAncestorItem.setVisible(false);
		tbm.appendToGroup("modes", fIgnoreAncestorItem); //$NON-NLS-1$

		tbm.add(new Separator());

		Action a = new Action() {
			public void run() {
				navigate(true, true, true);
			}
		};
		Utilities.initAction(a, getResourceBundle(), "action.NextDiff."); //$NON-NLS-1$
		fNextItem = new ActionContributionItem(a);
		tbm.appendToGroup("navigation", fNextItem); //$NON-NLS-1$
		Utilities.registerAction(fKeyBindingService, a, "org.eclipse.compare.selectNextChange"); //$NON-NLS-1$

		a = new Action() {
			public void run() {
				navigate(false, true, true);
			}
		};
		Utilities.initAction(a, getResourceBundle(), "action.PrevDiff."); //$NON-NLS-1$
		fPreviousItem = new ActionContributionItem(a);
		tbm.appendToGroup("navigation", fPreviousItem); //$NON-NLS-1$
		Utilities.registerAction(fKeyBindingService, a, "org.eclipse.compare.selectPreviousChange"); //$NON-NLS-1$

		CompareConfiguration cc = getCompareConfiguration();

		if (cc.isRightEditable()) {
			a = new Action() {
				public void run() {
					copyDiffLeftToRight();
				}
			};
			Utilities.initAction(a, getResourceBundle(), "action.CopyDiffLeftToRight."); //$NON-NLS-1$
			fCopyDiffLeftToRightItem = new ActionContributionItem(a);
			fCopyDiffLeftToRightItem.setVisible(true);
			tbm.appendToGroup("merge", fCopyDiffLeftToRightItem); //$NON-NLS-1$
			Utilities.registerAction(fKeyBindingService, a, "org.eclipse.compare.copyLeftToRight"); //$NON-NLS-1$
		}

		if (cc.isLeftEditable()) {
			a = new Action() {
				public void run() {
					copyDiffRightToLeft();
				}
			};
			Utilities.initAction(a, getResourceBundle(), "action.CopyDiffRightToLeft."); //$NON-NLS-1$
			fCopyDiffRightToLeftItem = new ActionContributionItem(a);
			fCopyDiffRightToLeftItem.setVisible(true);
			tbm.appendToGroup("merge", fCopyDiffRightToLeftItem); //$NON-NLS-1$
			Utilities.registerAction(fKeyBindingService, a, "org.eclipse.compare.copyRightToLeft"); //$NON-NLS-1$
		}
	}

	/* package */void propertyChange(PropertyChangeEvent event) {

		String key = event.getProperty();

		if (key.equals(CompareConfiguration.IGNORE_WHITESPACE) || key.equals(ComparePreferencePage.SHOW_PSEUDO_CONFLICTS)) {

			fShowPseudoConflicts = fPreferenceStore.getBoolean(ComparePreferencePage.SHOW_PSEUDO_CONFLICTS);

			// clear stuff
			fCurrentDiff = null;
			fChangeDiffs = null;
			fAllDiffs = null;

			doDiff();

			updateControls();
			invalidateLines();
			updateVScrollBar();
			refreshBirdsEyeView();

			selectFirstDiff();

			//	} else if (key.equals(ComparePreferencePage.USE_SPLINES)) {
			//		fUseSplines= fPreferenceStore.getBoolean(ComparePreferencePage.USE_SPLINES);
			//		invalidateLines();

		} else if (key.equals(ComparePreferencePage.USE_SINGLE_LINE)) {
			fUseSingleLine = fPreferenceStore.getBoolean(ComparePreferencePage.USE_SINGLE_LINE);
			//		fUseResolveUI= fUseSingleLine;
			fBasicCenterCurve = null;
			updateResolveStatus();
			invalidateLines();

			//	} else if (key.equals(ComparePreferencePage.USE_RESOLVE_UI)) {
			//		fUseResolveUI= fPreferenceStore.getBoolean(ComparePreferencePage.USE_RESOLVE_UI);
			//		updateResolveStatus();
			//		invalidateLines();

		} else if (key.equals(fSymbolicFontName)) {
			updateFont();
			invalidateLines();

		} else if (key.equals(INCOMING_COLOR) || key.equals(OUTGOING_COLOR) || key.equals(CONFLICTING_COLOR) || key.equals(RESOLVED_COLOR)) {
			updateColors(null);
			invalidateLines();

		} else if (key.equals(ComparePreferencePage.SYNCHRONIZE_SCROLLING)) {

			boolean b = fPreferenceStore.getBoolean(ComparePreferencePage.SYNCHRONIZE_SCROLLING);
			if (b != fSynchronizedScrolling)
				toggleSynchMode();

		} else if (key.equals(ComparePreferencePage.SHOW_MORE_INFO)) {

			boolean b = fPreferenceStore.getBoolean(ComparePreferencePage.SHOW_MORE_INFO);
			if (b != fShowMoreInfo) {
				fShowMoreInfo = b;
				if (fShowMoreInfo)
					updateStatus(fCurrentDiff);
				else
					clearStatus();
			}

		} else
			super.propertyChange(event);
	}

	private void setIgnoreAncestor(boolean ignore) {
		if (ignore != fIgnoreAncestor) {
			fIgnoreAncestor = ignore;
			setAncestorVisibility(false, !fIgnoreAncestor);

			// clear stuff
			fCurrentDiff = null;
			fChangeDiffs = null;
			fAllDiffs = null;

			doDiff();

			invalidateLines();
			updateVScrollBar();
			refreshBirdsEyeView();

			selectFirstDiff();
		}
	}

	private void selectFirstDiff() {

		if (fLeft == null || fRight == null) {
			return;
		}
		if (fLeft.getDocument() == null || fRight.getDocument() == null) {
			return;
		}

		Diff firstDiff = null;
		if (CompareNavigator.getDirection(fComposite))
			firstDiff = findNext(fRight, fChangeDiffs, -1, -1, false);
		else
			firstDiff = findPrev(fRight, fChangeDiffs, 9999999, 9999999, false);
		setCurrentDiff(firstDiff, true);
	}

	private void toggleSynchMode() {
		fSynchronizedScrolling = !fSynchronizedScrolling;

		scrollVertical(0, 0, 0, null);

		// throw away central control (Sash or Canvas)
		Control center = getCenter();
		if (center != null && !center.isDisposed())
			center.dispose();

		fLeft.getTextWidget().getVerticalBar().setVisible(!fSynchronizedScrolling);
		fRight.getTextWidget().getVerticalBar().setVisible(!fSynchronizedScrolling);

		fComposite.layout(true);
	}

	protected void updateToolItems() {

		if (fIgnoreAncestorItem != null)
			fIgnoreAncestorItem.setVisible(isThreeWay());

		if (fCopyDiffLeftToRightItem != null) {
			IAction a = fCopyDiffLeftToRightItem.getAction();
			if (a != null)
				a.setEnabled(a.isEnabled() && !fHasErrors);
		}
		if (fCopyDiffRightToLeftItem != null) {
			IAction a = fCopyDiffRightToLeftItem.getAction();
			if (a != null)
				a.setEnabled(a.isEnabled() && !fHasErrors);
		}

		super.updateToolItems();
	}

	//---- painting lines

	private void updateLines(IDocument d) {

		boolean left = false;
		boolean right = false;

		// FIXME: this optimization is incorrect because
		// it doesn't take replace operations into account where
		// the old and new line count does not differ
		if (d == fLeft.getDocument()) {
			int l = fLeft.getLineCount();
			left = fLeftLineCount != l;
			fLeftLineCount = l;
		} else if (d == fRight.getDocument()) {
			int l = fRight.getLineCount();
			right = fRightLineCount != l;
			fRightLineCount = l;
		}

		if (left || right) {

			if (left) {
				if (fLeftCanvas != null)
					fLeftCanvas.redraw();
			} else {
				if (fRightCanvas != null)
					fRightCanvas.redraw();
			}
			Control center = getCenter();
			if (center != null)
				center.redraw();

			updateVScrollBar();
			refreshBirdsEyeView();
		}
	}

	private void invalidateLines() {
		if (isThreeWay()) {
			if (Utilities.okToUse(fAncestorCanvas))
				fAncestorCanvas.redraw();
			if (fAncestor != null && fAncestor.isControlOkToUse())
				fAncestor.getTextWidget().redraw();
		}

		if (Utilities.okToUse(fLeftCanvas))
			fLeftCanvas.redraw();

		if (fLeft != null && fLeft.isControlOkToUse())
			fLeft.getTextWidget().redraw();

		if (Utilities.okToUse(getCenter()))
			getCenter().redraw();

		if (fRight != null && fRight.isControlOkToUse())
			fRight.getTextWidget().redraw();

		if (Utilities.okToUse(fRightCanvas))
			fRightCanvas.redraw();
	}

	private boolean showResolveUI() {
		if (!fUseResolveUI || !isThreeWay() || fIgnoreAncestor)
			return false;
		CompareConfiguration cc = getCompareConfiguration();
		if (cc == null)
			return false;
		// we only enable the new resolve UI if exactly one side is editable
		boolean l = cc.isLeftEditable();
		boolean r = cc.isRightEditable();
		//return (l && !r) || (r && !l);
		return l || r;
	}

	private void paintCenter(Canvas canvas, GC g) {

		Display display = canvas.getDisplay();

		checkForColorUpdate(display);

		if (!fSynchronizedScrolling)
			return;

		int lineHeight = fLeft.getTextWidget().getLineHeight();
		int visibleHeight = fRight.getViewportHeight();

		Point size = canvas.getSize();
		int x = 0;
		int w = size.x;

		g.setBackground(canvas.getBackground());
		g.fillRectangle(x + 1, 0, w - 2, size.y);

		if (!fIsMotif) {
			// draw thin line between center ruler and both texts
			g.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
			g.fillRectangle(0, 0, 1, size.y);
			g.fillRectangle(w - 1, 0, 1, size.y);
		}

		if (!fHighlightRanges)
			return;

		boolean showResolveUI = showResolveUI();

		if (fChangeDiffs != null) {
			int lshift = fLeft.getVerticalScrollOffset();
			int rshift = fRight.getVerticalScrollOffset();

			Point region = new Point(0, 0);

			Iterator e = fChangeDiffs.iterator();
			while (e.hasNext()) {
				Diff diff = (Diff) e.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				fLeft.getLineRange(diff.fLeftPos, region);
				int ly = (region.x * lineHeight) + lshift;
				int lh = region.y * lineHeight;

				fRight.getLineRange(diff.fRightPos, region);
				int ry = (region.x * lineHeight) + rshift;
				int rh = region.y * lineHeight;

				if (Math.max(ly + lh, ry + rh) < 0)
					continue;
				if (Math.min(ly, ry) >= visibleHeight)
					break;

				fPts[0] = x;
				fPts[1] = ly;
				fPts[2] = w;
				fPts[3] = ry;
				fPts[6] = x;
				fPts[7] = ly + lh;
				fPts[4] = w;
				fPts[5] = ry + rh;

				Color fillColor = getColor(display, getFillColor(diff));
				Color strokeColor = getColor(display, getStrokeColor(diff));

				if (fUseSingleLine) {
					int w2 = 3;

					g.setBackground(fillColor);
					g.fillRectangle(0, ly, w2, lh); // left
					g.fillRectangle(w - w2, ry, w2, rh); // right

					g.setLineWidth(LW);
					g.setForeground(strokeColor);
					g.drawRectangle(0 - 1, ly, w2, lh); // left
					g.drawRectangle(w - w2, ry, w2, rh); // right

					if (fUseSplines) {
						int[] points = getCenterCurvePoints(w2, ly + lh / 2, w - w2, ry + rh / 2);
						for (int i = 1; i < points.length; i++)
							g.drawLine(w2 + i - 1, points[i - 1], w2 + i, points[i]);
					} else {
						g.drawLine(w2, ly + lh / 2, w - w2, ry + rh / 2);
					}
				} else {
					// two lines
					if (fUseSplines) {
						g.setBackground(fillColor);

						g.setLineWidth(LW);
						g.setForeground(strokeColor);

						int[] topPoints = getCenterCurvePoints(fPts[0], fPts[1], fPts[2], fPts[3]);
						int[] bottomPoints = getCenterCurvePoints(fPts[6], fPts[7], fPts[4], fPts[5]);
						g.setForeground(fillColor);
						g.drawLine(0, bottomPoints[0], 0, topPoints[0]);
						for (int i = 1; i < bottomPoints.length; i++) {
							g.setForeground(fillColor);
							g.drawLine(i, bottomPoints[i], i, topPoints[i]);
							g.setForeground(strokeColor);
							g.drawLine(i - 1, topPoints[i - 1], i, topPoints[i]);
							g.drawLine(i - 1, bottomPoints[i - 1], i, bottomPoints[i]);
						}
					} else {
						g.setBackground(fillColor);
						g.fillPolygon(fPts);

						g.setLineWidth(LW);
						g.setForeground(strokeColor);
						g.drawLine(fPts[0], fPts[1], fPts[2], fPts[3]);
						g.drawLine(fPts[6], fPts[7], fPts[4], fPts[5]);
					}
				}

				if (fUseSingleLine && showResolveUI && diff.isUnresolvedIncomingOrConflicting()) {
					// draw resolve state
					int cx = (w - RESOLVE_SIZE) / 2;
					int cy = ((ly + lh / 2) + (ry + rh / 2) - RESOLVE_SIZE) / 2;

					g.setBackground(fillColor);
					g.fillRectangle(cx, cy, RESOLVE_SIZE, RESOLVE_SIZE);

					g.setForeground(strokeColor);
					g.drawRectangle(cx, cy, RESOLVE_SIZE, RESOLVE_SIZE);
				}
			}
		}
	}

	private int[] getCenterCurvePoints(int startx, int starty, int endx, int endy) {
		if (fBasicCenterCurve == null)
			buildBaseCenterCurve(endx - startx);
		double height = endy - starty;
		height = height / 2;
		int width = endx - startx;
		int[] points = new int[width];
		for (int i = 0; i < width; i++) {
			points[i] = (int) (-height * fBasicCenterCurve[i] + height + starty);
		}
		return points;
	}

	private void buildBaseCenterCurve(int w) {
		double width = w;
		fBasicCenterCurve = new double[getCenterWidth()];
		for (int i = 0; i < getCenterWidth(); i++) {
			double r = i / width;
			fBasicCenterCurve[i] = Math.cos(Math.PI * r);
		}
	}

	private void paintSides(GC g, MergeSourceViewer tp, Canvas canvas, boolean right) {

		Display display = canvas.getDisplay();

		int lineHeight = tp.getTextWidget().getLineHeight();
		int visibleHeight = tp.getViewportHeight();

		Point size = canvas.getSize();
		int x = 0;
		int w = fMarginWidth;
		int w2 = w / 2;

		g.setBackground(canvas.getBackground());
		g.fillRectangle(x, 0, w, size.y);

		if (!fIsMotif) {
			// draw thin line between ruler and text
			g.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
			if (right)
				g.fillRectangle(0, 0, 1, size.y);
			else
				g.fillRectangle(size.x - 1, 0, 1, size.y);
		}

		if (!fHighlightRanges)
			return;

		if (fChangeDiffs != null) {
			int shift = tp.getVerticalScrollOffset() + (2 - LW);

			Point region = new Point(0, 0);
			Iterator e = fChangeDiffs.iterator();
			while (e.hasNext()) {
				Diff diff = (Diff) e.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				tp.getLineRange(diff.getPosition(tp), region);
				int y = (region.x * lineHeight) + shift;
				int h = region.y * lineHeight;

				if (y + h < 0)
					continue;
				if (y >= visibleHeight)
					break;

				g.setBackground(getColor(display, getFillColor(diff)));
				if (right)
					g.fillRectangle(x, y, w2, h);
				else
					g.fillRectangle(x + w2, y, w2, h);

				g.setLineWidth(LW);
				g.setForeground(getColor(display, getStrokeColor(diff)));
				if (right)
					g.drawRectangle(x - 1, y - 1, w2, h);
				else
					g.drawRectangle(x + w2, y - 1, w2, h);
			}
		}
	}

	private void paint(PaintEvent event, MergeSourceViewer tp) {

		if (!fHighlightRanges)
			return;
		if (fChangeDiffs == null)
			return;

		Control canvas = (Control) event.widget;
		GC g = event.gc;

		Display display = canvas.getDisplay();

		int lineHeight = tp.getTextWidget().getLineHeight();
		int w = canvas.getSize().x;
		int shift = tp.getVerticalScrollOffset() + (2 - LW);
		int maxh = event.y + event.height; // visibleHeight

		//if (fIsMotif)
		shift += fTopInset;

		Point range = new Point(0, 0);

		Iterator e = fChangeDiffs.iterator();
		while (e.hasNext()) {
			Diff diff = (Diff) e.next();
			if (diff.isDeleted())
				continue;

			if (fShowCurrentOnly && !isCurrentDiff(diff))
				continue;

			tp.getLineRange(diff.getPosition(tp), range);
			int y = (range.x * lineHeight) + shift;
			int h = range.y * lineHeight;

			if (y + h < event.y)
				continue;
			if (y > maxh)
				break;

			g.setBackground(getColor(display, getStrokeColor(diff)));
			g.fillRectangle(0, y - 1, w, LW);
			g.fillRectangle(0, y + h - 1, w, LW);
		}
	}

	private RGB getFillColor(Diff diff) {
		boolean selected = fCurrentDiff != null && fCurrentDiff.fParent == diff;

		RGB selected_fill = getBackground(null);

		if (isThreeWay() && !fIgnoreAncestor) {
			switch (diff.fDirection) {
				case RangeDifference.RIGHT:
					if (fLeftIsLocal)
						return selected ? selected_fill : INCOMING_FILL;
					return selected ? selected_fill : OUTGOING_FILL;
				case RangeDifference.ANCESTOR:
					return selected ? selected_fill : CONFLICT_FILL;
				case RangeDifference.LEFT:
					if (fLeftIsLocal)
						return selected ? selected_fill : OUTGOING_FILL;
					return selected ? selected_fill : INCOMING_FILL;
				case RangeDifference.CONFLICT:
					return selected ? selected_fill : CONFLICT_FILL;
			}
			return null;
		}
		return selected ? selected_fill : OUTGOING_FILL;
	}

	private RGB getStrokeColor(Diff diff) {
		boolean selected = fCurrentDiff != null && fCurrentDiff.fParent == diff;

		if (isThreeWay() && !fIgnoreAncestor) {
			switch (diff.fDirection) {
				case RangeDifference.RIGHT:
					if (fLeftIsLocal)
						return selected ? SELECTED_INCOMING : INCOMING;
					return selected ? SELECTED_OUTGOING : OUTGOING;
				case RangeDifference.ANCESTOR:
					return selected ? SELECTED_CONFLICT : CONFLICT;
				case RangeDifference.LEFT:
					if (fLeftIsLocal)
						return selected ? SELECTED_OUTGOING : OUTGOING;
					return selected ? SELECTED_INCOMING : INCOMING;
				case RangeDifference.CONFLICT:
					return selected ? SELECTED_CONFLICT : CONFLICT;
			}
			return null;
		}
		return selected ? SELECTED_OUTGOING : OUTGOING;
	}

	private Color getColor(Display display, RGB rgb) {
		if (rgb == null)
			return null;
		if (fColors == null)
			fColors = new HashMap(20);
		Color c = (Color) fColors.get(rgb);
		if (c == null) {
			c = new Color(display, rgb);
			fColors.put(rgb, c);
		}
		return c;
	}

	static RGB interpolate(RGB fg, RGB bg, double scale) {
		if (fg != null && bg != null)
			return new RGB((int) ((1.0 - scale) * fg.red + scale * bg.red), (int) ((1.0 - scale) * fg.green + scale * bg.green), (int) ((1.0 - scale) * fg.blue + scale * bg.blue));
		if (fg != null)
			return fg;
		if (bg != null)
			return bg;
		return new RGB(128, 128, 128); // a gray
	}

	//---- Navigating and resolving Diffs

	/*
	 * Returns true if end (or beginning) of document reached.
	 */
	private boolean navigate(boolean down, boolean wrap, boolean deep) {

		Diff diff = null;

		for (;;) {

			if (fChangeDiffs != null) {
				MergeSourceViewer part = fFocusPart;
				if (part == null)
					part = fRight;

				if (part != null) {
					Point s = part.getSelectedRange();
					if (down)
						diff = findNext(part, fChangeDiffs, s.x, s.x + s.y, deep);
					else
						diff = findPrev(part, fChangeDiffs, s.x, s.x + s.y, deep);
				}
			}

			if (diff == null) { // at end or beginning
				if (wrap) {
					if (!fEndOfDocReached) {
						fEndOfDocReached = true;
						if (!endOfDocumentReached(down))
							return true;
					}
					fEndOfDocReached = false;
					if (fChangeDiffs != null && fChangeDiffs.size() > 0) {
						if (down)
							diff = (Diff) fChangeDiffs.get(0);
						else
							diff = (Diff) fChangeDiffs.get(fChangeDiffs.size() - 1);
					}
				} else {
					fEndOfDocReached = false;
					return true;
				}
			}

			setCurrentDiff(diff, true);

			if (diff != null && diff.fDirection == RangeDifference.ANCESTOR && !getAncestorEnabled())
				continue;

			break;
		}

		return false;
	}

	private boolean endOfDocumentReached(boolean down) {
		Control c = getControl();
		if (Utilities.okToUse(c)) {

			c.getDisplay().beep();

			if (down)
				return MessageDialog.openQuestion(c.getShell(), CompareMessages.TextMergeViewer_atEnd_title, CompareMessages.TextMergeViewer_atEnd_message);
			return MessageDialog.openQuestion(c.getShell(), CompareMessages.TextMergeViewer_atBeginning_title, CompareMessages.TextMergeViewer_atBeginning_message);
		}
		return false;
	}

	/*
	 * Find the Diff that overlaps with the given TextPart's text range.
	 * If the range doesn't overlap with any range <code>null</code>
	 * is returned.
	 */
	private Diff findDiff(MergeSourceViewer tp, int rangeStart, int rangeEnd) {
		if (fChangeDiffs != null) {
			Iterator e = fChangeDiffs.iterator();
			while (e.hasNext()) {
				Diff diff = (Diff) e.next();
				if (diff.overlaps(tp, rangeStart, rangeEnd))
					return diff;
			}
		}
		return null;
	}

	private static Diff findNext(MergeSourceViewer tp, List v, int start, int end, boolean deep) {
		for (int i = 0; i < v.size(); i++) {
			Diff diff = (Diff) v.get(i);
			Position p = diff.getPosition(tp);
			if (p != null) {
				int startOffset = p.getOffset();
				if (end < startOffset) // <=
					return diff;
				if (deep && diff.fDiffs != null) {
					Diff d = null;
					int endOffset = startOffset + p.getLength();
					if (start == startOffset && (end == endOffset || end == endOffset - 1)) {
						d = findNext(tp, diff.fDiffs, start - 1, start - 1, deep);
					} else if (end < endOffset) {
						d = findNext(tp, diff.fDiffs, start, end, deep);
					}
					if (d != null)
						return d;
				}
			}
		}
		return null;
	}

	private static Diff findPrev(MergeSourceViewer tp, List v, int start, int end, boolean deep) {
		for (int i = v.size() - 1; i >= 0; i--) {
			Diff diff = (Diff) v.get(i);
			Position p = diff.getPosition(tp);
			if (p != null) {
				int startOffset = p.getOffset();
				int endOffset = startOffset + p.getLength();
				if (start > endOffset)
					return diff;
				if (deep && diff.fDiffs != null) {
					Diff d = null;
					if (start == startOffset && end == endOffset) {
						d = findPrev(tp, diff.fDiffs, end, end, deep);
					} else if (start >= startOffset) {
						d = findPrev(tp, diff.fDiffs, start, end, deep);
					}
					if (d != null)
						return d;
				}
			}
		}
		return null;
	}

	/*
	 * Set the currently active Diff and update the toolbars controls and lines.
	 * If <code>revealAndSelect</code> is <code>true</code> the Diff is revealed and
	 * selected in both TextParts.
	 */
	private void setCurrentDiff(Diff d, boolean revealAndSelect) {

		//	if (d == fCurrentDiff)
		//		return;

		if (fCenterButton != null && !fCenterButton.isDisposed())
			fCenterButton.setVisible(false);

		fEndOfDocReached = false;

		Diff oldDiff = fCurrentDiff;

		if (d != null && revealAndSelect) {

			// before we set fCurrentDiff we change the selection
			// so that the paint code uses the old background colors
			// otherwise selection isn't drawn correctly
			if (isThreeWay() && !fIgnoreAncestor)
				fAncestor.setSelection(d.fAncestorPos);
			fLeft.setSelection(d.fLeftPos);
			fRight.setSelection(d.fRightPos);

			// now switch diffs
			fCurrentDiff = d;
			revealDiff(d, d.fIsToken);
		} else {
			fCurrentDiff = d;
		}

		Diff d1 = oldDiff != null ? oldDiff.fParent : null;
		Diff d2 = fCurrentDiff != null ? fCurrentDiff.fParent : null;
		if (d1 != d2) {
			updateDiffBackground(d1);
			updateDiffBackground(d2);
		}

		updateControls();
		invalidateLines();
		refreshBirdsEyeView();
	}

	/*
	 * Smart determines whether 
	 */
	private void revealDiff(Diff d, boolean smart) {

		boolean ancestorIsVisible = false;
		boolean leftIsVisible = false;
		boolean rightIsVisible = false;

		if (smart) {
			Point region = new Point(0, 0);
			// find the starting line of the diff in all text widgets
			int ls = fLeft.getLineRange(d.fLeftPos, region).x;
			int rs = fRight.getLineRange(d.fRightPos, region).x;

			if (isThreeWay() && !fIgnoreAncestor) {
				int as = fAncestor.getLineRange(d.fAncestorPos, region).x;
				if (as >= fAncestor.getTopIndex() && as <= fAncestor.getBottomIndex())
					ancestorIsVisible = true;
			}

			if (ls >= fLeft.getTopIndex() && ls <= fLeft.getBottomIndex())
				leftIsVisible = true;

			if (rs >= fRight.getTopIndex() && rs <= fRight.getBottomIndex())
				rightIsVisible = true;
		}

		// vertical scrolling
		if (!leftIsVisible || !rightIsVisible) {
			int avpos = 0, lvpos = 0, rvpos = 0;

			MergeSourceViewer allButThis = null;
			if (leftIsVisible) {
				avpos = lvpos = rvpos = realToVirtualPosition(fLeft, fLeft.getTopIndex());
				allButThis = fLeft;
			} else if (rightIsVisible) {
				avpos = lvpos = rvpos = realToVirtualPosition(fRight, fRight.getTopIndex());
				allButThis = fRight;
			} else if (ancestorIsVisible) {
				avpos = lvpos = rvpos = realToVirtualPosition(fAncestor, fAncestor.getTopIndex());
				allButThis = fAncestor;
			} else {
				if (fAllDiffs != null) {
					int vpos = 0;
					Iterator e = fAllDiffs.iterator();
					for (int i = 0; e.hasNext(); i++) {
						Diff diff = (Diff) e.next();
						if (diff == d)
							break;
						if (fSynchronizedScrolling) {
							vpos += diff.getMaxDiffHeight(fShowAncestor);
						} else {
							avpos += diff.getAncestorHeight();
							lvpos += diff.getLeftHeight();
							rvpos += diff.getRightHeight();
						}
					}
					if (fSynchronizedScrolling)
						avpos = lvpos = rvpos = vpos;
				}
				int delta = fRight.getViewportLines() / 4;
				avpos -= delta;
				if (avpos < 0)
					avpos = 0;
				lvpos -= delta;
				if (lvpos < 0)
					lvpos = 0;
				rvpos -= delta;
				if (rvpos < 0)
					rvpos = 0;
			}

			scrollVertical(avpos, lvpos, rvpos, allButThis);

			if (fVScrollBar != null)
				fVScrollBar.setSelection(avpos);
		}

		// horizontal scrolling
		if (d.fIsToken) {
			// we only scroll horizontally for token diffs
			reveal(fAncestor, d.fAncestorPos);
			reveal(fLeft, d.fLeftPos);
			reveal(fRight, d.fRightPos);
		} else {
			// in all other cases we reset the horizontal offset
			hscroll(fAncestor);
			hscroll(fLeft);
			hscroll(fRight);
		}
	}

	private static void reveal(MergeSourceViewer v, Position p) {
		if (v != null && p != null) {
			StyledText st = v.getTextWidget();
			if (st != null) {
				Rectangle r = st.getClientArea();
				if (!r.isEmpty()) // workaround for #7320: Next diff scrolls when going into current diff 
					v.revealRange(p.offset, p.length);
			}
		}
	}

	private static void hscroll(MergeSourceViewer v) {
		if (v != null) {
			StyledText st = v.getTextWidget();
			if (st != null)
				st.setHorizontalIndex(0);
		}
	}

	//--------------------------------------------------------------------------------

	void copyAllUnresolved(boolean leftToRight) {
		if (fChangeDiffs != null && isThreeWay() && !fIgnoreAncestor) {
			IRewriteTarget target = leftToRight ? fRight.getRewriteTarget() : fLeft.getRewriteTarget();
			boolean compoundChangeStarted = false;
			Iterator e = fChangeDiffs.iterator();
			try {
				while (e.hasNext()) {
					Diff diff = (Diff) e.next();
					switch (diff.fDirection) {
						case RangeDifference.LEFT:
							if (leftToRight) {
								if (!compoundChangeStarted) {
									target.beginCompoundChange();
									compoundChangeStarted = true;
								}
								copy(diff, leftToRight);
							}
							break;
						case RangeDifference.RIGHT:
							if (!leftToRight) {
								if (!compoundChangeStarted) {
									target.beginCompoundChange();
									compoundChangeStarted = true;
								}
								copy(diff, leftToRight);
							}
							break;
						default:
							continue;
					}
				}
			} finally {
				if (compoundChangeStarted) {
					target.endCompoundChange();
				}
			}
		}
	}

	/*
	 * Copy whole document from one side to the other.
	 */
	protected void copy(boolean leftToRight) {

		if (showResolveUI()) {
			copyAllUnresolved(leftToRight);
			invalidateLines();
			return;
		}

		if (leftToRight) {
			if (fLeft.getEnabled()) {
				// copy text
				String text = fLeft.getTextWidget().getText();
				fRight.getTextWidget().setText(text);
				fRight.setEnabled(true);
			} else {
				// delete
				fRight.getTextWidget().setText(""); //$NON-NLS-1$
				fRight.setEnabled(false);
			}
			fRightLineCount = fRight.getLineCount();
			setRightDirty(true);
		} else {
			if (fRight.getEnabled()) {
				// copy text
				String text = fRight.getTextWidget().getText();
				fLeft.getTextWidget().setText(text);
				fLeft.setEnabled(true);
			} else {
				// delete
				fLeft.getTextWidget().setText(""); //$NON-NLS-1$
				fLeft.setEnabled(false);
			}
			fLeftLineCount = fLeft.getLineCount();
			setLeftDirty(true);
		}
		doDiff();
		invalidateLines();
		updateVScrollBar();
		selectFirstDiff();
		refreshBirdsEyeView();
	}

	private void copyDiffLeftToRight() {
		copy(fCurrentDiff, true, false);
	}

	private void copyDiffRightToLeft() {
		copy(fCurrentDiff, false, false);
	}

	/*
	 * Copy the contents of the given diff from one side to the other.
	 */
	private void copy(Diff diff, boolean leftToRight, boolean gotoNext) {
		if (copy(diff, leftToRight)) {
			if (gotoNext) {
				navigate(true, true, true);
			} else {
				revealDiff(diff, true);
				updateControls();
			}
		}
	}

	/*
	 * Copy the contents of the given diff from one side to the other but
	 * doesn't reveal anything.
	 * Returns true if copy was successful.
	 */
	private boolean copy(Diff diff, boolean leftToRight) {

		if (diff != null && !diff.isResolved()) {

			Position fromPos = null;
			Position toPos = null;
			IDocument fromDoc = null;
			IDocument toDoc = null;

			if (leftToRight) {
				fRight.setEnabled(true);
				fromPos = diff.fLeftPos;
				toPos = diff.fRightPos;
				fromDoc = fLeft.getDocument();
				toDoc = fRight.getDocument();
			} else {
				fLeft.setEnabled(true);
				fromPos = diff.fRightPos;
				toPos = diff.fLeftPos;
				fromDoc = fRight.getDocument();
				toDoc = fLeft.getDocument();
			}

			if (fromDoc != null) {

				int fromStart = fromPos.getOffset();
				int fromLen = fromPos.getLength();

				int toStart = toPos.getOffset();
				int toLen = toPos.getLength();

				try {
					String s = null;

					switch (diff.fDirection) {
						case RangeDifference.RIGHT:
						case RangeDifference.LEFT:
							s = fromDoc.get(fromStart, fromLen);
							break;
						case RangeDifference.ANCESTOR:
							break;
						case RangeDifference.CONFLICT:
							if (APPEND_CONFLICT) {
								s = toDoc.get(toStart, toLen);
								s += fromDoc.get(fromStart, fromLen);
							} else
								s = fromDoc.get(fromStart, fromLen);
							break;
					}
					if (s != null) {
						toDoc.replace(toStart, toLen, s);
						toPos.setOffset(toStart);
						toPos.setLength(s.length());
					}

				} catch (BadLocationException e) {
					// silently ignored
				}
			}

			diff.setResolved(true);
			updateResolveStatus();

			return true;
		}
		return false;
	}

	//---- scrolling

	/*
	 * Calculates virtual height (in lines) of views by adding the maximum of corresponding diffs.
	 */
	private int getVirtualHeight() {
		int h = 1;
		if (fAllDiffs != null) {
			Iterator e = fAllDiffs.iterator();
			for (int i = 0; e.hasNext(); i++) {
				Diff diff = (Diff) e.next();
				h += diff.getMaxDiffHeight(fShowAncestor);
			}
		}
		return h;
	}

	/*
	 * Calculates height (in lines) of right view by adding the height of the right diffs.
	 */
	private int getRightHeight() {
		int h = 1;
		if (fAllDiffs != null) {
			Iterator e = fAllDiffs.iterator();
			for (int i = 0; e.hasNext(); i++) {
				Diff diff = (Diff) e.next();
				h += diff.getRightHeight();
			}
		}
		return h;
	}

	/*
	 * The height of the TextEditors in lines.
	 */
	private int getViewportHeight() {
		StyledText te = fLeft.getTextWidget();

		int vh = te.getClientArea().height;
		if (vh == 0) {
			Rectangle trim = te.computeTrim(0, 0, 0, 0);
			int scrollbarHeight = trim.height;

			int headerHeight = getHeaderHeight();

			Composite composite = (Composite) getControl();
			Rectangle r = composite.getClientArea();

			vh = r.height - headerHeight - scrollbarHeight;
		}

		return vh / te.getLineHeight();
	}

	/*
	 * Returns the virtual position for the given view position.
	 */
	private int realToVirtualPosition(MergeSourceViewer w, int vpos) {

		if (!fSynchronizedScrolling || fAllDiffs == null)
			return vpos;

		int viewPos = 0; // real view position
		int virtualPos = 0; // virtual position
		Point region = new Point(0, 0);

		Iterator e = fAllDiffs.iterator();
		while (e.hasNext()) {
			Diff diff = (Diff) e.next();
			Position pos = diff.getPosition(w);
			w.getLineRange(pos, region);
			int realHeight = region.y;
			int virtualHeight = diff.getMaxDiffHeight(fShowAncestor);
			if (vpos <= viewPos + realHeight) { // OK, found!
				vpos -= viewPos; // make relative to this slot
				// now scale position within this slot to virtual slot
				if (realHeight <= 0)
					vpos = 0;
				else
					vpos = (vpos * virtualHeight) / realHeight;
				return virtualPos + vpos;
			}
			viewPos += realHeight;
			virtualPos += virtualHeight;
		}
		return virtualPos;
	}

	private void scrollVertical(int avpos, int lvpos, int rvpos, MergeSourceViewer allBut) {

		int s = 0;

		if (fSynchronizedScrolling) {
			s = getVirtualHeight() - rvpos;
			int height = fRight.getViewportLines() / 4;
			if (s < 0)
				s = 0;
			if (s > height)
				s = height;
		}

		fInScrolling = true;

		if (isThreeWay() && allBut != fAncestor) {
			if (fSynchronizedScrolling || allBut == null) {
				int y = virtualToRealPosition(fAncestor, avpos + s) - s;
				fAncestor.vscroll(y);
			}
		}

		if (allBut != fLeft) {
			if (fSynchronizedScrolling || allBut == null) {
				int y = virtualToRealPosition(fLeft, lvpos + s) - s;
				fLeft.vscroll(y);
			}
		}

		if (allBut != fRight) {
			if (fSynchronizedScrolling || allBut == null) {
				int y = virtualToRealPosition(fRight, rvpos + s) - s;
				fRight.vscroll(y);
			}
		}

		fInScrolling = false;

		if (isThreeWay() && fAncestorCanvas != null)
			fAncestorCanvas.repaint();

		if (fLeftCanvas != null)
			fLeftCanvas.repaint();

		Control center = getCenter();
		if (center instanceof BufferedCanvas)
			((BufferedCanvas) center).repaint();

		if (fRightCanvas != null)
			fRightCanvas.repaint();
	}

	/*
	 * Updates Scrollbars with viewports.
	 */
	private void syncViewport(MergeSourceViewer w) {

		if (fInScrolling)
			return;

		int ix = w.getTopIndex();
		int ix2 = w.getDocumentRegionOffset();

		int viewPosition = realToVirtualPosition(w, ix - ix2);

		scrollVertical(viewPosition, viewPosition, viewPosition, w); // scroll all but the given views

		if (fVScrollBar != null) {
			int value = Math.max(0, Math.min(viewPosition, getVirtualHeight() - getViewportHeight()));
			fVScrollBar.setSelection(value);
			//refreshBirdEyeView();
		}
	}

	/**
	 */
	private void updateVScrollBar() {

		if (Utilities.okToUse(fVScrollBar) && fSynchronizedScrolling) {
			int virtualHeight = getVirtualHeight();
			int viewPortHeight = getViewportHeight();
			int pageIncrement = viewPortHeight - 1;
			int thumb = (viewPortHeight > virtualHeight) ? virtualHeight : viewPortHeight;

			fVScrollBar.setPageIncrement(pageIncrement);
			fVScrollBar.setMaximum(virtualHeight);
			fVScrollBar.setThumb(thumb);
		}
	}

	/*
	 * maps given virtual position into a real view position of this view.
	 */
	private int virtualToRealPosition(MergeSourceViewer part, int v) {

		if (!fSynchronizedScrolling || fAllDiffs == null)
			return v;

		int virtualPos = 0;
		int viewPos = 0;
		Point region = new Point(0, 0);

		Iterator e = fAllDiffs.iterator();
		while (e.hasNext()) {
			Diff diff = (Diff) e.next();
			Position pos = diff.getPosition(part);
			int viewHeight = part.getLineRange(pos, region).y;
			int virtualHeight = diff.getMaxDiffHeight(fShowAncestor);
			if (v < (virtualPos + virtualHeight)) {
				v -= virtualPos; // make relative to this slot
				if (viewHeight <= 0) {
					v = 0;
				} else {
					v = (v * viewHeight) / virtualHeight;
				}
				return viewPos + v;
			}
			virtualPos += virtualHeight;
			viewPos += viewHeight;
		}
		return viewPos;
	}
}
