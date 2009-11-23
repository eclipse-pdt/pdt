package org.eclipse.php.internal.core.ast.locator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class PhpElementConciliatorTest extends TestCase {

	private IProject project1;

	@Override
	protected void setUp() throws Exception {
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = createProject("project1");

	}

	private IFile setFileContent(String content) throws CoreException {
		IFile file = project1.getFile("test1.php");
		InputStream source = new ByteArrayInputStream(content.getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		return file;
	}

	public void testConcileClassName() {
		IFile file = null;
		try {
			file = setFileContent("<?php class TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 13;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 7;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileClassName1() {
		IFile file = null;
		try {
			file = setFileContent("<?php class TestRenameClass{} class TestExtendedClass extends TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 63;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileClassName2() {
		IFile file = null;
		try {
			file = setFileContent("<?php class A{function foo(){}} class B{function bar(){}} $a = new A();$a->foo(); A::foo(); $b = new B();$b->bar();B::bar();?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 68;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		start = 83;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileInterface() {
		IFile file = null;
		try {
			file = setFileContent("<?php interface iTemplate{public function setVariable($name, $var);}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 6;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 17;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileInterface1() {
		IFile file = null;
		try {
			file = setFileContent("<?php interface iTemplate{public function setVariable($name, $var);} class Template implements iTemplate{  public function setVariable($name, $var){}}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 6;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

		// select the class declaration
		start = 96;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME,
				PhpElementConciliator.concile(selectedNode));

	}

	public void testConcileProgram() {
		IFile file = null;
		try {
			file = setFileContent("<?php class TestRenameClass{}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		assertEquals(PhpElementConciliator.CONCILIATOR_PROGRAM,
				PhpElementConciliator.concile(program));
	}

	public void testConcileGlobalVar() {
		IFile file = null;
		try {
			file = setFileContent("<?php $a = 1;  function test(){  global $a; echo $a;} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the variable declaration.
		int start = 7;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// select the 'global $a'
		start = 41;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));

		// select the 'echo $a'
		start = 50;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileGlobalVar1() {
		IFile file = null;
		try {
			file = setFileContent("<?php $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		int start = 48;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileFunc() {
		IFile file = null;
		try {
			file = setFileContent("<?php $a = 1;  function test(){ echo $GLOBALS['a'];} ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 16;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION,
				PhpElementConciliator.concile(selectedNode));

		// selection the function name.
		start = 25;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_FUNCTION,
				PhpElementConciliator.concile(selectedNode));
	}

	public void testConcileConstant() {
		IFile file = null;
		try {
			file = setFileContent("<?php define('CONSTANT', 'Hello world.'); echo CONSTANT; echo Constant; ?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the function declaration.
		int start = 6;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 15;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 48;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

		start = 63;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CONSTANT,
				PhpElementConciliator.concile(selectedNode));

	}

	protected Program createProgram(IFile file) {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		Program program = null;
		try {
			program = createProgramFromSource(sourceModule);

		} catch (Exception e) {
			fail(e.getMessage());
		}
		return program;
	}

	public Program createProgramFromSource(IFile file) throws Exception {
		ISourceModule source = DLTKCore.createSourceModuleFrom(file);
		return createProgramFromSource(source);
	}

	public Program createProgramFromSource(ISourceModule source)
			throws Exception {
		IResource resource = source.getResource();
		IProject project = null;
		if (resource instanceof IFile) {
			project = ((IFile) resource).getProject();
		}
		PHPVersion version;
		if (project != null) {
			version = ProjectOptions.getPhpVersion(project);
		} else {
			version = ProjectOptions.getDefaultPhpVersion();
		}
		ASTParser newParser = ASTParser.newParser(version,
				(ISourceModule) source);
		return newParser.createAST(null);
	}

	protected ASTNode locateNode(Program program, int start, int end) {
		ASTNode locateNode = NodeFinder.perform(program, start, end);
		return locateNode;
	}

	public IProject createProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				name);
		if (project.exists()) {
			return project;
		}
		try {
			project.create(null);

			project.open(IResource.BACKGROUND_REFRESH,
					new NullProgressMonitor());
			IProjectDescription desc = project.getDescription();
			desc.setNatureIds(new String[] { PHPNature.ID });
			project.setDescription(desc, null);

			project.refreshLocal(IResource.DEPTH_INFINITE, null);
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		return project;
	}

	public IProject createProject(String name, PHPVersion version) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				name);
		if (project.exists()) {
			return project;
		}
		try {
			project.create(null);

			project.open(IResource.BACKGROUND_REFRESH,
					new NullProgressMonitor());
			IProjectDescription desc = project.getDescription();
			desc.setNatureIds(new String[] { PHPNature.ID });
			project.setDescription(desc, null);

			ProjectOptions.setPhpVersion(version, project);

			project.refreshLocal(IResource.DEPTH_INFINITE, null);
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		return project;
	}

}
