/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.compiler.ast.nodes.Include;
import org.eclipse.php.internal.core.compiler.ast.nodes.InfixExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.wst.jsdt.web.ui.internal.Logger;

public class IncludeHyperlinkDetector extends AbstractHyperlinkDetector {

	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		PHPStructuredEditor editor = EditorUtility.getPHPEditor(textViewer);
		if (editor == null) {
			return null;
		}

		IModelElement input = org.eclipse.dltk.internal.ui.editor.EditorUtility
				.getEditorInputModelElement(editor, false);
		if (!(input instanceof ISourceModule)) {
			return null;
		}

		final int offset = region.getOffset();
		final String file[] = new String[1];
		final Region selectRegion[] = new Region[1];

		final ISourceModule sourceModule = (ISourceModule) input;
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);

		ASTVisitor visitor = new ASTVisitor() {
			boolean found = false;

			public boolean visit(Expression expr) throws ModelException {
				if (expr.sourceStart() < offset && expr.sourceEnd() > offset) {
					if (expr instanceof Include) {
						Expression fileExpr = ((Include) expr).getExpr();
						if (fileExpr instanceof InfixExpression) {
							InfixExpression ie = (InfixExpression) fileExpr;
							if (ie.getRight() instanceof Scalar) {
								fileExpr = ie.getRight();
							}
						}
						if (fileExpr instanceof Scalar) {
							String value = ((Scalar) fileExpr).getValue();
							file[0] = ASTUtils.stripQuotes(value);
							file[0] = file[0].trim();

							// only select file, without quotes or surrounding
							// whitespaces
							int startIdx = fileExpr.sourceStart()
									+ value.indexOf(file[0]);
							int length = file[0].length();
							selectRegion[0] = new Region(startIdx, length);
						}
						found = true;
						return false;
					}
				}
				return !found;
			}

			public boolean visitGeneral(ASTNode n) {
				return !found;
			}
		};

		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		if (file[0] != null) {
			if (!inclusive(region, selectRegion[0]))
				return null;

			Set<String> set = new HashSet<String>();
			set.add(sourceModule.getResource().getLocation().toOSString());
			ISourceModule includedSourceModule = FileNetworkUtility
					.findSourceModule(sourceModule, file[0], set);
			if (includedSourceModule != null) {
				return new IHyperlink[] { new ModelElementHyperlink(
						selectRegion[0], includedSourceModule, new OpenAction(
								editor)) };
			}
		}
		return null;
	}

	/**
	 * @return true if region1 is included in region2
	 */
	private boolean inclusive(IRegion region1, Region region2) {
		return (region1.getOffset() >= region2.getOffset())
				&& (region1.getOffset() + region1.getLength() <= region2
						.getOffset() + region2.getLength());
	}
}
