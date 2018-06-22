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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.BufferManager;
import org.eclipse.dltk.internal.ui.editor.DocumentAdapter;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.ui.internal.editor.SelectionConvertor;

public class DOMModelAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (SelectionConvertor.class.equals(adapterType)) {
			return (T) new PHPSelectionConverter();
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { SelectionConvertor.class };
	}

	public class ModelSelection implements IndexedRegion, IAdaptable {
		private IModelElement modelElement;

		public ModelSelection(IModelElement modelElement) {
			this.modelElement = modelElement;
		}

		public IModelElement getModelElement() {
			return modelElement;
		}

		@Override
		public boolean contains(int testPosition) {
			return getStartOffset() >= testPosition && getEndOffset() <= testPosition;
		}

		@Override
		public int getEndOffset() {
			return getStartOffset() + getLength();
		}

		@Override
		public int getStartOffset() {
			try {
				return ((ISourceReference) modelElement).getSourceRange().getOffset();
			} catch (ModelException e) {
				Logger.logException(e);
			}

			return 0;
		}

		@Override
		public int getLength() {
			try {
				return ((ISourceReference) modelElement).getSourceRange().getLength();
			} catch (ModelException e) {
				Logger.logException(e);
			}

			return 0;
		}

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			if (IResource.class.equals(adapter)) {
				return (T) modelElement.getResource();
			} else if (IModelElement.class.equals(adapter)) {
				return (T) modelElement;
			}
			return null;
		}
	}

	private class PHPSelectionConverter extends SelectionConvertor {
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
					if (elementAt instanceof ISourceReference) {
						return new Object[] { new ModelSelection(elementAt) };
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}

			return super.getElements(model, start, end);
		}
	}

}
