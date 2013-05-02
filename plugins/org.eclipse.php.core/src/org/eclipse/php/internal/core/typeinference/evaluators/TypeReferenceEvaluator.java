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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.context.INamespaceContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;

public class TypeReferenceEvaluator extends GoalEvaluator {

	private TypeReference typeReference;
	private IEvaluatedType result;

	public TypeReferenceEvaluator(IGoal goal, TypeReference typeReference) {
		super(goal);
		this.typeReference = typeReference;
	}

	public IGoal[] init() {
		final IContext context = goal.getContext();
		String className = typeReference.getName();

		if ("self".equals(className) || "static".equals(className)) { //$NON-NLS-1$ //$NON-NLS-2$
			if (context instanceof MethodContext) {
				MethodContext methodContext = (MethodContext) context;
				IEvaluatedType instanceType = methodContext.getInstanceType();
				if (instanceType instanceof PHPClassType) {
					result = instanceType;
				}
			}
		} else if ("parent".equals(className)) { //$NON-NLS-1$
			if (context instanceof MethodContext) {
				final MethodContext methodContext = (MethodContext) context;
				ModuleDeclaration rootNode = methodContext.getRootNode();
				ISourceModule sourceModule = ((ISourceModuleContext) context)
						.getSourceModule();
				final IType currentNamespace = PHPModelUtils
						.getCurrentNamespace(sourceModule,
								rootNode.sourceStart());
				final ModuleDeclaration moduleDeclaration = SourceParserUtil
						.getModuleDeclaration(sourceModule);
				final MethodDeclaration methodDecl = methodContext
						.getMethodNode();

				// Look for parent class types:
				final List<IEvaluatedType> types = new LinkedList<IEvaluatedType>();
				try {
					rootNode.traverse(new ASTVisitor() {
						private TypeDeclaration currentType;
						private boolean found;

						public boolean visit(MethodDeclaration s)
								throws Exception {
							if (s == methodDecl
									&& currentType instanceof ClassDeclaration) {
								ClassDeclaration classDecl = (ClassDeclaration) currentType;

								ASTListNode superClasses = classDecl
										.getSuperClasses();
								List childs = superClasses.getChilds();
								for (Iterator iterator = childs.iterator(); iterator
										.hasNext();) {
									ASTNode node = (ASTNode) iterator.next();
									NamespaceReference namespace = null;
									SimpleReference reference = null;
									if (node instanceof SimpleReference) {
										reference = (SimpleReference) node;
										String typeName = reference.getName();
										if (reference instanceof FullyQualifiedReference) {
											FullyQualifiedReference ref = (FullyQualifiedReference) node;
											namespace = ref.getNamespace();
										}
										if (namespace != null
												&& !namespace.getName().equals(
														"")) { //$NON-NLS-1$
											String nsName = namespace.getName();
											if (nsName.equals("\\")) { //$NON-NLS-1$
												typeName = nsName + typeName;
											} else {
												if (nsName
														.startsWith("namespace\\")) { //$NON-NLS-1$
													nsName = nsName.replace(
															"namespace\\", ""); //$NON-NLS-1$ //$NON-NLS-2$
												}
												typeName = nsName
														+ NamespaceReference.NAMESPACE_SEPARATOR
														+ typeName;
											}
										}
										if (typeName
												.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
											// check if the first part
											// is an
											// alias,then get the full
											// name
											String prefix = typeName.substring(
													0,
													typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
											final Map<String, UsePart> result = PHPModelUtils
													.getAliasToNSMap(
															prefix,
															moduleDeclaration,
															reference
																	.sourceStart(),
															currentNamespace,
															true);
											if (result.containsKey(prefix)) {
												String fullName = result
														.get(prefix)
														.getNamespace()
														.getFullyQualifiedName();
												typeName = typeName.replace(
														prefix, fullName);
											}
										} else if (typeName
												.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {

											String prefix = typeName;
											final Map<String, UsePart> result = PHPModelUtils
													.getAliasToNSMap(
															prefix,
															moduleDeclaration,
															reference
																	.sourceStart(),
															currentNamespace,
															true);
											if (result.containsKey(prefix)) {
												String fullName = result
														.get(prefix)
														.getNamespace()
														.getFullyQualifiedName();
												typeName = fullName;
											}
										}
										IEvaluatedType type = PHPSimpleTypes
												.fromString(typeName);
										if (type == null) {
											if (typeName
													.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) != -1
													|| currentNamespace == null) {
												type = new PHPClassType(
														typeName);
											} else if (currentNamespace != null) {
												type = new PHPClassType(
														currentNamespace
																.getElementName(),
														typeName);
											}
										}

										types.add(type);
									}
									// if (namespace == null
									// || namespace.getName().equals("")) {
									// types.add(new PHPClassType(reference
									// .getName()));
									// } else {
									// types.add(new
									// PHPClassType(namespace.getName(),
									// reference.getName()));
									// }

								}
								found = true;
							}
							return !found;
						}

						public boolean visit(TypeDeclaration s)
								throws Exception {
							this.currentType = s;
							return !found;
						}

						public boolean endvisit(TypeDeclaration s)
								throws Exception {
							this.currentType = null;
							return super.endvisit(s);
						}

						public boolean visit(ASTNode n) throws Exception {
							return !found;
						}
					});
				} catch (Exception e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}

				if (types.size() == 1) {
					result = types.get(0);
				} else if (types.size() > 1) {
					result = new AmbiguousType(
							types.toArray(new IEvaluatedType[types.size()]));
				}
			}
		} else {
			String parentNamespace = null;

			// Check current context - if we are under some namespace:
			if (context instanceof INamespaceContext) {
				parentNamespace = ((INamespaceContext) context).getNamespace();
			}
			String fullyQualifiedName;
			// If the namespace was prefixed explicitly - use it:
			if (typeReference instanceof FullyQualifiedReference) {
				fullyQualifiedName = ((FullyQualifiedReference) typeReference)
						.getFullyQualifiedName();
			} else {
				fullyQualifiedName = typeReference.getName();
				className = PHPModelUtils
						.extractElementName(fullyQualifiedName);
			}
			ISourceModule sourceModule = ((ISourceModuleContext) context)
					.getSourceModule();
			int offset = typeReference.sourceStart();
			String extractedNamespace = PHPModelUtils.extractNamespaceName(
					fullyQualifiedName, sourceModule, offset);
			if (extractedNamespace != null) {
				parentNamespace = extractedNamespace;
				className = PHPModelUtils.getRealName(fullyQualifiedName,
						sourceModule, offset, className);
			}
			if (PHPModelUtils.isInUseTraitStatement(
					((ISourceModuleContext) context).getRootNode(),
					typeReference.sourceStart())) {
				if (parentNamespace != null) {
					result = new PHPTraitType(parentNamespace, className);
				} else {
					result = new PHPTraitType(className);
				}
			} else {
				if (parentNamespace != null) {
					result = new PHPClassType(parentNamespace, className);
				} else {
					result = new PHPClassType(className);
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
