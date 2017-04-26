/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.visitor;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.problem.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.ISourceLineTracker;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.parser.Messages;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
import org.eclipse.php.internal.core.preferences.CorePreferencesSupport;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPEvaluationUtils;

public class ValidatorVisitor extends PHPASTVisitor {

	private static final List<String> TYPE_SKIP = new ArrayList<String>();
	private static final String EMPTY = ""; //$NON-NLS-1$

	static {
		TYPE_SKIP.add("parent"); //$NON-NLS-1$
		TYPE_SKIP.add("self"); //$NON-NLS-1$
		TYPE_SKIP.add("static"); //$NON-NLS-1$
	}

	private Map<String, UsePartInfo> usePartInfo = new LinkedHashMap<String, UsePartInfo>();
	private Map<String, Boolean> elementExists = new HashMap<String, Boolean>();
	private NamespaceDeclaration currentNamespace;
	private String expectedNamespace;
	private String expectedTypeName;
	private boolean isFirstType = true;
	private boolean bindNamespaceEnabled = false;
	private boolean bindTypeNameEnabled = false;
	private boolean hasNamespace;
	private ISourceModule sourceModule;
	private IBuildContext context;

	public ValidatorVisitor(IBuildContext context) {
		this.context = context;
		this.sourceModule = context.getSourceModule();
		IProject project = sourceModule.getScriptProject().getProject();
		CorePreferencesSupport prefSupport = CorePreferencesSupport.getInstance();
		this.bindNamespaceEnabled = Boolean.parseBoolean(
				prefSupport.getPreferencesValue(PHPCoreConstants.VALIDATION_BIND_NAMESPACE, null, project));
		this.bindTypeNameEnabled = Boolean.parseBoolean(
				prefSupport.getPreferencesValue(PHPCoreConstants.VALIDATION_BIND_TYPENAME, null, project));
	}

	@Override
	public boolean visit(ModuleDeclaration s) throws Exception {
		expectedNamespace = PHPModelUtils.getNamespaceNameByLocation(sourceModule);
		expectedTypeName = PHPModelUtils.getTypeNameByFileName(sourceModule);
		return true;
	}

	@Override
	public boolean endvisit(ModuleDeclaration s) throws Exception {
		if (bindNamespaceEnabled && !expectedNamespace.equals(EMPTY) && !hasNamespace) {
			reportProblem(null, Messages.UnexpectedNamespaceDeclaration,
					PhpProblemIdentifier.UnexpectedNamespaceDeclaration, new String[] { EMPTY, expectedNamespace },
					ProblemSeverities.Error);
		}
		return super.endvisit(s);
	}

	@Override
	public boolean visit(NamespaceDeclaration s) throws Exception {
		hasNamespace = true;
		currentNamespace = s;
		if (bindNamespaceEnabled && !s.getName().equals(expectedNamespace)) {
			reportProblem(s.getRef(), Messages.UnexpectedNamespaceDeclaration,
					PhpProblemIdentifier.UnexpectedNamespaceDeclaration,
					new String[] { s.getName(), expectedNamespace }, ProblemSeverities.Error);
		}
		return true;
	}

	@Override
	public boolean endvisit(NamespaceDeclaration s) throws Exception {
		Collection<UsePartInfo> useInfos = usePartInfo.values();
		for (UsePartInfo useInfo : useInfos) {
			if (useInfo.getRefCount() == 0) {
				FullyQualifiedReference m = useInfo.getUsePart().getNamespace();
				String name = m.getFullyQualifiedName();
				reportProblem(m, Messages.UnusedImport, PhpProblemIdentifier.UnusedImport, name,
						ProblemSeverities.Warning);
			}
		}
		usePartInfo.clear();
		elementExists.clear();
		return super.endvisit(s);
	}

	@Override
	public boolean visit(PHPMethodDeclaration s) throws Exception {
		if (s.getPHPDoc() != null)
			s.getPHPDoc().traverse(this);
		return visitGeneral(s);
	}

	@Override
	public boolean visit(PHPFieldDeclaration s) throws Exception {
		if (s.getPHPDoc() != null) {
			s.getPHPDoc().traverse(this);
		}
		return super.visit(s);
	}

	@Override
	public boolean visit(PHPCallExpression node) throws Exception {
		if (node.getReceiver() != null) {
			node.getReceiver().traverse(this);
		}
		if (node.getArgs() != null) {
			node.getArgs().traverse(this);
		}
		return false;
	}

	@Override
	public boolean visit(FullyQualifiedReference node) throws Exception {
		return visit((TypeReference) node);
	}

	@Override
	public boolean visit(TypeReference node) throws Exception {
		return visit(node, ProblemSeverities.Error);
	}

	private boolean visit(TypeReference node, ProblemSeverity severity) throws Exception {
		if (PHPSimpleTypes.isSimpleType(node.getName()) || TYPE_SKIP.contains(node.getName())) {
			return true;
		}
		TypeReferenceInfo tri = new TypeReferenceInfo(node, false);
		String nodeName = tri.getTypeName();
		String key = null;
		if (tri.isGlobal()) {
			key = node.getName();
		} else {
			key = getFirstSegmentOfTypeName(nodeName);
		}
		UsePartInfo info = usePartInfo.get(key.toLowerCase());

		if (info != null) {
			if (!nodeName.equals(info.getFullyQualifiedName()))
				info.refer();
		}
		boolean isFound = findElement(tri);
		if (!isFound) {
			reportProblem(node, Messages.UndefinedType, PhpProblemIdentifier.UndefinedType, node.getName(), severity);
		}
		return false;
	}

	@Override
	public boolean visit(AnonymousClassDeclaration s) throws Exception {
		int end = s.start();
		int start = end - 1;
		while (sourceModule.getSource().charAt(start) != ' ') {
			start--;
		}
		checkUnimplementedMethods(s, start + 1, end);

		IModelElement element = sourceModule.getElementAt(s.start());
		if (s.getSuperClass() != null && element != null)
			checkSuperclass(s.getSuperClass(), false, element.getElementName());

		Collection<TypeReference> interfaces = s.getInterfaceList();
		if (interfaces != null && element != null) {
			for (TypeReference itf : interfaces)
				checkSuperclass(itf, true, element.getElementName());
		}
		return super.visit(s);
	}

	@Override
	public boolean visit(ClassDeclaration s) throws Exception {
		checkUnimplementedMethods(s, s.getRef());
		if (s.getSuperClass() != null)
			checkSuperclass(s.getSuperClass(), false, s.getName());

		Collection<TypeReference> interfaces = s.getInterfaceList();
		if (interfaces != null && interfaces.size() > 0) {
			for (TypeReference itf : interfaces)
				checkSuperclass(itf, true, s.getName());
		}
		return true;
	}

	@Override
	public boolean visit(ClassInstanceCreation s) throws Exception {
		if (s.getClassName() instanceof TypeReference) {
			TypeReferenceInfo tri = new TypeReferenceInfo((TypeReference) s.getClassName(), false);
			IType[] types = PHPModelUtils.getTypes(tri.getFullyQualifiedName(), sourceModule,
					s.getClassName().sourceStart(), null);
			for (IType type : types) {
				if (PHPFlags.isInterface(type.getFlags()) || PHPFlags.isAbstract(type.getFlags())) {
					reportProblem(s.getClassName(), Messages.CannotInstantiateType,
							PhpProblemIdentifier.CannotInstantiateType, type.getElementName(), ProblemSeverities.Error);
					break;
				}
			}
		}
		return visitGeneral(s);
	}

	@Override
	public boolean visit(InterfaceDeclaration s) throws Exception {
		if (s.getSuperClasses() == null)
			return true;
		for (ASTNode node : s.getSuperClasses().getChilds()) {
			checkSuperclass((TypeReference) node, true, s.getName());
		}
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		if (bindTypeNameEnabled && !(s instanceof NamespaceDeclaration) && isFirstType) {
			checkTypeName(s);
			isFirstType = false;
		}
		checkDuplicateDeclaration(s);
		return super.visit(s);
	}

	public boolean visit(UsePart part) throws Exception {
		UsePartInfo info = new UsePartInfo(part);
		TypeReferenceInfo tri = info.getTypeReferenceInfo();
		String name = tri.getTypeName();
		String currentNamespaceName;
		if (currentNamespace == null) {
			currentNamespaceName = EMPTY;
		} else {
			currentNamespaceName = currentNamespace.getName();
		}
		if (!findElement(tri)) {
			reportProblem(tri.getTypeReference(), Messages.ImportNotFound, PhpProblemIdentifier.ImportNotFound, name,
					ProblemSeverities.Error);
		} else if (usePartInfo.get(info.getRealName().toLowerCase()) != null) {
			reportProblem(tri.getTypeReference(), Messages.DuplicateImport, PhpProblemIdentifier.DuplicateImport,
					new String[] { name, info.getRealName() }, ProblemSeverities.Error);
		} else if (info.getNamespaceName().equals(currentNamespaceName)) {
			reportProblem(tri.getTypeReference(), Messages.UnnecessaryImport, PhpProblemIdentifier.UnnecessaryImport,
					new String[] { name }, ProblemSeverities.Warning);
		} else {
			usePartInfo.put(info.getRealName().toLowerCase(), info);
		}
		return false;
	}

	@Override
	public boolean visit(PHPDocTag phpDocTag) throws Exception {
		for (TypeReference fullTypeReference : phpDocTag.getTypeReferences()) {
			String fullTypeName = fullTypeReference.getName();
			// look for all type names inside fullTypeName by removing all type
			// delimiters, regardless if fullTypeName's content respects PHPDoc
			// standards or not
			Matcher matcher = PHPEvaluationUtils.TYPE_DELIMS_PATTERN.matcher(fullTypeName);
			int start = 0;
			while (matcher.find()) {
				if (matcher.start() != start) {
					String typeName = fullTypeName.substring(start, matcher.start());
					TypeReference typeReference = new TypeReference(fullTypeReference.start() + start,
							fullTypeReference.start() + start + typeName.length(), typeName);
					visit(typeReference, ProblemSeverities.Warning);
				}
				start = matcher.end();
			}
			if (start == 0) {
				visit(fullTypeReference, ProblemSeverities.Warning);
			} else if (start != fullTypeName.length()) {
				String typeName = fullTypeName.substring(start);
				TypeReference typeReference = new TypeReference(fullTypeReference.start() + start,
						fullTypeReference.start() + start + typeName.length(), typeName);
				visit(typeReference, ProblemSeverities.Warning);
			}
		}
		return false;
	}

	private void checkTypeName(TypeDeclaration s) {
		if (!s.getName().equals(expectedTypeName)) {
			String type = null;
			if (s instanceof ClassDeclaration) {
				type = "class"; //$NON-NLS-1$
			} else if (s instanceof InterfaceDeclaration) {
				type = "interface"; //$NON-NLS-1$
			} else if (s instanceof TraitDeclaration) {
				type = "trait"; //$NON-NLS-1$
			}
			reportProblem(s.getRef(), Messages.FirstTypeMustMatchFileName,
					PhpProblemIdentifier.FirstClassMustMatchFileName,
					new String[] { type, s.getName(), expectedTypeName }, ProblemSeverities.Error);
		}
	}

	private void checkDuplicateDeclaration(TypeDeclaration node) {
		String name = node.getName();
		if (usePartInfo.containsKey(name)) {
			reportProblem(node.getRef(), Messages.DuplicateDeclaration, PhpProblemIdentifier.DuplicateDeclaration, name,
					ProblemSeverities.Error);
		}
	}

	private void checkUnimplementedMethods(Statement statement, ASTNode classNode) throws ModelException {
		checkUnimplementedMethods(statement, classNode.sourceStart(), classNode.sourceEnd());
	}

	private void checkUnimplementedMethods(Statement statement, int nodeStart, int nodeEnd) throws ModelException {
		IModelElement element = sourceModule.getElementAt(statement.start());
		if (!(element instanceof IType))
			return;
		IType type = (IType) element;
		if (type.getSuperClasses().length > 0 && !PHPFlags.isAbstract(type.getFlags())) {
			IMethod[] methods = PHPModelUtils.getUnimplementedMethods(type, null);
			for (IMethod method : methods) {
				if (method.getParent().getElementName().equals(type.getElementName())) {
					continue;
				}
				StringBuilder methodName = new StringBuilder(method.getParent().getElementName()).append("::"); //$NON-NLS-1$
				PHPModelUtils.getMethodLabel(method, methodName);
				String message = Messages.getString("AbstractMethodMustBeImplemented",
						new String[] { type.getElementName(), methodName.toString() });
				reportProblem(nodeStart, nodeEnd, message, PhpProblemIdentifier.AbstractMethodMustBeImplemented,
						ProblemSeverities.Error);
			}
		}
	}

	private void checkSuperclass(TypeReference superClass, boolean isInterface, String className)
			throws ModelException {
		if (superClass != null) {
			TypeReferenceInfo refInfo = new TypeReferenceInfo(superClass, false);
			IType[] types = PHPModelUtils.getTypes(refInfo.getFullyQualifiedName(), sourceModule,
					superClass.sourceStart(), null);
			for (IType type : types) {
				if (!isInterface) {
					if (PHPFlags.isInterface(type.getFlags())) {
						reportProblem(superClass, Messages.SuperclassMustBeAClass,
								PhpProblemIdentifier.SuperclassMustBeAClass,
								new String[] { superClass.getName(), className }, ProblemSeverities.Error);
					}
					if (PHPFlags.isFinal(type.getFlags())) {
						reportProblem(superClass, Messages.ClassExtendFinalClass,
								PhpProblemIdentifier.ClassExtendFinalClass,
								new String[] { className, type.getElementName() }, ProblemSeverities.Error);
					}
				} else {
					if (!PHPFlags.isInterface(type.getFlags())) {
						reportProblem(superClass, Messages.SuperInterfaceMustBeAnInterface,
								PhpProblemIdentifier.SuperInterfaceMustBeAnInterface,
								new String[] { superClass.getName(), className }, ProblemSeverities.Error);
					}
				}
			}
		}
	}

	private boolean findElement(TypeReferenceInfo info) {
		String name = info.getFullyQualifiedName();
		if (elementExists.containsKey(name)) {
			return elementExists.get(name);
		}

		boolean isFound = false;
		try {
			TypeReference type = info.getTypeReference();
			IModelElement[] types = PHPModelUtils.getTypes(name, sourceModule, type.start(), null);
			if (types.length == 0) {
				types = PHPModelUtils.getTraits(name, sourceModule, type.start(), null, null);
			}
			if (types.length == 0 && info.isUseStatement()) {
				types = sourceModule.codeSelect(type.start(), type.end() - type.start());
			}
			if (types.length > 0) {
				isFound = true;
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		elementExists.put(name, isFound);
		return isFound;
	}

	private void reportProblem(ASTNode s, String message, IProblemIdentifier id, String[] stringArguments,
			ProblemSeverity severity) {
		message = MessageFormat.format(message, (Object[]) stringArguments);
		reportProblem(s, message, id, severity);
	}

	private void reportProblem(ASTNode s, String message, IProblemIdentifier id, ProblemSeverity severity) {
		int start = 0, end = 0;
		if (s != null) {
			start = s.sourceStart();
			end = s.sourceEnd();
		} else {
			start = end = context.getLineTracker().getLineOffset(1);
		}
		reportProblem(start, end, message, id, severity);
	}

	private void reportProblem(ASTNode s, String message, IProblemIdentifier id, String stringArguments,
			ProblemSeverity severity) {
		reportProblem(s, message, id, new String[] { stringArguments }, severity);
	}

	private void reportProblem(int start, int end, String message, IProblemIdentifier id, ProblemSeverity severity) {
		ISourceLineTracker tracker = context.getLineTracker();
		int line = tracker.getLineNumberOfOffset(start);
		IProblem problem = new DefaultProblem(context.getFile().getName(), message, id, null, severity, start, end,
				line, 0);
		context.getProblemReporter().reportProblem(problem);
	}

	private String getFirstSegmentOfTypeName(String typeName) {
		if (typeName != null) {
			String[] segments = typeName.split("\\\\"); //$NON-NLS-1$
			for (String segment : segments) {
				if (segment.trim().length() > 0) {
					return segment;
				}
			}
		}
		return EMPTY;
	}

	private class UsePartInfo {
		private UsePart usePart;
		private String realName;
		private int refCount;
		private String fullyQualifiedName;
		private TypeReferenceInfo tri;
		private boolean isAlias = false;

		public UsePartInfo(UsePart usePart) {
			this.usePart = usePart;
			tri = new TypeReferenceInfo(usePart.getNamespace(), true);
			if (usePart.getAlias() != null) {
				realName = usePart.getAlias().getName();
				isAlias = true;
			} else {
				realName = usePart.getNamespace().getName();
			}
			if (tri.getNamespaceName() != null) {
				fullyQualifiedName = tri.getNamespaceName();
			}
			fullyQualifiedName += NamespaceReference.NAMESPACE_DELIMITER + usePart.getNamespace().getName();
			if (!fullyQualifiedName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				fullyQualifiedName = NamespaceReference.NAMESPACE_DELIMITER + fullyQualifiedName;
			}
		}

		public UsePart getUsePart() {
			return usePart;
		}

		public int getRefCount() {
			return refCount;
		}

		public void refer() {
			refCount++;
		}

		public String getRealName() {
			return realName;
		}

		public String getFullyQualifiedName() {
			return fullyQualifiedName;
		}

		public String getNamespaceName() {
			return tri.getNamespaceName();
		}

		public TypeReferenceInfo getTypeReferenceInfo() {
			return tri;
		}

		@Override
		public String toString() {
			String str = "use " + fullyQualifiedName; //$NON-NLS-1$
			if (isAlias) {
				str += " as " + realName; //$NON-NLS-1$
			}
			return str;
		}
	}

	private class TypeReferenceInfo {

		private TypeReference typeReference;
		private boolean isGlobal = false;
		private boolean hasNamespace = false;
		private String namespaceName = EMPTY;
		private String typeName;
		private String fullyQualifiedName;
		private boolean isUseStatement;

		public TypeReferenceInfo(TypeReference typeReference, boolean isUseStatement) {
			this.typeReference = typeReference;
			this.isUseStatement = isUseStatement;
			FullyQualifiedReference fullTypeReference = null;
			if (typeReference instanceof FullyQualifiedReference) {
				fullTypeReference = (FullyQualifiedReference) typeReference;
				if (fullTypeReference.getNamespace() != null) {
					hasNamespace = true;
					namespaceName = fullTypeReference.getNamespace().getName();
					if (usePartInfo.get(namespaceName) != null) {
						namespaceName = usePartInfo.get(namespaceName).getFullyQualifiedName();
					}
				}
			}

			if (fullTypeReference != null && hasNamespace) {
				isGlobal = fullTypeReference.getNamespace().isGlobal();
				typeName = fullTypeReference.getFullyQualifiedName();
			} else {
				typeName = typeReference.getName();
			}

			if (fullTypeReference != null && isGlobal) {
				fullyQualifiedName = fullTypeReference.getFullyQualifiedName();
			} else if (hasNamespace) {
				fullyQualifiedName = namespaceName + NamespaceReference.NAMESPACE_DELIMITER + typeReference.getName();
			} else {
				fullyQualifiedName = typeName;
			}
			if (!isUseStatement && !fullyQualifiedName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				String key = getFirstSegmentOfTypeName(fullyQualifiedName).toLowerCase();
				if (usePartInfo.containsKey(key)) {
					fullyQualifiedName = usePartInfo.get(key).getFullyQualifiedName();
				} else if (currentNamespace != null)
					fullyQualifiedName = currentNamespace.getName() + NamespaceReference.NAMESPACE_DELIMITER
							+ fullyQualifiedName;
			}
			if (!fullyQualifiedName.startsWith(NamespaceReference.NAMESPACE_DELIMITER))
				fullyQualifiedName = NamespaceReference.NAMESPACE_DELIMITER + fullyQualifiedName;
		}

		public boolean isGlobal() {
			return isGlobal;
		}

		public String getTypeName() {
			return typeName;
		}

		public String getFullyQualifiedName() {
			return fullyQualifiedName;
		}

		public String getNamespaceName() {
			return namespaceName;
		}

		public TypeReference getTypeReference() {
			return typeReference;
		}

		public boolean isUseStatement() {
			return isUseStatement;
		}
	}

}
