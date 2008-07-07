package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;

public abstract class PHPContentAssistInvocationContext extends ScriptContentAssistInvocationContext {

	private boolean explicit;
	
	public PHPContentAssistInvocationContext(ITextViewer viewer, int offset, IEditorPart editor, String natureId, boolean explicit) {
		super(viewer, offset, editor, natureId);
		this.explicit = explicit;
	}
	
	public boolean isExplicit() {
		return explicit;
	}
}
