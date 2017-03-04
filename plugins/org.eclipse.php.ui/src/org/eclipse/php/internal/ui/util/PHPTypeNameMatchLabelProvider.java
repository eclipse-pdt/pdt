/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for {@link TypeNameMatch} instances.
 */
public class PHPTypeNameMatchLabelProvider extends LabelProvider {

	public static final int SHOW_FULLYQUALIFIED = 0x01;
	public static final int SHOW_PACKAGE_POSTFIX = 0x02;
	public static final int SHOW_PACKAGE_ONLY = 0x04;
	public static final int SHOW_ROOT_POSTFIX = 0x08;
	public static final int SHOW_TYPE_ONLY = 0x10;
	public static final int SHOW_TYPE_CONTAINER_ONLY = 0x20;
	public static final int SHOW_POST_QUALIFIED = 0x40;

	private static final Image INTERFACE_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_INTERFACE);
	private static final Image TRAIT_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);
	private static final Image PHPFOLDER_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHPFOLDER_ROOT);
	private static final Image NAMESPACE_ICON = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_NAMESPACE);
	private static final Image CLASS_ICON = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
	private static final Image ANNOTATION_ICON = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_ANNOTATION);

	private final int fFlags;
	private final IDLTKUILanguageToolkit fToolkit;

	/**
	 * Creates the label provider with the specified parameters.
	 * 
	 * @param flags
	 *            bitwise OR of the flags defined in this class
	 * @param toolkit
	 *            language specific UI-toolkit to be used
	 */
	public PHPTypeNameMatchLabelProvider(int flags, IDLTKUILanguageToolkit toolkit) {
		fFlags = flags;
		this.fToolkit = toolkit;
	}

	private boolean isSet(int flag) {
		return (fFlags & flag) != 0;
	}

	private String getPackageName(String packName) {
		if (packName.length() == 0)
			return DLTKUIMessages.TypeInfoLabelProvider_default_package;
		else
			return packName;
	}

	@Override
	public String getText(Object element) {
		if (!(element instanceof TypeNameMatch))
			return super.getText(element);

		TypeNameMatch typeRef = (TypeNameMatch) element;
		StringBuffer buf = new StringBuffer();
		if (isSet(SHOW_TYPE_ONLY)) {
			buf.append(typeRef.getSimpleTypeName());
		} else if (isSet(SHOW_TYPE_CONTAINER_ONLY)) {
			String containerName = typeRef.getTypeContainerName();
			buf.append(getPackageName(containerName));
		} else if (isSet(SHOW_PACKAGE_ONLY)) {
			String packName = typeRef.getPackageName();
			buf.append(getPackageName(packName));
		} else {
			if (isSet(SHOW_FULLYQUALIFIED)) {
				buf.append(typeRef.getFullyQualifiedName());
			} else if (isSet(SHOW_POST_QUALIFIED)) {
				buf.append(typeRef.getSimpleTypeName());
				String containerName = typeRef.getTypeContainerName();
				if (containerName != null && containerName.length() > 0) {
					buf.append(ScriptElementLabels.CONCAT_STRING);
					buf.append(containerName);
				}
			} else {
				buf.append(typeRef.getTypeQualifiedName());
			}

			if (isSet(SHOW_PACKAGE_POSTFIX)) {
				buf.append(ScriptElementLabels.CONCAT_STRING);
				String packName = typeRef.getPackageName();
				buf.append(getPackageName(packName));
			}
		}
		if (isSet(SHOW_ROOT_POSTFIX)) {
			buf.append(ScriptElementLabels.CONCAT_STRING);
			IProjectFragment root = typeRef.getProjectFragment();
			ScriptElementLabels labels = this.fToolkit.getScriptElementLabels();
			labels.getProjectFragmentLabel(root, ScriptElementLabels.ROOT_QUALIFIED, buf);
		}
		return buf.toString();
	}

	@Override
	public Image getImage(Object element) {
		if (!(element instanceof TypeNameMatch))
			return super.getImage(element);

		if (isSet(SHOW_TYPE_CONTAINER_ONLY)) {
			TypeNameMatch typeRef = (TypeNameMatch) element;
			if (typeRef.getPackageName().equals(typeRef.getTypeContainerName()))
				return PHPFOLDER_ICON;

			return NAMESPACE_ICON;

		} else if (isSet(SHOW_PACKAGE_ONLY)) {
			return PHPFOLDER_ICON;
		} else {
			int modifiers = ((TypeNameMatch) element).getModifiers();
			if ((modifiers & Modifiers.AccAnnotation) != 0) {
				return ANNOTATION_ICON;
			} else if (Flags.isInterface(modifiers)) {
				return INTERFACE_ICON;
			} else if (PHPFlags.isTrait(modifiers)) {
				return TRAIT_ICON;
			}
			return CLASS_ICON;
		}
	}
}
