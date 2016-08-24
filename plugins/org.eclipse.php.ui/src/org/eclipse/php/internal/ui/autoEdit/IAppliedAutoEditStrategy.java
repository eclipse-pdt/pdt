package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

public interface IAppliedAutoEditStrategy extends IAutoEditStrategy {
	/**
	 * @return returns true when strategy was successfully applied to current
	 *         command through
	 *         {@link IAutoEditStrategy#customizeDocumentCommand(IDocument, DocumentCommand)}
	 *         , even if command content may stay unchanged
	 */
	boolean wasApplied();
}
