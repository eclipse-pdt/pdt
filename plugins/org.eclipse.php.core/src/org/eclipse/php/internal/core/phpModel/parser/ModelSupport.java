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
package org.eclipse.php.internal.core.phpModel.parser;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.BodyDeclaration.Modifier;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public class ModelSupport {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	public static final CodeDataFilter STATIC_VARIABLES_FILTER = new StaticVariablesFilter(true);
	public static final CodeDataFilter NOT_STATIC_VARIABLES_FILTER = new StaticVariablesFilter(false);
	public static final CodeDataFilter STATIC_FUNCTIONS_FILTER = new StaticFunctionsFilter(true);
	public static final CodeDataFilter INTERNAL_CODEDATA_FILTER = new InternalPhpCodeData();
	public static final CodeDataFilter NOT_MAGIC_FUNCTION = new MagicFunctionFilter(false);

	public static final CodeDataFilter PIRVATE_ACCESS_LEVEL_FILTER = new AccessLevelFilter() {
		public boolean verify(int modifier) {
			return true;
		}
	};
	public static final CodeDataFilter PROTECTED_ACCESS_LEVEL_FILTER = new AccessLevelFilter() {
		public boolean verify(int modifier) {
			return !PHPModifier.isPrivate(modifier);
		}
	};
	public static final CodeDataFilter PUBLIC_ACCESS_LEVEL_FILTER = new AccessLevelFilter() {
		public boolean verify(int modifier) {
			return (!PHPModifier.isPrivate(modifier) && !PHPModifier.isProtected(modifier));
		}
	};
	public static final CodeDataFilter NOT_FINAL_FILTER = new AccessLevelFilter() {
		public boolean verify(int modifier) {
			return (!PHPModifier.isFinal(modifier));
		}
	};

	public static final CodeDataFilter PROTECTED_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC = new AccessLevelFilter() {
		//implements stub with no usage
		public boolean verify(int modifier) {
			return true;
		}

		public boolean accept(CodeData codeData) {
			if (codeData instanceof PHPClassVarData) {
				return PROTECTED_ACCESS_LEVEL_FILTER.accept(codeData) && STATIC_VARIABLES_FILTER.accept(codeData);
			}
			if (codeData instanceof PHPFunctionData) {
				return PROTECTED_ACCESS_LEVEL_FILTER.accept(codeData);
			}

			return true;
		}
	};

	public static final CodeDataFilter PUBLIC_ACCESS_LEVEL_FILTER_EXCLUDE_VARS_NOT_STATIC = new AccessLevelFilter() {
		//implements the stub with no usage
		public boolean verify(int modifier) {
			return true;
		}

		public boolean accept(CodeData codeData) {
			if (codeData instanceof PHPClassVarData) {
				return PUBLIC_ACCESS_LEVEL_FILTER.accept(codeData) && STATIC_VARIABLES_FILTER.accept(codeData);
			}
			if (codeData instanceof PHPFunctionData) {
				return PUBLIC_ACCESS_LEVEL_FILTER.accept(codeData);
			}
			return true;
		}
	};

	private static final int SMALL_ARRAY_SIZE = 12;

	private ModelSupport() {
	}

	public static CodeData[] getCodeDataStartingWith(CodeData[] sortedArray, String startsWith) {
		return getCodeDataStartingWith(sortedArray, startsWith, false, false);
	}

	public static CodeData[] getFileDataStartingWith(CodeData[] sortedArray, String startsWith) {
		return getCodeDataStartingWith(sortedArray, startsWith, true, false);
	}

	public static CodeData[] getCodeDataStartingWithCS(CodeData[] sortedArray, String startsWith) {
		return getCodeDataStartingWith(sortedArray, startsWith, false, true);
	}

	public static CodeData[] getFileDataStartingWithCS(CodeData[] sortedArray, String startsWith) {
		return getCodeDataStartingWith(sortedArray, startsWith, true, true);
	}

	private static CodeData[] getCodeDataStartingWith(CodeData[] sortedArray, String startsWith, boolean useComparableName, boolean caseSensitive) {
		if (sortedArray == null) {
			sortedArray = PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
		}
		if (startsWith == null || startsWith.equals(EMPTY_STRING) || sortedArray.length == 0) {
			return sortedArray;
		}

		int start = getFirstMatch(sortedArray, startsWith, false, useComparableName, caseSensitive);
		if (start < 0) {
			return PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
		}

		int end = sortedArray.length - 1;
		int length = startsWith.length();
		for (int i = start; i < sortedArray.length; i++) {
			String name = useComparableName ? ((ComparableName) sortedArray[i]).getComparableName() : sortedArray[i].getName();
			name = (name.length() > length) ? name.substring(0, length) : name;
			if (caseSensitive) {
				if (name.compareTo(startsWith) > 0) {
					end = i - 1;
					break;
				}
			} else if (name.compareToIgnoreCase(startsWith) > 0) {
				end = i - 1;
				break;
			}
		}
		int arrayLength = (end < start) ? 0 : end - start + 1;
		CodeData[] rv = new CodeData[arrayLength];
		System.arraycopy(sortedArray, start, rv, 0, rv.length);
		return rv;
	}

	public static int getFirstMatch(CodeData[] sortedArray, String searchName, boolean exactName) {
		return getFirstMatch(sortedArray, searchName, exactName, false, false);
	}

	public static int getFirstMatchCS(CodeData[] sortedArray, String searchName, boolean exactName) {
		return getFirstMatch(sortedArray, searchName, exactName, false, true);
	}

	//Binary search for the search start position
	private static int getFirstMatch(CodeData[] sortedArray, String searchName, boolean exactName, boolean useComparableName, boolean caseSensitive) {
		// if the searched name is empty.
		int searchNameLength = searchName.length();
		if (searchNameLength == 0) {
			if (exactName) {
				return -1;
			}
			return 0;
		}
		// if the array is empty.
		if (sortedArray == null || sortedArray.length == 0) {
			return -1;
		}
		// small Array, we use simple search.
		if (sortedArray.length < SMALL_ARRAY_SIZE) {
			for (int i = 0; i < sortedArray.length; i++) {
				String name = useComparableName ? ((ComparableName) sortedArray[i]).getComparableName() : sortedArray[i].getName();
				if (!exactName && name.length() > searchNameLength) {
					name = name.substring(0, searchNameLength);
				}
				if (caseSensitive) {
					if (searchName.equals(name)) {
						return i;
					}
				} else if (searchName.equalsIgnoreCase(name)) {
					return i;
				}
			}
			return -1;
		}

		// binary search.
		int start = 0;
		int end = sortedArray.length - 1;
		while (start <= end) {
			int mid = (start + end) >> 1;
			String name = useComparableName ? ((ComparableName) sortedArray[mid]).getComparableName() : sortedArray[mid].getName();
			if (!exactName && name.length() > searchNameLength) {
				name = name.substring(0, searchNameLength);
			}
			int compareResult = (caseSensitive ? name.compareTo(searchName) : name.compareToIgnoreCase(searchName));
			if (compareResult == 0) {
				start = mid;
				break;
			}
			if (compareResult < 0) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		// verify result.
		if (start < 0 || start >= sortedArray.length) {
			return -1;
		}
		String name = useComparableName ? ((ComparableName) sortedArray[start]).getComparableName() : sortedArray[start].getName();
		if (!exactName && name.length() > searchNameLength) {
			name = name.substring(0, searchNameLength);
		}
		if (caseSensitive) {
			if (!name.equals(searchName)) {
				return -1;
			}
		} else if (!name.equalsIgnoreCase(searchName)) {
			return -1;
		}
		// find the first matching name.
		for (start -= 1; start >= 0; start--) {
			name = useComparableName ? ((ComparableName) sortedArray[start]).getComparableName() : sortedArray[start].getName();
			if (!exactName && name.length() > searchNameLength) {
				name = name.substring(0, searchNameLength);
			}
			if (caseSensitive) {
				if (!name.equals(searchName)) {
					break;
				}
			} else if (!name.equalsIgnoreCase(searchName)) {
				break;
			}
		}
		return start + 1;
	}

	//Binary search for the search start position
	private static int getFirstMatch(File[] sortedArray, String searchName, boolean caseSensitive) {
		// if the searched name is empty.
		int searchNameLength = searchName.length();
		if (searchNameLength == 0) {
			return 0;
		}
		// if the array is empty.
		if (sortedArray == null || sortedArray.length == 0) {
			return -1;
		}
		// small Array, we use simple search.
		if (sortedArray.length < SMALL_ARRAY_SIZE) {
			for (int i = 0; i < sortedArray.length; i++) {
				String name = sortedArray[i].getName();
				if (name.length() > searchNameLength) {
					name = name.substring(0, searchNameLength);
				}
				if (caseSensitive) {
					if (searchName.equals(name)) {
						return i;
					}
				} else if (searchName.equalsIgnoreCase(name)) {
					return i;
				}
			}
			return -1;
		}

		// binary search.
		int start = 0;
		int end = sortedArray.length - 1;
		while (start <= end) {
			int mid = (start + end) >> 1;
			String name = sortedArray[mid].getName();
			if (name.length() > searchNameLength) {
				name = name.substring(0, searchNameLength);
			}
			int compareResult = (caseSensitive ? name.compareTo(searchName) : name.compareToIgnoreCase(searchName));
			if (compareResult == 0) {
				start = mid;
				break;
			}
			if (compareResult < 0) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		// verify result.
		if (start < 0 || start >= sortedArray.length) {
			return -1;
		}
		String name = sortedArray[start].getName();
		if (name.length() > searchNameLength) {
			name = name.substring(0, searchNameLength);
		}
		if (caseSensitive) {
			if (!name.equals(searchName)) {
				return -1;
			}
		} else if (!name.equalsIgnoreCase(searchName)) {
			return -1;
		}
		// find the first matching name.
		for (start -= 1; start >= 0; start--) {
			name = sortedArray[start].getName();
			if (name.length() > searchNameLength) {
				name = name.substring(0, searchNameLength);
			}
			if (caseSensitive) {
				if (!name.equals(searchName)) {
					break;
				}
			} else if (!name.equalsIgnoreCase(searchName)) {
				break;
			}
		}
		return start + 1;
	}

	public static CodeData[] removeRepeatedNames(CodeData[] sortedArray) {
		if (sortedArray == null || sortedArray.length < 2) {
			return sortedArray;
		}
		ArrayList<CodeData> newCodeDataList = new ArrayList<CodeData>();
		String baseName = ""; //$NON-NLS-1$
		for (int i = 0; i < sortedArray.length; i++) {
			String curName = sortedArray[i].getName();
			if (!baseName.equals(curName)) {
				newCodeDataList.add(sortedArray[i]);
			}
			baseName = curName;
		}

		CodeData[] rv = new CodeData[newCodeDataList.size()];
		newCodeDataList.toArray(rv);
		return rv;

	}

	public static CodeData[] mergeAndRemoveDuplicated(CodeData[] sortedArray1, CodeData[] sortedArray2) {
		if (sortedArray1 == null) {
			return sortedArray2;
		}
		if (sortedArray2 == null) {
			return sortedArray1;
		}
		if (sortedArray1.length == 0) {
			return sortedArray2;
		}
		if (sortedArray2.length == 0) {
			return sortedArray1;
		}
		List result = new ArrayList(sortedArray1.length + sortedArray2.length);
		int pointer1 = 0;
		int pointer2 = 0;
		while (pointer1 != sortedArray1.length || pointer2 != sortedArray2.length) {
			if (pointer1 == sortedArray1.length) {
				result.add(sortedArray2[pointer2++]);
				continue;
			}
			if (pointer2 == sortedArray2.length) {
				result.add(sortedArray1[pointer1++]);
				continue;
			}
			int compareTo = sortedArray1[pointer1].compareTo(sortedArray2[pointer2]);
			if (compareTo == 0) {
				result.add(sortedArray1[pointer1++]);
				pointer2++;
				continue;
			}
			if (compareTo < 0) {
				result.add(sortedArray1[pointer1++]);
			} else {
				result.add(sortedArray2[pointer2++]);
			}
		}

		CodeData[] rv = new CodeData[result.size()];
		result.toArray(rv);
		return rv;
	}

	public static CodeData[] merge(CodeData[] sortedArray1, CodeData[] sortedArray2) {
		if (sortedArray1 == null) {
			return sortedArray2;
		}
		if (sortedArray2 == null) {
			return sortedArray1;
		}
		if (sortedArray1.length == 0) {
			return sortedArray2;
		}
		if (sortedArray2.length == 0) {
			return sortedArray1;
		}
		int size = sortedArray1.length + sortedArray2.length;
		CodeData[] rv = new CodeData[size];

		int pointer1 = 0;
		int pointer2 = 0;
		for (int i = 0; i < size; i++) {
			if (pointer1 == sortedArray1.length) {
				System.arraycopy(sortedArray2, pointer2, rv, i, size - i);
				break;
			}
			if (pointer2 == sortedArray2.length) {
				System.arraycopy(sortedArray1, pointer1, rv, i, size - i);
				break;
			}
			if (sortedArray1[pointer1].compareTo(sortedArray2[pointer2]) < 0) {
				rv[i] = sortedArray1[pointer1++];
			} else {
				rv[i] = sortedArray2[pointer2++];
			}
		}
		return rv;
	}

	public static PHPCodeContext createContext(String className, String functionName) {
		if (className == null) {
			className = EMPTY_STRING;
		}
		if (functionName == null) {
			functionName = EMPTY_STRING;
		}
		if (EMPTY_STRING.equals(className) && EMPTY_STRING.equals(functionName)) {
			return EMPTY_CONTEXT;
		}
		return new PHPCodeContextImp(null, className, functionName);
	}

	public static PHPCodeContext createContext(PHPFileData fileData, int offset) {
		return createContext(PHPFileDataUtilities.getCodeData(fileData, offset));
	}

	public static PHPCodeContext createContext(String fileName, String className, String functionName) {
		return new PHPCodeContextImp(fileName, className, functionName);
	}

	public static PHPCodeContext createContext(CodeData currCodeData) {
		if (currCodeData == null) {
			return EMPTY_CONTEXT;
		}
		String fileName = currCodeData.getUserData().getFileName();
		String className = EMPTY_STRING;
		String functionName;
		if (currCodeData instanceof PHPFunctionData) {
			PHPFunctionData functionCodeData = (PHPFunctionData) currCodeData;
			functionName = functionCodeData.getName();

			PHPCodeData container = functionCodeData.getContainer();
			if (container != null && container instanceof PHPClassData) {
				className = container.getName();
			}
		} else {
			// it must be PHPClassData .
			functionName = currCodeData.getName();
		}
		return new PHPCodeContextImp(fileName, className, functionName);
	}

	public static CodeData[] getFilteredCodeData(CodeData[] data, CodeDataFilter codeDataFilter) {
		if (data == null || data.length == 0) {
			return data;
		}
		List listResult = new ArrayList();
		for (int i = 0; i < data.length; i++) {
			if (codeDataFilter.accept(data[i])) {
				listResult.add(data[i]);
			}
		}
		CodeData[] result = new CodeData[listResult.size()];
		listResult.toArray(result);
		return result;
	}

	public static CodeData[] removeFilteredCodeData(CodeData[] data, CodeDataFilter codeDataFilter) {
		if (data == null || data.length == 0) {
			return data;
		}
		List listResult = new ArrayList();
		for (int i = 0; i < data.length; i++) {
			if (!codeDataFilter.accept(data[i])) {
				listResult.add(data[i]);
			}
		}
		CodeData[] result = new CodeData[listResult.size()];
		listResult.toArray(result);
		return result;
	}

	public static final PHPCodeContext EMPTY_CONTEXT = new PHPCodeContextImp(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);

	public static File[] getFileSStartingWith(File[] sortedArray, String startsWith, boolean caseSensitive) {
		if (sortedArray == null) {
			sortedArray = new File[0];
		}
		if (startsWith == null || startsWith.equals(EMPTY_STRING) || sortedArray.length == 0) {
			return sortedArray;
		}

		int start = getFirstMatch(sortedArray, startsWith, caseSensitive);
		if (start < 0) {
			return new File[0];
		}

		int end = sortedArray.length - 1;
		int length = startsWith.length();
		for (int i = start; i < sortedArray.length; i++) {
			String name = sortedArray[i].getName();
			name = (name.length() > length) ? name.substring(0, length) : name;
			if (caseSensitive) {
				if (name.compareTo(startsWith) > 0) {
					end = i - 1;
					break;
				}
			} else if (name.compareToIgnoreCase(startsWith) > 0) {
				end = i - 1;
				break;
			}
		}
		int arrayLength = (end < start) ? 0 : end - start + 1;
		File[] rv = new File[arrayLength];
		System.arraycopy(sortedArray, start, rv, 0, rv.length);
		return rv;
	}

	private static class PHPCodeContextImp implements PHPCodeContext, Serializable {

		private final String className;
		private final String functionName;
		private int hash;

		PHPCodeContextImp(String fileName, String className, String functionName) {
			this.className = className;
			this.functionName = functionName;
		}

		public final String getContainerClassName() {
			return className;
		}

		public final String getContainerFunctionName() {
			return functionName;
		}

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof PHPCodeContextImp)) {
				return false;
			}
			PHPCodeContextImp other = (PHPCodeContextImp) obj;
			return className.equals(other.className) && functionName.equals(other.functionName);
		}

		public int hashCode() {
			int h = hash;
			if (h == 0) {
				h = functionName.hashCode();
				h = 31 * h + className.hashCode();
				h = 31 * h + functionName.hashCode();
				hash = h;
			}
			return h;
		}

	}

	private static class StaticVariablesFilter implements CodeDataFilter {

		boolean acceptStatic;

		StaticVariablesFilter(boolean acceptStatic) {
			this.acceptStatic = acceptStatic;
		}

		public boolean accept(CodeData codeData) {
			if (codeData instanceof PHPVariableData) {
				if (PHPModifier.isStatic(((PHPClassVarData) codeData).getModifiers())) {
					return acceptStatic;
				}
			}
			return !acceptStatic;
		}

	}

	private static class StaticFunctionsFilter implements CodeDataFilter {

		boolean acceptStatic;

		StaticFunctionsFilter(boolean acceptStatic) {
			this.acceptStatic = acceptStatic;
		}

		public boolean accept(CodeData codeData) {
			if (codeData instanceof PHPFunctionData) {
				if (PHPModifier.isStatic(((PHPFunctionData) codeData).getModifiers())) {
					return acceptStatic;
				}
			}
			return !acceptStatic;
		}

	}

	private static abstract class AccessLevelFilter implements CodeDataFilter {

		public boolean accept(CodeData codeData) {
			if (codeData instanceof PHPClassVarData) {
				return verify(((PHPClassVarData) codeData).getModifiers());
			}
			if (codeData instanceof PHPFunctionData) {
				return verify(((PHPFunctionData) codeData).getModifiers());
			}
			return true;
		}

		public abstract boolean verify(int modifier);
	}

	private static final class InternalPhpCodeData implements CodeDataFilter {

		public boolean accept(CodeData codeData) {
			if (codeData == null || !(codeData instanceof PHPCodeData))
				return false;
			// safe cast
			PHPCodeData phpData = (PHPCodeData) codeData;
			PHPDocBlock docBlock = phpData.getDocBlock();
			return docBlock != null && docBlock.hasTagOf(PHPDocTag.INTERNAL);
		}
	}

	/**
	 * filters magic functions (constructors are considered as magic functions) 
	 * @author guy.g
	 *
	 */
	private static final class MagicFunctionFilter implements CodeDataFilter {

		private static String[] magicFunction;
		boolean acceptMagicFunction;

		/**
		 * @param acceptMagicFunction the return value that should be returned if the function is a magic function.  
		 */
		MagicFunctionFilter(boolean acceptMagicFunction) {
			this.acceptMagicFunction = acceptMagicFunction;
			// use PHPCodeDataFactory to obtain a list of magic functions
			// Assume PHP5
			CodeData[] magics = PHPCodeDataFactory.createMagicMethods(PHPCodeDataFactory.createPHPClassData("dummyClass", Modifier.PUBLIC, null, PHPCodeDataFactory.createUserData("", 0, 0, 0, 0), null, PHPCodeDataFactory.EMPTY_INTERFACES_DATA_ARRAY, PHPCodeDataFactory.EMPTY_CLASS_VAR_DATA_ARRAY,
				PHPCodeDataFactory.EMPTY_CLASS_CONST_DATA_ARRAY, PHPCodeDataFactory.EMPTY_FUNCTIONS_DATA_ARRAY), true);
			MagicFunctionFilter.magicFunction = new String[magics.length + 2];
			// add "__construct" & "__destruct"
			MagicFunctionFilter.magicFunction[0] = "__construct";
			MagicFunctionFilter.magicFunction[1] = "__destruct";
			// add the rest
			for (int i = 2; i < MagicFunctionFilter.magicFunction.length; i++) {
				MagicFunctionFilter.magicFunction[i] = magics[i - 2].getName();
			}
		}

		public boolean accept(CodeData codeData) {
			if (codeData instanceof PHPFunctionData) {
				PHPFunctionData function = (PHPFunctionData) codeData;
				String functionName = function.getName();
				if (isMagicFunction(functionName)) {
					return acceptMagicFunction;
				}

				//if the function's name is the same as the class's name then it is a constructor. (PHP4 support)
				PHPCodeData container = function.getContainer();
				if (container instanceof PHPClassData) {
					PHPClassData classData = (PHPClassData) container;
					if (classData.getName().equals(functionName)) {
						return acceptMagicFunction;
					}
				}

			}
			return !acceptMagicFunction;
		}

		/**
		 * checking if the function name is one of the known magic functions  
		 * @return <code>true</code> if the function is a magic function
		 */
		private boolean isMagicFunction(String functionName) {
			for (int i = 0; i < magicFunction.length; i++) {
				String magicFunctionName = magicFunction[i];
				if (magicFunctionName.equals(functionName)) {
					return true;
				}
			}
			return false;
		}
	}
}