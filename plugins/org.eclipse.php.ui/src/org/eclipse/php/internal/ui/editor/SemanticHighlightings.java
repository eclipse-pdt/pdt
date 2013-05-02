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
package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.graphics.RGB;

/**
 * Semantic highlightings
 * 
 * @since 3.0
 */
public class SemanticHighlightings {

	/**
	 * A named preference part that controls the highlighting of static final
	 * fields.
	 */
	public static final String STATIC_FINAL_FIELD = "staticFinalField"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of static fields.
	 */
	public static final String STATIC_FIELD = "staticField"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of fields.
	 */
	public static final String FIELD = "field"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of method
	 * declarations.
	 */
	public static final String METHOD_DECLARATION = "methodDeclarationName"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of static method
	 * invocations.
	 */
	public static final String STATIC_METHOD_INVOCATION = "staticMethodInvocation"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of inherited
	 * method invocations.
	 */
	public static final String INHERITED_METHOD_INVOCATION = "inheritedMethodInvocation"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of annotation
	 * element references.
	 * 
	 * @since 3.1
	 */
	public static final String ANNOTATION_ELEMENT_REFERENCE = "annotationElementReference"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of abstract method
	 * invocations.
	 */
	public static final String ABSTRACT_METHOD_INVOCATION = "abstractMethodInvocation"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of local
	 * variables.
	 */
	public static final String LOCAL_VARIABLE_DECLARATION = "localVariableDeclaration"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of local
	 * variables.
	 */
	public static final String NULL_VALUE = "nullValue"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of parameter
	 * variables.
	 */
	public static final String PARAMETER_VARIABLE = "parameterVariable"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of deprecated
	 * members.
	 */
	public static final String DEPRECATED_MEMBER = "deprecatedMember"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of type
	 * parameters.
	 * 
	 * @since 3.1
	 */
	public static final String TYPE_VARIABLE = "typeParameter"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of methods
	 * (invocations and declarations).
	 * 
	 * @since 3.1
	 */
	public static final String METHOD = "method"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of auto(un)boxed
	 * expressions.
	 * 
	 * @since 3.1
	 */
	public static final String AUTOBOXING = "autoboxing"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of classes.
	 * 
	 * @since 3.2
	 */
	public static final String CLASS = "class"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of enums.
	 * 
	 * @since 3.2
	 */
	public static final String ENUM = "enum"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of interfaces.
	 * 
	 * @since 3.2
	 */
	public static final String INTERFACE = "interface"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of annotations.
	 * 
	 * @since 3.2
	 */
	public static final String ANNOTATION = "annotation"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of type arguments.
	 * 
	 * @since 3.2
	 */
	public static final String TYPE_ARGUMENT = "typeArgument"; //$NON-NLS-1$

	/**
	 * A named preference part that controls the highlighting of numbers.
	 * 
	 * @since 3.4
	 */
	public static final String NUMBER = "number"; //$NON-NLS-1$

	/**
	 * Semantic highlightings
	 */
	private static SemanticHighlighting[] fgSemanticHighlightings;

	/**
	 * A named preference that controls the given semantic highlighting's color.
	 * 
	 * @param semanticHighlighting
	 *            the semantic highlighting
	 * @return the color preference key
	 */
	public static String getColorPreferenceKey(
			SemanticHighlighting semanticHighlighting) {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ semanticHighlighting.getPreferenceKey()
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_COLOR_SUFFIX;
	}

	/**
	 * A named preference that controls if the given semantic highlighting has
	 * the text attribute bold.
	 * 
	 * @param semanticHighlighting
	 *            the semantic highlighting
	 * @return the bold preference key
	 */
	public static String getBoldPreferenceKey(
			SemanticHighlighting semanticHighlighting) {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ semanticHighlighting.getPreferenceKey()
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_BOLD_SUFFIX;
	}

	/**
	 * A named preference that controls if the given semantic highlighting has
	 * the text attribute italic.
	 * 
	 * @param semanticHighlighting
	 *            the semantic highlighting
	 * @return the italic preference key
	 */
	public static String getItalicPreferenceKey(
			SemanticHighlighting semanticHighlighting) {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ semanticHighlighting.getPreferenceKey()
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_ITALIC_SUFFIX;
	}

	/**
	 * A named preference that controls if the given semantic highlighting has
	 * the text attribute strikethrough.
	 * 
	 * @param semanticHighlighting
	 *            the semantic highlighting
	 * @return the strikethrough preference key
	 * @since 3.1
	 */
	public static String getStrikethroughPreferenceKey(
			SemanticHighlighting semanticHighlighting) {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ semanticHighlighting.getPreferenceKey()
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_STRIKETHROUGH_SUFFIX;
	}

	/**
	 * A named preference that controls if the given semantic highlighting has
	 * the text attribute underline.
	 * 
	 * @param semanticHighlighting
	 *            the semantic highlighting
	 * @return the underline preference key
	 * @since 3.1
	 */
	public static String getUnderlinePreferenceKey(
			SemanticHighlighting semanticHighlighting) {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ semanticHighlighting.getPreferenceKey()
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_UNDERLINE_SUFFIX;
	}

	/**
	 * A named preference that controls if the given semantic highlighting is
	 * enabled.
	 * 
	 * @param semanticHighlighting
	 *            the semantic highlighting
	 * @return the enabled preference key
	 */
	public static String getEnabledPreferenceKey(
			SemanticHighlighting semanticHighlighting) {
		return PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_PREFIX
				+ semanticHighlighting.getPreferenceKey()
				+ PreferenceConstants.EDITOR_SEMANTIC_HIGHLIGHTING_ENABLED_SUFFIX;
	}

	/**
	 * @return The semantic highlightings, the order defines the precedence of
	 *         matches, the first match wins.
	 */
	public static SemanticHighlighting[] getSemanticHighlightings() {
		if (fgSemanticHighlightings == null)
			fgSemanticHighlightings = new SemanticHighlighting[] { new NullHighlighting(),
			// new DeprecatedMemberHighlighting(),
			// new AutoboxHighlighting(),
			// new StaticFinalFieldHighlighting(),
			// new StaticFieldHighlighting(),
			// new FieldHighlighting(),
			// new MethodDeclarationHighlighting(),
			// new StaticMethodInvocationHighlighting(),
			// new AbstractMethodInvocationHighlighting(),
			// new AnnotationElementReferenceHighlighting(),
			// new InheritedMethodInvocationHighlighting(),
			// new ParameterVariableHighlighting(),
			// new LocalVariableDeclarationHighlighting(),
			// new LocalVariableHighlighting(),
			// new TypeVariableHighlighting(), // before type arguments!
			// new MethodHighlighting(), // before types to get ctors
			// new TypeArgumentHighlighting(), // before other types
			// new ClassHighlighting(),
			// new EnumHighlighting(),
			// new AnnotationHighlighting(), // before interfaces
			// new InterfaceHighlighting(),
			// new NumberHighlighting(),
			};
		return fgSemanticHighlightings;
	}

	/**
	 * Semantic highlighting for local variables.
	 */
	private static final class NullHighlighting extends SemanticHighlighting {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.php.internal.ui.editor.SemanticHighlighting#consumesLiteral
		 * (org.eclipse.php.internal.ui.editor.SemanticToken)
		 */
		@Override
		public boolean consumesLiteral(SemanticToken token) {
			final Expression node = token.getLiteral();
			return node.isNullExpression();
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.javaeditor.SemanticHighlighting#
		 * getPreferenceKey()
		 */
		public String getPreferenceKey() {
			return NULL_VALUE;
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.javaeditor.ISemanticHighlighting#
		 * getDefaultTextColor()
		 */
		public RGB getDefaultDefaultTextColor() {
			return new RGB(0, 0, 0);
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.javaeditor.ISemanticHighlighting#
		 * getDefaultTextStyleBold()
		 */
		public boolean isBoldByDefault() {
			return true;
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.javaeditor.SemanticHighlighting#
		 * isItalicByDefault()
		 */
		public boolean isItalicByDefault() {
			return true;
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.javaeditor.SemanticHighlighting#
		 * isEnabledByDefault()
		 */
		public boolean isEnabledByDefault() {
			return true;
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.javaeditor.ISemanticHighlighting#
		 * getDisplayName()
		 */
		public String getDisplayName() {
			return ""; //$NON-NLS-1$
		}

		/*
		 * @see
		 * org.eclipse.jdt.internal.ui.javaeditor.SemanticHighlighting#consumes
		 * (org.eclipse.jdt.internal.ui.javaeditor.SemanticToken)
		 */
		public boolean consumes(SemanticToken token) {
			return false;
		}
	}

	/**
	 * Initialize default preferences in the given preference store.
	 * 
	 * @param store
	 *            The preference store
	 */
	public static void initDefaults(IPreferenceStore store) {
		SemanticHighlighting[] semanticHighlightings = getSemanticHighlightings();
		for (int i = 0, n = semanticHighlightings.length; i < n; i++) {
			SemanticHighlighting semanticHighlighting = semanticHighlightings[i];
			setDefaultAndFireEvent(store, SemanticHighlightings
					.getColorPreferenceKey(semanticHighlighting),
					semanticHighlighting.getDefaultTextColor());
			store.setDefault(SemanticHighlightings
					.getBoldPreferenceKey(semanticHighlighting),
					semanticHighlighting.isBoldByDefault());
			store.setDefault(SemanticHighlightings
					.getItalicPreferenceKey(semanticHighlighting),
					semanticHighlighting.isItalicByDefault());
			store.setDefault(SemanticHighlightings
					.getStrikethroughPreferenceKey(semanticHighlighting),
					semanticHighlighting.isStrikethroughByDefault());
			store.setDefault(SemanticHighlightings
					.getUnderlinePreferenceKey(semanticHighlighting),
					semanticHighlighting.isUnderlineByDefault());
			store.setDefault(SemanticHighlightings
					.getEnabledPreferenceKey(semanticHighlighting),
					semanticHighlighting.isEnabledByDefault());
		}
	}

	/**
	 * Tests whether <code>event</code> in <code>store</code> affects the
	 * enablement of semantic highlighting.
	 * 
	 * @param store
	 *            the preference store where <code>event</code> was observed
	 * @param event
	 *            the property change under examination
	 * @return <code>true</code> if <code>event</code> changed semantic
	 *         highlighting enablement, <code>false</code> if it did not
	 * @since 3.1
	 */
	public static boolean affectsEnablement(IPreferenceStore store,
			PropertyChangeEvent event) {
		String relevantKey = null;
		SemanticHighlighting[] highlightings = getSemanticHighlightings();
		for (int i = 0; i < highlightings.length; i++) {
			if (event.getProperty().equals(
					getEnabledPreferenceKey(highlightings[i]))) {
				relevantKey = event.getProperty();
				break;
			}
		}
		if (relevantKey == null)
			return false;

		for (int i = 0; i < highlightings.length; i++) {
			String key = getEnabledPreferenceKey(highlightings[i]);
			if (key.equals(relevantKey))
				continue;
			if (store.getBoolean(key))
				return false; // another is still enabled or was enabled before
		}

		// all others are disabled, so toggling relevantKey affects the
		// enablement
		return true;
	}

	/**
	 * Tests whether semantic highlighting is currently enabled.
	 * 
	 * @param store
	 *            the preference store to consult
	 * @return <code>true</code> if semantic highlighting is enabled,
	 *         <code>false</code> if it is not
	 * @since 3.1
	 */
	public static boolean isEnabled(IPreferenceStore store) {
		SemanticHighlighting[] highlightings = getSemanticHighlightings();
		boolean enable = false;
		for (int i = 0; i < highlightings.length; i++) {
			String enabledKey = getEnabledPreferenceKey(highlightings[i]);
			if (!store.getBoolean(enabledKey)) {
				enable = true;
				break;
			}
		}

		return enable;
	}

	/**
	 * If the setting pointed to by <code>oldKey</code> is not the default
	 * setting, store that setting under <code>newKey</code> and reset
	 * <code>oldKey</code> to its default setting.
	 * <p>
	 * Returns <code>true</code> if any changes were made.
	 * </p>
	 * 
	 * @param store
	 *            the preference store to read from and write to
	 * @param oldKey
	 *            the old preference key
	 * @param newKey
	 *            the new preference key
	 * @return <code>true</code> if <code>store</code> was modified,
	 *         <code>false</code> if not
	 * @since 3.1
	 */
	private static boolean conditionalReset(IPreferenceStore store,
			String oldKey, String newKey) {
		if (!store.isDefault(oldKey)) {
			if (store.isDefault(newKey))
				store.setValue(newKey, store.getString(oldKey));
			store.setToDefault(oldKey);
			return true;
		}
		return false;
	}

	/**
	 * Sets the default value and fires a property change event if necessary.
	 * 
	 * @param store
	 *            the preference store
	 * @param key
	 *            the preference key
	 * @param newValue
	 *            the new value
	 * @since 3.3
	 */
	private static void setDefaultAndFireEvent(IPreferenceStore store,
			String key, RGB newValue) {
		RGB oldValue = null;
		if (store.isDefault(key))
			oldValue = PreferenceConverter.getDefaultColor(store, key);

		PreferenceConverter.setDefault(store, key, newValue);

		if (oldValue != null && !oldValue.equals(newValue))
			store.firePropertyChangeEvent(key, oldValue, newValue);
	}

	/**
	 * Do not instantiate
	 */
	private SemanticHighlightings() {
	}
}
