/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.test.internal.performance.data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * @since 3.1
 */
public class DataPoint {
	private int fStep;
	private Map<Dim, Scalar> fScalars;
	
	public DataPoint(int step, Map<Dim, Scalar> values) {
		fStep= step;
		fScalars= values;
	}
	
	public int getStep() {
		return fStep;
	}
	
	public Dim[] getDimensions() {
	    Set<?> set= fScalars.keySet();
	    return set.toArray(new Dim[set.size()]);
	}
	
    public Collection<Dim> getDimensions2() {
	    return fScalars.keySet();
    }

    public boolean contains(Dim dimension) {
		return fScalars.containsKey(dimension);
	}
	
	public Scalar[] getScalars() {
		return fScalars.values().toArray(new Scalar[fScalars.size()]);
	}
	
	public Scalar getScalar(Dim dimension) {
		return fScalars.get(dimension);
	}
	
	@Override
	public String toString() {
		return "DataPoint [step= " + fStep + ", #dimensions: " + fScalars.size() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
