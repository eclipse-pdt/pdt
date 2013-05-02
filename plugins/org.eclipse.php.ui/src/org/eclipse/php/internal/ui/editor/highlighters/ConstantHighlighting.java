/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.List;

import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.ast.nodes.*;
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

		public boolean visit(Quote quote) {
			isInQuote = true;
			return true;
		}

		public void endVisit(Quote quote) {
			isInQuote = false;
		}

		@Override
		public boolean visit(Scalar scalar) {
			String value = scalar.getStringValue();
			if ((scalar.getScalarType() == Scalar.TYPE_STRING && !isInQuote || scalar
					.getScalarType() == Scalar.TYPE_SYSTEM)
					&& !"null".equals(value) //$NON-NLS-1$
					&& !"false".equals(value) //$NON-NLS-1$
					&& !"true".equals(value) //$NON-NLS-1$
					&& value.charAt(0) != '\''
					&& value.charAt(0) != '"') {
				highlight(scalar);
			} else if (scalar.getParent() instanceof FunctionInvocation) {// for
																			// define
																			// function
				FunctionInvocation fi = (FunctionInvocation) scalar.getParent();
				if (fi.parameters().get(0) == scalar) {
					if (fi.getFunctionName().getName() instanceof Identifier) {
						final Identifier identifier = (Identifier) fi
								.getFunctionName().getName();
						if ("define".equalsIgnoreCase(identifier.getName()) || "constant".equalsIgnoreCase(identifier.getName())) {//$NON-NLS-1$ //$NON-NLS-2$
							highlight(scalar);
						}
					}
				}
			}
			return true;
		}

		@Override
		public boolean visit(StaticConstantAccess access) {
			highlight(access.getConstant());
			return true;
		}

		@Override
		public boolean visit(NamespaceName namespace) {
			ASTNode parent = namespace.getParent();
			if (!(parent instanceof NamespaceDeclaration)
					&& !(parent instanceof StaticDispatch)
					&& !(parent instanceof FunctionName)) {
				List<Identifier> segs = namespace.segments();
				Identifier c = segs.get(segs.size() - 1);
				if (PhpElementConciliator.concile(c) == PhpElementConciliator.CONCILIATOR_CONSTANT) {
					highlight(c);
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
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(0, 0, 192)
				.setItalicByDefault(true);
	}

	public String getDisplayName() {
		return Messages.ConstantHighlighting_0;
	}
}
