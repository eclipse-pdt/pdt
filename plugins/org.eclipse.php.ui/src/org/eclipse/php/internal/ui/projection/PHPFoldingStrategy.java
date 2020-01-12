package org.eclipse.php.internal.ui.projection;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.source.projection.IProjectionListener;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.folding.IStructuredTextFoldingProvider;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.ui.internal.projection.AbstractStructuredFoldingStrategy;

public class PHPFoldingStrategy extends AbstractStructuredFoldingStrategy {

	private IStructuredTextFoldingProvider currentFoldingProvider;

	public PHPFoldingStrategy() {
		super();
		currentFoldingProvider = PHPUiPlugin.getDefault().getFoldingStructureProviderRegistry()
				.getCurrentFoldingProvider();
	}

	@Override
	protected Position calcNewFoldPosition(IndexedRegion indexedRegion) {
		return null;
	}

	@Override
	protected boolean indexedRegionValidType(IndexedRegion indexedRegion) {
		return false;
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		// disable
	}

	@Override
	public void projectionDisabled() {
		super.projectionDisabled();
		IProjectionListener projectionListener = getProjectionListener();
		if (projectionListener != null) {
			projectionListener.projectionDisabled();
		}
	}

	@Override
	public void projectionEnabled() {
		super.projectionEnabled();
		IProjectionListener projectionListener = getProjectionListener();
		if (projectionListener != null) {
			projectionListener.projectionEnabled();
		}
	}

	private IProjectionListener getProjectionListener() {
		if (currentFoldingProvider instanceof IProjectionListener) {
			return (IProjectionListener) currentFoldingProvider;
		}
		return null;
	}

	@Override
	public void setViewer(ProjectionViewer viewer) {
		if (currentFoldingProvider != null) {
			currentFoldingProvider.install(viewer);
			currentFoldingProvider.initialize();
		}
	}

	public void restore() {
		if (currentFoldingProvider != null) {
			currentFoldingProvider.initialize();
		}
	}

	@Override
	public void uninstall() {
		super.uninstall();
		if (currentFoldingProvider != null) {
			currentFoldingProvider.uninstall();
		}
	}

}
