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
package org.eclipse.php.phpunit.model;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.Model;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.providers.PHP5ElementContentProvider;

public class PHPUnitSearchEngine {

	public static int FIND_ELEMENT_CLASS = 1 << 2;
	// public static int FIND_ELEMENT_FUNCTION = 1 << 3;
	public static int FIND_ELEMENT_PHPUNIT_CASE = 1;
	public static int FIND_ELEMENT_PHPUNIT_SUITE = 1 << 1;
	// public static int FIND_ELEMENT_PHPUNIT_TEST = 1 << 6;
	// public static int FIND_OPTION_ALL_MODELS = 1 << 5;
	public static int FIND_OPTION_FIRST_ONLY = 1 << 4;

	public static final String CLASS_CASE = "PHPUnit_Framework_TestCase"; //$NON-NLS-1$
	public static final String CLASS_SUITE = "PHPUnit_Framework_TestSuite"; //$NON-NLS-1$
	public static final String PHPUNIT_BASE = "PHPUnit_Framework_Test"; //$NON-NLS-1$
	public static final String FUNCTION_SUITE = "suite"; //$NON-NLS-1$
	// public static final String INTERFACE_TEST = "PHPUnit_Framework_Test";
	// //$NON-NLS-1$
	// public static final String RETURN_UNKNOWN = "unknown"; //$NON-NLS-1$

	private Map<IType, Set<IType>> typeHierarchyCache = new HashMap<IType, Set<IType>>();
	private IScriptProject project;

	public PHPUnitSearchEngine(IScriptProject project) {
		this.project = project;
	}

	public IType[] findTestCaseBaseClasses(IModelElement elementContainer, boolean listAbstract,
			IProgressMonitor monitor) {
		return findPHPUnitClassesBySupertype(elementContainer, getTestCase(), listAbstract, false, monitor);
	}

	public IType[] findTestSuiteBaseClasses(IModelElement elementContainer, boolean listAbstract,
			IProgressMonitor monitor) {
		return findPHPUnitClassesBySupertype(elementContainer, getTestSuite(), listAbstract, false, monitor);
	}

	public IType[] findTestCaseBaseClasses(IModelElement elementContainer, IType baseClass, boolean listAbstract,
			IProgressMonitor monitor) {
		return findPHPUnitClassesBySupertype(elementContainer, baseClass, listAbstract, false, monitor);
	}

	public IType[] findAllTestCasesAndSuites(IModelElement element, boolean listAbstract, IProgressMonitor monitor) {
		List<IType> result = new LinkedList<IType>();

		if (typeHierarchyCache.isEmpty()) {
			findPHPUnitClassesBySupertype(element, getTestCase(), listAbstract, false, monitor);

			findPHPUnitClassesBySupertype(element, getTestSuite(), listAbstract, false, monitor);
		}

		Collection<Set<IType>> values = typeHierarchyCache.values();
		if (values != null && !values.isEmpty()) {
			for (Set<IType> set : values) {
				result.addAll(set);
			}
		}

		return result.toArray(new IType[result.size()]);
	}

	public IType[] findPHPUnitClassesByTestCase(IModelElement elementContainer, boolean listAbstract, boolean isFirst,
			IProgressMonitor monitor) {
		return findPHPUnitClassesBySupertype(elementContainer, getTestCase(), listAbstract, isFirst, monitor);
	}

	public IType[] findPHPUnitClassesByTestSuite(IModelElement elementContainer, boolean listAbstract, boolean isFirst,
			IProgressMonitor monitor) {
		return findPHPUnitClassesBySupertype(elementContainer, getTestSuite(), listAbstract, isFirst, monitor);
	}

	public IType[] findPHPUnitClassesBySupertype(IModelElement elementContainer, IType superClass, boolean listAbstract,
			boolean isFirst, IProgressMonitor monitor) {

		IType[] subtypes = null;

		if (superClass == null || elementContainer == null) {
			return subtypes;
		}

		IPath parentPath = elementContainer.getPath();
		List<IType> result = new LinkedList<>();
		try {
			IScriptProject scriptProject = elementContainer.getScriptProject();
			if (elementContainer != null && scriptProject.getProject().hasNature(PHPNature.ID)) {

				Set<IType> subClasses = null;
				try {
					subClasses = typeHierarchyCache.get(superClass);
					if (subClasses == null) {

						IType baseClass = getByName(PHPUNIT_BASE);
						ITypeHierarchy hierarchy = baseClass.newTypeHierarchy(scriptProject, monitor);

						if (monitor != null && monitor.isCanceled()) {
							return null;
						}

						IType testCase = getTestCase();
						IType[] caseSubtypes = hierarchy.getAllSubtypes(testCase);

						IType testSuite = getTestSuite();
						IType[] suiteSubtypes = hierarchy.getAllSubtypes(testSuite);

						Set<IType> caseSubtypesSet = new HashSet<>();
						for (IType type : caseSubtypes) {
							caseSubtypesSet.add(type);
						}
						typeHierarchyCache.put(testCase, caseSubtypesSet);

						Set<IType> suiteSubtypesSet = new HashSet<>();
						for (IType type : suiteSubtypes) {
							suiteSubtypesSet.add(type);
						}

						typeHierarchyCache.put(testSuite, suiteSubtypesSet);
					}

					IDLTKSearchScope scope = SearchEngine.createSearchScope(elementContainer);
					subClasses = typeHierarchyCache.get(superClass);

					first: for (IType type : subClasses) {
						int flags = type.getFlags();

						if (listAbstract || !PHPFlags.isAbstract(flags)) {
							if (parentPath.isPrefixOf(type.getPath())) {
								result.add(type);
								if (isFirst) {
									subtypes = result.toArray(new IType[result.size()]);
									return subtypes;
								}
								continue first;
							}

							IModelElement element = type;

							second: do {
								if (scope.encloses(element)) {
									result.add(type);
									break second;
								}
								element = element.getParent();
							} while (element != null && element instanceof IModelElement
									&& !(element instanceof Model));
						}
					}
					if (listAbstract) {
						result.add(superClass);
					}
					subtypes = result.toArray(new IType[result.size()]);
				} catch (ModelException e) {
					PHPUnitPlugin.log(e);
				}
			}
		} catch (CoreException e) {
			PHPUnitPlugin.log(e);
		}
		return subtypes;
	}

	public Set<IType> findTestCaseBaseClassesInWorkspace(final IProgressMonitor monitor) {

		final Set<IType> cache = new HashSet<>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (project.isOpen()) {
				IScriptProject scriptProject = DLTKCore.create(project);
				boolean hasNature = false;
				try {
					hasNature = project.hasNature(PHPNature.ID);
				} catch (CoreException e) {
					PHPUnitPlugin.log(e);
				}
				if (scriptProject != null && scriptProject.isOpen() && hasNature) {
					IType[] foundTestCases = findTestCaseBaseClasses(scriptProject, getTestCase(), false, monitor);
					if (foundTestCases.length > 0) {
						cache.addAll(Arrays.asList(foundTestCases));
					}
				}
			}
		}
		return cache;
	}

	public IType[] findTestCases(IModelElement elementContainer, IProgressMonitor monitor) {
		return findTestCaseBaseClasses(elementContainer, getTestCase(), true, monitor);
	}

	public IType[] findTestSuites(IModelElement elementContainer, IProgressMonitor monitor) {
		return findTestCaseBaseClasses(elementContainer, getTestSuite(), true, monitor);
	}

	public boolean hasCasesOrSuites(IModelElement element, IProgressMonitor monitor) {
		return findPHPUnitClassesBySupertype(element, getTestCase(), false, true, monitor).length > 0
				|| findPHPUnitClassesBySupertype(element, getTestSuite(), false, true, monitor).length > 0;
	}

	private void collectElements(final Object parent, final IProgressMonitor pm, final Set result, final int flags) {
		collectElementsRecursive(parent, pm, result, flags);
	}

	private boolean collectElementsRecursive(final Object parent, final IProgressMonitor pm, final Set result,
			final int flags) {
		if (parent instanceof Collection)
			return collectFromObject(((Collection) parent).toArray(), pm, result, flags);
		if (parent instanceof Object[])
			return collectFromObject((Object[]) parent, pm, result, flags);
		if (parent instanceof IModelElement)
			return collectFromModelElement((IModelElement) parent, pm, result, flags);
		if (parent instanceof IProject)
			return collectFromProject((IProject) parent, pm, result, flags);
		// if (parent instanceof IScriptFolder)
		// return collectFromScriptFolder((IScriptFolder) parent, pm, result,
		// flags);
		if (parent instanceof IContainer)
			return collectFromContainer((IContainer) parent, pm, result, flags);
		if (parent instanceof IFile)
			return collectFromFile((IFile) parent, pm, result, flags);
		if (parent instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) parent).getAdapter(IResource.class);
			if (resource != null && resource instanceof IFile) {
				return collectFromFile((IFile) resource, pm, result, flags);
			}
		}
		return false;
	}

	private boolean collectFromModelElement(IModelElement parent, IProgressMonitor pm, Set result, int flags) {
		boolean isFirts = false;
		if ((flags & PHPUnitSearchEngine.FIND_OPTION_FIRST_ONLY) > 0) {
			isFirts = true;
		}
		if ((flags & FIND_ELEMENT_PHPUNIT_CASE) > 0) {
			IType[] findPHPUnitClassesBySupertype1 = findPHPUnitClassesBySupertype(parent, getTestCase(), false,
					isFirts, new SubProgressMonitor(pm, IProgressMonitor.UNKNOWN));
			if (findPHPUnitClassesBySupertype1 != null)
				result.addAll(Arrays.asList(findPHPUnitClassesBySupertype1));
		}

		if ((flags & FIND_ELEMENT_PHPUNIT_SUITE) > 0) {
			IType[] findPHPUnitClassesBySupertype2 = findPHPUnitClassesBySupertype(parent, getTestSuite(), false,
					isFirts, new SubProgressMonitor(pm, IProgressMonitor.UNKNOWN));
			if (findPHPUnitClassesBySupertype2 != null)
				result.addAll(Arrays.asList(findPHPUnitClassesBySupertype2));
		}
		return false;

	}

	private boolean collectFromContainer(final IContainer container, final IProgressMonitor pm, final Set result,
			final int flags) {
		final ITreeContentProvider provider = new PHP5ElementContentProvider();
		return collectElementsRecursive(provider.getChildren(container), pm, result, flags);
	}

	private boolean collectFromFile(final IFile file, final IProgressMonitor pm, final Set result, final int flags) {
		return collectElementsRecursive(DLTKCore.create(file), pm, result, flags);
	}

	private boolean collectFromObject(final Object[] items, final IProgressMonitor pm, final Set result,
			final int flags) {

		if (items == null || items.length == 0) {
			return false;
		}

		final int nItems = items.length;
		final IProgressMonitor ipm = new SubProgressMonitor(pm, 1);
		boolean r = false;
		for (int i = 0; i < nItems; ++i) {
			r |= collectElementsRecursive(items[i], ipm, result, flags);
			if ((flags & FIND_OPTION_FIRST_ONLY) > 0 && r)
				return true;
		}
		return r;
	}

	private boolean collectFromProject(final IProject project, final IProgressMonitor pm, final Set result,
			final int flags) {
		return collectElementsRecursive(DLTKCore.create(project), pm, result, flags);
	}

	private void doFindElements(final Object[] parents, final Set<IType> result, final IProgressMonitor pm,
			final int flags) {

		if (parents != null && parents.length > 0) {
			for (int i = 0; i < parents.length; i++) {
				collectElements(parents[i], pm, result, flags);
			}
		}

	}

	public IType[] findElements(final Object[] parents, final int flags, IProgressMonitor pm)
			throws InvocationTargetException, InterruptedException {
		final Set<IType> result = new HashSet<>();
		if (parents != null && parents.length > 0) {
			doFindElements(parents, result, pm, flags);
		}
		return result.toArray(new IType[result.size()]);
	}

	public boolean hasSuiteMethod(final IType classData) {
		if (classData == null) {
			return false;
		}

		IMethod[] functions;
		try {
			functions = classData.getMethods();
			IMethod function;
			for (int i = 0; i < functions.length; ++i) {
				function = functions[i];
				if (function.getElementName().compareToIgnoreCase(FUNCTION_SUITE) == 0) {
					return true;
				}
			}
		} catch (ModelException e) {
			PHPUnitPlugin.log(e);
		}
		return false;
	}

	public boolean hasTestCaseClass() {
		return getTestCase() != null;
	}

	public boolean isCase(IType classData) {
		return isSubOf(classData, getTestCase());
	}

	public boolean isSuite(IType classData) {
		return isSubOf(classData, getTestSuite());
	}

	public boolean isTest(final IType classData) {
		return isSuite(classData) || isCase(classData) || hasSuiteMethod(classData);
	}

	private IType getTestCase() {
		return getByName(CLASS_CASE);
	}

	private IType getTestSuite() {
		return getByName(CLASS_SUITE);
	}

	private IType getByName(String elementName) {
		if (elementName == null || elementName.length() == 0) {
			return null;
		}

		IType[] classes = PhpModelAccess.getDefault().findTypes(elementName, MatchRule.EXACT, 0, 0,
				SearchEngine.createSearchScope(project), null);

		if (classes != null && classes.length > 0) {
			return classes[0];
		}

		return null;
	}

	/**
	 * @param classData
	 * @param superClass
	 * @return
	 */
	private boolean isSubOf(final IType classData, IType superClass) {
		if (classData == null || superClass == null) {
			return false;
		}
		try {
			if (Flags.isAbstract(classData.getFlags())) {
				return false;
			}
			Set<IType> subclasses = typeHierarchyCache.get(superClass);

			if (subclasses == null) {
				findPHPUnitClassesBySupertype(project, superClass, true, false, null);
				subclasses = typeHierarchyCache.get(superClass);
			}
			return subclasses.contains(classData);
		} catch (ModelException e) {
			PHPUnitPlugin.log(e);
		}
		return false;
	}

}
