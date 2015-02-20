/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.php.ui.tests.formatter.autoedit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.BufferChangedEvent;
import org.eclipse.dltk.core.IBuffer;
import org.eclipse.dltk.core.IBufferChangedListener;
import org.eclipse.dltk.core.IOpenable;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultLineTracker;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ISynchronizable;

/**
 * Adapts <code>IDocument</code> to <code>IBuffer</code>. Uses the same
 * algorithm as the text widget to determine the buffer's line delimiter. All
 * text inserted into the buffer is converted to this line delimiter. This class
 * is <code>public</code> for test purposes only.
 */
public class DocumentAdapter implements IBuffer, IDocumentListener {

	/**
	 * Internal implementation of a NULL instanceof IBuffer.
	 */
	static private class NullBuffer implements IBuffer {
		public void addBufferChangedListener(IBufferChangedListener listener) {
		}

		public void append(char[] text) {
		}

		public void append(String text) {
		}

		public void close() {
		}

		public char getChar(int position) {
			return 0;
		}

		public char[] getCharacters() {
			return null;
		}

		public String getContents() {
			return null;
		}

		public int getLength() {
			return 0;
		}

		public IOpenable getOwner() {
			return null;
		}

		public String getText(int offset, int length) {
			return null;
		}

		public IResource getUnderlyingResource() {
			return null;
		}

		public boolean hasUnsavedChanges() {
			return false;
		}

		public boolean isClosed() {
			return false;
		}

		public boolean isReadOnly() {
			return true;
		}

		public void removeBufferChangedListener(IBufferChangedListener listener) {
		}

		public void replace(int position, int length, char[] text) {
		}

		public void replace(int position, int length, String text) {
		}

		public void save(IProgressMonitor progress, boolean force)
				throws ModelException {
		}

		public void setContents(char[] contents) {
		}

		public void setContents(String contents) {
		}
	}

	/** NULL implementing <code>IBuffer</code> */
	public final static IBuffer NULL = new NullBuffer();

	/*
	 *
		 */
	private IPath fPath;

	/**
	 * Executes a document set content call in the ui thread.
	 */
	protected class DocumentSetCommand implements Runnable {

		private String fContents;

		public void run() {
			fDocument.set(fContents);
		}

		public void set(String contents) {
			fContents = contents;
			// Display.getDefault().syncExec(this);
			DocumentAdapter.run(this);
		}
	}

	/**
	 * Executes a document replace call in the ui thread.
	 */
	protected class DocumentReplaceCommand implements Runnable {

		private int fOffset;
		private int fLength;
		private String fText;

		public void run() {
			try {
				fDocument.replace(fOffset, fLength, fText);
			} catch (BadLocationException x) {
				// ignore
			}
		}

		public void replace(int offset, int length, String text) {
			fOffset = offset;
			fLength = length;
			fText = text;
		}
	}

	private static final boolean DEBUG_LINE_DELIMITERS = true;

	private IOpenable fOwner;
	private IFile fFile;
	private ITextFileBuffer fTextFileBuffer;
	private IDocument fDocument;

	private DocumentSetCommand fSetCmd = new DocumentSetCommand();
	private DocumentReplaceCommand fReplaceCmd = new DocumentReplaceCommand();

	private Set fLegalLineDelimiters;

	private List fBufferListeners = new ArrayList(3);
	private IStatus fStatus;

	/**
	 * Constructs a new document adapter.
	 */
	public DocumentAdapter(IOpenable owner, IFile file) {

		fOwner = owner;
		fFile = file;
		fPath = fFile.getFullPath();

		initialize();
	}

	/**
	 * Constructs a new document adapter.
	 * 
	 * 
	 */
	public DocumentAdapter(IOpenable owner, IPath path) {
		Assert.isLegal(path != null);

		fOwner = owner;
		fPath = path;

		initialize();
	}

	private void initialize() {
		ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
		try {
			manager.connect(fPath, LocationKind.NORMALIZE,
					new NullProgressMonitor());
			fTextFileBuffer = manager.getTextFileBuffer(fPath,
					LocationKind.NORMALIZE);
			fDocument = fTextFileBuffer.getDocument();
		} catch (CoreException x) {
			fStatus = x.getStatus();
			fDocument = manager.createEmptyDocument(fPath,
					LocationKind.NORMALIZE);
			if (fDocument instanceof ISynchronizable)
				((ISynchronizable) fDocument).setLockObject(new Object());
		}
		fDocument.addPrenotifiedDocumentListener(this);
	}

	/**
	 * Returns the status of this document adapter.
	 */
	public IStatus getStatus() {
		if (fStatus != null)
			return fStatus;
		if (fTextFileBuffer != null)
			return fTextFileBuffer.getStatus();
		return null;
	}

	/**
	 * Returns the adapted document.
	 * 
	 * @return the adapted document
	 */
	public IDocument getDocument() {
		return fDocument;
	}

	/*
	 * @see IBuffer#addBufferChangedListener(IBufferChangedListener)
	 */
	public void addBufferChangedListener(IBufferChangedListener listener) {
		Assert.isNotNull(listener);
		if (!fBufferListeners.contains(listener))
			fBufferListeners.add(listener);
	}

	/*
	 * @see IBuffer#removeBufferChangedListener(IBufferChangedListener)
	 */
	public void removeBufferChangedListener(IBufferChangedListener listener) {
		Assert.isNotNull(listener);
		fBufferListeners.remove(listener);
	}

	/*
	 * @see IBuffer#append(char[])
	 */
	public void append(char[] text) {
		append(new String(text));
	}

	/*
	 * @see IBuffer#append(String)
	 */
	public void append(String text) {
		if (DEBUG_LINE_DELIMITERS) {
			validateLineDelimiters(text);
		}
		fReplaceCmd.replace(fDocument.getLength(), 0, text);
	}

	/*
	 * @see IBuffer#close()
	 */
	public void close() {

		if (isClosed())
			return;

		IDocument d = fDocument;
		fDocument = null;
		d.removePrenotifiedDocumentListener(this);

		if (fTextFileBuffer != null) {
			ITextFileBufferManager manager = FileBuffers
					.getTextFileBufferManager();
			try {
				manager.disconnect(fTextFileBuffer.getLocation(),
						LocationKind.NORMALIZE, new NullProgressMonitor());
			} catch (CoreException x) {
				// ignore
			}
			fTextFileBuffer = null;
		}

		fireBufferChanged(new BufferChangedEvent(this, 0, 0, null));
		fBufferListeners.clear();
	}

	/*
	 * @see IBuffer#getChar(int)
	 */
	public char getChar(int position) {
		try {
			return fDocument.getChar(position);
		} catch (BadLocationException x) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/*
	 * @see IBuffer#getCharacters()
	 */
	public char[] getCharacters() {
		String content = getContents();
		return content == null ? null : content.toCharArray();
	}

	/*
	 * @see IBuffer#getContents()
	 */
	public String getContents() {
		return fDocument.get();
	}

	/*
	 * @see IBuffer#getLength()
	 */
	public int getLength() {
		return fDocument.getLength();
	}

	/*
	 * @see IBuffer#getOwner()
	 */
	public IOpenable getOwner() {
		return fOwner;
	}

	/*
	 * @see IBuffer#getText(int, int)
	 */
	public String getText(int offset, int length) {
		try {
			return fDocument.get(offset, length);
		} catch (BadLocationException x) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/*
	 * @see IBuffer#getUnderlyingResource()
	 */
	public IResource getUnderlyingResource() {
		return fFile;
	}

	/*
	 * @see IBuffer#hasUnsavedChanges()
	 */
	public boolean hasUnsavedChanges() {
		return fTextFileBuffer != null ? fTextFileBuffer.isDirty() : false;
	}

	/*
	 * @see IBuffer#isClosed()
	 */
	public boolean isClosed() {
		return fDocument == null;
	}

	/*
	 * @see IBuffer#isReadOnly()
	 */
	public boolean isReadOnly() {
		IResource resource = getUnderlyingResource();
		return resource == null ? true : resource.getResourceAttributes()
				.isReadOnly();
	}

	/*
	 * @see IBuffer#replace(int, int, char[])
	 */
	public void replace(int position, int length, char[] text) {
		replace(position, length, new String(text));
	}

	/*
	 * @see IBuffer#replace(int, int, String)
	 */
	public void replace(int position, int length, String text) {
		if (DEBUG_LINE_DELIMITERS) {
			validateLineDelimiters(text);
		}
		fReplaceCmd.replace(position, length, text);
	}

	/*
	 * @see IBuffer#save(IProgressMonitor, boolean)
	 */
	public void save(IProgressMonitor progress, boolean force)
			throws ModelException {
		try {
			if (fTextFileBuffer != null)
				fTextFileBuffer.commit(progress, force);
		} catch (CoreException e) {
			throw new ModelException(e);
		}
	}

	/*
	 * @see IBuffer#setContents(char[])
	 */
	public void setContents(char[] contents) {
		setContents(new String(contents));
	}

	/*
	 * @see IBuffer#setContents(String)
	 */
	public void setContents(String contents) {
		int oldLength = fDocument.getLength();

		if (contents == null) {

			if (oldLength != 0)
				fSetCmd.set(""); //$NON-NLS-1$

		} else {

			// set only if different
			if (DEBUG_LINE_DELIMITERS) {
				validateLineDelimiters(contents);
			}

			if (!contents.equals(fDocument.get()))
				fSetCmd.set(contents);
		}
	}

	private void validateLineDelimiters(String contents) {

		if (fLegalLineDelimiters == null) {
			// collect all line delimiters in the document
			HashSet existingDelimiters = new HashSet();

			for (int i = fDocument.getNumberOfLines() - 1; i >= 0; i--) {
				try {
					String curr = fDocument.getLineDelimiter(i);
					if (curr != null) {
						existingDelimiters.add(curr);
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
					// DLTKPlugin.log(e);
				}
			}
			if (existingDelimiters.isEmpty()) {
				return; // first insertion of a line delimiter: no test
			}
			fLegalLineDelimiters = existingDelimiters;

		}

		DefaultLineTracker tracker = new DefaultLineTracker();
		tracker.set(contents);

		int lines = tracker.getNumberOfLines();
		if (lines <= 1)
			return;

		for (int i = 0; i < lines; i++) {
			try {
				String curr = tracker.getLineDelimiter(i);
				if (curr != null && !fLegalLineDelimiters.contains(curr)) {
					StringBuffer buf = new StringBuffer(
							"New line delimiter added to new code: "); //$NON-NLS-1$
					for (int k = 0; k < curr.length(); k++) {
						buf.append(String.valueOf((int) curr.charAt(k)));
					}
					// DLTKPlugin.log(new Exception(buf.toString()));
					(new Exception(buf.toString())).printStackTrace();
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * @see IDocumentListener#documentAboutToBeChanged(DocumentEvent)
	 */
	public void documentAboutToBeChanged(DocumentEvent event) {
		// there is nothing to do here
	}

	/*
	 * @see IDocumentListener#documentChanged(DocumentEvent)
	 */
	public void documentChanged(DocumentEvent event) {
		fireBufferChanged(new BufferChangedEvent(this, event.getOffset(),
				event.getLength(), event.getText()));
	}

	private void fireBufferChanged(BufferChangedEvent event) {
		if (fBufferListeners != null && fBufferListeners.size() > 0) {
			Iterator e = new ArrayList(fBufferListeners).iterator();
			while (e.hasNext())
				((IBufferChangedListener) e.next()).bufferChanged(event);
		}
	}

	/**
	 * Run the given runnable in the UI thread.
	 * 
	 * @param runnable
	 *            the runnable
	 * @since 3.3
	 */
	private static final void run(Runnable runnable) {
	}

}
