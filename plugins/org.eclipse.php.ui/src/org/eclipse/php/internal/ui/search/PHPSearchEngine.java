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
package org.eclipse.php.internal.ui.search;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPUserModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.search.decorators.PHPClassConstantDataDecorator;
import org.eclipse.php.internal.ui.search.decorators.PHPClassDataDecorator;
import org.eclipse.php.internal.ui.search.decorators.PHPConstantDataDecorator;
import org.eclipse.php.internal.ui.search.decorators.PHPFunctionDataDecorator;
import org.eclipse.php.internal.ui.util.SearchPattern;
import org.eclipse.search.ui.text.Match;

/**
 * PHPSearchEngine
 * 
 * @author shalom
 */
public class PHPSearchEngine {

	/**
	 * Create a new PHPSearchScope for the given elements.
	 * 
	 * @param elements
	 * @return An IPHPSearchScope.
	 */
	public static IPHPSearchScope createPHPSearchScope(int searchFor, Object[] elements) {
		PHPSearchScope scope = new PHPSearchScope(searchFor);
		for (int i = 0, length = elements.length; i < length; i++) {
			Object element = elements[i];
			if (element != null) {
				try {
					if (element instanceof IProject) {
						scope.add((IProject) element);
					} else if (element instanceof IResource) {
						scope.add((IResource) element);
					}
				} catch (Exception e) {
					// ignore
				}
			}
		}
		return scope;
	}

	/**
	 * Returns an IPHPSearchScope for the workspace. 
	 * The returned type is PHPWorspaceScope.
	 * 
	 * @return	IPHPSearchScope for the workspace.
	 */
	public static IPHPSearchScope createWorkspaceScope(int searchFor) {
		IWorkspace workspace = PHPUiPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		PHPWorspaceScope scope = new PHPWorspaceScope(searchFor);
		for (int i = 0; i < projects.length; i++) {
			try {
				if (projects[i].isAccessible() && projects[i].hasNature(PHPNature.ID)) {
					scope.add(projects[i]);
				}
			} catch (CoreException e) {
				PHPUiPlugin.log(e);
			}
		}
		return scope;
	}

	/**
	 * Perform the search and add any matches to the PHPSearchResult.
	 * 
	 * @param stringPattern
	 * @param scope
	 * @param textResult
	 * @param mainSearchPM
	 */
	public void search(String stringPattern, IPHPSearchScope scope, PHPSearchResult textResult, boolean caseSensitive, IProgressMonitor monitor) {
		// First, add the results from the fully searched projects.
		IProject[] projectsInScope = scope.getFullScopeProjects();
		if (monitor != null && monitor.isCanceled())
			throw new OperationCanceledException();
		if (monitor != null) {
			monitor.beginTask(PHPUIMessages.getString("PHPEngine_searching"), IProgressMonitor.UNKNOWN);
		}
		for (int i = 0; i < projectsInScope.length; i++) {
			if (monitor != null && monitor.isCanceled())
				throw new OperationCanceledException();
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(projectsInScope[i]);
			if (projectModel == null) {
				// The project in scope is not a PHP project
				return;
			}
			PHPUserModel userModel = projectModel.getPHPUserModel();
			PHPClassData[] fClasses = (PHPClassData[]) userModel.getClasses();
			switch (scope.getSearchFor()) {
				case IPHPSearchConstants.CLASS:
					addResultsFor(projectsInScope[i], (PHPClassData[]) userModel.getClasses(), stringPattern, caseSensitive, scope, textResult, monitor);
					break;
				case IPHPSearchConstants.FUNCTION:
					addResultsFor(projectsInScope[i], (PHPFunctionData[]) userModel.getFunctions(), stringPattern, caseSensitive, scope, textResult, monitor);
					// Add matches from the classes functions					
					for (int j = 0; j < fClasses.length; j++) {
						addResultsFor(projectsInScope[i], fClasses[j].getFunctions(), stringPattern, caseSensitive, scope, textResult, monitor);
					}
					break;
				case IPHPSearchConstants.CONSTANT:
					addResultsFor(projectsInScope[i], (PHPConstantData[]) userModel.getConstants(), stringPattern, caseSensitive, scope, textResult, monitor);
//					 Add matches from the classes constants
					for (int j = 0; j < fClasses.length; j++) {
						addResultsFor(projectsInScope[i], fClasses[j].getConsts(), stringPattern, caseSensitive, scope, textResult, monitor);
					}
					break;
				default:
					// DO NOTHING
			}

		}

		// Add the results from any other resources that are spanning to other projects
		IProject[] partialProjects = scope.getPartialScopeProjects();
		for (int i = 0; i < partialProjects.length; i++) {
			if (monitor != null && monitor.isCanceled())
				throw new OperationCanceledException();
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(partialProjects[i]);
			if (projectModel == null) {
				return; // The project in scope is not a PHP project
			}
			PHPUserModel userModel = projectModel.getPHPUserModel();
			IPath[] paths = scope.getPartialResourcesPaths(partialProjects[i]);
			for (int j = 0; j < paths.length; j++) {
				PHPFileData fileData = userModel.getFileData(paths[j].toString());
				if (fileData == null) {
					continue;
				}
				PHPClassData[] fClasses = fileData.getClasses();
				switch (scope.getSearchFor()) {
					case IPHPSearchConstants.CLASS:
						addResultsFor(partialProjects[i], fileData.getClasses(), stringPattern, caseSensitive, scope, textResult, monitor);
						break;
					case IPHPSearchConstants.FUNCTION:
						addResultsFor(partialProjects[i], fileData.getFunctions(), stringPattern, caseSensitive, scope, textResult, monitor);
						// Add matches from the classes functions						
						for (int k = 0; k < fClasses.length; k++) {
							addResultsFor(partialProjects[i], fClasses[k].getFunctions(), stringPattern, caseSensitive, scope, textResult, monitor);
						}
						break;
					case IPHPSearchConstants.CONSTANT:
						addResultsFor(partialProjects[i], fileData.getConstants(), stringPattern, caseSensitive, scope, textResult, monitor);
//						 Add matches from the classes constants
						for (int z = 0; z < fClasses.length; z++) {
							addResultsFor(partialProjects[i], fClasses[z].getConsts(), stringPattern, caseSensitive, scope, textResult, monitor);
						}
						break;
					default:
						// DO NOTHING
				}
			}
		}
		if (monitor != null && !monitor.isCanceled()) {
			monitor.done();
		}
	}

	private void addResultsFor(IProject project, PHPClassData[] classes, String stringPattern, boolean caseSensitive, IPHPSearchScope scope, PHPSearchResult textResult, IProgressMonitor monitor) {
		for (int i = 0; i < classes.length; i++) {
			if (i % 100 == 0) {
				// Check for monitor cancellation every 100 ticks
				if (monitor != null && monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
			}
			PHPClassData classData = classes[i];
			if (SearchPattern.match(stringPattern, classData.getName(), caseSensitive, true)) {
				int start = classData.getUserData().getStopPosition();
				textResult.addMatch(new Match(new PHPClassDataDecorator(classData, project), start, classData.getName().length()));
			}
			monitor.worked(1);
		}
	}

	private void addResultsFor(IProject project, PHPFunctionData[] functions, String stringPattern, boolean caseSensitive, IPHPSearchScope scope, PHPSearchResult textResult, IProgressMonitor monitor) {
		for (int i = 0; i < functions.length; i++) {
			if (i % 100 == 0) {
				// Check for monitor cancellation every 100 ticks
				if (monitor != null && monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
			}
			PHPFunctionData functionData = functions[i];
			if (SearchPattern.match(stringPattern, functionData.getName(), caseSensitive, true)) {
				int start = functionData.getUserData().getStopPosition();
				textResult.addMatch(new Match(new PHPFunctionDataDecorator(functionData, project), start, functionData.getName().length()));
			}
			monitor.worked(1);
		}
	}

	private void addResultsFor(IProject project, PHPConstantData[] constants, String stringPattern, boolean caseSensitive, IPHPSearchScope scope, PHPSearchResult textResult, IProgressMonitor monitor) {
		for (int i = 0; i < constants.length; i++) {
			if (i % 100 == 0) {
				// Check for monitor cancellation every 100 ticks
				if (monitor != null && monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
			}
			PHPConstantData constantData = constants[i];
			if (SearchPattern.match(stringPattern, constantData.getName(), caseSensitive, true)) {
				int start = constantData.getUserData().getStopPosition() + 1; // Shift by 1 (fixed bug #141302)
				textResult.addMatch(new Match(new PHPConstantDataDecorator(constantData, project), start, constantData.getName().length()));
			}
			monitor.worked(1);
		}
	}

	private void addResultsFor(IProject project, PHPClassConstData[] constants, String stringPattern, boolean caseSensitive, IPHPSearchScope scope, PHPSearchResult textResult, IProgressMonitor monitor) {
		for (int i = 0; i < constants.length; i++) {
			if (i % 100 == 0) {
				// Check for monitor cancellation every 100 ticks
				if (monitor != null && monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
			}
			PHPClassConstData constantData = constants[i];
			if (SearchPattern.match(stringPattern, constantData.getName(), caseSensitive, true)) {
				int start = constantData.getUserData().getStopPosition();
				textResult.addMatch(new Match(new PHPClassConstantDataDecorator(constantData, project), start, constantData.getName().length()));
			}
			monitor.worked(1);
		}
	}
}
