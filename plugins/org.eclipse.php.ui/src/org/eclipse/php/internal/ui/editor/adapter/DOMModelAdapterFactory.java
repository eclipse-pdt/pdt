/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.adapter;

import java.util.Enumeration;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.BufferManager;
import org.eclipse.dltk.internal.ui.editor.DocumentAdapter;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.ui.SelectionConverter;

public class DOMModelAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (SelectionConverter.class.equals(adapterType)) {
			return (T) new PHPSelectionConverter();
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { SelectionConverter.class };
	}

	private static class PHPSelectionConverter extends SelectionConverter {
		@Override
		public Object[] getElements(IStructuredModel model, int start, int end) {
			DOMModelForPHP impl = (DOMModelForPHP) model;
			IStructuredDocument document = impl.getStructuredDocument();
			ISourceModule modelElement = null;
			Enumeration<?> openBuffers = BufferManager.getDefaultBufferManager().getOpenBuffers();
			while (openBuffers.hasMoreElements()) {
				Object nextElement = openBuffers.nextElement();
				if (nextElement instanceof DocumentAdapter) {
					DocumentAdapter adapt = (DocumentAdapter) nextElement;
					if (adapt.getDocument().equals(document) && adapt.getOwner() instanceof ISourceModule) {
						modelElement = (ISourceModule) adapt.getOwner();
						break;
					}
				}
			}
			if (modelElement != null) {
				try {
					IModelElement elementAt = modelElement.getElementAt(start);
					if (elementAt instanceof IField
							&& (elementAt.getParent() instanceof IMethod || elementAt.getParent() instanceof IType)) {
						elementAt = elementAt.getParent();
					}
					if (elementAt instanceof ISourceReference) {
						return new Object[] { elementAt };
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}

			return super.getElements(model, start, end);
		}

		@Override
		public IRegion getRegion(Object o) {
			while (o instanceof ISourceReference) {
				try {
					ISourceRange sourceRange = ((ISourceReference) o).getSourceRange();
					return new Region(sourceRange.getOffset(), sourceRange.getLength());
				} catch (ModelException e) {
					Logger.logException(e);
					if (o instanceof IModelElement && ((IModelElement) o).getParent() != null) {
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=536109#c5
						// travel up and try to get source range from
						// nearest parent
						o = ((IModelElement) o).getParent();
					} else {
						// or abort
						return new Region(0, 0);
					}
				}
			}
			return super.getRegion(o);
		}

		@Override
		public IRegion getSelectionRegion(Object o) {
			while (o instanceof ISourceReference) {
				try {
					ISourceRange sourceRange = ((ISourceReference) o).getNameRange();
					if (sourceRange == null) {
						sourceRange = ((ISourceReference) o).getSourceRange();
					}
					return new Region(sourceRange.getOffset(), sourceRange.getLength());
				} catch (ModelException e) {
					Logger.logException(e);
					if (o instanceof IModelElement && ((IModelElement) o).getParent() != null) {
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=536109#c5
						// travel up and try to get source range from
						// nearest parent
						o = ((IModelElement) o).getParent();
					} else {
						// or abort
						return new Region(0, 0);
					}
				}
			}
			return super.getRegion(o);
		}
	}

}