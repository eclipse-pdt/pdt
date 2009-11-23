package org.eclipse.php.internal.core.ast.locator;

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

import junit.framework.TestCase;

public abstract class AbstraceConciliatorTest extends TestCase {

	public AbstraceConciliatorTest() {
		super();
	}

	public AbstraceConciliatorTest(String name) {
		super(name);
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

	public Program createProgramFromSource(ISourceModule source) throws Exception {
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