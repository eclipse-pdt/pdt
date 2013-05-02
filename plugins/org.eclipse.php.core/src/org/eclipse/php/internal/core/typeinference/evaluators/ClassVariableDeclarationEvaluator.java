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
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
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
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.*;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;

/**
 * This evaluator finds class field declaration either using : 1. @var hint 2.
 * in method body using field access. 3. magic declaration using the @property,
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
			types = PHPTypeInferenceUtils.getModelElements(
					context.getInstanceType(), context);
		}
		if (types == null) {
			return null;
		}

		IContext context = typedGoal.getContext();
		IModelAccessCache cache = null;
		if (context instanceof IModelCacheContext) {
			cache = ((IModelCacheContext) context).getCache();
		}

		String variableName = typedGoal.getVariableName();

		final List<IGoal> subGoals = new LinkedList<IGoal>();
		for (final IType type : types) {
			try {
				ITypeHierarchy hierarchy = null;
				if (cache != null) {
					hierarchy = cache.getSuperTypeHierarchy(type, null);
				}
				IField[] fields = PHPModelUtils.getTypeHierarchyField(type,
						hierarchy, variableName, true, null);
				Map<IType, IType> fieldDeclaringTypeSet = new HashMap<IType, IType>();
				for (IField field : fields) {
					IType declaringType = field.getDeclaringType();
					if (declaringType != null) {
						fieldDeclaringTypeSet.put(declaringType, type);
						ISourceModule sourceModule = declaringType
								.getSourceModule();
						ModuleDeclaration moduleDeclaration = SourceParserUtil
								.getModuleDeclaration(sourceModule);
						TypeDeclaration typeDeclaration = PHPModelUtils
								.getNodeByClass(moduleDeclaration,
										declaringType);

						if (typeDeclaration != null
								&& field instanceof SourceRefElement) {
							SourceRefElement sourceRefElement = (SourceRefElement) field;
							ISourceRange sourceRange = sourceRefElement
									.getSourceRange();

							ClassDeclarationSearcher searcher = new ClassDeclarationSearcher(
									sourceModule, typeDeclaration,
									sourceRange.getOffset(),
									sourceRange.getLength(), null, type,
									declaringType);
							try {
								moduleDeclaration.traverse(searcher);
								if (searcher.getResult() != null) {
									subGoals.add(new ExpressionTypeGoal(
											searcher.getContext(), searcher
													.getResult()));
								}
							} catch (Exception e) {
								if (DLTKCore.DEBUG) {
									e.printStackTrace();
								}
							}
						}
					}
				}

				if (subGoals.size() == 0) {
					getGoalFromStaticDeclaration(variableName, subGoals, type,
							null);
				}
				fieldDeclaringTypeSet.remove(type);
				if (subGoals.size() == 0 && !fieldDeclaringTypeSet.isEmpty()) {
					for (Iterator iterator = fieldDeclaringTypeSet.keySet()
							.iterator(); iterator.hasNext();) {
						IType fieldDeclaringType = (IType) iterator.next();
						getGoalFromStaticDeclaration(variableName, subGoals,
								fieldDeclaringType,
								fieldDeclaringTypeSet.get(fieldDeclaringType));
					}
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		resolveMagicClassVariableDeclaration(types, variableName, cache);

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	protected void getGoalFromStaticDeclaration(String variableName,
			final List<IGoal> subGoals, final IType declaringType,
			IType realType) throws ModelException {
		ISourceModule sourceModule = declaringType.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule);
		TypeDeclaration typeDeclaration = PHPModelUtils.getNodeByClass(
				moduleDeclaration, declaringType);

		// try to search declarations of type "self::$var =" or
		// "$this->var ="
		ClassDeclarationSearcher searcher;
		if (realType != null) {
			searcher = new ClassDeclarationSearcher(sourceModule,
					typeDeclaration, 0, 0, variableName, realType,
					declaringType);
		} else {
			searcher = new ClassDeclarationSearcher(sourceModule,
					typeDeclaration, 0, 0, variableName);
		}
		try {
			moduleDeclaration.traverse(searcher);
			Map<ASTNode, IContext> staticDeclarations = searcher
					.getStaticDeclarations();
			for (ASTNode node : staticDeclarations.keySet()) {
				IContext context = staticDeclarations.get(node);
				if (context instanceof MethodContext) {
					MethodContext methodContext = (MethodContext) context;
					methodContext.setCurrentType(realType);
				}
				subGoals.add(new ExpressionTypeGoal(context, node));
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Search for magic variables using the @property tag
	 * 
	 * @param types
	 * @param variableName
	 * @param cache
	 */
	private void resolveMagicClassVariableDeclaration(IType[] types,
			String variableName, IModelAccessCache cache) {
		for (IType type : types) {
			resolveMagicClassVariableDeclaration(variableName, type, cache);
			try {
				if (evaluated.isEmpty() && type.getSuperClasses() != null
						&& type.getSuperClasses().length > 0) {

					ITypeHierarchy hierarchy = null;
					if (cache != null) {
						hierarchy = cache.getSuperTypeHierarchy(type, null);
					}
					IType[] superClasses = PHPModelUtils.getSuperClasses(type,
							hierarchy);

					for (int i = 0; i < superClasses.length
					/* && evaluated.isEmpty() */; i++) {
						IType superClass = superClasses[i];
						resolveMagicClassVariableDeclaration(variableName,
								superClass, cache);
					}
				}
			} catch (ModelException e) {
				e.printStackTrace();
			}
		}
	}

	protected void resolveMagicClassVariableDeclaration(String variableName,
			IType type, IModelAccessCache cache) {
		final PHPDocBlock docBlock = PHPModelUtils.getDocBlock(type);
		if (docBlock != null) {
			for (PHPDocTag tag : docBlock.getTags()) {
				final int tagKind = tag.getTagKind();
				if (tagKind == PHPDocTag.PROPERTY
						|| tagKind == PHPDocTag.PROPERTY_READ
						|| tagKind == PHPDocTag.PROPERTY_WRITE) {
					final String typeName = getTypeBinding(variableName, tag);
					if (typeName != null) {
						IEvaluatedType resolved = PHPSimpleTypes
								.fromString(typeName);
						if (resolved == null) {
							resolved = new PHPClassType(typeName);
						}
						evaluated.add(resolved);
					}
				}
			}
		}
	}

	/**
	 * Resolves the type from the @property tag
	 * 
	 * @param variableName
	 * @param docTag
	 * @return the type of the given variable
	 */
	private String getTypeBinding(String variableName, PHPDocTag docTag) {
		final String[] split = docTag.getValue().trim().split("\\s+"); //$NON-NLS-1$
		if (split.length < 2) {
			return null;
		}
		return split[1].equals(variableName) ? split[0] : null;
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
		private TypeDeclaration typeDeclaration;
		private ASTNode result;
		private IContext context;
		private int offset;
		private int length;
		private String variableName;
		private Map<ASTNode, IContext> staticDeclarations;

		public ClassDeclarationSearcher(ISourceModule sourceModule,
				TypeDeclaration typeDeclaration, int offset, int length,
				String variableName) {
			super(sourceModule);
			this.typeDeclaration = typeDeclaration;
			this.offset = offset;
			this.length = length;
			this.variableName = variableName;
			this.staticDeclarations = new HashMap<ASTNode, IContext>();
		}

		public ClassDeclarationSearcher(ISourceModule sourceModule,
				TypeDeclaration typeDeclaration, int offset, int length,
				String variableName, IType realType, IType declaringType) {
			// this(sourceModule, typeDeclaration2, offset2, length2,
			// variableName);
			super(sourceModule, realType, declaringType);
			this.typeDeclaration = typeDeclaration;
			this.offset = offset;
			this.length = length;
			this.variableName = variableName;
			this.staticDeclarations = new HashMap<ASTNode, IContext>();
		}

		public ASTNode getResult() {
			return result;
		}

		public Map<ASTNode, IContext> getStaticDeclarations() {
			return staticDeclarations;
		}

		public IContext getContext() {
			return context;
		}

		public boolean visit(Statement e) throws Exception {
			if (typeDeclaration.sourceStart() < e.sourceStart()
					&& typeDeclaration.sourceEnd() > e.sourceEnd()) {
				if (e instanceof PHPFieldDeclaration) {
					PHPFieldDeclaration phpFieldDecl = (PHPFieldDeclaration) e;
					if (phpFieldDecl.getDeclarationStart() == offset
							&& phpFieldDecl.sourceEnd()
									- phpFieldDecl.getDeclarationStart() == length) {
						result = ((PHPFieldDeclaration) e).getVariableValue();
						if (result instanceof Scalar) {
							Scalar scalar = (Scalar) result;
							if (scalar.getScalarType() == Scalar.TYPE_STRING
									&& scalar.getValue().toLowerCase()
											.equals(NULL)) {
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
			if (typeDeclaration.sourceStart() < e.sourceStart()
					&& typeDeclaration.sourceEnd() > e.sourceEnd()) {
				if (e instanceof Assignment) {
					if (e.sourceStart() == offset
							&& e.sourceEnd() - e.sourceStart() == length) {
						result = ((Assignment) e).getValue();
						context = contextStack.peek();
					} else if (variableName != null) {
						Assignment assignment = (Assignment) e;
						Expression left = assignment.getVariable();
						Expression right = assignment.getValue();

						if (left instanceof StaticFieldAccess) {
							StaticFieldAccess fieldAccess = (StaticFieldAccess) left;
							Expression dispatcher = fieldAccess.getDispatcher();
							if (dispatcher instanceof TypeReference
									&& "self".equals(((TypeReference) dispatcher).getName())) { //$NON-NLS-1$
								Expression field = fieldAccess.getField();
								if (field instanceof VariableReference
										&& variableName
												.equals(((VariableReference) field)
														.getName())) {
									staticDeclarations.put(right,
											contextStack.peek());
								}
							}
						} else if (left instanceof FieldAccess) {
							FieldAccess fieldAccess = (FieldAccess) left;
							Expression dispatcher = fieldAccess.getDispatcher();
							if (dispatcher instanceof VariableReference
									&& "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
								Expression field = fieldAccess.getField();
								if (field instanceof SimpleReference
										&& variableName
												.equals('$' + ((SimpleReference) field)
														.getName())) {
									staticDeclarations.put(right,
											contextStack.peek());
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
	}
}
