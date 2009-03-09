/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.text.XMLStructuredDocumentRegion;

/**
 * This class injects USE statement if needed for the given completion proposal
 * @author michael
 */
public class UseStatementInjector {

	private ScriptCompletionProposal proposal;

	public UseStatementInjector(ScriptCompletionProposal proposal) {
		this.proposal = proposal;
	}

	/**
	 * Inserts USE statement into beginning of the document, or after the last USE statement.
	 * @param document
	 * @param textViewer
	 * @param offset
	 * @return new offset
	 */
	public int inject(IDocument document, ITextViewer textViewer, int offset) {
		IModelElement modelElement = proposal.getModelElement();

		// add use statement if needed:
		IType namespace = PHPModelUtils.getCurrentNamespace(modelElement);
		if (namespace != null) {

			// find source module of the current editor:
			if (textViewer instanceof PHPStructuredTextViewer) {
				ITextEditor textEditor = ((PHPStructuredTextViewer) textViewer).getTextEditor();
				if (textEditor instanceof PHPStructuredEditor) {
					IModelElement editorElement = ((PHPStructuredEditor) textEditor).getModelElement();
					if (editorElement != null) {
						ISourceModule sourceModule = ((ModelElement) editorElement).getSourceModule();
						
						String namespaceName = namespace.getElementName();
						
						IType currentNamespace = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
						if (currentNamespace != null && currentNamespace.getElementName().equals(namespaceName)) {
							// no need to insert USE statement as we are already in the required namespace:
							return offset;
						}

						ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);

						// find existing use statement:
						UsePart usePart = ASTUtils.findUseStatementByNamespace(moduleDeclaration, namespaceName, offset);
						if (usePart == null) {

							// find the place where to insert the use statement:
							int insertOffset = -1;

							UseStatement[] useStatements = ASTUtils.getUseStatements(moduleDeclaration, offset);
							if (useStatements.length > 0) {
								// insert after last use statement:
								insertOffset = useStatements[useStatements.length - 1].sourceEnd();
							} else if (currentNamespace != null) {
								// insert after the namespace statement:
								try {
									TypeDeclaration namespaceNode = PHPModelUtils.getNodeByClass(moduleDeclaration, currentNamespace);
									Statement firstStatement = (Statement) namespaceNode.getStatements().get(0);
									insertOffset = firstStatement.sourceStart() - 1;
									while (insertOffset > 0 && Character.isWhitespace(document.getChar(insertOffset))) {
										insertOffset--;
									}
									insertOffset++;
								} catch (Exception e) {
									if (DLTKCore.DEBUG_COMPLETION) {
										e.printStackTrace();
									}
								}
							} else {
								insertOffset = findPhpBlockOffset((IStructuredDocument) document);
							}

							if (insertOffset > 0) {
								String useStatement = new StringBuilder("\nuse ").append(namespaceName).append(";").toString();
								try {
									document.replace(insertOffset, 0, useStatement);
								} catch (BadLocationException e) {
									if (DLTKCore.DEBUG_COMPLETION) {
										e.printStackTrace();
									}
								}

								// update replacement string: add namespace alias prefix
								int i = namespaceName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
								String alias = namespaceName;
								if (i != -1) {
									alias = namespaceName.substring(i + 1);
								}

								String namespacePrefix = alias + NamespaceReference.NAMESPACE_SEPARATOR;
								String replacementString = proposal.getReplacementString();
								
								// Remove fully qualified namespace prefix form the replacement string:
								if (replacementString.startsWith(namespaceName)) {
									replacementString = replacementString.substring(namespaceName.length());
									if (replacementString.length() > 0 && replacementString.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
										replacementString = replacementString.substring(1);
									}
								}
								
								// Add alias to the replacement string:
								if (!replacementString.startsWith(namespacePrefix)) {
									replacementString = namespacePrefix + replacementString;
								}
								proposal.setReplacementString(replacementString);

								int replacementOffset = proposal.getReplacementOffset() + useStatement.length();
								offset += useStatement.length();
								proposal.setReplacementOffset(replacementOffset);
							}
						}
					}
				}
			}
		}
		
		return offset;
	}

	/**
	 * This function returns the start of first php block, if found, else -1 returned
	 * @return injectOffset
	 */
	protected int findPhpBlockOffset(IStructuredDocument document) {
		int injectOffset = -1;
		IPhpScriptRegion scriptRegion = null;
		ITextRegion[] subRegions = getStructuredDocumentsRegions(document);
		if (subRegions != null) {
			for (ITextRegion currentRegion : subRegions) {
				if (currentRegion != null && currentRegion instanceof IPhpScriptRegion) {
					scriptRegion = (IPhpScriptRegion) currentRegion;
					if (scriptRegion.getType().equals(PHPRegionTypes.PHP_CONTENT)) {
						injectOffset = scriptRegion.getStart();
						return injectOffset;
					}
				}
			}
		}
		return injectOffset;
	}

	protected ITextRegion[] getStructuredDocumentsRegions(IStructuredDocument document) {
		IStructuredDocumentRegion[] subRegions = document.getStructuredDocumentRegions();
		for (IStructuredDocumentRegion currentRegion : subRegions) {
			if (currentRegion instanceof XMLStructuredDocumentRegion) {
				ITextRegion[] textRegions = currentRegion.getRegions().toArray();
				return textRegions;
			}
		}
		return null;
	}

}
