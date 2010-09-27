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
package org.eclipse.php.core.tests.performance.typeinference;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;

public class TypeInferenceTestsWrapper extends AbstractPDTTTest {

	protected static final int ENGINE_TIMEOUT = 100000;
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/project/typeinference/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		// "/workspace/project/typeinference/php5",
				"/workspace/project/typeinference/php53" });
	};

	// private IFile testFile;
	private IProject project;
	private PerformanceMonitor perfMonitor;
	private static PHPTypeInferencer typeInferenceEngine;

	public TypeInferenceTestsWrapper() {
		super("");
	}

	public static void setUpSuite() throws Exception {
		typeInferenceEngine = new PHPTypeInferencer();
	}

	public static void tearDownSuite() throws Exception {
		typeInferenceEngine = null;
	}

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();

		TestSuite suite = new TestSuite("Type Inference Tests");

		final PHPVersion phpVersion = (PHPVersion) map
				.get(ProjectSuite.PHP_VERSION);
		for (String testsDirectory : TESTS.get(phpVersion)) {
			testsDirectory = testsDirectory.replaceAll("project", map.get(
					ProjectSuite.PROJECT).toString());
			for (final String fileName : getPDTTFiles(testsDirectory,
					PHPCorePerformanceTests.getDefault().getBundle())) {
				try {
					final PdttFile pdttFile = new PdttFile(
							PHPCorePerformanceTests.getDefault().getBundle(),
							fileName);
					final String pruner = getPrunerType(pdttFile);

					suite.addTest(new TypeInferenceTests(phpVersion.getAlias()
							+ " - /" + fileName) {

						protected void setUp() throws Exception {
							PHPCoreTests.setProjectPhpVersion(project,
									phpVersion);
						}

						protected void tearDown() throws Exception {
						}

						protected void runTest() throws Throwable {
							String criteriaFunction = new File(fileName)
									.getName().replaceAll("\\.pdtt", "");
							String code = pdttFile.getFile();

							findEvaluatedType(fileName, code, criteriaFunction,
									pruner);
						}
					});
				} catch (final Exception e) {
					suite.addTest(new TestCase(fileName) { // dummy
															// test
															// indicating
															// PDTT
															// file
															// parsing
															// failure
								protected void runTest() throws Throwable {
									throw e;
								}
							});
				}
			}
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

	private static String getPrunerType(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("prune");
	}

	class ASTNodeSearcher extends ContextFinder {

		private IContext context;
		private ASTNode result;
		private String criteriaFunction;

		public ASTNodeSearcher(ISourceModule sourceModule,
				String criteriaFunction) {
			super(sourceModule);
			this.criteriaFunction = criteriaFunction;
		}

		public boolean visit(Expression node) throws Exception {
			if (node instanceof CallExpression) {
				CallExpression callExpression = (CallExpression) node;
				if (criteriaFunction.equals(callExpression.getName())) {
					result = (ASTNode) callExpression.getArgs().getChilds()
							.get(0);
					context = contextStack.peek();
					return false;
				}
			}
			return true;
		}

		public ASTNode getResult() {
			return result;
		}

		public IContext getContext() {
			return context;
		}
	}

	protected void findEvaluatedType(String fileName, String code,
			String criteriaFunction, final String pruner) throws Exception {
		IFile file = project.getFile("pdttest/test.php");
		if (file.exists()) {
			file.setContents(new ByteArrayInputStream(code.getBytes()), true,
					false, null);
		} else {
			file.create(new ByteArrayInputStream(code.getBytes()), true, null);
		}

		try {
			project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
			PHPCoreTests.waitForIndexer();
			PHPCoreTests.waitForAutoBuild();

			ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
			ModuleDeclaration moduleDecl = SourceParserUtil
					.getModuleDeclaration(sourceModule);

			ASTNodeSearcher searcher = new ASTNodeSearcher(sourceModule,
					criteriaFunction);
			moduleDecl.traverse(searcher);

			Assert.assertNotNull("Method call " + criteriaFunction
					+ "() in code: " + code, searcher.getResult());
			Assert.assertNotNull("Can't find context for " + criteriaFunction
					+ "() in code: " + code, searcher.getContext());

			final ExpressionTypeGoal goal = new ExpressionTypeGoal(searcher
					.getContext(), searcher.getResult());
			perfMonitor.execute("PerformanceTests.testCodeAssist" + "_"
					+ fileName, new Operation() {
				public void run() throws Exception {
					if ("phpdocGoals".equals(pruner)) {
						typeInferenceEngine.evaluateTypeHeavy(goal,
								ENGINE_TIMEOUT);
					} else if ("heavyGoals".equals(pruner)) {
						typeInferenceEngine.evaluateTypePHPDoc(goal,
								ENGINE_TIMEOUT);
					} else {
						typeInferenceEngine.evaluateType(goal, ENGINE_TIMEOUT);
					}
				}
			}, 1, 10);

		} finally {
			try {
				file.delete(true, null);
			} catch (Exception e) {
				// do not handle - may be it's currently in use
			}
		}
	}

	public class TypeInferenceTests extends AbstractPDTTTest {

		public TypeInferenceTests(String description) {
			super(description);
		}

	}
}
