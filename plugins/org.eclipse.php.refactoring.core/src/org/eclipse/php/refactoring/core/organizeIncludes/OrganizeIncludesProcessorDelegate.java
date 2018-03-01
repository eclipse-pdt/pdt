/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.php.refactoring.core.RefactoringPlugin;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * Organize imports operation
 * 
 * @author Seva, 2007 (updated by Roy, 2008)
 * 
 */
public class OrganizeIncludesProcessorDelegate {

	private IProject project;

	private final IFile file;

	private IStructuredModel model;

	IStructuredDocument document;

	// PHPFileData fileData;
	//
	// PHPProjectModel projectModel;
	//
	// FileNode fileNode;
	//
	// FileNetwork network;

	Set<String> directIncludes;

	public OrganizeIncludesProcessorDelegate(IFile file) {
		this.file = file;
	}

	public boolean initializeModel() {
		project = file.getProject();
		try { // don't proceed until the project is built
			project.build(IncrementalProjectBuilder.AUTO_BUILD, null);
		} catch (CoreException e) {
		}
		// projectModel =
		// PHPWorkspaceModelManager.getInstance().getModelForProject(project,
		// true);
		// network =
		// FileNetworkModelManager.getDefault().getNetwork(projectModel);
		//
		// fileData =
		// PHPWorkspaceModelManager.getInstance().getModelForFile(file.getFullPath().toString(),
		// true);
		// if (fileData == null)
		// return false;
		//
		// fileNode = network.getNode(fileData);
		// if (fileNode == null) {
		// return false;
		// }
		// directIncludes = OrganizeIncludesUtils.getDirectIncludes(network,
		// fileData);
		try {
			model = StructuredModelManager.getModelManager().getModelForRead(file);
			document = model.getStructuredDocument();
			return true;
		} catch (IOException e) {
			RefactoringPlugin.logException(e);
		} catch (CoreException e) {
			RefactoringPlugin.logException(e);
		}
		disposeModel();
		return false;
	}

	// 0. Prepare the storages:
	public Change createChange(IProgressMonitor monitor) {
		// BucketMap<String, CodeData> existingIncludes = new BucketMap<String,
		// CodeData>();
		// BucketMap<PHPFileData, CodeData> missingHardIncludes = new
		// BucketMap<PHPFileData, CodeData>();
		// BucketMap<PHPFileData, CodeData> missingSoftIncludes = new
		// BucketMap<PHPFileData, CodeData>();
		// List<PHPIncludeFileData> unneededIncludes = new
		// ArrayList<PHPIncludeFileData>();
		// List<PHPIncludeFileData> unresolvedIncludes = new
		// ArrayList<PHPIncludeFileData>();
		//
		//		monitor.beginTask(PHPRefactoringCoreMessages.getString("OrganizeIncludesProcessorDelegate.0"), 11); //$NON-NLS-1$
		//
		// // 1. Find the elements:
		// if (monitor.isCanceled())
		// return null;
		// DoubleBucketMap<String, CodeDataMatch, CodeData> foundClasses =
		// CodeDataSearchEngine.searchClasses(model, new
		// SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// DoubleBucketMap<String, CodeDataMatch, CodeData> foundInterfaces =
		// CodeDataSearchEngine.searchInterfaces(model, new
		// SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// DoubleBucketMap<String, CodeDataMatch, CodeData> foundFunctions =
		// CodeDataSearchEngine.searchFunctions(model, new
		// SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// DoubleBucketMap<String, CodeDataMatch, CodeData> foundConstants =
		// CodeDataSearchEngine.searchConstants(model, new
		// SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// DoubleBucketMap<String, CodeDataMatch, CodeData> foundCallbacks =
		// CodeDataSearchEngine.searchCallbacks(model, new
		// SubProgressMonitor(monitor, 1));

		// 2. resolve includes for the elements:
		if (monitor.isCanceled())
		 {
			return null;
		// resolveIncludes(foundClasses, existingIncludes, missingHardIncludes,
		// missingSoftIncludes, new SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// resolveIncludes(foundInterfaces, existingIncludes,
		// missingHardIncludes, missingSoftIncludes, new
		// SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// resolveIncludes(foundFunctions, existingIncludes,
		// missingHardIncludes, missingSoftIncludes, new
		// SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// resolveIncludes(foundConstants, existingIncludes,
		// missingHardIncludes, missingSoftIncludes, new
		// SubProgressMonitor(monitor, 1));
		// if (monitor.isCanceled())
		// return null;
		// resolveIncludes(foundCallbacks, existingIncludes,
		// missingHardIncludes, missingSoftIncludes, new
		// SubProgressMonitor(monitor, 1));
		//
		// // 3. Collect unneeded includes:
		// if (monitor.isCanceled())
		// return null;
		// collectUnneededIncludes(existingIncludes, unneededIncludes,
		// unresolvedIncludes, new SubProgressMonitor(monitor, 1));
		//
		// // 4. Create the change:
		// OrganizeIncludesChange change = new OrganizeIncludesChange(this);
		// change.addEdits(existingIncludes, missingHardIncludes,
		// missingSoftIncludes, unneededIncludes, unresolvedIncludes);
		}

		// TextEditChangeGroup[] textEditChangeGroups =
		// change.getTextEditChangeGroups();
		// if (textEditChangeGroups.length == 0)
		// return null;
		// return change;
		return null;
	}

	// public PHPFileData getFileData() {
	// return fileData;
	// }

	public IFile getFile() {
		return file;
	}

	public IStructuredDocument getDocument() {
		return document;
	}

	// private void resolveIncludes(DoubleBucketMap<String, CodeDataMatch,
	// CodeData> foundElements, BucketMap<String, CodeData> existingIncludes,
	// BucketMap<PHPFileData, CodeData> missingHardIncludes,
	// BucketMap<PHPFileData, CodeData> missingSoftIncludes, IProgressMonitor
	// monitor) {
	//		monitor.beginTask(PHPRefactoringCoreMessages.getString("OrganizeIncludesProcessorDelegate.1"), foundElements.getFirst().getKeys().size()); //$NON-NLS-1$
	// for (String elementName : foundElements.getFirst().getKeys()) {
	// CodeData elementData = null;
	// PHPFileData container = null;
	// boolean containerResolved = false;
	// Set<CodeData> elementCandidates = new HashSet<CodeData>();
	// for (CodeData elementDataCandidate :
	// foundElements.getSecond().getSet(elementName)) {
	// PHPFileData elementContainerCandidate =
	// PHPModelUtil.getPHPFileContainer((PHPCodeData) elementDataCandidate);
	// if (elementContainerCandidate == null ||
	// elementContainerCandidate.getName().equals(fileData.getName())) {
	// containerResolved = true;
	// break;
	// }
	// if (directIncludes.contains(elementContainerCandidate.getName())) {
	// container = elementContainerCandidate;
	// elementData = elementDataCandidate;
	// containerResolved = true;
	// existingIncludes.add(container.getName(), elementData);
	// break;
	// }
	// elementCandidates.add(elementDataCandidate);
	// if (monitor.isCanceled())
	// return;
	// }
	// if (containerResolved)
	// continue;
	//
	// if (elementCandidates.size() == 1) {
	// elementData = elementCandidates.iterator().next();
	// container = PHPModelUtil.getPHPFileContainer((PHPCodeData) elementData);
	// } else {
	// ElementDialogOpener opener = new
	// ElementDialogOpener(elementCandidates.toArray(new
	// CodeData[elementCandidates.size()]), elementName, file);
	// Display.getDefault().syncExec(opener);
	// elementData = opener.getResult();
	// if (elementData == null)
	// continue;
	// }
	//
	// Set<CodeDataMatch> matches =
	// foundElements.getFirst().getSet(elementName);
	// arrangeMissingIncludes(elementData, matches, missingHardIncludes,
	// missingSoftIncludes);
	// if (monitor.isCanceled())
	// return;
	// monitor.worked(1);
	// }
	// }
	//
	// private void arrangeMissingIncludes(CodeData elementData,
	// Set<CodeDataMatch> matches, BucketMap<PHPFileData, CodeData>
	// missingHardIncludes, BucketMap<PHPFileData, CodeData>
	// missingSoftIncludes) {
	// PHPFileData container = PHPModelUtil.getPHPFileContainer((PHPCodeData)
	// elementData);
	// boolean matchHard = isHardMatch(matches);
	//
	// // same container should be either hard or soft - not both
	// if (matchHard) {
	// missingHardIncludes.add(container, elementData);
	// Collection<CodeData> exSoft = missingSoftIncludes.removeAll(container);
	// if (exSoft.size() > 0)
	// missingHardIncludes.addAll(container, exSoft);
	// } else {
	// Set<CodeData> set = missingHardIncludes.get(container);
	// if (set.size() > 0)
	// missingHardIncludes.add(container, elementData);
	// else
	// missingSoftIncludes.add(container, elementData);
	// }
	// }

	private static boolean isHardMatch(Set<CodeDataMatch> matches) {
		for (CodeDataMatch match : matches) {
			if (!CodeDataSearchEngine.elementIsOptional(match.getElementType())) {
				return true;
			}
			break;
		}
		return false;
	}

	void disposeModel() {
		if (model != null) {
			model.releaseFromRead();
		}
	}

	// private void collectUnneededIncludes(BucketMap<String, CodeData>
	// existingIncludes, List<PHPIncludeFileData> unneededIncludes,
	// List<PHPIncludeFileData> unresolvedIncludes, IProgressMonitor monitor) {
	// PHPIncludeFileData[] includes = fileData.getIncludeFiles();
	//		monitor.beginTask(PHPRefactoringCoreMessages.getString("OrganizeIncludesProcessorDelegate.2"), includes.length); //$NON-NLS-1$
	// for (PHPIncludeFileData element : includes) {
	// FileNode includedNode =
	// fileNode.getIncluded(FileNetworkUtils.getFilePath(element));
	// if (includedNode == null)
	// unresolvedIncludes.add(element);
	// else if (existingIncludes.get(includedNode.getFile().getName()).size() ==
	// 0)
	// unneededIncludes.add(element);
	// if (monitor.isCanceled())
	// return;
	// monitor.worked(1);
	// }
	// }

	public IProject getProject() {
		return project;
	}
}
