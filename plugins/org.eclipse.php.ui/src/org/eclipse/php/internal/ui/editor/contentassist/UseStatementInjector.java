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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.html.core.internal.Logger;

/**
 * This class injects USE statement if needed for the given completion proposal
 * @author michael
 */
public class UseStatementInjector {

	private ScriptCompletionProposal proposal;

	public UseStatementInjector(ScriptCompletionProposal proposal) {
		this.proposal = proposal;
	}

	private Collection<Identifier> createIdentifiers(AST ast, String namespaceName) {
		String[] split = namespaceName.split("\\\\");
		List<Identifier> identifiers = new ArrayList<Identifier>(split.length);
		for (String s : split) {
			identifiers.add(ast.newIdentifier(s));
		}
		return identifiers;
	}

	private NamespaceDeclaration getCurrentNamespace(Program program, int offset) {
		ASTNode node = program.getElementAt(offset);
		do {
			switch (node.getType()) {
				case ASTNode.NAMESPACE:
					return (NamespaceDeclaration) node;
			}
			node = node.getParent();
		} while (node != null);
		
		return null;
	}

	private String getNamespaceName(NamespaceDeclaration namespaceDecl) {
		StringBuilder nameBuf = new StringBuilder();
		NamespaceName name = namespaceDecl.getName();
		for (Identifier identifier : name.segments()) {
			if (nameBuf.length() > 0) {
				nameBuf.append('\\');
			}
			nameBuf.append(identifier.getName());
		}
		return nameBuf.toString();
	}

	private boolean needsAliasPrepend(IModelElement modelElement) throws ModelException {
		if (modelElement instanceof IMethod) {
			return ((IMethod) modelElement).getDeclaringType() == null;
		}
		if (modelElement instanceof IField) {
			IField field = (IField) modelElement;
			if (!PHPFlags.isConstant(field.getFlags())) {
				return false;
			}
		}
		return true;
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

						try {
							String namespaceName = namespace.getElementName();
							ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
							TextEdit edits = null;

							// find existing use statement:
							UsePart usePart = ASTUtils.findUseStatementByNamespace(moduleDeclaration, namespaceName, offset);
							if (usePart == null) {
								ASTParser parser = ASTParser.newParser(sourceModule);
								parser.setSource(document.get().toCharArray());

								Program program = parser.createAST(null);
								program.recordModifications();

								AST ast = program.getAST();

								NamespaceName newNamespaceName = ast.newNamespaceName(createIdentifiers(ast, namespaceName), false, false);
								UseStatementPart newUseStatementPart = ast.newUseStatementPart(newNamespaceName, null);
								org.eclipse.php.internal.core.ast.nodes.UseStatement newUseStatement = ast.newUseStatement(Arrays.asList(new UseStatementPart[] { newUseStatementPart }));

								NamespaceDeclaration currentNamespace = getCurrentNamespace(program, offset);
								if (currentNamespace != null) {
									if (namespaceName.equals(getNamespaceName(currentNamespace))) {
										return offset; // don't insert USE statement for current namespace
									}
									// insert in the beginning of the current namespace:
									currentNamespace.getBody().statements().add(0, newUseStatement);
								} else {
									// insert in the beginning of the document:
									program.statements().add(0, newUseStatement);
								}
								edits = program.rewrite(document, null);
								edits.apply(document);
							}

							if (needsAliasPrepend(modelElement)) {
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
							}

							if (edits != null) {
								int replacementOffset = proposal.getReplacementOffset() + edits.getLength();
								offset += edits.getLength();
								proposal.setReplacementOffset(replacementOffset);
							}

						} catch (Exception e) {
							Logger.logException(e);
						}
					}
				}
			}
		}

		return offset;
	}
}
