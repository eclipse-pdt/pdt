/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend - Initial implementation
 *******************************************************************************/
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
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
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
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.mixin.PHPDocField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.*;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;

/**
 * This evaluator finds class field declaration either using :
 * 1. @var hint 
 * 2. in method body using field access.
 * 3. magic declaration using the @property, @property-read, @property-write
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
			InstanceContext context = (InstanceContext) typedGoal.getContext();
			types = getTypes(context.getInstanceType(), context.getSourceModule());
		}

		String variableName = typedGoal.getVariableName();
		SearchEngine searchEngine = new SearchEngine();
		SearchParticipant[] participants = new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() };

		final List<IGoal> subGoals = new LinkedList<IGoal>();
		for (final IType type : types) {
			try {
				SearchRequestor requestor = new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						Object element = match.getElement();
						if (element instanceof IMember) {
							
							IType declaringType = (IType) ((IMember) element).getAncestor(IModelElement.TYPE);
							if (declaringType != null) {
								
								ISourceModule sourceModule = declaringType.getSourceModule();
								ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
								TypeDeclaration typeDeclaration = PHPModelUtils.getNodeByClass(moduleDeclaration, declaringType);
	
								if (typeDeclaration != null && element instanceof SourceRefElement) {
									SourceRefElement sourceRefElement = (SourceRefElement) element;
									ISourceRange sourceRange = sourceRefElement.getSourceRange();
	
									ClassDeclarationSearcher searcher = new ClassDeclarationSearcher(sourceModule, moduleDeclaration, sourceRange.getOffset(), sourceRange.getLength(), null);
									try {
										typeDeclaration.traverse(searcher);
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
					}
				};
				IDLTKSearchScope scope;
				SearchPattern pattern = SearchPattern.createPattern(variableName, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());

				scope = SearchEngine.createSearchScope(type);
				searchEngine.search(pattern, participants, scope, requestor, null);

				if (type.getSuperClasses() != null) {
					scope = SearchEngine.createSuperHierarchyScope(type);
					searchEngine.search(pattern, participants, scope, requestor, null);
				}

				if (subGoals.size() == 0) {
					ISourceModule sourceModule = type.getSourceModule();
					ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
					TypeDeclaration typeDeclaration = PHPModelUtils.getNodeByClass(moduleDeclaration, type);

					// try to search declarations of type "self::$var ="
					ClassDeclarationSearcher searcher = new ClassDeclarationSearcher(sourceModule, moduleDeclaration, 0, 0, variableName);
					try {
						typeDeclaration.traverse(searcher);
						Map<ASTNode, IContext> staticDeclarations = searcher.getStaticDeclarations();
						for (ASTNode node : staticDeclarations.keySet()) {
							subGoals.add(new ExpressionTypeGoal(staticDeclarations.get(node), node));
						}
					} catch (Exception e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		resolveMagicClassVariableDeclaration(types, variableName);

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	/**
	 * Search for magic variables using the @property tag
	 * @param types
	 * @param variableName
	 */
	private void resolveMagicClassVariableDeclaration(IType[] types, String variableName) {
		for (IType type : types) {
			IScriptProject scriptProject = type.getScriptProject();
			IDLTKSearchScope scope = SearchEngine.createSearchScope(type.getSourceModule());
			final IModelElement[] elements = PHPMixinModel.getInstance(scriptProject).getClassDoc(type.getElementName(), scope);
			for (IModelElement e : elements) {
				final PHPDocBlock docBlock = ((PHPDocField) e).getDocBlock();
				for (PHPDocTag tag : docBlock.getTags()) {
					final int tagKind = tag.getTagKind();
					if (tagKind == PHPDocTag.PROPERTY || tagKind == PHPDocTag.PROPERTY_READ || tagKind == PHPDocTag.PROPERTY_WRITE) {
						final String typeName = getTypeBinding(variableName, tag);
						if (typeName != null) {
							IEvaluatedType resolved = PHPSimpleTypes.fromString(typeName);
							if (resolved == null) {
								resolved = new PHPClassType(typeName);
							}
							evaluated.add(resolved);
						}
					}
				}
			}
		}
	}

	/**
	 * Resolves the type from the @property tag
	 * @param variableName
	 * @param docTag
	 * @return the type of the given variable
	 */
	private String getTypeBinding(String variableName, PHPDocTag docTag) {
		final String[] split = docTag.getValue().trim().split("\\s+");
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
	 * Searches for all class variable declarations using offset and length which is hold by model element 
	 * @author michael
	 */
	class ClassDeclarationSearcher extends ASTVisitor {

		private Stack<IContext> contextStack = new Stack<IContext>();
		private ISourceModule sourceModule;
		private ModuleDeclaration moduleDeclaration;
		private ASTNode result;
		private IContext context;
		private int offset;
		private int length;
		private String variableName;
		private Map<ASTNode, IContext> staticDeclarations;

		public ClassDeclarationSearcher(ISourceModule sourceModule, ModuleDeclaration moduleDeclaration, int offset, int length, String variableName) {
			this.sourceModule = sourceModule;
			this.moduleDeclaration = moduleDeclaration;
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
			if (e instanceof PHPFieldDeclaration) {
				PHPFieldDeclaration phpFieldDecl = (PHPFieldDeclaration) e;
				if (phpFieldDecl.getDeclarationStart() == offset && phpFieldDecl.sourceEnd() - phpFieldDecl.getDeclarationStart() == length) {
					result = ((PHPFieldDeclaration) e).getVariableValue();
					context = contextStack.peek();
				}
			}
			return visitGeneral(e);
		}

		public boolean visit(Expression e) throws Exception {
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
						if (dispatcher instanceof TypeReference && "self".equals(((TypeReference) dispatcher).getName())) { //$NON-NLS-1$
							Expression field = fieldAccess.getField();
							if (field instanceof VariableReference && variableName.equals(((VariableReference) field).getName())) {
								staticDeclarations.put(right, contextStack.peek());
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
