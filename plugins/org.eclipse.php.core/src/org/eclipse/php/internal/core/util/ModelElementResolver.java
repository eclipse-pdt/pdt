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
package org.eclipse.php.internal.core.util;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.BindingUtility;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.context.FileContext;

/**
 * This class is used for resolving model elements by offset in file. Results
 * are filtered in terms of file network.
 * 
 * @deprecated by {@link BindingUtility}
 */
public class ModelElementResolver {

	/**
	 * Returns possible model elements which are placed under the given offset
	 * in the source module
	 * 
	 * @param sourceModule
	 * @param offset
	 * @return model elements or <code>null</code> in case no element could be
	 *         found
	 */
	public static IModelElement[] resolve(ISourceModule sourceModule, int offset) {
		IModelElement[] modelElements = null;

		ContextFinder visitor = new ContextFinder(sourceModule, offset);
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule);
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		if (visitor.getNode() != null) {
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer
					.evaluateType(new ExpressionTypeGoal(visitor.getContext(),
							visitor.getNode()));
			modelElements = PHPTypeInferenceUtils.getModelElements(
					evaluatedType, (FileContext) visitor.getContext(), offset);
		}

		return modelElements;
	}

	/**
	 * Finds binding context for the given AST node for internal usages only.
	 */
	private static class ContextFinder extends
			org.eclipse.php.internal.core.typeinference.context.ContextFinder {

		private IContext context;
		private ASTNode node;
		private int offset;

		public ContextFinder(ISourceModule sourceModule, int offset) {
			super(sourceModule);
			this.offset = offset;
		}

		/**
		 * Returns found context
		 * 
		 * @return found context
		 */
		public IContext getContext() {
			return context;
		}

		/**
		 * Returns found AST node
		 * 
		 * @return AST node
		 */
		public ASTNode getNode() {
			return node;
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			if (node.sourceEnd() < offset || node.sourceStart() > offset) {
				return false;
			}
			if (node.sourceStart() <= offset && node.sourceEnd() >= offset) {
				if (!contextStack.isEmpty()) {
					this.context = contextStack.peek();
					this.node = node;
				}
			}
			// search inside - we are looking for minimal node
			return true;
		}
	}
}
