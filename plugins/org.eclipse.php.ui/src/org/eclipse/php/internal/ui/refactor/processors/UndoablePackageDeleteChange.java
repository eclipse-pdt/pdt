/*******************************************************************************
 * Copyright (c) 2006, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.internal.ui.refactor.processors;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.internal.corext.refactoring.changes.DynamicValidationStateChange;
import org.eclipse.dltk.internal.corext.refactoring.changes.UndoDeleteResourceChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ui.ide.undo.ResourceDescription;

public class UndoablePackageDeleteChange extends DynamicValidationStateChange {

	private final List/* <IResource> */ fPackageDeletes;

	public UndoablePackageDeleteChange(String name, List/* <IResource> */ packageDeletes) {
		super(name);
		fPackageDeletes = packageDeletes;
	}

	public Change perform(IProgressMonitor pm) throws CoreException {
		int count = fPackageDeletes.size();
		SubMonitor subMonitor = SubMonitor.convert(pm, count * 3);
		ResourceDescription[] packageDeleteDescriptions = new ResourceDescription[fPackageDeletes.size()];
		for (int i = 0; i < fPackageDeletes.size(); i++) {
			IResource resource = (IResource) fPackageDeletes.get(i);
			packageDeleteDescriptions[i] = ResourceDescription.fromResource(resource);
			subMonitor.worked(1);
		}

		DynamicValidationStateChange result = (DynamicValidationStateChange) super.perform(subMonitor.split(count));

		for (int i = 0; i < fPackageDeletes.size(); i++) {
			IResource resource = (IResource) fPackageDeletes.get(i);
			ResourceDescription resourceDescription = packageDeleteDescriptions[i];
			resourceDescription.recordStateFromHistory(resource, subMonitor.split(1));
			result.add(new UndoDeleteResourceChange(resourceDescription));
		}
		subMonitor.done();

		return result;
	}
}
