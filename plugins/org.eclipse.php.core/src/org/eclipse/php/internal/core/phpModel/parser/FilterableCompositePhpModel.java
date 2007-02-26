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
import java.util.Arrays;
import java.util.Iterator;
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
		IPhpModel[] models = getModels();
		PHPClassData exactClass = super.getClass(fileName, className);
		if (exactClass != null)
			return exactClass;
		List classes = new ArrayList();
		for (int i = 0; i < models.length; i++) {
			// else collect all the classes to apply filter
			CodeData[] modelClasses = models[i].getClass(className);
			if (modelClasses != null && modelClasses.length > 0) {
				classes.addAll(Arrays.asList(modelClasses));
			}
		}
		switch (classes.size()) {
			case 0:
				return null;
			case 1:
				return (PHPClassData) classes.get(0);
		}
		if (filter != null) {
			for (Iterator i = classes.iterator(); i.hasNext();) {
				PHPClassData classs = (PHPClassData) i.next();
				if (filter.select(this, classs, fileName)) {
					return classs;
				}
			}
		}
		return (PHPClassData) classes.get(0);
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#getConstant(java.lang.String, java.lang.String)
	 */
	public PHPConstantData getConstant(String fileName, String constantName) {
		IPhpModel[] models = getModels();
		PHPConstantData exactConstant = super.getConstant(fileName, constantName);
		if (exactConstant != null)
			return exactConstant;
		List constants = new ArrayList();
		for (int i = 0; i < models.length; i++) {
			// else collect all the constants to apply filter
			CodeData[] modelConstants = models[i].getConstant(constantName);
			if (modelConstants != null && modelConstants.length > 0) {
				constants.addAll(Arrays.asList(modelConstants));
			}
		}
		switch (constants.size()) {
			case 0:
				return null;
			case 1:
				return (PHPConstantData) constants.get(0);
		}
		if (filter != null) {
			for (Iterator i = constants.iterator(); i.hasNext();) {
				PHPConstantData constant = (PHPConstantData) i.next();
				if (filter.select(this, constant, fileName)) {
					return constant;
				}
			}
		}
		return (PHPConstantData) constants.get(0);
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.CompositePhpModel#getFunction(java.lang.String, java.lang.String)
	 */
	public PHPFunctionData getFunction(String fileName, String functionName) {
		IPhpModel[] models = getModels();
		PHPFunctionData exactFunction = super.getFunction(fileName, functionName);
		if (exactFunction != null)
			return exactFunction;
		List functions = new ArrayList();
		for (int i = 0; i < models.length; i++) {
			// else collect all the functions to apply filter
			CodeData[] modelFunctions = models[i].getFunction(functionName);
			if (modelFunctions != null && modelFunctions.length > 0) {
				functions.addAll(Arrays.asList(modelFunctions));
			}
		}
		switch (functions.size()) {
			case 0:
				return null;
			case 1:
				return (PHPFunctionData) functions.get(0);
		}
		if (filter != null) {
			for (Iterator i = functions.iterator(); i.hasNext();) {
				PHPFunctionData function = (PHPFunctionData) i.next();
				if (filter.select(this, function, fileName)) {
					return function;
				}
			}
		}
		return (PHPFunctionData) functions.get(0);
	}

	IPhpModelFilter filter;

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.IPhpModelFilterable#setFilter(org.eclipse.php.internal.core.phpModel.parser.IPhpModelFilterable.IPhpModelFilter)
	 */
	public void setFilter(IPhpModelFilter filter) {
		this.filter = filter;
	}
}
