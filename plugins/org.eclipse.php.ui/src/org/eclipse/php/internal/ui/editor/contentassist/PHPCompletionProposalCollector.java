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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.text.completion.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.CompletionFlag;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class PHPCompletionProposalCollector extends
		ScriptCompletionProposalCollector implements IPHPCompletionRequestor {

	private static final String DOUBLE_COLON = "::";//$NON-NLS-1$
	private static final String EMPTY_STRING = "";//$NON-NLS-1$
	private IDocument document;
	private boolean explicit;
	private int offset;
	private int flags = CompletionFlag.DEFAULT;

	public PHPCompletionProposalCollector(IDocument document, ISourceModule cu,
			boolean explicit) {
		super(cu);
		this.document = document;
		this.explicit = explicit;
	}

	protected ScriptCompletionProposal createOverrideCompletionProposal(
			IScriptProject scriptProject, ISourceModule compilationUnit,
			String name, String[] paramTypes, int start, int length,
			String label, String string) {
		return new PHPOverrideCompletionProposal(scriptProject,
				compilationUnit, name, paramTypes, start, length, label, string);
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i) {
		return new PHPCompletionProposal(completion, replaceStart, length,
				image, displayString, i);
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i, boolean isInDoc) {
		return new PHPCompletionProposal(completion, replaceStart, length,
				image, displayString, i, isInDoc);
	}

	// protected CompletionProposalLabelProvider createLabelProvider() {
	// CompletionProposalLabelProvider labelProvider = new
	// PHPCompletionProposalLabelProvider();
	//
	// // check if there are any adapters extending basic label provider
	// CompletionProposalLabelProvider extended =
	// (CompletionProposalLabelProvider) Platform
	// .getAdapterManager().getAdapter(labelProvider,
	// CompletionProposalLabelProvider.class);
	//
	// if (extended != null)
	// return extended;
	//
	// return labelProvider;
	// }

	protected IScriptCompletionProposal createPackageProposal(
			CompletionProposal proposal) {
		final AbstractScriptCompletionProposal scriptProposal = (AbstractScriptCompletionProposal) super
				.createPackageProposal(proposal);
		final IModelElement modelElement = proposal.getModelElement();
		if (modelElement != null) {
			scriptProposal.setProposalInfo(new ProposalInfo(modelElement
					.getScriptProject(), new String(proposal.getName())));
		}
		return scriptProposal;
	}

	protected IScriptCompletionProposal createKeywordProposal(
			CompletionProposal proposal) {
		AbstractScriptCompletionProposal scriptProposal = (AbstractScriptCompletionProposal) super
				.createKeywordProposal(proposal);
		final IModelElement modelElement = proposal.getModelElement();
		if (modelElement != null
				&& modelElement.getElementType() == IModelElement.SOURCE_MODULE) {
			scriptProposal.setImage(PHPPluginImages
					.get(PHPPluginImages.IMG_OBJS_PHP_FILE));
		}
		return scriptProposal;
	}

	protected IScriptCompletionProposal createScriptCompletionProposal(
			CompletionProposal proposal) {
		ScriptCompletionProposal completionProposal;
		if (proposal.getKind() == CompletionProposal.METHOD_DECLARATION) {
			completionProposal = createMethodDeclarationProposal(proposal);
		} else if (proposal.getKind() == CompletionProposal.TYPE_REF) {
			completionProposal = (ScriptCompletionProposal) createTypeProposal(proposal);
		} else {
			completionProposal = (ScriptCompletionProposal) super
					.createScriptCompletionProposal(proposal);
		}
		if (proposal.getKind() == CompletionProposal.METHOD_DECLARATION) {
			IMethod method = (IMethod) proposal.getModelElement();
			try {
				if (method.isConstructor()) {
					// replace method icon with class icon:
					int flags = proposal.getFlags();
					ImageDescriptor typeImageDescriptor = ScriptElementImageProvider
							.getTypeImageDescriptor(flags, false);
					int adornmentFlags = ScriptElementImageProvider
							.computeAdornmentFlags(
									method.getDeclaringType(),
									ScriptElementImageProvider.SMALL_ICONS
											| ScriptElementImageProvider.OVERLAY_ICONS);
					ScriptElementImageDescriptor descriptor = new ScriptElementImageDescriptor(
							typeImageDescriptor, adornmentFlags,
							ScriptElementImageProvider.SMALL_SIZE);
					completionProposal.setImage(getImage(descriptor));
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		return completionProposal;
	}

	protected char[] getVarTrigger() {
		// variable proposal will be inserted automatically if one of these
		// characters
		// is being typed in showing proposal time:
		return null;
	}

	public IDocument getDocument() {
		return document;
	}

	public boolean isExplicit() {
		return explicit;
	}

	private ScriptCompletionProposal createMethodDeclarationProposal(
			CompletionProposal proposal) {
		if (getSourceModule() == null
				|| getSourceModule().getScriptProject() == null) {
			return null;
		}

		String name = String.valueOf(proposal.getName());

		String[] paramTypes;

		paramTypes = new String[0];

		int start = proposal.getReplaceStart();
		int length = getLength(proposal);
		String label = ((PHPCompletionProposalLabelProvider) getLabelProvider())
				.createOverrideMethodProposalLabel(proposal);
		ScriptCompletionProposal scriptProposal = createParameterGuessingProposal(
				proposal, getSourceModule().getScriptProject(),
				getSourceModule(), name, paramTypes, start, length, label,
				String.valueOf(proposal.getCompletion()),
				proposal.getExtraInfo());
		scriptProposal.setImage(getImage(getLabelProvider()
				.createMethodImageDescriptor(proposal)));

		ProposalInfo info = new MethodProposalInfo(getSourceModule()
				.getScriptProject(), proposal);
		scriptProposal.setProposalInfo(info);

		scriptProposal.setRelevance(computeRelevance(proposal));
		return scriptProposal;
	}

	private ScriptCompletionProposal createParameterGuessingProposal(
			CompletionProposal proposal, IScriptProject scriptProject,
			ISourceModule sourceModule, String name, String[] paramTypes,
			int start, int length, String label, String string, Object extraInfo) {
		return new ParameterGuessingProposal(proposal, scriptProject,
				sourceModule, name, paramTypes, start, length, label, string,
				false, extraInfo, document);
	}

	protected IScriptCompletionProposal createTypeProposal(
			final CompletionProposal typeProposal) {

		String completion = new String(typeProposal.getCompletion());
		int replaceStart = typeProposal.getReplaceStart();
		// int length = typeProposal.getReplaceEnd()
		// - typeProposal.getReplaceStart() + 1;
		int length = getLength(typeProposal);
		Image image = getImage(((PHPCompletionProposalLabelProvider) getLabelProvider())
				.createTypeImageDescriptor(typeProposal));

		String displayString = ((PHPCompletionProposalLabelProvider) getLabelProvider())
				.createTypeProposalLabel(typeProposal);

		// return new PHPCompletionProposal(completion, replaceStart, length,
		// image, displayString, 0);
		ScriptCompletionProposal scriptProposal = new PHPCompletionProposal(
				completion, replaceStart, length, image, displayString, 0) {
			private boolean fReplacementStringComputed = false;

			public String getReplacementString() {
				if (!fReplacementStringComputed) {
					String replacementString = computeReplacementString();
					if (ProposalExtraInfo.isAddQuote(typeProposal
							.getExtraInfo())) {
						replacementString = "'" + replacementString + "'"; //$NON-NLS-1$ //$NON-NLS-2$
					}
					setReplacementString(replacementString);
				}
				return super.getReplacementString();
			}

			private String computeReplacementString() {
				fReplacementStringComputed = true;
				IType type = (IType) typeProposal.getModelElement();

				if (ProposalExtraInfo.isClassInNamespace(typeProposal
						.getExtraInfo())) {
					return PHPModelUtils.getFullName(type);
					// String result = PHPModelUtils.getFullName(type);
					// if (ProposalExtraInfo.isAddQuote(typeProposal
					// .getExtraInfo())) {
					// result = "'" + result + "'";
					// }
					// return result;
				}

				String prefix = ""; //$NON-NLS-1$
				try {
					int flags = type.getFlags();
					IType currentNamespace = PHPModelUtils
							.getCurrentNamespaceIfAny(getSourceModule(),
									getReplacementOffset());
					IType namespace = PHPModelUtils.getCurrentNamespace(type);
					if (!PHPFlags.isNamespace(flags)
							&& namespace == null
							&& currentNamespace != null
							&& !ProjectOptions.getPhpVersion(
									PHPCompletionProposalCollector.this
											.getScriptProject().getProject())
									.isLessThan(PHPVersion.PHP5_3)
							&& PHPCompletionProposalCollector.this.document
									.getChar(getReplacementOffset() - 1) != NamespaceReference.NAMESPACE_SEPARATOR) {
						prefix = prefix
								+ NamespaceReference.NAMESPACE_SEPARATOR;
					}
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
				} catch (BadLocationException e) {
					PHPUiPlugin.log(e);
				}
				String suffix = getSuffix(type);
				String replacementString = null;
				if (typeProposal.getModelElement() instanceof AliasType) {
					replacementString = ((AliasType) typeProposal
							.getModelElement()).getAlias();
				} else {
					replacementString = super.getReplacementString();
				}
				return prefix + replacementString + suffix;
			}

			public String getSuffix(IType type) {
				String defaultResult = EMPTY_STRING;
				if (type instanceof AliasType) {
				}
				if (ProposalExtraInfo.isTypeOnly(typeProposal.getExtraInfo())
						|| !PHPModelUtils.hasStaticOrConstMember(type)) {
					return defaultResult;
				}
				String nextWord = null;
				try {
					nextWord = document.get(getReplacementOffset(), 2);// "::".length()
				} catch (BadLocationException e) {
				}
				return DOUBLE_COLON.equals(nextWord) ? defaultResult
						: DOUBLE_COLON;
			}

			@Override
			public Object getExtraInfo() {
				return typeProposal.getExtraInfo();
			}
		};

		scriptProposal.setRelevance(computeRelevance(typeProposal));
		scriptProposal.setProposalInfo(new TypeProposalInfo(getSourceModule()
				.getScriptProject(), typeProposal));
		return scriptProposal;

	}

	protected IScriptCompletionProposal createFieldProposal(
			final CompletionProposal proposal) {
		String completion = String.valueOf(proposal.getCompletion());
		int start = proposal.getReplaceStart();
		int length = getLength(proposal);
		String label = ((PHPCompletionProposalLabelProvider) getLabelProvider())
				.createFieldProposalLabel(proposal);
		Image image = getImage(((PHPCompletionProposalLabelProvider) getLabelProvider())
				.createFieldImageDescriptor(proposal));
		int relevance = computeRelevance(proposal);
		// CompletionContext context = getContext();
		// ScriptCompletionProposal scriptProposal =
		// createScriptCompletionProposal(
		// completion, start, length, image, label, relevance, /*
		// * context
		// * .isInDoc
		// * ()
		// */false);
		ScriptCompletionProposal scriptProposal = new PHPCompletionProposal(
				completion, start, length, image, label, relevance) {
			private boolean fReplacementStringComputed = false;

			public String getReplacementString() {
				if (!fReplacementStringComputed) {
					String replacementString = super.getReplacementString();
					if (ProposalExtraInfo.isAddQuote(proposal.getExtraInfo())) {
						replacementString = "'" + replacementString + "'"; //$NON-NLS-1$ //$NON-NLS-2$
					}
					setReplacementString(replacementString);
				}
				fReplacementStringComputed = true;
				return super.getReplacementString();
			}

			@Override
			public Object getExtraInfo() {
				return proposal.getExtraInfo();
			}
		};
		if (getSourceModule().getScriptProject() != null)
			scriptProposal.setProposalInfo(new FieldProposalInfo(
					getSourceModule().getScriptProject(), proposal));
		scriptProposal.setTriggerCharacters(getVarTrigger());
		return scriptProposal;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	protected String getNatureId() {
		return PHPNature.ID;
	}

	@Override
	public int computeRelevance(CompletionProposal proposal) {
		// if (ProposalExtraInfo.STUB.equals(proposal.getExtraInfo())) {
		// return Integer.MAX_VALUE;
		// }
		if (proposal.getModelElement() instanceof IMethod
				&& ProposalExtraInfo.isMagicMethod(proposal.getExtraInfo())) {
			return -1;
		}
		if (proposal.getModelElement() instanceof IField
				&& ProposalExtraInfo.isMagicMethod(proposal.getExtraInfo())) {
			return Integer.MAX_VALUE;
		}
		return super.computeRelevance(proposal);
	}

	public boolean filter(int flag) {
		return (flags & flag) != 0;
	}

	public void addFlag(int flag) {
		flags |= flag;
	}
}
