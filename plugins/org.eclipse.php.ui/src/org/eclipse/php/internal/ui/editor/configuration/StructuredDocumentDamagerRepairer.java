package org.eclipse.php.internal.ui.editor.configuration;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.provisional.style.AbstractLineStyleProvider;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;

public class StructuredDocumentDamagerRepairer extends DefaultDamagerRepairer {

	private LineStyleProvider fProvider = null;

	public StructuredDocumentDamagerRepairer(LineStyleProvider provider) {
		super(new RuleBasedScanner());
		Assert.isNotNull(provider);
		fProvider = provider;
	}

	/**
	 * Returns presentation which covers only updated php tokens in case when
	 * the damage covers whole PHP region and this regions has not been fully
	 * reparsed.
	 * 
	 * @param region
	 * @param damage
	 * @return presentation or null - if region has been fully reparsed
	 */
	public TextPresentation getPresentation(ITypedRegion region, IRegion damage) {
		if (fProvider instanceof LineStyleProviderForPhp) {
			IStructuredDocumentRegion structuredDocumentRegion = ((LineStyleProviderForPhp) fProvider)
					.getDamagedRegion(region);
			ITextRegion textReg = structuredDocumentRegion
					.getRegionAtCharacterOffset(region.getOffset());
			if (textReg != null
					&& textReg.getType() == PHPRegionContext.PHP_CONTENT
					&& damageCoversWholeRegion(region, textReg, damage)) {
				IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) textReg;
				if (!phpScriptRegion.isFullReparsed()) {
					Region r = new Region(
							phpScriptRegion.getUpdatedTokensStart()
									+ region.getOffset(),
							phpScriptRegion.getUpdatedTokensLength());
					return new TextPresentation((IRegion) r, 1000);
				}
			}
		}
		return null;
	}

	private boolean damageCoversWholeRegion(ITypedRegion originalRegion,
			ITextRegion textRegion, IRegion damage) {
		// if php content region is bigger than actual damaged
		// region
		if ((textRegion.getLength() == originalRegion.getLength())
				&& (textRegion.getStart() < originalRegion.getOffset())) {
			if ((originalRegion.getOffset() == damage.getOffset())
					&& (originalRegion.getLength() == damage.getLength())) {
				return true;
			}
		} else if ((textRegion.getStart() == damage.getOffset())
				&& (textRegion.getLength() == damage.getLength())) {
			return true;
		}
		return false;
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
