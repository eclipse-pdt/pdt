package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.CompletionRequestor;
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
		
		for (String nextTag : PHPDOC_TAGS) {
			if (CodeAssistUtils.startsWithIgnoreCase(nextTag, tagName)) {
				if (!requestor.isContextInformationMode() || nextTag.length() == tagName.length()) {
					
					// Tags are reported like keywords:
					reporter.reportKeyword(nextTag, "");
				}
			}
		}
	}

}
