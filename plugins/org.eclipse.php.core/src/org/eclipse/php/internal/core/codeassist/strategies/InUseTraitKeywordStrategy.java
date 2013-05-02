package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

public class InUseTraitKeywordStrategy extends KeywordsStrategy {

	// private static final String CALLABLE = "callable";
	public static final String[] KEYWORDS = { "as", "insteadof" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * @param context
	 * @param elementFilter
	 */
	public InUseTraitKeywordStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	/**
	 * @param context
	 */
	public InUseTraitKeywordStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		AbstractCompletionContext context = (AbstractCompletionContext) getContext();
		String prefix = context.getPrefix();
		String suffix = ""; //$NON-NLS-1$
		SourceRange replaceRange = getReplacementRange(context);
		// if (context.getEnclosingType() != null) {
		// try {
		// int flags = context.getEnclosingType().getFlags();
		// if (!PHPFlags.isNamespace(flags)) {
		// for (String keyword : KEYWORDS) {
		// if (keyword.startsWith(prefix)) {
		// reporter.reportKeyword(keyword, suffix,
		// replaceRange);
		// }
		// }
		// }
		// } catch (ModelException e) {
		// PHPCorePlugin.log(e);
		// }
		// }

		PHPVersion phpVersion = context.getPhpVersion();
		if (phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
			for (String keyword : KEYWORDS) {
				if (keyword.startsWith(prefix)) {
					reporter.reportKeyword(keyword, suffix, replaceRange);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.codeassist.strategies.KeywordsStrategy#
	 * filterKeyword
	 * (org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData)
	 */
	@Override
	protected boolean filterKeyword(KeywordData keyword) {
		return true;
	}
}