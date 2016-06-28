/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.refactoring;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.core.model.ModelContainer;
import org.eclipse.php.composer.core.utils.DocumentStore;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;

import com.fasterxml.jackson.core.JsonParseException;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
public class RenameComposerProjectChange extends Change {

	private class FileDocumentStore implements DocumentStore {

		private IFile file;
		private CharArrayWriter caw;

		public FileDocumentStore(IFile file) {
			this.file = file;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.zend.php.composer.internal.ui.editors.JFaceDocumentStore#write()
		 */
		@Override
		public void write() throws CoreException {
			file.setContents(
					new ByteArrayInputStream(caw.toString().getBytes()), true,
					true, new NullProgressMonitor());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.zend.php.composer.core.utils.DocumentStore#getOutput()
		 */
		@Override
		public CharArrayWriter getOutput() throws IOException {
			caw = new CharArrayWriter();
			return caw;
		}

	}

	private IProject oldProject;

	private IProject newProject;

	/**
	 * 
	 */
	public RenameComposerProjectChange(IProject oldProject, IProject newProject) {
		this.oldProject = oldProject;
		this.newProject = newProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
	 */
	@Override
	public Object getModifiedElement() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Change#getName()
	 */
	@Override
	public String getName() {
		return Messages.RenameComposerProjectChange_ChangeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.
	 * eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void initializeValidationData(IProgressMonitor arg0) {
		// nothing to initialize
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	@Override
	public RefactoringStatus isValid(IProgressMonitor arg0)
			throws CoreException, OperationCanceledException {
		return RefactoringStatus.create(Status.OK_STATUS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	@Override
	public Change perform(IProgressMonitor monitor) throws CoreException {
		IFile composerFile = newProject.getFile(ComposerService.COMPOSER_JSON);
		if (composerFile.exists()) {
			ModelContainer modelContainer = new ModelContainer();
			modelContainer.setOutput(new FileDocumentStore(composerFile));
			try {
				modelContainer.deserialize(composerFile.getContents());
				modelContainer.getModel().getName()
						.setValue(newProject.getName(), true);
			} catch (JsonParseException e) {
				ComposerUIPlugin.logError(e);
			} catch (IOException e) {
				ComposerUIPlugin.logError(e);
			}
		}

		return new RenameComposerProjectChange(newProject, oldProject);
	}
}
