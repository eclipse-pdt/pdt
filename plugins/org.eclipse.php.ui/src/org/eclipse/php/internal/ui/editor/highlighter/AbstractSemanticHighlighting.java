/*******************************************************************************
 * Copyright (c) 2006, 2009 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Position;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.SemanticHighlightingStyle;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.ui.ISemanticHighlighting;
import org.eclipse.wst.sse.ui.ISemanticHighlightingExtension2;

@SuppressWarnings("restriction")
public abstract class AbstractSemanticHighlighting implements
		ISemanticHighlighting, ISemanticHighlightingExtension2,
		Comparable<AbstractSemanticHighlighting> {

	private ISourceModule sourceModule = null;

	private SemanticHighlightingStyle style = new SemanticHighlightingStyle(
			getPreferenceKey());

	private List<Position> list;

	private final String preferenceKey = this.getClass().getName();

	public String getPreferenceKey() {
		return preferenceKey;
	}

	public SemanticHighlightingStyle getStyle() {
		return style;
	}

	public ISourceModule getSourceModule() {
		if (sourceModule == null) {
			throw new IllegalStateException("Source module cannot be null"); //$NON-NLS-1$
		}
		return sourceModule;
	}

	protected AbstractSemanticHighlighting highlight(ISourceRange range) {
		if (range == null) {
			throw new IllegalArgumentException("Range cannot be null"); //$NON-NLS-1$
		}
		return highlight(range.getOffset(), range.getLength());
	}

	protected AbstractSemanticHighlighting highlight(ASTNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node cannot be null"); //$NON-NLS-1$
		}
		return highlight(node.getStart(), node.getLength());
	}

	protected AbstractSemanticHighlighting highlight(int start, int length) {
		if (list == null) {
			throw new IllegalStateException();
		}
		list.add(new Position(start, length));
		return this;
	}

	public Position[] consumes(Program program) {
		if (program != null) {
			list = new ArrayList<Position>();
			sourceModule = program.getSourceModule();
			AbstractSemanticApply apply = getSemanticApply();
			program.accept(apply);
			return list.toArray(new Position[list.size()]);
		}
		return new Position[0];
	}

	public Position[] consumes(IStructuredDocumentRegion region) {
		if (region.getStart() == 0) {
			Program program = getProgram(region);
			return consumes(program);
		}
		return new Position[0];
	}

	protected Program getProgram(final IStructuredDocumentRegion region) {// region.getParentDocument().get()
		sourceModule = null;
		// resolve current sourceModule
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PHPUiPlugin.getActivePage();
				if (page != null) {
					IEditorPart editor = page.getActiveEditor();
					if (editor instanceof PHPStructuredEditor) {
						PHPStructuredEditor phpStructuredEditor = (PHPStructuredEditor) editor;
						if (phpStructuredEditor.getTextViewer() != null
								&& phpStructuredEditor != null
								&& phpStructuredEditor.getDocument() == region
										.getParentDocument()) {
							if (phpStructuredEditor != null
									&& phpStructuredEditor.getTextViewer() != null) {
								sourceModule = (ISourceModule) phpStructuredEditor
										.getModelElement();
							}
						}
					}
				}
			}
		});

		// resolve AST
		Program program = null;
		if (sourceModule != null) {
			try {
				program = SharedASTProvider.getAST(sourceModule,
						SharedASTProvider.WAIT_YES, null);
			} catch (ModelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return program;
	}

	public String getBoldPreferenceKey() {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_BOLD_SUFFIX;
	}

	public String getColorPreferenceKey() {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_COLOR_SUFFIX;
	}

	public String getBackgroundColorPreferenceKey() {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_BGCOLOR_SUFFIX;
	}

	public String getEnabledPreferenceKey() {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_ENABLED_SUFFIX;
	}

	public String getItalicPreferenceKey() {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_ITALIC_SUFFIX;
	}

	public IPreferenceStore getPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	public String getStrikethroughPreferenceKey() {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_STRIKETHROUGH_SUFFIX;
	}

	public String getUnderlinePreferenceKey() {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ preferenceKey
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_UNDERLINE_SUFFIX;
	}

	public abstract AbstractSemanticApply getSemanticApply();

	public abstract void initDefaultPreferences();

	public int compareTo(AbstractSemanticHighlighting highlighter) {
		return getPriority() - highlighter.getPriority();
	}

	public int getPriority() {
		return 100;
	}
}
