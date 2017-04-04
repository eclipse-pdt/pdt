/*********************************************************import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.swt.graphics.RGB;
rg/legal/epl-v10.html
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class ParameterVariableHighlighting extends AbstractSemanticHighlighting {

	protected class ParameterVariableApply extends AbstractSemanticApply {

		private Collection<String> params = new LinkedList<String>();

		@Override
		public boolean visit(FunctionDeclaration functionDecl) {
			for (FormalParameter param : functionDecl.formalParameters()) {
				params.add(param.getParameterNameIdentifier().getName());
			}
			return true;
		}

		@Override
		public void endVisit(Variable variable) {
			if (variable.getParent().getType() != ASTNode.FIELD_ACCESS
					|| (variable.getParent().getType() == ASTNode.FIELD_ACCESS
							&& ((FieldAccess) variable.getParent()).getDispatcher() == variable)) {
				Expression varName = variable.getName();
				if (params.contains(((Identifier) varName).getName()) && varName instanceof Identifier
						&& ((variable.isDollared() && variable.getParent().getType() != ASTNode.STATIC_FIELD_ACCESS)
								|| (!variable.isDollared() && org.eclipse.php.internal.core.corext.ASTNodes
										.isQuotedDollaredCurlied(variable)))) {
					highlight(variable);
				}
			}
		}

		@Override
		public void endVisit(FunctionDeclaration functionDecl) {
			params.clear();
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new ParameterVariableApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(true).setUnderlineByDefault(true);
	}

	@Override
	public String getDisplayName() {
		return Messages.ParameterVariableHighlighting_0;
	}
}