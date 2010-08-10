package org.eclipse.php.internal.ui.editor.configuration;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.ui.internal.provisional.style.AbstractLineStyleProvider;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;

public class StructuredDocumentDamagerRepairer extends DefaultDamagerRepairer {

	private LineStyleProvider fProvider = null;

	public StructuredDocumentDamagerRepairer(LineStyleProvider provider) {
		super(new RuleBasedScanner());
		Assert.isNotNull(provider);
		fProvider = provider;
	}

	public void createPresentation(TextPresentation presentation,
			ITypedRegion region) {
		PresentationCollector collector = new PresentationCollector(
				presentation);
		fProvider.prepareRegions(region, region.getOffset(),
				region.getLength(), collector);
	}

	public void setProvider(LineStyleProvider provider) {
		fProvider = provider;
	}

	public void setDocument(IDocument document) {
		super.setDocument(document);
		if (fProvider instanceof AbstractLineStyleProvider)
			((AbstractLineStyleProvider) fProvider)
					.setDocument((IStructuredDocument) document);
	}

}
