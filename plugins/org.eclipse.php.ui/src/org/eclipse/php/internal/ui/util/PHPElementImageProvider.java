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
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.functions.PHPFunctionsContentProvider;
import org.eclipse.php.internal.ui.projectOutline.ProjectOutlineContentProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Default strategy of the PHP	 plugin for the construction of PHP element icons.
 */
public class PHPElementImageProvider {

	/**
	 * Flags for the PHPImageLabelProvider:
	 * Generate images with overlays.
	 */
	public final static int OVERLAY_ICONS = 0x1;

	/**
	 * Generate small sized images.
	 */
	public final static int SMALL_ICONS = 0x2;

	/**
	 * Use the 'light' style for rendering types.
	 */
	public final static int LIGHT_TYPE_ICONS = 0x4;

	public static final Point SMALL_SIZE = new Point(16, 16);
	public static final Point BIG_SIZE = new Point(22, 16);

	private static ImageDescriptor DESC_OBJ_PROJECT_CLOSED;
	private static ImageDescriptor DESC_OBJ_PROJECT;
	{
		ISharedImages images = PHPUiPlugin.getDefault().getWorkbench().getSharedImages();
		DESC_OBJ_PROJECT_CLOSED = images.getImageDescriptor(IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED);
		DESC_OBJ_PROJECT = PHPPluginImages.DESC_OBJS_PHP_PROJECT; //images.getImageDescriptor(IDE.SharedImages.IMG_OBJ_PROJECT);
	}

	private ImageDescriptorRegistry fRegistry;

	public PHPElementImageProvider() {
		fRegistry = null; // lazy initialization
	}

	/**
	 * Returns the icon for a given element. The icon depends on the element type
	 * and element properties. If configured, overlay icons are constructed for
	 * <code>ISourceReference</code>s.
	 * @param flags Flags as defined by the PHPImageLabelProvider
	 */
	public Image getImageLabel(Object element, int flags) {
		return getImageLabel(computeDescriptor(element, flags));
	}

	private Image getImageLabel(ImageDescriptor descriptor) {
		if (descriptor == null)
			return null;
		return getRegistry().get(descriptor);
	}

	private ImageDescriptorRegistry getRegistry() {
		if (fRegistry == null) {
			fRegistry = PHPUiPlugin.getImageDescriptorRegistry();
		}
		return fRegistry;
	}

	private ImageDescriptor computeDescriptor(Object element, int flags) {
		if (element instanceof PHPCodeData) {
			return getPHPImageDescriptor((PHPCodeData) element, flags);
		} else if (element instanceof PHPProjectModel) {
			return DESC_OBJ_PROJECT;
		} else if (element instanceof IFile) {
			IFile file = (IFile) element;
			if ("as".equals(file.getFileExtension())) { //$NON-NLS-1$
				return getCUResourceImageDescriptor(file, flags); // image for a CU not on the build path
			}
			return getWorkbenchImageDescriptor(file, flags);
		} else if (element instanceof IAdaptable) {
			return getWorkbenchImageDescriptor((IAdaptable) element, flags);
		} else if (element.equals(PHPFunctionsContentProvider.CONSTANTS_NODE_NAME)) {
			return PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP;
		} else if (element instanceof PHPIncludePathModel) {
			PHPIncludePathModel model = (PHPIncludePathModel) element;
			switch (model.getType()) {
				case VARIABLE:
					return PHPPluginImages.DESC_OBJS_ENV_VAR;
				default:
					return PHPPluginImages.DESC_OBJS_LIBRARY;
			}
		} else if (element instanceof FolderFilteredUserModel) {
			String id = ((IPhpModel) element).getID();
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(id));
			if (resource != null) {
				return computeDescriptor(resource, flags);
			}
		}
		switch (ProjectOutlineContentProvider.getNodeType(element)) {
			case ProjectOutlineContentProvider.CLASSES:
				return PHPPluginImages.DESC_OBJ_PHP_CLASSES_GROUP;
			case ProjectOutlineContentProvider.CONSTANTS:
				return PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP;
			case ProjectOutlineContentProvider.FUNCTIONS:
				return PHPPluginImages.DESC_OBJ_PHP_FUNCTIONS_GROUP;
		}
		return null;
	}

	private static boolean showOverlayIcons(int flags) {
		return (flags & OVERLAY_ICONS) != 0;
	}

	private static boolean useSmallSize(int flags) {
		return (flags & SMALL_ICONS) != 0;
	}

	private static boolean useLightIcons(int flags) {
		return (flags & LIGHT_TYPE_ICONS) != 0;
	}

	/**
	 * Returns an image descriptor for a compilation unit not on the class path.
	 * The descriptor includes overlays, if specified.
	 */
	public ImageDescriptor getCUResourceImageDescriptor(IFile file, int flags) {
		Point size = useSmallSize(flags) ? SMALL_SIZE : BIG_SIZE;
		return new PHPElementImageDescriptor(PHPPluginImages.DESC_OBJS_CUNIT_RESOURCE, 0, size);
	}

	/**
	 * Returns an image descriptor for a script element. The descriptor includes overlays, if specified.
	 */
	public ImageDescriptor getPHPImageDescriptor(PHPCodeData element, int flags) {
		int adornmentFlags = computePHPAdornmentFlags(element, flags);
		Point size = useSmallSize(flags) ? SMALL_SIZE : BIG_SIZE;
		return new PHPElementImageDescriptor(getBaseImageDescriptor(element, flags), adornmentFlags, size);
	}

	/**
	 * Returns an image descriptor for a IAdaptable. The descriptor includes overlays, if specified (only error ticks apply).
	 * Returns <code>null</code> if no image could be found.
	 */
	public ImageDescriptor getWorkbenchImageDescriptor(IAdaptable adaptable, int flags) {
		IWorkbenchAdapter wbAdapter = (IWorkbenchAdapter) adaptable.getAdapter(IWorkbenchAdapter.class);
		if (wbAdapter == null) {
			return null;
		}
		ImageDescriptor descriptor = wbAdapter.getImageDescriptor(adaptable);
		if (descriptor == null) {
			return null;
		}

		Point size = useSmallSize(flags) ? SMALL_SIZE : BIG_SIZE;
		return new PHPElementImageDescriptor(descriptor, 0, size);
	}

	// ---- Computation of base image key -------------------------------------------------

	/**
	 * Returns an image descriptor for a php element. This is the base image, no overlays.
	 */
	public ImageDescriptor getBaseImageDescriptor(PHPCodeData element, int renderFlags) {
		if (element instanceof PHPFunctionData) {
			PHPFunctionData member = (PHPFunctionData) element;
			return getMethodImageDescriptor(member.getModifiers());
		} else if (element instanceof PHPClassVarData) {
			PHPClassVarData var = (PHPClassVarData) element;
			return getFieldImageDescriptor(var.getModifiers());
		} else if (element instanceof PHPConstantData || element instanceof PHPClassConstData || element.equals(PHPFunctionsContentProvider.CONSTANTS_NODE_NAME)) {
			return PHPPluginImages.DESC_CONSTANT;
		} else if (element instanceof PHPClassData) {
			PHPClassData type = (PHPClassData) element;

			if (useLightIcons(renderFlags)) {
				if (PHPModifier.isInterface(type.getModifiers())) {
					return PHPPluginImages.DESC_OBJS_INTERFACE;
				}
				return PHPPluginImages.DESC_OBJS_CLASSALT;
			}
			return getTypeImageDescriptor(type.getModifiers());
		}

		else if (element instanceof PHPFileData) {
			return PHPPluginImages.DESC_OBJS_CUNIT;
		} else if (element instanceof PHPIncludeFileData) {
			return PHPPluginImages.DESC_OBJS_INCLUDE;
		}

		else if (element instanceof PHPProjectModel) {
			PHPProjectModel model = (PHPProjectModel) element;
			IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(model);
			if (project.isOpen()) {
				IWorkbenchAdapter adapter = (IWorkbenchAdapter) project.getAdapter(IWorkbenchAdapter.class);
				if (adapter != null) {
					ImageDescriptor result = adapter.getImageDescriptor(project);
					if (result != null)
						return result;
				}
				return DESC_OBJ_PROJECT;
			}
			return DESC_OBJ_PROJECT_CLOSED;
		} else if (element instanceof IContainer) {
			return getPHPFolderIcon(element, renderFlags);

		} else if (element instanceof PHPWorkspaceModelManager)
			return PHPPluginImages.DESC_OBJS_PHP_MODEL;

		Assert.isTrue(false, "no image for this  Type: " + element); //$NON-NLS-1$
		return null;

	}

	protected ImageDescriptor getPHPFolderIcon(Object element, int renderFlags) {
		IContainer folder = (IContainer) element;
		boolean containsPHPElements = false;
		try {
			containsPHPElements = folder.members().length > 0;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		if (!containsPHPElements && false)//(folder.getNonPHPResources().length > 0))
			return PHPPluginImages.DESC_OBJS_EMPTY_PHP_FOLDER_RESOURCES;
		else if (!containsPHPElements)
			return PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT;
		return PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT;
	}

	public void dispose() {
	}

	// ---- Methods to compute the adornments flags ---------------------------------

	private int computePHPAdornmentFlags(PHPCodeData element, int renderFlags) {
		int flags = 0;
		if (showOverlayIcons(renderFlags)) {
			int modifiers = -1;
			if (element instanceof PHPFunctionData) {
				PHPFunctionData functionData = (PHPFunctionData) element;
				modifiers = functionData.getModifiers();
			} else if (element instanceof PHPConstantData || element instanceof PHPClassConstData) {
				flags |= PHPElementImageDescriptor.CONSTANT;
			} else if (element instanceof PHPClassData) {
				PHPClassData classData = (PHPClassData) element;
				modifiers = classData.getModifiers();
			} else if (element instanceof PHPClassVarData) {
				PHPClassVarData classVarData = (PHPClassVarData) element;
				modifiers = classVarData.getModifiers();
			}
			if (modifiers != -1) {
				flags = computeAdornments(modifiers, flags);
			}
		}
		return flags;
	}

	// Compute PHP Adornment Flags from the given PHPModifier value
	private int computeAdornments(int modifiers, int flags) {
		if (PHPModifier.isAbstract(modifiers)) {
			flags |= PHPElementImageDescriptor.ABSTRACT;
		}
		if (PHPModifier.isFinal(modifiers)) {
			flags |= PHPElementImageDescriptor.FINAL;
		}
		if (PHPModifier.isStatic(modifiers)) {
			flags |= PHPElementImageDescriptor.STATIC;
		}
		return flags;
	}

	public static ImageDescriptor getMethodImageDescriptor(int flags) {
		if (PHPModifier.isProtected(flags)) {
			return PHPPluginImages.DESC_MISC_PROTECTED;
		}
		if (PHPModifier.isPrivate(flags)) {
			return PHPPluginImages.DESC_MISC_PRIVATE;
		}
		return PHPPluginImages.DESC_MISC_PUBLIC;
	}

	public static ImageDescriptor getFieldImageDescriptor(int flags) {
		if (PHPModifier.isProtected(flags)) {
			return PHPPluginImages.DESC_FIELD_PROTECTED;
		}
		if (PHPModifier.isPrivate(flags)) {
			return PHPPluginImages.DESC_FIELD_PRIVATE;
		}
		return PHPPluginImages.DESC_FIELD_PUBLIC;
	}

	public static ImageDescriptor getTypeImageDescriptor(int flags) {
		return getClassImageDescriptor(flags);
	}

	public static Image getDecoratedImage(ImageDescriptor baseImage, int adornments, Point size) {
		return PHPUiPlugin.getImageDescriptorRegistry().get(new PHPElementImageDescriptor(baseImage, adornments, size));
	}

	private static ImageDescriptor getClassImageDescriptor(int flags) {
		if (PHPModifier.isInterface(flags)) {
			return PHPPluginImages.DESC_OBJS_INTERFACE;
		}
		return PHPPluginImages.DESC_OBJS_CLASS;
	}

}
