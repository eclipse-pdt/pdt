package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProcessor;
import org.eclipse.php.internal.ui.editor.contentassist.PHPContentAssistant;
import org.eclipse.php.internal.ui.editor.hover.PHPTextHoverProxy;
import org.eclipse.php.internal.ui.editor.hyperlink.PHPHyperlinkDetector;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.tm4e.ui.text.TMPresentationReconciler;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

public class SimplePHPEditor extends TextEditor {

	public SimplePHPEditor() {
		super();
		setSourceViewerConfiguration(new TextSourceViewerConfiguration() {
			@Override
			public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
				return new TMPresentationReconciler();
			}

			@Override
			public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
				return new String[] { "org.eclipse.php.PHP_DEFAULT" };
			}

			@Override
			public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
				return "org.eclipse.wst.sse.core.default_structured_text_partitioning";
			}

			@Override
			public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
				PHPContentAssistant assistant = new PHPContentAssistant();
				assistant.setDocumentPartitioning(this.getConfiguredDocumentPartitioning(sourceViewer));
				assistant.addContentAssistProcessor(
						new PHPCompletionProcessor(SimplePHPEditor.this, assistant, "org.eclipse.php.PHP_DEFAULT"),
						"org.eclipse.php.PHP_DEFAULT");
				return assistant;
			}

			@Override
			public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
				return new IHyperlinkDetector[] { new PHPHyperlinkDetector(SimplePHPEditor.this) };
			}

			@Override
			public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
				return getTextHover(sourceViewer, contentType);
			}

			@Override
			public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
				PHPEditorTextHoverDescriptor[] hoverDescs = PHPUiPlugin.getDefault().getPHPEditorTextHoverDescriptors();
				int i = 0;
				while (i < hoverDescs.length) {
					if (hoverDescs[i].isEnabled()) {
						return new PHPTextHoverProxy(hoverDescs[i], SimplePHPEditor.this, null);
					}
					i++;
				}

				return null;
			}

		});
	}

}
