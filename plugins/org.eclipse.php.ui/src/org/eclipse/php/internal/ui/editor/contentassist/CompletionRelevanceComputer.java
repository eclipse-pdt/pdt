package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.HashMap;
import java.util.Map;

public class CompletionRelevanceComputer {

	protected static CompletionRelevanceComputer instance;

	protected Map<String, Integer> typeProposals = new HashMap<String, Integer>();

	private CompletionRelevanceComputer() {

	}

	public static CompletionRelevanceComputer getInstance() {
		if (instance == null) {
			instance = new CompletionRelevanceComputer();
		}

		return instance;
	}

	public void incrementTypeProposal(String fullyQualifiedName) {

		if (!typeProposals.containsKey(fullyQualifiedName)) {
			typeProposals.put(fullyQualifiedName, new Integer(1));
			return;
		}

		Integer curr = typeProposals.get(fullyQualifiedName);
		typeProposals.put(fullyQualifiedName, curr + 1);
	}

	public Integer getTypeRelevance(String fullyQualifiedName) {

		if (!typeProposals.containsKey(fullyQualifiedName)) {
			return 1;
		}

		return typeProposals.get(fullyQualifiedName);
	}
}
