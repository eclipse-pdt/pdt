/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/*
 * PHPstack.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author guy
 */
public class PHPstack {

	private Vector<StackLayer> layers;

	/**
	 * Creates new PHPstack
	 */
	public PHPstack() {
		layers = new Vector<>();
	}

	public void addLayer(StackLayer layer) {
		layers.add(layer);
	}

	public StackLayer removeLayer(int depth) {
		return layers.remove(depth);
	}

	public int getSize() {
		return layers.size();
	}

	public StackLayer getLayer(int depth) {
		return layers.get(depth);
	}

	public StackLayer[] getLayers() {
		StackLayer[] arrayLeyers = new StackLayer[layers.size()];
		layers.toArray(arrayLeyers);
		return arrayLeyers;
	}

	@Override
	public String toString() {
		String toReturn = "***\n"; //$NON-NLS-1$
		Enumeration<StackLayer> e = layers.elements();
		while (e.hasMoreElements()) {
			toReturn += e.nextElement().toString() + "\n" + "***\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return toReturn;
	}

}