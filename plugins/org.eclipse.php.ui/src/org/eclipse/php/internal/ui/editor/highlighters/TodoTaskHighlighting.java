/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

/**
 * @author blind
 */
public class TodoTaskHighlighting extends AbstractSemanticHighlighting {

	protected class TodoTaskApply extends AbstractSemanticApply {
		@Override
		public boolean visit(Program program) {
			ISourceModule sourceModule = getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
			if (moduleDeclaration instanceof PHPModuleDeclaration) {
				for (PHPDocBlock phpDocBlock : ((PHPModuleDeclaration) moduleDeclaration).getPhpDocBlocks()) {
					List<Scalar> todoTasks = phpDocBlock.getTodoTasks();
					if (todoTasks != null) {
						for (Scalar todoTask : todoTasks) {
							highlight(todoTask.start(), todoTask.end() - todoTask.start());
						}
					}
				}
			}
			return false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new TodoTaskApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setDefaultTextColor(124, 165, 213).setBoldByDefault(true).setEnabledByDefault(true);
	}

	public String getDisplayName() {
		return Messages.TodoTaskHighlighting_0;
	}
}