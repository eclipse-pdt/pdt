package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

public class AutoActivationTrigger implements ISelectionChangedListener {

	private StructuredTextViewer textViewer;
	
	private AutoActivationTrigger(StructuredTextViewer textViewer) {
		this.textViewer = textViewer;
	}
	
	public static void register(IDocument document) {
		StructuredTextViewer textViewer = null;
		IWorkbenchPage page = PHPUiPlugin.getActivePage();
		if (page != null) {
			IEditorPart editor = page.getActiveEditor();
			if (editor instanceof PHPStructuredEditor) {
				textViewer = ((PHPStructuredEditor) editor).getTextViewer();
			}
		}
		if (textViewer != null && textViewer.getDocument() == document) {
			textViewer.addSelectionChangedListener(new AutoActivationTrigger(textViewer));
		}
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		textViewer.removeSelectionChangedListener(this);
		
		final long delay = Platform.getPreferencesService().getLong(PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY, 200, null);
		new Timer("Temporary Completion Delay").schedule(new TimerTask() {
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
}
