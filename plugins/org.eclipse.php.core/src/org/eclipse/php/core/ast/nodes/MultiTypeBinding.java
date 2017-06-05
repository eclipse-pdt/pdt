/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
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
package org.eclipse.php.core.ast.nodes;

import java.util.*;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class MultiTypeBinding implements ITypeBinding {
	private final static IVariableBinding[] NO_VARIABLES = new IVariableBinding[0];
	private final static IMethodBinding[] NO_METHODS = new IMethodBinding[0];
	private final ITypeBinding[] subtypes;
	private final DefaultBindingResolver resolver;
	private IMethodBinding[] importedMethods;
	private ITypeBinding[] subTypes;
	private IVariableBinding[] fields;
	private IMethodBinding[] methods;

	public MultiTypeBinding(DefaultBindingResolver resolver, ITypeBinding[] subtypes) {
		this.subtypes = subtypes;
		this.resolver = resolver;
	}

	@Override
	public int getKind() {
		return IBinding.TYPE;
	}

	@Override
	public boolean isDeprecated() {
		for (ITypeBinding binding : subtypes) {
			if (binding.isDeprecated()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IModelElement getPHPElement() {
		IModelElement element;
		for (ITypeBinding binding : subTypes) {
			element = binding.getPHPElement();
			if (element != null) {
				return element;
			}
		}
		return null;
	}

	@Override
	public String getKey() {
		StringBuilder sb = new StringBuilder("mutli_");
		for (ITypeBinding binding : subTypes) {
			sb.append(binding.getKey());
		}
		return null;
	}

	@Override
	public ITypeBinding createArrayType(int dimension) {
		return null;
	}

	@Override
	public String getBinaryName() {
		return null;
	}

	@Override
	public ITypeBinding getComponentType() {
		if (!isArray()) {
			return null;
		}
		// TODO - This should be implemented as soon as the we will be able to
		// identify the types that
		// the array is holding.
		// Once we have that, we can return a TypeBinding or a
		// CompositeTypeBinding for multiple types array.
		return null;
	}

	@Override
	public IVariableBinding[] getDeclaredFields() {

		if (fields == null) {
			if (isUnknown()) {
				fields = NO_VARIABLES;
			} else {
				Set<IVariableBinding> tmp = new HashSet<>();
				for (ITypeBinding binging : subTypes) {
					tmp.addAll(Arrays.asList(binging.getDeclaredFields()));
				}
				fields = tmp.toArray(NO_VARIABLES);
			}
		}

		return fields;
	}

	@Override
	public IMethodBinding[] getDeclaredMethods() {
		if (methods == null) {
			if (isUnknown()) {
				methods = NO_METHODS;
			} else {
				Set<IMethodBinding> tmp = new HashSet<>();
				for (ITypeBinding binging : subTypes) {
					tmp.addAll(Arrays.asList(binging.getDeclaredMethods()));
				}
				methods = tmp.toArray(NO_METHODS);
			}
		}
		return methods;
	}

	@Override
	public int getModifiers() {
		int mods = 0;
		for (ITypeBinding type : subTypes) {
			mods = mods | type.getModifiers();
		}
		return 0;
	}

	@Override
	public int getDimensions() {
		return 0;
	}

	@Override
	public ITypeBinding getElementType() {
		return null;
	}

	@Override
	public ITypeBinding[] getInterfaces() {
		List<ITypeBinding> list = new LinkedList<>();
		for (ITypeBinding t : subtypes) {
			list.addAll(Arrays.asList(t.getInterfaces()));
		}
		return list.toArray(new ITypeBinding[0]);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public IEvaluatedType getEvaluatedType() {
		return subtypes[0].getEvaluatedType();
	}

	@Override
	public ITypeBinding getSuperclass() {
		List<ITypeBinding> list = new LinkedList<>();
		for (ITypeBinding t : subtypes) {
			ITypeBinding s = t.getSuperclass();
			if (s != null && !s.isUnknown()) {
				list.add(s);
			}
		}
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			return new MultiTypeBinding(resolver, list.toArray(new ITypeBinding[0]));
		}
		return null;
	}

	@Override
	public ITypeBinding getTypeDeclaration() {
		return subtypes[0].getTypeDeclaration();
	}

	@Override
	public boolean isArray() {
		for (ITypeBinding t : subtypes) {
			if (t.isArray()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isClass() {
		for (ITypeBinding t : subtypes) {
			if (t.isClass()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isTrait() {
		for (ITypeBinding t : subtypes) {
			if (t.isTrait()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInterface() {
		for (ITypeBinding t : subtypes) {
			if (t.isInterface()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isNullType() {
		for (ITypeBinding t : subtypes) {
			if (t.isNullType()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPrimitive() {
		for (ITypeBinding t : subtypes) {
			if (t.isPrimitive()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSubTypeCompatible(ITypeBinding type) {
		for (ITypeBinding t : subtypes) {
			if (t.isSubTypeCompatible(type)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAmbiguous() {
		return true;
	}

	@Override
	public boolean isUnknown() {
		return false;
	}

	@Override
	public List<IType> getTraitList(boolean isMethod, String classMemberName, boolean includeSuper) {
		List<IType> list = new LinkedList<>();
		for (ITypeBinding t : subtypes) {
			list.addAll(t.getTraitList(isMethod, classMemberName, includeSuper));
		}
		return list;
	}

	@Override
	public IModelElement[] getPHPElements() {
		List<IModelElement> list = new LinkedList<>();
		for (ITypeBinding t : subtypes) {
			list.addAll(Arrays.asList(t.getPHPElements()));
		}
		return list.toArray(new IModelElement[0]);
	}

	@Override
	public IMethodBinding[] getImportedMethods() {
		if (importedMethods == null) {
			if (isUnknown()) {
				importedMethods = NO_METHODS;
			} else {
				Set<IMethodBinding> tmp = new HashSet<>();
				for (ITypeBinding binging : subTypes) {
					tmp.addAll(Arrays.asList(binging.getImportedMethods()));
				}
				importedMethods = tmp.toArray(NO_METHODS);
			}
		}

		return importedMethods;
	}
}
