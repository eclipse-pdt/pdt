/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.changes;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ltk.core.refactoring.TextEditBasedChangeGroup;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.core.ast.nodes.Program;

/**
 * A program change is a text file change with augmented AST program
 * 
 * @author Roy, 2007
 */
public class ProgramFileChange extends TextFileChange {

	private final Program program;

	public ProgramFileChange(String name, IFile file, Program program) {
		super(name, file);
		this.program = program;
	}

	public Program getProgram() {
		return program;
	}

	/**
	 * Adapt to Program
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Change#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (adapter == Program.class) {
			return program;
		}
		return super.getAdapter(adapter);
	}

	/**
	 * Get the content from the begining (for syntax highlighting reasons) to
	 * the end of the changes
	 * 
	 * @see org.eclipse.ltk.core.refactoring.TextChange#getCurrentContent(org.eclipse.jface.text.IRegion,
	 *      boolean, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getCurrentContent(IRegion region,
			boolean expandRegionToFullLine, int surroundingLines,
			IProgressMonitor pm) throws CoreException {
		IRegion fixed = new Region(0, region.getOffset() + region.getLength());
		return super.getCurrentContent(fixed, expandRegionToFullLine,
				surroundingLines, pm);
	}

	/**
	 * Get the content from the begining (for syntax highlighting reasons) to
	 * the end of the changes
	 * 
	 * @see org.eclipse.ltk.core.refactoring.TextChange#getPreviewContent(org.eclipse.ltk.core.refactoring.TextEditBasedChangeGroup[],
	 *      org.eclipse.jface.text.IRegion, boolean, int,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getPreviewContent(TextEditBasedChangeGroup[] changeGroups,
			IRegion region, boolean expandRegionToFullLine,
			int surroundingLines, IProgressMonitor pm) throws CoreException {
		IRegion fixed = new Region(0, region.getOffset() + region.getLength());
		return super.getPreviewContent(changeGroups, fixed,
				expandRegionToFullLine, surroundingLines, pm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.TextFileChange#acquireDocument(org.eclipse
	 * .core.runtime.IProgressMonitor)
	 */
	@Override
	protected IDocument acquireDocument(IProgressMonitor pm)
			throws CoreException {
		// IFile file = getFile();
		// if (file instanceof ExternalFileWrapper) {
		// documentModel =
		// StructuredModelManager.getModelManager().getExistingModelForEdit(file);
		// return documentModel.getStructuredDocument();
		// }
		return super.acquireDocument(pm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.TextFileChange#releaseDocument(org.eclipse
	 * .jface.text.IDocument, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void releaseDocument(IDocument document, IProgressMonitor pm)
			throws CoreException {
		// IFile file = getFile();
		// if (file instanceof ExternalFileWrapper) {
		// documentModel.releaseFromEdit();
		// return;
		// }
		// super.releaseDocument(document, pm);

		boolean isModified = isDocumentModified();
		super.releaseDocument(document, pm);
		ISourceModule sm = program.getSourceModule();
		if (isModified && !isDocumentAcquired()) {
			if (sm.isWorkingCopy())
				sm.reconcile(false /* don't force problem detection */,
						null /* use primary owner */, null /*
														 * no progress monitor
														 */);

			else
				sm.makeConsistent(pm);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.TextFileChange#commit(org.eclipse.jface
	 * .text.IDocument, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void commit(IDocument document, IProgressMonitor pm)
			throws CoreException {
		// if (getFile() instanceof ExternalFileWrapper) {
		// try {
		// documentModel.save();
		// } catch (UnsupportedEncodingException e) {
		// } catch (IOException e) {
		// }
		// return;
		// }
		super.commit(document, pm);
	}
}
