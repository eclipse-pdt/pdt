package org.eclipse.php.internal.ui;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.core.util.text.TextSequenceUtilities;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.ui.internal.FileDropAction;

public class IncludeDropAction extends FileDropAction {

	public boolean run(DropTargetEvent event, IEditorPart targetEditor) {
		if (!(targetEditor instanceof PHPStructuredEditor))
			return super.run(event, targetEditor);
		PHPStructuredEditor phpEditor = (PHPStructuredEditor) targetEditor;
		final String[] fileNames = (String[]) event.data;
		if (fileNames == null || fileNames.length == 0) {
			return false;
		}

		List/* <String> */phpFileNames = new ArrayList();
		for (int i = 0; i < fileNames.length; ++i) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(fileNames[i]));
			PHPFileData fileData = null;
			if (file != null)
				fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(file, false);
			else
				fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(fileNames[i], false);
			if (fileData != null) {
				phpFileNames.add(fileData.getName());
			}
		}
		if (phpFileNames.size() != 0 && insert(phpFileNames, phpEditor)) {
			return true;
		}
		// default behavior
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				EditorUtility.openFilesInEditor(Arrays.asList(fileNames));
			}
		});
		return true;
		// return super.run(event, targetEditor);
	}

	private static String getPartitionType(IStructuredDocument document, int offset) {
		IStructuredDocumentRegion region = document.getRegionAtCharacterOffset(offset);
		if (region == null)
			return null;
		TextSequence statement = PHPTextSequenceUtilities.getStatement(offset, region, false);
		if (statement == null)
			return null;
		String partitionType = TextSequenceUtilities.getTypeByAbsoluteOffset(statement, offset);
		return partitionType;
	}

	protected boolean insert(List phpFiles, PHPStructuredEditor targetEditor) {
		ISelection selection = targetEditor.getSelectionProvider().getSelection();
		if (!(selection instanceof ITextSelection))
			return false;
		ITextSelection textSelection = (ITextSelection) selection;
		int offset = textSelection.getOffset();

		boolean isPHPState = false;
		IStructuredDocument document = (IStructuredDocument) targetEditor.getDocumentProvider().getDocument(targetEditor.getEditorInput());
		IStructuredModel model = StructuredModelManager.getModelManager().getModelForRead(document);
		if (!(model instanceof DOMModelForPHP)) {
			model.releaseFromRead();
			return false;
		}

		// the stupid bug - if in the end of line it inserts in the previous offset:
		int line = document.getLineOfOffset(offset);
		int lineEndOffset = -1;
		try {
			IRegion lineInformation = document.getLineInformation(line);
			String lineString = document.get(lineInformation.getOffset(), lineInformation.getLength()).replaceAll("[\n\r]+", ""); //$NON-NLS-1$ //$NON-NLS-2$
			lineEndOffset = lineInformation.getOffset() + lineString.length();

		} catch (BadLocationException e) {
		}
		if (lineEndOffset > 0 && offset == lineEndOffset - 1 && document.getLength() >= offset) {
			offset++;
		}

		String partitionType = getPartitionType(document, offset);
		isPHPState = PHPPartitionTypes.isPHPRegularState(partitionType) || PHPPartitionTypes.isPHPQuotesState(partitionType);
		if (!isPHPState) {
			return false;
		}
		insert(phpFiles, document, offset, (DOMModelForPHP) model);
		model.releaseFromRead();
		return true;
	}

	private void insert(List phpFileNames, IStructuredDocument document, int insertionOffset, DOMModelForPHP model) {
		PHPFileData currentFileData = model.getFileData();
		IFile file = (IFile) PHPModelUtil.getResource(currentFileData);
		boolean shrinkPaths = file != null && file.exists();
		IProject project = file.getProject();
		StringBuffer string = new StringBuffer();
		for (Iterator i = phpFileNames.iterator(); i.hasNext();) {
			String fileName = (String) i.next();
			if (shrinkPaths) {
				fileName = PHPModelUtil.getRelativeLocation(project, fileName);
			}
			string.append(MessageFormat.format("include_once ''{0}'';", new Object[] { fileName })); //$NON-NLS-1$
			string.append(document.getLineDelimiter());
		}
		try {
			document.replace(insertionOffset, 0, string.toString());
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
	}
}
