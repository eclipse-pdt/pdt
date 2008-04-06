package org.eclipse.php.internal.ui.search;

import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Block;
import org.eclipse.php.internal.core.ast.nodes.ClassConstantDeclaration;
import org.eclipse.php.internal.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FunctionName;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.internal.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.internal.core.ast.nodes.MethodInvocation;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Statement;
import org.eclipse.php.internal.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.internal.core.ast.nodes.StaticFieldAccess;
import org.eclipse.php.internal.core.ast.nodes.StaticMethodInvocation;
import org.eclipse.php.internal.core.ast.nodes.TypeDeclaration;
import org.eclipse.php.internal.core.ast.nodes.Variable;

/**
 * Class members occurrences finder.
 * 
 * @author shalom
 */
public class ClassMembersOccurrencesFinder extends AbstractOccurrencesFinder {
	
	public static final String ID = "ClassMembersOccurrencesFinder"; //$NON-NLS-1$
	private String classMemberName; // The member's name
	private String typeDeclarationName; // Class or Interface name // TODO - use Binding
	private boolean isMethod;

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		typeDeclarationName = null;
		isMethod = false;
		if (node.getType() == ASTNode.IDENTIFIER) {
			Identifier identifier = (Identifier) node;
			classMemberName = identifier.getName();
			// IBinding binding = identifier.resolveBinding(); // FIXME - This should be implemented...
			ASTNode parent = identifier.getParent();
			int type = parent.getType();
			isMethod = type == ASTNode.FUNCTION_DECLARATION || parent.getLocationInParent() == FunctionName.NAME_PROPERTY;
			while (typeDeclarationName == null && parent != fASTRoot) {
				if (type == ASTNode.CLASS_DECLARATION || type == ASTNode.INTERFACE_DECLARATION) {
					typeDeclarationName = ((TypeDeclaration) parent).getName().getName();
					break;
				}
				parent = parent.getParent();
				type = parent.getType();
			}
			return null;
		}
		fDescription = "OccurrencesFinder_occurrence_description";
		return fDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences()
	 */
	protected void findOccurrences() {
		if (isMethod) {
			fDescription = Messages.format(BASE_DESCRIPTION, classMemberName + BRACKETS);
		} else {
			fDescription = Messages.format(BASE_DESCRIPTION, classMemberName);
		}
		fASTRoot.accept(this);
	}

	/**
	 * context +
	 * Mark var on: ... public $a; ...
	 */
	public boolean visit(ClassDeclaration classDeclaration) {
		checkTypeDeclaration(classDeclaration);
		return false;
	}

	/**
	 * context
	 */
	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		checkTypeDeclaration(interfaceDeclaration);
		return false;
	}

	/**
	 * Mark foo() on: $a->foo();
	 */
	public boolean visit(MethodInvocation methodInvocation) {
		if (isMethod) {
			checkDispatch(methodInvocation.getMethod().getFunctionName().getName());			
		}
		return super.visit(methodInvocation);
	}

	/**
	 * Mark var on: $a->var;
	 */
	public boolean visit(FieldAccess fieldAccess) {
		if (!isMethod) {
			checkDispatch(fieldAccess.getField().getName());		
		}
		return super.visit(fieldAccess);
	}

	/**
	 * Mark CON on: MyClass::CON;
	 */
	public boolean visit(StaticConstantAccess classConstantAccess) {
		// TODO - Change this naive implementation to support real binded occurrences (using the Binding mechanism)
		// if (classConstantAccess.getClassName().getName().equals(typeDeclarationName)) {
		Identifier constant = classConstantAccess.getConstant();
		if (classMemberName.equals(constant.getName())) {
			fResult.add(new OccurrenceLocation(constant.getStart(), constant.getLength(), getOccurrenceType(constant), fDescription));
		}
		// }
		return true;
	}

	/**
	 * Mark foo() on: MyClass::foo();
	 */
	public boolean visit(StaticMethodInvocation methodInvocation) {
		if (isMethod) {
			checkDispatch(methodInvocation.getMethod().getFunctionName().getName());
		}
		return super.visit(methodInvocation);
	}

	/**
	 * Mark var on: MyClass::var;
	 */
	public boolean visit(StaticFieldAccess fieldAccess) {
		int start = fieldAccess.getMember().getStart();
		if (fieldAccess.getField().isDollared()) {
			start++;
		}
		if (!isMethod) {
			checkDispatch(fieldAccess.getField().getName());			
		}

		return super.visit(fieldAccess);
	}

	/**
	 * @param dispatch
	 * @throws RuntimeException
	 */
	private void checkDispatch(ASTNode node) {
		if (node.getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) node;
			if (id.getName().equalsIgnoreCase(classMemberName)) {
				fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(), getOccurrenceType(node), fDescription));	
			}
		} 
		if (node.getType() == ASTNode.VARIABLE) {
			Variable id = (Variable) node;
			checkDispatch(id.getName());
		} 
		
		
	}

	private void checkTypeDeclaration(TypeDeclaration typeDeclaration) {
		assert typeDeclaration != null;

		Block body = typeDeclaration.getBody();

		// definitions of the class property
		// TODO - Change this naive implementation to support real binded occurrences (using the Binding mechanism)		
		//		if (className.getName().equalsIgnoreCase(typeDeclarationName)) {
		List<Statement> statements = body.statements();
		for (Statement statement : statements) {
			if (statement.getType() == ASTNode.METHOD_DECLARATION) {
				final MethodDeclaration classMethodDeclaration = (MethodDeclaration) statement;
				if (isMethod) {
					final Identifier functionName = classMethodDeclaration.getFunction().getFunctionName();
					if (classMemberName.equalsIgnoreCase(functionName.getName())) {
						fResult.add(new OccurrenceLocation(functionName.getStart(), functionName.getLength(), getOccurrenceType(functionName), fDescription));
					}
				}
			} else if (statement.getType() == ASTNode.FIELD_DECLARATION) {
				if (!isMethod) {
					FieldsDeclaration classVariableDeclaration = (FieldsDeclaration) statement;
					final Variable[] variableNames = classVariableDeclaration.getVariableNames();
					for (int j = 0; j < variableNames.length; j++) {
						// safe cast to identifier
						assert variableNames[j].getName().getType() == ASTNode.IDENTIFIER;

						final Identifier variable = (Identifier) variableNames[j].getName();
						if (classMemberName.equals(variable.getName())) {
							fResult.add(new OccurrenceLocation(variable.getStart() - 1, variable.getLength() + 1, F_WRITE_OCCURRENCE, fDescription));
						}
					}
				}
			} else if (statement.getType() == ASTNode.CLASS_CONSTANT_DECLARATION) {
				ClassConstantDeclaration classVariableDeclaration = (ClassConstantDeclaration) statement;
				List<Identifier> variableNames = classVariableDeclaration.names();
				for (Identifier name : variableNames) {
					if (classMemberName.equals(name.getName())) {
						fResult.add(new OccurrenceLocation(name.getStart(), name.getLength(), getOccurrenceType(name), fDescription));
					}
				}
			}
		}
		//		}
		body.accept(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		// Default return is F_READ_OCCURRENCE, although the implementation of the Scalar visit might also use F_WRITE_OCCURRENCE
		if (node.getParent().getType() == ASTNode.CLASS_CONSTANT_DECLARATION) {
			return IOccurrencesFinder.F_WRITE_OCCURRENCE;
		}
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return classMemberName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}
