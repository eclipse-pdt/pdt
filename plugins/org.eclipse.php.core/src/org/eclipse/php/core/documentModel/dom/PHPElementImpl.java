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
import org.eclipse.php.core.phpModel.phpElementData.*;
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

	public PHPElementImpl() {
		super();
	}

	public PHPElementImpl(ElementImpl that) {
		super(that);
	}

	public Node cloneNode(boolean deep) {
		ElementImpl cloned = new PHPElementImpl(this);
		if (deep)
			cloneChildNodes(cloned, deep);
		return cloned;
	}

	protected void setOwnerDocument(Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}

	protected void setTagName(String tagName) {
		super.setTagName(tagName);
	}
	
	//
	// Catch the HTML adapter so HTML syntax checking isn't done for
	// a PHP Region. Not sure that this is a very good way to
	// handle this, but couldn't fix the problem subclassing the 
	// HTML validation code since it is package protected.
	//

	public INodeAdapter getExistingAdapter(Object type) {
		INodeAdapter result = null;

		final String name = ((Class) type).getName();
		if (name.equals("org.eclipse.wst.html.core.internal.validate.ElementPropagatingValidator")) {
			result = pValidator;
			
			// TRICKY: if we are in HTML Validator and we see a php element - just go out and do nothing !!! 
		} else if (name.equals("org.eclipse.wst.sse.core.internal.validate.ValidationAdapter")) {
			result = new HTMLEmptyValidator();
		} else {
			result = super.getExistingAdapter(type);
		}

		return result;
	}

	public PHPCodeData getPHPCodeData(int offset) {
		return getPHPCodeData(this, offset);
	}

	// return php model item  at offset
	public static PHPCodeData getPHPCodeData(NodeImpl node, int offset) {
		String location = node.getModel().getBaseLocation();

		PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(location);
		if (fileData == null)
			return null;

		PHPConstantData[] constants = fileData.getConstants();
		if (constants != null)
			for (int i = 0; i < constants.length; i++)
				if (isInside(offset, constants[i]))
					return constants[i];

		PHPFunctionData[] functions = fileData.getFunctions();
		if (functions != null)
			for (int i = 0; i < functions.length; i++)
				if (isInside(offset, functions[i]))
					return functions[i];

		PHPClassData[] classes = fileData.getClasses();
		if (classes != null)
			for (int i = 0; i < classes.length; i++)
				if (isInside(offset, classes[i])) {
					PHPClassVarData[] vars = classes[i].getVars();
					for (int j = 0; j < vars.length; j++)
						if (isInside(offset, vars[j]))
							return vars[j];
					PHPClassConstData[] consts = classes[i].getConsts();
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

	private static boolean isInside(int offset, PHPCodeData codeData) {
		UserData userData = codeData.getUserData();
		if (userData == null)
			return false;
		if (offset >= userData.getStartPosition() && offset <= userData.getEndPosition())
			return true;
		return false;
	}

	public boolean matchTagName(String tagName) {
		return tagName.equals("?>");
	}

	public boolean isContainer() {
		return true;
	}

	public boolean isJSPContainer() {
		return true;
	}

	public boolean isClosed() {
		IStructuredDocumentRegion flatNode = getEndStructuredDocumentRegion();
		if (flatNode != null) {
			ITextRegionList regions = flatNode.getRegions();
			if (regions != null && regions.size() != 0) {
				ITextRegion region = regions.get(regions.size() - 1);
				String regionType = region.getType();
				if ("PHP_CLOSETAG".equals(regionType))
					return true;
			}
		}
		return super.isClosed();
	}

}
