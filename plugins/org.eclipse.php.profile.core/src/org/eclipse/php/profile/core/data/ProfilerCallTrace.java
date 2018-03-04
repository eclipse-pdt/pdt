/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Profiler call trace descriptor.
 */
public class ProfilerCallTrace {

	private List<ProfilerCallTraceLayer> fLayers;
	private int fLayersCount;

	public ProfilerCallTrace() {
		fLayers = new ArrayList<>();
	}

	public ProfilerCallTrace(List<ProfilerCallTraceLayer> layers) {
		fLayers = layers;
	}

	public void addLayer(ProfilerCallTraceLayer layer) {
		fLayers.add(layer);
	}

	public void removeFirstLayer() {
		fLayers.remove(0);
		fLayersCount--;
	}

	/**
	 * Returns the layers count
	 */
	public int getLayersCount() {
		return fLayersCount;
	}

	/**
	 * Sets the layers count
	 */

	public void setLayersCount(int count) {
		this.fLayersCount = count;
	}

	/**
	 * Removing the first and last layer
	 */
	public void removeWrappedLayers() {
		fLayers.remove(0);
		fLayers.remove(fLayers.size() - 1);
		fLayersCount = fLayersCount - 2;
	}

	public ProfilerCallTraceLayer[] getLayers() {
		ProfilerCallTraceLayer[] pctl = new ProfilerCallTraceLayer[fLayers.size()];
		fLayers.toArray(pctl);
		return pctl;
	}
}
