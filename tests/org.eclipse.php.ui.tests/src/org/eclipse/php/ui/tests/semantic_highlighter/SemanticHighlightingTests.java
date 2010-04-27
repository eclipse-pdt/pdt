/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     William Candillon - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.tests.semantic_highlighter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.Position;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.ClassHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.ConstantHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.DeprecatedHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.FieldHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.FunctionHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.InternalClassHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.InternalConstantHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.InternalFunctionHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.MethodHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.ParameterVariableHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.StaticFieldHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.StaticMethodHighlighting;
import org.eclipse.php.internal.ui.editor.highlighters.SuperGlobalHighlighting;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.tests.PHPUiTests;

@SuppressWarnings("restriction")
public class SemanticHighlightingTests extends AbstractPDTTTest {

	protected static IProject project;
	protected static IFile testFile;
	protected static PHPVersion phpVersion;
	protected static final Map<PHPVersion, String> TESTS = new LinkedHashMap<PHPVersion, String>();
	static {
		TESTS.put(PHPVersion.PHP4, "/workspace/semantic_highlighting/php4");
		TESTS.put(PHPVersion.PHP5, "/workspace/semantic_highlighting/php5");
		TESTS.put(PHPVersion.PHP5_3, "/workspace/semantic_highlighting/php53");
	};

	private static Map<String, AbstractSemanticHighlighting> highlighters = new HashMap<String, AbstractSemanticHighlighting>();

	static {
		highlighters.put("class", new ClassHighlighting());
		highlighters.put("constant", new ConstantHighlighting());
		highlighters.put("deprecated", new DeprecatedHighlighting());
		highlighters.put("field", new FieldHighlighting());
		highlighters.put("function", new FunctionHighlighting());
		highlighters.put("internal_class", new InternalClassHighlighting());
		highlighters.put("internal_constant",
				new InternalConstantHighlighting());
		highlighters.put("internal_function",
				new InternalFunctionHighlighting());
		highlighters.put("method", new MethodHighlighting());
		highlighters.put("parameter_variable",
				new ParameterVariableHighlighting());
		highlighters.put("static_field", new StaticFieldHighlighting());
		highlighters.put("static_method", new StaticMethodHighlighting());
		highlighters.put("super_global", new SuperGlobalHighlighting());
	}

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"SemanticHighlighting");
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

	public SemanticHighlightingTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Semantic Highlighting Tests");
		for (Entry<PHPVersion, String> pair : TESTS.entrySet()) {
			phpVersion = pair.getKey();
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());

			String[] files = getPDTTFiles(pair.getValue(), PHPUiTests
					.getDefault().getBundle());

			for (final String fileName : files) {
				try {
					final PdttFile pdttFile = new PdttFile(PHPUiTests
							.getDefault().getBundle(), fileName);
					phpVerSuite.addTest(new SemanticHighlightingTests(
							phpVersion.getAlias() + " - /" + fileName) {

						protected void setUp() throws Exception {
							PHPCoreTests.setProjectPhpVersion(project,
									PHPVersion.PHP5_3);
						}

						protected void tearDown() throws Exception {
							if (testFile != null) {
								testFile.delete(true, null);
								testFile = null;
							}
						}

						protected void runTest() throws Throwable {
							;
							String result = "";
							createFile(new ByteArrayInputStream(pdttFile
									.getFile().getBytes()));
							ISourceModule module = getSourceModule();
							assertNotNull(module);
							String index = fileName.substring(fileName
									.lastIndexOf('/') + 1, fileName
									.indexOf('.'));
							// System.err.println(index);
							AbstractSemanticHighlighting highlighter = highlighters
									.get(index);
							assertNotNull(highlighter);
							Program program = getProgram(module);
							highlighter.initDefaultPreferences();
							Position[] positions = highlighter
									.consumes(program);
							assertNoDuplicates(highlighter.getDisplayName(),
									positions);
							result += highlighter.getClass().getName() + ":\n";
							for (Position position : positions) {
								result += "highlight("
										+ pdttFile.getFile().substring(
												position.getOffset(),
												position.getOffset()
														+ position.getLength())
										+ ")\n";
							}
							assertEquals(pdttFile.getExpected(), result);
							// We check the other highlighters for failure
							for (AbstractSemanticHighlighting h : highlighters
									.values()) {
								if (h != highlighter) {
									h.initDefaultPreferences();
									positions = h.consumes(program);
									assertNoDuplicates(highlighter
											.getDisplayName(), positions);
								}
							}
						}

						private Program getProgram(ISourceModule module)
								throws ModelException, IOException {
							return SharedASTProvider.getAST(module,
									SharedASTProvider.WAIT_YES, null);

						}
					});
				} catch (final Exception e) {
					phpVerSuite.addTest(new TestCase(fileName) {
						protected void runTest() throws Throwable {
							throw e;
						}
					});
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

	protected void assertNoDuplicates(String highlighter, Position[] positions) {
		for (Position p1 : positions) {
			for (Position p2 : positions) {
				if (p1 != p2 && p1.equals(p2)) {
					throw new IllegalStateException(
							"Found duplicate position in " + highlighter + ": "
									+ p1);
				}
			}
		}
	}

	protected static void createFile(InputStream inputStream) throws Exception {
		testFile = project.getFile("test.php");
		testFile.create(inputStream, true, null);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		PHPCoreTests.waitForIndexer();
		// PHPCoreTests.waitForAutoBuild();
	}

	protected static ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}
}
