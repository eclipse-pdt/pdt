/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.*;
import org.eclipse.dltk.ui.*;
import org.eclipse.dltk.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;

public class TempBPListLabelProvider extends LabelProvider {

	private String fNewLabel, fClassLabel, fCreateLabel;

	private ImageDescriptorRegistry fRegistry;

	private ImageDescriptor fProjectImage;

	public TempBPListLabelProvider() {

		fNewLabel = NewWizardMessages.CPListLabelProvider_new;
		fClassLabel = NewWizardMessages.CPListLabelProvider_classcontainer;
		fCreateLabel = NewWizardMessages.CPListLabelProvider_willbecreated;
		fRegistry = DLTKUIPlugin.getImageDescriptorRegistry();

		IWorkbench workbench = DLTKUIPlugin.getDefault().getWorkbench();

		fProjectImage = workbench.getSharedImages().getImageDescriptor(
				IDE.SharedImages.IMG_OBJ_PROJECT);
	}

	public String getText(Object element) {

		if (element instanceof BPListElement) {
			return getCPListElementText((BPListElement) element);
		} else if (element instanceof BPListElementAttribute) {
			BPListElementAttribute attribute = (BPListElementAttribute) element;
			String text = getCPListElementAttributeText(attribute);
			if (attribute.isInNonModifiableContainer()) {
				return Messages
						.format(
								NewWizardMessages.CPListLabelProvider_non_modifiable_attribute,
								text);
			}
			return text;
		} else if (element instanceof BPUserLibraryElement) {
			return getCPUserLibraryText((BPUserLibraryElement) element);
		} else if (element instanceof IAccessRule) {
			IAccessRule rule = (IAccessRule) element;
			return Messages
					.format(
							NewWizardMessages.CPListLabelProvider_access_rules_label,
							new String[] {
									AccessRulesLabelProvider
											.getResolutionLabel(rule.getKind()),
									rule.getPattern().toString() });
		}
		return super.getText(element);
	}

	public String getCPUserLibraryText(BPUserLibraryElement element) {

		String name = element.getName();
		if (element.isSystemLibrary()) {
			name = Messages.format(
					NewWizardMessages.CPListLabelProvider_systemlibrary, name);
		}
		return name;
	}

	public String getCPListElementAttributeText(BPListElementAttribute attrib) {

		String notAvailable = NewWizardMessages.CPListLabelProvider_none;
		String key = attrib.getKey();
		if (key.equals(BPListElement.EXCLUSION)) {
			String arg = null;
			IPath[] patterns = (IPath[]) attrib.getValue();
			if (patterns != null && patterns.length > 0) {
				int patternsCount = 0;
				StringBuffer buf = new StringBuffer();
				for (int i = 0; i < patterns.length; i++) {
					String pattern = patterns[i].toString();
					if (pattern.length() > 0) {
						if (patternsCount > 0) {
							buf
									.append(NewWizardMessages.CPListLabelProvider_exclusion_filter_separator);
						}
						buf.append(pattern);
						patternsCount++;
					}
				}
				if (patternsCount > 0) {
					arg = buf.toString();
				} else {
					arg = notAvailable;
				}
			} else {
				arg = notAvailable;
			}
			return Messages
					.format(
							NewWizardMessages.CPListLabelProvider_exclusion_filter_label,
							new String[] { arg });
		} else if (key.equals(BPListElement.INCLUSION)) {
			String arg = null;
			IPath[] patterns = (IPath[]) attrib.getValue();
			if (patterns != null && patterns.length > 0) {
				int patternsCount = 0;
				StringBuffer buf = new StringBuffer();
				for (int i = 0; i < patterns.length; i++) {
					String pattern = patterns[i].toString();
					if (pattern.length() > 0) {
						if (patternsCount > 0) {
							buf
									.append(NewWizardMessages.CPListLabelProvider_inclusion_filter_separator);
						}
						buf.append(pattern);
						patternsCount++;
					}
				}
				if (patternsCount > 0) {
					arg = buf.toString();
				} else {
					arg = notAvailable;
				}
			} else {
				arg = NewWizardMessages.CPListLabelProvider_all;
			}
			return Messages
					.format(
							NewWizardMessages.CPListLabelProvider_inclusion_filter_label,
							new String[] { arg });
		} else if (key.equals(BPListElement.ACCESSRULES)) {
			IAccessRule[] rules = (IAccessRule[]) attrib.getValue();
			int nRules = rules != null ? rules.length : 0;

			int parentKind = attrib.getParent().getEntryKind();
			if (parentKind == IBuildpathEntry.BPE_PROJECT) {
				Boolean combined = (Boolean) attrib.getParent().getAttribute(
						BPListElement.COMBINE_ACCESSRULES);
				if (nRules > 0) {
					if (combined.booleanValue()) {
						return Messages
								.format(
										NewWizardMessages.CPListLabelProvider_project_access_rules_combined,
										String.valueOf(nRules));
					} else {
						return Messages
								.format(
										NewWizardMessages.CPListLabelProvider_project_access_rules_not_combined,
										String.valueOf(nRules));
					}
				} else {
					return NewWizardMessages.CPListLabelProvider_project_access_rules_no_rules;
				}
			} else if (parentKind == IBuildpathEntry.BPE_CONTAINER) {
				if (nRules > 0) {
					return Messages
							.format(
									NewWizardMessages.CPListLabelProvider_container_access_rules,
									String.valueOf(nRules));
				} else {
					return NewWizardMessages.CPListLabelProvider_container_no_access_rules;
				}
			} else {
				if (nRules > 0) {
					return Messages
							.format(
									NewWizardMessages.CPListLabelProvider_access_rules_enabled,
									String.valueOf(nRules));
				} else {
					return NewWizardMessages.CPListLabelProvider_access_rules_disabled;
				}
			}
		}
		if (DLTKCore.DEBUG) {
			System.err.println("Add native library support"); //$NON-NLS-1$
		}
		// } else if (key.equals(CPListElement.NATIVE_LIB_PATH)) {
		// String arg= (String) attrib.getValue();
		// if (arg == null) {
		// arg= notAvailable;
		// }
		// return
		// Messages.format(NewWizardMessages.
		// CPListLabelProvider_native_library_path,
		// new String[] { arg });
		// }
		return notAvailable;
	}

	public String getCPListElementText(BPListElement cpentry) {

		IPath path = cpentry.getPath();
		if (path.toString().startsWith(
				IBuildpathEntry.BUILTIN_EXTERNAL_ENTRY_STR)) {
			return ScriptElementLabels.BUILTINS_FRAGMENT;
		}
		if (EnvironmentPathUtils.isFull(path)) {
			path = EnvironmentPathUtils.getLocalPath(path);
		}
		switch (cpentry.getEntryKind()) {
		case IBuildpathEntry.BPE_LIBRARY: {
			IResource resource = cpentry.getResource();
			if (resource instanceof IContainer) {
				StringBuffer buf = new StringBuffer(path.makeRelative()
						.toString());
				IPath linkTarget = cpentry.getLinkTarget();
				if (linkTarget != null) {
					buf.append(ScriptElementLabels.CONCAT_STRING);
					buf.append(linkTarget.toOSString());
				}
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
			} else if (ArchiveFileFilter.isArchivePath(path)) {
				return getPathString(path, resource == null);
			}
			// should not get here
			if (!cpentry.isExternalFolder()) {
				return path.makeRelative().toString();
			} else {
				return path.toString();
			}
		}
		case IBuildpathEntry.BPE_PROJECT:
			return path.lastSegment();
		case IBuildpathEntry.BPE_CONTAINER:
			try {
				IBuildpathContainer container = DLTKCore.getBuildpathContainer(
						path, cpentry.getScriptProject());
				if (container != null) {
					return container.getDescription(cpentry.getScriptProject());
				}
				BuildpathContainerInitializer initializer = DLTKCore
						.getBuildpathContainerInitializer(path.segment(0));
				if (initializer != null) {
					String description = initializer.getDescription(path,
							cpentry.getScriptProject());
					return Messages
							.format(
									NewWizardMessages.CPListLabelProvider_unbound_library,
									description);
				}
			} catch (ModelException e) {

			}
			return path.toString();
		case IBuildpathEntry.BPE_SOURCE: {
			StringBuffer buf = new StringBuffer(path.makeRelative().toString());
			IPath linkTarget = cpentry.getLinkTarget();
			if (linkTarget != null) {
				buf.append(ScriptElementLabels.CONCAT_STRING);
				buf.append(linkTarget.toOSString());
			}
			IResource resource = cpentry.getResource();
			if (resource != null && !resource.exists()) {
				buf.append(' ');
				if (cpentry.isMissing()) {
					buf.append(fCreateLabel);
				} else {
					buf.append(fNewLabel);
				}
			} else if (cpentry.getOrginalPath() == null) {
				buf.append(' ');
				buf.append(fNewLabel);
			}
			return buf.toString();
		}
		default:
			// pass
		}
		return NewWizardMessages.CPListLabelProvider_unknown_element_label;
	}

	private String getPathString(IPath path, boolean isExternal) {

		if (ArchiveFileFilter.isArchivePath(path)) {
			IPath appendedPath = path.removeLastSegments(1);
			String appended = isExternal ? appendedPath.toOSString()
					: appendedPath.makeRelative().toString();
			return Messages.format(
					NewWizardMessages.CPListLabelProvider_twopart,
					new String[] { path.lastSegment(), appended });
		} else {
			return isExternal ? path.toOSString() : path.makeRelative()
					.toString();
		}
	}

	protected ImageDescriptor getCPListElementBaseImage(BPListElement cpentry) {

		switch (cpentry.getEntryKind()) {
		case IBuildpathEntry.BPE_SOURCE:
			if (cpentry.getPath().segmentCount() == 1) {
				return fProjectImage;
			} else {
				return DLTKPluginImages
						.getDescriptor(DLTKPluginImages.IMG_OBJS_PACKFRAG_ROOT);
			}
		case IBuildpathEntry.BPE_LIBRARY:
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_EXTZIP_WSRC);
		case IBuildpathEntry.BPE_PROJECT:
			return fProjectImage;
		case IBuildpathEntry.BPE_CONTAINER:
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_LIBRARY);
		default:
			return null;
		}
	}

	public Image getImage(Object element) {

		if (element instanceof BPListElement) {
			BPListElement cpentry = (BPListElement) element;
			ImageDescriptor imageDescriptor = getCPListElementBaseImage(cpentry);
			if (imageDescriptor != null) {
				if (cpentry.isMissing()) {
					imageDescriptor = new ScriptElementImageDescriptor(
							imageDescriptor,
							ScriptElementImageDescriptor.WARNING,
							ScriptElementImageProvider.SMALL_SIZE);
				}
				return fRegistry.get(imageDescriptor);
			}
		} else if (element instanceof BPListElementAttribute) {
			String key = ((BPListElementAttribute) element).getKey();
			if (key.equals(BPListElement.EXCLUSION)) {
				return fRegistry
						.get(DLTKPluginImages.DESC_OBJS_EXCLUSION_FILTER_ATTRIB);
			} else if (key.equals(BPListElement.INCLUSION)) {
				return fRegistry
						.get(DLTKPluginImages.DESC_OBJS_INCLUSION_FILTER_ATTRIB);
			} else if (key.equals(BPListElement.ACCESSRULES)) {
				return fRegistry
						.get(DLTKPluginImages.DESC_OBJS_ACCESSRULES_ATTRIB);
			}
			// else if (key.equals(CPListElement.NATIVE_LIB_PATH)) {
			// return
			// fRegistry.get(DLTKPluginImages.DESC_OBJS_NATIVE_LIB_PATH_ATTRIB);
			// }
			return null;
		} else if (element instanceof BPUserLibraryElement) {
			return DLTKPluginImages.getDescriptor(
					DLTKPluginImages.IMG_OBJS_LIBRARY).createImage();
		} else if (element instanceof IAccessRule) {
			IAccessRule rule = (IAccessRule) element;
			return AccessRulesLabelProvider.getResolutionImage(rule.getKind());
		}
		return null;
	}
}
