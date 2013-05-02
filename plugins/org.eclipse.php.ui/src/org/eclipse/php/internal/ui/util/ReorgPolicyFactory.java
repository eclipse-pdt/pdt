/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.internal.corext.refactoring.Checks;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.refactoring.changes.*;
import org.eclipse.dltk.internal.corext.refactoring.reorg.*;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IReorgPolicy.ICopyPolicy;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IReorgPolicy.IMovePolicy;
import org.eclipse.dltk.internal.corext.refactoring.util.ModelElementUtil;
import org.eclipse.dltk.internal.corext.refactoring.util.ResourceUtil;
import org.eclipse.dltk.internal.corext.refactoring.util.TextChangeManager;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.*;

public class ReorgPolicyFactory {
	private ReorgPolicyFactory() {
		// private
	}

	public static ICopyPolicy createCopyPolicy(IResource[] resources,
			IModelElement[] modelElements) throws ModelException {
		return (ICopyPolicy) createReorgPolicy(true, resources, modelElements);
	}

	public static IMovePolicy createMovePolicy(IResource[] resources,
			IModelElement[] modelElements) throws ModelException {
		return (IMovePolicy) createReorgPolicy(false, resources, modelElements);
	}

	private static IReorgPolicy createReorgPolicy(boolean copy,
			IResource[] selectedResources,
			IModelElement[] selectedScriptElements) throws ModelException {
		final IReorgPolicy NO;
		if (copy)
			NO = new NoCopyPolicy();
		else
			NO = new NoMovePolicy();

		ActualSelectionComputer selectionComputer = new ActualSelectionComputer(
				selectedScriptElements, selectedResources);
		IResource[] resources = selectionComputer.getActualResourcesToReorg();
		IModelElement[] modelElements = selectionComputer
				.getActualScriptElementsToReorg();

		if (false) {
			System.out.println("createReorgPolicy():"); //$NON-NLS-1$ 
			System.out.println(" resources: " + Arrays.asList(resources)); //$NON-NLS-1$ 
			System.out.println(" elements: " + Arrays.asList(modelElements)); //$NON-NLS-1$
		}

		if (isNothingToReorg(resources, modelElements)
				|| containsNull(resources)
				|| containsNull(modelElements)
				|| ReorgUtils.hasElementsOfType(modelElements,
						IModelElement.SCRIPT_PROJECT)
				|| ReorgUtils.hasElementsOfType(modelElements,
						IModelElement.SCRIPT_MODEL)
				|| ReorgUtils.hasElementsOfType(resources, IResource.PROJECT
						| IResource.ROOT)
				|| !haveCommonParent(resources, modelElements))
			return NO;

		if (ReorgUtils.hasElementsOfType(modelElements,
				IModelElement.SCRIPT_FOLDER)) {
			if (resources.length != 0
					|| ReorgUtils.hasElementsNotOfType(modelElements,
							IModelElement.SCRIPT_FOLDER))
				return NO;
			if (copy)
				return new CopyPackagesPolicy(
						ArrayTypeConverter.toPackageArray(modelElements));
			else
				return new MovePackagesPolicy(
						ArrayTypeConverter.toPackageArray(modelElements));
		}

		if (ReorgUtils.hasElementsOfType(modelElements,
				IModelElement.PROJECT_FRAGMENT)) {
			if (resources.length != 0
					|| ReorgUtils.hasElementsNotOfType(modelElements,
							IModelElement.PROJECT_FRAGMENT))
				return NO;
			if (copy)
				return new CopyProjectFragmentsPolicy(
						ArrayTypeConverter
								.toProjectFragmentArray(modelElements));
			else
				return new MoveProjectFragmentsPolicy(
						ArrayTypeConverter
								.toProjectFragmentArray(modelElements));
		}

		if (ReorgUtils.hasElementsOfType(resources, IResource.FILE
				| IResource.FOLDER)
				|| ReorgUtils.hasElementsOfType(modelElements,
						IModelElement.SOURCE_MODULE)) {
			if (ReorgUtils.hasElementsNotOfType(modelElements,
					IModelElement.SOURCE_MODULE))
				return NO;
			if (ReorgUtils.hasElementsNotOfType(resources, IResource.FILE
					| IResource.FOLDER))
				return NO;
			if (copy)
				return new CopyFilesFoldersAndCusPolicy(
						ReorgUtils.getFiles(resources),
						ReorgUtils.getFolders(resources),
						ArrayTypeConverter.toCuArray(modelElements));
			else
				return new MoveFilesFoldersAndCusPolicy(
						ReorgUtils.getFiles(resources),
						ReorgUtils.getFolders(resources),
						ArrayTypeConverter.toCuArray(modelElements));
		}

		if (hasElementsSmallerThanCuOrClassFile(modelElements)) {
			// assertions guaranteed by common parent
			Assert.isTrue(resources.length == 0);
			Assert.isTrue(!ReorgUtils.hasElementsOfType(modelElements,
					IModelElement.SOURCE_MODULE));
			Assert.isTrue(!hasElementsLargerThanCuOrClassFile(modelElements));
			if (copy)
				return new CopySubCuElementsPolicy(modelElements);
			else {
				if (DLTKCore.DEBUG) {
					System.err
							.println("TODO: ReorgPolicyFactory: Add MoveSubCuElementsPolicy support"); //$NON-NLS-1$ 
				}
				// return new MoveSubCuElementsPolicy(modelElements);
			}
		}
		return NO;
	}

	private static boolean containsNull(Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] == null)
				return true;
		}
		return false;
	}

	private static boolean hasElementsSmallerThanCuOrClassFile(
			IModelElement[] modelElements) {
		for (int i = 0; i < modelElements.length; i++) {
			if (ReorgUtils.isInsideSourceModule(modelElements[i]))
				return true;
			// if (ReorgUtils.isInsideClassFile(modelElements[i]))
			// return true;
		}
		return false;
	}

	private static boolean hasElementsLargerThanCuOrClassFile(
			IModelElement[] modelElements) {
		for (int i = 0; i < modelElements.length; i++) {
			if (!ReorgUtils.isInsideSourceModule(modelElements[i])
			// && ! ReorgUtils.isInsideClassFile(modelElements[i])
			)
				return true;
		}
		return false;
	}

	private static boolean haveCommonParent(IResource[] resources,
			IModelElement[] modelElements) {
		return new ParentChecker(resources, modelElements).haveCommonParent();
	}

	private static boolean isNothingToReorg(IResource[] resources,
			IModelElement[] modelElements) {
		return resources.length + modelElements.length == 0;
	}

	private static abstract class ReorgPolicy implements IReorgPolicy {
		// invariant: only 1 of these can ever be not null
		private IResource fResourceDestination;
		private IModelElement fScriptElementDestination;

		public final RefactoringStatus setDestination(IResource destination)
				throws ModelException {
			Assert.isNotNull(destination);
			resetDestinations();
			fResourceDestination = destination;
			return verifyDestination(destination);
		}

		public final RefactoringStatus setDestination(IModelElement destination)
				throws ModelException {
			Assert.isNotNull(destination);
			resetDestinations();
			fScriptElementDestination = destination;
			return verifyDestination(destination);
		}

		protected abstract RefactoringStatus verifyDestination(
				IModelElement destination) throws ModelException;

		protected abstract RefactoringStatus verifyDestination(
				IResource destination) throws ModelException;

		public boolean canChildrenBeDestinations(IModelElement modelElement) {
			return true;
		}

		public boolean canChildrenBeDestinations(IResource resource) {
			return true;
		}

		public boolean canElementBeDestination(IModelElement modelElement) {
			return true;
		}

		public boolean canElementBeDestination(IResource resource) {
			return true;
		}

		private void resetDestinations() {
			fScriptElementDestination = null;
			fResourceDestination = null;
		}

		public final IResource getResourceDestination() {
			return fResourceDestination;
		}

		public final IModelElement getScriptElementDestination() {
			return fScriptElementDestination;
		}

		public IFile[] getAllModifiedFiles() {
			return new IFile[0];
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			return null;
		}

		public final RefactoringParticipant[] loadParticipants(
				RefactoringStatus status, RefactoringProcessor processor,
				String[] natures, SharableParticipants shared)
				throws CoreException {
			RefactoringModifications modifications = getModifications();
			if (modifications != null) {
				return modifications.loadParticipants(status, processor,
						natures, shared);
			} else {
				return new RefactoringParticipant[0];
			}
		}

		public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
				CheckConditionsContext context, IReorgQueries reorgQueries)
				throws CoreException {
			Assert.isNotNull(reorgQueries);
			ResourceChangeChecker checker = (ResourceChangeChecker) context
					.getChecker(ResourceChangeChecker.class);
			IFile[] allModifiedFiles = getAllModifiedFiles();
			RefactoringModifications modifications = getModifications();
			IResourceChangeDescriptionFactory deltaFactory = checker
					.getDeltaFactory();
			for (int i = 0; i < allModifiedFiles.length; i++) {
				deltaFactory.change(allModifiedFiles[i]);
			}
			if (modifications != null) {
				modifications.buildDelta(deltaFactory);
				modifications.buildValidateEdits((ValidateEditChecker) context
						.getChecker(ValidateEditChecker.class));
			}
			return new RefactoringStatus();
		}

		public boolean hasAllInputSet() {
			return fScriptElementDestination != null
					|| fResourceDestination != null;
		}

		public boolean canUpdateReferences() {
			return false;
		}

		public boolean getUpdateReferences() {
			Assert.isTrue(false);// should not be called if
			// canUpdateReferences is not overridden and
			// returns false
			return false;
		}

		public void setUpdateReferences(boolean update) {
			Assert.isTrue(false);// should not be called if
			// canUpdateReferences is not overridden and
			// returns false
		}

		public boolean canEnableQualifiedNameUpdating() {
			return false;
		}

		public boolean canUpdateQualifiedNames() {
			Assert.isTrue(false);// should not be called if
			// canEnableQualifiedNameUpdating is not
			// overridden and returns false
			return false;
		}

		public String getFilePatterns() {
			Assert.isTrue(false);// should not be called if
			// canEnableQualifiedNameUpdating is not
			// overridden and returns false
			return null;
		}

		public boolean getUpdateQualifiedNames() {
			Assert.isTrue(false);// should not be called if
			// canEnableQualifiedNameUpdating is not
			// overridden and returns false
			return false;
		}

		public void setFilePatterns(String patterns) {
			Assert.isTrue(false);// should not be called if
			// canEnableQualifiedNameUpdating is not
			// overridden and returns false
		}

		public void setUpdateQualifiedNames(boolean update) {
			Assert.isTrue(false);// should not be called if
			// canEnableQualifiedNameUpdating is not
			// overridden and returns false
		}

		public boolean canEnable() throws ModelException {
			IResource[] resources = getResources();
			for (int i = 0; i < resources.length; i++) {
				IResource resource = resources[i];
				if (!resource.exists() || resource.isPhantom()
						|| !resource.isAccessible())
					return false;
			}

			IModelElement[] modelElements = getScriptElements();
			for (int i = 0; i < modelElements.length; i++) {
				IModelElement element = modelElements[i];
				if (!element.exists())
					return false;
			}
			return true;
		}
	}

	private static abstract class FilesFoldersAndCusReorgPolicy extends
			ReorgPolicy {

		private ISourceModule[] fCus;
		private IFolder[] fFolders;
		private IFile[] fFiles;

		public FilesFoldersAndCusReorgPolicy(IFile[] files, IFolder[] folders,
				ISourceModule[] cus) {
			fFiles = files;
			fFolders = folders;
			fCus = cus;
		}

		protected RefactoringStatus verifyDestination(IModelElement modelElement)
				throws ModelException {
			Assert.isNotNull(modelElement);
			if (!modelElement.exists())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_doesnotexist0);
			if (modelElement instanceof IScriptModel)
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_jmodel);

			if (modelElement.isReadOnly())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_readonly);

			if (!modelElement.isStructureKnown())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_structure);

			if (modelElement instanceof IOpenable) {
				IOpenable openable = (IOpenable) modelElement;
				if (!openable.isConsistent())
					return RefactoringStatus
							.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_inconsistent);
			}

			if (modelElement instanceof IProjectFragment) {
				IProjectFragment root = (IProjectFragment) modelElement;
				if (root.isArchive())
					return RefactoringStatus
							.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_archive);
				if (root.isExternal())
					return RefactoringStatus
							.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_external);
			}

			if (ReorgUtils.isInsideSourceModule(modelElement)) {
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_cannot);
			}

			IContainer destinationAsContainer = getDestinationAsContainer();
			if (destinationAsContainer == null
					|| isChildOfOrEqualToAnyFolder(destinationAsContainer))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_not_this_resource);

			if (containsLinkedResources()
					&& !ReorgUtils
							.canBeDestinationForLinkedResources(modelElement))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_linked);
			return new RefactoringStatus();
		}

		protected RefactoringStatus verifyDestination(IResource resource)
				throws ModelException {
			Assert.isNotNull(resource);
			if (!resource.exists() || resource.isPhantom())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_phantom);
			if (!resource.isAccessible())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_inaccessible);
			Assert.isTrue(resource.getType() != IResource.ROOT);

			if (isChildOfOrEqualToAnyFolder(resource))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_not_this_resource);

			if (containsLinkedResources()
					&& !ReorgUtils.canBeDestinationForLinkedResources(resource))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_linked);

			return new RefactoringStatus();
		}

		private boolean isChildOfOrEqualToAnyFolder(IResource resource) {
			for (int i = 0; i < fFolders.length; i++) {
				IFolder folder = fFolders[i];
				if (folder.equals(resource)
						|| ParentChecker.isDescendantOf(resource, folder))
					return true;
			}
			return false;
		}

		public boolean canChildrenBeDestinations(IModelElement modelElement) {
			switch (modelElement.getElementType()) {
			case IModelElement.SCRIPT_MODEL:
			case IModelElement.SCRIPT_PROJECT:
			case IModelElement.PROJECT_FRAGMENT:
				return !modelElement.isReadOnly();
			default:
				return false;
			}
		}

		public boolean canChildrenBeDestinations(IResource resource) {
			return resource instanceof IContainer;
		}

		public boolean canElementBeDestination(IModelElement modelElement) {
			switch (modelElement.getElementType()) {
			case IModelElement.SCRIPT_FOLDER:
				return !modelElement.isReadOnly();
			default:
				return false;
			}
		}

		public boolean canElementBeDestination(IResource resource) {
			return resource instanceof IContainer;
		}

		private static IContainer getAsContainer(IResource resDest) {
			if (resDest instanceof IContainer)
				return (IContainer) resDest;
			if (resDest instanceof IFile)
				return ((IFile) resDest).getParent();
			return null;
		}

		protected final IContainer getDestinationAsContainer() {
			IResource resDest = getResourceDestination();
			if (resDest != null)
				return getAsContainer(resDest);
			IModelElement jelDest = getScriptElementDestination();
			Assert.isNotNull(jelDest);
			return getAsContainer(ReorgUtils.getResource(jelDest));
		}

		protected final IModelElement getDestinationContainerAsScriptElement() {
			if (getScriptElementDestination() != null)
				return getScriptElementDestination();
			IContainer destinationAsContainer = getDestinationAsContainer();
			if (destinationAsContainer == null)
				return null;
			IModelElement je = DLTKCore.create(destinationAsContainer);
			if (je != null && je.exists())
				return je;
			return null;
		}

		protected final IScriptFolder getDestinationAsScriptFolder() {
			IScriptFolder javaAsPackage = getScriptDestinationAsScriptFolder(getScriptElementDestination());
			if (javaAsPackage != null)
				return javaAsPackage;
			return getResourceDestinationAsScriptFolder(getResourceDestination());
		}

		private static IScriptFolder getScriptDestinationAsScriptFolder(
				IModelElement scriptDest) {
			if (scriptDest == null || !scriptDest.exists())
				return null;
			if (scriptDest instanceof IScriptFolder)
				return (IScriptFolder) scriptDest;
			if (scriptDest instanceof IProjectFragment)
				return ((IProjectFragment) scriptDest).getScriptFolder(""); //$NON-NLS-1$ 
			if (scriptDest instanceof IScriptProject) {
				try {
					IProjectFragment root = ReorgUtils
							.getCorrespondingProjectFragment((IScriptProject) scriptDest);
					if (root != null)
						return root.getScriptFolder(""); //$NON-NLS-1$ 
				} catch (ModelException e) {
					// fall through
				}
			}
			return (IScriptFolder) scriptDest
					.getAncestor(IModelElement.SCRIPT_FOLDER);
		}

		private static IScriptFolder getResourceDestinationAsScriptFolder(
				IResource resource) {
			if (resource instanceof IFile)
				return getScriptDestinationAsScriptFolder(DLTKCore
						.create(resource.getParent()));
			return null;
		}

		public final IModelElement[] getScriptElements() {
			return fCus;
		}

		public final IResource[] getResources() {
			return ReorgUtils.union(fFiles, fFolders);
		}

		protected boolean containsLinkedResources() {
			return ReorgUtils.containsLinkedResources(fFiles)
					|| ReorgUtils.containsLinkedResources(fFolders)
					|| ReorgUtils.containsLinkedResources(fCus);
		}

		protected final IFolder[] getFolders() {
			return fFolders;
		}

		protected final IFile[] getFiles() {
			return fFiles;
		}

		protected final ISourceModule[] getCus() {
			return fCus;
		}

		public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
				CheckConditionsContext context, IReorgQueries reorgQueries)
				throws CoreException {
			RefactoringStatus status = super.checkFinalConditions(pm, context,
					reorgQueries);
			confirmOverwritting(reorgQueries);
			return status;
		}

		private void confirmOverwritting(IReorgQueries reorgQueries) {
			OverwriteHelper oh = new OverwriteHelper();
			oh.setFiles(fFiles);
			oh.setFolders(fFolders);
			oh.setCus(fCus);
			IScriptFolder destPack = getDestinationAsScriptFolder();
			if (destPack != null) {
				oh.confirmOverwritting(reorgQueries, destPack);
			} else {
				IContainer destinationAsContainer = getDestinationAsContainer();
				if (destinationAsContainer != null)
					oh.confirmOverwritting(reorgQueries, destinationAsContainer);
			}
			fFiles = oh.getFilesWithoutUnconfirmedOnes();
			fFolders = oh.getFoldersWithoutUnconfirmedOnes();
			fCus = oh.getCusWithoutUnconfirmedOnes();
		}
	}

	private static abstract class SubCuElementReorgPolicy extends ReorgPolicy {
		private final IModelElement[] fScriptElements;

		SubCuElementReorgPolicy(IModelElement[] modelElements) {
			Assert.isNotNull(modelElements);
			fScriptElements = modelElements;
		}

		protected final RefactoringStatus verifyDestination(
				IResource destination) throws ModelException {
			return RefactoringStatus
					.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_no_resource);
		}

		protected final ISourceModule getSourceCu() {
			// all have a common parent, so all must be in the same cu
			// we checked before that the array in not null and not empty
			return (ISourceModule) fScriptElements[0]
					.getAncestor(IModelElement.SOURCE_MODULE);
		}

		public final IModelElement[] getScriptElements() {
			return fScriptElements;
		}

		public final IResource[] getResources() {
			return new IResource[0];
		}

		protected final ISourceModule getDestinationCu() {
			return getDestinationCu(getScriptElementDestination());
		}

		protected static final ISourceModule getDestinationCu(
				IModelElement destination) {
			if (destination instanceof ISourceModule)
				return (ISourceModule) destination;
			return (ISourceModule) destination
					.getAncestor(IModelElement.SOURCE_MODULE);
		}

		// private static ISourceModule getEnclosingCu(IModelElement
		// destination) {
		// if (destination instanceof ISourceModule)
		// return (ISourceModule) destination;
		// return
		// (ISourceModule)destination.getAncestor(IModelElement.SOURCE_MODULE);
		// }
		//
		// private static IType getEnclosingType(IModelElement destination) {
		// if (destination instanceof IType)
		// return (IType) destination;
		// return (IType)destination.getAncestor(IModelElement.TYPE);
		// }

		public boolean canEnable() throws ModelException {
			if (!super.canEnable())
				return false;
			for (int i = 0; i < fScriptElements.length; i++) {
				if (fScriptElements[i] instanceof IMember) {
					IMember member = (IMember) fScriptElements[i];
					// we can copy some binary members, but not all
					if (member.getSourceRange() == null)
						return false;
				}
			}
			return true;
		}

		protected RefactoringStatus verifyDestination(IModelElement destination)
				throws ModelException {
			return recursiveVerifyDestination(destination);
		}

		private RefactoringStatus recursiveVerifyDestination(
				IModelElement destination) throws ModelException {
			Assert.isNotNull(destination);
			if (!destination.exists())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_doesnotexist1);
			if (destination instanceof IScriptModel)
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_jmodel);
			if (!(destination instanceof ISourceModule)
					&& !ReorgUtils.isInsideSourceModule(destination))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_cannot);

			ISourceModule destinationCu = getDestinationCu(destination);
			Assert.isNotNull(destinationCu);
			if (destinationCu.isReadOnly())// the resource read-onliness is
				// handled by validateEdit
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_cannot_modify);

			switch (destination.getElementType()) {
			case IModelElement.SOURCE_MODULE:
				int[] types0 = new int[] { IModelElement.FIELD,
						IModelElement.METHOD };
				if (ReorgUtils.hasElementsOfType(getScriptElements(), types0))
					return RefactoringStatus
							.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_cannot);
				break;
			// case IModelElement.PACKAGE_DECLARATION:
			// return
			// RefactoringStatus.createFatalErrorStatus(RefactoringCoreMessages.
			// ReorgPolicyFactory_package_decl);

			// case IModelElement.IMPORT_CONTAINER:
			// if (ReorgUtils.hasElementsNotOfType(getScriptElements(),
			// IModelElement.IMPORT_DECLARATION))
			// return
			// RefactoringStatus.createFatalErrorStatus(RefactoringCoreMessages.
			// ReorgPolicyFactory_cannot);
			// break;
			//
			// case IModelElement.IMPORT_DECLARATION:
			// if (ReorgUtils.hasElementsNotOfType(getScriptElements(),
			// IModelElement.IMPORT_DECLARATION))
			// return
			// RefactoringStatus.createFatalErrorStatus(RefactoringCoreMessages.
			// ReorgPolicyFactory_cannot);
			// break;

			case IModelElement.FIELD:// fall thru
			case IModelElement.METHOD:// fall thru
				return recursiveVerifyDestination(destination.getParent());

			case IModelElement.TYPE:
				if (DLTKCore.DEBUG) {
					System.err.println("Add import support here..."); //$NON-NLS-1$
				}
				// int[] types1= new int[]{IModelElement.IMPORT_DECLARATION,
				// IModelElement.IMPORT_CONTAINER,
				// IModelElement.PACKAGE_DECLARATION};
				// if (ReorgUtils.hasElementsOfType(getScriptElements(),
				// types1))
				// return recursiveVerifyDestination(destination.getParent());
				break;
			}

			return new RefactoringStatus();
		}

		public boolean canChildrenBeDestinations(IResource resource) {
			return false;
		}

		public boolean canElementBeDestination(IResource resource) {
			return false;
		}
	}

	private static abstract class ProjectFragmentsReorgPolicy extends
			ReorgPolicy {

		private IProjectFragment[] fProjectFragments;

		public IModelElement[] getScriptElements() {
			return fProjectFragments;
		}

		public IResource[] getResources() {
			return new IResource[0];
		}

		public IProjectFragment[] getRoots() {
			return fProjectFragments;
		}

		public ProjectFragmentsReorgPolicy(IProjectFragment[] roots) {
			Assert.isNotNull(roots);
			fProjectFragments = roots;
		}

		public boolean canEnable() throws ModelException {
			if (!super.canEnable())
				return false;
			for (int i = 0; i < fProjectFragments.length; i++) {
				if (!(ReorgUtils.isSourceFolder(fProjectFragments[i]) || (fProjectFragments[i]
						.isArchive() && !fProjectFragments[i].isExternal())))
					return false;
			}
			if (ReorgUtils.containsLinkedResources(fProjectFragments))
				return false;
			return true;
		}

		public boolean canChildrenBeDestinations(IModelElement modelElement) {
			switch (modelElement.getElementType()) {
			case IModelElement.SCRIPT_MODEL:
			case IModelElement.SCRIPT_PROJECT:
				return true;
			default:
				return false;
			}
		}

		public boolean canChildrenBeDestinations(IResource resource) {
			return false;
		}

		public boolean canElementBeDestination(IModelElement modelElement) {
			return modelElement.getElementType() == IModelElement.SCRIPT_PROJECT;
		}

		public boolean canElementBeDestination(IResource resource) {
			return false;
		}

		protected RefactoringStatus verifyDestination(IResource resource) {
			return RefactoringStatus
					.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_src2proj);
		}

		protected RefactoringStatus verifyDestination(IModelElement modelElement)
				throws ModelException {
			Assert.isNotNull(modelElement);
			if (!modelElement.exists())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_cannot1);
			if (modelElement instanceof IScriptModel)
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_jmodel);
			if (!(modelElement instanceof IScriptProject
					|| modelElement instanceof IProjectFragment || modelElement instanceof IScriptFolder))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_src2proj);
			if (modelElement.isReadOnly())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_src2writable);
			// if
			// (ReorgUtils.isProjectFragment(modelElement.getScriptProject()))
			// // TODO: adapt message to archives:
			// return RefactoringStatus
			// .createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_src2nosrc);
			return new RefactoringStatus();
		}

		protected IScriptProject getDestinationScriptProject() {
			return getDestinationAsScriptProject(getScriptElementDestination());
		}

		private IScriptProject getDestinationAsScriptProject(
				IModelElement modelElementDestination) {
			if (modelElementDestination == null)
				return null;
			else
				return modelElementDestination.getScriptProject();
		}

		protected IProjectFragment[] getProjectFragments() {
			return fProjectFragments;
		}

		public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
				CheckConditionsContext context, IReorgQueries reorgQueries)
				throws CoreException {
			RefactoringStatus status = super.checkFinalConditions(pm, context,
					reorgQueries);
			confirmOverwritting(reorgQueries);
			return status;
		}

		private void confirmOverwritting(IReorgQueries reorgQueries) {
			OverwriteHelper oh = new OverwriteHelper();
			oh.setProjectFragments(fProjectFragments);
			IScriptProject scriptProject = getDestinationScriptProject();
			oh.confirmOverwritting(reorgQueries, scriptProject);
			fProjectFragments = oh.getProjectFragmentsWithoutUnconfirmedOnes();
		}
	}

	private static abstract class PackagesReorgPolicy extends ReorgPolicy {
		private IScriptFolder[] fScriptFolders;

		public IModelElement[] getScriptElements() {
			return fScriptFolders;
		}

		public IResource[] getResources() {
			return new IResource[0];
		}

		protected IScriptFolder[] getPackages() {
			return fScriptFolders;
		}

		public PackagesReorgPolicy(IScriptFolder[] ScriptFolders) {
			Assert.isNotNull(ScriptFolders);
			fScriptFolders = ScriptFolders;
		}

		public boolean canEnable() throws ModelException {
			for (int i = 0; i < fScriptFolders.length; i++) {
				if (ModelElementUtil.isDefaultPackage(fScriptFolders[i])
						|| fScriptFolders[i].isReadOnly())
					return false;
			}
			if (ReorgUtils.containsLinkedResources(fScriptFolders))
				return false;
			return true;
		}

		protected RefactoringStatus verifyDestination(IResource resource) {
			return RefactoringStatus
					.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_packages);
		}

		protected IProjectFragment getDestinationAsProjectFragment()
				throws ModelException {
			return getDestinationAsProjectFragment(getScriptElementDestination());
		}

		public boolean canChildrenBeDestinations(IModelElement modelElement) {
			switch (modelElement.getElementType()) {
			case IModelElement.SCRIPT_MODEL:
			case IModelElement.SCRIPT_PROJECT:
			case IModelElement.PROJECT_FRAGMENT: // can be nested (with
				// exclusion filters)
				return true;
			default:
				return false;
			}
		}

		public boolean canChildrenBeDestinations(IResource resource) {
			return false;
		}

		public boolean canElementBeDestination(IModelElement modelElement) {
			switch (modelElement.getElementType()) {
			case IModelElement.SCRIPT_PROJECT:
			case IModelElement.PROJECT_FRAGMENT:
				return true;
			default:
				return false;
			}
		}

		public boolean canElementBeDestination(IResource resource) {
			return false;
		}

		private IProjectFragment getDestinationAsProjectFragment(
				IModelElement modelElement) throws ModelException {
			if (modelElement == null)
				return null;

			if (modelElement instanceof IProjectFragment)
				return (IProjectFragment) modelElement;

			if (modelElement instanceof IScriptFolder) {
				IScriptFolder pack = (IScriptFolder) modelElement;
				if (pack.getParent() instanceof IProjectFragment)
					return (IProjectFragment) pack.getParent();
			}

			if (modelElement instanceof IScriptProject)
				return ReorgUtils
						.getCorrespondingProjectFragment((IScriptProject) modelElement);
			return null;
		}

		protected RefactoringStatus verifyDestination(IModelElement modelElement)
				throws ModelException {
			Assert.isNotNull(modelElement);
			if (!modelElement.exists())
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_cannot1);
			if (modelElement instanceof IScriptModel)
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_jmodel);
			IProjectFragment destRoot = getDestinationAsProjectFragment(modelElement);
			if (!ReorgUtils.isSourceFolder(destRoot))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_packages);
			return new RefactoringStatus();
		}

		public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
				CheckConditionsContext context, IReorgQueries reorgQueries)
				throws CoreException {
			RefactoringStatus refactoringStatus = super.checkFinalConditions(
					pm, context, reorgQueries);
			confirmOverwritting(reorgQueries);
			return refactoringStatus;
		}

		private void confirmOverwritting(IReorgQueries reorgQueries)
				throws ModelException {
			OverwriteHelper oh = new OverwriteHelper();
			oh.setPackages(fScriptFolders);
			IProjectFragment destRoot = getDestinationAsProjectFragment();
			oh.confirmOverwritting(reorgQueries, destRoot);
			fScriptFolders = oh.getPackagesWithoutUnconfirmedOnes();
		}
	}

	private static class CopySubCuElementsPolicy extends
			SubCuElementReorgPolicy implements ICopyPolicy {
		private CopyModifications fModifications;
		private ReorgExecutionLog fReorgExecutionLog;

		CopySubCuElementsPolicy(IModelElement[] modelElements) {
			super(modelElements);
		}

		public ReorgExecutionLog getReorgExecutionLog() {
			return fReorgExecutionLog;
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			if (fModifications != null)
				return fModifications;

			fModifications = new CopyModifications();
			fReorgExecutionLog = new ReorgExecutionLog();
			CopyArguments args = new CopyArguments(
					getScriptElementDestination(), fReorgExecutionLog);
			IModelElement[] modelElements = getScriptElements();
			for (int i = 0; i < modelElements.length; i++) {
				fModifications.copy(modelElements[i], args, null);
			}
			return fModifications;
		}

		public Change createChange(IProgressMonitor pm,
				INewNameQueries copyQueries) throws ModelException {
			if (DLTKCore.DEBUG) {
				System.err
						.println("ReorgPolicyFactory:createChange return null."); //$NON-NLS-1$
			}
			return null;
			// try {
			// SourceModule sourceCuNode= createSourceCuNode();
			// ISourceModule targetCu= getDestinationCu();
			// SourceModuleRewrite targetRewriter= new
			// SourceModuleRewrite(targetCu);
			// IModelElement[] modelElements= getScriptElements();
			// for (int i= 0; i < modelElements.length; i++) {
			// copyToDestination(modelElements[i], targetRewriter, sourceCuNode,
			// targetRewriter.getRoot());
			// }
			// return createSourceModuleChange(targetCu, targetRewriter);
			// } catch (ModelException e) {
			// throw e;
			// } catch (CoreException e) {
			// throw new ModelException(e);
			// }
		}

		// private SourceModule createSourceCuNode(){
		// Assert.isTrue(getSourceCu() != null || getSourceClassFile() != null);
		// Assert.isTrue(getSourceCu() == null || getSourceClassFile() == null);
		// ASTParser parser= ASTParser.newParser(AST.JLS3);
		// if (getSourceCu() != null)
		// parser.setSource(getSourceCu());
		// else
		// parser.setSource(getSourceClassFile());
		// return (SourceModule) parser.createAST(null);
		// }

		public boolean canEnable() throws ModelException {
			return super.canEnable() && (getSourceCu() != null);
		}

		public IFile[] getAllModifiedFiles() {
			return ReorgUtils.getFiles(new IResource[] { ReorgUtils
					.getResource(getDestinationCu()) });
		}
	}

	private static class CopyFilesFoldersAndCusPolicy extends
			FilesFoldersAndCusReorgPolicy implements ICopyPolicy {
		private CopyModifications fModifications;
		private ReorgExecutionLog fReorgExecutionLog;

		CopyFilesFoldersAndCusPolicy(IFile[] files, IFolder[] folders,
				ISourceModule[] cus) {
			super(files, folders, cus);
		}

		public ReorgExecutionLog getReorgExecutionLog() {
			return fReorgExecutionLog;
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			if (fModifications != null)
				return fModifications;
			fModifications = new CopyModifications();
			fReorgExecutionLog = new ReorgExecutionLog();
			CopyArguments jArgs = new CopyArguments(getDestination(),
					fReorgExecutionLog);
			CopyArguments rArgs = new CopyArguments(
					getDestinationAsContainer(), fReorgExecutionLog);
			ISourceModule[] cus = getCus();
			for (int i = 0; i < cus.length; i++) {
				fModifications.copy(cus[i], jArgs, rArgs);
			}
			IResource[] resources = ReorgUtils.union(getFiles(), getFolders());
			for (int i = 0; i < resources.length; i++) {
				fModifications.copy(resources[i], rArgs);
			}
			return fModifications;
		}

		private Object getDestination() {
			Object result = getDestinationAsScriptFolder();
			if (result != null)
				return result;
			return getDestinationAsContainer();
		}

		public Change createChange(IProgressMonitor pm,
				INewNameQueries copyQueries) {
			IFile[] file = getFiles();
			IFolder[] folders = getFolders();
			ISourceModule[] cus = getCus();
			pm.beginTask("", cus.length + file.length + folders.length); //$NON-NLS-1$
			NewNameProposer nameProposer = new NewNameProposer();
			CompositeChange composite = new DynamicValidationStateChange(
					RefactoringCoreMessages.ReorgPolicy_copy);
			composite.markAsSynthetic();
			for (int i = 0; i < cus.length; i++) {
				composite.add(createChange(cus[i], nameProposer, copyQueries));
				pm.worked(1);
			}
			if (pm.isCanceled())
				throw new OperationCanceledException();
			for (int i = 0; i < file.length; i++) {
				composite.add(createChange(file[i], nameProposer, copyQueries));
				pm.worked(1);
			}
			if (pm.isCanceled())
				throw new OperationCanceledException();
			for (int i = 0; i < folders.length; i++) {
				composite.add(createChange(folders[i], nameProposer,
						copyQueries));
				pm.worked(1);
			}
			pm.done();
			return composite;
		}

		private Change createChange(ISourceModule unit,
				NewNameProposer nameProposer, INewNameQueries copyQueries) {
			IScriptFolder pack = getDestinationAsScriptFolder();
			if (pack != null)
				return copyCuToPackage(unit, pack, nameProposer, copyQueries);
			IContainer container = getDestinationAsContainer();
			return copyFileToContainer(unit, container, nameProposer,
					copyQueries);
		}

		private static Change copyFileToContainer(ISourceModule cu,
				IContainer dest, NewNameProposer nameProposer,
				INewNameQueries copyQueries) {
			IResource resource = ReorgUtils.getResource(cu);
			return createCopyResourceChange(resource, nameProposer,
					copyQueries, dest);
		}

		private Change createChange(IResource resource,
				NewNameProposer nameProposer, INewNameQueries copyQueries) {
			IContainer dest = getDestinationAsContainer();
			return createCopyResourceChange(resource, nameProposer,
					copyQueries, dest);
		}

		private static Change createCopyResourceChange(IResource resource,
				NewNameProposer nameProposer, INewNameQueries copyQueries,
				IContainer destination) {
			if (resource == null || destination == null)
				return new NullChange();
			INewNameQuery nameQuery;
			String name = nameProposer.createNewName(resource, destination);
			if (name == null)
				nameQuery = copyQueries.createNullQuery();
			else
				nameQuery = copyQueries.createNewResourceNameQuery(resource,
						name);
			return new CopyResourceChange(resource, destination, nameQuery);
		}

		private static Change copyCuToPackage(ISourceModule cu,
				IScriptFolder dest, NewNameProposer nameProposer,
				INewNameQueries copyQueries) {
			// XXX workaround for bug 31998 we will have to disable renaming of
			// linked packages (and cus)
			IResource res = ReorgUtils.getResource(cu);
			if (res != null && res.isLinked()) {
				if (ResourceUtil.getResource(dest) instanceof IContainer)
					return copyFileToContainer(cu,
							(IContainer) ResourceUtil.getResource(dest),
							nameProposer, copyQueries);
			}

			String newName = nameProposer.createNewName(cu, dest);
			Change simpleCopy = new CopySourceModuleChange(cu, dest,
					copyQueries.createStaticQuery(newName));
			if (newName == null || newName.equals(cu.getElementName()))
				return simpleCopy;

			try {
				IPath newPath = ResourceUtil.getResource(cu).getParent()
						.getFullPath().append(newName);
				INewNameQuery nameQuery = copyQueries
						.createNewSourceModuleNameQuery(cu, newName);
				return new CreateCopyOfSourceModuleChange(newPath,
						cu.getSource(), cu, nameQuery);
			} catch (CoreException e) {
				return simpleCopy; // fallback - no ui here
			}
		}
	}

	private static class CopyProjectFragmentsPolicy extends
			ProjectFragmentsReorgPolicy implements ICopyPolicy {
		private CopyModifications fModifications;
		private ReorgExecutionLog fReorgExecutionLog;

		public CopyProjectFragmentsPolicy(IProjectFragment[] roots) {
			super(roots);
		}

		public ReorgExecutionLog getReorgExecutionLog() {
			return fReorgExecutionLog;
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			if (fModifications != null)
				return fModifications;

			fModifications = new CopyModifications();
			fReorgExecutionLog = new ReorgExecutionLog();
			CopyArguments javaArgs = new CopyArguments(
					getDestinationScriptProject(), fReorgExecutionLog);
			CopyArguments resourceArgs = new CopyArguments(
					getDestinationScriptProject().getProject(),
					fReorgExecutionLog);
			IProjectFragment[] roots = getRoots();
			for (int i = 0; i < roots.length; i++) {
				fModifications.copy(roots[i], javaArgs, resourceArgs);
			}
			return fModifications;
		}

		public Change createChange(IProgressMonitor pm,
				INewNameQueries copyQueries) {
			NewNameProposer nameProposer = new NewNameProposer();
			IProjectFragment[] roots = getProjectFragments();
			pm.beginTask("", roots.length); //$NON-NLS-1$
			CompositeChange composite = new DynamicValidationStateChange(
					RefactoringCoreMessages.ReorgPolicy_copy_source_folder);
			composite.markAsSynthetic();
			IScriptProject destination = getDestinationScriptProject();
			Assert.isNotNull(destination);
			for (int i = 0; i < roots.length; i++) {
				composite.add(createChange(roots[i], destination, nameProposer,
						copyQueries));
				pm.worked(1);
			}
			pm.done();
			return composite;
		}

		private Change createChange(IProjectFragment root,
				IScriptProject destination, NewNameProposer nameProposer,
				INewNameQueries copyQueries) {
			IResource res = root.getResource();
			IProject destinationProject = destination.getProject();
			String newName = nameProposer
					.createNewName(res, destinationProject);
			INewNameQuery nameQuery;
			if (newName == null)
				nameQuery = copyQueries.createNullQuery();
			else
				nameQuery = copyQueries.createNewProjectFragmentNameQuery(root,
						newName);
			// TODO sounds wrong that this change works on IProjects
			// TODO fix the query problem
			return new CopyProjectFragmentChange(root, destinationProject,
					nameQuery, null);
		}
	}

	private static class CopyPackagesPolicy extends PackagesReorgPolicy
			implements ICopyPolicy {
		private CopyModifications fModifications;
		private ReorgExecutionLog fReorgExecutionLog;

		public CopyPackagesPolicy(IScriptFolder[] ScriptFolders) {
			super(ScriptFolders);
		}

		public ReorgExecutionLog getReorgExecutionLog() {
			return fReorgExecutionLog;
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			if (fModifications != null)
				return fModifications;

			fModifications = new CopyModifications();
			fReorgExecutionLog = new ReorgExecutionLog();
			IProjectFragment destination = getDestinationAsProjectFragment();
			CopyArguments javaArgs = new CopyArguments(destination,
					fReorgExecutionLog);
			CopyArguments resourceArgs = new CopyArguments(
					destination.getResource(), fReorgExecutionLog);
			IScriptFolder[] packages = getPackages();
			for (int i = 0; i < packages.length; i++) {
				fModifications.copy(packages[i], javaArgs, resourceArgs);
			}
			return fModifications;
		}

		public Change createChange(IProgressMonitor pm,
				INewNameQueries newNameQueries) throws ModelException {
			NewNameProposer nameProposer = new NewNameProposer();
			IScriptFolder[] fragments = getPackages();
			pm.beginTask("", fragments.length); //$NON-NLS-1$
			CompositeChange composite = new DynamicValidationStateChange(
					RefactoringCoreMessages.ReorgPolicy_copy_package);
			composite.markAsSynthetic();
			IProjectFragment root = getDestinationAsProjectFragment();
			for (int i = 0; i < fragments.length; i++) {
				composite.add(createChange(fragments[i], root, nameProposer,
						newNameQueries));
				pm.worked(1);
			}
			pm.done();
			return composite;
		}

		private Change createChange(IScriptFolder pack,
				IProjectFragment destination, NewNameProposer nameProposer,
				INewNameQueries copyQueries) {
			String newName = nameProposer.createNewName(pack, destination);
			IDLTKLanguageToolkit tk = null;
			tk = DLTKLanguageManager.getLanguageToolkit(pack);
			IPath newPath = destination.getResource().getFullPath();
			if (newName != null) {
				newPath = newPath.append(newName);
			}
			if (newName == null
					|| (tk != null && tk.validateSourcePackage(newPath,
							EnvironmentManager.getEnvironment(destination)))) {
				INewNameQuery nameQuery;
				if (newName == null) {
					nameQuery = copyQueries.createNullQuery();
				} else {
					nameQuery = copyQueries.createNewPackageNameQuery(pack,
							newName);
				}
				return new CopyScriptFolderChange(pack, destination, nameQuery);
			} else {
				if (destination.getResource() instanceof IContainer) {
					IContainer dest = (IContainer) destination.getResource();
					IResource res = pack.getResource();
					INewNameQuery nameQuery = copyQueries
							.createNewResourceNameQuery(res, newName);
					return new CopyResourceChange(res, dest, nameQuery);
				} else {
					return new NullChange();
				}
			}
		}
	}

	private static class NoCopyPolicy extends ReorgPolicy implements
			ICopyPolicy {
		public boolean canEnable() throws ModelException {
			return false;
		}

		public ReorgExecutionLog getReorgExecutionLog() {
			return null;
		}

		protected RefactoringStatus verifyDestination(IResource resource)
				throws ModelException {
			return RefactoringStatus
					.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_noCopying);
		}

		protected RefactoringStatus verifyDestination(IModelElement modelElement)
				throws ModelException {
			return RefactoringStatus
					.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_noCopying);
		}

		public Change createChange(IProgressMonitor pm,
				INewNameQueries copyQueries) {
			return new NullChange();
		}

		public IResource[] getResources() {
			return new IResource[0];
		}

		public IModelElement[] getScriptElements() {
			return new IModelElement[0];
		}
	}

	private static class NewNameProposer {
		private final Set fAutoGeneratedNewNames = new HashSet(2);

		public String createNewName(ISourceModule cu, IScriptFolder destination) {
			if (isNewNameOk(destination, cu.getElementName()))
				return null;
			if (!ReorgUtils.isParentInWorkspaceOrOnDisk(cu, destination))
				return null;
			int i = 1;
			while (true) {
				String newName;
				if (i == 1)
					newName = Messages.format(
							RefactoringCoreMessages.CopyRefactoring_cu_copyOf1,
							cu.getElementName());
				else
					newName = Messages
							.format(RefactoringCoreMessages.CopyRefactoring_cu_copyOfMore,
									new String[] { String.valueOf(i),
											cu.getElementName() });
				if (isNewNameOk(destination, newName)
						&& !fAutoGeneratedNewNames.contains(newName)) {
					fAutoGeneratedNewNames.add(newName);
					return removeTrailingScript(newName);
				}
				i++;
			}
		}

		private static String removeTrailingScript(String name) {
			// return DLTKCore.removeScriptLikeExtension(name);
			if (DLTKCore.DEBUG) {
				System.err
						.println("TODO: ReorgPolicyFactory add removeScriptLikeExtension code..."); //$NON-NLS-1$
			}
			return name;
		}

		public String createNewName(IResource res, IContainer destination) {
			if (isNewNameOk(destination, res.getName()))
				return null;
			if (!ReorgUtils.isParentInWorkspaceOrOnDisk(res, destination))
				return null;
			int i = 1;
			while (true) {
				String newName;
				if (i == 1)
					newName = Messages
							.format(RefactoringCoreMessages.CopyRefactoring_resource_copyOf1,
									res.getName());
				else
					newName = Messages
							.format(RefactoringCoreMessages.CopyRefactoring_resource_copyOfMore,
									new String[] { String.valueOf(i),
											res.getName() });
				if (isNewNameOk(destination, newName)
						&& !fAutoGeneratedNewNames.contains(newName)) {
					fAutoGeneratedNewNames.add(newName);
					return newName;
				}
				i++;
			}
		}

		public String createNewName(IScriptFolder pack,
				IProjectFragment destination) {
			if (isNewNameOk(destination, pack.getElementName()))
				return null;
			if (!ReorgUtils.isParentInWorkspaceOrOnDisk(pack, destination))
				return null;
			int i = 1;
			while (true) {
				String newName;
				if (i == 1)
					newName = Messages
							.format(RefactoringCoreMessages.CopyRefactoring_package_copyOf1,
									pack.getElementName());
				else
					newName = Messages
							.format(RefactoringCoreMessages.CopyRefactoring_package_copyOfMore,
									new String[] { String.valueOf(i),
											pack.getElementName() });
				if (isNewNameOk(destination, newName)
						&& !fAutoGeneratedNewNames.contains(newName)) {
					fAutoGeneratedNewNames.add(newName);
					return newName;
				}
				i++;
			}
		}

		private static boolean isNewNameOk(IScriptFolder dest, String newName) {
			return !dest.getSourceModule(newName).exists();
		}

		private static boolean isNewNameOk(IContainer container, String newName) {
			return container.findMember(newName) == null;
		}

		private static boolean isNewNameOk(IProjectFragment root, String newName) {
			return !root.getScriptFolder(newName).exists();
		}
	}

	private static class MoveProjectFragmentsPolicy extends
			ProjectFragmentsReorgPolicy implements IMovePolicy {

		private MoveModifications fModifications;

		MoveProjectFragmentsPolicy(IProjectFragment[] roots) {
			super(roots);
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			if (fModifications != null)
				return fModifications;

			fModifications = new MoveModifications();
			IScriptProject destination = getDestinationScriptProject();
			boolean updateReferences = canUpdateReferences()
					&& getUpdateReferences();
			if (destination != null) {
				IProjectFragment[] roots = getProjectFragments();
				for (int i = 0; i < roots.length; i++) {
					fModifications.move(roots[i], new MoveArguments(
							destination, updateReferences));
				}
			}
			return fModifications;
		}

		public Change createChange(IProgressMonitor pm) throws ModelException {
			IProjectFragment[] roots = getProjectFragments();
			pm.beginTask("", roots.length); //$NON-NLS-1$
			CompositeChange composite = new DynamicValidationStateChange(
					RefactoringCoreMessages.ReorgPolicy_move_source_folder);
			composite.markAsSynthetic();
			IScriptProject destination = getDestinationScriptProject();
			Assert.isNotNull(destination);
			for (int i = 0; i < roots.length; i++) {
				composite.add(createChange(roots[i], destination));
				pm.worked(1);
			}
			pm.done();
			return composite;
		}

		public Change postCreateChange(Change[] participantChanges,
				IProgressMonitor pm) throws CoreException {
			return null;
		}

		private Change createChange(IProjectFragment root,
				IScriptProject destination) {
			// /XXX fix the query
			return new MoveProjectFragmentChange(root,
					destination.getProject(), null);
		}

		protected RefactoringStatus verifyDestination(IModelElement modelElement)
				throws ModelException {
			RefactoringStatus superStatus = super
					.verifyDestination(modelElement);
			if (superStatus.hasFatalError())
				return superStatus;
			IScriptProject scriptProject = getDestinationScriptProject();
			// if (isParentOfAny(scriptProject, getProjectFragments()))
			// return RefactoringStatus
			// .createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_element2parent);
			return superStatus;
		}

		private static boolean isParentOfAny(IScriptProject scriptProject,
				IProjectFragment[] roots) {
			for (int i = 0; i < roots.length; i++) {
				if (ReorgUtils.isParentInWorkspaceOrOnDisk(roots[i],
						scriptProject))
					return true;
			}
			return false;
		}

		public boolean canEnable() throws ModelException {
			if (!super.canEnable())
				return false;
			IProjectFragment[] roots = getProjectFragments();
			for (int i = 0; i < roots.length; i++) {
				if (roots[i].isReadOnly() && !(roots[i].isArchive())) {
					IResource res = roots[i].getResource();
					if (res != null) {
						final ResourceAttributes attributes = roots[i]
								.getResource().getResourceAttributes();
						if (attributes == null || attributes.isReadOnly())
							return false;
					} else {
						if (DLTKCore.DEBUG) {
							System.err
									.println("TODO: Add correct code of copy external folders in..."); //$NON-NLS-1$
						}
						return false;
					}
				}
			}
			return true;
		}

		public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
				CheckConditionsContext context, IReorgQueries reorgQueries)
				throws CoreException {
			try {
				RefactoringStatus status = super.checkFinalConditions(pm,
						context, reorgQueries);
				confirmMovingReadOnly(reorgQueries);
				return status;
			} catch (ModelException e) {
				throw e;
			} catch (CoreException e) {
				throw new ModelException(e);
			}
		}

		private void confirmMovingReadOnly(IReorgQueries reorgQueries)
				throws CoreException {
			if (!ReadOnlyResourceFinder.confirmMoveOfReadOnlyElements(
					getScriptElements(), getResources(), reorgQueries))
				throw new OperationCanceledException(); // saying' no' to this
			// one is like
			// cancelling the whole
			// operation
		}

		public ICreateTargetQuery getCreateTargetQuery(
				ICreateTargetQueries createQueries) {
			return null;
		}

		public boolean isTextualMove() {
			return false;
		}
	}

	private static class MovePackagesPolicy extends PackagesReorgPolicy
			implements IMovePolicy {
		private MoveModifications fModifications;

		MovePackagesPolicy(IScriptFolder[] ScriptFolders) {
			super(ScriptFolders);
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			if (fModifications != null)
				return fModifications;

			fModifications = new MoveModifications();
			boolean updateReferences = canUpdateReferences()
					&& getUpdateReferences();
			IScriptFolder[] packages = getPackages();
			IProjectFragment scriptDestination = getDestinationAsProjectFragment();
			for (int i = 0; i < packages.length; i++) {
				fModifications.move(packages[i], new MoveArguments(
						scriptDestination, updateReferences));
			}
			return fModifications;
		}

		protected RefactoringStatus verifyDestination(IModelElement modelElement)
				throws ModelException {
			RefactoringStatus superStatus = super
					.verifyDestination(modelElement);
			if (superStatus.hasFatalError())
				return superStatus;

			IProjectFragment root = getDestinationAsProjectFragment();
			if (isParentOfAny(root, getPackages()))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_package2parent);
			return superStatus;
		}

		private static boolean isParentOfAny(IProjectFragment root,
				IScriptFolder[] fragments) {
			for (int i = 0; i < fragments.length; i++) {
				IScriptFolder fragment = fragments[i];
				if (ReorgUtils.isParentInWorkspaceOrOnDisk(fragment, root))
					return true;
			}
			return false;
		}

		public Change createChange(IProgressMonitor pm) throws ModelException {
			IScriptFolder[] fragments = getPackages();
			pm.beginTask("", fragments.length); //$NON-NLS-1$
			CompositeChange result = new DynamicValidationStateChange(
					RefactoringCoreMessages.ReorgPolicy_move_package);
			result.markAsSynthetic();
			IProjectFragment root = getDestinationAsProjectFragment();
			for (int i = 0; i < fragments.length; i++) {
				result.add(createChange(fragments[i], root));
				pm.worked(1);
				if (pm.isCanceled())
					throw new OperationCanceledException();
			}
			pm.done();
			return result;
		}

		public Change postCreateChange(Change[] participantChanges,
				IProgressMonitor pm) throws CoreException {
			return null;
		}

		private Change createChange(IScriptFolder pack,
				IProjectFragment destination) {
			return new MoveScriptFolderChange(pack, destination);
		}

		public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
				CheckConditionsContext context, IReorgQueries reorgQueries)
				throws CoreException {
			try {
				RefactoringStatus status = super.checkFinalConditions(pm,
						context, reorgQueries);
				confirmMovingReadOnly(reorgQueries);
				return status;
			} catch (ModelException e) {
				throw e;
			} catch (CoreException e) {
				throw new ModelException(e);
			}
		}

		private void confirmMovingReadOnly(IReorgQueries reorgQueries)
				throws CoreException {
			if (!ReadOnlyResourceFinder.confirmMoveOfReadOnlyElements(
					getScriptElements(), getResources(), reorgQueries))
				throw new OperationCanceledException(); // saying' no' to this
			// one is like
			// cancelling the whole
			// operation
		}

		public ICreateTargetQuery getCreateTargetQuery(
				ICreateTargetQueries createQueries) {
			return null;
		}

		public boolean isTextualMove() {
			return false;
		}
	}

	private static class MoveFilesFoldersAndCusPolicy extends
			FilesFoldersAndCusReorgPolicy implements IMovePolicy {

		private boolean fUpdateReferences;
		private boolean fUpdateQualifiedNames;
		// private QualifiedNameSearchResult fQualifiedNameSearchResult;
		private String fFilePatterns;
		private TextChangeManager fChangeManager;
		private MoveModifications fModifications;

		MoveFilesFoldersAndCusPolicy(IFile[] files, IFolder[] folders,
				ISourceModule[] cus) {
			super(files, folders, cus);
			fUpdateReferences = true;
			fUpdateQualifiedNames = false;
			// fQualifiedNameSearchResult= new QualifiedNameSearchResult();
		}

		protected RefactoringModifications getModifications()
				throws CoreException {
			if (fModifications != null)
				return fModifications;

			fModifications = new MoveModifications();
			IScriptFolder pack = getDestinationAsScriptFolder();
			IContainer container = getDestinationAsContainer();
			Object unitDestination = null;
			if (pack != null)
				unitDestination = pack;
			else
				unitDestination = container;

			// don't use fUpdateReferences directly since it is only valid if
			// canUpdateReferences is true
			boolean updateReferenes = canUpdateReferences()
					&& getUpdateReferences();
			if (unitDestination != null) {
				ISourceModule[] units = getCus();
				for (int i = 0; i < units.length; i++) {
					fModifications.move(units[i], new MoveArguments(
							unitDestination, updateReferenes));
				}
			}
			if (container != null) {
				IFile[] files = getFiles();
				for (int i = 0; i < files.length; i++) {
					fModifications.move(files[i], new MoveArguments(container,
							updateReferenes));
				}
				IFolder[] folders = getFolders();
				for (int i = 0; i < folders.length; i++) {
					fModifications.move(folders[i], new MoveArguments(
							container, updateReferenes));
				}
			}
			return fModifications;
		}

		protected RefactoringStatus verifyDestination(IModelElement destination)
				throws ModelException {
			RefactoringStatus superStatus = super
					.verifyDestination(destination);
			if (superStatus.hasFatalError())
				return superStatus;

			Object commonParent = new ParentChecker(getResources(),
					getScriptElements()).getCommonParent();
			if (destination.equals(commonParent))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_parent);
			IContainer destinationAsContainer = getDestinationAsContainer();
			if (destinationAsContainer != null
					&& destinationAsContainer.equals(commonParent))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_parent);
			IScriptFolder destinationAsPackage = getDestinationAsScriptFolder();
			if (destinationAsPackage != null
					&& destinationAsPackage.equals(commonParent))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_parent);

			return superStatus;
		}

		protected RefactoringStatus verifyDestination(IResource destination)
				throws ModelException {
			RefactoringStatus superStatus = super
					.verifyDestination(destination);
			if (superStatus.hasFatalError())
				return superStatus;

			Object commonParent = getCommonParent();
			if (destination.equals(commonParent))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_parent);
			IContainer destinationAsContainer = getDestinationAsContainer();
			if (destinationAsContainer != null
					&& destinationAsContainer.equals(commonParent))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_parent);
			IModelElement destinationContainerAsPackage = getDestinationContainerAsScriptElement();
			if (destinationContainerAsPackage != null
					&& destinationContainerAsPackage.equals(commonParent))
				return RefactoringStatus
						.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_parent);

			return superStatus;
		}

		private Object getCommonParent() {
			return new ParentChecker(getResources(), getScriptElements())
					.getCommonParent();
		}

		public Change createChange(IProgressMonitor pm) throws ModelException {
			if (!fUpdateReferences) {
				return createSimpleMoveChange(pm);
			} else {
				return createReferenceUpdatingMoveChange(pm);
			}
		}

		public Change postCreateChange(Change[] participantChanges,
				IProgressMonitor pm) throws CoreException {
			if (DLTKCore.DEBUG) {
				System.err
						.println("TODO: ReorgPolicyFactory Add QualifiedNameSearchResult "); //$NON-NLS-1$
			}
			// if (fQualifiedNameSearchResult != null) {
			// return
			// fQualifiedNameSearchResult.getSingleChange(Changes.
			// getModifiedFiles(participantChanges));
			// } else {
			return null;
			// }
		}

		private Change createReferenceUpdatingMoveChange(IProgressMonitor pm)
				throws ModelException {
			pm.beginTask("", 2 + (fUpdateQualifiedNames ? 1 : 0)); //$NON-NLS-1$
			try {
				CompositeChange composite = new DynamicValidationStateChange(
						RefactoringCoreMessages.ReorgPolicy_move);
				composite.markAsSynthetic();
				// XX workaround for bug 13558
				// <workaround>
				if (fChangeManager == null) {
					fChangeManager = createChangeManager(
							new SubProgressMonitor(pm, 1),
							new RefactoringStatus()); // TODO: non-CU matches
					// silently dropped
					RefactoringStatus status = Checks.validateModifiesFiles(
							getAllModifiedFiles(), null);
					if (status.hasFatalError())
						fChangeManager = new TextChangeManager();
				}
				// </workaround>

				composite
						.merge(new CompositeChange(
								RefactoringCoreMessages.MoveRefactoring_reorganize_elements,
								fChangeManager.getAllChanges()));

				Change fileMove = createSimpleMoveChange(new SubProgressMonitor(
						pm, 1));
				if (fileMove instanceof CompositeChange) {
					composite.merge(((CompositeChange) fileMove));
				} else {
					composite.add(fileMove);
				}
				return composite;
			} finally {
				pm.done();
			}
		}

		private TextChangeManager createChangeManager(IProgressMonitor pm,
				RefactoringStatus status) throws ModelException {
			pm.beginTask("", 1); //$NON-NLS-1$
			try {
				if (!fUpdateReferences)
					return new TextChangeManager();

				// IScriptFolder packageDest= getDestinationAsScriptFolder();
				// if (DLTKCore.DEBUG) {
				// System.err.println("TODO: ReorgPolicyFactory Add
				// MoveCuUpdateCreator support..");
				// }
				// if (packageDest != null){
				// MoveCuUpdateCreator creator= new
				// MoveCuUpdateCreator(getCus(), packageDest);
				// return creator.createChangeManager(new SubProgressMonitor(pm,
				// 1), status);
				// } else
				return new TextChangeManager();
			} finally {
				pm.done();
			}
		}

		private Change createSimpleMoveChange(IProgressMonitor pm) {
			CompositeChange result = new DynamicValidationStateChange(
					RefactoringCoreMessages.ReorgPolicy_move);
			result.markAsSynthetic();
			IFile[] files = getFiles();
			IFolder[] folders = getFolders();
			ISourceModule[] cus = getCus();
			pm.beginTask("", files.length + folders.length + cus.length); //$NON-NLS-1$
			for (int i = 0; i < files.length; i++) {
				result.add(createChange(files[i]));
				pm.worked(1);
			}
			if (pm.isCanceled())
				throw new OperationCanceledException();
			for (int i = 0; i < folders.length; i++) {
				result.add(createChange(folders[i]));
				pm.worked(1);
			}
			if (pm.isCanceled())
				throw new OperationCanceledException();
			for (int i = 0; i < cus.length; i++) {
				result.add(createChange(cus[i]));
				pm.worked(1);
			}
			pm.done();
			return result;
		}

		private Change createChange(ISourceModule cu) {
			IScriptFolder pack = getDestinationAsScriptFolder();
			if (pack != null)
				return moveCuToPackage(cu, pack);
			IContainer container = getDestinationAsContainer();
			if (container == null)
				return new NullChange();
			return moveFileToContainer(cu, container);
		}

		private static Change moveCuToPackage(ISourceModule cu,
				IScriptFolder dest) {
			// XXX workaround for bug 31998 we will have to disable renaming of
			// linked packages (and cus)
			IResource resource = ResourceUtil.getResource(cu);
			if (resource != null && resource.isLinked()) {
				if (ResourceUtil.getResource(dest) instanceof IContainer)
					return moveFileToContainer(cu,
							(IContainer) ResourceUtil.getResource(dest));
			}
			return new MoveSourceModuleChange(cu, dest);
		}

		private static Change moveFileToContainer(ISourceModule cu,
				IContainer dest) {
			return new MoveResourceChange(ResourceUtil.getResource(cu), dest);
		}

		private Change createChange(IResource res) {
			IContainer destinationAsContainer = getDestinationAsContainer();
			if (destinationAsContainer == null)
				return new NullChange();
			return new MoveResourceChange(res, destinationAsContainer);
		}

		private void computeQualifiedNameMatches(IProgressMonitor pm)
				throws ModelException {
			if (!fUpdateQualifiedNames)
				return;
			IScriptFolder destination = getDestinationAsScriptFolder();
			if (destination != null) {
				ISourceModule[] cus = getCus();
				pm.beginTask("", cus.length); //$NON-NLS-1$
				pm.subTask(RefactoringCoreMessages.MoveRefactoring_scanning_qualified_names);
				for (int i = 0; i < cus.length; i++) {
					ISourceModule cu = cus[i];
					IType[] types = cu.getTypes();
					IProgressMonitor typesMonitor = new SubProgressMonitor(pm,
							1);
					typesMonitor.beginTask("", types.length); //$NON-NLS-1$
					for (int j = 0; j < types.length; j++) {
						handleType(types[j], destination,
								new SubProgressMonitor(typesMonitor, 1));
						if (typesMonitor.isCanceled())
							throw new OperationCanceledException();
					}
					typesMonitor.done();
				}
			}
			pm.done();
		}

		private void handleType(IType type, IScriptFolder destination,
				IProgressMonitor pm) {
			if (DLTKCore.DEBUG) {
				System.err.println("TODO: Add QualifiedNameFinder support..."); //$NON-NLS-1$
			}
			// QualifiedNameFinder.process(fQualifiedNameSearchResult,
			// type.getFullyQualifiedName(), destination.getElementName() + "."
			// + type.getTypeQualifiedName(),
			// fFilePatterns, type.getScriptProject().getProject(), pm);
		}

		public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
				CheckConditionsContext context, IReorgQueries reorgQueries)
				throws CoreException {
			try {
				pm.beginTask("", fUpdateQualifiedNames ? 7 : 3); //$NON-NLS-1$
				RefactoringStatus result = new RefactoringStatus();
				confirmMovingReadOnly(reorgQueries);
				fChangeManager = createChangeManager(new SubProgressMonitor(pm,
						2), result);
				if (fUpdateQualifiedNames)
					computeQualifiedNameMatches(new SubProgressMonitor(pm, 4));
				result.merge(super.checkFinalConditions(new SubProgressMonitor(
						pm, 1), context, reorgQueries));
				return result;
			} catch (ModelException e) {
				throw e;
			} catch (CoreException e) {
				throw new ModelException(e);
			} finally {
				pm.done();
			}
		}

		private void confirmMovingReadOnly(IReorgQueries reorgQueries)
				throws CoreException {
			if (!ReadOnlyResourceFinder.confirmMoveOfReadOnlyElements(
					getScriptElements(), getResources(), reorgQueries))
				throw new OperationCanceledException(); // saying' no' to this
			// one is like
			// cancelling the whole
			// operation
		}

		public IFile[] getAllModifiedFiles() {
			Set result = new HashSet();
			result.addAll(Arrays.asList(ResourceUtil.getFiles(fChangeManager
					.getAllSourceModules())));
			// result.addAll(Arrays.asList(fQualifiedNameSearchResult.
			// getAllFiles()));
			if (getDestinationAsScriptFolder() != null && getUpdateReferences())
				result.addAll(Arrays.asList(ResourceUtil.getFiles(getCus())));
			return (IFile[]) result.toArray(new IFile[result.size()]);
		}

		public boolean hasAllInputSet() {
			return super.hasAllInputSet() && !canUpdateReferences()
					&& !canUpdateQualifiedNames();
		}

		public boolean canUpdateReferences() {
			if (getCus().length == 0)
				return false;
			IScriptFolder pack = getDestinationAsScriptFolder();
			if (pack != null && pack.isRootFolder())
				return false;
			Object commonParent = getCommonParent();
			if (ModelElementUtil.isDefaultPackage(commonParent))
				return false;
			return true;
		}

		public boolean getUpdateReferences() {
			return fUpdateReferences;
		}

		public void setUpdateReferences(boolean update) {
			fUpdateReferences = update;
		}

		public boolean canEnableQualifiedNameUpdating() {
			return getCus().length > 0
					&& !ModelElementUtil.isDefaultPackage(getCommonParent());
		}

		public boolean canUpdateQualifiedNames() {
			IScriptFolder pack = getDestinationAsScriptFolder();
			return (canEnableQualifiedNameUpdating() && pack != null && !pack
					.isRootFolder());
		}

		public boolean getUpdateQualifiedNames() {
			return fUpdateQualifiedNames;
		}

		public void setUpdateQualifiedNames(boolean update) {
			fUpdateQualifiedNames = update;
		}

		public String getFilePatterns() {
			return fFilePatterns;
		}

		public void setFilePatterns(String patterns) {
			Assert.isNotNull(patterns);
			fFilePatterns = patterns;
		}

		public ICreateTargetQuery getCreateTargetQuery(
				ICreateTargetQueries createQueries) {
			return createQueries.createNewPackageQuery();
		}

		public boolean isTextualMove() {
			return false;
		}
	}

	private static class NoMovePolicy extends ReorgPolicy implements
			IMovePolicy {
		protected RefactoringStatus verifyDestination(IResource resource)
				throws ModelException {
			return RefactoringStatus
					.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_noMoving);
		}

		protected RefactoringStatus verifyDestination(IModelElement modelElement)
				throws ModelException {
			return RefactoringStatus
					.createFatalErrorStatus(RefactoringCoreMessages.ReorgPolicyFactory_noMoving);
		}

		public Change createChange(IProgressMonitor pm) {
			return new NullChange();
		}

		public Change postCreateChange(Change[] participantChanges,
				IProgressMonitor pm) throws CoreException {
			return null;
		}

		public boolean canEnable() throws ModelException {
			return false;
		}

		public IResource[] getResources() {
			return new IResource[0];
		}

		public IModelElement[] getScriptElements() {
			return new IModelElement[0];
		}

		public ICreateTargetQuery getCreateTargetQuery(
				ICreateTargetQueries createQueries) {
			return null;
		}

		public boolean isTextualMove() {
			return true;
		}
	}

	private static class ActualSelectionComputer {
		private final IResource[] fResources;
		private final IModelElement[] fScriptElements;

		public ActualSelectionComputer(IModelElement[] modelElements,
				IResource[] resources) {
			fScriptElements = modelElements;
			fResources = resources;
		}

		public IModelElement[] getActualScriptElementsToReorg()
				throws ModelException {
			List result = new ArrayList();
			for (int i = 0; i < fScriptElements.length; i++) {
				IModelElement element = fScriptElements[i];
				if (element == null)
					continue;
				if (ReorgUtils.isDeletedFromEditor(element))
					continue;
				if (element instanceof IType) {
					IType type = (IType) element;
					ISourceModule cu = type.getSourceModule();
					if (cu != null && type.getDeclaringType() == null
							&& cu.exists() && cu.getTypes().length == 1
							&& !result.contains(cu))
						result.add(cu);
					else if (!result.contains(type))
						result.add(type);
				} else if (element instanceof IScriptFolder
						&& !element.isReadOnly()
						&& element.getResource() != null) {
					// skip
				} else if (!result.contains(element)) {
					result.add(element);
				}
			}
			return (IModelElement[]) result.toArray(new IModelElement[result
					.size()]);
		}

		public IResource[] getActualResourcesToReorg() {
			Set modelElementSet = new HashSet(Arrays.asList(fScriptElements));
			List result = new ArrayList();
			for (int i = 0; i < fResources.length; i++) {
				if (fResources[i] == null)
					continue;
				IModelElement element = DLTKCore.create(fResources[i]);
				if (element == null || !element.exists()
						|| !modelElementSet.contains(element))
					if (!result.contains(fResources[i]))
						result.add(fResources[i]);
			}
			for (int i = 0; i < fScriptElements.length; ++i) {
				IModelElement element = fScriptElements[i];
				if (element == null)
					continue;
				if (element.getElementType() == IModelElement.SCRIPT_FOLDER
						&& !element.isReadOnly()) {
					IResource resource = element.getResource();
					if (resource != null) {
						if (!result.contains(resource))
							result.add(resource);
					}
				}
			}
			return (IResource[]) result.toArray(new IResource[result.size()]);

		}
	}
}
