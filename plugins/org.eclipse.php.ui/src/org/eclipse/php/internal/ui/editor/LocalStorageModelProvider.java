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
package org.eclipse.php.internal.ui.editor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.editors.text.StorageDocumentProvider;
import org.eclipse.ui.texteditor.DocumentProviderRegistry;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.encoding.CodedReaderCreator;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.util.Utilities;
import org.eclipse.wst.sse.ui.internal.IModelProvider;
import org.eclipse.wst.sse.ui.internal.debug.BreakpointRulerAction;
import org.eclipse.wst.sse.ui.internal.editor.EditorModelUtil;
import org.eclipse.wst.sse.ui.internal.extension.BreakpointProviderBuilder;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IExtendedStorageEditorInput;

/**
 * This class overrides org.eclipse.wst.sse.ui.internal.StorageModelProvider in
 * order to use IPath.toOSString() since it handles local file storage
 * 
 * @author yaronm
 */
public class LocalStorageModelProvider extends StorageDocumentProvider implements IModelProvider {

	private class InternalElementStateListener implements IElementStateListener {
		public void elementContentAboutToBeReplaced(Object element) {
			LocalStorageModelProvider.this.fireElementContentAboutToBeReplaced(element);
		}

		public void elementContentReplaced(Object element) {
			StorageInfo info = (StorageInfo) getElementInfo(element);
			if (info == null) {
				fireElementContentReplaced(element);
				fireElementDirtyStateChanged(element, false);
				return;
			}
			/**
			 * Force a reload of the markers into annotations since their
			 * previous Positions have been deleted. Disconnecting and
			 * reconnecting forces a call to the private catchupWithMarkers
			 * method.
			 */
			if (info.fModel != null) {
				info.fModel.disconnect(info.fDocument);
			}

			Reader reader = null;
			IStructuredDocument innerdocument = null;
			try {
				// update document from input's contents
				CodedReaderCreator codedReaderCreator = new CodedReaderCreator(
						calculateID((IStorageEditorInput) element),
						Utilities.getMarkSupportedStream(((IStorageEditorInput) element).getStorage().getContents()));
				reader = codedReaderCreator.getCodedReader();

				innerdocument = (IStructuredDocument) info.fDocument;

				int originalLengthToReplace = innerdocument.getLength();

				StringBuilder stringBuffer = new StringBuilder();
				int bufferSize = 2048;
				char[] buffer = new char[bufferSize];
				int nRead = 0;
				boolean eof = false;
				while (!eof) {
					nRead = reader.read(buffer, 0, bufferSize);
					if (nRead == -1) {
						eof = true;
					} else {
						stringBuffer.append(buffer, 0, nRead);
					}
				}
				innerdocument.replaceText(this, 0, originalLengthToReplace, stringBuffer.toString(), true);
			} catch (CoreException e) {
				Logger.logException(e);
			} catch (IOException e) {
				Logger.logException(e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
						// would be highly unusual
						Logger.logException(e1);
					}
				}
			}

			// forward the event
			if (info.fCanBeSaved) {
				info.fCanBeSaved = false;
				addUnchangedElementListeners(element, info);
			}
			fireElementContentReplaced(element);
			fireElementDirtyStateChanged(element, false);

			if (info.fModel != null) {
				info.fModel.connect(info.fDocument);
			}
		}

		public void elementDeleted(Object element) {

			LocalStorageModelProvider.this.fireElementDeleted(element);
		}

		public void elementDirtyStateChanged(Object element, boolean isDirty) {
			LocalStorageModelProvider.this.fireElementDirtyStateChanged(element, isDirty);
		}

		public void elementMoved(Object originalElement, Object movedElement) {
			LocalStorageModelProvider.this.fireElementMoved(originalElement, movedElement);
		}
	}

	/**
	 * Collection of info that goes with a model.
	 */
	private class ModelInfo {
		public IEditorInput fElement;
		public boolean fShouldReleaseOnInfoDispose;
		public IStructuredModel fStructuredModel;

		public ModelInfo(IStructuredModel structuredModel, IEditorInput element, boolean selfCreated) {
			fElement = element;
			fStructuredModel = structuredModel;
			fShouldReleaseOnInfoDispose = selfCreated;
		}
	}

	private static LocalStorageModelProvider fInstance = null;

	public synchronized static LocalStorageModelProvider getInstance() {
		if (fInstance == null)
			fInstance = new LocalStorageModelProvider();
		return fInstance;
	}

	private IElementStateListener fInternalListener;
	/** IStructuredModel information of all connected elements */
	private Map<IEditorInput, ModelInfo> fModelInfoMap = new HashMap<>();
	private boolean fReuseModelDocument = true;

	private LocalStorageModelProvider() {
		super();
		fInternalListener = new InternalElementStateListener();
	}

	/**
	 * This method simply overrides
	 * org.eclipse.wst.sse.ui.internal.StorageModelProvider
	 * .calculateBaseLocation()
	 * 
	 * @param input
	 * @return
	 */
	String calculateBaseLocation(IStorageEditorInput input) {
		String location = null;
		try {
			IStorage storage = input.getStorage();
			if (storage != null) {
				IPath storagePath = storage.getFullPath();
				String name = storage.getName();
				if (storagePath != null) {
					// If they are different, the IStorage contract is not
					// being honored
					// (https://bugs.eclipse.org/bugs/show_bug.cgi?id=73098).
					// Favor the name.
					if (!storagePath.lastSegment().equals(name)) {
						IPath workingPath = storagePath.addTrailingSeparator();
						location = workingPath.append(name).toString();
					} else {
						location = storagePath.makeAbsolute().toString();
					}
				}
				if (location == null)
					location = name;
			}
		} catch (CoreException e) {
			Logger.logException(e);
		} finally {
			if (location == null)
				location = input.getName();
		}
		return location;
	}

	/**
	 * This method simply overrides
	 * org.eclipse.wst.sse.ui.internal.StorageModelProvider.calculateID()
	 * 
	 * @param input
	 * @return
	 */
	String calculateID(IStorageEditorInput input) {
		/**
		 * Typically CVS will return a path of "filename.ext" and the input's
		 * name will be "filename.ext version". The path must be used to load
		 * the model so that the suffix will be available to compute the
		 * contentType properly. The editor input name can then be set as the
		 * base location for display on the editor title bar.
		 * 
		 */
		String path = null;
		boolean addHash = false;
		try {
			IStorage storage = input.getStorage();
			if (storage != null) {
				IPath storagePath = storage.getFullPath();
				String name = storage.getName();
				// if either the name or storage path are null or they are
				// identical, add a hash to it to guarantee uniqueness
				addHash = storagePath == null || storagePath.toString().equals(name);
				if (storagePath != null) {
					// If they are different, the IStorage contract is not
					// being honored
					// (https://bugs.eclipse.org/bugs/show_bug.cgi?id=73098).
					// Favor the name.
					if (!storagePath.lastSegment().equals(name)) {
						IPath workingPath = storagePath.addTrailingSeparator();
						path = workingPath.append(name).toString();
					} else {
						path = storagePath.makeAbsolute().toString();
					}
				}
				if (path == null)
					path = name;
			}
		} catch (CoreException e) {
			Logger.logException(e);
		} finally {
			if (path == null)
				path = ""; //$NON-NLS-1$
		}
		if (addHash)
			path = input.hashCode() + path;
		return path;
	}

	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		IAnnotationModel model = null;
		if (element instanceof IStorageEditorInput) {
			IStorageEditorInput input = (IStorageEditorInput) element;
			String contentType = (getModel(input) != null ? getModel(input).getContentTypeIdentifier() : null);
			String ext = BreakpointRulerAction.getFileExtension((IEditorInput) element);
			IResource res = BreakpointProviderBuilder.getInstance().getResource(input, contentType, ext);
			String id = input.getName();
			if (input.getStorage() != null && input.getStorage().getFullPath() != null) {
				id = input.getStorage().getFullPath().toString();
			}
			// we can only create a resource marker annotationmodel off of a
			// valid resource
			if (res != null)
				model = new PHPResourceMarkerAnnotationModel(res, id);
			else
				model = new AnnotationModel();
		}
		if (model == null) {
			model = super.createAnnotationModel(element);
		}
		return model;
	}

	protected IDocument createDocument(Object element) {
		// The following is largely copied from FileModelProvider
		IDocument document = null;
		if (element instanceof IEditorInput) {
			// create a new IDocument for the element; should always reflect
			// the contents of the resource
			ModelInfo info = getModelInfoFor((IEditorInput) element);
			if (info == null) {
				throw new IllegalArgumentException("no corresponding model info found"); //$NON-NLS-1$
			}
			IStructuredModel model = info.fStructuredModel;
			if (model != null) {
				if (!fReuseModelDocument && element instanceof IStorageEditorInput) {
					Reader reader = null;
					IStructuredDocument innerdocument = null;
					try {
						// update document from input's contents

						CodedReaderCreator codedReaderCreator = new CodedReaderCreator(
								calculateID((IStorageEditorInput) element), Utilities.getMarkSupportedStream(
										((IStorageEditorInput) element).getStorage().getContents()));
						reader = codedReaderCreator.getCodedReader();

						innerdocument = model.getStructuredDocument();

						int originalLengthToReplace = innerdocument.getLength();

						/*
						 * TODO_future: we could implement with sequential
						 * rewrite, if we don't pickup automatically from
						 * FileBuffer support, so not so much has to be pulled
						 * into memory (as an extra big string), but we need to
						 * carry that API through so that StructuredModel is not
						 * notified until done.
						 */

						// innerdocument.startSequentialRewrite(true);
						// innerdocument.replaceText(this, 0,
						// innerdocument.getLength(), "");
						StringBuilder stringBuffer = new StringBuilder();
						int bufferSize = 2048;
						char[] buffer = new char[bufferSize];
						int nRead = 0;
						boolean eof = false;
						while (!eof) {
							nRead = reader.read(buffer, 0, bufferSize);
							if (nRead == -1) {
								eof = true;
							} else {
								stringBuffer.append(buffer, 0, nRead);
								// innerdocument.replaceText(this,
								// innerdocument.getLength(), 0, new
								// String(buffer, 0, nRead));
							}
						}
						// ignore read-only settings if reverting whole
						// document
						innerdocument.replaceText(this, 0, originalLengthToReplace, stringBuffer.toString(), true);
						model.setDirtyState(false);

					} catch (CoreException e) {
						Logger.logException(e);
					} catch (IOException e) {
						Logger.logException(e);
					} finally {
						if (reader != null) {
							try {
								reader.close();
							} catch (IOException e1) {
								// would be highly unusual
								Logger.logException(e1);
							}
						}
						// if (innerdocument != null) {
						// innerdocument.stopSequentialRewrite();
						// }
					}

				}
				if (document == null) {
					document = model.getStructuredDocument();
				}
			}
		}
		return document;
	}

	/**
	 * Also create ModelInfo - extra resource synchronization classes should be
	 * stored within the ModelInfo
	 */
	protected ElementInfo createElementInfo(Object element) throws CoreException {
		if (getModelInfoFor((IEditorInput) element) == null) {
			createModelInfo((IEditorInput) element);
		}

		ElementInfo info = super.createElementInfo(element);
		return info;
	}

	public void createModelInfo(IEditorInput input) {
		if (getModelInfoFor(input) == null) {
			IStructuredModel structuredModel = selfCreateModel(input);
			if (structuredModel != null) {
				createModelInfo(input, structuredModel, true);
			}
		}
	}

	/**
	 * To be used when model is provided to us, ensures that when setInput is
	 * used on this input, the given model will be used.
	 */
	public void createModelInfo(IEditorInput input, IStructuredModel structuredModel,
			boolean releaseModelOnDisconnect) {
		// we have to make sure factories are added, whether we created or
		// not.
		if (getModelInfoFor(input) != null || getModelInfoFor(structuredModel) != null) {
			return;
		}

		if (input instanceof IExtendedStorageEditorInput) {
			((IExtendedStorageEditorInput) input).addElementStateListener(fInternalListener);
		}

		EditorModelUtil.addFactoriesTo(structuredModel);

		ModelInfo modelInfo = new ModelInfo(structuredModel, input, releaseModelOnDisconnect);
		fModelInfoMap.put(input, modelInfo);
	}

	protected void disposeElementInfo(Object element, ElementInfo info) {
		if (element instanceof IEditorInput) {
			IEditorInput input = (IEditorInput) element;
			ModelInfo modelInfo = getModelInfoFor(input);
			disposeModelInfo(modelInfo);
		}
		super.disposeElementInfo(element, info);
	}

	/**
	 * disconnect from this model info
	 * 
	 * @param info
	 */
	public void disposeModelInfo(ModelInfo info) {
		if (info.fElement instanceof IStorageEditorInput) {
			if (info.fElement instanceof IExtendedStorageEditorInput) {
				((IExtendedStorageEditorInput) info.fElement).removeElementStateListener(fInternalListener);
			}
			if (info.fShouldReleaseOnInfoDispose) {
				info.fStructuredModel.releaseFromEdit();
			}
		}
		fModelInfoMap.remove(info.fElement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.texteditor.AbstractDocumentProvider#doResetDocument(java
	 * .lang.Object, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void doResetDocument(Object element, IProgressMonitor monitor) throws CoreException {
		fReuseModelDocument = false;
		super.doResetDocument(element, monitor);
		fReuseModelDocument = true;
	}

	/**
	 * @see org.eclipse.ui.texteditor.AbstractDocumentProvider#doSaveDocument(org.eclipse.core.runtime.IProgressMonitor,
	 *      java.lang.Object, org.eclipse.jface.text.IDocument, boolean)
	 */
	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite)
			throws CoreException {
		IDocumentProvider provider = null;
		// BUG119211 - try to use registered document provider if possible
		if (element instanceof IEditorInput) {
			provider = DocumentProviderRegistry.getDefault().getDocumentProvider((IEditorInput) element);
		}
		if (provider == null)
			provider = new FileDocumentProvider();
		provider.saveDocument(monitor, element, document, overwrite);
	}

	public IStructuredModel getModel(IEditorInput element) {
		IStructuredModel result = null;
		ModelInfo info = getModelInfoFor(element);
		if (info != null) {
			result = info.fStructuredModel;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.sse.ui.IModelProvider#getModel(java.lang.Object)
	 */
	public IStructuredModel getModel(Object element) {
		if (element instanceof IEditorInput)
			return getModel((IEditorInput) element);
		return null;
	}

	private ModelInfo getModelInfoFor(IEditorInput element) {
		ModelInfo result = (ModelInfo) fModelInfoMap.get(element);
		return result;
	}

	private ModelInfo getModelInfoFor(IStructuredModel structuredModel) {
		ModelInfo result = null;
		if (structuredModel != null) {
			ModelInfo[] modelInfos = (ModelInfo[]) fModelInfoMap.values().toArray(new ModelInfo[0]);
			for (int i = 0; i < modelInfos.length; i++) {
				ModelInfo info = modelInfos[i];
				if (structuredModel.equals(info.fStructuredModel)) {
					result = info;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Method loadModel.
	 * 
	 * @param input
	 * @return IStructuredModel
	 */
	public IStructuredModel loadModel(IStorageEditorInput input) {
		return loadModel(input, false);
	}

	/**
	 * Method loadModel.
	 * 
	 * @param input
	 * @param logExceptions
	 * @return IStructuredModel
	 */
	public IStructuredModel loadModel(IStorageEditorInput input, boolean logExceptions) {
		String id = calculateID(input);

		InputStream contents = null;
		try {
			contents = input.getStorage().getContents();
		} catch (CoreException noStorageExc) {
			if (logExceptions) {
				Logger.logException(noStorageExc);
			}
		}

		IStructuredModel model = null;
		try {
			// first parameter must be unique
			model = StructuredModelManager.getModelManager().getModelForEdit(id, contents, null);
			model.setBaseLocation(calculateBaseLocation(input));
		} catch (IOException e) {
			if (logExceptions) {
				Logger.logException(e);
			}
		} finally {
			if (contents != null) {
				try {
					contents.close();
				} catch (IOException e) {
					// nothing
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		}
		return model;
	}

	/**
	 * @param input
	 * @return
	 */
	private IStructuredModel selfCreateModel(IEditorInput input) {
		return loadModel((IStorageEditorInput) input);
	}
}
