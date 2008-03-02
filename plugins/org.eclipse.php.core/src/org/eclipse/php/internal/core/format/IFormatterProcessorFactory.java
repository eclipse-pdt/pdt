/**
 * 
 */
package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

/**
 * Implementors of this interface should supply an {@link ICodeFormattingProcessor} for PHP code
 * formatting.
 * 
 * @author shalom
 */
public interface IFormatterProcessorFactory {

	/**
	 * Returns an {@link ICodeFormattingProcessor}.
	 * 
	 * @param document 
	 * @param phpVersion The PHP version.
	 * @param region An {@link IRegion}
	 * @return An ICodeFormattingProcessor that will format the PHP code.
	 * @throws Exception 
	 */
	public ICodeFormattingProcessor getCodeFormattingProcessor(IDocument document, String phpVersion, IRegion region) throws Exception;
}
