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
package org.eclipse.php.internal.ui.text.correction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.dltk.ui.text.IScriptCorrectionContext;
import org.eclipse.dltk.ui.text.IScriptCorrectionProcessor;
import org.eclipse.dltk.ui.text.MarkerResolutionProposal;
import org.eclipse.dltk.ui.text.completion.CompletionProposalComparator;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.internal.ui.text.correction.proposals.ChangeCorrectionProposal;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMarkerHelpRegistry;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;

public class PHPCorrectionProcessor implements
		org.eclipse.jface.text.quickassist.IQuickAssistProcessor,
		IScriptCorrectionProcessor {

	private static final String QUICKFIX_PROCESSOR_CONTRIBUTION_ID = "quickFixProcessors"; //$NON-NLS-1$
	private static final String QUICKASSIST_PROCESSOR_CONTRIBUTION_ID = "quickAssistProcessors"; //$NON-NLS-1$

	private static ContributedProcessorDescriptor[] fgContributedAssistProcessors = null;
	private static ContributedProcessorDescriptor[] fgContributedCorrectionProcessors = null;

	private static ContributedProcessorDescriptor[] getProcessorDescriptors(
			String contributionId, boolean testMarkerTypes) {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PHPUiPlugin.ID, contributionId);
		ArrayList res = new ArrayList(elements.length);

		for (int i = 0; i < elements.length; i++) {
			ContributedProcessorDescriptor desc = new ContributedProcessorDescriptor(
					elements[i], testMarkerTypes);
			IStatus status = desc.checkSyntax();
			if (status.isOK()) {
				res.add(desc);
			} else {
				PHPUiPlugin.log(status);
			}
		}
		return (ContributedProcessorDescriptor[]) res
				.toArray(new ContributedProcessorDescriptor[res.size()]);
	}

	private static ContributedProcessorDescriptor[] getCorrectionProcessors() {
		if (fgContributedCorrectionProcessors == null) {
			fgContributedCorrectionProcessors = getProcessorDescriptors(
					QUICKFIX_PROCESSOR_CONTRIBUTION_ID, true);
		}
		return fgContributedCorrectionProcessors;
	}

	private static ContributedProcessorDescriptor[] getAssistProcessors() {
		if (fgContributedAssistProcessors == null) {
			fgContributedAssistProcessors = getProcessorDescriptors(
					QUICKASSIST_PROCESSOR_CONTRIBUTION_ID, false);
		}
		return fgContributedAssistProcessors;
	}

	public static boolean hasCorrections(ISourceModule cu, int problemId,
			String markerType) {
		ContributedProcessorDescriptor[] processors = getCorrectionProcessors();
		SafeHasCorrections collector = new SafeHasCorrections(cu, problemId);
		for (int i = 0; i < processors.length; i++) {
			if (processors[i].canHandleMarkerType(markerType)) {
				collector.process(processors[i]);
				if (collector.hasCorrections()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isQuickFixableType(Annotation annotation) {
		return (annotation instanceof IScriptAnnotation || annotation instanceof SimpleMarkerAnnotation)
				&& !annotation.isMarkedDeleted();
	}

	public static boolean hasCorrections(Annotation annotation) {
		if (annotation instanceof IScriptAnnotation) {
			IScriptAnnotation javaAnnotation = (IScriptAnnotation) annotation;
			if (javaAnnotation.getId() != null) {
				String problemId = javaAnnotation.getId().name();
				ISourceModule cu = javaAnnotation.getSourceModule();
				if (cu != null) {
					return hasCorrections(cu, Integer.parseInt(problemId),
							javaAnnotation.getMarkerType());
				}
			}

		}
		if (annotation instanceof SimpleMarkerAnnotation) {
			return hasCorrections(((SimpleMarkerAnnotation) annotation)
					.getMarker());
		}
		return false;
	}

	private static boolean hasCorrections(IMarker marker) {
		if (marker == null || !marker.exists())
			return false;

		IMarkerHelpRegistry registry = IDE.getMarkerHelpRegistry();
		return registry != null && registry.hasResolutions(marker);
	}

	public static boolean hasAssists(IInvocationContext context) {
		ContributedProcessorDescriptor[] processors = getAssistProcessors();
		SafeHasAssist collector = new SafeHasAssist(context);

		for (int i = 0; i < processors.length; i++) {
			collector.process(processors[i]);
			if (collector.hasAssists()) {
				return true;
			}
		}
		return false;
	}

	private PHPCorrectionAssistant fAssistant;
	private String fErrorMessage;

	/*
	 * Constructor for JavaCorrectionProcessor.
	 */
	public PHPCorrectionProcessor() {
	}

	protected void initAssistant(PHPStructuredTextViewer textViewer) {
		if (fAssistant != null) {
			return;
		}

		fAssistant = (PHPCorrectionAssistant) textViewer
				.getQuickAssistAssistant();

		fAssistant.addCompletionListener(new ICompletionListener() {

			public void assistSessionEnded(ContentAssistEvent event) {
				fAssistant.setStatusLineVisible(false);
			}

			public void assistSessionStarted(ContentAssistEvent event) {
				fAssistant.setStatusLineVisible(true);
				fAssistant.setStatusMessage(getJumpHintStatusLineMessage());
			}

			public void selectionChanged(ICompletionProposal proposal,
					boolean smartToggle) {
				if (proposal instanceof IStatusLineProposal) {
					IStatusLineProposal statusLineProposal = (IStatusLineProposal) proposal;
					String message = statusLineProposal.getStatusMessage();
					if (message != null) {
						fAssistant.setStatusMessage(message);
						return;
					}
				}
				fAssistant.setStatusMessage(getJumpHintStatusLineMessage());
			}

			private String getJumpHintStatusLineMessage() {
				if (fAssistant.isUpdatedOffset()) {
					String key = getQuickAssistBinding();
					if (key == null)
						return CorrectionMessages.JavaCorrectionProcessor_go_to_original_using_menu;
					else
						return NLS
								.bind(CorrectionMessages.JavaCorrectionProcessor_go_to_original_using_key,
										key);
				} else if (fAssistant.isProblemLocationAvailable()) {
					String key = getQuickAssistBinding();
					if (key == null)
						return CorrectionMessages.JavaCorrectionProcessor_go_to_closest_using_menu;
					else
						return NLS
								.bind(CorrectionMessages.JavaCorrectionProcessor_go_to_closest_using_key,
										key);
				} else
					return ""; //$NON-NLS-1$
			}

			private String getQuickAssistBinding() {
				final IBindingService bindingSvc = (IBindingService) PlatformUI
						.getWorkbench().getAdapter(IBindingService.class);
				return bindingSvc
						.getBestActiveBindingFormattedFor(ITextEditorActionDefinitionIds.QUICK_ASSIST);
			}
		});
	}

	/*
	 * @see IContentAssistProcessor#computeCompletionProposals(ITextViewer, int)
	 */
	public ICompletionProposal[] computeQuickAssistProposals(
			IQuickAssistInvocationContext quickAssistContext) {

		ICompletionProposal[] res = null;

		ISourceViewer viewer = quickAssistContext.getSourceViewer();
		if (viewer instanceof PHPStructuredTextViewer) {

			PHPStructuredTextViewer textViewer = (PHPStructuredTextViewer) viewer;
			initAssistant(textViewer);

			int documentOffset = quickAssistContext.getOffset();

			IEditorPart part = fAssistant.getEditor();

			ISourceModule cu = DLTKUIPlugin.getEditorInputModelElement(part
					.getEditorInput());
			IAnnotationModel model = DLTKUIPlugin.getDocumentProvider()
					.getAnnotationModel(part.getEditorInput());

			int length = viewer != null ? viewer.getSelectedRange().y : 0;
			AssistContext context = new AssistContext(cu, viewer, part,
					documentOffset, length, SharedASTProvider.WAIT_YES);

			Annotation[] annotations = fAssistant.getAnnotationsAtOffset();

			fErrorMessage = null;
			if (model != null && annotations != null) {
				ArrayList proposals = new ArrayList(10);
				IStatus status = collectProposals(context, model, annotations,
						true, !fAssistant.isUpdatedOffset(), proposals);
				res = (ICompletionProposal[]) proposals
						.toArray(new ICompletionProposal[proposals.size()]);
				if (!status.isOK()) {
					fErrorMessage = status.getMessage();
					PHPUiPlugin.log(status);
				}
			}
		}

		if (res == null || res.length == 0) {
			return new ICompletionProposal[] { new ChangeCorrectionProposal(
					CorrectionMessages.NoCorrectionProposal_description,
					new NullChange(""), 0, null) }; //$NON-NLS-1$
		}
		if (res.length > 1) {
			Arrays.sort(res, new CompletionProposalComparator());
		}
		return res;
	}

	public static IStatus collectProposals(IInvocationContext context,
			IAnnotationModel model, Annotation[] annotations,
			boolean addQuickFixes, boolean addQuickAssists, Collection proposals) {
		ArrayList problems = new ArrayList();

		// collect problem locations and corrections from marker annotations
		for (int i = 0; i < annotations.length; i++) {
			Annotation curr = annotations[i];
			ProblemLocation problemLocation = null;
			if (curr instanceof IScriptAnnotation) {
				problemLocation = getProblemLocation((IScriptAnnotation) curr,
						model);
				if (problemLocation != null) {
					problems.add(problemLocation);
				}
			}
			if (problemLocation == null && addQuickFixes
					&& curr instanceof SimpleMarkerAnnotation) {
				collectMarkerProposals((SimpleMarkerAnnotation) curr, proposals);
			}
		}
		MultiStatus resStatus = null;

		IProblemLocation[] problemLocations = (IProblemLocation[]) problems
				.toArray(new IProblemLocation[problems.size()]);
		if (addQuickFixes) {
			IStatus status = collectCorrections(context, problemLocations,
					proposals);
			if (!status.isOK()) {
				resStatus = new MultiStatus(
						PHPUiPlugin.ID,
						IStatus.ERROR,
						CorrectionMessages.JavaCorrectionProcessor_error_quickfix_message,
						null);
				resStatus.add(status);
			}
		}
		if (addQuickAssists) {
			IStatus status = collectAssists(context, problemLocations,
					proposals);
			if (!status.isOK()) {
				if (resStatus == null) {
					resStatus = new MultiStatus(
							PHPUiPlugin.ID,
							IStatus.ERROR,
							CorrectionMessages.JavaCorrectionProcessor_error_quickassist_message,
							null);
				}
				resStatus.add(status);
			}
		}
		if (resStatus != null) {
			return resStatus;
		}
		return Status.OK_STATUS;
	}

	private static ProblemLocation getProblemLocation(
			IScriptAnnotation javaAnnotation, IAnnotationModel model) {
		if (javaAnnotation.getId() != null) {
			Position pos = model.getPosition((Annotation) javaAnnotation);
			if (pos != null) {
				return new ProblemLocation(pos.getOffset(), pos.getLength(),
						javaAnnotation); // java problems
											// all handled
											// by the
											// quick assist
											// processors
			}
		}
		return null;
	}

	private static void collectMarkerProposals(
			SimpleMarkerAnnotation annotation, Collection proposals) {
		IMarker marker = annotation.getMarker();
		IMarkerResolution[] res = IDE.getMarkerHelpRegistry().getResolutions(
				marker);
		if (res.length > 0) {
			for (int i = 0; i < res.length; i++) {
				proposals.add(new MarkerResolutionProposal(res[i], marker));
			}
		}
	}

	private static abstract class SafeCorrectionProcessorAccess implements
			ISafeRunnable {
		private MultiStatus fMulti = null;
		private ContributedProcessorDescriptor fDescriptor;

		public void process(ContributedProcessorDescriptor[] desc) {
			for (int i = 0; i < desc.length; i++) {
				fDescriptor = desc[i];
				SafeRunner.run(this);
			}
		}

		public void process(ContributedProcessorDescriptor desc) {
			fDescriptor = desc;
			SafeRunner.run(this);
		}

		public void run() throws Exception {
			safeRun(fDescriptor);
		}

		protected abstract void safeRun(ContributedProcessorDescriptor processor)
				throws Exception;

		public void handleException(Throwable exception) {
			if (fMulti == null) {
				fMulti = new MultiStatus(
						PHPUiPlugin.ID,
						IStatus.OK,
						CorrectionMessages.JavaCorrectionProcessor_error_status,
						null);
			}
			fMulti.merge(new Status(IStatus.ERROR, PHPUiPlugin.ID,
					IStatus.ERROR,
					CorrectionMessages.JavaCorrectionProcessor_error_status,
					exception));
		}

		public IStatus getStatus() {
			if (fMulti == null) {
				return Status.OK_STATUS;
			}
			return fMulti;
		}

	}

	private static class SafeCorrectionCollector extends
			SafeCorrectionProcessorAccess {
		private final IInvocationContext fContext;
		private final Collection fProposals;
		private IProblemLocation[] fLocations;

		public SafeCorrectionCollector(IInvocationContext context,
				Collection proposals) {
			fContext = context;
			fProposals = proposals;
		}

		public void setProblemLocations(IProblemLocation[] locations) {
			fLocations = locations;
		}

		public void safeRun(ContributedProcessorDescriptor desc)
				throws Exception {
			IQuickFixProcessor curr = (IQuickFixProcessor) desc.getProcessor(
					fContext.getCompilationUnit(), IQuickFixProcessor.class);
			if (curr != null) {
				IScriptCompletionProposal[] res = curr.getCorrections(fContext,
						fLocations);
				if (res != null) {
					for (int k = 0; k < res.length; k++) {
						fProposals.add(res[k]);
					}
				}
			}
		}
	}

	private static class SafeAssistCollector extends
			SafeCorrectionProcessorAccess {
		private final IInvocationContext fContext;
		private final IProblemLocation[] fLocations;
		private final Collection fProposals;

		public SafeAssistCollector(IInvocationContext context,
				IProblemLocation[] locations, Collection proposals) {
			fContext = context;
			fLocations = locations;
			fProposals = proposals;
		}

		public void safeRun(ContributedProcessorDescriptor desc)
				throws Exception {
			IQuickAssistProcessor curr = (IQuickAssistProcessor) desc
					.getProcessor(fContext.getCompilationUnit(),
							IQuickAssistProcessor.class);
			if (curr != null) {
				IScriptCompletionProposal[] res = curr.getAssists(fContext,
						fLocations);
				if (res != null) {
					for (int k = 0; k < res.length; k++) {
						fProposals.add(res[k]);
					}
				}
			}
		}
	}

	private static class SafeHasAssist extends SafeCorrectionProcessorAccess {
		private final IInvocationContext fContext;
		private boolean fHasAssists;

		public SafeHasAssist(IInvocationContext context) {
			fContext = context;
			fHasAssists = false;
		}

		public boolean hasAssists() {
			return fHasAssists;
		}

		public void safeRun(ContributedProcessorDescriptor desc)
				throws Exception {
			IQuickAssistProcessor processor = (IQuickAssistProcessor) desc
					.getProcessor(fContext.getCompilationUnit(),
							IQuickAssistProcessor.class);
			if (processor != null && processor.hasAssists(fContext)) {
				fHasAssists = true;
			}
		}
	}

	private static class SafeHasCorrections extends
			SafeCorrectionProcessorAccess {
		private final ISourceModule fCu;
		private final int fProblemId;
		private boolean fHasCorrections;

		public SafeHasCorrections(ISourceModule cu, int problemId) {
			fCu = cu;
			fProblemId = problemId;
			fHasCorrections = false;
		}

		public boolean hasCorrections() {
			return fHasCorrections;
		}

		public void safeRun(ContributedProcessorDescriptor desc)
				throws Exception {
			IQuickFixProcessor processor = (IQuickFixProcessor) desc
					.getProcessor(fCu, IQuickFixProcessor.class);
			if (processor != null && processor.hasCorrections(fCu, fProblemId)) {
				fHasCorrections = true;
			}
		}
	}

	public static IStatus collectCorrections(IInvocationContext context,
			IProblemLocation[] locations, Collection proposals) {
		ContributedProcessorDescriptor[] processors = getCorrectionProcessors();
		SafeCorrectionCollector collector = new SafeCorrectionCollector(
				context, proposals);
		for (int i = 0; i < processors.length; i++) {
			ContributedProcessorDescriptor curr = processors[i];
			IProblemLocation[] handled = getHandledProblems(locations, curr);
			if (handled != null) {
				collector.setProblemLocations(handled);
				collector.process(curr);
			}
		}
		return collector.getStatus();
	}

	private static IProblemLocation[] getHandledProblems(
			IProblemLocation[] locations,
			ContributedProcessorDescriptor processor) {
		// implementation tries to avoid creating a new array
		boolean allHandled = true;
		ArrayList res = null;
		for (int i = 0; i < locations.length; i++) {
			IProblemLocation curr = locations[i];
			if (processor.canHandleMarkerType(curr.getMarkerType())) {
				if (!allHandled) { // first handled problem
					if (res == null) {
						res = new ArrayList(locations.length - i);
					}
					res.add(curr);
				}
			} else if (allHandled) {
				if (i > 0) { // first non handled problem
					res = new ArrayList(locations.length - i);
					for (int k = 0; k < i; k++) {
						res.add(locations[k]);
					}
				}
				allHandled = false;
			}
		}
		if (allHandled) {
			return locations;
		}
		if (res == null) {
			return null;
		}
		return (IProblemLocation[]) res
				.toArray(new IProblemLocation[res.size()]);
	}

	public static IStatus collectAssists(IInvocationContext context,
			IProblemLocation[] locations, Collection proposals) {
		ContributedProcessorDescriptor[] processors = getAssistProcessors();
		SafeAssistCollector collector = new SafeAssistCollector(context,
				locations, proposals);
		collector.process(processors);

		return collector.getStatus();
	}

	/*
	 * @see IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		return fErrorMessage;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.quickassist.IQuickAssistProcessor#canFix(org.eclipse
	 * .jface.text.source.Annotation)
	 * 
	 * @since 3.2
	 */
	public boolean canFix(Annotation annotation) {
		return hasCorrections(annotation);
	}

	/*
	 * @see
	 * org.eclipse.jface.text.quickassist.IQuickAssistProcessor#canAssist(org
	 * .eclipse.jface.text.quickassist.IQuickAssistInvocationContext)
	 * 
	 * @since 3.2
	 */
	public boolean canAssist(IQuickAssistInvocationContext invocationContext) {
		if (invocationContext instanceof IInvocationContext)
			return hasAssists((IInvocationContext) invocationContext);
		return false;
	}

	public boolean canFix(IScriptAnnotation annotation) {
		if (annotation instanceof Annotation) {
			return canFix((Annotation) annotation);
		}
		return false;
	}

	public boolean canFix(IMarker marker) {
		return false;
	}

	public void computeQuickAssistProposals(IScriptAnnotation annotation,
			IScriptCorrectionContext context) {
	}

	public void computeQuickAssistProposals(IMarker marker,
			IScriptCorrectionContext context) {
	}
}
