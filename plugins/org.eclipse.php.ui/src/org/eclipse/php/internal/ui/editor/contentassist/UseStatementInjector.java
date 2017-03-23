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
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.*;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasField;
import org.eclipse.php.internal.core.codeassist.AliasMethod;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * This class injects USE statement if needed for the given completion proposal
 * 
 * @author michael
 */
public class UseStatementInjector {

	private ScriptCompletionProposal proposal;

	public UseStatementInjector(ScriptCompletionProposal proposal) {
		this.proposal = proposal;
	}

	/**
	 * Inserts USE statement into beginning of the document, or after the last
	 * USE statement.
	 * 
	 * @param document
	 * @param textViewer
	 * @param offset
	 * @return new offset
	 */
	public int inject(IDocument document, ITextViewer textViewer, int offset) {
		IModelElement modelElement = proposal.getModelElement();

		if (modelElement instanceof FakeConstructor) {
			FakeConstructor fc = (FakeConstructor) modelElement;
			if (fc.getParent() instanceof AliasType) {
				return offset;
			}

		} else if (modelElement instanceof AliasType || modelElement instanceof AliasMethod
				|| modelElement instanceof AliasField) {
			return offset;
		}
		try {
			if (modelElement.getElementType() == IModelElement.METHOD && (((IMethod) modelElement).isConstructor())) {
				modelElement = modelElement.getAncestor(IModelElement.TYPE);
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
		if (modelElement == null)
			return offset;
		if (proposal instanceof IPHPCompletionProposalExtension) {
			IPHPCompletionProposalExtension phpCompletionProposal = (IPHPCompletionProposalExtension) proposal;
			if (ProposalExtraInfo.isNotInsertUse(phpCompletionProposal.getExtraInfo())) {
				return offset;
			}
		}

		try {
			// qualified namespace should return offset directly
			if (proposal.getReplacementOffset() > 0 && document
					.getChar(proposal.getReplacementOffset() - 1) == NamespaceReference.NAMESPACE_SEPARATOR) {
				return offset;
			}
			if (modelElement.getElementType() == IModelElement.TYPE
					&& PHPFlags.isNamespace(((IType) modelElement).getFlags())) {
				if (offset > 0) {
					String prefix = document.get(proposal.getReplacementOffset(), proposal.getReplacementLength());
					String fullName = ((IType) modelElement).getElementName();
					if (fullName.startsWith(prefix) && prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {
						// int
						ITextEditor textEditor = ((PHPStructuredTextViewer) textViewer).getTextEditor();
						if (textEditor instanceof PHPStructuredEditor) {
							IModelElement editorElement = ((PHPStructuredEditor) textEditor).getModelElement();
							if (editorElement != null) {
								ISourceModule sourceModule = ((ModelElement) editorElement).getSourceModule();

								String namespaceName = fullName;
								int nsSeparatorIndex = fullName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
								if (nsSeparatorIndex > 0) {
									namespaceName = fullName.substring(0, nsSeparatorIndex);
								}
								String usePartName = namespaceName;
								boolean useAlias = !Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
										PHPCoreConstants.CODEASSIST_INSERT_FULL_QUALIFIED_NAME_FOR_NAMESPACE, true,
										null);

								ModuleDeclaration moduleDeclaration = SourceParserUtil
										.getModuleDeclaration(sourceModule);

								ASTParser parser = ASTParser.newParser(sourceModule);
								parser.setSource(document.get().toCharArray());
								Program program = parser.createAST(null);

								// Don't insert USE statement for current
								// namespace.
								// "program != null" is a workaround for bug
								// 465687.
								if (program != null && isSameNamespace(namespaceName, program, sourceModule, offset)) {
									return offset;
								}

								// find existing use statement:
								UsePart usePart = ASTUtils.findUseStatementByNamespace(moduleDeclaration, usePartName,
										offset);

								List<String> importedTypeName = getImportedTypeName(moduleDeclaration, offset);
								String typeName = namespaceName;

								// if the class/namespace has not been imported
								// add use statement
								if (program != null && !importedTypeName.contains(typeName)) {

									program.recordModifications();
									AST ast = program.getAST();
									NamespaceName newNamespaceName = ast
											.newNamespaceName(createIdentifiers(ast, usePartName), false, false);
									UseStatementPart newUseStatementPart = ast.newUseStatementPart(newNamespaceName,
											null);
									UseStatement newUseStatement = ast.newUseStatement(
											Arrays.asList(new UseStatementPart[] { newUseStatementPart }),
											UseStatement.T_NONE);

									NamespaceDeclaration currentNamespace = getCurrentNamespace(program, sourceModule,
											offset - 1);
									List<Statement> statements = program.statements();
									if (currentNamespace != null) {
										// insert in the beginning of the
										// current namespace:
										statements = currentNamespace.getBody().statements();
									}

									int index = getLastUsestatementIndex(statements, offset);
									addUseStatement(index, newUseStatement, statements, document);
									ast.setInsertUseStatement(true);

									TextEdit edits = program.rewrite(document, createOptions(modelElement));
									// workaround for bug 400976:
									// when current offset is in a php section
									// that only contains AstErrors, the use
									// statements will be added at the beginning
									// of the previous php section.
									// If luckily there is a previous php
									// section in the document, we're good,
									// otherwise we have to create one now at
									// the beginning of the document.
									// Text edits that will be correctly
									// inserted in an existing php section have
									// all their offset > 0, so looking for text
									// edits having all their offset = 0 (and
									// their length = 0) should be enough to
									// detect use statements that will be
									// wrongly inserted outside any existing php
									// section...
									if (new Region(0, 0).equals(edits.getRegion())) {
										String lineDelim = TextUtilities.getDefaultLineDelimiter(document);
										MultiTextEdit newEdits = new MultiTextEdit();
										newEdits.addChild(new InsertEdit(0, "<?php"));
										newEdits.addChild(new InsertEdit(0, lineDelim));
										for (TextEdit edit : edits.getChildren()) {
											// we have to copy the text edit to
											// reset its parent
											newEdits.addChild(edit.copy());
										}
										newEdits.addChild(new InsertEdit(0, "?>"));
										newEdits.addChild(new InsertEdit(0, lineDelim));
										edits = newEdits;
									} else if (index == 0 && edits.getChildrenSize() == 2) {
										// workaround for bug 435134
										addBlankLineEdit(edits, document);
									}

									edits.apply(document);
									ast.setInsertUseStatement(false);

									int replacementOffset = proposal.getReplacementOffset() + edits.getLength();
									offset += edits.getLength();
									proposal.setReplacementOffset(replacementOffset);
								} else if (!useAlias && (usePart == null
										|| !usePartName.equals(usePart.getNamespace().getFullyQualifiedName()))) {
									// if the type name already exists, use
									// fully
									// qualified name to replace
									proposal.setReplacementString(NamespaceReference.NAMESPACE_SEPARATOR + fullName);
								}
							}
						}

					}
					return offset;
				}
				return offset;
			} else
			// class members should return offset directly
			if (modelElement.getElementType() != IModelElement.TYPE && !(modelElement instanceof FakeConstructor)) {
				IModelElement type = modelElement.getAncestor(IModelElement.TYPE);
				if (type != null && !PHPFlags.isNamespace(((IType) type).getFlags())) {
					return offset;
				}
			}
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}

		return addUseStatement(modelElement, document, textViewer, offset);
	}

	private int addUseStatement(IModelElement modelElement, IDocument document, ITextViewer textViewer, int offset) {
		// add use statement if needed:
		IType namespace = PHPModelUtils.getCurrentNamespace(modelElement);
		if (namespace == null) {
			return offset;
		}

		// find source module of the current editor:
		if (!(textViewer instanceof PHPStructuredTextViewer)) {
			return offset;
		}
		ITextEditor textEditor = ((PHPStructuredTextViewer) textViewer).getTextEditor();
		if (!(textEditor instanceof PHPStructuredEditor)) {
			return offset;

		}
		IModelElement editorElement = ((PHPStructuredEditor) textEditor).getModelElement();
		if (editorElement == null) {
			return offset;
		}
		ISourceModule sourceModule = ((ModelElement) editorElement).getSourceModule();

		try {
			String namespaceName = namespace.getElementName();
			String usePartName = namespaceName;
			boolean useAlias = !Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
					PHPCoreConstants.CODEASSIST_INSERT_FULL_QUALIFIED_NAME_FOR_NAMESPACE, true, null);
			if (!useAlias) {
				usePartName = usePartName + NamespaceReference.NAMESPACE_SEPARATOR + modelElement.getElementName();
			}
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);

			ASTParser parser = ASTParser.newParser(sourceModule);
			parser.setSource(document.get().toCharArray());
			Program program = parser.createAST(null);

			// Don't insert USE statement for current namespace.
			// "program != null" is a workaround for bug 465687.
			if (program != null && isSameNamespace(namespaceName, program, sourceModule, offset)) {
				return offset;
			}

			// find existing use statement:
			UsePart usePart = ASTUtils.findUseStatementByNamespace(moduleDeclaration, usePartName, offset);

			List<String> importedTypeName = getImportedTypeName(moduleDeclaration, offset);
			String typeName = ""; //$NON-NLS-1$
			if (!useAlias) {
				typeName = modelElement.getElementName().toLowerCase();
			} else {
				if (usePart != null && usePart.getAlias() != null && usePart.getAlias().getName() != null) {
					typeName = usePart.getAlias().getName();
				} else {
					String elementName = PHPModelUtils.extractElementName(namespaceName);
					if (elementName != null) {
						typeName = elementName.toLowerCase();
					}
				}
			}

			PHPVersion phpVersion = ProjectOptions.getPHPVersion(modelElement);
			// if the class/namespace has not been imported
			// add use statement
			if (program != null && !importedTypeName.contains(typeName)
					&& canInsertUseStatement(getUseStatementType(modelElement), phpVersion)) {
				program.recordModifications();
				AST ast = program.getAST();
				NamespaceName newNamespaceName = ast.newNamespaceName(createIdentifiers(ast, usePartName), false,
						false);
				UseStatementPart newUseStatementPart = ast.newUseStatementPart(newNamespaceName, null);
				int type = getUseStatementType(modelElement);
				UseStatement newUseStatement = ast
						.newUseStatement(Arrays.asList(new UseStatementPart[] { newUseStatementPart }), type);

				NamespaceDeclaration currentNamespace = getCurrentNamespace(program, sourceModule, offset - 1);
				List<Statement> statements = program.statements();
				if (currentNamespace != null) {
					// insert in the beginning of the current namespace:
					statements = currentNamespace.getBody().statements();
				}

				int index = getLastUsestatementIndex(statements, offset);
				addUseStatement(index, newUseStatement, statements, document);
				ast.setInsertUseStatement(true);

				TextEdit edits = program.rewrite(document, createOptions(modelElement));
				// workaround for bug 400976:
				// when current offset is in a php section that only contains
				// AstErrors, the use statements will be added at the beginning
				// of the previous php section.
				// If luckily there is a previous php section in the document,
				// we're good, otherwise we have to create one now at the
				// beginning of the document.
				// Text edits that will be correctly inserted in an existing
				// php section have all their offset > 0, so looking for text
				// edits having all their offset = 0 (and their length = 0)
				// should be enough to detect use statements that will be
				// wrongly inserted outside any existing php section...

				if (new Region(0, 0).equals(edits.getRegion())) {
					String lineDelim = TextUtilities.getDefaultLineDelimiter(document);
					// String lineDelim =
					// TextUtilities.getDefaultLineDelimiter(document);
					MultiTextEdit newEdits = new MultiTextEdit();
					newEdits.addChild(new InsertEdit(0, "<?php"));
					newEdits.addChild(new InsertEdit(0, lineDelim));
					for (TextEdit edit : edits.getChildren()) {
						// we have to copy the text edit to reset its parent
						newEdits.addChild(edit.copy());
					}
					newEdits.addChild(new InsertEdit(0, "?>"));
					newEdits.addChild(new InsertEdit(0, lineDelim));
					edits = newEdits;
				} else if (index == 0 && edits.getChildrenSize() == 2) {
					// workaround for bug 435134
					addBlankLineEdit(edits, document);
				}
				edits.apply(document);
				ast.setInsertUseStatement(false);

				if (useAlias && needsAliasPrepend(modelElement)) {

					// update replacement string: add namespace
					// alias prefix
					String namespacePrefix = typeName + NamespaceReference.NAMESPACE_SEPARATOR;
					String replacementString = proposal.getReplacementString();

					String existingNamespacePrefix = readNamespacePrefix(sourceModule, document, offset, phpVersion);

					// Add alias to the replacement string:
					if (existingNamespacePrefix == null
							|| !usePartName.toLowerCase().equals(existingNamespacePrefix.toLowerCase())) {
						replacementString = namespacePrefix + replacementString;
					}
					proposal.setReplacementString(replacementString);
				}

				int replacementOffset = proposal.getReplacementOffset() + edits.getLength();
				offset += edits.getLength();
				proposal.setReplacementOffset(replacementOffset);
			} else if (!useAlias
					&& (usePart == null || !usePartName.equals(usePart.getNamespace().getFullyQualifiedName()))) {
				String namespacePrefix = NamespaceReference.NAMESPACE_SEPARATOR + namespaceName
						+ NamespaceReference.NAMESPACE_SEPARATOR;
				String replacementString = proposal.getReplacementString();

				String existingNamespacePrefix = readNamespacePrefix(sourceModule, document, offset, phpVersion);

				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=459306
				// if the type name already exists, use fully
				// qualified name to replace:
				if (existingNamespacePrefix == null
						|| !namespaceName.toLowerCase().equals(existingNamespacePrefix.toLowerCase())) {
					replacementString = namespacePrefix + replacementString;
				}
				proposal.setReplacementString(replacementString);
			}

		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
		return offset;
	}

	private Collection<Identifier> createIdentifiers(AST ast, String namespaceName) {
		String[] split = namespaceName.split("\\\\"); //$NON-NLS-1$
		List<Identifier> identifiers = new ArrayList<Identifier>(split.length);
		for (String s : split) {
			identifiers.add(ast.newIdentifier(s));
		}
		return identifiers;
	}

	private NamespaceDeclaration getCurrentNamespace(Program program, ISourceModule sourceModule, int offset) {
		SourceType ns = (SourceType) PHPModelUtils.getPossibleCurrentNamespace(sourceModule, offset);
		if (ns == null) {
			if (program.statements() != null && !program.statements().isEmpty()
					&& (program.statements().get(0) instanceof NamespaceDeclaration)) {
				NamespaceDeclaration result = (NamespaceDeclaration) program.statements().get(0);
				for (Statement statement : program.statements()) {
					if (statement.getStart() >= offset) {
						return result;
					}
					if (statement instanceof NamespaceDeclaration) {
						result = (NamespaceDeclaration) statement;
					}
				}
				return result;
			} else {
				return null;
			}
		}
		ASTNode node = null;
		try {
			node = program.getElementAt(ns.getSourceRange().getOffset());
		} catch (ModelException e) {
		}
		if (node == null) {
			return null;
		}
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
		if (name == null) {
			return "\\"; //$NON-NLS-1$
		}
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
			if (modelElement instanceof FakeConstructor) {
				return true;
			}
			IType declaringType = ((IMethod) modelElement).getDeclaringType();
			return declaringType == null || PHPFlags.isNamespace(declaringType.getFlags());
		}
		if (modelElement instanceof IField) {
			IField field = (IField) modelElement;
			if (!PHPFlags.isConstant(field.getFlags())) {
				return false;
			}
			IType declaringType = ((IField) modelElement).getDeclaringType();
			return declaringType == null || PHPFlags.isNamespace(declaringType.getFlags());
		}
		return true;
	}

	private String readNamespacePrefix(ISourceModule sourceModule, IDocument document, int offset,
			PHPVersion phpVersion) {

		if (offset > 0) {
			--offset;
		}

		IStructuredDocumentRegion sRegion = ((IStructuredDocument) document).getRegionAtCharacterOffset(offset);
		if (sRegion != null) {
			ITextRegion tRegion = sRegion.getRegionAtCharacterOffset(offset);

			ITextRegionCollection container = sRegion;
			if (tRegion instanceof ITextRegionContainer) {
				container = (ITextRegionContainer) tRegion;
				tRegion = container.getRegionAtCharacterOffset(offset);
			}

			if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
				IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;
				try {
					tRegion = phpScriptRegion
							.getPhpToken(offset - container.getStartOffset() - phpScriptRegion.getStart());
				} catch (BadLocationException e) {
					return null;
				}

				// Determine element name:
				int elementStart = container.getStartOffset() + phpScriptRegion.getStart() + tRegion.getStart();
				TextSequence statement = PHPTextSequenceUtilities.getStatement(elementStart + tRegion.getLength(),
						sRegion, true);
				if (statement.length() != 0) {
					int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statement, statement.length());
					int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(phpVersion, statement,
							endPosition, true);
					String elementName = startPosition < 0 ? "" //$NON-NLS-1$
							: statement.subSequence(startPosition, endPosition).toString();
					if (elementName.length() > 0) {
						return PHPModelUtils.extractNamespaceName(elementName, sourceModule, offset);
					}
				}
			}
		}
		return null;
	}

	private int getLastUsestatementIndex(List<Statement> statements, int offset) {
		int result = 0;
		for (int i = 0; i < statements.size(); i++) {
			Statement statement = statements.get(i);
			if (statement.getEnd() <= offset && statement instanceof UseStatement) {
				result = i + 1;
			}
		}
		return result;
	}

	/**
	 * Get all the class / namespace names which have been imported by use
	 * statement
	 * 
	 * @param moduleDeclaration
	 * @param offset
	 * @return
	 */
	private List<String> getImportedTypeName(ModuleDeclaration moduleDeclaration, final int offset) {
		org.eclipse.php.core.compiler.ast.nodes.UseStatement[] useStatements = ASTUtils
				.getUseStatements(moduleDeclaration, offset);
		List<String> importedClass = new ArrayList<String>();
		for (org.eclipse.php.core.compiler.ast.nodes.UseStatement statement : useStatements) {
			for (UsePart usePart : statement.getParts()) {
				String name;
				if (usePart.getAlias() != null) {
					name = usePart.getAlias().getName();
				} else {
					// In case there's no alias - the alias is the
					// last segment of the namespace name:
					name = usePart.getNamespace().getName();
				}
				importedClass.add(name.toLowerCase());
			}
		}
		return importedClass;
	}

	public int getUseStatementType(IModelElement modelElement) throws ModelException {
		if (modelElement.getElementType() != IModelElement.TYPE && !(modelElement instanceof FakeConstructor)) {
			if (modelElement.getElementType() == IModelElement.METHOD) {
				return UseStatement.T_FUNCTION;
			} else if (modelElement.getElementType() == IModelElement.FIELD
					&& PHPFlags.isConstant(((IMember) modelElement).getFlags())) {
				return UseStatement.T_CONST;
			}
		}
		return UseStatement.T_NONE;
	}

	private void addUseStatement(int index, UseStatement newUseStatement, List<Statement> statements,
			IDocument document) {
		if (index > 0) { // workaround for bug 393253
			try {
				int beginLine = document.getLineOfOffset(statements.get(index - 1).getEnd()) + 1;
				newUseStatement.setSourceRange(document.getLineOffset(beginLine), 0);
			} catch (Exception e) {
			}
		}
		statements.add(index, newUseStatement);
	}

	private void addBlankLineEdit(TextEdit edits, IDocument document) throws BadLocationException {
		String lineDelim = TextUtilities.getDefaultLineDelimiter(document);
		int changeOffset = edits.getChildren()[0].getOffset();
		IRegion region = document.getLineInformationOfOffset(changeOffset);
		String space = document.get(region.getOffset(), changeOffset - region.getOffset());
		edits.addChild(new InsertEdit(changeOffset, lineDelim + space));
	}

	private boolean isSameNamespace(String namespaceName, Program program, ISourceModule sourceModule, int offset) {
		NamespaceDeclaration currentNamespace = getCurrentNamespace(program, sourceModule, offset - 1);
		if (currentNamespace == null) {
			return false;
		}
		if (namespaceName.equals(getNamespaceName(currentNamespace))) {
			return true;
		}
		return false;
	}

	private boolean canInsertUseStatement(int statementType, PHPVersion phpVersion) {
		return statementType == UseStatement.T_NONE || phpVersion.isGreaterThan(PHPVersion.PHP5_5);
	}

	private Map<Object, Object> createOptions(IModelElement modelElement) {
		Map<Object, Object> options = new HashMap<Object, Object>(PHPCorePlugin.getOptions());

		if (modelElement == null || modelElement.getScriptProject() == null) {
			return options;
		}

		IScopeContext[] contents = new IScopeContext[] { new ProjectScope(modelElement.getScriptProject().getProject()),
				InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		for (int i = 0; i < contents.length; i++) {
			IScopeContext scopeContext = contents[i];
			IEclipsePreferences node = scopeContext.getNode(PHPCorePlugin.ID);
			if (node != null) {
				if (!options.containsKey(PHPCoreConstants.FORMATTER_USE_TABS)) {
					String useTabs = node.get(PHPCoreConstants.FORMATTER_USE_TABS, null);
					if (useTabs != null) {
						options.put(PHPCoreConstants.FORMATTER_USE_TABS, useTabs);
					}
				}
				if (!options.containsKey(PHPCoreConstants.FORMATTER_INDENTATION_SIZE)) {
					String size = node.get(PHPCoreConstants.FORMATTER_INDENTATION_SIZE, null);
					if (size != null) {
						options.put(PHPCoreConstants.FORMATTER_INDENTATION_SIZE, size);
					}
				}
			}
		}
		return options;
	}

}
