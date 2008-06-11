package org.eclipse.php.internal.ui.text;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.actions.SelectionConverter;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.IEditorPart;

/**
 * Provides a PHP element information to be displayed in by an information presenter.
 * This class can handle IModelElements, unlike the {@link PHPElementProvider}. 
 */
public class PHPInformationElementProvider implements IInformationProvider, IInformationProviderExtension {

	private PHPStructuredEditor fEditor;
	private boolean fUseCodeResolve;

	public PHPInformationElementProvider(IEditorPart editor) {
		fUseCodeResolve = false;
		if (editor instanceof PHPStructuredEditor)
			fEditor = (PHPStructuredEditor) editor;
	}

	public PHPInformationElementProvider(IEditorPart editor, boolean useCodeResolve) {
		this(editor);
		fUseCodeResolve = useCodeResolve;
	}

	/*
	 * @see IInformationProvider#getSubject(ITextViewer, int)
	 */
	public IRegion getSubject(ITextViewer textViewer, int offset) {
		if (textViewer != null && fEditor != null) {
			IRegion region = PHPWordFinder.findWord(textViewer.getDocument(), offset);
			if (region != null)
				return region;
			else
				return new Region(offset, 0);
		}
		return null;
	}

	/*
	 * @see IInformationProvider#getInformation(ITextViewer, IRegion)
	 */
	public String getInformation(ITextViewer textViewer, IRegion subject) {
		return getInformation2(textViewer, subject).toString();
	}

	/*
	 * @see IInformationProviderExtension#getElement(ITextViewer, IRegion)
	 */
	public Object getInformation2(ITextViewer textViewer, IRegion subject) {
		if (fEditor == null)
			return null;

		try {
			if (fUseCodeResolve) {
				IStructuredSelection sel = SelectionConverter.getStructuredSelection(fEditor);
				if (!sel.isEmpty()) {
					IModelElement inputModelElement = fEditor.getModelElement();
					if (inputModelElement instanceof ISourceModule && sel instanceof ITextSelection) {
						IModelElement modelElement = getSelectionModelElement(((ITextSelection) sel).getOffset(), 1, (ISourceModule) inputModelElement);
						if (modelElement != null) {
							return modelElement;
						} else {
							IType[] topLevelTypes = ((ISourceModule) inputModelElement).getTypes();
							if (topLevelTypes != null && topLevelTypes.length > 0 && topLevelTypes[0] instanceof IModelElement) {
								return topLevelTypes[0];
							}
						}
					}
				}
			}
			return EditorUtility.getEditorInputModelElement(fEditor, false);
		} catch (ModelException e) {
			return null;
		}
	}

	/**
	 * Returns an {@link IModelElement} from the given selection.
	 * In case that the element is not resolvable, return null.
	 * 
	 * @param selection
	 * @param sourceModule
	 * @return The {@link IModelElement} or null.
	 */
	protected IModelElement getSelectionModelElement(int offset, int length, ISourceModule sourceModule) {
		IModelElement element = null;
		try {
			Program ast = SharedASTProvider.getAST(sourceModule, SharedASTProvider.WAIT_NO, null);
			if (ast != null) {
				ASTNode selectedNode = NodeFinder.perform(ast, offset, length);
				if (selectedNode.getType() == ASTNode.IDENTIFIER) {
					element = ((Identifier) selectedNode).resolveBinding().getPHPElement();
				}
			}
		} catch (Exception e) {
			// Logger.logException(e);
		}
		if (element == null) {
			// try to get the top level
			try {
				element = sourceModule.getElementAt(offset);
			} catch (ModelException e) {
			}
		}
		return element;
	}
}
