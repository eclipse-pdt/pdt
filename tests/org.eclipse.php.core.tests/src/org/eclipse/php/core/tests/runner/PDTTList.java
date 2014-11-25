/*******************************************************************************
 * Copyright (c) 2014 Dawid Pakuła and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.tests.runner;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

/**
 * Special runner for PDT test with parametters collected to String[]
 * 
 * TODO: Allow Before and After with fileName
 */
public class PDTTList extends AbstractPDTTRunner {
	/**
	 * Element for public static field. Have to be String[]
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Dirs {
	}

	/**
	 * Public static method with one or two arguments.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface BeforeList {
	}

	/**
	 * Public static method with one or two arguments
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface AfterList {
	}

	public class BeforeStatement extends Statement {
		private final Statement fNext;
		private final List<FrameworkMethod> fBefores;
		private final Object fTest;

		public BeforeStatement(Statement next, List<FrameworkMethod> befores,
				Object test) {
			fNext = next;
			fBefores = befores;
			fTest = test;
		}

		@Override
		public void evaluate() throws Throwable {
			for (FrameworkMethod before : fBefores) {
				before.invokeExplosively(fTest);
			}
			fNext.evaluate();
		}
	}

	public class AfterStatement extends Statement {
		private final Statement fBefore;
		private final List<FrameworkMethod> fAfters;
		private final Object fTest;

		public AfterStatement(Statement before, List<FrameworkMethod> afters,
				Object test) {
			fBefore = before;
			fAfters = afters;
			fTest = test;
		}

		@Override
		public void evaluate() throws Throwable {
			List<Throwable> errors = new LinkedList<Throwable>();
			try {
				fBefore.evaluate();
			} catch (Throwable e) {
				errors.add(e);
			} finally {
				for (FrameworkMethod after : fAfters) {
					try {
						after.invokeExplosively(fTest);
					} catch (Throwable e) {
						errors.add(e);
					}
				}
			}
			MultipleFailureException.assertEmpty(errors);
		}
	}

	/**
	 * Runner for concrette PDTT file, it also holds TestInstance
	 */
	private class FileTestClassRunner extends BlockJUnit4ClassRunner {
		private final String fName;

		private final int fIndex;

		public FileTestClassRunner(Class<?> clazz, String name)
				throws InitializationError {
			super(clazz);
			fName = name;
			// to avoid jUnit limitation
			fIndex = counter++;
		}

		@Override
		protected String getName() {
			return fName;
		}

		@Override
		protected String testName(FrameworkMethod method) {
			return method.getName() + '[' + fIndex + ']';
		}

		@Override
		protected void validateConstructor(List<Throwable> errors) {
			Class<?> javaClass = getTestClass().getJavaClass();
			if (javaClass.getConstructors().length != 1) {
				errors.add(new Exception("Only one constructor is allowed!"));
				return;
			}
			Constructor<?> constructor = javaClass.getConstructors()[0];
			if (constructor.getParameterTypes().length != 1
					|| !constructor.getParameterTypes()[0]
							.isAssignableFrom(String[].class)) {
				errors.add(new Exception(
						"Public constructor with string argument is required"));
			}
		}

		@Override
		protected Object createTest() throws Exception {
			return createTestInstance();
		}

		@Override
		protected Statement classBlock(RunNotifier notifier) {
			return childrenInvoker(notifier);
		}

		@Override
		protected Annotation[] getRunnerAnnotations() {
			return new Annotation[0];
		}

		@Override
		protected void validateTestMethods(List<Throwable> errors) {
			for (FrameworkMethod method : getTestClass().getAnnotatedMethods(
					Test.class)) {
				method.validatePublicVoid(false, errors);
				Class<?>[] types = method.getMethod().getParameterTypes();
				if (!(types.length == 0 || (types.length == 1 && types[0]
						.isAssignableFrom(String.class)))) {
					errors.add(new Exception(method.toString()
							+ ": Dirs list must by empty or one string"));
				}
			}
		}

		@Override
		protected Statement methodInvoker(final FrameworkMethod method,
				final Object test) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					if (method.getMethod().getParameterTypes().length == 0) {
						method.invokeExplosively(test);
					} else {
						method.invokeExplosively(test, fName);
					}
				}
			};
		}
	}

	private static final List<Runner> EMPTY_RUNNERS = Collections
			.<Runner> emptyList();

	private Object testInstance;
	private String[] fileList;
	private final List<Runner> runners = new LinkedList<Runner>();
	private int counter = 0;

	public PDTTList(Class<?> klass) throws Throwable {
		super(klass);
		readParameters();
		buildRunners();
	}

	private void buildRunners() throws Throwable {
		for (String fileName : fileList) {
			runners.add(new FileTestClassRunner(getTestClass().getJavaClass(),
					fileName));
		}
	}

	private void readParameters() throws Throwable {
		for (FrameworkField field : getTestClass().getAnnotatedFields(
				Dirs.class)) {
			if (field.isPublic() && field.isPublic()
					&& field.getType().isAssignableFrom(String[].class)) {
				fileList = buildFileList((String[]) field.getField().get(null));
				return;
			}
		}

		throw new Exception(getTestClass().getName()
				+ ": Public static String[] field with @Dirs is required");
	}

	protected Object createTestInstance() {
		if (testInstance == null) {
			try {
				testInstance = getTestClass().getOnlyConstructor().newInstance(
						new Object[] { fileList });
			} catch (Exception e) {
				System.out.println("This is not possible!");
			}
		}

		return testInstance;
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {
		Statement statement = childrenInvoker(notifier);
		statement = withBeforeClasses(statement);
		statement = withAfterClasses(statement);
		return statement;
	}

	protected void collectInitializationErrors(java.util.List<Throwable> errors) {
		super.collectInitializationErrors(errors);
		validatePublicVoidWithNoArguments(BeforeList.class, errors);
		validatePublicVoidWithNoArguments(AfterList.class, errors);
	}

	protected void validatePublicVoidWithNoArguments(
			Class<? extends Annotation> clazz, List<Throwable> errors) {
		for (FrameworkMethod method : getTestClass().getAnnotatedMethods(clazz)) {
			if (!method.isPublic() || method.isStatic()) {
				errors.add(new Exception("Method have to be public not static"));
			}
			if (method.getMethod().getParameterTypes().length != 0) {
				errors.add(new Exception("Method must be without arguments"));
			}
		}
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		List<FrameworkMethod> annotatedMethods = getTestClass()
				.getAnnotatedMethods(BeforeList.class);
		if (!annotatedMethods.isEmpty()) {
			statement = new BeforeStatement(statement, annotatedMethods,
					createTestInstance());
		}
		return super.withBeforeClasses(statement);
	}

	@Override
	protected Statement withAfterClasses(Statement statement) {
		statement = super.withAfterClasses(statement);

		List<FrameworkMethod> annotatedMethods = getTestClass()
				.getAnnotatedMethods(AfterList.class);
		if (!annotatedMethods.isEmpty()) {
			statement = new AfterStatement(statement, annotatedMethods,
					createTestInstance());
		}

		return statement;
	}
}
