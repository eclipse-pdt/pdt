/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.launch;

import org.eclipse.php.phpunit.PHPUnitPlugin;

public interface PHPUnitLaunchAttributes {

	String ATTRIBUTE_PHPUNIT_LAUNCH = PHPUnitPlugin.ID + ".phpUnitLaunch"; //$NON-NLS-1$
	String ATTRIBUTE_EXECUTION_TYPE = PHPUnitPlugin.ID + ".runType"; //$NON-NLS-1$
	/**
	 * Possible values for ATTRIBUTE_EXECUTION_TYPE
	 */
	String COMPOSER_EXECUTION_TYPE = "COMPOSER_EXECUTION_TYPE"; //$NON-NLS-1$
	String PHAR_EXECUTION_TYPE = "PHAR_EXECUTION_TYPE"; //$NON-NLS-1$

	String ATTRIBUTE_CONTAINER_TYPE = "ZEND_PHPUNIT_TESTS_CONTAINER_TYPE"; //$NON-NLS-1$
	/**
	 * Possible values for ATTRIBUTE_CONTAINER_TYPE
	 */
	String PROJECT_CONTAINER = "SCRIPT_PROJECT"; //$NON-NLS-1$
	String FOLDER_CONTAINER = "SCRIPT_FOLDER"; //$NON-NLS-1$
	String SOURCE_CONTAINER = "SCRIPT_SOURCE"; //$NON-NLS-1$

	String ATTRIBUTE_CONTAINER = PHPUnitPlugin.ID + ".container"; //$NON-NLS-1$
	String ATTRIBUTE_CLASS = PHPUnitPlugin.ID + ".class"; //$NON-NLS-1$
	String ATTRIBUTE_METHOD = PHPUnitPlugin.ID + ".method"; //$NON-NLS-1$
	String ATTRIBUTE_CODE_COVERAGE = PHPUnitPlugin.ID + ".code_coverage"; //$NON-NLS-1$
	String ATTRIBUTE_PHPUNIT_CFG = PHPUnitPlugin.ID + ".phpunitPath"; //$NON-NLS-1$
	String ATTRIBUTE_LOG_XML = PHPUnitPlugin.ID + ".logXml"; //$NON-NLS-1$
	String ATTRIBUTE_LOG_XML_LOCATION = PHPUnitPlugin.ID + ".logXmlLocation"; //$NON-NLS-1$
	String ATTRIBUTE_METHOD_NAME = "ATTRIBUTE_METHOD_NAME"; //$NON-NLS-1$
	String ATTRIBUTE_FILE = PHPUnitPlugin.ID + ".file"; //$NON-NLS-1$
	String ATTRIBUTE_PROJECT = PHPUnitPlugin.ID + ".project"; //$NON-NLS-1$
	String ATTRIBUTE_RERUN = PHPUnitPlugin.ID + ".rerun"; //$NON-NLS-1$
	String ATTRIBUTE_FILTER = PHPUnitPlugin.ID + ".filter"; //$NON-NLS-1$
	String ATTRIBUTE_TEST_SUITE = PHPUnitPlugin.ID + ".suite"; //$NON-NLS-1$
	String ATTRIBUTE_RUN_CONTAINER = PHPUnitPlugin.ID + ".is_suite"; //$NON-NLS-1$

	String ATTRIBUTE_COLLECT_CODE_COVERAGE = "collectCodeCoverage"; //$NON-NLS-1$

}
