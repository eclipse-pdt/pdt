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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.eclipse.compare.*;
import org.eclipse.compare.contentmergeviewer.IDocumentRange;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.contentmergeviewer.ITokenComparator;
import org.eclipse.compare.contentmergeviewer.TokenComparator;
import org.eclipse.compare.internal.*;
import org.eclipse.compare.internal.merge.DocumentMerger;
import org.eclipse.compare.internal.merge.DocumentMerger.Diff;
import org.eclipse.compare.internal.merge.DocumentMerger.IDocumentMergerInput;
import org.eclipse.compare.patch.IHunk;
import org.eclipse.compare.rangedifferencer.RangeDifference;
import org.eclipse.compare.structuremergeviewer.*;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.*;
import org.eclipse.wst.sse.core.internal.document.StructuredDocumentFactory;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.text.JobSafeStructuredDocument;

import com.ibm.icu.text.MessageFormat;

/**
 * A text merge viewer uses the <code>RangeDifferencer</code> to perform a
 * textual, line-by-line comparison of two (or three) input documents. It is
 * based on the <code>ContentMergeViewer</code> and uses <code>TextViewer</code>
 * s to implement the ancestor, left, and right content areas.
 * <p>
 * In the three-way compare case ranges of differing lines are highlighted and
 * framed with different colors to show whether the difference is an incoming,
 * outgoing, or conflicting change. The <code>TextMergeViewer</code> supports
 * the notion of a current "differing range" and provides toolbar buttons to
 * navigate from one range to the next (or previous).
 * <p>
 * If there is a current "differing range" and the underlying document is
 * editable the <code>TextMergeViewer</code> enables actions in context menu and
 * toolbar to copy a range from one side to the other side, thereby performing a
 * merge operation.
 * <p>
 * In addition to a line-by-line comparison the <code>TextMergeViewer</code>
 * uses a token based compare on differing lines. The token compare is activated
 * when navigating into a range of differing lines. At first the lines are
 * selected as a block. When navigating into this block the token compare shows
 * for every line the differing token by selecting them.
 * <p>
 * The <code>TextMergeViewer</code>'s default token compare works on characters
 * separated by whitespace. If a different strategy is needed (for example, Java
 * tokens in a Java-aware merge viewer), clients can create their own token
 * comparators by implementing the <code>ITokenComparator</code> interface and
 * overriding the <code>TextMergeViewer.createTokenComparator</code> factory
 * method).
 * <p>
 * Access to the <code>TextMergeViewer</code>'s model is by means of an
 * <code>IMergeViewerContentProvider</code>. Its <code>get<it>X</it></code>
 * Content</code> methods must return either an <code>IDocument</code>, an
 * <code>IDocumentRange</code>, or an <code>IStreamContentAccessor</code>. In
 * the <code>IDocumentRange</code> case the <code>TextMergeViewer</code> works
 * on a subrange of a document. In the <code>IStreamContentAccessor</code> case
 * a document is created internally and initialized from the stream.
 * <p>
 * A <code>TextMergeViewer</code> can be used as is. However clients may
 * subclass to customize the behavior. For example a
 * <code>MergeTextViewer</code> for Java would override the
 * <code>configureTextViewer</code> method to configure the
 * <code>TextViewer</code> for Java source code, the
 * <code>createTokenComparator</code> method to create a Java specific
 * tokenizer.
 * 
 * @see org.eclipse.compare.rangedifferencer.RangeDifferencer
 * @see org.eclipse.jface.text.TextViewer
 * @see ITokenComparator
 * @see IDocumentRange
 * @see org.eclipse.compare.IStreamContentAccessor
 */
public class TextMergeViewer extends ContentMergeViewer implements IAdaptable {

	private static final String COPY_LEFT_TO_RIGHT_INDICATOR = ">"; //$NON-NLS-1$
	private static final String COPY_RIGHT_TO_LEFT_INDICATOR = "<"; //$NON-NLS-1$
	private static final char ANCESTOR_CONTRIBUTOR = MergeViewerContentProvider.ANCESTOR_CONTRIBUTOR;
	private static final char RIGHT_CONTRIBUTOR = MergeViewerContentProvider.RIGHT_CONTRIBUTOR;
	private static final char LEFT_CONTRIBUTOR = MergeViewerContentProvider.LEFT_CONTRIBUTOR;

	private static final String DIFF_RANGE_CATEGORY = CompareUIPlugin.PLUGIN_ID
			+ ".DIFF_RANGE_CATEGORY"; //$NON-NLS-1$

	static final boolean DEBUG = false;

	private static final boolean FIX_47640 = true;

	private static final String[] GLOBAL_ACTIONS = {
			ActionFactory.UNDO.getId(), ActionFactory.REDO.getId(),
			ActionFactory.CUT.getId(), ActionFactory.COPY.getId(),
			ActionFactory.PASTE.getId(), ActionFactory.DELETE.getId(),
			ActionFactory.SELECT_ALL.getId(), ActionFactory.SAVE.getId(),
			ActionFactory.FIND.getId() };
	private static final String[] TEXT_ACTIONS = { MergeSourceViewer.UNDO_ID,
			MergeSourceViewer.REDO_ID, MergeSourceViewer.CUT_ID,
			MergeSourceViewer.COPY_ID, MergeSourceViewer.PASTE_ID,
			MergeSourceViewer.DELETE_ID, MergeSourceViewer.SELECT_ALL_ID,
			MergeSourceViewer.SAVE_ID, MergeSourceViewer.FIND_ID };

	private static final String BUNDLE_NAME = "org.eclipse.compare.contentmergeviewer.TextMergeViewerResources"; //$NON-NLS-1$

	// the following symbolic constants must match the IDs in Compare's
	// plugin.xml
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

	/** line width of change borders */
	private static final int LW = 1;

	// determines whether a change between left and right is considered incoming
	// or outgoing
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
	private RGB INCOMING_TEXT_FILL;

	private RGB SELECTED_CONFLICT;
	private RGB CONFLICT;
	private RGB CONFLICT_FILL;
	private RGB CONFLICT_TEXT_FILL;

	private RGB SELECTED_OUTGOING;
	private RGB OUTGOING;
	private RGB OUTGOING_FILL;
	private RGB OUTGOING_TEXT_FILL;

	private RGB RESOLVED;

	private IPreferenceStore fPreferenceStore;
	private IPropertyChangeListener fPreferenceChangeListener;

	private HashMap fNewAncestorRanges = new HashMap();
	private HashMap fNewLeftRanges = new HashMap();
	private HashMap fNewRightRanges = new HashMap();

	private MergeSourceViewer fAncestor;
	private MergeSourceViewer fLeft;
	private MergeSourceViewer fRight;

	private int fLeftLineCount;
	private int fRightLineCount;

	private boolean fInScrolling;

	private int fPts[] = new int[8]; // scratch area for polygon drawing

	private int fInheritedDirection; // inherited direction
	private int fTextDirection; // requested direction for embedded SourceViewer

	private ActionContributionItem fIgnoreAncestorItem;
	private boolean fHighlightRanges;

	private boolean fShowPseudoConflicts = false;

	private boolean fUseSplines = true;
	private boolean fUseSingleLine = true;
	private boolean fUseResolveUI = true;
	private boolean fHighlightTokenChanges = false;

	private String fSymbolicFontName;

	private ActionContributionItem fNextDiff; // goto next difference
	private ActionContributionItem fPreviousDiff; // goto previous difference
	private ActionContributionItem fCopyDiffLeftToRightItem;
	private ActionContributionItem fCopyDiffRightToLeftItem;

	private CompareHandlerService fHandlerService;

	private boolean fSynchronizedScrolling = true;

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

	private ContributorInfo fLeftContributor;
	private ContributorInfo fRightContributor;
	private ContributorInfo fAncestorContributor;
	private boolean isRefreshing;
	private int fSynchronziedScrollPosition;
	private ActionContributionItem fNextChange;
	private ActionContributionItem fPreviousChange;
	private ShowWhitespaceAction showWhitespaceAction;
	private InternalOutlineViewerCreator fOutlineViewerCreator;
	private TextEditorPropertyAction toggleLineNumbersAction;
	private IFindReplaceTarget fFindReplaceTarget;
	private ChangePropertyAction fIgnoreWhitespace;
	private DocumentMerger fMerger;
	/** The current diff */
	private Diff fCurrentDiff;

	private final class InternalOutlineViewerCreator extends
			OutlineViewerCreator implements ISelectionChangedListener {
		public Viewer findStructureViewer(Viewer oldViewer,
				ICompareInput input, Composite parent,
				CompareConfiguration configuration) {
			if (input != getInput())
				return null;
			final Viewer v = CompareUI.findStructureViewer(oldViewer, input,
					parent, configuration);
			if (v != null) {
				v.getControl().addDisposeListener(new DisposeListener() {
					public void widgetDisposed(DisposeEvent e) {
						v.removeSelectionChangedListener(InternalOutlineViewerCreator.this);
					}
				});
				v.addSelectionChangedListener(this);
			}

			return v;
		}

		public boolean hasViewerFor(Object input) {
			return true;
		}

		public void selectionChanged(SelectionChangedEvent event) {
			ISelection s = event.getSelection();
			if (s instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) s;
				Object element = ss.getFirstElement();
				Diff diff = findDiff(element);
				if (diff != null)
					setCurrentDiff(diff, true);
			}
		}

		private Diff findDiff(Object element) {
			if (element instanceof ICompareInput) {
				ICompareInput ci = (ICompareInput) element;
				Position p = getPosition(ci.getLeft());
				if (p != null)
					return findDiff(p, true);
				p = getPosition(ci.getRight());
				if (p != null)
					return findDiff(p, false);
			}
			return null;
		}

		private Diff findDiff(Position p, boolean left) {
			for (Iterator iterator = fMerger.rangesIterator(); iterator
					.hasNext();) {
				Diff diff = (Diff) iterator.next();
				Position diffPos;
				if (left) {
					diffPos = diff.getPosition(LEFT_CONTRIBUTOR);
				} else {
					diffPos = diff.getPosition(RIGHT_CONTRIBUTOR);
				}
				// If the element falls within a diff, highlight that diff
				if (diffPos.offset + diffPos.length >= p.offset
						&& diff.getKind() != RangeDifference.NOCHANGE)
					return diff;
				// Otherwise, highlight the first diff after the elements
				// position
				if (diffPos.offset >= p.offset)
					return diff;
			}
			return null;
		}

		private Position getPosition(ITypedElement left) {
			if (left instanceof DocumentRangeNode) {
				DocumentRangeNode drn = (DocumentRangeNode) left;
				return drn.getRange();
			}
			return null;
		}

		public Object getInput() {
			return TextMergeViewer.this.getInput();
		}
	}

	class ContributorInfo implements IElementStateListener, VerifyListener,
			IDocumentListener {
		private final TextMergeViewer fViewer;
		private final Object fElement;
		private char fLeg;
		private String fEncoding;
		private IDocumentProvider fDocumentProvider;
		private IEditorInput fDocumentKey;
		private ISelection fSelection;
		private int fTopIndex = -1;
		private boolean fNeedsValidation = false;
		private MergeSourceViewer fSourceViewer;

		public ContributorInfo(TextMergeViewer viewer, Object element, char leg) {
			fViewer = viewer;
			fElement = element;
			fLeg = leg;
			if (fElement instanceof IEncodedStreamContentAccessor) {
				try {
					fEncoding = ((IEncodedStreamContentAccessor) fElement)
							.getCharset();
				} catch (CoreException e) {
					// silently ignored
				}
			}
		}

		public String getEncoding() {
			if (fEncoding == null)
				return ResourcesPlugin.getEncoding();
			return fEncoding;
		}

		public void setEncodingIfAbsent(ContributorInfo otherContributor) {
			if (fEncoding == null)
				fEncoding = otherContributor.fEncoding;
		}

		public IDocument getDocument() {
			if (fDocumentProvider != null) {
				IDocument document = fDocumentProvider
						.getDocument(getDocumentKey());
				if (document != null)
					return document;
			}
			if (fElement instanceof IDocument)
				return (IDocument) fElement;
			if (fElement instanceof IDocumentRange)
				return ((IDocumentRange) fElement).getDocument();
			if (fElement instanceof IStreamContentAccessor)
				return DocumentManager.get(fElement);
			return null;
		}

		public void setDocument(MergeSourceViewer viewer, boolean isEditable) {
			// Ensure that this method is only called once
			Assert.isTrue(fSourceViewer == null);
			fSourceViewer = viewer;
			try {
				internalSetDocument(viewer);
			} catch (RuntimeException e) {
				// The error may be due to a stale entry in the DocumentManager
				// (see bug 184489)
				clearCachedDocument();
				throw e;
			}
			viewer.setEditable(isEditable);
			// Verify changes if the document is editable
			if (isEditable) {
				fNeedsValidation = true;
				viewer.getTextWidget().addVerifyListener(this);
			}
		}

		/*
		 * Returns true if a new Document could be installed.
		 */
		private boolean internalSetDocument(MergeSourceViewer tp) {

			if (tp == null)
				return false;

			// since Coloring in preview is mixed up we should
			// unconfigure this before setting new document
			tp.unconfigure();

			IDocument newDocument = null;
			Position range = null;

			if (fElement instanceof IDocumentRange) {
				newDocument = ((IDocumentRange) fElement).getDocument();
				range = ((IDocumentRange) fElement).getRange();
				connectToSharedDocument();

			} else if (fElement instanceof IDocument) {
				newDocument = (IDocument) fElement;

			} else if (fElement instanceof IStreamContentAccessor) {
				newDocument = DocumentManager.get(fElement);
				if (newDocument == null) {
					newDocument = createDocument();
					DocumentManager.put(fElement, newDocument);
					setupDocument(newDocument);
				} else if (fDocumentProvider == null) {
					// Connect to a shared document so we can get the proper
					// save synchronization
					connectToSharedDocument();
				}
			} else if (fElement == null) { // deletion on one side

				ITypedElement parent = this.fViewer.getParent(fLeg); // we try
																		// to
																		// find
																		// an
																		// insertion
																		// position
																		// within
																		// the
																		// deletion's
																		// parent

				if (parent instanceof IDocumentRange) {
					newDocument = ((IDocumentRange) parent).getDocument();
					newDocument.addPositionCategory(DIFF_RANGE_CATEGORY);
					Object input = this.fViewer.getInput();
					range = this.fViewer.getNewRange(fLeg, input);
					if (range == null) {
						int pos = 0;
						if (input instanceof ICompareInput)
							pos = this.fViewer.findInsertionPosition(fLeg,
									(ICompareInput) input);
						range = new Position(pos, 0);
						try {
							newDocument.addPosition(DIFF_RANGE_CATEGORY, range);
						} catch (BadPositionCategoryException ex) {
							// silently ignored
							if (TextMergeViewer.DEBUG)
								System.out
										.println("BadPositionCategoryException: " + ex); //$NON-NLS-1$
						} catch (BadLocationException ex) {
							// silently ignored
							if (TextMergeViewer.DEBUG)
								System.out
										.println("BadLocationException: " + ex); //$NON-NLS-1$
						}
						this.fViewer.addNewRange(fLeg, input, range);
					}
				} else if (parent instanceof IDocument) {
					newDocument = ((IDocumentRange) fElement).getDocument();
				}
			}

			boolean enabled = true;
			if (newDocument == null) {
				newDocument = new Document(""); //$NON-NLS-1$
				enabled = false;
			}

			// Update the viewer document or range
			IDocument oldDoc = tp.getDocument();
			if (newDocument != oldDoc) {
				updateViewerDocument(tp, newDocument, range);
			} else { // same document but different range
				updateViewerDocumentRange(tp, range);
			}
			newDocument.addDocumentListener(this);

			// since Coloring in preview is mixed up we should
			// re-configure this after setting new document
			if (newDocument instanceof IStructuredDocument) {
				tp.setDocument(newDocument);
				tp.configure(new PHPStructuredTextViewerConfiguration());
			}

			tp.setEnabled(enabled);

			return enabled;
		}

		/*
		 * The viewer document is the same but the range has changed
		 */
		private void updateViewerDocumentRange(MergeSourceViewer tp,
				Position range) {
			tp.setRegion(range);
			if (this.fViewer.fSubDoc) {
				if (range != null) {
					IRegion r = this.fViewer.normalizeDocumentRegion(
							tp.getDocument(), TextMergeViewer.toRegion(range));
					tp.setVisibleRegion(r.getOffset(), r.getLength());
				} else
					tp.resetVisibleRegion();
			} else
				tp.resetVisibleRegion();
		}

		/*
		 * The viewer has a new document
		 */
		private void updateViewerDocument(MergeSourceViewer tp,
				IDocument document, Position range) {
			unsetDocument(tp);
			if (document == null)
				return;

			// Add a position updater to the document
			document.addPositionCategory(DIFF_RANGE_CATEGORY);
			if (this.fViewer.fPositionUpdater == null)
				this.fViewer.fPositionUpdater = this.fViewer.new ChildPositionUpdater(
						DIFF_RANGE_CATEGORY);
			else
				document.removePositionUpdater(this.fViewer.fPositionUpdater);
			document.addPositionUpdater(this.fViewer.fPositionUpdater);

			// install new document
			tp.setRegion(range);
			if (this.fViewer.fSubDoc) {
				if (range != null) {
					IRegion r = this.fViewer.normalizeDocumentRegion(document,
							TextMergeViewer.toRegion(range));
					tp.setDocument(document, r.getOffset(), r.getLength());
				} else
					tp.setDocument(document);
			} else
				tp.setDocument(document);

			tp.rememberDocument(document);
		}

		private void unsetDocument(MergeSourceViewer tp) {
			IDocument oldDoc = internalGetDocument(tp);
			if (oldDoc != null) {
				tp.rememberDocument(null);
				try {
					oldDoc.removePositionCategory(DIFF_RANGE_CATEGORY);
				} catch (BadPositionCategoryException ex) {
					// Ignore
				}
				if (fPositionUpdater != null)
					oldDoc.removePositionUpdater(fPositionUpdater);
				oldDoc.removeDocumentListener(this);
			}
		}

		private IDocument createDocument() {
			// If the content provider is a text content provider, attempt to
			// obtain
			// a shared document (i.e. file buffer)
			IDocument newDoc = connectToSharedDocument();

			if (newDoc == null || !(newDoc instanceof IStructuredDocument)) {
				IStreamContentAccessor sca = (IStreamContentAccessor) fElement;
				String s = null;

				try {
					String encoding = getEncoding();
					s = Utilities.readString(sca, encoding);
				} catch (CoreException ex) {
					this.fViewer.setError(fLeg, ex.getMessage());
				}

				//				newDoc= new Document(s != null ? s : ""); //$NON-NLS-1$
				newDoc = StructuredDocumentFactory
						.getNewStructuredDocumentInstance(new PhpSourceParser());
				newDoc.set(s != null ? s : ""); //$NON-NLS-1$
				// newDoc= new Document(s != null ? s : ""); //$NON-NLS-1$
				IDocumentPartitioner partitioner = getDocumentPartitioner();
				if (partitioner != null) {
					if (newDoc instanceof JobSafeStructuredDocument) {
						((JobSafeStructuredDocument) newDoc)
								.setDocumentPartitioner(
										"org.eclipse.wst.sse.core.default_structured_text_partitioning", partitioner); //$NON-NLS-1$
					} else {
						newDoc.setDocumentPartitioner(partitioner);
					}
					partitioner.connect(newDoc);
				}
			}
			return newDoc;
		}

		/**
		 * Connect to a shared document if possible. Return <code>null</code> if
		 * the connection was not possible.
		 * 
		 * @return the shared document or <code>null</code> if connection to a
		 *         shared document was not possible
		 */
		private IDocument connectToSharedDocument() {
			IEditorInput key = getDocumentKey();
			if (key != null) {
				if (fDocumentProvider != null) {
					// We've already connected and setup the document
					return fDocumentProvider.getDocument(key);
				}
				IDocumentProvider documentProvider = getDocumentProvider();
				if (documentProvider != null) {
					try {
						connect(documentProvider, key);
						setCachedDocumentProvider(key, documentProvider);
						IDocument newDoc = documentProvider.getDocument(key);
						this.fViewer.updateDirtyState(key, documentProvider,
								fLeg);
						return newDoc;
					} catch (CoreException e) {
						// Connection failed. Log the error and continue without
						// a shared document
						CompareUIPlugin.log(e);
					}
				}
			}
			return null;
		}

		private void connect(IDocumentProvider documentProvider,
				IEditorInput input) throws CoreException {
			final ISharedDocumentAdapter sda = (ISharedDocumentAdapter) Utilities
					.getAdapter(fElement, ISharedDocumentAdapter.class);
			if (sda != null) {
				sda.connect(documentProvider, input);
			} else {
				documentProvider.connect(input);
			}
		}

		private void disconnect(IDocumentProvider provider, IEditorInput input) {
			final ISharedDocumentAdapter sda = (ISharedDocumentAdapter) Utilities
					.getAdapter(fElement, ISharedDocumentAdapter.class);
			if (sda != null) {
				sda.disconnect(provider, input);
			} else {
				provider.disconnect(input);
			}
		}

		private void setCachedDocumentProvider(IEditorInput key,
				IDocumentProvider documentProvider) {
			fDocumentKey = key;
			fDocumentProvider = documentProvider;
			documentProvider.addElementStateListener(this);
		}

		public void disconnect() {
			IDocumentProvider provider = null;
			IEditorInput input = getDocumentKey();
			synchronized (this) {
				if (fDocumentProvider != null) {
					provider = fDocumentProvider;
					fDocumentProvider = null;
					fDocumentKey = null;
				}
			}
			if (provider != null) {
				disconnect(provider, input);
				provider.removeElementStateListener(this);
			}
			// If we have a listener registered with the widget, remove it
			if (fSourceViewer != null
					&& !fSourceViewer.getTextWidget().isDisposed()) {
				if (fNeedsValidation) {
					fSourceViewer.getTextWidget().removeVerifyListener(this);
					fNeedsValidation = false;
				}
				IDocument oldDoc = internalGetDocument(fSourceViewer);
				if (oldDoc != null) {
					oldDoc.removeDocumentListener(this);
				}
			}
			clearCachedDocument();
		}

		private void clearCachedDocument() {
			// Finally, remove the document from the document manager
			IDocument doc = DocumentManager.get(fElement);
			if (doc != null)
				DocumentManager.remove(doc);
		}

		private IDocument internalGetDocument(MergeSourceViewer tp) {
			IDocument oldDoc = tp.getDocument();
			if (oldDoc == null) {
				oldDoc = tp.getRememberedDocument();
			}
			return oldDoc;
		}

		/**
		 * Return the document key used to obtain a shared document. A
		 * <code>null</code> is returned in the following cases:
		 * <ol>
		 * <li>This contributor does not have a shared document adapter.</li>
		 * <li>This text merge viewer has a document partitioner but uses the
		 * default partitioning.</li>
		 * <li>This text merge viewer does not use he default content provider.</li>
		 * </ol>
		 * 
		 * @return the document key used to obtain a shared document or
		 *         <code>null</code>
		 */
		private IEditorInput getDocumentKey() {
			if (fDocumentKey != null)
				return fDocumentKey;
			if (isUsingDefaultContentProvider() && fElement != null
					&& canHaveSharedDocument()) {
				ISharedDocumentAdapter sda = (ISharedDocumentAdapter) Utilities
						.getAdapter(fElement, ISharedDocumentAdapter.class,
								true);
				if (sda != null) {
					return sda.getDocumentKey(fElement);
				}
			}
			return null;
		}

		private IDocumentProvider getDocumentProvider() {
			if (fDocumentProvider != null)
				return fDocumentProvider;
			// We will only use document providers if the content provider is
			// the
			// default content provider
			if (isUsingDefaultContentProvider()) {
				IEditorInput input = getDocumentKey();
				if (input != null)
					return SharedDocumentAdapter.getDocumentProvider(input);
			}
			return null;
		}

		private boolean isUsingDefaultContentProvider() {
			return fViewer.isUsingDefaultContentProvider();
		}

		private boolean canHaveSharedDocument() {
			return fViewer.canHaveSharedDocument();
		}

		boolean hasSharedDocument(Object object) {
			return (fElement == object && fDocumentProvider != null && fDocumentProvider
					.getDocument(getDocumentKey()) != null);
		}

		public boolean flush() throws CoreException {
			if (fDocumentProvider != null) {
				IEditorInput input = getDocumentKey();
				IDocument document = fDocumentProvider.getDocument(input);
				if (document != null) {
					final ISharedDocumentAdapter sda = (ISharedDocumentAdapter) Utilities
							.getAdapter(fElement, ISharedDocumentAdapter.class);
					if (sda != null) {
						sda.flushDocument(fDocumentProvider, input, document,
								false);
						return true;
					}
					try {
						fDocumentProvider.aboutToChange(input);
						fDocumentProvider.saveDocument(
								new NullProgressMonitor(), input, document,
								false);
						return true;
					} finally {
						fDocumentProvider.changed(input);
					}
				}
			}
			return false;
		}

		public void elementMoved(Object originalElement, Object movedElement) {
			IEditorInput input = getDocumentKey();
			if (input != null && input.equals(originalElement)) {
				// This method will only get called if the buffer is not dirty
				resetDocument();
			}
		}

		public void elementDirtyStateChanged(Object element, boolean isDirty) {
			if (!checkState())
				return;
			IEditorInput input = getDocumentKey();
			if (input != null && input.equals(element)) {
				this.fViewer.updateDirtyState(input, getDocumentProvider(),
						fLeg);
			}
		}

		public void elementDeleted(Object element) {
			IEditorInput input = getDocumentKey();
			if (input != null && input.equals(element)) {
				// This method will only get called if the buffer is not dirty
				resetDocument();
			}
		}

		private void resetDocument() {
			// Need to remove the document from the manager before refreshing
			// or the old document will still be found
			clearCachedDocument();
			// TODO: This is fine for now but may need to be revisited if a
			// refresh is performed
			// higher up as well (e.g. perhaps a refresh request that waits
			// until after all parties
			// have been notified).
			if (checkState())
				fViewer.refresh();
		}

		private boolean checkState() {
			if (fViewer == null)
				return false;
			Control control = fViewer.getControl();
			if (control == null)
				return false;
			return !control.isDisposed();
		}

		public void elementContentReplaced(Object element) {
			if (!checkState())
				return;
			IEditorInput input = getDocumentKey();
			if (input != null && input.equals(element)) {
				this.fViewer.updateDirtyState(input, getDocumentProvider(),
						fLeg);
			}
		}

		public void elementContentAboutToBeReplaced(Object element) {
			// Nothing to do
		}

		public Object getElement() {
			return fElement;
		}

		public void cacheSelection(MergeSourceViewer viewer) {
			if (viewer == null) {
				this.fSelection = null;
				this.fTopIndex = -1;
			} else {
				this.fSelection = viewer.getSelection();
				this.fTopIndex = viewer.getTopIndex();
			}
		}

		public void updateSelection(MergeSourceViewer viewer,
				boolean includeScroll) {
			if (fSelection != null)
				viewer.setSelection(fSelection);
			if (includeScroll && fTopIndex != -1) {
				viewer.setTopIndex(fTopIndex);
			}
		}

		public void transferContributorStateFrom(ContributorInfo oldContributor) {
			if (oldContributor != null) {
				fSelection = oldContributor.fSelection;
				fTopIndex = oldContributor.fTopIndex;
			}

		}

		public boolean validateChange() {
			if (fElement == null)
				return true;
			if (fDocumentProvider instanceof IDocumentProviderExtension) {
				IDocumentProviderExtension ext = (IDocumentProviderExtension) fDocumentProvider;
				if (ext.isReadOnly(fDocumentKey)) {
					try {
						ext.validateState(fDocumentKey, getControl().getShell());
						ext.updateStateCache(fDocumentKey);
					} catch (CoreException e) {
						ErrorDialog.openError(getControl().getShell(),
								CompareMessages.TextMergeViewer_12,
								CompareMessages.TextMergeViewer_13,
								e.getStatus());
						return false;
					}
				}
				return !ext.isReadOnly(fDocumentKey);
			}
			IEditableContentExtension ext = (IEditableContentExtension) Utilities
					.getAdapter(fElement, IEditableContentExtension.class);
			if (ext != null) {
				if (ext.isReadOnly()) {
					IStatus status = ext.validateEdit(getControl().getShell());
					if (!status.isOK()) {
						if (status.getSeverity() == IStatus.ERROR) {
							ErrorDialog.openError(getControl().getShell(),
									CompareMessages.TextMergeViewer_14,
									CompareMessages.TextMergeViewer_15, status);
							return false;
						}
						if (status.getSeverity() == IStatus.CANCEL)
							return false;
					}
				}
			}
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.
		 * events.VerifyEvent)
		 */
		public void verifyText(VerifyEvent e) {
			if (!validateChange()) {
				e.doit = false;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged
		 * (org.eclipse.jface.text.DocumentEvent)
		 */
		public void documentAboutToBeChanged(DocumentEvent e) {
			// nothing to do
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse
		 * .jface.text.DocumentEvent)
		 */
		public void documentChanged(DocumentEvent e) {
			boolean dirty = true;
			if (fDocumentProvider != null && fDocumentKey != null) {
				dirty = fDocumentProvider.canSaveDocument(fDocumentKey);
			}
			TextMergeViewer.this.documentChanged(e, dirty);
			// Remove our verify listener since the document is now dirty
			if (fNeedsValidation && fSourceViewer != null
					&& !fSourceViewer.getTextWidget().isDisposed()) {
				fSourceViewer.getTextWidget().removeVerifyListener(this);
				fNeedsValidation = false;
			}
		}
	}

	class HeaderPainter implements PaintListener {

		private static final int INSET = BIRDS_EYE_VIEW_INSET;

		private RGB fIndicatorColor;
		private Color fSeparatorColor;

		public HeaderPainter() {
			fSeparatorColor = fSummaryHeader.getDisplay().getSystemColor(
					SWT.COLOR_WIDGET_NORMAL_SHADOW);
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

		private void drawBevelRect(GC gc, int x, int y, int w, int h,
				Color topLeft, Color bottomRight) {
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
				Rectangle r = new Rectangle((s.x - min) / 2, (s.y - min) / 2,
						min, min);
				e.gc.fillRectangle(r);
				if (d != null)
					drawBevelRect(e.gc, r.x, r.y, r.width - 1, r.height - 1,
							d.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW),
							d.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));

				e.gc.setForeground(fSeparatorColor);
				e.gc.setLineWidth(0 /* 1 */);
				e.gc.drawLine(0 + 1, s.y - 1, s.x - 1 - 1, s.y - 1);
			}
		}
	}

	/*
	 * The position updater used to adapt the positions representing the child
	 * document ranges to changes of the parent document.
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

			if (fPosition == fLeft.getRegion()
					|| fPosition == fRight.getRegion()) {
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

	private class ChangeHighlighter implements ITextPresentationListener {

		private final MergeSourceViewer viewer;

		public ChangeHighlighter(MergeSourceViewer viewer) {
			this.viewer = viewer;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.text.ITextPresentationListener#applyTextPresentation
		 * (org.eclipse.jface.text.TextPresentation)
		 */
		public void applyTextPresentation(TextPresentation textPresentation) {
			if (!fHighlightTokenChanges)
				return;
			IRegion region = textPresentation.getExtent();
			Diff[] changeDiffs = fMerger.getChangeDiffs(getLeg(viewer), region);
			for (int i = 0; i < changeDiffs.length; i++) {
				Diff diff = changeDiffs[i];
				StyleRange range = getStyleRange(diff, region);
				if (range != null)
					textPresentation.mergeStyleRange(range);
			}
		}

		private StyleRange getStyleRange(Diff diff, IRegion region) {
			// Color cText = getColor(null, getTextColor());
			Color cTextFill = getColor(null, getTextFillColor(diff));
			if (cTextFill == null)
				return null;
			Position p = diff.getPosition(getLeg(viewer));
			int start = p.getOffset();
			int length = p.getLength();
			// Don't start before the region
			if (start < region.getOffset()) {
				length = length - (region.getOffset() - start);
				start = region.getOffset();
			}
			// Don't go past the end of the region
			int regionEnd = region.getOffset() + region.getLength();
			if (start + length > regionEnd) {
				length = regionEnd - start;
			}
			if (length < 0)
				return null;

			return new StyleRange(start, length, null, cTextFill);
		}

		private RGB getTextFillColor(Diff diff) {
			if (isThreeWay() && !isIgnoreAncestor()) {
				switch (diff.getKind()) {
				case RangeDifference.RIGHT:
					if (fLeftIsLocal)
						return INCOMING_TEXT_FILL;
					return OUTGOING_TEXT_FILL;
				case RangeDifference.ANCESTOR:
					return CONFLICT_TEXT_FILL;
				case RangeDifference.LEFT:
					if (fLeftIsLocal)
						return OUTGOING_TEXT_FILL;
					return INCOMING_TEXT_FILL;
				case RangeDifference.CONFLICT:
					return CONFLICT_TEXT_FILL;
				}
				return null;
			}
			return OUTGOING_TEXT_FILL;
		}

	}

	private class FindReplaceTarget implements IFindReplaceTarget {

		public boolean canPerformFind() {
			return fFocusPart != null;
		}

		public int findAndSelect(int widgetOffset, String findString,
				boolean searchForward, boolean caseSensitive, boolean wholeWord) {
			return fFocusPart.getFindReplaceTarget().findAndSelect(
					widgetOffset, findString, searchForward, caseSensitive,
					wholeWord);
		}

		public Point getSelection() {
			return fFocusPart.getFindReplaceTarget().getSelection();
		}

		public String getSelectionText() {
			return fFocusPart.getFindReplaceTarget().getSelectionText();
		}

		public boolean isEditable() {
			return fFocusPart.getFindReplaceTarget().isEditable();
		}

		public void replaceSelection(String text) {
			fFocusPart.getFindReplaceTarget().replaceSelection(text);
		}

	}

	// ---- MergeTextViewer

	/**
	 * Creates a text merge viewer under the given parent control.
	 * 
	 * @param parent
	 *            the parent control
	 * @param configuration
	 *            the configuration object
	 */
	public TextMergeViewer(Composite parent, CompareConfiguration configuration) {
		this(parent, SWT.NULL, configuration);
	}

	/**
	 * Creates a text merge viewer under the given parent control.
	 * 
	 * @param parent
	 *            the parent control
	 * @param style
	 *            SWT style bits for top level composite of this viewer
	 * @param configuration
	 *            the configuration object
	 */
	public TextMergeViewer(Composite parent, int style,
			CompareConfiguration configuration) {
		super(style, ResourceBundle.getBundle(BUNDLE_NAME), configuration);

		fMerger = new DocumentMerger(new IDocumentMergerInput() {
			public ITokenComparator createTokenComparator(String line) {
				return TextMergeViewer.this.createTokenComparator(line);
			}

			public CompareConfiguration getCompareConfiguration() {
				return TextMergeViewer.this.getCompareConfiguration();
			}

			public IDocument getDocument(char contributor) {
				switch (contributor) {
				case LEFT_CONTRIBUTOR:
					return fLeft.getDocument();
				case RIGHT_CONTRIBUTOR:
					return fRight.getDocument();
				case ANCESTOR_CONTRIBUTOR:
					return fAncestor.getDocument();
				}
				return null;
			}

			public int getHunkStart() {
				return TextMergeViewer.this.getHunkStart();
			}

			public Position getRegion(char contributor) {
				switch (contributor) {
				case LEFT_CONTRIBUTOR:
					return fLeft.getRegion();
				case RIGHT_CONTRIBUTOR:
					return fRight.getRegion();
				case ANCESTOR_CONTRIBUTOR:
					return fAncestor.getRegion();
				}
				return null;
			}

			public boolean isHunkOnLeft() {
				ITypedElement left = ((ICompareInput) getInput()).getRight();
				return left != null
						&& Utilities.getAdapter(left, IHunk.class) != null;
			}

			public boolean isIgnoreAncestor() {
				return TextMergeViewer.this.isIgnoreAncestor();
			}

			public boolean isPatchHunk() {
				return TextMergeViewer.this.isPatchHunk();
			}

			public boolean isShowPseudoConflicts() {
				return fShowPseudoConflicts;
			}

			public boolean isThreeWay() {
				return TextMergeViewer.this.isThreeWay();
			}

			public boolean isPatchHunkOk() {
				return TextMergeViewer.this.isPatchHunkOk();
			}

		});

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
				TextMergeViewer.this.handlePropertyChangeEvent(event);
			}
		};

		fPreferenceStore = createChainedPreferenceStore();
		if (fPreferenceStore != null) {
			fPreferenceStore
					.addPropertyChangeListener(fPreferenceChangeListener);

			checkForColorUpdate(display);

			fLeftIsLocal = Utilities.getBoolean(getCompareConfiguration(),
					"LEFT_IS_LOCAL", false); //$NON-NLS-1$
			fSynchronizedScrolling = fPreferenceStore
					.getBoolean(ComparePreferencePage.SYNCHRONIZE_SCROLLING);
			fShowPseudoConflicts = fPreferenceStore
					.getBoolean(ComparePreferencePage.SHOW_PSEUDO_CONFLICTS);
			// fUseSplines=
			// fPreferenceStore.getBoolean(ComparePreferencePage.USE_SPLINES);
			fUseSingleLine = fPreferenceStore
					.getBoolean(ComparePreferencePage.USE_SINGLE_LINE);
			fHighlightTokenChanges = fPreferenceStore
					.getBoolean(ComparePreferencePage.HIGHLIGHT_TOKEN_CHANGES);
			// fUseResolveUI=
			// fPreferenceStore.getBoolean(ComparePreferencePage.USE_RESOLVE_UI);
		}

		buildControl(parent);

		INavigatable nav = new INavigatable() {
			public boolean selectChange(int flag) {
				if (flag == INavigatable.FIRST_CHANGE
						|| flag == INavigatable.LAST_CHANGE) {
					selectFirstDiff(flag == INavigatable.FIRST_CHANGE);
					return false;
				}
				return navigate(flag == INavigatable.NEXT_CHANGE, false, false);
			}

			public Object getInput() {
				return TextMergeViewer.this.getInput();
			}

			public boolean openSelectedChange() {
				return false;
			}

			public boolean hasChange(int flag) {
				return getNextVisibleDiff(flag == INavigatable.NEXT_CHANGE,
						false) != null;
			}
		};
		fComposite.setData(INavigatable.NAVIGATOR_PROPERTY, nav);

		fBirdsEyeCursor = new Cursor(parent.getDisplay(), SWT.CURSOR_HAND);

		JFaceResources.getFontRegistry().addListener(fPreferenceChangeListener);
		JFaceResources.getColorRegistry()
				.addListener(fPreferenceChangeListener);
		updateFont();
	}

	private ChainedPreferenceStore createChainedPreferenceStore() {
		ArrayList stores = new ArrayList(2);
		stores.add(getCompareConfiguration().getPreferenceStore());
		stores.add(EditorsUI.getPreferenceStore());
		return new ChainedPreferenceStore(
				(IPreferenceStore[]) stores.toArray(new IPreferenceStore[stores
						.size()]));
	}

	private void updateFont() {
		Font f = JFaceResources.getFont(JFaceResources.TEXT_FONT);
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
			RGB fg = fPreferenceStore
					.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT) ? display
					.getSystemColor(SWT.COLOR_LIST_FOREGROUND).getRGB()
					: new Color(display, PreferenceConverter.getColor(
							fPreferenceStore,
							AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND))
							.getRGB();
			if (fForeground == null || !fg.equals(fForeground)) {
				fForeground = fg;
				updateColors(display);
			}
		}
		if (fPollSystemBackground) {
			RGB bg = fPreferenceStore
					.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT) ? display
					.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB()
					: new Color(display, PreferenceConverter.getColor(
							fPreferenceStore,
							AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND))
							.getRGB();
			if (fBackground == null || !bg.equals(fBackground)) {
				fBackground = bg;
				updateColors(display);
			}
		}
	}

	/**
	 * Sets the viewer's background color to the given RGB value. If the value
	 * is <code>null</code> the system's default background color is used.
	 * 
	 * @param background
	 *            the background color or <code>null</code> to use the system's
	 *            default background color
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
	 * Sets the viewer's foreground color to the given RGB value. If the value
	 * is <code>null</code> the system's default foreground color is used.
	 * 
	 * @param foreground
	 *            the foreground color or <code>null</code> to use the system's
	 *            default foreground color
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
		INCOMING_TEXT_FILL = interpolate(SELECTED_INCOMING, bg, 0.85);

		SELECTED_OUTGOING = registry.getRGB(OUTGOING_COLOR);
		if (SELECTED_OUTGOING == null)
			SELECTED_OUTGOING = new RGB(0, 0, 0); // BLACK
		OUTGOING = interpolate(SELECTED_OUTGOING, bg, 0.6);
		OUTGOING_FILL = interpolate(SELECTED_OUTGOING, bg, 0.97);
		OUTGOING_TEXT_FILL = interpolate(SELECTED_OUTGOING, bg, 0.85);

		SELECTED_CONFLICT = registry.getRGB(CONFLICTING_COLOR);
		if (SELECTED_CONFLICT == null)
			SELECTED_CONFLICT = new RGB(255, 0, 0); // RED
		CONFLICT = interpolate(SELECTED_CONFLICT, bg, 0.6);
		CONFLICT_FILL = interpolate(SELECTED_CONFLICT, bg, 0.97);
		CONFLICT_TEXT_FILL = interpolate(SELECTED_CONFLICT, bg, 0.85);

		RESOLVED = registry.getRGB(RESOLVED_COLOR);
		if (RESOLVED == null)
			RESOLVED = new RGB(0, 255, 0); // GREEN

		updatePresentation(display);
	}

	private void updatePresentation(Display display) {
		if (display == null)
			display = fComposite.getDisplay();
		refreshBirdsEyeView();
		invalidateLines();
		updateAllDiffBackgrounds(display);
		invalidateTextPresentation();
	}

	/**
	 * Invalidates the current presentation by invalidating the three text
	 * viewers.
	 * 
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
	 * Configures the passed text viewer. This method is called after the three
	 * text viewers have been created for the content areas. The
	 * <code>TextMergeViewer</code> implementation of this method will configure
	 * the viewer with a {@link SourceViewerConfiguration}. Subclasses may
	 * reimplement to provide a specific configuration for the text viewer.
	 * 
	 * @param textViewer
	 *            the text viewer to configure
	 */
	protected void configureTextViewer(TextViewer textViewer) {
		// to get undo for all text files
		// bugzilla 131895, 33665
		if (textViewer instanceof MergeSourceViewer) {
			SourceViewerConfiguration configuration = new SourceViewerConfiguration();
			((MergeSourceViewer) textViewer).configure(configuration);
		}
	}

	/**
	 * Creates an <code>ITokenComparator</code> which is used to show the intra
	 * line differences. The <code>TextMergeViewer</code> implementation of this
	 * method returns a tokenizer that breaks a line into words separated by
	 * whitespace. Subclasses may reimplement to provide a specific tokenizer.
	 * 
	 * @param line
	 *            the line for which to create the <code>ITokenComparator</code>
	 * @return a ITokenComparator which is used for a second level token
	 *         compare.
	 */
	protected ITokenComparator createTokenComparator(String line) {
		return new TokenComparator(line);
	}

	/**
	 * Setup the given document for use with this viewer. By default, the
	 * partitioner returned from {@link #getDocumentPartitioner()} is registered
	 * as the default partitioner for the document. Subclasses that return a
	 * partitioner must also override {@link #getDocumentPartitioning()} if they
	 * wish to be able to use shared documents (i.e. file buffers).
	 * 
	 * @param document
	 *            the document to be set up
	 * 
	 * @since 3.3
	 */
	protected void setupDocument(IDocument document) {
		String partitioning = getDocumentPartitioning();
		if (partitioning == null || !(document instanceof IDocumentExtension3)) {
			if (document.getDocumentPartitioner() == null) {
				IDocumentPartitioner partitioner = getDocumentPartitioner();
				if (partitioner != null) {
					document.setDocumentPartitioner(partitioner);
					partitioner.connect(document);
				}
			}
		} else {
			IDocumentExtension3 ex3 = (IDocumentExtension3) document;
			if (ex3.getDocumentPartitioner(partitioning) == null) {
				IDocumentPartitioner partitioner = getDocumentPartitioner();
				if (partitioner != null) {
					ex3.setDocumentPartitioner(partitioning, partitioner);
					partitioner.connect(document);
				}
			}
		}
	}

	/**
	 * Returns a document partitioner which is suitable for the underlying
	 * content type. This method is only called if the input provided by the
	 * content provider is a <code>IStreamContentAccessor</code> and an internal
	 * document must be obtained. This document is initialized with the
	 * partitioner returned from this method.
	 * <p>
	 * The <code>TextMergeViewer</code> implementation of this method returns
	 * <code>null</code>. Subclasses may reimplement to create a partitioner for
	 * a specific content type. Subclasses that do return a partitioner should
	 * also return a partitioning from {@link #getDocumentPartitioning()} in
	 * order to make use of shared documents (e.g. file buffers).
	 * 
	 * @return a document partitioner, or <code>null</code>
	 */
	protected IDocumentPartitioner getDocumentPartitioner() {
		return null;
	}

	/**
	 * Return the partitioning to which the partitioner returned from
	 * {@link #getDocumentPartitioner()} is to be associated. Return
	 * <code>null</code> only if partitioning is not needed or if the subclass
	 * overrode {@link #setupDocument(IDocument)} directly. By default,
	 * <code>null</code> is returned which means that shared documents that
	 * return a partitioner from {@link #getDocumentPartitioner()} will not be
	 * able to use shared documents.
	 * 
	 * @see IDocumentExtension3
	 * @return a partitioning
	 * 
	 * @since 3.3
	 */
	protected String getDocumentPartitioning() {
		return null;
	}

	/**
	 * Called on the viewer disposal. Unregisters from the compare
	 * configuration. Clients may extend if they have to do additional cleanup.
	 * 
	 * @param event
	 */
	protected void handleDispose(DisposeEvent event) {

		if (fHandlerService != null)
			fHandlerService.dispose();

		Object input = getInput();
		removeFromDocumentManager(ANCESTOR_CONTRIBUTOR, input);
		removeFromDocumentManager(LEFT_CONTRIBUTOR, input);
		removeFromDocumentManager(RIGHT_CONTRIBUTOR, input);

		if (DEBUG)
			DocumentManager.dump();

		if (fPreferenceChangeListener != null) {
			JFaceResources.getFontRegistry().removeListener(
					fPreferenceChangeListener);
			JFaceResources.getColorRegistry().removeListener(
					fPreferenceChangeListener);
			if (fPreferenceStore != null)
				fPreferenceStore
						.removePropertyChangeListener(fPreferenceChangeListener);
			fPreferenceChangeListener = null;
		}

		fLeftCanvas = null;
		fRightCanvas = null;
		fVScrollBar = null;
		fBirdsEyeCanvas = null;
		fSummaryHeader = null;

		fAncestorContributor.unsetDocument(fAncestor);
		fLeftContributor.unsetDocument(fLeft);
		fRightContributor.unsetDocument(fRight);

		disconnect(fLeftContributor);
		disconnect(fRightContributor);
		disconnect(fAncestorContributor);

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

		if (showWhitespaceAction != null)
			showWhitespaceAction.dispose();

		if (toggleLineNumbersAction != null)
			toggleLineNumbersAction.dispose();

		if (fIgnoreWhitespace != null)
			fIgnoreWhitespace.dispose();

		super.handleDispose(event);
	}

	private void disconnect(ContributorInfo legInfo) {
		if (legInfo != null)
			legInfo.disconnect();
	}

	// -------------------------------------------------------------------------------------------------------------
	// --- internal
	// ------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------

	/*
	 * Creates the specific SWT controls for the content areas. Clients must not
	 * call or override this method.
	 */
	protected void createControls(Composite composite) {

		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(composite, ICompareContextIds.TEXT_MERGE_VIEW);

		// 1st row
		if (fMarginWidth > 0) {
			fAncestorCanvas = new BufferedCanvas(composite, SWT.NONE) {
				public void doPaint(GC gc) {
					paintSides(gc, fAncestor, fAncestorCanvas, false);
				}
			};
			fAncestorCanvas.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					setCurrentDiff2(
							handleMouseInSides(fAncestorCanvas, fAncestor, e.y),
							false);
				}
			});
		}

		fAncestor = createPart(composite);
		fAncestor.setEditable(false);
		fAncestor.getTextWidget().getAccessible()
				.addAccessibleListener(new AccessibleAdapter() {
					public void getName(AccessibleEvent e) {
						e.result = NLS
								.bind(CompareMessages.TextMergeViewer_accessible_ancestor,
										getCompareConfiguration()
												.getAncestorLabel(getInput()));
					}
				});
		fAncestor.addTextPresentationListener(new ChangeHighlighter(fAncestor));

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
					setCurrentDiff2(
							handleMouseInSides(fLeftCanvas, fLeft, e.y), false);
				}
			});
		}

		fLeft = createPart(composite);
		fLeft.getTextWidget().getVerticalBar()
				.setVisible(!fSynchronizedScrolling);
		fLeft.addAction(MergeSourceViewer.SAVE_ID, fLeftSaveAction);
		fLeft.getTextWidget().getAccessible()
				.addAccessibleListener(new AccessibleAdapter() {
					public void getName(AccessibleEvent e) {
						e.result = NLS
								.bind(CompareMessages.TextMergeViewer_accessible_left,
										getCompareConfiguration().getLeftLabel(
												getInput()));
					}
				});
		fLeft.addTextPresentationListener(new ChangeHighlighter(fLeft));

		fRight = createPart(composite);
		fRight.getTextWidget().getVerticalBar()
				.setVisible(!fSynchronizedScrolling);
		fRight.addAction(MergeSourceViewer.SAVE_ID, fRightSaveAction);
		fRight.getTextWidget().getAccessible()
				.addAccessibleListener(new AccessibleAdapter() {
					public void getName(AccessibleEvent e) {
						e.result = NLS
								.bind(CompareMessages.TextMergeViewer_accessible_right,
										getCompareConfiguration()
												.getRightLabel(getInput()));
					}
				});
		fRight.addTextPresentationListener(new ChangeHighlighter(fRight));

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
					setCurrentDiff2(
							handleMouseInSides(fRightCanvas, fRight, e.y),
							false);
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
				synchronizedScrollVertical(vpos);
			}
		});

		fBirdsEyeCanvas = new BufferedCanvas(composite, SWT.NONE) {
			public void doPaint(GC gc) {
				paintBirdsEyeView(this, gc);
			}
		};
		fBirdsEyeCanvas.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				setCurrentDiff2(
						handlemouseInBirdsEyeView(fBirdsEyeCanvas, e.y), true);
			}
		});
		fBirdsEyeCanvas.addMouseMoveListener(new MouseMoveListener() {

			private Cursor fLastCursor;

			public void mouseMove(MouseEvent e) {
				Cursor cursor = null;
				Diff diff = handlemouseInBirdsEyeView(fBirdsEyeCanvas, e.y);
				if (diff != null && diff.getKind() != RangeDifference.NOCHANGE)
					cursor = fBirdsEyeCursor;
				if (fLastCursor != cursor) {
					fBirdsEyeCanvas.setCursor(cursor);
					fLastCursor = cursor;
				}
			}
		});
	}

	private void hsynchViewport(final TextViewer tv1, final TextViewer tv2,
			final TextViewer tv3) {
		final StyledText st1 = tv1.getTextWidget();
		final StyledText st2 = tv2.getTextWidget();
		final StyledText st3 = tv3.getTextWidget();
		final ScrollBar sb1 = st1.getHorizontalBar();
		sb1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (fSynchronizedScrolling) {
					int v = sb1.getSelection();
					if (st2.isVisible())
						st2.setHorizontalPixel(v);
					if (st3.isVisible())
						st3.setHorizontalPixel(v);
					workaround65205();
				}
			}
		});
	}

	/**
	 * A workaround for bug #65205. On MacOS X a Display.update() is required to
	 * flush pending paint requests after programmatically scrolling.
	 */
	private void workaround65205() {
		if (fIsCarbon && fComposite != null && !fComposite.isDisposed())
			fComposite.getDisplay().update();
	}

	private void setCurrentDiff2(Diff diff, boolean reveal) {
		if (diff != null && diff.getKind() != RangeDifference.NOCHANGE) {
			// fCurrentDiff= null;
			setCurrentDiff(diff, reveal);
		}
	}

	private Diff handleMouseInSides(Canvas canvas, MergeSourceViewer tp, int my) {

		int lineHeight = tp.getTextWidget().getLineHeight();
		int visibleHeight = tp.getViewportHeight();

		if (!fHighlightRanges)
			return null;

		if (fMerger.hasChanges()) {
			int shift = tp.getVerticalScrollOffset() + (2 - LW);

			Point region = new Point(0, 0);
			char leg = getLeg(tp);
			for (Iterator iterator = fMerger.changesIterator(); iterator
					.hasNext();) {
				Diff diff = (Diff) iterator.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				tp.getLineRange(diff.getPosition(leg), region);
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

		if (fMerger.hasChanges()) {
			int lshift = fLeft.getVerticalScrollOffset();
			int rshift = fRight.getVerticalScrollOffset();

			Point region = new Point(0, 0);

			for (Iterator iterator = fMerger.changesIterator(); iterator
					.hasNext();) {
				Diff diff = (Diff) iterator.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				fLeft.getLineRange(diff.getPosition(LEFT_CONTRIBUTOR), region);
				int ly = (region.x * lineHeight) + lshift;
				int lh = region.y * lineHeight;

				fRight.getLineRange(diff.getPosition(RIGHT_CONTRIBUTOR), region);
				int ry = (region.x * lineHeight) + rshift;
				int rh = region.y * lineHeight;

				if (Math.max(ly + lh, ry + rh) < 0)
					continue;
				if (Math.min(ly, ry) >= visibleHeight)
					break;

				int cx = (w - RESOLVE_SIZE) / 2;
				int cy = ((ly + lh / 2) + (ry + rh / 2) - RESOLVE_SIZE) / 2;
				if (my >= cy && my < cy + RESOLVE_SIZE && mx >= cx
						&& mx < cx + RESOLVE_SIZE) {
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
		return fMerger.findDiff(getViewportHeight(), fSynchronizedScrolling,
				canvas.getSize(), my);
	}

	private void paintBirdsEyeView(Canvas canvas, GC gc) {

		Color c;
		Rectangle r = new Rectangle(0, 0, 0, 0);
		int yy, hh;

		Point size = canvas.getSize();

		int virtualHeight = fSynchronizedScrolling ? fMerger.getVirtualHeight()
				: fMerger.getRightHeight();
		if (virtualHeight < getViewportHeight())
			return;

		Display display = canvas.getDisplay();
		int y = 0;
		for (Iterator iterator = fMerger.rangesIterator(); iterator.hasNext();) {
			Diff diff = (Diff) iterator.next();
			int h = fSynchronizedScrolling ? diff.getMaxDiffHeight() : diff
					.getRightHeight();

			if (fMerger.useChange(diff)) {

				yy = (y * size.y) / virtualHeight;
				hh = (h * size.y) / virtualHeight;
				if (hh < 3)
					hh = 3;

				c = getColor(display, getFillColor(diff));
				if (c != null) {
					gc.setBackground(c);
					gc.fillRectangle(BIRDS_EYE_VIEW_INSET, yy, size.x
							- (2 * BIRDS_EYE_VIEW_INSET), hh);
				}
				c = getColor(display, getStrokeColor(diff));
				if (c != null) {
					gc.setForeground(c);
					r.x = BIRDS_EYE_VIEW_INSET;
					r.y = yy;
					r.width = size.x - (2 * BIRDS_EYE_VIEW_INSET) - 1;
					r.height = hh;
					if (isCurrentDiff(diff)) {
						gc.setLineWidth(2);
						r.x++;
						r.y++;
						r.width--;
						r.height--;
					} else {
						gc.setLineWidth(0 /* 1 */);
					}
					gc.drawRectangle(r);
				}
			}

			y += h;
		}
	}

	private void refreshBirdsEyeView() {
		if (fBirdsEyeCanvas != null)
			fBirdsEyeCanvas.redraw();
	}

	/**
	 * Override to give focus to the pane that previously had focus or to a
	 * suitable default pane.
	 * 
	 * @see org.eclipse.compare.contentmergeviewer.ContentMergeViewer#handleSetFocus()
	 * @since 3.3
	 */
	protected boolean handleSetFocus() {
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
			if (!fIsDown && fUseSingleLine && showResolveUI()
					&& handleMouseMoveOverCenter(fCanvas, e.x, e.y))
				return;
			super.mouseMove(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.compare.contentmergeviewer.ContentMergeViewer#createCenterControl
	 * (org.eclipse.swt.widgets.Composite)
	 */
	protected final Control createCenterControl(Composite parent) {
		if (fSynchronizedScrolling) {
			final Canvas canvas = new BufferedCanvas(parent, SWT.NONE) {
				public void doPaint(GC gc) {
					paintCenter(this, gc);
				}
			};
			if (fUseResolveUI) {

				new HoverResizer(canvas, HORIZONTAL);

				fCenterButton = new Button(canvas, fIsCarbon ? SWT.FLAT
						: SWT.PUSH);
				if (fNormalCursor == null)
					fNormalCursor = new Cursor(canvas.getDisplay(),
							SWT.CURSOR_ARROW);
				fCenterButton.setCursor(fNormalCursor);
				fCenterButton.setText(COPY_RIGHT_TO_LEFT_INDICATOR);
				fCenterButton.pack();
				fCenterButton.setVisible(false);
				fCenterButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						fCenterButton.setVisible(false);
						if (fButtonDiff != null) {
							setCurrentDiff(fButtonDiff, false);
							copy(fCurrentDiff,
									fCenterButton.getText().equals(
											COPY_LEFT_TO_RIGHT_INDICATOR),
									fCurrentDiff.getKind() != RangeDifference.CONFLICT);
						}
					}
				});
			} else {
				new Resizer(canvas, HORIZONTAL);
			}

			return canvas;
		}
		return super.createCenterControl(parent);
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
					fCenterButton.setText(COPY_RIGHT_TO_LEFT_INDICATOR);
					String tt = fCopyDiffRightToLeftItem.getAction()
							.getToolTipText();
					fCenterButton.setToolTipText(tt);
					fCenterButton.setBounds(r);
					fCenterButton.setVisible(true);
				} else if (fRight.isEditable()) {
					fButtonDiff = diff;
					fCenterButton.setText(COPY_LEFT_TO_RIGHT_INDICATOR);
					String tt = fCopyDiffLeftToRightItem.getAction()
							.getToolTipText();
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
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.compare.contentmergeviewer.ContentMergeViewer#getCenterWidth
	 * ()
	 */
	protected final int getCenterWidth() {
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

		final MergeSourceViewer part = new MergeSourceViewer(parent,
				getDirection(), getResourceBundle(), getCompareConfiguration()
						.getContainer());
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
				// syncViewport(part);
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

		// Add the find action to the popup menu of the viewer
		contributeFindAction(part);

		configureTextViewer(part);

		return part;
	}

	private void contributeFindAction(MergeSourceViewer viewer) {
		IAction action;
		IWorkbenchPart wp = getCompareConfiguration().getContainer()
				.getWorkbenchPart();
		if (wp != null)
			action = new FindReplaceAction(getResourceBundle(),
					"Editor.FindReplace.", wp); //$NON-NLS-1$
		else
			action = new FindReplaceAction(
					getResourceBundle(),
					"Editor.FindReplace.", viewer.getControl().getShell(), getFindReplaceTarget()); //$NON-NLS-1$
		action.setActionDefinitionId(IWorkbenchActionDefinitionIds.FIND_REPLACE);
		viewer.addAction(MergeSourceViewer.FIND_ID, action);
	}

	private void connectGlobalActions(final MergeSourceViewer part) {
		if (fHandlerService != null) {
			if (part != null)
				part.updateActions();
			fHandlerService.updatePaneActionHandlers(new Runnable() {
				public void run() {
					for (int i = 0; i < GLOBAL_ACTIONS.length; i++) {
						IAction action = null;
						if (part != null) {
							action = part.getAction(TEXT_ACTIONS[i]);
							if (action == null
									&& TEXT_ACTIONS[i]
											.equals(MergeSourceViewer.SAVE_ID)) {
								if (part == fLeft)
									action = fLeftSaveAction;
								else
									action = fRightSaveAction;
							}
						}
						fHandlerService.setGlobalActionHandler(
								GLOBAL_ACTIONS[i], action);
					}
				}
			});
		}
	}

	private IDocument getElementDocument(char type, Object element) {
		if (element instanceof IDocument) {
			return (IDocument) element;
		}
		ITypedElement te = Utilities.getLeg(type, element);
		// First check the contributors for the document
		IDocument document = null;
		switch (type) {
		case ANCESTOR_CONTRIBUTOR:
			document = getDocument(te, fAncestorContributor);
			break;
		case LEFT_CONTRIBUTOR:
			document = getDocument(te, fLeftContributor);
			break;
		case RIGHT_CONTRIBUTOR:
			document = getDocument(te, fRightContributor);
			break;
		}
		if (document != null)
			return document;
		// The document is not associated with the input of the viewer so try to
		// find the document
		return Utilities.getDocument(type, element,
				isUsingDefaultContentProvider(), canHaveSharedDocument());
	}

	private boolean isUsingDefaultContentProvider() {
		return getContentProvider() instanceof MergeViewerContentProvider;
	}

	private boolean canHaveSharedDocument() {
		return getDocumentPartitioning() != null
				|| getDocumentPartitioner() == null;
	}

	private IDocument getDocument(ITypedElement te, ContributorInfo info) {
		if (info != null && info.getElement() == te)
			return info.getDocument();
		return null;
	}

	IDocument getDocument(char type, Object input) {
		IDocument doc = getElementDocument(type, input);
		if (doc != null)
			return doc;

		if (input instanceof IDiffElement) {
			IDiffContainer parent = ((IDiffElement) input).getParent();
			return getElementDocument(type, parent);
		}
		return null;
	}

	/*
	 * Returns true if the given inputs map to the same documents
	 */
	boolean sameDoc(char type, Object newInput, Object oldInput) {
		IDocument newDoc = getDocument(type, newInput);
		IDocument oldDoc = getDocument(type, oldInput);
		return newDoc == oldDoc;
	}

	/**
	 * Overridden to prevent save confirmation if new input is sub document of
	 * current input.
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
		// TODO: Would be good if this could be restated in terms of Saveables
		// and moved up
		if (oldInput != null && newInput != null) {
			// check whether underlying documents have changed.
			if (sameDoc(ANCESTOR_CONTRIBUTOR, newInput, oldInput)
					&& sameDoc(LEFT_CONTRIBUTOR, newInput, oldInput)
					&& sameDoc(RIGHT_CONTRIBUTOR, newInput, oldInput)) {
				if (DEBUG)
					System.out.println("----- Same docs !!!!"); //$NON-NLS-1$
				return false;
			}
		}

		if (DEBUG)
			System.out.println("***** New docs !!!!"); //$NON-NLS-1$

		removeFromDocumentManager(ANCESTOR_CONTRIBUTOR, oldInput);
		removeFromDocumentManager(LEFT_CONTRIBUTOR, oldInput);
		removeFromDocumentManager(RIGHT_CONTRIBUTOR, oldInput);

		if (DEBUG)
			DocumentManager.dump();

		return super.doSave(newInput, oldInput);
	}

	private void removeFromDocumentManager(char leg, Object oldInput) {
		IDocument document = getDocument(leg, oldInput);
		if (document != null)
			DocumentManager.remove(document);
	}

	private ITypedElement getParent(char type) {
		Object input = getInput();
		if (input instanceof IDiffElement) {
			IDiffContainer parent = ((IDiffElement) input).getParent();
			return Utilities.getLeg(type, parent);
		}
		return null;
	}

	/*
	 * Initializes the text viewers of the three content areas with the given
	 * input objects. Subclasses may extend.
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

					if (ci.getAncestor() instanceof IDocumentRange
							|| ci.getLeft() instanceof IDocumentRange
							|| ci.getRight() instanceof IDocumentRange) {

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

		resetDiffs();
		fHasErrors = false; // start with no errors

		CompareConfiguration cc = getCompareConfiguration();
		IMergeViewerContentProvider cp = getMergeContentProvider();

		if (cp instanceof MergeViewerContentProvider) {
			MergeViewerContentProvider mcp = (MergeViewerContentProvider) cp;
			mcp.setAncestorError(null);
			mcp.setLeftError(null);
			mcp.setRightError(null);
		}

		// Record current contributors so we disconnect after creating the new
		// ones.
		// This is done in case the old and new use the same document.
		ContributorInfo oldLeftContributor = fLeftContributor;
		ContributorInfo oldRightContributor = fRightContributor;
		ContributorInfo oldAncestorContributor = fAncestorContributor;

		// Create the new contributor
		fLeftContributor = createLegInfoFor(left, LEFT_CONTRIBUTOR);
		fRightContributor = createLegInfoFor(right, RIGHT_CONTRIBUTOR);
		fAncestorContributor = createLegInfoFor(ancestor, ANCESTOR_CONTRIBUTOR);

		fLeftContributor.transferContributorStateFrom(oldLeftContributor);
		fRightContributor.transferContributorStateFrom(oldRightContributor);
		fAncestorContributor
				.transferContributorStateFrom(oldAncestorContributor);

		// Now disconnect the old ones
		disconnect(oldLeftContributor);
		disconnect(oldRightContributor);
		disconnect(oldAncestorContributor);

		// Get encodings from streams. If an encoding is null, abide by the
		// other one
		// Defaults to workbench encoding only if both encodings are null
		fLeftContributor.setEncodingIfAbsent(fRightContributor);
		fRightContributor.setEncodingIfAbsent(fLeftContributor);
		fAncestorContributor.setEncodingIfAbsent(fLeftContributor);

		// set new documents
		fLeftContributor.setDocument(fLeft,
				cc.isLeftEditable() && cp.isLeftEditable(input));
		fLeftLineCount = fLeft.getLineCount();

		fRightContributor.setDocument(fRight,
				cc.isRightEditable() && cp.isRightEditable(input));
		fRightLineCount = fRight.getLineCount();

		fAncestorContributor.setDocument(fAncestor, false);

		// if the input is part of a patch hunk, toggle synchronized scrolling
		/*
		 * if (isPatchHunk()){ setSyncScrolling(false); } else {
		 * setSyncScrolling
		 * (fPreferenceStore.getBoolean(ComparePreferencePage.SYNCHRONIZE_SCROLLING
		 * )); }
		 */
		setSyncScrolling(fPreferenceStore
				.getBoolean(ComparePreferencePage.SYNCHRONIZE_SCROLLING));

		update(false);

		if (!fHasErrors && !emptyInput && !fComposite.isDisposed()) {
			if (isRefreshing()) {
				fLeftContributor
						.updateSelection(fLeft, !fSynchronizedScrolling);
				fRightContributor.updateSelection(fRight,
						!fSynchronizedScrolling);
				fAncestorContributor.updateSelection(fAncestor,
						!fSynchronizedScrolling);
				if (fSynchronizedScrolling && fSynchronziedScrollPosition != -1) {
					synchronizedScrollVertical(fSynchronziedScrollPosition);
				}
			} else {
				if (isPatchHunk()) {
					if (right != null
							&& Utilities.getAdapter(right, IHunk.class) != null)
						fLeft.setTopIndex(getHunkStart());
					else
						fRight.setTopIndex(getHunkStart());
				} else {
					Diff selectDiff = null;
					if (FIX_47640) {
						if (leftRange != null)
							selectDiff = fMerger.findDiff(LEFT_CONTRIBUTOR,
									leftRange);
						else if (rightRange != null)
							selectDiff = fMerger.findDiff(RIGHT_CONTRIBUTOR,
									rightRange);
					}
					if (selectDiff != null)
						setCurrentDiff(selectDiff, true);
					else
						selectFirstDiff(true);
				}
			}
		}

	}

	private boolean isRefreshing() {
		return isRefreshing;
	}

	private ContributorInfo createLegInfoFor(Object element, char leg) {
		return new ContributorInfo(this, element, leg);
	}

	private void updateDiffBackground(Diff diff) {

		if (!fHighlightRanges)
			return;

		if (diff == null || diff.isToken())
			return;

		if (fShowCurrentOnly && !isCurrentDiff(diff))
			return;

		Color c = getColor(null, getFillColor(diff));
		if (c == null)
			return;

		if (isThreeWay())
			fAncestor.setLineBackground(diff.getPosition(ANCESTOR_CONTRIBUTOR),
					c);
		fLeft.setLineBackground(diff.getPosition(LEFT_CONTRIBUTOR), c);
		fRight.setLineBackground(diff.getPosition(RIGHT_CONTRIBUTOR), c);
	}

	private void updateAllDiffBackgrounds(Display display) {
		if (fMerger.hasChanges()) {
			boolean threeWay = isThreeWay();
			for (Iterator iterator = fMerger.changesIterator(); iterator
					.hasNext();) {
				Diff diff = (Diff) iterator.next();
				Color c = getColor(display, getFillColor(diff));
				if (threeWay)
					fAncestor.setLineBackground(
							diff.getPosition(ANCESTOR_CONTRIBUTOR), c);
				fLeft.setLineBackground(diff.getPosition(LEFT_CONTRIBUTOR), c);
				fRight.setLineBackground(diff.getPosition(RIGHT_CONTRIBUTOR), c);
			}
		}
	}

	/*
	 * Called whenever one of the documents changes. Sets the dirty state of
	 * this viewer and updates the lines. Implements IDocumentListener.
	 */
	private void documentChanged(DocumentEvent e, boolean dirty) {

		IDocument doc = e.getDocument();

		if (doc == fLeft.getDocument()) {
			setLeftDirty(dirty);
		} else if (doc == fRight.getDocument()) {
			setRightDirty(dirty);
		}

		updateLines(doc);
	}

	/*
	 * This method is called if a range of text on one side is copied into an
	 * empty sub-document on the other side. The method returns the position
	 * where the sub-document is placed into the base document. This default
	 * implementation determines the position by using the text range
	 * differencer. However this position is not always optimal for specific
	 * types of text. So subclasses (which are aware of the type of text they
	 * are dealing with) may override this method to find a better position
	 * where to insert a newly added piece of text.
	 * 
	 * @param type the side for which the insertion position should be
	 * determined: 'A' for ancestor, 'L' for left hand side, 'R' for right hand
	 * side.
	 * 
	 * @param input the current input object of this viewer
	 * 
	 * @since 2.0
	 */
	protected int findInsertionPosition(char type, ICompareInput input) {

		ITypedElement other = null;
		char otherType = 0;

		switch (type) {
		case ANCESTOR_CONTRIBUTOR:
			other = input.getLeft();
			otherType = LEFT_CONTRIBUTOR;
			if (other == null) {
				other = input.getRight();
				otherType = RIGHT_CONTRIBUTOR;
			}
			break;
		case LEFT_CONTRIBUTOR:
			other = input.getRight();
			otherType = RIGHT_CONTRIBUTOR;
			if (other == null) {
				other = input.getAncestor();
				otherType = ANCESTOR_CONTRIBUTOR;
			}
			break;
		case RIGHT_CONTRIBUTOR:
			other = input.getLeft();
			otherType = LEFT_CONTRIBUTOR;
			if (other == null) {
				other = input.getAncestor();
				otherType = ANCESTOR_CONTRIBUTOR;
			}
			break;
		}

		if (other instanceof IDocumentRange) {
			IDocumentRange dr = (IDocumentRange) other;
			Position p = dr.getRange();
			Diff diff = findDiff(otherType, p.offset);
			return fMerger.findInsertionPoint(diff, type);
		}
		return 0;
	}

	private void setError(char type, String message) {
		IMergeViewerContentProvider cp = getMergeContentProvider();
		if (cp instanceof MergeViewerContentProvider) {
			MergeViewerContentProvider mcp = (MergeViewerContentProvider) cp;
			switch (type) {
			case ANCESTOR_CONTRIBUTOR:
				mcp.setAncestorError(message);
				break;
			case LEFT_CONTRIBUTOR:
				mcp.setLeftError(message);
				break;
			case RIGHT_CONTRIBUTOR:
				mcp.setRightError(message);
				break;
			}
		}
		fHasErrors = true;
	}

	private void updateDirtyState(IEditorInput key,
			IDocumentProvider documentProvider, char type) {
		boolean dirty = documentProvider.canSaveDocument(key);
		if (type == LEFT_CONTRIBUTOR)
			setLeftDirty(dirty);
		else if (type == RIGHT_CONTRIBUTOR)
			setRightDirty(dirty);
	}

	private Position getNewRange(char type, Object input) {
		switch (type) {
		case ANCESTOR_CONTRIBUTOR:
			return (Position) fNewAncestorRanges.get(input);
		case LEFT_CONTRIBUTOR:
			return (Position) fNewLeftRanges.get(input);
		case RIGHT_CONTRIBUTOR:
			return (Position) fNewRightRanges.get(input);
		}
		return null;
	}

	private void addNewRange(char type, Object input, Position range) {
		switch (type) {
		case ANCESTOR_CONTRIBUTOR:
			fNewAncestorRanges.put(input, range);
			break;
		case LEFT_CONTRIBUTOR:
			fNewLeftRanges.put(input, range);
			break;
		case RIGHT_CONTRIBUTOR:
			fNewRightRanges.put(input, range);
			break;
		}
	}

	/**
	 * Returns the contents of the underlying document as an array of bytes
	 * using the current workbench encoding.
	 * 
	 * @param left
	 *            if <code>true</code> the contents of the left side is
	 *            returned; otherwise the right side
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
						bytes = contents.getBytes(left ? fLeftContributor
								.getEncoding() : fRightContributor
								.getEncoding());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.compare.contentmergeviewer.ContentMergeViewer#
	 * handleResizeAncestor(int, int, int, int)
	 */
	protected final void handleResizeAncestor(int x, int y, int width,
			int height) {
		if (width > 0) {
			Rectangle trim = fLeft.getTextWidget().computeTrim(0, 0, 0, 0);
			int scrollbarHeight = trim.height;
			if (Utilities.okToUse(fAncestorCanvas))
				fAncestorCanvas.setVisible(true);
			if (fAncestor.isControlOkToUse())
				fAncestor.getTextWidget().setVisible(true);

			if (fAncestorCanvas != null) {
				fAncestorCanvas.setBounds(x, y, fMarginWidth, height
						- scrollbarHeight);
				x += fMarginWidth;
				width -= fMarginWidth;
			}
			fAncestor.setBounds(x, y, width, height);
		} else {
			if (Utilities.okToUse(fAncestorCanvas))
				fAncestorCanvas.setVisible(false);
			if (fAncestor.isControlOkToUse()) {
				StyledText t = fAncestor.getTextWidget();
				t.setVisible(false);
				fAncestor.setBounds(0, 0, 0, 0);
				if (fFocusPart == fAncestor) {
					fFocusPart = fLeft;
					fFocusPart.getTextWidget().setFocus();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.compare.contentmergeviewer.ContentMergeViewer#
	 * handleResizeLeftRight(int, int, int, int, int, int)
	 */
	protected final void handleResizeLeftRight(int x, int y, int width1,
			int centerWidth, int width2, int height) {

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

		fLeft.setBounds(x, y, leftTextWidth, height);
		x += leftTextWidth;

		if (fCenter == null || fCenter.isDisposed())
			fCenter = createCenterControl(composite);
		fCenter.setBounds(x, y, centerWidth, height - scrollbarHeight);
		x += centerWidth;

		if (!fSynchronizedScrolling) { // canvas is to the left of text
			if (fRightCanvas != null) {
				fRightCanvas.setBounds(x, y, fMarginWidth, height
						- scrollbarHeight);
				fRightCanvas.redraw();
				x += fMarginWidth;
			}
			// we draw the canvas to the left of the text widget
		}

		int scrollbarWidth = 0;
		if (fSynchronizedScrolling && fScrollCanvas != null) {
			trim = fLeft.getTextWidget().computeTrim(0, 0, 0, 0);
			// one pixel was cut off
			scrollbarWidth = trim.width + 2 * trim.x + 1;
		}
		int rightTextWidth = width2 - scrollbarWidth;
		if (fRightCanvas != null)
			rightTextWidth -= fMarginWidth;
		fRight.setBounds(x, y, rightTextWidth, height);
		x += rightTextWidth;

		if (fSynchronizedScrolling) {
			if (fRightCanvas != null) { // canvas is to the right of the text
				fRightCanvas.setBounds(x, y, fMarginWidth, height
						- scrollbarHeight);
				x += fMarginWidth;
			}
			if (fScrollCanvas != null)
				fScrollCanvas.setBounds(x, y, scrollbarWidth, height
						- scrollbarHeight);
		}

		if (fBirdsEyeCanvas != null) {
			int verticalScrollbarButtonHeight = scrollbarWidth;
			int horizontalScrollbarButtonHeight = scrollbarHeight;
			if (fIsCarbon) {
				verticalScrollbarButtonHeight += 2;
				horizontalScrollbarButtonHeight = 18;
			}
			if (fSummaryHeader != null)
				fSummaryHeader.setBounds(x + scrollbarWidth, y,
						BIRDS_EYE_VIEW_WIDTH, verticalScrollbarButtonHeight);
			y += verticalScrollbarButtonHeight;
			fBirdsEyeCanvas
					.setBounds(
							x + scrollbarWidth,
							y,
							BIRDS_EYE_VIEW_WIDTH,
							height
									- (2 * verticalScrollbarButtonHeight + horizontalScrollbarButtonHeight));
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

	// ---- the differencing

	/**
	 * Perform a two level 2- or 3-way diff. The first level is based on line
	 * comparison, the second level on token comparison.
	 */
	private void doDiff() {
		IDocument lDoc = fLeft.getDocument();
		IDocument rDoc = fRight.getDocument();
		if (lDoc == null || rDoc == null)
			return;
		fAncestor.resetLineBackground();
		fLeft.resetLineBackground();
		fRight.resetLineBackground();

		fCurrentDiff = null;
		try {
			fMerger.doDiff();
		} catch (CoreException e) {
			CompareUIPlugin.log(e.getStatus());
			String title = Utilities.getString(getResourceBundle(),
					"tooComplexError.title"); //$NON-NLS-1$
			String format = Utilities.getString(getResourceBundle(),
					"tooComplexError.format"); //$NON-NLS-1$
			String msg = MessageFormat.format(format, new Object[] { Integer
					.toString(PlatformUI.getWorkbench().getProgressService()
							.getLongOperationTime() / 1000) });
			MessageDialog.openError(fComposite.getShell(), title, msg);
		}

		if (fMerger.hasChanges()) {
			for (Iterator iterator = fMerger.changesIterator(); iterator
					.hasNext();) {
				Diff diff = (Diff) iterator.next();
				updateDiffBackground(diff);
			}
		}
		invalidateTextPresentation();
	}

	private Diff findDiff(char type, int pos) {
		try {
			return fMerger.findDiff(type, pos);
		} catch (CoreException e) {
			CompareUIPlugin.log(e.getStatus());
			String title = Utilities.getString(getResourceBundle(),
					"tooComplexError.title"); //$NON-NLS-1$
			String format = Utilities.getString(getResourceBundle(),
					"tooComplexError.format"); //$NON-NLS-1$
			String msg = MessageFormat.format(format, new Object[] { Integer
					.toString(PlatformUI.getWorkbench().getProgressService()
							.getLongOperationTime() / 1000) });
			MessageDialog.openError(fComposite.getShell(), title, msg);
			return null;
		}
	}

	private void resetPositions(IDocument doc) {
		if (doc == null)
			return;
		try {
			doc.removePositionCategory(DIFF_RANGE_CATEGORY);
		} catch (BadPositionCategoryException e) {
			// Ignore
		}
		doc.addPositionCategory(DIFF_RANGE_CATEGORY);
	}

	// ---- update UI stuff

	private void updateControls() {

		boolean leftToRight = false;
		boolean rightToLeft = false;

		updateStatus(fCurrentDiff);
		updateResolveStatus();

		if (fCurrentDiff != null) {
			IMergeViewerContentProvider cp = getMergeContentProvider();
			if (cp != null) {
				if (!isPatchHunk()) {
					rightToLeft = cp.isLeftEditable(getInput());
					leftToRight = cp.isRightEditable(getInput());
				}
			}
		}

		if (fDirectionLabel != null) {
			if (fHighlightRanges && fCurrentDiff != null && isThreeWay()
					&& !isIgnoreAncestor()) {
				fDirectionLabel.setImage(fCurrentDiff.getImage());
			} else {
				fDirectionLabel.setImage(null);
			}
		}

		if (fCopyDiffLeftToRightItem != null)
			((Action) fCopyDiffLeftToRightItem.getAction())
					.setEnabled(leftToRight);
		if (fCopyDiffRightToLeftItem != null)
			((Action) fCopyDiffRightToLeftItem.getAction())
					.setEnabled(rightToLeft);

		boolean enableNavigation = isNavigationPossible();

		if (fNextDiff != null) {
			IAction a = fNextDiff.getAction();
			a.setEnabled(enableNavigation || hasNextElement(true));
		}
		if (fPreviousDiff != null) {
			IAction a = fPreviousDiff.getAction();
			a.setEnabled(enableNavigation || hasNextElement(false));
		}
		if (fNextChange != null) {
			IAction a = fNextChange.getAction();
			a.setEnabled(enableNavigation);
		}
		if (fPreviousChange != null) {
			IAction a = fPreviousChange.getAction();
			a.setEnabled(enableNavigation);
		}
	}

	private void updateResolveStatus() {

		RGB rgb = null;

		if (showResolveUI()) {
			// we only show red or green if there is at least one incoming or
			// conflicting change
			int incomingOrConflicting = 0;
			int unresolvedIncoming = 0;
			int unresolvedConflicting = 0;

			if (fMerger.hasChanges()) {
				for (Iterator iterator = fMerger.changesIterator(); iterator
						.hasNext();) {
					Diff d = (Diff) iterator.next();
					if (d.isIncomingOrConflicting() /*
													 * &&
													 * useChange(d.fDirection)
													 * && !d.fIsWhitespace
													 */) {
						incomingOrConflicting++;
						if (!d.isResolved()) {
							if (d.getKind() == RangeDifference.CONFLICT) {
								unresolvedConflicting++;
								break; // we can stop here because a conflict
										// has the maximum priority
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

		String diffDescription;

		if (diff == null) {
			diffDescription = CompareMessages.TextMergeViewer_diffDescription_noDiff_format;
		} else {

			if (diff.isToken()) // we don't show special info for token diffs
				diff = diff.getParent();

			String format = CompareMessages.TextMergeViewer_diffDescription_diff_format;
			diffDescription = MessageFormat.format(format, new String[] {
					getDiffType(diff), // 0: diff type
					getDiffNumber(diff), // 1: diff number
					getDiffRange(fLeft, diff.getPosition(LEFT_CONTRIBUTOR)), // 2:
																				// left
																				// start
																				// line
					getDiffRange(fRight, diff.getPosition(RIGHT_CONTRIBUTOR)) // 3:
																				// left
																				// end
																				// line
					});
		}

		String format = CompareMessages.TextMergeViewer_statusLine_format;
		String s = MessageFormat.format(format, new String[] {
				getCursorPosition(fLeft), // 0: left column
				getCursorPosition(fRight), // 1: right column
				diffDescription // 2: diff description
				});

		getCompareConfiguration().getContainer().setStatusMessage(s);
	}

	private void clearStatus() {
		getCompareConfiguration().getContainer().setStatusMessage(null);
	}

	private String getDiffType(Diff diff) {
		String s = ""; //$NON-NLS-1$
		switch (diff.getKind()) {
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
		return MessageFormat.format(format,
				new String[] { s, diff.changeType() });
	}

	private String getDiffNumber(Diff diff) {
		// find the diff's number
		int diffNumber = 0;
		if (fMerger.hasChanges()) {
			for (Iterator iterator = fMerger.changesIterator(); iterator
					.hasNext();) {
				Diff d = (Diff) iterator.next();
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
		return MessageFormat.format(
				format,
				new String[] { Integer.toString(startLine),
						Integer.toString(endLine) });
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
					int column = caret - lineOffset + (tabWidth - 1)
							* occurrences;

					String format = CompareMessages.TextMergeViewer_cursorPosition_format;
					return MessageFormat.format(
							format,
							new String[] { Integer.toString(line + 1),
									Integer.toString(column + 1) });

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
	 * Creates the two items for copying a difference range from one side to the
	 * other and adds them to the given toolbar manager.
	 */
	protected void createToolItems(ToolBarManager tbm) {

		fHandlerService = CompareHandlerService.createFor(
				getCompareConfiguration().getContainer(), fLeft.getControl()
						.getShell());

		final String ignoreAncestorActionKey = "action.IgnoreAncestor."; //$NON-NLS-1$
		Action ignoreAncestorAction = new Action() {
			public void run() {
				// First make sure the ancestor is hidden
				if (!isIgnoreAncestor())
					getCompareConfiguration().setProperty(
							ICompareUIConstants.PROP_ANCESTOR_VISIBLE,
							Boolean.FALSE);
				// Then set the property to ignore the ancestor
				getCompareConfiguration().setProperty(
						ICompareUIConstants.PROP_IGNORE_ANCESTOR,
						Boolean.valueOf(!isIgnoreAncestor()));
				Utilities.initToggleAction(this, getResourceBundle(),
						ignoreAncestorActionKey, isIgnoreAncestor());
			}
		};
		ignoreAncestorAction.setChecked(isIgnoreAncestor());
		Utilities.initAction(ignoreAncestorAction, getResourceBundle(),
				ignoreAncestorActionKey);
		Utilities.initToggleAction(ignoreAncestorAction, getResourceBundle(),
				ignoreAncestorActionKey, isIgnoreAncestor());

		fIgnoreAncestorItem = new ActionContributionItem(ignoreAncestorAction);
		fIgnoreAncestorItem.setVisible(false);
		tbm.appendToGroup("modes", fIgnoreAncestorItem); //$NON-NLS-1$

		tbm.add(new Separator());

		Action a = new Action() {
			public void run() {
				if (navigate(true, false, false)) {
					endOfDocumentReached(true);
				}
			}
		};
		Utilities.initAction(a, getResourceBundle(), "action.NextDiff."); //$NON-NLS-1$
		fNextDiff = new ActionContributionItem(a);
		tbm.appendToGroup("navigation", fNextDiff); //$NON-NLS-1$
		// Don't register this action since it is probably registered by the
		// container

		a = new Action() {
			public void run() {
				if (navigate(false, false, false)) {
					endOfDocumentReached(false);
				}
			}
		};
		Utilities.initAction(a, getResourceBundle(), "action.PrevDiff."); //$NON-NLS-1$
		fPreviousDiff = new ActionContributionItem(a);
		tbm.appendToGroup("navigation", fPreviousDiff); //$NON-NLS-1$
		// Don't register this action since it is probably registered by the
		// container

		a = new Action() {
			public void run() {
				if (navigate(true, false, true)) {
					endOfDocumentReached(true);
				}
			}
		};
		Utilities.initAction(a, getResourceBundle(), "action.NextChange."); //$NON-NLS-1$
		fNextChange = new ActionContributionItem(a);
		tbm.appendToGroup("navigation", fNextChange); //$NON-NLS-1$
		fHandlerService.registerAction(a,
				"org.eclipse.compare.selectNextChange"); //$NON-NLS-1$

		a = new Action() {
			public void run() {
				if (navigate(false, false, true)) {
					endOfDocumentReached(false);
				}
			}
		};
		Utilities.initAction(a, getResourceBundle(), "action.PrevChange."); //$NON-NLS-1$
		fPreviousChange = new ActionContributionItem(a);
		tbm.appendToGroup("navigation", fPreviousChange); //$NON-NLS-1$
		fHandlerService.registerAction(a,
				"org.eclipse.compare.selectPreviousChange"); //$NON-NLS-1$

		CompareConfiguration cc = getCompareConfiguration();

		if (cc.isRightEditable()) {
			a = new Action() {
				public void run() {
					copyDiffLeftToRight();
				}
			};
			Utilities.initAction(a, getResourceBundle(),
					"action.CopyDiffLeftToRight."); //$NON-NLS-1$
			fCopyDiffLeftToRightItem = new ActionContributionItem(a);
			fCopyDiffLeftToRightItem.setVisible(true);
			tbm.appendToGroup("merge", fCopyDiffLeftToRightItem); //$NON-NLS-1$
			fHandlerService.registerAction(a,
					"org.eclipse.compare.copyLeftToRight"); //$NON-NLS-1$
		}

		if (cc.isLeftEditable()) {
			a = new Action() {
				public void run() {
					copyDiffRightToLeft();
				}
			};
			Utilities.initAction(a, getResourceBundle(),
					"action.CopyDiffRightToLeft."); //$NON-NLS-1$
			fCopyDiffRightToLeftItem = new ActionContributionItem(a);
			fCopyDiffRightToLeftItem.setVisible(true);
			tbm.appendToGroup("merge", fCopyDiffRightToLeftItem); //$NON-NLS-1$
			fHandlerService.registerAction(a,
					"org.eclipse.compare.copyRightToLeft"); //$NON-NLS-1$
		}

		fIgnoreWhitespace = ChangePropertyAction.createIgnoreWhiteSpaceAction(
				getResourceBundle(), getCompareConfiguration());
		fIgnoreWhitespace
				.setActionDefinitionId(ICompareUIConstants.COMMAND_IGNORE_WHITESPACE);
		fLeft.addTextAction(fIgnoreWhitespace);
		fRight.addTextAction(fIgnoreWhitespace);
		fAncestor.addTextAction(fIgnoreWhitespace);
		fHandlerService.registerAction(fIgnoreWhitespace,
				fIgnoreWhitespace.getActionDefinitionId());

		showWhitespaceAction = new ShowWhitespaceAction(
				new MergeSourceViewer[] { fLeft, fRight, fAncestor });
		fHandlerService.registerAction(showWhitespaceAction,
				ITextEditorActionDefinitionIds.SHOW_WHITESPACE_CHARACTERS);

		toggleLineNumbersAction = new TextEditorPropertyAction(
				CompareMessages.TextMergeViewer_16,
				new MergeSourceViewer[] { fLeft, fRight, fAncestor },
				AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER);
		fHandlerService.registerAction(toggleLineNumbersAction,
				ITextEditorActionDefinitionIds.LINENUMBER_TOGGLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.compare.contentmergeviewer.ContentMergeViewer#
	 * handlePropertyChangeEvent(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	protected void handlePropertyChangeEvent(PropertyChangeEvent event) {

		String key = event.getProperty();

		if (key.equals(CompareConfiguration.IGNORE_WHITESPACE)
				|| key.equals(ComparePreferencePage.SHOW_PSEUDO_CONFLICTS)) {

			fShowPseudoConflicts = fPreferenceStore
					.getBoolean(ComparePreferencePage.SHOW_PSEUDO_CONFLICTS);

			update(true);
			selectFirstDiff(true);

			// } else if (key.equals(ComparePreferencePage.USE_SPLINES)) {
			// fUseSplines=
			// fPreferenceStore.getBoolean(ComparePreferencePage.USE_SPLINES);
			// invalidateLines();

		} else if (key.equals(ComparePreferencePage.USE_SINGLE_LINE)) {
			fUseSingleLine = fPreferenceStore
					.getBoolean(ComparePreferencePage.USE_SINGLE_LINE);
			// fUseResolveUI= fUseSingleLine;
			fBasicCenterCurve = null;
			updateResolveStatus();
			invalidateLines();

		} else if (key.equals(ComparePreferencePage.HIGHLIGHT_TOKEN_CHANGES)) {
			fHighlightTokenChanges = fPreferenceStore
					.getBoolean(ComparePreferencePage.HIGHLIGHT_TOKEN_CHANGES);
			updateResolveStatus();
			updatePresentation(null);

			// } else if (key.equals(ComparePreferencePage.USE_RESOLVE_UI)) {
			// fUseResolveUI=
			// fPreferenceStore.getBoolean(ComparePreferencePage.USE_RESOLVE_UI);
			// updateResolveStatus();
			// invalidateLines();

		} else if (key.equals(fSymbolicFontName)) {
			updateFont();
			invalidateLines();

		} else if (key.equals(INCOMING_COLOR) || key.equals(OUTGOING_COLOR)
				|| key.equals(CONFLICTING_COLOR) || key.equals(RESOLVED_COLOR)) {
			updateColors(null);
			invalidateLines();
			invalidateTextPresentation();

		} else if (key.equals(ComparePreferencePage.SYNCHRONIZE_SCROLLING)) {
			boolean b = fPreferenceStore
					.getBoolean(ComparePreferencePage.SYNCHRONIZE_SCROLLING);
			setSyncScrolling(b);

		} else {
			super.handlePropertyChangeEvent(event);

			if (key.equals(ICompareUIConstants.PROP_IGNORE_ANCESTOR)) {
				update(false);
				selectFirstDiff(true);
			}
		}
	}

	private void selectFirstDiff(boolean first) {

		if (fLeft == null || fRight == null) {
			return;
		}
		if (fLeft.getDocument() == null || fRight.getDocument() == null) {
			return;
		}

		Diff firstDiff = null;
		if (first)
			firstDiff = findNext(fRight, -1, -1, false);
		else
			firstDiff = findPrev(fRight, 9999999, 9999999, false);
		setCurrentDiff(firstDiff, true);
	}

	private void setSyncScrolling(boolean newMode) {
		if (fSynchronizedScrolling != newMode) {
			fSynchronizedScrolling = newMode;

			scrollVertical(0, 0, 0, null);

			// throw away central control (Sash or Canvas)
			Control center = getCenterControl();
			if (center != null && !center.isDisposed())
				center.dispose();

			fLeft.getTextWidget().getVerticalBar()
					.setVisible(!fSynchronizedScrolling);
			fRight.getTextWidget().getVerticalBar()
					.setVisible(!fSynchronizedScrolling);

			fComposite.layout(true);
		}
	}

	protected void updateToolItems() {
		// only update toolbar items if diffs need to be calculated (which
		// dictates whether a toolbar gets added at all)
		if (!isPatchHunk()) {
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
	}

	// ---- painting lines

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
			Control center = getCenterControl();
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

		if (Utilities.okToUse(getCenterControl()))
			getCenterControl().redraw();

		if (fRight != null && fRight.isControlOkToUse())
			fRight.getTextWidget().redraw();

		if (Utilities.okToUse(fRightCanvas))
			fRightCanvas.redraw();
	}

	private boolean showResolveUI() {
		if (!fUseResolveUI || !isThreeWay() || isIgnoreAncestor())
			return false;
		CompareConfiguration cc = getCompareConfiguration();
		// we only enable the new resolve UI if exactly one side is editable
		boolean l = cc.isLeftEditable();
		boolean r = cc.isRightEditable();
		// return (l && !r) || (r && !l);
		return l || r;
	}

	private void paintCenter(Canvas canvas, GC g) {

		Display display = canvas.getDisplay();

		checkForColorUpdate(display);

		if (!fSynchronizedScrolling)
			return;

		int lineHeightLeft = fLeft.getTextWidget().getLineHeight();
		int lineHeightRight = fRight.getTextWidget().getLineHeight();
		int visibleHeight = fRight.getViewportHeight();

		Point size = canvas.getSize();
		int x = 0;
		int w = size.x;

		g.setBackground(canvas.getBackground());
		g.fillRectangle(x + 1, 0, w - 2, size.y);

		if (!fIsMotif) {
			// draw thin line between center ruler and both texts
			g.setBackground(display
					.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
			g.fillRectangle(0, 0, 1, size.y);
			g.fillRectangle(w - 1, 0, 1, size.y);
		}

		if (!fHighlightRanges)
			return;

		boolean showResolveUI = showResolveUI();

		if (fMerger.hasChanges()) {
			int lshift = fLeft.getVerticalScrollOffset();
			int rshift = fRight.getVerticalScrollOffset();

			Point region = new Point(0, 0);

			for (Iterator iterator = fMerger.changesIterator(); iterator
					.hasNext();) {
				Diff diff = (Diff) iterator.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				fLeft.getLineRange(diff.getPosition(LEFT_CONTRIBUTOR), region);
				int ly = (region.x * lineHeightLeft) + lshift;
				int lh = region.y * lineHeightLeft;

				fRight.getLineRange(diff.getPosition(RIGHT_CONTRIBUTOR), region);
				int ry = (region.x * lineHeightRight) + rshift;
				int rh = region.y * lineHeightRight;

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

					g.setLineWidth(0 /* LW */);
					g.setForeground(strokeColor);
					g.drawRectangle(0 - 1, ly, w2, lh); // left
					g.drawRectangle(w - w2, ry, w2, rh); // right

					if (fUseSplines) {
						int[] points = getCenterCurvePoints(w2, ly + lh / 2, w
								- w2, ry + rh / 2);
						for (int i = 1; i < points.length; i++)
							g.drawLine(w2 + i - 1, points[i - 1], w2 + i,
									points[i]);
					} else {
						g.drawLine(w2, ly + lh / 2, w - w2, ry + rh / 2);
					}
				} else {
					// two lines
					if (fUseSplines) {
						g.setBackground(fillColor);

						g.setLineWidth(0 /* LW */);
						g.setForeground(strokeColor);

						int[] topPoints = getCenterCurvePoints(fPts[0],
								fPts[1], fPts[2], fPts[3]);
						int[] bottomPoints = getCenterCurvePoints(fPts[6],
								fPts[7], fPts[4], fPts[5]);
						g.setForeground(fillColor);
						g.drawLine(0, bottomPoints[0], 0, topPoints[0]);
						for (int i = 1; i < bottomPoints.length; i++) {
							g.setForeground(fillColor);
							g.drawLine(i, bottomPoints[i], i, topPoints[i]);
							g.setForeground(strokeColor);
							g.drawLine(i - 1, topPoints[i - 1], i, topPoints[i]);
							g.drawLine(i - 1, bottomPoints[i - 1], i,
									bottomPoints[i]);
						}
					} else {
						g.setBackground(fillColor);
						g.fillPolygon(fPts);

						g.setLineWidth(0 /* LW */);
						g.setForeground(strokeColor);
						g.drawLine(fPts[0], fPts[1], fPts[2], fPts[3]);
						g.drawLine(fPts[6], fPts[7], fPts[4], fPts[5]);
					}
				}

				if (fUseSingleLine && showResolveUI
						&& diff.isUnresolvedIncomingOrConflicting()) {
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

	private int[] getCenterCurvePoints(int startx, int starty, int endx,
			int endy) {
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

	private void paintSides(GC g, MergeSourceViewer tp, Canvas canvas,
			boolean right) {

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
			g.setBackground(display
					.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
			if (right)
				g.fillRectangle(0, 0, 1, size.y);
			else
				g.fillRectangle(size.x - 1, 0, 1, size.y);
		}

		if (!fHighlightRanges)
			return;

		if (fMerger.hasChanges()) {
			int shift = tp.getVerticalScrollOffset() + (2 - LW);

			Point region = new Point(0, 0);
			char leg = getLeg(tp);
			for (Iterator iterator = fMerger.changesIterator(); iterator
					.hasNext();) {
				Diff diff = (Diff) iterator.next();
				if (diff.isDeleted())
					continue;

				if (fShowCurrentOnly2 && !isCurrentDiff(diff))
					continue;

				tp.getLineRange(diff.getPosition(leg), region);
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

				g.setLineWidth(0 /* LW */);
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
		if (!fMerger.hasChanges())
			return;

		Control canvas = (Control) event.widget;
		GC g = event.gc;

		Display display = canvas.getDisplay();

		int lineHeight = tp.getTextWidget().getLineHeight();
		int w = canvas.getSize().x;
		int shift = tp.getVerticalScrollOffset() + (2 - LW);
		int maxh = event.y + event.height; // visibleHeight

		// if (fIsMotif)
		shift += fTopInset;

		Point range = new Point(0, 0);

		char leg = getLeg(tp);
		for (Iterator iterator = fMerger.changesIterator(); iterator.hasNext();) {
			Diff diff = (Diff) iterator.next();
			if (diff.isDeleted())
				continue;

			if (fShowCurrentOnly && !isCurrentDiff(diff))
				continue;

			tp.getLineRange(diff.getPosition(leg), range);
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
		boolean selected = fCurrentDiff != null
				&& fCurrentDiff.getParent() == diff;
		RGB selected_fill = getBackground(null);
		if (isThreeWay() && !isIgnoreAncestor()) {
			switch (diff.getKind()) {
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
		boolean selected = fCurrentDiff != null
				&& fCurrentDiff.getParent() == diff;

		if (isThreeWay() && !isIgnoreAncestor()) {
			switch (diff.getKind()) {
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
			return new RGB((int) ((1.0 - scale) * fg.red + scale * bg.red),
					(int) ((1.0 - scale) * fg.green + scale * bg.green),
					(int) ((1.0 - scale) * fg.blue + scale * bg.blue));
		if (fg != null)
			return fg;
		if (bg != null)
			return bg;
		return new RGB(128, 128, 128); // a gray
	}

	// ---- Navigating and resolving Diffs

	private Diff getNextVisibleDiff(boolean down, boolean deep) {
		Diff diff = null;
		MergeSourceViewer part = getNavigationPart();
		if (part == null)
			return null;
		Point s = part.getSelectedRange();
		char leg = getLeg(part);
		for (;;) {
			diff = null;
			diff = internalGetNextDiff(down, deep, part, s);
			if (diff != null && diff.getKind() == RangeDifference.ANCESTOR
					&& !isAncestorVisible()) {
				Position position = diff.getPosition(leg);
				s = new Point(position.getOffset(), position.getLength());
				diff = null;
				continue;
			}
			break;
		}
		return diff;
	}

	private Diff internalGetNextDiff(boolean down, boolean deep,
			MergeSourceViewer part, Point s) {
		if (fMerger.hasChanges()) {
			if (down)
				return findNext(part, s.x, s.x + s.y, deep);
			return findPrev(part, s.x, s.x + s.y, deep);
		}
		return null;
	}

	private MergeSourceViewer getNavigationPart() {
		MergeSourceViewer part = fFocusPart;
		if (part == null)
			part = fRight;
		return part;
	}

	private Diff getWrappedDiff(Diff diff, boolean down) {
		return fMerger.getWrappedDiff(diff, down);
	}

	/*
	 * Returns true if end (or beginning) of document reached.
	 */
	private boolean navigate(boolean down, boolean wrap, boolean deep) {
		Diff diff = null;
		boolean wrapped = false;
		for (;;) {
			diff = getNextVisibleDiff(down, deep);
			if (diff == null && wrap) {
				if (wrapped)
					// We've already wrapped once so break out
					break;
				wrapped = true;
				diff = getWrappedDiff(diff, down);
			}
			if (diff != null)
				setCurrentDiff(diff, true, deep);
			if (diff != null && diff.getKind() == RangeDifference.ANCESTOR
					&& !isAncestorVisible())
				continue;
			break;
		}
		return diff == null;
	}

	private void endOfDocumentReached(boolean down) {
		Control c = getControl();
		if (Utilities.okToUse(c)) {
			handleEndOfDocumentReached(c.getShell(), down);
		}
	}

	private void handleEndOfDocumentReached(Shell shell, boolean next) {
		boolean hasNextElement = hasNextElement(next);
		IPreferenceStore store = CompareUIPlugin.getDefault()
				.getPreferenceStore();
		String value = store
				.getString(ICompareUIConstants.PREF_NAVIGATION_END_ACTION);
		if (!value.equals(ICompareUIConstants.PREF_VALUE_PROMPT)) {
			// We only want to do the automatic thing if there is something to
			// do
			if (hasNextElement
					|| store.getString(
							ICompareUIConstants.PREF_NAVIGATION_END_ACTION)
							.equals(ICompareUIConstants.PREF_VALUE_LOOP)) {
				performEndOfDocumentAction(shell, store,
						ICompareUIConstants.PREF_NAVIGATION_END_ACTION, next);
				return;
			}
		}
		shell.getDisplay().beep();
		if (hasNextElement) {
			String loopMessage;
			String nextMessage;
			String message;
			String title;
			if (next) {
				title = CompareMessages.TextMergeViewer_0;
				message = CompareMessages.TextMergeViewer_1;
				loopMessage = CompareMessages.TextMergeViewer_2;
				nextMessage = CompareMessages.TextMergeViewer_3;
			} else {
				title = CompareMessages.TextMergeViewer_4;
				message = CompareMessages.TextMergeViewer_5;
				loopMessage = CompareMessages.TextMergeViewer_6;
				nextMessage = CompareMessages.TextMergeViewer_7;
			}
			String[] localLoopOption = new String[] { loopMessage,
					ICompareUIConstants.PREF_VALUE_LOOP };
			String[] nextElementOption = new String[] { nextMessage,
					ICompareUIConstants.PREF_VALUE_NEXT };
			NavigationEndDialog dialog = new NavigationEndDialog(shell, title,
					null, message, new String[][] { localLoopOption,
							nextElementOption, });
			int result = dialog.open();
			if (result == Window.OK) {
				performEndOfDocumentAction(shell, store,
						ICompareUIConstants.PREF_NAVIGATION_END_ACTION_LOCAL,
						next);
				if (dialog.getToggleState()) {
					store.putValue(
							ICompareUIConstants.PREF_NAVIGATION_END_ACTION,
							store.getString(ICompareUIConstants.PREF_NAVIGATION_END_ACTION_LOCAL));
				}
			}
		} else {
			String message;
			String title;
			if (next) {
				title = CompareMessages.TextMergeViewer_8;
				message = CompareMessages.TextMergeViewer_9;
			} else {
				title = CompareMessages.TextMergeViewer_10;
				message = CompareMessages.TextMergeViewer_11;
			}
			if (MessageDialog.openQuestion(shell, title, message)) {
				selectFirstDiff(next);
			}
		}
	}

	private void performEndOfDocumentAction(Shell shell,
			IPreferenceStore store, String key, boolean next) {
		String value = store.getString(key);
		if (value.equals(ICompareUIConstants.PREF_VALUE_NEXT)) {
			ICompareNavigator navigator = getCompareConfiguration()
					.getContainer().getNavigator();
			if (hasNextElement(next))
				navigator.selectChange(next);
			else
				shell.getDisplay().beep();
		} else {
			selectFirstDiff(next);
		}
	}

	private boolean hasNextElement(boolean down) {
		ICompareNavigator navigator = getCompareConfiguration().getContainer()
				.getNavigator();
		if (navigator instanceof CompareNavigator) {
			CompareNavigator n = (CompareNavigator) navigator;
			return n.hasChange(down);
		}
		return false;
	}

	/*
	 * Find the Diff that overlaps with the given TextPart's text range. If the
	 * range doesn't overlap with any range <code>null</code> is returned.
	 */
	private Diff findDiff(MergeSourceViewer tp, int rangeStart, int rangeEnd) {
		char contributor = getLeg(tp);
		return fMerger.findDiff(contributor, rangeStart, rangeEnd);
	}

	private Diff findNext(MergeSourceViewer tp, int start, int end, boolean deep) {
		return fMerger.findNext(getLeg(tp), start, end, deep);
	}

	private Diff findPrev(MergeSourceViewer tp, int start, int end, boolean deep) {
		return fMerger.findPrev(getLeg(tp), start, end, deep);
	}

	/*
	 * Set the currently active Diff and update the toolbars controls and lines.
	 * If <code>revealAndSelect</code> is <code>true</code> the Diff is revealed
	 * and selected in both TextParts.
	 */
	private void setCurrentDiff(Diff d, boolean revealAndSelect) {
		setCurrentDiff(d, revealAndSelect, false);
	}

	/*
	 * Set the currently active Diff and update the toolbars controls and lines.
	 * If <code>revealAndSelect</code> is <code>true</code> the Diff is revealed
	 * and selected in both TextParts.
	 */
	private void setCurrentDiff(Diff d, boolean revealAndSelect, boolean deep) {

		// if (d == fCurrentDiff)
		// return;

		if (fCenterButton != null && !fCenterButton.isDisposed())
			fCenterButton.setVisible(false);

		Diff oldDiff = fCurrentDiff;

		if (d != null && revealAndSelect) {

			// before we set fCurrentDiff we change the selection
			// so that the paint code uses the old background colors
			// otherwise selection isn't drawn correctly
			if (d.isToken() || !fHighlightTokenChanges || deep
					|| !d.hasChildren()) {
				if (isThreeWay() && !isIgnoreAncestor())
					fAncestor.setSelection(d.getPosition(ANCESTOR_CONTRIBUTOR));
				fLeft.setSelection(d.getPosition(LEFT_CONTRIBUTOR));
				fRight.setSelection(d.getPosition(RIGHT_CONTRIBUTOR));
			} else {
				if (isThreeWay() && !isIgnoreAncestor())
					fAncestor.setSelection(new Position(d
							.getPosition(ANCESTOR_CONTRIBUTOR).offset, 0));
				fLeft.setSelection(new Position(
						d.getPosition(LEFT_CONTRIBUTOR).offset, 0));
				fRight.setSelection(new Position(d
						.getPosition(RIGHT_CONTRIBUTOR).offset, 0));
			}

			// now switch diffs
			fCurrentDiff = d;
			revealDiff(d, d.isToken());
		} else {
			fCurrentDiff = d;
		}

		Diff d1 = oldDiff != null ? oldDiff.getParent() : null;
		Diff d2 = fCurrentDiff != null ? fCurrentDiff.getParent() : null;
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
			int ls = fLeft
					.getLineRange(d.getPosition(LEFT_CONTRIBUTOR), region).x;
			int rs = fRight.getLineRange(d.getPosition(RIGHT_CONTRIBUTOR),
					region).x;

			if (isThreeWay() && !isIgnoreAncestor()) {
				int as = fAncestor.getLineRange(
						d.getPosition(ANCESTOR_CONTRIBUTOR), region).x;
				if (as >= fAncestor.getTopIndex()
						&& as <= fAncestor.getBottomIndex())
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
				avpos = lvpos = rvpos = realToVirtualPosition(LEFT_CONTRIBUTOR,
						fLeft.getTopIndex());
				allButThis = fLeft;
			} else if (rightIsVisible) {
				avpos = lvpos = rvpos = realToVirtualPosition(
						RIGHT_CONTRIBUTOR, fRight.getTopIndex());
				allButThis = fRight;
			} else if (ancestorIsVisible) {
				avpos = lvpos = rvpos = realToVirtualPosition(
						ANCESTOR_CONTRIBUTOR, fAncestor.getTopIndex());
				allButThis = fAncestor;
			} else {
				int vpos = 0;
				for (Iterator iterator = fMerger.rangesIterator(); iterator
						.hasNext();) {
					Diff diff = (Diff) iterator.next();
					if (diff == d)
						break;
					if (fSynchronizedScrolling) {
						vpos += diff.getMaxDiffHeight();
					} else {
						avpos += diff.getAncestorHeight();
						lvpos += diff.getLeftHeight();
						rvpos += diff.getRightHeight();
					}
				}
				if (fSynchronizedScrolling)
					avpos = lvpos = rvpos = vpos;
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
		if (d.isToken()) {
			// we only scroll horizontally for token diffs
			reveal(fAncestor, d.getPosition(ANCESTOR_CONTRIBUTOR));
			reveal(fLeft, d.getPosition(LEFT_CONTRIBUTOR));
			reveal(fRight, d.getPosition(RIGHT_CONTRIBUTOR));
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
				if (!r.isEmpty()) // workaround for #7320: Next diff scrolls
									// when going into current diff
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

	// --------------------------------------------------------------------------------

	void copyAllUnresolved(boolean leftToRight) {
		if (fMerger.hasChanges() && isThreeWay() && !isIgnoreAncestor()) {
			IRewriteTarget target = leftToRight ? fRight.getRewriteTarget()
					: fLeft.getRewriteTarget();
			boolean compoundChangeStarted = false;
			try {
				for (Iterator iterator = fMerger.changesIterator(); iterator
						.hasNext();) {
					Diff diff = (Diff) iterator.next();
					switch (diff.getKind()) {
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
		if (!validateChange(!leftToRight))
			return;
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
		update(false);
		selectFirstDiff(true);
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
				navigate(true, true, false /* don't step in */);
			} else {
				revealDiff(diff, true);
				updateControls();
			}
		}
	}

	/*
	 * Copy the contents of the given diff from one side to the other but
	 * doesn't reveal anything. Returns true if copy was successful.
	 */
	private boolean copy(Diff diff, boolean leftToRight) {

		if (diff != null && !diff.isResolved()) {
			if (!validateChange(!leftToRight))
				return false;
			if (leftToRight) {
				fRight.setEnabled(true);
			} else {
				fLeft.setEnabled(true);
			}
			boolean result = fMerger.copy(diff, leftToRight);
			if (result)
				updateResolveStatus();
			return result;
		}
		return false;
	}

	private boolean validateChange(boolean left) {
		ContributorInfo info;
		if (left)
			info = fLeftContributor;
		else
			info = fRightContributor;

		return info.validateChange();
	}

	// ---- scrolling

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
	private int realToVirtualPosition(char contributor, int vpos) {
		if (!fSynchronizedScrolling)
			return vpos;
		return fMerger.realToVirtualPosition(contributor, vpos);
	}

	private void scrollVertical(int avpos, int lvpos, int rvpos,
			MergeSourceViewer allBut) {

		int s = 0;

		if (fSynchronizedScrolling) {
			s = fMerger.getVirtualHeight() - rvpos;
			int height = fRight.getViewportLines() / 4;
			if (s < 0)
				s = 0;
			if (s > height)
				s = height;
		}

		fInScrolling = true;

		if (isThreeWay() && allBut != fAncestor) {
			if (fSynchronizedScrolling || allBut == null) {
				int y = virtualToRealPosition(ANCESTOR_CONTRIBUTOR, avpos + s)
						- s;
				fAncestor.vscroll(y);
			}
		}

		if (allBut != fLeft) {
			if (fSynchronizedScrolling || allBut == null) {
				int y = virtualToRealPosition(LEFT_CONTRIBUTOR, lvpos + s) - s;
				fLeft.vscroll(y);
			}
		}

		if (allBut != fRight) {
			if (fSynchronizedScrolling || allBut == null) {
				int y = virtualToRealPosition(RIGHT_CONTRIBUTOR, rvpos + s) - s;
				fRight.vscroll(y);
			}
		}

		fInScrolling = false;

		if (isThreeWay() && fAncestorCanvas != null)
			fAncestorCanvas.repaint();

		if (fLeftCanvas != null)
			fLeftCanvas.repaint();

		Control center = getCenterControl();
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

		int viewPosition = realToVirtualPosition(getLeg(w), ix - ix2);

		scrollVertical(viewPosition, viewPosition, viewPosition, w); // scroll
																		// all
																		// but
																		// the
																		// given
																		// views

		if (fVScrollBar != null) {
			int value = Math.max(
					0,
					Math.min(viewPosition, fMerger.getVirtualHeight()
							- getViewportHeight()));
			fVScrollBar.setSelection(value);
			// refreshBirdEyeView();
		}
	}

	/**
	 */
	private void updateVScrollBar() {

		if (Utilities.okToUse(fVScrollBar) && fSynchronizedScrolling) {
			int virtualHeight = fMerger.getVirtualHeight();
			int viewPortHeight = getViewportHeight();
			int pageIncrement = viewPortHeight - 1;
			int thumb = (viewPortHeight > virtualHeight) ? virtualHeight
					: viewPortHeight;

			fVScrollBar.setPageIncrement(pageIncrement);
			fVScrollBar.setMaximum(virtualHeight);
			fVScrollBar.setThumb(thumb);
		}
	}

	/*
	 * maps given virtual position into a real view position of this view.
	 */
	private int virtualToRealPosition(char contributor, int v) {
		if (!fSynchronizedScrolling)
			return v;
		return fMerger.virtualToRealPosition(contributor, v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.compare.contentmergeviewer.ContentMergeViewer#flushContent
	 * (java.lang.Object, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void flushContentOld(Object oldInput, IProgressMonitor monitor) {

		// check and handle any shared buffers
		IMergeViewerContentProvider content = getMergeContentProvider();
		Object leftContent = content.getLeftContent(oldInput);
		Object rightContent = content.getRightContent(oldInput);

		if (leftContent != null && getCompareConfiguration().isLeftEditable()
				&& isLeftDirty()) {
			if (fLeftContributor.hasSharedDocument(leftContent)) {
				if (flush(fLeftContributor))
					setLeftDirty(false);
			}
		}

		if (rightContent != null && getCompareConfiguration().isRightEditable()
				&& isRightDirty()) {
			if (fRightContributor.hasSharedDocument(rightContent)) {
				if (flush(fRightContributor))
					setRightDirty(false);
			}
		}

		if (!(content instanceof MergeViewerContentProvider) || isLeftDirty()
				|| isRightDirty()) {
			super.flushContent(oldInput, monitor);
		}
	}

	protected void flushContent(Object oldInput, IProgressMonitor monitor) {
		flushLeftSide(oldInput, monitor);
		flushRightSide(oldInput, monitor);

		IMergeViewerContentProvider content = getMergeContentProvider();

		if (!(content instanceof MergeViewerContentProvider) || isLeftDirty()
				|| isRightDirty()) {
			super.flushContent(oldInput, monitor);
		}
	}

	private boolean flush(final ContributorInfo info) {
		try {
			return info.flush();
		} catch (CoreException e) {
			handleException(e);
		}
		return false;
	}

	private void handleException(Throwable throwable) {
		// TODO: Should show error to the user
		if (throwable instanceof InvocationTargetException) {
			InvocationTargetException ite = (InvocationTargetException) throwable;
			handleException(ite.getTargetException());
			return;
		}
		CompareUIPlugin.log(throwable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == IMergeViewerTestAdapter.class) {
			return new IMergeViewerTestAdapter() {
				public IDocument getDocument(char leg) {
					switch (leg) {
					case LEFT_CONTRIBUTOR:
						return fLeft.getDocument();
					case RIGHT_CONTRIBUTOR:
						return fRight.getDocument();
					case ANCESTOR_CONTRIBUTOR:
						return fAncestor.getDocument();
					}
					return null;
				}
			};
		}
		if (adapter == OutlineViewerCreator.class) {
			if (fOutlineViewerCreator == null)
				fOutlineViewerCreator = new InternalOutlineViewerCreator();
			return fOutlineViewerCreator;

		}
		if (adapter == IFindReplaceTarget.class)
			return getFindReplaceTarget();
		if (adapter == CompareHandlerService.class)
			return fHandlerService;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.compare.contentmergeviewer.ContentMergeViewer#
	 * handleCompareInputChange()
	 */
	protected void handleCompareInputChange() {
		try {
			beginRefresh();
			super.handleCompareInputChange();
		} finally {
			endRefresh();
		}
	}

	private void beginRefresh() {
		isRefreshing = true;
		fLeftContributor.cacheSelection(fLeft);
		fRightContributor.cacheSelection(fRight);
		fAncestorContributor.cacheSelection(fAncestor);
		if (fSynchronizedScrolling) {
			fSynchronziedScrollPosition = fVScrollBar.getSelection();
		}

	}

	private void endRefresh() {
		isRefreshing = false;
		fLeftContributor.cacheSelection(null);
		fRightContributor.cacheSelection(null);
		fAncestorContributor.cacheSelection(null);
		fSynchronziedScrollPosition = -1;
	}

	private void synchronizedScrollVertical(int vpos) {
		scrollVertical(vpos, vpos, vpos, null);
		workaround65205();
	}

	private boolean isIgnoreAncestor() {
		return Utilities.getBoolean(getCompareConfiguration(),
				ICompareUIConstants.PROP_IGNORE_ANCESTOR, false);
	}

	/* package */void update(boolean includeControls) {
		if (getControl().isDisposed())
			return;
		if (fHasErrors) {
			resetDiffs();
		} else {
			doDiff();
		}

		if (includeControls)
			updateControls();

		updateVScrollBar();
		updatePresentation(null);
	}

	private void resetDiffs() {
		// clear stuff
		fCurrentDiff = null;
		fMerger.reset();
		resetPositions(fLeft.getDocument());
		resetPositions(fRight.getDocument());
		resetPositions(fAncestor.getDocument());
	}

	private boolean isPatchHunk() {
		return Utilities.isHunk(getInput());
	}

	private boolean isPatchHunkOk() {
		if (isPatchHunk())
			return Utilities.isHunkOk(getInput());
		return false;
	}

	/**
	 * Return the provided start position of the hunk in the target file.
	 * 
	 * @return the provided start position of the hunk in the target file
	 */
	private int getHunkStart() {
		Object input = getInput();
		if (input != null && input instanceof DiffNode) {
			ITypedElement right = ((DiffNode) input).getRight();
			if (right != null) {
				Object element = Utilities.getAdapter(right, IHunk.class);
				if (element instanceof IHunk)
					return ((IHunk) element).getStartPosition();
			}
			ITypedElement left = ((DiffNode) input).getLeft();
			if (left != null) {
				Object element = Utilities.getAdapter(left, IHunk.class);
				if (element instanceof IHunk)
					return ((IHunk) element).getStartPosition();
			}
		}
		return 0;
	}

	private IFindReplaceTarget getFindReplaceTarget() {
		if (fFindReplaceTarget == null)
			fFindReplaceTarget = new FindReplaceTarget();
		return fFindReplaceTarget;
	}

	/* package */char getLeg(MergeSourceViewer w) {
		if (w == fLeft)
			return LEFT_CONTRIBUTOR;
		if (w == fRight)
			return RIGHT_CONTRIBUTOR;
		if (w == fAncestor)
			return ANCESTOR_CONTRIBUTOR;
		return ANCESTOR_CONTRIBUTOR;
	}

	private boolean isCurrentDiff(Diff diff) {
		if (diff == null)
			return false;
		if (diff == fCurrentDiff)
			return true;
		if (fCurrentDiff != null && fCurrentDiff.getParent() == diff)
			return true;
		return false;
	}

	private boolean isNavigationPossible() {
		if (fCurrentDiff == null && fMerger.hasChanges())
			return true;
		else if (fMerger.changesCount() > 1)
			return true;
		else if (fCurrentDiff != null && fCurrentDiff.hasChildren())
			return true;
		else if (fCurrentDiff != null && fCurrentDiff.isToken())
			return true;
		return false;
	}

	void flushLeftSide(Object oldInput, IProgressMonitor monitor) {
		IMergeViewerContentProvider content = getMergeContentProvider();
		Object leftContent = content.getLeftContent(oldInput);

		if (leftContent != null && getCompareConfiguration().isLeftEditable()
				&& isLeftDirty()) {
			if (fLeftContributor.hasSharedDocument(leftContent)) {
				if (flush(fLeftContributor))
					setLeftDirty(false);
			}
		}

		if (!(content instanceof MergeViewerContentProvider) || isLeftDirty()) {
			super.flushLeftSide(oldInput, monitor);
		}
	}

	void flushRightSide(Object oldInput, IProgressMonitor monitor) {
		IMergeViewerContentProvider content = getMergeContentProvider();
		Object rightContent = content.getRightContent(oldInput);

		if (rightContent != null && getCompareConfiguration().isRightEditable()
				&& isRightDirty()) {
			if (fRightContributor.hasSharedDocument(rightContent)) {
				if (flush(fRightContributor))
					setRightDirty(false);
			}
		}

		if (!(content instanceof MergeViewerContentProvider) || isRightDirty()) {
			super.flushRightSide(oldInput, monitor);
		}
	}
}
