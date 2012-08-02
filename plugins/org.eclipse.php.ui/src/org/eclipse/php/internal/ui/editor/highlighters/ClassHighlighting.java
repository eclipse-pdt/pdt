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

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class ClassHighlighting extends AbstractSemanticHighlighting {

	protected class ClassApply extends AbstractSemanticApply {

		@Override
		public boolean visit(ClassDeclaration clazz) {
			if (clazz instanceof TraitDeclaration) {
				return true;
			}
			highlight(clazz.getName());
			Expression superClass = clazz.getSuperClass();
			if (superClass instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) superClass);
			} else if (superClass != null) {
				highlight(superClass);
			}
			return true;
		}

		@Override
		public boolean visit(ClassInstanceCreation clazz) {
			Expression name = clazz.getClassName().getName();
			if (name instanceof Identifier) {
				highlight(name);
			} else if (name instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) name);
			}
			return true;
		}

		@Override
		public boolean visit(FormalParameter param) {
			Expression type = param.getParameterType();
			if (type instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) type);
			} else if (type instanceof Identifier) {
				highlight(type);
			}
			return true;
		}

		private void highlightNamespaceType(NamespaceName name) {
			List<Identifier> segments = name.segments();
			Identifier segment = segments.get(segments.size() - 1);
			highlight(segment);
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new ClassApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(0, 80, 50);
	}

	public String getDisplayName() {
		return "Classes";
	}
}