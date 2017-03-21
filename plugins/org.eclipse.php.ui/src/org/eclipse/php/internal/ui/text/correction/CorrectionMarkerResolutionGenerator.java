/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Kaloyan Raev - Bug 485250 - Quick fixes in the Problems view
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.DefaultProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.editor.ScriptMarkerAnnotation;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.contentassist.CompletionProposalComparator;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.*;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class CorrectionMarkerResolutionGenerator implements IMarkerResolutionGenerator2 {

	public static class CorrectionMarkerResolution implements IMarkerResolution2 {

		private ISourceModule fSourceModule;
		private int fOffset;
		private int fLength;
		private IScriptCompletionProposal fProposal;

		/**
		 * Constructor for CorrectionMarkerResolution.
		 * 
		 * @param cu
		 *            the source module
		 * @param offset
		 *            the offset
		 * @param length
		 *            the length
		 * @param proposal
		 *            the proposal for the given marker
		 * @param marker
		 *            the marker to fix
		 */
		public CorrectionMarkerResolution(ISourceModule cu, int offset, int length,
				IScriptCompletionProposal proposal) {
			fSourceModule = cu;
			fOffset = offset;
			fLength = length;
			fProposal = proposal;
		}

		@Override
		public String getLabel() {
			return fProposal.getDisplayString();
		}

		@Override
		public void run(IMarker marker) {
			try {
				IEditorPart part = EditorUtility.isOpenInEditor(fSourceModule);
				if (part == null) {
					part = DLTKUIPlugin.openInEditor(fSourceModule, true, false);
					if (part instanceof ITextEditor) {
						((ITextEditor) part).selectAndReveal(fOffset, fLength);
					}
				}
				if (part != null) {
					IEditorInput input = part.getEditorInput();
					IDocument doc = DLTKUIPlugin.getDocumentProvider().getDocument(input);
					fProposal.apply(doc);
				}
			} catch (CoreException e) {
				PHPUiPlugin.log(e);
			}
		}

		@Override
		public String getDescription() {
			return fProposal.getAdditionalProposalInfo();
		}

		@Override
		public Image getImage() {
			return fProposal.getImage();
		}

	}

	private static final IMarkerResolution[] NO_RESOLUTIONS = new IMarkerResolution[0];

	/**
	 * Constructor for CorrectionMarkerResolutionGenerator.
	 */
	public CorrectionMarkerResolutionGenerator() {
		super();
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		return internalGetResolutions(marker);
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return internalHasResolutions(marker);
	}

	private static boolean internalHasResolutions(IMarker marker) {
		String id = marker.getAttribute(IModelMarker.ID, "");
		IProblemIdentifier problemId = DefaultProblemIdentifier.decode(id);
		ISourceModule cu = getSourceModule(marker);
		return cu != null && problemId != null
				&& PHPCorrectionProcessor.hasCorrections(cu, problemId, MarkerUtilities.getMarkerType(marker));
	}

	@SuppressWarnings("unchecked")
	private static IMarkerResolution[] internalGetResolutions(IMarker marker) {
		if (!internalHasResolutions(marker)) {
			return NO_RESOLUTIONS;
		}

		ISourceModule cu = getSourceModule(marker);
		if (cu != null) {
			IEditorInput input = EditorUtility.getEditorInput(cu);
			if (input != null) {
				IProblemLocation location = findProblemLocation(input, marker);
				if (location != null) {

					IInvocationContext context = new AssistContext(cu, location.getOffset(), location.getLength());

					List<IScriptCompletionProposal> proposals = new ArrayList<IScriptCompletionProposal>();
					PHPCorrectionProcessor.collectCorrections(context, new IProblemLocation[] { location }, proposals);
					Collections.sort(proposals, new CompletionProposalComparator());

					int nProposals = proposals.size();
					IMarkerResolution[] resolutions = new IMarkerResolution[nProposals];
					for (int i = 0; i < nProposals; i++) {
						resolutions[i] = new CorrectionMarkerResolution(context.getCompilationUnit(),
								location.getOffset(), location.getLength(), proposals.get(i));
					}
					return resolutions;
				}
			}
		}
		return NO_RESOLUTIONS;
	}

	private static ISourceModule getSourceModule(IMarker marker) {
		IResource res = marker.getResource();
		if (res instanceof IFile && res.isAccessible()) {
			IModelElement element = DLTKCore.create((IFile) res);
			if (element instanceof ISourceModule)
				return (ISourceModule) element;
		}
		return null;
	}

	private static IProblemLocation findProblemLocation(IEditorInput input, IMarker marker) {
		IAnnotationModel model = DLTKUIPlugin.getDocumentProvider().getAnnotationModel(input);
		if (model != null) { // open in editor
			Iterator<Annotation> iter = model.getAnnotationIterator();
			while (iter.hasNext()) {
				Annotation curr = iter.next();
				if (curr instanceof ScriptMarkerAnnotation) {
					ScriptMarkerAnnotation annot = (ScriptMarkerAnnotation) curr;
					if (marker.equals(annot.getMarker())) {
						Position pos = model.getPosition(annot);
						if (pos != null) {
							return new ProblemLocation(pos.getOffset(), pos.getLength(), annot);
						}
					}
				}
			}
		} else { // not open in editor
			ISourceModule cu = getSourceModule(marker);
			return createFromMarker(marker, cu);
		}
		return null;
	}

	private static IProblemLocation createFromMarker(IMarker marker, ISourceModule cu) {
		try {
			String id = marker.getAttribute(IModelMarker.ID, "");
			IProblemIdentifier problemId = DefaultProblemIdentifier.decode(id);
			int start = marker.getAttribute(IMarker.CHAR_START, -1);
			int end = marker.getAttribute(IMarker.CHAR_END, -1);
			int severity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			String[] arguments = CorrectionEngine.getProblemArguments(marker);
			String markerType = marker.getType();
			if (cu != null && problemId != null && start != -1 && end != -1) {
				boolean isError = (severity == IMarker.SEVERITY_ERROR);
				return new ProblemLocation(start, end - start, problemId, arguments, isError, markerType);
			}
		} catch (CoreException e) {
			PHPUiPlugin.log(e);
		}
		return null;
	}

}
