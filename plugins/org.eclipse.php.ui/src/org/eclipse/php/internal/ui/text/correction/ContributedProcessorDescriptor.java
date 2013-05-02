/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.expressions.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IScriptModelMarker;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.StatusInfo;

public final class ContributedProcessorDescriptor {

	private final IConfigurationElement fConfigurationElement;
	private Object fProcessorInstance;
	private Boolean fStatus;
	// private String fRequiredSourceLevel;
	private final Set<String> fHandledMarkerTypes;

	private static final String ID = "id"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$

	// private static final String REQUIRED_SOURCE_LEVEL= "requiredSourceLevel";

	private static final String HANDLED_MARKER_TYPES = "handledMarkerTypes"; //$NON-NLS-1$
	private static final String MARKER_TYPE = "markerType"; //$NON-NLS-1$

	public ContributedProcessorDescriptor(IConfigurationElement element,
			boolean testMarkerTypes) {
		fConfigurationElement = element;
		fProcessorInstance = null;
		fStatus = null; // undefined
		if (fConfigurationElement.getChildren(ExpressionTagNames.ENABLEMENT).length == 0) {
			fStatus = Boolean.TRUE;
		}
		// fRequiredSourceLevel= element.getAttribute(REQUIRED_SOURCE_LEVEL);
		fHandledMarkerTypes = testMarkerTypes ? getHandledMarkerTypes(element)
				: null;
	}

	private Set<String> getHandledMarkerTypes(IConfigurationElement element) {
		HashSet<String> map = new HashSet<String>(7);
		IConfigurationElement[] children = element
				.getChildren(HANDLED_MARKER_TYPES);
		for (int i = 0; i < children.length; i++) {
			IConfigurationElement[] types = children[i]
					.getChildren(MARKER_TYPE);
			for (int k = 0; k < types.length; k++) {
				String attribute = types[k].getAttribute(ID);
				if (attribute != null) {
					map.add(attribute);
				}
			}
		}
		if (map.isEmpty()) {
			map.add(IScriptModelMarker.DLTK_MODEL_PROBLEM_MARKER);
			map.add(IScriptModelMarker.BUILDPATH_PROBLEM_MARKER);
			map.add(IScriptModelMarker.TASK_MARKER);
		}
		return map;
	}

	public IStatus checkSyntax() {
		IConfigurationElement[] children = fConfigurationElement
				.getChildren(ExpressionTagNames.ENABLEMENT);
		if (children.length > 1) {
			String id = fConfigurationElement.getAttribute(ID);
			return new StatusInfo(IStatus.ERROR,
					Messages.ContributedProcessorDescriptor_4 + id);
		}
		return new StatusInfo(IStatus.OK,
				Messages.ContributedProcessorDescriptor_5);
	}

	private boolean matches(ISourceModule cunit) {
		// if (fRequiredSourceLevel != null) {
		// String current=
		// cunit.getJavaProject().getOption(JavaCore.COMPILER_SOURCE, true);
		// if (JavaModelUtil.isVersionLessThan(current, fRequiredSourceLevel)) {
		// return false;
		// }
		// }

		if (fStatus != null) {
			return fStatus.booleanValue();
		}

		IConfigurationElement[] children = fConfigurationElement
				.getChildren(ExpressionTagNames.ENABLEMENT);
		if (children.length == 1) {
			try {
				ExpressionConverter parser = ExpressionConverter.getDefault();
				Expression expression = parser.perform(children[0]);
				EvaluationContext evalContext = new EvaluationContext(null,
						cunit);
				evalContext.addVariable("compilationUnit", cunit); //$NON-NLS-1$
				IScriptProject javaProject = cunit.getScriptProject();
				String[] natures = javaProject.getProject().getDescription()
						.getNatureIds();
				evalContext.addVariable(
						"projectNatures", Arrays.asList(natures)); //$NON-NLS-1$
				// evalContext.addVariable("sourceLevel",
				// javaProject.getOption(JavaCore.COMPILER_SOURCE, true));
				return expression.evaluate(evalContext) == EvaluationResult.TRUE;
			} catch (CoreException e) {
				PHPUiPlugin.log(e);
			}
			return false;
		}
		fStatus = Boolean.FALSE;
		return false;
	}

	public Object getProcessor(ISourceModule cunit, Class<?> expectedType) {
		if (matches(cunit)) {
			if (fProcessorInstance == null) {
				try {
					Object extension = fConfigurationElement
							.createExecutableExtension(CLASS);
					if (expectedType.isInstance(extension)) {
						fProcessorInstance = extension;
					} else {
						String message = Messages.ContributedProcessorDescriptor_8
								+ fConfigurationElement.getName()
								+ Messages.ContributedProcessorDescriptor_9
								+ expectedType.getName()
								+ "'." + fConfigurationElement.getContributor().getName(); //$NON-NLS-1$
						PHPUiPlugin.log(new Status(IStatus.ERROR,
								PHPUiPlugin.ID, message));
						fStatus = Boolean.FALSE;
						return null;
					}
				} catch (CoreException e) {
					PHPUiPlugin.log(e);
					fStatus = Boolean.FALSE;
					return null;
				}
			}
			return fProcessorInstance;
		}
		return null;
	}

	public boolean canHandleMarkerType(String markerType) {
		return fHandledMarkerTypes == null
				|| fHandledMarkerTypes.contains(markerType);
	}

}
