/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.changes;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IDocumentPartitioningListener;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;

public class DummyDocument implements IDocument {

	@Override
	public void addDocumentListener(IDocumentListener listener) {

	}

	@Override
	public void addDocumentPartitioningListener(IDocumentPartitioningListener listener) {
	}

	@Override
	public void addPosition(Position position) throws BadLocationException {
	}

	@Override
	public void addPosition(String category, Position position)
			throws BadLocationException, BadPositionCategoryException {
	}

	@Override
	public void addPositionCategory(String category) {
	}

	@Override
	public void addPositionUpdater(IPositionUpdater updater) {
	}

	@Override
	public void addPrenotifiedDocumentListener(IDocumentListener documentAdapter) {
	}

	@Override
	public int computeIndexInCategory(String category, int offset)
			throws BadLocationException, BadPositionCategoryException {
		return 0;
	}

	@Override
	public int computeNumberOfLines(String text) {
		return 0;
	}

	@Override
	public ITypedRegion[] computePartitioning(int offset, int length) throws BadLocationException {
		return null;
	}

	@Override
	public boolean containsPosition(String category, int offset, int length) {
		return false;
	}

	@Override
	public boolean containsPositionCategory(String category) {
		return false;
	}

	@Override
	public String get() {
		return null;
	}

	@Override
	public String get(int offset, int length) throws BadLocationException {
		return null;
	}

	@Override
	public char getChar(int offset) throws BadLocationException {
		return 0;
	}

	@Override
	public String getContentType(int offset) throws BadLocationException {
		return null;
	}

	@Override
	public IDocumentPartitioner getDocumentPartitioner() {
		return null;
	}

	@Override
	public String[] getLegalContentTypes() {
		return null;
	}

	@Override
	public String[] getLegalLineDelimiters() {
		return null;
	}

	@Override
	public int getLength() {
		return 0;
	}

	@Override
	public String getLineDelimiter(int line) throws BadLocationException {
		return null;
	}

	@Override
	public IRegion getLineInformation(int line) throws BadLocationException {
		return null;
	}

	@Override
	public IRegion getLineInformationOfOffset(int offset) throws BadLocationException {
		return null;
	}

	@Override
	public int getLineLength(int line) throws BadLocationException {
		return 0;
	}

	@Override
	public int getLineOfOffset(int offset) throws BadLocationException {
		return 0;
	}

	@Override
	public int getLineOffset(int line) throws BadLocationException {
		return 0;
	}

	@Override
	public int getNumberOfLines() {
		return 0;
	}

	@Override
	public int getNumberOfLines(int offset, int length) throws BadLocationException {
		return 0;
	}

	@Override
	public ITypedRegion getPartition(int offset) throws BadLocationException {
		return null;
	}

	@Override
	public String[] getPositionCategories() {
		return null;
	}

	@Override
	public IPositionUpdater[] getPositionUpdaters() {
		return null;
	}

	@Override
	public Position[] getPositions(String category) throws BadPositionCategoryException {
		return null;
	}

	@Override
	public void insertPositionUpdater(IPositionUpdater updater, int index) {
	}

	@Override
	public void removeDocumentListener(IDocumentListener listener) {
	}

	@Override
	public void removeDocumentPartitioningListener(IDocumentPartitioningListener listener) {
	}

	@Override
	public void removePosition(Position position) {
	}

	@Override
	public void removePosition(String category, Position position) throws BadPositionCategoryException {
	}

	@Override
	public void removePositionCategory(String category) throws BadPositionCategoryException {
	}

	@Override
	public void removePositionUpdater(IPositionUpdater updater) {
	}

	@Override
	public void removePrenotifiedDocumentListener(IDocumentListener documentAdapter) {
	}

	@Override
	public void replace(int offset, int length, String text) throws BadLocationException {
	}

	@Override
	public int search(int startOffset, String findString, boolean forwardSearch, boolean caseSensitive,
			boolean wholeWord) throws BadLocationException {
		return 0;
	}

	@Override
	public void set(String text) {
	}

	@Override
	public void setDocumentPartitioner(IDocumentPartitioner partitioner) {
	}
}