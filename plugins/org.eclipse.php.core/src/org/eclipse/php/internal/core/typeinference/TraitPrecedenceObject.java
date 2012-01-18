package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.List;

public class TraitPrecedenceObject {
	public String traitName;
	public String traitMethodName;
	public List<String> insteadofTraitNameList = new ArrayList<String>();

}
