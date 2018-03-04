/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.types;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.php.internal.server.ui.Activator;
import org.eclipse.php.internal.ui.wizards.WizardFragmentsFactoryRegistry;
import org.eclipse.php.server.ui.types.IServerTypeDescriptor;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * Common implementation for server type descriptor.
 */
public class ServerTypeDescriptor implements IServerTypeDescriptor {

	private class Fragment {

		private String id;

		Fragment(String id) {
			super();
			this.id = id;
		}

		String getId() {
			return id;
		}

	}

	protected static final String FRAGMENT_GROUP_ID = "org.eclipse.php.server.ui.serverWizardAndComposite"; //$NON-NLS-1$

	private IConfigurationElement element;
	private String id;
	private String serverTypeId;
	private List<Fragment> wizardFragments;
	private List<Fragment> editorFragments;

	public ServerTypeDescriptor(IConfigurationElement element) {
		construct(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.ui.types.IServerTypeDescriptor#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.ui.types.IServerTypeDescriptor#
	 * getServerTypeId()
	 */
	@Override
	public String getServerTypeId() {
		return serverTypeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.ui.types.IServerTypeDescriptor#getImage
	 * (org .eclipse.php.internal.server.ui.types.IServerTypeDescriptor.ImageType)
	 */
	@Override
	public Image getImage(ImageType type) {
		return getImage(type.getAttribute());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.ui.types.IServerTypeDescriptor#
	 * getImageDescriptor
	 * (org.eclipse.php.internal.server.ui.types.IServerTypeDescriptor .ImageType)
	 */
	@Override
	public ImageDescriptor getImageDescriptor(ImageType type) {
		return getImageDescriptor(type.getAttribute());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.ui.types.IServerTypeDescriptor#
	 * getWizardFragmentFactories()
	 */
	@Override
	public ICompositeFragmentFactory[] getWizardFragmentFactories() {
		Map<String, ICompositeFragmentFactory> factories = WizardFragmentsFactoryRegistry
				.getFragmentsFactories(FRAGMENT_GROUP_ID);
		List<ICompositeFragmentFactory> result = new ArrayList<>();
		for (Fragment fragment : wizardFragments) {
			ICompositeFragmentFactory factory = factories.get(fragment.getId());
			if (factory != null) {
				result.add(factory);
			}
		}
		return result.toArray(new ICompositeFragmentFactory[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.ui.types.IServerTypeDescriptor#
	 * getEditorFragmentFactories()
	 */
	@Override
	public ICompositeFragmentFactory[] getEditorFragmentFactories() {
		Map<String, ICompositeFragmentFactory> factories = WizardFragmentsFactoryRegistry
				.getFragmentsFactories(FRAGMENT_GROUP_ID);
		List<ICompositeFragmentFactory> result = new ArrayList<>();
		for (Fragment fragment : editorFragments) {
			ICompositeFragmentFactory factory = factories.get(fragment.getId());
			if (factory != null) {
				result.add(factory);
			}
		}
		return result.toArray(new ICompositeFragmentFactory[result.size()]);
	}

	private void construct(IConfigurationElement element) {
		this.element = element;
		this.id = element.getAttribute("id"); //$NON-NLS-1$
		this.serverTypeId = element.getAttribute("serverTypeId"); //$NON-NLS-1$
		this.wizardFragments = new ArrayList<>();
		this.editorFragments = new ArrayList<>();
		for (IConfigurationElement child : element.getChildren()) {
			if (child.getName().equals("wizard")) { //$NON-NLS-1$
				for (IConfigurationElement wizardFragment : child.getChildren()) {
					wizardFragments.add(new Fragment(wizardFragment.getAttribute("id"))); //$NON-NLS-1$
				}
			} else if (child.getName().equals("editor")) { //$NON-NLS-1$
				for (IConfigurationElement wizardFragment : child.getChildren()) {
					editorFragments.add(new Fragment(wizardFragment.getAttribute("id"))); //$NON-NLS-1$
				}
			}
		}
	}

	private Image getImage(String name) {
		ImageRegistry regitry = Activator.getDefault().getImageRegistry();
		String id = getId() + name;
		Image image = regitry.get(id);
		if (image == null) {
			ImageDescriptor descriptor = getImageDescriptor(name);
			regitry.put(id, descriptor != null ? descriptor : ImageDescriptor.getMissingImageDescriptor());
			image = regitry.get(id);
		}
		return image;
	}

	private ImageDescriptor getImageDescriptor(String attribute) {
		Bundle bundle = Platform.getBundle(element.getContributor().getName());
		String iconPath = element.getAttribute(attribute);
		if (iconPath != null) {
			URL iconURL = FileLocator.find(bundle, new Path(iconPath), null);
			if (iconURL != null) {
				return ImageDescriptor.createFromURL(iconURL);
			} else { // try to search as a URL in case it is absolute path
				try {
					iconURL = FileLocator.find(new URL(iconPath));
					if (iconURL != null) {
						return ImageDescriptor.createFromURL(iconURL);
					}
				} catch (MalformedURLException e) {
					// return null
				}
			}
		}
		return null;
	}

}
