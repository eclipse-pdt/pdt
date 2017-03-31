/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.typeinference.evaluators.FormalParameterEvaluator;
import org.eclipse.php.internal.core.util.MagicMemberUtil;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;

public class PHPDocTag extends ASTNode {

	public enum TagKind {

		ABSTRACT("abstract"), //$NON-NLS-1$
		AUTHOR("author"), //$NON-NLS-1$
		DEPRECATED("deprecated"), //$NON-NLS-1$
		FINAL("final"), //$NON-NLS-1$
		GLOBAL("global"), //$NON-NLS-1$
		NAME("name"), //$NON-NLS-1$
		RETURN("return"), //$NON-NLS-1$
		PARAM("param"), //$NON-NLS-1$
		SEE("see"), //$NON-NLS-1$
		STATIC("static"), //$NON-NLS-1$
		STATICVAR("staticvar"), //$NON-NLS-1$
		TODO("todo"), //$NON-NLS-1$
		VAR("var"), //$NON-NLS-1$
		PACKAGE("package"), //$NON-NLS-1$
		ACCESS("access"), //$NON-NLS-1$
		CATEGORY("category"), //$NON-NLS-1$
		COPYRIGHT("copyright"), //$NON-NLS-1$
		DESC("desc"), //$NON-NLS-1$
		EXAMPLE("example"), //$NON-NLS-1$
		FILESOURCE("filesource"), //$NON-NLS-1$
		IGNORE("ignore"), //$NON-NLS-1$
		INTERNAL("internal"), //$NON-NLS-1$
		LICENSE("license"), //$NON-NLS-1$
		LINK("link"), //$NON-NLS-1$
		SINCE("since"), //$NON-NLS-1$
		SUBPACKAGE("subpackage"), //$NON-NLS-1$
		TUTORIAL("tutorial"), //$NON-NLS-1$
		USES("uses"), //$NON-NLS-1$
		VERSION("version"), //$NON-NLS-1$
		THROWS("throws"), //$NON-NLS-1$
		PROPERTY("property"), //$NON-NLS-1$
		PROPERTY_READ("property-read"), //$NON-NLS-1$
		PROPERTY_WRITE("property-write"), //$NON-NLS-1$
		METHOD("method"), //$NON-NLS-1$
		NAMESPACE("namespace"), //$NON-NLS-1$
		INHERITDOC("inheritdoc", "{@inheritdoc}"), //$NON-NLS-1$ //$NON-NLS-2$
		EXCEPTION("exception"), //$NON-NLS-1$
		MAGIC("magic"); //$NON-NLS-1$

		String name;
		String value;

		private static final class Mapping {
			private static final Map<Integer, TagKind> mapIds = new HashMap<Integer, TagKind>();
			private static final Map<String, TagKind> mapNames = new TreeMap<String, TagKind>(
					String.CASE_INSENSITIVE_ORDER);
			private static final Map<String, TagKind> mapValues = new TreeMap<String, TagKind>(
					String.CASE_INSENSITIVE_ORDER);
		}

		private TagKind(String name) {
			this(name, '@' + name);
		}

		private TagKind(String name, String value) {
			this.name = name;
			this.value = value;
			Mapping.mapIds.put(getId(), this);
			Mapping.mapNames.put(this.name, this);
			Mapping.mapValues.put(this.value, this);
		}

		// will never be null
		@NonNull
		public String getName() {
			return name;
		}

		// will never be null
		@NonNull
		public String getValue() {
			return value;
		}

		// for backward compatibility with PHPDocTagKinds
		public int getId() {
			// the ordinal value of a TagKind element
			// must be the value of its corresponding
			// PHPDocTagKinds property, so for example
			// getTagKind(PROPERTY_READ).ordinal() is equal to
			// PHPDocTagKinds.PROPERTY_READ
			return ordinal();
		}

		// For backward compatibility with PHPDocTagKinds.
		// Can be null.
		@Nullable
		public static TagKind getTagKind(int tagId) {
			return Mapping.mapIds.get(tagId);
		}

		// Search a TagKind by its name.
		// The search is case-insensitive.
		// Can be null.
		@Nullable
		public static TagKind getTagKindFromName(String name) {
			return Mapping.mapNames.get(name);
		}

		// Search a TagKind by its value.
		// The search is case-insensitive.
		// Can be null.
		@Nullable
		public static TagKind getTagKindFromValue(String value) {
			return Mapping.mapValues.get(value);
		}
	}

	private static final String ELLIPSIS_DOLLAR = FormalParameterEvaluator.ELLIPSIS + "$"; //$NON-NLS-1$

	private final TagKind tagKind;
	private final String matchedTag;
	private String value;
	private List<Scalar> texts;
	private VariableReference variableReference;
	private TypeReference singleTypeReference;
	private List<TypeReference> typeReferences;
	private List<SimpleReference> allReferencesWithOrigOrder;
	private List<String> descTexts;
	private String trimmedDescText;

	public PHPDocTag(int start, int end, TagKind tag, String matchedTag, String value, List<Scalar> texts) {
		super(start, end);
		if (!(0 <= start && start <= end) || tag == null || matchedTag == null || value == null || texts == null) {
			throw new IllegalArgumentException();
		}
		this.tagKind = tag;
		this.matchedTag = matchedTag;
		this.value = value;
		this.texts = texts;
		updateReferences(start, end);
	}

	/**
	 * Never null.
	 */
	@NonNull
	public List<Scalar> getTexts() {
		return texts;
	}

	/**
	 * Never null.
	 */
	@NonNull
	public List<String> getDescTexts() {
		return descTexts;
	}

	/**
	 * Never null.
	 */
	@NonNull
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
				if (StringUtils.isBlank(text)) {
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

	@NonNull
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

	protected void updateReferences(int start, int end) {
		// (re)set references
		variableReference = null;
		singleTypeReference = null;
		typeReferences = new ArrayList<TypeReference>();
		allReferencesWithOrigOrder = new ArrayList<SimpleReference>();
		descTexts = new ArrayList<String>();

		int valueStart = start + matchedTag.length();
		// For all unsupported tags
		int wordsToSkip = 0;

		if (tagKind == TagKind.RETURN || tagKind == TagKind.VAR || tagKind == TagKind.THROWS
				|| tagKind == TagKind.SEE) {

			// Read first word
			int wordStart = PHPTextSequenceUtilities.readForwardSpaces(value, 0);
			int wordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, wordStart);

			if (tagKind == TagKind.VAR && wordStart < wordEnd) {

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
			if (tagKind == TagKind.VAR && variableReference == null && wordStart < wordEnd) {

				String word = value.substring(wordStart, wordEnd);
				if (word.charAt(0) == '$') {
					variableReference = new VariableReference(valueStart + wordStart, valueStart + wordEnd, word);
					allReferencesWithOrigOrder.add(variableReference);
					wordsToSkip++;
				}
			}
		} else if (tagKind == TagKind.PARAM || tagKind == TagKind.PROPERTY || tagKind == TagKind.PROPERTY_READ
				|| tagKind == TagKind.PROPERTY_WRITE) {

			int firstWordStart = PHPTextSequenceUtilities.readForwardSpaces(value, 0);
			int firstWordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, firstWordStart);
			if (firstWordStart < firstWordEnd) {

				int secondWordStart = PHPTextSequenceUtilities.readForwardSpaces(value, firstWordEnd);
				int secondWordEnd = PHPTextSequenceUtilities.readForwardUntilSpaces(value, secondWordStart);
				if (secondWordStart < secondWordEnd) {

					String firstWord = value.substring(firstWordStart, firstWordEnd);
					String secondWord = value.substring(secondWordStart, secondWordEnd);
					if (firstWord.charAt(0) == '$' || firstWord.startsWith(ELLIPSIS_DOLLAR)) {
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

	/**
	 * @return a non-null TagKind element
	 */
	@NonNull
	public TagKind getTagKind() {
		return tagKind;
	}

	@NonNull
	public String getMatchedTag() {
		return matchedTag;
	}

	@NonNull
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
	@NonNull
	public List<TypeReference> getTypeReferences() {
		return typeReferences;
	}

	/**
	 * All references, whose names are never empty or blank
	 * 
	 * @return all references, empty list otherwise
	 */
	@NonNull
	public List<SimpleReference> getAllReferencesWithOrigOrder() {
		return allReferencesWithOrigOrder;
	}

	public boolean isValidMethodDescriptorTag() {
		return isValidParamTag() || isValidPropertiesTag();
	}

	public boolean isValidPropertiesTag() {
		return (tagKind == TagKind.PROPERTY || tagKind == TagKind.PROPERTY_READ || tagKind == TagKind.PROPERTY_WRITE)
				&& singleTypeReference != null && variableReference != null;
	}

	public boolean isValidParamTag() {
		return tagKind == TagKind.PARAM && singleTypeReference != null && variableReference != null;
	}

	public boolean isValidVarTag() {
		// NB: the variable reference is optional for @var tags
		return tagKind == TagKind.VAR && singleTypeReference != null;
	}

	public void adjustStart(int start) {
		setStart(sourceStart() + start);
		setEnd(sourceEnd() + start);
		for (Scalar text : texts) {
			text.setStart(text.sourceStart() + start);
			text.setEnd(text.sourceEnd() + start);
		}
		updateReferences(sourceStart(), sourceEnd());
	}

}
