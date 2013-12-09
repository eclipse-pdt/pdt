package org.eclipse.php.internal.ui.util;

import java.util.*;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.ui.explorer.PHPExplorerPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.wst.validation.ValidationFramework;

public class LibraryFolderHelper {

	public static void useAsLibraryFolder(IModelElement[] elements,
			IProgressMonitor monitor) throws OperationCanceledException,
			InterruptedException, CoreException {
		disableValidation(elements);

		elements = removeNonExisting(elements);
		if (elements.length > 0) {
			updatePhpExplorer(elements);
			waitValidationJobs();
			deleteProblemMarkers(elements);
		}
	}

	public static void useAsSourceFolder(IModelElement[] elements,
			IProgressMonitor monitor) throws OperationCanceledException,
			InterruptedException, CoreException {
		enableValidation(elements);

		elements = removeNonExisting(elements);
		if (elements.length > 0) {
			updatePhpExplorer(elements);
			waitValidationJobs();
			revalidate(elements, monitor);
		}
	}

	private static void enableValidation(IModelElement[] elements) {
		ValidationFramework vf = ValidationFramework.getDefault();

		for (IModelElement element : elements) {
			vf.enableValidation(element.getResource());
		}
	}

	private static void disableValidation(IModelElement[] elements)
			throws ModelException {
		ValidationFramework vf = ValidationFramework.getDefault();

		for (IModelElement element : elements) {
			for (IModelElement subfolder : getAllSubfolders(element)) {
				vf.enableValidation(subfolder.getResource());
			}

			vf.disableValidation(element.getResource());
		}
	}

	private static IModelElement[] removeNonExisting(IModelElement[] elements) {
		Collection<IModelElement> existing = new HashSet<IModelElement>();

		for (IModelElement element : elements) {
			if (element.exists()) {
				existing.add(element);
			}
		}

		return existing.toArray(new IModelElement[existing.size()]);
	}

	private static void deleteProblemMarkers(IModelElement[] elements)
			throws CoreException {
		for (IModelElement element : elements) {
			element.getResource().deleteMarkers(null, true,
					IResource.DEPTH_INFINITE);
		}
	}

	private static void revalidate(IModelElement[] elements,
			IProgressMonitor monitor) throws CoreException {
		for (IModelElement element : elements) {
			deepTouch(element.getResource(), monitor);
		}
	}

	private static void deepTouch(IResource resource, IProgressMonitor monitor)
			throws CoreException {
		resource.touch(monitor);

		// touch recursively resources inside folders and projects
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			for (IResource member : container.members()) {
				deepTouch(member, monitor);
			}
		}
	}

	private static void updatePhpExplorer(IModelElement[] elements)
			throws ModelException {
		final IModelElement[] subfolders = getAllSubfolders(elements);

		Display.getDefault().asyncExec(new Runnable() {
			@SuppressWarnings("restriction")
			public void run() {
				final PHPExplorerPart phpExplorer = getPhpExplorer();
				if (phpExplorer != null) {
					phpExplorer.getTreeViewer().update(subfolders, null);
				}
			}
		});
	}

	private static PHPExplorerPart getPhpExplorer() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null)
			return null;

		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			return null;

		for (IWorkbenchPartReference ref : page.getViewReferences()) {
			IWorkbenchPart part = ref.getPart(false);
			if (part != null && part instanceof PHPExplorerPart) {
				return (PHPExplorerPart) part;
			}
		}

		return null;
	}

	private static IModelElement[] getAllSubfolders(IModelElement[] elements)
			throws ModelException {
		Collection<IModelElement> allSubfolders = new HashSet<IModelElement>();

		for (IModelElement element : elements) {
			allSubfolders.addAll(Arrays.asList(getAllSubfolders(element)));
		}

		return allSubfolders.toArray(new IModelElement[allSubfolders.size()]);
	}

	private static IModelElement[] getAllSubfolders(IModelElement element)
			throws ModelException {
		List<IModelElement> children = new ArrayList<IModelElement>();

		IPath path = element.getPath();
		IProjectFragment[] fragments = element.getScriptProject()
				.getProjectFragments();

		for (IProjectFragment fragment : fragments) {
			if (pathContainsModelElement(path, fragment)) {
				children.add(fragment);
			}

			for (IModelElement child : fragment.getChildren()) {
				if (child.getElementType() == IModelElement.SCRIPT_FOLDER) {
					if (pathContainsModelElement(path, child)) {
						children.add(child);
					}
				}
			}
		}

		return children.toArray(new IModelElement[children.size()]);
	}

	private static boolean pathContainsModelElement(IPath path,
			IModelElement element) {
		return path.equals(element.getPath().uptoSegment(path.segmentCount()));
	}

	private static void waitValidationJobs() throws OperationCanceledException,
			InterruptedException {
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
	}

}
