/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.List;

import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.locator.PHPElementConciliator;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class ConstantHighlighting extends AbstractSemanticHighlighting {

	protected class ConstantApply extends AbstractSemanticApply {
		boolean isInQuote = false;

		@Override
		public boolean visit(ConstantDeclaration constDecl) {
			List<Identifier> names = constDecl.names();
			for (Identifier name : names) {
				highlight(name);
			}
			return true;
		}

		@Override
		public boolean visit(Quote quote) {
			isInQuote = true;
			return true;
		}

		@Override
		public void endVisit(Quote quote) {
			isInQuote = false;
		}

		@Override
		public boolean visit(Scalar scalar) {
			String value = scalar.getStringValue();
			if ((scalar.getScalarType() == Scalar.TYPE_STRING && !isInQuote
					|| scalar.getScalarType() == Scalar.TYPE_SYSTEM) && !"null".equalsIgnoreCase(value) //$NON-NLS-1$
					&& !"false".equalsIgnoreCase(value) //$NON-NLS-1$
					&& !"true".equalsIgnoreCase(value) //$NON-NLS-1$
					&& value.length() > 0 && value.charAt(0) != '\'' && value.charAt(0) != '"') {
				highlight(scalar);
			} else if (scalar.getParent() instanceof FunctionInvocation) {// for
																			// define
																			// function
				FunctionInvocation fi = (FunctionInvocation) scalar.getParent();
				if (fi.parameters().get(0) == scalar) {
					if (fi.getFunctionName().getName() instanceof Identifier) {
						final Identifier identifier = (Identifier) fi.getFunctionName().getName();
						if ("define".equalsIgnoreCase(identifier.getName()) //$NON-NLS-1$
								|| "constant".equalsIgnoreCase(identifier.getName())) {//$NON-NLS-1$
							highlight(scalar);
						}
					}
				}
			}
			return true;
		}

		@Override
		public boolean visit(StaticConstantAccess access) {
			if (!"class".equalsIgnoreCase(access.getConstant().getName())) { //$NON-NLS-1$
				highlight(access.getConstant());
			}
			return true;
		}

		@Override
		public boolean visit(FieldsDeclaration fieldsDeclaration) {
			for (AttributeGroup g : fieldsDeclaration.attributes()) {
				g.accept(this);
			}
			for (Expression expr : fieldsDeclaration.getInitialValues()) {
				if (expr != null) {
					expr.accept(this);
				}
			}
			return false;
		}

		public boolean visit(DNFType namespace) {
			return false;
		}

		@Override
		public boolean visit(NamespaceName namespace) {
			ASTNode parent = namespace.getParent();
			if (!(parent instanceof NamespaceDeclaration) && !(parent instanceof StaticDispatch)
					&& !(parent instanceof FunctionName) && !(parent instanceof UseStatement)
					&& !(parent instanceof DNFType)) {
				List<Identifier> segs = namespace.segments();
				if (segs.size() > 0) {
					Identifier c = segs.get(segs.size() - 1);
					if (PHPElementConciliator.concile(c) == PHPElementConciliator.CONCILIATOR_CONSTANT) {
						highlight(c);
					}
				}
			}
			return true;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new ConstantApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(true).setDefaultTextColor(0, 0, 192).setItalicByDefault(true);
	}

	@Override
	public String getDisplayName() {
		return Messages.ConstantHighlighting_0;
	}
}
