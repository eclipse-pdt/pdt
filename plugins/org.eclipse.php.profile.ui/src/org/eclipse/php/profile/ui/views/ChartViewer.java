/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

/**
 * The selector of charts in SWT.
 */
public class ChartViewer extends Composite implements PaintListener {

	private IDeviceRenderer fDeviceRenderer = null;
	private Chart fChartModel = null;
	private GeneratedChartState fGeneratedChartState = null;

	/**
	 * Get the connection with SWT device to render the graphics.
	 */
	public ChartViewer(Composite parent, int style) {
		super(parent, style);
		final PluginSettings ps = PluginSettings.instance();
		try {
			fDeviceRenderer = ps.getDevice("dv.SWT");//$NON-NLS-1$
		} catch (ChartException ex) {
			ProfilerUiPlugin.log(ex);
		}

		addPaintListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events
	 * .PaintEvent)
	 */
	@Override
	public final void paintControl(PaintEvent e) {
		if (fChartModel == null) {
			return;
		}

		if (fDeviceRenderer == null) {
			return;
		}

		Rectangle d = this.getClientArea();
		Image imgChart = new Image(this.getDisplay(), d);
		GC gcImage = new GC(imgChart);
		fDeviceRenderer.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gcImage);

		Bounds bo = BoundsImpl.create(0, 0, d.width, d.height);
		bo.scale(72d / fDeviceRenderer.getDisplayServer().getDpiResolution());

		Generator gr = Generator.instance();
		try {
			fGeneratedChartState = gr.build(fDeviceRenderer.getDisplayServer(), fChartModel, bo, null, null, null);
		} catch (ChartException ce) {
			ProfilerUiPlugin.log(ce);
		}
		try {
			gr.render(fDeviceRenderer, fGeneratedChartState);
			GC gc = e.gc;
			gc.drawImage(imgChart, d.x, d.y);
		} catch (ChartException gex) {
			ProfilerUiPlugin.log(gex);
		}
	}

	public void updateChartModel(Chart chartModel) {
		fChartModel = chartModel;
		redraw();
	}
}
