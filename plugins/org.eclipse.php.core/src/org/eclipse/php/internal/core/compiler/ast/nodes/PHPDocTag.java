/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.util.MagicMemberUtil;

public class PHPDocTag extends ASTNode implements PHPDocTagKinds {

	public static final String ERROR = "ERROR!!!"; //$NON-NLS-1$

	// Tag names are defined here in the same order as their associated tag
	// kinds from class PHPDocTagKinds:

	public static final String ABSTRACT_NAME = "abstract"; //$NON-NLS-1$
	public static final String AUTHOR_NAME = "author"; //$NON-NLS-1$
	public static final String DEPRECATED_NAME = "deprecated"; //$NON-NLS-1$
	public static final String FINAL_NAME = "final"; //$NON-NLS-1$
	public static final String GLOBAL_NAME = "global"; //$NON-NLS-1$
	public static final String NAME_NAME = "name"; //$NON-NLS-1$
	public static final String RETURN_NAME = "return"; //$NON-NLS-1$
	public static final String PARAM_NAME = "param"; //$NON-NLS-1$
	public static final String SEE_NAME = "see"; //$NON-NLS-1$
	public static final String STATIC_NAME = "static"; //$NON-NLS-1$
	public static final String STATICVAR_NAME = "staticvar"; //$NON-NLS-1$
	public static final String TODO_NAME = "todo"; //$NON-NLS-1$
	public static final String VAR_NAME = "var"; //$NON-NLS-1$
	public static final String PACKAGE_NAME = "package"; //$NON-NLS-1$
	public static final String ACCESS_NAME = "access"; //$NON-NLS-1$
	public static final String CATEGORY_NAME = "category"; //$NON-NLS-1$
	public static final String COPYRIGHT_NAME = "copyright"; //$NON-NLS-1$
	public static final String DESC_NAME = "desc"; //$NON-NLS-1$
	public static final String EXAMPLE_NAME = "example"; //$NON-NLS-1$
	public static final String FILESOURCE_NAME = "filesource"; //$NON-NLS-1$
	public static final String IGNORE_NAME = "ignore"; //$NON-NLS-1$
	public static final String INTERNAL_NAME = "internal"; //$NON-NLS-1$
	public static final String LICENSE_NAME = "license"; //$NON-NLS-1$
	public static final String LINK_NAME = "link"; //$NON-NLS-1$
	public static final String SINCE_NAME = "since"; //$NON-NLS-1$
	public static final String SUBPACKAGE_NAME = "subpackage"; //$NON-NLS-1$
	public static final String TUTORIAL_NAME = "tutorial"; //$NON-NLS-1$
	public static final String USES_NAME = "uses"; //$NON-NLS-1$
	public static final String VERSION_NAME = "version"; //$NON-NLS-1$
	public static final String THROWS_NAME = "throws"; //$NON-NLS-1$
	public static final String PROPERTY_NAME = "property"; //$NON-NLS-1$
	public static final String PROPERTY_READ_NAME = "property-read"; //$NON-NLS-1$
	public static final String PROPERTY_WRITE_NAME = "property-write"; //$NON-NLS-1$
	public static final String METHOD_NAME = "method"; //$NON-NLS-1$
	public static final String NAMESPACE_NAME = "namespace"; //$NON-NLS-1$
	public static final String INHERITDOC_NAME = "inheritdoc"; //$NON-NLS-1$

	private final int tagKind;
	private final String matchedTag;
	private String value;
	private List<Scalar> texts;
	private VariableReference variableReference;
	private TypeReference singleTypeReference;
	private List<TypeReference> typeReferences;
	private List<SimpleReference> allReferencesWithOrigOrder;
	private List<String> descTexts;
	private String trimmedDescText;

	public PHPDocTag(int start, int end, int tagKind, String matchedTag,
			String value, List<Scalar> texts) {
		super(start, end);
		this.tagKind = tagKind;
		this.matchedTag = matchedTag;
		this.value = value;
		this.texts = texts;
		updateReferences(start, end);
	}

	/**
	 * Never null.
	 */
	public List<Scalar> getTexts() {
		return texts;
	}

	/**
	 * Never null.
	 */
	public List<String> getDescTexts() {
		return descTexts;
	}

	/**
	 * Never null.
	 */
	public String getTrimmedDescText() {
		return trimmedDescText;
	}

	private String getTrimmedDescText(int wordsToSkip) {
		String text = value.trim();
		if (wordsToSkip == 0) {
			return text;
		}
		String[] split = MagicMemberUtil.WHITESPACE_SEPERATOR.split(text);
		for (int i = 0; i < wordsToSkip; i++) {
			int index = text.indexOf(split[i]);
			text = text.substring(split[i].length() + index);
		}
		return text.trim();
	}

	private List<String> getDescTexts(int wordsToSkip) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < texts.size(); i++) {
			String text = texts.get(i).getValue();
			if (wordsToSkip <= 0) {
				result.add(text);
			} else {
				if (text.trim().length() == 0) {
					continue;
				}
				List<String> commentWords = Arrays
						.asList(MagicMemberUtil.WHITESPACE_SEPERATOR.split(text
								.trim()));
				commentWords = removeEmptyString(commentWords);
				if (commentWords.size() <= wordsToSkip) {
					wordsToSkip = wordsToSkip - commentWords.size();
				} else {
					text = removeFirstWords(text, commentWords, wordsToSkip);
					result.add(text);
					wordsToSkip = 0;
				}
			}

		}
		return result;
	}

	public static List<String> removeEmptyString(List<String> commentWords) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < commentWords.size(); i++) {
			String word = commentWords.get(i);
			if (word.trim().length() != 0) {
				result.add(word);
			}

		}
		return result;
	}

	private String removeFirstWords(String text, List<String> commentWords,
			int wordSize) {
		for (int i = 0; i < wordSize; i++) {
			int index = text.indexOf(commentWords.get(i));
			text = text.substring(commentWords.get(i).length() + index);
		}
		return text.trim();
	}

	private static int getNonWhitespaceIndex(String line, int startIndex) {
		int i = startIndex;
		for (; i < line.length(); ++i) {
			if (!Character.isWhitespace(line.charAt(i))) {
				return i;
			}
		}
		return i;
	}

	private static int getWhitespaceIndex(String line, int startIndex) {
		int i = startIndex;
		for (; i < line.length(); ++i) {
			if (Character.isWhitespace(line.charAt(i))) {
				return i;
			}
		}
		return i;
	}

	private static int getClassStartIndex(String line, int startIndex) {
		int i = startIndex;
		for (; i < line.length(); ++i) {
			if (line.charAt(i) != Constants.TYPE_SEPERATOR_CHAR) {
				return i;
			}
		}
		return i;
	}

	private static int getClassEndIndex(String line, int startIndex) {
		int i = startIndex;
		for (; i < line.length(); ++i) {
			if (line.charAt(i) == Constants.TYPE_SEPERATOR_CHAR) {
				return i;
			}
		}
		return i;
	}

	private void splitSingleTypeReference(TypeReference reference,
			List<TypeReference> types) {
		String word = reference.getName();
		int valueStart = reference.sourceStart();
		int classStart = getClassStartIndex(word, 0);
		int classEnd = getClassEndIndex(word, classStart);

		while (classStart < classEnd) {
			String className = word.substring(classStart, classEnd);
			types.add(new TypeReference(valueStart + classStart, valueStart
					+ classEnd, className));

			classStart = getClassStartIndex(word, classEnd);
			classEnd = getClassEndIndex(word, classStart);
		}
	}

	private void updateReferences(int start, int end) {
		// (re)set references
		variableReference = null;
		singleTypeReference = null;
		typeReferences = new ArrayList<TypeReference>();
		allReferencesWithOrigOrder = new ArrayList<SimpleReference>();
		descTexts = new ArrayList<String>();

		int valueStart = start + matchedTag.length();
		// For all unsupported tags
		int wordsToSkip = 0;

		if (tagKind == RETURN || tagKind == VAR || tagKind == THROWS
				|| tagKind == SEE) {

			// Read first word
			int wordStart = getNonWhitespaceIndex(value, 0);
			int wordEnd = getWhitespaceIndex(value, wordStart);

			if (tagKind == VAR && wordStart < wordEnd) {

				String word = value.substring(wordStart, wordEnd);
				if (word.charAt(0) == '$') {
					variableReference = new VariableReference(valueStart
							+ wordStart, valueStart + wordEnd, word);
					allReferencesWithOrigOrder.add(variableReference);
					wordsToSkip++;
					// Read next word
					wordStart = getNonWhitespaceIndex(value, wordEnd);
					wordEnd = getWhitespaceIndex(value, wordStart);
				}
			}
			if (wordStart < wordEnd) {

				String word = value.substring(wordStart, wordEnd);
				singleTypeReference = new TypeReference(valueStart + wordStart,
						valueStart + wordEnd, word);
				splitSingleTypeReference(singleTypeReference, typeReferences);
				allReferencesWithOrigOrder.addAll(typeReferences);
				wordsToSkip++;
				// Read next word
				wordStart = getNonWhitespaceIndex(value, wordEnd);
				wordEnd = getWhitespaceIndex(value, wordStart);
			}
			if (tagKind == VAR && variableReference == null
					&& wordStart < wordEnd) {

				String word = value.substring(wordStart, wordEnd);
				if (word.charAt(0) == '$') {
					variableReference = new VariableReference(valueStart
							+ wordStart, valueStart + wordEnd, word);
					allReferencesWithOrigOrder.add(variableReference);
					wordsToSkip++;
				}
			}
		} else if (tagKind == PARAM || tagKind == PROPERTY
				|| tagKind == PROPERTY_READ || tagKind == PROPERTY_WRITE) {

			int firstWordStart = getNonWhitespaceIndex(value, 0);
			int firstWordEnd = getWhitespaceIndex(value, firstWordStart);
			if (firstWordStart < firstWordEnd) {

				int secondWordStart = getNonWhitespaceIndex(value, firstWordEnd);
				int secondWordEnd = getWhitespaceIndex(value, secondWordStart);
				if (secondWordStart < secondWordEnd) {

					String firstWord = value.substring(firstWordStart,
							firstWordEnd);
					String secondWord = value.substring(secondWordStart,
							secondWordEnd);
					if (firstWord.charAt(0) == '$'
							|| firstWord.startsWith("...$")) { //$NON-NLS-1$
						variableReference = new VariableReference(valueStart
								+ firstWordStart, valueStart + firstWordEnd,
								firstWord);
						singleTypeReference = new TypeReference(valueStart
								+ secondWordStart, valueStart + secondWordEnd,
								secondWord);
						splitSingleTypeReference(singleTypeReference,
								typeReferences);
						allReferencesWithOrigOrder.add(variableReference);
						allReferencesWithOrigOrder.addAll(typeReferences);
						// The two words following the tag name were splitted
						// into two references
						wordsToSkip = 2;
					} else if (secondWord.charAt(0) == '$'
							|| secondWord.startsWith("...$")) { //$NON-NLS-1$
						variableReference = new VariableReference(valueStart
								+ secondWordStart, valueStart + secondWordEnd,
								secondWord);
						singleTypeReference = new TypeReference(valueStart
								+ firstWordStart, valueStart + firstWordEnd,
								firstWord);
						splitSingleTypeReference(singleTypeReference,
								typeReferences);
						allReferencesWithOrigOrder.addAll(typeReferences);
						allReferencesWithOrigOrder.add(variableReference);
						// The two words following the tag name were splitted
						// into two references
						wordsToSkip = 2;
					}
				}
			}
		}

		descTexts = getDescTexts(wordsToSkip);
		trimmedDescText = getTrimmedDescText(wordsToSkip);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			for (SimpleReference ref : allReferencesWithOrigOrder) {
				ref.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.PHP_DOC_TAG;
	}

	public int getTagKind() {
		return tagKind;
	}

	public String getMatchedTag() {
		return matchedTag;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Variable reference, whose name is never empty or blank
	 * 
	 * @return variable reference (if present), null otherwise
	 */
	public VariableReference getVariableReference() {
		return variableReference;
	}

	/**
	 * Type reference, whose name is never empty or blank
	 * 
	 * @return type reference (not splitted), null otherwise
	 */
	public TypeReference getSingleTypeReference() {
		return singleTypeReference;
	}

	/**
	 * Type references, whose names are never empty or blank
	 * 
	 * @return all type references, empty list otherwise
	 */
	public List<TypeReference> getTypeReferences() {
		return typeReferences;
	}

	/**
	 * All references, whose names are never empty or blank
	 * 
	 * @return all references, empty list otherwise
	 */
	public List<SimpleReference> getAllReferencesWithOrigOrder() {
		return allReferencesWithOrigOrder;
	}

	public boolean isValidMethodDescriptorTag() {
		return isValidParamTag() || isValidPropertiesTag();
	}

	public boolean isValidPropertiesTag() {
		return (tagKind == PROPERTY || tagKind == PROPERTY_READ || tagKind == PROPERTY_WRITE)
				&& singleTypeReference != null && variableReference != null;
	}

	public boolean isValidParamTag() {
		return tagKind == PARAM && singleTypeReference != null
				&& variableReference != null;
	}

	public boolean isValidVarTag() {
		// NB: the variable reference is optional for @var tags
		return tagKind == VAR && singleTypeReference != null;
	}

	public void adjustStart(int start) {
		setStart(sourceStart() + start);
		setEnd(sourceEnd() + start);
	}

	public static String getTagKind(int kind) {
		switch (kind) {
		case ABSTRACT:
			return ABSTRACT_NAME;
		case AUTHOR:
			return AUTHOR_NAME;
		case DEPRECATED:
			return DEPRECATED_NAME;
		case FINAL:
			return FINAL_NAME;
		case GLOBAL:
			return GLOBAL_NAME;
		case NAME:
			return NAME_NAME;
		case RETURN:
			return RETURN_NAME;
		case PARAM:
			return PARAM_NAME;
		case SEE:
			return SEE_NAME;
		case STATIC:
			return STATIC_NAME;
		case STATICVAR:
			return STATICVAR_NAME;
		case TODO:
			return TODO_NAME;
		case VAR:
			return VAR_NAME;
		case PACKAGE:
			return PACKAGE_NAME;
		case ACCESS:
			return ACCESS_NAME;
		case CATEGORY:
			return CATEGORY_NAME;
		case COPYRIGHT:
			return COPYRIGHT_NAME;
		case DESC:
			return DESC_NAME;
		case EXAMPLE:
			return EXAMPLE_NAME;
		case FILESOURCE:
			return FILESOURCE_NAME;
		case IGNORE:
			return IGNORE_NAME;
		case INTERNAL:
			return INTERNAL_NAME;
		case LICENSE:
			return LICENSE_NAME;
		case LINK:
			return LINK_NAME;
		case SINCE:
			return SINCE_NAME;
		case SUBPACKAGE:
			return SUBPACKAGE_NAME;
		case TUTORIAL:
			return TUTORIAL_NAME;
		case USES:
			return USES_NAME;
		case VERSION:
			return VERSION_NAME;
		case THROWS:
			return THROWS_NAME;
		case PROPERTY:
			return PROPERTY_NAME;
		case PROPERTY_READ:
			return PROPERTY_READ_NAME;
		case PROPERTY_WRITE:
			return PROPERTY_WRITE_NAME;
		case METHOD:
			return METHOD_NAME;
		case NAMESPACE:
			return NAMESPACE_NAME;
		case INHERITDOC:
			return INHERITDOC_NAME;
		}
		return ERROR;
	}

}
