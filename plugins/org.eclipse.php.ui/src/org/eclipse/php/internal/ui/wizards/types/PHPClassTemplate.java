/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.types;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;

/**
 * This class represents the template for creating a PHP class code.
 * 
 */
public class PHPClassTemplate extends PHPElementTemplate {
	private String requiredPHPsBlock;

	public PHPClassTemplate() {
		super();
	}

	@Override
	public String getTemplatePath() {
		return TypeWizardConstants.CLASS_TEMPLATE_LOCATION;
	}

	@Override
	public String processTemplate(NewPHPElementData data) {
		// handle class default PHPDOC
		set(DEFAULT_PHPDOC_VAR, ""); //$NON-NLS-1$

		// handle superclass declaration
		extract(INPUT, SUPERCLASS_STRUCT, SUPERCLASS_STRUCT_COMPILED);
		set(SUPERCLASS_STRUCT, ""); //$NON-NLS-1$
		if (data.superClass != null) {
			String superClass = data.superClass.getElementName();
			if (!isImported(data, data.superClass)) {
				superClass = "\\" + superClass; //$NON-NLS-1$
			}
			set(SUPERCLASS_NAME_VAR, superClass);
			compile(SUPERCLASS_STRUCT_COMPILED, SUPERCLASS_STRUCT, true);
		}

		// handle interfaces declaration
		extract(INPUT, INTERFACES_STRUCT, INTERFACES_STRUCT_COMPILED);
		set(INTERFACES_STRUCT, ""); //$NON-NLS-1$
		if (data.interfaces.length > 0) {
			for (int i = 0; i < data.interfaces.length; i++) {
				String interfaceName = data.interfaces[i].getElementName();
				if (!isImported(data, data.interfaces[i])) {
					interfaceName = "\\" + interfaceName; //$NON-NLS-1$
				}
				// if not the last interface name, add the ','
				if ((i + 1) < data.interfaces.length) {
					interfaceName += ", "; //$NON-NLS-1$
				}
				set(INTERFACE_NAME_VAR, interfaceName);
				compile(INTERFACES_STRUCT_COMPILED, INTERFACES_STRUCT, true);
			}
		}
		extract(INPUT, IMPLEMENTS_STRUCT, IMPLEMENTS_STRUCT_COMPILED);
		set(IMPLEMENTS_STRUCT, ""); //$NON-NLS-1$
		if (data.interfaces.length > 0) {
			compile(IMPLEMENTS_STRUCT_COMPILED, IMPLEMENTS_STRUCT, true);
		}

		// handle traits declaration
		extract(INPUT, TRAITS_STRUCT, TRAITS_STRUCT_COMPILED);
		set(TRAITS_STRUCT, ""); //$NON-NLS-1$
		if (data.traits.length > 0) {
			for (int i = 0; i < data.traits.length; i++) {
				String interfaceName = data.traits[i].getElementName();
				if (!isImported(data, data.traits[i])) {
					interfaceName = "\\" + interfaceName; //$NON-NLS-1$
				}
				// if not the last interface name, add the ','
				if ((i + 1) < data.traits.length) {
					interfaceName += ", "; //$NON-NLS-1$
				}
				set(TRAIT_NAME_VAR, interfaceName);
				compile(TRAITS_STRUCT_COMPILED, TRAITS_STRUCT, true);
			}
		}
		extract(INPUT, USESTRAITS_STRUCT, USESTRAITS_STRUCT_COMPILED);
		set(USESTRAITS_STRUCT, ""); //$NON-NLS-1$
		if (data.traits.length > 0) {
			compile(USESTRAITS_STRUCT_COMPILED, USESTRAITS_STRUCT, true);
		}

		// handle methods content
		extract(INPUT, FUNC_PARAMS_STRUCT, FUNC_PARAMS_STRUCT_COMPILED);// extract
		// method
		// params
		extract(INPUT, FUNC_PHPDOC_PARAMS_STRUCT, FUNC_PHPDOC_PARAMS_STRUCT_COMPILED);// extract
																						// method
																						// phpdoc
		// params
		extract(INPUT, FUNC_PHPDOC_STRUCT, FUNC_PHPDOC_STRUCT_COMPILED);// extract
		// method
		// phpdoc
		extract(INPUT, FUNCTIONS_STRUCT, FUNCTIONS_STRUCT_COMPILED);// extract
		// method
		set(FUNC_PARAMS_STRUCT, ""); //$NON-NLS-1$
		set(FUNC_PHPDOC_STRUCT, ""); //$NON-NLS-1$
		set(FUNCTIONS_STRUCT, ""); //$NON-NLS-1$
		set(FUNCTION_MODIFIER_VAR, ""); //$NON-NLS-1$
		set(FUNCTION_STATIC_VAR, ""); //$NON-NLS-1$
		set(FUNCTION_PARENT_CALL, ""); //$NON-NLS-1$

		// handle constructor
		if (data.isGenerateConstructor
				&& !containsFunction(data.funcsToAdd, TypeWizardConstants.PHP5_CONSTRUCTOR_TEMPLATE)) {
			generateConstructor(data);
		}

		// generate methods !
		set(TODO_VAR, ""); //$NON-NLS-1$
		if (data.funcsToAdd.length > 0) {
			generateInheritedMethods(data, data.funcsToAdd);
			set(TODO_VAR, ""); //$NON-NLS-1$
		} else {
			// fixed bug 14453 - change the class template and add todo
			// comment for the class in case of no methods.
			if (data.isGenerateTODOs) {
				set(TODO_VAR, TODO_TEXT);
			}
		}

		// handle destructor
		if (data.isGenerateDestructor
				&& !containsFunction(data.funcsToAdd, TypeWizardConstants.PHP5_DESTRUCTOR_TEMPLATE)) {
			generateDestructor(data);
		}

		// handle class
		extract(INPUT, CLASS_STRUCT, CLASS_STRUCT_COMPILED);
		set(CLASS_STRUCT, ""); //$NON-NLS-1$
		set(ABSTRACT_VAR, ""); //$NON-NLS-1$
		set(FINAL_VAR, ""); //$NON-NLS-1$
		if (data.isFinal) {
			set(FINAL_VAR, FINAL_TEXT);
		}
		if (data.isAbstract) {
			set(ABSTRACT_VAR, ABSTRACT_TEXT);
		}

		// Generate the PHPDoc for class.
		if (data.isGeneratePHPDoc) {
			set(DEFAULT_PHPDOC_VAR, getDefaultPHPDoc());
		}

		set(CLASS_NAME_VAR, data.className);
		set(ELEMENT_TYPE_VAR, TypeWizardConstants.CLASS_TYPE);
		compile(CLASS_STRUCT_COMPILED, CLASS_STRUCT, false);

		// handle namespace
		extract(INPUT, NAMESPACE_STRUCT, NAMESPACE_STRUCT_COMPILED);
		set(NAMESPACE_STRUCT, ""); //$NON-NLS-1$

		if (data.namespace != null && !data.namespace.isEmpty()) {
			set(NAMESPACE_NAME, data.namespace);
			compile(NAMESPACE_STRUCT_COMPILED, NAMESPACE_STRUCT, true);
		}

		// handle requires list declaration
		handleRequires(data);

		// handle use
		extract(INPUT, USE_STRUCT, USE_STRUCT_COMPILED);
		set(USE_STRUCT, ""); //$NON-NLS-1$

		for (int i = 0; i < data.imports.length; i++) {
			set(USE, data.imports[i]);
			compile(USE_STRUCT_COMPILED, USE_STRUCT, true);
		}

		// handle namespace in existing file
		extract(CLASS_STRUCT, NAMESPACE_IN_FILE_STRUCT, NAMESPACE_IN_FILE_STRUCT_COMPILED);
		set(NAMESPACE_IN_FILE_STRUCT, ""); //$NON-NLS-1$

		// handle use in existing file
		extract(CLASS_STRUCT, USE_IN_FILE_STRUCT, USE_IN_STRUCT_COMPILED);
		set(USE_IN_FILE_STRUCT, ""); //$NON-NLS-1$

		if (data.isExistingFile && data.isInFirstBlock) {
			if (data.namespace != null && !data.namespace.isEmpty()) {
				set(NAMESPACE_NAME, data.namespace);
				compile(NAMESPACE_IN_FILE_STRUCT_COMPILED, NAMESPACE_IN_FILE_STRUCT, true);
			}
			for (int i = 0; i < data.imports.length; i++) {
				set(USE, data.imports[i]);
				compile(USE_IN_STRUCT_COMPILED, USE_IN_FILE_STRUCT, true);
			}
		}

		// handle php_content
		extract(INPUT, PHP_CONTENT_STRUCT, PHP_CONTENT_STRUCT_COMPILED);
		compile(PHP_CONTENT_STRUCT_COMPILED, PHP_CONTENT_STRUCT, false);

		// a new php file or a new block ==> include the PHP open/close tag in
		// string
		if (!data.isExistingFile || (data.isExistingFile && !data.isInFirstBlock)
				|| (data.isExistingFile && data.isInFirstBlock && !data.hasFirstBlock)) {
			compile(get(PHP_CONTENT_STRUCT));
			return compile(get(INPUT));
		}

		// an existing file, take only class code
		else {
			return compile(get(CLASS_STRUCT));
		}
	}

	private void generateInheritedMethods(NewPHPElementData data, IMethod[] methodsToOverride) {
		MethodDeclaration decl = null;
		ISourceModule sourceModule = null;
		for (int i = 0; i < methodsToOverride.length; i++) {
			IMethod func = methodsToOverride[i];

			sourceModule = func.getSourceModule();
			ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule);

			try {
				decl = PHPModelUtils.getNodeByMethod(module, func);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
				return;
			}

			// check for PHPDoc
			if (data.isGeneratePHPDoc) {
				set(FUNC_PHPDOC_PARAM_VAR, "(non-PHPdoc)"); //$NON-NLS-1$
				String targetTypeName = func.getDeclaringType()
						.getTypeQualifiedName(PHPModelUtils.ENCLOSING_TYPE_SEPARATOR);
				if (targetTypeName.indexOf(PHPModelUtils.ENCLOSING_TYPE_SEPARATOR) > 0) {
					targetTypeName = PHPModelUtils.ENCLOSING_TYPE_SEPARATOR + targetTypeName;
				}
				set(FUNC_PHPDOC_SEE_VAR, "* @see " + targetTypeName + "::" //$NON-NLS-1$ //$NON-NLS-2$
						+ func.getElementName() + "()\n"); //$NON-NLS-1$
				compile(FUNC_PHPDOC_PARAMS_STRUCT_COMPILED, FUNC_PHPDOC_PARAMS_STRUCT, false);
				compile(FUNC_PHPDOC_STRUCT_COMPILED, FUNC_PHPDOC_STRUCT, false);
			}

			// loop on all function's params
			List<?> paramsList = decl.getArguments();
			FormalParameter[] params = paramsList.toArray(new FormalParameter[paramsList.size()]);

			set(FUNC_PARAM_NAME_VAR, ""); //$NON-NLS-1$
			compile(FUNC_PARAMS_STRUCT_COMPILED, FUNC_PARAMS_STRUCT, false);

			for (int k = 0; k < params.length; k++) {
				SimpleReference referenceType = null;
				if (params[k].getParameterType() != null) {
					referenceType = params[k].getParameterType();
				}

				String argType = null;
				if (referenceType != null) {
					argType = referenceType.getName();
					boolean isExcluded = false;// check if the param's type
					// should be added to the method
					// arguments
					for (String comparedType : EXCLUDE_PARAM_TYPES_LIST) {
						if (comparedType.equalsIgnoreCase(argType)) {
							isExcluded = true;
							break;
						}
					}
					if (isExcluded) {
						argType = ""; //$NON-NLS-1$
					} else {
						argType += " "; //$NON-NLS-1$

					}
				} else {
					argType = ""; //$NON-NLS-1$
				}

				String funcParam = argType + params[k].getName();

				// handle default value
				ASTNode node = params[k].getInitialization();
				String sourceCode = null;
				try {
					String defaultValue = null;
					if (node != null) {
						sourceCode = sourceModule.getSource();
						if (sourceCode != null) {
							defaultValue = sourceCode.substring(node.sourceStart(), node.sourceEnd());
						}
					}

					if (defaultValue != null && !defaultValue.equals("")) { //$NON-NLS-1$
						funcParam += " = " + defaultValue; //$NON-NLS-1$
					}
					if ((k + 1) < params.length) {
						funcParam += ","; //$NON-NLS-1$
					}
					set(FUNC_PARAM_NAME_VAR, funcParam);
					compile(FUNC_PARAMS_STRUCT_COMPILED, FUNC_PARAMS_STRUCT, true);

				} catch (ModelException e) {
					Logger.logException(e);
				}
			}

			if (data.isGenerateTODOs) {
				set(TODO_VAR, TODO_TEXT);
			} else {
				set(TODO_VAR, ""); //$NON-NLS-1$
			}
			set(FUNCTION_PARENT_CALL, ""); //$NON-NLS-1$
			int flags = 0;
			try {
				flags = func.getFlags();
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
			String funcModifier = getVisibilityModifier(flags);
			set(FUNCTION_MODIFIER_VAR, funcModifier);
			if (isStaticFunction(func.getElementType())) {
				set(FUNCTION_STATIC_VAR, TypeWizardConstants.STATIC_MODIFIER);
			} else {
				set(FUNCTION_STATIC_VAR, ""); //$NON-NLS-1$
			}

			set(FUNCTION_NAME_VAR, func.getElementName());
			compile(FUNCTIONS_STRUCT_COMPILED, FUNCTIONS_STRUCT, true);
		}
	}

	// generates a constructor
	private void generateConstructor(NewPHPElementData data) {
		IMethod func = null;
		if (data.superClass != null) {
			IMethod[] methods = null;
			try {
				methods = data.superClass.getMethods();
			} catch (ModelException e) {
				Logger.logException(e);
			}
			if (methods != null) {
				for (IMethod m : methods) {
					String elName = m.getElementName();
					if (elName.equalsIgnoreCase(data.superClass.getElementName())
							|| elName.equalsIgnoreCase("__construct")) { //$NON-NLS-1$
						func = m;
						break;
					}

				}
			}
		}

		MethodDeclaration decl = null;
		if (func != null) {
			ISourceModule sourceModule = func.getSourceModule();
			ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule);

			try {
				decl = PHPModelUtils.getNodeByMethod(module, func);
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		// check for PHPDoc
		if (data.isGeneratePHPDoc) {
			set(FUNC_PHPDOC_PARAM_VAR, ""); //$NON-NLS-1$
			set(FUNC_PHPDOC_SEE_VAR, ""); //$NON-NLS-1$
			compile(FUNC_PHPDOC_PARAMS_STRUCT_COMPILED, FUNC_PHPDOC_PARAMS_STRUCT, false);
			if (func != null) {
				PHPDocBlock docBlock = null;
				if (decl != null && decl instanceof IPHPDocAwareDeclaration) {
					IPHPDocAwareDeclaration docDecl = (IPHPDocAwareDeclaration) decl;
					docBlock = docDecl.getPHPDoc();
				}

				if (docBlock != null) {
					PHPDocTag[] tags = docBlock.getTags();
					// loop on all phpdoc params
					for (PHPDocTag docTag : tags) {
						String phpdocParamType = " " //$NON-NLS-1$
								+ docTag.getTagKind().getValue();
						String phpdocParamValue = docTag.getValue();
						set(FUNC_PHPDOC_PARAM_VAR, phpdocParamType + " " + phpdocParamValue); //$NON-NLS-1$
						compile(FUNC_PHPDOC_PARAMS_STRUCT_COMPILED, FUNC_PHPDOC_PARAMS_STRUCT, true);
					}
				} else {
					if (decl != null) {
						List<?> paramsList = decl.getArguments();
						FormalParameter[] params = paramsList.toArray(new FormalParameter[paramsList.size()]);
						if (params != null) {
							for (int k = 0; k < params.length; k++) {
								String funcParam = params[k].getName();
								if (params[k].getParameterType() != null) {
									funcParam = params[k].getParameterType().getName() + " " + funcParam; //$NON-NLS-1$
								}
								set(FUNC_PHPDOC_PARAM_VAR, " @param " //$NON-NLS-1$
										+ funcParam + "\n"); //$NON-NLS-1$
								compile(FUNC_PHPDOC_PARAMS_STRUCT_COMPILED, FUNC_PHPDOC_PARAMS_STRUCT, true);
							}
						}
					}
				}
			}
			compile(FUNC_PHPDOC_STRUCT_COMPILED, FUNC_PHPDOC_STRUCT, false);
		}

		// loop on all function's params
		FormalParameter[] params = null;
		if (decl != null) {
			List<?> paramsList = decl.getArguments();
			params = paramsList.toArray(new FormalParameter[paramsList.size()]);
		}

		StringBuilder paramsOverriden = new StringBuilder();
		if (func != null) {
			paramsOverriden.append("parent::__construct("); //$NON-NLS-1$
		}
		set(FUNC_PARAM_NAME_VAR, ""); //$NON-NLS-1$
		compile(FUNC_PARAMS_STRUCT_COMPILED, FUNC_PARAMS_STRUCT, false);
		String defaultValue = null;
		if (params != null) {
			for (int k = 0; k < params.length; k++) {
				String funcParam = params[k].getName();
				// handle default value
				ASTNode argumentDefaultValueNode = params[k].getInitialization();
				if (argumentDefaultValueNode instanceof Scalar) {
					Scalar scalarNode = (Scalar) argumentDefaultValueNode;
					defaultValue = scalarNode.getValue();

				}

				if (defaultValue != null && !defaultValue.equals("")) { //$NON-NLS-1$
					funcParam += " = " + defaultValue; //$NON-NLS-1$
				}
				if ((k + 1) < params.length) {
					funcParam += ","; //$NON-NLS-1$
				}
				paramsOverriden.append(funcParam);
				if (params[k].getParameterType() != null) {
					funcParam = params[k].getParameterType().getName() + " " //$NON-NLS-1$
							+ funcParam;
				}
				set(FUNC_PARAM_NAME_VAR, funcParam);
				compile(FUNC_PARAMS_STRUCT_COMPILED, FUNC_PARAMS_STRUCT, true);
			}
		}

		if (func != null) {
			paramsOverriden.append(");"); //$NON-NLS-1$
		}

		set(FUNCTION_PARENT_CALL, paramsOverriden.toString());
		if (data.isGenerateTODOs) {
			set(TODO_VAR, TODO_TEXT);
		} else {
			set(TODO_VAR, ""); //$NON-NLS-1$
		}
		String funcModifier = "public"; //$NON-NLS-1$
		String funcName = TypeWizardConstants.PHP5_CONSTRUCTOR_TEMPLATE;

		if (func != null) {
			int flags = 0;
			try {
				flags = func.getFlags();
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
			funcModifier = getVisibilityModifier(flags);
			funcName = func.getElementName();
		}
		set(FUNCTION_MODIFIER_VAR, funcModifier);
		set(FUNCTION_NAME_VAR, funcName);
		compile(FUNCTIONS_STRUCT_COMPILED, FUNCTIONS_STRUCT, true);
	}

	// generates a destructor
	private void generateDestructor(NewPHPElementData data) {
		set(FUNCTION_PARENT_CALL, ""); //$NON-NLS-1$
		if (data.isGeneratePHPDoc) {
			set(FUNC_PHPDOC_PARAM_VAR, ""); //$NON-NLS-1$
			set(FUNC_PHPDOC_SEE_VAR, ""); //$NON-NLS-1$
			compile(FUNC_PHPDOC_PARAMS_STRUCT_COMPILED, FUNC_PHPDOC_PARAMS_STRUCT, false);
			compile(FUNC_PHPDOC_STRUCT_COMPILED, FUNC_PHPDOC_STRUCT, false);
		}

		set(FUNC_PARAM_NAME_VAR, ""); //$NON-NLS-1$
		compile(FUNC_PARAMS_STRUCT_COMPILED, FUNC_PARAMS_STRUCT, false);

		// fixed bug 14452 - empty the TODO_VAR in case of isGenerateTODOs is
		// false
		if (data.isGenerateTODOs) {
			set(TODO_VAR, TODO_TEXT);
		} else {
			set(TODO_VAR, ""); //$NON-NLS-1$
		}

		set(FUNCTION_MODIFIER_VAR, ""); //$NON-NLS-1$
		set(FUNCTION_NAME_VAR, TypeWizardConstants.PHP5_DESTRUCTOR_TEMPLATE);
		compile(FUNCTIONS_STRUCT_COMPILED, FUNCTIONS_STRUCT, true);
	}

	@Override
	public String getRequiredPHPs() {
		return requiredPHPsBlock;
	}

	/**
	 * returns true if the given functions array contains a function by the
	 * given name This method is used for avoiding multiple declaration of
	 * constructors and destructors.
	 * 
	 * @param funcs
	 *            the functions array to search in
	 * @param name
	 *            the name of the function to look for
	 * @return true if there is a function by the name "name" in the array
	 *         "funcs"
	 */
	private boolean containsFunction(IMethod[] funcs, String name) {
		for (int i = 0; i < funcs.length; i++) {
			if (funcs[i].getElementName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
}
