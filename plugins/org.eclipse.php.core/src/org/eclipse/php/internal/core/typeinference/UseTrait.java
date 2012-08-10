package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseTrait {

	private List<String> traits = new ArrayList<String>();
	private List<TraitPrecedenceObject> traitPrecedences = new ArrayList<TraitPrecedenceObject>();
	private List<TraitAliasObject> traitAliases = new ArrayList<TraitAliasObject>();
	private Map<String, TraitPrecedenceObject> precedenceMap = new HashMap<String, TraitPrecedenceObject>();
	private Map<String, List<TraitAliasObject>> aliasMap = new HashMap<String, List<TraitAliasObject>>();

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
