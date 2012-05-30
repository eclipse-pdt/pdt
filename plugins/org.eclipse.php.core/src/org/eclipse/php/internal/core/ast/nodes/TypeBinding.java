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
package org.eclipse.php.internal.core.ast.nodes;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPTraitType;

public class TypeBinding implements ITypeBinding {

	private IEvaluatedType type;
	private IModelElement[] elements;
	private BindingResolver resolver;
	private IType[] superTypes;
	private ITypeBinding superClass;
	private ITypeBinding[] interfaces;
	private IVariableBinding[] fields;
	private IMethodBinding[] methods;
	private Map<IType, ITypeHierarchy> hierarchy = new HashMap<IType, ITypeHierarchy>();

	/**
	 * Constructs a new TypeBinding.
	 * 
	 * @param resolver
	 * @param type
	 * @param elements
	 */
	public TypeBinding(BindingResolver resolver, IEvaluatedType type,
			IModelElement[] elements) {
		this.resolver = resolver;
		this.type = type;
		if (elements != null && elements.length > 0) {
			final int length = elements.length;
			this.elements = new IModelElement[length];
			System.arraycopy(elements, 0, this.elements, 0, length);
		} else {
			this.elements = new IModelElement[0];
		}
	}

	/**
	 * Constructs a new TypeBinding.
	 * 
	 * @param resolver
	 * @param type
	 * @param element
	 */
	public TypeBinding(BindingResolver resolver, IEvaluatedType type,
			IModelElement element) {
		this.resolver = resolver;
		this.type = type;
		if (element != null) {
			this.elements = new IModelElement[] { element };
		} else {
			this.elements = new IModelElement[0];
		}
	}

	/**
	 * Answer an array type binding using the receiver and the given dimension.
	 * 
	 * <p>
	 * If the receiver is an array binding, then the resulting dimension is the
	 * given dimension plus the dimension of the receiver. Otherwise the
	 * resulting dimension is the given dimension.
	 * </p>
	 * 
	 * @param dimension
	 *            the given dimension
	 * @return an array type binding
	 * @throws IllegalArgumentException
	 *             :
	 *             <ul>
	 *             <li>if the receiver represents the void type</li>
	 *             <li>if the resulting dimensions is lower than one or greater
	 *             than 255</li>
	 *             </ul>
	 */
	public ITypeBinding createArrayType(int dimension) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the binary name of this type binding. The binary name of a class
	 * is defined in the Java Language Specification 3rd edition, section 13.1.
	 * <p>
	 * Note that in some cases, the binary name may be unavailable. This may
	 * happen, for example, for a local type declared in unreachable code.
	 * </p>
	 * 
	 * @return the binary name of this type, or <code>null</code> if the binary
	 *         name is unknown
	 */
	public String getBinaryName() {
		if (isUnknown() || isAmbiguous()) {
			return null;
		}
		return this.elements[0].getHandleIdentifier();
	}

	/**
	 * Returns the binding representing the component type of this array type,
	 * or <code>null</code> if this is not an array type binding. The component
	 * type of an array might be an array type.
	 * <p>
	 * This is subject to change before 3.2 release.
	 * </p>
	 * 
	 * @return the component type binding, or <code>null</code> if this is not
	 *         an array type
	 */
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

	/**
	 * Returns a list of bindings representing all the fields declared as
	 * members of this class or interface type.
	 * 
	 * <p>
	 * These include public, protected, default (package-private) access, and
	 * private fields declared by the class, but excludes inherited fields.
	 * Fields from binary types that reference unresolvable types may not be
	 * included.
	 * </p>
	 * 
	 * <p>
	 * Returns an empty list if the class or interface declares no fields, and
	 * for other kinds of type bindings that do not directly have members.
	 * </p>
	 * 
	 * <p>
	 * The resulting bindings are in no particular order.
	 * </p>
	 * 
	 * @return the list of bindings for the field members of this type, or the
	 *         empty list if this type does not have field members
	 */
	public IVariableBinding[] getDeclaredFields() {
		if (isUnknown()) {
			return new IVariableBinding[0];
		}

		if (fields == null) {
			if (isClass()) {
				List<IVariableBinding> variableBindings = new ArrayList<IVariableBinding>();

				for (IModelElement element : this.elements) {
					IType type = (IType) element;
					try {
						IField[] fields = type.getFields();
						for (int i = 0; i < fields.length; i++) {
							IVariableBinding variableBinding = resolver
									.getVariableBinding(fields[i]);
							if (variableBinding != null) {
								variableBindings.add(variableBinding);
							}
						}
					} catch (ModelException e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
				fields = variableBindings
						.toArray(new IVariableBinding[variableBindings.size()]);
			} else {
				fields = new IVariableBinding[0];
			}
		}
		return fields;
	}

	/**
	 * Returns a list of method bindings representing all the methods and
	 * constructors declared for this class, interface or annotation type.
	 * <p>
	 * These include public, protected, default (package-private) access, and
	 * private methods Synthetic methods and constructors may or may not be
	 * included. Returns an empty list if the class or interface type declares
	 * no methods or constructors, if the annotation type declares no members,
	 * or if this type binding represents some other kind of type binding.
	 * Methods from binary types that reference unresolvable types may not be
	 * included.
	 * </p>
	 * <p>
	 * The resulting bindings are in no particular order.
	 * </p>
	 * 
	 * @return the list of method bindings for the methods and constructors
	 *         declared by this class, interface, or annotation type, or the
	 *         empty list if this type does not declare any methods or
	 *         constructors
	 */
	public IMethodBinding[] getDeclaredMethods() {
		if (isUnknown()) {
			return new IMethodBinding[0];
		}

		if (methods == null) {
			if (isClass()) {
				List<IMethodBinding> methodBindings = new ArrayList<IMethodBinding>();
				for (IModelElement element : this.elements) {
					IType type = (IType) element;
					try {
						IMethod[] methods = type.getMethods();
						if (methods != null) {
							for (int i = 0; i < methods.length; i++) {
								IMethodBinding methodBinding = resolver
										.getMethodBinding(methods[i]);
								methodBindings.add(methodBinding);
							}
						}
					} catch (ModelException e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
				methods = methodBindings
						.toArray(new IMethodBinding[methodBindings.size()]);
			} else {
				methods = new IMethodBinding[0]; // TODO - Implement
				// IMethodBinding
			}
		}
		return methods;
	}

	/**
	 * Returns the declared modifiers for this class or interface binding as
	 * specified in the original source declaration of the class or interface.
	 * The result may not correspond to the modifiers in the compiled binary,
	 * since the compiler may change them (in particular, for inner class
	 * emulation). The <code>getModifiers</code> method should be used if the
	 * compiled modifiers are needed. Returns -1 if this type does not represent
	 * a class or interface.
	 * 
	 * @return the bit-wise or of <code>Modifiers</code> constants
	 * @see Modifiers
	 */
	public int getModifiers() {
		if (isClass()) {
			// element.
		}
		return -1;
	}

	/**
	 * Returns the dimensionality of this array type, or <code>0</code> if this
	 * is not an array type binding.
	 * 
	 * @return the number of dimension of this array type binding, or
	 *         <code>0</code> if this is not an array type
	 */
	public int getDimensions() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Returns the binding representing the element type of this array type, or
	 * <code>null</code> if this is not an array type binding. The element type
	 * of an array is never itself an array type.
	 * 
	 * @return the element type binding, or <code>null</code> if this is not an
	 *         array type
	 */
	public ITypeBinding getElementType() {
		// TODO Auto-generated method stub
		if (null == this.elements || this.elements.length != 1) {
			return null;
		}
		return null;
	}

	/**
	 * Returns a list of type bindings representing the direct superinterfaces
	 * of the class, interface, or enum type represented by this type binding.
	 * <p>
	 * If this type binding represents a class or enum type, the return value is
	 * an array containing type bindings representing all interfaces directly
	 * implemented by this class. The number and order of the interface objects
	 * in the array corresponds to the number and order of the interface names
	 * in the <code>implements</code> clause of the original declaration of this
	 * type.
	 * </p>
	 * <p>
	 * If this type binding represents an interface, the array contains type
	 * bindings representing all interfaces directly extended by this interface.
	 * The number and order of the interface objects in the array corresponds to
	 * the number and order of the interface names in the <code>extends</code>
	 * clause of the original declaration of this interface.
	 * </p>
	 * <p>
	 * If the class or enum implements no interfaces, or the interface extends
	 * no interfaces, or if this type binding represents an array type, a
	 * primitive type, the null type, a type variable, an annotation type, a
	 * wildcard type, or a capture binding, this method returns an array of
	 * length 0.
	 * </p>
	 * 
	 * @return the list of type bindings for the interfaces extended by this
	 *         class or enum, or interfaces extended by this interface, or
	 *         otherwise the empty list
	 */
	public ITypeBinding[] getInterfaces() {
		if (isUnknown()) {
			return new ITypeBinding[0];
		}

		if (this.interfaces == null) {
			IType[] types = getSuperTypes();
			List<ITypeBinding> interfaces = new LinkedList<ITypeBinding>();
			for (IType type : types) {
				try {
					if (PHPFlags.isInterface(type.getFlags())) {
						interfaces.add(resolver.getTypeBinding(type));
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
			this.interfaces = (ITypeBinding[]) interfaces
					.toArray(new ITypeBinding[interfaces.size()]);
		}
		return this.interfaces;
	}

	/**
	 * Returns the unqualified name of the type represented by this binding if
	 * it has one.
	 * <ul>
	 * <li>For top-level types, member types, and local types, the name is the
	 * simple name of the type. Example: <code>"String"</code> or
	 * <code>"Collection"</code>. Note that the type parameters of a generic
	 * type are not included.</li>
	 * <li>For primitive types, the name is the keyword for the primitive type.
	 * Example: <code>"int"</code>.</li>
	 * <li>For the null type, the name is the string "null".</li>
	 * <li>For anonymous classes, which do not have a name, this method returns
	 * an empty string.</li>
	 * <li>For array types, the name is the unqualified name of the component
	 * type (as computed by this method) followed by "[]". Example:
	 * <code>"String[]"</code>. Note that the component type is never an an
	 * anonymous class.</li>
	 * <li>For type variables, the name is just the simple name of the type
	 * variable (type bounds are not included). Example: <code>"X"</code>.</li>
	 * <li>For type bindings that correspond to particular instances of a
	 * generic type arising from a parameterized type reference, the name is the
	 * unqualified name of the erasure type (as computed by this method)
	 * followed by the names (again, as computed by this method) of the type
	 * arguments surrounded by "&lt;&gt;" and separated by ",". Example:
	 * <code>"Collection&lt;String&gt;"</code>.</li>
	 * <li>For type bindings that correspond to particular instances of a
	 * generic type arising from a raw type reference, the name is the
	 * unqualified name of the erasure type (as computed by this method).
	 * Example: <code>"Collection"</code>.</li>
	 * <li>For wildcard types, the name is "?" optionally followed by a single
	 * space followed by the keyword "extends" or "super" followed a single
	 * space followed by the name of the bound (as computed by this method) when
	 * present. Example: <code>"? extends InputStream"</code>.</li>
	 * <li>Capture types do not have a name. For these types, and array types
	 * thereof, this method returns an empty string.</li>
	 * </ul>
	 * 
	 * @return the unqualified name of the type represented by this binding, or
	 *         the empty string if it has none
	 * @see #getQualifiedName()
	 */
	public String getName() {
		return isUnknown() ? null : this.type.getTypeName();
	}

	protected IType[] getSuperTypes() {
		if (superTypes == null) {
			if (elements != null && elements.length > 0) {
				IDLTKSearchScope scope = SearchEngine
						.createSearchScope(elements[0].getScriptProject());

				Set<String> superTypeNames = new HashSet<String>();
				for (IModelElement element : elements) {
					IType type = (IType) element;
					try {
						String[] superClassNames = type.getSuperClasses();
						if (superClassNames != null) {
							for (String name : superClassNames) {
								if (!superTypeNames.contains(name)) {
									superTypeNames.add(name);
								}
							}
						}
					} catch (CoreException e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
				List<IType> typeList = new ArrayList<IType>();
				List<IModelElement> elementList = Arrays.asList(elements);
				for (String superTypeName : superTypeNames) {
					try {
						ISourceModule sourceModule = (ISourceModule) elements[0]
								.getAncestor(IModelElement.SOURCE_MODULE);

						String typeName = PHPModelUtils
								.extractElementName(superTypeName);
						String nameSpace = PHPModelUtils
								.extractNameSapceName(superTypeName);
						Collection<IType> types = resolver
								.getModelAccessCache()
								.getClassesOrInterfaces(sourceModule, typeName,
										nameSpace, null);
						if (types != null) {
							for (IType type : types) {
								if (!elementList.contains(type)) {
									typeList.add(type);
								}
							}
						}
					} catch (ModelException e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
				if (typeList.size() > 0) {
					superTypes = typeList.toArray(new IType[typeList.size()]);
				} else {
					superTypes = new IType[0];
				}
			} else {
				superTypes = new IType[0];
			}
		}
		return superTypes;
	}

	/**
	 * Returns the type binding for the superclass of the type represented by
	 * this class binding.
	 * <p>
	 * If this type binding represents any class other than the class
	 * <code>java.lang.Object</code>, then the type binding for the direct
	 * superclass of this class is returned. If this type binding represents the
	 * class <code>java.lang.Object</code>, then <code>null</code> is returned.
	 * <p>
	 * Loops that ascend the class hierarchy need a suitable termination test.
	 * Rather than test the superclass for <code>null</code>, it is more
	 * transparent to check whether the class is <code>Object</code>, by
	 * comparing whether the class binding is identical to
	 * <code>ast.resolveWellKnownType("java.lang.Object")</code>.
	 * </p>
	 * <p>
	 * If this type binding represents an interface, an array type, a primitive
	 * type, the null type, a type variable, an enum type, an annotation type, a
	 * wildcard type, or a capture binding then <code>null</code> is returned.
	 * </p>
	 * 
	 * @return the superclass of the class represented by this type binding, or
	 *         <code>null</code> if none
	 * @see AST#resolveWellKnownType(String)
	 */
	public ITypeBinding getSuperclass() {
		if (isUnknown()) {
			return null;
		}
		if (superClass == null) {
			IType[] types = getSuperTypes();
			List<IType> superClasses = new LinkedList<IType>();
			for (IType type : types) {
				try {
					if (!PHPFlags.isInterface(type.getFlags())) {
						superClasses.add(type);
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
			superClass = resolver.getTypeBinding(superClasses
					.toArray(new IType[superClasses.size()]));
		}
		return superClass;
	}

	/**
	 * Returns the binding for the type declaration corresponding to this type
	 * binding.
	 * <p>
	 * For parameterized types ({@link #isParameterizedType()}) and most raw
	 * types ({@link #isRawType()}), this method returns the binding for the
	 * corresponding generic type.
	 * </p>
	 * <p>
	 * For raw member types ({@link #isRawType()}, {@link #isMember()}) of a raw
	 * declaring class, the type declaration is a generic or a non-generic type.
	 * </p>
	 * <p>
	 * A different non-generic binding will be returned when one of the
	 * declaring types/methods was parameterized.
	 * </p>
	 * <p>
	 * For other type bindings, this returns the same binding.
	 * </p>
	 * 
	 * @return the type binding
	 */
	public ITypeBinding getTypeDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns whether this type binding represents an array type.
	 * 
	 * @return <code>true</code> if this type binding is for an array type, and
	 *         <code>false</code> otherwise
	 * @see #getElementType()
	 * @see #getDimensions()
	 */
	public boolean isArray() {
		return type.getClass() == MultiTypeType.class;
	}

	/**
	 * Returns whether this type binding represents a class type or a recovered
	 * binding.
	 * 
	 * @return <code>true</code> if this object represents a class or a
	 *         recovered binding, and <code>false</code> otherwise
	 */
	public boolean isClass() {
		if (isUnknown()) {
			return false;
		}
		return type.getClass() == PHPClassType.class;
	}

	/**
	 * Returns whether this type binding represents a class trait or a recovered
	 * binding.
	 * 
	 * @return <code>true</code> if this object represents a trait or a
	 *         recovered binding, and <code>false</code> otherwise
	 */
	public boolean isTrait() {
		if (isUnknown()) {
			return false;
		}
		return type.getClass() == PHPTraitType.class;
	}

	/**
	 * Returns whether this type binding represents an interface type.
	 * <p>
	 * Note that an interface can also be an annotation type.
	 * </p>
	 * 
	 * @return <code>true</code> if this object represents an interface, and
	 *         <code>false</code> otherwise
	 */
	public boolean isInterface() {
		if (isUnknown()) {
			return false;
		}

		boolean result = true;
		for (IModelElement element : elements) {
			IType member = (IType) element;
			try {
				result &= (member.getFlags() & Modifiers.AccInterface) != 0;
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * Returns whether this type binding represents the null type.
	 * <p>
	 * The null type is the type of a <code>NullLiteral</code> node.
	 * </p>
	 * 
	 * @return <code>true</code> if this type binding is for the null type, and
	 *         <code>false</code> otherwise
	 */
	public boolean isNullType() {
		if (type instanceof SimpleType) {
			return ((SimpleType) type).getType() == SimpleType.TYPE_NULL;
		}
		return false;
	}

	/**
	 * Returns whether this type binding represents a primitive type.
	 * <p>
	 * There are nine predefined type bindings to represent the eight primitive
	 * types and <code>void</code>. These have the same names as the primitive
	 * types that they represent, namely boolean, byte, char, short, int, long,
	 * float, and double, and void.
	 * </p>
	 * 
	 * @return <code>true</code> if this type binding is for a primitive type,
	 *         and <code>false</code> otherwise
	 */
	public boolean isPrimitive() {
		return type.getClass() == SimpleType.class && !isNullType();
	}

	/**
	 * Returns whether this type is subtype compatible with the given type.
	 * 
	 * @param type
	 *            the type to check compatibility against
	 * @return <code>true</code> if this type is subtype compatible with the
	 *         given type, and <code>false</code> otherwise
	 * 
	 *         NOTE: if one of the resolved types are not compatible with this
	 *         type <code>false</code> is returned
	 */
	public boolean isSubTypeCompatible(ITypeBinding otherType) {

		if (otherType == null || elements == null || elements.length == 0) {
			return false;
		}

		boolean isSubTypeCompatible = false;
		for (IModelElement element : elements) {
			IType type = (IType) element;
			try {
				if (type.getSuperClasses() == null
						|| type.getSuperClasses().length == 0) {
					return false;
				}

				ITypeHierarchy supertypeHierarchy = hierarchy.get(type);
				if (supertypeHierarchy == null) {
					supertypeHierarchy = type
							.newSupertypeHierarchy(new NullProgressMonitor());
					hierarchy.put(type, supertypeHierarchy);
				}
				IModelElement[] otherElements = ((TypeBinding) otherType).elements;
				if (otherElements != null) {
					for (IModelElement modelElement : otherElements) {
						if (modelElement instanceof IType
								&& supertypeHierarchy
										.contains((IType) modelElement)) {
							isSubTypeCompatible = true;
							break;
						}
					}
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		return isSubTypeCompatible;
	}

	/**
	 * Returns the key for this binding.
	 * <p>
	 * Within a connected cluster of bindings (for example, all bindings
	 * reachable from a given AST), each binding will have a distinct keys. The
	 * keys are generated in a manner that is predictable and as stable as
	 * possible. This last property makes these keys useful for comparing
	 * bindings between disconnected clusters of bindings (for example, the
	 * bindings between the "before" and "after" ASTs of the same compilation
	 * unit).
	 * </p>
	 * <p>
	 * The exact details of how the keys are generated is unspecified. However,
	 * it is a function of the following information:
	 * <ul>
	 * <li>packages - the name of the package (for an unnamed package, some
	 * internal id)</li>
	 * <li>classes or interfaces - the VM name of the type and the key of its
	 * package</li>
	 * <li>array types - the key of the component type and number of dimensions</li>
	 * <li>primitive types - the name of the primitive type</li>
	 * <li>fields - the name of the field and the key of its declaring type</li>
	 * <li>methods - the name of the method, the key of its declaring type, and
	 * the keys of the parameter types</li>
	 * <li>constructors - the key of its declaring class, and the keys of the
	 * parameter types</li>
	 * <li>local variables - the name of the local variable, the index of the
	 * declaring block relative to its parent, the key of its method</li>
	 * <li>local types - the name of the type, the index of the declaring block
	 * relative to its parent, the key of its method</li>
	 * <li>anonymous types - the occurence count of the anonymous type relative
	 * to its declaring type, the key of its declaring type</li>
	 * <li>enum types - treated like classes</li>
	 * <li>annotation types - treated like interfaces</li>
	 * <li>type variables - the name of the type variable and the key of the
	 * generic type or generic method that declares that type variable</li>
	 * <li>wildcard types - the key of the optional wildcard type bound</li>
	 * <li>capture type bindings - the key of the wildcard captured</li>
	 * <li>generic type instances - the key of the generic type and the keys of
	 * the type arguments used to instantiate it, and whether the instance is
	 * explicit (a parameterized type reference) or implicit (a raw type
	 * reference)</li>
	 * <li>generic method instances - the key of the generic method and the keys
	 * of the type arguments used to instantiate it, and whether the instance is
	 * explicit (a parameterized method reference) or implicit (a raw method
	 * reference)</li>
	 * <li>members of generic type instances - the key of the generic type
	 * instance and the key of the corresponding member in the generic type</li>
	 * <li>annotations - the key of the annotated element and the key of the
	 * annotation type</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Note that the key for member value pair bindings is not yet implemented.
	 * This returns <code>null</code> for this kind of bindings.<br>
	 * Recovered bindings have a unique key.
	 * </p>
	 * 
	 * @return the key for this binding
	 */
	public String getKey() {
		if (isUnknown() || isAmbiguous()) {
			return null;
		}
		return elements[0].getHandleIdentifier();
	}

	/**
	 * Returns the kind of bindings this is. That is one of the kind constants:
	 * <code>TYPE</code>, <code>VARIABLE</code>, <code>METHOD</code>, or
	 * <code>MEMBER_VALUE_PAIR</code>.
	 * <p>
	 * Note that additional kinds might be added in the future, so clients
	 * should not assume this list is exhaustive and should program defensively,
	 * e.g. by having a reasonable default in a switch statement.
	 * </p>
	 * 
	 * @return one of the kind constants
	 */
	public int getKind() {
		return IBinding.TYPE;
	}

	/**
	 * Returns the PHP element that corresponds to this binding. Returns
	 * <code>null</code> if this binding has no corresponding PHP element.
	 * <p>
	 * For array types, this method returns the PHP element that corresponds to
	 * the array's element type. For raw and parameterized types, this method
	 * returns the PHP element of the erasure. For annotations, this method
	 * returns the PHP element of the annotation (i.e. an {@link IAnnotation}).
	 * </p>
	 * <p>
	 * Here are the cases where a <code>null</code> should be expected:
	 * <ul>
	 * <li>primitive types, including void</li>
	 * <li>null type</li>
	 * <li>wildcard types</li>
	 * <li>capture types</li>
	 * <li>array types of any of the above</li>
	 * <li>the "length" field of an array type</li>
	 * <li>the default constructor of a source class</li>
	 * <li>the constructor of an anonymous class</li>
	 * <li>member value pairs</li>
	 * </ul>
	 * For all other kind of type, method, variable, annotation and package
	 * bindings, this method returns non-<code>null</code>.
	 * </p>
	 * 
	 * @return the PHP element that corresponds to this binding, or
	 *         <code>null</code> if none
	 * @since 3.1
	 */
	public IModelElement getPHPElement() {
		if (isUnknown() || isAmbiguous()) {
			return null;
		}
		return elements[0];
	}

	/**
	 * Return whether this binding is for something that is deprecated. A
	 * deprecated class, interface, field, method, or constructor is one that is
	 * marked with the 'deprecated' tag in its PHPdoc comment.
	 * 
	 * Note: Currently we return false for all type bindings since we do not
	 * check for the PHPDoc deprecated annotation.
	 * 
	 * @return <code>true</code> if this binding is deprecated, and
	 *         <code>false</code> otherwise (Currently, returns false all the
	 *         time).
	 */
	public boolean isDeprecated() {
		return false;
	}

	/**
	 * There is no special definition of equality for bindings; equality is
	 * simply object identity. Within the context of a single cluster of
	 * bindings, each binding is represented by a distinct object. However,
	 * between different clusters of bindings, the binding objects may or may
	 * not be different; in these cases, the client should compare bindings
	 * using {@link #isEqualTo(IBinding)}, which checks their keys.
	 * 
	 * @param other
	 *            {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	public boolean equals(Object other) {
		if (other == this) {
			// identical binding - equal (key or no key)
			return true;
		}
		if (other == null) {
			// other binding missing
			return false;
		}
		if (!(other instanceof TypeBinding)) {
			return false;
		}
		TypeBinding otherBinding = (TypeBinding) other;
		if (!this.type.equals(otherBinding.type)) {
			return false;
		}
		if (this.elements == null) {
			return otherBinding.elements == null;
		}

		if (elements.length != otherBinding.elements.length) {
			return false;
		}

		for (int i = 0; i < elements.length; i++) {
			boolean hasEqual = false;
			for (int j = 0; j < otherBinding.elements.length; j++) {
				// if found equals, break the inner loop
				if (elements[i].equals(otherBinding.elements[j])) {
					hasEqual = true;
					break;
				}
			}
			// if no equal element found, return false.
			if (!hasEqual) {
				return false;
			}

		}
		return true;

	}

	/*
	 * (non-Java)
	 * 
	 * @see ITypeBinding#isAmbiguous()
	 */
	public boolean isAmbiguous() {
		return !isUnknown() && (elements.length != 1);
	}

	/*
	 * (non-Java)
	 * 
	 * @see ITypeBinding#isAmbiguous()
	 */
	public boolean isUnknown() {
		return this.elements == null && !(this.type instanceof SimpleType);
	}

	public List<IType> getTraitList(boolean isMethod, String classMemberName,
			boolean includeSuper) {
		List<IType> result = new LinkedList<IType>();
		if (this.elements == null || elements.length == 0) {
			return result;
		}
		for (IModelElement type : elements) {
			IType trait = getTrait((IType) type, isMethod, classMemberName);
			if (trait != null) {
				result.add(trait);
			}
		}
		if (includeSuper) {

			for (IModelElement element : elements) {
				IType type = (IType) element;
				try {
					if (type.getSuperClasses() == null
							|| type.getSuperClasses().length == 0
							|| PHPFlags.isTrait(type.getFlags())) {
						return result;
					}

					ITypeHierarchy supertypeHierarchy = hierarchy.get(type);
					if (supertypeHierarchy == null) {
						supertypeHierarchy = type
								.newSupertypeHierarchy(new NullProgressMonitor());
						hierarchy.put(type, supertypeHierarchy);
					}
					IType trait = getTrait(type, isMethod, classMemberName);
					if (trait != null) {
						result.add(trait);
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	private IType getTrait(IType type, boolean isMethod, String classMemberName) {
		if (type != null) {
			try {
				// if (PHPFlags.isTrait(element.getFlags())) {
				// return element;
				// }
				IMember[] members;
				if (isMethod) {
					members = PHPModelUtils.getTypeMethod(type,
							classMemberName, true);
				} else {
					members = PHPModelUtils.getTypeField(type, classMemberName,
							true);
				}
				for (IMember member : members) {
					IType declaringType = member.getDeclaringType();

					if (PHPFlags.isTrait(declaringType.getFlags())) {
						return declaringType;
					}
				}
			} catch (ModelException e) {
			}
		}
		return null;
	}

	public IModelElement[] getPHPElements() {
		return elements;
	}

}
