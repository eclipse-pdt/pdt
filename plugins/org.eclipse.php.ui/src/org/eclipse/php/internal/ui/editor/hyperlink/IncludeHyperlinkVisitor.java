/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.hyperlink;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.compiler.ast.nodes.Include;
import org.eclipse.php.core.compiler.ast.nodes.InfixExpression;
import org.eclipse.php.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;

public class IncludeHyperlinkVisitor extends ASTVisitor {

	private final static String DIRNAME_METHOD = "dirname"; //$NON-NLS-1$
	private final static String DIR_CONST = "__DIR__"; //$NON-NLS-1$
	private final static String FILE_CONST = "__FILE__"; //$NON-NLS-1$

	private boolean found = false;
	private int offset;
	private ISourceModule sourceModule;
	private StringBuilder filePath;
	private Region selectRegion;

	public IncludeHyperlinkVisitor(int offset, ISourceModule sourceModule) {
		this.offset = offset;
		this.sourceModule = sourceModule;
		this.filePath = new StringBuilder();
	}

	@Override
	public boolean visit(Expression expr) throws ModelException {
		if (expr.sourceStart() < offset && expr.sourceEnd() > offset) {
			if (expr instanceof Include) {
				Expression fileExpr = ((Include) expr).getExpr();
				if (fileExpr instanceof InfixExpression) {
					processInfixExpression((InfixExpression) fileExpr);
				} else if (fileExpr instanceof Scalar) {
					processScalar((Scalar) fileExpr);
				}
				selectRegion = new Region(fileExpr.sourceStart(), fileExpr.sourceEnd() - fileExpr.sourceStart());
				found = true;
				return false;
			}
		}
		return !found;
	}

	private void processInfixExpression(InfixExpression infixExpression) {
		if (!infixExpression.getOperator().equals(".")) { //$NON-NLS-1$
			return;
		}
		for (ASTNode child : infixExpression.getChilds()) {
			if (child instanceof InfixExpression) {
				processInfixExpression((InfixExpression) child);
			} else if (child instanceof Scalar) {
				processScalar((Scalar) child);
			} else if (child instanceof PHPCallExpression) {
				String tmpPath = resolvePHPCallExpression((PHPCallExpression) child);
				if (tmpPath != null) {
					filePath = filePath.append(tmpPath);
				}
			}
		}
	}

	private void processScalar(Scalar scalar) {
		String resolvedValue = resolveScalarValue(scalar);
		if (resolvedValue == null) {
			return;
		}
		filePath = filePath.append(resolvedValue);
	}

	/**
	 * Resolves call expression. Actually only expressions with 'dirname' method
	 * and only first argument from call list is resolved.
	 * 
	 * @param callExpression
	 * @return path resolved from expression
	 */
	private String resolvePHPCallExpression(PHPCallExpression callExpression) {
		if (callExpression.getCallName().getName().equals(DIRNAME_METHOD)) {
			CallArgumentsList argsList = callExpression.getArgs();
			for (ASTNode astNode : argsList.getChilds()) {
				if (astNode instanceof Scalar) {
					return resolveDirname(resolveScalarValue((Scalar) astNode));
				} else if (astNode instanceof PHPCallExpression) {
					return resolveDirname(resolvePHPCallExpression((PHPCallExpression) astNode));
				}
			}
		}
		return null;
	}

	private String resolveScalarValue(Scalar scalar) {
		if (scalar.getValue() == DIR_CONST) {
			return sourceModule.getResource().getLocation().removeLastSegments(1).toOSString();
		} else if (scalar.getValue() == FILE_CONST) {
			return sourceModule.getResource().getLocation().toOSString();
		} else {
			String value = scalar.getValue();
			return ASTUtils.stripQuotes(value).trim();
		}
	}

	private String resolveDirname(String path) {
		if (path == null) {
			return null;
		}
		return new Path(path).removeLastSegments(1).toOSString();
	}

	@Override
	public boolean visitGeneral(ASTNode n) {
		return !found;
	}

	public String getFile() {
		return filePath.toString();
	}

	public Region getSelectRegion() {
		return selectRegion;
	}

};