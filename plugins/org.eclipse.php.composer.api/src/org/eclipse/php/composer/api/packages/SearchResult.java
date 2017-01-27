/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.composer.api.MinimalPackage;

public class SearchResult {
	public List<MinimalPackage> results;
	public String next;
	public String total;

	public SearchResult(Object obj) {
		fromJson(obj);
	}

	@SuppressWarnings("rawtypes")
	public void fromJson(Object obj) {
		if (obj instanceof LinkedHashMap) {
			LinkedHashMap json = (LinkedHashMap) obj;

			next = (String) json.get("next"); //$NON-NLS-1$
			total = json.get("total").toString(); //$NON-NLS-1$
			results = new LinkedList<MinimalPackage>();
			Object r = json.get("results"); //$NON-NLS-1$

			if (r instanceof LinkedList) {
				for (Object p : (LinkedList) r) {
					results.add(new MinimalPackage(p));
				}
			}
		}
	}
}
