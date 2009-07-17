package org.eclipse.php.internal.core.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class ModelAccess implements IModelAccess {

	private static IModelAccess instance;

	public static IModelAccess getDefault() {
		if (instance == null) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(
							PHPCorePlugin.ID + ".modelAccess");
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("modelAccess")) {
					try {
						instance = (IModelAccess) element
								.createExecutableExtension("class");
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}

			if (instance == null) {
				instance = new ModelAccess();
			}
		}
		return instance;
	}

	public IField[] findFields(String name, MatchRule matchRule, int flags,
			IDLTKSearchScope scope) {
		return null;
	}

	public IField[] findFieldsInTypeHierarchy(IType type, String name,
			MatchRule matchRule, int flags, IProgressMonitor monitor) {
		return null;
	}

	public IMethod[] findFunctions(String name, MatchRule matchRule, int flags,
			IDLTKSearchScope scope) {
		return null;
	}

	public IField[] findIncludes(String name, IDLTKSearchScope scope) {
		return null;
	}

	public IMethod[] findMethodsInTypeHierarchy(IType type, String name,
			MatchRule matchRule, int flags, IProgressMonitor monitor) {
		return null;
	}

	public IType[] findTypes(String name, MatchRule matchRule, int flags,
			IDLTKSearchScope scope) {
		return null;
	}
}
