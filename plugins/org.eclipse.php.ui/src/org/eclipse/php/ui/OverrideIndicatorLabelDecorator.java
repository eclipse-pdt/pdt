package org.eclipse.php.ui;

import java.io.IOException;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.util.MethodOverrideTester;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.util.SuperTypeHierarchyCache;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.php.internal.ui.util.ImageImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * LabelDecorator that decorates an method's image with override or implements
 * overlays. The viewer using this decorator is responsible for updating the
 * images on element changes.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 2.0
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class OverrideIndicatorLabelDecorator implements ILabelDecorator, ILightweightLabelDecorator {

	private ImageDescriptorRegistry fRegistry;
	private boolean fUseNewRegistry = false;

	/**
	 * Creates a decorator. The decorator creates an own image registry to cache
	 * images.
	 */
	public OverrideIndicatorLabelDecorator() {
		this(null);
		fUseNewRegistry = true;
	}

	/*
	 * Creates decorator with a shared image registry.
	 * 
	 * @param registry The registry to use or <code>null</code> to use the Java
	 * plugin's image registry.
	 */
	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param registry
	 *            The registry to use.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public OverrideIndicatorLabelDecorator(ImageDescriptorRegistry registry) {
		fRegistry = registry;
	}

	private ImageDescriptorRegistry getRegistry() {
		if (fRegistry == null) {
			fRegistry = fUseNewRegistry ? new ImageDescriptorRegistry() : PHPUiPlugin.getImageDescriptorRegistry();
		}
		return fRegistry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ILabelDecorator#decorateText(String, Object)
	 */
	@Override
	public String decorateText(String text, Object element) {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ILabelDecorator#decorateImage(Image, Object)
	 */
	@Override
	public Image decorateImage(Image image, Object element) {
		if (image == null)
			return null;

		int adornmentFlags = computeAdornmentFlags(element);
		if (adornmentFlags != 0) {
			ImageDescriptor baseImage = new ImageImageDescriptor(image);
			Rectangle bounds = image.getBounds();
			return getRegistry().get(new ScriptElementImageDescriptor(baseImage, adornmentFlags,
					new Point(bounds.width, bounds.height)));
		}
		return image;
	}

	/**
	 * Note: This method is for internal use only. Clients should not call this
	 * method.
	 * 
	 * @param element
	 *            The element to decorate
	 * @return Resulting decorations (combination of
	 *         JavaElementImageDescriptor.IMPLEMENTS and
	 *         JavaElementImageDescriptor.OVERRIDES)
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public int computeAdornmentFlags(Object element) {
		if (element instanceof IMethod) {
			try {
				IMethod method = (IMethod) element;
				if (method.getParent().getElementType() != IModelElement.TYPE) {
					return 0;
				}
				if (!method.getScriptProject().isOnBuildpath(method)) {
					return 0;
				}

				if (!shouldCompute((IType) method.getParent())) {
					return 0;
				}

				if (!shouldCompute(method)) {
					return 0;
				}

				return getOverrideIndicators(method);
			} catch (ModelException e) {
				if (!e.isDoesNotExist()) {
					PHPUiPlugin.log(e);
				}
			} catch (IOException e) {
				PHPUiPlugin.log(e);
			}
		}
		return 0;
	}

	private boolean shouldCompute(IType parent) throws ModelException {
		return !PHPFlags.isInterface(parent.getFlags()) && parent.getSuperClasses() != null
				&& parent.getSuperClasses().length != 0;
	}

	private boolean shouldCompute(IMethod method) throws ModelException {
		int flags = method.getFlags();
		return !method.isConstructor() && !Flags.isPrivate(flags) && !Flags.isStatic(flags);
	}

	/**
	 * Note: This method is for internal use only. Clients should not call this
	 * method.
	 * 
	 * @param method
	 *            The element to decorate
	 * @return Resulting decorations (combination of
	 *         ScriptElementImageDescriptor.IMPLEMENTS and
	 *         ScriptElementImageDescriptor.OVERRIDES)
	 * @throws ModelException
	 * @throws IOException
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	protected int getOverrideIndicators(IMethod method) throws ModelException, IOException {
		IType type = method.getDeclaringType();
		if (type != null) {
			MethodOverrideTester methodOverrideTester = SuperTypeHierarchyCache.getMethodOverrideTester(type);
			IMethod defining = methodOverrideTester.findOverriddenMethod(method, true);
			if (defining != null) {
				if (isAbstract(defining)) {
					return ScriptElementImageDescriptor.IMPLEMENTS;
				} else {
					return ScriptElementImageDescriptor.OVERRIDES;
				}
			}
		}

		Program astRoot = null;
		// XXX: do not call SharedASTProvider.getAST() due bug 466694 and
		// until bug 438661 will be fixed (PHPReconcilingStrategy require access
		// to UI thread)
		// Program astRoot = SharedASTProvider.getAST(method.getSourceModule(),
		// SharedASTProvider.WAIT_YES, null);
		if (astRoot == null) {
			// XXX: must be removed once 438661 is fixed
			astRoot = CodeGeneration.generateProgram(method, null);
		}
		if (astRoot != null) {
			int res = findInHierarchyWithAST(astRoot, method);
			if (res != -1) {
				return res;
			}
		}

		return 0;
	}

	private int findInHierarchyWithAST(Program astRoot, IMethod method) throws ModelException {
		SourceRange range = new SourceRange(method.getNameRange().getOffset(), method.getNameRange().getLength());
		ASTNode node = NodeFinder.perform(astRoot, range);
		if (node instanceof Identifier && node.getParent() instanceof FunctionDeclaration
				&& node.getParent().getParent() instanceof MethodDeclaration) {
			IMethodBinding binding = ((MethodDeclaration) node.getParent().getParent()).resolveMethodBinding();
			if (binding != null) {
				IMethodBinding defining = Bindings.findOverriddenMethod(binding, true);
				if (defining != null) {
					if (isAbstract(defining)) {
						return ScriptElementImageDescriptor.IMPLEMENTS;
					} else {
						return ScriptElementImageDescriptor.OVERRIDES;
					}
				}
				return 0;
			}
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang
	 * .Object, org.eclipse.jface.viewers.IDecoration)
	 */
	@Override
	public void decorate(Object element, IDecoration decoration) {
		int adornmentFlags = computeAdornmentFlags(element);
		if ((adornmentFlags & ScriptElementImageDescriptor.IMPLEMENTS) != 0) {

			decoration.addOverlay(DLTKPluginImages.DESC_OVR_IMPLEMENTS);
		} else if ((adornmentFlags & ScriptElementImageDescriptor.OVERRIDES) != 0) {

			decoration.addOverlay(DLTKPluginImages.DESC_OVR_OVERRIDES);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IBaseLabelProvider#addListener(ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		if (fRegistry != null && fUseNewRegistry) {
			fRegistry.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IBaseLabelProvider#isLabelProperty(Object, String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IBaseLabelProvider#removeListener(ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	private static boolean isAbstract(IMember member) throws ModelException {
		return Flags.isAbstract(member.getFlags());
	}

	private static boolean isAbstract(IMethodBinding member) {
		return isAbstract(member.getModifiers());
	}

	private static boolean isAbstract(int mod) {
		return (mod & Modifiers.AccAbstract) != 0;
	}

}
