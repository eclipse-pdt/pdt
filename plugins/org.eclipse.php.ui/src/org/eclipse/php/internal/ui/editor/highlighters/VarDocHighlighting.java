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
public class VarDocHighlighting extends AbstractSemanticHighlighting {

	protected class VarDocApply extends AbstractSemanticApply {
		@Override
		public boolean visit(Program program) {
			ISourceModule sourceModule = getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
			if (moduleDeclaration instanceof PHPModuleDeclaration) {
				for (VarComment comment : ((PHPModuleDeclaration) moduleDeclaration).getVarComments()) {
					highlight(comment.getVarTagStart(), 4); // 4 =
															// "@var".length()
				}
			}
			return false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new VarDocApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setDefaultTextColor(127, 175, 143).setBoldByDefault(true).setEnabledByDefault(true);
	}

	@Override
	public int getPriority() {
		return 112;
	}

	@Override
	public String getDisplayName() {
		return Messages.VarDocHighlighting_0;
	}
}