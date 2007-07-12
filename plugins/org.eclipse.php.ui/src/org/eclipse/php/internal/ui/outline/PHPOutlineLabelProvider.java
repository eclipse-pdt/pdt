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
package org.eclipse.php.internal.ui.outline;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.SuperClassLabelProvider;
import org.eclipse.php.internal.ui.outline.PHPOutlineContentProvider.GroupNode;
import org.eclipse.php.internal.ui.treecontent.PHPTreeNode;
import org.eclipse.php.internal.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.sse.core.utils.StringUtils;
import org.eclipse.wst.xml.core.internal.contentmodel.CMAttributeDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDataType;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.ModelQuery;
import org.eclipse.wst.xml.core.internal.modelquery.ModelQueryUtil;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class PHPOutlineLabelProvider extends AppearanceAwareLabelProvider {
	AttributeShowingLabelProvider phpLabelProvider = new AttributeShowingLabelProvider();

	protected ILabelProvider superClassLabelProviderFragment = new SuperClassLabelProvider(this);

	private boolean showAttributes;

	public Image getImage(Object element) {
		if (element instanceof PHPCodeData) {
			Image image = superClassLabelProviderFragment.getImage(element);
			if (image != null) {
				return image;
			}
			return super.getImage(element);
		} else if (element instanceof GroupNode) {
			return ((GroupNode) element).getImage();
		} else if (element instanceof PHPTreeNode) {
			return ((PHPTreeNode) element).getImage();
		}
		return phpLabelProvider.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof PHPCodeData) {
			String text = superClassLabelProviderFragment.getText(element);
			if (text != null) {
				return text;
			}
			return super.getText(element);
		} else if (element instanceof GroupNode) {
			return ((GroupNode) element).getText();
		} else if (element instanceof PHPTreeNode) {
			return ((PHPTreeNode) element).getText();
		}

		return phpLabelProvider.getText(element);

	}

	public String getTooltipText(Object element) {
		return PHPElementLabels.getTooltipTextLabel(element);
	}

	private class AttributeShowingLabelProvider extends JFaceNodeLabelProvider {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object o) {
			StringBuffer text = new StringBuffer(super.getText(o));
			if (o instanceof Node) {
				Node node = (Node) o;
				if (node.getNodeType() == Node.ELEMENT_NODE && showAttributes) {
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=88444
					if (node.hasAttributes()) {
						Element element = (Element) node;
						NamedNodeMap attributes = element.getAttributes();
						Node idTypedAttribute = null;
						//Node requiredAttribute = null;
						ArrayList requiredAttribute = new ArrayList(2);
						boolean hasId = false;
						boolean hasName = false;
//						Node shownAttribute = null;
						ArrayList shownAttribute = new ArrayList(2);

						// try to get content model element
						// declaration
						CMElementDeclaration elementDecl = null;
						ModelQuery mq = ModelQueryUtil.getModelQuery(element.getOwnerDocument());
						if (mq != null) {
							elementDecl = mq.getCMElementDeclaration(element);
						}
						// find an attribute of type (or just named)
						// ID
						if (elementDecl != null) {
							int i = 0;
							while (i < attributes.getLength() && idTypedAttribute == null) {
								Node attr = attributes.item(i);
								String attrName = attr.getNodeName();
								CMAttributeDeclaration attrDecl = (CMAttributeDeclaration) elementDecl.getAttributes().getNamedItem(attrName);
								if (attrDecl != null) {
									if ((attrDecl.getAttrType() != null) && (CMDataType.ID.equals(attrDecl.getAttrType().getDataTypeName()))) {
										idTypedAttribute = attr;
									} else if (attrDecl.getUsage() == CMAttributeDeclaration.REQUIRED) {
										requiredAttribute.add(attr);
									} else if (node.getNodeName().equals("script") && attrName.equalsIgnoreCase("src")) {
										requiredAttribute.add(attr);
									} else {
										hasId = hasId || attrName.equals("id"); //$NON-NLS-1$
										hasName = hasName || attrName.equals("name"); //$NON-NLS-1$
									}
								}
								++i;
							}
						}

						/*
						 * If no suitable attribute was found, try using a
						 * required attribute, if none, then prefer "id" or
						 * "name", otherwise just use first attribute
						 */
						if (idTypedAttribute != null) {
							shownAttribute.add(idTypedAttribute);
						} else if (requiredAttribute.size() > 0) {
							shownAttribute.addAll(requiredAttribute);
						} else if (hasId) {
							shownAttribute.add(attributes.getNamedItem("id")); //$NON-NLS-1$
						} else if (hasName) {
							shownAttribute.add(attributes.getNamedItem("name")); //$NON-NLS-1$
						}
						if (shownAttribute == null) {
							shownAttribute.add(attributes.item(0));
						}

						// display the attribute and value (without quotes)
						for (int i = 0; i < shownAttribute.size(); i++) {
							Node curr = (Node) shownAttribute.get(i);
							String attributeName = curr.getNodeName();
							if (attributeName != null && attributeName.length() > 0) {
								text.append(" " + attributeName); //$NON-NLS-1$
								String attributeValue = curr.getNodeValue();
								if (attributeValue != null && attributeValue.length() > 0) {
									text.append("=" + StringUtils.strip(attributeValue)); //$NON-NLS-1$
								}
							}

						}
					}
				}
			}
			return text.toString();
		}
	}

	public void setShowAttributes(boolean showAttributes) {
		this.showAttributes = showAttributes;
	}
}
