package org.eclipse.php.internal.ui.treecontent;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.IPhpModel;
import org.eclipse.php.internal.core.phpModel.parser.ModelListener;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.IPhpProjectOptionChangeListener;
import org.eclipse.swt.graphics.Image;

/**
 * Main Include Path node under project.
 */
class IncludesNode extends PHPTreeNode implements IPhpProjectOptionChangeListener, ModelListener {

	private IncludePathTreeContent provider;

	IncludesNode(final String text, final Image image, final String id, final IProject project, final IncludePathTreeContent provider) {
		super(text, image, id, project, null);
		this.provider = provider;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.project.options.IPhpProjectOptionChangeListener#notifyOptionChanged(java.lang.Object, java.lang.Object)
	 */
	public void notifyOptionChanged(final Object oldOption, final Object newOption) {
		final List<IIncludePathEntry> oldEntriesList = Arrays.asList((IIncludePathEntry[]) oldOption);
		final List<IIncludePathEntry> newEntriesList = Arrays.asList((IIncludePathEntry[]) newOption);

		boolean hasChanges = false;

		for (final IIncludePathEntry newEntry : newEntriesList)
			if (!oldEntriesList.contains(newEntry))
				hasChanges = true;

		for (final IIncludePathEntry oldEntry : oldEntriesList)
			if (!newEntriesList.contains(oldEntry))
				hasChanges = true;

		if (hasChanges) {
			IncludePathTreeContent.includePathTree.getDataTree().empty();
			refresh();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#fileDataChanged(org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData)
	 */
	public void fileDataChanged(final PHPFileData fileData) {
		refreshFile(fileData, false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#fileDataAdded(org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData)
	 */
	public void fileDataAdded(final PHPFileData fileData) {
		refreshFile(fileData, false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#fileDataRemoved(org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData)
	 */
	public void fileDataRemoved(final PHPFileData fileData) {
		refreshFile(fileData, true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#dataCleared()
	 */
	public void dataCleared() {
		refresh();
	}

	/**
	 * Refresh the whole tree.
	 */
	private void refresh() {
		refresh(this);
	}

	/**
	 * Refresh an object in the tree.
	 * @param obj
	 */
	private void refresh(final Object obj) {
		if (provider.getTreeViewer() != null && !provider.getTreeViewer().getControl().isDisposed())
			provider.getTreeViewer().getControl().getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (provider.getTreeViewer() != null && !provider.getTreeViewer().getControl().isDisposed())
						provider.getTreeViewer().refresh(obj);
				}
			});
	}

	/**
	 * refresh the file in the tree
	 * @param fileData
	 * @param deleted
	 */
	private void refreshFile(final PHPFileData fileData, final boolean deleted) {
		final IPhpModel model = IncludePathTreeContent.findModel(fileData);
		if (model == null)
			return;

		final IPath fileLocation = new Path(fileData.getName());

		final IPath includeModelLocation = PHPModelUtil.getIncludeModelLocation(model);
		final IPath modelPath = IncludePathTreeContent.INCLUDE_PATHS_ROOT_PATH.append(IncludeModelPathRootConverter.toString(model));
		final IPath fileTreeLocation = modelPath.append(fileLocation.removeFirstSegments(includeModelLocation.segmentCount()));
		if (IncludePathTreeContent.includePathTree.includes(fileTreeLocation)) {
			refreshExistingFileNode(fileTreeLocation, deleted);
			if (!deleted)
				IncludePathTreeContent.includePathTree.createElement(fileTreeLocation, fileData);
		} else
			refreshMissingFileNode(fileTreeLocation, deleted);
	}

	/**
	 * Refresh the existing file in the tree
	 * @param fileTreeLocation
	 * @param deleted
	 */
	private void refreshExistingFileNode(final IPath fileTreeLocation, final boolean deleted) {
		if (deleted) {
			IPath elementToRefresh = fileTreeLocation;
			while (IncludePathTreeContent.includePathTree.getChildCount(elementToRefresh) < 1 && elementToRefresh.segmentCount() > 1) {
				if (IncludePathTreeContent.includePathTree.includes(elementToRefresh))
					IncludePathTreeContent.includePathTree.deleteElement(elementToRefresh);
				elementToRefresh = elementToRefresh.removeLastSegments(1);
			}
			refresh(IncludePathTreeContent.includePathTree.getElementData(elementToRefresh));
		} else
			refresh(IncludePathTreeContent.includePathTree.getElementData(fileTreeLocation));
	}

	/**
	 * Refresh the missing file in the tree
	 * @param fileTreeLocation
	 * @param deleted
	 */
	private void refreshMissingFileNode(final IPath fileTreeLocation, final boolean deleted) {
		IPath elementToRefresh = fileTreeLocation;
		while (!IncludePathTreeContent.includePathTree.includes(elementToRefresh) && elementToRefresh.segmentCount() > 1) {
			if (deleted)
				if (IncludePathTreeContent.includePathTree.includes(elementToRefresh) && IncludePathTreeContent.includePathTree.getChildCount(elementToRefresh) < 1)
					IncludePathTreeContent.includePathTree.deleteElement(elementToRefresh);
			elementToRefresh = fileTreeLocation.removeLastSegments(1);
		}
		refresh(IncludePathTreeContent.includePathTree.getElementData(elementToRefresh));
	}

}