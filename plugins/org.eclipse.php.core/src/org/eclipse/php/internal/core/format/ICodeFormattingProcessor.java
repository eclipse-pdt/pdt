/**
 * 
 */
package org.eclipse.php.internal.core.format;

import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * A code formatting processor interface that its implementors should supply
 * functionalities for creating code indentations and {@link TextEdit}s.
 * 
 * @author shalom
 *
 */
public interface ICodeFormattingProcessor {

	/**
	 * Returns the text edits that represents the result of this visitor's code formatting
	 * process.
	 * 
	 * @return A {@link MultiTextEdit} that holds all the text edits that were aggregated during the 
	 * code formatter visitor's processing.
	 */
	public MultiTextEdit getTextEdits();

	/**
	 * Returns a string that represents the indentation 
	 * @param indentationUnits
	 * @return
	 */
	public String createIndentationString(int indentationUnits);

}
