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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementVisitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;

public class ModelStructureTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/model_structure/php53" });
	};

	protected static IProject project;
	protected static IFile testFile;

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("ModelStructureTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
	}

	public static void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public ModelStructureTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Auto Code Assist Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());
			
			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final PdttFile pdttFile = new PdttFile(fileName);
						phpVerSuite.addTest(new ModelStructureTests(phpVersion.getAlias() + " - /" + fileName) {

							protected void setUp() throws Exception {
								PHPCoreTests.setProjectPhpVersion(project, phpVersion);
							}

							protected void tearDown() throws Exception {
								if (testFile != null) {
									testFile.delete(true, null);
									testFile = null;
								}
							}

							protected void runTest() throws Throwable {
								ISourceModule sourceModule = createFile(pdttFile.getFile());
								
								ByteArrayOutputStream stream = new ByteArrayOutputStream();
								PrintStream printStream = new PrintStream(stream);
								sourceModule.accept(new PrintVisitor(printStream));
								printStream.close();
								
								assertContents(pdttFile.getExpected(), stream.toString());
							}
						});
					} catch (final Exception e) {
						phpVerSuite.addTest(new TestCase(fileName) { // dummy test indicating PDTT file parsing failure
								protected void runTest() throws Throwable {
									throw e;
								}
							});
					}
				}
			}
			suite.addTest(phpVerSuite);
		}

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			protected void setUp() throws Exception {
				setUpSuite();
			}

			protected void tearDown() throws Exception {
				tearDownSuite();
			}
		};
		return setup;
	}

	/**
	 * Creates test file with the specified content and calculates the offset at 
	 * OFFSET_CHAR. Offset character itself is stripped off.
	 * 
	 * @param data File data
	 * @return offset where's the offset character set. 
	 * @throws Exception
	 */
	protected static ISourceModule createFile(String data) throws Exception {
		testFile = project.getFile("test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		
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
					}
					else if ((flags & Modifiers.AccNameSpace) != 0) {
						stream.print("NAMESPACE: ");
					}
					else {
						stream.print("CLASS: ");
					}
				}
				else if (element.getElementType() == IModelElement.METHOD) {
					IMethod method = (IMethod) element;
					IType declaringType = method.getDeclaringType();
					if (declaringType == null || (declaringType.getFlags() & Modifiers.AccNameSpace) != 0) {
						stream.print("FUNCTION: ");
					} else {
						stream.print("METHOD: ");
					}
				}
				else if (element.getElementType() == IModelElement.FIELD) {
					stream.print("VARIABLE: ");
				}
				else if (element.getElementType() == IModelElement.SOURCE_MODULE) {
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
