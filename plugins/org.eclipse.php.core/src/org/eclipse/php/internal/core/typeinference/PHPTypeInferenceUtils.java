/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.ModelClassType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;

public class PHPTypeInferenceUtils {

	public static IEvaluatedType combineMultiType(Collection<IEvaluatedType> evaluatedTypes) {
		MultiTypeType multiTypeType = new MultiTypeType();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type == null) {
				type = PHPSimpleTypes.NULL;
			}
			multiTypeType.addType(type);
		}
		return multiTypeType;
	}

	private static Collection<IEvaluatedType> resolveAmbiguousTypes(Collection<IEvaluatedType> evaluatedTypes) {
		List<IEvaluatedType> resolved = new LinkedList<IEvaluatedType>();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type instanceof AmbiguousType) {
				AmbiguousType ambType = (AmbiguousType) type;
				resolved.addAll(resolveAmbiguousTypes(Arrays.asList(ambType.getPossibleTypes())));
			} else {
				resolved.add(type);
			}
		}
		return resolved;
	}

	public static IEvaluatedType combineTypes(Collection<IEvaluatedType> evaluatedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(resolveAmbiguousTypes(evaluatedTypes));
		if (types.contains(null)) {
			types.remove(null);
			types.add(PHPSimpleTypes.NULL);
		}
		if (types.size() == 0) {
			return UnknownType.INSTANCE;
		}
		if (types.size() == 1) {
			return types.iterator().next();
		}
		return new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
	}

	public static IEvaluatedType resolveExpression(ISourceModule sourceModule, ASTNode expression) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		IContext context = ASTUtils.findContext(sourceModule, moduleDeclaration, expression);
		return resolveExpression(sourceModule, moduleDeclaration, context, expression);
	}

	public static IEvaluatedType resolveExpression(ISourceModule sourceModule, ModuleDeclaration moduleDeclaration, IContext context, ASTNode expression) {
		if (context != null) {
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			return typeInferencer.evaluateType(new ExpressionTypeGoal(context, expression));
		}
		return null;
	}

	/**
	 * Converts IEvaluatedType to IModelElement, if found. This method filters elements using file network dependencies.
	 * @param evaluatedType Evaluated type
	 * @return model elements
	 */
	public static IModelElement[] getModelElements(IEvaluatedType evaluatedType, ISourceModuleContext context) {
		IModelElement[] elements = null;
		ISourceModule sourceModule = context.getSourceModule();

		if (evaluatedType instanceof ModelClassType) {
			return new IModelElement[] { ((ModelClassType) evaluatedType).getTypeDeclaration() };
		}
		if (evaluatedType instanceof PHPClassType) {
			IScriptProject scriptProject = sourceModule.getScriptProject();
			if (!ScriptProject.hasScriptNature(scriptProject.getProject())) {
				List<IModelElement> result = new LinkedList<IModelElement>();
				try {
					IType[] types = sourceModule.getTypes();
					for (IType t : types) {
						if (t.getElementName().equalsIgnoreCase(evaluatedType.getTypeName())) {
							result.add(t);
							break;
						}
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
				return result.toArray(new IModelElement[result.size()]);
			} else {
				IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
				elements = PHPMixinModel.getInstance(scriptProject).getClass(((PHPClassType) evaluatedType).getTypeName(), scope);
			}
		} else if (evaluatedType instanceof AmbiguousType) {
			List<IModelElement> tmpList = new LinkedList<IModelElement>();
			IEvaluatedType[] possibleTypes = ((AmbiguousType) evaluatedType).getPossibleTypes();
			for (IEvaluatedType possibleType : possibleTypes) {
				IModelElement[] tmpArray = getModelElements(possibleType, context);
				if (tmpArray != null) {
					tmpList.addAll(Arrays.asList(tmpArray));
				}
			}
			elements = tmpList.toArray(new IModelElement[tmpList.size()]);
		}
		return PHPModelUtils.filterElements(sourceModule, elements);
	}
}
