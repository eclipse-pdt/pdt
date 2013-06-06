package org.eclipse.php.core.tests.performance;

import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.filenetwork.AbstractModelTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ast.locator.PhpElementConciliatorTestWrapper;
import org.eclipse.php.core.tests.performance.codeassist.CodeAssistTestsWrapper;
import org.eclipse.php.core.tests.performance.filenetwork.FileNetworkTestsWrapper;
import org.eclipse.php.core.tests.performance.formatter.FormatterTestsWrapper;
import org.eclipse.php.core.tests.performance.markoccurrence.MarkOccurrenceTestsWrapper;
import org.eclipse.php.core.tests.performance.selection.SelectionEngineTestsWrapper;
import org.eclipse.php.core.tests.performance.typeinference.TypeInferenceTestsWrapper;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class ProjectSuite extends AbstractModelTests {
	protected Map map;
	protected PerformanceMonitor perfMonitor;

	public static final String PHP_VERSION = "phpVersion";
	public static final String PROJECT = "project";
	public static final String URL = "url";
	/**
	 * this is for method testTypeHierarchy and testSuperTypeHierarchy
	 */
	public static final String TYPE = "type";
	public static final String CHANGE_INCLUDE_PATH = "changeIncludePath";
	public static final String INCLUDE_PATH = "includePath";
	public static final String REFERENCED_FILE = "referencedfiles";
	public static final String REFERENCING_FILE = "referencingfiles";

	public ProjectSuite() {
		super(PHPCorePerformanceTests.PLUGIN_ID, "");
	}

	private String getProjectNameWithVersion(Map map) {
		return map.get(PROJECT).toString() + "_"
				+ ((PHPVersion) map.get(PHP_VERSION)).getAlias();
	}

	public Test suite(final Map map) {
		this.map = map;

		TestSuite suite = new TestSuite(getProjectNameWithVersion(map)
				+ " Performance Tests");

		addTests(suite);

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			protected void setUp() throws Exception {
				deleteProject(map.get(PROJECT).toString());
				IProject project = getProject(map.get(PROJECT).toString());

				IScriptProject scriptProject = DLTKCore.create(project);
				project.create(null);
				project.open(null);

				// configure nature
				IProjectDescription desc = project.getDescription();
				desc.setNatureIds(new String[] { PHPNature.ID });
				project.setDescription(desc, null);
				ProjectOptions.setPhpVersion((PHPVersion) map.get(PHP_VERSION),
						project.getProject());
				IFolder testFolder = project.getFolder("pdttest");
				testFolder.create(true, true, null);

				if (map.get(CHANGE_INCLUDE_PATH) != null
						&& ((Boolean) map.get(CHANGE_INCLUDE_PATH))
								.booleanValue()) {
					String[] includePath = (String[]) map.get(INCLUDE_PATH);
					IBuildpathEntry[] buildpathEntries = new IBuildpathEntry[includePath.length + 1];
					buildpathEntries[buildpathEntries.length - 1] = DLTKCore
							.newSourceEntry(testFolder.getFullPath());
					for (int i = 0; i < includePath.length; i++) {
						buildpathEntries[i] = DLTKCore.newSourceEntry(project
								.getFullPath().append(includePath[i]));
					}
					scriptProject.setRawBuildpath(buildpathEntries, null);
				}

				// Util.downloadAndExtract(map.get(URL).toString(),
				// scriptProject
				// .getProject().getLocation().toString());
				perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
			}

			protected void tearDown() throws Exception {
				deleteProject(map.get(PROJECT).toString());
			}
		};
		return setup;
	}

	protected void addTests(TestSuite suite) {
		suite.addTest(new BuildProjectTest());
		// suite.addTest(new SearchAllTypesTest());
		// suite.addTest(new SearchAllFunctionsTest());
		// suite.addTest(new SearchGlobalVariablesTest());
		// suite.addTest(new SearchIncludeStatementsTest());
		// suite.addTest(new SuperTypeHierarchyTest());
		// suite.addTest(new TypeHierarchyTest());

		suite.addTest(new CodeAssistTestsWrapper().suite(map));
		suite.addTest(new MarkOccurrenceTestsWrapper().suite(map));
		suite.addTest(new PhpElementConciliatorTestWrapper().suite(map));
		suite.addTest(new FormatterTestsWrapper().suite(map));
		suite.addTest(new SelectionEngineTestsWrapper().suite(map));
		suite.addTest(new TypeInferenceTestsWrapper().suite(map));
		suite.addTest(new FileNetworkTestsWrapper().suite(map));
	}

	// public static class Metadata {
	//
	// public final PHPVersion phpVersion;
	// public final String project;
	// /**
	// * this is for method testTypeHierarchy and testSuperTypeHierarchy
	// */
	// public final String url;
	// public final String type;
	//
	// public Metadata(String project, String url, String type,
	// PHPVersion phpVersion) {
	// this.project = project;
	// this.url = url;
	// this.type = type;
	// this.phpVersion = phpVersion;
	// }
	// }

	public class BuildProjectTest extends AbstractPDTTTest {
		public BuildProjectTest() {
			super(getProjectNameWithVersion(map) + "_BuildProjectTest");
		}

		public void runTest() throws Throwable {
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(map.get(PROJECT).toString());

			perfMonitor.execute(getProjectNameWithVersion(map)
					+ ".testBuildProject", new Operation() {
				public void run() throws Exception {
					project.refreshLocal(IResource.DEPTH_INFINITE, null);
					PHPCoreTests.waitForIndexer();
				}
			}, 1, 10);
		}
	}

	public class SearchAllTypesTest extends AbstractPDTTTest {
		public SearchAllTypesTest() {
			super(getProjectNameWithVersion(map) + "_SearchAllTypesTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(map.get(PROJECT).toString());

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findTypes("", MatchRule.PREFIX, 0, 0, scope, null);
			perfMonitor.execute(getProjectNameWithVersion(map)
					+ ".testSearchAllTypes", new Operation() {
				public void run() throws Exception {
					modelAccess.findTypes("", MatchRule.PREFIX, 0, 0, scope,
							null);
				}
			}, 10, 10);
		}
	}

	public class SearchAllFunctionsTest extends AbstractPDTTTest {
		public SearchAllFunctionsTest() {
			super(getProjectNameWithVersion(map) + "_SearchAllFunctionsTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(map.get(PROJECT).toString());

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findMethods("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, scope, null);
			perfMonitor.execute(getProjectNameWithVersion(map)
					+ ".testSearchAllFunctions", new Operation() {
				public void run() throws Exception {
					modelAccess.findMethods("", MatchRule.PREFIX,
							Modifiers.AccGlobal, 0, scope, null);
				}
			}, 10, 10);
		}
	}

	public class SearchGlobalVariablesTest extends AbstractPDTTTest {
		public SearchGlobalVariablesTest() {
			super(getProjectNameWithVersion(map) + "_SearchGlobalVariablesTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(map.get(PROJECT).toString());

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findFields("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, scope, null);
			perfMonitor.execute(getProjectNameWithVersion(map)
					+ ".testSearchGlobalVariables", new Operation() {
				public void run() throws Exception {
					modelAccess.findFields("", MatchRule.PREFIX,
							Modifiers.AccGlobal, 0, scope, null);
				}
			}, 10, 10);
		}
	}

	public class SearchIncludeStatementsTest extends AbstractPDTTTest {
		public SearchIncludeStatementsTest() {
			super(getProjectNameWithVersion(map)
					+ "_SearchIncludeStatementsTest");
		}

		public void runTest() throws Throwable {
			final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
			IProject project = getProject(map.get(PROJECT).toString());

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			modelAccess.findIncludes("", MatchRule.PREFIX, scope, null);
			perfMonitor.execute(getProjectNameWithVersion(map)
					+ ".testSearchIncludeStatements", new Operation() {
				public void run() throws Exception {
					modelAccess.findIncludes("", MatchRule.PREFIX, scope, null);
				}
			}, 10, 10);
		}
	}

	public class SuperTypeHierarchyTest extends AbstractPDTTTest {
		public SuperTypeHierarchyTest() {
			super(getProjectNameWithVersion(map) + "_SuperTypeHierarchyTest");
		}

		public void runTest() throws Throwable {
			IProject project = getProject(map.get(PROJECT).toString());

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			final IType[] exceptionType = PhpModelAccess.getDefault()
					.findTypes(map.get(TYPE).toString(), MatchRule.EXACT, 0, 0,
							scope, null);

			Assert.assertNotNull(exceptionType);
			Assert.assertEquals(exceptionType.length, 1);

			perfMonitor.execute(getProjectNameWithVersion(map)
					+ ".testSuperTypeHierarchy", new Operation() {
				public void run() throws Exception {
					exceptionType[0].newSupertypeHierarchy(null);
				}
			}, 10, 10);
		}
	}

	public class TypeHierarchyTest extends AbstractPDTTTest {
		public TypeHierarchyTest() {
			super(getProjectNameWithVersion(map) + "_TypeHierarchyTest");
		}

		public void runTest() throws Throwable {
			IProject project = getProject(map.get(PROJECT).toString());

			IScriptProject scriptProject = DLTKCore.create(project);
			final IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			final IType[] exceptionType = PhpModelAccess.getDefault()
					.findTypes(map.get(TYPE).toString(), MatchRule.EXACT, 0, 0,
							scope, null);

			Assert.assertNotNull(exceptionType);
			Assert.assertEquals(exceptionType.length, 1);

			perfMonitor.execute(getProjectNameWithVersion(map)
					+ ".testTypeHierarchy", new Operation() {
				public void run() throws Exception {
					exceptionType[0].newTypeHierarchy(null);
				}
			}, 10, 10);
		}
	}
}
