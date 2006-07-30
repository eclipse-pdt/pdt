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
package org.eclipse.php.ui.workingset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.ui.ILocalWorkingSetManager;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.IWorkingSetUpdater;
import org.eclipse.ui.PlatformUI;

public class WorkingSetModel {

	private static class ElementMapper {
		private final Map fElementToWorkingSet = new HashMap();
		private final Map fResourceToWorkingSet = new HashMap();

		private final Map fWorkingSetToElement = new IdentityHashMap();

		private void addElement(final IAdaptable element, final IWorkingSet ws) {
			addToMap(fElementToWorkingSet, element, ws);
			final IResource resource = (IResource) element.getAdapter(IResource.class);
			if (resource != null)
				addToMap(fResourceToWorkingSet, resource, ws);
		}

		private void addToMap(final Map map, final IAdaptable key, final IWorkingSet value) {
			final Object obj = map.get(key);
			if (obj == null)
				map.put(key, value);
			else if (obj instanceof IWorkingSet) {
				final List l = new ArrayList(2);
				l.add(obj);
				l.add(value);
				map.put(key, l);
			} else if (obj instanceof List)
				((List) obj).add(value);
		}

		public void clear() {
			fElementToWorkingSet.clear();
			fWorkingSetToElement.clear();
			fResourceToWorkingSet.clear();
		}

		private void computeDelta(final List toRemove, final List toAdd, final IAdaptable[] oldElements, final IAdaptable[] newElements) {
			for (int i = 0; i < oldElements.length; i++)
				toAdd.remove(oldElements[i]);
			for (int i = 0; i < newElements.length; i++)
				toRemove.remove(newElements[i]);

		}

		private List getAllElements(final Map map, final Object key) {
			final Object obj = map.get(key);
			if (obj instanceof List)
				return (List) obj;
			if (obj == null)
				return new ArrayList(0);
			final List result = new ArrayList(1);
			result.add(obj);
			return result;
		}

		public List getAllWorkingSets(final Object element) {
			return getAllElements(fElementToWorkingSet, element);
		}

		public List getAllWorkingSetsForResource(final IResource resource) {
			return getAllElements(fResourceToWorkingSet, resource);
		}

		private Object getFirstElement(final Map map, final Object key) {
			final Object obj = map.get(key);
			if (obj instanceof List)
				return ((List) obj).get(0);
			return obj;
		}

		public IWorkingSet getFirstWorkingSet(final Object element) {
			return (IWorkingSet) getFirstElement(fElementToWorkingSet, element);
		}

		public IWorkingSet getFirstWorkingSetForResource(final IResource resource) {
			return (IWorkingSet) getFirstElement(fResourceToWorkingSet, resource);
		}

		private void put(final IWorkingSet ws) {
			if (fWorkingSetToElement.containsKey(ws))
				return;
			final IAdaptable[] elements = ws.getElements();
			fWorkingSetToElement.put(ws, elements);
			for (int i = 0; i < elements.length; i++)
				addElement(elements[i], ws);
		}

		public void rebuild(final IWorkingSet[] workingSets) {
			clear();
			for (int i = 0; i < workingSets.length; i++)
				put(workingSets[i]);
		}

		public IAdaptable[] refresh(final IWorkingSet ws) {
			final IAdaptable[] oldElements = (IAdaptable[]) fWorkingSetToElement.get(ws);
			if (oldElements == null)
				return null;
			final IAdaptable[] newElements = ws.getElements();
			final List toRemove = new ArrayList(Arrays.asList(oldElements));
			final List toAdd = new ArrayList(Arrays.asList(newElements));
			computeDelta(toRemove, toAdd, oldElements, newElements);
			for (final Iterator iter = toAdd.iterator(); iter.hasNext();)
				addElement((IAdaptable) iter.next(), ws);
			for (final Iterator iter = toRemove.iterator(); iter.hasNext();)
				removeElement((IAdaptable) iter.next(), ws);
			if (toRemove.size() > 0 || toAdd.size() > 0)
				fWorkingSetToElement.put(ws, newElements);
			return oldElements;
		}

		public IAdaptable[] remove(final IWorkingSet ws) {
			final IAdaptable[] elements = (IAdaptable[]) fWorkingSetToElement.remove(ws);
			if (elements != null)
				for (int i = 0; i < elements.length; i++)
					removeElement(elements[i], ws);
			return elements;
		}

		private void removeElement(final IAdaptable element, final IWorkingSet ws) {
			removeFromMap(fElementToWorkingSet, element, ws);
			final IResource resource = (IResource) element.getAdapter(IResource.class);
			if (resource != null)
				removeFromMap(fResourceToWorkingSet, resource, ws);
		}

		private void removeFromMap(final Map map, final IAdaptable key, final IWorkingSet value) {
			final Object current = map.get(key);
			if (current == null)
				return;
			else if (current instanceof List) {
				final List list = (List) current;
				list.remove(value);
				switch (list.size()) {
					case 0:
						map.remove(key);
						break;
					case 1:
						map.put(key, list.get(0));
						break;
				}
			} else if (current == value)
				map.remove(key);
		}
	}

	private static class WorkingSetComparar implements IElementComparer {
		public boolean equals(final Object o1, final Object o2) {
			final IWorkingSet w1 = o1 instanceof IWorkingSet ? (IWorkingSet) o1 : null;
			final IWorkingSet w2 = o2 instanceof IWorkingSet ? (IWorkingSet) o2 : null;
			if (w1 == null || w2 == null)
				return o1.equals(o2);
			return w1 == w2;
		}

		public int hashCode(final Object element) {
			if (element instanceof IWorkingSet)
				return System.identityHashCode(element);
			return element.hashCode();
		}
	}

	public static final String CHANGE_WORKING_SET_MODEL_CONTENT = "workingSetModelChanged"; //$NON-NLS-1$
	public static final IElementComparer COMPARER = new WorkingSetComparar();
	private static final String TAG_ACTIVE_WORKING_SET = "activeWorkingSet"; //$NON-NLS-1$
	private static final String TAG_CONFIGURED = "configured"; //$NON-NLS-1$

	private static final String TAG_LOCAL_WORKING_SET_MANAGER = "localWorkingSetManager"; //$NON-NLS-1$
	private static final String TAG_WORKING_SET_NAME = "workingSetName"; //$NON-NLS-1$
	private List fActiveWorkingSets;
	private boolean fConfigured;
	private final ElementMapper fElementMapper = new ElementMapper();

	private ListenerList fListeners;

	private ILocalWorkingSetManager fLocalWorkingSetManager;

	private OthersWorkingSetUpdater fOthersWorkingSetUpdater;

	private IPropertyChangeListener fWorkingSetManagerListener;

	public WorkingSetModel() {
		fLocalWorkingSetManager = PlatformUI.getWorkbench().createLocalWorkingSetManager();
		addListenersToWorkingSetManagers();
		fActiveWorkingSets = new ArrayList(2);

		final IWorkingSet others = fLocalWorkingSetManager.createWorkingSet(PHPUIMessages.WorkingSetModel_others_name, new IAdaptable[0]);
		others.setId(OthersWorkingSetUpdater.ID);
		fLocalWorkingSetManager.addWorkingSet(others);
		fActiveWorkingSets.add(others);

		if (fOthersWorkingSetUpdater != null)
			fOthersWorkingSetUpdater.init(this);
		fElementMapper.rebuild(getActiveWorkingSets());
	}

	public WorkingSetModel(final IMemento memento) {
		restoreState(memento);
		if (fOthersWorkingSetUpdater != null)
			fOthersWorkingSetUpdater.init(this);
		fElementMapper.rebuild(getActiveWorkingSets());
	}

	private void addListenersToWorkingSetManagers() {
		fListeners = new ListenerList();
		fWorkingSetManagerListener = new IPropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent event) {
				workingSetManagerChanged(event);
			}
		};
		PlatformUI.getWorkbench().getWorkingSetManager().addPropertyChangeListener(fWorkingSetManagerListener);
		fLocalWorkingSetManager.addPropertyChangeListener(fWorkingSetManagerListener);
	}

	/**
	 * Adds a property change listener.
	 * 
	 * @param listener the property change listener to add
	 */
	public void addPropertyChangeListener(final IPropertyChangeListener listener) {
		fListeners.add(listener);
	}

	//---- model relationships ---------------------------------------

	public Object[] addWorkingSets(final Object[] elements) {
		List result = null;
		for (int i = 0; i < elements.length; i++) {
			final Object element = elements[i];
			List sets = null;
			if (element instanceof IResource)
				sets = fElementMapper.getAllWorkingSetsForResource((IResource) element);
			else
				sets = fElementMapper.getAllWorkingSets(element);
			if (sets != null && sets.size() > 0) {
				if (result == null)
					result = new ArrayList(Arrays.asList(elements));
				result.addAll(sets);
			}
		}
		if (result == null)
			return elements;
		return result.toArray();
	}

	public void configured() {
		fConfigured = true;
	}

	public void dispose() {
		if (fWorkingSetManagerListener != null) {
			PlatformUI.getWorkbench().getWorkingSetManager().removePropertyChangeListener(fWorkingSetManagerListener);
			fLocalWorkingSetManager.removePropertyChangeListener(fWorkingSetManagerListener);
			fLocalWorkingSetManager.dispose();
			fWorkingSetManagerListener = null;
		}
	}

	private void fireEvent(final PropertyChangeEvent event) {
		final Object[] listeners = fListeners.getListeners();
		for (int i = 0; i < listeners.length; i++)
			((IPropertyChangeListener) listeners[i]).propertyChange(event);
	}

	public IWorkingSet[] getActiveWorkingSets() {
		return (IWorkingSet[]) fActiveWorkingSets.toArray(new IWorkingSet[fActiveWorkingSets.size()]);
	}

	public Object[] getAllParents(final Object element) {
		if (element instanceof IWorkingSet && fActiveWorkingSets.contains(element))
			return new Object[] { this };
		return fElementMapper.getAllWorkingSets(element).toArray();
	}

	//---- working set management -----------------------------------

	public IWorkingSet[] getAllWorkingSets() {
		final List result = new ArrayList();
		result.addAll(fActiveWorkingSets);
		final IWorkingSet[] locals = fLocalWorkingSetManager.getWorkingSets();
		for (int i = 0; i < locals.length; i++)
			if (!result.contains(locals[i]))
				result.add(locals[i]);
		final IWorkingSet[] globals = PlatformUI.getWorkbench().getWorkingSetManager().getWorkingSets();
		for (int i = 0; i < globals.length; i++)
			if (!result.contains(globals[i]))
				result.add(globals[i]);
		return (IWorkingSet[]) result.toArray(new IWorkingSet[result.size()]);
	}

	public Object[] getChildren(final IWorkingSet workingSet) {
		return workingSet.getElements();
	}

	private IWorkingSet getHistoryWorkingSet() {
		final IWorkingSet[] workingSets = fLocalWorkingSetManager.getWorkingSets();
		for (int i = 0; i < workingSets.length; i++)
			if (HistoryWorkingSetUpdater.ID.equals(workingSets[i].getId()))
				return workingSets[i];
		return null;
	}

	public Object getParent(final Object element) {
		if (element instanceof IWorkingSet && fActiveWorkingSets.contains(element))
			return this;
		return fElementMapper.getFirstWorkingSet(element);
	}

	private boolean isAffected(final PropertyChangeEvent event) {
		if (fActiveWorkingSets == null)
			return false;
		final Object oldValue = event.getOldValue();
		final Object newValue = event.getNewValue();
		if (oldValue != null && fActiveWorkingSets.contains(oldValue) || newValue != null && fActiveWorkingSets.contains(newValue))
			return true;
		return false;
	}

	public boolean needsConfiguration() {
		return !fConfigured && fActiveWorkingSets.size() == 1 && OthersWorkingSetUpdater.ID.equals(((IWorkingSet) fActiveWorkingSets.get(0)).getId());
	}

	/**
	 * Removes the property change listener.
	 * 
	 * @param listener the property change listener to remove
	 */
	public void removePropertyChangeListener(final IPropertyChangeListener listener) {
		fListeners.remove(listener);
	}

	private void restoreState(final IMemento memento) {
		final String configured = memento.getString(TAG_CONFIGURED);
		if (configured != null)
			fConfigured = Boolean.valueOf(configured).booleanValue();
		fLocalWorkingSetManager = PlatformUI.getWorkbench().createLocalWorkingSetManager();
		addListenersToWorkingSetManagers();
		fLocalWorkingSetManager.restoreState(memento.getChild(TAG_LOCAL_WORKING_SET_MANAGER));
		final IWorkingSet history = getHistoryWorkingSet();
		if (history != null)
			fLocalWorkingSetManager.removeWorkingSet(history);
		final IMemento[] actives = memento.getChildren(TAG_ACTIVE_WORKING_SET);
		fActiveWorkingSets = new ArrayList(actives.length);
		for (int i = 0; i < actives.length; i++) {
			final String name = actives[i].getString(TAG_WORKING_SET_NAME);
			if (name != null) {
				IWorkingSet ws = fLocalWorkingSetManager.getWorkingSet(name);
				if (ws == null)
					ws = PlatformUI.getWorkbench().getWorkingSetManager().getWorkingSet(name);
				if (ws != null)
					fActiveWorkingSets.add(ws);
			}
		}
	}

	public void saveState(final IMemento memento) {
		memento.putString(TAG_CONFIGURED, Boolean.toString(fConfigured));
		fLocalWorkingSetManager.saveState(memento.createChild(TAG_LOCAL_WORKING_SET_MANAGER));
		for (final Iterator iter = fActiveWorkingSets.iterator(); iter.hasNext();) {
			final IMemento active = memento.createChild(TAG_ACTIVE_WORKING_SET);
			final IWorkingSet workingSet = (IWorkingSet) iter.next();
			active.putString(TAG_WORKING_SET_NAME, workingSet.getName());
		}
	}

	public void setActiveWorkingSets(final IWorkingSet[] workingSets) {
		fActiveWorkingSets = new ArrayList(Arrays.asList(workingSets));
		fElementMapper.rebuild(getActiveWorkingSets());
		if (fOthersWorkingSetUpdater != null)
			fOthersWorkingSetUpdater.updateElements();
		fireEvent(new PropertyChangeEvent(this, CHANGE_WORKING_SET_MODEL_CONTENT, null, null));
	}

	private void workingSetManagerChanged(final PropertyChangeEvent event) {
		final String property = event.getProperty();
		if (IWorkingSetManager.CHANGE_WORKING_SET_UPDATER_INSTALLED.equals(property) && event.getSource() == fLocalWorkingSetManager) {
			final IWorkingSetUpdater updater = (IWorkingSetUpdater) event.getNewValue();
			if (updater instanceof OthersWorkingSetUpdater)
				fOthersWorkingSetUpdater = (OthersWorkingSetUpdater) updater;
			return;
		}
		// don't handle working sets not managed by the model
		if (!isAffected(event))
			return;

		if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE.equals(property)) {
			final IWorkingSet workingSet = (IWorkingSet) event.getNewValue();
			final IAdaptable[] elements = fElementMapper.refresh(workingSet);
			if (elements != null)
				fireEvent(event);
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property)) {
			final IWorkingSet workingSet = (IWorkingSet) event.getOldValue();
			final List elements = new ArrayList(fActiveWorkingSets);
			elements.remove(workingSet);
			setActiveWorkingSets((IWorkingSet[]) elements.toArray(new IWorkingSet[elements.size()]));
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_NAME_CHANGE.equals(property))
			fireEvent(event);
	}
}