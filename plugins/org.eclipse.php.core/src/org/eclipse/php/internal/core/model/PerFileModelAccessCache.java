package org.eclipse.php.internal.core.model;

import java.util.*;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This class can be used for caching model access results during a sequence of
 * processes that run on the same file at a time when model updates are
 * impossible. Each method first searches for all elements using a '*' pattern,
 * then it caches results for a subsequent queries. This class is not thread
 * safe.
 * 
 * @author Michael
 */
public class PerFileModelAccessCache implements IModelAccessCache {

	private ISourceModule sourceModule;
	private Map<IType, ITypeHierarchy> hierarchyCache = Collections
			.synchronizedMap(new HashMap<IType, ITypeHierarchy>());
	private Map<String, Collection<IMethod>> globalFunctionsCache;
	private Map<String, Collection<IType>> allTypesCache;
	private Map<String, Collection<IType>> allTraitsCache;
	private ReferenceTree fileHierarchy;

	/**
	 * Constructs new cache
	 * 
	 * @param sourceModule
	 *            Current file
	 */
	public PerFileModelAccessCache(ISourceModule sourceModule) {
		this.sourceModule = sourceModule;
	}

	public ISourceModule getSourceModule() {
		return sourceModule;
	}

	public ITypeHierarchy getSuperTypeHierarchy(IType type,
			IProgressMonitor monitor) throws ModelException {

		ITypeHierarchy hierarchy = hierarchyCache.get(type);
		if (hierarchy == null) {
			hierarchy = type.newSupertypeHierarchy(monitor);
			hierarchyCache.put(type, hierarchy);
		}
		return hierarchy;
	}

	/**
	 * Analyzes file dependences, and builds tree of all source modules, which
	 * are referenced by the given source module.
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param monitor
	 *            Progress monitor
	 */
	public ReferenceTree getFileHierarchy(ISourceModule sourceModule,
			IProgressMonitor monitor) {

		if (!this.sourceModule.equals(sourceModule)) {
			// Invoke a new search, since we only cache for the original file in
			// this class:
			return FileNetworkUtility.buildReferencedFilesTree(sourceModule,
					monitor);
		}
		if (fileHierarchy == null) {
			fileHierarchy = FileNetworkUtility.buildReferencedFilesTree(
					sourceModule, monitor);
		}
		return fileHierarchy;
	}

	/**
	 * Filters given set of element according to a file network
	 * 
	 * @param sourceModule
	 *            Current file
	 * @param elements
	 *            Elements set
	 * @param monitor
	 *            Progress monitor
	 * @return
	 */
	private <T extends IModelElement> Collection<T> filterElements(
			ISourceModule sourceModule, Collection<T> elements,
			IProgressMonitor monitor) {

		if (elements == null) {
			return null;
		}

		// If it's just one element - return it
		if (elements.size() == 1) {
			return elements;
		}

		List<T> filteredElements = new LinkedList<T>();
		// If some of elements belong to current file return just it:
		for (T element : elements) {
			if (sourceModule.equals(element.getOpenable())) {
				filteredElements.add(element);
			}
		}
		if (filteredElements.size() > 0) {
			return filteredElements;
		}

		return PHPModelUtils.fileNetworkFilter(sourceModule, elements, this,
				monitor);
	}

	/**
	 * Returns cached result of a function search, or invokes a new search query
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param functionName
	 *            The name of the global function
	 * @param monitor
	 *            Progress monitor
	 * @return a collection of functions according to a given name, or
	 *         <code>null</code> if not found
	 */
	public Collection<IMethod> getGlobalFunctions(ISourceModule sourceModule,
			String functionName, IProgressMonitor monitor) {

		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=342465
		if (functionName == null) {
			return new ArrayList<IMethod>();
		}
		Collection<IMethod> functions;

		if (!this.sourceModule.equals(sourceModule)) {
			// Invoke a new search, since we only cache for the original file in
			// this class:
			IScriptProject scriptProject = sourceModule.getScriptProject();
			IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			functions = Arrays.asList(PhpModelAccess.getDefault().findMethods(
					functionName, MatchRule.EXACT, Modifiers.AccGlobal, 0,
					scope, monitor));

		} else {
			functionName = functionName.toLowerCase();

			if (globalFunctionsCache == null) {
				globalFunctionsCache = Collections
						.synchronizedMap(new HashMap<String, Collection<IMethod>>());

				IScriptProject scriptProject = sourceModule.getScriptProject();
				IDLTKSearchScope scope = SearchEngine
						.createSearchScope(scriptProject);

				IMethod[] allFunctions = PhpModelAccess.getDefault()
						.findMethods(null, MatchRule.PREFIX,
								Modifiers.AccGlobal, 0, scope, monitor);
				for (IMethod function : allFunctions) {
					String elementName = function.getElementName()
							.toLowerCase();
					Collection<IMethod> funcList = globalFunctionsCache
							.get(elementName);
					if (funcList == null) {
						funcList = new LinkedList<IMethod>();
						globalFunctionsCache.put(elementName, funcList);
					}
					funcList.add(function);
				}
			}
			functions = globalFunctionsCache.get(functionName);
		}
		return filterElements(sourceModule, functions, monitor);
	}

	/**
	 * Returns cached result of a type search, or invokes a new search query
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param typeName
	 *            The name of the type (class, interface or namespace)
	 * @param monitor
	 *            Progress monitor
	 * @return a collection of types according to a given name, or
	 *         <code>null</code> if not found
	 */
	public Collection<IType> getTypes(ISourceModule sourceModule,
			String typeName, String namespaceName, IProgressMonitor monitor) {

		Collection<IType> types;

		if (!this.sourceModule.equals(sourceModule)) {
			// Invoke a new search, since we only cache for the original file in
			// this class:
			IScriptProject scriptProject = sourceModule.getScriptProject();
			IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			types = Arrays.asList(PhpModelAccess.getDefault()
					.findTypes(namespaceName, typeName, MatchRule.EXACT, 0, 0,
							scope, null));

		} else {
			typeName = typeName.toLowerCase();

			if (allTypesCache == null) {
				allTypesCache = Collections
						.synchronizedMap(new HashMap<String, Collection<IType>>());

				IScriptProject scriptProject = sourceModule.getScriptProject();
				IDLTKSearchScope scope = SearchEngine
						.createSearchScope(scriptProject);

				IType[] allTypes = PhpModelAccess.getDefault().findTypes(null,
						MatchRule.PREFIX, 0, 0, scope, null);
				for (IType type : allTypes) {
					String elementName = type.getTypeQualifiedName()
							.toLowerCase();
					Collection<IType> typesList;
					typesList = allTypesCache.get(elementName);
					if (typesList == null) {
						typesList = new LinkedList<IType>();
						allTypesCache.put(elementName, typesList);
					}
					typesList.add(type);
				}
			}

			// if the namespace is not blank, append it to the key.
			StringBuffer key = new StringBuffer();
			if (namespaceName != null && !"".equals(namespaceName.trim())) { //$NON-NLS-1$
				String nameSpace = namespaceName;
				if (namespaceName.startsWith("\\") //$NON-NLS-1$
						|| namespaceName.startsWith("/")) { //$NON-NLS-1$
					nameSpace = namespaceName.substring(1);
				}
				if (nameSpace.length() > 0) {
					key.append(nameSpace.toLowerCase()).append("$"); //$NON-NLS-1$
				}
			}
			key.append(typeName);

			types = allTypesCache.get(key.toString());
		}
		return filterElements(sourceModule, types, monitor);
	}

	/**
	 * Returns cached result of a trait search, or invokes a new search query
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param typeName
	 *            The name of the trait
	 * @param monitor
	 *            Progress monitor
	 * @return a collection of traits according to a given name, or
	 *         <code>null</code> if not found
	 */
	public Collection<IType> getTraits(ISourceModule sourceModule,
			String typeName, String namespaceName, IProgressMonitor monitor) {

		Collection<IType> types;

		if (!this.sourceModule.equals(sourceModule)) {
			// Invoke a new search, since we only cache for the original file in
			// this class:
			IScriptProject scriptProject = sourceModule.getScriptProject();
			IDLTKSearchScope scope = SearchEngine
					.createSearchScope(scriptProject);
			types = Arrays.asList(PhpModelAccess.getDefault().findTraits(
					typeName, MatchRule.EXACT, 0, 0, scope, null));

		} else {
			typeName = typeName.toLowerCase();

			if (allTraitsCache == null) {
				allTraitsCache = Collections
						.synchronizedMap(new HashMap<String, Collection<IType>>());

				IScriptProject scriptProject = sourceModule.getScriptProject();
				IDLTKSearchScope scope = SearchEngine
						.createSearchScope(scriptProject);

				IType[] allTypes = PhpModelAccess.getDefault().findTraits(null,
						MatchRule.PREFIX, 0, 0, scope, null);
				for (IType type : allTypes) {
					String elementName = type.getTypeQualifiedName()
							.toLowerCase();
					Collection<IType> typesList = allTraitsCache
							.get(elementName);
					if (typesList == null) {
						typesList = new LinkedList<IType>();
						allTraitsCache.put(elementName, typesList);
					}
					typesList.add(type);
				}
			}

			// if the namespace is not blank, append it to the key.
			StringBuffer key = new StringBuffer();
			if (namespaceName != null && !"".equals(namespaceName.trim())) { //$NON-NLS-1$
				String nameSpace = namespaceName;
				if (namespaceName.startsWith("\\") //$NON-NLS-1$
						|| namespaceName.startsWith("/")) { //$NON-NLS-1$
					nameSpace = namespaceName.substring(1);
				}
				if (nameSpace.length() > 0) {
					key.append(nameSpace.toLowerCase()).append("$"); //$NON-NLS-1$
				}
			}
			key.append(typeName);

			types = allTraitsCache.get(key.toString());
		}
		return filterElements(sourceModule, types, monitor);
	}

	/**
	 * Searches for classes by name
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param name
	 *            Class name
	 * @param monitor
	 *            Progress monitor
	 * @return classes collection if found, otherwise <code>null</code>
	 * @throws ModelException
	 */
	public Collection<IType> getClasses(ISourceModule sourceModule,
			String name, String namespaceName, IProgressMonitor monitor)
			throws ModelException {
		Collection<IType> allTypes = getTypes(sourceModule, name,
				namespaceName, monitor);
		if (allTypes == null) {
			return null;
		}
		Collection<IType> result = new LinkedList<IType>();
		for (IType type : allTypes) {
			if (PHPFlags.isClass(type.getFlags())) {
				result.add(type);
			}
		}
		return result;
	}

	/**
	 * Searches for interfaces by name
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param name
	 *            Interface name
	 * @param monitor
	 *            Progress monitor
	 * @return interfaces collection if found, otherwise <code>null</code>
	 * @throws ModelException
	 */
	public Collection<IType> getInterfaces(ISourceModule sourceModule,
			String name, String namespaceName, IProgressMonitor monitor)
			throws ModelException {
		Collection<IType> allTypes = getTypes(sourceModule, name,
				namespaceName, monitor);
		if (allTypes == null) {
			return null;
		}
		Collection<IType> result = new LinkedList<IType>();
		for (IType type : allTypes) {
			if (PHPFlags.isInterface(type.getFlags())) {
				result.add(type);
			}
		}
		return result;
	}

	/**
	 * Searches for classes or interfaces by name
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param name
	 *            Class name
	 * @param monitor
	 *            Progress monitor
	 * @return classes collection if found, otherwise <code>null</code>
	 * @throws ModelException
	 */
	public Collection<IType> getClassesOrInterfaces(ISourceModule sourceModule,
			String name, String namespaceName, IProgressMonitor monitor)
			throws ModelException {
		Collection<IType> allTypes = getTypes(sourceModule, name,
				namespaceName, monitor);
		if (allTypes == null) {
			return null;
		}
		Collection<IType> result = new LinkedList<IType>();
		for (IType type : allTypes) {
			if (!PHPFlags.isNamespace(type.getFlags())) {
				result.add(type);
			}
		}
		return result;
	}

	/**
	 * Searches for name-spaces by name
	 * 
	 * @param sourceModule
	 *            Current source module
	 * @param name
	 *            Name-space name
	 * @param monitor
	 *            Progress monitor
	 * @return name-spaces collection if found, otherwise <code>null</code>
	 * @throws ModelException
	 */
	public Collection<IType> getNamespaces(ISourceModule sourceModule,
			String name, IProgressMonitor monitor) throws ModelException {
		Collection<IType> allTypes = getTypes(sourceModule, name, null, monitor);
		if (allTypes == null) {
			return null;
		}
		Collection<IType> result = new LinkedList<IType>();
		for (IType type : allTypes) {
			if (PHPFlags.isNamespace(type.getFlags())) {
				result.add(type);
			}
		}
		return result;
	}
}
