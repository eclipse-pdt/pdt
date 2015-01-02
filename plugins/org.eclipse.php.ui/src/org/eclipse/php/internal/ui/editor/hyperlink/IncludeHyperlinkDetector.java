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

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;

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

		final ISourceModule sourceModule = (ISourceModule) input;
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		if (moduleDeclaration == null) {
			return null;
		}

		IncludeHyperlinkVisitor includeVisitor = new IncludeHyperlinkVisitor(
				offset, sourceModule);
		try {
			moduleDeclaration.traverse(includeVisitor);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}

		if (includeVisitor.getFile() != null) {
			if (!inclusive(region, includeVisitor.getSelectRegion())) {
				return null;
			}

			Set<String> set = new HashSet<String>();
			if (sourceModule.getResource() != null) {
				set.add(sourceModule.getResource().getLocation().toOSString());
			}
			ISourceModule includedSourceModule = FileNetworkUtility
					.findSourceModule(sourceModule, includeVisitor.getFile(),
							set);
			if (includedSourceModule != null) {
				return new IHyperlink[] { new ModelElementHyperlink(
						includeVisitor.getSelectRegion(), includedSourceModule,
						new OpenAction(editor)) };
			}
		}
		return null;
	}

	/**
	 * @return true if region1 is included in region2
	 */
	private boolean inclusive(IRegion region1, Region region2) {
		if (region1 == null || region2 == null) {
			return false;
		}
		return (region1.getOffset() >= region2.getOffset())
				&& (region1.getOffset() + region1.getLength() <= region2
						.getOffset() + region2.getLength());
	}
}
