/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import java.util.*;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.IPHPPreferencePageBlock;

/**
 * Registry class for all {@link IPHPPreferencePageBlock} extentions.
 * 
 * @author shalom
 */
public class PHPPreferencePageBlocksRegistry {

	private static final String EXTENSION_POINT_NAME = "phpPreferencePageBlocks"; //$NON-NLS-1$
	private static final String BLOCK_TAG = "block"; //$NON-NLS-1$
	//	private static final String ID_ATTRIBUTE = "id"; 
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PAGE_ID_ATTRIBUTE = "pageId"; //$NON-NLS-1$
	private static final String PDT_PREFIX = "org.eclipse.php."; //$NON-NLS-1$

	/*
	 * PHP preferences addons stored by the ID of the PHP preferences page. This
	 * group of addons are for the worspace scope. Since multiple addons can be
	 * added to the same preferences page, the values are stored as a List.
	 */
	private Dictionary pageBlocks = new Hashtable();

	/** Instance of this registry */
	private static PHPPreferencePageBlocksRegistry instance = null;
	private static Comparator pageBlockComparator = new PageBlockComparator();

	private PHPPreferencePageBlocksRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPUiPlugin.ID,
						EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (BLOCK_TAG.equals(element.getName())) {
				String preferencesPageID = element
						.getAttribute(PAGE_ID_ATTRIBUTE);
				addBlock(pageBlocks, preferencesPageID, element);
			}
		}
	}

	private void addBlock(Dictionary dictionary, String preferencesPageID,
			IConfigurationElement element) {
		List list = (List) dictionary.get(preferencesPageID);
		if (list == null) {
			list = new ArrayList(5);
		}
		list.add(new PHPPreferencePageBlocksFactory(element));
		dictionary.put(preferencesPageID, list);
	}

	private Dictionary getPageBlocks() {
		return pageBlocks;
	}

	private static PHPPreferencePageBlocksRegistry getInstance() {
		if (instance == null) {
			instance = new PHPPreferencePageBlocksRegistry();
		}
		return instance;
	}

	/**
	 * Return PHP preferences page workspace addons according to its ID. The
	 * returned {@link IPHPPreferencePageBlock} is always a new instance.
	 * 
	 * @param preferencesPageID
	 *            The PHP preferences page ID
	 * @return An array of newly instanciated {@link IPHPPreferencePageBlock}s
	 *         (an empty array, if non exists). Note: The returned order of the
	 *         addons is by their ID.
	 */
	public static IPHPPreferencePageBlock[] getPHPPreferencePageBlock(
			String pageId) throws Exception {
		List addonFactories = (List) getInstance().getPageBlocks().get(pageId);
		IPHPPreferencePageBlock[] addons = getBlocks(addonFactories);
		Arrays.sort(addons, pageBlockComparator);
		return addons;
	}

	// Collect, initialize and return all the {@link IPHPPreferencePageBlock}
	// from the given factories List.
	private static IPHPPreferencePageBlock[] getBlocks(List addonFactories) {
		if (addonFactories == null) {
			return new IPHPPreferencePageBlock[0];
		}
		List<IPHPPreferencePageBlock> addons = new LinkedList<IPHPPreferencePageBlock>();
		for (Object addonFactory : addonFactories) {
			IPHPPreferencePageBlock pageBlock = ((PHPPreferencePageBlocksFactory) addonFactory)
					.createPHPPreferencePageBlock();
			if (pageBlock != null) {
				addons.add(pageBlock);
			}
		}
		return (IPHPPreferencePageBlock[]) addons
				.toArray(new IPHPPreferencePageBlock[addons.size()]);
	}

	/**
	 * Instantiation proxy of the PHP preferences page addon object
	 */
	class PHPPreferencePageBlocksFactory {

		private IConfigurationElement element;
		private IPHPPreferencePageBlock preferencesPageBlock;

		public PHPPreferencePageBlocksFactory(IConfigurationElement element) {
			this.element = element;
		}

		public IPHPPreferencePageBlock createPHPPreferencePageBlock() {
			SafeRunner.run(new SafeRunnable(
					PHPUIMessages.PHPPreferencePageBlocksRegistry_0
							+ PHPUiPlugin.ID + "." + EXTENSION_POINT_NAME) { //$NON-NLS-1$
						public void run() throws Exception {
							try {
								preferencesPageBlock = (IPHPPreferencePageBlock) element
										.createExecutableExtension(CLASS_ATTRIBUTE);
								preferencesPageBlock.setComparableName(element
										.getAttribute(NAME_ATTRIBUTE));
							} catch (Exception e) {
								// do nothing
							}
						}
					});
			return preferencesPageBlock;
		}
	}

	static class PageBlockComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			IPHPPreferencePageBlock firstBlock = (IPHPPreferencePageBlock) o1;
			IPHPPreferencePageBlock secondBlock = (IPHPPreferencePageBlock) o2;
			if (firstBlock != null && secondBlock != null) {
				boolean firstIsPDT = firstBlock.getClass().getName()
						.startsWith(PDT_PREFIX);
				boolean secondIsPDT = secondBlock.getClass().getName()
						.startsWith(PDT_PREFIX);
				if (firstIsPDT) {
					if (!secondIsPDT) {
						return -1;
					}
				} else {
					if (secondIsPDT) {
						return 1;
					}
				}
				return firstBlock.getComparableName().compareTo(
						secondBlock.getComparableName());
			}
			if (firstBlock == null) {
				return secondBlock == null ? 0 : -1;
			}
			return 1;
		}
	}
}