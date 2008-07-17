/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.template.php;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;


/**
 * Resolves template variables to variables which are assignment-compatible with the variable
 * instance class parameters.
 * 
 * @since 3.4
 */
public abstract class AbstractVariableResolver extends TemplateVariableResolver {

	protected final String fDefaultType;
	private IType[] fTypes;

	/**
	 * Create a variable resolver resolving to
	 * <code>defaultType</code> if no types specified
	 * as parameter
	 * @param defaultType the default type to resolve to
	 */
	protected AbstractVariableResolver(String defaultType) {
		fDefaultType= defaultType;
	}
	
	/**
	 * Returns a set of variables of <code>type</code> visible in
	 * the given <code>context</code>.
	 * 
	 * @param type the type name to search variables for
	 * @param context context within to search for variables
	 * @return the visible variables of <code>type</code> in <code>context</code>, empty array if no visible variables
	 */
	protected abstract IType[] getVisibleVariables(String type, PhpContext context);
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateVariableResolver#resolve(org.eclipse.jface.text.templates.TemplateVariable, org.eclipse.jface.text.templates.TemplateContext)
	 */
	public void resolve(TemplateVariable variable, TemplateContext context) {
		
		if (variable instanceof PhpVariable) {
			PhpContext jc= (PhpContext) context;
			PhpVariable jv= (PhpVariable) variable;
	
			List params= variable.getVariableType().getParams();
			if (params.size() == 0) {
				fTypes= getVisibleVariables(fDefaultType, jc);
				jv.setParamType(fDefaultType);
			} else if (params.size() == 1) {
				String type= (String) params.get(0);
				fTypes= getVisibleVariables(type, jc);
				jv.setParamType(type);
			} else {
				ArrayList variables= new ArrayList();
				for (Iterator iterator= params.iterator(); iterator.hasNext();) {
					variables.addAll(Arrays.asList(getVisibleVariables((String) iterator.next(), jc)));
				}
				fTypes= (IType[]) variables.toArray(new IType[variables.size()]);
				
				//set to default type, a template which references to the type
				//of _the_ parameter will not correctly work anyway
				jv.setParamType(fDefaultType);
			}
			
			if (fTypes.length > 0) {
				jv.setChoices(fTypes);
				jc.markAsUsed(jv.getDefaultValue());
			} else {
				super.resolve(variable, context);
				return;
			}
			if (fTypes.length > 1)
				variable.setUnambiguous(false);
			else
				variable.setUnambiguous(isUnambiguous(context));
		} else
			super.resolve(variable, context);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateVariableResolver#resolveAll(org.eclipse.jface.text.templates.TemplateContext)
	 */
	protected String[] resolveAll(TemplateContext context) {
	    
		String[] names= new String[fTypes.length];
	    for (int i= 0; i < fTypes.length; i++)
			names[i]= fTypes[i].getElementName();
	    
	    if (names.length > 0)
	    	((PhpContext)context).markAsUsed(names[0]);
	    
		return names;
	}

}
