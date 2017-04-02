/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
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
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocument;
import org.eclipse.wst.sse.ui.internal.spelling.ISpellcheckDelegate;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;

// this file content is taken from org.eclipse.wst.xml.ui.internal.spelling.SpellcheckDelegateAdapterFactory
// and org.eclipse.php.internal.core.format.PHPHeuristicScanner

/**
 * An <code>IAdapterFactory</code> to adapt an <code>IDOMModel</code> to a
 * <code>ISpellcheckDelegate</code>.
 */
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
		@Override
		public boolean shouldSpellcheck(int offset, IStructuredModel model) {
			if (model instanceof DOMModelForPHP) {
				IStructuredDocument doc = model.getStructuredDocument();
				if (doc instanceof BasicStructuredDocument) {
					IStructuredDocumentRegion sdRegion = ((BasicStructuredDocument) doc)
							.getRegionAtCharacterOffset(offset);

					try {
						if (sdRegion != null) {
							ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(offset);
							int startRegion = sdRegion.getStartOffset();
							// in case of container we have to extract the
							// PhpScriptRegion
							if (textRegion instanceof ITextRegionContainer) {
								ITextRegionContainer container = (ITextRegionContainer) textRegion;
								startRegion += container.getStart();
								textRegion = container.getRegionAtCharacterOffset(offset);
							}
							if (textRegion instanceof IPhpScriptRegion) {
								IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) textRegion;
								startRegion += phpScriptRegion.getStart();
								String partition = phpScriptRegion.getPartition(offset - startRegion);
								if (partition.equals(PHPPartitionTypes.PHP_QUOTED_STRING)
										|| partition.equals(PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT)
										|| partition.equals(PHPPartitionTypes.PHP_MULTI_LINE_COMMENT)
										|| partition.equals(PHPPartitionTypes.PHP_DOC)) {
									return true;
								}
							}
						}
						// not a PHP script region, then it must be HTML ->
						// enable spellcheck
						return true;
					} catch (BadLocationException e) {
					}
				}
			}

			return false;
		}

	}

	/**
	 * Adapts <code>IDOMModel</code> to <code>ISpellcheckDelegate</code>.
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
	 *      java.lang.Class)
	 */
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		ISpellcheckDelegate decision = null;

		if (adaptableObject instanceof IDOMModel && ISpellcheckDelegate.class.equals(adapterType)) {
			decision = DELEGATE;
		}

		return decision;
	}

	/**
	 * This adapter only adapts to <code>ISpellcheckDelegate</code>
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@Override
	public Class[] getAdapterList() {
		return ADAPTER_LIST;
	}

}
