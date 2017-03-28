/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.extract.function;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewriteFlattener;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.internal.core.ast.scanner.php5.PhpAstLexer;
import org.eclipse.php.internal.core.ast.util.Util;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.internal.ui.corext.util.Resources;
import org.eclipse.php.refactoring.core.LinkedNodeFinder;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.changes.ProgramDocumentChange;
import org.eclipse.php.refactoring.core.extract.function.SnippetFinder.Match;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;
import org.eclipse.php.ui.CodeGeneration;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

public class ExtractFunctionRefactoring extends Refactoring {

	private ISourceModule sourceModule = null;
	private IDocument document = null;
	// private IExpressionFragment fSelectedExpression;
	private int selectionStartOffset;
	private int selectionLength;
	private Program astRoot;
	private boolean fReplaceAllOccurrences;
	private String fNewFunctionName = null;
	protected String[] arguments = null;
	private DocumentChange textFileChange = null;
	private RefactoringStatus matchingFragmentsStatus;

	/**
	 * The root change for all changes
	 */
	protected CompositeChange rootChange;
	private ASTRewrite fRewriter;
	private AST fAST;
	private ExtractFunctionAnalyzer fAnalyzer;
	private ArrayList<ParameterInfo> fParameterInfos;
	private int fVisibility;
	private Match[] fDuplicates;
	private boolean fReplaceDuplicates;
	private boolean fGeneratePHPDoc;

	public ExtractFunctionRefactoring(ISourceModule sourceModule,
			IDocument document, int offset, int length) {
		this.sourceModule = sourceModule;
		this.document = document;
		this.selectionStartOffset = offset;
		this.selectionLength = length;
		fReplaceAllOccurrences = true; // default
		fVisibility = -1;
		fNewFunctionName = "extracted"; //$NON-NLS-1$

	}

	/**
	 * Sets the new variable name (given by the user)
	 * 
	 * @param newVariableName
	 */
	public void setNewFunctionName(String newVariableName) {
		this.fNewFunctionName = newVariableName;
	}

	/**
	 * Sets the value for replace all occurrences (given by the user)
	 * 
	 * @param replaceAllOccurrences
	 */
	public void setReplaceDuplicates(boolean replaceAllOccurrences) {
		this.fReplaceAllOccurrences = replaceAllOccurrences;
	}

	public boolean getReplaceDuplicates() {
		return fReplaceAllOccurrences;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		try {
			pm.beginTask("", 8); //$NON-NLS-1$
			// check if the file is in sync
			RefactoringStatus status = validateModifiesFiles(
					new IResource[] { sourceModule.getResource() },
					getValidationContext());
			if (status.hasFatalError()) {
				return status;
			}
			try {
				astRoot = ASTUtils.createProgramFromSource(sourceModule);

				final Reader reader = new StringReader(document.get());
				astRoot.initCommentMapper(document, new PhpAstLexer(reader));

				fAST = astRoot.getAST();
				astRoot.accept(createVisitor());

			} catch (Exception e) {
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages
								.getString("ExtractFunctionRefactoring.10")); //$NON-NLS-1$
			}
			status.merge(fAnalyzer.checkInitialConditions());

			status.merge(fAnalyzer.checkSelection(status,
					new SubProgressMonitor(pm, 3)));
			if (status.hasFatalError())
				return status;

			if (fVisibility == -1) {
				setVisibility(Modifiers.AccPrivate);
			}

			initializeParameterInfos();
			initializeDuplicates();

			return status;
		} finally {
			pm.done();
		}
	}

	public boolean isClassMethod() {
		return this.fAnalyzer.getEnclosingBodyDeclaration() instanceof MethodDeclaration;
	}

	public boolean isStaticMethod() {
		if (this.fAnalyzer.getEnclosingBodyDeclaration() instanceof MethodDeclaration) {
			return Flags.isStatic(((MethodDeclaration) fAnalyzer
					.getEnclosingBodyDeclaration()).getModifier());
		}
		return false;
	}

	private void initializeDuplicates() {
		ASTNode start = fAnalyzer.getEnclosingBodyDeclaration();
		while (isClassMethod() && !(start instanceof ClassDeclaration)) {
			start = start.getParent();
		}

		fDuplicates = SnippetFinder
				.perform(start, fAnalyzer.getSelectedNodes());
		fReplaceDuplicates = fDuplicates.length > 0;
	}

	private void initializeParameterInfos() {
		IVariableBinding[] arguments = fAnalyzer.getArguments();
		if (arguments != null) {
			fParameterInfos = new ArrayList<ParameterInfo>(arguments.length);
			for (int i = 0; i < arguments.length; i++) {
				IVariableBinding argument = arguments[i];
				if (argument == null)
					continue;
				ParameterInfo info = new ParameterInfo(argument,
						argument.getName(), i);
				fParameterInfos.add(info);
			}
		}
	}

	private AbstractVisitor createVisitor() throws CoreException, IOException {
		fAnalyzer = new ExtractFunctionAnalyzer(astRoot, sourceModule,
				document, Selection.createFromStartLength(selectionStartOffset,
						selectionLength));
		return fAnalyzer;
	}

	public ASTNode[] getSelectedNodes() {
		return fAnalyzer.getSelectedNodes();
	}

	// -------- validateEdit checks ----

	public static RefactoringStatus validateModifiesFiles(
			IResource[] filesToModify, Object context) {
		RefactoringStatus result = new RefactoringStatus();
		IStatus status = Resources.checkInSync(filesToModify);
		if (!status.isOK())
			result.merge(RefactoringStatus.create(status));
		status = Resources.makeCommittable(filesToModify, context);
		if (!status.isOK()) {
			result.merge(RefactoringStatus.create(status));
			if (!result.hasFatalError()) {
				result.addFatalError(PhpRefactoringCoreMessages
						.getString("ExtractFunctionRefactoring.11")); //$NON-NLS-1$
			}
		}
		return result;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {

		RefactoringStatus status = new RefactoringStatus();
		// createChange(pm);

		status.merge(matchingFragmentsStatus);
		status.merge(doesNameAlreadyExist(fNewFunctionName));

		return status;
	}

	/**
	 * Checks whether the user given name already exists in the visible scope
	 * 
	 * @return the status including necessary warnings
	 */
	private RefactoringStatus doesNameAlreadyExist(String name) {

		RefactoringStatus status = new RefactoringStatus();

		// if the selection is enclosed by a function,
		// check if the user given variable name already exists in the function
		// scope
		if (fAnalyzer.getEnclosingBodyDeclaration().getType() == ASTNode.FUNCTION_DECLARATION
				|| fAnalyzer.getEnclosingBodyDeclaration().getType() == ASTNode.METHOD_DECLARATION) {
			if (PhpElementConciliator.functionAlreadyExists(astRoot, name)) {
				status.addWarning(PhpRefactoringCoreMessages
						.getString("ExtractFunctionRefactoring.3")); //$NON-NLS-1$
			}
		}
		return status;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		try {
			pm.beginTask(PhpRefactoringCoreMessages
					.getString("ExtractFunctionRefactoring"), 1); //$NON-NLS-1$

			ASTNode declaration = fAnalyzer.getEnclosingBodyDeclaration();
			fRewriter = ASTRewrite.create(declaration.getAST());

			rootChange = new CompositeChange(
					PhpRefactoringCoreMessages
							.format("ExtractFunctionRefactoring.2", new String[] { fNewFunctionName })); //$NON-NLS-1$
			rootChange.markAsSynthetic();

			MultiTextEdit root = new MultiTextEdit();
			textFileChange = new ProgramDocumentChange(
					PhpRefactoringCoreMessages
							.format("ExtractFunctionRefactoring.2", new String[] { fNewFunctionName }), document, astRoot); //$NON-NLS-1$
			textFileChange.setEdit(root);
			textFileChange.setTextType("php"); //$NON-NLS-1$

			rootChange.add(textFileChange);

			ASTNode[] selectedNodes = fAnalyzer.getSelectedNodes();

			TextEditGroup substituteDesc = new TextEditGroup(
					PhpRefactoringCoreMessages
							.format("ExtractFunctionRefactoring.2", new String[] { fNewFunctionName })); //$NON-NLS-1$
			textFileChange.addTextEditGroup(substituteDesc);

			String lineDelimiter = Util.getLineSeparator(astRoot
					.getSourceModule().getSource(), astRoot.getSourceModule()
					.getScriptProject());

			FunctionDeclaration function = createNewFunction(selectedNodes,
					lineDelimiter, substituteDesc);
			MethodDeclaration method = null;

			Comment funcComment = null;
			if (isClassMethod()) {
				method = fAST.newMethodDeclaration();
				method.setFunction(function);
				int flags = fVisibility;
				if (isStaticMethod()) {
					flags = flags | Modifiers.AccStatic;
				}
				method.setModifier(flags);
				if (fGeneratePHPDoc) {
					IScriptProject sp = fAnalyzer.getEnclosingBodyDeclaration()
							.getProgramRoot().getSourceModule()
							.getScriptProject();
					ITypeBinding classDecl = ((MethodDeclaration) fAnalyzer
							.getEnclosingBodyDeclaration())
							.resolveMethodBinding().getDeclaringClass();
					List<FormalParameter> parameters = method.getFunction()
							.formalParameters();
					String[] paramNames = new String[parameters.size()];
					for (int i = 0; i < parameters.size(); i++) {
						Expression name = parameters.get(i).getParameterName();
						if (name instanceof Scalar) {
							paramNames[i] = removeDollar(((Scalar) name)
									.getStringValue());
						}
						if (name instanceof Identifier) {
							paramNames[i] = ((Identifier) name).getName();
						}
					}
					String comments = CodeGeneration.getMethodComment(sp,
							classDecl.getName(), method.getFunction()
									.getFunctionName().getName(), paramNames,
							null, null, null, lineDelimiter, null);

					Comment commentNode = (Comment) fRewriter
							.createStringPlaceholder(comments, ASTNode.COMMENT);
					commentNode.setCommentType(Comment.TYPE_PHPDOC);
					method.setComment(commentNode);
				}
			} else {
				if (fGeneratePHPDoc) {
					IScriptProject sp = fAnalyzer.getEnclosingBodyDeclaration()
							.getProgramRoot().getSourceModule()
							.getScriptProject();

					List<FormalParameter> parameters = function
							.formalParameters();
					String[] paramNames = new String[parameters.size()];
					for (int i = 0; i < parameters.size(); i++) {
						Expression name = parameters.get(i).getParameterName();
						if (name instanceof Scalar) {
							paramNames[i] = removeDollar(((Scalar) name)
									.getStringValue());
						}
						if (name instanceof Identifier) {
							paramNames[i] = ((Identifier) name).getName();
						}
					}

					String comments = CodeGeneration.getMethodComment(sp,
							"", //$NON-NLS-1$
							function.getFunctionName().getName(), paramNames,
							null, null, null, lineDelimiter, null);
					comments = lineDelimiter + comments + lineDelimiter;

					funcComment = (Comment) fRewriter.createStringPlaceholder(
							comments, ASTNode.COMMENT);
					funcComment.setCommentType(Comment.TYPE_PHPDOC);
				}

			}

			TextEditGroup insertDesc = new TextEditGroup(
					PhpRefactoringCoreMessages
							.getString("ExtractFunctionRefactoring.4")); //$NON-NLS-1$
			textFileChange.addTextEditGroup(insertDesc);

			ChildListPropertyDescriptor desc = (ChildListPropertyDescriptor) declaration
					.getLocationInParent();

			ListRewrite container = null;
			if (declaration instanceof Program) {
				container = fRewriter.getListRewrite(declaration,
						Program.STATEMENTS_PROPERTY);
			} else {
				container = fRewriter.getListRewrite(declaration.getParent(),
						desc);
			}
			if (method != null) {
				container.insertAfter(method, declaration, insertDesc);
			} else {
				if (declaration instanceof Program) {
					// This is a work around to add the new function before the
					// empty statement of the Program.

					List<Statement> statements = ((Program) declaration)
							.statements();
					int length = statements.size();
					// Since the program at least has the select expression and
					// a empty statement,
					// it's safe for assuming the length > 1.
					// Work ground for now.
					Statement node = statements.get(length - 1);
					if (length >= 2 && (node instanceof InLineHtml)) {
						container.insertBefore(function,
								(ASTNode) statements.get(length - 2),
								insertDesc);
						if (funcComment != null) {
							container.insertBefore(funcComment, function,
									insertDesc);
						}
					} else if (length > 1 && node instanceof EmptyStatement) {
						container.insertBefore(function,
								(ASTNode) statements.get(length - 1),
								insertDesc);
						if (funcComment != null) {
							container.insertBefore(funcComment, function,
									insertDesc);
						}

					} else {
						container.insertLast(function, insertDesc);
						if (funcComment != null) {
							container.insertBefore(funcComment, function,
									insertDesc);
						}
					}

				} else {
					container.insertAfter(function, declaration, insertDesc);
					if (funcComment != null) {
						container.insertBefore(funcComment, function,
								insertDesc);
					}
				}
			}

			if (getReplaceDuplicates()) {
				replaceDuplicates(textFileChange);
			}
			TextEdit edit = fRewriter.rewriteAST(document, null);
			root.addChild(edit);

			return rootChange;
		} finally {
			pm.done();
		}

	}

	private void replaceDuplicates(DocumentChange textFileChange2) {
		int numberOf = getNumberOfDuplicates();
		if (numberOf == 0 || !fReplaceDuplicates)
			return;
		String label = null;
		if (numberOf == 1)
			label = PhpRefactoringCoreMessages
					.format("ExtractFunctionRefactoring.5", new String[] { fNewFunctionName }); //$NON-NLS-1$
		else
			label = PhpRefactoringCoreMessages
					.format("ExtractFunctionRefactoring.6", new String[] { fNewFunctionName }); //$NON-NLS-1$

		TextEditGroup description = new TextEditGroup(label);
		textFileChange2.addTextEditGroup(description);

		for (int d = 0; d < fDuplicates.length; d++) {
			SnippetFinder.Match duplicate = fDuplicates[d];
			if (!duplicate.isMethodBody()) {
				ASTNode[] callNodes = createCallNodes(duplicate);

				ASTNode[] nodes = duplicate.getNodes();
				for (ASTNode node : nodes) {
					fRewriter.replace(node, callNodes[0], description);
				}
			}
		}
	}

	private FunctionDeclaration createNewFunction(ASTNode[] selectedNodes,
			String lineSeparator, TextEditGroup substitute) {
		FunctionDeclaration result = createNewFunctionDeclaration();

		result.setBody(createFunctionBody(selectedNodes, substitute));
		return result;
	}

	private Block createFunctionBody(ASTNode[] selectedNodes,
			TextEditGroup substitute) {
		Block result = fAST.newBlock();
		ListRewrite statements = fRewriter.getListRewrite(result,
				Block.STATEMENTS_PROPERTY);

		for (Iterator<ParameterInfo> iter = fParameterInfos.iterator(); iter
				.hasNext();) {
			ParameterInfo parameter = iter.next();
			if (parameter.isRenamed()) {
				for (int n = 0; n < selectedNodes.length; n++) {
					Identifier[] oldNames = LinkedNodeFinder.findByBinding(
							selectedNodes[n], parameter.getOldBinding());
					for (int i = 0; i < oldNames.length; i++) {
						fRewriter.replace(oldNames[i], fAST
								.newIdentifier(removeDollar(parameter
										.getNewName())), substitute);
					}
				}
			}
		}

		boolean extractsExpression = fAnalyzer.isExpressionSelected();
		ASTNode[] callNodes = createCallNodes(null);
		ASTNode replacementNode;
		if (callNodes.length == 1) {
			replacementNode = callNodes[0];
		} else {
			replacementNode = fRewriter.createGroupNode(callNodes);
		}
		if (extractsExpression) {
			// if we have an expression then only one node is selected.
			ReturnStatement rs = fAST.newReturnStatement();
			rs.setExpression((Expression) fRewriter
					.createMoveTarget(selectedNodes[0]));
			statements.insertLast(rs, null);
			fRewriter.replace(selectedNodes[0], replacementNode, substitute);
		} else {
			if (selectedNodes.length == 1) {
				statements.insertLast(
						fRewriter.createMoveTarget(selectedNodes[0]),
						substitute);
				fRewriter
						.replace(selectedNodes[0], replacementNode, substitute);
			} else {
				ListRewrite source = fRewriter.getListRewrite(selectedNodes[0]
						.getParent(),
						(ChildListPropertyDescriptor) selectedNodes[0]
								.getLocationInParent());

				ASTNode[] nodes = filterComments(selectedNodes);
				if (nodes.length > 0) {
					ASTNode toMove = source.createMoveTarget(nodes[0],
							selectedNodes[nodes.length - 1], replacementNode,
							substitute);
					statements.insertLast(toMove, substitute);
				}
			}
			IVariableBinding returnValue = fAnalyzer.getReturnValue();
			if (returnValue != null) {
				ReturnStatement rs = fAST.newReturnStatement();
				rs.setExpression(fAST.newIdentifier((getName(returnValue))));
				statements.insertLast(rs, null);
			}
		}
		return result;

	}

	private ASTNode[] filterComments(ASTNode[] selectedNodes) {
		ArrayList<ASTNode> nodes = new ArrayList<ASTNode>(selectedNodes.length);
		for (ASTNode node : selectedNodes) {
			if (!(node instanceof Comment)) {
				nodes.add(node);
			}
		}

		return nodes.toArray(new ASTNode[nodes.size()]);
	}

	private String getName(IVariableBinding binding) {
		for (Iterator<ParameterInfo> iter = fParameterInfos.iterator(); iter
				.hasNext();) {
			ParameterInfo info = iter.next();
			if (binding.equals(info.getOldBinding())) {
				return info.getNewName();
			}
		}
		return binding.getName();
	}

	@SuppressWarnings("unchecked")
	private ASTNode[] createCallNodes(Object duplicate) {
		List result = new ArrayList(2);

		Expression invocation = null;

		List arguments = null;
		if (isClassMethod()) {
			if (isStaticMethod()) {
				invocation = fAST.newStaticMethodInvocation();
				FunctionInvocation funcInv = fAST.newFunctionInvocation();
				funcInv.setFunctionName(fAST.newFunctionName(fAST
						.newIdentifier(fNewFunctionName)));
				((StaticMethodInvocation) invocation).setMethod(funcInv);
				((StaticMethodInvocation) invocation).setClassName(fAST
						.newIdentifier("self")); //$NON-NLS-1$
				arguments = ((StaticMethodInvocation) invocation).getMethod()
						.parameters();

			} else {
				invocation = fAST.newMethodInvocation();
				FunctionInvocation funcInv = fAST.newFunctionInvocation();
				funcInv.setFunctionName(fAST.newFunctionName(fAST
						.newIdentifier(fNewFunctionName)));
				((MethodInvocation) invocation).setMethod(funcInv);
				((MethodInvocation) invocation).setDispatcher(fAST
						.newVariable("this")); //$NON-NLS-1$
				arguments = ((MethodInvocation) invocation).getMethod()
						.parameters();
			}

		} else {
			invocation = fAST.newFunctionInvocation();
			((FunctionInvocation) invocation).setFunctionName(fAST
					.newFunctionName(fAST.newIdentifier(fNewFunctionName)));
			arguments = ((FunctionInvocation) invocation).parameters();
		}

		for (int i = 0; i < fParameterInfos.size(); i++) {
			ParameterInfo parameter = (ParameterInfo) fParameterInfos.get(i);
			arguments.add(fAST.newScalar(parameter.getOldName()));
		}

		ASTNode call;
		int returnKind = fAnalyzer.getReturnKind();
		switch (returnKind) {
		case ExtractFunctionAnalyzer.ACCESS_TO_LOCAL:
			Assignment assignment = fAST.newAssignment();
			assignment.setLeftHandSide(fAST.newVariable(removeDollar(fAnalyzer
					.getReturnValue().getName())));
			assignment.setRightHandSide(invocation);
			call = assignment;
			break;
		case ExtractFunctionAnalyzer.RETURN_STATEMENT_VALUE:
			ReturnStatement rs = fAST.newReturnStatement();
			rs.setExpression(invocation);
			call = rs;
			break;
		default:
			call = invocation;
		}

		if (call instanceof Expression && !fAnalyzer.isExpressionSelected()) {
			call = fAST.newExpressionStatement((Expression) call);
		}
		result.add(call);
		return (ASTNode[]) result.toArray(new ASTNode[result.size()]);
	}

	private String removeDollar(String name) {
		String value = name;
		if (name != null && name.length() > 0 && name.startsWith("$")) { //$NON-NLS-1$
			value = name.substring(1);
		}

		return value;
	}

	private FunctionDeclaration createNewFunctionDeclaration() {
		FunctionDeclaration result = fAST.newFunctionDeclaration();
		result.setFunctionName(fAST.newIdentifier(this.fNewFunctionName));

		List<FormalParameter> parameters = result.formalParameters();
		for (int i = 0; i < fParameterInfos.size(); i++) {
			ParameterInfo info = (ParameterInfo) fParameterInfos.get(i);
			FormalParameter parameter = fAST.newFormalParameter();
			parameter.setParameterName(fAST.newScalar(info.getNewName()));
			parameters.add(parameter);
		}

		return result;
	}

	// private Variable getVariableDeclaration(ParameterInfo parameter) {
	// return ((VariableBinding) parameter.getOldBinding()).getVarialbe();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#getName()
	 */
	@Override
	public String getName() {
		return PhpRefactoringCoreMessages
				.getString("ExtractFunctionRefactoring.8"); //$NON-NLS-1$
	}

	// public Change getChange() {
	// return rootChange;
	// }

	/**
	 * @param text
	 * @return
	 */
	public RefactoringStatus checkNewVariableName(String text) {
		return doesNameAlreadyExist(text);
	}

	/**
	 * @return
	 */
	public int getVisibility() {
		return fVisibility;
	}

	public List<ParameterInfo> getParameterInfos() {
		return fParameterInfos;
	}

	public int getNumberOfDuplicates() {

		return fDuplicates.length;
	}

	public void setVisibility(int intValue) {
		fVisibility = intValue;

	}

	public boolean getGeneratePHPdoc() {
		return fGeneratePHPDoc;
	}

	public void setGeneratePHPdoc(boolean value) {
		fGeneratePHPDoc = value;

	}

	/**
	 * Returns the signature of the new method.
	 * 
	 * @return the signature of the extracted method
	 */
	public String getSignature() {
		return getSignature(fNewFunctionName);
	}

	/**
	 * Returns the signature of the new method.
	 * 
	 * @param methodName
	 *            the method name used for the new method
	 * @return the signature of the extracted method
	 */
	public String getSignature(String methodName) {
		FunctionDeclaration methodDecl = createNewFunctionDeclaration();
		methodDecl.setBody(fAST.newBlock());
		methodDecl.getFunctionName();
		String str = ASTRewriteFlattener.asString(methodDecl, null);
		return str.substring(0, str.indexOf('{'));
	}

	public void setMethodName(String text) {
		this.fNewFunctionName = text;

	}

	public RefactoringStatus checkFunctionName() {
		RefactoringStatus status = new RefactoringStatus();
		status.merge(RefactoringUtility.checkNewElementName(fNewFunctionName));
		status.merge(doesNameAlreadyExist(fNewFunctionName));
		return status;
	}

	public RefactoringStatus checkParameterNames() {
		RefactoringStatus status = new RefactoringStatus();
		status.merge(RefactoringUtility.checkNewElementName(fNewFunctionName));
		status.merge(doesNameAlreadyExist(fNewFunctionName));
		return status;
	}
}
