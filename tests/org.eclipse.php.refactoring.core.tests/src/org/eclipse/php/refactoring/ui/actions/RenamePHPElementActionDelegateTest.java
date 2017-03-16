/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementVisitor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.Variable;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

import junit.framework.TestCase;

public class RenamePHPElementActionDelegateTest extends TestCase {

	private IProject project1;

	@Override
	protected void setUp() throws Exception {

		project1 = TestUtils.createProject("project1");
		IFolder folder = TestUtils.createFolder(project1, "src");
		folder = TestUtils.createFolder(project1, "src2");
		folder = TestUtils.createFolder(project1, "src/src1");
		TestUtils.createFile(folder, "test1.php", "<?php class TestRenameClass{}?>");

		TestUtils.waitForIndexer();
	}

	public void testGetSourceOffsetAndGetNode() {
		IFile file = project1.getFile("src/src1/test1.php");

		IModelElement source = null;
		try {
			source = DLTKCore.create(file);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(source);

		final ArrayList<IType> type = new ArrayList<IType>();
		try {
			source.accept(new IModelElementVisitor() {
				@Override
				public boolean visit(IModelElement element) {
					if (element.getElementType() == ISourceModule.TYPE) {
						type.add((IType) element);
						return false;
					}
					return true;
				}
			});

		} catch (ModelException e) {
			fail(e.getMessage());
		}
		assertTrue(type.size() > 0);

		RenamePHPElementActionDelegateProxy action = new RenamePHPElementActionDelegateProxy();
		int offset = -1;
		try {
			offset = action.getSourceOffset(type.get(0));
		} catch (ModelException e) {
			fail(e.getMessage());
		}

		assertTrue(offset >= 0);
		Program program;
		try {
			program = RefactoringUtility.getProgramForFile(file);
			ASTNode node = action.getSelectedNode(program, offset, 0);
			assertTrue(node instanceof ClassDeclaration);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testGetSourceOffsetAndGetNode1() {

		IFile file = project1.getFile("test1.php");

		InputStream sourceStream = new ByteArrayInputStream("<?php function foo(){};?>".getBytes());
		try {
			if (!file.exists()) {

				file.create(sourceStream, true, new NullProgressMonitor());
			} else {
				file.setContents(sourceStream, IFile.FORCE, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		IModelElement source = null;
		try {
			source = DLTKCore.create(file);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(source);

		final ArrayList<IMethod> methods = new ArrayList<IMethod>();
		try {
			source.accept(new IModelElementVisitor() {
				@Override
				public boolean visit(IModelElement element) {
					if (element.getElementType() == ISourceModule.METHOD) {
						methods.add((IMethod) element);
						return false;
					}
					return true;
				}
			});

		} catch (ModelException e) {
			fail(e.getMessage());
		}
		assertTrue(methods.size() > 0);

		RenamePHPElementActionDelegateProxy action = new RenamePHPElementActionDelegateProxy();
		int offset = -1;
		try {
			offset = action.getSourceOffset(methods.get(0));
		} catch (ModelException e) {
			fail(e.getMessage());
		}

		assertTrue(offset >= 0);
		Program program;
		try {
			program = RefactoringUtility.getProgramForFile(file);
			ASTNode node = action.getSelectedNode(program, offset, 0);
			assertTrue(node instanceof FunctionDeclaration);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testGetSourceOffsetAndGetNode2() {

		IFile file = project1.getFile("test2.php");

		InputStream sourceStream = new ByteArrayInputStream(
				"<?php class MyClass { function myfunc1(){ return(true);}}?>".getBytes());
		try {
			if (!file.exists()) {

				file.create(sourceStream, true, new NullProgressMonitor());
			} else {
				file.setContents(sourceStream, IFile.FORCE, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		IModelElement source = null;
		try {
			source = DLTKCore.create(file);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(source);

		try {
			((ISourceModule) source).makeConsistent(new NullProgressMonitor());
		} catch (ModelException e1) {
			fail(e1.getMessage());
		}

		final ArrayList<IMethod> methods = new ArrayList<IMethod>();
		try {
			source.accept(new IModelElementVisitor() {
				@Override
				public boolean visit(IModelElement element) {
					if (element.getElementType() == ISourceModule.METHOD) {
						methods.add((IMethod) element);
						return false;
					}
					return true;
				}
			});

		} catch (ModelException e) {
			fail(e.getMessage());
		}
		assertTrue(methods.size() > 0);

		RenamePHPElementActionDelegateProxy action = new RenamePHPElementActionDelegateProxy();
		int offset = -1;
		try {
			offset = action.getSourceOffset(methods.get(0));
		} catch (ModelException e) {
			fail(e.getMessage());
		}

		assertTrue(offset >= 0);
		Program program;
		try {
			program = RefactoringUtility.getProgramForFile(file);
			ASTNode node = action.getSelectedNode(program, offset, 0);
			assertTrue(node instanceof FunctionDeclaration);
			assertTrue(node.getParent() instanceof MethodDeclaration);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testGetSourceOffsetAndGetNode3() {

		IFile file = project1.getFile("test3.php");

		InputStream sourceStream = new ByteArrayInputStream("<?php class MyClss{public $a,$b;}?>".getBytes());
		try {
			if (!file.exists()) {

				file.create(sourceStream, true, new NullProgressMonitor());
			} else {
				file.setContents(sourceStream, IFile.FORCE, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		IModelElement source = null;
		try {
			source = DLTKCore.create(file);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(source);

		try {
			((ISourceModule) source).makeConsistent(new NullProgressMonitor());
		} catch (ModelException e1) {
			fail(e1.getMessage());
		}

		final ArrayList<IField> methods = new ArrayList<IField>();
		try {
			source.accept(new IModelElementVisitor() {
				@Override
				public boolean visit(IModelElement element) {
					if (element.getElementType() == ISourceModule.FIELD) {
						methods.add((IField) element);
						return false;
					}
					return true;
				}
			});

		} catch (ModelException e) {
			fail(e.getMessage());
		}
		assertTrue(methods.size() > 0);

		RenamePHPElementActionDelegateProxy action = new RenamePHPElementActionDelegateProxy();
		int offset = -1;
		try {
			offset = action.getSourceOffset(methods.get(0));
		} catch (ModelException e) {
			fail(e.getMessage());
		}

		assertTrue(offset >= 0);
		Program program;
		try {
			program = RefactoringUtility.getProgramForFile(file);
			ASTNode node = action.getSelectedNode(program, offset, 0);
			assertTrue(node instanceof Variable);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testIsSourceReference() {
		IFile file = project1.getFile("src/src1/test1.php");
		IModelElement source = null;
		try {
			source = DLTKCore.create(file);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(source);

		RenamePHPElementActionDelegateProxy action = new RenamePHPElementActionDelegateProxy();
		assertTrue(action.isModelElement(source));
		assertTrue(action.isSourceReference(source));
	}

	public void testIsScriptContainer() {
		RenamePHPElementActionDelegateProxy action = new RenamePHPElementActionDelegateProxy();

		// script project case
		final IScriptProject scriptProject = DLTKCore.create(project1.getProject());
		assertTrue(action.isScriptContainer(scriptProject));

		// script fragment case
		IPath fPath = new Path("/project1/src");
		IFolder folder = project1.getFolder(fPath);
		IModelElement element = scriptProject.getProjectFragment(folder);
		assertTrue(action.isScriptContainer(element));

		// script folder case
		fPath = new Path("/project1/src/src1");
		folder = project1.getFolder(fPath);
		element = DLTKCore.create(folder);
		assertTrue(action.isScriptContainer(element));

		// resource folder case
		folder = project1.getFolder("src2");
		assertFalse(action.isScriptContainer(folder));

		// resource file case
		IFile file = project1.getFile("src/src1/test1.php");
		assertFalse(action.isScriptContainer(folder));

		// IModelElement case
		Program program = null;
		try {
			program = RefactoringUtility.getProgramForFile(file);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertFalse(action.isScriptContainer(program));
	}

	@Override
	protected void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

}
