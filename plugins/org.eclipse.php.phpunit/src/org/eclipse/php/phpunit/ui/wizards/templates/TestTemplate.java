/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.wizards.templates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.ui.preferences.includepath.IncludePathUtils;
import org.eclipse.php.internal.ui.wizards.types.TextTemplate;
import org.eclipse.php.phpunit.PHPUnitPlugin;

abstract public class TestTemplate extends TextTemplate {

	private static final String TEST_SUPER_CLASS_NAME = "TestSuperClassName"; //$NON-NLS-1$
	private static final String TEST_CLASS_NAME = "TestClassName"; //$NON-NLS-1$
	private static final String IGNORE_STRUCT = "Ignore"; //$NON-NLS-1$
	protected static final String INPUT = "input"; //$NON-NLS-1$
	private static final String OUTPUT = "output"; //$NON-NLS-1$

	private static final String REQUIRE_VAR = "RequireLocation"; //$NON-NLS-1$

	private static final String REQUIRES_STRUCT = "Requires"; //$NON-NLS-1$
	private static final String REQUIRES_COMPILED = "RequiresCompiled"; //$NON-NLS-1$

	private List<String> requires;

	public void addRequire(final String location) {
		if (requires == null) {
			requires = new ArrayList<>(1);
		}
		requires.add(location.replace('\\', '/'));
	}

	protected void compileRequires() {
		extract(INPUT, REQUIRES_STRUCT, REQUIRES_COMPILED);
		if (requires == null || requires.isEmpty()) {
			set(REQUIRES_STRUCT, ""); //$NON-NLS-1$
			return;
		}
		for (String require : requires) {
			set(REQUIRE_VAR, require);
			compile(REQUIRES_COMPILED, REQUIRES_STRUCT, true);
		}
	}

	public String compileTemplate() {
		compileRequires();
		removeIgnore();
		return compile(INPUT, OUTPUT, false);
	}

	abstract protected String getTemplatePath();

	private String readTemplate() throws IOException {
		URL url = FileLocator.find(PHPUnitPlugin.getDefault().getBundle(), new Path(getTemplatePath()), null);
		url = FileLocator.resolve(url);
		final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) url.getContent()));
		String line;
		final StringBuilder buffer = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append("\n"); //$NON-NLS-1$
		}
		return buffer.toString();
	}

	private void removeIgnore() {
		extract(INPUT, IGNORE_STRUCT, NULL_VAR);
		set(IGNORE_STRUCT, ""); //$NON-NLS-1$
	}

	public String resolveTemplate() throws IOException {
		return set(INPUT, readTemplate());
	}

	public void setTestClassParams(final String name, final String location) {
		set(TEST_CLASS_NAME, name);
	}

	public void setTestSuperClass(final IType superClass, String superClassName, final IProject project) {
		String superClassFileName = null;
		if (superClass == null) {
			superClassFileName = null;
		} else {
			final IScriptProject create = DLTKCore.create(project);
			final IPath relative = IncludePathUtils.getRelativeLocationFromIncludePath(create, superClass);
			if (!relative.isEmpty()) {
				superClassFileName = relative.toOSString();
			}

		}
		setTestSuperClassParams(superClassName, superClassFileName);
	}

	private void setTestSuperClassParams(final String name, final String relativeLocation) {
		set(TEST_SUPER_CLASS_NAME, name);
	}
}