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
package org.eclipse.php.internal.ui.editor.hover;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.internal.ui.editor.contentassist.CompletionProposalComparator;
import org.eclipse.php.internal.ui.text.correction.*;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.MarkerAnnotation;

public class ProblemHover extends AbstractAnnotationHover implements IPHPTextHover {

	protected static class ProblemInfo extends AnnotationInfo {

		private static final ICompletionProposal[] NO_PROPOSALS = new ICompletionProposal[0];

		public ProblemInfo(Annotation annotation, Position position, ITextViewer textViewer) {
			super(annotation, position, textViewer);
		}

		@Override
		public ICompletionProposal[] getCompletionProposals() {
			if (annotation instanceof IScriptAnnotation) {
				ICompletionProposal[] result = getScriptAnnotationFixes((IScriptAnnotation) annotation);
				if (result.length > 0)
					return result;
			}

			if (annotation instanceof MarkerAnnotation)
				return getMarkerAnnotationFixes((MarkerAnnotation) annotation);

			return NO_PROPOSALS;
		}

		@SuppressWarnings("unchecked")
		private ICompletionProposal[] getScriptAnnotationFixes(IScriptAnnotation scriptAnnotation) {
			ProblemLocation location = new ProblemLocation(position.getOffset(), position.getLength(),
					scriptAnnotation);
			ISourceModule cu = scriptAnnotation.getSourceModule();
			if (cu == null)
				return NO_PROPOSALS;

			ISourceViewer sourceViewer = null;
			if (viewer instanceof ISourceViewer)
				sourceViewer = (ISourceViewer) viewer;

			IInvocationContext context = new AssistContext(cu, sourceViewer, location.getOffset(), location.getLength(),
					SharedASTProvider.WAIT_ACTIVE_ONLY);

			ArrayList<IScriptCompletionProposal> proposals = new ArrayList<IScriptCompletionProposal>();
			PHPCorrectionProcessor.collectCorrections(context, new IProblemLocation[] { location }, proposals);
			Collections.sort(proposals, new CompletionProposalComparator());

			return proposals.toArray(new ICompletionProposal[proposals.size()]);
		}

		private ICompletionProposal[] getMarkerAnnotationFixes(MarkerAnnotation markerAnnotation) {
			if (markerAnnotation.isQuickFixableStateSet() && !markerAnnotation.isQuickFixable())
				return NO_PROPOSALS;

			IMarker marker = markerAnnotation.getMarker();

			ISourceModule cu = getSourceModule(marker);
			if (cu == null)
				return NO_PROPOSALS;

			IEditorInput input = EditorUtility.getEditorInput(cu);
			if (input == null)
				return NO_PROPOSALS;

			IAnnotationModel model = DLTKUIPlugin.getDocumentProvider().getAnnotationModel(input);
			if (model == null)
				return NO_PROPOSALS;

			ISourceViewer sourceViewer = null;
			if (viewer instanceof ISourceViewer)
				sourceViewer = (ISourceViewer) viewer;

			AssistContext context = new AssistContext(cu, sourceViewer, position.getOffset(), position.getLength());

			ArrayList<IScriptCompletionProposal> proposals = new ArrayList<IScriptCompletionProposal>();
			PHPCorrectionProcessor.collectProposals(context, model, new Annotation[] { markerAnnotation }, true, false,
					proposals);

			return proposals.toArray(new ICompletionProposal[proposals.size()]);
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

	}

	public ProblemHover() {
		super(false);
	}

	@Override
	protected AnnotationInfo createAnnotationInfo(Annotation annotation, Position position, ITextViewer textViewer) {
		return new ProblemInfo(annotation, position, textViewer);
	}

	@Override
	public IHoverMessageDecorator getMessageDecorator() {
		// TODO Auto-generated method stub
		return null;
	}

}
