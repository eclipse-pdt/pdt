package org.eclipse.php.internal.ui.spelling;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocument;
import org.eclipse.wst.sse.ui.internal.spelling.ISpellcheckDelegate;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;

// this file content is taken from org.eclipse.wst.xml.ui.internal.spelling.SpellcheckDelegateAdapterFactory
// and org.eclipse.php.internal.core.format.PHPHeuristicScanner

/**
 * An <code>IAdapterFactory</code> to adapt an <code>IDOMModel</code> to a
 * <code>ISpellcheckDelegate</code>.
 */
@SuppressWarnings("restriction")
public class SpellcheckDelegateAdapterFactory implements IAdapterFactory {
	/**
	 * This list of classes this factory adapts to.
	 */
	private static final Class[] ADAPTER_LIST = new Class[] { ISpellcheckDelegate.class };

	/**
	 * The <code>ISpellcheckDelegate</code> this factory adapts to
	 */
	private static final ISpellcheckDelegate DELEGATE = new PHPSpellcheckDelegate();

	/**
	 * Implementation of <code>ISpellcheckDelegate</code> for XML type
	 * documents.
	 */
	private static class PHPSpellcheckDelegate implements ISpellcheckDelegate {

		/**
		 * If the region in the given <code>model</code> at the given
		 * <code>offset</code> is a <code>Comment</code> region then it should
		 * be spell checked, otherwise it should not.
		 * 
		 * @see org.eclipse.wst.sse.ui.internal.spelling.ISpellcheckDelegate#shouldSpellcheck(org.eclipse.wst.sse.core.internal.provisional.IndexedRegion)
		 */
		public boolean shouldSpellcheck(int offset, IStructuredModel model) {
			boolean shouldSpellcheck = false;

			if (model instanceof DOMModelForPHP) {
				IStructuredDocument doc = ((DOMModelForPHP) model)
						.getStructuredDocument();
				if (doc instanceof BasicStructuredDocument) {
					IStructuredDocumentRegion sdRegion = ((BasicStructuredDocument) doc)
							.getRegionAtCharacterOffset(offset);
					ITextRegion textRegion = sdRegion
							.getRegionAtCharacterOffset(offset);
					try {
						if (textRegion instanceof IPhpScriptRegion) {
							IPhpScriptRegion phpReg = (IPhpScriptRegion) textRegion;
							String partition = phpReg.getPartition(offset
									- sdRegion.getStart());
							if (partition
									.equals(PHPPartitionTypes.PHP_QUOTED_STRING)
									|| partition
											.equals(PHPPartitionTypes.PHP_COMMENT)
									|| partition
											.equals(PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT)
									|| partition
											.equals(PHPPartitionTypes.PHP_MULTI_LINE_COMMENT)
									|| partition
											.equals(PHPPartitionTypes.PHP_DOC)) {
								shouldSpellcheck = true;
							}
						} else {
							// not a PHP script region, then it must be HTML ->
							// enable spellcheck
							shouldSpellcheck = true;
						}
					} catch (BadLocationException e) {

					}

				}

			}

			return shouldSpellcheck;
		}

	}

	/**
	 * Adapts <code>IDOMModel</code> to <code>ISpellcheckDelegate</code>.
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
	 *      java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		ISpellcheckDelegate decision = null;

		if (adaptableObject instanceof IDOMModel
				&& ISpellcheckDelegate.class.equals(adapterType)) {
			decision = DELEGATE;
		}

		return decision;
	}

	/**
	 * This adapter only adapts to <code>ISpellcheckDelegate</code>
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return ADAPTER_LIST;
	}

}
