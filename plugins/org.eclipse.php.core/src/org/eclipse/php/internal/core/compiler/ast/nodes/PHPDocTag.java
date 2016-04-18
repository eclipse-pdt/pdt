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

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.util.MagicMemberUtil;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;

public class PHPDocTag extends ASTNode implements PHPDocTagKinds {

	public static final String ERROR = "ERROR!!!"; //$NON-NLS-1$

	public enum Tag {

		ABSTRACT(PHPDocTagKinds.ABSTRACT, "abstract"), //$NON-NLS-1$
		AUTHOR(PHPDocTagKinds.AUTHOR, "author"), //$NON-NLS-1$
		DEPRECATED(PHPDocTagKinds.DEPRECATED, "deprecated"), //$NON-NLS-1$
		FINAL(PHPDocTagKinds.FINAL, "final"), //$NON-NLS-1$
		GLOBAL(PHPDocTagKinds.GLOBAL, "global"), //$NON-NLS-1$
		NAME(PHPDocTagKinds.NAME, "name"), //$NON-NLS-1$
		RETURN(PHPDocTagKinds.RETURN, "return"), //$NON-NLS-1$
		PARAM(PHPDocTagKinds.PARAM, "param"), //$NON-NLS-1$
		SEE(PHPDocTagKinds.SEE, "see"), //$NON-NLS-1$
		STATIC(PHPDocTagKinds.STATIC, "static"), //$NON-NLS-1$
		STATICVAR(PHPDocTagKinds.STATICVAR, "staticvar"), //$NON-NLS-1$
		TODO(PHPDocTagKinds.TODO, "todo"), //$NON-NLS-1$
		VAR(PHPDocTagKinds.VAR, "var"), //$NON-NLS-1$
		PACKAGE(PHPDocTagKinds.PACKAGE, "package"), //$NON-NLS-1$
		ACCESS(PHPDocTagKinds.ACCESS, "access"), //$NON-NLS-1$
		CATEGORY(PHPDocTagKinds.CATEGORY, "category"), //$NON-NLS-1$
		COPYRIGHT(PHPDocTagKinds.COPYRIGHT, "copyright"), //$NON-NLS-1$
		DESC(PHPDocTagKinds.DESC, "desc"), //$NON-NLS-1$
		EXAMPLE(PHPDocTagKinds.EXAMPLE, "example"), //$NON-NLS-1$
		FILESOURCE(PHPDocTagKinds.FILESOURCE, "filesource"), //$NON-NLS-1$
		IGNORE(PHPDocTagKinds.IGNORE, "ignore"), //$NON-NLS-1$
		INTERNAL(PHPDocTagKinds.INTERNAL, "internal"), //$NON-NLS-1$
		LICENSE(PHPDocTagKinds.LICENSE, "license"), //$NON-NLS-1$
		LINK(PHPDocTagKinds.LINK, "link"), //$NON-NLS-1$
		SINCE(PHPDocTagKinds.SINCE, "since"), //$NON-NLS-1$
		SUBPACKAGE(PHPDocTagKinds.SUBPACKAGE, "subpackage"), //$NON-NLS-1$
		TUTORIAL(PHPDocTagKinds.TUTORIAL, "tutorial"), //$NON-NLS-1$
		USES(PHPDocTagKinds.USES, "uses"), //$NON-NLS-1$
		VERSION(PHPDocTagKinds.VERSION, "version"), //$NON-NLS-1$
		THROWS(PHPDocTagKinds.THROWS, "throws"), //$NON-NLS-1$
		PROPERTY(PHPDocTagKinds.PROPERTY, "property"), //$NON-NLS-1$
		PROPERTY_READ(PHPDocTagKinds.PROPERTY_READ, "property-read"), //$NON-NLS-1$
		PROPERTY_WRITE(PHPDocTagKinds.PROPERTY_WRITE, "property-write"), //$NON-NLS-1$
		METHOD(PHPDocTagKinds.METHOD, "method"), //$NON-NLS-1$
		NAMESPACE(PHPDocTagKinds.NAMESPACE, "namespace"), //$NON-NLS-1$
		INHERITDOC(PHPDocTagKinds.INHERITDOC, "inheritdoc", "{@inheritdoc}"); //$NON-NLS-1$ //$NON-NLS-2$

		int tagKind;
		String name;
		String fullTagValue;

		private static class Mapping {
			private static Map<Integer, Tag> map = new HashMap<Integer, Tag>();
		}

		private Tag(int tagKind, String name) {
			this(tagKind, name, '@' + name);
		}

		private Tag(int tagKind, String name, String fullTagValue) {
			this.tagKind = tagKind;
			this.name = name;
			this.fullTagValue = fullTagValue;
			Mapping.map.put(tagKind, this);
		}

		public int getTagKind() {
			return tagKind;
		}

		public String getName() {
			return name;
		}

		public String getFullTagValue() {
			return fullTagValue;
		}

		public static String getTagName(int tagKind) {
			return Mapping.map.get(tagKind).getName();
		}

		public static String getFullTagValue(int tagKind) {
			return Mapping.map.get(tagKind).getFullTagValue();
		}
	}

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

	public PHPDocTag(int start, int end, int tagKind, String matchedTag, String value, List<Scalar> texts) {
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
				List<String> commentWords = Arrays.asList(MagicMemberUtil.WHITESPACE_SEPERATOR.split(text.trim()));
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

	private String removeFirstWords(String text, List<String> commentWords, int wordSize) {
		for (int i = 0; i < wordSize; i++) {
			int index = text.indexOf(commentWords.get(i));
			text = text.substring(commentWords.get(i).length() + index);
		}
		return text.trim();
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

	private void splitSingleTypeReference(TypeReference reference, List<TypeReference> types) {
		String word = reference.getName();
		int valueStart = reference.sourceStart();
		int classStart = getClassStartIndex(word, 0);
		int classEnd = getClassEndIndex(word, classStart);

		while (classStart < classEnd) {
			String className = word.substring(classStart, classEnd);
			types.add(new TypeReference(valueStart + classStart, valueStart + classEnd, className));

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

		if (tagKind == RETURN || tagKind == VAR || tagKind == THROWS || tagKind == SEE) {

			// Read first word
			int wordStart = PHPTextSequenceUtilities.readForwardSpaces(value, 0);
			int wordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, wordStart);

			if (tagKind == VAR && wordStart < wordEnd) {

				String word = value.substring(wordStart, wordEnd);
				if (word.charAt(0) == '$') {
					variableReference = new VariableReference(valueStart + wordStart, valueStart + wordEnd, word);
					allReferencesWithOrigOrder.add(variableReference);
					wordsToSkip++;
					// Read next word
					wordStart = PHPTextSequenceUtilities.readForwardSpaces(value, wordEnd);
					wordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, wordStart);
				}
			}
			if (wordStart < wordEnd) {

				String word = value.substring(wordStart, wordEnd);
				singleTypeReference = new TypeReference(valueStart + wordStart, valueStart + wordEnd, word);
				splitSingleTypeReference(singleTypeReference, typeReferences);
				allReferencesWithOrigOrder.addAll(typeReferences);
				wordsToSkip++;
				// Read next word
				wordStart = PHPTextSequenceUtilities.readForwardSpaces(value, wordEnd);
				wordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, wordStart);
			}
			if (tagKind == VAR && variableReference == null && wordStart < wordEnd) {

				String word = value.substring(wordStart, wordEnd);
				if (word.charAt(0) == '$') {
					variableReference = new VariableReference(valueStart + wordStart, valueStart + wordEnd, word);
					allReferencesWithOrigOrder.add(variableReference);
					wordsToSkip++;
				}
			}
		} else if (tagKind == PARAM || tagKind == PROPERTY || tagKind == PROPERTY_READ || tagKind == PROPERTY_WRITE) {

			int firstWordStart = PHPTextSequenceUtilities.readForwardSpaces(value, 0);
			int firstWordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, firstWordStart);
			if (firstWordStart < firstWordEnd) {

				int secondWordStart = PHPTextSequenceUtilities.readForwardSpaces(value, firstWordEnd);
				int secondWordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, secondWordStart);
				if (secondWordStart < secondWordEnd) {

					String firstWord = value.substring(firstWordStart, firstWordEnd);
					String secondWord = value.substring(secondWordStart, secondWordEnd);
					if (firstWord.charAt(0) == '$' || firstWord.startsWith("...$")) { //$NON-NLS-1$
						variableReference = new VariableReference(valueStart + firstWordStart,
								valueStart + firstWordEnd, firstWord);
						singleTypeReference = new TypeReference(valueStart + secondWordStart,
								valueStart + secondWordEnd, secondWord);
						splitSingleTypeReference(singleTypeReference, typeReferences);
						allReferencesWithOrigOrder.add(variableReference);
						allReferencesWithOrigOrder.addAll(typeReferences);
						// The two words following the tag name were splitted
						// into two references
						wordsToSkip = 2;
					} else if (secondWord.charAt(0) == '$' || secondWord.startsWith("...$")) { //$NON-NLS-1$
						variableReference = new VariableReference(valueStart + secondWordStart,
								valueStart + secondWordEnd, secondWord);
						singleTypeReference = new TypeReference(valueStart + firstWordStart, valueStart + firstWordEnd,
								firstWord);
						splitSingleTypeReference(singleTypeReference, typeReferences);
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
		return tagKind == PARAM && singleTypeReference != null && variableReference != null;
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
		String name = Tag.getTagName(kind);
		if (name != null) {
			return name;
		}
		return ERROR;
	}

}
