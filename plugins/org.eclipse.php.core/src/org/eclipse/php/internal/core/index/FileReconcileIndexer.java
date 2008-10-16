package org.eclipse.php.internal.core.index;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.search.SearchDocument;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.index.Index;
import org.eclipse.dltk.core.search.indexing.IProjectIndexer;
import org.eclipse.dltk.core.search.indexing.IndexManager;
import org.eclipse.dltk.core.search.indexing.IndexRequest;
import org.eclipse.dltk.core.search.indexing.ReadWriteMonitor;
import org.eclipse.dltk.internal.core.ModelManager;

public class FileReconcileIndexer implements IProjectIndexer {

	private final IndexManager manager = ModelManager.getModelManager().getIndexManager();
	
	public void reconciled(ISourceModule module, IDLTKLanguageToolkit toolkit) {

		final IPath containerPath = module.getScriptProject().getProject().getFullPath();
		final SearchParticipant participant = SearchEngine.getDefaultSearchParticipant();
		final IResource resource = module.getResource();
		final SearchDocument document = participant.getDocument(resource.getFullPath().toString(), resource.getProject());

		manager.request(new IndexRequest(containerPath, manager) {
			public boolean execute(IProgressMonitor progressMonitor) {
				if (this.isCancelled || progressMonitor != null && progressMonitor.isCanceled()) {
					return true;
				}
				/* ensure no concurrent write access to index */
				String indexLocation = manager.computeIndexLocation(containerPath);
				Index index = manager.getIndex(this.containerPath, indexLocation, true, true);
				/* reuse index file */
				/* create if none */
				if (index == null) {
					return true;
				}
				ReadWriteMonitor monitor = index.monitor;
				if (monitor == null) {
					return true; // index got deleted since acquired
				}
				try {
					monitor.enterWrite(); // ask permission to write
					manager.indexDocument(document, participant, index, new Path(indexLocation));
				} finally {
					monitor.exitWrite(); // free write lock
				}
				return true;
			}

			public String toString() {
				return "indexing " + document.getPath(); //$NON-NLS-1$
			}
		});
	}

	public void indexLibrary(IScriptProject project, IPath path) {
	}

	public void indexProject(IScriptProject project) {
	}

	public void indexProjectFragment(IScriptProject project, IPath path) {
	}

	public void indexSourceModule(final ISourceModule module, IDLTKLanguageToolkit toolkit) {
	}

	public void removeLibrary(IScriptProject project, IPath path) {
	}

	public void removeProject(IPath projectPath) {
	}

	public void removeProjectFragment(IScriptProject project, IPath path) {
	}

	public void removeSourceModule(IScriptProject project, String path) {
	}
}
