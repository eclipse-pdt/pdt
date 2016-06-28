/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

/**
 * Set of possible version constraints.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public enum VersionConstraint {

	NONE("none"), //$NON-NLS-1$

	GREATER(">"), //$NON-NLS-1$

	LESS("<"), //$NON-NLS-1$

	GREATER_EQUAL(">="), //$NON-NLS-1$

	LESS_EQUAL("<="), //$NON-NLS-1$

	APPROX("~"); //$NON-NLS-1$

	private String symbol;

	private VersionConstraint(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public static VersionConstraint bySymbol(String symbol) {
		if (symbol == null) {
			return NONE;
		}
		VersionConstraint[] values = values();
		for (VersionConstraint relation : values) {
			if (symbol.equals(relation.getSymbol())) {
				return relation;
			}
		}
		return NONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getSymbol();
	}

}
