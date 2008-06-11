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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.internal.ui.typehierarchy.HierarchyInformationControl;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;
import org.eclipse.php.internal.core.format.PhpFormatProcessorImpl;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.autoEdit.CloseTagAutoEditStrategyPHP;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.php.internal.ui.doubleclick.PHPDoubleClickStrategy;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProcessor;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.php.internal.ui.editor.hover.PHPTextHoverProxy;
import org.eclipse.php.internal.ui.editor.hyperlink.PHPHyperlinkDetector;
import org.eclipse.php.internal.ui.text.PHPElementProvider;
import org.eclipse.php.internal.ui.text.PHPInformationElementProvider;
import org.eclipse.php.internal.ui.text.PHPOutlineInformationControl;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.php.internal.ui.util.ElementCreationProxy;
import org.eclipse.php.ui.editor.hover.IHyperlinkDetectorForPHP;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
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

	private HashMap<String, ArrayList<IContentAssistProcessor>> processorsCache = new HashMap<String, ArrayList<IContentAssistProcessor>>();
	private static final String PHP_CONTENT_ASSISTANT_EXT = "org.eclipse.php.ui.phpContentAssistant"; //$NON-NLS-1$
	private static final String CONTENT_ASSIST_PROCESSOR_EXT = "org.eclipse.php.ui.phpContentAssistProcessor"; //$NON-NLS-1$
	private static final String HYPERLINK_DETECTOR_EXT = "org.eclipse.php.ui.phpHyperlinkDetector"; //$NON-NLS-1$
	private static final String FORMATTER_PROCESSOR_EXT = "org.eclipse.php.ui.phpFormatterProcessor"; //$NON-NLS-1$
	private static final String SHOW_OUTLINE_PREF_KEY = "org.eclipse.php.ui.edit.text.php.show.outline"; //$NON-NLS-1$
	private static final String EMPTY = ""; //$NON-NLS-1$
	private static final String[] DEFAULT_PREFIXES = new String[] { "//", "#", EMPTY }; //$NON-NLS-1$
	private static final IAutoEditStrategy mainAutoEditStrategy = new MainAutoEditStrategy();
	private static final IAutoEditStrategy closeTagAutoEditStrategy = new CloseTagAutoEditStrategyPHP();
	private static final IAutoEditStrategy[] phpStrategies = new IAutoEditStrategy[] { mainAutoEditStrategy };

	private String[] configuredContentTypes;
	private LineStyleProvider fLineStyleProvider;
	private StructuredContentAssistant fContentAssistant = null;

	public PHPStructuredTextViewerConfiguration() {
	}

	/*
	 * Returns an array of all the contentTypes (partition names) supported by
	 * this editor. They include all those supported by HTML editor plus PHP.
	 */
	@Override
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

	@Override
	public LineStyleProvider[] getLineStyleProviders(ISourceViewer sourceViewer, String partitionType) {
		if (partitionType == PHPPartitionTypes.PHP_DEFAULT) {
			return new LineStyleProvider[] { getLineStyleProvider() };
		}
		return super.getLineStyleProviders(sourceViewer, partitionType);
	}

	@Override
	public IContentAssistProcessor[] getContentAssistProcessors(ISourceViewer sourceViewer, String partitionType) {
		IContentAssistProcessor[] processors = null;

		if (partitionType == PHPPartitionTypes.PHP_DEFAULT) {
			ArrayList<IContentAssistProcessor> processorsList = getPHPProcessors(partitionType, (PHPStructuredTextViewer) sourceViewer);
			processors = new IContentAssistProcessor[processorsList.size()];
			processorsList.toArray(processors);
		} else {
			ArrayList<IContentAssistProcessor> phpDocProcessors = getPHPProcessors(partitionType, (PHPStructuredTextViewer) sourceViewer);
			IContentAssistProcessor[] superProcessors = super.getContentAssistProcessors(sourceViewer, partitionType);
			if (superProcessors != null) {
				for (IContentAssistProcessor processor : superProcessors) {
					phpDocProcessors.add(processor);
				}
			}
			processors = new IContentAssistProcessor[phpDocProcessors.size()];
			phpDocProcessors.toArray(processors);
		}
		return processors;
	}

	private ArrayList<IContentAssistProcessor> getPHPProcessors(String partitionType, PHPStructuredTextViewer viewer) {
		if (processorsCache.get(partitionType) != null) {
			return processorsCache.get(partitionType);
		}
		ArrayList<IContentAssistProcessor> processors = new ArrayList<IContentAssistProcessor>();
		processors.add(new PHPCompletionProcessor(viewer.getTextEditor(), (ContentAssistant) getPHPContentAssistant(viewer), PHPPartitionTypes.PHP_DEFAULT));

		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(CONTENT_ASSIST_PROCESSOR_EXT);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("processor")) { //$NON-NLS-1$
				String partitionTypeAtt = element.getAttribute("partitionType");
				if (partitionTypeAtt == null) {// backward compatibility when
					// no "partitionType" attribute
					partitionTypeAtt = PHPPartitionTypes.PHP_DEFAULT;
				}
				if (partitionTypeAtt.equals(partitionType)) {
					ElementCreationProxy ecProxy = new ElementCreationProxy(element, CONTENT_ASSIST_PROCESSOR_EXT);
					IContentAssistProcessor processor = (IContentAssistProcessor) ecProxy.getObject();
					if (processor != null) {
						processors.add(processor);
					}
				}
			}
		}

		processorsCache.put(partitionType, processors);
		return processors;
	}

	public IContentAssistant getPHPContentAssistant(ISourceViewer sourceViewer) {
		return getPHPContentAssistant(sourceViewer, false);
	}

	public IContentAssistant getPHPContentAssistant(ISourceViewer sourceViewer, boolean reCreate) {
		if (fContentAssistant == null || reCreate) {
			fContentAssistant = getPHPContentAssistantExtension();
			
			if (fContentAssistant == null) {
				fContentAssistant = new StructuredContentAssistant();
			}
			// content assistant configurations
			fContentAssistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
			fContentAssistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
			fContentAssistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
			fContentAssistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
			
			Preferences preferences = PHPCorePlugin.getDefault().getPluginPreferences();
			fContentAssistant.enableAutoActivation(preferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION));
			fContentAssistant.setAutoActivationDelay(preferences.getInt(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY));
			fContentAssistant.enableAutoInsert(preferences.getBoolean(PHPCoreConstants.CODEASSIST_AUTOINSERT));

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

	private StructuredContentAssistant getPHPContentAssistantExtension() {
		StructuredContentAssistant rv = null;
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(PHP_CONTENT_ASSISTANT_EXT);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("contentAssistant")) { //$NON-NLS-1$
				ElementCreationProxy ecProxy = new ElementCreationProxy(element, PHP_CONTENT_ASSISTANT_EXT);
				StructuredContentAssistant contentAssistant = (StructuredContentAssistant) ecProxy.getObject();
				if (contentAssistant != null) {
					rv = contentAssistant;
				}
			}
		}
		return rv;
	}

	@Override
	public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
		return DEFAULT_PREFIXES;
	}

	/*
	 * @see SourceViewerConfiguration#getConfiguredTextHoverStateMasks(ISourceViewer,
	 *      String)
	 */
	@Override
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
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
		// Screen out any non-PHP content
		if (!PHPStructuredTextPartitioner.isPHPPartitionType(contentType)) {
			return super.getTextHover(sourceViewer, contentType, stateMask);
		}

		PHPEditorTextHoverDescriptor[] hoverDescs = PHPUiPlugin.getDefault().getPHPEditorTextHoverDescriptors();
		int i = 0;
		while (i < hoverDescs.length) {
			if (hoverDescs[i].isEnabled() && hoverDescs[i].getStateMask() == stateMask) {
				return new PHPTextHoverProxy(hoverDescs[i], ((PHPStructuredTextViewer) sourceViewer).getTextEditor(), fPreferenceStore);
			}
			i++;
		}

		return null;
	}

	/*
	 * @see SourceViewerConfiguration#getTextHover(ISourceViewer, String)
	 */
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return getTextHover(sourceViewer, contentType, ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (!fPreferenceStore.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_HYPERLINKS_ENABLED))
			return null;

		List<IHyperlinkDetector> detectors = new LinkedList<IHyperlinkDetector>();
		IHyperlinkDetector[] inheritedDetectors = super.getHyperlinkDetectors(sourceViewer);
		if (inheritedDetectors != null) {
			detectors.addAll(Arrays.asList(inheritedDetectors));
		}

		detectors.add(new PHPHyperlinkDetector(((PHPStructuredTextViewer)sourceViewer).getTextEditor()));
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(HYPERLINK_DETECTOR_EXT);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("detector")) { //$NON-NLS-1$
				ElementCreationProxy ecProxy = new ElementCreationProxy(element, HYPERLINK_DETECTOR_EXT);
				IHyperlinkDetectorForPHP detector = (IHyperlinkDetectorForPHP) ecProxy.getObject();
				if (detector != null) {
					detectors.add(detector);
				}
			}
		}
		
		return detectors.toArray(new IHyperlinkDetector[detectors.size()]);
	}

	@Override
	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		IContentFormatter usedFormatter = null;

		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(FORMATTER_PROCESSOR_EXT);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("processor")) { //$NON-NLS-1$
				ElementCreationProxy ecProxy = new ElementCreationProxy(element, FORMATTER_PROCESSOR_EXT);
				usedFormatter = (IContentFormatter) ecProxy.getObject();
			}
		}

		if (usedFormatter == null) {
			usedFormatter = new MultiPassContentFormatter(getConfiguredDocumentPartitioning(sourceViewer), IHTMLPartitions.HTML_DEFAULT);
			((MultiPassContentFormatter) usedFormatter).setMasterStrategy(new StructuredFormattingStrategy(new PhpFormatProcessorImpl()));
		}

		return usedFormatter;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		if (contentType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
			return phpStrategies;
		}

		return getPhpAutoEditStrategy(sourceViewer, contentType);
	}

	private final IAutoEditStrategy[] getPhpAutoEditStrategy(ISourceViewer sourceViewer, String contentType) {
		final IAutoEditStrategy[] autoEditStrategies = super.getAutoEditStrategies(sourceViewer, contentType);
		final int length = autoEditStrategies.length;
		final IAutoEditStrategy[] augAutoEditStrategies = new IAutoEditStrategy[length + 1];
		System.arraycopy(autoEditStrategies, 0, augAutoEditStrategies, 0, length);
		augAutoEditStrategies[length] = closeTagAutoEditStrategy;

		return augAutoEditStrategies;
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (contentType == PHPPartitionTypes.PHP_DEFAULT) {
			// use php's doubleclick strategy
			return new PHPDoubleClickStrategy();
		} else
			return super.getDoubleClickStrategy(sourceViewer, contentType);
	}

	@Override
	public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
		Vector<String> vector = new Vector<String>();

		// prefix[0] is either '\t' or ' ' x tabWidth, depending on preference
		char indentCharPref = FormatPreferencesSupport.getInstance().getIndentationChar(null);
		int indentationSize = FormatPreferencesSupport.getInstance().getIndentationSize(null);

		for (int i = 0; i <= indentationSize; i++) {
			StringBuffer prefix = new StringBuffer();
			boolean appendTab = false;

			for (int j = 0; j + i < indentationSize; j++)
				prefix.append(indentCharPref);

			if (i != 0) {
				appendTab = true;
			}

			if (appendTab) {
				prefix.append('\t');
				vector.add(prefix.toString());
				// remove the tab so that indentation - tab is also an indent
				// prefix
				prefix.deleteCharAt(prefix.length() - 1);
			}
			vector.add(prefix.toString());
		}

		vector.add(EMPTY); //$NON-NLS-1$

		return (String[]) vector.toArray(new String[vector.size()]);
	}

	/**
	 * Returns the outline presenter which will determine and shown information
	 * requested for the current cursor position.
	 * 
	 * @param sourceViewer
	 *            the source viewer to be configured by this configuration
	 * @return an information presenter
	 * @since 2.1
	 */
	public IInformationPresenter getOutlinePresenter(ISourceViewer sourceViewer) {
		InformationPresenter presenter;
		presenter = new InformationPresenter(getOutlinePresenterControlCreator(sourceViewer, SHOW_OUTLINE_PREF_KEY)); //$NON-NLS-1$
		presenter.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		presenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
		IInformationProvider provider = new PHPElementProvider(((PHPStructuredTextViewer) sourceViewer).getTextEditor());
		presenter.setInformationProvider(provider, PHPPartitionTypes.PHP_DEFAULT);
		presenter.setSizeConstraints(50, 20, true, false);
		return presenter;
	}

	/**
	 * Returns the hierarchy presenter which will determine and shown type hierarchy
	 * information requested for the current cursor position.
	 *
	 * @param sourceViewer the source viewer to be configured by this configuration
	 * @param doCodeResolve a boolean which specifies whether code resolve should be used to compute the PHP element
	 * @return an information presenter
	 * @since 3.4
	 */
	public IInformationPresenter getHierarchyPresenter(PHPStructuredTextViewer viewer, boolean doCodeResolve) {

		// Do not create hierarchy presenter if there's no CU.
		if (viewer.getTextEditor() == null || viewer.getTextEditor().getEditorInput() == null) {
			return null;
		}
		InformationPresenter presenter = new InformationPresenter(getHierarchyPresenterControlCreator());
		presenter.setDocumentPartitioning(getConfiguredDocumentPartitioning(viewer));
		presenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
		IInformationProvider provider = new PHPInformationElementProvider(viewer.getTextEditor(), doCodeResolve);
		presenter.setInformationProvider(provider, PHPPartitionTypes.PHP_DEFAULT);
		presenter.setSizeConstraints(50, 20, true, false);
		return presenter;
	}

	private IInformationControlCreator getHierarchyPresenterControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				int shellStyle = SWT.RESIZE;
				int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
				return new HierarchyInformationControl(parent, shellStyle, treeStyle) {
					protected IPreferenceStore getPreferenceStore() {
						return PHPUiPlugin.getDefault().getPreferenceStore();
					}
				};
			}
		};
	}

	/**
	 * Returns the outline presenter control creator. The creator is a factory
	 * creating outline presenter controls for the given source viewer. This
	 * implementation always returns a creator for
	 * <code>PHPOutlineInformationControl</code> instances.
	 * 
	 * @param sourceViewer
	 *            the source viewer to be configured by this configuration
	 * @param commandId
	 *            the ID of the command that opens this control
	 * @return an information control creator
	 * @since 2.1
	 */
	private IInformationControlCreator getOutlinePresenterControlCreator(ISourceViewer sourceViewer, final String commandId) {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				int shellStyle = SWT.RESIZE;
				int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
				return new PHPOutlineInformationControl(parent, shellStyle, treeStyle, commandId);
			}
		};
	}
}
