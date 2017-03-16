package org.eclipse.php.refactoring.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.junit.Assert;

public abstract class AbstractRefactoringTest {

	protected static final char OFFSET_CHAR = '|';

	protected Program createProgram(IFile file) throws Exception {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		Program program = null;
		program = ASTUtils.createProgramFromSource(sourceModule);
		return program;
	}

	protected void performChange(RefactoringProcessor processor) {
		try {
			Change change = processor.createChange(new NullProgressMonitor());
			if (change != null) {
				change.perform(new NullProgressMonitor());
			}
		} catch (OperationCanceledException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (CoreException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		TestUtils.waitForIndexer();
	}

	protected void checkInitCondition(RefactoringProcessor processor) {
		try {
			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}
	}

	protected void checkFinalCondition(RefactoringProcessor processor) {
		try {
			RefactoringStatus status = processor.checkFinalConditions(new NullProgressMonitor(), null);
			assertNotSame(Status.ERROR, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}
	}

	protected void performChange(Refactoring processor) {
		try {
			Change change = processor.createChange(new NullProgressMonitor());
			change.perform(new NullProgressMonitor());
		} catch (OperationCanceledException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	protected void checkInitCondition(Refactoring processor) {
		try {
			RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(Status.OK, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}
	}

	protected ASTNode locateNode(Program program, int start, int end) {
		ASTNode locateNode = NodeFinder.perform(program, start, end);
		return locateNode;
	}

	protected IStructuredModel createUnManagedStructuredModelFor(IFile file) throws IOException, CoreException {
		IStructuredModel model = null;
		try {
			model = StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(file);
		} catch (Exception e) {
			try {
				Thread.sleep(3000);
				model = StructuredModelManager.getModelManager().createUnManagedStructuredModelFor(file);
			} catch (InterruptedException e1) {
				Assert.fail(e1.getMessage());
			}
		}
		return model;
	}

}
