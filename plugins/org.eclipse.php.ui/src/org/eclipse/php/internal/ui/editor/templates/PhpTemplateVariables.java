/*******************************************************************************
 * Copyright (c) 2010 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.codeassist.CompletionRequestorExtension;
import org.eclipse.php.internal.core.codeassist.contexts.ClassObjMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalStatementContext;
import org.eclipse.php.internal.core.codeassist.templates.CodeCompletionRequestor;
import org.eclipse.php.internal.core.codeassist.templates.contexts.GlobalMethodStatementContextForTemplate;
import org.eclipse.php.internal.core.codeassist.templates.contexts.GlobalStatementContextForTemplate;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class PhpTemplateVariables {

	public static class Index extends TemplateVariableResolver {
		public static final String NAME = "index"; //$NON-NLS-1$

		public Index() {
			super(NAME, Messages.PhpTemplateVariables_4);
		}

		protected String resolve(TemplateContext context) {
			ISourceModule module = ((ScriptTemplateContext) context)
					.getSourceModule();
			int offset = ((ScriptTemplateContext) context)
					.getCompletionOffset();
			CodeCompletionRequestor requestor = new VariableCodeCompletionRequestor();
			try {
				module.codeComplete(offset, requestor, 1000);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			String[] knownVars = requestor.getVariables();
			Set knownVarsSet = new HashSet(Arrays.asList(knownVars));

			return findUnusedName(knownVarsSet);
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	public static class NewVariable extends TemplateVariableResolver {
		public static final String NAME = "new_variable"; //$NON-NLS-1$

		public NewVariable() {
			super(
					NAME,
					Messages.PhpTemplateVariables_17);
		}

		protected String resolve(TemplateContext context) {
			return "dupcia"; //$NON-NLS-1$
		}

		public void resolve(TemplateVariable variable, TemplateContext context) {
			ISourceModule module = ((ScriptTemplateContext) context)
					.getSourceModule();
			int offset = ((ScriptTemplateContext) context)
					.getCompletionOffset();
			CodeCompletionRequestor requestor = new VariableCodeCompletionRequestor();
			try {
				module.codeComplete(offset, requestor, 1000);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			String[] knownVars = requestor.getVariables();
			Set knownVarsSet = new HashSet(Arrays.asList(knownVars));

			List params = variable.getVariableType().getParams();
			if (params.size() == 0) {
				String result = findUnusedName(knownVarsSet);
				variable.setValue(result);
				variable.setResolved(true);
			} else {
				String prefix = (String) params.get(0);
				String result = findUnusedName(prefix, knownVarsSet);
				variable.setValue(result);
				variable.setResolved(true);
			}
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	public static class Variable extends TemplateVariableResolver {
		public static final String NAME = "variable"; //$NON-NLS-1$

		public Variable() {
			super(NAME, Messages.PhpTemplateVariables_22);
		}

		protected String[] resolveAll(TemplateContext context) {
			ISourceModule module = ((ScriptTemplateContext) context)
					.getSourceModule();
			int offset = ((ScriptTemplateContext) context)
					.getCompletionOffset();
			CodeCompletionRequestor requestor = new VariableCodeCompletionRequestor();
			try {
				module.codeComplete(offset, requestor, 1000);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			return requestor.getVariables();
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	public static class Encoding extends TemplateVariableResolver {
		public static final String NAME = "encoding"; //$NON-NLS-1$

		public Encoding() {
			super(NAME, Messages.PhpTemplateVariables_23);
		}

		protected String resolve(TemplateContext context) {
			String path = null;

			try {
				ISourceModule module = getSourceModule(context);
				if (module == null)
					return null;

				IFile file = (IFile) module.getResource();
				return file.getCharset();

			} catch (CoreException e) {
				PHPUiPlugin.log(e);
			}

			return path;
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	public static class FunctionContainer extends TemplateVariableResolver {
		public static final String NAME = "function_container"; //$NON-NLS-1$

		public FunctionContainer() {
			super(NAME, Messages.PhpTemplateVariables_24);
		}

		protected String resolve(TemplateContext context) {
			ISourceModule module = getSourceModule(context);
			if (module == null)
				return null;

			int position = ((ScriptTemplateContext) context)
					.getCompletionOffset();
			try {
				IModelElement element = module.getElementAt(position);
				while ((element != null)
						&& (element.getElementType() != IModelElement.METHOD)) {
					element = element.getParent();
				}

				return element == null ? null : element.getElementName();
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			return null;
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	public static class ClassContainer extends TemplateVariableResolver {
		public static final String NAME = "class_container"; //$NON-NLS-1$

		public ClassContainer() {
			super(NAME, Messages.PhpTemplateVariables_25);
		}

		protected String resolve(TemplateContext context) {
			ISourceModule module = getSourceModule(context);
			if (module == null)
				return null;

			int position = ((ScriptTemplateContext) context)
					.getCompletionOffset();
			try {
				IModelElement element = module.getElementAt(position);
				while ((element != null)
						&& (element.getElementType() != IModelElement.TYPE)) {
					element = element.getParent();
				}

				return element == null ? null : element.getElementName();
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			return null;
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	public static class Class extends TemplateVariableResolver {
		public static final String NAME = "class"; //$NON-NLS-1$

		public Class() {
			super(NAME, Messages.PhpTemplateVariables_26);
		}

		protected String[] resolveAll(TemplateContext context) {
			ISourceModule module = ((ScriptTemplateContext) context)
					.getSourceModule();
			int offset = ((ScriptTemplateContext) context)
					.getCompletionOffset();
			CodeCompletionRequestor requestor = new CodeCompletionRequestor() {
				@Override
				public void accept(CompletionProposal proposal) {
					if (isIgnored(proposal.getKind()))
						return;
					switch (proposal.getKind()) {
					case CompletionProposal.TYPE_REF:
						try {
							if (!PHPFlags.isNamespace(((IType) proposal
									.getModelElement()).getFlags())) {
								addProposal(proposal);
							}
						} catch (ModelException e) {
						}

						break;
					default:
						break;
					}
				}

				public ICompletionContext[] createContexts() {
					return new ICompletionContext[] { new GlobalStatementContext() };
				}
			};
			try {
				module.codeComplete(offset, requestor, 1000);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			return requestor.getVariables();
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	public static class NumberVariable extends TemplateVariableResolver {
		public static final String NAME = "number_variable"; //$NON-NLS-1$

		public NumberVariable() {
			super(NAME, Messages.PhpTemplateVariables_27);
		}

		protected String resolve(TemplateContext context) {
			return "dupcia"; //$NON-NLS-1$
		}

		public void resolve(TemplateVariable variable, TemplateContext context) {
			ISourceModule module = ((ScriptTemplateContext) context)
					.getSourceModule();
			int offset = ((ScriptTemplateContext) context)
					.getCompletionOffset();
			CodeCompletionRequestor requestor = new VariableCodeCompletionRequestor();
			try {
				module.codeComplete(offset, requestor, 1000);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			String[] knownVars = requestor.getVariables();
			Set knownVarsSet = new HashSet(Arrays.asList(knownVars));

			List params = variable.getVariableType().getParams();
			String result = "$" + findUnusedName("index", knownVarsSet); //$NON-NLS-1$ //$NON-NLS-2$
			variable.setValue(result);
			variable.setResolved(true);
		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	private static ISourceModule getSourceModule(TemplateContext context) {
		return ((ScriptTemplateContext) context).getSourceModule();
	}

	/*
	 * Produces names in following order, skipping all already listed in
	 * knownNames set: i, j, j2, k, k2, ..., z, z2, a, a2, ..., h, h2, i1, i2,
	 * i3, ..., i2147483647
	 */
	private static String findUnusedName(Set knownNames) {
		int base = 'i' - 'a';
		int counter = -1;
		int maxCounter = 2;
		String proposal = String.valueOf((char) ('a' + base));

		while (knownNames.contains(proposal)) {
			counter = (counter + 1) % (maxCounter);

			if (counter == 0) {
				base = ((base + 1) % ('z' - 'a' + 1));
				if (base == 'i' - 'a') {
					maxCounter = Integer.MAX_VALUE;
				}
			}

			proposal = String.valueOf((char) ('a' + base));
			if (counter > 0) {
				proposal += (counter + 1);
			}
		}

		return proposal;
	}

	private static String findUnusedName(String prefix, Set knownNames) {
		int counter = 1;

		String proposal = prefix;
		while (knownNames.contains(proposal)) {
			proposal = prefix + counter;
			counter++;
		}

		return proposal;
	}

	public static class VariableCodeCompletionRequestor extends
			CodeCompletionRequestor implements CompletionRequestorExtension {

		public VariableCodeCompletionRequestor() {
		}

		@Override
		public void accept(CompletionProposal proposal) {
			if (isIgnored(proposal.getKind()))
				return;

			switch (proposal.getKind()) {
			case CompletionProposal.LOCAL_VARIABLE_REF:
				addProposal(proposal);
				break;
			case CompletionProposal.FIELD_REF:
				addProposal(proposal);
				break;

			default:
				break;
			}
		}

		public ICompletionContext[] createContexts() {
			return new ICompletionContext[] { new ClassObjMemberContext(),
					new GlobalMethodStatementContextForTemplate(),
					new GlobalStatementContextForTemplate(), };
		}
	}

	public static class Namespace extends TemplateVariableResolver {
		public static final String NAME = "namespace"; //$NON-NLS-1$

		public Namespace() {
			super(NAME, Messages.PhpTemplateVariables_31);
		}

		protected String resolve(TemplateContext context) {
			String path = null;

			ISourceModule module = getSourceModule(context);
			if (module == null)
				return null;
			IModelElement parent = module.getParent();
			path = parent.getElementName();
			while (!(parent instanceof IProjectFragment)) {
				parent = parent.getParent();
				if (parent.getElementName().length() == 0)
					break;
				path = parent.getElementName() + "\\" + path; //$NON-NLS-1$
			}
			return path;

		}

		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

}
