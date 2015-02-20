/*******************************************************************************
 * Copyright (c) 2011, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.ui.tests.formatter.autoedit;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuffer;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.php.ui.tests.PHPUiTests;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@RunWith(PDTTList.class)
public class FormatterAutoEditTests {

	protected static final char OFFSET_CHAR = '|';

	protected IProject project;
	protected int count;

	protected PHPVersion phpVersion;
	protected String[] fileNames;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/formatter-autoedit" });
	};

	public FormatterAutoEditTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
		this.fileNames = fileNames;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("FormatterTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		WorkingCopyOwner.setPrimaryBufferProvider(new WorkingCopyOwner() {
			public IBuffer createBuffer(ISourceModule workingCopy) {
				ISourceModule original = workingCopy.getPrimary();
				IResource resource = original.getResource();
				if (resource != null) {
					if (resource instanceof IFile) {
						return new DocumentAdapter(workingCopy,
								(IFile) resource);
					}
				}
				return DocumentAdapter.NULL;
			}
		});

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		PHPCoreTests.setProjectPhpVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}

	@Test
	public void formatter(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);
		IFile file = createFile(pdttFile.getFile().trim());
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
		IStructuredModel modelForEdit = StructuredModelManager
				.getModelManager().getModelForEdit(file);
		try {
			IDocument document = modelForEdit.getStructuredDocument();
			String beforeFormat = document.get();
			String data = document.get();
			int offset = data.lastIndexOf(OFFSET_CHAR);
			if (offset == -1) {
				throw new IllegalArgumentException(
						"Offset character is not set");
			}

			// replace the offset character
			data = data.substring(0, offset) + data.substring(offset + 1);

			document.set(data);

			IAutoEditStrategy indentLineAutoEditStrategy = new MainAutoEditStrategy();

			DocumentCommand cmd = new DocumentCommand() {
			};

			cmd.offset = offset;
			cmd.length = 0;
			if (pdttFile.getOther() != null) {
				cmd.text = pdttFile.getOther();
				if (cmd.text != null && cmd.text.trim().length() == 1) {
					// support single (non-blank) character insertion
					cmd.text = cmd.text.trim();
				}
			} else {
				cmd.text = "\n";
			}

			cmd.doit = true;
			cmd.shiftsCaret = true;
			cmd.caretOffset = -1;

			indentLineAutoEditStrategy.customizeDocumentCommand(document, cmd);
			document.replace(cmd.offset, cmd.length, cmd.text);

			PDTTUtils.assertContents(pdttFile.getExpected(), document.get());

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

	protected IFile createFile(String data) throws Exception {
		IFile testFile = project.getFile("test" + (++count) + ".php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		return testFile;
	}

}
