/*******************************************************************************
 * Copyright (c) 2014 Dawid Pakuła and others. 
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.tests.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.osgi.framework.Bundle;

public abstract class AbstractPDTTRunner extends Suite {
	/**
	 * Annotate any public static Bundle getBundle() method
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface Context {
	}

	protected static final List<Runner> EMPTY_RUNNERS = Collections.<Runner> emptyList();
	protected final List<Runner> runners = new LinkedList<>();

	protected int counter = 0;

	public AbstractPDTTRunner(Class<?> klass) throws Exception {
		super(klass, EMPTY_RUNNERS);
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}

	protected String[] buildFileList(String[] dirs) throws Throwable {
		List<String> list = new LinkedList<>();
		Bundle context = getBundle();
		for (String dir : dirs) {
			for (String fileName : PDTTUtils.getPDTTFiles(dir, context)) {
				list.add(fileName);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	protected Bundle getBundle() throws Throwable {
		for (FrameworkMethod method : getTestClass().getAnnotatedMethods(Context.class)) {
			if (method.isPublic() && method.isStatic() && method.getReturnType().isAssignableFrom(Bundle.class)) {
				return (Bundle) method.invokeExplosively(null);
			}
		}

		return PHPCoreTests.getDefault().getBundle();
	}

}
