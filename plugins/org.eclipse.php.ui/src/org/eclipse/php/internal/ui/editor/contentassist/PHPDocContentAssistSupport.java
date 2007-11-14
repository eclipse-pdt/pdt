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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.util.WeakPropertyChangeListener;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContextTypeIds;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;

public class PHPDocContentAssistSupport extends ContentAssistSupport {

	private static final char TAG_SIGN = '@';

	private char[] autoActivationTriggers;

	private final CompletionProposalGroup phpDocCompletionProposalGroup = new PHPCompletionProposalGroup();

	protected IPropertyChangeListener prefChangeListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			if (event != null) {
				initPreferences(event.getProperty());
			}
		}
	};

	@Override
	protected void initPreferences(String prefKey) {
		if (prefKey == null || PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC.equals(prefKey)) {
			autoActivationTriggers = PreferenceConstants.getPreferenceStore().getString(PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC).trim().toCharArray();
		}
	}

	public PHPDocContentAssistSupport() {
		// Initialize all preferences
		initPreferences(null);

		// Listen to preferences changes
		PreferenceConstants.getPreferenceStore().addPropertyChangeListener(WeakPropertyChangeListener.create(prefChangeListener, PreferenceConstants.getPreferenceStore()));
	}

	@Override
	public char[] getAutoactivationTriggers() {
		return autoActivationTriggers;
	}

	@Override
	protected void calcCompletionOption(DOMModelForPHP editorModel, int offset, ITextViewer viewer, boolean explicit) throws BadLocationException {

		PHPProjectModel projectModel = editorModel.getProjectModel();

		// if there is no project model (the file is not part of a project)
		// get the default project model
		if (projectModel == null) {
			projectModel = PHPWorkspaceModelManager.getDefaultPHPProjectModel();
		}

		int selectionLength = ((TextSelection) viewer.getSelectionProvider().getSelection()).getLength();

		IStructuredDocumentRegion sdRegion = ContentAssistUtils.getStructuredDocumentRegion(viewer, offset);
		ITextRegion textRegion = null;
		// 	in case we are at the end of the document, asking for completion
		if (offset == editorModel.getStructuredDocument().getLength()) {
			textRegion = sdRegion.getLastRegion();
		} else {
			textRegion = sdRegion.getRegionAtCharacterOffset(offset);
		}

		if (textRegion == null)
			return;

		ITextRegionCollection container = sdRegion;

		if (textRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) textRegion;
			textRegion = container.getRegionAtCharacterOffset(offset);
		}

		if (textRegion.getType() == PHPRegionContext.PHP_OPEN) {
			return;
		}
		if (textRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			if (sdRegion.getStartOffset(textRegion) == offset) {
				ITextRegion regionBefore = sdRegion.getRegionAtCharacterOffset(offset - 1);
				if (regionBefore instanceof IPhpScriptRegion) {
					textRegion = regionBefore;
				}
			} else {
				return;
			}
		}

		// find the start String for completion
		int startOffset = container.getStartOffset(textRegion);

		//in case we are standing at the beginning of a word and asking for completion
		//should not take into account the found region
		//find the previous region and update the start offset
		if (startOffset == offset) {
			ITextRegion preTextRegion = container.getRegionAtCharacterOffset(offset - 1);
			IStructuredDocumentRegion preSdRegion = null;
			if (preTextRegion != null || (preSdRegion = sdRegion.getPrevious()) != null && (preTextRegion = preSdRegion.getRegionAtCharacterOffset(offset - 1)) != null) {
				if (preTextRegion.getType() == "") { //$NON-NLS-1$
					// TODO needs to be fixed. The problem is what to do if the cursor is exatly between problematic regions, e.g. single line comment and quoted string??
				}
			}
			startOffset = sdRegion.getStartOffset(textRegion);
		}

		IPhpScriptRegion phpScriptRegion = null;
		String partitionType = null;
		int internalOffset = 0;
		if (textRegion instanceof IPhpScriptRegion) {
			phpScriptRegion = (IPhpScriptRegion) textRegion;
			internalOffset = offset - container.getStartOffset() - phpScriptRegion.getStart();

			partitionType = phpScriptRegion.getPartition(internalOffset);
			//if we are at the beginning of multi-line comment or docBlock then we should get completion.
			if (partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || partitionType == PHPPartitionTypes.PHP_DOC) {
				String regionType = phpScriptRegion.getPhpToken(internalOffset).getType();
				if (regionType == PHPRegionTypes.PHP_COMMENT_START || regionType == PHPRegionTypes.PHPDOC_COMMENT_START) {
					if (phpScriptRegion.getPhpToken(internalOffset).getStart() == internalOffset) {
						partitionType = phpScriptRegion.getPartition(internalOffset - 1);
					}
				}
			}
			if (!partitionType.equals(PHPPartitionTypes.PHP_DOC)) {
				return;
			}
		} else {
			return;
		}

		TextSequence statementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, false);

		int totalLength = statementText.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(statementText, endPosition, true);
		String lastWord = statementText.subSequence(startPosition, endPosition).toString();
		boolean haveSpacesAtEnd = totalLength != endPosition;

		if (isInPhpDocCompletion(viewer, statementText, offset, lastWord, selectionLength, haveSpacesAtEnd, explicit)) {
			// the current position is php doc block.
			return;
		}

		PHPFileData fileData = editorModel.getFileData(true);

		if (fileData != null && isVariableCompletion(editorModel, fileData, offset, lastWord, selectionLength, haveSpacesAtEnd)) {
			// the current position is a variable in a php doc block.
			return;
		}
		if (explicit) {
			templateProposals = getTemplates(viewer, offset);
		}
	}

	private boolean isInPhpDocCompletion(ITextViewer viewer, CharSequence statementText, int offset, String tagName, int selectionLength, boolean hasSpacesAtEnd, boolean explicit) {
		if (hasSpacesAtEnd) {
			return false;
		}
		int startPosition = statementText.length() - tagName.length();
		if (startPosition <= 0 || statementText.charAt(startPosition - 1) != TAG_SIGN) {
			return false; // this is not a tag
		}

		startPosition--;
		// verify that only whitespaces and '*' before the tag
		boolean founeX = false;
		for (; startPosition > 0; startPosition--) {
			if (!Character.isWhitespace(statementText.charAt(startPosition - 1))) {
				if (founeX || statementText.charAt(startPosition - 1) != '*') {
					break;
				}
				founeX = true;
			}
		}

		CodeData[] tags = PHPDocLanguageModel.getPHPDocTags(tagName);
		phpDocCompletionProposalGroup.setData(offset, tags, tagName, selectionLength);
		completionProposalGroup = phpDocCompletionProposalGroup;
		if (explicit) {
			templateProposals = getTemplates(viewer, offset);
		}
		return true;
	}

	private boolean isVariableCompletion(DOMModelForPHP phpDOMModel, PHPFileData fileData, int offset, String tagName, int selectionLength, boolean haveSpacesAtEnd) {
		if (haveSpacesAtEnd) {
			return false;
		}
		if (tagName.startsWith("$")) { //$NON-NLS-1$
			tagName = tagName.substring(1);

			PHPCodeContext context = ModelSupport.createContext(fileData, offset);
			PHPProjectModel projectModel = phpDOMModel.getProjectModel();
			String fileName = fileData.getName();
			CodeData[] variables = projectModel.getVariables(fileName, context, tagName, true);

			PHPClassData[] classes = fileData.getClasses();
			boolean mergedData = false;
			for (PHPClassData element : classes) {
				CodeData rv = isInClassBlocks(element, offset);
				if (rv != null) {
					context = ModelSupport.createContext(rv);
					CodeData[] contextVariables = projectModel.getVariables(fileName, context, tagName, false);
					variables = mergeCodeData(variables, contextVariables);
					mergedData = true;
					break;
				}
			}
			if (!mergedData) {
				PHPFunctionData[] functions = fileData.getFunctions();
				for (PHPFunctionData element : functions) {
					if (isInBlock(element, offset)) {
						context = ModelSupport.createContext(element);
						CodeData[] contextVariables = projectModel.getVariables(fileName, context, tagName, false);
						variables = mergeCodeData(variables, contextVariables);
						break;
					}
				}
			}
			completionProposalGroup = phpCompletionProposalGroup;

			completionProposalGroup.setData(offset, variables, tagName, selectionLength);
			return true;
		}
		return false;
	}

	private CodeData isInClassBlocks(PHPClassData data, int position) {
		if (isInBlock(data, position)) {
			return data;
		} else {
			PHPFunctionData[] functions = data.getFunctions();
			for (PHPFunctionData element : functions) {
				if (isInBlock(element, position)) {
					return element;
				}
			}
		}
		return null;
	}

	private CodeData[] mergeCodeData(CodeData[] codeDataArr1, CodeData[] codeDataArr2) {
		CodeData[] merged = new CodeData[codeDataArr1.length + codeDataArr2.length];
		System.arraycopy(codeDataArr1, 0, merged, 0, codeDataArr1.length);
		System.arraycopy(codeDataArr2, 0, merged, codeDataArr1.length, codeDataArr2.length);
		return merged;
	}

	private boolean isInBlock(PHPCodeData data, int position) {
		return data.getDocBlock() != null ? data.getDocBlock().containsPosition(position) : false;
	}

	@Override
	protected String getTemplateContext() {
		return PHPTemplateContextTypeIds.PHPDOC;
	}

}
