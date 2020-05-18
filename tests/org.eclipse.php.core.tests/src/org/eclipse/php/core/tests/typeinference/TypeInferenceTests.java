/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
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
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.typeinference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.TestUtils.ColliderType;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class TypeInferenceTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected static final int ENGINE_TIMEOUT = 100000;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<>();

	static {

		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/typeinference/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
				"/workspace/typeinference/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
				"/workspace/typeinference/php54", "/workspace/typeinference/php55" });
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
				"/workspace/typeinference/php54", "/workspace/typeinference/php55", "/workspace/typeinference/php56" });
		TESTS.put(PHPVersion.PHP7_0,
				new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
						"/workspace/typeinference/php54", "/workspace/typeinference/php55",
						"/workspace/typeinference/php56", "/workspace/typeinference/php70" });
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
						"/workspace/typeinference/php54", "/workspace/typeinference/php55",
						"/workspace/typeinference/php56", "/workspace/typeinference/php70" });
		TESTS.put(PHPVersion.PHP7_2,
				new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
						"/workspace/typeinference/php54", "/workspace/typeinference/php55",
						"/workspace/typeinference/php56", "/workspace/typeinference/php70" });
		TESTS.put(PHPVersion.PHP7_3,
				new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
						"/workspace/typeinference/php54", "/workspace/typeinference/php55",
						"/workspace/typeinference/php56", "/workspace/typeinference/php70" });
		TESTS.put(PHPVersion.PHP7_4,
				new String[] { "/workspace/typeinference/php5", "/workspace/typeinference/php53",
						"/workspace/typeinference/php54", "/workspace/typeinference/php55",
						"/workspace/typeinference/php56", "/workspace/typeinference/php70" });
	};

	private static int counter = 0;

	private PHPTypeInferencer typeInferenceEngine;
	private IProject project;
	private PHPVersion version;
	protected IFile testFile;

	public TypeInferenceTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		TestUtils.disableColliders(ColliderType.ALL);
		project = TestUtils.createProject("TypeInferenceTests_" + version.toString());
		TestUtils.setProjectPHPVersion(project, version);
		typeInferenceEngine = new PHPTypeInferencer();
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
		typeInferenceEngine = null;
		TestUtils.enableColliders(ColliderType.ALL);
	}

	@Test
	public void inference(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);
		createFile(pdttFile);
		final String pruner = getPrunerType(pdttFile);
		String criteriaFunction = new File(fileName).getName().replaceAll("\\.pdtt", "");
		String code = pdttFile.getFile();
		IEvaluatedType evaluatedType = findEvaluatedType(code, criteriaFunction, pruner);
		assertNotNull("Can't evaluate type for: " + code, evaluatedType);
		assertEquals(pdttFile.getExpected().trim(), evaluatedType.getTypeName().trim());
	}

	@After
	public void after() throws Exception {
		TestUtils.deleteFile(testFile);
	}

	private static String getPrunerType(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("prune");
	}

	class ASTNodeSearcher extends ContextFinder {

		private IContext context;
		private ASTNode result;
		private String criteriaFunction;

		public ASTNodeSearcher(ISourceModule sourceModule, String criteriaFunction) {
			super(sourceModule);
			this.criteriaFunction = criteriaFunction;
		}

		@Override
		public boolean visit(Expression node) throws Exception {
			if (node instanceof CallExpression) {
				CallExpression callExpression = (CallExpression) node;
				if (criteriaFunction.equals(callExpression.getName())) {
					result = callExpression.getArgs().getChilds().get(0);
					context = contextStack.peek();
					return false;
				}
			}
			return true;
		}

		public ASTNode getResult() {
			return result;
		}

		@Override
		public IContext getContext() {
			return context;
		}
	}

	protected void createFile(PdttFile pdttFile) throws Exception {
		String data = pdttFile.getFile();
		testFile = TestUtils.createFile(project, "test-" + counter++ + ".php", data);
		TestUtils.waitForIndexer();
	}

	protected IEvaluatedType findEvaluatedType(String code, String criteriaFunction, String pruner) throws Exception {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(testFile);
		ModuleDeclaration moduleDecl = SourceParserUtil.getModuleDeclaration(sourceModule);
		ASTNodeSearcher searcher = new ASTNodeSearcher(sourceModule, criteriaFunction);
		moduleDecl.traverse(searcher);
		assertNotNull("Method call " + criteriaFunction + "() in code: " + code, searcher.getResult());
		assertNotNull("Can't find context for " + criteriaFunction + "() in code: " + code, searcher.getContext());
		ExpressionTypeGoal goal = new ExpressionTypeGoal(searcher.getContext(), searcher.getResult());
		if ("factorymethodGoals".equals(pruner)) {
			return typeInferenceEngine.evaluateTypeFactoryMethod(goal, ENGINE_TIMEOUT);
		}
		if ("phpdocGoals".equals(pruner)) {
			return typeInferenceEngine.evaluateTypeHeavy(goal, ENGINE_TIMEOUT);
		}
		if ("heavyGoals".equals(pruner)) {
			return typeInferenceEngine.evaluateTypePHPDoc(goal, ENGINE_TIMEOUT);
		}
		return typeInferenceEngine.evaluateType(goal, ENGINE_TIMEOUT);
	}

}
