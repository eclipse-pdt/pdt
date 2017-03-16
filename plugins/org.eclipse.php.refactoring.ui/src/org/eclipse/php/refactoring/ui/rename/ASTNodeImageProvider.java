/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.php.internal.ui.util.PHPElementImageDescriptor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Description: Images for AST Nodes
 * 
 * @author Roy, 2007
 * @inspiredby JDT (JavaElementImageProvide) In the future - should replaced
 *             with PhpElementImageProvider
 */
public class ASTNodeImageProvider {

	/**
	 * Flags for the PHPImageLabelProvider: Generate images with overlays.
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

	private ImageDescriptorRegistry fRegistry;

	public ASTNodeImageProvider() {
		fRegistry = null; // lazy initialization
	}

	/**
	 * Returns the icon for a given element. The icon depends on the element
	 * type and element properties. If configured, overlay icons are constructed
	 * for <code>ISourceReference</code>s.
	 * 
	 * @param flags
	 *            Flags as defined by the PHPImageLabelProvider
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
		if (element instanceof ASTNode) {
			return getPHPImageDescriptor((ASTNode) element, flags);
		} else if (element instanceof IFile) {
			IFile file = (IFile) element;
			if ("as".equals(file.getFileExtension())) { //$NON-NLS-1$
				return getCUResourceImageDescriptor(file, flags); // image for a
																	// CU not on
																	// the build
																	// path
			}
			return getWorkbenchImageDescriptor(file, flags);
		} else if (element instanceof IAdaptable) {
			return getWorkbenchImageDescriptor((IAdaptable) element, flags);
		}
		return null;
	}

	private static boolean showOverlayIcons(int flags) {
		return (flags & OVERLAY_ICONS) != 0;
	}

	private static boolean useSmallSize(int flags) {
		return (flags & SMALL_ICONS) != 0;
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
	 * Returns an image descriptor for a script element. The descriptor includes
	 * overlays, if specified.
	 */
	public ImageDescriptor getPHPImageDescriptor(ASTNode node, int flags) {
		int adornmentFlags = computePHPAdornmentFlags(node, flags);
		Point size = useSmallSize(flags) ? SMALL_SIZE : BIG_SIZE;
		return new PHPElementImageDescriptor(getBaseImageDescriptor(node, flags), adornmentFlags, size);
	}

	/**
	 * Returns an image descriptor for a IAdaptable. The descriptor includes
	 * overlays, if specified (only error ticks apply). Returns
	 * <code>null</code> if no image could be found.
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

	// ---- Computation of base image key
	// -------------------------------------------------

	/**
	 * Returns an image descriptor for a php element. This is the base image, no
	 * overlays.
	 */
	public ImageDescriptor getBaseImageDescriptor(ASTNode element, int renderFlags) {
		switch (element.getType()) {
		case ASTNode.FUNCTION_DECLARATION:
			return PHPPluginImages.DESC_MISC_PUBLIC;
		case ASTNode.METHOD_DECLARATION:
			MethodDeclaration member = (MethodDeclaration) element;
			return getMethodImageDescriptor(member.getModifier());
		case ASTNode.FIELD_DECLARATION:
			FieldsDeclaration var = (FieldsDeclaration) element;
			return getFieldImageDescriptor(var.getModifier());
		case ASTNode.SCALAR:
			return PHPPluginImages.DESC_CONSTANT;
		case ASTNode.CLASS_DECLARATION:
			return PHPPluginImages.DESC_OBJS_CLASSALT;
		case ASTNode.INTERFACE_DECLARATION:
			return PHPPluginImages.DESC_OBJS_INTERFACE;
		case ASTNode.PROGRAM:
			return PHPPluginImages.DESC_OBJS_CUNIT;
		default:
			return PHPPluginImages.DESC_OBJS_UNKNOWN;
		}
	}

	protected ImageDescriptor getPHPFolderIcon(Object element, int renderFlags) {
		IContainer folder = (IContainer) element;
		boolean containsPHPElements = false;
		try {
			containsPHPElements = folder.members().length > 0;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		if (!containsPHPElements && false)// (folder.getNonPHPResources().length
											// > 0))
			return PHPPluginImages.DESC_OBJS_EMPTY_PHP_FOLDER_RESOURCES;
		else if (!containsPHPElements)
			return PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT;
		return PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT;
	}

	public void dispose() {
	}

	// ---- Methods to compute the adornments flags
	// ---------------------------------

	private int computePHPAdornmentFlags(ASTNode node, int renderFlags) {
		final int type = node.getType();

		int flags = 0;
		if (showOverlayIcons(renderFlags)) {
			int modifiers = -1;
			switch (type) {
			case ASTNode.INTERFACE_DECLARATION:
				break;
			case ASTNode.CLASS_DECLARATION:
				ClassDeclaration typeDeclaration = (ClassDeclaration) node;
				modifiers = typeDeclaration.getModifier();
				break;
			case ASTNode.METHOD_DECLARATION:
				MethodDeclaration methodDeclaration = (MethodDeclaration) node;
				modifiers = methodDeclaration.getModifier();
				break;
			case ASTNode.FIELD_DECLARATION:
				FieldsDeclaration fieldsDeclaration = (FieldsDeclaration) node;
				modifiers = fieldsDeclaration.getModifier();
				break;
			case ASTNode.SCALAR:
				flags |= PHPElementImageDescriptor.CONSTANT;
				break;

			}
			if (modifiers != -1) {
				flags = computeAdornments(modifiers, flags);
			}
		}
		return flags;
	}

	// Compute PHP Adornment Flags from the given PHPModifier value
	private int computeAdornments(int modifiers, int flags) {
		if (PHPFlags.isAbstract(modifiers)) {
			flags |= PHPElementImageDescriptor.ABSTRACT;
		}
		if (PHPFlags.isFinal(modifiers)) {
			flags |= PHPElementImageDescriptor.FINAL;
		}
		if (PHPFlags.isStatic(modifiers)) {
			flags |= PHPElementImageDescriptor.STATIC;
		}
		return flags;
	}

	public static ImageDescriptor getMethodImageDescriptor(int flags) {
		if (PHPFlags.isProtected(flags)) {
			return PHPPluginImages.DESC_MISC_PROTECTED;
		}
		if (PHPFlags.isPrivate(flags)) {
			return PHPPluginImages.DESC_MISC_PRIVATE;
		}
		return PHPPluginImages.DESC_MISC_PUBLIC;
	}

	public static ImageDescriptor getFieldImageDescriptor(int flags) {
		if (PHPFlags.isProtected(flags)) {
			return PHPPluginImages.DESC_FIELD_PROTECTED;
		}
		if (PHPFlags.isPrivate(flags)) {
			return PHPPluginImages.DESC_FIELD_PRIVATE;
		}
		return PHPPluginImages.DESC_FIELD_PUBLIC;
	}

	public static Image getDecoratedImage(ImageDescriptor baseImage, int adornments, Point size) {
		return PHPUiPlugin.getImageDescriptorRegistry().get(new PHPElementImageDescriptor(baseImage, adornments, size));
	}

	public static ImageDescriptor getClassImageDescriptor(TypeDeclaration typeDeclaration) {
		if (typeDeclaration.getType() == ASTNode.INTERFACE_DECLARATION) {
			return PHPPluginImages.DESC_OBJS_INTERFACE;
		}
		return DLTKPluginImages.DESC_OBJS_CLASS;
	}

}
