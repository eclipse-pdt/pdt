/**
 * 
 */
package org.eclipse.php.internal.core.format;

import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.text.edits.MultiTextEdit;

/**
 * An empty implementation of the {@link ICodeFormattingProcessor} that performs visits on 
 * the ASTNode but does not aggregate any text edits.
 * 
 * @author shalom
 */
public class NullCodeFormattingProcessor extends AbstractVisitor implements ICodeFormattingProcessor {

	public String createIndentationString(int indentationUnits) {
		return ""; //$NON-NLS-1$
	}

	public MultiTextEdit getTextEdits() {
		return new MultiTextEdit();
	}

}
