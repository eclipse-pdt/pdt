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
package org.eclipse.php.phpunit.model;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.phpunit.PHPUnitMessages;

public class PHPUnitValidator {

	public static final String CLASS_NAME_VALID_REGEXP = "[a-zA-Z_][a-zA-Z0-9_]*"; //$NON-NLS-1$
	private static final IType[] EMPTY_TYPES = new IType[0];

	public static String validateClassName(String className, final IProject project, final StatusInfo status) {
		className = className.trim();
		if (className.length() == 0) {
			if (status != null) {
				status.setError(PHPUnitMessages.PHPUnitValidator_Empty_Class_Name);
			}
			return className;
		}
		final Matcher matcher = Pattern.compile(CLASS_NAME_VALID_REGEXP).matcher(className);
		if (!matcher.matches()) {
			if (status != null) {
				status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Class_Invalid, className));
			}
			return className;
		}
		if (project != null) {

			final IType[] typesByName = getTypesByName(className, project);
			if (typesByName != null && typesByName.length > 0) {
				if (status != null) {
					status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Class_Exists,
							project.getName(), className));
				}
			}
		}
		return matcher.group();
	}

	/**
	 * @param className
	 * @param project
	 * @return
	 */
	private static IType[] getTypesByName(String className, final IProject project) {
		final IScriptProject scriptProject = DLTKCore.create(project);
		IDLTKSearchScope searchScope = SearchEngine.createSearchScope(scriptProject);
		return PHPModelAccess.getDefault().findTypes(className, MatchRule.EXACT, 0,
				Modifiers.AccInterface | Modifiers.AccNameSpace, searchScope, null);
	}

	public static IContainer validateContainer(final Object container) {
		return validateContainer(container, false, null);
	}

	public static IContainer validateContainer(final Object container, final boolean validateProject) {
		return validateContainer(container, validateProject, null);
	}

	public static IContainer validateContainer(final Object container, final boolean validateProject,
			final StatusInfo status) {
		String containerName;
		if (container instanceof String) {
			containerName = (String) container;
		} else if (container instanceof IContainer) {
			final IContainer theContainer = (IContainer) container;
			containerName = theContainer.getFullPath().toOSString();
			if (!theContainer.isAccessible()) {
				if (status != null) {
					status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_Accessible,
							new Object[] { containerName }));
				}
			}
		} else {
			if (status != null) {
				status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_Folder, container));
			}
			return null;
		}

		if (containerName.length() == 0) {
			if (status != null) {
				status.setError(PHPUnitMessages.PHPUnitValidator_Folder_Name_Empty);
			}
			return null;
		}

		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IPath containerPath = new Path(containerName);
		IResource resource = workspaceRoot.findMember(containerPath);
		if (resource == null) {
			if (status != null) {
				status.setError(
						MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Folder_Not_Exists, containerName));
			}
			return null;
		}

		if (!(resource instanceof IContainer)) {
			if (status != null) {
				status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_Folder, containerName));
			}
			return null;
		}

		if (validateProject) {
			if (PHPUnitValidator.validateProject(resource.getProject(), status) == null) {
				return null;
			}

			resource = workspaceRoot.findMember(containerPath);
			if (resource == null || !resource.isAccessible()) {
				if (status != null) {
					status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Folder_Not_Accessible,
							containerName));
				}
				return null;
			}
		}

		return (IContainer) resource;
	}

	public static IModelElement validateElement(final Object element, final IProject project,
			final boolean validateProject, int elementType, final StatusInfo status) {
		if (validateProject && validateProject(project, status) == null) {
			return null;
		}

		if (element instanceof IModelElement) {
			if (project == null) {
				return null;
			}

			IModelElement modelElement = (IModelElement) element;
			IScriptProject scriptProject = modelElement.getScriptProject();
			if (scriptProject.getProject() != project) {
				// validate class name
				IDLTKSearchScope searchScope = SearchEngine.createSearchScope(scriptProject);
				IType[] foundClassElementsToTest = PHPModelAccess.getDefault().findTypes(modelElement.getElementName(),
						MatchRule.EXACT, 0, Modifiers.AccInterface, searchScope, null);
				if (foundClassElementsToTest == null || foundClassElementsToTest.length < 1) {
					if (status != null) {
						status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_In_Project,
								modelElement.getElementName(), project.getName()));
					}
					return null;
				}
				modelElement = foundClassElementsToTest[0];
			}
			return modelElement;
		} else if (element instanceof String) {
			if (project == null) {
				return null;
			}
			final String elementName = ((String) element).trim();
			if (elementName.length() == 0) {
				if (status != null) {
					status.setWarning(PHPUnitMessages.PHPUnitValidator_No_Element);
				}
				return null;
			}
			// validate class name
			IScriptProject scriptProject = DLTKCore.create(project);
			IDLTKSearchScope searchScope = SearchEngine.createSearchScope(scriptProject);

			IModelElement[] foundClassElementsToTest = null;
			if (elementType == IModelElement.TYPE) {
				foundClassElementsToTest = PHPModelAccess.getDefault().findTypes(elementName, MatchRule.EXACT, 0,
						Modifiers.AccInterface, searchScope, null);
			} else {
				foundClassElementsToTest = PHPModelAccess.getDefault().findMethods(elementName, MatchRule.PREFIX,
						Modifiers.AccGlobal, 0, searchScope, null);
			}

			if (foundClassElementsToTest == null || foundClassElementsToTest.length == 0) {
				if (status != null) {
					status.setWarning(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_No_Element_In_Project,
							elementName, project.getName()));
				}
				return null;
			} else if (foundClassElementsToTest.length > 1) {
				if (status != null) {
					status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Multiple_Elements,
							elementName, project.getName()));
					return null;
				}
			}
			return foundClassElementsToTest[0];
		} else {
			if (status != null) {
				status.setWarning(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Unknown_Element, element));
			}
			return null;
		}
	}

	public static String validateFileName(String fileName, final IContainer container, final StatusInfo status) {
		fileName = fileName.trim();
		if (container == null) {
			if (status != null) {
				status.setError(PHPUnitMessages.PHPUnitValidator_No_Container);
			}
			return fileName;
		}
		if (fileName.length() == 0) {
			if (status != null) {
				status.setError(PHPUnitMessages.PHPUnitValidator_No_FileName);
			}
			return fileName;
		}

		if (container.findMember(new Path(fileName)) != null) {
			if (status != null) {
				status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_File_Exists, fileName,
						container.getName()));
			}
			return fileName;
		}

		final IContentType contentType = Platform.getContentTypeManager()
				.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		if (!contentType.isAssociatedWith(fileName)) {
			if (status != null) {
				status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_File_Not_PHP, fileName));
			}
			return fileName;
		}
		return fileName;

	}

	public static IProject validateProject(final Object project) {
		return validateProject(project, null);
	}

	public static IProject validateProject(final Object project, final StatusInfo status) {
		final String projectName;
		final IProject theProject;
		if (project instanceof IProject) {
			theProject = (IProject) project;
			projectName = theProject.getName();
		} else if (project instanceof String) {
			projectName = (String) project;
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			theProject = workspaceRoot.getProject(projectName);
		} else {
			if (status != null) {
				status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_Project, project));
			}
			return null;
		}

		try {

			if (!theProject.isAccessible()) {
				if (status != null) {
					status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Project_Not_Accessible,
							theProject.getName()));
				}
				return null;
			}

			if (!theProject.hasNature(PHPNature.ID)) {
				if (status != null) {
					status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Project_Not_PHP,
							theProject.getName()));
				}
				return null;
			}
		} catch (final CoreException e) {
			if (status != null) {
				status.setError(
						MessageFormat.format(PHPUnitMessages.PHPUnitValidator_No_Project, projectName, e.getMessage()));
			}
		}

		return theProject;

	}

	public static IType[] validateTests(final IType[] tests, final IProject project, final boolean validateProject,
			final StatusInfo status) {
		if (validateProject && validateProject(project, status) == null) {
			return EMPTY_TYPES;
		}

		if (project == null) {
			return EMPTY_TYPES;
		}

		if (tests == null) {
			status.setWarning(
					MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Folder_No_Suites, project.getName()));
			return EMPTY_TYPES;
		}

		if (tests.length == 0) {
			status.setWarning(PHPUnitMessages.PHPUnitValidator_No_Suites);
			return EMPTY_TYPES;
		}

		final PHPUnitSearchEngine searchEngine = new PHPUnitSearchEngine(DLTKCore.create(project));

		for (int i = 0; i < tests.length; ++i) {
			final IResource resource = tests[i].getResource();
			if (resource.getProject() != project) {
				if (status != null) {
					status.setError(MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_Exists,
							tests[i].getElementName(), project.getName()));
				}
				return EMPTY_TYPES;
			}
			if (!searchEngine.isTest(tests[i])) {
				status.setError(
						MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_Test, tests[i].getElementName()));
				return EMPTY_TYPES;
			}
		}
		return tests;
	}
}
