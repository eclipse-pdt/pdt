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
package org.eclipse.php.internal.ui.documentation;

import java.io.*;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProvider;
import org.eclipse.jface.internal.text.html.HTMLPrinter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ConstantDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.DefineMethodUtils;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.hover.PHPDocumentationHover;
import org.eclipse.php.ui.PHPElementLabels;
import org.eclipse.swt.graphics.FontData;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
public class PHPDocumentationProvider implements IScriptDocumentationProvider {

	protected static final String DL_END = "</dl>"; //$NON-NLS-1$
	protected static final String DL_START = "<dl>"; //$NON-NLS-1$

	/**
	 * The style sheet (css).
	 */
	private static String fgStyleSheet;

	private static final long LABEL_FLAGS = ScriptElementLabels.ALL_FULLY_QUALIFIED
			| ScriptElementLabels.M_PRE_RETURNTYPE
			| ScriptElementLabels.M_PARAMETER_TYPES
			| ScriptElementLabels.M_PARAMETER_NAMES
			| ScriptElementLabels.M_EXCEPTIONS
			| ScriptElementLabels.F_PRE_TYPE_SIGNATURE
			| ScriptElementLabels.M_PRE_TYPE_PARAMETERS
			| ScriptElementLabels.T_TYPE_PARAMETERS
			| ScriptElementLabels.USE_RESOLVED;

	private static final long LOCAL_VARIABLE_FLAGS = LABEL_FLAGS
			& ~ScriptElementLabels.F_FULLY_QUALIFIED
			| ScriptElementLabels.F_POST_QUALIFIED;

	public Reader getInfo(IMember element, boolean lookIntoParents,
			boolean lookIntoExternal) {
		if (element instanceof FakeConstructor) {
			IType type = (IType) element.getParent();
			if (type instanceof AliasType) {
				type = (IType) type.getParent();
				element = FakeConstructor.createFakeConstructor(null, type,
						false);
			}
			IMethod[] ctors = FakeConstructor.getConstructors(type, true);
			if (ctors != null && ctors.length == 2) {
				if (ctors[0] != null) {
					element = ctors[0];
				}
			}
		} else if (element instanceof AliasType) {
			element = (IType) element.getParent();
		}
		StringBuffer buffer = new StringBuffer();
		String constantValue = null;
		if (element instanceof IField) {
			try {
				constantValue = getConstantValue((IField) element);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
			if (constantValue != null)
				constantValue = HTMLPrinter.convertToHTMLContent(constantValue);
		}

		HTMLPrinter.addSmallHeader(buffer,
				getInfoText(element, constantValue, true));
		Reader reader = null;
		try {
			reader = getHTMLContent(element);
		} catch (ModelException e) {
		}

		if (reader != null) {
			HTMLPrinter.addParagraph(buffer, reader);
		}

		if (buffer.length() > 0) {
			HTMLPrinter.insertPageProlog(buffer, 0, getStyleSheet());
			HTMLPrinter.addPageEpilog(buffer);

			return new StringReader(buffer.toString());
		}
		return null;
	}

	public Reader getInfo(String keyword) {
		String builtinDoc = BuiltinDoc.getString(keyword);
		if (builtinDoc.length() > 0) {
			StringBuilder buf = new StringBuilder(DL_START);
			buf.append(builtinDoc);
			buf.append(DL_END);
			return new StringReader(buf.toString());
		}
		return null;
	}

	private static StringBuffer getInfoText(IMember member) {
		long flags = member.getElementType() == IModelElement.FIELD ? LOCAL_VARIABLE_FLAGS
				: LABEL_FLAGS;
		String label = PHPElementLabels.getDefault().getElementLabel(member,
				flags);
		return new StringBuffer(label);
	}

	protected static Reader getHTMLContent(IMember curr) throws ModelException {
		String html = PHPDocumentationContentAccess.getHTMLContent(curr);
		if (html != null) {
			return new StringReader(html);
		}
		return null;
	}

	private static String getInfoText(IMember element, String constantValue,
			boolean allowImage) {
		StringBuffer label = getInfoText(element);
		if (element.getElementType() == IModelElement.FIELD) {
			if (constantValue != null) {
				label.append(' ');
				label.append('=');
				label.append(' ');
				label.append(constantValue);
			}
		}

		String imageName = null;
		if (allowImage) {
			URL imageUrl = PHPUiPlugin.getDefault().getImagesOnFSRegistry()
					.getImageURL(element);
			if (imageUrl != null) {
				imageName = imageUrl.toExternalForm();
			}
		}

		StringBuffer buf = new StringBuffer();
		PHPDocumentationHover.addImageAndLabel(buf, imageName, 16, 16, 2, 2,
				label.toString(), 20, 2);
		return buf.toString();
	}

	/**
	 * Returns the Javadoc hover style sheet with the current Javadoc font from
	 * the preferences.
	 * 
	 * @return the updated style sheet
	 */
	protected static String getStyleSheet() {
		if (fgStyleSheet == null)
			fgStyleSheet = loadStyleSheet();
		String css = fgStyleSheet;
		if (css != null) {
			FontData fontData = JFaceResources.getFontRegistry().getFontData(
					PreferenceConstants.APPEARANCE_DOCUMENTATION_FONT)[0];
			css = HTMLPrinter.convertTopLevelFont(css, fontData);
		}

		return css;
	}

	/**
	 * Loads and returns the Javadoc hover style sheet.
	 * 
	 * @return the style sheet, or <code>null</code> if unable to load
	 */
	private static String loadStyleSheet() {
		Bundle bundle = Platform.getBundle(PHPUiPlugin.getPluginId());
		URL styleSheetURL = bundle
				.getEntry("/PHPDocumentationHoverStyleSheet.css"); //$NON-NLS-1$
		if (styleSheetURL != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
						styleSheetURL.openStream()));
				StringBuffer buffer = new StringBuffer(1500);
				String line = reader.readLine();
				while (line != null) {
					buffer.append(line);
					buffer.append('\n');
					line = reader.readLine();
				}
				return buffer.toString();
			} catch (IOException ex) {
				PHPUiPlugin.log(ex);
				return ""; //$NON-NLS-1$
			} finally {
				try {
					if (reader != null)
						reader.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	private String getConstantValue(IField field) throws ModelException {
		if (!isFinal(field)) {
			return null;
		}
		ISourceModule sourceModule = field.getSourceModule();

		ModuleDeclaration module = SourceParserUtil
				.getModuleDeclaration(sourceModule);
		ASTNode node = PHPModelUtils.getNodeByField(module, field);

		if (node == null) {// define constant
			PHPCallExpression callExpression = DefineMethodUtils
					.getDefineNodeByField(module, field);
			if (callExpression != null) {
				CallArgumentsList args = callExpression.getArgs();
				if (args != null && args.getChilds() != null
						&& args.getChilds().size() >= 2) {
					ASTNode argument = (ASTNode) args.getChilds().get(1);
					if (argument instanceof Scalar) {
						String value = ASTUtils.stripQuotes(((Scalar) argument)
								.getValue());
						return value;
					}
				}
			}
		}
		if (!(node instanceof IPHPDocAwareDeclaration)) {
			return null;
		}
		if (node instanceof ConstantDeclaration) {
			ConstantDeclaration constantDeclaration = (ConstantDeclaration) node;
			if (constantDeclaration.getConstantValue() instanceof Scalar) {
				Scalar scalar = (Scalar) constantDeclaration.getConstantValue();
				return scalar.getValue();
			}
		}
		return null;

	}

	private static boolean isFinal(IField field) {
		try {
			return PHPFlags.isFinal(field.getFlags());
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
			return false;
		}
	}
}
