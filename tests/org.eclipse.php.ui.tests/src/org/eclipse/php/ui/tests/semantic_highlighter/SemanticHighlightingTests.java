/*******************************************************************************
 * Copyright (c) 2009, 2014, 2016 IBM Corporation and others.
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.DefaultProblemFactory;
import org.eclipse.dltk.compiler.problem.IProblemFactory;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IBuildState;
import org.eclipse.dltk.internal.core.builder.SourceModuleBuildContext;
import org.eclipse.jface.text.Position;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.internal.core.builder.TaskTagBuildParticipantFactory;
import org.eclipse.php.internal.core.preferences.TaskPatternsProvider;
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
import org.eclipse.php.internal.ui.editor.highlighters.TaskTagHighlighting;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.tests.PHPUiTests;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
@RunWith(PDTTList.class)
public class SemanticHighlightingTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected IProject project;
	protected IFile testFile;
	protected List<IFile> otherFiles = null;
	protected PHPVersion phpVersion;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/semantic_highlighting/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/semantic_highlighting/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/semantic_highlighting/php54" });
		TESTS.put(PHPVersion.PHP7_1, new String[] { "/workspace/semantic_highlighting/php71" });
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
		highlighters.put("internal_constant", new InternalConstantHighlighting());
		highlighters.put("internal_function", new InternalFunctionHighlighting());
		highlighters.put("method", new MethodHighlighting());
		highlighters.put("parameter_variable", new ParameterVariableHighlighting());
		highlighters.put("static_field", new StaticFieldHighlighting());
		highlighters.put("static_method", new StaticMethodHighlighting());
		highlighters.put("super_global", new SuperGlobalHighlighting());
		highlighters.put("task_tags", new TaskTagHighlighting());
	}

	public SemanticHighlightingTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("SemanticHighlighting_" + phpVersion);
		TestUtils.setProjectPhpVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TaskPatternsProvider.unregisterProject(project);
		setDefaultTaskTagPreferences();
		TestUtils.deleteProject(project);
	}

	@After
	public void tearDown() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
		if (otherFiles != null) {
			for (IFile file : otherFiles) {
				if (file != null) {
					TestUtils.deleteFile(file);
				}
			}
			otherFiles = null;
		}
	}

	private void setDefaultTaskTagPreferences() throws CoreException {
		String preferences = PHPCorePlugin.ID + '/' + PHPCoreConstants.TASK_TAGS + '='
				+ PHPCoreConstants.DEFAULT_TASK_TAGS + "\n";
		preferences += PHPCorePlugin.ID + '/' + PHPCoreConstants.TASK_PRIORITIES + '='
				+ PHPCoreConstants.DEFAULT_TASK_PRIORITIES + "\n";
		preferences += PHPCorePlugin.ID + '/' + PHPCoreConstants.TASK_CASE_SENSITIVE + '=' + PHPCoreConstants.ENABLED
				+ "\n";
		Platform.getPreferencesService().importPreferences(new ByteArrayInputStream(preferences.getBytes()));
	}

	private void build(ISourceModule module) throws CoreException {
		TaskTagBuildParticipantFactory factory = new TaskTagBuildParticipantFactory() {
			@Override
			public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
					throws CoreException {
				natureId = PHPNature.ID;
			}
		};
		factory.setInitializationData(null, null, null);
		IBuildParticipant buildParticipant = factory.createBuildParticipant(module.getScriptProject());
		IProblemFactory problemFactory = new DefaultProblemFactory();
		IBuildState buildState = new IBuildState() {
			@Override
			public void recordStructuralChange(IPath path) {
			}

			@Override
			public void recordImportProblem(IPath path) {
			}

			@Override
			public void recordDependency(IPath path, IPath dependency, int flags) {
			}

			@Override
			public void recordDependency(IPath path, IPath dependency) {
			}

			@Override
			public Set<IPath> getStructuralChanges() {
				return null;
			}
		};
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(module, null);
		if (moduleDeclaration instanceof PHPModuleDeclaration) {
			SourceModuleBuildContext context = new SourceModuleBuildContext(problemFactory, module,
					IBuildContext.FULL_BUILD, buildState);
			context.set(IBuildContext.ATTR_MODULE_DECLARATION, moduleDeclaration);
			buildParticipant.build(context);
		}
	}

	@Test
	public void highlighter(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault().getBundle(), fileName);
		// reset all task tag preferences before applying any specific ones
		// (to avoid using task tag preferences defined by other PdttFiles)
		setDefaultTaskTagPreferences();
		pdttFile.applyPreferences();

		String result = "";
		createFile(pdttFile);
		ISourceModule module = getSourceModule();
		assertNotNull(module);

		String index = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.indexOf('.'));
		int endIndex = index.indexOf('-');
		if (endIndex != -1) {
			index = index.substring(0, endIndex);
		}
		AbstractSemanticHighlighting highlighter = highlighters.get(index);
		assertNotNull(highlighter);

		build(module);

		Program program = getProgram(module);
		Position[] positions = highlighter.consumes(program);
		assertNoDuplicates(highlighter.getDisplayName(), positions);
		result += highlighter.getClass().getName() + ":\n";
		for (Position position : positions) {
			result += "highlight("
					+ pdttFile.getFile().substring(position.getOffset(), position.getOffset() + position.getLength())
					+ ")\n";
		}
		assertEquals(pdttFile.getExpected(), result);
		// We check the other highlighters for failure
		for (AbstractSemanticHighlighting h : highlighters.values()) {
			if (h != highlighter) {
				positions = h.consumes(program);
				assertNoDuplicates(highlighter.getDisplayName(), positions);
			}
		}
	}

	private Program getProgram(ISourceModule module) throws ModelException, IOException {
		return SharedASTProvider.getAST(module, SharedASTProvider.WAIT_YES, null);

	}

	protected void assertNoDuplicates(String highlighter, Position[] positions) {
		for (Position p1 : positions) {
			for (Position p2 : positions) {
				if (p1 != p2 && p1.equals(p2)) {
					throw new IllegalStateException("Found duplicate position in " + highlighter + ": " + p1);
				}
			}
		}
	}

	protected void createFile(PdttFile pdttFile) throws Exception {
		testFile = TestUtils.createFile(project, "test.php", new String(pdttFile.getFile().getBytes()));
		String[] otherFiles = pdttFile.getOtherFiles();
		this.otherFiles = new ArrayList<IFile>(otherFiles.length);
		int i = 0;
		for (String otherFileContent : otherFiles) {
			IFile tmp = TestUtils.createFile(project, String.format("test%s.php", i), otherFileContent);
			this.otherFiles.add(i, tmp);
			i++;
		}
		// Wait for indexer...
		TestUtils.waitForIndexer();
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}
}
