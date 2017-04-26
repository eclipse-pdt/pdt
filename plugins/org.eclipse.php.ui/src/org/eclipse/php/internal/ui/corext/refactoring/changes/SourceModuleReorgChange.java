/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.refactoring.changes;

import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.corext.refactoring.base.DLTKChange;
import org.eclipse.dltk.internal.corext.refactoring.reorg.INewNameQuery;
import org.eclipse.dltk.internal.ui.model.DLTKElementResourceMapping;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.participants.ReorgExecutionLog;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.corext.refactoring.RefactoringCoreMessages;

public abstract class SourceModuleReorgChange extends DLTKChange {

	private String fCuHandle;
	private String fOldPackageHandle;
	private String fNewPackageHandle;

	private INewNameQuery fNewNameQuery;

	SourceModuleReorgChange(ISourceModule cu, IScriptFolder dest, INewNameQuery newNameQuery) {
		fCuHandle = cu.getHandleIdentifier();
		fNewPackageHandle = dest.getHandleIdentifier();
		fNewNameQuery = newNameQuery;
		fOldPackageHandle = cu.getParent().getHandleIdentifier();
	}

	SourceModuleReorgChange(ISourceModule cu, IScriptFolder dest) {
		this(cu, dest, null);
	}

	SourceModuleReorgChange(String oldPackageHandle, String newPackageHandle, String cuHandle) {
		fOldPackageHandle = oldPackageHandle;
		fNewPackageHandle = newPackageHandle;
		fCuHandle = cuHandle;
	}

	@Override
	public final Change perform(IProgressMonitor pm) throws CoreException {
		pm.beginTask(getName(), 1);
		try {
			ISourceModule unit = getCu();
			ResourceMapping mapping = DLTKElementResourceMapping.create(unit);
			Change result = doPerformReorg(SubMonitor.convert(pm, 1));
			markAsExecuted(unit, mapping);
			return result;
		} finally {
			pm.done();
		}
	}

	abstract Change doPerformReorg(IProgressMonitor pm) throws CoreException;

	@Override
	public Object getModifiedElement() {
		return getCu();
	}

	ISourceModule getCu() {
		return (ISourceModule) DLTKCore.create(fCuHandle);
	}

	IScriptFolder getOldPackage() {
		return (IScriptFolder) DLTKCore.create(fOldPackageHandle);
	}

	IScriptFolder getDestinationPackage() {
		return (IScriptFolder) DLTKCore.create(fNewPackageHandle);
	}

	String getNewName() {
		if (fNewNameQuery == null)
			return null;
		return fNewNameQuery.getNewName();
	}

	static String getPackageName(IScriptFolder pack) {
		if (pack.isRootFolder())
			return RefactoringCoreMessages.MoveSourceModuleChange_global_namespace;
		else
			return PHPModelUtils.getNamespaceNameByLocation(pack.getElementName());
	}

	private void markAsExecuted(ISourceModule unit, ResourceMapping mapping) {
		ReorgExecutionLog log = (ReorgExecutionLog) getAdapter(ReorgExecutionLog.class);
		if (log != null) {
			log.markAsProcessed(unit);
			log.markAsProcessed(mapping);
		}
	}
}
