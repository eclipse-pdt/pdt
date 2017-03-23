/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.SourceRefElement;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;

/**
 * This evaluator finds class field declaration using method body and field
 * access.
 * 
 * @property-read, @property-write
 */
public class ClassVariableDeclarationEvaluator extends AbstractPHPGoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public ClassVariableDeclarationEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ClassVariableDeclarationGoal typedGoal = (ClassVariableDeclarationGoal) goal;
		IType[] types = typedGoal.getTypes();

		if (types == null) {
			TypeContext context = (TypeContext) typedGoal.getContext();
			types = PHPTypeInferenceUtils.getModelElements(context.getInstanceType(), context);
		}
		if (types == null) {
			return null;
		}

		IContext context = typedGoal.getContext();
		IModelAccessCache cache = null;
		if (context instanceof IModelCacheContext) {
			cache = ((IModelCacheContext) context).getCache();
		}

		String variableName = PHPEvaluationUtils.removeArrayBrackets(typedGoal.getVariableName());

		final List<IGoal> subGoals = new LinkedList<IGoal>();
		for (final IType type : types) {
			try {
				ITypeHierarchy hierarchy = null;
				if (cache != null) {
					hierarchy = cache.getSuperTypeHierarchy(type, null);
				}
				IField[] fields = PHPModelUtils.getTypeHierarchyField(type, hierarchy, variableName, true, null);
				Map<IType, IType> fieldDeclaringTypeSet = new HashMap<IType, IType>();
				for (IField field : fields) {
					IType declaringType = field.getDeclaringType();
					if (declaringType != null) {
						fieldDeclaringTypeSet.put(declaringType, type);

						if (field instanceof SourceRefElement) {
							ISourceReference sourceRefElement = (ISourceReference) field;
							ISourceRange sourceRange = sourceRefElement.getSourceRange();
							ISourceModule sourceModule = declaringType.getSourceModule();
							ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);

							ClassDeclarationSearcher searcher = new ClassDeclarationSearcher(sourceModule,
									declaringType.getSourceRange(), sourceRange.getOffset(), sourceRange.getLength(),
									null, type, declaringType);
							try {
								moduleDeclaration.traverse(searcher);
								if (searcher.getResult() != null) {
									subGoals.add(new ExpressionTypeGoal(searcher.getContext(), searcher.getResult()));
								}
							} catch (Exception e) {
								if (DLTKCore.DEBUG) {
									e.printStackTrace();
								}
							}
						}
					}
				}

				addGoalFromStaticDeclaration(variableName, subGoals, type, null);

				fieldDeclaringTypeSet.remove(type);
				for (Entry<IType, IType> entry : fieldDeclaringTypeSet.entrySet()) {
					addGoalFromStaticDeclaration(variableName, subGoals, entry.getKey(), entry.getValue());
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	protected void addGoalFromStaticDeclaration(String variableName, final List<IGoal> subGoals,
			final IType declaringType, IType realType) throws ModelException {
		ISourceModule sourceModule = declaringType.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		// try to search declarations of type "self::$var =" or "$this->var ="
		ClassDeclarationSearcher searcher;
		if (realType != null) {
			searcher = new ClassDeclarationSearcher(sourceModule, declaringType.getSourceRange(), 0, 0, variableName,
					realType, declaringType);
		} else {
			searcher = new ClassDeclarationSearcher(sourceModule, declaringType.getSourceRange(), 0, 0, variableName);
		}
		try {
			moduleDeclaration.traverse(searcher);
			for (Entry<ASTNode, IContext> entry : searcher.getStaticDeclarations().entrySet()) {
				final IContext context = entry.getValue();
				if (context instanceof MethodContext) {
					MethodContext methodContext = (MethodContext) context;
					methodContext.setCurrentType(realType);
				}
				if (context instanceof IModelCacheContext
						&& ClassVariableDeclarationEvaluator.this.goal.getContext() instanceof IModelCacheContext) {
					((IModelCacheContext) context).setCache(
							((IModelCacheContext) ClassVariableDeclarationEvaluator.this.goal.getContext()).getCache());
				}
				subGoals.add(new ExpressionTypeGoal(context, entry.getKey()));
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

	/**
	 * Searches for all class variable declarations using offset and length
	 * which is hold by model element
	 * 
	 * @author michael
	 */
	class ClassDeclarationSearcher extends ContextFinder {

		private static final String NULL = "null"; //$NON-NLS-1$
		private ISourceRange typeDeclarationRange;
		private ASTNode result;
		private IContext context;
		private int offset;
		private int length;
		private String variableName;
		private ISourceModule sourceModule;
		private Map<ASTNode, IContext> staticDeclarations = new HashMap<ASTNode, IContext>();

		public ClassDeclarationSearcher(ISourceModule sourceModule, ISourceRange typeDeclarationRange, int offset,
				int length, String variableName) {
			super(sourceModule);
			this.typeDeclarationRange = typeDeclarationRange;
			this.offset = offset;
			this.length = length;
			this.sourceModule = sourceModule;
			this.variableName = variableName;
		}

		public ClassDeclarationSearcher(ISourceModule sourceModule, ISourceRange typeDeclarationRange, int offset,
				int length, String variableName, IType realType, IType declaringType) {
			super(sourceModule, realType, declaringType);
			this.typeDeclarationRange = typeDeclarationRange;
			this.offset = offset;
			this.length = length;
			this.sourceModule = sourceModule;
			this.variableName = variableName;
		}

		public ASTNode getResult() {
			return result;
		}

		public Map<ASTNode, IContext> getStaticDeclarations() {
			return staticDeclarations;
		}

		public IContext getContext() {
			if (context instanceof IModelCacheContext
					&& ClassVariableDeclarationEvaluator.this.goal.getContext() instanceof IModelCacheContext) {
				((IModelCacheContext) context).setCache(
						((IModelCacheContext) ClassVariableDeclarationEvaluator.this.goal.getContext()).getCache());
			}
			return context;
		}

		public boolean visit(Statement e) throws Exception {
			if (typeDeclarationRange.getOffset() < e.sourceStart()
					&& (typeDeclarationRange.getOffset() + typeDeclarationRange.getLength()) > e.sourceEnd()) {
				if (e instanceof PHPFieldDeclaration) {
					PHPFieldDeclaration phpFieldDecl = (PHPFieldDeclaration) e;
					if (phpFieldDecl.getDeclarationStart() == offset
							&& phpFieldDecl.sourceEnd() - phpFieldDecl.getDeclarationStart() == length) {
						result = ((PHPFieldDeclaration) e).getVariableValue();
						if (result instanceof Scalar) {
							Scalar scalar = (Scalar) result;
							if (scalar.getScalarType() == Scalar.TYPE_STRING
									&& scalar.getValue().equalsIgnoreCase(NULL)) {
								result = null;
							}
						}
						context = contextStack.peek();
					}
				}
			}
			return visitGeneral(e);
		}

		public boolean visit(Expression e) throws Exception {
			if (typeDeclarationRange.getOffset() < e.sourceStart()
					&& (typeDeclarationRange.getOffset() + typeDeclarationRange.getLength()) > e.sourceEnd()) {
				if (e instanceof Assignment) {
					if (e.sourceStart() == offset && e.sourceEnd() - e.sourceStart() == length) {
						result = ((Assignment) e).getValue();
						context = contextStack.peek();
					} else if (variableName != null) {
						Assignment assignment = (Assignment) e;
						Expression left = assignment.getVariable();
						Expression right = assignment.getValue();

						if (left instanceof StaticFieldAccess) {
							StaticFieldAccess fieldAccess = (StaticFieldAccess) left;
							Expression dispatcher = fieldAccess.getDispatcher();
							if (isSelf(dispatcher)) {
								Expression field = fieldAccess.getField();
								if (field instanceof VariableReference
										&& variableName.equals(((VariableReference) field).getName())) {
									staticDeclarations.put(right, contextStack.peek());
								}
							}
						} else if (left instanceof FieldAccess) {
							FieldAccess fieldAccess = (FieldAccess) left;
							Expression dispatcher = fieldAccess.getDispatcher();
							if (dispatcher instanceof VariableReference
									&& "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
								Expression field = fieldAccess.getField();
								if (field instanceof SimpleReference
										&& variableName.equals('$' + ((SimpleReference) field).getName())) {
									staticDeclarations.put(right, contextStack.peek());
								}
							}
						}
					}
				}
			}
			return visitGeneral(e);
		}

		public boolean visitGeneral(ASTNode e) throws Exception {
			return e.sourceStart() <= offset || variableName != null;
		}

		private boolean isSelf(Expression dispatcher) {
			if (!(dispatcher instanceof TypeReference)) {
				return false;
			}
			if ("self".equals(((TypeReference) dispatcher).getName())) { //$NON-NLS-1$
				return true;
			} else if (PHPVersion.PHP5_4.isLessThan(ProjectOptions.getPHPVersion(sourceModule))
					&& "self".equalsIgnoreCase(((TypeReference) dispatcher).getName())) { //$NON-NLS-1$
				return true;
			}

			return false;
		}
	}
}
