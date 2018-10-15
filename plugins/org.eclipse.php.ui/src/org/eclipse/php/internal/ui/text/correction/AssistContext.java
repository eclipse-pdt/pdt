/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.io.IOException;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.TextInvocationContext;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.ui.IEditorPart;

public class AssistContext extends TextInvocationContext implements IInvocationContext {

	private final ISourceModule fProgram;
	private final IEditorPart fEditor;

	private Program fASTRoot;
	private final SharedASTProvider.WAIT_FLAG fWaitFlag;

	/*
	 * @since 3.5
	 */
	public AssistContext(ISourceModule cu, ISourceViewer sourceViewer, IEditorPart editor, int offset, int length,
			SharedASTProvider.WAIT_FLAG waitFlag) {
		super(sourceViewer, offset, length);
		fProgram = cu;
		fEditor = editor;
		fASTRoot = null;
		fWaitFlag = waitFlag;
	}

	/*
	 * @since 3.5
	 */
	public AssistContext(ISourceModule cu, ISourceViewer sourceViewer, int offset, int length,
			SharedASTProvider.WAIT_FLAG waitFlag) {
		this(cu, sourceViewer, null, offset, length, waitFlag);
	}

	/*
	 * @since 3.5
	 */
	public AssistContext(ISourceModule cu, ISourceViewer sourceViewer, IEditorPart editor, int offset, int length) {
		this(cu, sourceViewer, editor, offset, length, SharedASTProvider.WAIT_YES);
	}

	/*
	 * Constructor for CorrectionContext.
	 * 
	 * @since 3.4
	 */
	public AssistContext(ISourceModule cu, ISourceViewer sourceViewer, int offset, int length) {
		this(cu, sourceViewer, null, offset, length);
	}

	/*
	 * Constructor for CorrectionContext.
	 */
	public AssistContext(ISourceModule cu, int offset, int length) {
		this(cu, null, offset, length);
	}

	/**
	 * Returns the compilation unit.
	 * 
	 * @return an <code>ISourceModule</code>
	 */
	@Override
	public ISourceModule getCompilationUnit() {
		return fProgram;
	}

	/**
	 * Returns the editor or <code>null</code> if none.
	 * 
	 * @return an <code>IEditorPart</code> or <code>null</code> if none
	 * @since 3.5
	 */
	public IEditorPart getEditor() {
		return fEditor;
	}

	/**
	 * Returns the length.
	 * 
	 * @return int
	 */
	@Override
	public int getSelectionLength() {
		return Math.max(getLength(), 0);
	}

	/**
	 * Returns the offset.
	 * 
	 * @return int
	 */
	@Override
	public int getSelectionOffset() {
		return getOffset();
	}

	@Override
	public Program getASTRoot() {
		if (fASTRoot == null) {
			try {
				fASTRoot = SharedASTProvider.getAST(fProgram, fWaitFlag, null);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			} catch (IOException e) {
				PHPUiPlugin.log(e);
			}
			if (fASTRoot == null) {
				// see bug 63554
				ASTParser parser = ASTParser.newParser(fProgram);
				try {
					fASTRoot = parser.createAST(null);
				} catch (Exception e) {
					PHPUiPlugin.log(e);
				}
			}
		}
		return fASTRoot;
	}

	/**
	 * @param root
	 *            The ASTRoot to set.
	 */
	public void setASTRoot(Program root) {
		fASTRoot = root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.text.java.IInvocationContext#getCoveringNode()
	 */
	@Override
	public ASTNode getCoveringNode() {
		Program astRoot = getASTRoot();
		if (astRoot == null) {
			return null;
		}
		NodeFinder finder = new NodeFinder(getOffset(), getLength());
		astRoot.accept(finder);
		return finder.getCoveringNode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.text.java.IInvocationContext#getCoveredNode()
	 */
	@Override
	public ASTNode getCoveredNode() {
		Program astRoot = getASTRoot();
		if (astRoot == null) {
			return null;
		}
		NodeFinder finder = new NodeFinder(getOffset(), getLength());
		astRoot.accept(finder);
		return finder.getCoveredNode();
	}

}
