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
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.parser.ModelSupport;
import org.eclipse.php.internal.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.internal.core.phpModel.parser.PHPDocLanguageModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.core.util.WeakPropertyChangeListener;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContextTypeIds;
import org.eclipse.php.internal.ui.editor.util.TextSequence;
import org.eclipse.php.internal.ui.editor.util.TextSequenceUtilities;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;

public class PHPDocContentAssistSupport extends ContentAssistSupport {

	private static final char TAG_SIGN = '@';
	
	private char[] autoActivationTriggers;

	private CompletionProposalGroup phpDocCompletionProposalGroup = new PHPCompletionProposalGroup();
	
	protected IPropertyChangeListener prefChangeListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			if (event != null) {
				initPreferences(event.getProperty());
			}
		}
	};
	
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
	
	public char[] getAutoactivationTriggers() {
		return autoActivationTriggers;
	}

	protected void calcCompletionOption(DOMModelForPHP editorModel, int offset, ITextViewer viewer) throws BadLocationException {
		PHPFileData fileData = editorModel.getFileData();
		if (fileData == null) {
			return;
		}
		IStructuredDocumentRegion sdRegion = ContentAssistUtils.getStructuredDocumentRegion((StructuredTextViewer) viewer, offset);
		int lineStartOffset = editorModel.getStructuredDocument().getLineInformationOfOffset(offset).getOffset();

		ITextRegion textRegion = null;
		// 	in case we are at the end of the document, asking for completion
		if (offset == editorModel.getStructuredDocument().getLength()) {
			textRegion = sdRegion.getLastRegion();
		} else {
			textRegion = sdRegion.getRegionAtCharacterOffset(offset);
		}

		if (textRegion == null)
			return;

		if (textRegion.getType() == PHPRegionContext.PHP_CLOSE) { // dont provide completion if staying after PHP close tag.
			return;
		}
		
		PhpScriptRegion phpScriptRegion = (PhpScriptRegion)textRegion;
		int internalOffset = offset-sdRegion.getStartOffset()-phpScriptRegion.getStart();
		
		String partitionType = phpScriptRegion.getPartition(internalOffset);
		if (partitionType == PHPPartitionTypes.PHP_DOC){
		}else {
			return;
		}
		
		TextSequence statmentText = TextSequenceUtilities.createTextSequence(sdRegion, lineStartOffset, offset - lineStartOffset);
		int totalLength = statmentText.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statmentText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifiarStartIndex(statmentText, endPosition, true);
		String lastWord = statmentText.subSequence(startPosition, endPosition).toString();
		boolean haveSpacesAtEnd = totalLength != endPosition;
		int selectionLength = ((TextSelection) viewer.getSelectionProvider().getSelection()).getLength();

		if (isInPhpDocCompletion(viewer, statmentText, offset, lastWord, selectionLength, haveSpacesAtEnd)) {
			// the current position is php doc block.
			return;
		}
		if (isVariableCompletion(editorModel, fileData, offset, lastWord, selectionLength, haveSpacesAtEnd)) {
			// the current position is a variable in a php doc block.
			return;
		}
		templateProposals = getTemplates(viewer, offset);
	}

	private boolean isInPhpDocCompletion( ITextViewer viewer, CharSequence statmentText, int offset, String tagName, int selectionLength, boolean hasSpacesAtEnd) {
		if (hasSpacesAtEnd) {
			return false;
		}
		int startPosition = statmentText.length() - tagName.length();
		if (startPosition <= 0 || statmentText.charAt(startPosition - 1) != TAG_SIGN) {
			return false; // this is not a tag
		}

		startPosition--;
		// verify that only whitespaces and '*' before the tag
		boolean founeX = false;
		for (; startPosition > 0; startPosition--) {
			if (!Character.isWhitespace(statmentText.charAt(startPosition - 1))) {
				if (founeX || statmentText.charAt(startPosition - 1) != '*') {
					break;
				}
				founeX = true;
			}
		}

		if (startPosition != 0) {
			return true; // this is not the start of the line
		}

		CodeData[] tags = PHPDocLanguageModel.getPHPDocTags(tagName);
		phpDocCompletionProposalGroup.setData(offset, tags, tagName, selectionLength);
		completionProposalGroup = phpDocCompletionProposalGroup;
		templateProposals = getTemplates(viewer, offset);
		return true;
	}

	private boolean isVariableCompletion(DOMModelForPHP phpDOMModel, PHPFileData fileData, int offset, String tagName, int selectionLength, boolean haveSpacesAtEnd) {
		if (haveSpacesAtEnd) {
			return false;
		}
		if (tagName.startsWith("$")) {
			tagName = tagName.substring(1);

			PHPCodeContext context = ModelSupport.createContext(fileData, offset);
			PHPProjectModel projectModel = phpDOMModel.getProjectModel();
			String fileName = fileData.getName();
			CodeData[] variables = projectModel.getVariables(fileName, context, tagName, true);

			PHPClassData[] classes = fileData.getClasses();
			boolean mergedData = false;
			for (int i = 0; i < classes.length; i++) {
				CodeData rv = isInClassBlocks(classes[i], offset);
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
				for (int i = 0; i < functions.length; i++) {
					if (isInBlock(functions[i], offset)) {
						context = ModelSupport.createContext(functions[i]);
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
			for (int i = 0; i < functions.length; i++) {
				if (isInBlock(functions[i], position)) {
					return functions[i];
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

	protected String getTemplateContext() {
		return PHPTemplateContextTypeIds.PHPDOC;
	}

}
