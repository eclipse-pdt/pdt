/*******************************************************************************
 * Copyright (c) 2009, 2016, 2017, 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [469267]
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
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.internal.core.ImportDeclaration;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.IInstanceContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.context.INamespaceContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.ConstantDeclarationGoal;

public class TypeReferenceEvaluator extends GoalEvaluator {

	private TypeReference typeReference;
	private IEvaluatedType result;
	private PHPVersion phpVersion;

	public TypeReferenceEvaluator(IGoal goal, TypeReference typeReference) {
		super(goal);
		this.typeReference = typeReference;
		if (goal.getContext() instanceof ISourceModuleContext) {
			phpVersion = ProjectOptions.getPHPVersion(((ISourceModuleContext) goal.getContext()).getSourceModule());
		}
	}

	private boolean isSelfOrStatic() {
		String name = typeReference.getName();
		if (phpVersion != null && PHPVersion.PHP5_4.isLessThan(phpVersion)) {
			name = name.toLowerCase();
		}

		return "self".equals(name) || "static".equals(name); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private boolean isParent() {
		String name = typeReference.getName();
		if (phpVersion != null && PHPVersion.PHP5_4.isLessThan(phpVersion)) {
			name = name.toLowerCase();
		}

		return "parent".equals(name); //$NON-NLS-1$
	}

	@Override
	public IGoal[] init() {
		final IContext context = goal.getContext();
		String elementName = typeReference.getName();

		if (isSelfOrStatic()) {
			if (context instanceof MethodContext || context instanceof TypeContext) {
				IInstanceContext instanceContext = (IInstanceContext) context;
				IEvaluatedType instanceType = instanceContext.getInstanceType();
				if (instanceType instanceof PHPClassType) {
					result = instanceType;
				}
			}
		} else if (isParent()) {
			ISourceModule sourceModule = ((ISourceModuleContext) context).getSourceModule();
			final List<IEvaluatedType> parentClassTypes = new LinkedList<>();

			if (context instanceof MethodContext) {
				try {
					final MethodContext methodContext = (MethodContext) context;
					final ModuleDeclaration rootNode = methodContext.getRootNode();
					final MethodDeclaration methodDecl = methodContext.getMethodNode();

					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=525370
					// Look for parent class types (inside method declaration):
					rootNode.traverse(new ASTVisitor() {
						private TypeDeclaration currentType;
						private boolean found;

						@Override
						public boolean visit(MethodDeclaration s) throws Exception {
							if (s == methodDecl && currentType instanceof ClassDeclaration) {
								addParentClassTypes(sourceModule, currentType, parentClassTypes);
								found = true;
							}
							return !found;
						}

						@Override
						public boolean visit(TypeDeclaration s) throws Exception {
							this.currentType = s;
							return !found;
						}

						@Override
						public boolean endvisit(TypeDeclaration s) throws Exception {
							this.currentType = null;
							return super.endvisit(s);
						}

						@Override
						public boolean visit(ASTNode n) throws Exception {
							return !found;
						}
					});
				} catch (Exception e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			} else if (context instanceof TypeContext) {
				try {
					final TypeContext typeContext = (TypeContext) context;
					ModuleDeclaration rootNode = typeContext.getRootNode();

					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=525370
					// Look for parent class types (inside class declaration):
					IType currentType = PHPModelUtils.getCurrentType(sourceModule, typeReference.sourceStart());
					if (currentType != null) {
						TypeDeclaration currentTypeDeclaration = PHPModelUtils.getNodeByClass(rootNode, currentType);
						addParentClassTypes(sourceModule, currentTypeDeclaration, parentClassTypes);
					}
				} catch (Exception e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}

			if (parentClassTypes.size() == 1) {
				result = parentClassTypes.get(0);
			} else if (parentClassTypes.size() > 1) {
				result = new AmbiguousType(parentClassTypes.toArray(new IEvaluatedType[parentClassTypes.size()]));
			}
		} else if (PHPSimpleTypes.isHintable(typeReference.getName(), phpVersion)) {
			result = PHPSimpleTypes.fromString(typeReference.getName());
		} else {
			String parentNamespace = null;

			// Check current context - if we are under some namespace:
			if (context instanceof INamespaceContext) {
				parentNamespace = ((INamespaceContext) context).getNamespace();
			}
			String fullyQualifiedName;
			int elementType = FullyQualifiedReference.T_TYPE;
			// If the namespace was prefixed explicitly - use it:
			if (typeReference instanceof FullyQualifiedReference) {
				fullyQualifiedName = ((FullyQualifiedReference) typeReference).getFullyQualifiedName();
				elementType = ((FullyQualifiedReference) typeReference).getElementType();
			} else {
				fullyQualifiedName = typeReference.getName();
				fullyQualifiedName = PHPEvaluationUtils.extractArrayType(fullyQualifiedName);

				elementName = PHPModelUtils.extractElementName(fullyQualifiedName);
			}
			ISourceModule sourceModule = ((ISourceModuleContext) context).getSourceModule();
			int offset = typeReference.sourceStart();
			try {
				// for use statement, extract namespace and class name directly
				IModelElement element = sourceModule.getElementAt(offset);
				if (element instanceof ImportDeclaration) {
					fullyQualifiedName = ((ImportDeclaration) element).getElementName();
					elementName = PHPModelUtils.extractElementName(fullyQualifiedName);
					String namespace = PHPModelUtils.extractNameSpaceName(fullyQualifiedName);
					// http://php.net/manual/en/language.namespaces.importing.php
					// "Note that for namespaced names (fully qualified
					// namespace names containing namespace separator, such as
					// Foo\Bar as opposed to global names that do not, such as
					// FooBar), the leading backslash is unnecessary and not
					// recommended, as import names must be fully qualified, and
					// are not processed relative to the current namespace."
					if (namespace != null && namespace.length() > 0
							&& namespace.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
						namespace = NamespaceReference.NAMESPACE_DELIMITER + namespace;
					}
					// NB: ImportDeclaration has no useful type, we have to look
					// at the type of "typeReference"
					if (elementType == FullyQualifiedReference.T_CONSTANT) {
						return new IGoal[] { new ConstantDeclarationGoal(goal.getContext(), elementName, namespace) };
					}
					if (elementType == FullyQualifiedReference.T_TYPE) {
						if (namespace != null) {
							result = new PHPClassType(namespace, elementName);
						} else {
							result = new PHPClassType(elementName);
						}
					}
					// NB: elementType == FullyQualifiedReference.T_FUNCTION is
					// not handled here (see also
					// PHPSelectionEngine.internalASTResolve())
					return IGoal.NO_GOALS;
				}
			} catch (ModelException e) {
			}
			String extractedNamespace = PHPModelUtils.extractNamespaceName(fullyQualifiedName, sourceModule, offset);
			if (extractedNamespace != null) {
				parentNamespace = extractedNamespace;
				// See also code from PHPClassType.fromTypeName(...) and
				// PHPClassType.fromTraitName(...)
				elementName = PHPModelUtils.getRealName(extractedNamespace, fullyQualifiedName, sourceModule, offset,
						elementName);
			}
			if (PHPModelUtils.isInUseTraitStatement(((ISourceModuleContext) context).getRootNode(),
					typeReference.sourceStart())) {
				if (parentNamespace != null) {
					result = new PHPTraitType(parentNamespace, elementName);
				} else {
					result = new PHPTraitType(elementName);
				}
			} else {
				if (parentNamespace != null) {
					if (elementType == FullyQualifiedReference.T_CONSTANT) {
						return new IGoal[] {
								new ConstantDeclarationGoal(goal.getContext(), elementName, parentNamespace) };
					} else {
						result = new PHPClassType(parentNamespace, elementName);
					}
				} else {
					result = new PHPClassType(elementName);
				}
			}
			if (PHPEvaluationUtils.isArrayType(typeReference.getName())) {
				// XXX Quick Fix for bug 549308
				MultiTypeType tmp = new MultiTypeType();
				tmp.addType(result);
				result = tmp;
			}
		}

		return IGoal.NO_GOALS;
	}

	private void addParentClassTypes(ISourceModule sourceModule, TypeDeclaration currentTypeDeclaration,
			List<IEvaluatedType> parentClassTypes) {
		final ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		final IType currentNamespace = PHPModelUtils.getCurrentNamespace(sourceModule,
				currentTypeDeclaration.sourceStart());

		if (currentTypeDeclaration instanceof ClassDeclaration) {
			ClassDeclaration classDecl = (ClassDeclaration) currentTypeDeclaration;

			ASTListNode superClasses = classDecl.getSuperClasses();
			List<ASTNode> childs = superClasses.getChilds();
			for (Iterator<ASTNode> iterator = childs.iterator(); iterator.hasNext();) {
				ASTNode node = iterator.next();
				NamespaceReference namespace = null;
				SimpleReference reference = null;
				if (node instanceof SimpleReference) {
					reference = (SimpleReference) node;
					String typeName = reference.getName();
					if (reference instanceof FullyQualifiedReference) {
						FullyQualifiedReference ref = (FullyQualifiedReference) node;
						namespace = ref.getNamespace();
					}
					if (namespace != null && !namespace.getName().equals("")) { //$NON-NLS-1$
						String nsName = namespace.getName();
						if (nsName.equals("\\")) { //$NON-NLS-1$
							typeName = nsName + typeName;
						} else {
							if (nsName.startsWith("namespace\\")) { //$NON-NLS-1$
								nsName = nsName.replace("namespace\\", ""); //$NON-NLS-1$ //$NON-NLS-2$
							}
							typeName = nsName + NamespaceReference.NAMESPACE_SEPARATOR + typeName;
						}
					}
					if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
						// check if the first part
						// is an
						// alias,then get the full
						// name
						String prefix = typeName.substring(0, typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
						final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration,
								reference.sourceStart(), currentNamespace, true);
						if (result.containsKey(prefix)) {
							String fullName = result.get(prefix).getFullUseStatementName();
							typeName = typeName.replace(prefix, fullName);
							if (typeName.length() > 0 && typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
								typeName = NamespaceReference.NAMESPACE_SEPARATOR + typeName;
							}
						}
					} else if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {

						String prefix = typeName;
						final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration,
								reference.sourceStart(), currentNamespace, true);
						if (result.containsKey(prefix)) {
							String fullName = result.get(prefix).getFullUseStatementName();
							typeName = fullName;
							if (typeName.length() > 0 && typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
								typeName = NamespaceReference.NAMESPACE_SEPARATOR + typeName;
							}
						}
					}
					IEvaluatedType type = PHPSimpleTypes.fromString(typeName);
					if (type == null) {
						if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) != -1
								|| currentNamespace == null) {
							type = new PHPClassType(typeName);
						} else {
							type = new PHPClassType(currentNamespace.getElementName(), typeName);
						}
					}

					parentClassTypes.add(type);
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
		}
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (result instanceof IEvaluatedType) {
			this.result = (IEvaluatedType) result;
		}
		return IGoal.NO_GOALS;
	}

}
