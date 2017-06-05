/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util.text;

import java.util.List;

public final class LCSS {

	private LCSS() {
	}

	private static final int[] EMPTY_SEQUENCE = new int[0];

	/**
	 * Returns the best, i.e, the longest continuous sequence - or the empty
	 * sequence if no subsequence could be found.
	 */
	public static int[] bestSubsequence(String completion, String token) {
		int bestScore = -1;
		int[] bestSequence = EMPTY_SEQUENCE;
		for (int[] s1 : findSequences(completion, token)) {
			int score = scoreSubsequence(s1);
			if (score > bestScore) {
				bestScore = score;
				bestSequence = s1;
			}
		}
		return bestSequence;
	}

	public static int scoreSubsequence(int[] s1) {
		int score = 0;
		for (int i = 0; i < s1.length - 1; i++) {
			if (s1[i] + 1 == s1[i + 1]) {
				score++;
			}
		}
		return score;
	}

	public static List<int[]> findSequences(String completion, String token) {
		return new SequenceFinder(completion, token).findSeqeuences();
	}

	public static boolean containsSubsequence(String completion, String token) {
		return !findSequences(completion, token).isEmpty();
	}
}
