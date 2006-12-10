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
package org.eclipse.php.ui.preferences.includepath;

import java.text.MessageFormat;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.IncludePathContainerInitializer;
import org.eclipse.php.core.project.IIncludePathContainer;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.ImageDescriptorRegistry;
import org.eclipse.php.ui.util.PHPElementImageDescriptor;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class IPListLabelProvider extends LabelProvider {

	private String fNewLabel, fClassLabel, fCreateLabel;
	private ImageDescriptor fZipIcon, fExtZipIcon, fJarWSrcIcon, fExtZipWSrcIcon;
	private ImageDescriptor fFolderImage, fProjectImage, fVariableImage, fContainerImage;

	private ImageDescriptorRegistry fRegistry;

	public IPListLabelProvider() {
		fNewLabel = PHPUIMessages.CPListLabelProvider_new;
		fClassLabel = PHPUIMessages.CPListLabelProvider_container;
		fCreateLabel = PHPUIMessages.CPListLabelProvider_willbecreated;
		fRegistry = PHPUiPlugin.getImageDescriptorRegistry();

		fZipIcon = PHPPluginImages.DESC_OBJS_ZIP;
		fExtZipIcon = PHPPluginImages.DESC_OBJS_EXTZIP;
		fFolderImage = PHPPluginImages.DESC_OBJS_PHP_FOLDER;
		fContainerImage = PHPPluginImages.DESC_OBJS_LIBRARY;
		fVariableImage = PHPPluginImages.DESC_OBJS_ENV_VAR;

		IWorkbench workbench = PlatformUI.getWorkbench();

		fProjectImage = workbench.getSharedImages().getImageDescriptor(IDE.SharedImages.IMG_OBJ_PROJECT);
	}

	public String getText(Object element) {
		if (element instanceof IPListElement) {
			return getCPListElementText((IPListElement) element);
		} else if (element instanceof IPListElementAttribute) {
			IPListElementAttribute attribute = (IPListElementAttribute) element;
			String text = getCPListElementAttributeText(attribute);
			if (attribute.isInNonModifiableContainer()) {
				return MessageFormat.format(PHPUIMessages.CPListLabelProvider_non_modifiable_attribute, new String[]{text});
			}
			return text;
		} else if (element instanceof IPUserLibraryElement) {
			return getCPUserLibraryText((IPUserLibraryElement) element);
		}
		return super.getText(element);
	}

	public String getCPUserLibraryText(IPUserLibraryElement element) {
		String name = element.getName();
		if (element.isSystemLibrary()) {
			name = MessageFormat.format(PHPUIMessages.CPListLabelProvider_systemlibrary, new Object[] { name });
		}
		return name;
	}

	public String getCPListElementAttributeText(IPListElementAttribute attrib) {
		String notAvailable = PHPUIMessages.CPListLabelProvider_none;
		String key = attrib.getKey();
		return notAvailable;
	}

	public String getCPListElementText(IPListElement cpentry) {
		IPath path = cpentry.getPath();
		switch (cpentry.getEntryKind()) {
			case IIncludePathEntry.IPE_LIBRARY: {
				IResource resource = cpentry.getResource();
				if (resource instanceof IContainer) {
					StringBuffer buf = new StringBuffer(path.makeRelative().toString());
					buf.append(' ');
					buf.append(fClassLabel);
					if (!resource.exists()) {
						buf.append(' ');
						if (cpentry.isMissing()) {
							buf.append(fCreateLabel);
						} else {
							buf.append(fNewLabel);
						}
					}
					return buf.toString();
				} else if (ArchieveFileFilter.isZipPath(path)) {
					return getPathString(path, resource == null);
				}
				// should not get here - BUT IT DOES! 
				return getPathString(path, resource == null);
			}
			case IIncludePathEntry.IPE_VARIABLE: {
				return getVariableString(path);
			}
			case IIncludePathEntry.IPE_PROJECT:
				return path.lastSegment();
			case IIncludePathEntry.IPE_CONTAINER:
				IIncludePathContainer container = PHPProjectOptions.getIncludePathContainer(path, cpentry.getProject());
				if (container != null) {
					return container.getDescription();
				}
				IncludePathContainerInitializer initializer = PHPProjectOptions.getIncludePathContainerInitializer(path.segment(0));
				if (initializer != null) {
					String description = initializer.getDescription(path, cpentry.getProject());
					return MessageFormat.format(PHPUIMessages.CPListLabelProvider_unbound_library, new Object[] { description });
				}
				return path.toString();
			case IIncludePathEntry.IPE_SOURCE: {
				StringBuffer buf = new StringBuffer(path.makeRelative().toString());
				IResource resource = cpentry.getResource();
				if (resource != null && !resource.exists()) {
					buf.append(' ');
					if (cpentry.isMissing()) {
						buf.append(fCreateLabel);
					} else {
						buf.append(fNewLabel);
					}
				}
				return buf.toString();
			}
			default:
		// pass
		}
		return PHPUIMessages.CPListLabelProvider_unknown_element_label;
	}

	private String getPathString(IPath path, boolean isExternal) {
		if (ArchieveFileFilter.isZipPath(path)) {
			IPath appendedPath = path.removeLastSegments(1);
			String appended = isExternal ? appendedPath.toOSString() : appendedPath.makeRelative().toString();
			return MessageFormat.format(PHPUIMessages.CPListLabelProvider_twopart, new String[] { path.lastSegment(), appended });
		} else {
			return isExternal ? path.toOSString() : path.makeRelative().toString();
		}
	}

	private String getVariableString(IPath path) {
		String name = path.makeRelative().toString();
		IPath entryPath = PHPProjectOptions.getIncludePathVariable(path.segment(0));
		if (entryPath != null) {
			String appended = entryPath.append(path.removeFirstSegments(1)).toOSString();
			return MessageFormat.format(PHPUIMessages.CPListLabelProvider_twopart, new String[] { name, appended });
		} else {
			return name;
		}
	}

	private ImageDescriptor getCPListElementBaseImage(IPListElement cpentry) {
		switch (cpentry.getEntryKind()) {
			case IIncludePathEntry.IPE_SOURCE:
				if (cpentry.getPath().segmentCount() == 1) {
					return fProjectImage;
				} else {
					return fFolderImage;
				}
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_JRE:
				IResource res = cpentry.getResource();
				if (res == null) {
					if(cpentry.getContentKind() == IIncludePathEntry.K_BINARY){
						return fExtZipIcon;
					} else {
						return fContainerImage;
					}
				} else if (res instanceof IFile) {
					return fZipIcon;
				} else {
					return fFolderImage;
				}
			case IIncludePathEntry.IPE_PROJECT:
				return fProjectImage;
			case IIncludePathEntry.IPE_VARIABLE:
				return fVariableImage;
			case IIncludePathEntry.IPE_CONTAINER:
				return fContainerImage;
			default:
				return null;
		}
	}

	public Image getImage(Object element) {
		if (element instanceof IPListElement) {
			IPListElement cpentry = (IPListElement) element;
			ImageDescriptor imageDescriptor = getCPListElementBaseImage(cpentry);
			if (imageDescriptor != null) {
				if (cpentry.isMissing()) {
					imageDescriptor = new PHPElementImageDescriptor(imageDescriptor, PHPElementImageDescriptor.WARNING, PHPElementImageDescriptor.SMALL_SIZE);
				}
				return fRegistry.get(imageDescriptor);
			}
		} else if (element instanceof IPListElementAttribute) {
			String key = ((IPListElementAttribute) element).getKey();

			return fRegistry.get(fVariableImage);
		} else if (element instanceof IPUserLibraryElement) {
			return fRegistry.get(PHPPluginImages.DESC_OBJS_LIBRARY);
		}
		return null;
	}

}
