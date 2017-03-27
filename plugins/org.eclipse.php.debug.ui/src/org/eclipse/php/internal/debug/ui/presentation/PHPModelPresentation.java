/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.presentation;

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.*;
import org.eclipse.debug.ui.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.Openable;
import org.eclipse.dltk.internal.core.util.HandleFactory;
import org.eclipse.dltk.internal.debug.ui.ExternalFileEditorInput;
import org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.internal.ui.search.DLTKSearchScopeFactory;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;
import org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;
import org.eclipse.php.internal.debug.core.zend.model.PHPMultiDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPStackFrame;
import org.eclipse.php.internal.debug.core.zend.model.PHPValue;
import org.eclipse.php.internal.debug.ui.*;
import org.eclipse.php.internal.debug.ui.breakpoint.PHPBreakpointImageDescriptor;
import org.eclipse.php.internal.debug.ui.breakpoint.PHPExceptionBreakpointAnnotation;
import org.eclipse.php.internal.ui.editor.UntitledPHPEditor;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

import com.ibm.icu.text.MessageFormat;

/**
 * Renders PHP debug elements
 */
@SuppressWarnings("restriction")
public class PHPModelPresentation extends LabelProvider implements IDebugModelPresentation, IDebugEditorPresentation {

	private ImageDescriptorRegistry fDebugImageRegistry;
	private Map<IThread, Annotation> fExceptionAnnotations = new HashMap<IThread, Annotation>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse
	 * .debug.core.model.IValue, org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener) {
		String detail = ""; //$NON-NLS-1$
		try {
			if (value instanceof PHPValue) {
				detail = ((PHPValue) value).getValueDetail();
			} else {
				detail = value.getValueString();
			}
		} catch (DebugException e) {
		}
		listener.detailComputed(value, detail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof PHPConditionalBreakpoint) {
			return getLineBreakpointImage((PHPConditionalBreakpoint) element);
		} else if (element instanceof IPHPExceptionBreakpoint) {
			return getExceptionBreakpointImage((IPHPExceptionBreakpoint) element);
		} else if (element instanceof IVariable) {
			return getVariableImage((IVariable) element);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof IDebugTarget) {
			return getTargetText((IDebugTarget) element);
		} else if (element instanceof IThread) {
			return getThreadText((IThread) element);
		} else if (element instanceof IStackFrame) {
			return getStackFrameText((IStackFrame) element);
		} else if (element instanceof ILineBreakpoint) {
			return getLineBreakpointText((ILineBreakpoint) element);
		} else if (element instanceof IPHPExceptionBreakpoint) {
			return getExceptionBreakpointText((IPHPExceptionBreakpoint) element);
		}
		return null;

	}

	public String getEditorId(IEditorInput input, Object inputObject) {
		if (input instanceof NonExistingPHPFileEditorInput) {
			return UntitledPHPEditor.ID;
		}
		try {
			IEditorDescriptor descriptor = IDE.getEditorDescriptor(input.getName());
			return descriptor.getId();
		} catch (PartInitException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ISourcePresentation#getEditorInput(java.lang.Object)
	 */
	public IEditorInput getEditorInput(Object element) {
		if (element instanceof IFile) {
			return new FileEditorInput((IFile) element);
		}
		if (element instanceof IFileHandle) {
			return new ExternalFileEditorInput((IFileHandle) element);
		}
		if (element instanceof ILineBreakpoint) {
			return getLineBreakpointEditorInput(element);
		}
		if (element instanceof IStorage) {
			return new ExternalStorageEditorInput((IStorage) element);
		}
		if (element instanceof IFileStore) {
			IFileStore fileStore = (IFileStore) element;
			NonExistingPHPFileEditorInput nonExistingEditorInput = NonExistingPHPFileEditorInput
					.findEditorInput(new Path(fileStore.toURI().getPath()));
			if (nonExistingEditorInput != null) {
				return nonExistingEditorInput;
			}
			return new FileStoreEditorInput(fileStore);
		}
		Logger.log(Logger.WARNING_DEBUG, "Unknown editor input type: " //$NON-NLS-1$
				+ element.getClass().getName());
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#setAttribute(java.lang.
	 * String , java.lang.Object)
	 */
	public void setAttribute(String attribute, Object value) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugEditorPresentation#addAnnotations(org.eclipse.
	 * ui.IEditorPart, org.eclipse.debug.core.model.IStackFrame)
	 */
	@Override
	public boolean addAnnotations(IEditorPart editorPart, IStackFrame frame) {
		IBreakpoint[] breakpoints = frame.getThread().getBreakpoints();
		for (IBreakpoint breakpoint : breakpoints) {
			if (breakpoint instanceof IPHPExceptionBreakpoint) {
				IPHPExceptionBreakpoint exceptionBreakpoint = (IPHPExceptionBreakpoint) breakpoint;
				try {
					if (frame.getLineNumber() == exceptionBreakpoint.getLine(frame.getDebugTarget())) {
						addExceptionAnnotation(editorPart, frame, exceptionBreakpoint);
						return true;
					}
				} catch (DebugException e) {
					Logger.logException(e);
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.IDebugEditorPresentation#removeAnnotations(org.
	 * eclipse.ui.IEditorPart, org.eclipse.debug.core.model.IThread)
	 */
	@Override
	public void removeAnnotations(IEditorPart editorPart, IThread thread) {
		ITextEditor textEditor = editorPart.getAdapter(ITextEditor.class);
		IDocumentProvider docProvider = textEditor.getDocumentProvider();
		IEditorInput editorInput = textEditor.getEditorInput();
		// If there is no annotation model, there's nothing more to do
		IAnnotationModel annModel = docProvider.getAnnotationModel(editorInput);
		if (annModel == null) {
			return;
		}
		Annotation annotation = fExceptionAnnotations.remove(thread);
		if (annotation != null) {
			annModel.removeAnnotation(annotation);
		}
	}

	protected Image getVariableImage(IVariable variable) {
		IVariableFacet facetOwner = null;
		if (variable instanceof IVariableFacet) {
			facetOwner = (IVariableFacet) variable;
		} else {
			facetOwner = (IVariableFacet) variable.getAdapter(IVariableFacet.class);
		}
		if (facetOwner == null)
			return null;
		if (facetOwner.hasFacet(KIND_SUPER_GLOBAL)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_SUPER_GLOBAL);
		}
		if (facetOwner.hasFacet(KIND_LOCAL)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_LOCAL);
		}
		if (facetOwner.hasFacet(KIND_THIS)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_PUBLIC_ACCESS);
		}
		if (facetOwner.hasFacet(KIND_ARRAY_MEMBER)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_ARRAY);
		}
		if (facetOwner.hasFacet(KIND_CONSTANT)) {
			if (facetOwner.hasFacet(Facet.MOD_PUBLIC)) {
				return getDebugImageRegistry().get(new PHPDebugUICompositeImageDescriptor(
						PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OBJ_MEMBER_PUBLIC_ACCESS),
						PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OVR_MEMBER_CONSTANT),
						PHPDebugUICompositeImageDescriptor.TOP_RIGHT));
			}
		}
		if (facetOwner.hasFacet(KIND_OBJECT_MEMBER)) {
			if (facetOwner.hasFacet(Facet.MOD_PUBLIC)) {
				if (facetOwner.hasFacet(Facet.MOD_STATIC))
					return getDebugImageRegistry().get(new PHPDebugUICompositeImageDescriptor(
							PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OBJ_MEMBER_PUBLIC_ACCESS),
							PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OVR_MEMBER_STATIC),
							PHPDebugUICompositeImageDescriptor.TOP_RIGHT));
				return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_PUBLIC_ACCESS);
			}
			if (facetOwner.hasFacet(Facet.MOD_PROTECTED)) {
				if (facetOwner.hasFacet(Facet.MOD_STATIC))
					return getDebugImageRegistry().get(new PHPDebugUICompositeImageDescriptor(
							PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OBJ_MEMBER_PROTECTED_ACCESS),
							PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OVR_MEMBER_STATIC),
							PHPDebugUICompositeImageDescriptor.TOP_RIGHT));
				return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_PROTECTED_ACCESS);
			}
			if (facetOwner.hasFacet(Facet.MOD_PRIVATE)) {
				if (facetOwner.hasFacet(Facet.MOD_STATIC))
					return getDebugImageRegistry().get(new PHPDebugUICompositeImageDescriptor(
							PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OBJ_MEMBER_PRIVATE_ACCESS),
							PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OVR_MEMBER_STATIC),
							PHPDebugUICompositeImageDescriptor.TOP_RIGHT));
				return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_PRIVATE_ACCESS);
			}
		}
		if (facetOwner.hasFacet(VIRTUAL_CLASS)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_VIRTUAL_CLASS);
		}
		if (facetOwner.hasFacet(VIRTUAL_PARTITION)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_VIRTUAL_CONTAINER);
		}
		if (facetOwner.hasFacet(Facet.VIRTUAL_LENGTH)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_VIRTUAL_LENGTH);
		}
		if (facetOwner.hasFacet(Facet.VIRTUAL_UNINIT)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_VIRTUAL_UNINIT);
		}
		if (facetOwner.hasFacet(Facet.VIRTUAL_ARRAY_MEMBER)) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_MEMBER_VIRTUAL_ARRAY);
		}
		return null;
	}

	/**
	 * Returns the conditional breakpoint icon (enabled / disabled). In case the
	 * breakpoint is not conditional, return null and let the default breakpoint
	 * icon.
	 */
	protected Image getLineBreakpointImage(PHPConditionalBreakpoint breakpoint) {
		try {
			if (breakpoint.isConditionEnabled()) {
				PHPBreakpointImageDescriptor descriptor;
				if (breakpoint.isEnabled()) {
					descriptor = new PHPBreakpointImageDescriptor(
							DebugUITools.getImageDescriptor(IDebugUIConstants.IMG_OBJS_BREAKPOINT),
							PHPBreakpointImageDescriptor.CONDITIONAL | PHPBreakpointImageDescriptor.ENABLED);
				} else {
					descriptor = new PHPBreakpointImageDescriptor(
							DebugUITools.getImageDescriptor(IDebugUIConstants.IMG_OBJS_BREAKPOINT_DISABLED),
							PHPBreakpointImageDescriptor.CONDITIONAL);
				}
				return getDebugImageRegistry().get(descriptor);
			}
		} catch (CoreException e) {
			return null;
		}
		return null;
	}

	protected Image getExceptionBreakpointImage(IPHPExceptionBreakpoint breakpoint) {
		try {
			switch (breakpoint.getType()) {
			case EXCEPTION: {
				if (breakpoint.isEnabled()) {
					return PHPDebugUIImages.get(PHPDebugUIImages.IMG_ELCL_EXCEPTION_BREAKPOINT);
				} else {
					return PHPDebugUIImages.get(PHPDebugUIImages.IMG_DLCL_EXCEPTION_BREAKPOINT);
				}
			}
			case ERROR: {
				if (breakpoint.isEnabled()) {
					return PHPDebugUIImages.get(PHPDebugUIImages.IMG_ELCL_ERROR_BREAKPOINT);
				} else {
					return PHPDebugUIImages.get(PHPDebugUIImages.IMG_DLCL_ERROR_BREAKPOINT);
				}
			}
			default:
				return null;
			}

		} catch (CoreException e) {
			return null;
		}
	}

	protected ImageDescriptorRegistry getDebugImageRegistry() {
		if (fDebugImageRegistry == null) {
			fDebugImageRegistry = PHPDebugUIPlugin.getImageDescriptorRegistry();
		}
		return fDebugImageRegistry;
	}

	protected String getLineBreakpointText(ILineBreakpoint breakpoint) {
		IMarker marker = breakpoint.getMarker();
		IResource resource = marker.getResource();
		try {
			Integer lineNumber = (Integer) marker.getAttribute(IMarker.LINE_NUMBER);
			String fileName = null;
			if (resource instanceof IFile) {
				fileName = resource.getFullPath().toString();
			} else if (resource instanceof IWorkspaceRoot) {
				fileName = (String) marker.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY);
				if (fileName == null) {
					return null;
				}
				IPath path = Path.fromPortableString(fileName);
				if ((path.getDevice() == null) && (path.toString().startsWith("org.eclipse.dltk"))) { //$NON-NLS-1$
					String fullPathString = path.toString();
					String absolutePath = fullPathString.substring(fullPathString.indexOf(':') + 1);
					path = Path.fromPortableString(absolutePath);
				} else {
					path = EnvironmentPathUtils.getLocalPath(path);
				}
				NonExistingPHPFileEditorInput nonExistingEditorInput = NonExistingPHPFileEditorInput
						.findEditorInput(path);
				if (nonExistingEditorInput != null) {
					fileName = nonExistingEditorInput.getName();
				} else {
					if (EnvironmentPathUtils.isFull(path)) {
						fileName = EnvironmentPathUtils.getLocalPathString(path);
					} else {
						fileName = path.toPortableString();
					}
				}
			}
			if (fileName != null) {
				return fileName + " [line: " + lineNumber.toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return null;
	}

	protected String getExceptionBreakpointText(IPHPExceptionBreakpoint breakpoint) {
		switch (breakpoint.getType()) {
		case EXCEPTION:
			return breakpoint.getExceptionName() + PHPDebugUIMessages.PHPModelPresentation_Break_on_exception;
		case ERROR:
			return breakpoint.getExceptionName() + PHPDebugUIMessages.PHPModelPresentation_Break_on_error_condition;
		}
		return null;
	}

	protected String getTargetText(IDebugTarget target) {
		String label = ""; //$NON-NLS-1$
		if (target.isTerminated()) {
			label = MessageFormat.format(PHPDebugUIMessages.MPresentation_Terminated_1, new Object[] {});
		}
		String name = PHPDebugUIMessages.MPresentation_PHP_APP_1;
		if (target instanceof PHPMultiDebugTarget) {
			name = PHPDebugUIMessages.MPresentation_PHP_APP_1
					+ PHPDebugUIMessages.PHPModelPresentation_ParallelRequests;
		}
		return label + name;
	}

	protected String getThreadText(IThread thread) {
		StringBuilder buf = new StringBuilder();
		IDebugTarget target = (IDebugTarget) thread.getDebugTarget();
		try {
			String targetName = target.getName();
			targetName = resolveUntitledEditorName(targetName);
			buf.append(targetName);
		} catch (DebugException e) {
			// Just log should never happen
			Logger.logException(e);
		}
		if (thread.isStepping()) {
			buf.append(PHPDebugUIMessages.MPresentation_Stepping_1);
		} else if (thread.isSuspended()) {
			IBreakpoint[] breakpoints = thread.getBreakpoints();
			if (breakpoints.length == 0) {
				buf.append(PHPDebugUIMessages.MPresentation_Suspended_1);
			} else {
				IBreakpoint breakpoint = breakpoints[0]; // there can only be
				// one in PHP
				if (breakpoint instanceof PHPLineBreakpoint) {
					buf.append(PHPDebugUIMessages.MPresentation_SLineBreakpoint_1);
				} else if (breakpoint instanceof IPHPExceptionBreakpoint) {
					buf.append(PHPDebugUIMessages.MPresentation_SExceptionBreakpoint_1);
				}
			}
		} else if (thread.isTerminated()) {
			buf.append(PHPDebugUIMessages.MPresentation_Terminated_1);
		}
		return buf.toString();
	}

	protected String getStackFrameText(IStackFrame frame) {
		if (frame instanceof PHPStackFrame) {
			PHPStackFrame phpStackFrame = (PHPStackFrame) frame;
			try {
				StringBuffer buffer = new StringBuffer();
				String frameName = phpStackFrame.getName();
				if (frameName != null && frameName.length() > 0) {
					buffer.append(frameName);
					buffer.append("(): "); //$NON-NLS-1$
				}
				String sourceName = phpStackFrame.getSourceName();
				sourceName = resolveUntitledEditorName(sourceName);
				buffer.append(sourceName);
				buffer.append(PHPDebugUIMessages.MPresentation_ATLine_1 + ' ' + phpStackFrame.getLineNumber());
				return buffer.toString();
			} catch (DebugException e) {
				Logger.logException(e);
			}
		}
		return ""; //$NON-NLS-1$
	}

	protected IEditorInput getLineBreakpointEditorInput(Object element) {
		ILineBreakpoint bp = (ILineBreakpoint) element;
		IMarker marker = bp.getMarker();
		IResource resource = marker.getResource();
		if (resource instanceof IFile) {
			return new FileEditorInput((IFile) resource);
		}
		try {
			final String location = (String) marker
					.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY);
			if (location == null) {
				return null;
			}
			IPath path = Path.fromPortableString(location);
			NonExistingPHPFileEditorInput nonExistingEditorInput = NonExistingPHPFileEditorInput.findEditorInput(path);
			if (nonExistingEditorInput != null) {
				return nonExistingEditorInput;
			}
			HandleFactory fac = new HandleFactory();
			IDLTKSearchScope scope = DLTKSearchScopeFactory.getInstance().createWorkspaceScope(true,
					PHPLanguageToolkit.getDefault());
			Openable openable = fac.createOpenable(path.toString(), scope);
			if (openable instanceof IStorage) {
				return new ExternalStorageEditorInput((IStorage) openable);
			}
			// Support external files opened using File -> Open
			File localFile = new File(location);
			if (localFile.exists()) {
				return new FileStoreEditorInput(new LocalFile(localFile));
			}
		} catch (CoreException e) {
			DLTKUIPlugin.log(e);
		}
		return null;
	}

	private String resolveUntitledEditorName(String location) {
		try {
			NonExistingPHPFileEditorInput nonExistingEditorInput = NonExistingPHPFileEditorInput
					.findEditorInput(new Path(location));
			if (nonExistingEditorInput != null) {
				location = nonExistingEditorInput.getName();
			}
		} catch (Exception e) {
		}
		return location;
	}

	private void addExceptionAnnotation(IEditorPart editorPart, IStackFrame frame, IPHPExceptionBreakpoint breakpoint) {
		ITextEditor textEditor = editorPart.getAdapter(ITextEditor.class);
		IDocumentProvider docProvider = textEditor.getDocumentProvider();
		IEditorInput editorInput = textEditor.getEditorInput();
		// If there is no annotation model, there's nothing more to do
		IAnnotationModel annModel = docProvider.getAnnotationModel(editorInput);
		if (annModel == null) {
			return;
		}
		Position position = null;
		int charStart = -1;
		int length = -1;
		try {
			charStart = frame.getCharStart();
			length = frame.getCharEnd() - charStart;
		} catch (DebugException de) {
		}
		if (charStart < 0) {
			IDocument doc = docProvider.getDocument(editorInput);
			if (doc == null) {
				return;
			}
			try {
				int lineNumber = frame.getLineNumber() - 1;
				IRegion region = doc.getLineInformation(lineNumber);
				charStart = region.getOffset();
				length = region.getLength();
			} catch (BadLocationException ble) {
				return;
			} catch (DebugException de) {
				return;
			}
		}
		if (charStart < 0) {
			return;
		}
		position = new Position(charStart, length);
		if (frame.isTerminated()) {
			return;
		}
		Annotation annotation = fExceptionAnnotations.remove(frame.getThread());
		if (annotation != null) {
			annModel.removeAnnotation(annotation);
		}
		// Add/replace exception breakpoint annotation
		annotation = new PHPExceptionBreakpointAnnotation(breakpoint);
		fExceptionAnnotations.put(frame.getThread(), annotation);
		annModel.addAnnotation(annotation, position);
		// Position editor to current frame (exception line)
		positionEditor(textEditor, frame);
	}

	private void positionEditor(ITextEditor editor, IStackFrame frame) {
		try {
			int charStart = frame.getCharStart();
			if (charStart >= 0) {
				editor.selectAndReveal(charStart, 0);
				return;
			}
			int lineNumber = frame.getLineNumber();
			lineNumber--;
			IRegion region = getLineInformation(editor, lineNumber);
			if (region != null) {
				editor.selectAndReveal(region.getOffset(), 0);
			}
		} catch (DebugException e) {
		}
	}

	private IRegion getLineInformation(ITextEditor editor, int lineNumber) {
		IDocumentProvider provider = editor.getDocumentProvider();
		IEditorInput input = editor.getEditorInput();
		try {
			provider.connect(input);
		} catch (CoreException e) {
			return null;
		}
		try {
			IDocument document = provider.getDocument(input);
			if (document != null) {
				return document.getLineInformation(lineNumber);
			}
		} catch (BadLocationException e) {
		} finally {
			provider.disconnect(input);
		}
		return null;
	}

}