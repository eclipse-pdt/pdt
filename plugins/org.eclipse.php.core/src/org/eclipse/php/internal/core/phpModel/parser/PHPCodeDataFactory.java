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
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.util.Visitor;

public class PHPCodeDataFactory {

	public static final CodeData[] EMPTY_CODE_DATA_ARRAY = new CodeData[0];
	public static final PHPClassData[] EMPTY_CLASS_DATA_ARRAY = new PHPClassData[0];
	public static final PHPFunctionData[] EMPTY_FUNCTIONS_DATA_ARRAY = new PHPFunctionData[0];
	public static final PHPIncludeFileData[] EMPTY_INCLUDE_DATA_ARRAY = new PHPIncludeFileData[0];
	public static final PHPConstantData[] EMPTY_CONSTANT_DATA_ARRAY = new PHPConstantData[0];
	public static final IPHPMarker[] EMPTY_MARKERS_DATA_ARRAY = new IPHPMarker[0];
	public static final PHPClassVarData[] EMPTY_CLASS_VAR_DATA_ARRAY = new PHPClassVarData[0];
	public static final PHPClassConstData[] EMPTY_CLASS_CONST_DATA_ARRAY = new PHPClassConstData[0];
	public static final PHPClassData.PHPInterfaceNameData[] EMPTY_INTERFACES_DATA_ARRAY = new PHPClassData.PHPInterfaceNameData[0];
	public static final PHPBlock[] EMPTY_PHP_BLOCK_ARRAY = new PHPBlock[0];
	public static final PHPFunctionData.PHPFunctionParameter[] EMPTY_FUNCTION_PARAMETER_DATA_ARRAY = new PHPFunctionData.PHPFunctionParameter[0];
	public static final PHPDocTag[] EMPTY_PHP_DOC_TAG = new PHPDocTag[0];

	/**
	 * Returns new PHPFunctionData.
	 */
	public static PHPFunctionData createPHPFuctionData(String name, int modifier, PHPDocBlock docBlock, UserData userData, PHPFunctionData.PHPFunctionParameter[] parameter, String returnType) {
		return new PHPFunctionDataImp(name, modifier, docBlock, userData, parameter, returnType);
	}

	public static PHPFunctionData.PHPFunctionParameter createPHPFunctionParameter(String name, UserData userData, boolean isReference, boolean isConst, String classType, String defaultValue) {
		return new PHPFunctionParameterImp(name, userData, isReference, isConst, classType, defaultValue);
	}

	/**
	 * Returns new PHPClassVarData.
	 */
	public static PHPClassVarData createPHPClassVarData(String name, int modifier, String classType, PHPDocBlock docBlock, UserData userData) {
		return new PHPClassVarDataImp(name, modifier, classType, docBlock, userData);
	}

	/**
	 * Returns new PHPClassVarData.
	 */
	public static PHPClassConstData createPHPClassConstData(String name, PHPDocBlock docBlock, UserData userData) {
		return new PHPClassConstDataImp(name, docBlock, userData);
	}

	/**
	 * Returns new PHPClassVarData.
	 * @param includingType TODO
	 */
	public static PHPIncludeFileData createPHPIncludeFileData(String includingType, String name, PHPDocBlock docBlock, UserData userData) {
		return new PHPIncludeFileDataImp(includingType, name, docBlock, userData);
	}

	/**
	 * Returns new PHPClassData.
	 */
	public static PHPClassData createPHPClassData(String name, int modifier, PHPDocBlock docBlock, UserData userData, PHPClassData.PHPSuperClassNameData superClass, PHPClassData.PHPInterfaceNameData[] interfaces, PHPClassVarData[] vars, PHPClassConstData[] consts, PHPFunctionData[] functions) {
		return new PHPClassDataImp(name, modifier, docBlock, userData, superClass, interfaces, vars, consts, functions);
	}

	/**
	 * Returns new PHPClassData.
	 */
	public static PHPKeywordData createPHPKeywordData(String name, String suffix, int suffixOffset) {
		return new PHPKeywordDataImp(name, suffix, suffixOffset);
	}

	/**
	 * Returns new PHPClassData.PHPSuperClassNameData.
	 */
	public static PHPClassData.PHPSuperClassNameData createPHPSuperClassNameData(String name, UserData userData) {
		return new PHPSuperClassNameDataImp(name, userData);
	}

	/**
	 * Returns new PHPClassData.PHPInterfaceNameData.
	 */
	public static PHPClassData.PHPInterfaceNameData createPHPInterfaceNameData(String name, UserData userData) {
		return new PHPInterfaceNameDataImp(name, userData);
	}

	/**
	 * Returns new PHPClassData.
	 */
	public static PHPConstantData createPHPConstantData(String name, String value, UserData userData, PHPDocBlock docBlock) {
		return new PHPConstantDataImp(name, value, userData, docBlock);
	}

	/**
	 * Returns new PHPVariableDataImp.
	 */
	public static PHPVariableData createPHPVariableData(String name, PHPDocBlock docBlock, UserData userData) {
		return new PHPVariableDataImp(name, docBlock, userData);
	}

	public static PHPVariableData createPHPVariableData(String name, boolean isGlobal, PHPDocBlock docBlock, UserData userData) {
		return new PHPVariableDataImp(name, isGlobal, docBlock, userData);
	}

	/**
	 * Returns new PHPFileDataImp.
	 */
	public static PHPFileData createPHPFileData(String fileName, UserData userData, PHPClassData[] classes, PHPFunctionData[] functions, PHPVariablesTypeManager variablesTypeManager, PHPIncludeFileData[] includeFiles, PHPConstantData[] constans, IPHPMarker[] markers, PHPBlock[] phpBlocks,
			PHPDocBlock docBlock, long lastModified) {
		return new PHPFileDataImp(fileName, userData, classes, functions, variablesTypeManager, includeFiles, constans, markers, phpBlocks, docBlock, lastModified);
	}

	/**
	 * Returns new UserData.
	 */
	public static UserData createUserData(String fileName, int startPosition, int endPosition, int stopPosition, int stopLine) {
		return new UserDataImp(fileName, startPosition, endPosition, stopPosition, stopLine);
	}

	/**
	 * Creates magic methods for the specified class
	 */
	public static CodeData[] createMagicMethods(PHPClassData classData, boolean isPHP5) {
		List methods = new LinkedList();

		methods.add(PHPCodeDataFactory.createPHPFuctionData("__get", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is called each time variable is referenced from the object", null, new PHPDocTag[] { new BasicPHPDocTag(PHPDocTag.PARAM, "string $name variable name"),
			new BasicPHPDocTag(PHPDocTag.RETURN, "variable value") }, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[] { new PHPFunctionParameterImp("name", null, false, false, "string", null) }, "mixed"));

		methods.add(PHPCodeDataFactory.createPHPFuctionData("__set", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is called each time variable is set in the object", null, new PHPDocTag[] { new BasicPHPDocTag(PHPDocTag.PARAM, "string $name variable name"),
			new BasicPHPDocTag(PHPDocTag.PARAM, "mixed $value variable value"), new BasicPHPDocTag(PHPDocTag.RETURN, "void") }, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[] {
			new PHPFunctionParameterImp("name", null, false, false, "string", null), new PHPFunctionParameterImp("value", null, false, false, "string", null), }, "void"));

		methods.add(PHPCodeDataFactory.createPHPFuctionData("__call", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is invoked each time not existing method is called on the object", null, new PHPDocTag[] { new BasicPHPDocTag(PHPDocTag.PARAM, "string $name method name"),
			new BasicPHPDocTag(PHPDocTag.PARAM, "array arguments method arguments"), new BasicPHPDocTag(PHPDocTag.RETURN, "Return value of non-existent method") }, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[] {
			new PHPFunctionParameterImp("name", null, false, false, "string", null), new PHPFunctionParameterImp("arguments", null, false, false, "array", null), }, "mixed"));

		methods.add(PHPCodeDataFactory.createPHPFuctionData("__sleep", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is executed prior to any serialization of the object", null, EMPTY_PHP_DOC_TAG, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(),
			EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null));

		methods.add(PHPCodeDataFactory.createPHPFuctionData("__wakeup", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is executed after the object is deserialized", null, EMPTY_PHP_DOC_TAG, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), EMPTY_FUNCTION_PARAMETER_DATA_ARRAY,
			null));

		if (isPHP5) {
			methods.add(PHPCodeDataFactory.createPHPFuctionData("__isset", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is invoked each time isset() is called on the object variable", null, new PHPDocTag[] { new BasicPHPDocTag(PHPDocTag.PARAM, "string $name variable name"),
				new BasicPHPDocTag(PHPDocTag.RETURN, "true if the object variable is set, otherwise false") }, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[] { new PHPFunctionParameterImp("name", null, false, false, "string", null), }, "boolean"));

			methods.add(PHPCodeDataFactory.createPHPFuctionData("__unset", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is invoked each time unset() is called on the object variable", null, new PHPDocTag[] { new BasicPHPDocTag(PHPDocTag.PARAM, "string $name variable name"),
				new BasicPHPDocTag(PHPDocTag.RETURN, "unsets the object variable") }, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[] { new PHPFunctionParameterImp("name", null, false, false, "string", null), }, "void"));
			
			methods.add(PHPCodeDataFactory.createPHPFuctionData("__toString", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is used for setting a string value for the object that will be used if the object is used as a string.", null, new PHPDocTag[]{new BasicPHPDocTag(PHPDocTag.RETURN, "string representing the object")} ,
				PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[0], "string"));
			
			methods.add(PHPCodeDataFactory.createPHPFuctionData("__set_state", PHPModifier.PUBLIC, new PHPDocBlockImp("This static method is called for classes exported by var_export() since PHP 5.1.0", null, new PHPDocTag[0], PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[0], "void"));
			
			methods.add(PHPCodeDataFactory.createPHPFuctionData("__clone", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is invoked each time clone is called on the object variable", null, new PHPDocTag[0], PHPDocBlock.FUNCTION_DOCBLOCK),
				classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[0], "void"));
			
			methods.add(PHPCodeDataFactory.createPHPFuctionData("__autoload", PHPModifier.PUBLIC, new PHPDocBlockImp("This magic method is invoked in case you are trying to use a class which hasn't been defined yet", null, new PHPDocTag[] { new BasicPHPDocTag(PHPDocTag.PARAM, "string $name class name"),
				new BasicPHPDocTag(PHPDocTag.RETURN, "new class object") }, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), new PHPFunctionData.PHPFunctionParameter[] { new PHPFunctionParameterImp("name", null, false, false, "string", null), }, "mixed"));
		}

		return (CodeData[]) methods.toArray(new CodeData[methods.size()]);
	}

	/**
	 * Creates constructors for the class
	 */
	public static CodeData[] createConstructors(PHPClassData classData, boolean isPHP5) {
		List constructors = new LinkedList();

		constructors.add(PHPCodeDataFactory.createPHPFuctionData(classData.getName(), PHPModifier.PUBLIC, new PHPDocBlockImp("Constructs this object", null, EMPTY_PHP_DOC_TAG, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, classData
			.getName()));

		if (isPHP5) {
			constructors.add(PHPCodeDataFactory.createPHPFuctionData(PHPClassData.CONSTRUCTOR, PHPModifier.PUBLIC, new PHPDocBlockImp("Constructs this object", null, EMPTY_PHP_DOC_TAG, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY,
				classData.getName()));
			constructors.add(PHPCodeDataFactory.createPHPFuctionData(PHPClassData.DESCRUCTOR, PHPModifier.PUBLIC, new PHPDocBlockImp("Destructs this object", null, EMPTY_PHP_DOC_TAG, PHPDocBlock.FUNCTION_DOCBLOCK), classData.getUserData(), PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY,
				"void"));
		}

		return (CodeData[]) constructors.toArray(new CodeData[constructors.size()]);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPIdentifierData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static abstract class PHPCodeDataImp extends AbstractCodeData implements PHPCodeData {

		private PHPCodeData container;
		protected PHPDocBlock docBlock;

		public PHPCodeDataImp(String name, PHPDocBlock docBlock, UserData userData) {
			super(name, docBlock != null ? docBlock.getShortDescription() : "", userData);
			this.docBlock = docBlock;
		}

		public PHPCodeData getContainer() {
			return container;
		}

		public void setContainer(PHPCodeData container) {
			this.container = container;
		}

		public PHPDocBlock getDocBlock() {
			return docBlock;
		}

		public void setDocBlock(PHPDocBlock block) {
			docBlock = block;
		}

		public String getDescription() {
			if (getDocBlock() != null) {
				return getDocBlock().getShortDescription();
			}
			return "";
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPVariableData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPVariableDataImp extends PHPCodeDataImp implements PHPVariableData {

		private boolean isGlobal;

		/**
		 * Construct a new PHPKeywordData.
		 */
		public PHPVariableDataImp(String name, PHPDocBlock docBlock, UserData userData) {
			super(name, docBlock, userData);
			isGlobal = false;
		}

		public PHPVariableDataImp(String name, boolean isGlobal, PHPDocBlock docBlock, UserData userData) {
			super(name, docBlock, userData);
			this.isGlobal = isGlobal;
		}

		public boolean isGlobal() {
			return isGlobal;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPFunctionParameterImp implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPFunctionParameterImp extends PHPVariableDataImp implements PHPFunctionData.PHPFunctionParameter {

		private boolean isReference;
		private boolean isConst;
		private String classType;
		private String defaultValue;

		private PHPFunctionParameterImp(String name, UserData userData, boolean isReference, boolean isConst, String classType, String defaultValue) {
			super(name, null, userData);
			this.isReference = isReference;
			this.isConst = isConst;
			this.classType = classType;
			this.defaultValue = defaultValue;
		}

		public boolean isReference() {
			return isReference;
		}

		public boolean isConst() {
			return isConst;
		}

		public String getClassType() {
			return classType;
		}

		public void setClassType(String classType) {
			this.classType = classType;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPClassFuctionData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	public static class PHPFunctionDataImp extends PHPCodeDataImp implements PHPFunctionData {

		private PHPFunctionData.PHPFunctionParameter[] parameter;
		private String returnType = "unknown";
		private int modifier;

		/**
		 * Construct a new PHPFuctionDataImp.
		 */
		public PHPFunctionDataImp(String name, int modifier, PHPDocBlock docBlock, UserData userData, PHPFunctionData.PHPFunctionParameter[] parameter, String returnType) {
			super(name, docBlock, userData);
			this.modifier = modifier;
			this.parameter = parameter;
			if (returnType != null && returnType.trim().length() != 0) {
				this.returnType = returnType;
			}
		}

		public PHPFunctionData.PHPFunctionParameter[] getParameters() {
			return parameter;
		}

		public void setReturnType(String returnType) {
			this.returnType = returnType;
		}

		/**
		 * Returns the function return type as a string.
		 */
		public String getReturnType() {
			return returnType;
		}

		public int getModifiers() {
			return modifier;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPClassVarData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPClassVarDataImp extends PHPVariableDataImp implements PHPClassVarData {

		private int modifier;
		private String classType;

		private PHPClassVarDataImp(String name, int modifier, String classType, PHPDocBlock docBlock, UserData userData) {
			super(name, docBlock, userData);
			this.modifier = modifier;
			this.classType = classType;
		}

		public int getModifiers() {
			return modifier;
		}

		public String getClassType() {
			return classType;
		}

		public void setClassType(String classType) {
			this.classType = classType;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPClassConstDataImp implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPClassConstDataImp extends PHPCodeDataImp implements PHPClassConstData {

		/**
		 * Construct a new PHPClassConstDataImp.
		 */
		public PHPClassConstDataImp(String name, PHPDocBlock docBlock, UserData userData) {
			super(name, docBlock, userData);
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPClassData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	public static class PHPClassDataImp extends PHPCodeDataImp implements PHPClassData {

		private PHPClassVarData[] vars;
		private PHPClassConstData[] consts;
		private PHPFunctionData[] functions;
		private PHPFunctionData constructor;
		private boolean hasConstructor;
		private int modifier;
		private PHPClassData.PHPSuperClassNameData superClass;
		private PHPClassData.PHPInterfaceNameData[] interfaces;

		public PHPClassDataImp(String name, int modifier, PHPDocBlock docBlock, UserData userData, PHPClassData.PHPSuperClassNameData superClass, PHPClassData.PHPInterfaceNameData[] interfaces, PHPClassVarData[] vars, PHPClassConstData[] consts, PHPFunctionData[] functions) {
			super(name, docBlock, userData);
			this.modifier = modifier;
			this.interfaces = interfaces;
			this.vars = vars;
			this.consts = consts;
			this.superClass = superClass;
			if (superClass != null) {
				this.superClass.setContainer(this);
			}

			for (int i = 0; i < interfaces.length; ++i) {
				this.interfaces[i].setContainer(this);
			}

			setVars(vars);
			setFunctions(functions);
		}

		public PHPClassData.PHPSuperClassNameData getSuperClassData() {
			return superClass;
		}

		/**
		 * return the interfaces names.
		 */
		public PHPClassData.PHPInterfaceNameData[] getInterfacesNamesData() {
			return interfaces;
		}

		public void setVars(PHPClassVarData[] vars) {
			this.vars = vars;
		}

		/**
		 * returns all class vars names.
		 */
		public PHPClassVarData[] getVars() {
			return vars;
		}

		public void setConsts(PHPClassConstData[] consts) {
			this.consts = consts;
		}

		/**
		 * returns all class vars names.
		 */
		public PHPClassConstData[] getConsts() {
			return consts;
		}

		public void setFunctions(PHPFunctionData[] functions) {
			this.functions = functions;

			hasConstructor = false;
			for (int i = 0; i < functions.length; i++) {
				String functionName = functions[i].getName();
				if (functionName.equals(CONSTRUCTOR) || functionName.equalsIgnoreCase(getName())) {
					constructor = functions[i];
					hasConstructor = true;
					break;
				}
			}
			if (!hasConstructor) {
				constructor = createPHPFuctionData(CONSTRUCTOR, PHPModifier.PUBLIC, null, getUserData(), EMPTY_FUNCTION_PARAMETER_DATA_ARRAY, null);
				((PHPFunctionDataImp) constructor).setContainer(this);
			}
		}

		/**
		 * Returns all class functions as FunctionData objects.
		 */
		public PHPFunctionData[] getFunctions() {
			return functions;
		}

		/**
		 * Returns if the class have a constructor.
		 */
		public boolean hasConstructor() {
			return hasConstructor;
		}

		/**
		 * Returns the constructor or if it does not have return a default constructor.
		 */
		public PHPFunctionData getConstructor() {
			return constructor;
		}

		public int getModifiers() {
			return modifier;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPKeywordData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPKeywordDataImp extends PHPCodeDataImp implements PHPKeywordData {
		private String suffix;
		private int suffixOffset;

		/**
		 * Construct a new PHPKeywordData.
		 */
		private PHPKeywordDataImp(String name, String suffix, int suffixOffset) {
			super(name, null, null);
			this.suffix = suffix;
			this.suffixOffset = suffixOffset;
		}

		public String getSuffix() {
			return suffix;
		}

		public int getSuffixOffset() {
			return suffixOffset;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPIncludeFileData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	private static class PHPIncludeFileDataImp extends PHPCodeDataImp implements PHPIncludeFileData {

		private String includingType;

		private PHPIncludeFileDataImp(String includingType, String name, PHPDocBlock docBlock, UserData userData) {
			super(name, docBlock, userData);
			this.includingType = includingType;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

		public String getIncludingType() {
			return includingType;
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPClassData.PHPSuperClassNameData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPSuperClassNameDataImp extends PHPCodeDataImp implements PHPClassData.PHPSuperClassNameData {

		public PHPSuperClassNameDataImp(String name, UserData userData) {
			super(name, null, userData);
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPClassData.PHPInterfaceNameData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPInterfaceNameDataImp extends PHPCodeDataImp implements PHPClassData.PHPInterfaceNameData {

		public PHPInterfaceNameDataImp(String name, UserData userData) {
			super(name, null, userData);
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPConstantData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPConstantDataImp extends PHPCodeDataImp implements PHPConstantData {

		private String value;

		public PHPConstantDataImp(String name, String value, UserData userData, PHPDocBlock docBlock) {
			super(name, docBlock, userData);
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		// fix bug 9124.
		// We can have 2 constants differ only in capitalization.
		public int compareTo(Object o) {
			int rv = super.compareTo(o);
			if (rv != 0) {
				return rv;
			}
			if (!(o instanceof PHPConstantDataImp)) {
				return -1;
			}
			PHPConstantDataImp other = (PHPConstantDataImp) o;
			return getName().compareTo(other.getName());
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// PHPFileData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	public static class PHPFileDataImp extends PHPCodeDataImp implements PHPFileData {//, Externalizable {
		private PHPClassData[] classes;
		private PHPFunctionData[] functions;
		private PHPIncludeFileData[] includeFiles;
		private PHPVariablesTypeManager variablesTypeManager;
		private IPHPMarker[] markers;
		private PHPBlock[] phpBlocks;
		private PHPConstantData[] constants;
		private long lastModified;
		private String comparableName;

		public PHPFileDataImp(String fileName, UserData userData, PHPClassData[] classes, PHPFunctionData[] functions, PHPVariablesTypeManager variablesTypeManager, PHPIncludeFileData[] includeFiles, PHPConstantData[] constans, IPHPMarker[] markers, PHPBlock[] phpBlocks, PHPDocBlock docBlock,
			long lastModified) {
			super(fileName, docBlock, userData);
			this.classes = classes;
			this.functions = functions;
			this.variablesTypeManager = variablesTypeManager;
			this.includeFiles = includeFiles;
			this.markers = markers;
			this.phpBlocks = phpBlocks;
			this.constants = constans;
			this.lastModified = lastModified;
			this.comparableName = new File(name).getName();
		}

		public PHPClassData[] getClasses() {
			return classes;
		}

		public PHPFunctionData[] getFunctions() {
			return functions;
		}

		public PHPVariablesTypeManager getVariableTypeManager() {
			return variablesTypeManager;
		}

		public PHPIncludeFileData[] getIncludeFiles() {
			return includeFiles;
		}

		public IPHPMarker[] getMarkers() {
			return markers;
		}

		public PHPBlock[] getPHPBlocks() {
			return phpBlocks;
		}

		public PHPConstantData[] getConstants() {
			return constants;
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

		public Object getIdentifier() {
			return getName();
		}

		public boolean isValid() {
			long fileLastModified = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(getName())).getModificationStamp();
			return getCreationTimeLastModified() - fileLastModified >= 0;
		}

		public long getCreationTimeLastModified() {
			return lastModified;
		}

		public String getComparableName() {
			return comparableName;
		}

		public int compareTo(Object o) {
			return comparableName.compareToIgnoreCase(((ComparableName) o).getComparableName());
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// UserData implemation
	/////////////////////////////////////////////////////////////////////////////////////////////
	static final class UserDataImp implements UserData, Serializable {

		private String fileName;
		private int startPosition;
		private int endPosition;
		private int stopPosition;
		private int stopLine;

		public UserDataImp(String fileName, int startPosition, int endPosition, int stopPosition, int stopLine) {
			this.fileName = fileName;
			this.startPosition = startPosition;
			this.endPosition = endPosition;
			this.stopPosition = stopPosition;
			this.stopLine = stopLine;
		}

		/**
		 * Returns the name of the file.
		 */
		public final String getFileName() {
			return fileName;
		}

		final void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * Returns the start position of the user data in the file.
		 */
		public final int getStartPosition() {
			return startPosition;
		}

		final void setStartPosition(int startPosition) {
			this.startPosition = startPosition;
		}

		/**
		 * Returns the end position of the user data in the file.
		 */
		public final int getEndPosition() {
			return endPosition;
		}

		final void setEndPosition(int endPosition) {
			this.endPosition = endPosition;
		}

		/**
		 * Returns the stop position of the user data in the file.
		 */
		public final int getStopPosition() {
			return stopPosition;
		}

		final void setStopPosition(int stopPosition) {
			this.stopPosition = stopPosition;
		}

		/**
		 * Returns the stop line of the user data in the file.
		 */
		public final int getStopLine() {
			return stopLine;
		}

		final void setStopLine(int stopLine) {
			this.stopLine = stopLine;
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof UserData)) {
				return false;
			}
			UserData other = (UserData) obj;
			return startPosition == other.getStartPosition() && endPosition == other.getEndPosition() && stopPosition == other.getStopPosition() && fileName.equals(other.getFileName());
		}
	}

}