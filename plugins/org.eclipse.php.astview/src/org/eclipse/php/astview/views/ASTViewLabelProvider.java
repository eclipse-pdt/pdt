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

package org.eclipse.php.astview.views;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.util.Signature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ASTViewLabelProvider extends LabelProvider implements IColorProvider, IFontProvider {
	private int fSelectionStart;
	private int fSelectionLength;
	
	private final Color fBlue, fRed, fDarkGray, fDarkGreen, fDarkRed;
	private final Font fBold;
	
	//to dispose:
	private final Font fAllocatedBoldItalic;
	private final Color fLightBlue, fLightRed;
	
	public ASTViewLabelProvider() {
		fSelectionStart= -1;
		fSelectionLength= -1;
		
		Display display= Display.getCurrent();
		
		fRed= display.getSystemColor(SWT.COLOR_RED);
		fDarkGray= display.getSystemColor(SWT.COLOR_DARK_GRAY);
		fBlue= display.getSystemColor(SWT.COLOR_DARK_BLUE);
		fDarkGreen= display.getSystemColor(SWT.COLOR_DARK_GREEN);
		fDarkRed= display.getSystemColor(SWT.COLOR_DARK_RED);
		
		fLightBlue= new Color(display, 232, 242, 254); // default for AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR
		fLightRed= new Color(display, 255, 190, 190);
		
		fBold= PlatformUI.getWorkbench().getThemeManager().getCurrentTheme().getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
		FontData[] fontData= fBold.getFontData();
		for (int i= 0; i < fontData.length; i++) {
			fontData[i].setStyle(fontData[i].getStyle() | SWT.ITALIC);
		}
		fAllocatedBoldItalic= new Font(display, fontData);
	}
	
	public void setSelectedRange(int start, int length) {
		fSelectionStart= start;
		fSelectionLength= length;
		 // could be made more efficient by only updating selected node and parents (of old and new selection)
		fireLabelProviderChanged(new LabelProviderChangedEvent(this));
	}

	public String getText(Object obj) {
		StringBuffer buf= new StringBuffer();
		if (obj instanceof ASTNode) {
			getNodeType((ASTNode) obj, buf);
		} else if (obj instanceof ASTAttribute) {
			buf.append(((ASTAttribute) obj).getLabel()); //FIXME
		}
		return buf.toString(); 
	}
	
	private void getNodeType(ASTNode node, StringBuffer buf) {
		buf.append(Signature.getSimpleName(node.getClass().getName()));
		buf.append(" ["); //$NON-NLS-1$
		buf.append(node.getStart());
		buf.append(", "); //$NON-NLS-1$
		buf.append(node.getLength());
		buf.append(']');
		if ((node.getFlags() & ASTNode.MALFORMED) != 0) {
			buf.append(" (malformed)"); //$NON-NLS-1$
		}
		if ((node.getFlags() & ASTNode.RECOVERED) != 0) {
			buf.append(" (recovered)"); //$NON-NLS-1$
		}
	}
	
	
	public Image getImage(Object obj) {
		if (obj instanceof ASTNode) {
			return null;
		} else if (obj instanceof ASTAttribute) {
			return ((ASTAttribute) obj).getImage();
		}
		
		return null;
//		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
//		if (obj instanceof ASTNode) {
//			imageKey = ISharedImages.IMG_OBJ_FOLDER;
//		}
//		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		if ((element instanceof Error))
			return fRed;
		if ((element instanceof ExceptionAttribute) && ((ExceptionAttribute) element).getException() != null)
			return fRed;
		
		if (element instanceof ASTNode) {
			ASTNode node= (ASTNode) element;
			if ((node.getFlags() & ASTNode.MALFORMED) != 0) {
				return fRed;
			}
			return fDarkGray;
		} else if (element instanceof Binding) {
			Binding binding= (Binding) element;
			if (!binding.isRelevant())
				return fDarkGray;
			return fBlue;
		} else if (element instanceof NodeProperty) {
			return null; // normal color
		} else if (element instanceof BindingProperty) {
			BindingProperty binding= (BindingProperty) element;
			if (!binding.isRelevant())
				return fDarkGray;
			return fBlue;
		} else if (element instanceof PhpElement) {
			PhpElement javaElement= (PhpElement) element;
			if (javaElement.getPhpElement() == null || ! javaElement.getPhpElement().exists()) {
				return fRed;
			}
			return fDarkGreen;
		}
		return fDarkRed; // all extra properties
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		if (isNotProperlyNested(element)) {
			return fLightRed;
		}
		if (fSelectionStart != -1 && isInside(element)) {
			return fLightBlue;
		}
		return null;
	}
	
	private boolean isNotProperlyNested(Object element) {
		if (element instanceof ASTNode) {
			ASTNode node= (ASTNode) element;
			int start= node.getStart();
			int end= start + node.getLength();
			
			ASTNode parent= node.getParent();
			if (parent != null) {
				int parentstart= parent.getStart();
				int parentend= parentstart + parent.getLength();
				
				if (start < parentstart || end > parentend) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isInsideNode(ASTNode node) {
		int start= node.getStart();
		int end= start + node.getLength();
		if (start <= fSelectionStart && (fSelectionStart + fSelectionLength) < end) {
			return true;
		}
		return false;
	}
	
	private boolean isInside(Object element) {
		if (element instanceof ASTNode) {
			return isInsideNode((ASTNode) element);
		} else if (element instanceof NodeProperty) {
			NodeProperty property= (NodeProperty) element;
			Object object= property.getNode();
			if (object instanceof ASTNode) {
				return isInsideNode((ASTNode) object);
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
	 */
	public Font getFont(Object element) {
		if (element instanceof ASTNode) {
			ASTNode node= (ASTNode) element;
			if ((node.getFlags() & ASTNode.RECOVERED) != 0)
				return fAllocatedBoldItalic;
			else
				return fBold;
		}
		return null;
	}
	
	public void dispose() {
		super.dispose();
		fLightBlue.dispose();
		fLightRed.dispose();
		fAllocatedBoldItalic.dispose();
	}
	
}
