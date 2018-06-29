/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseTrait {

	private List<String> traits = new ArrayList<>();
	private List<TraitPrecedenceObject> traitPrecedences = new ArrayList<>();
	private List<TraitAliasObject> traitAliases = new ArrayList<>();
	private Map<String, TraitPrecedenceObject> precedenceMap = new HashMap<>();
	private Map<String, List<TraitAliasObject>> aliasMap = new HashMap<>();

	public List<String> getTraits() {
		return traits;
	}

	public List<TraitPrecedenceObject> getTraitPrecedences() {
		return traitPrecedences;
	}

	public List<TraitAliasObject> getTraitAliases() {
		return traitAliases;
	}

	public Map<String, List<TraitAliasObject>> getAliasMap() {
		return aliasMap;
	}

	public Map<String, TraitPrecedenceObject> getPrecedenceMap() {
		return precedenceMap;
	}

}
