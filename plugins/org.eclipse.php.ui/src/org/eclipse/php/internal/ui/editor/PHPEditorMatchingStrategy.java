package org.eclipse.php.internal.ui.editor;

import java.io.File;

import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.containers.LocalFileStorageEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.internal.util.Util;

/**
 * This class provides strategy of matching PHP editor inputs.
 * First check whether the editor input is {@link FileStoreEditorInput}, which can be
 * received from Eclipse platform; if it is - convert it to {@link LocalFileStorageEditorInput}
 * - WST-compatible editor input.
 * 
 * @author Michael
 */
public class PHPEditorMatchingStrategy implements IEditorMatchingStrategy {

	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		if (input instanceof FileStoreEditorInput) {
			input = new LocalFileStorageEditorInput(new LocalFileStorage(new File(((FileStoreEditorInput) input).getURI())));
		}
		try {
			IEditorInput editorInput = editorRef.getEditorInput();
			return Util.equals(editorInput, input);
		} catch (PartInitException e) {
			Logger.logException(e);
		}
		return false;
	}
}
