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
package org.eclipse.php.core.documentModel.dom;

import org.eclipse.php.core.documentModel.validate.HTMLElementPropagatingValidator;
import org.eclipse.php.core.documentModel.validate.HTMLEmptyValidator;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.xml.core.internal.document.ElementImpl;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class PHPElementImpl extends ElementImpl {

	static HTMLElementPropagatingValidator pValidator = new HTMLElementPropagatingValidator();

	// return php model item  at offset
	public static PHPCodeData getPHPCodeData(final NodeImpl node, final int offset) {
		final String location = node.getModel().getBaseLocation();

		final PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(location);
		if (fileData == null)
			return null;

		final PHPConstantData[] constants = fileData.getConstants();
		if (constants != null)
			for (int i = 0; i < constants.length; i++)
				if (isInside(offset, constants[i]))
					return constants[i];

		PHPFunctionData[] functions = fileData.getFunctions();
		if (functions != null)
			for (int i = 0; i < functions.length; i++)
				if (isInside(offset, functions[i]))
					return functions[i];

		final PHPClassData[] classes = fileData.getClasses();
		if (classes != null)
			for (int i = 0; i < classes.length; i++)
				if (isInside(offset, classes[i])) {
					final PHPClassVarData[] vars = classes[i].getVars();
					for (int j = 0; j < vars.length; j++)
						if (isInside(offset, vars[j]))
							return vars[j];
					final PHPClassConstData[] consts = classes[i].getConsts();
					for (int j = 0; j < consts.length; j++)
						if (isInside(offset, consts[j]))
							return consts[j];
					functions = classes[i].getFunctions();
					for (int j = 0; j < functions.length; j++)
						if (isInside(offset, functions[j]))
							return functions[j];
					return classes[i];
				}
		return fileData;

	}

	private static boolean isInside(final int offset, final PHPCodeData codeData) {
		final UserData userData = codeData.getUserData();
		if (userData == null)
			return false;
		if (offset >= userData.getStartPosition() && offset <= userData.getEndPosition())
			return true;
		return false;
	}

	public PHPElementImpl() {
		super();
	}

	public PHPElementImpl(final ElementImpl that) {
		super(that);
	}

	public Node cloneNode(final boolean deep) {
		final ElementImpl cloned = new PHPElementImpl(this);
		if (deep)
			cloneChildNodes(cloned, deep);
		return cloned;
	}

	//
	// Catch the HTML adapter so HTML syntax checking isn't done for
	// a PHP Region. Not sure that this is a very good way to
	// handle this, but couldn't fix the problem subclassing the 
	// HTML validation code since it is package protected.
	//

	public INodeAdapter getExistingAdapter(final Object type) {
		INodeAdapter result = null;

		final String name = ((Class) type).getName();
		if (name.equals("org.eclipse.wst.html.core.internal.validate.ElementPropagatingValidator"))
			result = pValidator;
		else if (name.equals("org.eclipse.wst.sse.core.internal.validate.ValidationAdapter"))
			result = new HTMLEmptyValidator();
		else
			result = super.getExistingAdapter(type);

		return result;
	}

	public PHPCodeData getPHPCodeData(final int offset) {
		return getPHPCodeData(this, offset);
	}

	public boolean isClosed() {
		final IStructuredDocumentRegion flatNode = getEndStructuredDocumentRegion();
		if (flatNode != null) {
			final ITextRegionList regions = flatNode.getRegions();
			if (regions != null && regions.size() != 0) {
				final ITextRegion region = regions.get(regions.size() - 1);
				final String regionType = region.getType();
				if ("PHP_CLOSETAG".equals(regionType))
					return true;
			}
		}
		return super.isClosed();
	}

	public boolean isContainer() {
		return true;
	}

	public boolean isJSPContainer() {
		return true;
	}

	public boolean matchTagName(final String tagName) {
		return tagName.equals("?>");
	}

	protected void setOwnerDocument(final Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}

	protected void setTagName(final String tagName) {
		super.setTagName(tagName);
	}

}
