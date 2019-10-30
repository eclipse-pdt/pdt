/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
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
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.VarComment;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

/**
 * @author blind
 */
public class VarDocCommentHighlighting extends AbstractSemanticHighlighting {

	protected class VarDocCommentApply extends AbstractSemanticApply {
		@Override
		public boolean visit(Program program) {
			ISourceModule sourceModule = getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
			if (moduleDeclaration instanceof PHPModuleDeclaration) {
				for (VarComment comment : ((PHPModuleDeclaration) moduleDeclaration).getVarComments()) {
					highlight(comment.start(), comment.end() - comment.start());
				}
			}
			return false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new VarDocCommentApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setDefaultTextColor(85, 127, 95).setEnabledByDefault(true);
	}

	@Override
	public int getPriority() {
		return 111;
	}

	@Override
	public String getDisplayName() {
		return Messages.VarDocCommentHighlighting_0;
	}
}