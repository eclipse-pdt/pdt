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
package org.eclipse.php.profile.ui.utils;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;

/**
 * Chart utility class.
 */
public class ChartUtil {

	public static final Chart createPieChart(String[] labels, double[] values) {

		if (labels == null || labels.length <= 0) {
			return null;
		}

		ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create();

		// Plot
		cwoaPie.setSeriesThickness(8);
		cwoaPie.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL);
		cwoaPie.getBlock().setBackground(ColorDefinitionImpl.TRANSPARENT());

		Plot p = cwoaPie.getPlot();
		p.getClientArea().setBackground(ColorDefinitionImpl.TRANSPARENT());
		p.getClientArea().getOutline().setVisible(false);
		p.getOutline().setVisible(false);

		// Legend
		Legend lg = cwoaPie.getLegend();
		lg.setPosition(Position.BELOW_LITERAL);
		lg.getText().getFont().setSize(10);
		lg.setBackground(null);
		lg.getOutline().setVisible(false);

		// Title
		cwoaPie.getTitle().setVisible(false);

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create(labels);
		NumberDataSet seriesValues = NumberDataSetImpl.create(values);

		// Base Series
		Series categorySeries = SeriesImpl.create();
		categorySeries.setDataSet(categoryValues);

		SeriesDefinition categorySeriesDefinition = SeriesDefinitionImpl.create();
		cwoaPie.getSeriesDefinitions().add(categorySeriesDefinition);
		categorySeriesDefinition.getSeriesPalette().update(0);
		categorySeriesDefinition.getSeries().add(categorySeries);

		// Orthogonal Series
		PieSeries pieSeries = (PieSeries) PieSeriesImpl.create();
		pieSeries.setDataSet(seriesValues);
		pieSeries.setExplosion(15);
		pieSeries.setRatio(1.0);
		pieSeries.getLabel().setVisible(false);
		pieSeries.getTitle().setVisible(false);

		SeriesDefinition pieSeriesDefinition = SeriesDefinitionImpl.create();
		pieSeriesDefinition.getQuery().setDefinition("");//$NON-NLS-1$
		categorySeriesDefinition.getSeriesDefinitions().add(pieSeriesDefinition);
		pieSeriesDefinition.getSeries().add(pieSeries);

		return cwoaPie;
	}
}
