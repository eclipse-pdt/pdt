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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;

public abstract class FilterableCompositePhpModel extends CompositePhpModel implements IPhpModelFilterable {

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#addModel(org.eclipse.php.internal.core.phpModel.parser.IPhpModel)
	 */
	public void addModel(IPhpModel newModel) {
		super.addModel(newModel);
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#getClass(java.lang.String, java.lang.String)
	 */
	public PHPClassData getClass(String fileName, String className) {
		PHPClassData exactClass = super.getClass(fileName, className);
		if (exactClass != null || filter == null)
			return exactClass;
		CodeData[] allClasses = getClass(className);
		if (allClasses != null) {
			for (int j = 0; j < allClasses.length; ++j) {
				if (filter.select(this, allClasses[j], fileName)) {
					return (PHPClassData) allClasses[j];
				}
			}
		}
		return null;
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#getConstant(java.lang.String, java.lang.String)
	 */
	public PHPConstantData getConstant(String fileName, String constantName) {
		PHPConstantData exactConstant = super.getConstant(fileName, constantName);
		if (exactConstant != null || filter == null)
			return exactConstant;
		CodeData[] allConstants = getConstant(constantName);
		if (allConstants != null) {
			for (int j = 0; j < allConstants.length; ++j) {
				if (filter.select(this, allConstants[j], fileName)) {
					return (PHPConstantData) allConstants[j];
				}
			}
		}
		return null;
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#getFunction(java.lang.String, java.lang.String)
	 */
	public PHPFunctionData getFunction(String fileName, String functionName) {
		PHPFunctionData exactFunction = super.getFunction(fileName, functionName);
		if (exactFunction != null || filter == null)
			return exactFunction;
		CodeData[] allFunctions = getFunction(functionName);
		if (allFunctions != null) {
			for (int j = 0; j < allFunctions.length; ++j) {
				if (filter.select(this, allFunctions[j], fileName)) {
					return (PHPFunctionData) allFunctions[j];
				}
			}
		}
		return null;
	}

	IPhpModelFilter filter;

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.IPhpModelFilterable#setFilter(org.eclipse.php.internal.core.phpModel.parser.IPhpModelFilterable.IPhpModelFilter)
	 */
	public void setFilter(IPhpModelFilter filter) {
		this.filter = filter;
	}

	public CodeData[] getFilteredClasses(String fileName, String className) {
		CodeData[] allClasses = getClass(className);
		List filteredClasses = new ArrayList();
		if (allClasses != null) {
			for (int j = 0; j < allClasses.length; ++j) {
				if (filter.select(this, allClasses[j], fileName)) {
					filteredClasses.add(allClasses[j]);
				}
			}
		}
		return (CodeData[]) filteredClasses.toArray(new CodeData[filteredClasses.size()]);
	}

	public CodeData[] getFilteredConstants(String fileName, String constantName) {
		CodeData[] allConstants = getConstant(constantName);
		List filteredConstants = new ArrayList();
		if (allConstants != null) {
			for (int j = 0; j < allConstants.length; ++j) {
				if (filter.select(this, allConstants[j], fileName)) {
					filteredConstants.add(allConstants[j]);
				}
			}
		}
		return (CodeData[]) filteredConstants.toArray(new CodeData[filteredConstants.size()]);
	}

	public CodeData[] getFilteredFunctions(String fileName, String functionName) {
		CodeData[] allFunctions = getFunction(functionName);
		List filteredFunctions = new ArrayList();
		if (allFunctions != null) {
			for (int j = 0; j < allFunctions.length; ++j) {
				if (filter.select(this, allFunctions[j], fileName)) {
					filteredFunctions.add(allFunctions[j]);
				}
			}
		}
		return (CodeData[]) filteredFunctions.toArray(new CodeData[filteredFunctions.size()]);
	}
}
