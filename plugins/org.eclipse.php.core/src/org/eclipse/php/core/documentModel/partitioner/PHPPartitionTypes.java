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

import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;

public abstract class PHPPartitionTypes {

	public static final String PHP_DEFAULT = "org.eclipse.php.PHP_DEFAULT";
	public static final String PHP_SINGLE_LINE_COMMENT = "org.eclipse.php.PHP_SINGLE_LINE_COMMENT";
	public static final String PHP_MULTI_LINE_COMMENT = "org.eclipse.php.PHP_MULTI_LINE_COMMENT";
	public static final String PHP_DOC = "org.eclipse.php.PHP_DOC";
	public static final String PHP_QUOTED_STRING = "org.eclipse.php.PHP_QUOTED_STRING";

	public static boolean isPHPCommentState(final String type) {
		return type == null ? false : isPHPMultiLineCommentState(type) || isPHPLineCommentState(type) || isPHPDocState(type);
	}

	public static boolean isPHPDocState(final String type) {
		return type == null ? false : type.startsWith("PHPDOC");
	}

	public static boolean isPHPLineCommentState(final String type) {
		return type == PHPRegionTypes.PHP_LINE_COMMENT;
	}

	public static boolean isPHPMultiLineCommentState(final String type) {
		return type == PHPRegionTypes.PHP_COMMENT || type == PHPRegionTypes.PHP_COMMENT_START || type == PHPRegionTypes.PHP_COMMENT_END;
	}

	public static boolean isPHPQuotesState(final String type) {
		return type == PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING || type == PHPRegionTypes.PHP_HEREDOC_TAG;
	}

	public static final boolean isPHPRegularState(final String type) {
		return !isPHPCommentState(type) && !isPHPQuotesState(type);
	}
}
