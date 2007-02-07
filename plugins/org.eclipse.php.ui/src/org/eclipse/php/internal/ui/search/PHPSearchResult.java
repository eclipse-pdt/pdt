/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.search.decorators.*;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

/**
 * PHP Search results.
 * 
 * @author shalom
 */
public class PHPSearchResult extends AbstractTextSearchResult implements IEditorMatchAdapter, IFileMatchAdapter {

	private PHPSearchQuery fQuery;
	private static final Match[] NO_MATCHES = new Match[0];
	private ISearchResultListener fFilterListener;

	public PHPSearchResult(PHPSearchQuery query) {
		this.fQuery = query;
	}

	public ImageDescriptor getImageDescriptor() {
		return fQuery.getImageDescriptor();
	}

	public String getLabel() {
		return fQuery.getResultLabel(getMatchCount());
	}

	public String getTooltip() {
		return getLabel();
	}

	public void setFilterListner(ISearchResultListener listener) {
		fFilterListener = listener;
	}

	protected void fireChange(SearchResultEvent e) {
		if (fFilterListener != null) {
			fFilterListener.searchResultChanged(e);
		}
		super.fireChange(e);
		
	}

	public Match[] computeContainedMatches(AbstractTextSearchResult result, IEditorPart editor) {
		final PHPStructuredEditor structuredEditor = EditorUtility.getPHPStructuredEditor(editor);
		if (structuredEditor != null)
			editor = structuredEditor;
		
		IEditorInput editorInput= editor.getEditorInput();
		if (editorInput instanceof IFileEditorInput)  {
			IFileEditorInput fileEditorInput= (IFileEditorInput) editorInput;
			return computeContainedMatches(result, fileEditorInput.getFile());
		}
		return NO_MATCHES;
	}

	public Match[] computeContainedMatches(AbstractTextSearchResult result, IFile file) {
		Set matches= new HashSet();
		PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
		if (projectModel != null) {
			collectMatches(matches, projectModel.getFileData(file.getFullPath().toString()));
		}
		return (Match[]) matches.toArray(new Match[matches.size()]);
	}
	
	private void collectMatches(Set matches, PHPFileData fileData) {
		if (fileData == null) {
			return;
		}
		PHPClassData[] classes = fileData.getClasses();
		switch (fQuery.getSpecification().getScope().getSearchFor()) {
			case IPHPSearchConstants.CLASS:
				PHPClassData[] classData = fileData.getClasses();
				for (int i = 0; i < classData.length; i++) {
					// no need to pass the IProject since it is not count in the hash function...
					addToMatches(matches, new PHPClassDataDecorator(classData[i], null));
				}
				break;
			case IPHPSearchConstants.FUNCTION:
				PHPFunctionData[] functions = fileData.getFunctions();
				for (int i = 0; i < functions.length; i++) {
					// no need to pass the IProject since it is not count in the hash function...
					addToMatches(matches, new PHPFunctionDataDecorator(functions[i], null));
				}				
				for (int i = 0; i < classes.length; i++) {
					functions = classes[i].getFunctions();
					for (int j = 0; j < functions.length; j++) {
						// no need to pass the IProject since it is not count in the hash function...
						addToMatches(matches, new PHPFunctionDataDecorator(functions[j], null));
					}
				}
				break;
			case IPHPSearchConstants.CONSTANT:
				PHPConstantData[] constants = fileData.getConstants();
				for (int i = 0; i < constants.length; i++) {
					// no need to pass the IProject since it is not count in the hash function...
					addToMatches(matches, new PHPConstantDataDecorator(constants[i], null));
				}
				
				for (int i = 0; i < classes.length; i++) {
					PHPClassConstData classConsts[] = classes[i].getConsts();
					for (int j = 0; j < classConsts.length; j++) {
						// no need to pass the IProject since it is not count in the hash function...
						addToMatches(matches, new PHPClassConstantDataDecorator(classConsts[j], null));
					}
				}
				
				break;
		}
	}

	// Add matches
	private void addToMatches(Set matches, PHPDataDecorator decorator) {
		Match[] hits = getMatches(decorator);
		if (hits != null) {
			for (int i = 0; i < hits.length; i++) {
				matches.add(hits[i]);
			}
		}
		// Collections.addAll(matches, hits); // When we move to JRE 5x
	}

	/* (non-Javadoc)
	 * @see org.eclipse.search.ui.ISearchResultCategory#getFile(java.lang.Object)
	 */
	public IFile getFile(Object element) {
		if (element instanceof CodeData) {
			CodeData cd = (CodeData) element;
			return (IFile)(cd.getAdapter(IResource.class));
		}
		if (element instanceof IFile)
			return (IFile) element;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.search2.ui.text.IStructureProvider#isShownInEditor(org.eclipse.search2.ui.text.Match,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public boolean isShownInEditor(Match match, IEditorPart editor) {
		final PHPStructuredEditor structuredEditor = EditorUtility.getPHPStructuredEditor(editor);
		if (structuredEditor != null)
			editor = structuredEditor;
		
		IEditorInput editorInput = editor.getEditorInput();
		if (match.getElement() instanceof PHPCodeData) {
			PHPCodeData data = (PHPCodeData) match.getElement();
			IFile file = (IFile)((IAdaptable)data).getAdapter(IResource.class);
			if (file != null && editorInput != null && editorInput instanceof IFileEditorInput)  {
				IFileEditorInput fileEditorInput= (IFileEditorInput) editorInput;
				return file.equals(fileEditorInput.getFile());
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.search.ui.ISearchResult#getQuery()
	 */
	public ISearchQuery getQuery() {
		return fQuery;
	}

	public IFileMatchAdapter getFileMatchAdapter() {
		return this;
	}

	public IEditorMatchAdapter getEditorMatchAdapter() {
		return this;
	}

}
