package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

public class PHPDocTagStrategy implements ICompletionStrategy {

	protected static final String[] PHPDOC_TAGS = { "abstract", "access", "author", "category", "copyright", "deprecated", "example", "final", "filesource", "global", "ignore", "internal", "license", "link", "method", "name", "package", "param", "property", "return", "see", "since", "static",
		"staticvar", "subpackage", "todo", "tutorial", "uses", "var", "version" };

	public void apply(ICompletionContext context, ICompletionReporter reporter) {

		PHPDocTagContext phpdocTagContext = (PHPDocTagContext) context;
		String tagName = phpdocTagContext.getTagName();
		CompletionRequestor requestor = phpdocTagContext.getCompletionRequestor();

		SourceRange replaceRange = new SourceRange(phpdocTagContext.getOffset() - tagName.length(), tagName.length());
		String suffix = " "; //$NON-NLS-1$
		
		try {
			String nextWord = phpdocTagContext.getNextWord();
			if (nextWord.length() > 0 && Character.isWhitespace(nextWord.charAt(0))) {
				replaceRange = new SourceRange(replaceRange.getOffset(), replaceRange.getLength() + 1);
			}
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		
		for (String nextTag : PHPDOC_TAGS) {
			if (CodeAssistUtils.startsWithIgnoreCase(nextTag, tagName)) {
				if (!requestor.isContextInformationMode() || nextTag.length() == tagName.length()) {
					
					// Tags are reported like keywords:
					reporter.reportKeyword(nextTag, suffix, replaceRange);
				}
			}
		}
	}

}
