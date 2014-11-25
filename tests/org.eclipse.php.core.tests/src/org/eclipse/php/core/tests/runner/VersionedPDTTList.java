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
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.PHPVersion;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.osgi.framework.Bundle;

/**
 * Special runner for PDT test with parametters collected to Map<PHPVersion,
 * String[] dirs>
 * 
 * TODO: Allow Before and After with fileName
 */
public class VersionedPDTTList extends Suite implements IPDTTRunner {
	/**
	 * Element for public static field. Have to be Map<PHPVersion, String[]>
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Parameters {
		/**
		 * If true, tests will be with real directory structure, each level as
		 * seperate test tree element
		 */
		boolean recursive() default false;
	}

	/**
	 * Public static method with one or two arguments.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface BeforeVersion {
	}

	/**
	 * Public static method with one or two arguments
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface AfterVersion {
	}

	/**
	 * Parent runner for PHPVersionRunner and DirRunner
	 */
	private abstract class ParentRunner extends Suite {
		protected final List<Runner> runners = new LinkedList<Runner>();
		protected Object testInstance;
		protected final PHPVersion fVersion;
		protected String[] fFileList = new String[0];

		public ParentRunner(Class<?> klass, PHPVersion phpVersion)
				throws Exception {
			super(klass, EMPTY_RUNNERS);
			fVersion = phpVersion;

			// fFileList = buildFileList(dirs);
		}

		@Override
		protected Annotation[] getRunnerAnnotations() {
			return new Annotation[0];
		}

		protected Object createTestInstance() {
			if (testInstance == null) {
				try {
					testInstance = getTestClass().getOnlyConstructor()
							.newInstance(fVersion, fFileList);
				} catch (Exception e) {
					System.out.println("This is not possible!");
				}
			}

			return testInstance;
		}

		@Override
		protected List<Runner> getChildren() {
			return runners;
		}

		@Override
		protected Statement withBeforeClasses(Statement statement) {
			List<FrameworkMethod> annotatedMethods = getTestClass()
					.getAnnotatedMethods(BeforeVersion.class);
			if (fFileList.length > 0 && !annotatedMethods.isEmpty()) {
				statement = new BeforeStatement(statement, annotatedMethods,
						createTestInstance());
			}
			return super.withBeforeClasses(statement);
		}

		@Override
		protected Statement withAfterClasses(Statement statement) {
			statement = super.withAfterClasses(statement);

			List<FrameworkMethod> annotatedMethods = getTestClass()
					.getAnnotatedMethods(AfterVersion.class);
			if (fFileList.length > 0 && !annotatedMethods.isEmpty()) {
				statement = new AfterStatement(statement, annotatedMethods,
						createTestInstance());
			}

			return statement;
		}
	}

	/**
	 * Dir runner is used ony with recursives
	 */
	private class DirRunner extends ParentRunner {
		private final String fTestName;

		public DirRunner(Class<?> klass, PHPVersion phpVersion, String dir,
				String testName) throws Throwable {
			super(klass, phpVersion);
			fTestName = testName;

			fFileList = buildFileList(new String[] { dir });
			for (String fileName : fFileList) {
				runners.add(new FileTestClassRunner(this, fileName));
			}

			Enumeration<String> entryPaths = getBundle().getEntryPaths(dir);
			Bundle bundle = getBundle();
			if (entryPaths != null) {
				while (entryPaths.hasMoreElements()) {
					final String path = (String) entryPaths.nextElement();
					if (!path.endsWith("/")) {
						continue;
					}
					final String namePath = path
							.substring(0, path.length() - 1);
					int pos = namePath.lastIndexOf('/');
					final String name = (pos >= 0 ? namePath.substring(pos + 1)
							: namePath);
					try {
						bundle.getEntry(path); // test Only
						runners.add(new DirRunner(klass, phpVersion, path, name));
					} catch (Exception e) {
						continue;
					}
				}
			}
		}

		@Override
		protected String getName() {
			return fTestName;
		}
	}

	/**
	 * PHPVersion group runner, it holds test instance for whole runner
	 */
	private class PHPVersionRunner extends ParentRunner {
		public PHPVersionRunner(Class<?> klass, PHPVersion phpVersion,
				String[] dirs) throws Throwable {
			super(klass, phpVersion);
			if (isRecursive) {
				for (String dirName : dirs) {
					runners.add(new DirRunner(klass, phpVersion, dirName,
							dirName));
				}
			} else {
				fFileList = buildFileList(dirs);

				for (String fileName : fFileList) {
					runners.add(new FileTestClassRunner(this, fileName));
				}
			}
		}

		@Override
		protected String getName() {
			return fVersion.toString();
		}

		@Override
		protected Statement classBlock(RunNotifier notifier) {
			Statement statement = childrenInvoker(notifier);
			if (!isRecursive) {
				statement = withBeforeClasses(statement);
				statement = withAfterClasses(statement);
			}
			return statement;
		}

		protected void collectInitializationErrors(
				java.util.List<Throwable> errors) {
			super.collectInitializationErrors(errors);
			validatePublicVoidWithNoArguments(BeforeVersion.class, errors);
			validatePublicVoidWithNoArguments(AfterVersion.class, errors);
		}

		protected void validatePublicVoidWithNoArguments(
				Class<? extends Annotation> clazz, List<Throwable> errors) {
			for (FrameworkMethod method : getTestClass().getAnnotatedMethods(
					clazz)) {
				if (!method.isPublic() || method.isStatic()) {
					errors.add(new Exception(
							"Method have to be public not static"));
				}
				if (method.getMethod().getParameterTypes().length != 0) {
					errors.add(new Exception("Method must be without arguments"));
				}
			}
		}

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

		private final ParentRunner fParentRunner;

		public FileTestClassRunner(ParentRunner parentRunner, String name)
				throws InitializationError {
			super(parentRunner.getTestClass().getJavaClass());
			fName = name;
			// to avoid jUnit limitation
			fIndex = counter++;
			fParentRunner = parentRunner;
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
			if (constructor.getParameterTypes().length != 2
					|| !constructor.getParameterTypes()[0]
							.isAssignableFrom(PHPVersion.class)
					|| !constructor.getParameterTypes()[1]
							.isAssignableFrom(String[].class)) {
				errors.add(new Exception(
						"Public constructor with string argument is required"));
			}
		}

		@Override
		protected Object createTest() throws Exception {
			return fParentRunner.createTestInstance();
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

	private Map<PHPVersion, String[]> parameters;

	private final List<Runner> runners = new LinkedList<Runner>();

	private int counter = 0;

	private boolean isRecursive = false;

	public VersionedPDTTList(Class<?> klass) throws Throwable {
		super(klass, EMPTY_RUNNERS);
		readParameters();
		buildRunners();
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}

	private void buildRunners() throws Throwable {
		for (Entry<PHPVersion, String[]> entry : parameters.entrySet()) {
			runners.add(new PHPVersionRunner(getTestClass().getJavaClass(),
					entry.getKey(), entry.getValue()));
		}
	}

	private String[] buildFileList(String[] dirs) throws Throwable {
		List<String> list = new LinkedList<String>();
		Bundle context = getBundle();
		for (String dir : dirs) {
			for (String fileName : PDTTUtils.getPDTTFiles(dir, context)) {
				list.add(fileName);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	private Bundle getBundle() throws Throwable {
		for (FrameworkMethod method : getTestClass().getAnnotatedMethods(
				Context.class)) {
			if (method.isPublic() && method.isStatic()
					&& method.getReturnType().isAssignableFrom(Bundle.class)) {
				return (Bundle) method.invokeExplosively(null);
			}
		}

		return PHPCoreTests.getDefault().getBundle();
	}

	private void readParameters() throws Exception {
		for (FrameworkField field : getTestClass().getAnnotatedFields(
				Parameters.class)) {
			if (field.isPublic() && field.isPublic()
					&& field.getType().isAssignableFrom(Map.class)) {
				parameters = (Map<PHPVersion, String[]>) field.getField().get(
						null);
				for (Annotation ann : field.getAnnotations()) {
					if (ann instanceof Parameters) {
						isRecursive = ((Parameters) ann).recursive();
					}
				}

				return;
			}
		}

		throw new Exception(
				getTestClass().getName()
						+ ": Public static Map<PHPVersion, String[]> field with @Dirs is required");
	}
}
