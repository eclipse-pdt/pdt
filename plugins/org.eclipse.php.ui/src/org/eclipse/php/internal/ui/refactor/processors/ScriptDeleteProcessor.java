/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import java.util.*;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.*;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringAvailabilityTester;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.refactoring.participants.ResourceProcessors;
import org.eclipse.dltk.internal.corext.refactoring.participants.ScriptProcessors;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IConfirmQuery;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IReorgQueries;
import org.eclipse.dltk.internal.corext.refactoring.reorg.ParentChecker;
import org.eclipse.dltk.internal.corext.refactoring.tagging.ICommentProvider;
import org.eclipse.dltk.internal.corext.refactoring.util.ModelElementUtil;
import org.eclipse.dltk.internal.corext.refactoring.util.ResourceUtil;
import org.eclipse.dltk.internal.corext.refactoring.util.TextChangeManager;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.corext.util.Resources;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.*;

public final class ScriptDeleteProcessor extends DeleteProcessor implements
		ICommentProvider {

	private boolean fWasCanceled;
	private Object[] fElements;
	private IResource[] fResources;
	private IModelElement[] fScriptElements;
	private IReorgQueries fDeleteQueries;
	private DeleteModifications fDeleteModifications;
	private String fComment;

	private Change fDeleteChange;
	private boolean fDeleteSubPackages;

	public static final String IDENTIFIER = "org.eclipse.dltk.ui.DeleteProcessor"; //$NON-NLS-1$ 

	public ScriptDeleteProcessor(Object[] elements) {
		fElements = elements;
		fResources = RefactoringAvailabilityTester.getResources(elements);
		fScriptElements = RefactoringAvailabilityTester
				.getScriptElements(elements);
		fDeleteSubPackages = false;
		fWasCanceled = false;
	}

	// ---- IRefactoringProcessor
	// ---------------------------------------------------

	public String getIdentifier() {
		return IDENTIFIER;
	}

	public boolean isApplicable() throws CoreException {
		if (fElements.length == 0)
			return false;
		if (fElements.length != fResources.length + fScriptElements.length)
			return false;
		for (int i = 0; i < fResources.length; i++) {
			if (!RefactoringAvailabilityTester.isDeleteAvailable(fResources[i]))
				return false;
		}
		for (int i = 0; i < fScriptElements.length; i++) {
			if (!RefactoringAvailabilityTester
					.isDeleteAvailable(fScriptElements[i]))
				return false;
		}
		return true;
	}

	public boolean needsProgressMonitor() {
		if (fResources != null && fResources.length > 0)
			return true;
		if (fScriptElements != null) {
			for (int i = 0; i < fScriptElements.length; i++) {
				int type = fScriptElements[i].getElementType();
				if (type <= IModelElement.SOURCE_MODULE)
					return true;
			}
		}
		return false;

	}

	public String getProcessorName() {
		return RefactoringCoreMessages.DeleteRefactoring_7;
	}

	public Object[] getElements() {
		return fElements;
	}

	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants shared) throws CoreException {
		return fDeleteModifications.loadParticipants(status, this,
				getAffectedProjectNatures(), shared);
	}

	private String[] getAffectedProjectNatures() throws CoreException {
		String[] jNatures = ScriptProcessors
				.computeAffectedNaturs(fScriptElements);
		String[] rNatures = ResourceProcessors
				.computeAffectedNatures(fResources);
		Set result = new HashSet();
		result.addAll(Arrays.asList(jNatures));
		result.addAll(Arrays.asList(rNatures));
		return (String[]) result.toArray(new String[result.size()]);
	}

	public void setDeleteSubPackages(boolean selection) {
		fDeleteSubPackages = selection;
	}

	public boolean getDeleteSubPackages() {
		return fDeleteSubPackages;
	}

	public boolean hasSubPackagesToDelete() {
		try {
			for (int i = 0; i < fScriptElements.length; i++) {
				if (fScriptElements[i] instanceof IScriptFolder) {
					IScriptFolder scriptFolder = (IScriptFolder) fScriptElements[i];
					if (scriptFolder.isRootFolder())
						continue; // see bug 132576 (can remove this if(..)
					// continue; statement when bug is fixed)
					if (scriptFolder.hasSubfolders())
						return true;
				}
			}
		} catch (ModelException e) {
			DLTKUIPlugin.log(e);
		}
		return false;
	}

	public void setQueries(IReorgQueries queries) {
		Assert.isNotNull(queries);
		fDeleteQueries = queries;
	}

	public IModelElement[] getScriptElementsToDelete() {
		return fScriptElements;
	}

	public boolean wasCanceled() {
		return fWasCanceled;
	}

	public IResource[] getResourcesToDelete() {
		return fResources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.corext.refactoring.base.Refactoring#checkActivation
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException {
		Assert.isNotNull(fDeleteQueries);// must be set before checking
		// activation
		RefactoringStatus result = new RefactoringStatus();
		result.merge(RefactoringStatus.create(Resources.checkInSync(ReorgUtils
				.getNotLinked(fResources))));
		IResource[] javaResources = ReorgUtils.getResources(fScriptElements);
		result.merge(RefactoringStatus.create(Resources.checkInSync(ReorgUtils
				.getNotLinked(javaResources))));
		for (int i = 0; i < fScriptElements.length; i++) {
			// IModelElement element= fScriptElements[i];
			// if (element instanceof IType && ((IType)element).isAnonymous()) {
			// // work around for bug
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=44450
			// //
			// result.addFatalError("Currently, there isn't any support to delete an anonymous type.");
			// }
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.corext.refactoring.base.Refactoring#checkInput
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException {
		pm.beginTask(RefactoringCoreMessages.DeleteRefactoring_1, 1);
		try {
			fWasCanceled = false;
			RefactoringStatus result = new RefactoringStatus();

			recalculateElementsToDelete();

			checkDirtySourceModules(result);
			checkDirtyResources(result);
			fDeleteModifications = new DeleteModifications();
			fDeleteModifications.delete(fResources);
			fDeleteModifications.delete(fScriptElements);
			List packageDeletes = fDeleteModifications.postProcess();

			TextChangeManager manager = new TextChangeManager();
			fDeleteChange = DeleteChangeCreator.createDeleteChange(manager,
					fResources, fScriptElements, getProcessorName(),
					packageDeletes);

			ResourceChangeChecker checker = (ResourceChangeChecker) context
					.getChecker(ResourceChangeChecker.class);
			IResourceChangeDescriptionFactory deltaFactory = checker
					.getDeltaFactory();
			fDeleteModifications.buildDelta(deltaFactory);
			IFile[] files = getBuildpathFiles();
			for (int i = 0; i < files.length; i++) {
				deltaFactory.change(files[i]);
			}
			files = ResourceUtil.getFiles(manager.getAllSourceModules());
			for (int i = 0; i < files.length; i++) {
				deltaFactory.change(files[i]);
			}
			return result;
		} catch (OperationCanceledException e) {
			fWasCanceled = true;
			throw e;
		} catch (ModelException e) {
			throw e;
		} catch (CoreException e) {
			throw new ModelException(e);
		} finally {
			pm.done();
		}
	}

	private void checkDirtySourceModules(RefactoringStatus result)
			throws CoreException {
		if (fScriptElements == null || fScriptElements.length == 0)
			return;
		for (int je = 0; je < fScriptElements.length; je++) {
			IModelElement element = fScriptElements[je];
			if (element instanceof ISourceModule) {
				checkDirtySourceModule(result, (ISourceModule) element);
			} else if (element instanceof IScriptFolder) {
				ISourceModule[] units = ((IScriptFolder) element)
						.getSourceModules();
				for (int u = 0; u < units.length; u++) {
					checkDirtySourceModule(result, units[u]);
				}
			}
		}
	}

	private void checkDirtySourceModule(RefactoringStatus result,
			ISourceModule cunit) {
		IResource resource = cunit.getResource();
		if (resource == null || resource.getType() != IResource.FILE)
			return;
		checkDirtyFile(result, (IFile) resource);
	}

	private void checkDirtyResources(final RefactoringStatus result)
			throws CoreException {
		for (int i = 0; i < fResources.length; i++) {
			IResource resource = fResources[i];
			resource.accept(new IResourceVisitor() {
				public boolean visit(IResource visitedResource)
						throws CoreException {
					if (visitedResource instanceof IFile) {
						checkDirtyFile(result, (IFile) visitedResource);
					}
					return true;
				}
			}, IResource.DEPTH_INFINITE, false);
		}
	}

	private void checkDirtyFile(RefactoringStatus result, IFile file) {
		if (file == null || !file.exists())
			return;
		ITextFileBuffer buffer = FileBuffers.getTextFileBufferManager()
				.getTextFileBuffer(file.getFullPath(), LocationKind.NORMALIZE);
		if (buffer != null && buffer.isDirty()) {
			if (buffer.isStateValidated() && buffer.isSynchronized()) {
				result.addWarning(Messages
						.format(RefactoringCoreMessages.ScriptDeleteProcessor_unsaved_changes,
								file.getFullPath().toString()));
			} else {
				result.addFatalError(Messages
						.format(RefactoringCoreMessages.ScriptDeleteProcessor_unsaved_changes,
								file.getFullPath().toString()));
			}
		}
	}

	/*
	 * The set of elements that will eventually be deleted may be very different
	 * from the set originally selected - there may be fewer, more or different
	 * elements. This method is used to calculate the set of elements that will
	 * be deleted - if necessary, it asks the user.
	 */
	private void recalculateElementsToDelete() throws CoreException {
		// the sequence is critical here

		if (fDeleteSubPackages) /*
								 * add subpackages first, to allow removing
								 * elements with parents in selection etc.
								 */
			addSubPackages();

		removeElementsWithParentsInSelection(); /*
												 * ask before adding empty cus -
												 * you don't want to ask if you,
												 * for example deletethe
												 * package, in which the cus
												 * live
												 */
		removeUnconfirmedFoldersThatContainSourceFolders(); /*
															 * a selected folder
															 * may be a parent
															 * of a source
															 * folder we must
															 * inform the user
															 * about it and ask
															 * if ok to delete
															 * the folder
															 */
		removeUnconfirmedReferencedArchives();
		addEmptySourceModulesToDelete();
		removeScriptElementsChildrenOfScriptElements();/*
														 * because adding cus
														 * may create elements
														 * (types in cus)whose
														 * parents are in
														 * selection
														 */
		confirmDeletingReadOnly(); /*
									 * after empty cus - you want to ask for all
									 * cus that are to be deleted
									 */

		addDeletableParentPackagesOnPackageDeletion(); /*
														 * do not change the
														 * sequence in
														 * fScriptElements after
														 * this method
														 */
	}

	/**
	 * Adds all subpackages of the selected packages to the list of items to be
	 * deleted.
	 * 
	 * @throws ModelException
	 */
	private void addSubPackages() throws ModelException {

		final Set modelElements = new HashSet();
		for (int i = 0; i < fScriptElements.length; i++) {
			if (fScriptElements[i] instanceof IScriptFolder) {
				modelElements
						.addAll(Arrays.asList(ModelElementUtil
								.getPackageAndSubpackages((IScriptFolder) fScriptElements[i])));
			} else {
				modelElements.add(fScriptElements[i]);
			}
		}

		fScriptElements = (IModelElement[]) modelElements
				.toArray(new IModelElement[modelElements.size()]);
	}

	/**
	 * Add deletable parent packages to the list of items to delete.
	 * 
	 * @throws CoreException
	 */
	private void addDeletableParentPackagesOnPackageDeletion()
			throws CoreException {

		final List/* <IScriptFolder */initialPackagesToDelete = ReorgUtils
				.getElementsOfType(fScriptElements, IModelElement.SCRIPT_FOLDER);

		if (initialPackagesToDelete.size() == 0)
			return;

		// Move from inner to outer packages
		Collections.sort(initialPackagesToDelete, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				IScriptFolder one = (IScriptFolder) arg0;
				IScriptFolder two = (IScriptFolder) arg1;
				return two.getElementName().compareTo(one.getElementName());
			}
		});

		// Get resources andscriptelements which will be deleted as well
		final Set/* <IResource> */deletedChildren = new HashSet();
		deletedChildren.addAll(Arrays.asList(fResources));
		for (int i = 0; i < fScriptElements.length; i++) {
			if (!ReorgUtils.isInsideSourceModule(fScriptElements[i]))
				deletedChildren.add(fScriptElements[i].getResource());
		}

		// new package list in the right sequence
		final List/* <IScriptFolder */allFragmentsToDelete = new ArrayList();

		for (Iterator outerIter = initialPackagesToDelete.iterator(); outerIter
				.hasNext();) {
			final IScriptFolder currentScriptFolder = (IScriptFolder) outerIter
					.next();

			// The package will at least be cleared
			allFragmentsToDelete.add(currentScriptFolder);

			if (canRemoveCompletely(currentScriptFolder,
					initialPackagesToDelete)) {

				final IScriptFolder parent = ModelElementUtil
						.getParentSubpackage(currentScriptFolder);
				if (parent != null && !initialPackagesToDelete.contains(parent)) {

					final List/* <IScriptFolder> */emptyParents = new ArrayList();
					addDeletableParentPackages(parent, initialPackagesToDelete,
							deletedChildren, emptyParents);

					// Add parents in the right sequence (inner to outer)
					allFragmentsToDelete.addAll(emptyParents);
				}
			}
		}

		// Remove resources in deleted packages; and the packages as well
		final List/* <IModelElement> */modelElements = new ArrayList();
		for (int i = 0; i < fScriptElements.length; i++) {
			if (!(fScriptElements[i] instanceof IScriptFolder)) {
				// remove children of deleted packages
				final IScriptFolder frag = (IScriptFolder) fScriptElements[i]
						.getAncestor(IModelElement.SCRIPT_FOLDER);
				if (!allFragmentsToDelete.contains(frag))
					modelElements.add(fScriptElements[i]);
			}
		}
		// Re-add deleted packages - note the (new) sequence
		modelElements.addAll(allFragmentsToDelete);

		// Remove resources in deleted folders
		final List/* <IResource> */resources = new ArrayList();
		for (int i = 0; i < fResources.length; i++) {
			IResource parent = fResources[i];
			if (parent.getType() == IResource.FILE)
				parent = parent.getParent();
			if (!deletedChildren.contains(parent))
				resources.add(fResources[i]);
		}

		fScriptElements = (IModelElement[]) modelElements
				.toArray(new IModelElement[modelElements.size()]);
		fResources = (IResource[]) resources.toArray(new IResource[resources
				.size()]);
	}

	/**
	 * Returns true if this initially selected package is really deletable (if
	 * it has non-selected subpackages, it may only be cleared).
	 * 
	 */
	private boolean canRemoveCompletely(IScriptFolder pack,
			List packagesToDelete) throws ModelException {
		final IScriptFolder[] subPackages = ModelElementUtil
				.getPackageAndSubpackages(pack);
		for (int i = 0; i < subPackages.length; i++) {
			if (!(subPackages[i].equals(pack))
					&& !(packagesToDelete.contains(subPackages[i])))
				return false;
		}
		return true;
	}

	/**
	 * Adds deletable parent packages of the fragment "frag" to the list
	 * "deletableParentPackages"; also adds the resources of those packages to
	 * the set "resourcesToDelete".
	 * 
	 */
	private void addDeletableParentPackages(IScriptFolder frag,
			List initialPackagesToDelete, Set resourcesToDelete,
			List deletableParentPackages) throws CoreException {

		if (frag.getResource().isLinked()) {
			final IConfirmQuery query = fDeleteQueries
					.createYesNoQuery(
							RefactoringCoreMessages.ScriptDeleteProcessor_confirm_linked_folder_delete,
							false, IReorgQueries.CONFIRM_DELETE_LINKED_PARENT);
			if (!query
					.confirm(Messages
							.format(RefactoringCoreMessages.ScriptDeleteProcessor_delete_linked_folder_question,
									new String[] { frag.getResource().getName() })))
				return;
		}

		final IResource[] children = (((IContainer) frag.getResource()))
				.members();
		for (int i = 0; i < children.length; i++) {
			// Child must be a package fragment already in the list,
			// or a resource which is deleted as well.
			if (!resourcesToDelete.contains(children[i]))
				return;
		}
		resourcesToDelete.add(frag.getResource());
		deletableParentPackages.add(frag);

		final IScriptFolder parent = ModelElementUtil.getParentSubpackage(frag);
		if (parent != null && !initialPackagesToDelete.contains(parent))
			addDeletableParentPackages(parent, initialPackagesToDelete,
					resourcesToDelete, deletableParentPackages);
	}

	// ask for confirmation of deletion of all package fragment roots that are
	// on buildpaths of other projects
	private void removeUnconfirmedReferencedArchives() throws ModelException {
		String queryTitle = RefactoringCoreMessages.DeleteRefactoring_2;
		IConfirmQuery query = fDeleteQueries.createYesYesToAllNoNoToAllQuery(
				queryTitle, true,
				IReorgQueries.CONFIRM_DELETE_REFERENCED_ARCHIVES);
		removeUnconfirmedReferencedProjectFragments(query);
		removeUnconfirmedReferencedArchiveFiles(query);
	}

	private void removeUnconfirmedReferencedArchiveFiles(IConfirmQuery query)
			throws ModelException, OperationCanceledException {
		List filesToSkip = new ArrayList(0);
		for (int i = 0; i < fResources.length; i++) {
			IResource resource = fResources[i];
			if (!(resource instanceof IFile))
				continue;

			IScriptProject project = DLTKCore.create(resource.getProject());
			if (project == null || !project.exists())
				continue;
			IProjectFragment root = project.findProjectFragment(resource
					.getFullPath());
			if (root == null)
				continue;
			List referencingProjects = new ArrayList(1);
			referencingProjects.add(root.getScriptProject());
			referencingProjects.addAll(Arrays.asList(ModelElementUtil
					.getReferencingProjects(root)));
			if (skipDeletingReferencedRoot(query, root, referencingProjects))
				filesToSkip.add(resource);
		}
		removeFromSetToDelete((IFile[]) filesToSkip
				.toArray(new IFile[filesToSkip.size()]));
	}

	private void removeUnconfirmedReferencedProjectFragments(IConfirmQuery query)
			throws ModelException, OperationCanceledException {
		List rootsToSkip = new ArrayList(0);
		for (int i = 0; i < fScriptElements.length; i++) {
			IModelElement element = fScriptElements[i];
			if (!(element instanceof IProjectFragment))
				continue;
			IProjectFragment root = (IProjectFragment) element;
			List referencingProjects = Arrays.asList(ModelElementUtil
					.getReferencingProjects(root));
			if (skipDeletingReferencedRoot(query, root, referencingProjects))
				rootsToSkip.add(root);
		}
		removeFromSetToDelete((IModelElement[]) rootsToSkip
				.toArray(new IModelElement[rootsToSkip.size()]));
	}

	private static boolean skipDeletingReferencedRoot(IConfirmQuery query,
			IProjectFragment root, List referencingProjects)
			throws OperationCanceledException {
		if (referencingProjects.isEmpty() || root == null || !root.exists()
				|| !root.isArchive())
			return false;
		String question = Messages.format(
				RefactoringCoreMessages.DeleteRefactoring_3,
				root.getElementName());
		return !query.confirm(question, referencingProjects.toArray());
	}

	private void removeUnconfirmedFoldersThatContainSourceFolders()
			throws CoreException {
		String queryTitle = RefactoringCoreMessages.DeleteRefactoring_4;
		IConfirmQuery query = fDeleteQueries.createYesYesToAllNoNoToAllQuery(
				queryTitle, true,
				IReorgQueries.CONFIRM_DELETE_FOLDERS_CONTAINING_SOURCE_FOLDERS);
		List foldersToSkip = new ArrayList(0);
		for (int i = 0; i < fResources.length; i++) {
			IResource resource = fResources[i];
			if (resource instanceof IFolder) {
				IFolder folder = (IFolder) resource;
				if (containsSourceFolder(folder)) {
					String question = Messages.format(
							RefactoringCoreMessages.DeleteRefactoring_5,
							folder.getName());
					if (!query.confirm(question))
						foldersToSkip.add(folder);
				}
			}
		}
		removeFromSetToDelete((IResource[]) foldersToSkip
				.toArray(new IResource[foldersToSkip.size()]));
	}

	private static boolean containsSourceFolder(IFolder folder)
			throws CoreException {
		IResource[] subFolders = folder.members();
		for (int i = 0; i < subFolders.length; i++) {
			if (!(subFolders[i] instanceof IFolder))
				continue;
			IModelElement element = DLTKCore.create(folder);
			if (element instanceof IProjectFragment)
				return true;
			if (element instanceof IScriptFolder)
				continue;
			if (containsSourceFolder((IFolder) subFolders[i]))
				return true;
		}
		return false;
	}

	private void removeElementsWithParentsInSelection() {
		ParentChecker parentUtil = new ParentChecker(fResources,
				fScriptElements);
		parentUtil.removeElementsWithAncestorsOnList(false);
		fScriptElements = parentUtil.getScriptElements();
		fResources = parentUtil.getResources();
	}

	private void removeScriptElementsChildrenOfScriptElements() {
		ParentChecker parentUtil = new ParentChecker(fResources,
				fScriptElements);
		parentUtil.removeElementsWithAncestorsOnList(true);
		fScriptElements = parentUtil.getScriptElements();
	}

	private IFile[] getBuildpathFiles() {
		List result = new ArrayList();
		for (int i = 0; i < fScriptElements.length; i++) {
			IModelElement element = fScriptElements[i];
			if (element instanceof IProjectFragment) {
				IProject project = element.getScriptProject().getProject();
				IFile buildpathFile = project.getFile(".classpath"); //$NON-NLS-1$ 
				if (buildpathFile.exists())
					result.add(buildpathFile);
			}
		}
		return (IFile[]) result.toArray(new IFile[result.size()]);
	}

	public Change createChange(IProgressMonitor pm) throws CoreException {
		pm.beginTask("", 1); //$NON-NLS-1$ 
		pm.done();
		return fDeleteChange;
	}

	private void addToSetToDelete(IModelElement[] newElements) {
		fScriptElements = ReorgUtils.union(fScriptElements, newElements);
	}

	private void removeFromSetToDelete(IResource[] resourcesToNotDelete) {
		fResources = ReorgUtils.setMinus(fResources, resourcesToNotDelete);
	}

	private void removeFromSetToDelete(IModelElement[] elementsToNotDelete) {
		fScriptElements = ReorgUtils.setMinus(fScriptElements,
				elementsToNotDelete);
	}

	// private static IField[] getFields(IModelElement[] elements){
	// List fields= new ArrayList(3);
	// for (int i= 0; i < elements.length; i++) {
	// if (elements[i] instanceof IField)
	// fields.add(elements[i]);
	// }
	// return (IField[]) fields.toArray(new IField[fields.size()]);
	// }

	// ----------- read-only confirmation business ------
	private void confirmDeletingReadOnly() throws CoreException {
		if (!ReadOnlyResourceFinder.confirmDeleteOfReadOnlyElements(
				fScriptElements, fResources, fDeleteQueries))
			throw new OperationCanceledException(); // saying 'no' to this one
		// is like cancelling the
		// whole operation
	}

	// ----------- empty source modules related method
	private void addEmptySourceModulesToDelete() throws ModelException {
		Set modulesToEmpty = getCusToEmpty();
		addToSetToDelete((ISourceModule[]) modulesToEmpty
				.toArray(new ISourceModule[modulesToEmpty.size()]));
	}

	private Set getCusToEmpty() throws ModelException {
		Set result = new HashSet();
		for (int i = 0; i < fScriptElements.length; i++) {
			IModelElement element = fScriptElements[i];
			ISourceModule module = ReorgUtils.getSourceModule(element);
			if (module != null && !result.contains(module)) {
				IDLTKLanguageToolkit toolkit = DLTKLanguageManager
						.getLanguageToolkit(module);
				if (toolkit != null
						&& toolkit
								.get(DLTKFeatures.DELETE_MODULE_WITHOUT_TOP_LEVEL_TYPES)
						&& willHaveAllTopLevelTypesDeleted(module)) {
					result.add(module);
				}
			}
		}
		return result;
	}

	private boolean willHaveAllTopLevelTypesDeleted(ISourceModule cu)
			throws ModelException {
		Set elementSet = new HashSet(Arrays.asList(fScriptElements));
		IType[] topLevelTypes = cu.getTypes();
		for (int i = 0; i < topLevelTypes.length; i++) {
			if (!elementSet.contains(topLevelTypes[i]))
				return false;
		}
		return true;
	}

	public boolean canEnableComment() {
		return true;
	}

	public String getComment() {
		return fComment;
	}

	public void setComment(String comment) {
		fComment = comment;
	}
}
