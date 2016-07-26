/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.core.PHPVersion;

class SimpleProposal {

	public static final SimpleProposal[] BASIC_TYPES = new SimpleProposal[] {
			// http://php.net/manual/en/functions.arguments.php#functions.arguments.type-declaration.types
			new SimpleProposal("self"), //$NON-NLS-1$
			new SimpleProposal("array"), //$NON-NLS-1$
			new SimpleProposal("callable", PHPVersion.PHP5_4), //$NON-NLS-1$
			new SimpleProposal("bool", PHPVersion.PHP7_0), //$NON-NLS-1$
			new SimpleProposal("float", PHPVersion.PHP7_0), //$NON-NLS-1$
			new SimpleProposal("int", PHPVersion.PHP7_0), //$NON-NLS-1$
			new SimpleProposal("string", PHPVersion.PHP7_0), //$NON-NLS-1$

			new SimpleProposal("parent", PHPVersion.PHP5_3), //$NON-NLS-1$
			new SimpleProposal("Closure"), //$NON-NLS-1$

			new SimpleProposal("iterable", PHPVersion.PHP7_1) //$NON-NLS-1$
	};

	public String proposal;
	public PHPVersion validSince;

	public SimpleProposal(String proposal) {
		this(proposal, null);
	}

	public SimpleProposal(String proposal, PHPVersion validSince) {
		Assert.isNotNull(proposal);
		this.proposal = proposal;
		this.validSince = validSince;
	}

	public boolean isValid(String prefix, PHPVersion phpVersion) {
		if (validSince != null && !(validSince == phpVersion || validSince.isLessThan(phpVersion))) {
			return false;
		}
		return getProposal().startsWith(prefix);
	}

	public String getProposal() {
		return proposal;
	}

}