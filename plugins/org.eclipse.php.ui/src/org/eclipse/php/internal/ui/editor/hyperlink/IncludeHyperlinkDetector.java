package org.eclipse.php.internal.ui.editor.hyperlink;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.compiler.ast.nodes.Include;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.ui.editor.hover.IHyperlinkDetectorForPHP;
import org.eclipse.wst.jsdt.web.ui.internal.Logger;

public class IncludeHyperlinkDetector implements IHyperlinkDetectorForPHP {

	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {

		PHPStructuredEditor editor = EditorUtility.getPHPStructuredEditor(textViewer);
		if (editor == null) {
			return null;
		}
		
		IModelElement input = org.eclipse.dltk.internal.ui.editor.EditorUtility.getEditorInputModelElement(editor, false);
		if (!(input instanceof ISourceModule)) {
			return null;
		}

		final int offset = region.getOffset();
		final String file[] = new String[1];
		final Region selectRegion[] = new Region[1];
		
		final ISourceModule sourceModule = (ISourceModule) input;
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		
		ASTVisitor visitor = new ASTVisitor() {
			boolean found = false;
			
			public boolean visit(Expression expr) throws ModelException {
				if (expr.sourceStart() < offset && expr.sourceEnd() > offset) {
					if (expr instanceof Include) {
						Expression fileExpr = ((Include) expr).getExpr();
						if (fileExpr instanceof Scalar) {
							IBuffer buffer = sourceModule.getBuffer();
							if (buffer != null) {
								int start = fileExpr.sourceStart();
								int length = fileExpr.sourceEnd() - start;
								file[0] = ASTUtils.stripQuotes(buffer.getText(start, length));
								selectRegion[0] = new Region(start, length);
							}
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
			ISourceModule includedSourceModule = FileNetworkUtility.findSourceModule(sourceModule, file[0]);
			return new IHyperlink[] { new ModelElementHyperlink(selectRegion[0], includedSourceModule, new OpenAction(editor)) };
		}
		return null;
	}
}
