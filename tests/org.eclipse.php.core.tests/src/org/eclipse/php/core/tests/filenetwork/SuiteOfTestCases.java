/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.core.tests.filenetwork;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.AssertionFailedError;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * A test case class that can be set up (using the setUpSuite() method) and tore
 * down (using the teardDownSuite() method) once for all test cases of this
 * class.
 */
public abstract class SuiteOfTestCases extends TestCase {

	/*
	 * A test suite that initialize the test case's fields once, then that
	 * copies the values of these fields intto each subsequent test case.
	 */
	public static class Suite extends TestSuite {
		SuiteOfTestCases currentTestCase;

		/*
		 * Creates a new suite on the given class. This class must be a subclass
		 * of SetupableTestSuite.
		 */
		public Suite(Class<? extends SuiteOfTestCases> theClass) {
			super(theClass);
		}

		/**
		 * Creates a new suite on the given class. Only the methods specified in
		 * the second parameter and included in the suite.
		 * 
		 * @param theClass
		 * @param methodNames
		 */
		public Suite(Class<? extends SuiteOfTestCases> theClass,
				String... methodNames) {
			super(theClass.getName());
			for (int i = 0; i < methodNames.length; ++i) {
				final String methodName = methodNames[i];
				try {
					final Method method = theClass.getMethod(methodName,
							new Class[0]);
					if (Modifier.isPublic(method.getModifiers())
							&& !Modifier.isStatic(method.getModifiers())) {
						addTest(createTest(theClass, methodName));
					} else {
						warning(methodName, "Wrong modifiers");
					}
				} catch (SecurityException e) {
					warning(methodName, e.toString());
				} catch (NoSuchMethodException e) {
					warning(methodName, e.toString());
				}
			}
		}

		private void warning(final String name, final String message) {
			addTest(new TestCase(name) {
				protected void runTest() {
					fail(message);
				}
			});
		}

		public Suite(String name) {
			super(name);
		}

		void initialize(SuiteOfTestCases test) {
			Class<?> currentClass = test.getClass();
			while (currentClass != null
					&& !currentClass.equals(SuiteOfTestCases.class)) {
				Field[] fields = currentClass.getDeclaredFields();
				for (int i = 0, length = fields.length; i < length; i++) {
					Field field = fields[i];

					// skip static and final fields
					int modifiers = field.getModifiers();
					if (Modifier.isStatic(modifiers)
							|| Modifier.isFinal(modifiers))
						continue;

					// make the field accessible
					field.setAccessible(true);

					try {
						Object value = field.get(this.currentTestCase);
						field.set(test, value);
					} catch (IllegalAccessException e) {
					}
				}
				currentClass = currentClass.getSuperclass();
			}
		}

		public void run(final TestResult result) {
			Protectable p = new Protectable() {
				public void protect() throws Exception {
					try {
						// run suite (first test run will setup the suite)
						superRun(result);
					} finally {
						// tear down the suite
						if (Suite.this.currentTestCase != null) {
							// protect against empty test suite
							Suite.this.currentTestCase.tearDownSuite();
						}
					}
				}
			};
			result.runProtected(this, p);
		}

		public void superRun(TestResult result) {
			super.run(result);
		}

		public void runTest(Test test, TestResult result) {
			if (test instanceof SuiteOfTestCases) {
				final SuiteOfTestCases current = (SuiteOfTestCases) test;
				if (this.currentTestCase == null) {
					// setup suite
					try {
						current.setUpSuite();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					// copy the values of the previous current test case's
					// fields into the current one
					this.initialize(current);
				}
				current.parentSuite = this;
				try {
					super.runTest(test, result);
				} finally {
					current.parentSuite = null;
					// make current
					this.currentTestCase = current;
				}
			} else {
				/*
				 * If there was error in TestCase constructor - the test will
				 * not be of the SuiteOfTestCases type
				 */
				super.runTest(test, result);
			}
		}
	}

	Suite parentSuite;
	private final static Map<String, Suite> initializedSuites = new HashMap<String, Suite>();

	public SuiteOfTestCases(String name) {
		super(name);
	}

	/**
	 * Setup the test suite once before all test cases run.
	 */
	public void setUpSuite() throws Exception {
	}

	/**
	 * Tear down the test suite once after all test cases have run.
	 */
	public void tearDownSuite() throws Exception {
	}

	@Override
	public void run(TestResult result) {
		final String className = getClass().getName();
		parentSuite = initializedSuites.get(className);
		if (parentSuite == null) {
			parentSuite = new Suite(getClass().getName());
			initializedSuites.put(getClass().getName(), parentSuite);
			System.out.println("setUpSuite() in " + getClass().getName());
			// TODO (alex) tearDownSuite() not executed
			final AtomicBoolean errors = new AtomicBoolean();
			final TestListener listener = new TestListener() {
				public void startTest(Test test) {
				}

				public void endTest(Test test) {
				}

				public void addFailure(Test test, AssertionFailedError t) {
					errors.set(true);
				}

				public void addError(Test test, Throwable t) {
					errors.set(true);
				}
			};
			result.addListener(listener);
			result.runProtected(this, new Protectable() {
				public void protect() throws Throwable {
					setUpSuite();
				}
			});
			result.removeListener(listener);
			if (errors.get()) {
				return;
			}
		} else if (parentSuite.currentTestCase != null) {
			parentSuite.initialize(this);
		}
		parentSuite.currentTestCase = this;
		super.run(result);
	}

}
