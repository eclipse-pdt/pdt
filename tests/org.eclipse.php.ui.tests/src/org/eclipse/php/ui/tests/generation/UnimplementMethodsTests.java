/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.tests.generation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Bindings;
import org.eclipse.php.core.ast.nodes.IMethodBinding;
import org.eclipse.php.core.ast.nodes.ITypeBinding;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.TypeBinding;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.profiles.PHPDefaultFormatterPreferences;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.ui.actions.AddUnimplementedMethodsOperation;
import org.eclipse.php.ui.tests.PHPUiTests;
import org.eclipse.php.ui.util.CodeGenerationUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@RunWith(PDTTList.class)
public class UnimplementMethodsTests {

	protected IProject project;
	protected IFile testFile;
	protected IDocument testDocument;
	protected PHPVersion phpVersion;

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<>();

	static {
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/generation/unimplement_methods" }); //$NON-NLS-1$
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/generation/unimplement_methods_php56" }); //$NON-NLS-1$
		TESTS.put(PHPVersion.PHP7_0, new String[] { "/workspace/generation/unimplement_methods_php56", //$NON-NLS-1$
				"/workspace/generation/unimplement_methods_php7" }); //$NON-NLS-1$
	};

	protected static final char SELECTION_CHAR = '|';

	public UnimplementMethodsTests(PHPVersion version, String[] fileNames) {
		this.phpVersion = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("UnimplementMethods_" + this.phpVersion);
		TestUtils.setProjectPHPVersion(project, phpVersion);

		IEclipsePreferences node = DefaultScope.INSTANCE.getNode("org.eclipse.php.formatter.core"); //$NON-NLS-1$
		node.put(CodeFormatterConstants.FORMATTER_PROFILE, PHPDefaultFormatterPreferences.ID);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@After
	public void tearDown() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
	}

	@Test
	public void generate(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPUiTests.getDefault().getBundle(), fileName);
		pdttFile.applyPreferences();

		IType type = createFile(pdttFile.getFile());
		final ASTParser parser = ASTParser.newParser(phpVersion, false, true, type.getSourceModule());
		Program unit = parser.createAST(new NullProgressMonitor());

		IEvaluatedType evaluatedType = PHPClassType.fromIType(type);
		final ITypeBinding binding = new TypeBinding(unit.getAST().getBindingResolver(), evaluatedType, type);

		List<IMethodBinding> toImplement = new ArrayList<>();
		IMethodBinding[] overridable = null;

		final IMethodBinding[] methods = CodeGenerationUtils.getOverridableMethods(unit.getAST(), binding, false);
		List<IMethodBinding> list = new ArrayList<>(methods.length);
		for (final IMethodBinding cur : methods) {
			if (Bindings.isVisibleInHierarchy(cur)) {
				list.add(cur);
			}
		}
		overridable = list.toArray(new IMethodBinding[list.size()]);

		for (IMethodBinding element : overridable) {
			toImplement.add(element);
		}

		IMethodBinding[] toImplementArray = toImplement.toArray(new IMethodBinding[toImplement.size()]);
		AddUnimplementedMethodsOperation operation = new AddUnimplementedMethodsOperation(unit, type, binding,
				toImplementArray, -1, true, testDocument);
		operation.setCreateComments(true);

		operation.run(new NullProgressMonitor());

		PDTTUtils.assertContents(pdttFile.getExpected(), testDocument.get());
	}

	protected InputStream[] prepareOtherStreams(PdttFile file) {
		String[] contents = file.getOtherFiles();
		InputStream[] result = new InputStream[contents.length];
		for (int i = 0; i < contents.length; i++) {
			result[i] = new ByteArrayInputStream(contents[i].getBytes());
		}

		return result;
	}

	/**
	 * Creates test file with the specified content and calculates the source range
	 * for the selection. Selection characters themself are stripped off.
	 * 
	 * @param data
	 *            File data
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected IType createFile(String data) throws Exception {
		int left = data.indexOf(SELECTION_CHAR);
		if (left == -1) {
			throw new IllegalArgumentException("Selection characters are not set"); //$NON-NLS-1$
		}
		// replace the left character
		data = data.substring(0, left) + data.substring(left + 1);

		int right = data.indexOf(SELECTION_CHAR);
		if (right == -1) {
			throw new IllegalArgumentException("Selection is not closed"); //$NON-NLS-1$
		}
		data = data.substring(0, right) + data.substring(right + 1);

		testDocument = new Document();
		testDocument.set(data);

		testFile = TestUtils.createFile(project, "test.php", data);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		TestUtils.waitForIndexer();

		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(testFile);
		IModelElement element = sourceModule.getElementAt(left);
		IType type = CodeGenerationUtils.getType(element);
		if (type != null) {
			return type;
		}

		throw new IllegalArgumentException("No type found under selection"); //$NON-NLS-1$
	}

	@Context
	public static Bundle getBundle() {
		return PHPUiTests.getDefault().getBundle();
	}
}
