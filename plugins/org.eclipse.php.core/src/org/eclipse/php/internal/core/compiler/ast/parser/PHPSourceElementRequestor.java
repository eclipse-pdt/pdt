package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.wst.xml.core.internal.Logger;

public class PHPSourceElementRequestor extends SourceElementRequestVisitor {

	/*
	 * This should replace the need for fInClass, fInMethod and fCurrentMethod
	 * since in php the type declarations can be nested.
	 */
	protected Stack<Declaration> declarations = new Stack<Declaration>();
	private PHPSourceElementRequestorExtension[] extensions;

	public PHPSourceElementRequestor(ISourceElementRequestor requestor, char[] contents, char[] filename) {
		super(requestor);

		// Load PHP source element requestor extensions
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(PHPCorePlugin.ID, "phpSourceElementRequestors");
		List<PHPSourceElementRequestorExtension> requestors = new ArrayList<PHPSourceElementRequestorExtension>(elements.length);
		for (IConfigurationElement element : elements) {
			try {
				PHPSourceElementRequestorExtension extension = (PHPSourceElementRequestorExtension) element.createExecutableExtension("class");
				extension.setRequestor(fRequestor);
				extension.setContents(contents);
				extension.setFilename(new String(filename));

				requestors.add(extension);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		extensions = requestors.toArray(new PHPSourceElementRequestorExtension[requestors.size()]);
	}

	protected MethodDeclaration getCurrentMethod() {
		Declaration currDecleration = declarations.peek();
		if (currDecleration instanceof MethodDeclaration) {
			return (MethodDeclaration) currDecleration;
		}
		return null;
	}

	public boolean endvisit(MethodDeclaration method) throws Exception {
		declarations.pop();

		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.endvisit(method);
		}
		return super.endvisit(method);
	}

	public boolean endvisit(TypeDeclaration type) throws Exception {
		declarations.pop();

		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.endvisit(type);
		}
		return super.endvisit(type);
	}

	public boolean visit(MethodDeclaration method) throws Exception {
		declarations.push(method);

		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.visit(method);
		}
		return super.visit(method);
	}

	public boolean visit(TypeDeclaration type) throws Exception {
		declarations.push(type);

		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.visit(type);
		}
		return super.visit(type);
	}

	public boolean visit(PHPFieldDeclaration declaration) throws Exception {
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = declaration.getModifiers();
		info.name = declaration.getName();
		SimpleReference var = declaration.getRef();
		info.nameSourceEnd = var.sourceEnd() - 1;
		info.nameSourceStart = var.sourceStart();
		info.declarationStart = declaration.sourceStart();
		fRequestor.enterField(info);
		return true;
	}

	public boolean endvisit(PHPFieldDeclaration declaration) throws Exception {
		fRequestor.exitField(declaration.sourceEnd() - 1);
		return true;
	}

	public boolean visit(CallExpression call) throws Exception {
		int argsCount = 0;
		CallArgumentsList args = call.getArgs();
		if (args != null && args.getChilds() != null) {
			argsCount = args.getChilds().size();
		}
		fRequestor.acceptMethodReference(call.getName().toCharArray(), argsCount, call.sourceStart(), call.sourceEnd());
		return true;
	}

	public boolean visit(Include include) throws Exception {
		// special case for include statements; we need to cache this information in order to access it quickly:
		if (include.getExpr() instanceof Scalar) {
			Scalar filePath = (Scalar) include.getExpr();
			fRequestor.acceptMethodReference("include".toCharArray(), 0, filePath.sourceStart(), filePath.sourceEnd());
		}
		return true;
	}

	public boolean visit(ClassConstantDeclaration declaration) throws Exception {
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccConstant;
		ConstantReference constantName = declaration.getConstantName();
		info.name = constantName.getName();
		info.nameSourceEnd = constantName.sourceEnd() - 1;
		info.nameSourceStart = constantName.sourceStart();
		info.declarationStart = declaration.sourceStart();
		fRequestor.enterField(info);
		return true;
	}

	public boolean endvisit(ClassConstantDeclaration declaration) throws Exception {
		fRequestor.exitField(declaration.sourceEnd() - 1);
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(Assignment assignment) throws Exception {
		Expression left = assignment.getVariable();
		if (left instanceof FieldAccess) { // class variable ($this->a = .)
			FieldAccess fieldAccess = (FieldAccess) left;
			Expression dispatcher = fieldAccess.getDispatcher();
			if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
				Expression field = fieldAccess.getField();
				if (field instanceof SimpleReference) {
					SimpleReference ref = (SimpleReference) field;
					ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
					info.modifiers = Modifiers.AccDefault;
					info.name = '$' + ref.getName();
					info.nameSourceEnd = ref.sourceEnd() - 1;
					info.nameSourceStart = ref.sourceStart();
					info.declarationStart = assignment.sourceStart();
					fRequestor.enterField(info);
					fNodes.push(assignment);
				}
			}
		}
		return true;
	}

	public boolean endvisit(Assignment assignment) throws Exception {
		if (!fNodes.isEmpty() && fNodes.peek() == assignment) {
			fRequestor.exitField(assignment.sourceEnd() - 1);
			fNodes.pop();
		}
		return true;
	}

	public boolean visit(TypeReference reference) throws Exception {
		fRequestor.acceptTypeReference(reference.getName().toCharArray(), reference.sourceStart());
		return true;
	}

	public boolean visit(Statement node) throws Exception {
		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.visit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(PHPFieldDeclaration.class.getName())) {
			return visit((PHPFieldDeclaration) node);
		}
		if (clasName.equals(ClassConstantDeclaration.class.getName())) {
			return visit((ClassConstantDeclaration) node);
		}
		if (clasName.equals(CallExpression.class.getName())) {
			return visit((CallExpression) node);
		}
		return true;
	}

	public boolean endvisit(Statement node) throws Exception {
		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.endvisit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(PHPFieldDeclaration.class.getName())) {
			return endvisit((PHPFieldDeclaration) node);
		}
		if (clasName.equals(ClassConstantDeclaration.class.getName())) {
			return endvisit((ClassConstantDeclaration) node);
		}
		return true;
	}

	public boolean visit(Expression node) throws Exception {
		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.visit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(Assignment.class.getName())) {
			return visit((Assignment) node);
		}
		if (clasName.equals(TypeReference.class.getName())) {
			return visit((TypeReference) node);
		}
		if (clasName.equals(Include.class.getName())) {
			return visit((Include) node);
		}
		return true;
	}

	public boolean endvisit(Expression node) throws Exception {
		for (SourceElementRequestVisitor visitor : extensions) {
			visitor.endvisit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(Assignment.class.getName())) {
			return endvisit((Assignment) node);
		}
		return true;
	}
}
