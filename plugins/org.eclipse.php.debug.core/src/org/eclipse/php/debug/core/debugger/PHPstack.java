/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/*
 * PHPstack.java
 *
 */

package org.eclipse.php.debug.core.debugger;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author guy
 */
public class PHPstack {

    private Vector layers;

    /**
     * Creates new PHPstack
     */
    public PHPstack() {
        layers = new Vector();
    }

    public void addLayer(StackLayer layer) {
        layers.add(layer);
    }

    public StackLayer removeLayer(int depth) {
        return (StackLayer) layers.remove(depth);
    }

    public int getSize() {
        return layers.size();
    }

    public StackLayer getLayer(int depth) {
        return (StackLayer) layers.get(depth);
    }

    public StackLayer[] getLayers() {
        StackLayer[] arrayLeyers = new StackLayer[layers.size()];
        layers.toArray(arrayLeyers);
        return arrayLeyers;
    }

    public String toString() {
        String toReturn = "***\n";
        Enumeration e = layers.elements();
        while (e.hasMoreElements()) {
            toReturn += e.nextElement().toString() + "\n" + "***\n";
        }
        return toReturn;
    }

}