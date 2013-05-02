package org.eclipse.php.internal.core.util;

import java.util.regex.Pattern;

public class MagicMemberUtil {
	public static final Pattern WHITESPACE_SEPERATOR = Pattern.compile("\\s+"); //$NON-NLS-1$

	public static class MagicMember {
		public String name;
		public String desc;
	}

	public static class MagicMethod extends MagicMember {
		public String[] parameterNames;
		public String[] parameterInitializers;
		public String[] parameterTypes;
		public String returnType;
	}

	public static class MagicField extends MagicMember {
		public String type;
	}

	public static String removeParenthesis(final String[] split) {
		final String name = split[1];
		return name.endsWith("()") ? name.substring(0, name.length() - 2) //$NON-NLS-1$
				: name;
	}

	public static String removeParenthesis2(final String[] split) {
		String name = split[1];
		int index = name.indexOf('(');
		if (index > 0) {
			name = name.substring(0, index);
		}
		return name.endsWith("()") ? name.substring(0, name.length() - 2) //$NON-NLS-1$
				: name;
	}

	public static MagicMethod getMagicMethod(String docValue) {
		docValue = docValue.trim();
		final String[] split = WHITESPACE_SEPERATOR.split(docValue);
		if (split.length < 2) {
			return null;
		}
		MagicMethod mi = new MagicMethod();
		try {
			mi.returnType = split[0];
			mi.name = removeParenthesis(split);
			if (split.length > 2) {
				for (int i = 0; i < 2; i++) {
					docValue = docValue.substring(split[i].length()).trim();
				}
				if (docValue.startsWith(mi.name)) {
					docValue = docValue.substring(mi.name.length()).trim();
					if (docValue.startsWith("(") && docValue.indexOf(')') > 0) { //$NON-NLS-1$
						int endIndex = docValue.indexOf(')');
						String paramsString = docValue.substring(1, endIndex);
						String[] params = paramsString.split(","); //$NON-NLS-1$
						String[] paramType = new String[params.length];
						String[] paramName = new String[params.length];
						String[] paramValue = new String[params.length];
						// List<String>
						for (int i = 0; i < params.length; i++) {
							String param = params[i];
							String value = null;
							int equalIndex = param.indexOf('=');
							if (equalIndex > 0) {
								value = param.substring(equalIndex + 1).trim();
								param = param.substring(0, equalIndex).trim();
							}
							String[] paramPair = WHITESPACE_SEPERATOR
									.split(param.trim());
							if (paramPair.length == 1) {
								paramName[i] = paramPair[0];
							} else if (paramPair.length == 2) {
								paramType[i] = paramPair[0];
								paramName[i] = paramPair[1];
							}
							if (value != null) {
								paramValue[i] = value;
							}
						}
						mi.parameterNames = paramName;
						mi.parameterTypes = paramType;
						mi.parameterInitializers = paramValue;
						if (docValue.length() > endIndex) {
							mi.desc = docValue.substring(endIndex + 1);
						}
					} else {
						mi.desc = docValue;
					}
				} else {
					mi.desc = docValue;
				}

			}
		} catch (Exception e) {
			mi = null;
		}

		return mi;
	}

	/**
	 * for @method int setName(string $name,string $name1)
	 */
	public static MagicMethod getMagicMethod2(String docValue) {
		docValue = docValue.trim();
		final String[] split = WHITESPACE_SEPERATOR.split(docValue);
		if (split.length < 2) {
			return null;
		}
		MagicMethod mi = new MagicMethod();
		try {
			mi.returnType = split[0];
			mi.name = removeParenthesis2(split);
			if (split.length > 1) {
				for (int i = 0; i < 1; i++) {
					docValue = docValue.substring(split[i].length()).trim();
				}
				if (docValue.startsWith(mi.name)) {
					docValue = docValue.substring(mi.name.length()).trim();
					if (docValue.startsWith("(") && docValue.indexOf(')') > 0) { //$NON-NLS-1$
						int endIndex = docValue.indexOf(')');
						String paramsString = docValue.substring(1, endIndex);
						String[] params = paramsString.split(","); //$NON-NLS-1$
						String[] paramType = new String[params.length];
						String[] paramName = new String[params.length];
						String[] paramValue = new String[params.length];
						// List<String>
						for (int i = 0; i < params.length; i++) {
							String param = params[i];
							String value = null;
							int equalIndex = param.indexOf('=');
							if (equalIndex > 0) {
								value = param.substring(equalIndex + 1).trim();
								param = param.substring(0, equalIndex).trim();
							}
							String[] paramPair = WHITESPACE_SEPERATOR
									.split(param.trim());
							if (paramPair.length == 1) {
								paramName[i] = paramPair[0];
							} else if (paramPair.length == 2) {
								paramType[i] = paramPair[0];
								paramName[i] = paramPair[1];
							}
							if (value != null) {
								paramValue[i] = value;
							}
						}
						mi.parameterNames = paramName;
						mi.parameterTypes = paramType;
						mi.parameterInitializers = paramValue;
						if (docValue.length() > endIndex) {
							mi.desc = docValue.substring(endIndex + 1);
						}
					} else {
						mi.desc = docValue;
					}
				} else {
					mi.desc = docValue;
				}

			}
		} catch (Exception e) {
			mi = null;
		}

		return mi;
	}

	public static MagicField getMagicField(String docValue) {
		docValue = docValue.trim();
		final String[] split = WHITESPACE_SEPERATOR.split(docValue);
		if (split.length < 2) {
			return null;
		}
		MagicField info = new MagicField();
		try {
			info.name = split[1];
			info.type = split[0];
			if (split.length > 2) {
				for (int i = 0; i < 2; i++) {
					docValue = docValue.substring(split[i].length()).trim();
				}
				info.desc = docValue;
			}
		} catch (Exception e) {
			info = null;
		}
		return info;
	}
}
