/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.extract.variable;

import java.util.*;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.corext.dom.fragments.ASTFragmentFactory;
import org.eclipse.php.internal.ui.corext.dom.fragments.AssociativeInfixExpressionFragment;
import org.eclipse.php.internal.ui.corext.dom.fragments.IASTFragment;
import org.eclipse.php.internal.ui.corext.dom.fragments.IExpressionFragment;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.RefactoringPlugin;
import org.eclipse.php.refactoring.core.SourceModuleSourceContext;
import org.eclipse.php.refactoring.core.changes.ProgramDocumentChange;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;
import org.eclipse.php.refactoring.core.visitor.ScopeSyntaxErrorsVisitor;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

public class ExtractVariableRefactoring extends Refactoring {

	private static final String CHANGE_DESCRIPTION = PhpRefactoringCoreMessages
			.getString("ExtractVariableRefactoring.4"); //$NON-NLS-1$
	private ISourceModule sourceModule = null;
	private IDocument document = null;
	private IExpressionFragment fSelectedExpression;
	private int selectionStartOffset;
	private int selectionLength;
	private Program astRoot;
	private boolean fReplaceAllOccurrences;
	private String newVariableName = null;
	private String[] fGuessedTempNames;
	private ASTNode enclosingBodyNode;
	private DocumentChange textFileChange = null;
	private IASTFragment[] allMatchingFragments = null;
	private IASTFragment[] validMatchingFragments = null;
	private RefactoringStatus matchingFragmentsStatus;
	private boolean createVariableDeclaration = false;

	/**
	 * The root change for all changes
	 */
	protected CompositeChange rootChange;
	private ASTRewrite fRewriter;
	private AST fAst;

	public ExtractVariableRefactoring(ISourceModule source, IDocument document, int offset, int length)
			throws CoreException {
		this.sourceModule = source;
		this.document = document;
		this.selectionStartOffset = offset;
		// for the case of place a cursor under an expression with no text
		// selection
		if (length == 0) {
			recalculateLength(source, offset);
		} else {
			this.selectionLength = length;
		}

		fReplaceAllOccurrences = true; // default
	}

	private void recalculateLength(ISourceModule source, int offset) throws CoreException {
		Program program = null;

		try {
			program = ASTUtils.createProgramFromSource(source);
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, RefactoringPlugin.PLUGIN_ID, "Unexpected Error", e)); //$NON-NLS-1$
		}
		ASTNode selectedNode = NodeFinder.perform(program, offset, 0);
		ASTNode parent = selectedNode.getParent();

		// for the php 5.3 with name space case.
		if (parent instanceof NamespaceName) {
			parent = parent.getParent();
		}

		selectionStartOffset = parent.getStart();
		selectionLength = parent.getLength();
	}

	/**
	 * Sets the new variable name (given by the user)
	 * 
	 * @param newVariableName
	 */
	public void setNewVariableName(String newVariableName) {
		this.newVariableName = newVariableName;
	}

	/**
	 * Sets the value for replace all occurrences (given by the user)
	 * 
	 * @param replaceAllOccurrences
	 */
	public void setReplaceAllOccurrences(boolean replaceAllOccurrences) {
		this.fReplaceAllOccurrences = replaceAllOccurrences;
	}

	public boolean getReplaceAllOccurrences() {
		return fReplaceAllOccurrences;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		try {

			pm.beginTask("", 8); //$NON-NLS-1$

			// check if the file is in sync
			RefactoringStatus status = RefactoringUtility
					.validateModifiesFiles(new IResource[] { sourceModule.getResource() }, getValidationContext());
			if (status.hasFatalError())
				return status;

			try {
				astRoot = ASTUtils.createProgramFromSource(sourceModule);
			} catch (Exception e) {
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.0")); //$NON-NLS-1$
			}

			status.merge(checkSelection(status, new SubProgressMonitor(pm, 3)));

			if (!status.hasFatalError() && isLiteralNodeSelected())
				fReplaceAllOccurrences = false;

			return status;

		} finally {
			pm.done();
		}
	}

	private boolean isLiteralNodeSelected() {
		IExpressionFragment fragment = getSelectedExpression();
		if (fragment == null)
			return false;
		Expression expression = fragment.getAssociatedExpression();
		if (expression == null)
			return false;
		return (expression.getType() == ASTNode.SCALAR);
	}

	/**
	 * Gets the current selection
	 * 
	 * @param astRoot
	 * @return
	 */
	private RefactoringStatus checkSelection(RefactoringStatus status, IProgressMonitor pm) {

		try {
			pm.beginTask("", 8); //$NON-NLS-1$

			IExpressionFragment selectedExpression = getSelectedExpression();

			if (selectedExpression == null) {
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.2")); //$NON-NLS-1$
			}
			pm.worked(1);

			enclosingBodyNode = getEnclosingBodyNode();
			if (enclosingBodyNode == null)
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.3")); //$NON-NLS-1$
			pm.worked(1);

			if (scopeHasSyntaxErrors(enclosingBodyNode)) {
				return RefactoringStatus
						.createFatalErrorStatus("Unable to activate refactoring. Please fix syntax errors"); //$NON-NLS-1$
			}
			pm.worked(1);

			Expression expression = getSelectedExpression().getAssociatedExpression();

			status.merge(canExtract(expression));

			pm.worked(1);

			return status;

		} finally {
			pm.done();
		}
	}

	/**
	 * Remove from the matching fragments, the invalid ones and look for non
	 * fatal errors/warnings
	 * 
	 * @param recalculate
	 * @return
	 */
	@SuppressWarnings("restriction")
	private IASTFragment[] retainOnlyReplacableMatches(boolean recalculate) {

		// if (validMatchingFragments != null && !recalculate)
		// return validMatchingFragments;

		matchingFragmentsStatus = new RefactoringStatus();

		IASTFragment[] allMatches = getMatchingFragments(recalculate);

		matchingFragmentsStatus.merge(checkSemanticProblems(allMatches));

		List<IASTFragment> result = new ArrayList<IASTFragment>(allMatches.length);
		for (int i = 0; i < allMatches.length; i++) {

			Expression associatedExpression = getExpressionFromFragment(allMatches[i]).getAssociatedExpression();
			RefactoringStatus status = canExtract(associatedExpression);
			// if the match has a fatal error, it is not added to the final
			// array
			if (!status.hasFatalError()) {
				result.add(allMatches[i]);
			}
		}

		validMatchingFragments = result.toArray(new IASTFragment[result.size()]);

		matchingFragmentsStatus.merge(checkMatchingExpressions(validMatchingFragments));

		return validMatchingFragments;
	}

	/**
	 * Look for semantic problems in the matching fragments
	 * 
	 * @param allMatches
	 * @return RefactoringStatus
	 */
	private RefactoringStatus checkSemanticProblems(IASTFragment[] allMatches) {

		boolean firstRighHandSideFlag = false;
		boolean firstLeftHandSideFlag = false;
		boolean hasSemanticProblem = false;

		RefactoringStatus status = new RefactoringStatus();

		// look for semantic problems.
		// The user wants to extract $a. $a is assigned to variables twice,
		// and between them it is assigned a new value
		// warn from the following case:
		// $a = 4;
		// $c = $a;
		// $a = 5;
		// $b = $a;

		ISourceRange region = null;
		for (int i = 0; i < allMatches.length; i++) {
			Expression associatedExpression = getExpressionFromFragment(allMatches[i]).getAssociatedExpression();

			if (isExpressionRightHandSide(associatedExpression)) {
				if (!firstRighHandSideFlag) {
					firstRighHandSideFlag = true;
				} else if (firstLeftHandSideFlag) {
					hasSemanticProblem = true;
					break;
				}
			} else if (isExpressionLeftHandSide(associatedExpression) && !firstLeftHandSideFlag) {
				if (firstRighHandSideFlag) {
					firstLeftHandSideFlag = true;
					region = new org.eclipse.dltk.corext.SourceRange(associatedExpression.getStart(),
							associatedExpression.getLength());
				}
			}
		}

		if (hasSemanticProblem) {
			status.merge(RefactoringStatus.createWarningStatus(
					PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.7"), //$NON-NLS-1$
					new SourceModuleSourceContext(sourceModule, region)));
		}

		return status;
	}

	/**
	 * Look for non fatal errors /warning in the matching fragments
	 * 
	 * @param allMatches
	 * @return RefactoringStatus
	 */
	private RefactoringStatus checkMatchingExpressions(IASTFragment[] allMatches) {
		RefactoringStatus status = new RefactoringStatus();

		IExpressionFragment selectedExpression = getSelectedExpression();

		for (int i = 0; i < allMatches.length; i++) {
			Expression associatedExpression = getExpressionFromFragment(allMatches[i]).getAssociatedExpression();
			boolean matchAppearsBeforeVariableDeclaration = createVariableDeclaration
					&& (associatedExpression.getStart() < selectedExpression.getStartPosition());
			if (matchAppearsBeforeVariableDeclaration) {
				status.merge(RefactoringStatus
						.createErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.8"))); //$NON-NLS-1$
			}
		}

		return status;
	}

	@SuppressWarnings("restriction")
	private RefactoringStatus canExtract(Expression expression) {

		if (expression.getType() == ASTNode.SCALAR) {
			Scalar scalar = (Scalar) expression;
			if (scalar.getScalarType() == Scalar.TYPE_STRING && scalar.getStringValue().equalsIgnoreCase("null")) { //$NON-NLS-1$
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.5")); //$NON-NLS-1$
			}
		}

		if (isDispatch(expression)) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.9")); //$NON-NLS-1$
		}

		if (isFunctionName(expression)) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.9")); //$NON-NLS-1$
		}

		if (isClassName(expression)) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.9")); //$NON-NLS-1$
		}

		if (isExpressionLeftHandSide(expression)) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.10")); //$NON-NLS-1$
		}

		if (isUsedInForInitializerOrUpdaterOrIncrementor(expression))
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.11")); //$NON-NLS-1$

		if (assignmentInStaticStatement(expression)) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.12")); //$NON-NLS-1$
		}

		if (expressionOfCatchVariable(expression)) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.13")); //$NON-NLS-1$
		}

		if (expression.isStaticScalar()) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.14")); //$NON-NLS-1$
		}

		if (expression.getType() == ASTNode.FORMAL_PARAMETER
				|| (expression.getParent() != null && expression.getParent().getType() == ASTNode.FORMAL_PARAMETER)) {
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.15")); //$NON-NLS-1$
		}
		return new RefactoringStatus();
	}

	private boolean isClassName(Expression selectedExpression) {
		ASTNode parent = selectedExpression.getParent();
		if (parent == null) {
			return false;
		}
		return parent instanceof TypeDeclaration;
	}

	private boolean isFunctionName(Expression selectedExpression) {
		ASTNode parent = selectedExpression.getParent();
		if (parent == null) {
			return false;
		}
		return parent instanceof FunctionDeclaration;

	}

	/**
	 * Checks the case of try { } catch (Exception $a) { } and the $a is
	 * selected
	 * 
	 * @param expression
	 * @return
	 */
	private boolean expressionOfCatchVariable(Expression expression) {
		final ASTNode parent = expression.getParent();
		if (parent.getType() == ASTNode.CATCH_CLAUSE) {
			final CatchClause claue = (CatchClause) parent;
			if (claue.getVariable() == expression) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if there is a syntax error in the scope of the selection
	 * 
	 * @param enclosingBodyNode
	 * @return true in case the scope of the selection as a syntax error, false
	 *         otherwise
	 */
	private boolean scopeHasSyntaxErrors(ASTNode enclosingBodyNode) {
		ScopeSyntaxErrorsVisitor visitor = new ScopeSyntaxErrorsVisitor();

		switch (enclosingBodyNode.getType()) {
		case ASTNode.FUNCTION_DECLARATION:
			((FunctionDeclaration) enclosingBodyNode).getBody().accept(visitor);
			break;
		case ASTNode.PROGRAM:
			enclosingBodyNode.accept(visitor);
			break;
		default:
			assert (false);
		}
		return visitor.hasSyntaxError;
	}

	/**
	 * Checks if the current selection is in a for statement In this case, the
	 * only valid area for a selection is the "action"/block of the for
	 * statement and not the initializer/updater/condition
	 * 
	 * @param expression
	 * @return true, in case the selection is in an invalid position in the for
	 *         statement, false otherwise
	 */
	private boolean isUsedInForInitializerOrUpdaterOrIncrementor(Expression expression) {
		boolean isInForStatement = false;
		boolean isInBlock = false;

		ASTNode parent = expression.getParent();
		while (parent != null) {

			if (parent instanceof ForStatement) {
				isInForStatement = true;

				// the selection was in a block - therefore valid
				if (isInBlock) {
					return false;
				}

				// this for statement does not have a block (no parenthesis)
				// in this case check if the selection is in the for action.
				// if this is the case, return false. Otherwise it means the
				// selection
				// is one of the for initializers/updaters/incrementors
				Statement action = ((ForStatement) parent).getBody();
				return !(selectionIsInForAction(expression, action));

			}

			if (parent instanceof Block) {
				isInBlock = true;
			}

			parent = parent.getParent();
		}

		return isInForStatement;

	}

	private boolean selectionIsInForAction(Expression expression, Statement action) {
		ASTNode parent = expression.getParent();
		while (parent != null && !(parent instanceof ForStatement)) {
			if (parent == action) {
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

	/**
	 * @param expression
	 * @return whether the selected expression in an assignment in a static
	 *         statement
	 */
	private boolean assignmentInStaticStatement(Expression expression) {
		if (expression.getType() == ASTNode.ASSIGNMENT) {
			ASTNode parent = expression.getParent();
			if (parent.getType() == ASTNode.STATIC_STATEMENT) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the expression is left hand side of assignment expression
	 * 
	 * @param expression
	 * @return
	 */
	private boolean isExpressionLeftHandSide(Expression expression) {
		final ASTNode parent = expression.getParent();
		if (parent != null && parent.getType() == ASTNode.ASSIGNMENT) {
			final Assignment assignment = (Assignment) parent;
			return assignment.getLeftHandSide() == expression;
		}
		return false;
	}

	/**
	 * Checks if the expression is right hand side of assignment expression
	 * 
	 * @param expression
	 * @return
	 */
	private boolean isExpressionRightHandSide(Expression expression) {
		final ASTNode parent = expression.getParent();
		if (parent != null && parent.getType() == ASTNode.ASSIGNMENT) {
			final Assignment assignment = (Assignment) parent;
			return assignment.getRightHandSide() == expression;
		}
		return false;
	}

	/**
	 * 
	 * @param selectedExpression
	 * @return Whether the selection is the member of a dispatch
	 */
	private boolean isDispatch(Expression selectedExpression) {
		ASTNode parent = selectedExpression.getParent();
		if (parent == null) {
			return false;
		}

		if (parent instanceof Dispatch) {
			Dispatch dispatch = (Dispatch) parent;
			return selectedExpression == dispatch.getMember();
		}

		if (parent instanceof StaticDispatch) {
			StaticDispatch dispatch = (StaticDispatch) parent;
			return selectedExpression == dispatch.getMember();
		}

		return false;
	}

	private ASTNode getEnclosingBodyNode() {
		ASTNode node = getSelectedExpression().getAssociatedNode();
		return node.getEnclosingBodyNode();
	}

	/**
	 * @return the fragment for the current selection
	 */
	private IExpressionFragment getSelectedExpression() {
		if (fSelectedExpression != null)
			return fSelectedExpression;

		IASTFragment selectedFragment;
		try {
			selectedFragment = ASTFragmentFactory.createFragmentForSourceRange(
					new SourceRange(selectionStartOffset, selectionLength), astRoot, document);
		} catch (Exception e) {
			// on bad region - return null
			return null;
		}

		fSelectedExpression = getExpressionFromFragment(selectedFragment);

		return fSelectedExpression;
	}

	/**
	 * 
	 * @param selectedFragment
	 * @return the Expression for the given fragment
	 */
	private IExpressionFragment getExpressionFromFragment(IASTFragment selectedFragment) {

		IExpressionFragment fragment = null;
		if (selectedFragment instanceof IExpressionFragment) {
			fragment = (IExpressionFragment) selectedFragment;
		} else if (selectedFragment != null) {

			ASTNode associatedNode = selectedFragment.getAssociatedNode();
			if (associatedNode instanceof ExpressionStatement) {
				ExpressionStatement exprStatement = (ExpressionStatement) associatedNode;
				Expression expression = exprStatement.getExpression();
				fragment = (IExpressionFragment) ASTFragmentFactory.createFragmentForFullSubtree(expression);
			} else if (associatedNode instanceof Assignment) {
				Assignment assignment = (Assignment) associatedNode;
				fragment = (IExpressionFragment) ASTFragmentFactory.createFragmentForFullSubtree(assignment);
			} else if (associatedNode instanceof ArrayElement) {
				ArrayElement arrayElement = (ArrayElement) associatedNode;
				if (arrayElement.getKey() == null) {
					fragment = (IExpressionFragment) ASTFragmentFactory
							.createFragmentForFullSubtree(arrayElement.getValue());
				}
			}
		}

		return fragment;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {

		String[] guessTempNames = guessTempNames();

		// in case the new variable name is empty, get the last suggestion in
		// the list
		if (newVariableName.trim().length() == 0) {
			newVariableName = guessTempNames[0];
		}

		RefactoringStatus status = new RefactoringStatus();
		createChange(pm);

		status.merge(matchingFragmentsStatus);
		status.merge(doesNameAlreadyExist(newVariableName));

		return status;
	}

	private void replaceOccurances() throws CoreException {
		IASTFragment[] fragmentsToReplace = retainOnlyReplacableMatches(false);
		for (IASTFragment fragment : fragmentsToReplace) {
			if (fragment.getAssociatedNode() != getSelectedExpression().getAssociatedNode()) {
				ISourceRange range = getReplaceOffsets(fragment);
				// replace the existing statement
				replaceSelectedExpressionWithVariableDeclaration(getFullVariableName(), range.getOffset(),
						range.getLength(), fragment.getAssociatedNode());
			}
		}
	}

	/**
	 * For a given fragment, returns the exact start offset and length for the
	 * replacement AssociativeInfixExpressionFragment have special replacements
	 * logic
	 * 
	 * @param fragment
	 * @return Source range with the relevant start offset and length
	 */
	private ISourceRange getReplaceOffsets(IASTFragment fragment) {

		ASTNode associatedNode = fragment.getAssociatedNode();

		int start = associatedNode.getStart();
		int length = associatedNode.getLength();
		if (fragment instanceof AssociativeInfixExpressionFragment) {
			AssociativeInfixExpressionFragment infixExpressionFragment = (AssociativeInfixExpressionFragment) fragment;
			List<Expression> operands = infixExpressionFragment.getOperands();
			start = operands.get(0).getStart();
			length = operands.get(operands.size() - 1).getEnd() - start;
		}

		return new SourceRange(start, length);
	}

	/**
	 * Handle the creation of changes for extracting a variable for the current
	 * selection (not including the other occurrences)
	 * 
	 * @throws CoreException
	 * @throws BadLocationException
	 */
	private void extractVariable() throws CoreException, BadLocationException {

		IExpressionFragment selectedExpressionFragment = getSelectedExpression();

		Expression selectedExpression = selectedExpressionFragment.getAssociatedExpression(); // whole
																								// expression
																								// selected

		if (shouldReplaceSelectedExpressionWithVariableDeclaration()) {
			createVariableDeclaration = true;

			// the new text that will replace the selected expression
			String replacement = getFullVariableName() + " = " //$NON-NLS-1$
					+ getASTNodeValue(getSelectedExpression().getAssociatedNode()) + ";"; //$NON-NLS-1$
			replaceSelectedExpressionWithTempDeclaration(replacement);
			// addReplaceExpressionWithTemp();
		} else {
			ISourceRange range = getReplaceOffsets(selectedExpressionFragment);

			createAndInsertVariableDeclaration(selectedExpression, range);

			addReplaceExpressionWithTemp();

		}
	}

	private void addReplaceExpressionWithTemp() {
		IASTFragment[] fragmentsToReplace = retainOnlyReplacableMatches(true);

		ASTRewrite rewrite = fRewriter;
		HashSet seen = new HashSet();
		for (int i = 0; i < fragmentsToReplace.length; i++) {
			IASTFragment fragment = fragmentsToReplace[i];
			if (!seen.add(fragment))
				continue;
			Identifier tempName = fRewriter.getAST().newIdentifier(getFullVariableName());
			TextEditGroup description = new TextEditGroup("replace "); //$NON-NLS-1$

			fragment.replace(rewrite, tempName, description);
			// if (fLinkedProposalModel != null)
			// fLinkedProposalModel.getPositionGroup(KEY_NAME,
			// true).addPosition(rewrite.track(tempName), false);
		}
	}

	private void createAndInsertVariableDeclaration(Expression selectedExpression, ISourceRange range)
			throws CoreException, BadLocationException {

		if ((!fReplaceAllOccurrences) || (retainOnlyReplacableMatches(true).length <= 1)) {
			// insertVariableDeclaration(selectedExpression, selectedExpression,
			// shouldWrapStatement, range);
			insertAt(selectedExpression);
			return;
		}

		ASTNode[] firstReplaceNodeParents = getParents(getFirstReplacedExpression().getAssociatedNode());
		ASTNode[] commonPath = findDeepestCommonSuperNodePathForReplacedNodes();
		Assert.isTrue(commonPath.length <= firstReplaceNodeParents.length);

		ASTNode deepestCommonParent = firstReplaceNodeParents[commonPath.length - 1];

		if (deepestCommonParent instanceof Block || deepestCommonParent instanceof Program) {
			insertAt(firstReplaceNodeParents[commonPath.length]);
		} else {
			insertAt(deepestCommonParent);
		}
	}

	private void insertAt(ASTNode target) throws BadLocationException {
		ASTRewrite rewrite = fRewriter;
		TextEditGroup groupDescription = new TextEditGroup("dec Variable"); //$NON-NLS-1$

		ASTNode parent = target.getParent();
		StructuralPropertyDescriptor locationInParent = target.getLocationInParent();

		// the new text that will replace the selected expression
		String insertionString = getFullVariableName() + " = " //$NON-NLS-1$
				+ getASTNodeValue(getSelectedExpression().getAssociatedNode()) + ";"; //$NON-NLS-1$

		// + System.getProperty("line.separator") + indentationBuffer;
		// //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		ASTNode declaration = fRewriter.createStringPlaceholder(insertionString, ASTNode.EXPRESSION_STATEMENT);

		while (locationInParent != Block.STATEMENTS_PROPERTY && locationInParent != SwitchStatement.BODY_PROPERTY) {
			if (locationInParent == IfStatement.TRUE_STATEMENT_PROPERTY
					|| locationInParent == IfStatement.FALSE_STATEMENT_PROPERTY
					|| locationInParent == ForStatement.BODY_PROPERTY || locationInParent == DoStatement.BODY_PROPERTY
					|| locationInParent == WhileStatement.BODY_PROPERTY) {
				// create intermediate block if target was the body property of
				// a control statement:
				Block replacement = rewrite.getAST().newBlock();
				ListRewrite replacementRewrite = rewrite.getListRewrite(replacement, Block.STATEMENTS_PROPERTY);

				replacementRewrite.insertFirst(declaration, null);
				replacementRewrite.insertLast(rewrite.createMoveTarget(target), null);
				rewrite.replace(target, replacement, groupDescription);
				return;
			}
			if (parent instanceof Program) {
				break;
			}
			target = parent;
			parent = parent.getParent();
			locationInParent = target.getLocationInParent();
		}
		ListRewrite listRewrite = rewrite.getListRewrite(parent, (ChildListPropertyDescriptor) locationInParent);
		listRewrite.insertBefore(declaration, target, groupDescription);
	}

	/**
	 * Add the needed TextChanges (Insert and Replace) for the extraction
	 * 
	 * @param range
	 * @param selectedExpression
	 * @param change
	 * @throws CoreException
	 * @throws BadLocationException
	 */
	private void insertVariableDeclaration(ASTNode target, ASTNode expr, boolean shouldWrapStatement, SourceRange range)
			throws CoreException, BadLocationException {

		Block block = fAst.newBlock();
		block.setIsCurly(true);
		ListRewrite replacementRewrite = fRewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);

		// replacementRewrite.insertFirst(expr, null);

		ASTNode move = fRewriter.createMoveTarget(target.getParent());

		// if (move.getType() == ASTNode.IF_STATEMENT) {
		// IfStatement ifState = (IfStatement) move;
		// IfStatement orgTarget = (IfStatement) target;
		//
		// Statement trueStatment = orgTarget.getTrueStatement();
		//
		// Scalar condition = (Scalar) orgTarget.getCondition();
		//
		// ASTNode copyCondition = fRewriter.createMoveTarget(condition);
		//
		// ExpressionStatement copyTrue = (ExpressionStatement) fRewriter
		// .createMoveTarget(((ExpressionStatement) trueStatment));
		// copyTrue.setExpression((Expression) fRewriter
		// .createMoveTarget(((ExpressionStatement) trueStatment)
		// .getExpression()));
		//
		// Statement falseStatment = orgTarget.getFalseStatement();
		// ExpressionStatement copyFalse = (ExpressionStatement) fRewriter
		// .createMoveTarget(((ExpressionStatement) falseStatment));
		//
		// copyFalse.setExpression((Expression) fRewriter
		// .createMoveTarget(((ExpressionStatement) falseStatment)
		// .getExpression()));
		// // ConditionalExpression conditionExp =
		// // fAst.newConditionalExpression(
		// // (Expression) copyCondition, (Expression) copyTrue,
		// // (Expression) copyFalse);
		// // condition.setCondition(orgTarget.getCondition());
		// // condition.setIfFalse(orgTarget.get);
		// ifState.setCondition((Expression) copyCondition);
		// ifState.setFalseStatement((Statement) copyFalse);
		// ifState.setTrueStatement((Statement) copyTrue);
		//
		// // ifState.setParent(parent, location)
		// }

		String expressionBuffer = getASTNodeValue(range.getOffset(), range.getLength());
		String insertionString = getFullVariableName() + " = " //$NON-NLS-1$
				+ expressionBuffer + ";"; //$NON-NLS-1$
		// + System.getProperty("line.separator") + indentationBuffer;
		// //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		ASTNode declaration = fRewriter.createStringPlaceholder(insertionString, ASTNode.EXPRESSION_STATEMENT);

		replacementRewrite.insertFirst(declaration, null);

		replacementRewrite.insertLast(move, null);

		TextEditGroup insertDesc = new TextEditGroup(
				PhpRefactoringCoreMessages.getString("ExtractFunctionRefactoring.4")); //$NON-NLS-1$
		textFileChange.addTextEditGroup(insertDesc);

		fRewriter.replace(target.getParent(), block, insertDesc);

		// if (shouldWrapStatement) {
		// insertionString = "{" + System.getProperty("line.separator") +
		// insertionString; //$NON-NLS-1$ //$NON-NLS-2$
		// }

		// insert the new statement
		// InsertEdit insertEdit = new InsertEdit(statementOffset,
		// insertionString);
		// addTextEditChange(insertEdit);

		// replace the existing statement
		// String replacementStr = getFullVariableName();
		// replaceSelectedExpressionWithVariableDeclaration(replacementStr,
		// range,
		// expr);

		// if needed, close the wrapping block
		// if (shouldWrapStatement) {
		// String closeCurlyStr = "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		// insertEdit = new InsertEdit(parentStatement.getEnd(), closeCurlyStr);
		// addTextEditChange(insertEdit);
		// }

	}

	/**
	 * @param offset
	 * @return a string containing the indentation characters.
	 * @throws BadLocationException
	 */
	private String getLineIndentation(int offset) throws BadLocationException {
		IRegion region = document.getLineInformationOfOffset(offset);
		String lineContent = document.get(region.getOffset(), region.getLength());

		StringBuilder buff = new StringBuilder();

		for (int index = 0; index < lineContent.length(); index++) {
			if (!Character.isWhitespace(lineContent.charAt(index))) {
				break;
			}
			buff.append(lineContent.charAt(index));
		}

		return buff.toString();

	}

	private IExpressionFragment getFirstReplacedExpression() {
		if (!fReplaceAllOccurrences)
			return getSelectedExpression();
		IASTFragment[] nodesToReplace = retainOnlyReplacableMatches(false);
		if (nodesToReplace.length == 0) {
			return getSelectedExpression();
		}
		return (IExpressionFragment) nodesToReplace[0];
	}

	private static ASTNode[] getParents(ASTNode node) {
		ASTNode current = node;
		List<ASTNode> parents = new ArrayList<ASTNode>();
		do {
			parents.add(current.getParent());
			current = current.getParent();
		} while (current.getParent() != null);
		Collections.reverse(parents);
		return parents.toArray(new ASTNode[parents.size()]);
	}

	private ASTNode[] findDeepestCommonSuperNodePathForReplacedNodes() {
		ASTNode[] matchNodes = getMatchNodes();

		ASTNode[][] matchingNodesParents = new ASTNode[matchNodes.length][];
		for (int i = 0; i < matchNodes.length; i++) {
			matchingNodesParents[i] = getParents(matchNodes[i]);
		}
		List<Object> l = Arrays.asList(getLongestArrayPrefix(matchingNodesParents));
		return l.toArray(new ASTNode[l.size()]);
	}

	private ASTNode[] getMatchNodes() {
		IASTFragment[] matches = retainOnlyReplacableMatches(false);
		ASTNode[] result = new ASTNode[matches.length];
		for (int i = 0; i < matches.length; i++)
			result[i] = matches[i].getAssociatedNode();
		return result;
	}

	private static Object[] getLongestArrayPrefix(Object[][] arrays) {
		int length = -1;
		if (arrays.length == 0)
			return new Object[0];
		int minArrayLength = arrays[0].length;
		for (int i = 1; i < arrays.length; i++)
			minArrayLength = Math.min(minArrayLength, arrays[i].length);

		for (int i = 0; i < minArrayLength; i++) {
			if (!allArraysEqual(arrays, i))
				break;
			length++;
		}
		if (length == -1)
			return new Object[0];
		return getArrayPrefix(arrays[0], length + 1);
	}

	private static boolean allArraysEqual(Object[][] arrays, int position) {
		Object element = arrays[0][position];
		for (int i = 0; i < arrays.length; i++) {
			Object[] array = arrays[i];
			if (!element.equals(array[position]))
				return false;
		}
		return true;
	}

	private static Object[] getArrayPrefix(Object[] array, int prefixLength) {
		Assert.isTrue(prefixLength <= array.length);
		Assert.isTrue(prefixLength >= 0);
		Object[] prefix = new Object[prefixLength];
		for (int i = 0; i < prefix.length; i++) {
			prefix[i] = array[i];
		}
		return prefix;
	}

	private boolean shouldReplaceSelectedExpressionWithVariableDeclaration() {
		IExpressionFragment selectedFragment = getSelectedExpression();
		return selectedFragment.getAssociatedNode().getParent().getType() == ASTNode.EXPRESSION_STATEMENT
				&& selectedFragment
						.matches(ASTFragmentFactory.createFragmentForFullSubtree(selectedFragment.getAssociatedNode()));
	}

	private void replaceSelectedExpressionWithTempDeclaration(String str) throws CoreException {
		ASTRewrite rewrite = fRewriter;
		Expression selectedExpression = getSelectedExpression().getAssociatedExpression(); // whole
																							// expression
																							// selected

		ASTNode declaration = fRewriter.createStringPlaceholder(str, ASTNode.ASSIGNMENT);

		ExpressionStatement parent = (ExpressionStatement) selectedExpression.getParent();

		final TextEditGroup textEditGroup = new TextEditGroup(CHANGE_DESCRIPTION);

		TextEditChangeGroup textEditChangeGroup = new TextEditChangeGroup(textFileChange, textEditGroup);
		textFileChange.addTextEditChangeGroup(textEditChangeGroup);

		rewrite.replace(parent, declaration, textEditGroup);
	}

	private void replaceSelectedExpressionWithVariableDeclaration(String replacement, int start, int length,
			ASTNode astNode) throws CoreException {
		// create replace change
		ASTNode node = fRewriter.createStringPlaceholder(replacement, ASTNode.ASSIGNMENT);

		final TextEditGroup textEditGroup = new TextEditGroup(CHANGE_DESCRIPTION);

		TextEditChangeGroup textEditChangeGroup = new TextEditChangeGroup(textFileChange, textEditGroup);
		textFileChange.addTextEditChangeGroup(textEditChangeGroup);

		fRewriter.replace(astNode, node, textEditGroup);

	}

	private boolean shouldWrapWithBlock(ASTNode node) {
		if (node.getParent() == null || !(node.getParent() instanceof Statement)) {
			return false;
		}
		// in case the selected expression in the only element of a control
		// statement
		return (ASTNodes.isControlStatement(node.getParent()));

	}

	private Statement getParentStatement(ASTNode node) {
		if (node instanceof Statement)
			return (Statement) node;

		ASTNode parent = node.getParent();
		while (!(parent instanceof Statement)) {
			parent = parent.getParent();
		}
		return (Statement) parent;
	}

	private String getASTNodeValue(ASTNode node) throws BadLocationException {
		return getASTNodeValue(node.getStart(), node.getLength());
	}

	private String getASTNodeValue(int start, int length) throws BadLocationException {
		return document.get(start, length);
	}

	/**
	 * Adds the text change to the root change
	 * 
	 * @param change
	 * @param textEdit
	 */
	private void addTextEditChange(TextEdit textEdit) {
		final TextEditGroup textEditGroup = new TextEditGroup(CHANGE_DESCRIPTION);
		textEditGroup.addTextEdit(textEdit);

		TextEditChangeGroup textEditChangeGroup = new TextEditChangeGroup(textFileChange, textEditGroup);
		textFileChange.addTextEditChangeGroup(textEditChangeGroup);
		textFileChange.addEdit(textEdit);
	}

	private String getFullVariableName() {
		assert (newVariableName != null);
		return "$" + newVariableName; //$NON-NLS-1$
	}

	private IASTFragment[] getMatchingFragments(boolean clean) {
		if (fReplaceAllOccurrences) {
			if (clean || allMatchingFragments == null) {
				allMatchingFragments = ASTFragmentFactory.createFragmentForFullSubtree(getEnclosingBodyNode())
						.getSubFragmentsMatching(getSelectedExpression());
				Comparator<IASTFragment> comparator = new Comparator<IASTFragment>() {

					public int compare(IASTFragment o1, IASTFragment o2) {
						return o1.getStartPosition() - o2.getStartPosition();
					}
				};
				Arrays.sort(allMatchingFragments, comparator);
			}
			return allMatchingFragments;

		} else
			return new IASTFragment[] { getSelectedExpression() };
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
		if (enclosingBodyNode.getType() == ASTNode.FUNCTION_DECLARATION) {
			if (PhpElementConciliator.localVariableAlreadyExists((FunctionDeclaration) enclosingBodyNode, name)) {
				status.addWarning(
						NLS.bind(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.26"), name)); //$NON-NLS-1$
			}
		} else {
			// check if the user given variable name already exists in the
			// global scope
			if (PhpElementConciliator.globalVariableAlreadyExists((Program) astRoot, name)) {
				status.addWarning(
						NLS.bind(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.27"), name)); //$NON-NLS-1$
			}
		}

		return status;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		try {
			pm.beginTask(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.28"), 1); //$NON-NLS-1$

			MultiTextEdit root = new MultiTextEdit();

			rootChange = new CompositeChange(CHANGE_DESCRIPTION);
			rootChange.markAsSynthetic();

			textFileChange = new ProgramDocumentChange(CHANGE_DESCRIPTION, document, astRoot);
			textFileChange.setEdit(root);
			textFileChange.setTextType("php"); //$NON-NLS-1$
			rootChange.add(textFileChange);

			fAst = getSelectedExpression().getAssociatedNode().getAST();
			fRewriter = ASTRewrite.create(fAst);

			try {
				extractVariable();
			} catch (CoreException exception) {
				RefactoringPlugin.logException(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.29"), //$NON-NLS-1$
						exception);
			} catch (BadLocationException e) {
				RefactoringPlugin.logException(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.30"), //$NON-NLS-1$
						e);
			}

			// handle matching occurrences
			if (fReplaceAllOccurrences) {
				replaceOccurances();
			}
			TextEdit edit = fRewriter.rewriteAST(document, null);
			root.addChild(edit);

		} finally {
			pm.done();
		}
		return rootChange;
	}

	@Override
	public String getName() {
		return PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.6"); //$NON-NLS-1$
	}

	public Change getChange() {
		return rootChange;
	}

	public TextChange getTextChange() {
		return textFileChange;
	}

	/**
	 * @return proposed variable names (may be empty, but not null). The first
	 *         proposal should be used as "best guess" (if it exists).
	 */
	public String[] guessTempNames() {
		if (fGuessedTempNames == null) {

			Expression expression = getSelectedExpression().getAssociatedExpression();
			if (expression != null) {
				fGuessedTempNames = RefactoringUtility.getVariableNameSuggestions(expression);

			}

			if (fGuessedTempNames == null || fGuessedTempNames.length == 0) {
				fGuessedTempNames = new String[0];
			} else {
				adjustGuessList(fGuessedTempNames);
			}
		}
		return fGuessedTempNames;
	}

	/**
	 * Go over the list of suggestions and adjust the names to the existing
	 * variable names
	 * 
	 * @param guessedTempNames
	 */
	private void adjustGuessList(String[] guessedTempNames) {

		for (int i = 0; i < guessedTempNames.length; i++) {
			int idx = 2;
			String suggestionStr = guessedTempNames[i];
			while (doesNameAlreadyExist(guessedTempNames[i]).getSeverity() != IStatus.OK) {
				guessedTempNames[i] = suggestionStr + idx;
				idx++;
			}
		}
	}

	/**
	 * Validates the new variable name
	 * 
	 * @param text
	 * @return
	 */
	public RefactoringStatus checkNewVariableName(String text) {
		RefactoringStatus status = new RefactoringStatus();
		status.merge(RefactoringUtility.checkNewElementName(text));
		status.merge(doesNameAlreadyExist(text));
		return status;
	}

}
