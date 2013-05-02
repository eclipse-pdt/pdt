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
package org.eclipse.php.internal.ui.editor.hover;

import java.io.*;
import java.net.URL;
import java.util.*;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.internal.text.html.BrowserInformationControl;
import org.eclipse.jface.internal.text.html.BrowserInformationControlInput;
import org.eclipse.jface.internal.text.html.BrowserInput;
import org.eclipse.jface.internal.text.html.HTMLPrinter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.documentation.PHPDocumentationContentAccess;
import org.eclipse.php.internal.ui.documentation.PHPElementLinks;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.internal.ui.util.OpenBrowserUtil;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
public class PHPDocumentationHover extends AbstractPHPEditorTextHover implements
		IPHPTextHover, IInformationProviderExtension2 {

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}

	/**
	 * Action to go back to the previous input in the hover control.
	 * 
	 * @since 3.4
	 */
	private static final class BackAction extends Action {
		private final BrowserInformationControl fInfoControl;

		public BackAction(BrowserInformationControl infoControl) {
			fInfoControl = infoControl;
			setText(PHPHoverMessages.JavadocHover_back);
			ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
			setImageDescriptor(images
					.getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
			setDisabledImageDescriptor(images
					.getImageDescriptor(ISharedImages.IMG_TOOL_BACK_DISABLED));

			update();
		}

		public void run() {
			BrowserInformationControlInput previous = (BrowserInformationControlInput) fInfoControl
					.getInput().getPrevious();
			if (previous != null) {
				fInfoControl.setInput(previous);
			}
		}

		public void update() {
			BrowserInformationControlInput current = fInfoControl.getInput();

			if (current != null && current.getPrevious() != null) {
				BrowserInput previous = current.getPrevious();
				setToolTipText(Messages.format(
						PHPHoverMessages.JavadocHover_back_toElement_toolTip,
						BasicElementLabels.getJavaElementName(previous
								.getInputName())));
				setEnabled(true);
			} else {
				setToolTipText(PHPHoverMessages.JavadocHover_back);
				setEnabled(false);
			}
		}
	}

	/**
	 * Action to go forward to the next input in the hover control.
	 * 
	 * @since 3.4
	 */
	private static final class ForwardAction extends Action {
		private final BrowserInformationControl fInfoControl;

		public ForwardAction(BrowserInformationControl infoControl) {
			fInfoControl = infoControl;
			setText(PHPHoverMessages.JavadocHover_forward);
			ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
			setImageDescriptor(images
					.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
			setDisabledImageDescriptor(images
					.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD_DISABLED));

			update();
		}

		public void run() {
			BrowserInformationControlInput next = (BrowserInformationControlInput) fInfoControl
					.getInput().getNext();
			if (next != null) {
				fInfoControl.setInput(next);
			}
		}

		public void update() {
			BrowserInformationControlInput current = fInfoControl.getInput();

			if (current != null && current.getNext() != null) {
				setToolTipText(Messages
						.format(PHPHoverMessages.JavadocHover_forward_toElement_toolTip,
								BasicElementLabels.getJavaElementName(current
										.getNext().getInputName())));
				setEnabled(true);
			} else {
				setToolTipText(PHPHoverMessages.JavadocHover_forward_toolTip);
				setEnabled(false);
			}
		}
	}

	/**
	 * Action that opens the current hover input element.
	 * 
	 * @since 3.4
	 */
	private static final class OpenDeclarationAction extends Action {
		private final BrowserInformationControl fInfoControl;

		public OpenDeclarationAction(BrowserInformationControl infoControl) {
			fInfoControl = infoControl;
			setText(PHPHoverMessages.JavadocHover_openDeclaration);
			DLTKPluginImages.setLocalImageDescriptors(this, "goto_input.gif"); //$NON-NLS-1$ //TODO: better images
		}

		/*
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			PHPDocumentationBrowserInformationControlInput infoInput = (PHPDocumentationBrowserInformationControlInput) fInfoControl
					.getInput(); // TODO: check cast
			fInfoControl.notifyDelayedInputChange(null);
			fInfoControl.dispose(); // FIXME: should have protocol to hide,
			// rather than dispose

			try {
				// FIXME: add hover location to editor navigation history?
				IEditorPart editor = EditorUtility.openInEditor(
						infoInput.getElement(), true);
				EditorUtility.revealInEditor(editor, infoInput.getElement());
			} catch (PartInitException e) {
				PHPUiPlugin.log(e);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
	}

	/**
	 * Presenter control creator.
	 * 
	 * @since 3.3
	 */
	public static final class PresenterControlCreator extends
			AbstractReusableInformationControlCreator {

		/*
		 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
		 * AbstractReusableInformationControlCreator
		 * #doCreateInformationControl(org.eclipse.swt.widgets.Shell)
		 */
		public IInformationControl doCreateInformationControl(Shell parent) {
			if (BrowserInformationControl.isAvailable(parent)) {
				ToolBarManager tbm = new ToolBarManager(SWT.FLAT);
				String font = PreferenceConstants.APPEARANCE_DOCUMENTATION_FONT;
				BrowserInformationControl iControl = new BrowserInformationControl(
						parent, font, tbm);

				final BackAction backAction = new BackAction(iControl);
				backAction.setEnabled(false);
				tbm.add(backAction);
				final ForwardAction forwardAction = new ForwardAction(iControl);
				tbm.add(forwardAction);
				forwardAction.setEnabled(false);

				final OpenDeclarationAction openDeclarationAction = new OpenDeclarationAction(
						iControl);
				tbm.add(openDeclarationAction);

				// final SimpleSelectionProvider selectionProvider = new
				// SimpleSelectionProvider();
				// OpenExternalBrowserAction openExternalJavadocAction = new
				// OpenExternalBrowserAction(
				// parent.getDisplay(), selectionProvider);
				// selectionProvider
				// .addSelectionChangedListener(openExternalJavadocAction);
				// selectionProvider.setSelection(new StructuredSelection());
				// tbm.add(openExternalJavadocAction);

				IInputChangedListener inputChangeListener = new IInputChangedListener() {
					public void inputChanged(Object newInput) {
						backAction.update();
						forwardAction.update();
						if (newInput == null) {
						} else if (newInput instanceof BrowserInformationControlInput) {
							BrowserInformationControlInput input = (BrowserInformationControlInput) newInput;
							Object inputElement = input.getInputElement();
							boolean isJavaElementInput = inputElement instanceof IModelElement;
							openDeclarationAction
									.setEnabled(isJavaElementInput);
						}
					}
				};
				iControl.addInputChangeListener(inputChangeListener);

				tbm.update(true);

				addLinkListener(iControl);

				return iControl;

			} else {
				return new DefaultInformationControl(parent, true);
			}
		}
	}

	/**
	 * Hover control creator.
	 * 
	 * @since 3.3
	 */
	public static final class HoverControlCreator extends
			AbstractReusableInformationControlCreator {
		/**
		 * The information presenter control creator.
		 * 
		 * @since 3.4
		 */
		private final IInformationControlCreator fInformationPresenterControlCreator;

		/**
		 * @param informationPresenterControlCreator
		 *            control creator for enriched hover
		 * @since 3.4
		 */
		public HoverControlCreator(
				IInformationControlCreator informationPresenterControlCreator) {
			fInformationPresenterControlCreator = informationPresenterControlCreator;
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
		 * AbstractReusableInformationControlCreator
		 * #doCreateInformationControl(org.eclipse.swt.widgets.Shell)
		 */
		public IInformationControl doCreateInformationControl(Shell parent) {
			String tooltipAffordanceString = EditorsUI
					.getTooltipAffordanceString();
			if (BrowserInformationControl.isAvailable(parent)) {
				String font = PreferenceConstants.APPEARANCE_DOCUMENTATION_FONT;
				BrowserInformationControl iControl = new BrowserInformationControl(
						parent, font, tooltipAffordanceString) {
					/*
					 * @see
					 * org.eclipse.jface.text.IInformationControlExtension5#
					 * getInformationPresenterControlCreator()
					 */
					public IInformationControlCreator getInformationPresenterControlCreator() {
						return fInformationPresenterControlCreator;
					}
				};

				addLinkListener(iControl);
				return iControl;
			} else {
				return new DefaultInformationControl(parent,
						tooltipAffordanceString);
			}
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
		 * AbstractReusableInformationControlCreator
		 * #canReuse(org.eclipse.jface.text.IInformationControl)
		 */
		public boolean canReuse(IInformationControl control) {
			if (!super.canReuse(control))
				return false;

			if (control instanceof IInformationControlExtension4) {
				String tooltipAffordanceString = EditorsUI
						.getTooltipAffordanceString();
				((IInformationControlExtension4) control)
						.setStatusText(tooltipAffordanceString);
			}

			return true;
		}
	}

	private static final long LABEL_FLAGS = ScriptElementLabels.ALL_FULLY_QUALIFIED
			| ScriptElementLabels.M_PRE_RETURNTYPE
			| ScriptElementLabels.M_PARAMETER_TYPES
			| ScriptElementLabels.M_PARAMETER_NAMES
			| ScriptElementLabels.M_EXCEPTIONS
			| ScriptElementLabels.F_PRE_TYPE_SIGNATURE
			| ScriptElementLabels.M_PRE_TYPE_PARAMETERS
			| ScriptElementLabels.T_TYPE_PARAMETERS
			| ScriptElementLabels.USE_RESOLVED;
	private static final long LOCAL_VARIABLE_FLAGS = LABEL_FLAGS
			& ~ScriptElementLabels.F_FULLY_QUALIFIED
			| ScriptElementLabels.F_POST_QUALIFIED;

	/**
	 * The style sheet (css).
	 * 
	 * @since 3.4
	 */
	private static String fgStyleSheet;

	/**
	 * The hover control creator.
	 * 
	 * @since 3.2
	 */
	private IInformationControlCreator fHoverControlCreator;
	/**
	 * The presentation control creator.
	 * 
	 * @since 3.2
	 */
	private IInformationControlCreator fPresenterControlCreator;

	/*
	 * @seeorg.eclipse.jface.text.ITextHoverExtension2#
	 * getInformationPresenterControlCreator()
	 * 
	 * @since 3.1
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		if (fPresenterControlCreator == null)
			fPresenterControlCreator = new PresenterControlCreator();
		return fPresenterControlCreator;
	}

	/*
	 * @see ITextHoverExtension#getHoverControlCreator()
	 * 
	 * @since 3.2
	 */
	public IInformationControlCreator getHoverControlCreator() {
		if (fHoverControlCreator == null)
			fHoverControlCreator = new HoverControlCreator(
					getInformationPresenterControlCreator());
		return fHoverControlCreator;
	}

	private static void addLinkListener(final BrowserInformationControl control) {
		control.addLocationListener(PHPElementLinks
				.createLocationListener(new PHPElementLinks.ILinkHandler() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.jdt.internal.ui.viewsupport.JavaElementLinks
					 * .ILinkHandler
					 * #handleInlineJavadocLink(org.eclipse.jdt.core
					 * .IModelElement)
					 */
					public void handleInlineLink(IModelElement linkTarget) {
						PHPDocumentationBrowserInformationControlInput hoverInfo = getHoverInfo(
								new IModelElement[] { linkTarget },
								null,
								(PHPDocumentationBrowserInformationControlInput) control
										.getInput());
						if (control.hasDelayedInputChangeListener())
							control.notifyDelayedInputChange(hoverInfo);
						else
							control.setInput(hoverInfo);
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.jdt.internal.ui.viewsupport.JavaElementLinks
					 * .ILinkHandler
					 * #handleDeclarationLink(org.eclipse.jdt.core.
					 * IModelElement)
					 */
					public void handleDeclarationLink(IModelElement linkTarget) {
						control.notifyDelayedInputChange(null);
						control.dispose(); // FIXME: should have protocol to
						// hide, rather than dispose
						// try {
						// FIXME: add hover location to editor navigation
						// history?
						// JavaUI.openInEditor(linkTarget);
						// } catch (PartInitException e) {
						// PHPUiPlugin.log(e);
						// } catch (ModelException e) {
						// PHPUiPlugin.log(e);
						// }
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.jdt.internal.ui.viewsupport.JavaElementLinks
					 * .ILinkHandler#handleExternalLink(java.net.URL,
					 * org.eclipse.swt.widgets.Display)
					 */
					public boolean handleExternalLink(URL url, Display display) {
						control.notifyDelayedInputChange(null);
						control.dispose(); // FIXME: should have protocol to
						// hide, rather than dispose

						// open external links in real browser:
						OpenBrowserUtil.open(url, display); //$NON-NLS-1$

						return true;
					}

					public void handleTextSet() {
					}
				}));
	}

	/**
	 * @deprecated see
	 *             {@link org.eclipse.jface.text.ITextHover#getHoverInfo(ITextViewer, IRegion)}
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		PHPDocumentationBrowserInformationControlInput info = (PHPDocumentationBrowserInformationControlInput) getHoverInfo2(
				textViewer, hoverRegion);
		return info != null ? info.getHtml() : null;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.ITextHoverExtension2#getHoverInfo2(org.eclipse
	 * .jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
	 */
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		return internalGetHoverInfo(textViewer, hoverRegion);
	}

	private PHPDocumentationBrowserInformationControlInput internalGetHoverInfo(
			ITextViewer textViewer, IRegion hoverRegion) {
		IModelElement[] elements = getElementsAt(textViewer, hoverRegion);
		if (elements == null || elements.length == 0)
			return null;
		// filter the same namespace
		Set<IModelElement> elementSet = new TreeSet<IModelElement>(
				new Comparator<IModelElement>() {

					public int compare(IModelElement o1, IModelElement o2) {
						if (o1 instanceof IType && o2 instanceof IType) {
							IType type1 = (IType) o1;
							IType type2 = (IType) o2;
							try {
								if (PHPFlags.isNamespace(type1.getFlags())
										&& PHPFlags.isNamespace(type2
												.getFlags())
										&& type1.getElementName().equals(
												type2.getElementName())) {
									return 0;
								}
							} catch (ModelException e) {
							}
						}
						return 1;
					}

				});
		List<IModelElement> elementList = new ArrayList<IModelElement>();
		for (int i = 0; i < elements.length; i++) {
			if (!elementSet.contains(elements[i])) {
				elementSet.add(elements[i]);
				elementList.add(elements[i]);
			}
		}
		elements = elementList.toArray(new IModelElement[elementList.size()]);

		String constantValue;
		if (elements.length == 1
				&& elements[0].getElementType() == IModelElement.FIELD) {
			constantValue = getConstantValue((IField) elements[0], hoverRegion);
			if (constantValue != null)
				constantValue = HTMLPrinter.convertToHTMLContent(constantValue);
		} else {
			constantValue = null;
		}

		return getHoverInfo(elements, constantValue, null);
	}

	/**
	 * Computes the hover info.
	 * 
	 * @param elements
	 *            the resolved elements
	 * @param constantValue
	 *            a constant value iff result contains exactly 1 constant field,
	 *            or <code>null</code>
	 * @param previousInput
	 *            the previous input, or <code>null</code>
	 * @return the HTML hover info for the given element(s) or <code>null</code>
	 *         if no information is available
	 * @since 3.4
	 */
	private static PHPDocumentationBrowserInformationControlInput getHoverInfo(
			IModelElement[] elements, String constantValue,
			PHPDocumentationBrowserInformationControlInput previousInput) {
		StringBuffer buffer = new StringBuffer();
		boolean hasContents = false;
		IModelElement element = null;
		int leadingImageWidth = 20;
		for (int i = 0; i < elements.length; i++) {
			element = elements[i];
			if (element instanceof IMember) {
				IMember member = (IMember) element;
				HTMLPrinter.addSmallHeader(buffer,
						getInfoText(member, constantValue, true, i == 0));
				Reader reader = null;
				try {
					reader = getHTMLContent(member);
				} catch (ModelException e) {
				}

				if (reader != null) {
					HTMLPrinter.addParagraph(buffer, reader);
				}
				if (i != elements.length - 1) {
					buffer.append("<hr>"); //$NON-NLS-1$
				}
				hasContents = true;
			} else if (element.getElementType() == IModelElement.FIELD) {
				HTMLPrinter.addSmallHeader(buffer,
						getInfoText(element, constantValue, true, i == 0));
				hasContents = true;
			}
		}

		if (!hasContents)
			return null;

		if (buffer.length() > 0) {
			HTMLPrinter.insertPageProlog(buffer, 0, getStyleSheet());
			HTMLPrinter.addPageEpilog(buffer);
			return new PHPDocumentationBrowserInformationControlInput(
					previousInput, element, buffer.toString(),
					leadingImageWidth);
		}

		return null;
	}

	private static String getInfoText(IModelElement element,
			String constantValue, boolean allowImage, boolean isFirstElement) {
		StringBuffer label = getInfoText(element);
		if (element.getElementType() == IModelElement.FIELD) {
			if (constantValue != null) {
				label.append(' ');
				label.append('=');
				label.append(' ');
				label.append(constantValue);
			}
		}

		String imageName = null;
		if (allowImage) {
			URL imageUrl = PHPUiPlugin.getDefault().getImagesOnFSRegistry()
					.getImageURL(element);
			if (imageUrl != null) {
				imageName = imageUrl.toExternalForm();
			}
		}

		StringBuffer buf = new StringBuffer();
		addImageAndLabel(buf, imageName, 16, 16, 2, 2, label.toString(), 20, 2,
				isFirstElement);
		return buf.toString();
	}

	/*
	 * @since 3.4
	 */
	private static boolean isFinal(IField field) {
		try {
			return PHPFlags.isFinal(field.getFlags());
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
			return false;
		}
	}

	/**
	 * Returns the constant value for the given field.
	 * 
	 * @param field
	 *            the field
	 * @param hoverRegion
	 *            the hover region
	 * @return the constant value for the given field or <code>null</code> if
	 *         none
	 * @since 3.4
	 */
	private String getConstantValue(IField field, IRegion hoverRegion) {
		if (!isFinal(field))
			return null;

		ISourceModule typeRoot = getEditorInputModelElement();
		if (typeRoot == null)
			return null;

		Object constantValue = null;
		if (field != null && field.exists()) {
			try {
				Program unit = SharedASTProvider.getAST(
						field.getSourceModule(), SharedASTProvider.WAIT_YES,
						null);
				ASTNode node = NodeFinder.perform(unit, field.getNameRange()
						.getOffset(), field.getNameRange().getLength());
				if (node != null) {
					if (node instanceof Identifier
							&& node.getParent() instanceof ConstantDeclaration) {
						ConstantDeclaration decl = (ConstantDeclaration) node
								.getParent();
						if (decl.initializers().size() == 1
								&& decl.initializers().get(0) instanceof Scalar) {
							Scalar scalar = (Scalar) decl.initializers().get(0);
							constantValue = scalar.getStringValue();
						}
					} else if (node instanceof Scalar
							&& node.getParent() instanceof FunctionInvocation) {
						FunctionInvocation invocation = (FunctionInvocation) node
								.getParent();
						Expression function = invocation.getFunctionName()
								.getName();
						String functionName = ""; //$NON-NLS-1$
						// for PHP5.3
						if (function instanceof NamespaceName) {
							// global function
							if (((NamespaceName) function).isGlobal()) {
								functionName = ((NamespaceName) function)
										.getName();
								if (functionName.charAt(0) == '\\') {
									functionName = functionName.substring(1);
								}
							} else {
								ModuleDeclaration parsedUnit = SourceParserUtil
										.getModuleDeclaration(
												field.getSourceModule(), null);
								org.eclipse.dltk.ast.ASTNode func = ASTUtils
										.findMinimalNode(parsedUnit,
												function.getStart(),
												function.getEnd());

								if (func instanceof FullyQualifiedReference) {
									functionName = ((FullyQualifiedReference) func)
											.getFullyQualifiedName();
								}
								// look for the element in current namespace
								if (functionName.indexOf('\\') == -1) {
									IType currentNamespace = PHPModelUtils
											.getCurrentNamespace(
													field.getSourceModule(),
													function.getStart());
									String fullyQualifiedFuncName = ""; //$NON-NLS-1$
									if (currentNamespace != null) {
										fullyQualifiedFuncName = "\\" //$NON-NLS-1$
												+ currentNamespace
														.getElementName()
												+ "\\" + functionName; //$NON-NLS-1$
									} else {
										fullyQualifiedFuncName = functionName;
									}

									IMethod[] methods = PHPModelUtils
											.getFunctions(
													fullyQualifiedFuncName,
													field.getSourceModule(),
													function.getStart(), null);
									if (methods != null && methods.length > 0) {
										functionName = fullyQualifiedFuncName;
									}
								}
							}
						} else if (function instanceof Identifier) {
							functionName = ((Identifier) function).getName();
						}
						if (functionName.equalsIgnoreCase("define") //$NON-NLS-1$
								&& invocation.parameters().size() >= 2
								&& invocation.parameters().get(1) instanceof Scalar) {
							constantValue = ((Scalar) invocation.parameters()
									.get(1)).getStringValue();
						}
					}
				}
			} catch (ModelException e) {
			} catch (IOException e) {
			}
		}

		if (constantValue == null)
			return null;

		if (constantValue instanceof String) {
			StringBuffer result = new StringBuffer();
			String stringConstant = (String) constantValue;
			if (stringConstant.length() > 80) {
				result.append(stringConstant.substring(0, 80));
				result.append(ScriptElementLabels.ELLIPSIS_STRING);
			} else {
				result.append(stringConstant);
			}
			return result.toString();

		} else if (constantValue instanceof Integer) {
			int intValue = ((Integer) constantValue).intValue();
			return formatWithHexValue(constantValue,
					"0x" + Integer.toHexString(intValue)); //$NON-NLS-1$
		} else {
			return constantValue.toString();
		}
	}

	/**
	 * Creates and returns a formatted message for the given constant with its
	 * hex value.
	 * 
	 * @param constantValue
	 *            the constant value
	 * @param hexValue
	 *            the hex value
	 * @return a formatted string with constant and hex values
	 * @since 3.4
	 */
	private static String formatWithHexValue(Object constantValue,
			String hexValue) {
		return Messages.format(
				PHPHoverMessages.JavadocHover_constantValue_hexValue,
				new String[] { constantValue.toString(), hexValue });
	}

	/**
	 * Returns the Javadoc hover style sheet with the current Javadoc font from
	 * the preferences.
	 * 
	 * @return the updated style sheet
	 * @since 3.4
	 */
	protected static String getStyleSheet() {
		if (fgStyleSheet == null)
			fgStyleSheet = loadStyleSheet();
		String css = fgStyleSheet;
		if (css != null) {
			FontData fontData = JFaceResources.getFontRegistry().getFontData(
					PreferenceConstants.APPEARANCE_DOCUMENTATION_FONT)[0];
			css = HTMLPrinter.convertTopLevelFont(css, fontData);
		}

		return css;
	}

	/**
	 * Loads and returns the Javadoc hover style sheet.
	 * 
	 * @return the style sheet, or <code>null</code> if unable to load
	 * @since 3.4
	 */
	private static String loadStyleSheet() {
		Bundle bundle = Platform.getBundle(PHPUiPlugin.getPluginId());
		URL styleSheetURL = bundle
				.getEntry("/PHPDocumentationHoverStyleSheet.css"); //$NON-NLS-1$
		if (styleSheetURL != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
						styleSheetURL.openStream()));
				StringBuffer buffer = new StringBuffer(1500);
				String line = reader.readLine();
				while (line != null) {
					buffer.append(line);
					buffer.append('\n');
					line = reader.readLine();
				}
				return buffer.toString();
			} catch (IOException ex) {
				PHPUiPlugin.log(ex);
				return ""; //$NON-NLS-1$
			} finally {
				try {
					if (reader != null)
						reader.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public static void addImageAndLabel(StringBuffer buf, String imageName,
			int imageWidth, int imageHeight, int imageLeft, int imageTop,
			String label, int labelLeft, int labelTop) {
		addImageAndLabel(buf, imageName, imageWidth, imageHeight, imageLeft,
				imageTop, label, labelLeft, labelTop, true);
	}

	private static void addImageAndLabel(StringBuffer buf, String imageName,
			int imageWidth, int imageHeight, int imageLeft, int imageTop,
			String label, int labelLeft, int labelTop, boolean isFirstElement) {

		// workaround to make the window wide enough
		label = label + "&nbsp"; //$NON-NLS-1$
		if (imageName != null) {
			StringBuffer imageStyle = new StringBuffer("position: absolute; "); //$NON-NLS-1$
			imageStyle.append("width: ").append(imageWidth).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			imageStyle.append("height: ").append(imageHeight).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			if (isFirstElement) {
				imageStyle.append("top: ").append(imageTop).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
				imageStyle.append("left: ").append(imageLeft).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				imageStyle
						.append("margin-top: ").append(imageTop).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
				imageStyle
						.append("margin-left: ").append(-imageLeft).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			}
			buf.append("<!--[if lte IE 6]><![if gte IE 5.5]>\n"); //$NON-NLS-1$
			buf.append("<span style=\"").append(imageStyle).append("filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='").append(imageName).append("')\"></span>\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			buf.append("<![endif]><![endif]-->\n"); //$NON-NLS-1$

			buf.append("<!--[if !IE]>-->\n"); //$NON-NLS-1$
			buf.append("<img style='").append(imageStyle).append("' src='").append(imageName).append("'/>\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			buf.append("<!--<![endif]-->\n"); //$NON-NLS-1$
			buf.append("<!--[if gte IE 7]>\n"); //$NON-NLS-1$
			buf.append("<img style='").append(imageStyle).append("' src='").append(imageName).append("'/>\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			buf.append("<![endif]-->\n"); //$NON-NLS-1$
		}

		buf.append("<div style='word-wrap:break-word;"); //$NON-NLS-1$
		if (imageName != null) {
			buf.append("margin-left: ").append(labelLeft).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			buf.append("margin-top: ").append(labelTop).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buf.append("'>"); //$NON-NLS-1$
		buf.append(label);
		buf.append("</div>"); //$NON-NLS-1$
	}

	protected static Reader getHTMLContent(IMember curr) throws ModelException {
		String html = PHPDocumentationContentAccess.getHTMLContent(curr);
		if (html != null) {
			return new StringReader(html);
		}
		return null;
		// return ScriptDocumentationAccess.getHTMLContentReader(PHPNature.ID,
		// curr, true, true);
	}

	private static StringBuffer getInfoText(IModelElement member) {
		long flags = member.getElementType() == IModelElement.FIELD ? LOCAL_VARIABLE_FLAGS
				: LABEL_FLAGS;
		String label = ScriptElementLabels.getDefault().getElementLabel(member,
				flags);
		return new StringBuffer(label);
	}
}
