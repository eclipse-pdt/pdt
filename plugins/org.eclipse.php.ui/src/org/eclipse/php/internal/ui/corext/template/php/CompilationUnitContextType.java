/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.template.php;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;


/**
 * Compilation unit context type.
 */
public abstract class CompilationUnitContextType extends TemplateContextType {
	
	protected static class ReturnType extends TemplateVariableResolver {
	 	public ReturnType() {
	 	 	super("return_type", PhpTemplateMessages.CompilationUnitContextType_variable_description_return_type);  //$NON-NLS-1$
	 	}
	 	protected String resolve(TemplateContext context) {
			IJavaElement element= ((CompilationUnitContext) context).findEnclosingElement(IJavaElement.METHOD);
			if (element == null)
				return null;

			try {
				return Signature.toString(((IMethod) element).getReturnType());
			} catch (JavaModelException e) {
				return null;
			}
		}
	}

	protected static class File extends TemplateVariableResolver {
		public File() {
			super("file", PhpTemplateMessages.CompilationUnitContextType_variable_description_file);  //$NON-NLS-1$
		}
		protected String resolve(TemplateContext context) {
			ICompilationUnit unit= ((CompilationUnitContext) context).getCompilationUnit();
			
			return (unit == null) ? null : unit.getElementName();
		}
	 	
		/*
		 * @see org.eclipse.jface.text.templates.TemplateVariableResolver#isUnambiguous(org.eclipse.jface.text.templates.TemplateContext)
		 */
		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}
	
	protected static class PrimaryTypeName extends TemplateVariableResolver {
		public PrimaryTypeName() {
			super("primary_type_name", PhpTemplateMessages.CompilationUnitContextType_variable_description_primary_type_name);  //$NON-NLS-1$
			
		}
		protected String resolve(TemplateContext context) {
			ICompilationUnit unit= ((CompilationUnitContext) context).getCompilationUnit();
			if (unit == null) 
				return null;
			return JavaCore.removeJavaLikeExtension(unit.getElementName());
		}
	 	
		/*
		 * @see org.eclipse.jface.text.templates.TemplateVariableResolver#isUnambiguous(org.eclipse.jface.text.templates.TemplateContext)
		 */
		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}

	protected static class EnclosingJavaElement extends TemplateVariableResolver {
		protected final int fElementType;
		
		public EnclosingJavaElement(String name, String description, int elementType) {
			super(name, description);
			fElementType= elementType;
		}
		protected String resolve(TemplateContext context) {
			IJavaElement element= ((CompilationUnitContext) context).findEnclosingElement(fElementType);
			return (element == null) ? null : element.getElementName();			
		}
	 	
		/*
		 * @see org.eclipse.jface.text.templates.TemplateVariableResolver#isUnambiguous(org.eclipse.jface.text.templates.TemplateContext)
		 */
		protected boolean isUnambiguous(TemplateContext context) {
			return resolve(context) != null;
		}
	}
	
	protected static class Method extends EnclosingJavaElement {
		public Method() {
			super("enclosing_method", PhpTemplateMessages.CompilationUnitContextType_variable_description_enclosing_method, IJavaElement.METHOD);  //$NON-NLS-1$
		}
	}

	protected static class Type extends EnclosingJavaElement {
		public Type() {
			super("enclosing_type", PhpTemplateMessages.CompilationUnitContextType_variable_description_enclosing_type, IJavaElement.TYPE);  //$NON-NLS-1$
		}
	}
/*
	protected static class SuperClass extends EnclosingJavaElement {
		public Type() {
			super("super_class", TemplateMessages.getString("PhpContextType.variable.description.type"), IJavaElement.TYPE);
		}
	}
*/
	protected static class Package extends EnclosingJavaElement {
		public Package() {
			super("enclosing_package", PhpTemplateMessages.CompilationUnitContextType_variable_description_enclosing_package, IJavaElement.PACKAGE_FRAGMENT);  //$NON-NLS-1$
		}
	}	

	protected static class Project extends EnclosingJavaElement {
		public Project() {
			super("enclosing_project", PhpTemplateMessages.CompilationUnitContextType_variable_description_enclosing_project, IJavaElement.JAVA_PROJECT);  //$NON-NLS-1$
		}
	}	
/*
	protected static class Project2 extends TemplateVariableResolver {
		public Project2() {
			super("project", TemplateMessages.getString("PhpContextType.variable.description.project"));
		}
		public String evaluate(TemplateContext context) {
			ICompilationUnit unit= ((PhpContext) context).getUnit();
			return (unit == null) ? null : unit.getJavaProject().getElementName();
		}
	}	
*/
	protected static class Arguments extends TemplateVariableResolver {
		public Arguments() {
			super("enclosing_method_arguments", PhpTemplateMessages.CompilationUnitContextType_variable_description_enclosing_method_arguments);  //$NON-NLS-1$
		}
		protected String resolve(TemplateContext context) {
			IJavaElement element= ((CompilationUnitContext) context).findEnclosingElement(IJavaElement.METHOD);
			if (element == null)
				return null;
				
			IMethod method= (IMethod) element;
			
			try {
				String[] arguments= method.getParameterNames();
				StringBuffer buffer= new StringBuffer();
				
				for (int i= 0; i < arguments.length; i++) {
					if (i > 0)
						buffer.append(", "); //$NON-NLS-1$
					buffer.append(arguments[i]);				
				}
				
				return buffer.toString();

			} catch (JavaModelException e) {
				return null;
			}
		}
	}

/*	
	protected static class Line extends TemplateVariableResolver {
		public Line() {
			super("line", TemplateMessages.getString("CompilationUnitContextType.variable.description.line"));
		}
		public String evaluate(TemplateContext context) {
			return ((JavaTemplateContext) context).guessLineNumber();
		}
	}
*/	

	/*
	 * @see ContextType#ContextType(String)
	 */
	public CompilationUnitContextType(String id) {
		super(id);	
	}

	public CompilationUnitContextType() {
	}

	public abstract CompilationUnitContext createContext(IDocument document, int completionPosition, int length, ICompilationUnit compilationUnit);
	public abstract CompilationUnitContext createContext(IDocument document, Position completionPosition, ICompilationUnit compilationUnit);

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.corext.template.ContextType#validateVariables(org.eclipse.jdt.internal.corext.template.TemplateVariable[])
	 */
	protected void validateVariables(TemplateVariable[] variables) throws TemplateException {
		// check for multiple cursor variables		
		for (int i= 0; i < variables.length; i++) {
			TemplateVariable var= variables[i];
			if (var.getType().equals(GlobalTemplateVariables.Cursor.NAME)) {
				if (var.getOffsets().length > 1) {
					throw new TemplateException(PhpTemplateMessages.ContextType_error_multiple_cursor_variables); 
				}
			}
		}
	}

}
