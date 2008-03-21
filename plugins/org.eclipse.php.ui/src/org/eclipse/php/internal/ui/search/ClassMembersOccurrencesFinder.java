package org.eclipse.php.internal.ui.search;

import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Class members occurrences finder.
 * 
 * @author shalom
 * FIXME - Partial implementation
 */
public class ClassMembersOccurrencesFinder extends AbstractOccurrencesFinder {
	public static final String ID = "ClassMembersOccurrencesFinder"; //$NON-NLS-1$
	private String classMemberName; // The member's name
	private String typeDeclarationName; // Class or Interface name
	private boolean isFunction;

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		typeDeclarationName = null;
		isFunction = false;
		if (node.getType() == ASTNode.IDENTIFIER) {
			Identifier identifier = (Identifier) node;
			classMemberName = identifier.getName();
			// IBinding binding = identifier.resolveBinding(); // FIXME - This should be implemented...
			ASTNode parent = identifier.getParent();
			int type = parent.getType();
			isFunction = type == ASTNode.FUNCTION_DECLARATION;
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
		fDescription = Messages.format("Occurrance of ''{0}()", classMemberName);
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
	 * context +
	 * Mark foo on: ... public foo() { }; ...
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		return super.visit(functionDeclaration);
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
		checkDispatch(methodInvocation.getMember().getStart());
		return super.visit(methodInvocation);
	}

	/**
	 * Mark var on: $a->var;
	 */
	public boolean visit(FieldAccess fieldAccess) {
		checkDispatch(fieldAccess.getMember().getStart());
		return super.visit(fieldAccess);
	}

	/**
	 * Mark CON on: MyClass::CON;
	 */
	public boolean visit(StaticConstantAccess classConstantAccess) {
		if (classConstantAccess.getClassName().getName().equals(typeDeclarationName)) {
			Identifier constant = classConstantAccess.getConstant();
			if (classMemberName.equals(constant.getName())) {
				fResult.add(new OccurrenceLocation(constant.getStart(), constant.getLength(), getOccurrenceReadWriteType(constant), fDescription));
			}
		}
		return true;
	}

	/**
	 * Mark foo() on: MyClass::foo();
	 */
	public boolean visit(StaticMethodInvocation methodInvocation) {
		checkDispatch(methodInvocation.getMember().getStart());
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
		checkDispatch(start);
		return super.visit(fieldAccess);
	}

	/**
	 * @param dispatch
	 * @throws RuntimeException
	 * FIXME - Use IBinding to check the dispatch.
	 */
	private void checkDispatch(int start) throws RuntimeException {
		//		try {
		//			IStructuredModel existingModel;
		//			IStructuredDocument structuredDocument = null;
		//
		//			if (changedFile instanceof ExternalFileWrapper) {
		//				existingModel = StructuredModelManager.getModelManager().getExistingModelForRead(changedFile.getFullPath().toOSString());
		//			} else {
		//				existingModel = StructuredModelManager.getModelManager().getModelForRead(changedFile);
		//			}
		//
		//			if (existingModel != null) {
		//				structuredDocument = existingModel.getStructuredDocument();
		//			} else {
		//				structuredDocument = StructuredModelManager.getModelManager().createStructuredDocumentFor(changedFile);
		//			}
		//
		//			final PHPFileData fileModel = PHPWorkspaceModelManager.getInstance().getModelForFile(changedFile, false);
		//			if (fileModel == null) {
		//				throw new RuntimeException();
		//			}
		//
		//			final CodeData[] resolve = CodeDataResolver.getInstance().resolve(structuredDocument, start, projectModel, fileModel);
		//			if (resolve != null && resolve.length > 0) {
		//				if (resolve[0] == propertyCodeData) {
		//					addChange(start);
		//				}
		//			}
		//		} catch (Exception e) {
		//			throw new RuntimeException(e);
		//		}
	}

	private void checkTypeDeclaration(TypeDeclaration typeDeclaration) {
		assert typeDeclaration != null;

		Identifier className = typeDeclaration.getName();
		Block body = typeDeclaration.getBody();

		// definitions of the class property
		if (className.getName().equalsIgnoreCase(typeDeclarationName)) {
			List<Statement> statements = body.statements();
			for (Statement statement : statements) {
				if (statement.getType() == ASTNode.METHOD_DECLARATION) {
					final MethodDeclaration classMethodDeclaration = (MethodDeclaration) statement;
					if (isFunction) {
						final Identifier functionName = classMethodDeclaration.getFunction().getFunctionName();
						if (classMemberName.equalsIgnoreCase(functionName.getName())) {
							fResult.add(new OccurrenceLocation(functionName.getStart(), functionName.getLength(), getOccurrenceReadWriteType(functionName), fDescription));
						}
					}
				} else if (statement.getType() == ASTNode.FIELD_DECLARATION) {
					FieldsDeclaration classVariableDeclaration = (FieldsDeclaration) statement;
					final Variable[] variableNames = classVariableDeclaration.getVariableNames();
					for (int j = 0; j < variableNames.length; j++) {
						// safe cast to identifier
						assert variableNames[j].getName().getType() == ASTNode.IDENTIFIER;

						final Identifier variable = (Identifier) variableNames[j].getName();
						if (classMemberName.equals(variable.getName())) {
							fResult.add(new OccurrenceLocation(variable.getStart(), variable.getLength(), getOccurrenceReadWriteType(variable), fDescription));
						}
					}
				} else if (statement.getType() == ASTNode.CLASS_CONSTANT_DECLARATION) {
					ClassConstantDeclaration classVariableDeclaration = (ClassConstantDeclaration) statement;
					List<Identifier> variableNames = classVariableDeclaration.names();
					for (Identifier name : variableNames) {
						if (classMemberName.equals(name.getName())) {
							fResult.add(new OccurrenceLocation(name.getStart(), name.getLength(), getOccurrenceReadWriteType(name), fDescription));
						}
					}
				}
			}
		}
		body.accept(this);
	}

	/**
	 * @param identifier
	 */
	private void checkIdentifier(Identifier identifier) {
		fResult.add(new OccurrenceLocation(identifier.getStart(), identifier.getLength(), getOccurrenceReadWriteType(identifier), fDescription));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceReadWriteType(ASTNode node) {
		// Default return is F_READ_OCCURRENCE, although the implementation of the Scalar visit might also use F_WRITE_OCCURRENCE
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
