package org.eclipse.php.internal.core.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;

public interface CompletionRequestorExtension {
	ICompletionContext[] createContexts();
}
