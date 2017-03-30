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
package org.eclipse.php.internal.ui.actions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.AbstractSourceModule;
import org.eclipse.dltk.internal.core.util.MethodOverrideTester;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.util.SuperTypeHierarchyCache;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.CodeGeneration;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;

public class AddDescriptionAction extends Action implements IObjectActionDelegate {

	private static final String PHP_COMMENT_BLOCK_END = " */"; //$NON-NLS-1$
	private static final String PHP_COMMENT_BLOCK_MID = " *"; //$NON-NLS-1$
	private static final String PHP_COMMENT_BLOCK_START = "/**"; //$NON-NLS-1$
	private final String PHP_BLOCK_OPEN_TAG = "<?php"; //$NON-NLS-1$
	private final String PHP_BLOCK_CLOSE_TAG = "?>"; //$NON-NLS-1$
	private IModelElement[] fModelElement;
	private int startPosition = 0;
	String docBlock;
	String lineDelim;

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	@Override
	public void run(IAction action) {
		final IModelElement[] modelElement = getModelElement();
		if (modelElement == null) {
			return;
		}

		// Sorting the PHP code elements array by "first-line" position.
		// this will enable "right" order of iteration

		Arrays.sort(modelElement, new modelElementComparatorImplementation());

		// iterating the functions that need to add 'PHP Doc' bottoms-up - to
		// eliminate mutual interference
		for (int i = modelElement.length - 1; i >= 0; i--) {

			IModelElement modelElem = modelElement[i];
			if (null == modelElem) {
				continue; // if we got to null pointer, skipping it
			}

			IEditorInput input;
			try {
				input = org.eclipse.php.internal.ui.util.EditorUtility.getEditorInput(modelElem);
			} catch (ModelException e) {
				Logger.logException(e);
				return;
			}

			IWorkbenchPage page = PHPUiPlugin.getActivePage();
			IEditorPart editorPart;
			try {
				editorPart = IDE.openEditor(page, input, PHPUiConstants.PHP_EDITOR_ID);
			} catch (PartInitException e) {
				Logger.logException(e);
				return;
			}

			if (editorPart instanceof ITextEditor) {
				ITextEditor textEditor = (ITextEditor) editorPart;
				IEditorInput editorInput = editorPart.getEditorInput();
				IDocument document = textEditor.getDocumentProvider().getDocument(editorInput);
				this.lineDelim = TextUtilities.getDefaultLineDelimiter(document);

				String docBlockText = handleElement(textEditor, modelElem, document);
				if (docBlockText == null) {
					return;
				}

				EditorUtility.revealInEditor(textEditor, startPosition, docBlock.length());
			}
		}
	}

	private String handleElement(ITextEditor textEditor, IModelElement modelElem, IDocument document) {
		if (textEditor instanceof PHPStructuredEditor) {
			PHPStructuredEditor editor = (PHPStructuredEditor) textEditor;
			if (editor.getTextViewer() != null && !editor.getTextViewer().isEditable()) {
				return null;
			}
		}
		if (modelElem instanceof ISourceModule) {
			handleFileDocBlock((ISourceModule) modelElem, (IStructuredDocument) document);
			return null;
		}

		try {
			startPosition = getCodeDataOffset(modelElem);
		} catch (ModelException e) {
			Logger.logException(e);
			return null;
		}

		// Calculating indentation need to be added
		String indentString = null;
		try {
			indentString = getIndentString(document, modelElem);
		} catch (BadLocationException e) {
			Logger.logException(e);
			return null;
		}

		if (!textEditor.isEditable()) {
			return null;
		}
		int type = modelElem != null ? modelElem.getElementType() : -1;
		if (type != IModelElement.METHOD && type != IModelElement.TYPE && type != IModelElement.FIELD) {
			assert false;
			return null;
		}
		String comment = null;
		try {
			switch (type) {
			case IModelElement.TYPE:
				if (PHPModelUtils.getDocBlock((IType) modelElem) != null) {
					return null;
				}
				comment = createTypeComment((IType) modelElem, lineDelim);
				break;
			case IModelElement.FIELD:
				if (!isParameter((IField) modelElem)) {
					if (PHPModelUtils.getDocBlock((IField) modelElem) != null) {
						return null;
					}
					comment = createFieldComment((IField) modelElem, lineDelim);
					break;
				} else if (modelElem != null) {
					// If the element is a parameter in the function
					// declaration,
					// get the parent element and go to method case.
					modelElem = modelElem.getParent();
					// reset the position to the beginning of the method
					startPosition = getCodeDataOffset(modelElem);

					try {
						// reset the indent level to the method.
						indentString = getIndentString(document, modelElem);
					} catch (BadLocationException e) {
						Logger.logException(e);
						return null;
					}
				}
			case IModelElement.METHOD:
				if (PHPModelUtils.getDocBlock((IMethod) modelElem) != null) {
					return null;
				}
				comment = createMethodComment((IMethod) modelElem, lineDelim);
				break;
			default:
				comment = createDefaultComment(lineDelim);
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}

		if (comment == null) {
			comment = createDefaultComment(lineDelim);
		}

		docBlock = indentPattern(comment, indentString, lineDelim);

		String docBlockText = insertDocBlock((IStructuredDocument) document, startPosition, docBlock);

		return docBlockText;
	}

	private boolean isParameter(IField field) {
		ISourceModule sourceModule = field.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		ASTNode fieldDeclaration = null;
		try {
			fieldDeclaration = PHPModelUtils.getNodeByField(moduleDeclaration, field);
		} catch (ModelException e) {

		}
		return fieldDeclaration instanceof Argument;
	}

	private String indentPattern(String originalPattern, String indentation, String lineDelim) {
		String delimPlusIndent = lineDelim + indentation;
		String indentedPattern = originalPattern.replaceAll(Pattern.quote(lineDelim), delimPlusIndent)
				+ delimPlusIndent;

		return indentedPattern;
	}

	private String createDefaultComment(String lineDelimiter) {
		return PHP_COMMENT_BLOCK_START + lineDelimiter + PHP_COMMENT_BLOCK_MID + lineDelimiter + PHP_COMMENT_BLOCK_END;
	}

	private String createTypeComment(IType type, String lineDelimiter) throws CoreException {

		return CodeGeneration.getTypeComment(type.getScriptProject(), type.getTypeQualifiedName(),
				/* typeParameterNames */null, lineDelimiter);
	}

	private String createMethodComment(IMethod meth, String lineDelimiter) throws CoreException {
		IType declaringType = meth.getDeclaringType();
		IMethod overridden = null;

		if (!meth.isConstructor() && null != declaringType) {
			ITypeHierarchy hierarchy = SuperTypeHierarchyCache.getTypeHierarchy(declaringType);
			MethodOverrideTester tester = new MethodOverrideTester(declaringType, hierarchy);
			overridden = tester.findOverriddenMethod(meth, true);
		}
		return CodeGeneration.getMethodComment(meth, overridden, lineDelimiter);
	}

	private String createFieldComment(IField field, String lineDelimiter) throws ModelException, CoreException {
		return CodeGeneration.getFieldComment(field.getScriptProject(), field, lineDelimiter);
	}

	/**
	 * Calculates and returns the desired docBlock surrounded by '<?php' and
	 * '?>' tags (no indentation)
	 * 
	 * @param document
	 *            - The IStructuredDocument that we are working on
	 * 
	 * @return String to be used as leading indentation
	 * @throws CoreException
	 */
	public String createPHPScopeFileDocBlock(IScriptProject scriptProject) {
		String fileComment = null;
		try {
			fileComment = CodeGeneration.getFileComment(scriptProject, lineDelim);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		if (fileComment == null) {
			fileComment = createDefaultComment(lineDelim);
		}
		return PHP_BLOCK_OPEN_TAG + lineDelim + fileComment + PHP_BLOCK_CLOSE_TAG + lineDelim;
	}

	/**
	 * Calculates the leading string to be used as indentation prefix
	 * 
	 * @param document
	 *            The IStructuredDocument that we are working on
	 * @param modelElem
	 *            A PHPFileData that need to be documented
	 * 
	 * @return String to be used as leading indentation
	 */
	private String getIndentString(IDocument document, IModelElement modelElem) throws BadLocationException {
		int elementOffset = 0;
		String leadingString = null;
		try {
			elementOffset = getCodeDataOffset(modelElem);
			int lineStartOffset = document.getLineInformationOfOffset(elementOffset).getOffset();
			leadingString = document.get(lineStartOffset, elementOffset - lineStartOffset);
		} catch (ModelException e) {
			Logger.logException(e);
			return null;
		}
		// replacing all non-spaces/tabs to single-space, in order to get
		// "char-clean" prefix
		leadingString = leadingString.replaceAll("[^\\p{javaWhitespace}]", " "); //$NON-NLS-1$ //$NON-NLS-2$

		return leadingString;
	}

	private int getCodeDataOffset(IModelElement modelElem) throws ModelException {
		if (modelElem instanceof ISourceModule) {
			ISourceReference primaryModelElem = (ISourceReference) (((ISourceModule) modelElem).getPrimaryElement());// .getPHPBlocks();
			return primaryModelElem != null
					? primaryModelElem.getSourceRange().getOffset() + primaryModelElem.getSourceRange().getLength()
					/*
					 * getPHPStartTag ( ). getEndPosition ()
					 */ : -1;
		}
		if (modelElem instanceof ISourceReference) {
			int dataOffset = ((ISourceReference) modelElem).getSourceRange().getOffset();
			return dataOffset;
		}
		assert false;
		return -1;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection == null || !(selection instanceof IStructuredSelection)) {
			return;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		setModelElement(new IModelElement[structuredSelection.size()]);
		Iterator<?> i = structuredSelection.iterator();
		int idx = 0;
		final IModelElement[] modelElement = getModelElement();
		while (i.hasNext()) {
			modelElement[idx++] = (IModelElement) i.next();
		}
	}

	/**
	 * Handle a situation where a file DocBlock is requested and there is an
	 * undocumented class, function or define at the beginning of the document.
	 * In this case we auto-document the undocumented element to comply the
	 * DocBlock rules.
	 * 
	 * @param data
	 *            A PHPFileData that need to be documented
	 * @param document
	 *            The IStructuredDocument that we are working on
	 */
	private void handleFileDocBlock(ISourceModule data, IStructuredDocument document) {

		// Find the first PHP script region:
		IStructuredDocumentRegion sdRegion = document.getFirstStructuredDocumentRegion();
		IPhpScriptRegion phpScriptRegion = null;
		ITextRegion textRegion = null;
		String docBlock = null;
		while (sdRegion != null && docBlock == null) {
			ITextRegion region = sdRegion.getFirstRegion();
			if (region.getType() == PHPRegionContext.PHP_OPEN) {
				// File's content starts with '<?PHP' tag
				region = sdRegion.getRegionAtCharacterOffset(region.getEnd() + sdRegion.getStartOffset());
				if (region != null && region.getType() == PHPRegionContext.PHP_CONTENT) {
					phpScriptRegion = (IPhpScriptRegion) region;
					try {
						docBlock = CodeGeneration.getFileComment(data, null);
					} catch (CoreException e) {
						Logger.logException("Generating default phpdoc comment", e); //$NON-NLS-1$
					}
					if (docBlock == null) {
						// XXX : should we insert newlines?
						docBlock = createDefaultComment("");
					}
					break;
				}
			} else if (region.getType() == DOMRegionContext.XML_DECLARATION_OPEN) {
				// File's content starts with HTML code
				region = sdRegion.getRegionAtCharacterOffset(region.getEnd() + sdRegion.getStartOffset());
				if (region != null) {
					phpScriptRegion = null;
					textRegion = (ITextRegion) region;
					docBlock = createPHPScopeFileDocBlock(data.getScriptProject());
					break;
				}
			}

			sdRegion = sdRegion.getNext();
		}

		if (phpScriptRegion != null || textRegion != null) {
			try {
				int offset = 0;
				if (phpScriptRegion == null && textRegion != null) {
					// File's content starts with HTML code
					offset = 0;
				} else if (phpScriptRegion != null && sdRegion != null) {
					// File's content starts with '<?php' tag
					textRegion = phpScriptRegion.getPhpToken(0);
					String lineDelimiter = document.getLineDelimiter(document.getLineOfOffset(textRegion.getStart()));
					if (lineDelimiter == null) {
						// XXX : should we add a newline before inserting
						// docBlock?
						lineDelimiter = ""; //$NON-NLS-1$
					}
					int lineDelimiterLength = lineDelimiter.length();
					offset = textRegion.getStart() + sdRegion.getStartOffset() + phpScriptRegion.getStart()
							+ lineDelimiterLength;
				} else {
					assert false;// we shouldn't get here ...
				}

				if (data instanceof AbstractSourceModule)
					insertDocBlock(document, offset, docBlock);

			} catch (BadLocationException e) {
			}
		}
	}

	private String insertDocBlock(IDocument document, int offset, String docBlock) {
		try {
			document.replace(offset, 0, docBlock);
		} catch (BadLocationException e) {
			Logger.logException(e);
			docBlock = null;
		}
		return docBlock;
	}

	private final class modelElementComparatorImplementation implements Comparator<IModelElement> {
		@Override
		public int compare(IModelElement object1, IModelElement object2) {
			/*
			 * handling null-pointers on both levels (object=null or
			 * object1.getUserData()=null) 'null' objects will be considered as
			 * 'bigger' and will be pushed to the end of the array
			 */
			if (object1 instanceof ISourceReference && object2 instanceof ISourceReference) {
				ISourceReference sourceReference1 = (ISourceReference) object1;
				ISourceReference sourceReference2 = (ISourceReference) object2;

				try {
					if (sourceReference1.getSourceRange() == null) {
						if (sourceReference2.getSourceRange() == null) {
							return 0; // both null => equal
						} else {
							return 1; // only object1 is null => object1 is
							// bigger
						}
					}

					if (sourceReference2.getSourceRange() == null) {
						return -1; // only object2 is null => object2 is bigger
					}

					return sourceReference1.getSourceRange().getOffset()
							- sourceReference2.getSourceRange().getOffset();

				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
			assert false; // we never supposed to get here
			return 0;
		}
	}

	/**
	 * @param fmodelElement
	 *            the fmodelElement to set
	 */
	public void setModelElement(IModelElement[] fmodelElement) {
		this.fModelElement = fmodelElement;
	}

	/**
	 * @return the fmodelElement
	 */
	public IModelElement[] getModelElement() {
		return fModelElement;
	}

}
