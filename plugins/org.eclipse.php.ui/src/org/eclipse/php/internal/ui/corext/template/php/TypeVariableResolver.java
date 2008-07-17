/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.template.php;

import java.util.List;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

import org.eclipse.jdt.core.Signature;

import org.eclipse.jdt.internal.corext.template.java.CompilationUnitCompletion.Variable;

import org.eclipse.jdt.internal.ui.text.template.contentassist.MultiVariable;

/**
 * Resolves to the lower bound of a type argument of another template variable.
 * 
 * @since 3.3
 */
public class TypeVariableResolver extends TemplateVariableResolver {

	public TypeVariableResolver() {
	}

	/*
	 * @see org.eclipse.jface.text.templates.TemplateVariableResolver#resolve(org.eclipse.jface.text.templates.TemplateVariable, org.eclipse.jface.text.templates.TemplateContext)
	 * @since 3.3
	 */
	public void resolve(TemplateVariable variable, TemplateContext context) {
		if (!(variable instanceof MultiVariable)) {
			super.resolve(variable, context);
			return;
		}
		MultiVariable mv= (MultiVariable) variable;
		List params= variable.getVariableType().getParams();
		if (params.isEmpty()) {
			super.resolve(variable, context);
			return;
		}
		
		PhpContext jc= (PhpContext) context;
		String reference= (String) params.get(0);
		int index= 0;
		if (params.size() > 1) {
			String indexParam= (String) params.get(1);
			try {
				index= Integer.parseInt(indexParam);
			} catch (NumberFormatException x) {
			}
		}
		TemplateVariable refVar= jc.getTemplateVariable(reference);
		if (refVar instanceof PhpVariable) {
			PhpVariable jvar= (PhpVariable) refVar;
			resolve(mv, jvar, index, jc);
			
			return;
		}
		
		
		super.resolve(variable, context);
	}

	private void resolve(MultiVariable mv, PhpVariable master, int index, PhpContext context) {
		Object[] choices= master.getChoices();
		if (choices instanceof Variable[]) {
			context.addDependency(master, mv);
			Variable[] variables= (Variable[]) choices;
			String type= master.getParamType();
			for (int i= 0; i < choices.length; i++) {
				String[] bounds= variables[i].getTypeArgumentBoundSignatures(type, index);
				for (int j= 0; j < bounds.length; j++)
					bounds[j]= Signature.getSignatureSimpleName(bounds[j]);
				mv.setChoices(variables[i], bounds);
			}
			mv.setKey(master.getCurrentChoice());
		} else {
			super.resolve(mv, context);
			return; 
		}
	}
	
}
