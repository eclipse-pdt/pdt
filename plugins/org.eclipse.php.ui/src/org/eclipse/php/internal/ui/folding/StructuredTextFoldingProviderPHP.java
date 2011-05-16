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
package org.eclipse.php.internal.ui.folding;

import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.corext.SourceRange;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.*;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.internal.ui.folding.html.ProjectionModelNodeAdapterFactoryHTML;
import org.eclipse.php.internal.ui.folding.html.ProjectionModelNodeAdapterHTML;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.text.DocumentCharacterIterator;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.jsdt.core.IClassFile;
import org.eclipse.wst.jsdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.PropagatingAdapter;
import org.eclipse.wst.sse.core.internal.model.FactoryRegistry;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.Node;

/**
 * Updates the projection model of a structured model for JSP.
 */
public class StructuredTextFoldingProviderPHP implements IProjectionListener,
		IStructuredTextFoldingProvider {

	/**
	 * A context that contains the information needed to compute the folding
	 * structure of an {@link ICompilationUnit} or an {@link IClassFile}.
	 * Computed folding regions are collected via
	 * {@linkplain #addProjectionRange(StructuredTextFoldingProviderPHP.PhpProjectionAnnotation, Position)
	 * addProjectionRange}.
	 */
	protected final class FoldingStructureComputationContext {
		private final ProjectionAnnotationModel fModel;
		private final IDocument fDocument;

		private final boolean fAllowCollapsing;

		private IModelElement fFirstElement;
		private boolean fHasHeaderComment;
		private LinkedHashMap<Object, Position> fMap = new LinkedHashMap<Object, Position>();
		private ICommentScanner fScanner;
		private boolean headerChecked = false;

		private FoldingStructureComputationContext(IDocument document,
				ProjectionAnnotationModel model, boolean allowCollapsing) {
			Assert.isNotNull(document);
			Assert.isNotNull(model);
			fDocument = document;
			fModel = model;
			fAllowCollapsing = allowCollapsing;
		}

		private void setFirstElement(IModelElement element) {
			if (hasFirstElement())
				throw new IllegalStateException();
			fFirstElement = element;
		}

		boolean hasFirstElement() {
			return fFirstElement != null;
		}

		private IModelElement getFirstElement() {
			return fFirstElement;
		}

		private boolean hasHeaderComment() {
			return fHasHeaderComment;
		}

		private void setHasHeaderComment() {
			fHasHeaderComment = true;
		}

		/**
		 * Returns <code>true</code> if newly created folding regions may be
		 * collapsed, <code>false</code> if not. This is usually
		 * <code>false</code> when updating the folding structure while typing;
		 * it may be <code>true</code> when computing or restoring the initial
		 * folding structure.
		 * 
		 * @return <code>true</code> if newly created folding regions may be
		 *         collapsed, <code>false</code> if not
		 */
		public boolean allowCollapsing() {
			return fAllowCollapsing;
		}

		/**
		 * Returns the document which contains the code being folded.
		 * 
		 * @return the document which contains the code being folded
		 */
		private IDocument getDocument() {
			return fDocument;
		}

		private ProjectionAnnotationModel getModel() {
			return fModel;
		}

		private ICommentScanner getScanner() {
			if (fScanner == null)
				fScanner = new CommentScanner(fDocument);
			return fScanner;
		}

		/**
		 * Adds a projection (folding) region to this context. The created
		 * annotation / position pair will be added to the
		 * {@link ProjectionAnnotationModel} of the {@link ProjectionViewer} of
		 * the editor.
		 * 
		 * @param annotation
		 *            the annotation to add
		 * @param position
		 *            the corresponding position
		 */
		public void addProjectionRange(PhpProjectionAnnotation annotation,
				Position position) {
			fMap.put(annotation, position);
		}

		/**
		 * Returns <code>true</code> if header comments should be collapsed.
		 * 
		 * @return <code>true</code> if header comments should be collapsed
		 */
		public boolean collapseHeaderComments() {
			return fAllowCollapsing && fCollapseHeaderComments;
		}

		/**
		 * Returns <code>true</code> if import containers should be collapsed.
		 * 
		 * @return <code>true</code> if import containers should be collapsed
		 */
		public boolean collapseImportContainer() {
			return fAllowCollapsing && fCollapseImportContainer;
		}

		/**
		 * Returns <code>true</code> if inner types should be collapsed.
		 * 
		 * @return <code>true</code> if inner types should be collapsed
		 */
		public boolean collapseTypes() {
			return fAllowCollapsing && fCollapseTypes;
		}

		/**
		 * Returns <code>true</code> if javadoc comments should be collapsed.
		 * 
		 * @return <code>true</code> if javadoc comments should be collapsed
		 */
		public boolean collapseJavadoc() {
			return fAllowCollapsing && fCollapsePhpdoc;
		}

		/**
		 * Returns <code>true</code> if methods should be collapsed.
		 * 
		 * @return <code>true</code> if methods should be collapsed
		 */
		public boolean collapseMembers() {
			return fAllowCollapsing && fCollapseMembers;
		}

		/**
		 * @return true if the header was computed already
		 */
		public boolean isHeaderChecked() {
			return headerChecked;
		}

		/**
		 * set the header checked property
		 */
		public void setHeaderChecked() {
			headerChecked = true;
		}

	}

	private interface ICommentScanner {

		public abstract void resetTo(int start);

		public abstract int computePreviousComment();

		public abstract int getCurrentCommentStartPosition();

		public abstract int getCurrentCommentEndPosition();

	}

	public class CommentScanner implements ICommentScanner {

		private final IDocument document;
		private int startElement;
		private int start;
		private int end;

		public CommentScanner(IDocument document) {
			if (document == null) {
				throw new IllegalArgumentException();
			}
			this.document = document;
		}

		public void resetTo(int start) {
			this.startElement = start;
		}

		public int getCurrentCommentStartPosition() {
			return start;
		}

		public int computePreviousComment() {
			start = startElement - 1;

			try {
				// ignore whitespaces
				while (start > 0
						&& Character.isWhitespace(document.getChar(start))) {
					start--;
				}

				if (start > 0 && document.getChar(start--) != '/') {
					return startElement;
				}
				// remember the end of the comment
				int end = start;

				if (start > 0 && document.getChar(start--) != '*') {
					return startElement;
				}

				while (start > 0
						&& (document.getChar(start) != '*' || document
								.getChar(start - 1) != '/')) {
					start--;
				}

				if (start == 0)
					return startElement;
				else {
					this.end = end;
					return start;
				}

			} catch (BadLocationException e) {
				return startElement;
			}
		}

		public int getCurrentCommentEndPosition() {
			return this.end;
		}
	}

	/**
	 * A {@link ProjectionAnnotation} for java code.
	 */
	protected static final class PhpProjectionAnnotation extends
			ProjectionAnnotation {

		private IModelElement fJavaElement;
		private boolean fIsComment;

		/**
		 * Creates a new projection annotation.
		 * 
		 * @param isCollapsed
		 *            <code>true</code> to set the initial state to collapsed,
		 *            <code>false</code> to set it to expanded
		 * @param element
		 *            the java element this annotation refers to
		 * @param isComment
		 *            <code>true</code> for a foldable comment,
		 *            <code>false</code> for a foldable code element
		 */
		public PhpProjectionAnnotation(boolean isCollapsed,
				IModelElement element, boolean isComment) {
			super(isCollapsed);
			fJavaElement = element;
			fIsComment = isComment;
		}

		IModelElement getElement() {
			return fJavaElement;
		}

		void setElement(IModelElement element) {
			fJavaElement = element;
		}

		boolean isComment() {
			return fIsComment;
		}

		void setIsComment(boolean isComment) {
			fIsComment = isComment;
		}

		/*
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "PhpProjectionAnnotation:\n" + //$NON-NLS-1$
					"\telement: \t" + fJavaElement.toString() + "\n" + //$NON-NLS-1$ //$NON-NLS-2$
					"\tcollapsed: \t" + isCollapsed() + "\n" + //$NON-NLS-1$ //$NON-NLS-2$
					"\tcomment: \t" + isComment() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private static final class Tuple {
		PhpProjectionAnnotation annotation;
		Position position;

		Tuple(PhpProjectionAnnotation annotation, Position position) {
			this.annotation = annotation;
			this.position = position;
		}
	}

	/**
	 * Filter for annotations.
	 */
	private static interface Filter {
		boolean match(PhpProjectionAnnotation annotation);
	}

	/**
	 * Matches comments.
	 */
	private static final class CommentFilter implements Filter {
		public boolean match(PhpProjectionAnnotation annotation) {
			if (annotation.isComment() && !annotation.isMarkedDeleted()) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Matches members.
	 */
	private static final class MemberFilter implements Filter {
		public boolean match(PhpProjectionAnnotation annotation) {
			if (!annotation.isComment() && !annotation.isMarkedDeleted()) {
				IModelElement element = annotation.getElement();
				if (element instanceof IMember) {
					if (element.getElementType() != IModelElement.TYPE
							|| ((IMember) element).getDeclaringType() != null) {
						return true;
					}
				}
			}
			return false;
		}
	}

	/**
	 * Matches java elements contained in a certain set.
	 */
	private static final class PhpElementSetFilter implements Filter {
		private final Set/* <? extends IModelElement> */<IModelElement> fSet;
		private final boolean fMatchCollapsed;

		private PhpElementSetFilter(
				Set/* <? extends IModelElement> */<IModelElement> set,
				boolean matchCollapsed) {
			fSet = set;
			fMatchCollapsed = matchCollapsed;
		}

		public boolean match(PhpProjectionAnnotation annotation) {
			boolean stateMatch = fMatchCollapsed == annotation.isCollapsed();
			if (stateMatch && !annotation.isComment()
					&& !annotation.isMarkedDeleted()) {
				IModelElement element = annotation.getElement();
				if (fSet.contains(element)) {
					return true;
				}
			}
			return false;
		}
	}

	private class ElementChangedListener implements IElementChangedListener {

		/*
		 * @see
		 * org.eclipse.jdt.core.IElementChangedListener#elementChanged(org.eclipse
		 * .jdt.core.ElementChangedEvent)
		 */
		public void elementChanged(ElementChangedEvent e) {
			IModelElementDelta delta = findElement(fInput, e.getDelta());
			if (delta != null
					&& (delta.getFlags() & (IModelElementDelta.F_CONTENT | IModelElementDelta.F_CHILDREN)) != 0) {

				if (shouldIgnoreDelta(e.getDelta().getElement(), delta))
					return;

				fUpdatingCount++;
				try {
					update(createContext(false));
				} finally {
					fUpdatingCount--;
				}
			}
		}

		/**
		 * Ignore the delta if there are errors on the caret line.
		 * <p>
		 * We don't ignore the delta if an import is added and the caret isn't
		 * inside the import container.
		 * </p>
		 * 
		 * @param ast
		 *            the compilation unit AST
		 * @param delta
		 *            the Java element delta for the given AST element
		 * @return <code>true</code> if the delta should be ignored
		 * @since 3.3
		 */
		private boolean shouldIgnoreDelta(IModelElement ast,
				IModelElementDelta delta) {
			if (ast == null)
				return false; // can't compute

			if (!(ast.getResource() instanceof IFile)) {
				return false;
			}
			final IFile resource = (IFile) ast.getResource();

			IDocument document = getDocument();
			if (document == null)
				return false; // can't compute

			PHPStructuredEditor editor = fEditor;
			if (editor == null || editor.getCachedSelectedRange() == null)
				return false; // can't compute

			int caretLine = 0;
			try {
				caretLine = document.getLineOfOffset(editor
						.getCachedSelectedRange().x) + 1;
			} catch (BadLocationException x) {
				return false; // can't compute
			}

			if (caretLine > 0) {
				try {
					IMarker[] problems = resource.findMarkers(
							DefaultProblem.MARKER_TYPE_PROBLEM, false,
							IResource.DEPTH_INFINITE);
					for (int i = 0; i < problems.length; i++) {
						final IMarker marker = problems[i];
						final boolean isInCaret = isCaretLine(caretLine,
								IMarker.LINE_NUMBER, marker);
						final boolean isSyntaxError = isCaretLine(
								IMarker.SEVERITY_ERROR, IMarker.SEVERITY,
								marker);
						if (isSyntaxError && isInCaret) {
							return true;
						}
					}

				} catch (CoreException e) {
					return false;
				}
			}
			return false;
		}

		private final boolean isCaretLine(int expected, String attribute,
				final IMarker marker) throws CoreException {
			final Object res = marker.getAttribute(attribute); // IMarker.LINE_NUMBER
			return res != null && res instanceof Integer
					&& (Integer) res == expected;
		}

		private IModelElementDelta findElement(IModelElement target,
				IModelElementDelta delta) {

			if (delta == null || target == null)
				return null;

			IModelElement element = delta.getElement();

			if (element.getElementType() > IModelElement.BINARY_MODULE)
				return null;

			if (target.equals(element))
				return delta;

			IModelElementDelta[] children = delta.getAffectedChildren();

			for (int i = 0; i < children.length; i++) {
				IModelElementDelta d = findElement(target, children[i]);
				if (d != null)
					return d;
			}

			return null;
		}
	}

	/**
	 * Projection position that will return two foldable regions: one folding
	 * away the region from after the '/**' to the beginning of the content, the
	 * other from after the first content line until after the comment.
	 */
	private static final class CommentPosition extends Position implements
			IProjectionPosition {
		CommentPosition(int offset, int length) {
			super(offset, length);
		}

		/*
		 * @seeorg.eclipse.jface.text.source.projection.IProjectionPosition#
		 * computeFoldingRegions(org.eclipse.jface.text.IDocument)
		 */
		public IRegion[] computeProjectionRegions(IDocument document)
				throws BadLocationException {
			DocumentCharacterIterator sequence = new DocumentCharacterIterator(
					document, offset, offset + length);
			int prefixEnd = 0;
			int contentStart = findFirstContent(sequence, prefixEnd);

			int firstLine = document.getLineOfOffset(offset + prefixEnd);
			int captionLine = document.getLineOfOffset(offset + contentStart);
			int lastLine = document.getLineOfOffset(offset + length);

			Assert.isTrue(firstLine <= captionLine,
					"first folded line is greater than the caption line"); //$NON-NLS-1$
			Assert.isTrue(captionLine <= lastLine,
					"caption line is greater than the last folded line"); //$NON-NLS-1$

			IRegion preRegion;
			if (firstLine < captionLine) {
				int preOffset = document.getLineOffset(firstLine);
				IRegion preEndLineInfo = document
						.getLineInformation(captionLine);
				int preEnd = preEndLineInfo.getOffset();
				preRegion = new Region(preOffset, preEnd - preOffset);
			} else {
				preRegion = null;
			}

			if (captionLine < lastLine) {
				int postOffset = document.getLineOffset(captionLine + 1);
				IRegion postRegion = new Region(postOffset, offset + length
						- postOffset);

				if (preRegion == null)
					return new IRegion[] { postRegion };

				return new IRegion[] { preRegion, postRegion };
			}

			if (preRegion != null)
				return new IRegion[] { preRegion };

			return null;
		}

		/**
		 * Finds the offset of the first identifier part within
		 * <code>content</code>. Returns 0 if none is found.
		 * 
		 * @param content
		 *            the content to search
		 * @param prefixEnd
		 *            the end of the prefix
		 * @return the first index of a unicode identifier part, or zero if none
		 *         can be found
		 */
		private int findFirstContent(final CharSequence content, int prefixEnd) {
			int lenght = content.length();
			for (int i = prefixEnd; i < lenght; i++) {
				if (Character.isUnicodeIdentifierPart(content.charAt(i)))
					return i;
			}
			return 0;
		}

		/*
		 * @seeorg.eclipse.jface.text.source.projection.IProjectionPosition#
		 * computeCaptionOffset(org.eclipse.jface.text.IDocument)
		 */
		public int computeCaptionOffset(IDocument document) {
			DocumentCharacterIterator sequence = new DocumentCharacterIterator(
					document, offset, offset + length);
			return findFirstContent(sequence, 0);
		}
	}

	/**
	 * Projection position that will return two foldable regions: one folding
	 * away the lines before the one containing the simple name of the php
	 * element, one folding away any lines after the caption.
	 */
	private static final class PhpElementPosition extends Position implements
			IProjectionPosition {

		private IMember fMember;

		public PhpElementPosition(int offset, int length, IMember member) {
			super(offset, length);
			Assert.isNotNull(member);
			fMember = member;
		}

		public void setMember(IMember member) {
			Assert.isNotNull(member);
			fMember = member;
		}

		/*
		 * @seeorg.eclipse.jface.text.source.projection.IProjectionPosition#
		 * computeFoldingRegions(org.eclipse.jface.text.IDocument)
		 */
		public IRegion[] computeProjectionRegions(IDocument document)
				throws BadLocationException {
			int nameStart = offset;
			try {
				/*
				 * The member's name range may not be correct. However,
				 * reconciling would trigger another element delta which would
				 * lead to reentrant situations. Therefore, we optimistically
				 * assume that the name range is correct, but double check the
				 * received lines below.
				 */
				ISourceRange nameRange = fMember.getNameRange();
				if (nameRange != null)
					nameStart = nameRange.getOffset();

			} catch (ModelException e) {
				// ignore and use default
			}

			int firstLine = document.getLineOfOffset(offset);
			int captionLine = document.getLineOfOffset(nameStart);
			int lastLine = document.getLineOfOffset(offset + length);

			/*
			 * see comment above - adjust the caption line to be inside the
			 * entire folded region, and rely on later element deltas to correct
			 * the name range.
			 */
			if (captionLine < firstLine)
				captionLine = firstLine;
			if (captionLine > lastLine)
				captionLine = lastLine;

			IRegion preRegion;
			if (firstLine < captionLine) {
				int preOffset = document.getLineOffset(firstLine);
				IRegion preEndLineInfo = document
						.getLineInformation(captionLine);
				int preEnd = preEndLineInfo.getOffset();
				preRegion = new Region(preOffset, preEnd - preOffset);
			} else {
				preRegion = null;
			}

			if (captionLine < lastLine) {
				int postOffset = document.getLineOffset(captionLine + 1);
				IRegion postRegion = new Region(postOffset, offset + length
						- postOffset);

				if (preRegion == null)
					return new IRegion[] { postRegion };

				return new IRegion[] { preRegion, postRegion };
			}

			if (preRegion != null)
				return new IRegion[] { preRegion };

			return null;
		}

		/*
		 * @seeorg.eclipse.jface.text.source.projection.IProjectionPosition#
		 * computeCaptionOffset(org.eclipse.jface.text.IDocument)
		 */
		public int computeCaptionOffset(IDocument document)
				throws BadLocationException {
			int nameStart = offset;
			try {
				// need a reconcile here?
				ISourceRange nameRange = fMember.getNameRange();
				if (nameRange != null)
					nameStart = nameRange.getOffset();
			} catch (ModelException e) {
				// ignore and use default
			}

			return nameStart - offset;
		}

	}

	/**
	 * Internal projection listener.
	 */
	private final class ProjectionListener implements IProjectionListener {
		private ProjectionViewer fViewer;

		/**
		 * Registers the listener with the viewer.
		 * 
		 * @param viewer
		 *            the viewer to register a listener with
		 */
		public ProjectionListener(ProjectionViewer viewer) {
			Assert.isLegal(viewer != null);
			fViewer = viewer;
			fViewer.addProjectionListener(this);
		}

		/**
		 * Disposes of this listener and removes the projection listener from
		 * the viewer.
		 */
		public void dispose() {
			if (fViewer != null) {
				fViewer.removeProjectionListener(this);
				fViewer = null;
			}
		}

		/*
		 * @seeorg.eclipse.jface.text.source.projection.IProjectionListener#
		 * projectionEnabled()
		 */
		public void projectionEnabled() {
			handleProjectionEnabled();
		}

		/*
		 * @seeorg.eclipse.jface.text.source.projection.IProjectionListener#
		 * projectionDisabled()
		 */
		public void projectionDisabled() {
			handleProjectionDisabled();
		}
	}

	/* context and listeners */
	private PHPStructuredEditor fEditor;
	private ProjectionListener fProjectionListener;
	private IModelElement fInput;
	private IElementChangedListener fElementListener;

	/* preferences */
	private boolean fCollapsePhpdoc = true;
	private boolean fCollapseImportContainer = true;
	private boolean fCollapseTypes = true;
	private boolean fCollapseMembers = false;
	private boolean fCollapseHeaderComments = true;

	/* filters */
	/** Member filter, matches nested members (but not top-level types). */
	private final Filter fMemberFilter = new MemberFilter();
	/** Comment filter, matches comments. */
	private final Filter fCommentFilter = new CommentFilter();

	private volatile int fUpdatingCount = 0;
	private PHPStructuredTextViewer viewer;
	private IDocument fDocument;
	/**
	 * Maximum number of child nodes to add adapters to (limit for performance
	 * sake)
	 */
	private static final int MAX_CHILDREN = 10;
	/**
	 * Maximum number of sibling nodes to add adapters to (limit for performance
	 * sake)
	 */
	private static final int MAX_SIBLINGS = 1000;

	/**
	 * Creates a new folding provider. It must be
	 * {@link #install(ITextEditor, ProjectionViewer) installed} on an
	 * editor/viewer pair before it can be used, and {@link #uninstall()
	 * uninstalled} when not used any longer.
	 * <p>
	 * The projection state may be reset by calling {@link #initialize()}.
	 * </p>
	 */
	public StructuredTextFoldingProviderPHP() {
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Subclasses may extend.
	 * </p>
	 * 
	 * @param editor
	 *            {@inheritDoc}
	 * @param viewer
	 *            {@inheritDoc}
	 */
	public void install(ProjectionViewer viewer) {
		Assert.isLegal(viewer != null);

		internalUninstall();

		PHPStructuredTextViewer viewer1 = (PHPStructuredTextViewer) viewer;
		ITextEditor editor = viewer1.getTextEditor();

		this.viewer = viewer1;

		Assert.isLegal(editor != null);

		if (editor instanceof PHPStructuredEditor) {
			fProjectionListener = new ProjectionListener(viewer);
			fEditor = (PHPStructuredEditor) editor;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Subclasses may extend.
	 * </p>
	 */
	public void uninstall() {
		internalUninstall();
	}

	/**
	 * Internal implementation of {@link #uninstall()}.
	 */
	private void internalUninstall() {
		if (isInstalled()) {
			handleProjectionDisabled();
			fProjectionListener.dispose();
			fProjectionListener = null;
			fEditor = null;
		}
	}

	/**
	 * Returns <code>true</code> if the provider is installed,
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if the provider is installed,
	 *         <code>false</code> otherwise
	 */
	protected final boolean isInstalled() {
		return fEditor != null;
	}

	/**
	 * Called whenever projection is enabled, for example when the viewer issues
	 * a {@link IProjectionListener#projectionEnabled() projectionEnabled}
	 * message. When the provider is already enabled when this method is called,
	 * it is first {@link #handleProjectionDisabled() disabled}.
	 * <p>
	 * Subclasses may extend.
	 * </p>
	 */
	protected void handleProjectionEnabled() {
		// http://home.ott.oti.com/teams/wswb/anon/out/vms/index.html
		// projectionEnabled messages are not always paired with
		// projectionDisabled
		// i.e. multiple enabled messages may be sent out.
		// we have to make sure that we disable first when getting an enable
		// message.
		handleProjectionDisabled();

		if (isInstalled()) {
			initialize();
			fElementListener = new ElementChangedListener();
			DLTKCore.addElementChangedListener(fElementListener);
		}
	}

	/**
	 * Called whenever projection is disabled, for example when the provider is
	 * {@link #uninstall() uninstalled}, when the viewer issues a
	 * {@link IProjectionListener#projectionDisabled() projectionDisabled}
	 * message and before {@link #handleProjectionEnabled() enabling} the
	 * provider. Implementations must be prepared to handle multiple calls to
	 * this method even if the provider is already disabled.
	 * <p>
	 * Subclasses may extend.
	 * </p>
	 */
	protected void handleProjectionDisabled() {
		if (fElementListener != null) {
			DLTKCore.removeElementChangedListener(fElementListener);
			fElementListener = null;
		}

		final ProjectionModelNodeAdapterFactoryHTML factory2 = getAdapterFactoryHTML(false);
		if (factory2 != null) {
			factory2.removeProjectionViewer(viewer);
		}

		// clear out all annotations
		if (viewer.getProjectionAnnotationModel() != null) {
			viewer.getProjectionAnnotationModel().removeAllAnnotations();
		}

		removeAllAdapters();

		fDocument = null;
	}

	/**
	 * Goes through every node and removes adapter from each for cleanup
	 * purposes
	 */
	private void removeAllAdapters() {
		if (fDocument != null) {
			IStructuredModel sModel = null;
			try {
				sModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(fDocument);
				if (sModel != null) {
					final int startOffset = 0;
					final IndexedRegion startNode = sModel
							.getIndexedRegion(startOffset);
					if (startNode instanceof Node) {
						Node nextSibling = (Node) startNode;
						while (nextSibling != null) {
							final Node currentNode = nextSibling;
							nextSibling = currentNode.getNextSibling();

							removeAdapterFromNodeAndChildren(currentNode, 0);
						}
					}
				}
			} finally {
				if (sModel != null) {
					sModel.releaseFromRead();
				}
			}
		}

	}

	/**
	 * Removes an adapter from node and its children
	 * 
	 * @param node
	 * @param level
	 */
	private void removeAdapterFromNodeAndChildren(Node node, int level) {
		if (node instanceof INodeNotifier) {
			final INodeNotifier notifier = (INodeNotifier) node;

			// try and get the adapter for the current node and remove it
			final INodeAdapter adapter2 = notifier
					.getExistingAdapter(ProjectionModelNodeAdapterHTML.class);
			if (adapter2 != null) {
				notifier.removeAdapter(adapter2);
			}

			Node nextChild = node.getFirstChild();
			while (nextChild != null) {
				final Node childNode = nextChild;
				nextChild = childNode.getNextSibling();

				removeAdapterFromNodeAndChildren(childNode, level + 1);
			}
		}
	}

	/*
	 * @see
	 * org.eclipse.jdt.ui.text.folding.IJavaFoldingStructureProvider#initialize
	 * ()
	 */
	public final void initialize() {
		if (viewer != null) {
			fDocument = viewer.getDocument();

			// set projection viewer on new document's adapter factory
			if (viewer.getProjectionAnnotationModel() != null) {
				final ProjectionModelNodeAdapterFactoryHTML factory2 = getAdapterFactoryHTML(true);
				if (factory2 != null) {
					factory2.addProjectionViewer(viewer);
				}

				addAllAdapters();
			}
		}

		fUpdatingCount++;
		try {
			update(createInitialContext());
		} finally {
			fUpdatingCount--;
		}

	}

	/**
	 * Goes through every node and adds an adapter onto each for tracking
	 * purposes
	 */
	private void addAllAdapters() {
		final long start = System.currentTimeMillis();

		if (fDocument != null) {
			IStructuredModel sModel = null;
			try {
				sModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(fDocument);
				if (sModel != null) {
					IndexedRegion startNode = sModel.getIndexedRegion(0);
					if (startNode == null) {
						assert sModel instanceof IDOMModel;
						startNode = ((IDOMModel) sModel).getDocument();
					}

					if (startNode instanceof Node) {
						int siblingLevel = 0;
						Node nextSibling = (Node) startNode;

						while (nextSibling != null
								&& siblingLevel < MAX_SIBLINGS) {
							final Node currentNode = nextSibling;
							nextSibling = currentNode.getNextSibling();

							addAdapterToNodeAndChildrenHTML(currentNode, 0);
							++siblingLevel;
						}
					}
				}
			} finally {
				if (sModel != null) {
					sModel.releaseFromRead();
				}
			}
		}
	}

	/**
	 * Adds an adapter to node and its children
	 * 
	 * @param node
	 * @param childLevel
	 */
	private void addAdapterToNodeAndChildrenHTML(Node node, int childLevel) {
		// stop adding initial adapters MAX_CHILDREN levels deep for
		// performance sake
		if (node instanceof INodeNotifier && childLevel < MAX_CHILDREN) {
			final INodeNotifier notifier = (INodeNotifier) node;

			// try and get the adapter for the current node and update the
			// adapter with projection information
			final ProjectionModelNodeAdapterHTML adapter2 = (ProjectionModelNodeAdapterHTML) notifier
					.getExistingAdapter(ProjectionModelNodeAdapterHTML.class);
			if (adapter2 != null) {
				adapter2.updateAdapter(node);
			} else {
				// just call getadapter so the adapter is created and
				// automatically initialized
				notifier.getAdapterFor(ProjectionModelNodeAdapterHTML.class);
			}
			int siblingLevel = 0;
			Node nextChild = node.getFirstChild();
			while (nextChild != null && siblingLevel < MAX_SIBLINGS) {
				final Node childNode = nextChild;
				nextChild = childNode.getNextSibling();

				addAdapterToNodeAndChildrenHTML(childNode, childLevel + 1);
				++siblingLevel;
			}
		}
	}

	/**
	 * Get the ProjectionModelNodeAdapterFactoryHTML to use with this provider.
	 * 
	 * @return ProjectionModelNodeAdapterFactoryHTML
	 */
	private ProjectionModelNodeAdapterFactoryHTML getAdapterFactoryHTML(
			boolean createIfNeeded) {
		final long start = System.currentTimeMillis();

		ProjectionModelNodeAdapterFactoryHTML factory = null;
		if (fDocument != null) {
			IStructuredModel sModel = null;
			try {
				sModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(fDocument);
				if (sModel != null && (sModel instanceof IDOMModel)) {
					final FactoryRegistry factoryRegistry = sModel
							.getFactoryRegistry();

					// getting the projectionmodelnodeadapter for the first
					// time
					// so do some initializing
					if (!factoryRegistry
							.contains(ProjectionModelNodeAdapterHTML.class)
							&& createIfNeeded) {
						final ProjectionModelNodeAdapterFactoryHTML newFactory = new ProjectionModelNodeAdapterFactoryHTML();

						// add factory to factory registry
						factoryRegistry.addFactory(newFactory);

						// add factory to propogating adapter
						final IDOMModel domModel = (IDOMModel) sModel;
						final IDOMDocument document = domModel.getDocument();
						final PropagatingAdapter propagatingAdapter = (PropagatingAdapter) ((INodeNotifier) document)
								.getAdapterFor(PropagatingAdapter.class);
						if (propagatingAdapter != null) {
							propagatingAdapter
									.addAdaptOnCreateFactory(newFactory);
						}
					}

					// try and get the factory
					factory = (ProjectionModelNodeAdapterFactoryHTML) factoryRegistry
							.getFactoryFor(ProjectionModelNodeAdapterHTML.class);
				}
			} finally {
				if (sModel != null) {
					sModel.releaseFromRead();
				}
			}
		}

		return factory;
	}

	private FoldingStructureComputationContext createInitialContext() {
		initializePreferences();
		fInput = getInputElement();
		if (fInput == null)
			return null;

		return createContext(true);
	}

	private FoldingStructureComputationContext createContext(
			boolean allowCollapse) {
		if (!isInstalled())
			return null;
		ProjectionAnnotationModel model = getModel();
		if (model == null)
			return null;
		IDocument doc = getDocument();
		if (doc == null)
			return null;

		return new FoldingStructureComputationContext(doc, model, allowCollapse);
	}

	private IModelElement getInputElement() {
		if (fEditor == null)
			return null;
		return EditorUtility.getEditorInputModelElement(fEditor, false);
	}

	private void initializePreferences() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		// fCollapseImportContainer=
		// store.getBoolean(PreferenceConstants.EDITOR_FOLDING_IMPORTS);
		fCollapseTypes = store
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_CLASSES);
		fCollapsePhpdoc = store
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_PHPDOC);
		fCollapseMembers = store
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_FUNCTIONS);
		fCollapseHeaderComments = store
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_HEADER_COMMENTS);
	}

	private void update(FoldingStructureComputationContext ctx) {
		if (ctx == null)
			return;

		Map<PhpProjectionAnnotation, Position> additions = new HashMap<PhpProjectionAnnotation, Position>();
		List<PhpProjectionAnnotation> deletions = new ArrayList<PhpProjectionAnnotation>();
		List<PhpProjectionAnnotation> updates = new ArrayList<PhpProjectionAnnotation>();

		computeFoldingStructure(ctx);
		Map<Object, Position> newStructure = ctx.fMap;
		Map<IModelElement, Object> oldStructure = computeCurrentStructure(ctx);

		Iterator<Object> e = newStructure.keySet().iterator();
		while (e.hasNext()) {
			PhpProjectionAnnotation newAnnotation = (PhpProjectionAnnotation) e
					.next();
			Position newPosition = newStructure.get(newAnnotation);

			IModelElement element = newAnnotation.getElement();
			/*
			 * See https://bugs.eclipse.org/bugs/show_bug.cgi?id=130472 and
			 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=127445 In the
			 * presence of syntax errors, anonymous types may have a source
			 * range offset of 0. When such a situation is encountered, we
			 * ignore the proposed folding range: if no corresponding folding
			 * range exists, it is silently ignored; if there *is* a matching
			 * folding range, we ignore the position update and keep the old
			 * range, in order to keep the folding structure stable.
			 */
			boolean isMalformedAnonymousType = newPosition.getOffset() == 0
					&& element.getElementType() == IModelElement.TYPE;
			List annotations = (List) oldStructure.get(element);
			if (annotations == null) {
				if (!isMalformedAnonymousType)
					additions.put(newAnnotation, newPosition);
			} else {
				Iterator x = annotations.iterator();
				boolean matched = false;
				while (x.hasNext()) {
					Tuple tuple = (Tuple) x.next();
					PhpProjectionAnnotation existingAnnotation = tuple.annotation;
					Position existingPosition = tuple.position;
					if (newAnnotation.isComment() == existingAnnotation
							.isComment()) {
						boolean updateCollapsedState = ctx.allowCollapsing()
								&& existingAnnotation.isCollapsed() != newAnnotation
										.isCollapsed();
						if (!isMalformedAnonymousType
								&& existingPosition != null
								&& (!newPosition.equals(existingPosition) || updateCollapsedState)) {
							existingPosition.setOffset(newPosition.getOffset());
							existingPosition.setLength(newPosition.getLength());
							if (updateCollapsedState)
								if (newAnnotation.isCollapsed())
									existingAnnotation.markCollapsed();
								else
									existingAnnotation.markExpanded();
							updates.add(existingAnnotation);
						}
						matched = true;
						x.remove();
						break;
					}
				}
				if (!matched)
					additions.put(newAnnotation, newPosition);

				if (annotations.isEmpty())
					oldStructure.remove(element);
			}
		}

		e = oldStructure.values().iterator();
		while (e.hasNext()) {
			List list = (List) e.next();
			int size = list.size();
			for (int i = 0; i < size; i++)
				deletions.add(((Tuple) list.get(i)).annotation);
		}

		match(deletions, additions, updates, ctx);

		Annotation[] deletedArray = deletions.toArray(new Annotation[deletions
				.size()]);
		Annotation[] changedArray = updates.toArray(new Annotation[updates
				.size()]);
		ctx.getModel().modifyAnnotations(deletedArray, additions, changedArray);
	}

	private void computeFoldingStructure(FoldingStructureComputationContext ctx) {
		IParent parent = (IParent) fInput;
		try {
			if (!(fInput instanceof ISourceReference))
				return;
			computeFoldingStructure(parent.getChildren(), ctx);
		} catch (ModelException x) {
		}
	}

	private void computeFoldingStructure(IModelElement[] elements,
			FoldingStructureComputationContext ctx) throws ModelException {
		for (int i = 0; i < elements.length; i++) {
			IModelElement element = elements[i];

			computeFoldingStructure(element, ctx);

			if (element instanceof IParent) {
				IParent parent = (IParent) element;
				computeFoldingStructure(parent.getChildren(), ctx);
			}
		}
	}

	/**
	 * Computes the folding structure for a given {@link IModelElement java
	 * element}. Computed projection annotations are
	 * {@link StructuredTextFoldingProviderPHP.FoldingStructureComputationContext#addProjectionRange(StructuredTextFoldingProviderPHP.PhpProjectionAnnotation, Position)
	 * added} to the computation context.
	 * <p>
	 * Subclasses may extend or replace. The default implementation creates
	 * projection annotations for the following elements:
	 * <ul>
	 * <li>true members (not for top-level types)</li>
	 * <li>the javadoc comments of any member</li>
	 * <li>header comments (javadoc or multi-line comments appearing before the
	 * first type's javadoc or before the package or import declarations).</li>
	 * </ul>
	 * </p>
	 * 
	 * @param element
	 *            the java element to compute the folding structure for
	 * @param ctx
	 *            the computation context
	 */
	protected void computeFoldingStructure(IModelElement element,
			FoldingStructureComputationContext ctx) {

		boolean collapse = false;
		boolean collapseCode = true;
		switch (element.getElementType()) {

		// TODO : ask DLTK to have include container
		case IModelElement.IMPORT_CONTAINER:
			collapse = ctx.collapseImportContainer();
			break;
		case IModelElement.TYPE:
			collapse = ctx.collapseTypes();
			break;
		case IModelElement.METHOD:
			collapse = ctx.collapseMembers();
			break;
		case IModelElement.FIELD:
			// class fields should be folded as well
			IModelElement parent = element.getParent();
			if (parent != null && parent.getElementType() != IModelElement.TYPE) {
				return;
			}
			collapse = ctx.collapseMembers();
			break;
		default:
			return;
		}

		IRegion[] regions = computeProjectionRanges((ISourceReference) element,
				ctx);
		if (regions.length > 0) {
			// comments
			// use the set to filter the duplicate comment IRegion,or sometimes
			// you will see the header comment is collapsed but with a expanded
			// iamge,this is because there are two ProjectionAnnotations for one
			// comment,and the second ProjectionAnnotation's image overide the
			// header one
			Set<IRegion> regionSet = new HashSet<IRegion>();
			for (int i = 0; i < regions.length - 1; i++) {
				IRegion normalized = alignRegion(regions[i], ctx);
				if (normalized != null && regionSet.add(normalized)) {
					Position position = createCommentPosition(normalized);
					if (position != null) {
						boolean commentCollapse;
						if (i == 0
								&& (regions.length > 2 || ctx
										.hasHeaderComment())
								&& element == ctx.getFirstElement()) {
							commentCollapse = ctx.collapseHeaderComments();
						} else {
							commentCollapse = ctx.collapseJavadoc();
						}
						ctx.addProjectionRange(new PhpProjectionAnnotation(
								commentCollapse, element, true), position);
					}
				}
			}
			// code
			if (collapseCode) {
				IRegion normalized = alignRegion(regions[regions.length - 1],
						ctx);
				if (normalized != null) {
					Position position = element instanceof IMember ? createMemberPosition(
							normalized, (IMember) element)
							: createCommentPosition(normalized);
					if (position != null)
						ctx.addProjectionRange(new PhpProjectionAnnotation(
								collapse, element, false), position);
				}
			}
		}
	}

	/**
	 * Computes the projection ranges for a given <code>ISourceReference</code>.
	 * More than one range or none at all may be returned. If there are no
	 * foldable regions, an empty array is returned.
	 * <p>
	 * The last region in the returned array (if not empty) describes the region
	 * for the java element that implements the source reference. Any preceding
	 * regions describe javadoc comments of that java element.
	 * </p>
	 * 
	 * @param reference
	 *            a java element that is a source reference
	 * @param ctx
	 *            the folding context
	 * @return the regions to be folded
	 */
	protected final IRegion[] computeProjectionRanges(
			ISourceReference reference, FoldingStructureComputationContext ctx) {
		try {
			ISourceRange range = reference.getSourceRange();
			if (!SourceRange.isAvailable(range))
				return new IRegion[0];
			List<IRegion> regions = new ArrayList<IRegion>();
			if (!ctx.isHeaderChecked() && reference instanceof IModelElement) {
				ctx.setFirstElement((IModelElement) reference);
				ctx.setHeaderChecked();

				IRegion headerComment = computeHeaderComment(ctx);
				if (headerComment != null) {
					regions.add(headerComment);
					ctx.setHasHeaderComment();
				}
			}

			final int shift = range.getOffset();
			ICommentScanner scanner = ctx.getScanner();
			scanner.resetTo(shift);

			int start = shift;

			start = scanner.computePreviousComment();

			if (start != shift) {
				start = scanner.getCurrentCommentStartPosition();
				int end = scanner.getCurrentCommentEndPosition();
				regions.add(new Region(start, end - start));
			}

			// shift -> start
			regions.add(new Region(shift, range.getLength()));

			IRegion[] result = new IRegion[regions.size()];
			regions.toArray(result);
			return result;
		} catch (ModelException e) {
		}

		return new IRegion[0];
	}

	private IRegion computeHeaderComment(FoldingStructureComputationContext ctx)
			throws ModelException {
		if (!(ctx.getDocument() instanceof IStructuredDocument)) {
			return null;
		}
		final IStructuredDocument document = (IStructuredDocument) ctx
				.getDocument();
		IStructuredDocumentRegion sdRegion = document
				.getFirstStructuredDocumentRegion();
		int i = 0;
		while (sdRegion != null
				&& sdRegion.getType() != PHPRegionTypes.PHP_CONTENT && i++ < 40) {
			sdRegion = sdRegion.getNext();
		}

		if (sdRegion == null
				|| sdRegion.getType() != PHPRegionTypes.PHP_CONTENT
				|| sdRegion.getRegions().size() < 2) {
			return null;
		}

		final IPhpScriptRegion textRegion = (IPhpScriptRegion) sdRegion
				.getRegions().get(1);
		try {
			ITextRegion phpToken = textRegion.getPhpToken(0);
			i = 0;
			while (phpToken != null
					&& phpToken.getType() != PHPRegionTypes.PHPDOC_COMMENT_START
					&& i++ < 3) {
				phpToken = textRegion.getPhpToken(phpToken.getEnd() + 1);
			}
			if (phpToken == null
					|| phpToken.getType() != PHPRegionTypes.PHPDOC_COMMENT_START) {
				return null;
			}
			int start = phpToken.getStart();
			ITextRegion lastToken = null;
			while (lastToken != phpToken && phpToken != null
					&& phpToken.getType() != PHPRegionTypes.PHPDOC_COMMENT_END) {
				phpToken = textRegion.getPhpToken(phpToken.getEnd() + 1);
			}

			if (phpToken != null
					&& phpToken.getType() == PHPRegionTypes.PHPDOC_COMMENT_END) {
				int end = phpToken.getEnd();
				return new Region(sdRegion.getStartOffset()
						+ textRegion.getStart() + start, end - start);
			}
			return null;

		} catch (BadLocationException e) {
			return null;
		}

		// ISourceRange range= ctx.getFirstType().getSourceRange();
		// if (range == null)
		// return null;
		// int start= 0;
		// int end= range.getOffset();
		/*
		 * code adapted from CommentFormattingStrategy: scan the header content
		 * up to the first type. Once a comment is found, accumulate any
		 * additional comments up to the stop condition. The stop condition is
		 * reaching a package declaration, import container, or the end of the
		 * input.
		 */
		// IScanner scanner= ctx.getScanner();
		// scanner.resetTo(start, end);
		//
		// int headerStart= -1;
		// int headerEnd= -1;
		// try {
		// boolean foundComment= false;
		// int terminal= scanner.getNextToken();
		// while (terminal != ITerminalSymbols.TokenNameEOF && !(terminal ==
		// ITerminalSymbols.TokenNameclass || terminal ==
		// ITerminalSymbols.TokenNameinterface || terminal ==
		// ITerminalSymbols.TokenNameenum || (foundComment && (terminal ==
		// ITerminalSymbols.TokenNameimport || terminal ==
		// ITerminalSymbols.TokenNamepackage)))) {
		//
		// if (terminal == ITerminalSymbols.TokenNameCOMMENT_JAVADOC || terminal
		// == ITerminalSymbols.TokenNameCOMMENT_BLOCK || terminal ==
		// ITerminalSymbols.TokenNameCOMMENT_LINE) {
		// if (!foundComment)
		// headerStart= scanner.getCurrentTokenStartPosition();
		// headerEnd= scanner.getCurrentTokenEndPosition();
		// foundComment= true;
		// }
		// terminal= scanner.getNextToken();
		// }
		//
		//
		// } catch (InvalidInputException ex) {
		// return null;
		// }
		// if (end != -1) {
		// return new Region(start, end - start);
		// }
	}

	/**
	 * Creates a comment folding position from an
	 * {@link #alignRegion(IRegion, StructuredTextFoldingProviderPHP.FoldingStructureComputationContext)
	 * aligned} region.
	 * 
	 * @param aligned
	 *            an aligned region
	 * @return a folding position corresponding to <code>aligned</code>
	 */
	protected final Position createCommentPosition(IRegion aligned) {
		return new CommentPosition(aligned.getOffset(), aligned.getLength());
	}

	/**
	 * Creates a folding position that remembers its member from an
	 * {@link #alignRegion(IRegion, StructuredTextFoldingProviderPHP.FoldingStructureComputationContext)
	 * aligned} region.
	 * 
	 * @param aligned
	 *            an aligned region
	 * @param member
	 *            the member to remember
	 * @return a folding position corresponding to <code>aligned</code>
	 */
	protected final Position createMemberPosition(IRegion aligned,
			IMember member) {
		return new PhpElementPosition(aligned.getOffset(), aligned.getLength(),
				member);
	}

	/**
	 * Aligns <code>region</code> to start and end at a line offset. The
	 * region's start is decreased to the next line offset, and the end offset
	 * increased to the next line start or the end of the document.
	 * <code>null</code> is returned if <code>region</code> is <code>null</code>
	 * itself or does not comprise at least one line delimiter, as a single line
	 * cannot be folded.
	 * 
	 * @param region
	 *            the region to align, may be <code>null</code>
	 * @param ctx
	 *            the folding context
	 * @return a region equal or greater than <code>region</code> that is
	 *         aligned with line offsets, <code>null</code> if the region is too
	 *         small to be foldable (e.g. covers only one line)
	 */
	protected final IRegion alignRegion(IRegion region,
			FoldingStructureComputationContext ctx) {
		if (region == null)
			return null;

		IDocument document = ctx.getDocument();

		try {

			int start = document.getLineOfOffset(region.getOffset());
			int end = document.getLineOfOffset(Math.min(region.getOffset()
					+ region.getLength() - 1, document.getLength()));
			if (start >= end)
				return null;

			int offset = document.getLineOffset(start);
			int endOffset;
			if (document.getNumberOfLines() > end + 1)
				endOffset = document.getLineOffset(end + 1);
			else
				endOffset = document.getLineOffset(end)
						+ document.getLineLength(end);

			return new Region(offset, endOffset - offset);

		} catch (BadLocationException x) {
			// concurrent modification
			return null;
		}
	}

	private ProjectionAnnotationModel getModel() {
		return this.viewer.getProjectionAnnotationModel();
	}

	private IDocument getDocument() {
		PHPStructuredEditor editor = fEditor;
		if (editor == null)
			return null;

		IDocumentProvider provider = editor.getDocumentProvider();
		if (provider == null)
			return null;

		return provider.getDocument(editor.getEditorInput());
	}

	/**
	 * Matches deleted annotations to changed or added ones. A deleted
	 * annotation/position tuple that has a matching addition / change is
	 * updated and marked as changed. The matching tuple is not added (for
	 * additions) or marked as deletion instead (for changes). The result is
	 * that more annotations are changed and fewer get deleted/re-added.
	 * 
	 * @param deletions
	 *            list with deleted annotations
	 * @param additions
	 *            map with position to annotation mappings
	 * @param changes
	 *            list with changed annotations
	 * @param ctx
	 *            the context
	 */
	private void match(List<PhpProjectionAnnotation> deletions,
			Map<PhpProjectionAnnotation, Position> additions,
			List<PhpProjectionAnnotation> changes,
			FoldingStructureComputationContext ctx) {
		if (deletions.isEmpty() || (additions.isEmpty() && changes.isEmpty()))
			return;

		List<PhpProjectionAnnotation> newDeletions = new ArrayList<PhpProjectionAnnotation>();
		List<PhpProjectionAnnotation> newChanges = new ArrayList<PhpProjectionAnnotation>();

		Iterator<PhpProjectionAnnotation> deletionIterator = deletions
				.iterator();
		while (deletionIterator.hasNext()) {
			PhpProjectionAnnotation deleted = deletionIterator.next();
			Position deletedPosition = ctx.getModel().getPosition(deleted);
			if (deletedPosition == null)
				continue;

			Tuple deletedTuple = new Tuple(deleted, deletedPosition);

			Tuple match = findMatch(deletedTuple, changes, null, ctx);
			boolean addToDeletions = true;
			if (match == null) {
				match = findMatch(deletedTuple, additions.keySet(), additions,
						ctx);
				addToDeletions = false;
			}

			if (match != null) {
				IModelElement element = match.annotation.getElement();
				deleted.setElement(element);
				deletedPosition.setLength(match.position.getLength());
				if (deletedPosition instanceof PhpElementPosition
						&& element instanceof IMember) {
					PhpElementPosition jep = (PhpElementPosition) deletedPosition;
					jep.setMember((IMember) element);
				}

				deletionIterator.remove();
				newChanges.add(deleted);

				if (addToDeletions)
					newDeletions.add(match.annotation);
			}
		}

		deletions.addAll(newDeletions);
		changes.addAll(newChanges);
	}

	/**
	 * Finds a match for <code>tuple</code> in a collection of annotations. The
	 * positions for the <code>JavaProjectionAnnotation</code> instances in
	 * <code>annotations</code> can be found in the passed
	 * <code>positionMap</code> or <code>fCachedModel</code> if
	 * <code>positionMap</code> is <code>null</code>.
	 * <p>
	 * A tuple is said to match another if their annotations have the same
	 * comment flag and their position offsets are equal.
	 * </p>
	 * <p>
	 * If a match is found, the annotation gets removed from
	 * <code>annotations</code>.
	 * </p>
	 * 
	 * @param tuple
	 *            the tuple for which we want to find a match
	 * @param annotations
	 *            collection of <code>JavaProjectionAnnotation</code>
	 * @param positionMap
	 *            a <code>Map&lt;Annotation, Position&gt;</code> or
	 *            <code>null</code>
	 * @param ctx
	 *            the context
	 * @return a matching tuple or <code>null</code> for no match
	 */
	private Tuple findMatch(Tuple tuple,
			Collection<PhpProjectionAnnotation> annotations,
			Map<PhpProjectionAnnotation, Position> positionMap,
			FoldingStructureComputationContext ctx) {
		Iterator<PhpProjectionAnnotation> it = annotations.iterator();
		while (it.hasNext()) {
			PhpProjectionAnnotation annotation = it.next();
			if (tuple.annotation.isComment() == annotation.isComment()) {
				Position position = positionMap == null ? ctx.getModel()
						.getPosition(annotation) : positionMap.get(annotation);
				if (position == null)
					continue;

				if (tuple.position.getOffset() == position.getOffset()) {
					it.remove();
					return new Tuple(annotation, position);
				}
			}
		}

		return null;
	}

	private Map<IModelElement, Object> computeCurrentStructure(
			FoldingStructureComputationContext ctx) {
		Map<IModelElement, Object> map = new HashMap<IModelElement, Object>();
		ProjectionAnnotationModel model = ctx.getModel();
		Iterator e = model.getAnnotationIterator();
		while (e.hasNext()) {
			Object annotation = e.next();
			if (annotation instanceof PhpProjectionAnnotation) {
				PhpProjectionAnnotation java = (PhpProjectionAnnotation) annotation;
				Position position = model.getPosition(java);
				Assert.isNotNull(position);
				List<Tuple> list = (List<Tuple>) map.get(java.getElement());
				if (list == null) {
					list = new ArrayList<Tuple>(2);
					map.put(java.getElement(), list);
				}
				list.add(new Tuple(java, position));
			}
		}

		Comparator comparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Tuple) o1).position.getOffset()
						- ((Tuple) o2).position.getOffset();
			}
		};
		for (Iterator<Object> it = map.values().iterator(); it.hasNext();) {
			List list = (List) it.next();
			Collections.sort(list, comparator);
		}
		return map;
	}

	/*
	 * @see IJavaFoldingStructureProviderExtension#collapseMembers()
	 * 
	 * @since 3.2
	 */
	public final void collapseMembers() {
		modifyFiltered(fMemberFilter, false);
	}

	/*
	 * @see IJavaFoldingStructureProviderExtension#collapseComments()
	 * 
	 * @since 3.2
	 */
	public final void collapseComments() {
		modifyFiltered(fCommentFilter, false);
	}

	/*
	 * @see
	 * org.eclipse.jdt.ui.text.folding.IJavaFoldingStructureProviderExtension
	 * #collapseElements(org.eclipse.jdt.core.IModelElement[])
	 */
	public final void collapseElements(IModelElement[] elements) {
		Set<IModelElement> set = new HashSet<IModelElement>(
				Arrays.asList(elements));
		modifyFiltered(new PhpElementSetFilter(set, false), false);
	}

	/*
	 * @see
	 * org.eclipse.jdt.ui.text.folding.IJavaFoldingStructureProviderExtension
	 * #expandElements(org.eclipse.jdt.core.IModelElement[])
	 */
	public final void expandElements(IModelElement[] elements) {
		Set<IModelElement> set = new HashSet<IModelElement>(
				Arrays.asList(elements));
		modifyFiltered(new PhpElementSetFilter(set, true), true);
	}

	/**
	 * Collapses or expands all annotations matched by the passed filter.
	 * 
	 * @param filter
	 *            the filter to use to select which annotations to collapse
	 * @param expand
	 *            <code>true</code> to expand the matched annotations,
	 *            <code>false</code> to collapse them
	 */
	private void modifyFiltered(Filter filter, boolean expand) {
		if (!isInstalled())
			return;

		ProjectionAnnotationModel model = getModel();
		if (model == null)
			return;

		List<PhpProjectionAnnotation> modified = new ArrayList<PhpProjectionAnnotation>();
		Iterator iter = model.getAnnotationIterator();
		while (iter.hasNext()) {
			Object annotation = iter.next();
			if (annotation instanceof PhpProjectionAnnotation) {
				PhpProjectionAnnotation java = (PhpProjectionAnnotation) annotation;

				if (expand == java.isCollapsed() && filter.match(java)) {
					if (expand)
						java.markExpanded();
					else
						java.markCollapsed();
					modified.add(java);
				}

			}
		}

		model.modifyAnnotations(null, null,
				modified.toArray(new Annotation[modified.size()]));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.projection.IProjectionListener#
	 * projectionDisabled()
	 */
	public void projectionDisabled() {
		handleProjectionDisabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.projection.IProjectionListener#
	 * projectionEnabled()
	 */
	public void projectionEnabled() {
		handleProjectionEnabled();
	}
}
