/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.Arrays;

public class SimilarElement {

	private final int fKind;
	private final String fName;
	private final String[] fTypesParameters;
	private final int fRelevance;

	public SimilarElement(int kind, String name, int relevance) {
		this(kind, name, null, relevance);
	}

	public SimilarElement(int kind, String name, String[] typesParameters, int relevance) {
		fKind = kind;
		fName = name;
		fTypesParameters = typesParameters;
		fRelevance = relevance;
	}

	/**
	 * Gets the kind.
	 * 
	 * @return Returns a int
	 */
	public int getKind() {
		return fKind;
	}

	/**
	 * Gets the parameter types.
	 * 
	 * @return Returns a int
	 */
	public String[] getTypesParameter() {
		return fTypesParameters;
	}

	/**
	 * Gets the name.
	 * 
	 * @return Returns a String
	 */
	public String getName() {
		return fName;
	}

	/**
	 * Gets the relevance.
	 * 
	 * @return Returns a int
	 */
	public int getRelevance() {
		return fRelevance;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimilarElement) {
			SimilarElement elem = (SimilarElement) obj;
			return fName.equals(elem.fName) && fKind == elem.fKind
					&& Arrays.equals(fTypesParameters, elem.fTypesParameters);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return fName.hashCode() + fKind;
	}
}
