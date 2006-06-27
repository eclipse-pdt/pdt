/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.documentModel.partitioner;

import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IDocumentPartitionerExtension2;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.core.documentModel.parser.PhpLexer;
import org.eclipse.php.core.documentModel.parser.regions.PHPContentRegion;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.html.core.internal.text.StructuredTextPartitionerForHTML;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class PHPStructuredTextPartitioner extends StructuredTextPartitionerForHTML implements IDocumentPartitionerExtension2 {

	private final static String[] configuredContentTypes = new String[] { PHPPartitionTypes.PHP_DEFAULT, PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT, PHPPartitionTypes.PHP_MULTI_LINE_COMMENT, PHPPartitionTypes.PHP_DOC, PHPPartitionTypes.PHP_QUOTED_STRING };

	public String getContentType(int offset, boolean preferOpenPartitions) {
		return getPartition(offset, preferOpenPartitions).getType();
	}

	public ITypedRegion[] computePartitioning(int offset, int length, boolean includeZeroLengthPartitions) {
		// seva: just to support the interface
		return computePartitioning(offset, length);
	}

	public String[] getManagingPositionCategories() {
		return null;
	}

	public ITypedRegion getPartition(int offset, boolean preferOpenPartitions) {
		ITypedRegion region = getPartition(offset);
		ITypedRegion newRegion;
		if (preferOpenPartitions) {
			if (region.getOffset() == offset) {
				if (offset > 0) {
					newRegion = getPartition(offset - 1);
					String newRegionType = newRegion.getType();
					if(newRegionType != PHPPartitionTypes.PHP_MULTI_LINE_COMMENT && newRegionType != PHPPartitionTypes.PHP_QUOTED_STRING)
						region = newRegion;
				}
			}
		}
		return region;
	}


	public IDocumentPartitioner newInstance() {
		return new PHPStructuredTextPartitioner();
	}

	public String getPartitionType(ITextRegion region, int offset) {
		String result = null;

		if (region instanceof PHPContentRegion) {
			if (PhpLexer.isPHPMultiLineCommentState(region.getType())) {
				result = PHPPartitionTypes.PHP_MULTI_LINE_COMMENT;
			} else if (PhpLexer.isPHPLineCommentState(region.getType())) {
				result = PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT;
			} else if (PhpLexer.isPHPDocState(region.getType())) {
				result = PHPPartitionTypes.PHP_DOC;
			} else if (PhpLexer.isPHPQuotesState(region.getType())) {
				result = PHPPartitionTypes.PHP_QUOTED_STRING;
			} else if (PHPRegionTypes.TASK.equals(region.getType())) {
				// return the previous region type
				IStructuredDocumentRegion docRegion = fStructuredDocument.getRegionAtCharacterOffset(offset);
				ITextRegion prevRegion = docRegion.getRegionAtCharacterOffset(docRegion.getStartOffset() + region.getStart() - 1);
				result = getPartitionType(prevRegion, offset);
			} else {
				result = PHPPartitionTypes.PHP_DEFAULT;
			}
		} else if (region.getType() == PHPRegionTypes.PHP_OPENTAG || region.getType() == PHPRegionTypes.PHP_CLOSETAG) {
			result = PHPPartitionTypes.PHP_DEFAULT;
		} else {
			result = super.getPartitionType(region, offset);
		}

		return result;
	}

	public static String[] getConfiguredContentTypes() {
		return configuredContentTypes;
	}

	public static boolean isPHPPartitionType(String type) {
		for (int i = 0; i < configuredContentTypes.length; i++) {
			if (configuredContentTypes[i].equals(type)) {
				return true;
			}
		}
		return false;
	}
}
