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
package org.eclipse.php.internal.ui.editor.configuration;

import java.util.*;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.dltk.internal.ui.typehierarchy.HierarchyInformationControl;
import org.eclipse.dltk.ui.actions.IScriptEditorActionDefinitionIds;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.information.*;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.core.format.IFormatterCommonPrferences;
import org.eclipse.php.internal.core.format.PhpFormatProcessorImpl;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.autoEdit.CloseTagAutoEditStrategyPHP;
import org.eclipse.php.internal.ui.autoEdit.IndentLineAutoEditStrategy;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.php.internal.ui.doubleclick.PHPDoubleClickStrategy;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProcessor;
import org.eclipse.php.internal.ui.editor.contentassist.PHPContentAssistant;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.php.internal.ui.editor.hover.BestMatchHover;
import org.eclipse.php.internal.ui.editor.hover.PHPTextHoverProxy;
import org.eclipse.php.internal.ui.text.PHPElementProvider;
import org.eclipse.php.internal.ui.text.PHPInformationHierarchyProvider;
import org.eclipse.php.internal.ui.text.PHPOutlineInformationControl;
import org.eclipse.php.internal.ui.text.correction.PHPCorrectionAssistant;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.php.internal.ui.util.ElementCreationProxy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.css.core.text.ICSSPartitions;
import org.eclipse.wst.css.ui.internal.contentassist.CSSStructuredContentAssistProcessor;
import org.eclipse.wst.html.core.internal.text.StructuredTextPartitionerForHTML;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.ui.StructuredTextViewerConfigurationHTML;
import org.eclipse.wst.sse.core.text.IStructuredPartitions;
import org.eclipse.wst.sse.ui.internal.contentassist.StructuredContentAssistant;
import org.eclipse.wst.sse.ui.internal.correction.CompoundQuickAssistProcessor;
import org.eclipse.wst.sse.ui.internal.format.StructuredFormattingStrategy;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;
import org.eclipse.wst.sse.ui.internal.provisional.style.ReconcilerHighlighter;
import org.eclipse.wst.sse.ui.internal.provisional.style.StructuredPresentationReconciler;
import org.eclipse.wst.xml.core.internal.text.rules.StructuredTextPartitionerForXML;

public class PHPStructuredTextViewerConfiguration extends
		StructuredTextViewerConfigurationHTML {

	private static final String FORMATTER_PROCESSOR_EXT = "org.eclipse.php.ui.phpFormatterProcessor"; //$NON-NLS-1$
	private static final String EMPTY = ""; //$NON-NLS-1$
	private static final String[] DEFAULT_PREFIXES = new String[] {
			"//", "#", EMPTY }; //$NON-NLS-1$ //$NON-NLS-2$
	private static final IAutoEditStrategy indentLineAutoEditStrategy = new IndentLineAutoEditStrategy();
	private static final IAutoEditStrategy mainAutoEditStrategy = new MainAutoEditStrategy();
	private static final IAutoEditStrategy closeTagAutoEditStrategy = new CloseTagAutoEditStrategyPHP();
	private static final IAutoEditStrategy[] phpStrategies = new IAutoEditStrategy[] { mainAutoEditStrategy };

	private String[] configuredContentTypes;
	private LineStyleProvider fLineStyleProvider;
	private StructuredContentAssistant fContentAssistant;
	private IQuickAssistAssistant fQuickAssistant;
	private PHPCompletionProcessor phpCompletionProcessor;
	Map<String, IContentAssistProcessor[]> processorMap = new HashMap<String, IContentAssistProcessor[]>();
	private ReconcilerHighlighter fHighlighter = null;

	public PHPStructuredTextViewerConfiguration() {
	}

	/*
	 * Returns an array of all the contentTypes (partition names) supported by
	 * this editor. They include all those supported by HTML editor plus PHP.
	 */
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		if (configuredContentTypes == null) {
			String[] phpTypes = PHPStructuredTextPartitioner
					.getConfiguredContentTypes();
			String[] xmlTypes = StructuredTextPartitionerForXML
					.getConfiguredContentTypes();
			String[] htmlTypes = StructuredTextPartitionerForHTML
					.getConfiguredContentTypes();
			configuredContentTypes = new String[2 + phpTypes.length
					+ xmlTypes.length + htmlTypes.length];

			configuredContentTypes[0] = IStructuredPartitions.DEFAULT_PARTITION;
			configuredContentTypes[1] = IStructuredPartitions.UNKNOWN_PARTITION;

			int index = 0;
			System.arraycopy(phpTypes, 0, configuredContentTypes, index += 2,
					phpTypes.length);
			System.arraycopy(xmlTypes, 0, configuredContentTypes,
					index += phpTypes.length, xmlTypes.length);
			System.arraycopy(htmlTypes, 0, configuredContentTypes,
					index += xmlTypes.length, htmlTypes.length);
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
	public LineStyleProvider[] getLineStyleProviders(
			ISourceViewer sourceViewer, String partitionType) {
		if (partitionType == PHPPartitionTypes.PHP_DEFAULT) {
			return new LineStyleProvider[] { getLineStyleProvider() };
		}
		return super.getLineStyleProviders(sourceViewer, partitionType);
	}

	@Override
	public IContentAssistProcessor[] getContentAssistProcessors(
			ISourceViewer sourceViewer, String partitionType) {
		IContentAssistProcessor[] processors = null;

		if (sourceViewer instanceof PHPStructuredTextViewer) {

			ITextEditor textEditor = ((PHPStructuredTextViewer) sourceViewer)
					.getTextEditor();
			if (phpCompletionProcessor == null) {
				ContentAssistant phpContentAssistant = (ContentAssistant) getPHPContentAssistant(sourceViewer);
				// phpCompletionProcessor may be initialized in
				// getPHPContentAssistant call
				if (phpCompletionProcessor == null) {
					phpCompletionProcessor = new PHPCompletionProcessor(
							textEditor, phpContentAssistant,
							PHPPartitionTypes.PHP_DEFAULT);
					addContentAssistProcessors(sourceViewer);
				}
			} else {
				ContentAssistant phpContentAssistant = (ContentAssistant) getPHPContentAssistant(sourceViewer);
				// make sure current contentAssistant install dltk's
				// ContentAssistProcessor.CompletionListener,so we can call
				// IContentAssistantExtension3.setRepeatedInvocationTrigger for
				// PHPContentAssistant,so we can press "ctrl+space" to switch
				// between different template categories
				if (phpCompletionProcessor.getAssistant() != phpContentAssistant) {
					phpCompletionProcessor = new PHPCompletionProcessor(
							textEditor, phpContentAssistant,
							PHPPartitionTypes.PHP_DEFAULT);
				}
			}

			if (partitionType == PHPPartitionTypes.PHP_DEFAULT) {
				processors = new IContentAssistProcessor[] { phpCompletionProcessor };
			} else {
				IContentAssistProcessor[] superProcessors;
				if (partitionType.equals(ICSSPartitions.STYLE)
						|| partitionType.equals(ICSSPartitions.COMMENT)) {
					IContentAssistProcessor processor = new CSSStructuredContentAssistProcessor(
							this.getContentAssistant(), partitionType,
							sourceViewer);
					superProcessors = new IContentAssistProcessor[] { processor };

				} else {
					superProcessors = super.getContentAssistProcessors(
							sourceViewer, partitionType);
				}
				processorMap.put(partitionType, superProcessors);
				if (superProcessors != null) {
					processors = new IContentAssistProcessor[superProcessors.length + 1];
					System.arraycopy(superProcessors, 0, processors, 0,
							superProcessors.length);
					processors[superProcessors.length] = phpCompletionProcessor;
				} else {
					processors = new IContentAssistProcessor[] { phpCompletionProcessor };
				}
			}
		} else {
			processors = new IContentAssistProcessor[0];
		}
		return processors;
	}

	public IContentAssistant getPHPContentAssistant(ISourceViewer sourceViewer) {
		return getPHPContentAssistant(sourceViewer, false);
	}

	public IContentAssistant getPHPContentAssistant(ISourceViewer sourceViewer,
			boolean reSet) {
		if (fContentAssistant == null || reSet) {
			if (fContentAssistant != null) {
				fContentAssistant.uninstall();
			}
			fContentAssistant = new PHPContentAssistant();

			// content assistant configurations
			fContentAssistant
					.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
			fContentAssistant
					.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
			fContentAssistant
					.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
			fContentAssistant
					.setInformationControlCreator(getInformationControlCreator(sourceViewer));

			IPreferencesService preferencesService = Platform
					.getPreferencesService();
			fContentAssistant.enableAutoActivation(preferencesService
					.getBoolean(PHPCorePlugin.ID,
							PHPCoreConstants.CODEASSIST_AUTOACTIVATION, false,
							null));
			fContentAssistant.setAutoActivationDelay(preferencesService.getInt(
					PHPCorePlugin.ID,
					PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY, 0, null));
			fContentAssistant.enableAutoInsert(preferencesService.getBoolean(
					PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_AUTOINSERT,
					false, null));

			addContentAssistProcessors(sourceViewer);
		}

		return fContentAssistant;
	}

	/**
	 * @param sourceViewer
	 */
	private void addContentAssistProcessors(ISourceViewer sourceViewer) {
		// add content assist processors for each partition type
		String[] types = getConfiguredContentTypes(sourceViewer);
		for (int i = 0; i < types.length; i++) {
			String type = types[i];

			// add all content assist processors for current partiton type
			IContentAssistProcessor[] processors = getContentAssistProcessors(
					sourceViewer, type);
			if (processors != null) {
				for (int j = 0; j < processors.length; j++) {
					fContentAssistant.setContentAssistProcessor(processors[j],
							type);
				}
			}
		}

		// reset auto activation delay based on pdt's setting,because it was
		// changed by wtp
		IPreferencesService preferencesService = Platform
				.getPreferencesService();
		int fAutoActivationDelay = preferencesService.getInt(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY, 200, null);

		fContentAssistant.setAutoActivationDelay(fAutoActivationDelay);
	}

	@Override
	public String[] getDefaultPrefixes(ISourceViewer sourceViewer,
			String contentType) {
		return DEFAULT_PREFIXES;
	}

	/*
	 * @see
	 * SourceViewerConfiguration#getConfiguredTextHoverStateMasks(ISourceViewer,
	 * String)
	 */
	@Override
	public int[] getConfiguredTextHoverStateMasks(ISourceViewer sourceViewer,
			String contentType) {
		PHPEditorTextHoverDescriptor[] hoverDescs = PHPUiPlugin.getDefault()
				.getPHPEditorTextHoverDescriptors();
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
		System.arraycopy(stateMasks, 0, shortenedStateMasks, 0,
				stateMasksLength);
		return shortenedStateMasks;
	}

	/*
	 * @see SourceViewerConfiguration#getTextHover(ISourceViewer, String, int)
	 */
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType, int stateMask) {
		// Screen out any non-PHP content
		if (!PHPStructuredTextPartitioner.isPHPPartitionType(contentType)) {
			return super.getTextHover(sourceViewer, contentType, stateMask);
		}

		if (sourceViewer instanceof PHPStructuredTextViewer) {
			PHPEditorTextHoverDescriptor[] hoverDescs = PHPUiPlugin
					.getDefault().getPHPEditorTextHoverDescriptors();
			int i = 0;
			while (i < hoverDescs.length) {
				if (hoverDescs[i].isEnabled()
						&& hoverDescs[i].getStateMask() == stateMask) {
					return new PHPTextHoverProxy(hoverDescs[i],
							((PHPStructuredTextViewer) sourceViewer)
									.getTextEditor(), fPreferenceStore);
				}
				i++;
			}
		}
		return null;
	}

	/*
	 * @see SourceViewerConfiguration#getTextHover(ISourceViewer, String)
	 */
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType) {
		return getTextHover(sourceViewer, contentType,
				ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
	}

	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (!fPreferenceStore
				.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_HYPERLINKS_ENABLED))
			return null;

		// Create proxy to prevent throwing of errors in case there's an
		// exception occurred in hyperlink detector:
		List<IHyperlinkDetector> detectors = new LinkedList<IHyperlinkDetector>();
		IHyperlinkDetector[] inheritedDetectors = super
				.getHyperlinkDetectors(sourceViewer);
		if (inheritedDetectors != null) {
			for (final IHyperlinkDetector detector : inheritedDetectors) {
				detectors.add(new IHyperlinkDetector() {
					public IHyperlink[] detectHyperlinks(
							ITextViewer textViewer, IRegion region,
							boolean canShowMultipleHyperlinks) {
						try {
							return detector.detectHyperlinks(textViewer,
									region, canShowMultipleHyperlinks);
						} catch (Throwable e) {
						}
						return null;
					}
				});
			}
		}
		return detectors.toArray(new IHyperlinkDetector[detectors.size()]);
	}

	protected Map getHyperlinkDetectorTargets(ISourceViewer sourceViewer) {
		Map targets = super.getHyperlinkDetectorTargets(sourceViewer);
		targets.put(ContentTypeIdForPHP.ContentTypeID_PHP, null);
		return targets;
	}

	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		IContentFormatter usedFormatter = null;

		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(FORMATTER_PROCESSOR_EXT);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("processor")) { //$NON-NLS-1$
				ElementCreationProxy ecProxy = new ElementCreationProxy(
						element, FORMATTER_PROCESSOR_EXT);
				usedFormatter = (IContentFormatter) ecProxy.getObject();
			}
		}

		if (usedFormatter == null) {
			usedFormatter = new MultiPassContentFormatter(
					getConfiguredDocumentPartitioning(sourceViewer),
					IHTMLPartitions.HTML_DEFAULT);
			((MultiPassContentFormatter) usedFormatter)
					.setMasterStrategy(new StructuredFormattingStrategy(
							new PhpFormatProcessorImpl()));
		}

		return usedFormatter;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		if (contentType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
			return phpStrategies;
		}

		return getPhpAutoEditStrategy(sourceViewer, contentType);
	}

	private final IAutoEditStrategy[] getPhpAutoEditStrategy(
			ISourceViewer sourceViewer, String contentType) {
		final IAutoEditStrategy[] autoEditStrategies = super
				.getAutoEditStrategies(sourceViewer, contentType);
		for (int i = 0; i < autoEditStrategies.length; i++) {
			if (autoEditStrategies[i] instanceof org.eclipse.wst.html.ui.internal.autoedit.AutoEditStrategyForTabs
			// ||
			// org.eclipse.wst.jsdt.web.ui.internal.autoedit.AutoEditStrategyForTabs
			) {
				autoEditStrategies[i] = indentLineAutoEditStrategy;
			}
		}
		final int length = autoEditStrategies.length;
		final IAutoEditStrategy[] augAutoEditStrategies = new IAutoEditStrategy[length + 1];
		System.arraycopy(autoEditStrategies, 0, augAutoEditStrategies, 0,
				length);
		augAutoEditStrategies[length] = closeTagAutoEditStrategy;

		return augAutoEditStrategies;
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (contentType == PHPPartitionTypes.PHP_DEFAULT) {
			// use php's doubleclick strategy
			return new PHPDoubleClickStrategy();
		} else
			return super.getDoubleClickStrategy(sourceViewer, contentType);
	}

	@Override
	public String[] getIndentPrefixes(ISourceViewer sourceViewer,
			String contentType) {
		Vector<String> vector = new Vector<String>();

		// prefix[0] is either '\t' or ' ' x tabWidth, depending on preference
		IFormatterCommonPrferences formatterCommonPrferences = FormatterUtils
				.getFormatterCommonPrferences();
		char indentCharPref = formatterCommonPrferences
				.getIndentationChar(sourceViewer.getDocument());
		int indentationSize = formatterCommonPrferences
				.getIndentationSize(sourceViewer.getDocument());

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
		InformationPresenter presenter = null;
		if (sourceViewer instanceof PHPStructuredTextViewer) {
			// set the right commandid,after doing this you can make double
			// clicking on Ctrl+O enable
			presenter = new InformationPresenter(
					getOutlinePresenterControlCreator(sourceViewer,
							IScriptEditorActionDefinitionIds.SHOW_OUTLINE));
			presenter
					.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
			presenter
					.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
			IInformationProvider provider = new PHPElementProvider(
					((PHPStructuredTextViewer) sourceViewer).getTextEditor());
			presenter.setInformationProvider(provider,
					PHPPartitionTypes.PHP_DEFAULT);
			presenter.setSizeConstraints(50, 20, true, false);
		}
		return presenter;
	}

	/**
	 * Returns the hierarchy presenter which will determine and shown type
	 * hierarchy information requested for the current cursor position.
	 * 
	 * @param sourceViewer
	 *            the source viewer to be configured by this configuration
	 * @param doCodeResolve
	 *            a boolean which specifies whether code resolve should be used
	 *            to compute the PHP element
	 * @return an information presenter
	 * @since 3.4
	 */
	public IInformationPresenter getHierarchyPresenter(
			PHPStructuredTextViewer viewer, boolean doCodeResolve) {

		// Do not create hierarchy presenter if there's no CU.
		if (viewer.getTextEditor() == null
				|| viewer.getTextEditor().getEditorInput() == null) {
			return null;
		}
		InformationPresenter presenter = new InformationPresenter(
				getHierarchyPresenterControlCreator());
		presenter
				.setDocumentPartitioning(getConfiguredDocumentPartitioning(viewer));
		presenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
		IInformationProvider provider = new PHPInformationHierarchyProvider(
				viewer.getTextEditor(), doCodeResolve);
		presenter.setInformationProvider(provider,
				PHPPartitionTypes.PHP_DEFAULT);
		presenter.setSizeConstraints(50, 20, true, false);
		return presenter;
	}

	private IInformationControlCreator getHierarchyPresenterControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				int shellStyle = SWT.RESIZE;
				int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
				return new HierarchyInformationControl(parent, shellStyle,
						treeStyle) {
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
	private IInformationControlCreator getOutlinePresenterControlCreator(
			ISourceViewer sourceViewer, final String commandId) {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				int shellStyle = SWT.RESIZE;
				int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
				return new PHPOutlineInformationControl(parent, shellStyle,
						treeStyle, commandId);
			}
		};
	}

	/*
	 * @seeorg.eclipse.jface.text.source.SourceViewerConfiguration#
	 * getQuickAssistAssistant(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IQuickAssistAssistant getQuickAssistAssistant(
			ISourceViewer sourceViewer) {
		if (fQuickAssistant == null) {
			IQuickAssistAssistant assistant = new PHPCorrectionAssistant();
			assistant
					.setQuickAssistProcessor(new CompoundQuickAssistProcessor());
			fQuickAssistant = assistant;
		}
		return fQuickAssistant;
	}

	public Map<String, IContentAssistProcessor[]> getProcessorMap() {
		return processorMap;
	}

	@Override
	protected IInformationProvider getInformationProvider(
			ISourceViewer sourceViewer, String partitionType) {
		if (!(sourceViewer instanceof PHPStructuredTextViewer))
			return super.getInformationProvider(sourceViewer, partitionType);
		ITextHover bestMatchHover = new BestMatchHover(
				((PHPStructuredTextViewer) sourceViewer).getTextEditor(),
				PHPUiPlugin.getDefault().getPreferenceStore());

		return new TextHoverInformationProvider(bestMatchHover);
	}

	class TextHoverInformationProvider implements IInformationProvider,
			IInformationProviderExtension, IInformationProviderExtension2 {
		private ITextHover fTextHover;

		public TextHoverInformationProvider(ITextHover hover) {
			fTextHover = hover;
		}

		public String getInformation(ITextViewer textViewer, IRegion subject) {
			return (String) getInformation2(textViewer, subject);
		}

		public Object getInformation2(ITextViewer textViewer, IRegion subject) {
			return fTextHover.getHoverInfo(textViewer, subject);
		}

		public IInformationControlCreator getInformationPresenterControlCreator() {
			if (fTextHover instanceof IInformationProviderExtension2)
				return ((IInformationProviderExtension2) fTextHover)
						.getInformationPresenterControlCreator();

			return null;
		}

		public IRegion getSubject(ITextViewer textViewer, int offset) {
			return fTextHover.getHoverRegion(textViewer, offset);
		}
	}

	/**
	 * @return the associated content assistnat
	 */
	protected StructuredContentAssistant getContentAssistant() {
		return this.fContentAssistant;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		StructuredPresentationReconciler reconciler = new PHPStructuredPresentationReconciler();
		reconciler
				.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		String[] contentTypes = getConfiguredContentTypes(sourceViewer);

		if (contentTypes != null) {
			StructuredDocumentDamagerRepairer dr = null;

			for (int i = 0; i < contentTypes.length; i++) {
				if (fHighlighter != null) {
					LineStyleProvider provider = fHighlighter
							.getProvider(contentTypes[i]);
					if (provider == null)
						continue;

					dr = new StructuredDocumentDamagerRepairer(provider);
					dr.setDocument(sourceViewer.getDocument());
					reconciler.setDamager(dr, contentTypes[i]);
					reconciler.setRepairer(dr, contentTypes[i]);
				}
			}
		}

		return reconciler;
	}

	public void setHighlighter(ReconcilerHighlighter highlighter) {
		fHighlighter = highlighter;
		super.setHighlighter(highlighter);
	}

}
