/*******************************************************************************
 * Copyright (c) 2020 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.adapters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.documentModel.dom.ElementImplForPHP;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Document;

public class PHPElementResourceAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		ElementImplForPHP element = (ElementImplForPHP) adaptableObject;
		if (adapterType == IResource.class || adapterType == IFile.class) {
			IModelElement modelElement = element.getModelElement();
			if (modelElement != null) {
				IModelElement ancestor = modelElement.getAncestor(IModelElement.SOURCE_MODULE);
				if (ancestor != null) {
					return (T) ancestor.getResource();
				}
			}
			Document document = element.getOwnerDocument();
			if (document instanceof IDOMNode) {
				IDOMModel model = ((IDOMNode) document).getModel();
				if (model != null && model.getBaseLocation() != null) {
					return (T) ResourcesPlugin.getWorkspace().getRoot()
							.getFile(Path.fromPortableString(model.getBaseLocation()));
				}
			}
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IResource.class, IFile.class };
	}

}
