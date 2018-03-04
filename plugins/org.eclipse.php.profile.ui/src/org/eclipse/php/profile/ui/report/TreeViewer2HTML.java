/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.report;

import java.io.PrintWriter;
import java.util.Random;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.profile.ui.views.IHTMLPresentableTreeElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;

/**
 * Tree viewer to HTML.
 */
public class TreeViewer2HTML {

	private PrintWriter fWriter;
	private TreeViewer fTreeViewer;
	private Tree fTree;
	private ITableLabelProvider fLabelProvider;
	private ITreeContentProvider fContentProvider;
	private String fTableId;

	public TreeViewer2HTML(TreeViewer treeViewer, PrintWriter writer) {
		fTreeViewer = treeViewer;
		fTree = treeViewer.getTree();
		fLabelProvider = (ITableLabelProvider) treeViewer.getLabelProvider();
		fContentProvider = (ITreeContentProvider) treeViewer.getContentProvider();
		fTableId = Integer.toHexString(new Random().nextInt()).substring(0, 3).toUpperCase();

		fWriter = writer;
	}

	/**
	 * Prints header's section of the table
	 * 
	 * @param Tree
	 *            tree
	 * @param StringBuilder
	 *            string buffer to append the output to
	 */
	private void generateTableHeader() {
		fWriter.println("<tr>"); //$NON-NLS-1$
		int columnsNum = fTree.getColumnCount();
		for (int i = 0; i < columnsNum; ++i) {
			fWriter.print("<th>"); //$NON-NLS-1$
			fWriter.print(fTree.getColumn(i).getText());
			fWriter.println("</th>"); //$NON-NLS-1$
		}
		fWriter.println("</tr>"); //$NON-NLS-1$
	}

	/**
	 * Generates table row
	 * 
	 * @param Object
	 *            [] current items
	 * @param int
	 *            number of tabs
	 * @param String
	 *            row id prefix (initial=table's prefix, then id_0, id_0_0, id_1,
	 *            etc...)
	 */
	private void generateTableRow(Object[] items, int tabs, String idPrefix) {
		if (items == null || items.length == 0) {
			return;
		}

		StringBuilder tabsBuf = new StringBuilder();
		for (int i = 0; i < tabs; ++i) {
			tabsBuf.append("&nbsp;&nbsp;&nbsp;"); //$NON-NLS-1$
		}

		for (int i = 0; i < items.length; ++i) {
			int numColumns = fTree.getColumnCount();
			String newId = idPrefix + "_" + i; //$NON-NLS-1$
			fWriter.print("<tr id="); //$NON-NLS-1$
			fWriter.print(newId);
			if (items[i] instanceof IHTMLPresentableTreeElement) {
				fWriter.print(" class=\""); //$NON-NLS-1$
				fWriter.print(((IHTMLPresentableTreeElement) items[i]).getTableRowClass());
				fWriter.print("\""); //$NON-NLS-1$
			}
			fWriter.println(">"); //$NON-NLS-1$

			for (int j = 0; j < numColumns; ++j) {
				String text = fLabelProvider.getColumnText(items[i], j);
				fWriter.print("\t<td"); //$NON-NLS-1$
				if (text == null) {
					fWriter.print(" align=right"); //$NON-NLS-1$
					text = "-"; //$NON-NLS-1$
				} else {
					switch (fTree.getColumn(j).getAlignment()) {
					case SWT.LEFT:
						fWriter.print(" align=left"); //$NON-NLS-1$
						break;
					case SWT.CENTER:
						fWriter.print(" align=center"); //$NON-NLS-1$
						break;
					case SWT.RIGHT:
						fWriter.print(" align=right"); //$NON-NLS-1$
						break;
					}
				}
				fWriter.print(">"); //$NON-NLS-1$
				if (j == 0) {
					fWriter.print(tabsBuf);
					if (fContentProvider.hasChildren(items[i])) {
						fWriter.print("<a href=\"#\""); //$NON-NLS-1$
						if (items[i] instanceof IHTMLPresentableTreeElement) {
							fWriter.print(" class=\""); //$NON-NLS-1$
							fWriter.print(((IHTMLPresentableTreeElement) items[i]).getTableLinkClass());
							fWriter.print("\""); //$NON-NLS-1$
						}
						fWriter.print(" onclick=\"return toggle('"); //$NON-NLS-1$
						fWriter.print(newId);
						fWriter.print("');\">"); //$NON-NLS-1$

						fWriter.print("<img id=\""); //$NON-NLS-1$
						fWriter.print(newId);
						fWriter.print("_s\" class=\"fold\" src=\""); //$NON-NLS-1$
						fWriter.print(HTMLReporter.IMAGES_DIR);
						fWriter.print("/minus.png\"/>"); //$NON-NLS-1$
					}

					fWriter.print("<img class=\"type\" src=\""); //$NON-NLS-1$
					fWriter.print(HTMLReporter.IMAGES_DIR);
					fWriter.print("/"); //$NON-NLS-1$
					if (items[i] instanceof IHTMLPresentableTreeElement) {
						fWriter.print(((IHTMLPresentableTreeElement) items[i]).getImageURL());
					}
					fWriter.print("\"/>"); //$NON-NLS-1$
				}
				fWriter.print(text);
				fWriter.println("</td>"); //$NON-NLS-1$
			}
			fWriter.println("</tr>"); //$NON-NLS-1$
			generateTableRow(fContentProvider.getChildren(items[i]), tabs + 1, newId);
		}
	}

	/**
	 * Generates table rows
	 */
	private void generateTableRows() {
		generateTableRow(fContentProvider.getChildren(fTreeViewer.getInput()), 0, fTableId);
	}

	/**
	 * Generate HTML table from the tree viewer
	 */
	private void generateTable() {

		fWriter.print("<table class=\"tree\" id="); //$NON-NLS-1$
		fWriter.print(fTableId);
		fWriter.println(">"); //$NON-NLS-1$
		fWriter.println("<colgroup><col width=\"200\" /><col width=\"0*\" /></colgroup>"); //$NON-NLS-1$

		generateTableHeader();
		generateTableRows();

		fWriter.println("</table>"); //$NON-NLS-1$
	}

	/**
	 * Converts TreeViewer viewer to HTML table representation.
	 * 
	 * @param TreeViewer
	 *            viewer
	 * @param OutputStream
	 *            stream to write the output to
	 */
	public static void process(TreeViewer treeViewer, PrintWriter writer) {
		TreeViewer2HTML treeViewer2HTML = new TreeViewer2HTML(treeViewer, writer);
		treeViewer2HTML.generateTable();
	}
}
