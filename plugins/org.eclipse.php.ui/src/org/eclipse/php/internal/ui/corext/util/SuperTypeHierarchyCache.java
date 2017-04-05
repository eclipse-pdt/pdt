/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.util;

import java.util.*;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ITypeHierarchy;
import org.eclipse.dltk.core.ITypeHierarchyChangedListener;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.hierarchy.TypeHierarchy;
import org.eclipse.dltk.internal.core.util.MethodOverrideTester;
import org.eclipse.php.core.PHPToolkitUtil;

/**
 * TODO : should be in the DLTK ui
 * 
 * @author roy
 * 
 */
public class SuperTypeHierarchyCache {

	private static class FakeTypeHierarchy extends TypeHierarchy {
		public FakeTypeHierarchy() {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=494388
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=498339
			// We must initialize the internal properties to avoid NPEs
			// when using this class methods.
			initialize(1);
		}
	}

	private static class HierarchyCacheEntry implements ITypeHierarchyChangedListener {

		private ITypeHierarchy fTypeHierarchy;
		private long fLastAccess;

		public HierarchyCacheEntry(ITypeHierarchy hierarchy) {
			fTypeHierarchy = hierarchy;
			fTypeHierarchy.addTypeHierarchyChangedListener(this);
			markAsAccessed();
		}

		@Override
		public void typeHierarchyChanged(ITypeHierarchy typeHierarchy) {
			removeHierarchyEntryFromCache(this);
		}

		public ITypeHierarchy getTypeHierarchy() {
			return fTypeHierarchy;
		}

		public void markAsAccessed() {
			fLastAccess = System.currentTimeMillis();
		}

		public long getLastAccess() {
			return fLastAccess;
		}

		public void dispose() {
			fTypeHierarchy.removeTypeHierarchyChangedListener(this);
			fTypeHierarchy = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Super hierarchy of: " + fTypeHierarchy.getType().getElementName(); //$NON-NLS-1$
		}

	}

	private static final int CACHE_SIZE = 8;

	private static List<HierarchyCacheEntry> fgHierarchyCache = new ArrayList<>(CACHE_SIZE);
	private static Map<IType, MethodOverrideTester> fgMethodOverrideTesterCache = new LRUMap(CACHE_SIZE);

	private static int fgCacheHits = 0;
	private static int fgCacheMisses = 0;

	/**
	 * Get a hierarchy for the given type
	 */
	public static ITypeHierarchy getTypeHierarchy(IType type) throws ModelException {
		return getTypeHierarchy(type, null);
	}

	public static MethodOverrideTester getMethodOverrideTester(IType type) throws ModelException {
		MethodOverrideTester test = null;
		synchronized (fgMethodOverrideTesterCache) {
			test = fgMethodOverrideTesterCache.get(type);
		}
		if (test == null) {
			ITypeHierarchy hierarchy = getTypeHierarchy(type); // don't nest the
			// locks
			synchronized (fgMethodOverrideTesterCache) {
				test = fgMethodOverrideTesterCache.get(type); // test
																// again
																// after
																// waiting
																// a
																// long
																// time
																// for
				// 'getTypeHierarchy'
				if (test == null) {
					test = new MethodOverrideTester(type, hierarchy);
					fgMethodOverrideTesterCache.put(type, test);
				}
			}
		}
		return test;
	}

	private static void removeMethodOverrideTester(ITypeHierarchy hierarchy) {
		synchronized (fgMethodOverrideTesterCache) {
			for (Iterator<MethodOverrideTester> iter = fgMethodOverrideTesterCache.values().iterator(); iter
					.hasNext();) {
				MethodOverrideTester curr = iter.next();
				if (curr != null && curr.getTypeHierarchy() != null && curr.getTypeHierarchy().equals(hierarchy)) {
					iter.remove();
				}
			}
		}
	}

	/**
	 * Get a hierarchy for the given type
	 * 
	 * @throws ModelException
	 */
	public static ITypeHierarchy getTypeHierarchy(IType type, IProgressMonitor progressMonitor) throws ModelException {
		if (type == null || !type.exists() || !PHPToolkitUtil.isFromPHPProject(type)) {
			return new FakeTypeHierarchy();
		}
		ITypeHierarchy hierarchy = findTypeHierarchyInCache(type);
		if (hierarchy == null) {
			fgCacheMisses++;
			hierarchy = type.newSupertypeHierarchy(progressMonitor);
			addTypeHierarchyToCache(hierarchy);
		} else {
			fgCacheHits++;
		}
		return hierarchy;
	}

	private static void addTypeHierarchyToCache(ITypeHierarchy hierarchy) {
		synchronized (fgHierarchyCache) {
			int nEntries = fgHierarchyCache.size();
			if (nEntries >= CACHE_SIZE) {
				// find obsolete entries or remove entry that was least recently
				// accessed
				HierarchyCacheEntry oldest = null;
				List<HierarchyCacheEntry> obsoleteHierarchies = new ArrayList<>(CACHE_SIZE);
				for (int i = 0; i < nEntries; i++) {
					HierarchyCacheEntry entry = fgHierarchyCache.get(i);
					ITypeHierarchy curr = entry.getTypeHierarchy();
					if (!curr.exists() || hierarchy.contains(curr.getType())) {
						obsoleteHierarchies.add(entry);
					} else {
						if (oldest == null || entry.getLastAccess() < oldest.getLastAccess()) {
							oldest = entry;
						}
					}
				}
				if (!obsoleteHierarchies.isEmpty()) {
					for (int i = 0; i < obsoleteHierarchies.size(); i++) {
						removeHierarchyEntryFromCache(obsoleteHierarchies.get(i));
					}
				} else if (oldest != null) {
					removeHierarchyEntryFromCache(oldest);
				}
			}
			HierarchyCacheEntry newEntry = new HierarchyCacheEntry(hierarchy);
			fgHierarchyCache.add(newEntry);
		}
	}

	/**
	 * Check if the given type is in the hierarchy
	 * 
	 * @param type
	 * @return Return <code>true</code> if a hierarchy for the given type is
	 *         cached.
	 */
	public static boolean hasInCache(IType type) {
		return findTypeHierarchyInCache(type) != null;
	}

	private static ITypeHierarchy findTypeHierarchyInCache(IType type) {
		if (type != null) {
			synchronized (fgHierarchyCache) {
				for (int i = fgHierarchyCache.size() - 1; i >= 0; i--) {
					HierarchyCacheEntry curr = fgHierarchyCache.get(i);
					ITypeHierarchy hierarchy = curr.getTypeHierarchy();
					if (!hierarchy.exists()) {
						removeHierarchyEntryFromCache(curr);
					} else {
						if (hierarchy.contains(type)) {
							curr.markAsAccessed();
							return hierarchy;
						}
					}
				}
			}
		}
		return null;
	}

	private static void removeHierarchyEntryFromCache(HierarchyCacheEntry entry) {
		synchronized (fgHierarchyCache) {
			removeMethodOverrideTester(entry.getTypeHierarchy());
			entry.dispose();
			fgHierarchyCache.remove(entry);
		}
	}

	/**
	 * Gets the number of times the hierarchy could be taken from the hierarchy.
	 * 
	 * @return Returns a int
	 */
	public static int getCacheHits() {
		return fgCacheHits;
	}

	/**
	 * Gets the number of times the hierarchy was build. Used for testing.
	 * 
	 * @return Returns a int
	 */
	public static int getCacheMisses() {
		return fgCacheMisses;
	}

	/**
	 * 
	 */
	public static class LRUMap extends LinkedHashMap<IType, MethodOverrideTester> {

		private static final long serialVersionUID = 1L;
		private final int fMaxSize;

		public LRUMap(int maxSize) {
			super(maxSize, 0.75f, true);
			fMaxSize = maxSize;
		}

		@Override
		protected boolean removeEldestEntry(java.util.Map.Entry<IType, MethodOverrideTester> eldest) {
			return size() > fMaxSize;
		}
	}

}
