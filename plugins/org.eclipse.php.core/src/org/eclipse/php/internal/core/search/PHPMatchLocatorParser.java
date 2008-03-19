package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.search.matching.MatchLocator;
import org.eclipse.dltk.core.search.matching.MatchLocatorParser;
import org.eclipse.dltk.core.search.matching.PossibleMatch;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;

public class PHPMatchLocatorParser extends MatchLocatorParser {

	public PHPMatchLocatorParser(MatchLocator locator) {
		super(locator);
	}

	public ModuleDeclaration parse(PossibleMatch possibleMatch) {
		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration((org.eclipse.dltk.core.ISourceModule) possibleMatch.getModelElement(), null);
		return module;
	}

	private ASTVisitor visitor = new ASTVisitor() {

		public boolean visitGeneral(ASTNode s) throws Exception {
			processStatement(s);
			return true;
		}

		public boolean visit(MethodDeclaration s) throws Exception {
			getPatternLocator().match(processMethod(s), getNodeSet());
			return true;
		}

		public boolean visit(TypeDeclaration s) throws Exception {
			getPatternLocator().match(processType(s), getNodeSet());
			return true;
		}
	};

	public void parseBodies(ModuleDeclaration unit) {
		try {
			unit.traverse(this.visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MethodDeclaration processMethod(MethodDeclaration m) {
		return m;
	}

	private TypeDeclaration processType(TypeDeclaration t) {
		return t;
	}

	private void processStatement(ASTNode node) {
		if (node instanceof FieldDeclaration) {
			getPatternLocator().match((FieldDeclaration) node, getNodeSet());
		} else if (node instanceof ClassConstantDeclaration) {
			ClassConstantDeclaration constDecl = (ClassConstantDeclaration) node;
			ConstantReference constantName = constDecl.getConstantName();
			FieldDeclaration decl = new FieldDeclaration(constantName.getName(), constantName.sourceStart(), constantName.sourceEnd(), constDecl.sourceStart(), constDecl.sourceEnd());
			decl.setModifiers(Modifiers.AccConstant);
			getPatternLocator().match(decl, getNodeSet());
		} else if(node instanceof Assignment){
			Expression left = ((Assignment)node).getVariable();
			if (left instanceof FieldAccess) { // class variable ($this->a = .)
				FieldAccess fieldAccess = (FieldAccess) left;
				Expression dispatcher = fieldAccess.getDispatcher();
				if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
					Expression field = fieldAccess.getField();
					if (field instanceof SimpleReference) {
						SimpleReference ref = (SimpleReference) field;
						FieldDeclaration decl = new FieldDeclaration(ref.getName(), ref.sourceStart(), ref.sourceEnd(), node.sourceStart(), node.sourceEnd());
						getPatternLocator().match(decl, getNodeSet());
					}
				}
			}
		} else if (node instanceof TypeReference) {
			getPatternLocator().match((TypeReference)node, getNodeSet());
		} else if (node instanceof CallExpression) {
			getPatternLocator().match((CallExpression)node, getNodeSet());
		} else if (node instanceof Include) {
			Include include = (Include) node;
			if (include.getExpr() instanceof Scalar) {
				Scalar filePath = (Scalar) include.getExpr();
				CallExpression callExpression = new CallExpression(filePath.sourceStart(), filePath.sourceEnd(), null, "include", new CallArgumentsList());
				getPatternLocator().match(callExpression, getNodeSet());
			}
		}
	}
}
