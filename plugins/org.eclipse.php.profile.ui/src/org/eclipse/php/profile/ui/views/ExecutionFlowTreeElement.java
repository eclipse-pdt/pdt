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
package org.eclipse.php.profile.ui.views;

import org.eclipse.php.profile.core.data.ProfilerCallTraceLayer;

/**
 * Execution flow tree element.
 */
public class ExecutionFlowTreeElement extends SimpleHTMLPresentableTreeElement {

	private final static int ROOT_ID = -1;

	private ProfilerCallTraceLayer fLayer;
	private double fDuration;
	private double fTimePercentage = 100;
	private int fOrderID;

	public ExecutionFlowTreeElement() {
		this(ROOT_ID);
	}

	public ExecutionFlowTreeElement(int orderID) {
		fOrderID = orderID;
	}

	public int getOrderID() {
		return fOrderID;
	}

	public void setDuration(double duration) {
		fDuration = duration;
	}

	public double getDuration() {
		return fDuration;
	}

	public void setTimePercentage(double timePercentage) {
		fTimePercentage = (Math.round(timePercentage * 100)) / 100.0;
	}

	public double getTimePercentage() {
		return fTimePercentage;
	}

	public void setLayer(ProfilerCallTraceLayer layer) {
		fLayer = layer;
	}

	public ProfilerCallTraceLayer getLayer() {
		return fLayer;
	}

	public boolean isRootElement() {
		return fOrderID == ROOT_ID;
	}
}