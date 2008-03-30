package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.internal.core.SourceRefElement;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPFieldDeclaration;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.wst.xml.core.internal.Logger;

/**
 * This evaluator finds class field declartion either using "var" or in method body using field access.
 */
public class ClassVariableDeclarationEvaluator extends AbstractPHPGoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public ClassVariableDeclarationEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ClassVariableDeclarationGoal typedGoal = (ClassVariableDeclarationGoal) goal;
		InstanceContext context = (InstanceContext) typedGoal.getContext();

		final List<IGoal> subGoals = new LinkedList<IGoal>();

		IType[] types = getTypes(context.getInstanceType(), context.getSourceModule());

		String variableName = typedGoal.getVariableName();
		SearchEngine searchEngine = new SearchEngine();
		SearchParticipant[] participants = new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() };

		for (IType type : types) {
			final ISourceModule sourceModule = type.getSourceModule();
			final ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);

			SearchRequestor requestor = new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					Object element = match.getElement();
					if (element instanceof SourceRefElement) {
						SourceRefElement sourceRefElement = (SourceRefElement) element;
						ISourceRange sourceRange = sourceRefElement.getSourceRange();
						ClassDeclarationSearcher searcher = new ClassDeclarationSearcher(sourceModule, moduleDeclaration, sourceRange.getOffset(), sourceRange.getLength());
						try {
							moduleDeclaration.traverse(searcher);
							if (searcher.getResult() != null) {
								subGoals.add(new ExpressionTypeGoal(searcher.getContext(), searcher.getResult()));
							}
						} catch (Exception e) {
							Logger.logException(e);
						}
					}
				}
			};
			try {
				IDLTKSearchScope scope = SearchEngine.createHierarchyScope(type);
				SearchPattern pattern = SearchPattern.createPattern(variableName, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
				searchEngine.search(pattern, participants, scope, requestor, null);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

	class ClassDeclarationSearcher extends ASTVisitor {

		private Stack<IContext> contextStack = new Stack<IContext>();
		private ISourceModule sourceModule;
		private ModuleDeclaration moduleDeclaration;
		private ASTNode result;
		private IContext context;
		private int offset;
		private int length;

		public ClassDeclarationSearcher(ISourceModule sourceModule, ModuleDeclaration moduleDeclaration, int offset, int length) {
			this.sourceModule = sourceModule;
			this.moduleDeclaration = moduleDeclaration;
			this.offset = offset;
			this.length = length;
		}

		public ASTNode getResult() {
			return result;
		}

		public IContext getContext() {
			return context;
		}

		public boolean visit(Statement e) throws Exception {
			if (e instanceof PHPFieldDeclaration) {
				if (e.sourceStart() == offset && e.sourceEnd() - e.sourceStart() == length) {
					result = ((PHPFieldDeclaration)e).getVariableValue();
					context = contextStack.peek();
				}
			}
			return visitGeneral(e);
		}

		public boolean visit(Expression e) throws Exception {
			if (e instanceof Assignment) {
				if (e.sourceStart() == offset && e.sourceEnd() - e.sourceStart() == length) {
					result = ((Assignment)e).getValue();
					context = contextStack.peek();
				}
			}
			return visitGeneral(e);
		}

		public boolean visitGeneral(ASTNode e) throws Exception {
			return e.sourceStart() <= offset;
		}

		public boolean visit(TypeDeclaration node) throws Exception {
			InstanceContext context = new InstanceContext(sourceModule, moduleDeclaration, new PHPClassType(node.getName()));
			contextStack.push(context);
			return visitGeneral(node);
		}

		public boolean endvisit(TypeDeclaration node) throws Exception {
			contextStack.pop();
			endvisitGeneral(node);
			return true;
		}

		@SuppressWarnings("unchecked")
		public boolean visit(MethodDeclaration e) throws Exception {
			List<String> argumentsList = new LinkedList<String>();
			List<IEvaluatedType> argTypes = new LinkedList<IEvaluatedType>();
			List<Argument> args = e.getArguments();
			for (Argument a : args) {
				argumentsList.add(a.getName());
				argTypes.add(UnknownType.INSTANCE);
			}
			MethodContext context = new MethodContext(contextStack.peek(), sourceModule, moduleDeclaration, e, argumentsList.toArray(new String[argumentsList.size()]), argTypes.toArray(new IEvaluatedType[argTypes.size()]));
			contextStack.push(context);

			return visitGeneral(e);
		}

		public boolean endvisit(MethodDeclaration node) throws Exception {
			contextStack.pop();
			endvisitGeneral(node);
			return true;
		}

		public boolean visit(ModuleDeclaration node) throws Exception {
			BasicContext context = new BasicContext(sourceModule, node);
			contextStack.push(context);
			return visitGeneral(node);
		}

		public boolean endvisit(ModuleDeclaration node) throws Exception {
			contextStack.pop();
			endvisitGeneral(node);
			return true;
		}
	}
}
