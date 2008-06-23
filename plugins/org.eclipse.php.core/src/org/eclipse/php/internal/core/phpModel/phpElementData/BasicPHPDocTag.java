/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.phpElementData;

import java.util.HashMap;

public class BasicPHPDocTag implements PHPDocTag {

	private int id;
	private String value;
	private static HashMap nameToID;

	public BasicPHPDocTag(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getID() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static String getName(int id) {
		switch (id) {
			case ABSTRACT:
				return "abstract"; //$NON-NLS-1$
			case AUTHOR:
				return "author"; //$NON-NLS-1$
			case ACCESS:
				return "access"; //$NON-NLS-1$
			case CATEGORY:
				return "category"; //$NON-NLS-1$
			case COPYRIGHT:
				return "copyright"; //$NON-NLS-1$
			case DEPRECATED:
				return "deprecated"; //$NON-NLS-1$
			case EXAMPLE:
				return "example"; //$NON-NLS-1$
			case FINAL:
				return "final"; //$NON-NLS-1$
			case FILESOURCE:
				return "filesource"; //$NON-NLS-1$
			case GLOBAL:
				return "global"; //$NON-NLS-1$
			case IGNORE:
				return "ignore"; //$NON-NLS-1$
			case INTERNAL:
				return "internal"; //$NON-NLS-1$
			case LICENSE:
				return "license"; //$NON-NLS-1$
			case LINK:
				return "link"; //$NON-NLS-1$
			case NAME:
				return "name"; //$NON-NLS-1$
			case RETURN:
				return "return"; //$NON-NLS-1$
			case PACKAGE:
				return "package"; //$NON-NLS-1$
			case PARAM:
				return "param"; //$NON-NLS-1$
			case SEE:
				return "see"; //$NON-NLS-1$
			case SINCE:
				return "since"; //$NON-NLS-1$
			case STATIC:
				return "static"; //$NON-NLS-1$
			case STATICVAR:
				return "staticvar"; //$NON-NLS-1$
			case SUBPACKAGE:
				return "subpackage"; //$NON-NLS-1$
			case THROWS:
				return "throws"; //$NON-NLS-1$
			case TODO:
				return "todo"; //$NON-NLS-1$
			case TUTORIAL:
				return "tutorial"; //$NON-NLS-1$
			case USES:
				return "uses"; //$NON-NLS-1$
			case VAR:
				return "var"; //$NON-NLS-1$
			case VERSION:
				return "version"; //$NON-NLS-1$
			case DESC:
				return "desc"; //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	public static int getID(String name) {
		Integer rv = (Integer) getNameToID().get(name);
		if (rv == null) {
			return -1;
		}
		return rv.intValue();
	}

	private static HashMap getNameToID() {
		if (nameToID == null) {
			nameToID = new HashMap();
			nameToID.put("abstract", new Integer(ABSTRACT)); //$NON-NLS-1$
			nameToID.put("access", new Integer(ACCESS)); //$NON-NLS-1$
			nameToID.put("author", new Integer(AUTHOR)); //$NON-NLS-1$
			nameToID.put("category", new Integer(CATEGORY)); //$NON-NLS-1$
			nameToID.put("copyright", new Integer(COPYRIGHT)); //$NON-NLS-1$
			nameToID.put("deprecated", new Integer(DEPRECATED)); //$NON-NLS-1$
			nameToID.put("desc", new Integer(DESC)); //$NON-NLS-1$
			nameToID.put("example", new Integer(EXAMPLE)); //$NON-NLS-1$
			nameToID.put("final", new Integer(FINAL)); //$NON-NLS-1$
			nameToID.put("filesource", new Integer(FILESOURCE)); //$NON-NLS-1$
			nameToID.put("global", new Integer(GLOBAL)); //$NON-NLS-1$
			nameToID.put("ignore", new Integer(IGNORE)); //$NON-NLS-1$
			nameToID.put("internal", new Integer(INTERNAL)); //$NON-NLS-1$
			nameToID.put("license", new Integer(LICENSE)); //$NON-NLS-1$
			nameToID.put("link", new Integer(LINK)); //$NON-NLS-1$
			nameToID.put("name", new Integer(NAME)); //$NON-NLS-1$
			nameToID.put("return", new Integer(RETURN)); //$NON-NLS-1$
			nameToID.put("package", new Integer(PACKAGE)); //$NON-NLS-1$
			nameToID.put("param", new Integer(PARAM)); //$NON-NLS-1$
			nameToID.put("see", new Integer(SEE)); //$NON-NLS-1$
			nameToID.put("since", new Integer(SINCE)); //$NON-NLS-1$
			nameToID.put("static", new Integer(STATIC)); //$NON-NLS-1$
			nameToID.put("staticvar", new Integer(STATICVAR)); //$NON-NLS-1$
			nameToID.put("subpackage", new Integer(SUBPACKAGE)); //$NON-NLS-1$
			nameToID.put("throws", new Integer(THROWS)); //$NON-NLS-1$
			nameToID.put("todo", new Integer(TODO)); //$NON-NLS-1$
			nameToID.put("tutorial", new Integer(TUTORIAL)); //$NON-NLS-1$
			nameToID.put("uses", new Integer(USES)); //$NON-NLS-1$
			nameToID.put("var", new Integer(VAR)); //$NON-NLS-1$
			nameToID.put("version", new Integer(VERSION)); //$NON-NLS-1$
		}
		return nameToID;
	}

	public String toString() {
		StringBuffer b = new StringBuffer("@"); //$NON-NLS-1$
		b.append(getName(getID()));
		b.append(" "); //$NON-NLS-1$
		if (getValue() != null) {
			b.append(getValue().toString());
		}
		return b.toString();
	}
}
