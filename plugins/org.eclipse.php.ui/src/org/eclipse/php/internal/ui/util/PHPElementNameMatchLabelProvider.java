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

import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.search.MethodNameMatch;
import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.internal.core.search.DLTKSearchMethodNameMatch;
import org.eclipse.dltk.internal.core.search.DLTKSearchTypeNameMatch;
import org.eclipse.dltk.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.search.FieldNameMatch;
import org.eclipse.php.internal.core.search.IElementNameMatch;
import org.eclipse.php.internal.core.search.PHPSearchMethodNameMatch;
import org.eclipse.php.internal.core.search.PHPSearchTypeNameMatch;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for {@link IElementNameMatch} instances.
 */
public class PHPElementNameMatchLabelProvider extends LabelProvider {

	public static final int SHOW_FULLYQUALIFIED = 0x01;
	public static final int SHOW_PACKAGE_POSTFIX = 0x02;
	public static final int SHOW_PACKAGE_ONLY = 0x04;
	public static final int SHOW_ROOT_POSTFIX = 0x08;
	public static final int SHOW_TYPE_ONLY = 0x10;
	public static final int SHOW_TYPE_CONTAINER_ONLY = 0x20;
	public static final int SHOW_POST_QUALIFIED = 0x40;

	private static final Image PHPFOLDER_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHPFOLDER_ROOT);
	private static final Image NAMESPACE_ICON = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_NAMESPACE);
	private static final Image CLASS_ICON = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
	private static final Image INTERFACE_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_INTERFACE);
	private static final Image TRAIT_ICON = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);
	private static final Image METHOD_ICON = DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PUBLIC);
	private static final Image FIELD_ICON = DLTKPluginImages.get(DLTKPluginImages.IMG_FIELD_PUBLIC);
	private static final Image CONSTANT_ICON = PHPUiPlugin.getImageDescriptorRegistry()
			.get(new PHPElementImageDescriptor(PHPPluginImages.DESC_CONSTANT, PHPElementImageDescriptor.CONSTANT,
					ScriptElementImageProvider.SMALL_SIZE));

	private final int fFlags;

	public PHPElementNameMatchLabelProvider(int flags) {
		fFlags = flags;
	}

	@Override
	public String getText(Object element) {
		element = convert(element);
		if (!(element instanceof IElementNameMatch))
			return super.getText(element);

		IElementNameMatch elementRef = (IElementNameMatch) element;
		StringBuilder buf = new StringBuilder();
		if (isSet(SHOW_TYPE_ONLY)) {
			buf.append(elementRef.getSimpleName());
		} else if (isSet(SHOW_TYPE_CONTAINER_ONLY)) {
			String containerName = elementRef.getContainerName();
			buf.append(getPackageName(containerName));
		} else if (isSet(SHOW_PACKAGE_ONLY)) {
			String packName = elementRef.getPackageName();
			buf.append(getPackageName(packName));
		} else {
			if (isSet(SHOW_FULLYQUALIFIED)) {
				buf.append(elementRef.getFullyQualifiedName());
			} else if (isSet(SHOW_POST_QUALIFIED)) {
				buf.append(elementRef.getSimpleName());
				String containerName = elementRef.getContainerName();
				if (containerName != null && containerName.length() > 0) {
					buf.append(ScriptElementLabels.CONCAT_STRING);
					buf.append(containerName);
				}
			} else {
				buf.append(elementRef.getTypeQualifiedName());
			}

			if (isSet(SHOW_PACKAGE_POSTFIX)) {
				buf.append(ScriptElementLabels.CONCAT_STRING);
				String packName = elementRef.getPackageName();
				buf.append(getPackageName(packName));
			}
		}
		if (isSet(SHOW_ROOT_POSTFIX)) {
			buf.append(ScriptElementLabels.CONCAT_STRING);
			IProjectFragment root = elementRef.getProjectFragment();
			ScriptElementLabels labels = PHPUILanguageToolkit.getInstance().getScriptElementLabels();
			StringBuffer sb = new StringBuffer();
			labels.getProjectFragmentLabel(root, ScriptElementLabels.ROOT_QUALIFIED, sb);
			buf.append(sb);
		}
		return buf.toString();
	}

	private String getPackageName(String packName) {
		if (packName.length() == 0)
			return DLTKUIMessages.TypeInfoLabelProvider_default_package;
		else
			return packName;
	}

	@Override
	public Image getImage(Object element) {
		element = convert(element);
		if (!(element instanceof IElementNameMatch)) {
			return super.getImage(element);
		}

		if (isSet(SHOW_TYPE_CONTAINER_ONLY)) {
			IElementNameMatch typeRef = (IElementNameMatch) element;
			if (typeRef.getPackageName().equals(typeRef.getContainerName())) {
				return PHPFOLDER_ICON;
			}

			return NAMESPACE_ICON;
		} else if (isSet(SHOW_PACKAGE_ONLY)) {
			return PHPFOLDER_ICON;
		} else {
			if (element instanceof TypeNameMatch) {
				int modifiers = ((TypeNameMatch) element).getModifiers();
				if (Flags.isInterface(modifiers)) {
					return INTERFACE_ICON;
				} else if (PHPFlags.isTrait(modifiers)) {
					return TRAIT_ICON;
				}
				return CLASS_ICON;
			} else if (element instanceof MethodNameMatch) {
				return METHOD_ICON;
			} else if (element instanceof FieldNameMatch) {
				int modifiers = ((FieldNameMatch) element).getModifiers();
				if (PHPFlags.isConstant(modifiers)) {
					return CONSTANT_ICON;
				}
				return FIELD_ICON;
			}
		}
		return super.getImage(element);
	}

	private boolean isSet(int flag) {
		return (fFlags & flag) != 0;
	}

	private Object convert(Object element) {
		if (element.getClass() == DLTKSearchTypeNameMatch.class) {
			DLTKSearchTypeNameMatch nameMatch = (DLTKSearchTypeNameMatch) element;
			return new PHPSearchTypeNameMatch(nameMatch.getType(), nameMatch.getModifiers());
		} else if (element.getClass() == DLTKSearchMethodNameMatch.class) {
			DLTKSearchMethodNameMatch nameMatch = (DLTKSearchMethodNameMatch) element;
			return new PHPSearchMethodNameMatch(nameMatch.getMethod(), nameMatch.getModifiers());
		}
		return element;
	}

}
