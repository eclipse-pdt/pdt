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
package org.eclipse.php.core.tests.codeassist;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.core.tests.PdttFile;

/**
 * This is an extension of {@link PdttFile} that parses the --EXPECT-- section
 * into expected proposals list. The format of this section must be as follows:
 * 
 * <pre>
 * type(A)
 * method(A)
 * field(A)
 * </pre>
 */
public class CodeAssistPdttFile extends PdttFile {

	static public class ExpectedProposal {
		/** Element name: {@link IModelElement#getElementName()} */
		public String name;
		/** Element type: {@link IModelElement#getElementType()} */
		public int type;
	}

	private ExpectedProposal[] expectedProposals;

	public CodeAssistPdttFile(String fileName) throws Exception {
		super(fileName);
	}

	public ExpectedProposal[] getExpectedProposals() {
		return expectedProposals;
	}

	@Override
	protected void parse(InputStream stream, String charsetName) throws Exception {
		super.parse(stream, null);

		List<ExpectedProposal> expectedProposals = new LinkedList<>();
		String[] lines = getExpected().split("\n");
		for (String line : lines) {
			int i = line.indexOf('(');
			int j = line.indexOf(')');
			if (i == -1 || j == -1) { // wrong format
				continue;
			}
			String type = line.substring(0, i);
			ExpectedProposal proposal = new ExpectedProposal();
			proposal.name = line.substring(i + 1, j);
			if ("type".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.TYPE;
			} else if ("method".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.METHOD;
			} else if ("field".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.FIELD;
			} else if ("sourceModule".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.SOURCE_MODULE;
			} else if ("archiveFolder".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.SCRIPT_FOLDER;
			} else if ("keyword".equalsIgnoreCase(type)) {
			} else { // wrong format
				continue;
			}
			expectedProposals.add(proposal);
		}
		this.expectedProposals = expectedProposals.toArray(new ExpectedProposal[expectedProposals.size()]);
	}
}
