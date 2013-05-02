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
package org.eclipse.php.internal.ui.viewsupport;

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.corext.util.Strings;
import org.eclipse.php.internal.ui.util.PHPElementImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * Label provider to render bindings in viewers.
 * 
 * @since 3.1
 */
public class BindingLabelProvider extends LabelProvider {

	private static int getAdornmentFlags(IBinding binding) {
		int adornments = 0;
		if (binding instanceof IMethodBinding
				&& ((IMethodBinding) binding).isConstructor())
			adornments |= PHPElementImageDescriptor.CONSTRUCTOR;
		final int modifiers = binding.getModifiers();
		if (PHPFlags.isAbstract(modifiers))
			adornments |= PHPElementImageDescriptor.ABSTRACT;
		if (PHPFlags.isFinal(modifiers))
			adornments |= PHPElementImageDescriptor.FINAL;
		// if (PHPFlags.isSynchronized(modifiers))
		// adornments|= PHPElementImageDescriptor.SYNCHRONIZED;
		if (PHPFlags.isStatic(modifiers))
			adornments |= PHPElementImageDescriptor.STATIC;
		if (binding.isDeprecated())
			adornments |= PHPElementImageDescriptor.DEPRECATED;
		// if (binding instanceof IVariableBinding && ((IVariableBinding)
		// binding).isField()) {
		// if (PHPFlags.isTransient(modifiers))
		// adornments|= PHPElementImageDescriptor.TRANSIENT;
		// if (PHPFlags.isVolatile(modifiers))
		// adornments|= PHPElementImageDescriptor.VOLATILE;
		// }
		return adornments;
	}

	private static ImageDescriptor getBaseImageDescriptor(IBinding binding,
			int flags) {
		if (binding instanceof ITypeBinding) {
			ITypeBinding typeBinding = (ITypeBinding) binding;
			if (typeBinding.isArray()) {
				typeBinding = typeBinding.getElementType();
			}
			// if (typeBinding.isCapture()) {
			// typeBinding.getWildcard();
			// }
			return getTypeImageDescriptor(false /*
												 * typeBinding.getDeclaringClass(
												 * ) != null
												 */, typeBinding, flags);
		} else if (binding instanceof IMethodBinding) {
			// ITypeBinding type= ((IMethodBinding)
			// binding).getDeclaringClass();
			// int modifiers= binding.getModifiers();
			// if (type.isEnum() && (!PHPFlags.isPublic(modifiers) &&
			// !PHPFlags.isProtected(modifiers) &&
			// !PHPFlags.isPrivate(modifiers)) && ((IMethodBinding)
			// binding).isConstructor())
			// return DLTKPluginImages.DESC_MISC_PRIVATE;
			return getMethodImageDescriptor(binding.getModifiers());
		} else if (binding instanceof IVariableBinding)
			return getFieldImageDescriptor((IVariableBinding) binding);
		return DLTKPluginImages.DESC_OBJS_UNKNOWN;
	}

	private static ImageDescriptor getClassImageDescriptor(int modifiers) {
		// if (PHPFlags.isPublic(modifiers) || PHPFlags.isProtected(modifiers)
		// || PHPFlags.isPrivate(modifiers))
		return DLTKPluginImages.DESC_OBJS_CLASS;
		// else
		// return DLTKPluginImages.DESC_OBJS_CLASS_DEFAULT;
	}

	private static ImageDescriptor getFieldImageDescriptor(
			IVariableBinding binding) {
		final int modifiers = binding.getModifiers();
		if (PHPFlags.isPublic(modifiers)/* || binding.isEnumConstant() */)
			return DLTKPluginImages.DESC_FIELD_PUBLIC;
		if (PHPFlags.isProtected(modifiers))
			return DLTKPluginImages.DESC_FIELD_PROTECTED;
		if (PHPFlags.isPrivate(modifiers))
			return DLTKPluginImages.DESC_FIELD_PRIVATE;

		return DLTKPluginImages.DESC_FIELD_DEFAULT;
	}

	private static void getFieldLabel(IVariableBinding binding, long flags,
			StringBuffer buffer) {
		if (((flags & ScriptElementLabels.F_PRE_TYPE_SIGNATURE) != 0)/*
																	 * &&
																	 * !binding.
																	 * isEnumConstant
																	 * ()
																	 */) {
			getTypeLabel(binding.getType(),
					(flags & ScriptElementLabels.T_TYPE_PARAMETERS), buffer);
			buffer.append(' ');
		}
		// qualification

		if ((flags & ScriptElementLabels.F_FULLY_QUALIFIED) != 0) {
			ITypeBinding declaringClass = binding.getDeclaringClass();
			if (declaringClass != null) { // test for array.length
				getTypeLabel(declaringClass,
						ScriptElementLabels.T_FULLY_QUALIFIED
								| (flags & ScriptElementLabels.P_COMPRESSED),
						buffer);
				buffer.append('.');
			}
		}
		buffer.append(binding.getName());
		if (((flags & ScriptElementLabels.F_APP_TYPE_SIGNATURE) != 0)/*
																	 * &&
																	 * !binding.
																	 * isEnumConstant
																	 * ()
																	 */) {
			buffer.append(ScriptElementLabels.DECL_STRING);
			getTypeLabel(binding.getType(),
					(flags & ScriptElementLabels.T_TYPE_PARAMETERS), buffer);
		}
		// post qualification
		if ((flags & ScriptElementLabels.F_POST_QUALIFIED) != 0) {
			ITypeBinding declaringClass = binding.getDeclaringClass();
			if (declaringClass != null) { // test for array.length
				buffer.append(ScriptElementLabels.CONCAT_STRING);
				getTypeLabel(declaringClass,
						ScriptElementLabels.T_FULLY_QUALIFIED
								| (flags & ScriptElementLabels.P_COMPRESSED),
						buffer);
			}
		}
	}

	private static void getLocalVariableLabel(IVariableBinding binding,
			long flags, StringBuffer buffer) {
		if (((flags & ScriptElementLabels.F_PRE_TYPE_SIGNATURE) != 0)) {
			getTypeLabel(binding.getType(),
					(flags & ScriptElementLabels.T_TYPE_PARAMETERS), buffer);
			buffer.append(' ');
		}
		if (((flags & ScriptElementLabels.F_FULLY_QUALIFIED) != 0)) {
			IFunctionBinding declaringMethod = binding.getDeclaringFunction();
			if (declaringMethod != null) {
				getMethodLabel(declaringMethod, flags, buffer);
				buffer.append('.');
			}
		}
		buffer.append(binding.getName());
		if (((flags & ScriptElementLabels.F_APP_TYPE_SIGNATURE) != 0)) {
			buffer.append(ScriptElementLabels.DECL_STRING);
			getTypeLabel(binding.getType(),
					(flags & ScriptElementLabels.T_TYPE_PARAMETERS), buffer);
		}
	}

	// private static ImageDescriptor getInnerClassImageDescriptor(int
	// modifiers) {
	// if (PHPFlags.isPublic(modifiers))
	// return DLTKPluginImages.DESC_OBJS_INNER_CLASS_PUBLIC;
	// else if (PHPFlags.isPrivate(modifiers))
	// return DLTKPluginImages.DESC_OBJS_INNER_CLASS_PRIVATE;
	// else if (PHPFlags.isProtected(modifiers))
	// return DLTKPluginImages.DESC_OBJS_INNER_CLASS_PROTECTED;
	// else
	// return DLTKPluginImages.DESC_OBJS_INNER_CLASS_DEFAULT;
	// }

	// private static ImageDescriptor getInnerInterfaceImageDescriptor(int
	// modifiers) {
	// if (PHPFlags.isPublic(modifiers))
	// return DLTKPluginImages.DESC_OBJS_INNER_INTERFACE_PUBLIC;
	// else if (PHPFlags.isPrivate(modifiers))
	// return DLTKPluginImages.DESC_OBJS_INNER_INTERFACE_PRIVATE;
	// else if (PHPFlags.isProtected(modifiers))
	// return DLTKPluginImages.DESC_OBJS_INNER_INTERFACE_PROTECTED;
	// else
	// return DLTKPluginImages.DESC_OBJS_INTERFACE_DEFAULT;
	// }

	private static ImageDescriptor getInterfaceImageDescriptor(int modifiers) {
		// if (PHPFlags.isPublic(modifiers) || PHPFlags.isProtected(modifiers)
		// || PHPFlags.isPrivate(modifiers))
		return DLTKPluginImages.DESC_OBJS_INTERFACE;
		// else
		// return DLTKPluginImages.DESC_OBJS_INTERFACE_DEFAULT;
	}

	private static ImageDescriptor getMethodImageDescriptor(int modifiers) {
		if (PHPFlags.isPublic(modifiers))
			return DLTKPluginImages.DESC_METHOD_PUBLIC;
		if (PHPFlags.isProtected(modifiers))
			return DLTKPluginImages.DESC_METHOD_PROTECTED;
		if (PHPFlags.isPrivate(modifiers))
			return DLTKPluginImages.DESC_METHOD_PRIVATE;

		return DLTKPluginImages.DESC_METHOD_DEFAULT;
	}

	private static void appendDimensions(int dim, StringBuffer buffer) {
		for (int i = 0; i < dim; i++) {
			buffer.append('[').append(']');
		}
	}

	private static void getMethodLabel(IFunctionBinding binding, long flags,
			StringBuffer buffer) {
		// return type
		// if ((flags & ScriptElementLabels.M_PRE_TYPE_PARAMETERS) != 0) {
		// if (binding.isGenericMethod()) {
		// ITypeBinding[] typeParameters= binding.getTypeParameters();
		// if (typeParameters.length > 0) {
		// getTypeParametersLabel(typeParameters, buffer);
		// buffer.append(' ');
		// }
		// }
		// }
		// return type
		IMethodBinding methodBinding = binding instanceof IMethodBinding ? (IMethodBinding) binding
				: null;
		ITypeBinding[] returnTypes = null;
		if ((flags & ScriptElementLabels.M_PRE_RETURNTYPE) != 0) {
			returnTypes = binding.getReturnType();
			for (ITypeBinding returnType : returnTypes) {
				if ((methodBinding == null || !methodBinding.isConstructor())) {
					getTypeLabel(returnType,
							(flags & ScriptElementLabels.T_TYPE_PARAMETERS),
							buffer);
					buffer.append('|');
				}
			}
		}

		// qualification
		if ((flags & ScriptElementLabels.M_FULLY_QUALIFIED) != 0
				&& methodBinding != null) {
			getTypeLabel(methodBinding.getDeclaringClass(),
					ScriptElementLabels.T_FULLY_QUALIFIED
							| (flags & ScriptElementLabels.P_COMPRESSED),
					buffer);
			buffer.append('.');
		}
		buffer.append(binding.getName());
		if ((flags & ScriptElementLabels.M_APP_TYPE_PARAMETERS) != 0) {
			// if (binding.isParameterizedMethod()) {
			// ITypeBinding[] typeArguments= binding.getTypeArguments();
			// if (typeArguments.length > 0) {
			// buffer.append(' ');
			// getTypeArgumentsLabel(typeArguments, (flags &
			// ScriptElementLabels.T_TYPE_PARAMETERS), buffer);
			// }
			// }
		}

		// parameters
		buffer.append('(');
		if ((flags & ScriptElementLabels.M_PARAMETER_TYPES | ScriptElementLabels.M_PARAMETER_NAMES) != 0) {
			ITypeBinding[] parameters = ((flags & ScriptElementLabels.M_PARAMETER_TYPES) != 0) ? binding
					.getParameterTypes()
					: null;
			if (parameters != null) {
				for (int index = 0; index < parameters.length; index++) {
					if (index > 0) {
						buffer.append(ScriptElementLabels.COMMA_STRING);
					}
					ITypeBinding paramType = parameters[index];
					if (binding.isVarargs() && (index == parameters.length - 1)) {
						getTypeLabel(
								paramType.getElementType(),
								(flags & ScriptElementLabels.T_TYPE_PARAMETERS),
								buffer);
						appendDimensions(paramType.getDimensions() - 1, buffer);
						buffer.append(ScriptElementLabels.ELLIPSIS_STRING);
					} else {
						getTypeLabel(
								paramType,
								(flags & ScriptElementLabels.T_TYPE_PARAMETERS),
								buffer);
					}
				}
			}
		} else {
			if (binding.getParameterTypes().length > 0) {
				buffer.append(ScriptElementLabels.ELLIPSIS_STRING);
			}
		}
		buffer.append(')');

		if ((flags & ScriptElementLabels.M_EXCEPTIONS) != 0) {
			ITypeBinding[] exceptions = binding.getExceptionTypes();
			if (exceptions.length > 0) {
				buffer.append(" throws "); //$NON-NLS-1$
				for (int index = 0; index < exceptions.length; index++) {
					if (index > 0) {
						buffer.append(ScriptElementLabels.COMMA_STRING);
					}
					getTypeLabel(exceptions[index],
							(flags & ScriptElementLabels.T_TYPE_PARAMETERS),
							buffer);
				}
			}
		}
		if ((flags & ScriptElementLabels.M_APP_TYPE_PARAMETERS) != 0) {
			// if (binding.isGenericMethod()) {
			ITypeBinding[] typeParameters = binding.getParameterTypes();
			if (typeParameters.length > 0) {
				buffer.append(' ');
				getTypeParametersLabel(typeParameters, buffer);
			}
			// }
		}
		if (((flags & ScriptElementLabels.M_APP_RETURNTYPE) != 0)
				&& (methodBinding == null || !methodBinding.isConstructor())) {
			if (returnTypes == null) {
				returnTypes = binding.getReturnType();
			}
			buffer.append(ScriptElementLabels.DECL_STRING);
			for (ITypeBinding returnType : returnTypes) {
				getTypeLabel(returnType,
						(flags & ScriptElementLabels.T_TYPE_PARAMETERS), buffer);
			}
		}
		// post qualification
		if ((flags & ScriptElementLabels.M_POST_QUALIFIED) != 0
				&& methodBinding != null) {
			buffer.append(ScriptElementLabels.CONCAT_STRING);
			getTypeLabel(methodBinding.getDeclaringClass(),
					ScriptElementLabels.T_FULLY_QUALIFIED
							| (flags & ScriptElementLabels.P_COMPRESSED),
					buffer);
		}
	}

	private static ImageDescriptor getTypeImageDescriptor(boolean inner,
			ITypeBinding binding, int flags) {
		/*
		 * if (binding.isEnum()) return DLTKPluginImages.DESC_OBJS_ENUM; else if
		 * (binding.isAnnotation()) return
		 * DLTKPluginImages.DESC_OBJS_ANNOTATION; else
		 */if (binding.isInterface()) {
			if ((flags & ScriptElementImageProvider.LIGHT_TYPE_ICONS) != 0)
				return DLTKPluginImages.DESC_OBJS_INTERFACEALT;
			// if (inner)
			// return getInnerInterfaceImageDescriptor(binding.getModifiers());
			return getInterfaceImageDescriptor(binding.getModifiers());
		} else if (binding.isClass()) {
			if ((flags & ScriptElementImageProvider.LIGHT_TYPE_ICONS) != 0)
				return DLTKPluginImages.DESC_OBJS_CLASSALT;
			// if (inner)
			// return getInnerClassImageDescriptor(binding.getModifiers());
			return getClassImageDescriptor(binding.getModifiers());
			// } else if (binding.isTypeVariable()) {
			// return DLTKPluginImages.DESC_OBJS_TYPEVARIABLE;
		}
		// primitive type, wildcard
		return null;
	}

	private static void getTypeLabel(ITypeBinding binding, long flags,
			StringBuffer buffer) {
		// if ((flags & ScriptElementLabels.T_FULLY_QUALIFIED) != 0) {
		// final IPackageBinding pack= binding.getPackage();
		// if (pack != null && !pack.isUnnamed()) {
		// buffer.append(pack.getName());
		// buffer.append('.');
		// }
		// }
		if ((flags & (ScriptElementLabels.T_FULLY_QUALIFIED | ScriptElementLabels.T_CONTAINER_QUALIFIED)) != 0) {
			// final ITypeBinding declaring= binding.getDeclaringClass();
			// if (declaring != null) {
			// getTypeLabel(declaring, ScriptElementLabels.T_CONTAINER_QUALIFIED
			// | (flags & ScriptElementLabels.P_COMPRESSED), buffer);
			// buffer.append('.');
			// }
			// final IMethodBinding declaringMethod=
			// binding.getDeclaringMethod();
			// if (declaringMethod != null) {
			// getMethodLabel(declaringMethod, 0, buffer);
			// buffer.append('.');
			// }
		}

		// if (binding.isCapture()) {
		// getTypeLabel(binding.getWildcard(), flags &
		// ScriptElementLabels.T_TYPE_PARAMETERS, buffer);
		// } else if (binding.isWildcardType()) {
		// buffer.append('?');
		// ITypeBinding bound= binding.getBound();
		// if (bound != null) {
		// if (binding.isUpperbound()) {
		//					buffer.append(" extends "); 
		// } else {
		//					buffer.append(" super "); 
		// }
		// getTypeLabel(bound, flags & ScriptElementLabels.T_TYPE_PARAMETERS,
		// buffer);
		// }
		// } else
		if (binding == null) {
			return;
		}
		if (binding.isArray()) {
			getTypeLabel(binding.getElementType(), flags
					& ScriptElementLabels.T_TYPE_PARAMETERS, buffer);
			appendDimensions(binding.getDimensions(), buffer);
		} else { // type variables, primitive, reftype
			String name = binding.getName();
			if (name != null) {
				buffer.append(name);
			}
			// if (binding.isEnum()) {
			// buffer.append('{' + ScriptElementLabels.ELLIPSIS_STRING + '}');
			// } else if (binding.isAnonymous()) {
			// ITypeBinding[] superInterfaces= binding.getInterfaces();
			// ITypeBinding baseType;
			// if (superInterfaces.length > 0) {
			// baseType= superInterfaces[0];
			// } else {
			// baseType= binding.getSuperclass();
			// }
			// if (baseType != null) {
			// StringBuffer anonymBaseType= new StringBuffer();
			// getTypeLabel(baseType, flags &
			// ScriptElementLabels.T_TYPE_PARAMETERS, anonymBaseType);
			// buffer.append(Messages.format(JavaUIMessages.ScriptElementLabels_anonym_type,
			// anonymBaseType.toString()));
			// } else {
			// buffer.append(JavaUIMessages.ScriptElementLabels_anonym);
			// }
			// } else {
			// }

			if ((flags & ScriptElementLabels.T_TYPE_PARAMETERS) != 0) {
				// if (binding.isGenericType()) {
				// getTypeParametersLabel(binding.getTypeParameters(), buffer);
				// } else if (binding.isParameterizedType()) {
				// getTypeArgumentsLabel(binding.getTypeArguments(), flags,
				// buffer);
				// }
			}
		}

		// if ((flags & ScriptElementLabels.T_POST_QUALIFIED) != 0) {
		// final IMethodBinding declaringMethod= binding.getDeclaringMethod();
		// final ITypeBinding declaringType= binding.getDeclaringClass();
		// if (declaringMethod != null) {
		// buffer.append(ScriptElementLabels.CONCAT_STRING);
		// getMethodLabel(declaringMethod, ScriptElementLabels.T_FULLY_QUALIFIED
		// | (flags & ScriptElementLabels.P_COMPRESSED), buffer);
		// } else if (declaringType != null) {
		// buffer.append(ScriptElementLabels.CONCAT_STRING);
		// getTypeLabel(declaringType, ScriptElementLabels.T_FULLY_QUALIFIED |
		// (flags & ScriptElementLabels.P_COMPRESSED), buffer);
		// } else {
		// final IPackageBinding pack= binding.getPackage();
		// if (pack != null && !pack.isUnnamed()) {
		// buffer.append(ScriptElementLabels.CONCAT_STRING);
		// buffer.append(pack.getName());
		// }
		// }
		// }
	}

	// private static void getTypeArgumentsLabel(ITypeBinding[] typeArgs, long
	// flags, StringBuffer buf) {
	// if (typeArgs.length > 0) {
	// buf.append('<');
	// for (int i = 0; i < typeArgs.length; i++) {
	// if (i > 0) {
	// buf.append(ScriptElementLabels.COMMA_STRING);
	// }
	// getTypeLabel(typeArgs[i], flags & ScriptElementLabels.T_TYPE_PARAMETERS,
	// buf);
	// }
	// buf.append('>');
	// }
	// }

	private static void getTypeParametersLabel(ITypeBinding[] typeParameters,
			StringBuffer buffer) {
		if (typeParameters.length > 0) {
			buffer.append('<');
			for (int index = 0; index < typeParameters.length; index++) {
				if (index > 0) {
					buffer.append(ScriptElementLabels.COMMA_STRING);
				}
				buffer.append(typeParameters[index].getName());
			}
			buffer.append('>');
		}
	}

	/**
	 * Returns the label for a Java element with the flags as defined by
	 * {@link ScriptElementLabels}.
	 * 
	 * @param binding
	 *            The binding to render.
	 * @param flags
	 *            The text flags as defined in {@link ScriptElementLabels}
	 * @return the label of the binding
	 */
	public static String getBindingLabel(IBinding binding, long flags) {
		StringBuffer buffer = new StringBuffer(60);
		if (binding instanceof ITypeBinding) {
			getTypeLabel(((ITypeBinding) binding), flags, buffer);
		} else if (binding instanceof IMethodBinding) {
			getMethodLabel(((IMethodBinding) binding), flags, buffer);
		} else if (binding instanceof IVariableBinding) {
			final IVariableBinding variable = (IVariableBinding) binding;
			if (variable.isField())
				getFieldLabel(variable, flags, buffer);
			else
				getLocalVariableLabel(variable, flags, buffer);
		}
		return Strings.markLTR(buffer.toString());
	}

	/**
	 * Returns the image descriptor for a binding with the flags as defined by
	 * {@link ScriptElementImageProvider}.
	 * 
	 * @param binding
	 *            The binding to get the image for.
	 * @param imageFlags
	 *            The image flags as defined in
	 *            {@link ScriptElementImageProvider}.
	 * @return the image of the binding or null if there is no image
	 */
	public static ImageDescriptor getBindingImageDescriptor(IBinding binding,
			int imageFlags) {
		ImageDescriptor baseImage = getBaseImageDescriptor(binding, imageFlags);
		if (baseImage != null) {
			int adornmentFlags = getAdornmentFlags(binding);
			Point size = ((imageFlags & ScriptElementImageProvider.SMALL_ICONS) != 0) ? ScriptElementImageProvider.SMALL_SIZE
					: ScriptElementImageProvider.BIG_SIZE;
			return new PHPElementImageDescriptor(baseImage, adornmentFlags,
					size);
		}
		return null;
	}

	public static final long DEFAULT_TEXTFLAGS = ScriptElementLabels.ALL_DEFAULT;
	public static final int DEFAULT_IMAGEFLAGS = ScriptElementImageProvider.OVERLAY_ICONS;

	final private long fTextFlags;
	final private int fImageFlags;

	private ImageDescriptorRegistry fRegistry;

	/**
	 * Creates a new binding label provider with default text and image flags
	 */
	public BindingLabelProvider() {
		this(DEFAULT_TEXTFLAGS, DEFAULT_IMAGEFLAGS);
	}

	/**
	 * @param textFlags
	 *            Flags defined in {@link ScriptElementLabels}.
	 * @param imageFlags
	 *            Flags defined in {@link ScriptElementImageProvider}.
	 */
	public BindingLabelProvider(final long textFlags, final int imageFlags) {
		fImageFlags = imageFlags;
		fTextFlags = textFlags;
		fRegistry = null;
	}

	/*
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof IBinding) {
			ImageDescriptor baseImage = getBindingImageDescriptor(
					(IBinding) element, fImageFlags);
			if (baseImage != null) {
				return getRegistry().get(baseImage);
			}
		}
		return super.getImage(element);
	}

	private ImageDescriptorRegistry getRegistry() {
		if (fRegistry == null)
			fRegistry = DLTKUIPlugin.getImageDescriptorRegistry();
		return fRegistry;
	}

	/*
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof IBinding) {
			return getBindingLabel((IBinding) element, fTextFlags);
		}
		return super.getText(element);
	}
}
