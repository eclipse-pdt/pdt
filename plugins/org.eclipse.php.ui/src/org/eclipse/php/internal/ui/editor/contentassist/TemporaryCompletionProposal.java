package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

/**
 * A completion proposal wrapper which re-asks for proposal after document change
 * @author seva, 2007
 */
public class TemporaryCompletionProposal implements ICompletionProposal {

	private ICompletionProposal proposal;

	public TemporaryCompletionProposal(ICompletionProposal proposal) {
		this.proposal = proposal;
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#apply(org.eclipse.jface.text.IDocument)
	 */
	public void apply(IDocument document) {
		final IWorkbenchPage page = PHPUiPlugin.getActivePage();
		final StructuredTextViewer textViewer;
		if (page != null) {
			final IEditorPart editor = page.getActiveEditor();
			if (editor instanceof PHPStructuredEditor) {
				textViewer = ((PHPStructuredEditor) editor).getTextViewer();
			} else {
				textViewer = null;
			}
		} else {
			textViewer = null;
		}
		if (textViewer != null && textViewer.getDocument() == document) {
			textViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					textViewer.removeSelectionChangedListener(this);
					final long delay = PHPCorePlugin.getDefault().getPluginPreferences().getInt(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY);
					new Timer("Temporary Completion delay").schedule(new TimerTask() {
						@Override
						public void run() {
							textViewer.getControl().getDisplay().asyncExec(new Runnable() {
								public void run() {
									BusyIndicator.showWhile(textViewer.getControl().getDisplay(), new Runnable() {
										public void run() {
											textViewer.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
										}
									});
								}
							});
						}
					}, delay);
				}
			});
		}
		proposal.apply(document);
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getAdditionalProposalInfo()
	 */
	public String getAdditionalProposalInfo() {
		return proposal.getAdditionalProposalInfo();
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getContextInformation()
	 */
	public IContextInformation getContextInformation() {
		return proposal.getContextInformation();
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getDisplayString()
	 */
	public String getDisplayString() {
		return proposal.getDisplayString();
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getImage()
	 */
	public Image getImage() {
		return proposal.getImage();
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getSelection(org.eclipse.jface.text.IDocument)
	 */
	public Point getSelection(IDocument document) {
		return proposal.getSelection(document);
	}

}