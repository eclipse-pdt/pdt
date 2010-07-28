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
package org.eclipse.php.core.tests.performance.zf;

import junit.framework.Assert;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.Util;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.codeassist.PHPCompletionEngine;
import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * Various performance tests performed on Zend Framework project.
 * 
 * @author Michael
 * 
 */
public class ZFPerformanceTests extends AbstractModelTests {

	public static final String PROJECT = "ZendFramework";
	private PerformanceMonitor perfMonitor;

	public ZFPerformanceTests(String name) {
		super(PHPCorePerformanceTests.PLUGIN_ID, name);
	}

	public static TestSuite suite() {
		return new Suite(ZFPerformanceTests.class);
	}

	public void setUpSuite() throws Exception {
		deleteProject(PROJECT);
		IScriptProject scriptProject = setUpScriptProject(PROJECT);

		Util.downloadAndExtract(
				"http://framework.zend.com/releases/ZendFramework-1.9.5/ZendFramework-1.9.5.zip",
				scriptProject.getProject().getLocation().toString());

		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
	}

	public void tearDownSuite() throws Exception {
		deleteProject(PROJECT);
	}

	public void testBuildProject() throws Exception {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(ZFPerformanceTests.PROJECT);

		perfMonitor.execute("ZFPerformanceTests.testBuildProject",
				new Operation() {
					public void run() throws Exception {
						project.refreshLocal(IResource.DEPTH_INFINITE, null);
						PHPCoreTests.waitForIndexer();
					}
				}, 1, 10);
	}

	public void testSearchAllTypes() throws Exception {
		final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
		final IDLTKSearchScope scope = SearchEngine
				.createWorkspaceScope(PHPLanguageToolkit.getDefault());
		modelAccess.findTypes("", MatchRule.PREFIX, 0, 0, scope, null);
		perfMonitor.execute("ZFPerformanceTests.testSearchAllTypes",
				new Operation() {
					public void run() throws Exception {
						modelAccess.findTypes("", MatchRule.PREFIX, 0, 0,
								scope, null);
					}
				}, 10, 10);
	}

	public void testSearchAllFunctions() throws Exception {
		final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
		final IDLTKSearchScope scope = SearchEngine
				.createWorkspaceScope(PHPLanguageToolkit.getDefault());
		modelAccess.findMethods("", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, null);
		perfMonitor.execute("ZFPerformanceTests.testSearchAllFunctions",
				new Operation() {
					public void run() throws Exception {
						modelAccess.findMethods("", MatchRule.PREFIX,
								Modifiers.AccGlobal, 0, scope, null);
					}
				}, 10, 10);
	}

	public void testSearchGlobalVariables() throws Exception {
		final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
		final IDLTKSearchScope scope = SearchEngine
				.createWorkspaceScope(PHPLanguageToolkit.getDefault());
		modelAccess.findFields("", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, null);
		perfMonitor.execute("ZFPerformanceTests.testSearchGlobalVariables",
				new Operation() {
					public void run() throws Exception {
						modelAccess.findFields("", MatchRule.PREFIX,
								Modifiers.AccGlobal, 0, scope, null);
					}
				}, 10, 10);
	}

	public void testSearchIncludeStatements() throws Exception {
		final PhpModelAccess modelAccess = PhpModelAccess.getDefault();
		final IDLTKSearchScope scope = SearchEngine
				.createWorkspaceScope(PHPLanguageToolkit.getDefault());
		modelAccess.findIncludes("", MatchRule.PREFIX, scope, null);
		perfMonitor.execute("ZFPerformanceTests.testSearchIncludeStatements",
				new Operation() {
					public void run() throws Exception {
						modelAccess.findIncludes("", MatchRule.PREFIX, scope,
								null);
					}
				}, 10, 10);
	}

	public void testSuperTypeHierarchy() throws Exception {
		final IType[] exceptionType = PhpModelAccess.getDefault().findTypes(
				"Zend_Exception",
				MatchRule.EXACT,
				0,
				0,
				SearchEngine.createWorkspaceScope(PHPLanguageToolkit
						.getDefault()), null);

		Assert.assertNotNull(exceptionType);
		Assert.assertEquals(exceptionType.length, 1);

		perfMonitor.execute("ZFPerformanceTests.testSuperTypeHierarchy",
				new Operation() {
					public void run() throws Exception {
						exceptionType[0].newSupertypeHierarchy(null);
					}
				}, 10, 10);
	}

	public void testTypeHierarchy() throws Exception {
		final IType[] exceptionType = PhpModelAccess.getDefault().findTypes(
				"Zend_Exception",
				MatchRule.EXACT,
				0,
				0,
				SearchEngine.createWorkspaceScope(PHPLanguageToolkit
						.getDefault()), null);

		Assert.assertNotNull(exceptionType);
		Assert.assertEquals(exceptionType.length, 1);

		perfMonitor.execute("ZFPerformanceTests.testTypeHierarchy",
				new Operation() {
					public void run() throws Exception {
						exceptionType[0].newTypeHierarchy(null);
					}
				}, 10, 10);
	}

	public void testCodeAssist() throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				ZFPerformanceTests.PROJECT);
		IFile file = project.getFile("bin/zf.php");
		final ISourceModule sourceModule = (ISourceModule) DLTKCore
				.create(file);
		final int offset = sourceModule.getSourceRange().getLength() - 1;
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();

		IScriptProject scriptProject = sourceModule.getScriptProject();
		final PHPCompletionEngine engine = new PHPCompletionEngine();
		engine.setRequestor(new CompletionRequestor() {
			public void accept(CompletionProposal proposal) {
			}
		});
		engine.setOptions(scriptProject.getOptions(true));
		engine.setProject(scriptProject);

		engine.complete((IModuleSource) sourceModule, offset, 0, false);

		perfMonitor.execute("PHPModelAccessTest.testSearchAllTypes",
				new Operation() {
					public void run() throws Exception {
						engine.complete((IModuleSource) sourceModule, offset,
								0, false);
					}
				}, 10, 10);
	}
}
