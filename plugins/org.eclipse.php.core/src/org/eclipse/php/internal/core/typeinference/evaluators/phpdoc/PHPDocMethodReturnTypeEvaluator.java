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
package org.eclipse.php.internal.core.typeinference.evaluators.phpdoc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractMethodReturnTypeEvaluator;

/**
 * This Evaluator process the phpdoc of a method to determine its 
 * returned type(s)
 *   
 * @see the PHPCodumentor spec at {@link http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.return.pkg.html}  
 */
public class PHPDocMethodReturnTypeEvaluator extends AbstractMethodReturnTypeEvaluator {

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
		Set<PHPDocBlock> docs = new HashSet<PHPDocBlock>();
		for (IMethod method : getMethods()) {
			PHPDocBlock docBlock = PHPModelUtils.getDocBlock(method);
			if (docBlock != null) {
				docs.add(docBlock);
			}
		}

		for (PHPDocBlock doc : docs) {
			for (PHPDocTag tag : doc.getTags()) {
				if (tag.getTagKind() == PHPDocTag.RETURN) {
					// @return datatype1|datatype2|...
					for (SimpleReference reference : tag.getReferences()) {
						final String[] typesNames = PIPE_PATTERN.split(reference.getName());
						for (String typeName : typesNames) {
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
