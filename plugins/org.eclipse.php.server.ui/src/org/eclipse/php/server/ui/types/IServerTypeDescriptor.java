/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.server.ui.types;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.graphics.Image;

/**
 * Interface for server type descriptors that provides UI presentation model and
 * factories for creating server wizard & edit dialogs.
 */
public interface IServerTypeDescriptor {

	/**
	 * Image type.
	 */
	public enum ImageType {

		/**
		 * 16x16 icon for server type descriptor.
		 */
		ICON_16("icon16"), //$NON-NLS-1$
		/**
		 * 32x32 icon for server type descriptor.
		 */
		ICON_32("icon32"), //$NON-NLS-1$
		/**
		 * Wizard banner icon for server type descriptor.
		 */
		WIZARD("iconWizard"); //$NON-NLS-1$

		private String attribute;

		public String getAttribute() {
			return attribute;
		}

		private ImageType(String attribute) {
			this.attribute = attribute;
		}

	}

	/**
	 * Returns server type descriptor unique ID.
	 * 
	 * @return server type descriptor unique ID
	 */
	String getId();

	/**
	 * Returns corresponding server type unique ID.
	 * 
	 * @return corresponding server type unique ID
	 */
	String getServerTypeId();

	/**
	 * Returns image for provided image type.
	 * 
	 * @param type
	 * @return image
	 */
	Image getImage(ImageType type);

	/**
	 * Returns image descriptor for provided image type.
	 * 
	 * @param type
	 * @return image descriptor
	 */
	ImageDescriptor getImageDescriptor(ImageType type);

	/**
	 * Gets and returns wizard fragment factories that are registered for this
	 * server type descriptor.
	 * 
	 * @return registered wizard fragment factories
	 */
	public ICompositeFragmentFactory[] getWizardFragmentFactories();

	/**
	 * Gets and returns editor fragment factories that are registered for this
	 * server type descriptor.
	 * 
	 * @return registered editor fragment factories
	 */
	public ICompositeFragmentFactory[] getEditorFragmentFactories();

}
