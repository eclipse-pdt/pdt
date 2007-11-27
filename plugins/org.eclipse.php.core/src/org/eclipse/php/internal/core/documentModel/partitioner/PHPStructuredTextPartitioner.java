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
package org.eclipse.php.internal.core.documentModel.partitioner;

import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.html.core.internal.text.StructuredTextPartitionerForHTML;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class PHPStructuredTextPartitioner extends StructuredTextPartitionerForHTML {

	public String getContentType(final int offset, final boolean preferOpenPartitions) {
		final ITypedRegion partition = getPartition(offset);
		return partition == null ? null : partition.getType();
	}

	public String getPartitionType(final ITextRegion region, final int offset) {
		// if php region
		if (isPhpRegion(region.getType()))
			return PHPPartitionTypes.PHP_DEFAULT;

		// else do super 
		return super.getPartitionType(region, offset);
	}

	/**
	 * @param regionType
	 * @return
	 */
	private static final boolean isPhpRegion(final String regionType) {
		return regionType == PHPRegionContext.PHP_OPEN || regionType == PHPRegionContext.PHP_CLOSE || regionType == PHPRegionContext.PHP_CONTENT;
	}
	
	private final static String[] configuredContentTypes = new String[] { PHPPartitionTypes.PHP_DEFAULT, PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT, PHPPartitionTypes.PHP_MULTI_LINE_COMMENT, PHPPartitionTypes.PHP_DOC, PHPPartitionTypes.PHP_QUOTED_STRING };

	public static String[] getConfiguredContentTypes() {
		return configuredContentTypes;
	}
	
	public static boolean isPHPPartitionType(final String type) {
		for (int i = 0; i < configuredContentTypes.length; i++)
			if (configuredContentTypes[i].equals(type))
				return true;
		return false;
	}

	public IDocumentPartitioner newInstance() {
		return new PHPStructuredTextPartitioner();
	}

	public ITypedRegion getPartition(int offset) {
		
		// in case we are in the end of document
		// we return the partition of last region
		int docLength = fStructuredDocument.getLength();
		if (offset == docLength) {
			return super.getPartition(offset - 1);
		}
		return super.getPartition(offset);
	}
	
	
}
