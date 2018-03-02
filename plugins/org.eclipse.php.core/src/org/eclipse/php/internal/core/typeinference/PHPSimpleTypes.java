/*******************************************************************************
 * Copyright (c) 2009, 2016, 2017, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [469267]
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.PHPVersion;

/**
 * This is a factory for PHP simple types
 * 
 * @author michael
 */
public class PHPSimpleTypes {

	private static class TypeInfo {
		public IEvaluatedType type;
		public PHPVersion hintableSince;

		public TypeInfo(@NonNull IEvaluatedType type, @Nullable PHPVersion hintableSince) {
			this.type = type;
			this.hintableSince = hintableSince;
		}

		public boolean isHintable() {
			return hintableSince != null;
		}

		public boolean isHintable(@Nullable PHPVersion phpVersion) {
			if (!isHintable()) {
				return false;
			}
			if (phpVersion == null) {
				return true;
			}

			return hintableSince == phpVersion || hintableSince.isLessThan(phpVersion);
		}
	}

	private static final Map<String, TypeInfo> SIMPLE_TYPES;

	@NonNull
	public static final IEvaluatedType NUMBER = new SimpleType(SimpleType.TYPE_NUMBER);
	@NonNull
	public static final IEvaluatedType BOOLEAN = new SimpleType(SimpleType.TYPE_BOOLEAN);
	@NonNull
	public static final IEvaluatedType STRING = new SimpleType(SimpleType.TYPE_STRING);
	@NonNull
	public static final IEvaluatedType OBJECT = new PHPClassType("object"); //$NON-NLS-1$
	@NonNull
	public static final IEvaluatedType RESOURCE = new PHPClassType("resource"); //$NON-NLS-1$
	@NonNull
	public static final IEvaluatedType ARRAY = new MultiTypeType() {
		@Override
		public final void addType(IEvaluatedType type) {
			// by security, make this instance "read-only"
		}

		@Override
		public final List<IEvaluatedType> getTypes() {
			// by security, make this instance "read-only"
			return Collections.unmodifiableList(super.getTypes());
		}

		@Override
		public final String getTypeName() {
			return "array"; //$NON-NLS-1$
		}
	};
	@NonNull
	public static final IEvaluatedType VOID = new SimpleType(SimpleType.TYPE_NONE);
	@NonNull
	public static final IEvaluatedType NULL = new SimpleType(SimpleType.TYPE_NULL);
	@NonNull
	public static final IEvaluatedType MIXED = new PHPClassType("mixed"); //$NON-NLS-1$

	/**
	 * @since 4.0
	 */
	@NonNull
	public static final IEvaluatedType CALLABLE = new PHPClassType("callable"); //$NON-NLS-1$

	/**
	 * @since 5.1
	 */
	@NonNull
	public static final IEvaluatedType ITERABLE = new PHPClassType("iterable"); //$NON-NLS-1$

	static {

		SIMPLE_TYPES = new HashMap<>();
		SIMPLE_TYPES.put("array", new TypeInfo(ARRAY, PHPVersion.PHP5)); //$NON-NLS-1$
		SIMPLE_TYPES.put("bool", new TypeInfo(BOOLEAN, PHPVersion.PHP7_0)); //$NON-NLS-1$
		SIMPLE_TYPES.put("boolean", new TypeInfo(BOOLEAN, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("int", new TypeInfo(NUMBER, PHPVersion.PHP7_0)); //$NON-NLS-1$
		SIMPLE_TYPES.put("integer", new TypeInfo(NUMBER, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("float", new TypeInfo(NUMBER, PHPVersion.PHP7_0)); //$NON-NLS-1$
		SIMPLE_TYPES.put("double", new TypeInfo(NUMBER, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("number", new TypeInfo(NUMBER, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("string", new TypeInfo(STRING, PHPVersion.PHP7_0)); //$NON-NLS-1$
		SIMPLE_TYPES.put("resource", new TypeInfo(RESOURCE, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("object", new TypeInfo(OBJECT, PHPVersion.PHP7_2)); //$NON-NLS-1$
		SIMPLE_TYPES.put("void", new TypeInfo(VOID, PHPVersion.PHP7_1)); //$NON-NLS-1$
		SIMPLE_TYPES.put("null", new TypeInfo(NULL, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("mixed", new TypeInfo(MIXED, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("callable", new TypeInfo(CALLABLE, PHPVersion.PHP5_4)); //$NON-NLS-1$
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=531379
		// "callback" is a (less complete) synonym of "callable"
		SIMPLE_TYPES.put("callback", new TypeInfo(CALLABLE, null)); //$NON-NLS-1$
		SIMPLE_TYPES.put("iterable", new TypeInfo(ITERABLE, PHPVersion.PHP7_1)); //$NON-NLS-1$
	}

	/**
	 * Returns {@link IEvaluatedType} for the PHP simple type name
	 * 
	 * @param type
	 * @return
	 */
	@Nullable
	public static IEvaluatedType fromString(@Nullable String type) {
		if (type == null) {
			return null;
		}
		TypeInfo typeInfo = SIMPLE_TYPES.get(type.toLowerCase());
		if (typeInfo != null) {
			return typeInfo.type;
		}

		return null;
	}

	/**
	 * Case sensitive version
	 * 
	 * @param type
	 * @return
	 * @since 3.5
	 * @deprecated
	 * @see PHPSimpleTypes#fromString(String)
	 */
	@Deprecated
	@Nullable
	public static IEvaluatedType fromStringCS(@Nullable String type) {
		if (type == null) {
			return null;
		}
		TypeInfo typeInfo = SIMPLE_TYPES.get(type);
		if (typeInfo != null) {
			return typeInfo.type;
		}

		return null;
	}

	/**
	 * Case sensitive version
	 * 
	 * @param type
	 * @return
	 * @since 3.5
	 * @deprecated
	 * @see PHPSimpleTypes#isSimpleType(String)
	 */
	@Deprecated
	public static boolean isSimpleTypeCS(@Nullable String type) {
		if (type == null) {
			return false;
		}
		return SIMPLE_TYPES.containsKey(type);
	}

	/**
	 * Case sensitive version
	 * 
	 * @param type
	 * @return
	 * @since 4.0
	 */
	public static boolean isSimpleType(@Nullable String type) {
		if (type == null) {
			return false;
		}
		return SIMPLE_TYPES.containsKey(type.toLowerCase());
	}

	/**
	 * @param name
	 * @param phpVersion
	 * @return
	 * @since 4.0
	 */
	public static boolean isHintable(@Nullable String name, @Nullable PHPVersion phpVersion) {
		if (name == null) {
			return false;
		}
		TypeInfo typeInfo = SIMPLE_TYPES.get(name.toLowerCase());
		if (typeInfo != null) {
			return typeInfo.isHintable(phpVersion);
		}

		return false;
	}

	/**
	 * @param type
	 * @return
	 * @since 4.0
	 */
	private static TypeInfo findInfo(@Nullable IEvaluatedType type) {
		if (type == null) {
			return null;
		}
		for (TypeInfo info : SIMPLE_TYPES.values()) {
			if (info.type.equals(type)) {
				return info;
			}
		}
		return null;
	}

	/**
	 * @param type
	 * @return
	 * @since 4.0
	 */
	public static boolean isSimpleType(@Nullable IEvaluatedType type) {
		return findInfo(type) != null;
	}

	/**
	 * @param type
	 * @param phpVersion
	 * @return
	 * @since 4.0
	 */
	public static boolean isHintable(@Nullable IEvaluatedType type, @Nullable PHPVersion phpVersion) {
		TypeInfo info = findInfo(type);
		if (info != null) {
			return info.isHintable(phpVersion);
		}

		return false;
	}
}
