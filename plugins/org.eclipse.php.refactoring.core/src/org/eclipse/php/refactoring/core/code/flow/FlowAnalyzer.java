/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.code.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.ApplyAll;

/**
 * Special flow analyzer to determine the return value of the extracted method
 * and the variables which have to be passed to the method.
 * 
 * Note: This analyzer doesn't do a full flow analysis. For example it doesn't
 * do dead code analysis or variable initialization analysis. It analyses the
 * the first access to a variable (read or write) and if all execution paths
 * return a value.
 */
abstract class FlowAnalyzer extends ApplyAll {

	static protected class SwitchData {
		private boolean fHasDefaultCase;
		private List<IRegion> fRanges = new ArrayList<IRegion>(4);
		private List<FlowInfo> fInfos = new ArrayList<FlowInfo>(4);

		public void setHasDefaultCase() {
			fHasDefaultCase = true;
		}

		public boolean hasDefaultCase() {
			return fHasDefaultCase;
		}

		public void add(IRegion range, FlowInfo info) {
			fRanges.add(range);
			fInfos.add(info);
		}

		public IRegion[] getRanges() {
			return (IRegion[]) fRanges.toArray(new IRegion[fRanges.size()]);
		}

		public FlowInfo[] getInfos() {
			return (FlowInfo[]) fInfos.toArray(new FlowInfo[fInfos.size()]);
		}

		public FlowInfo getInfo(int index) {
			return (FlowInfo) fInfos.get(index);
		}
	}

	private HashMap<ASTNode, FlowInfo> fData = new HashMap<ASTNode, FlowInfo>(100);
	/* package */FlowContext fFlowContext = null;

	public FlowAnalyzer(FlowContext context) {
		fFlowContext = context;
	}

	protected abstract boolean createReturnFlowInfo(ReturnStatement node);

	protected abstract boolean traverseNode(ASTNode node);

	protected boolean skipNode(ASTNode node) {
		return !traverseNode(node);
	}

	protected final boolean apply(ASTNode node) {
		return traverseNode(node);
	}

	// ---- Hooks to create Flow info objects. User may introduce their own
	// infos.

	protected ReturnFlowInfo createReturn(ReturnStatement statement) {
		return new ReturnFlowInfo(statement);
	}

	protected ThrowFlowInfo createThrow() {
		return new ThrowFlowInfo();
	}

	protected BranchFlowInfo createBranch(Identifier label) {
		return new BranchFlowInfo(label, fFlowContext);
	}

	protected GenericSequentialFlowInfo createSequential() {
		return new GenericSequentialFlowInfo();
	}

	protected ConditionalFlowInfo createConditional() {
		return new ConditionalFlowInfo();
	}

	protected EnhancedForFlowInfo createEnhancedFor() {
		return new EnhancedForFlowInfo();
	}

	protected ForFlowInfo createFor() {
		return new ForFlowInfo();
	}

	protected TryFlowInfo createTry() {
		return new TryFlowInfo();
	}

	protected WhileFlowInfo createWhile() {
		return new WhileFlowInfo();
	}

	protected IfFlowInfo createIf() {
		return new IfFlowInfo();
	}

	protected DoWhileFlowInfo createDoWhile() {
		return new DoWhileFlowInfo();
	}

	protected SwitchFlowInfo createSwitch() {
		return new SwitchFlowInfo();
	}

	protected BlockFlowInfo createBlock() {
		return new BlockFlowInfo();
	}

	protected MessageSendFlowInfo createMessageSendFlowInfo() {
		return new MessageSendFlowInfo();
	}

	protected FlowContext getFlowContext() {
		return fFlowContext;
	}

	// ---- Helpers to access flow analysis objects
	// ----------------------------------------

	protected FlowInfo getFlowInfo(ASTNode node) {
		return (FlowInfo) fData.remove(node);
	}

	protected void setFlowInfo(ASTNode node, FlowInfo info) {
		fData.put(node, info);
	}

	protected FlowInfo assignFlowInfo(ASTNode target, ASTNode source) {
		FlowInfo result = getFlowInfo(source);
		setFlowInfo(target, result);
		return result;
	}

	protected FlowInfo accessFlowInfo(ASTNode node) {
		return (FlowInfo) fData.get(node);
	}

	// ---- Helpers to process sequential flow infos
	// -------------------------------------

	protected GenericSequentialFlowInfo processSequential(ASTNode parent, List<? extends ASTNode> nodes) {
		GenericSequentialFlowInfo result = createSequential(parent);
		process(result, nodes);
		return result;
	}

	protected GenericSequentialFlowInfo processSequential(ASTNode parent, ASTNode node1) {
		GenericSequentialFlowInfo result = createSequential(parent);
		if (node1 != null)
			result.merge(getFlowInfo(node1), fFlowContext);
		return result;
	}

	protected GenericSequentialFlowInfo processSequential(ASTNode parent, ASTNode node1, ASTNode node2) {
		GenericSequentialFlowInfo result = createSequential(parent);
		if (node1 != null)
			result.merge(getFlowInfo(node1), fFlowContext);
		if (node2 != null)
			result.merge(getFlowInfo(node2), fFlowContext);
		return result;
	}

	protected GenericSequentialFlowInfo createSequential(ASTNode parent) {
		GenericSequentialFlowInfo result = createSequential();
		setFlowInfo(parent, result);
		return result;
	}

	protected GenericSequentialFlowInfo createSequential(List<? extends ASTNode> nodes) {
		GenericSequentialFlowInfo result = createSequential();
		process(result, nodes);
		return result;
	}

	// ---- Generic merge methods
	// --------------------------------------------------------

	protected void process(GenericSequentialFlowInfo info, List<? extends ASTNode> nodes) {
		if (nodes == null)
			return;
		for (Iterator<? extends ASTNode> iter = nodes.iterator(); iter.hasNext();) {
			info.merge(getFlowInfo(iter.next()), fFlowContext);
		}
	}

	protected void process(GenericSequentialFlowInfo info, ASTNode node) {
		if (node != null)
			info.merge(getFlowInfo(node), fFlowContext);
	}

	protected void process(GenericSequentialFlowInfo info, ASTNode node1, ASTNode node2) {
		if (node1 != null)
			info.merge(getFlowInfo(node1), fFlowContext);
		if (node2 != null)
			info.merge(getFlowInfo(node2), fFlowContext);
	}

	// ---- special visit methods
	// -------------------------------------------------------

	public boolean visit(EmptyStatement node) {
		// Empty statements aren't of any interest.
		return false;
	}

	public boolean visit(TryStatement node) {
		if (traverseNode(node)) {
			fFlowContext.pushExcptions(node);
			node.getBody().accept(this);
			fFlowContext.popExceptions();
			List<CatchClause> catchClauses = node.catchClauses();
			for (CatchClause catchClause : catchClauses) {
				catchClause.accept(this);
			}
		}
		return false;
	}

	// ---- Helper to process switch statement
	// ----------------------------------------

	protected SwitchData createSwitchData(SwitchStatement node) {
		SwitchData result = new SwitchData();
		List<Statement> statements = node.getBody().statements();
		if (statements.isEmpty())
			return result;

		int start = -1, end = -1;
		GenericSequentialFlowInfo info = null;

		for (Iterator<Statement> iter = statements.iterator(); iter.hasNext();) {
			Statement statement = (Statement) iter.next();
			if (statement instanceof SwitchCase) {
				SwitchCase switchCase = (SwitchCase) statement;
				if (switchCase.isDefault()) {
					result.setHasDefaultCase();
				}
				if (info == null) {
					info = createSequential();
					start = statement.getStart();
				} else {
					if (info.isReturn() || info.isPartialReturn() || info.branches()) {
						result.add(new Region(start, end - start + 1), info);
						info = createSequential();
						start = statement.getStart();
					}
				}
			} else if (info != null) {
				info.merge(getFlowInfo(statement), fFlowContext);
			}
			end = statement.getEnd() - 1;
		}
		result.add(new Region(start, end - start + 1), info);
		return result;
	}

	// ---- concrete endVisit methods
	// ---------------------------------------------------

	// TODO - when should we call "info.setNoReturn();" ?

	public void endVisit(ArrayAccess node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getName(), node.getIndex());
	}

	public void endVisit(ArrayCreation node) {
		if (skipNode(node))
			return;
		processSequential(node, node.elements());
	}

	public void endVisit(ArrayElement node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getKey(), node.getValue());
	}

	public void endVisit(Assignment node) {
		if (skipNode(node))
			return;
		FlowInfo lhs = getFlowInfo(node.getLeftHandSide());
		FlowInfo rhs = getFlowInfo(node.getRightHandSide());
		if (lhs instanceof LocalFlowInfo) {
			LocalFlowInfo llhs = (LocalFlowInfo) lhs;
			llhs.setWriteAccess(fFlowContext);
			if (node.getOperator() != Assignment.OP_EQUAL) {
				GenericSequentialFlowInfo tmp = createSequential();
				tmp.merge(new LocalFlowInfo(llhs, FlowInfo.READ, fFlowContext), fFlowContext);
				tmp.merge(rhs, fFlowContext);
				rhs = tmp;
			}
		}
		GenericSequentialFlowInfo info = createSequential(node);
		// first process right and side and then left hand side.
		info.merge(rhs, fFlowContext);
		info.merge(lhs, fFlowContext);
	}

	public void endVisit(BackTickExpression node) {
		if (skipNode(node))
			return;

		processSequential(node, node.expressions());
	}

	public void endVisit(Block node) {
		if (skipNode(node))
			return;
		BlockFlowInfo info = createBlock();
		setFlowInfo(node, info);
		process(info, node.statements());
	}

	public void endVisit(BreakStatement node) {
		if (skipNode(node))
			return;
		// TODO - what about int value?
		// setFlowInfo(node, createBranch(node.getLabel()));
		processSequential(node, node.getExpression());
	}

	public void endVisit(CastExpression node) {
		if (skipNode(node))
			return;

		processSequential(node, node.getExpression());
	}

	public void endVisit(CatchClause node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getVariable(), node.getBody());
	}

	public void endVisit(ConstantDeclaration node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.names());
		process(info, node.initializers());

	}

	public void endVisit(ClassDeclaration node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getName());
		process(info, node.getSuperClass());
		process(info, node.interfaces());
		process(info, node.getBody());

	}

	public void endVisit(ClassInstanceCreation node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getClassName());
		process(info, node.ctorParams());
	}

	public void endVisit(ClassName node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getName());
	}

	public void endVisit(CloneExpression node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getExpression());
	}

	public void endVisit(Comment node) {
		// nothing to do
	}

	public void endVisit(ConditionalExpression node) {
		if (skipNode(node))
			return;
		ConditionalFlowInfo info = createConditional();
		setFlowInfo(node, info);
		info.mergeCondition(getFlowInfo(node.getCondition()), fFlowContext);
		info.merge(getFlowInfo(node.getIfTrue()), getFlowInfo(node.getIfFalse()), fFlowContext);
	}

	public void endVisit(ContinueStatement node) {
		if (skipNode(node))
			return;
		// TODO - what about int value?
		// setFlowInfo(node, createBranch(node.getLabel()));
		processSequential(node, node.getExpression());
	}

	public void endVisit(DeclareStatement node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.directiveNames());
		process(info, node.directiveValues());
		process(info, node.getBody());
	}

	public void endVisit(DoStatement node) {
		if (skipNode(node))
			return;
		DoWhileFlowInfo info = createDoWhile();
		setFlowInfo(node, info);
		info.mergeAction(getFlowInfo(node.getBody()), fFlowContext);
		info.mergeCondition(getFlowInfo(node.getCondition()), fFlowContext);
		info.removeLabel(null);
	}

	public void endVisit(EchoStatement node) {
		if (skipNode(node))
			return;
		processSequential(node, node.expressions());
	}

	public void endVisit(EmptyStatement node) {
		// Leaf node.
	}

	public void endVisit(ExpressionStatement node) {
		if (skipNode(node))
			return;
		assignFlowInfo(node, node.getExpression());
	}

	public void endVisit(FieldAccess node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getField(), node.getField().getName());
	}

	public void endVisit(FieldsDeclaration node) {
		if (skipNode(node))
			return;
		processSequential(node, node.fields());
	}

	// TODO - ensure the right order of the merge
	public void endVisit(ForEachStatement node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getExpression());
		process(info, node.getKey());
		process(info, node.getValue());
		process(info, node.getStatement());

	}

	public void endVisit(FormalParameter node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getParameterType());
		process(info, node.getParameterName());
		process(info, node.getDefaultValue());
	}

	public void endVisit(ForStatement node) {
		if (skipNode(node))
			return;
		ForFlowInfo forInfo = createFor();
		setFlowInfo(node, forInfo);
		forInfo.mergeInitializer(createSequential(node.initializers()), fFlowContext);
		forInfo.mergeCondition(createSequential(node.conditions()), fFlowContext);
		forInfo.mergeAction(getFlowInfo(node.getBody()), fFlowContext);
		// Increments are executed after the action.
		forInfo.mergeIncrement(createSequential(node.updaters()), fFlowContext);
		forInfo.removeLabel(null);
	}

	public void endVisit(FunctionDeclaration node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.formalParameters());
		process(info, node.getBody());
	}

	public void endVisit(FunctionInvocation node) {
		endVisitFunctionInvocation(node, node.parameters(), getMethodBinding(node));
	}

	public void endVisit(FunctionName node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getName());
	}

	public void endVisit(GlobalStatement node) {
		if (skipNode(node))
			return;
		processSequential(node, node.variables());
	}

	public void endVisit(Identifier node) {

	}

	public void endVisit(IfStatement node) {
		if (skipNode(node))
			return;
		IfFlowInfo info = createIf();
		setFlowInfo(node, info);
		info.mergeCondition(getFlowInfo(node.getCondition()), fFlowContext);
		info.merge(getFlowInfo(node.getTrueStatement()), getFlowInfo(node.getFalseStatement()), fFlowContext);
	}

	public void endVisit(Include node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getExpression());
	}

	public void endVisit(InfixExpression node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getLeft(), node.getRight());
	}

	public void endVisit(InLineHtml node) {
		// nothing to do
	}

	public void endVisit(InterfaceDeclaration node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getName());
		process(info, node.interfaces());
		process(info, node.getBody());
	}

	public void endVisit(InstanceOfExpression node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getExpression(), node.getClassName());
	}

	public void endVisit(MethodDeclaration node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getFunction().formalParameters());
		// process(info, node.parameters());
		process(info, node.getFunction().getBody());
	}

	public void endVisit(MethodInvocation node) {
		endVisitMethodInvocation(node, node.getDispatcher(), node.getMethod().parameters(),
				getMethodBinding(node.getMethod()));
	}

	public void endVisit(ParenthesisExpression node) {
		if (skipNode(node))
			return;
		assignFlowInfo(node, node.getExpression());
	}

	public void endVisit(PostfixExpression node) {
		endVisitIncDecOperation(node, node.getVariable());
	}

	public void endVisit(PrefixExpression node) {
		endVisitIncDecOperation(node, node.getVariable());
	}

	public void endVisit(Program node) {
		if (skipNode(node))
			return;
		processSequential(node, node.statements());
	}

	public void endVisit(Quote node) {
		if (skipNode(node))
			return;
		processSequential(node, node.expressions());
	}

	public void endVisit(Reference node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getExpression());
	}

	public void endVisit(ReflectionVariable node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getName());
	}

	public void endVisit(ReturnStatement node) {
		if (skipNode(node))
			return;

		if (createReturnFlowInfo(node)) {
			ReturnFlowInfo info = createReturn(node);
			setFlowInfo(node, info);
			info.merge(getFlowInfo(node.getExpression()), fFlowContext);
		} else {
			assignFlowInfo(node, node.getExpression());
		}
	}

	public void endVisit(Scalar node) {
		// nothing to do
	}

	public void endVisit(SingleFieldDeclaration node) {
		if (skipNode(node))
			return;
		processSequential(node, node.getName());
	}

	public void endVisit(StaticConstantAccess node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getClassName());
		process(info, node.getConstant());
	}

	public void endVisit(StaticFieldAccess node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getClassName());
		process(info, node.getField());
	}

	public void endVisit(StaticMethodInvocation node) {
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getClassName());
		process(info, node.getMethod());
	}

	public void endVisit(StaticStatement node) {
		if (skipNode(node))
			return;
		processSequential(node, node.expressions());
	}

	public void endVisit(SwitchCase node) {
		// TODO ???
		// endVisitNode(node);
		if (skipNode(node))
			return;
		GenericSequentialFlowInfo info = processSequential(node, node.getValue());
		process(info, node.actions());
	}

	public void endVisit(SwitchStatement node) {
		if (skipNode(node))
			return;
		endVisit(node, createSwitchData(node));
	}

	protected void endVisit(SwitchStatement node, SwitchData data) {
		SwitchFlowInfo switchFlowInfo = createSwitch();
		setFlowInfo(node, switchFlowInfo);
		switchFlowInfo.mergeTest(getFlowInfo(node.getExpression()), fFlowContext);
		FlowInfo[] cases = data.getInfos();
		for (int i = 0; i < cases.length; i++)
			switchFlowInfo.mergeCase(cases[i], fFlowContext);
		switchFlowInfo.mergeDefault(data.hasDefaultCase(), fFlowContext);
		switchFlowInfo.removeLabel(null);
	}

	public void endVisit(ThrowStatement node) {
		if (skipNode(node))
			return;
		ThrowFlowInfo info = createThrow();
		setFlowInfo(node, info);
		Expression expression = node.getExpression();
		info.merge(getFlowInfo(expression), fFlowContext);
		info.mergeException(expression.resolveTypeBinding(), fFlowContext);
	}

	public void endVisit(TryStatement node) {
		if (skipNode(node))
			return;
		TryFlowInfo info = createTry();
		setFlowInfo(node, info);
		info.mergeTry(getFlowInfo(node.getBody()), fFlowContext);
		info.removeExceptions(node);
		List<CatchClause> catchClauses = node.catchClauses();
		for (CatchClause catchClause : catchClauses) {
			info.mergeCatch(getFlowInfo(catchClause), fFlowContext);
		}
	}

	public void endVisit(UnaryOperation node) {
		assignFlowInfo(node, node.getExpression());
	}

	public void endVisit(Variable node) {
		if (skipNode(node))
			return;

		IVariableBinding binding = node.resolveVariableBinding();
		if (binding != null && !binding.isField()) {
			setFlowInfo(node, new LocalFlowInfo(binding, FlowInfo.READ, fFlowContext));
		}
	}

	public void endVisit(WhileStatement node) {
		if (skipNode(node))
			return;
		WhileFlowInfo info = createWhile();
		setFlowInfo(node, info);
		info.mergeCondition(getFlowInfo(node.getCondition()), fFlowContext);
		info.mergeAction(getFlowInfo(node.getBody()), fFlowContext);
		info.removeLabel(null);
	}

	// TODO - do we need this code? do we need the binding?
	private void endVisitMethodInvocation(ASTNode node, ASTNode receiver, List<Expression> arguments,
			IFunctionBinding binding) {
		if (skipNode(node))
			return;
		MessageSendFlowInfo info = createMessageSendFlowInfo();
		setFlowInfo(node, info);
		for (Iterator<Expression> iter = arguments.iterator(); iter.hasNext();) {
			Expression arg = iter.next();
			info.mergeArgument(getFlowInfo(arg), fFlowContext);
		}
		info.mergeReceiver(getFlowInfo(receiver), fFlowContext);
		// info.mergeExceptions(binding, fFlowContext);
	}

	// TODO - do we need this code? do we need the binding?
	private void endVisitFunctionInvocation(ASTNode node, List<Expression> arguments, IFunctionBinding binding) {
		if (skipNode(node))
			return;
		MessageSendFlowInfo info = createMessageSendFlowInfo();
		setFlowInfo(node, info);
		for (Iterator<Expression> iter = arguments.iterator(); iter.hasNext();) {
			Expression arg = (Expression) iter.next();
			info.mergeArgument(getFlowInfo(arg), fFlowContext);
		}
		// info.mergeExceptions(binding, fFlowContext);
	}

	private void endVisitIncDecOperation(Expression node, Expression operand) {
		if (skipNode(node))
			return;
		FlowInfo info = getFlowInfo(operand);
		if (info instanceof LocalFlowInfo) {
			// Normally we should do this in the parent node since the write
			// access take place later.
			// But I couldn't come up with a case where this influences the flow
			// analysis. So I kept
			// it here to simplify the code.
			GenericSequentialFlowInfo result = createSequential(node);
			result.merge(info, fFlowContext);
			result.merge(new LocalFlowInfo((LocalFlowInfo) info, FlowInfo.WRITE, fFlowContext), fFlowContext);
		} else {
			setFlowInfo(node, info);
		}
	}

	private IFunctionBinding getMethodBinding(FunctionInvocation function) {
		// TODO - check what is the final purpose of calling this method
		if (function == null)
			return null;
		IBinding binding = function.resolveFunctionBinding();
		if (binding instanceof IFunctionBinding)
			return (IMethodBinding) binding;
		return null;
	}
}
