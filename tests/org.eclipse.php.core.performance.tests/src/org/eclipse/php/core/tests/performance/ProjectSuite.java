package org.eclipse.php.core.tests.performance;

import junit.extensions.TestSetup;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ast.locator.PhpElementConciliatorTestWrapper;
import org.eclipse.php.core.tests.performance.codeassist.CodeAssistTestsWrapper;
import org.eclipse.php.core.tests.performance.formatter.FormatterTestsWrapper;
import org.eclipse.php.core.tests.performance.markoccurrence.MarkOccurrenceTestsWrapper;
import org.eclipse.php.core.tests.performance.selection.SelectionEngineTestsWrapper;
import org.eclipse.php.core.tests.performance.typeinference.TypeInferenceTestsWrapper;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class ProjectSuite extends AbstractModelTests {
	private Metadata metadata;
	private PerformanceMonitor perfMonitor;

	public ProjectSuite(String name) {
		super(PHPCorePerformanceTests.PLUGIN_ID, name);
	}

	public Test suite(final Metadata metadata) {
		this.metadata = metadata;

		TestSuite suite = new TestSuite(metadata.project + " Performance Tests");

		suite.addTest(new BuildProjectTest());
		suite.addTest(new SearchAllTypesTest());
		suite.addTest(new SearchAllFunctionsTest());
		suite.addTest(new SearchGlobalVariablesTest());
		suite.addTest(new SearchIncludeStatementsTest());
		suite.addTest(new SuperTypeHierarchyTest());
		suite.addTest(new TypeHierarchyTest());

		suite.addTest(new CodeAssistTestsWrapper().suite(metadata));
		suite.addTest(new MarkOccurrenceTestsWrapper().suite(metadata));
		suite.addTest(new PhpElementConciliatorTestWrapper().suite(metadata));
		suite.addTest(new FormatterTestsWrapper().suite(metadata));
		suite.addTest(new SelectionEngineTestsWrapper().suite(metadata));
		suite.addTest(new TypeInferenceTestsWrapper().suite(metadata));

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			protected void setUp() throws Exception {
				deleteProject(metadata.project);
				IProject project = getProject(metadata.project);

				IScriptProject scriptProject = DLTKCore.create(project);
				project.create(null);
				project.open(null);

				// configure nature
				IProjectDescription desc = project.getDescription();
				desc.setNatureIds(new String[] { PHPNature.ID });
				project.setDescription(desc, null);
				ProjectOptions.setPhpVersion(metadata.phpVersion, project
						.getProject());

				Util.downloadAndExtract(metadata.url, scriptProject
						.getProject().getLocation().toString());
				perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
			}

			protected void tearDown() throws Exception {
				deleteProject(metadata.project);
			}
		};
		return setup;
	}

	public static class Metadata {

		public final PHPVersion phpVersion;
		public final String project;
		/**
		 * this is for method testTypeHierarchy and testSuperTypeHierarchy
		 */
		public final String url;
		public final String type;

		public Metadata(String project, String url, String type,
				PHPVersion phpVersion) {
			this.project = project;
			this.url = url;
			this.type = type;
			this.phpVersion = phpVersion;
		}
	}

	public class BuildProjectTest extends AbstractPDTTTest {
		public BuildProjectTest() {
			super(metadata.project + "_BuildProjectTest");
		}

		public void runTest() throws Throwable {
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(metadata.project);

			perfMonitor.execute(metadata.project + ".testBuildProject",
					new Operation() {
						public void run() throws Exception {
							project.refreshLocal(IResource.DEPTH_INFINITE, null);
							PHPCoreTests.waitForIndexer();
						}
					}, 1, 10);
		}
	}

	public class SearchAllTypesTest extends AbstractPDTTTest {
		public SearchAllTypesTest() {
			super(metadata.project + "_SearchAllTypesTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(metadata.project);

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findTypes("", MatchRule.PREFIX, 0, 0, scope, null);
			perfMonitor.execute(metadata.project + ".testSearchAllTypes",
					new Operation() {
						public void run() throws Exception {
							modelAccess.findTypes("", MatchRule.PREFIX, 0, 0,
									scope, null);
						}
					}, 10, 10);
		}
	}

	public class SearchAllFunctionsTest extends AbstractPDTTTest {
		public SearchAllFunctionsTest() {
			super(metadata.project + "_SearchAllFunctionsTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(metadata.project);

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findMethods("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, scope, null);
			perfMonitor.execute(metadata.project + ".testSearchAllFunctions",
					new Operation() {
						public void run() throws Exception {
							modelAccess.findMethods("", MatchRule.PREFIX,
									Modifiers.AccGlobal, 0, scope, null);
						}
					}, 10, 10);
		}
	}

	public class SearchGlobalVariablesTest extends AbstractPDTTTest {
		public SearchGlobalVariablesTest() {
			super(metadata.project + "_SearchGlobalVariablesTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(metadata.project);

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findFields("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, scope, null);
			perfMonitor.execute(
					metadata.project + ".testSearchGlobalVariables",
					new Operation() {
						public void run() throws Exception {
							modelAccess.findFields("", MatchRule.PREFIX,
									Modifiers.AccGlobal, 0, scope, null);
						}
					}, 10, 10);
		}
	}

	public class SearchIncludeStatementsTest extends AbstractPDTTTest {
		public SearchIncludeStatementsTest() {
			super(metadata.project + "_SearchIncludeStatementsTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(metadata.project);

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findIncludes("", MatchRule.PREFIX, scope, null);
			perfMonitor.execute(metadata.project
					+ ".testSearchIncludeStatements", new Operation() {
				public void run() throws Exception {
					modelAccess.findIncludes("", MatchRule.PREFIX, scope, null);
				}
			}, 10, 10);
		}
	}

	public class SuperTypeHierarchyTest extends AbstractPDTTTest {
		public SuperTypeHierarchyTest() {
			super(metadata.project + "_SuperTypeHierarchyTest");
		}

		public void runTest() throws Throwable {
			IProject project = getProject(metadata.project);

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			final IType[] exceptionType = PhpModelAccess.getDefault()
					.findTypes(metadata.type, MatchRule.EXACT, 0, 0, scope,
							null);

			Assert.assertNotNull(exceptionType);
			Assert.assertEquals(exceptionType.length, 1);

			perfMonitor.execute(metadata.project + ".testSuperTypeHierarchy",
					new Operation() {
						public void run() throws Exception {
							exceptionType[0].newSupertypeHierarchy(null);
						}
					}, 10, 10);
		}
	}

	public class TypeHierarchyTest extends AbstractPDTTTest {
		public TypeHierarchyTest() {
			super(metadata.project + "_TypeHierarchyTest");
		}

		public void runTest() throws Throwable {
			IProject project = getProject(metadata.project);

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			final IType[] exceptionType = PhpModelAccess.getDefault()
					.findTypes(metadata.type, MatchRule.EXACT, 0, 0, scope,
							null);

			Assert.assertNotNull(exceptionType);
			Assert.assertEquals(exceptionType.length, 1);

			perfMonitor.execute(metadata.project + ".testTypeHierarchy",
					new Operation() {
						public void run() throws Exception {
							exceptionType[0].newTypeHierarchy(null);
						}
					}, 10, 10);
		}
	}
}
