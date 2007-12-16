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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.text.MessageFormat;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.core.IncludePathContainerInitializer;
import org.eclipse.php.internal.core.project.IIncludePathContainer;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.php.internal.ui.util.PHPElementImageDescriptor;
import org.eclipse.php.internal.ui.util.PHPElementImageProvider;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class IPListLabelProvider extends LabelProvider {

	private String fNewLabel, fClassLabel, fCreateLabel;
	private ImageDescriptor fZipIcon, fExtZipIcon, fJarWSrcIcon, fExtZipWSrcIcon;
	private ImageDescriptor fFolderImage, fProjectImage, fVariableImage, fContainerImage;

	private ImageDescriptorRegistry fRegistry;
	private PHPElementImageProvider imageProvider;

	public IPListLabelProvider() {
		fNewLabel = PHPUIMessages.getString("CPListLabelProvider_new");
		fClassLabel = PHPUIMessages.getString("CPListLabelProvider_container");
		fCreateLabel = PHPUIMessages.getString("CPListLabelProvider_willbecreated");
		fRegistry = PHPUiPlugin.getImageDescriptorRegistry();

		fZipIcon = PHPPluginImages.DESC_OBJS_ZIP;
		fExtZipIcon = PHPPluginImages.DESC_OBJS_EXTZIP;
		imageProvider = new PHPElementImageProvider();
		fFolderImage = imageProvider.getWorkbenchImageDescriptor(((Workspace) ResourcesPlugin.getWorkspace()).newResource(new Path("/dummy/folder"), IResource.FOLDER), 0); //$NON-NLS-1$, flags)
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
				return MessageFormat.format(PHPUIMessages.getString("CPListLabelProvider_non_modifiable_attribute"), new Object[] { text });
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
			name = MessageFormat.format(PHPUIMessages.getString("CPListLabelProvider_systemlibrary"), new Object[] { name });
		}
		return name;
	}

	public String getCPListElementAttributeText(IPListElementAttribute attrib) {
		return PHPUIMessages.getString("CPListLabelProvider_none");
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
				return path.toString().substring(1);
			case IIncludePathEntry.IPE_CONTAINER:
				IIncludePathContainer container = PHPProjectOptions.getIncludePathContainer(path, cpentry.getProject());
				if (container != null) {
					return container.getDescription();
				}
				IncludePathContainerInitializer initializer = PHPProjectOptions.getIncludePathContainerInitializer(path.segment(0));
				if (initializer != null) {
					String description = initializer.getDescription(path, cpentry.getProject());
					return MessageFormat.format(PHPUIMessages.getString("CPListLabelProvider_unbound_library"), new Object[] { description });
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
		return PHPUIMessages.getString("CPListLabelProvider_unknown_element_label");
	}

	private String getPathString(IPath path, boolean isExternal) {
		if (ArchieveFileFilter.isZipPath(path)) {
			IPath appendedPath = path.removeLastSegments(1);
			String appended = isExternal ? appendedPath.toOSString() : appendedPath.makeRelative().toString();
			return MessageFormat.format(PHPUIMessages.getString("CPListLabelProvider_twopart"), new Object[] { path.lastSegment(), appended });
		}
		return isExternal ? path.toOSString() : path.makeRelative().toString();
	}

	private String getVariableString(IPath path) {
		String name = path.makeRelative().toString();
		IPath entryPath = PHPProjectOptions.getIncludePathVariable(path.segment(0));
		if (entryPath != null) {
			String appended = entryPath.append(path.removeFirstSegments(1)).toOSString();
			return MessageFormat.format(PHPUIMessages.getString("CPListLabelProvider_twopart"), new Object[] { name, appended });
		}
		return name;
	}

	private ImageDescriptor getCPListElementBaseImage(IPListElement entry) {
		switch (entry.getEntryKind()) {
			case IIncludePathEntry.IPE_SOURCE:
				if (entry.getPath().segmentCount() == 1) {
					return fProjectImage;
				}
				return fFolderImage;
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_JRE:
				IResource res = entry.getResource();
				if (res == null) {
					if (entry.getContentKind() == IIncludePathEntry.K_BINARY) {
						return fExtZipIcon;
					}
					return fContainerImage;
				} else if (res instanceof IFile) {
					return fZipIcon;
				} else {
					return fFolderImage;
				}
			case IIncludePathEntry.IPE_PROJECT:
				IResource container = entry.getResource();
				if (container != null) {
					if (imageProvider == null) {
						imageProvider = new PHPElementImageProvider();
					}
					return imageProvider.getWorkbenchImageDescriptor(container, 0);
				}
				return null;
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
			return fRegistry.get(fVariableImage);
		} else if (element instanceof IPUserLibraryElement) {
			return fRegistry.get(PHPPluginImages.DESC_OBJS_LIBRARY);
		}
		return null;
	}

}
