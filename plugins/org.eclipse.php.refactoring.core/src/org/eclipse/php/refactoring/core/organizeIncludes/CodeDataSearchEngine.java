/*******************************************************************************
 * Copyright (c) 2007, 2015, 2017 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.util.collections.BucketMap;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.core.util.text.TextSequenceUtilities;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.RefactoringPlugin;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;

/**
 * @author seva
 */
public class CodeDataSearchEngine {
	public static final int ELEMENT_CALLBACK = 1 << 8;

	public static final int ELEMENT_CLASS_CALLBACK = 1 << 9;

	public static final int ELEMENT_CLASS_FUNCTION_PARAMETER = 1 << 3;

	public static final int ELEMENT_CLASS_INSTANCE = 1 << 6;

	public static final int ELEMENT_CLASS_INTERFACE = 1 << 1;

	public static final int ELEMENT_CLASS_MEMBER = 1 << 5;

	public static final int ELEMENT_CLASS_METHOD_PARAMETER = 1 << 2;

	public static final int ELEMENT_CLASS_NEW = 1 << 4;

	public static final int ELEMENT_CLASS_SUPER = 1 << 0;

	public static final int ELEMENT_FUNCTION_CALL = 1 << 7;

	public static final int ELEMENT_FUNCTION_CALLBACK = 1 << 10;

	public static final int ELEMENT_CONSTANT_READ = 1 << 11;

	public static final String REGEX_ELEMENT_NAME = "[a-zA-Z_][\\w]*"; //$NON-NLS-1$

	public static final Pattern PATTERN_CLASS_INSTANCE = Pattern
			.compile(MessageFormat.format("instanceof[ \\t\\n\\r]+(\\$?{0})", new Object[] { REGEX_ELEMENT_NAME })); //$NON-NLS-1$

	public static final Pattern PATTERN_CLASS_MEMBER = Pattern
			.compile(MessageFormat.format("(\\$?{0})[ \\t\\n\\r]*::", new Object[] { REGEX_ELEMENT_NAME })); //$NON-NLS-1$

	public static final Pattern PATTERN_CONSTANT_READ = Pattern.compile(MessageFormat.format(
			"(((new|function|instanceof|class|interface)[ \\t\\n\\r]+|\\$)?{0})(?![ \\t\\n\\r]*\\()", //$NON-NLS-1$
			new Object[] { REGEX_ELEMENT_NAME }));

	public static final String REGEX_ELEMENT_CALLBACK_DOUBLE = MessageFormat.format("\"({0})\"", //$NON-NLS-1$
			new Object[] { REGEX_ELEMENT_NAME });

	public static final String REGEX_ELEMENT_CALLBACK_SINGLE = MessageFormat.format("''({0})''", //$NON-NLS-1$
			new Object[] { REGEX_ELEMENT_NAME });

	public static final Pattern PATTERN_ELEMENT_CALLBACK_DOUBLE = Pattern.compile(REGEX_ELEMENT_CALLBACK_DOUBLE);

	public static final Pattern PATTERN_ELEMENT_CALLBACK_SINGLE = Pattern.compile(REGEX_ELEMENT_CALLBACK_SINGLE);

	public static final Pattern PATTERN_FUNCTION_CALL = Pattern
			.compile(MessageFormat.format("(((new|function)[ \\t\\n\\r]+|\\$)?{0})[ \\t\\n\\r]*\\(", //$NON-NLS-1$
					new Object[] { REGEX_ELEMENT_NAME }));

	private static final Pattern PATTERN_CLASS_NEW = Pattern
			.compile(MessageFormat.format("new[ \\t\\n\\r]+(\\$?{0})", new Object[] { REGEX_ELEMENT_NAME })); //$NON-NLS-1$

	// public static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// searchCallbacks(IStructuredModel model, IProgressMonitor monitor) {
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.0"),
	// 2); //$NON-NLS-1$
	// BucketMap<String, CodeDataMatch> searchResults = new BucketMap<String,
	// CodeDataMatch>(new LinkedHashSet<CodeDataMatch>(1));
	// collectCallbacks(model, searchResults, new SubProgressMonitor(monitor,
	// 1));
	// return validateCallbacks(model, searchResults, new
	// SubProgressMonitor(monitor, 1));
	// }
	//
	// public static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// searchClasses(IStructuredModel model, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> searchResults = new BucketMap<String,
	// CodeDataMatch>(new LinkedHashSet<CodeDataMatch>(1));
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.1"),
	// 2); //$NON-NLS-1$
	// collectClasses(model, searchResults, new SubProgressMonitor(monitor, 1));
	// return validateClasses(model, searchResults, new
	// SubProgressMonitor(monitor, 1));
	// }
	//
	// public static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// searchConstants(IStructuredModel model, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> searchResults = new BucketMap<String,
	// CodeDataMatch>(new LinkedHashSet<CodeDataMatch>(1));
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.2"),
	// 2); //$NON-NLS-1$
	// collectConstants(model, searchResults, new SubProgressMonitor(monitor,
	// 1));
	// return validateConstants(model, searchResults, new
	// SubProgressMonitor(monitor, 1));
	// }
	//
	// public static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// searchFunctions(IStructuredModel model, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> searchResults = new BucketMap<String,
	// CodeDataMatch>(new LinkedHashSet<CodeDataMatch>(1));
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.3"),
	// 2); //$NON-NLS-1$
	// collectFunctions(model, searchResults, new SubProgressMonitor(monitor,
	// 1));
	// return validateFunctions(model, searchResults, new
	// SubProgressMonitor(monitor, 1));
	// }
	//
	// public static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// searchInterfaces(IStructuredModel model, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> searchResults = new BucketMap<String,
	// CodeDataMatch>(new LinkedHashSet<CodeDataMatch>(1));
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.4"),
	// 2); //$NON-NLS-1$
	// collectInterfaces(model, searchResults, new SubProgressMonitor(monitor,
	// 1));
	// return validateInterfaces(model, searchResults, new
	// SubProgressMonitor(monitor, 1));
	// }
	//
	// private static boolean classIsInterface(PHPClassData classData) {
	// return PHPModifier.isInterface(classData.getModifiers());
	// }

	private static void collectCallbacks(IStructuredModel model, BucketMap<String, CodeDataMatch> searchResults,
			IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.5"), 4); //$NON-NLS-1$
		if (monitor.isCanceled())
			return;
		Set<String> set = searchElementNames(model, PATTERN_ELEMENT_CALLBACK_DOUBLE, 1,
				new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CALLBACK, true, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		set = searchElementNames(model, PATTERN_ELEMENT_CALLBACK_SINGLE, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CALLBACK, true, new SubProgressMonitor(monitor, 1));
	}

	/**
	 * @param model
	 * @param searchResults
	 * @param monitor
	 */
	private static void collectClasses(IStructuredModel model, BucketMap<String, CodeDataMatch> searchResults,
			IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.6"), 2); //$NON-NLS-1$
		if (monitor.isCanceled())
			return;
		// collectClassesFromModel(model.getFileData(), searchResults, new
		// SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		collectClassesFromText(model, searchResults, new SubProgressMonitor(monitor, 1));
	}

	/**
	 * @param model
	 * @param searchResults
	 */
	// private static void collectClassesFromFunctionParameters(PHPFileData
	// fileData, BucketMap<String, CodeDataMatch> searchResults,
	// IProgressMonitor monitor) {
	// PHPFunctionData[] functionDatas = fileData.getFunctions();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.7"),
	// functionDatas.length); //$NON-NLS-1$
	// for (PHPFunctionData functionData : functionDatas) {
	// PHPFunctionParameter[] parameters = functionData.getParameters();
	// if (parameters == null)
	// continue;
	// for (PHPFunctionParameter parameter : parameters) {
	// String parameterClassName = parameter.getClassType();
	// if (parameterClassName == null)
	// continue;
	// parameterClassName = parameterClassName.trim();
	// // may include extra spaces
	// searchResults.add(parameterClassName.toLowerCase(), new
	// CodeDataMatch(parameterClassName, ELEMENT_CLASS_FUNCTION_PARAMETER));
	// }
	// if (monitor.isCanceled())
	// return;
	// monitor.worked(1);
	// }
	// }
	//
	// private static void collectClassesFromModel(PHPFileData fileData,
	// BucketMap<String, CodeDataMatch> searchResults, IProgressMonitor monitor)
	// {
	// PHPClassData[] classDatas = fileData.getClasses();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.8"),
	// classDatas.length + 1); //$NON-NLS-1$
	// for (PHPClassData classData : classDatas) {
	// if (!classIsInterface(classData))
	// collectExtendedClass(classData, searchResults);
	// collectMethodParameterClasses(classData, searchResults);
	// if (monitor.isCanceled())
	// return;
	// monitor.worked(1);
	// }
	// if (monitor.isCanceled())
	// return;
	// collectClassesFromFunctionParameters(fileData, searchResults, new
	// SubProgressMonitor(monitor, 1));
	// }

	/**
	 * @param model
	 * @param searchResults
	 * @param monitor
	 */
	private static void collectClassesFromText(IStructuredModel model, BucketMap<String, CodeDataMatch> searchResults,
			IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.9"), 6); //$NON-NLS-1$
		if (monitor.isCanceled())
			return;
		Set<String> set = searchElementNames(model, PATTERN_CLASS_NEW, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CLASS_NEW, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		set = searchElementNames(model, PATTERN_CLASS_MEMBER, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CLASS_MEMBER, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		set = searchElementNames(model, PATTERN_CLASS_INSTANCE, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CLASS_INSTANCE, new SubProgressMonitor(monitor, 1));
	}

	/**
	 * @param model
	 * @param searchResults
	 * @param monitor
	 */
	private static void collectConstants(IStructuredModel model, BucketMap<String, CodeDataMatch> searchResults,
			IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.10"), 2); //$NON-NLS-1$
		if (monitor.isCanceled())
			return;
		Set<String> set = searchElementNames(model, PATTERN_CONSTANT_READ, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CONSTANT_READ, true, new SubProgressMonitor(monitor, 1));
	}

	/**
	 * @param model
	 * @param classData
	 * @param searchResults
	 */
	// private static void collectExtendedClass(PHPClassData classData,
	// BucketMap<String, CodeDataMatch> searchResults) {
	// PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
	// if (superClassNameData == null)
	// return;
	// String superClassName = superClassNameData.getName();
	// if (superClassName == null)
	// return;
	// searchResults.add(superClassName.toLowerCase(), new
	// CodeDataMatch(superClassName, ELEMENT_CLASS_SUPER));
	// }

	/**
	 * @param model
	 * @param searchResults
	 */
	private static void collectFunctions(IStructuredModel model, BucketMap<String, CodeDataMatch> searchResults,
			IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.11"), 2); //$NON-NLS-1$
		if (monitor.isCanceled())
			return;
		Set<String> set = searchElementNames(model, PATTERN_FUNCTION_CALL, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_FUNCTION_CALL, new SubProgressMonitor(monitor, 1));
	}

	private static void collectInterfaces(IStructuredModel model, BucketMap<String, CodeDataMatch> searchResults,
			IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.12"), 2); //$NON-NLS-1$
		if (monitor.isCanceled())
			return;
		// collectInterfacesFromModel(model.getFileData(), searchResults, new
		// SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		collectInterfacesFromText(model, searchResults, new SubProgressMonitor(monitor, 1));
	}

	/**
	 * @param model
	 * @param classData
	 * @param searchResults
	 */
	// private static void collectInterfacesFromImplementor(PHPClassData
	// classData, BucketMap<String, CodeDataMatch> searchResults) {
	// PHPInterfaceNameData[] interfaceNameDatas =
	// classData.getInterfacesNamesData();
	// if (interfaceNameDatas == null)
	// return;
	// for (PHPInterfaceNameData interfaceNameData : interfaceNameDatas) {
	// String interfaceName = interfaceNameData.getName();
	// searchResults.add(interfaceName, new CodeDataMatch(interfaceName,
	// ELEMENT_CLASS_INTERFACE));
	// }
	// }
	//
	// private static void collectInterfacesFromModel(PHPFileData fileData,
	// BucketMap<String, CodeDataMatch> searchResults, IProgressMonitor monitor)
	// {
	// PHPClassData[] classDatas = fileData.getClasses();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.13"),
	// classDatas.length + 1); //$NON-NLS-1$
	// for (int i = 0; i < classDatas.length; ++i) {
	// PHPClassData classData = classDatas[i];
	// if (classIsInterface(classData))
	// collectExtendedClass(classData, searchResults);
	// collectInterfacesFromImplementor(classData, searchResults);
	// collectMethodParameterClasses(classData, searchResults);
	// if (monitor.isCanceled())
	// return;
	// monitor.worked(1);
	// }
	// if (monitor.isCanceled())
	// return;
	// collectClassesFromFunctionParameters(fileData, searchResults, new
	// SubProgressMonitor(monitor, 1));
	// }

	private static void collectInterfacesFromText(IStructuredModel model,
			BucketMap<String, CodeDataMatch> searchResults, IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.14"), 4); //$NON-NLS-1$
		if (monitor.isCanceled())
			return;
		Set<String> set = searchElementNames(model, PATTERN_CLASS_MEMBER, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CLASS_MEMBER, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		set = searchElementNames(model, PATTERN_CLASS_INSTANCE, 1, new SubProgressMonitor(monitor, 1));
		if (monitor.isCanceled())
			return;
		fillSearchResults(searchResults, set, ELEMENT_CLASS_INSTANCE, new SubProgressMonitor(monitor, 1));
	}

	/**
	 * @param model
	 * @param classData
	 * @param searchResults
	 */
	// private static void collectMethodParameterClasses(PHPClassData classData,
	// BucketMap<String, CodeDataMatch> searchResults) {
	// PHPFunctionData[] functionDatas = classData.getFunctions();
	// for (PHPFunctionData functionData : functionDatas) {
	// PHPFunctionParameter[] parameters = functionData.getParameters();
	// if (parameters == null)
	// continue;
	// for (PHPFunctionParameter element2 : parameters) {
	// String parameterClassName = element2.getClassType();
	// if (parameterClassName == null)
	// continue;
	// parameterClassName = parameterClassName.trim();
	// // parameterClassLength may includes extra spaces!
	// searchResults.add(parameterClassName.toLowerCase(), new
	// CodeDataMatch(parameterClassName, ELEMENT_CLASS_METHOD_PARAMETER));
	// }
	// }
	// }

	private static void fillSearchResults(BucketMap<String, CodeDataMatch> searchResults, Collection<String> collection,
			int elementType, IProgressMonitor monitor) {
		fillSearchResults(searchResults, collection, elementType, false, monitor);
	}

	private static void fillSearchResults(BucketMap<String, CodeDataMatch> searchResults, Collection<String> collection,
			int elementType, boolean caseSensitive, IProgressMonitor monitor) {
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.15"), collection.size()); //$NON-NLS-1$
		for (String elementName : collection) {
			searchResults.add(caseSensitive ? elementName : elementName.toLowerCase(),
					new CodeDataMatch(elementName, elementType));
			if (monitor.isCanceled())
				return;
			monitor.worked(1);
		}
	}

	private static String getPartitionType(IStructuredDocument document, int offset) {
		IStructuredDocumentRegion region = document.getRegionAtCharacterOffset(offset);
		TextSequence statement = PHPTextSequenceUtilities.getStatement(offset, region, false);
		if (statement.length() == 0)
			return null;
		String partitionType = TextSequenceUtilities.getTypeByAbsoluteOffset(statement, offset);
		return partitionType;
	}

	private static boolean isCode(IStructuredDocument document, int offset) {
		String partitionType = getPartitionType(document, offset);
		return PHPPartitionTypes.isPHPRegularState(partitionType);
	}

	private static Set<String> searchElementNames(IStructuredModel model, Pattern pattern, int elementPatternPosition,
			IProgressMonitor monitor) {
		IStructuredDocument document = model.getStructuredDocument();
		String documentContents = null;
		try {
			documentContents = document.get(0, document.getLength());
		} catch (BadLocationException e) {
			RefactoringPlugin.logException(e);
		}
		Set<String> elements = new HashSet<String>();
		if (documentContents == null)
			return elements;
		monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.16"), 2); //$NON-NLS-1$
		Matcher matcher = pattern.matcher(documentContents);
		while (matcher.find()) {
			int end = matcher.end();
			if (!isCode(document, end))
				continue;
			String elementName = matcher.group(elementPatternPosition);
			elements.add(elementName);
		}
		return elements;
	}

	// private static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// validateCallbacks(IStructuredModel model, BucketMap<String,
	// CodeDataMatch>
	// searchResults, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> validSearchResults = new
	// BucketMap<String, CodeDataMatch>();
	// BucketMap<String, CodeData> callbackDataResults = new BucketMap<String,
	// CodeData>();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.18"),
	// searchResults.getKeys().size()); //$NON-NLS-1$
	// for (String elementName : searchResults.getKeys()) {
	// CodeData codeDatas[] = model.getProjectModel().getClass(elementName);
	// if (codeDatas.length > 0) {
	// validSearchResults.add(elementName, new CodeDataMatch(elementName,
	// ELEMENT_CLASS_CALLBACK));
	// callbackDataResults.addAll(elementName, Arrays.asList(codeDatas));
	// }
	// codeDatas = model.getProjectModel().getFunction(elementName);
	// if (codeDatas.length > 0) {
	// validSearchResults.add(elementName, new CodeDataMatch(elementName,
	// ELEMENT_FUNCTION_CALLBACK));
	// callbackDataResults.addAll(elementName, Arrays.asList(codeDatas));
	// }
	// if (monitor.isCanceled())
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, callbackDataResults);
	// monitor.worked(1);
	// }
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, callbackDataResults);
	// }
	//
	// private static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// validateClasses(IStructuredModel model, BucketMap<String, CodeDataMatch>
	// searchResults, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> validSearchResults = new
	// BucketMap<String, CodeDataMatch>();
	// BucketMap<String, CodeData> classDataResults = new BucketMap<String,
	// CodeData>();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.19"),
	// searchResults.getKeys().size()); //$NON-NLS-1$
	// for (String className : searchResults.getKeys()) {
	// CodeData classDatas[] = model.getProjectModel().getClass(className);
	// for (int j = 0; j < classDatas.length; j++)
	// if (!classIsInterface((PHPClassData) classDatas[j]))
	// classDataResults.add(className, classDatas[j]);
	// if (classDataResults.get(className).size() == 0)
	// continue;
	// validSearchResults.addAll(className, searchResults.get(className));
	// if (monitor.isCanceled())
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, classDataResults);
	// monitor.worked(1);
	// }
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, classDataResults);
	// }
	//
	// private static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// validateConstants(IStructuredModel model, BucketMap<String,
	// CodeDataMatch>
	// searchResults, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> validSearchResults = new
	// BucketMap<String, CodeDataMatch>();
	// BucketMap<String, CodeData> constantDataResults = new BucketMap<String,
	// CodeData>();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.20"),
	// searchResults.getKeys().size()); //$NON-NLS-1$
	// for (String constantName : searchResults.getKeys()) {
	// CodeData constantDatas[] =
	// model.getProjectModel().getConstant(constantName);
	// for (CodeData constantData : constantDatas) {
	// if (constantData.getName().equals(constantName)) {
	// validSearchResults.addAll(constantName, searchResults.get(constantName));
	// constantDataResults.add(constantName, constantData);
	// }
	// }
	// if (monitor.isCanceled())
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, constantDataResults);
	// monitor.worked(1);
	// }
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, constantDataResults);
	// }
	//
	// private static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// validateFunctions(IStructuredModel model, BucketMap<String,
	// CodeDataMatch>
	// searchResults, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> validSearchResults = new
	// BucketMap<String, CodeDataMatch>();
	// BucketMap<String, CodeData> functionDataResults = new BucketMap<String,
	// CodeData>();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.21"),
	// searchResults.getKeys().size()); //$NON-NLS-1$
	// for (String functionName : searchResults.getKeys()) {
	// CodeData functionDatas[] =
	// model.getProjectModel().getFunction(functionName);
	// if (functionDatas.length > 0) {
	// validSearchResults.addAll(functionName, searchResults.get(functionName));
	// functionDataResults.addAll(functionName, Arrays.asList(functionDatas));
	// }
	// if (monitor.isCanceled())
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, functionDataResults);
	// monitor.worked(1);
	// }
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, functionDataResults);
	// }
	//
	// private static DoubleBucketMap<String, CodeDataMatch, CodeData>
	// validateInterfaces(IStructuredModel model, BucketMap<String,
	// CodeDataMatch>
	// searchResults, IProgressMonitor monitor) {
	// BucketMap<String, CodeDataMatch> validSearchResults = new
	// BucketMap<String, CodeDataMatch>();
	// BucketMap<String, CodeData> interfaceCodeDatas = new BucketMap<String,
	// CodeData>();
	// monitor.beginTask(PhpRefactoringCoreMessages.getString("CodeDataSearchEngine.22"),
	// searchResults.getKeys().size()); //$NON-NLS-1$
	// for (String interfaceName : searchResults.getKeys()) {
	// CodeData interfaceDatas[] =
	// model.getProjectModel().getClass(interfaceName);
	// for (CodeData element : interfaceDatas)
	// if (classIsInterface((PHPClassData) element))
	// interfaceCodeDatas.add(interfaceName, element);
	// if (interfaceCodeDatas.get(interfaceName).size() == 0)
	// continue;
	// validSearchResults.addAll(interfaceName,
	// searchResults.get(interfaceName));
	// if (monitor.isCanceled())
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, interfaceCodeDatas);
	// monitor.worked(1);
	// }
	// return new DoubleBucketMap<String, CodeDataMatch,
	// CodeData>(validSearchResults, interfaceCodeDatas);
	// }

	public static boolean elementIsOptional(int elementType) {
		// TODO think about it... Still no usage.
		return false;
	}
}
