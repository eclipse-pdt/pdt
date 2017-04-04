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
package org.eclipse.php.ui.util;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.internal.core.ast.scanner.php5.PhpAstLexer;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.core.typeinference.context.TypeContext;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.CodeGenerationSettings;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.CodeGeneration;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.*;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class CodeGenerationUtils {

	private CodeGenerationUtils() {
	}

	/**
	 * Finds a method in a type. This searches for a method with the same name
	 * and signature. Parameter types are only compared by the simple name, no
	 * resolving for the fully qualified type name is done. Constructors are
	 * only compared by parameters, not the name.
	 * 
	 * @param name
	 *            The name of the method to find
	 * @param paramTypes
	 *            The type signatures of the parameters e.g.
	 *            <code>{"QString;","I"}</code>
	 * @param isConstructor
	 *            If the method is a constructor
	 * @param type
	 *            the type
	 * @return The first found method or <code>null</code>, if nothing foun
	 * @throws ModelException
	 *             thrown when the type can not be accessed
	 */
	public static IMethod findMethod(String name, int parameters, boolean isConstructor, IType type)
			throws ModelException {
		IMethod[] methods = type.getMethods();
		for (IMethod method : methods) {
			if (isSameMethodSignature(name, parameters, isConstructor, method)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * Tests if a method equals to the given signature. Parameter types are only
	 * compared by the simple name, no resolving for the fully qualified type
	 * name is done. Constructors are only compared by parameters, not the name.
	 * 
	 * @param name
	 *            Name of the method
	 * @param isConstructor
	 *            Specifies if the method is a constructor
	 * @param curr
	 *            the method
	 * @return Returns <code>true</code> if the method has the given name and
	 *         parameter types and constructor state.
	 * @throws ModelException
	 *             thrown when the method can not be accessed
	 */
	public static boolean isSameMethodSignature(String name, int parameters, boolean isConstructor, IMethod curr)
			throws ModelException {
		if (isConstructor || name.equals(curr.getElementName())) {
			if (isConstructor == curr.isConstructor()) {
				String[] currParamTypes = curr.getParameterNames();
				if (parameters == currParamTypes.length) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the Setter method of the field.
	 * 
	 * @param field
	 * @return getter method
	 * @throws ModelException
	 */
	public static IMethod getSetter(IField field) throws ModelException {
		return findMethod(getSetterName(field), 1, false, field.getDeclaringType());
	}

	/**
	 * Returns the getter name String.
	 * 
	 * @param field
	 * @return Getter name string
	 * @throws ModelException
	 */
	public static String getSetterName(IField field) throws ModelException {
		return "set" + toInitialCaps(field.getElementName(), true); //$NON-NLS-1$

	}

	/**
	 * utility method for changing the case of a string's first letter
	 * 
	 * @param s
	 *            input string
	 * @param toUpper
	 *            if true change first letter to upper case, false change to
	 *            lower case
	 * @return the given string with the first letter changed to upper/lower
	 *         case - according to the flag. Empty string for null or empty
	 *         strings.
	 */
	private static String toInitialCaps(String label, boolean toUpper) {
		String s = label;
		if (s == null || s.length() == 0) {
			return ""; //$NON-NLS-1$
		}

		// remove the $ from PHP variable.
		s = s.substring(1);

		// strip leading underscore to make getter/setter names more
		// reader-friendly
		if ((s.charAt(0) == '_') && (s.length() > 1) && (s.charAt(1) != '_')) {
			s = s.substring(1);
		}

		StringBuilder res = new StringBuilder();

		if (toUpper) {
			res.append(Character.toUpperCase(s.charAt(0)));
		} else {
			res.append(Character.toLowerCase(s.charAt(0)));
		}

		if (s.length() > 1) {
			res.append(s.substring(1));
		}
		return res.toString();
	}

	/**
	 * Returns the getter method of give filed.
	 * 
	 * @param field
	 * @return the getter method.
	 * @throws ModelException
	 */
	public static IMethod getGetter(IField field) throws ModelException {
		String getterName = getGetterName(field);
		return findMethod(getterName, 0, false, field.getDeclaringType());
	}

	/**
	 * Returns the getter name string.
	 * 
	 * @param field
	 * @return getter name string.
	 */
	public static String getGetterName(IField field) {
		return "get" + toInitialCaps(field.getElementName(), true); //$NON-NLS-1$
	}

	/**
	 * Create a stub for a getter of the given field using getter/setter
	 * templates. The resulting code has to be formatted and indented.
	 * 
	 * @param field
	 *            The field to create a getter for
	 * @param setterName
	 *            The chosen name for the setter
	 * @param addComments
	 *            If <code>true</code>, comments will be added.
	 * @param flags
	 *            The flags signaling visibility, if static, synchronized or
	 *            final
	 * @return Returns the generated stub.
	 * @throws CoreException
	 *             when stub creation failed
	 */
	public static void createSetterStub(IField field, String setterName, boolean addComments, int flags,
			ListRewrite rewrite, ASTNode insertion) throws CoreException {
		String fieldName = field.getElementName();
		IType parentType = field.getDeclaringType();

		AST ast = rewrite.getASTRewrite().getAST();

		String lineDelim = StubUtility.getLineDelimiterUsed(field.getScriptProject());

		MethodDeclaration method = new MethodDeclarationStub(ast);

		FunctionDeclaration func = ast.newFunctionDeclaration();
		FormalParameter param = ast.newFormalParameter();

		Expression exp = ast.newScalar(fieldName);
		param.setParameterName(exp);

		func.formalParameters().add(param);
		func.setFunctionName(rewrite.getParent().getAST().newIdentifier(getSetterName(field)));

		method.setFunction(func);

		boolean isStatic = Flags.isStatic(flags);
		boolean isFinal = Flags.isFinal(flags);

		String argname = field.getElementName();
		if (argname.equals(fieldName) || (!isStatic)) {
			if (isStatic) {
				fieldName = parentType.getElementName() + "::" + fieldName; //$NON-NLS-1$
			} else {
				fieldName = "$this->" + removeDollarSign(fieldName); //$NON-NLS-1$
			}
		}

		// only php 5 supports modifers.
		int modifiers = 0;
		if (isStatic) {
			modifiers = modifiers | Modifiers.AccStatic;
		}
		if (isFinal) {
			modifiers = modifiers | Modifiers.AccFinal;
		}
		if (Flags.isPublic(flags)) {
			modifiers = modifiers | Modifiers.AccPublic;
		}
		if (Flags.isProtected(flags)) {
			modifiers = modifiers | Modifiers.AccProtected;
		}
		if (Flags.isPrivate(flags)) {
			modifiers = modifiers | Modifiers.AccPrivate;
		}

		method.setModifier(modifiers);

		Block body = ast.newBlock();
		func.setBody(body);

		String bodyContent = CodeGeneration.getSetterMethodBodyContent(field.getScriptProject(),
				parentType.getTypeQualifiedName(), setterName, fieldName, field.getElementName(), lineDelim);

		if (bodyContent != null) {
			ASTNode todoNode = rewrite.getASTRewrite().createStringPlaceholder(bodyContent, ASTNode.EMPTY_STATEMENT);
			body.statements().add((Statement) todoNode);
		}

		if (addComments) {
			String filedType = getFieldType(field);
			String comment = CodeGeneration.getSetterComment(field.getScriptProject(),
					field.getDeclaringType().getElementName(), setterName, fieldName, filedType, field.getElementName(),
					field.getElementName(), lineDelim);
			if (comment != null) {
				Comment commentNode = (Comment) rewrite.getASTRewrite().createStringPlaceholder(comment + lineDelim,
						ASTNode.COMMENT);
				commentNode.setCommentType(Comment.TYPE_PHPDOC);
				method.setComment(commentNode);
			}
		}

		addNewAccessor(method, rewrite, insertion);
	}

	private static String getFieldType(IField field) throws ModelException {
		String filedType = field.getType();
		IType type = field.getDeclaringType();
		PHPClassType classType = PHPClassType.fromIType(type);

		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(type.getSourceModule(), null);
		FileContext fileContext = new FileContext(type.getSourceModule(), moduleDeclaration,
				field.getNameRange().getOffset());
		TypeContext typeContext = new TypeContext(fileContext, classType);
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
		PHPDocClassVariableGoal phpDocGoal = new PHPDocClassVariableGoal(typeContext, field.getElementName(),
				field.getNameRange().getOffset());
		IEvaluatedType evaluatedType = typeInferencer.evaluateTypePHPDoc(phpDocGoal, 3000);
		if (evaluatedType instanceof UnknownType) {

			ClassVariableDeclarationGoal goal = new ClassVariableDeclarationGoal(typeContext, new IType[] { type },
					field.getElementName());
			evaluatedType = typeInferencer.evaluateType(goal);

		}
		if (!(evaluatedType instanceof UnknownType)) {
			filedType = evaluatedType.getTypeName();
		}
		return filedType;
	}

	private static String removeDollarSign(String fieldName) {
		String name = fieldName;
		if (isDollared(name)) {
			name = name.substring(1);
		}
		return name;
	}

	private static boolean isDollared(String variableName) {
		return variableName.indexOf('$') == 0;
	}

	/**
	 * Adds a new accessor for the specified field.
	 * 
	 * @param type
	 *            the type
	 * @param field
	 *            the field
	 * @param contents
	 *            the contents of the accessor method
	 * @param rewrite
	 *            the list rewrite to use
	 * @param insertion
	 *            the insertion point
	 * @throws JavaModelException
	 *             if an error occurs
	 */
	private static void addNewAccessor(ASTNode node, final ListRewrite rewrite, final ASTNode insertion)
			throws ModelException {
		if (insertion != null) {
			rewrite.insertBefore(node, insertion, null);
		} else {
			rewrite.insertLast(node, null);
		}
	}

	/**
	 * Create a stub for a getter of the given field using getter/setter
	 * templates. The resulting code has to be formatted and indented.
	 * 
	 * @param field
	 *            The field to create a getter for
	 * @param getterName
	 *            The chosen name for the getter
	 * @param addComments
	 *            If <code>true</code>, comments will be added.
	 * @param flags
	 *            The flags signaling visibility, if static, synchronized or
	 *            final
	 * @param insertion
	 * @param rewrite
	 * @param field2
	 * @param type
	 * @return Returns the generated stub.
	 * @throws CoreException
	 *             when stub creation failed
	 */
	public static void createGetterStub(IField field, String getterName, boolean addComments, int flags, IType type,
			ListRewrite rewrite, ASTNode insertion) throws CoreException {
		String fieldName = field.getElementName();
		IType parentType = field.getDeclaringType();
		String lineDelim = StubUtility.getLineDelimiterUsed(field.getScriptProject());

		AST ast = rewrite.getASTRewrite().getAST();

		MethodDeclaration method = new MethodDeclarationStub(ast);

		FunctionDeclaration func = ast.newFunctionDeclaration();

		func.setFunctionName(ast.newIdentifier(getGetterName(field)));

		method.setFunction(func);

		boolean isStatic = Flags.isStatic(flags);
		boolean isFinal = Flags.isFinal(flags);
		fieldName = isStatic ? parentType.getElementName() + "::" + fieldName : "$this->" + removeDollarSign(fieldName); //$NON-NLS-1$

		int modifiers = 0;
		if (isStatic) {
			modifiers = modifiers | Modifiers.AccStatic;
		}
		if (isFinal) {
			modifiers = modifiers | Modifiers.AccFinal;
		}
		if (Flags.isPublic(flags)) {
			modifiers = modifiers | Modifiers.AccPublic;
		}
		if (Flags.isProtected(flags)) {
			modifiers = modifiers | Modifiers.AccProtected;
		}
		if (Flags.isPrivate(flags)) {
			modifiers = modifiers | Modifiers.AccPrivate;
		}

		method.setModifier(modifiers);

		Block body = ast.newBlock();
		func.setBody(body);

		String bodyContent = CodeGeneration.getGetterMethodBodyContent(field.getScriptProject(),
				parentType.getElementName(), getterName, fieldName, lineDelim);

		if (bodyContent != null) {
			ASTNode todoNode = rewrite.getASTRewrite().createStringPlaceholder(bodyContent, ASTNode.EMPTY_STATEMENT);
			body.statements().add((Statement) todoNode);
		}

		if (addComments) {
			String filedType = getFieldType(field);
			String comment = CodeGeneration.getGetterComment(field.getScriptProject(),
					field.getDeclaringType().getElementName(), getterName, fieldName, filedType, field.getElementName(),
					lineDelim);
			if (comment != null) {
				Comment commentNode = (Comment) rewrite.getASTRewrite().createStringPlaceholder(comment + lineDelim,
						ASTNode.COMMENT);
				commentNode.setCommentType(Comment.TYPE_PHPDOC);
				method.setComment(commentNode);
			}
		}
		addNewAccessor(method, rewrite, insertion);
	}

	/**
	 * Returns the element after the give element.
	 * 
	 * @param member
	 *            a Java element
	 * @return the next sibling of the given element or <code>null</code>
	 * @throws ModelException
	 *             thrown if the element could not be accessed
	 */
	public static IModelElement findNextSibling(IModelElement member) throws ModelException {
		IModelElement parent = member.getParent();
		if (parent instanceof IParent) {
			IModelElement[] elements = ((IParent) parent).getChildren();
			for (int i = elements.length - 2; i >= 0; i--) {
				if (member.equals(elements[i])) {
					return elements[i + 1];
				}
			}
		}
		return null;
	}

	/**
	 * Evaluates the insertion position of a new node.
	 * 
	 * @param listRewrite
	 *            The list rewriter to which the new node will be added
	 * @param sibling
	 *            The Java element before which the new element should be added.
	 * @return the AST node of the list to insert before or null to insert as
	 *         last.
	 * @throws ModelException
	 *             thrown if accessing the Model element failed
	 */

	public static ASTNode getNodeToInsertBefore(ListRewrite listRewrite, IModelElement sibling) throws ModelException {
		if (sibling instanceof IMember) {
			ISourceRange sourceRange = ((IMember) sibling).getSourceRange();
			if (sourceRange == null) {
				return null;
			}
			int insertPos = sourceRange.getOffset();

			return getNodeToInsertBefore(listRewrite, insertPos);
		}
		return null;
	}

	/**
	 * Evaluates the insertion position of a new node.
	 * 
	 * @param listRewrite
	 *            The list rewriter to which the new node will be added
	 * @param insertPos
	 *            The position of the element.
	 * @return the AST node of the list to insert before or null to insert as
	 *         last.
	 * @throws ModelException
	 *             thrown if accessing the Model element failed
	 */
	public static ASTNode getNodeToInsertBefore(ListRewrite listRewrite, int insertPos) {
		List<?> members = listRewrite.getOriginalList();
		for (Object object : members) {
			ASTNode curr = (ASTNode) object;
			if (curr.getStart() >= insertPos) {
				return curr;
			}
		}
		return null;
	}

	/**
	 * Returns the current model element from the PHP editor
	 * 
	 * @param source
	 * @param editor
	 * @param selection2
	 * @return
	 * @throws ModelException
	 */
	public static IModelElement getCurrentModelElement(IModelElement source, PHPStructuredEditor editor,
			ITextSelection selection) throws ExecutionException {
		ISourceViewer viewer = editor.getTextViewer();
		Point originalSelection = viewer.getSelectedRange();
		int offset = -1;

		if (originalSelection == null) {
			if (selection instanceof TextSelection) {
				TextSelection textSelection = (TextSelection) selection;
				offset = textSelection.getOffset();
			}
		} else {
			offset = originalSelection.x;
		}

		if (source instanceof ISourceModule) {
			ISourceModule module = (ISourceModule) source;
			try {
				return module.getElementAt(offset);
			} catch (ModelException e) {
				throw new ExecutionException("Error trying to resolve document's element", e); //$NON-NLS-1$
			}
		}

		return null;
	}

	public static PHPStructuredEditor getPHPEditor(ExecutionEvent event) {
		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		PHPStructuredEditor textEditor = null;
		if (editorPart instanceof PHPStructuredEditor) {
			textEditor = (PHPStructuredEditor) editorPart;
		} else {
			Object o = editorPart.getAdapter(ITextEditor.class);
			if (o != null) {
				textEditor = (PHPStructuredEditor) o;
			}
		}
		return textEditor;

	}

	public static IMethodBinding[] getOverridableMethods(AST ast, ITypeBinding typeBinding, boolean isSubType) {
		List<IMethodBinding> allMethods = new ArrayList<>();
		IMethodBinding[] typeMethods = typeBinding.getDeclaredMethods();
		for (IMethodBinding methodBinding : typeMethods) {
			final int modifiers = methodBinding.getModifiers();
			if (!Flags.isPrivate(modifiers)) {
				allMethods.add(methodBinding);
			}
		}
		ITypeBinding clazz = typeBinding.getSuperclass();
		while (clazz != null) {
			IMethodBinding[] methods = clazz.getDeclaredMethods();
			for (IMethodBinding methodBinding : methods) {
				final int modifiers = methodBinding.getModifiers();
				if (!Flags.isPrivate(modifiers) && findOverridingMethod(methodBinding, allMethods) == null) {
					allMethods.add(methodBinding);
				}
			}
			clazz = clazz.getSuperclass();
		}
		clazz = typeBinding;
		while (clazz != null) {
			ITypeBinding[] superInterfaces = clazz.getInterfaces();
			for (ITypeBinding superInterface : superInterfaces) {
				getOverridableMethods(ast, superInterface, allMethods);
			}
			clazz = clazz.getSuperclass();
		}
		if (!isSubType) {
			allMethods.removeAll(Arrays.asList(typeMethods));
		}
		if (!typeBinding.isInterface()) {
			for (int index = allMethods.size() - 1; index >= 0; index--) {
				IMethodBinding method = allMethods.get(index);
				int modifiers = method.getModifiers();
				if (PHPFlags.isFinal(modifiers)) {
					allMethods.remove(index);
				}
			}
		}
		return allMethods.toArray(new IMethodBinding[allMethods.size()]);
	}

	private static IMethodBinding findOverridingMethod(IMethodBinding method, List<IMethodBinding> allMethods) {
		for (IMethodBinding curr : allMethods) {
			if (Bindings.isSubsignature(curr, method)) {
				return curr;
			}
		}
		return null;
	}

	private static void getOverridableMethods(AST ast, ITypeBinding superBinding, List<IMethodBinding> allMethods) {
		IMethodBinding[] methods = superBinding.getDeclaredMethods();
		for (IMethodBinding method : methods) {
			final int modifiers = method.getModifiers();
			if (!method.isConstructor() && !PHPFlags.isPrivate(modifiers)
					&& findOverridingMethod(method, allMethods) == null) {
				allMethods.add(method);
			}
		}
		ITypeBinding[] superInterfaces = superBinding.getInterfaces();
		for (ITypeBinding superInterface : superInterfaces) {
			getOverridableMethods(ast, superInterface, allMethods);
		}
	}

	/**
	 * Returns the array of unimplemented methods of given type.
	 * 
	 * @param typeBinding
	 * @return the array of unimplemented methods
	 */
	public static IMethodBinding[] getUnimplementedMethods(ITypeBinding typeBinding) {
		return getUnimplementedMethods(typeBinding, false);
	}

	/**
	 * The array of unimplemented methods of given type.
	 * 
	 * @param typeBinding
	 * @param implementAbstractsOfInput
	 *            weather the abstract method should be included.
	 * @return the array of unimplemented methods
	 */
	public static IMethodBinding[] getUnimplementedMethods(ITypeBinding typeBinding,
			boolean implementAbstractsOfInput) {
		List<IMethodBinding> allMethods = new ArrayList<>();
		List<IMethodBinding> toImplement = new ArrayList<>();

		IMethodBinding[] typeMethods = typeBinding.getDeclaredMethods();
		for (IMethodBinding typeMethod : typeMethods) {
			int modifiers = typeMethod.getModifiers();
			if (!typeMethod.isConstructor() && !PHPFlags.isStatic(modifiers) && !PHPFlags.isPrivate(modifiers)) {
				allMethods.add(typeMethod);
			}
		}

		ITypeBinding superClass = typeBinding.getSuperclass();
		Set<ITypeBinding> bindingSet = new HashSet<>();
		while (superClass != null && !bindingSet.contains(superClass)) {
			bindingSet.add(superClass);
			typeMethods = superClass.getDeclaredMethods();
			for (IMethodBinding curr : typeMethods) {
				int modifiers = curr.getModifiers();
				if (!curr.isConstructor() && !PHPFlags.isStatic(modifiers) && !PHPFlags.isPrivate(modifiers)
						&& findMethodBinding(curr, allMethods) == null) {
					allMethods.add(curr);
				}
			}
			superClass = superClass.getSuperclass();
		}

		for (IMethodBinding curr : allMethods) {
			int modifiers = curr.getModifiers();
			if ((PHPFlags.isAbstract(modifiers) || curr.getDeclaringClass().isInterface())
					&& (implementAbstractsOfInput || typeBinding != curr.getDeclaringClass())) {
				// implement all abstract methods
				toImplement.add(curr);
			}
		}

		Set<ITypeBinding> visited = new HashSet<>();
		ITypeBinding curr = typeBinding;
		bindingSet.clear();
		while (curr != null && !bindingSet.contains(curr)) {
			bindingSet.add(curr);
			ITypeBinding[] superInterfaces = curr.getInterfaces();
			for (ITypeBinding superInterface : superInterfaces) {
				findUnimplementedInterfaceMethods(superInterface, visited, allMethods, toImplement);
			}
			curr = curr.getSuperclass();
		}

		return toImplement.toArray(new IMethodBinding[toImplement.size()]);
	}

	private static void findUnimplementedInterfaceMethods(ITypeBinding typeBinding, Set<ITypeBinding> visited,
			List<IMethodBinding> allMethods, List<IMethodBinding> toImplement) {
		if (visited.add(typeBinding)) {
			IMethodBinding[] typeMethods = typeBinding.getDeclaredMethods();
			for (IMethodBinding curr : typeMethods) {
				IMethodBinding impl = findMethodBinding(curr, allMethods);
				if (impl == null || !Bindings.isVisibleInHierarchy(impl)) {
					if (impl != null) {
						allMethods.remove(impl);
					}
					toImplement.add(curr);
					allMethods.add(curr);
				}
			}
			ITypeBinding[] superInterfaces = typeBinding.getInterfaces();
			for (ITypeBinding superInterface : superInterfaces) {
				findUnimplementedInterfaceMethods(superInterface, visited, allMethods, toImplement);
			}
		}
	}

	private static IMethodBinding findMethodBinding(IMethodBinding method, List<IMethodBinding> allMethods) {
		for (IMethodBinding curr : allMethods) {
			if (Bindings.isSubsignature(method, curr)) {
				return curr;
			}
		}
		return null;
	}

	private static int getImplementationModifiers(IMethodBinding method, boolean deferred) {
		int modifiers = method.getModifiers() & ~Modifiers.AccPrivate;
		modifiers = modifiers & ~Modifiers.AccAbstract;
		if (deferred) {
			modifiers = modifiers & ~Modifiers.AccProtected;
			modifiers = modifiers | Modifiers.AccPublic;
		}
		return modifiers;
	}

	/**
	 * Creates the method stub of the given method.
	 * 
	 * @param unit
	 * @param rewrite
	 * @param binding
	 * @param type
	 * @param settings
	 * @param deferred
	 * @return the block of the method.
	 * @throws CoreException
	 */
	public static MethodDeclaration createImplementationStub(Program unit, ASTRewrite rewrite, IMethodBinding binding,
			String type, CodeGenerationSettings settings, boolean deferred) throws CoreException {
		AST ast = rewrite.getAST();

		MethodDeclaration decl = new MethodDeclarationStub(ast);

		decl.setModifier(getImplementationModifiers(binding, deferred));

		FunctionDeclaration func = ast.newFunctionDeclaration();
		func.setFunctionName(ast.newIdentifier(binding.getName()));
		func.setFlags(getImplementationModifiers(binding, deferred));
		decl.setFunction(func);

		IMethod method = (IMethod) ((MethodBinding) binding).getPHPElement();

		MethodDeclaration methodDecl = null;
		if (method.getSourceModule() != unit.getSourceModule()) {
			Program program = null;
			ISourceModule source = method.getSourceModule();
			IProject project = source.getScriptProject().getProject();
			ASTParser parserForExpected = ASTParser.newParser(ProjectOptions.getPHPVersion(project),
					ProjectOptions.useShortTags(project));
			try {
				parserForExpected.setSource(source);
				program = parserForExpected.createAST(new NullProgressMonitor());
				program.recordModifications();
				program.setSourceModule(source);

				ASTNode function = program.getElementAt(method.getSourceRange().getOffset());
				if (function instanceof FunctionDeclaration) {
					methodDecl = (MethodDeclaration) function.getParent();
				}
				if (function instanceof MethodDeclaration) {
					methodDecl = (MethodDeclaration) function;
				}
			} catch (Exception e) {
				PHPUiPlugin.log(e);
			}
		} else {
			ASTNode function = unit.getElementAt(method.getSourceRange().getOffset());

			if (function instanceof FunctionDeclaration) {
				methodDecl = (MethodDeclaration) function.getParent();
			}
			if (function instanceof MethodDeclaration) {
				methodDecl = (MethodDeclaration) function;
			}
		}
		List<FormalParameter> typeParams = null;
		List<FormalParameter> typeParameters = null;
		if (methodDecl != null) {
			func.setReturnType(ASTNode.copySubtree(ast, methodDecl.getFunction().getReturnType()));

			typeParams = methodDecl.getFunction().formalParameters();

			typeParameters = func.formalParameters();
			for (int i = 0; typeParams != null && i < typeParams.size(); i++) {
				FormalParameter curr = typeParams.get(i);

				FormalParameter newTypeParam = ast.newFormalParameter();

				Expression exp = ASTNode.copySubtree(ast, curr.getParameterName());
				newTypeParam.setParameterName(exp);
				newTypeParam.setIsVariadic(curr.isVariadic());

				Expression value = curr.getDefaultValue();
				if (value != null) {
					newTypeParam.setDefaultValue(ASTNode.copySubtree(ast, value));
				}

				Expression ptype = curr.getParameterType();
				if (ptype != null) {
					Expression expression = ASTNode.copySubtree(ast, ptype);
					if (ptype instanceof NamespaceName) {
						NamespaceName newName = (NamespaceName) expression;
						if (!newName.isGlobal()) {
							String fullName = PHPModelUtils.getFullName(newName);
							fullName = PHPModelUtils.getFullName(fullName, method.getSourceModule(),
									newName.getStart());
							String[] names = fullName.split("\\\\"); //$NON-NLS-1$
							if (names.length > 1) {
								newName.segments().clear();
								newName.setGlobal(true);
								for (int j = 0; j < names.length; j++) {
									Identifier identifier = ast.newIdentifier();
									identifier.setName(names[j]);
									newName.segments().add(identifier);
								}
							}
						}
					}
					newTypeParam.setParameterType(expression);
				}

				typeParameters.add(newTypeParam);

			}
		}
		String delimiter = StubUtility.getLineDelimiterUsed(unit.getSourceModule().getScriptProject());
		if (!deferred) {
			Block body = ast.newBlock();
			func.setBody(body);
			String bodyStatement = ""; //$NON-NLS-1$

			String placeHolder = CodeGeneration.getMethodBodyContent(unit.getSourceModule().getScriptProject(), type,
					binding.getName(), false, bodyStatement, delimiter);
			if (placeHolder != null) {
				ASTNode todoNode = rewrite.createStringPlaceholder(placeHolder, ASTNode.EMPTY_STATEMENT);
				body.statements().add((Statement) todoNode);
				if (addReturnStatement(methodDecl)) {
					Identifier parentIdentifier = ast.newIdentifier("parent");
					FunctionName functionName = ast.newFunctionName(ast.newIdentifier(binding.getName()));
					FunctionInvocation functionInvocation = ast.newFunctionInvocation(functionName, null);
					Expression expression = ast.newStaticMethodInvocation(parentIdentifier, functionInvocation);
					body.statements().add(ast.newReturnStatement(expression));
				}
			}
		}

		if (settings != null && settings.createComments) {
			String comment = CodeGeneration.getMethodComment(method, method, delimiter);

			if (comment != null) {
				Comment phpdoc = (Comment) rewrite.createStringPlaceholder(comment + delimiter, ASTNode.COMMENT);
				phpdoc.setCommentType(Comment.TYPE_PHPDOC);
				decl.setComment(phpdoc);
			}
		}

		return decl;
	}

	private static boolean addReturnStatement(MethodDeclaration methodDecl) {
		if (methodDecl == null || methodDecl.getFunction() == null || methodDecl.getFunction().getBody() == null) {
			return false;
		}
		final AtomicBoolean add = new AtomicBoolean(false);
		methodDecl.getFunction().getBody().accept(new AbstractVisitor() {
			@Override
			public boolean visit(ReturnStatement expressionStatement) {
				add.set(true);
				return false;
			}
		});
		return add.get();
	}

	/**
	 * Opens the editor currently associated with the given element
	 * (IJavaElement, IFile, IStorage...)
	 * 
	 * @param inputElement
	 *            the input element
	 * @param activate
	 *            <code>true</code> if the editor should be activated
	 * @return an open editor or <code>null</code> if an external editor was
	 *         opened
	 * @throws PartInitException
	 *             if the editor could not be opened or the input element is not
	 *             valid Status code if opening the editor failed as no editor
	 *             input could be created for the given element.
	 */
	public static IEditorPart openInEditor(IModelElement inputElement, boolean activate) throws PartInitException {
		if (inputElement instanceof IField) {
			IWorkbenchPage page = PHPUiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart editor = page != null ? page.getActiveEditor() : null;
			if (editor != null && editor instanceof PHPStructuredEditor) {
				IModelElement source = ((PHPStructuredEditor) editor).getModelElement();

				if (source.equals(((IField) inputElement).getSourceModule())) {
					if (activate && (page != null && page.getActivePart() != editor)) {
						page.activate(editor);
					}
					return editor;
				}

			}

		}

		IEditorInput input = getEditorInput(inputElement);
		if (input == null) {
			IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK, "Can't open editor", //$NON-NLS-1$
					null);
			throw new PartInitException(status);
		}

		return openInEditor(input, getEditorID(input), activate);
	}

	private static IEditorPart openInEditor(IEditorInput input, String editorID, boolean activate)
			throws PartInitException {
		IWorkbenchPage page = PHPUiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page == null) {
			IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK, "Can't open editor", //$NON-NLS-1$
					null);
			throw new PartInitException(status);
		}
		return page.openEditor(input, editorID, activate);
	}

	private static IEditorInput getEditorInput(IModelElement element) {
		if (element != null) {
			IResource resource = element.getResource();
			if (resource instanceof IFile) {
				return new FileEditorInput((IFile) resource);
			}
		}

		return null;
	}

	/**
	 * Returns the editor id of give editor input.
	 * 
	 * @param input
	 * @return
	 * @throws PartInitException
	 */
	public static String getEditorID(IEditorInput input) throws PartInitException {
		IEditorDescriptor editorDescriptor;
		if (input instanceof IFileEditorInput) {
			editorDescriptor = IDE.getEditorDescriptor(((IFileEditorInput) input).getFile(), true, false);
		} else {
			String name = input.getName();
			if (name == null) {
				IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK, "Can't find editor", null); //$NON-NLS-1$
				throw new PartInitException(status);
			}

			editorDescriptor = IDE.getEditorDescriptor(name, true, false);
		}
		return editorDescriptor.getId();
	}

	/**
	 * Returns the current model element from the PHP editor
	 * 
	 * @param event
	 * @return
	 * @throws ModelException
	 */
	public static IModelElement getCurrentModelElement(ExecutionEvent event) throws ExecutionException {
		PHPStructuredEditor textEditor = getPHPEditor(event);
		IModelElement modelElement = null;
		if (textEditor != null) {
			modelElement = textEditor.getModelElement();
		}
		if (textEditor != null && modelElement != null) {
			final ISelectionProvider selectionProvider = textEditor.getSelectionProvider();
			ISourceViewer viewer = textEditor.getTextViewer();
			Point originalSelection = viewer.getSelectedRange();
			int offset = -1;
			if (originalSelection == null) {
				final ISelection selection = selectionProvider.getSelection();

				if (selection instanceof TextSelection) {
					TextSelection textSelection = (TextSelection) selection;
					offset = textSelection.getOffset();
				}
			} else {
				offset = originalSelection.x;
			}

			if (modelElement instanceof ISourceModule) {
				ISourceModule module = (ISourceModule) modelElement;
				try {
					return module.getElementAt(offset);
				} catch (ModelException e) {
					throw new ExecutionException("Error trying to resolve document's element", e); //$NON-NLS-1$
				}
			}

		}
		return null;
	}

	/**
	 * Figure out the type parent of the given element
	 * 
	 * @param element
	 * @return the type which the element belongs to, or null if can't find.
	 */
	public static IType getType(IModelElement element) {
		if (element == null) {
			return null;
		}

		IModelElement model = element;
		while (!(model instanceof IType)) {
			if (model == null) {
				return null;
			}
			IModelElement parent = model.getParent();
			if (parent == model) {
				return null;
			}
			model = parent;
			if (model instanceof ISourceModule) {
				return null;
			}
		}

		return (IType) model;
	}

	/**
	 * Creates the Program instance for the given document. Especially the
	 * Comment Mapper is initialised, so that the generating of the code can
	 * aware of the existing of the comments.
	 * 
	 * @param source
	 * @param document
	 * @param project
	 * @return program instance.
	 */
	public static Program getASTRoot(ISourceModule source, IDocument document, IProject project) {
		ASTParser parserForExpected = ASTParser.newParser(ProjectOptions.getPHPVersion(project), source);

		Program program = null;
		try {
			parserForExpected.setSource(document.get().toCharArray());
			program = parserForExpected.createAST(new NullProgressMonitor());
			program.setSourceModule(source);

			final Reader reader = new StringReader(document.get());

			program.initCommentMapper(document, new PhpAstLexer(reader));
			program.recordModifications();

		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
		return program;
	}

	private static class MethodDeclarationStub extends MethodDeclaration implements MethodStub {
		public MethodDeclarationStub(AST ast) {
			super(ast);
		}
	}
}
