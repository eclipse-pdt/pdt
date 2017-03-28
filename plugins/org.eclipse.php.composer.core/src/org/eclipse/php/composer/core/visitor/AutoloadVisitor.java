/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.visitor;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.core.model.ModelAccess;
import org.eclipse.php.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.core.compiler.ast.nodes.ArrayElement;
import org.eclipse.php.core.compiler.ast.nodes.InfixExpression;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;

@SuppressWarnings("restriction")
public class AutoloadVisitor extends PHPASTVisitor {
	protected ISourceModule source;
	private NamespaceVisitor visitor;

	public AutoloadVisitor(ISourceModule source) {
		this.source = source;
	}

	@Override
	public boolean visit(ArrayCreation s) throws Exception {
		visitor = new NamespaceVisitor();
		s.traverse(visitor);

		Psr psr0 = visitor.getPsr0();

		if (psr0.size() > 0) {
			ModelAccess.getInstance().updatePsr0(psr0, source.getScriptProject());
		}

		return true;
	}

	public Psr getPsr0() {

		if (visitor != null) {
			return visitor.getPsr0();
		}

		return null;
	}

	protected class NamespaceVisitor extends PHPASTVisitor {

		protected Psr psr0 = new Psr();

		@Override
		public boolean visit(ArrayElement element) throws Exception {
			if (!(element.getKey() instanceof Scalar)) {
				return false;
			}

			if (element.getValue() instanceof InfixExpression) {
				Scalar namespace = (Scalar) element.getKey();
				Scalar path = (Scalar) ((InfixExpression) element.getValue()).getRight();
				VariableReference reference = (VariableReference) ((InfixExpression) element.getValue()).getLeft();
				extractPsr0(namespace, path, reference);
				return false;
			} else if (element.getValue() instanceof ArrayCreation) {
				Scalar namespace = (Scalar) element.getKey();
				ArrayCreation paths = (ArrayCreation) element.getValue();
				for (ArrayElement elem : paths.getElements()) {
					if (elem.getValue() instanceof InfixExpression) {
						Scalar path = (Scalar) ((InfixExpression) elem.getValue()).getRight();
						VariableReference reference = (VariableReference) ((InfixExpression) elem.getValue()).getLeft();
						extractPsr0(namespace, path, reference);
					}
					return false;
				}
			}

			return true;
		}

		protected void extractPsr0(Scalar namespace, Scalar path, VariableReference reference) {
			String resourcePath = ""; //$NON-NLS-1$

			if ("$baseDir".equals(reference.getName())) { //$NON-NLS-1$
				resourcePath = path.getValue().replace("'", ""); //$NON-NLS-1$ //$NON-NLS-2$
			} else if ("$vendorDir".equals(reference.getName())) { //$NON-NLS-1$
				resourcePath = "vendor" + path.getValue().replace("'", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}

			if (resourcePath.startsWith("/")) { //$NON-NLS-1$
				resourcePath = resourcePath.replaceFirst("/", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}

			String ns = namespace.getValue().replace("'", "").replace("\\\\", "\\"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			psr0.add(new Namespace(ns, resourcePath));
		}

		public Psr getPsr0() {
			return psr0;
		}
	}
}
