/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.breakpoint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension4;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.util.Debug;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.extension.BreakpointProviderBuilder;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.ISourceEditingTextTools;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IBreakpointProvider;

/**
 * Adapter to create the break point.
 */
public class ScriptLineBreakpointAdapter implements IToggleBreakpointsTarget {

	protected IVerticalRulerInfo fRulerInfo = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.debug.ui.actions.IToggleBreakpointsTarget#
	 * canToggleLineBreakpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleLineBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		PHPStructuredEditor textEditor = getEditor(part);

		if (textEditor != null) {
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.debug.ui.actions.IToggleBreakpointsTarget#
	 * canToggleMethodBreakpoints(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#canToggleWatchpoints
	 * (org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleLineBreakpoints
	 * (org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleLineBreakpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		ITextEditor editor = (ITextEditor) part.getAdapter(ITextEditor.class);
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			IDocument document = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());
			int lineNumber = -1;
			try {
				lineNumber = document
						.getLineOfOffset(textSelection.getOffset());
			} catch (BadLocationException e) {
			}
			if (lineNumber >= 0) {

				IResource resource = getResource(editor.getEditorInput());
				AbstractMarkerAnnotationModel model = getAnnotationModel(editor);
				IBreakpoint[] breakpoints = getBreakpoints(resource, document,
						model, lineNumber);
				if (breakpoints.length > 0) {
					IBreakpointManager breakpointManager = DebugPlugin
							.getDefault().getBreakpointManager();
					for (int i = 0; i < breakpoints.length; i++) {
						breakpoints[i].getMarker().delete();
						breakpointManager
								.removeBreakpoint(breakpoints[i], true);
					}
				} else {
					createBreakpoints(editor, lineNumber + 1);
				}

			}
		}

	}

	protected String getContentType(ITextEditor editor, IDocument document) {
		IModelManager mgr = StructuredModelManager.getModelManager();
		String contentType = null;

		IDocumentProvider provider = editor.getDocumentProvider();
		if (provider instanceof IDocumentProviderExtension4) {
			try {
				IContentType type = ((IDocumentProviderExtension4) provider)
						.getContentType(editor.getEditorInput());
				if (type != null)
					contentType = type.getId();
			} catch (CoreException e) {
				/*
				 * A failure accessing the underlying store really isn't
				 * interesting, although it can be a problem for
				 * IStorageEditorInputs.
				 */
			}
		}

		if (contentType == null) {
			IStructuredModel model = null;
			try {
				model = mgr.getExistingModelForRead(document);
				if (model != null) {
					contentType = model.getContentTypeIdentifier();
				}
			} finally {
				if (model != null) {
					model.releaseFromRead();
				}
			}
		}
		return contentType;
	}

	public static final String getFileExtension(IEditorInput input) {
		IPath path = null;
		if (input instanceof IStorageEditorInput) {
			try {
				path = ((IStorageEditorInput) input).getStorage().getFullPath();
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		if (path != null) {
			return path.getFileExtension();
		}
		String name = input.getName();
		int index = name.lastIndexOf('.');
		if (index == -1)
			return null;
		if (index == (name.length() - 1))
			return ""; //$NON-NLS-1$
		return name.substring(index + 1);
	}

	protected boolean createBreakpoints(ITextEditor editor, int lineNumber) {
		/*
		 * Note: we'll always allow processing to continue, even for a "read
		 * only" IStorageEditorInput, for the ActiveScript debugger. But this
		 * means sometimes the ActiveScript provider might get an input from CVS
		 * or something that is not related to debugging.
		 */

		IEditorInput input = editor.getEditorInput();
		IDocument document = editor.getDocumentProvider().getDocument(input);
		if (document == null)
			return false;

		String contentType = getContentType(editor, document);
		IBreakpointProvider[] providers = BreakpointProviderBuilder
				.getInstance().getBreakpointProviders(editor, contentType,
						getFileExtension(input));

		int pos = -1;
		ISourceEditingTextTools tools = (ISourceEditingTextTools) editor
				.getAdapter(ISourceEditingTextTools.class);
		if (tools != null) {
			pos = tools.getCaretOffset();
		}

		final int n = providers.length;
		List errors = new ArrayList(0);
		for (int i = 0; i < n; i++) {
			try {
				if (Debug.debugBreakpoints)
					System.out.println(providers[i].getClass().getName()
							+ " adding breakpoint to line " + lineNumber); //$NON-NLS-1$
				IStatus status = providers[i].addBreakpoint(document, input,
						lineNumber, pos);
				if (status != null && !status.isOK()) {
					errors.add(status);
				}
			} catch (CoreException e) {
				errors.add(e.getStatus());
			} catch (Exception t) {
				Logger.logException("exception while adding breakpoint", t); //$NON-NLS-1$
			}
		}

		IStatus status = null;
		if (errors.size() > 0) {
			Shell shell = editor.getSite().getShell();
			if (errors.size() > 1) {
				status = new MultiStatus(SSEUIPlugin.ID, IStatus.OK,
						(IStatus[]) errors.toArray(new IStatus[0]),
						SSEUIMessages.ManageBreakpoints_error_adding_message1,
						null);
			} else {
				status = (IStatus) errors.get(0);
			}
			if ((status.getSeverity() > IStatus.INFO)
					|| (Platform.inDebugMode() && !status.isOK())) {
				Platform.getLog(SSEUIPlugin.getDefault().getBundle()).log(
						status);
			}
			/*
			 * Show for conditions more severe than INFO or when no breakpoints
			 * were created
			 */
			if (status.getSeverity() > IStatus.INFO
					|| getBreakpoints(getMarkers(editor)).length < 1) {
				ErrorDialog.openError(shell,
						SSEUIMessages.ManageBreakpoints_error_adding_title1,
						status.getMessage(), status);
				return false;
			}
		}

		return true;
	}

	protected IBreakpoint[] getBreakpoints(IMarker[] markers) {
		IBreakpointManager manager = DebugPlugin.getDefault()
				.getBreakpointManager();
		List breakpoints = new ArrayList(markers.length);
		for (int i = 0; i < markers.length; i++) {
			IBreakpoint breakpoint = manager.getBreakpoint(markers[i]);
			if (breakpoint != null) {
				breakpoints.add(breakpoint);
			}
		}
		return (IBreakpoint[]) breakpoints.toArray(new IBreakpoint[0]);
	}

	/**
	 * Returns the <code>IDocument</code> of the editor's input.
	 * 
	 * @return the document of the editor's input
	 */
	protected IDocument getDocument(ITextEditor editor) {
		IDocumentProvider provider = editor.getDocumentProvider();
		return provider.getDocument(editor.getEditorInput());
	}

	/**
	 * Returns all markers which include the ruler's line of activity.
	 * 
	 * @return an array of markers which include the ruler's line of activity
	 */
	protected IMarker[] getMarkers(ITextEditor editor) {
		List markers = new ArrayList();

		IResource resource = getResource(editor.getEditorInput());
		IDocument document = getDocument(editor);
		AbstractMarkerAnnotationModel annotationModel = getAnnotationModel(editor);

		if (resource != null && annotationModel != null && resource.exists()) {
			try {
				IMarker[] allMarkers = resource.findMarkers(
						IBreakpoint.BREAKPOINT_MARKER, true,
						IResource.DEPTH_ZERO);
				if (allMarkers != null) {
					for (int i = 0; i < allMarkers.length; i++) {
						if (includesRulerLine(editor,
								annotationModel
										.getMarkerPosition(allMarkers[i]),
								document)) {
							markers.add(allMarkers[i]);
						}
					}
				}
			} catch (CoreException x) {
				//
			}
		}

		return (IMarker[]) markers.toArray(new IMarker[0]);
	}

	/**
	 * Checks whether a position includes the ruler's line of activity.
	 * 
	 * @param editor
	 * 
	 * @param position
	 *            the position to be checked
	 * @param document
	 *            the document the position refers to
	 * @return <code>true</code> if the line is included by the given position
	 */
	protected boolean includesRulerLine(ITextEditor editor, Position position,
			IDocument document) {
		return false;
	}

	private IBreakpoint[] getBreakpoints(IResource resource,
			IDocument document, AbstractMarkerAnnotationModel model,
			int lineNumber) {
		List markers = new ArrayList();
		if (document != null && resource != null && model != null
				&& resource.exists()) {
			try {
				IMarker[] allMarkers = resource.findMarkers(
						IBreakpoint.LINE_BREAKPOINT_MARKER, true,
						IResource.DEPTH_ZERO);
				if (allMarkers != null) {
					for (int i = 0; i < allMarkers.length; i++) {
						Position p = model.getMarkerPosition(allMarkers[i]);
						int markerLine = -1;
						try {
							markerLine = document
									.getLineOfOffset(p.getOffset());
						} catch (BadLocationException e1) {
						}
						if (markerLine == lineNumber) {
							markers.add(allMarkers[i]);
						}
					}
				}
			} catch (CoreException x) {
			}
		}
		IBreakpointManager manager = DebugPlugin.getDefault()
				.getBreakpointManager();
		List breakpoints = new ArrayList(markers.size());
		for (int i = 0; i < markers.size(); i++) {
			IBreakpoint breakpoint = manager.getBreakpoint((IMarker) markers
					.get(i));
			if (breakpoint != null) {
				breakpoints.add(breakpoint);
			}
		}
		return (IBreakpoint[]) breakpoints.toArray(new IBreakpoint[0]);
	}

	/**
	 * Returns the <code>AbstractMarkerAnnotationModel</code> of the editor's
	 * input.
	 * 
	 * @param editor
	 * 
	 * @return the marker annotation model
	 */
	protected AbstractMarkerAnnotationModel getAnnotationModel(
			ITextEditor editor) {
		IDocumentProvider provider = editor.getDocumentProvider();
		IAnnotationModel model = provider.getAnnotationModel(editor
				.getEditorInput());
		if (model instanceof AbstractMarkerAnnotationModel)
			return (AbstractMarkerAnnotationModel) model;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleMethodBreakpoints
	 * (org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) throws CoreException {
		// don't support
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleWatchpoints
	 * (org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		// don't support
	}

	private PHPStructuredEditor getEditor(IWorkbenchPart part) {
		if (part instanceof PHPStructuredEditor) {
			return (PHPStructuredEditor) part;
		}
		return null;
	}

	public final IResource getResource(IEditorInput input) {
		IResource resource = null;

		if (input instanceof IFileEditorInput)
			resource = ((IFileEditorInput) input).getFile();
		if (resource == null)
			resource = (IResource) input.getAdapter(IFile.class);
		if (resource == null)
			resource = (IResource) input.getAdapter(IResource.class);

		IEditorPart editorPart = null;
		if (resource == null) {
			IWorkbench workbench = SSEUIPlugin.getDefault().getWorkbench();
			if (workbench != null) {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					IPartService service = window.getPartService();
					if (service != null) {
						Object part = service.getActivePart();
						if (part != null && part instanceof IEditorPart) {
							editorPart = (IEditorPart) part;
							if (editorPart != null) {
								IStructuredModel model = null;
								ITextEditor textEditor = null;
								try {
									if (editorPart instanceof ITextEditor) {
										textEditor = (ITextEditor) editorPart;
									}
									if (textEditor == null) {
										textEditor = (ITextEditor) editorPart
												.getAdapter(ITextEditor.class);
									}
									if (textEditor != null) {
										IDocument textDocument = textEditor
												.getDocumentProvider()
												.getDocument(input);
										model = StructuredModelManager
												.getModelManager()
												.getExistingModelForRead(
														textDocument);
										if (model != null) {
											resource = BreakpointProviderBuilder
													.getInstance()
													.getResource(
															input,
															model.getContentTypeIdentifier(),
															getFileExtension(input));
										}
									}
									if (resource == null) {
										IBreakpointProvider[] providers = BreakpointProviderBuilder
												.getInstance()
												.getBreakpointProviders(
														editorPart, null,
														getFileExtension(input));
										for (int i = 0; i < providers.length
												&& resource == null; i++) {
											resource = providers[i]
													.getResource(input);
										}
									}
								} catch (Exception e) {
									Logger.logException(e);
								} finally {
									if (model != null) {
										model.releaseFromRead();
									}
								}
							}

						}
					}
				}

			}
		}
		return resource;
	}

}
