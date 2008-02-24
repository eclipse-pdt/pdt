package org.eclipse.php.internal.core.ast.nodes;

/**
 * A method binding represents a method or constructor of a class or interface.
 * Method bindings usually correspond directly to method or
 * constructor declarations found in the source code.
 * 
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 *
 * @see ITypeBinding#getDeclaredMethods()
 * @since 2.0
 */
public interface IFunctionBinding extends IBinding {
	
	/**
	 * Returns the name of the method declared in this binding. The method name
	 * is always a simple identifier. The name of a constructor is always the
	 * same as the declared name of its declaring class.
	 * 
	 * @return the name of this method, or the declared name of this
	 *   constructor's declaring class
	 */
	public String getName();
	
	/**
	 * Returns a list of type bindings representing the formal parameter types,
	 * in declaration order, of this method or constructor. Returns an array of
	 * length 0 if this method or constructor does not takes any parameters.
	 * <p>
	 * Note that the binding for the last parameter type of a vararg method
	 * declaration like <code>void fun(Foo... args)</code> is always for
	 * an array type (i.e., <code>Foo[]</code>) reflecting the the way varargs
	 * get compiled. However, the type binding obtained directly from
	 * the <code>SingleVariableDeclaration</code> for the vararg parameter
	 * is always for the type as written; i.e., the type binding for
	 * <code>Foo</code>.
	 * </p>
	 * <p>
	 * Note: The result does not include synthetic parameters introduced by
	 * inner class emulation.
	 * </p>
	 * 
	 * @return a (possibly empty) list of type bindings for the formal
	 *   parameters of this method or constructor
	 */
	public ITypeBinding[] getParameterTypes();

	/**
	 * Returns the binding for the return type of this method. Returns the
	 * special primitive <code>void</code> return type for constructors.
	 * 
	 * @return the binding for the return type of this method, or the
	 *    <code>void</code> return type for constructors
	 */
	public ITypeBinding getReturnType();

	/**
	 * Returns a list of type bindings representing the types of the exceptions thrown
	 * by this method or constructor. Returns an array of length 0 if this method
	 * throws no exceptions. The resulting types are in no particular order.
	 * 
	 * @return a list of type bindings for exceptions
	 *   thrown by this method or constructor
	 */
	public ITypeBinding[] getExceptionTypes();


	/**
	 * Returns whether this is a variable arity method.
	 * <p>
	 * Note: Variable arity ("varargs") methods were added in JLS3.
	 * </p>
	 * 
	 * @return <code>true</code> if this is a variable arity method,
	 *    and <code>false</code> otherwise
	 * @since 3.1
	 */ 
	public boolean isVarargs();
	
	
	/**
	 * Returns the resolved default value of an annotation type member, 
	 * or <code>null</code> if the member has no default value, or if this
	 * is not the binding for an annotation type member.
	 * <p>
	 * Resolved values are represented as follows (same as for
	 * {@link IMemberValuePairBinding#getValue()}):
	 * <ul>
	 * <li>Primitive type - the equivalent boxed object</li>
	 * <li>java.lang.Class - the <code>ITypeBinding</code> for the class object</li>
	 * <li>java.lang.String - the string value itself</li>
	 * <li>enum type - the <code>IVariableBinding</code> for the enum constant</li>
	 * <li>annotation type - an <code>IAnnotationBinding</code></li>
	 * <li>array type - an <code>Object[]</code> whose elements are as per above
	 * (the language only allows single dimensional arrays in annotations)</li>
	 * </ul>
	 * 
	 * @return the default value of this annotation type member, or <code>null</code>
	 * if none or not applicable
	 * @since 3.2
	 */
	public Object getDefaultValue();
	
	/**
	 * Returns whether this is the binding for an annotation type member.
	 * 
	 * @return <code>true</code> iff this is the binding for an annotation type member
	 *         and <code>false</code> otherwise
	 * @since 3.2
	 */
	public boolean isAnnotationMember();
		
}
