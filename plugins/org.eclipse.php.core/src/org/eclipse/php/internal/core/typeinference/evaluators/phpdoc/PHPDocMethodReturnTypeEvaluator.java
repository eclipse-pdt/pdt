/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators.phpdoc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.mixin.PHPDocField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

/**
 * This Evaluator process the phpdoc of a method to determine its 
 * returned type(s)
 *   
 * @see the PHPCodumentor spec at {@link http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.return.pkg.html}  
 */
public class PHPDocMethodReturnTypeEvaluator extends GoalEvaluator {

	/**
	 * Used for splitting the data types list of the returned tag
	 */
	private final static Pattern PIPE_PATTERN = Pattern.compile("\\|");

	/**
	 * Holds the result of evaluated types that this evaluator resolved
	 */
	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public PHPDocMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		PHPDocMethodReturnTypeGoal typedGoal = (PHPDocMethodReturnTypeGoal) goal;
		IContext context = goal.getContext();
		String methodName = typedGoal.getMethodName();

		InstanceContext typedGontext = (InstanceContext) context;
		IEvaluatedType instanceType = typedGontext.getInstanceType();

		Set<PHPDocField> docs = new HashSet<PHPDocField>();

		if (instanceType instanceof PHPClassType || instanceType instanceof AmbiguousType) {

			List<IType> types = new LinkedList<IType>();
			if (instanceType instanceof AmbiguousType) {
				AmbiguousType ambiguousType = (AmbiguousType) instanceType;
				IScriptProject scriptProject = typedGontext.getSourceModule().getScriptProject();
				IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
				for (IEvaluatedType type : ambiguousType.getPossibleTypes()) {
					if (type instanceof PHPClassType) {
						PHPClassType classType = (PHPClassType) type;
						IModelElement[] classes = PHPMixinModel.getInstance(scriptProject).getClass(classType.getTypeName(), scope);
						for (IModelElement c : classes) {
							types.add((IType) c);
						}
					}
				}
			} else {
				IScriptProject scriptProject = typedGontext.getSourceModule().getScriptProject();
				IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
				PHPClassType classType = (PHPClassType) instanceType;
				IModelElement[] classes = PHPMixinModel.getInstance(scriptProject).getClass(classType.getTypeName(), scope);
				for (IModelElement c : classes) {
					types.add((IType) c);
				}
			}

			for (IType type : types) {
				try {
					for (PHPDocField doc : PHPModelUtils.getClassMethodDoc(type, methodName, null)) {
						docs.add(doc);
					}
				} catch (CoreException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		} else {
			IScriptProject scriptProject = typedGontext.getSourceModule().getScriptProject();
			IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
			IModelElement[] elements = PHPMixinModel.getInstance(scriptProject).getFunctionDoc(methodName, scope);
			for (IModelElement e : elements) {
				docs.add((PHPDocField) e);
			}
		}

		PHPDocField docFromSameFile = null;
		for (PHPDocField doc : docs) {
			if (doc.getSourceModule().equals(typedGontext.getSourceModule())) {
				docFromSameFile = doc;
				break;
			}
		}
		// If doc from the same file was found  - use it
		if (docFromSameFile != null) {
			docs.clear();
			docs.add(docFromSameFile);
		}

		for (PHPDocField doc : docs) {
			PHPDocBlock docBlock = doc.getDocBlock();
			for (PHPDocTag tag : docBlock.getTags()) {
				if (tag.getTagKind() == PHPDocTag.RETURN) {
					// @return datatype1|datatype2|...
					for (SimpleReference reference : tag.getReferences()) {
						final String[] types = PIPE_PATTERN.split(reference.getName());
						for (String typeName : types) {
							IEvaluatedType type = PHPSimpleTypes.fromString(typeName);
							if (type == null) {
								type = new PHPClassType(typeName);
							}
							evaluated.add(type);
						}
					}
				}
			}
		}

		return IGoal.NO_GOALS;
	}
	



	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
