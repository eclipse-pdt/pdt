/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     William Candillon - initial API and implementation
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.ui.tests.semantic_highlighter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
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
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
@RunWith(PDTTList.class)
public class SemanticHighlightingTests {

	protected IProject project;
	protected IFile testFile;
	protected PHPVersion phpVersion;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP4,
				new String[] { "/workspace/semantic_highlighting/php4" });
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/semantic_highlighting/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/semantic_highlighting/php53" });
	};

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}

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

	@BeforeList
	public void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("SemanticHighlighting_" + phpVersion);
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
		PHPCoreTests.setProjectPhpVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	@After
	public void tearDown() throws Exception {
		if (testFile != null) {
			testFile.delete(true, null);
			testFile = null;
		}
	}

	public SemanticHighlightingTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
	}

	@Test
	public void highlighter(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault()
				.getBundle(), fileName);
		String result = "";
		createFile(new ByteArrayInputStream(pdttFile.getFile().getBytes()));
		ISourceModule module = getSourceModule();
		assertNotNull(module);

		String index = fileName.substring(fileName.lastIndexOf('/') + 1,
				fileName.indexOf('.'));
		int endIndex = index.indexOf('-');
		if (endIndex != -1) {
			index = index.substring(0, endIndex);
		}
		AbstractSemanticHighlighting highlighter = highlighters.get(index);
		assertNotNull(highlighter);
		Program program = getProgram(module);
		highlighter.initDefaultPreferences();
		Position[] positions = highlighter.consumes(program);
		assertNoDuplicates(highlighter.getDisplayName(), positions);
		result += highlighter.getClass().getName() + ":\n";
		for (Position position : positions) {
			result += "highlight("
					+ pdttFile.getFile().substring(position.getOffset(),
							position.getOffset() + position.getLength())
					+ ")\n";
		}
		assertEquals(pdttFile.getExpected(), result);
		// We check the other highlighters for failure
		for (AbstractSemanticHighlighting h : highlighters.values()) {
			if (h != highlighter) {
				h.initDefaultPreferences();
				positions = h.consumes(program);
				assertNoDuplicates(highlighter.getDisplayName(), positions);
			}
		}
	}

	private Program getProgram(ISourceModule module) throws ModelException,
			IOException {
		return SharedASTProvider.getAST(module, SharedASTProvider.WAIT_YES,
				null);

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

	protected void createFile(InputStream inputStream) throws Exception {
		testFile = project.getFile("test.php");
		testFile.create(inputStream, true, null);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		PHPCoreTests.waitForIndexer();
		// PHPCoreTests.waitForAutoBuild();
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}
}
