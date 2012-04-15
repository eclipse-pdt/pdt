package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.core.resources.IStorage;
import org.eclipse.dltk.core.ICodeAssist;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.internal.ui.text.hover.AbstractScriptEditorTextHover;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IWorkingCopyManager;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.EditorsUI;

public class AbstractPHPEditorTextHover extends AbstractScriptEditorTextHover
		implements ITextHoverExtension2 {

	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		return getHoverInfo(textViewer, hoverRegion);
	}

	/*
	 * @see ITextHoverExtension#getHoverControlCreator()
	 * 
	 * @since 3.0
	 */
	public IInformationControlCreator getHoverControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent,
						EditorsUI.getTooltipAffordanceString());
			}
		};
	}

	/*
	 * @seeorg.eclipse.jface.text.ITextHoverExtension2#
	 * getInformationPresenterControlCreator()
	 * 
	 * @since 3.4
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell shell) {
				return new DefaultInformationControl(shell, true);
			}
		};
	}

	protected ICodeAssist getCodeAssist() {
		IEditorPart editor = getEditor();
		if (editor != null) {
			IEditorInput input = editor.getEditorInput();

			if (input instanceof ExternalStorageEditorInput) {
				ExternalStorageEditorInput external = (ExternalStorageEditorInput) input;
				IStorage storage = external.getStorage();
				if (storage != null) {
					if (storage instanceof ExternalSourceModule) {
						ExternalSourceModule externalSourceModule = (ExternalSourceModule) storage;
						return externalSourceModule;
					}
				}
			}

			IWorkingCopyManager manager = DLTKUIPlugin.getDefault()
					.getWorkingCopyManager();
			return manager.getWorkingCopy(input, false);
		}

		return null;
	}

	/**
	 * Returns the Java elements at the given hover region.
	 * 
	 * @param textViewer
	 *            the text viewer
	 * @param hoverRegion
	 *            the hover region
	 * @return the array with the Java elements or <code>null</code>
	 * @since 3.4
	 */
	protected IModelElement[] getElementsAt(ITextViewer textViewer,
			IRegion hoverRegion) {
		/*
		 * The region should be a word region an not of length 0. This check is
		 * needed because codeSelect(...) also finds the Java element if the
		 * offset is behind the word.
		 */
		if (hoverRegion.getLength() == 0)
			return null;
		IModelElement[] elements = null;
		ICodeAssist resolve = getCodeAssist();
		if (resolve != null) {
			try {
				elements = resolve.codeSelect(hoverRegion.getOffset(),
						hoverRegion.getLength());

				if ((elements == null || elements.length == 0)
						&& resolve instanceof ISourceModule) {
					elements = PHPModelUtils.getTypeInString(
							(ISourceModule) resolve, hoverRegion);

				}
			} catch (ModelException x) {
				return null;
			}
		}
		return elements;
	}
}
