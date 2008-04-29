package org.eclipse.php.ui.editor.contentassist;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * processors who implement this interface has the abilty to allow recieving a heads up about the 
 * ISourceViewer.CONTENTASSIST_PROPOSALS operation; notifing the processors that the next request for completion is an explicit
 * request and not triggers request. 
 */
public interface IContentAssistProcessorForPHP extends IContentAssistProcessor {

	public void explicitActivationRequest();
	
	public IContentAssistSupport createContentAssistSupport();
	
	public void handlePreferenceStoreChanged(PropertyChangeEvent event);
}
