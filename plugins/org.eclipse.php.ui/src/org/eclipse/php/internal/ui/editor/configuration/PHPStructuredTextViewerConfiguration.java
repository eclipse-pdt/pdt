/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.configuration;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.format.PhpFormatProcessorImpl;
import org.eclipse.php.internal.core.util.WeakPropertyChangeListener;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.php.internal.ui.doubleclick.PHPDoubleClickStrategy;
import org.eclipse.php.internal.ui.editor.PHPCodeHyperlinkDetector;
import org.eclipse.php.internal.ui.editor.contentassist.PHPContentAssistProcessor;
import org.eclipse.php.internal.ui.editor.contentassist.PHPDocContentAssistProcessor;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.php.internal.ui.editor.hover.PHPTextHoverProxy;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.php.internal.ui.util.ElementCreationProxy;
import org.eclipse.php.ui.editor.hover.IHyperlinkDetectorForPHP;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.wst.html.core.internal.text.StructuredTextPartitionerForHTML;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.ui.StructuredTextViewerConfigurationHTML;
import org.eclipse.wst.sse.core.text.IStructuredPartitions;
import org.eclipse.wst.sse.ui.internal.contentassist.StructuredContentAssistant;
import org.eclipse.wst.sse.ui.internal.format.StructuredFormattingStrategy;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;
import org.eclipse.wst.xml.core.internal.text.rules.StructuredTextPartitionerForXML;

public class PHPStructuredTextViewerConfiguration extends StructuredTextViewerConfigurationHTML {

	private String[] configuredContentTypes;
	private LineStyleProvider fLineStyleProvider;
	private IPropertyChangeListener propertyChangeListener;

	private ArrayList processors = new ArrayList();
	private ArrayList detectors = new ArrayList();

	public PHPStructuredTextViewerConfiguration() {

		String processorsExtensionName = "org.eclipse.php.ui.phpContentAssistProcessor";
		
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(processorsExtensionName);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("processor")) {
				ElementCreationProxy ecProxy = new ElementCreationProxy(element, processorsExtensionName);
				IContentAssistProcessor processor = (IContentAssistProcessor)ecProxy.getObject();
				if (processor != null) {
					processors.add(processor);
				}
			}
		}

		detectors.add(new PHPCodeHyperlinkDetector());
		String detectorsExtensionName = "org.eclipse.php.ui.phpHyperlinkDetector";
		elements = Platform.getExtensionRegistry().getConfigurationElementsFor(detectorsExtensionName);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("detector")) {
				ElementCreationProxy ecProxy = new ElementCreationProxy(element, detectorsExtensionName);
				IHyperlinkDetectorForPHP detector = (IHyperlinkDetectorForPHP)ecProxy.getObject();
				if (detector != null) {
					detectors.add(detector);
				}
			}
		}

	}

	/*
	 * Returns an array of all the contentTypes (partition names) supported
	 * by this editor. They include all those supported by HTML editor plus
	 * PHP.
	 */
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		if (configuredContentTypes == null) {
			String[] phpTypes = PHPStructuredTextPartitioner.getConfiguredContentTypes();
			String[] xmlTypes = StructuredTextPartitionerForXML.getConfiguredContentTypes();
			String[] htmlTypes = StructuredTextPartitionerForHTML.getConfiguredContentTypes();
			configuredContentTypes = new String[2 + phpTypes.length + xmlTypes.length + htmlTypes.length];

			configuredContentTypes[0] = IStructuredPartitions.DEFAULT_PARTITION;
			configuredContentTypes[1] = IStructuredPartitions.UNKNOWN_PARTITION;

			int index = 0;
			System.arraycopy(phpTypes, 0, configuredContentTypes, index += 2, phpTypes.length);
			System.arraycopy(xmlTypes, 0, configuredContentTypes, index += phpTypes.length, xmlTypes.length);
			System.arraycopy(htmlTypes, 0, configuredContentTypes, index += xmlTypes.length, htmlTypes.length);
		}

		return configuredContentTypes;
	}

	public LineStyleProvider getLineStyleProvider() {
		if (fLineStyleProvider == null) {
			fLineStyleProvider = new LineStyleProviderForPhp();
		}
		return fLineStyleProvider;
	}

	public LineStyleProvider[] getLineStyleProviders(ISourceViewer sourceViewer, String partitionType) {
		if (partitionType == PHPPartitionTypes.PHP_DEFAULT) {
			return new LineStyleProvider[] { getLineStyleProvider() };
		}
		return super.getLineStyleProviders(sourceViewer, partitionType);
	}

	protected IContentAssistProcessor[] getContentAssistProcessors(ISourceViewer sourceViewer, String partitionType) {
		IContentAssistProcessor[] processors = null;

		ArrayList processorsList = getProcessorsForPartition(partitionType);

		if (partitionType == PHPPartitionTypes.PHP_DEFAULT){
			processorsList.add(new PHPContentAssistProcessor());
			processorsList.add(new PHPDocContentAssistProcessor());
		} else {
			processors = super.getContentAssistProcessors(sourceViewer, partitionType);
		}

		if (processors == null) {
			processors = new IContentAssistProcessor[processorsList.size()];
			processorsList.toArray(processors);
		}

		configureContentAssistant(sourceViewer);

		return processors;
	}

	private ArrayList getProcessorsForPartition(String partitionType) {
		if (partitionType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
			return processors;
		}
		return new ArrayList(0);
	}

	protected void setupPropertyChangeListener(final ISourceViewer sourceViewer) {
		if (propertyChangeListener == null) {
			propertyChangeListener = new IPropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent event) {
					String property = event.getProperty();
					if (PreferenceConstants.CODEASSIST_AUTOINSERT.equals(property) || PreferenceConstants.CODEASSIST_AUTOACTIVATION.equals(property) || PreferenceConstants.CODEASSIST_AUTOACTIVATION_DELAY.equals(property)) {
						configureContentAssistant(sourceViewer);
					}
				}
			};
			PreferenceConstants.getPreferenceStore().addPropertyChangeListener(WeakPropertyChangeListener.create(propertyChangeListener, PreferenceConstants.getPreferenceStore()));
		}
	}

	protected void configureContentAssistant(ISourceViewer sourceViewer) {
		setupPropertyChangeListener(sourceViewer);

		IContentAssistant contentAssistant = getPHPContentAssistant(sourceViewer);
		if (contentAssistant instanceof StructuredContentAssistant) {
			StructuredContentAssistant structuredContentAssistant = (StructuredContentAssistant) contentAssistant;
			structuredContentAssistant.enableAutoInsert(PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_AUTOINSERT));
			structuredContentAssistant.enableAutoActivation(PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_AUTOACTIVATION));
			structuredContentAssistant.setAutoActivationDelay(PreferenceConstants.getPreferenceStore().getInt(PreferenceConstants.CODEASSIST_AUTOACTIVATION_DELAY));
		}
	}

	
	private StructuredContentAssistant fContentAssistant = null;
	
	   public IContentAssistant getPHPContentAssistant(ISourceViewer sourceViewer) {
			if (fContentAssistant == null) {
				fContentAssistant = getPHPContentAssistantExtension();
				if(fContentAssistant == null){
					fContentAssistant = new StructuredContentAssistant();
				}
				// content assistant configurations
				fContentAssistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
				fContentAssistant.enableAutoActivation(true);
				fContentAssistant.setAutoActivationDelay(500);
				fContentAssistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
				fContentAssistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
				fContentAssistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));

				// add content assist processors for each partition type
				String[] types = getConfiguredContentTypes(sourceViewer);
				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					// add all content assist processors for current partiton type
					IContentAssistProcessor[] processors = getContentAssistProcessors(sourceViewer, type);
					if (processors != null) {
						for (int j = 0; j < processors.length; j++) {
							fContentAssistant.setContentAssistProcessor(processors[j], type);
						}
					}
				}
			}
			return fContentAssistant;
		}
	   
	   private StructuredContentAssistant getPHPContentAssistantExtension(){
		   StructuredContentAssistant rv = null;
			String extensionName = "org.eclipse.php.ui.phpContentAssistant";
			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionName);
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement element = elements[i];
				if (element.getName().equals("contentAssistant")) {
					ElementCreationProxy ecProxy = new ElementCreationProxy(element, extensionName);
					StructuredContentAssistant contentAssistant = (StructuredContentAssistant)ecProxy.getObject();
					if (contentAssistant != null) {
						rv  = contentAssistant;
					}
				}
			}
		   return rv;
	   }
	   
		public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
		return new String[] { "//", "#", "" }; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/*
	 * @see SourceViewerConfiguration#getConfiguredTextHoverStateMasks(ISourceViewer, String)
	 */
	public int[] getConfiguredTextHoverStateMasks(ISourceViewer sourceViewer, String contentType) {
		PHPEditorTextHoverDescriptor[] hoverDescs = PHPUiPlugin.getDefault().getPHPEditorTextHoverDescriptors();
		int stateMasks[] = new int[hoverDescs.length];
		int stateMasksLength = 0;
		for (int i = 0; i < hoverDescs.length; i++) {
			if (hoverDescs[i].isEnabled()) {
				int j = 0;
				int stateMask = hoverDescs[i].getStateMask();
				while (j < stateMasksLength) {
					if (stateMasks[j] == stateMask)
						break;
					j++;
				}
				if (j == stateMasksLength)
					stateMasks[stateMasksLength++] = stateMask;
			}
		}
		if (stateMasksLength == hoverDescs.length)
			return stateMasks;

		int[] shortenedStateMasks = new int[stateMasksLength];
		System.arraycopy(stateMasks, 0, shortenedStateMasks, 0, stateMasksLength);
		return shortenedStateMasks;
	}

	/*
	 * @see SourceViewerConfiguration#getTextHover(ISourceViewer, String, int)
	 */
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
		// Screen out any non-PHP content
		if (!PHPStructuredTextPartitioner.isPHPPartitionType(contentType)) {
			return super.getTextHover(sourceViewer, contentType, stateMask);
		}

		PHPEditorTextHoverDescriptor[] hoverDescs = PHPUiPlugin.getDefault().getPHPEditorTextHoverDescriptors();
		int i = 0;
		while (i < hoverDescs.length) {
			if (hoverDescs[i].isEnabled() && hoverDescs[i].getStateMask() == stateMask) {
				return new PHPTextHoverProxy(hoverDescs[i], null);
			}
			i++;
		}

		return null;
	}

	/*
	 * @see SourceViewerConfiguration#getTextHover(ISourceViewer, String)
	 */
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return getTextHover(sourceViewer, contentType, ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
	}

	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (!fPreferenceStore.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_HYPERLINKS_ENABLED))
			return null;

		IHyperlinkDetector[] inheritedDetectors = super.getHyperlinkDetectors(sourceViewer);

		int inheritedDetectorsLength = inheritedDetectors != null ? inheritedDetectors.length : 0;
		IHyperlinkDetector[] detectors = new IHyperlinkDetector[inheritedDetectorsLength + this.detectors.size()];
		this.detectors.toArray(detectors);
		System.arraycopy(inheritedDetectors, 0, detectors, this.detectors.size(), inheritedDetectorsLength);

		return detectors;
	}

	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		final MultiPassContentFormatter formatter = new MultiPassContentFormatter(getConfiguredDocumentPartitioning(sourceViewer), IHTMLPartitions.HTML_DEFAULT);
		formatter.setMasterStrategy(new StructuredFormattingStrategy(new PhpFormatProcessorImpl()));
		return formatter;
	}

	private static IAutoEditStrategy mainAutoEditStrategy = new MainAutoEditStrategy();

	private static IAutoEditStrategy[] phpStrategies = new IAutoEditStrategy[] { mainAutoEditStrategy };

	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		if (contentType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
			return phpStrategies;
		}
		return super.getAutoEditStrategies(sourceViewer, contentType);
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (contentType == PHPPartitionTypes.PHP_DEFAULT) {
			// use php's doubleclick strategy
			return new PHPDoubleClickStrategy();
		} else
			return super.getDoubleClickStrategy(sourceViewer, contentType);
	}
}
