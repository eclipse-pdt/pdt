/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.types;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.wst.sse.core.utils.StringUtils;

/**
 * Simple template engine similar to PHPLib's
 */
public class TextTemplate {

	static class StructPatternHandler {
		protected Matcher matcher;
		protected Pattern pattern;
		protected String placeHolder;
		protected String regexp;
		protected String structName;

		public StructPatternHandler(final String regexp, final String placeHolder) {
			this.regexp = regexp;
			this.placeHolder = placeHolder;
		}

		public StructPatternHandler(final String regexp, final String placeHolder, final String structName) {
			this(regexp, placeHolder);
			setStructName(structName);
		}

		@Override
		public boolean equals(final Object o) {
			if (o instanceof StructPatternHandler) {
				final StructPatternHandler handler = (StructPatternHandler) o;
				return regexp.equals(handler.getRegexp()) && placeHolder.equals(handler.getPlaceHolder())
						&& (structName != null && structName.equals(handler.getStructName())
								|| structName == null && handler.getStructName() == null);
			}
			return false;
		}

		public Matcher getMatcher() {
			return matcher;
		}

		public String getPlaceHolder() {
			return placeHolder;
		}

		public String getRegexp() {
			return regexp;
		}

		/**
		 * @return the structName
		 */
		public String getStructName() {
			return structName;
		}

		public void setStructName(final String structName) {
			if (!structName.equals(this.structName)) {
				this.structName = structName;
				pattern = Pattern.compile(StringUtils.replace(REGEXP_STRUCT, REGEXP_ELEMENT_PLACEHOLDER, structName),
						flags);
				matcher = pattern.matcher(""); //$NON-NLS-1$
				matcher.reset();
			}
		}
	}

	final static private int flags = Pattern.MULTILINE | Pattern.DOTALL;

	final static public String NULL_VAR = ""; //$NON-NLS-1$

	protected static StructPatternHandler patternHandler;

	/**
	 * Placeholder for the following regular expressions which is replaced by an
	 * element name
	 */
	protected static String REGEXP_ELEMENT_PLACEHOLDER = "@"; //$NON-NLS-1$
	/**
	 * Valid element name
	 */
	protected static String REGEXP_ELEMENT_VALID = "(\\w+)"; //$NON-NLS-1$

	/**
	 * Struct definition pattern
	 */
	protected static String REGEXP_STRUCT = "/\\*\\s*" + REGEXP_ELEMENT_PLACEHOLDER + ":\\s*\\*/(.*?)/\\*\\s*:" //$NON-NLS-1$ //$NON-NLS-2$
			+ REGEXP_ELEMENT_PLACEHOLDER + "\\s*\\*/"; // e.g. /* //$NON-NLS-1$
														// Struct: */ Some Text
														// /* :Struct */

	/**
	 * Variable definition pattern
	 */
	protected static String REGEXP_VAR = "#" + REGEXP_ELEMENT_PLACEHOLDER + "#"; // e.g. //$NON-NLS-1$ //$NON-NLS-2$
																					// _VarName_

	static {
		patternHandler = new StructPatternHandler(REGEXP_STRUCT, REGEXP_ELEMENT_PLACEHOLDER);
	}

	/**
	 * @param text
	 *            Source text which should contain struct definition.
	 * @param structName
	 *            Name of a struct defined by {@link #REGEXP_STRUCT}
	 * @return Content of a found struct
	 */
	public static String extract(final String text, final String structName) {
		if ("".equals(text)) //$NON-NLS-1$
			return null;
		if (!structName.matches(REGEXP_ELEMENT_VALID))
			return null;
		patternHandler.setStructName(structName);
		final Matcher matcher = patternHandler.getMatcher().reset(text);
		if (matcher.find())
			return matcher.group();
		return null;
	}

	Map<String, String> vars;

	/**
	 * @param text
	 *            {@link #compile(String)}
	 * @return {@link #compile(String)}
	 */
	public String compile(String text) {
		Object val;
		String valCompiled;
		String var;

		if ("".equals(text)) //$NON-NLS-1$
			return ""; //$NON-NLS-1$

		final Matcher matcher = Pattern
				.compile(StringUtils.replace(REGEXP_VAR, REGEXP_ELEMENT_PLACEHOLDER, REGEXP_ELEMENT_VALID))
				.matcher(text);
		while (matcher.find()) {
			var = matcher.group(1);
			val = vars.get(var);
			if (val == null)
				continue;
			valCompiled = compile(val.toString()); // WARNING: recursion
			text = StringUtils.replace(text, matcher.group(0), valCompiled);
		}
		return text;
	}

	/**
	 * @param sourceVar
	 *            See {@link #get(String)}
	 * @param targetVar
	 *            See {@link #set(String, String)}
	 * @param append
	 *            Wheter to append or rewrite the compiled text to the source.
	 * @return targetVar new value
	 */
	public String compile(final String sourceVar, final String targetVar, final boolean append) {
		set(targetVar, (append ? get(targetVar) : "") + compile(get(sourceVar))); //$NON-NLS-1$
		return get(targetVar);
	}

	Pattern createStructPattern(final String structName) {
		return Pattern.compile(StringUtils.replace(REGEXP_STRUCT, REGEXP_ELEMENT_PLACEHOLDER, structName), flags);
	}

	/**
	 * @param sourceVar
	 *            See {@link #get(String)}
	 * @param structName
	 *            See {@link #extract(String, String)}
	 * @param targetVar
	 *            Variable which replaces the extracted structure
	 */
	public void extract(final String sourceVar, final String structName, String targetVar) {
		String text = get(sourceVar);
		if (text == null)
			return;
		if (targetVar == null)
			targetVar = structName;
		final String struct = extract(text, structName);
		if (struct != null) {
			text = StringUtils.replace(text, struct,
					StringUtils.replace(REGEXP_VAR, REGEXP_ELEMENT_PLACEHOLDER, structName));
			vars.put(targetVar, patternHandler.getMatcher().group(1));
			vars.put(sourceVar, text);
		}
	}

	/**
	 * @param var
	 *            Variable to get
	 * @return Variable's value
	 */
	public String get(final String var) {
		if (vars == null)
			return ""; //$NON-NLS-1$
		final Object val = vars.get(var);
		if (val == null)
			return ""; //$NON-NLS-1$
		return val.toString();
	}

	/**
	 * @param var
	 *            variable to set
	 * @param val
	 *            New variable's value
	 * @return previous {@link #get(String)}
	 */
	public String set(final String var, final String val) {
		if (vars == null)
			vars = new HashMap<>();
		return vars.put(var, val);
	}

	/**
	 * @param var
	 *            See {@link #set(String, String)}
	 * @param file
	 *            File to read
	 * @throws IOException
	 */
	public void setFile(final String var, final File file) throws IOException {
		set(var, FileUtils.getContents(file));
	}
}
