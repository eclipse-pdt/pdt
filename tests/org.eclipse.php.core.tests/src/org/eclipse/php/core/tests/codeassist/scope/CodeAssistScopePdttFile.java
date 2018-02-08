/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - Assist Scope Tests
 *******************************************************************************/
package org.eclipse.php.core.tests.codeassist.scope;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.tests.PdttFile;

/**
 * This is an extension of {@link PdttFile} that parses the --EXPECT-- section
 * into expected proposals list. The format of this section must be as follows:
 * 
 * <pre>
 * TRAIT(3,4)
 * NAMESPACE(1,9)
 * FILE(0,10)
 * </pre>
 */
public class CodeAssistScopePdttFile extends PdttFile {

	static public class ExpectedScope {
		public ICompletionScope.Type type;
		public int offset;
		public int length;
		public String name = null;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof ICompletionScope) {
				ICompletionScope scope = (ICompletionScope) obj;
				if (scope.getType() != type || scope.getLength() != length || scope.getOffset() != offset) {
					return false;
				}
				String objName = scope.getName();
				if (name == null && objName != null) {
					return false;
				} else if (name != null && !name.equals(objName)) {
					return false;
				}

				return true;
			}
			return super.equals(obj);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(type.name()).append('(').append(offset).append(',').append(length);
			if (name != null) {
				sb.append(',').append(name);
			}
			return sb.append(')').toString();
		}
	}

	private ExpectedScope[] expectedScopes;

	public CodeAssistScopePdttFile(String fileName) throws Exception {
		super(fileName);
	}

	public ExpectedScope[] getExpectedScopes() {
		return expectedScopes;
	}

	@Override
	protected void parse(InputStream stream, String charsetName) throws Exception {
		super.parse(stream, null);

		List<ExpectedScope> expectedScopes = new LinkedList<>();
		String[] lines = getExpected().split("\n");
		for (String line : lines) {
			int i = line.indexOf('(');
			int j = line.indexOf(')');
			if (i == -1 || j == -1) { // wrong format
				continue;
			}
			ExpectedScope proposal = new ExpectedScope();
			proposal.type = ICompletionScope.Type.valueOf(line.substring(0, i));
			String[] nums = line.substring(i + 1, j).split(",", 3);
			if (nums.length != 2 && nums.length != 3) {
				throw new Exception("Invalid sope arguments!");
			}
			proposal.offset = Integer.valueOf(nums[0]);
			proposal.length = Integer.valueOf(nums[1]);
			if (nums.length == 3) {
				proposal.name = nums[2];
			}
			expectedScopes.add(proposal);
		}
		this.expectedScopes = expectedScopes.toArray(new ExpectedScope[expectedScopes.size()]);
	}

}
