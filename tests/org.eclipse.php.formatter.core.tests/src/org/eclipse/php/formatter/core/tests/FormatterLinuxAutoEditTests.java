/*******************************************************************************
 * Copyright (c) 2013, 2014 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.formatter.core.tests;

import java.util.LinkedHashMap;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class FormatterLinuxAutoEditTests extends FormatterTests {

	protected static final char OFFSET_CHAR = '|';

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/formatter-autoedit/php5" });
	};

	public FormatterLinuxAutoEditTests(PHPVersion version, String[] fileNames) throws Exception {
		super(version, fileNames);
	}

	@Override
	@Test
	public void formatter(String fileName) throws Exception {
		IFile file = files.get(fileName);
		PdttFile pdttFile = pdttFiles.get(fileName);
		ISourceModule modelElement = (ISourceModule) DLTKCore.create(file);
		if (ScriptModelUtil.isPrimary(modelElement))
			modelElement.becomeWorkingCopy(new IProblemRequestor() {

				public void acceptProblem(IProblem problem) {
				}

				public void beginReporting() {
				}

				public void endReporting() {
				}

				public boolean isActive() {
					return false;
				}
			}, null);
		IStructuredModel modelForEdit = StructuredModelManager.getModelManager().getModelForEdit(file);
		try {
			IDocument document = modelForEdit.getStructuredDocument();
			String beforeFormat = document.get();
			String data = document.get();
			int offset = data.lastIndexOf(OFFSET_CHAR);
			if (offset == -1) {
				throw new IllegalArgumentException(data + ",offset character is not set");
			}

			// replace the offset character
			data = data.substring(0, offset) + data.substring(offset + 1);

			document.set(data);

			MainAutoEditStrategy indentLineAutoEditStrategy = new MainAutoEditStrategy();

			DocumentCommand cmd = new DocumentCommand() {

			};

			cmd.offset = offset;
			cmd.length = 0;
			if (pdttFile.getOther() != null) {
				cmd.text = pdttFile.getOther().replaceAll("\r\n", "\n");
			} else {
				cmd.text = "\n";
			}

			cmd.doit = true;
			cmd.shiftsCaret = true;
			cmd.caretOffset = -1;

			indentLineAutoEditStrategy.customizeDocumentCommand(document, cmd);
			document.replace(cmd.offset, cmd.length, cmd.text);

			PDTTUtils.assertContents(pdttFile.getExpected().replaceAll("\r\n", "\n"), document.get());

			// change the document text as was before
			// the formatting
			document.set(beforeFormat);
			modelForEdit.save();
		} finally {
			if (modelForEdit != null) {
				modelForEdit.releaseFromEdit();
			}
		}
	}

}
