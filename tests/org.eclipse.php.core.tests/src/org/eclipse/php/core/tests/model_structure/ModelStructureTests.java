/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.model_structure;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementVisitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.core.PHPVersion;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class ModelStructureTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/model_structure/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/model_structure/php53" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/model_structure/php53" });
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/model_structure/php53" });
		TESTS.put(PHPVersion.PHP7_0, new String[] { "/workspace/model_structure/php53" });
	};

	protected IProject project;
	protected IFile testFile;
	protected PHPVersion version;

	public ModelStructureTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("ModelStructureTests");
		TestUtils.setProjectPhpVersion(project, version);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Test
	public void structure(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);
		ISourceModule sourceModule = createFile(pdttFile.getFile());

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(stream);
		sourceModule.accept(new PrintVisitor(printStream));
		printStream.close();

		PDTTUtils.assertContents(pdttFile.getExpected(), stream.toString());
	}

	@After
	public void after() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
	}

	/**
	 * Creates test file with the specified content and calculates the offset at
	 * OFFSET_CHAR. Offset character itself is stripped off.
	 * 
	 * @param data
	 *            File data
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected ISourceModule createFile(String data) throws Exception {
		testFile = TestUtils.createFile(project, "test.php", data);
		TestUtils.waitForIndexer();
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	class PrintVisitor implements IModelElementVisitor {

		private PrintStream stream;

		public PrintVisitor(PrintStream stream) {
			this.stream = stream;
		}

		public boolean visit(IModelElement element) {
			try {
				String tabs = getTabs(element);
				stream.print(tabs);

				if (element.getElementType() == IModelElement.TYPE) {
					IType type = (IType) element;

					int flags = type.getFlags();
					if ((flags & Modifiers.AccInterface) != 0) {
						stream.print("INTERFACE: ");
					} else if ((flags & Modifiers.AccNameSpace) != 0) {
						stream.print("NAMESPACE: ");
					} else {
						stream.print("CLASS: ");
					}
				} else if (element.getElementType() == IModelElement.METHOD) {
					IMethod method = (IMethod) element;
					IType declaringType = method.getDeclaringType();
					if (declaringType == null || (declaringType.getFlags() & Modifiers.AccNameSpace) != 0) {
						stream.print("FUNCTION: ");
					} else {
						stream.print("METHOD: ");
					}
				} else if (element.getElementType() == IModelElement.FIELD) {
					stream.print("VARIABLE: ");
				} else if (element.getElementType() == IModelElement.SOURCE_MODULE) {
					stream.print("FILE: ");
				}

				stream.println(element.getElementName());

			} catch (ModelException e) {
			}
			return true;
		}

		protected String getTabs(IModelElement e) {
			StringBuilder buf = new StringBuilder();
			while (e.getElementType() != IModelElement.SOURCE_MODULE) {
				buf.append('\t');
				e = e.getParent();
			}
			return buf.toString();
		}
	}
}
