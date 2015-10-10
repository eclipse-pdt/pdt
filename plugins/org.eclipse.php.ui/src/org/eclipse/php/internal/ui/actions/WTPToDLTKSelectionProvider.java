/*******************************************************************************
 * Copyright (c) 2015 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.*;

/**
 * Simple class that transform WTP {@link IStructuredSelection} to simple
 * {@link ITextSelection}
 * 
 * @author zulus
 */
public class WTPToDLTKSelectionProvider implements ISelectionProvider, ISelectionChangedListener {
	Set<ISelectionChangedListener> listeners = new HashSet<ISelectionChangedListener>();
	private ISelectionProvider parentProvider;

	public WTPToDLTKSelectionProvider(ISelectionProvider parentProvider) {
		this.parentProvider = parentProvider;
	}

	@Override
	public void setSelection(ISelection selection) {
		parentProvider.setSelection(selection);
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);

	}

	@Override
	public ISelection getSelection() {
		return transformSelection(parentProvider.getSelection());
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection sel = transformSelection(event.getSelection());
		SelectionChangedEvent newEvent = new SelectionChangedEvent(this, sel);
		for (ISelectionChangedListener listener : listeners) {
			listener.selectionChanged(newEvent);
		}
	}

	/**
	 * WTP StructuredTextSelection is also {@link ITextSelection}
	 * 
	 * @param selection
	 * @return
	 */
	protected ISelection transformSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection && selection instanceof ITextSelection) {
			selection = new TextSelection(((ITextSelection) selection).getOffset(),
					((ITextSelection) selection).getLength());
			return selection;
		}
		return selection;
	}

	public void dispose() {
		this.parentProvider.removeSelectionChangedListener(this);
	}

	public void register() {
		this.parentProvider.addSelectionChangedListener(this);
	}
}