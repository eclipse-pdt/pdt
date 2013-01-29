package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.PHPVersion;

public interface IContentFormatter2 {
	void format(IDocument document, IRegion region, PHPVersion phpVersion);
}
