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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractMethodReturnTypeEvaluator;

/**
 * This Evaluator process the phpdoc of a method to determine its returned
 * type(s)
 * 
 * @see the PHPCodumentor spec at {@link http
 *      ://manual.phpdoc.org/HTMLSmartyConverter
 *      /HandS/phpDocumentor/tutorial_tags.return.pkg.html}
 */
public class PHPDocMethodReturnTypeEvaluator extends
		AbstractMethodReturnTypeEvaluator {

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
		for (IMethod method : getMethods()) {
			PHPDocBlock docBlock = PHPModelUtils.getDocBlock(method);
			if (docBlock != null) {
				IType currentNamespace = PHPModelUtils
						.getCurrentNamespace(method);
				for (PHPDocTag tag : docBlock.getTags()) {
					if (tag.getTagKind() == PHPDocTag.RETURN) {
						// @return datatype1|datatype2|...
						for (SimpleReference reference : tag.getReferences()) {
							final String[] typesNames = PIPE_PATTERN
									.split(reference.getName());
							for (String typeName : typesNames) {
								addType(currentNamespace, typeName);
							}
						}
					}
				}
			} else {// resolve the type binding
				// FIXME since this class name is
				// PHPDocMethodReturnTypeEvaluator,so do we need change the
				// class name or move the code to another class
				try {
					resolveReturnType(method);
				} catch (ModelException e) {
					PHPCorePlugin.log(e);
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	private void addType(IType currentNamespace, String typeName) {
		IEvaluatedType type = PHPSimpleTypes.fromString(typeName);
		if (type == null) {
			if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) != -1
					|| currentNamespace == null) {
				type = new PHPClassType(typeName);
			} else if (currentNamespace != null) {
				type = new PHPClassType(currentNamespace.getElementName(),
						typeName);
			}
		}
		if (type != null) {
			evaluated.add(type);
		}
	}

	private void resolveReturnType(IMethod method) throws ModelException {
		Program program = null;
		ISourceModule source = method.getSourceModule();
		ASTParser parserForExpected = ASTParser.newParser(ProjectOptions
				.getPhpVersion(source.getScriptProject().getProject()), source);
		try {
			parserForExpected.setSource(source);
			program = parserForExpected.createAST(new NullProgressMonitor());
			program.setSourceModule(source);
		} catch (Exception e) {
		}
		if (program == null) {
			return;
		}

		ASTNode elementAt = program.getElementAt(method.getSourceRange()
				.getOffset());

		if (elementAt.getParent() instanceof MethodDeclaration) {
			elementAt = elementAt.getParent();
		}

		ITypeBinding[] returnTypes = null;
		IFunctionBinding resolvedBinding = null;

		if (elementAt instanceof MethodDeclaration) {
			MethodDeclaration methodDeclaration = (MethodDeclaration) elementAt;
			resolvedBinding = methodDeclaration.resolveMethodBinding();
		} else if (elementAt instanceof FunctionDeclaration) {
			FunctionDeclaration functionDeclaration = (FunctionDeclaration) elementAt;
			resolvedBinding = functionDeclaration.resolveFunctionBinding();
		}
		if (null != resolvedBinding) {
			returnTypes = resolvedBinding.getReturnType();
			IType currentNamespace = PHPModelUtils.getCurrentNamespace(method);
			if (null != returnTypes && returnTypes.length > 0) {
				for (ITypeBinding returnType : returnTypes) {
					if (!returnType.isUnknown() && !returnType.isAmbiguous()) {
						addType(currentNamespace, returnType.getName());
					}
				}
			}

		}
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
