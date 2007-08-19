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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPKeywordData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPModifier;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.internal.core.phpModel.phpElementData.SimplePHPCodeDataVisitor;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.contentassist.ContentAssistSupport.PHPTagData;
import org.eclipse.php.internal.ui.util.PHPElementImageDescriptor;
import org.eclipse.php.internal.ui.util.PHPElementImageProvider;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class PHPCompletionRendererVisitor extends SimplePHPCodeDataVisitor {

	private String text;
	private Image image;

	protected StringBuffer buffer;

	public PHPCompletionRendererVisitor() {
		buffer = new StringBuffer();
	}

	public void init(CodeData codeData) {
		buffer.setLength(0);
		codeData.accept(this);
		text = buffer.toString();
	}

	public String getDisplayString() {
		return text;
	}

	public Image getImage() {
		return image;
	}

	// ------------------------------------------------------------------------------------------------
	public void visit(CodeData codeData) {
		buffer.append(codeData.getName());
	}

	public void visit(PHPFunctionData codeData) {
		int modifiers = codeData.getModifiers();
		int flags = computeAdornments(modifiers, 0);
		if (PHPModifier.isProtected(modifiers)) {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_MISC_PROTECTED, flags, PHPElementImageProvider.SMALL_SIZE);
		} else if (PHPModifier.isPrivate(modifiers)) {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_MISC_PRIVATE, flags, PHPElementImageProvider.SMALL_SIZE);
		} else {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_MISC_PUBLIC, flags, PHPElementImageProvider.SMALL_SIZE);
		}
		buffer.append(codeData.getName());

		buffer.append("(");
		PHPFunctionData.PHPFunctionParameter[] parameters = codeData.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			if (i != 0) {
				buffer.append(", ");
			}
			visit(parameters[i]);
		}
		buffer.append(")");

		String rt = codeData.getReturnType().trim();
		rt = (rt.split(" ", 2))[0];

		// we must have a space before the return vlaue;
		rt = "  " + rt;

		buffer.append(rt);
	}

	public void visit(PHPTagData codeData) {
		image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_KEYWORD);
		buffer.append("php");
	}

	public void visit(PHPClassVarData codeData) {
		int modifiers = codeData.getModifiers();
		int flags = computeAdornments(modifiers, 0);
		if (PHPModifier.isProtected(modifiers)) {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_FIELD_PROTECTED, flags, PHPElementImageProvider.SMALL_SIZE);
		} else if (PHPModifier.isPrivate(modifiers)) {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_FIELD_PRIVATE, flags, PHPElementImageProvider.SMALL_SIZE);
		} else {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_FIELD_PUBLIC, flags, PHPElementImageProvider.SMALL_SIZE);
		}
		super.visit(codeData);
	}

	public void visit(PHPClassConstData codeData) {
		image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_CONSTANT, PHPElementImageDescriptor.CONSTANT, PHPElementImageProvider.SMALL_SIZE);
		super.visit(codeData);
	}

	public void visit(PHPConstantData codeData) {
		image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_CONSTANT, PHPElementImageDescriptor.CONSTANT, PHPElementImageProvider.SMALL_SIZE);
		super.visit(codeData);
	}

	public void visit(PHPClassData codeData) {
		int modifiers = codeData.getModifiers();
		int flags = computeAdornments(modifiers, 0);
		if (PHPModifier.isInterface(modifiers)) {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_OBJS_INTERFACE, flags, PHPElementImageProvider.SMALL_SIZE);
		} else {
			image = PHPElementImageProvider.getDecoratedImage(PHPPluginImages.DESC_OBJS_CLASS, flags, PHPElementImageProvider.SMALL_SIZE);
		}
		super.visit(codeData);
	}

	public void visit(PHPKeywordData codeData) {
		image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_KEYWORD);
		super.visit(codeData);
	}

	public void visit(PHPVariableData codeData) {
		image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_FIELD_PUBLIC);
		super.visit(codeData);
	}

	public void visit(PHPFunctionData.PHPFunctionParameter codeData) {
		//image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_MISC_PUBLIC);

		String classType = codeData.getClassType();
		if (classType != null && classType.length() != 0) {
			buffer.append(classType).append(" ");
		}
		if (codeData.isConst()) {
			buffer.append("const ");
		}
		if (codeData.isReference()) {
			buffer.append("&");
		}
		// add the $ sign only in case of the parameter is with prefix (e.g. const)
		// fixed bug 196114
		if (buffer.length() > 0) {
			buffer.append("$");
		}
		buffer.append(codeData.getName());
		String defaultValue = codeData.getDefaultValue();
		if (defaultValue != null && defaultValue.length() != 0) {
			buffer.append(" = ").append(defaultValue);
		}

	}

	// Compute PHP Adornment Flags from the given PHPModifier value
	private int computeAdornments(int modifiers, int flags) {
		if (PHPModifier.isAbstract(modifiers)) {
			flags |= PHPElementImageDescriptor.ABSTRACT;
		}
		if (PHPModifier.isFinal(modifiers)) {
			flags |= PHPElementImageDescriptor.FINAL;
		}
		if (PHPModifier.isStatic(modifiers)) {
			flags |= PHPElementImageDescriptor.STATIC;
		}
		return flags;
	}

}
