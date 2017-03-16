/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.corext.dom.LocalVariableIndex;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.code.flow.FlowContext;
import org.eclipse.php.refactoring.core.code.flow.FlowInfo;
import org.eclipse.php.refactoring.core.code.flow.InOutFlowAnalyzer;
import org.eclipse.php.refactoring.core.code.flow.InputFlowAnalyzer;
import org.eclipse.php.refactoring.core.visitor.CodeAnalyzer;
import org.eclipse.php.refactoring.core.visitor.ScopeSyntaxErrorsVisitor;

/* package */class ExtractFunctionAnalyzer extends CodeAnalyzer {

	public static final int ERROR = -2;
	public static final int UNDEFINED = -1;
	public static final int NO = 0;
	public static final int EXPRESSION = 1;
	public static final int ACCESS_TO_LOCAL = 2;
	public static final int RETURN_STATEMENT_VOID = 3;
	public static final int RETURN_STATEMENT_VALUE = 4;
	public static final int MULTIPLE = 5;

	private ASTNode fEnclosingBodyDeclaration;
	private FlowInfo fInputFlowInfo;
	private FlowContext fInputFlowContext;
	private int fMaxVariableId;
	// private IVariableBinding[] fMethodLocals;
	private int fReturnKind;
	private IFunctionBinding fEnclosingMethodBinding;
	private boolean fIsLastStatementSelected;
	private IVariableBinding fReturnValue;
	private IVariableBinding[] fArguments;
	// private Object fTypeVariables;

	public ExtractFunctionAnalyzer(Program cunit, ISourceModule sourceModule, IDocument document, Selection selection)
			throws CoreException, IOException {
		super(cunit, sourceModule, document, selection, false);
	}

	// ---- Activation checking
	// ---------------------------------------------------------------------------

	public RefactoringStatus checkInitialConditions() {
		RefactoringStatus result = getStatus();
		checkExpression(result);
		if (result.hasFatalError())
			return result;

		fReturnKind = UNDEFINED;

		fMaxVariableId = LocalVariableIndex.perform(getEnclosingBodyDeclaration());
		if (analyzeSelection(result).hasFatalError())
			return result;

		int returns = fReturnKind == NO ? 0 : 1;
		if (fReturnValue != null) {
			fReturnKind = ACCESS_TO_LOCAL;
			returns++;
		}
		if (isExpressionSelected()) {
			fReturnKind = EXPRESSION;
			returns++;
		}

		if (returns > 1) {
			// result.addFatalError(RefactoringCoreMessages.ExtractMethodAnalyzer_ambiguous_return_value,
			// JavaStatusContext.create(fCUnit, getSelection()));
			fReturnKind = MULTIPLE;
			return result;
		}
		// initReturnType(rewriter);
		return result;
	}

	/**
	 * Gets the current selection
	 * 
	 * @param astRoot
	 * @return
	 */
	RefactoringStatus checkSelection(RefactoringStatus status, IProgressMonitor pm) {

		try {
			pm.beginTask("", 8); //$NON-NLS-1$

			ASTNode[] selectedNodes = getSelectedNodes();

			if (selectedNodes == null || selectedNodes.length == 0) {
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.2")); //$NON-NLS-1$
			}
			pm.worked(1);

			ASTNode enclosingBodyNode = getFirstSelectedNode().getEnclosingBodyNode();
			if (enclosingBodyNode == null)
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages.getString("ExtractVariableRefactoring.3")); //$NON-NLS-1$
			pm.worked(1);

			if (scopeHasSyntaxErrors(enclosingBodyNode)) {
				return RefactoringStatus
						.createFatalErrorStatus("Unable to activate refactoring. Please fix syntax errors"); //$NON-NLS-1$
			}
			pm.worked(1);

			checkExpression(status);

			pm.worked(1);

			return status;

		} finally {
			pm.done();
		}
	}

	private RefactoringStatus analyzeSelection(RefactoringStatus status) {
		fInputFlowContext = new FlowContext(0, fMaxVariableId + 1);
		fInputFlowContext.setConsiderAccessMode(true);
		fInputFlowContext.setComputeMode(FlowContext.ARGUMENTS);

		InOutFlowAnalyzer flowAnalyzer = new InOutFlowAnalyzer(fInputFlowContext);
		fInputFlowInfo = flowAnalyzer.perform(getSelectedNodes());

		if (fInputFlowInfo.branches()) {
			status.addFatalError(PhpRefactoringCoreMessages.getString("ExtractFunctionAnalyzer.0")); //$NON-NLS-1$
			fReturnKind = ERROR;
			return status;
		}
		if (fInputFlowInfo.isValueReturn()) {
			fReturnKind = RETURN_STATEMENT_VALUE;
		} else if (fInputFlowInfo.isVoidReturn()
				|| (fInputFlowInfo.isPartialReturn() && isVoidMethod() && isLastStatementSelected())) {
			fReturnKind = RETURN_STATEMENT_VOID;
		} else if (fInputFlowInfo.isNoReturn() || fInputFlowInfo.isThrow() || fInputFlowInfo.isUndefined()) {
			fReturnKind = NO;
		}
		//
		// if (fReturnKind == UNDEFINED) {
		// status.addError(RefactoringCoreMessages.FlowAnalyzer_execution_flow,
		// JavaStatusContext.create(fCUnit, getSelection()));
		// fReturnKind= NO;
		// }
		computeInput();
		// computeExceptions();
		computeOutput(status);
		// if (status.hasFatalError())
		// return status;
		//
		// adjustArgumentsAndMethodLocals();
		// compressArrays();
		return status;
	}

	private void computeOutput(RefactoringStatus status) {
		// First find all writes inside the selection.
		FlowContext flowContext = new FlowContext(0, fMaxVariableId + 1);
		flowContext.setConsiderAccessMode(true);
		flowContext.setComputeMode(FlowContext.RETURN_VALUES);
		FlowInfo returnInfo = new InOutFlowAnalyzer(flowContext).perform(getSelectedNodes());
		IVariableBinding[] returnValues = returnInfo.get(flowContext,
				FlowInfo.WRITE | FlowInfo.WRITE_POTENTIAL | FlowInfo.UNKNOWN);

		// Compute a selection that exactly covers the selected nodes
		IRegion region = getSelectedNodeRange();
		Selection selection = Selection.createFromStartLength(region.getOffset(), region.getLength());

		int counter = 0;
		flowContext.setComputeMode(FlowContext.ARGUMENTS);

		FlowInfo argInfo = null;
		if (fEnclosingBodyDeclaration instanceof MethodDeclaration) {
			argInfo = new InputFlowAnalyzer(flowContext, selection, true)
					.perform(((MethodDeclaration) fEnclosingBodyDeclaration).getFunction());
		} else if (fEnclosingBodyDeclaration instanceof FunctionDeclaration) {
			argInfo = new InputFlowAnalyzer(flowContext, selection, true).perform(fEnclosingBodyDeclaration);
		} else {
			argInfo = new InputFlowAnalyzer(flowContext, selection, true).perform(fEnclosingBodyDeclaration);
		}

		IVariableBinding[] reads = argInfo.get(flowContext, FlowInfo.READ | FlowInfo.READ_POTENTIAL | FlowInfo.UNKNOWN);
		outer: for (int i = 0; i < returnValues.length && counter <= 1; i++) {
			IVariableBinding binding = returnValues[i];
			for (int x = 0; x < reads.length; x++) {
				if (reads[x].equals(binding)) {
					counter++;
					fReturnValue = binding;
					continue outer;
				}
			}
		}
		switch (counter) {
		case 0:
			fReturnValue = null;
			break;
		case 1:
			break;
		default:
			fReturnValue = null;
			status.addFatalError(PhpRefactoringCoreMessages.getString("ExtractFunctionAnalyzer.1")); //$NON-NLS-1$
			return;
		}
		// List callerLocals= new ArrayList(5);
		// FlowInfo localInfo= new InputFlowAnalyzer(flowContext, selection,
		// false).perform(fEnclosingBodyDeclaration);
		// IVariableBinding[] writes= localInfo.get(flowContext, FlowInfo.WRITE
		// | FlowInfo.WRITE_POTENTIAL | FlowInfo.UNKNOWN);
		// for (int i= 0; i < writes.length; i++) {
		// IVariableBinding write= writes[i];
		// if (getSelection().covers(ASTNodes.findDeclaration(write,
		// fEnclosingBodyDeclaration)))
		// callerLocals.add(write);
		// }
		// fCallerLocals= (IVariableBinding[])callerLocals.toArray(new
		// IVariableBinding[callerLocals.size()]);
		// if (fReturnValue != null &&
		// getSelection().covers(ASTNodes.findDeclaration(fReturnValue,
		// fEnclosingBodyDeclaration)))
		// fReturnLocal= fReturnValue;
	}

	private boolean isVoidMethod() {
		// if we have an initializer
		if (fEnclosingMethodBinding == null)
			return true;
		ITypeBinding[] binding = fEnclosingMethodBinding.getReturnType();
		for (ITypeBinding currentBinding : binding) {
			if (fEnclosingBodyDeclaration.getAST().resolveWellKnownType("void").equals(currentBinding)) //$NON-NLS-1$
				return true;
		}
		return false;
	}

	public boolean isLastStatementSelected() {
		return fIsLastStatementSelected;
	}

	private void checkExpression(RefactoringStatus status) {
		ASTNode[] nodes = getSelectedNodes();
		if (nodes != null && nodes.length == 1) {
			ASTNode node = nodes[0];
			if (!(node instanceof ExpressionStatement) && !(node instanceof EchoStatement)
					&& !(node instanceof InfixExpression) && !(node instanceof ForStatement)
					&& !(node instanceof DoStatement) && !(node instanceof ForEachStatement)
					&& !(node instanceof IfStatement) && !(node instanceof SwitchStatement)
					&& !(node instanceof TryStatement) && !(node instanceof WhileStatement)) {
				status.addFatalError(PhpRefactoringCoreMessages.getString("ExtractFunctionAnalyzer.2")); //$NON-NLS-1$
			}
		}

		if (nodes == null || nodes.length == 0) {
			status.addFatalError(PhpRefactoringCoreMessages.getString("ExtractFunctionAnalyzer.3")); //$NON-NLS-1$
		}

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

	public ASTNode getEnclosingBodyDeclaration() {
		// Class method case
		fEnclosingBodyDeclaration = (BodyDeclaration) ASTNodes.getParent(getFirstSelectedNode(), BodyDeclaration.class);

		// Global Function case
		if (fEnclosingBodyDeclaration == null) {
			fEnclosingBodyDeclaration = (Statement) ASTNodes.getParent(getFirstSelectedNode(),
					FunctionDeclaration.class);
		}

		// Global scope case
		if (fEnclosingBodyDeclaration == null) {
			fEnclosingBodyDeclaration = ASTNodes.getParent(getFirstSelectedNode(), Program.class);
		}

		return fEnclosingBodyDeclaration;
	}

	private void computeInput() {
		int argumentMode = FlowInfo.READ | FlowInfo.READ_POTENTIAL | FlowInfo.WRITE_POTENTIAL | FlowInfo.UNKNOWN;
		fArguments = removeSelectedDeclarations(fInputFlowInfo.get(fInputFlowContext, argumentMode));
		// IVariableBinding[] bindings = fInputFlowInfo.get(fInputFlowContext,
		// FlowInfo.WRITE | FlowInfo.WRITE_POTENTIAL);
		// fTypeVariables =
		// computeTypeVariables(fInputFlowInfo.getTypeVariables());
	}

	// private ITypeBinding[] computeTypeVariables(ITypeBinding[] bindings) {
	// Selection selection = getSelection();
	// Set result = new HashSet();
	// // first remove all type variables that come from outside of the method
	// // or are covered by the selection
	// // Program compilationUnit= (Program)fEnclosingBodyDeclaration.getRoot();
	// for (int i = 0; i < bindings.length; i++) {
	// ASTNode decl = ((VariableBinding) bindings[i]).getVarialbe();
	// if (decl == null || (!selection.covers(decl) && decl.getParent()
	// instanceof MethodDeclaration))
	// result.add(bindings[i]);
	// }
	// // all all type variables which are needed since a local variable uses it
	// for (int i = 0; i < fArguments.length; i++) {
	// IVariableBinding arg = fArguments[i];
	// ITypeBinding type = arg.getType();
	// if (type != null && type.isTypeVariable()) {
	// ASTNode decl = ((VariableBinding) bindings[i]).getVarialbe();
	// if (decl == null || (!selection.covers(decl) && decl.getParent()
	// instanceof MethodDeclaration))
	// result.add(type);
	// }
	// }
	// return (ITypeBinding[]) result.toArray(new ITypeBinding[result.size()]);
	// }

	public IVariableBinding getReturnValue() {
		return fReturnValue;
	}

	private void computeLastStatementSelected() {
		ASTNode[] nodes = getSelectedNodes();
		if (nodes.length == 0) {
			fIsLastStatementSelected = false;
		} else {
			Block body = null;
			if (fEnclosingBodyDeclaration instanceof FunctionDeclaration) {
				body = ((FunctionDeclaration) fEnclosingBodyDeclaration).getBody();
			}
			if (fEnclosingBodyDeclaration instanceof MethodDeclaration) {
				body = ((MethodDeclaration) fEnclosingBodyDeclaration).getFunction().getBody();
			}

			if (body != null) {
				List<Statement> statements = body.statements();
				fIsLastStatementSelected = nodes[nodes.length - 1] == statements.get(statements.size() - 1);
			}
		}
	}

	@Override
	public void endVisit(Program program) {
		computeLastStatementSelected();
		super.endVisit(program);
	}

	public int getReturnKind() {
		return fReturnKind;
	}

	public IVariableBinding[] getArguments() {
		return fArguments;
	}

	private IVariableBinding[] removeSelectedDeclarations(IVariableBinding[] bindings) {
		List<IVariableBinding> result = new ArrayList<IVariableBinding>(bindings.length);
		Selection selection = getSelection();

		for (int i = 0; i < bindings.length; i++) {
			if (!selection.covers(((VariableBinding) bindings[i]).getVarialbe()))
				;
			result.add(bindings[i]);
		}
		return result.toArray(new IVariableBinding[result.size()]);
	}

}
