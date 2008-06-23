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
package org.eclipse.php.internal.core.phpModel.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;

public abstract class FilterableCompositePhpModel extends CompositePhpModel implements IPhpModelFilterable {

	private static final CodeData[] EMPTY = new CodeData[0];

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
		PHPClassData exactElement = super.getClass(fileName, className);
		if (exactElement != null)
			return exactElement;
		CodeData[] allElements = getClass(className);
		if (allElements.length == 0 || allElements == null) {
			return null;
		}
		if (filter != null) {
			for (int j = 0; j < allElements.length; ++j) {
				if (filter.select(this, allElements[j], fileName)) {
					return (PHPClassData) allElements[j];
				}
			}
		}
		return (PHPClassData) allElements[0];
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#getConstant(java.lang.String, java.lang.String)
	 */
	public PHPConstantData getConstant(String fileName, String constantName) {
		PHPConstantData exactElement = super.getConstant(fileName, constantName);
		if (exactElement != null)
			return exactElement;
		CodeData[] allElements = getConstant(constantName);
		if (allElements.length == 0 || allElements == null) {
			return null;
		}
		if (filter != null) {
			for (int j = 0; j < allElements.length; ++j) {
				if (filter.select(this, allElements[j], fileName)) {
					return (PHPConstantData) allElements[j];
				}
			}
		}
		return (PHPConstantData) allElements[0];
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#getFunction(java.lang.String, java.lang.String)
	 */
	public PHPFunctionData getFunction(String fileName, String functionName) {
		PHPFunctionData exactElement = super.getFunction(fileName, functionName);
		if (exactElement != null)
			return exactElement;
		CodeData[] allElements = getFunction(functionName);
		if (allElements.length == 0 || allElements == null) {
			return null;
		}
		if (filter != null) {
			for (int j = 0; j < allElements.length; ++j) {
				if (filter.select(this, allElements[j], fileName)) {
					return (PHPFunctionData) allElements[j];
				}
			}
		}
		return (PHPFunctionData) allElements[0];
	}

	IPhpModelFilter filter;

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.IPhpModelFilterable#setFilter(org.eclipse.php.internal.core.phpModel.parser.IPhpModelFilterable.IPhpModelFilter)
	 */
	public void setFilter(IPhpModelFilter filter) {
		this.filter = filter;
	}

	public CodeData[] getFilteredClasses(String fileName, String className) {
		CodeData[] allElements = getClass(className);
		return selectElements(fileName, allElements);
	}

	public CodeData[] getFilteredConstants(String fileName, String constantName) {
		CodeData[] allElements = getConstant(constantName);
		return selectElements(fileName, allElements);
	}

	public CodeData[] getFilteredFunctions(String fileName, String functionName) {
		CodeData[] allElements = getFunction(functionName);
		return selectElements(fileName, allElements);
	}

	/**
	 * @param fileName
	 * @param allElements
	 * @return selected elements
	 */
	private CodeData[] selectElements(String fileName, CodeData[] allElements) {
		if (allElements == null || allElements.length == 0)
			return EMPTY;
		if (filter == null)
			return allElements;

		List filteredElements = new ArrayList();
		if (allElements != null && filter != null) {
			for (int j = 0; j < allElements.length; ++j) {
				if (filter.select(this, allElements[j], fileName)) {
					filteredElements.add(allElements[j]);
				}
			}
		}
		if (filteredElements.size() != 0)
			return (CodeData[]) filteredElements.toArray(new CodeData[filteredElements.size()]);
		return allElements;
	}

}
