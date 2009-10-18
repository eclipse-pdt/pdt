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
package org.eclipse.php.internal.ui.dialogs.openType.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.ide.dialogs.SimpleListContentProvider;

public class ElementAddition implements Runnable {

	private TableViewer tableViewer;
	private boolean defaultElementSelection;
	private Object[] tableElements = new Object[] {};
	private Object changeTableElements = new Object();
	private boolean continueAddingElements = false;
	private boolean exit = false;
	private boolean elementsChanged = false;

	ElementAddition(TableViewer tableViewer, boolean defaultElementSelection) {
		this.tableViewer = tableViewer;
		this.defaultElementSelection = defaultElementSelection;
	}

	public void run() {
		while (true) {
			continueAddingElements = true;
			elementsChanged = false;
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					if (!continueAddingElements) {
						return;
					}
					tableViewer
							.setContentProvider(new SimpleListContentProvider());
					tableViewer.setInput(null);
				}
			});

			int elementsLength;
			synchronized (changeTableElements) {
				elementsLength = tableElements.length;
			}
			for (int i = 0; continueAddingElements && i < elementsLength; i++) {
				final Object element;
				synchronized (changeTableElements) {
					if (!continueAddingElements) {
						break;
					}
					element = tableElements[i];
				}
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						if (!continueAddingElements) {
							return;
						}
						tableViewer.add(element);
					}
				});
				if (i == 0 && defaultElementSelection) {
					if (!continueAddingElements) {
						break;
					}
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							if (!continueAddingElements) {
								return;
							}
							tableViewer
									.setSelection(new IStructuredSelection() {
										public Object getFirstElement() {
											return element;
										}

										public Iterator iterator() {
											return toList().iterator();
										}

										public int size() {
											return 1;
										}

										public Object[] toArray() {
											return new Object[] { element };
										}

										public List toList() {
											ArrayList arrayList = new ArrayList();
											arrayList.add(element);
											return arrayList;
										}

										public boolean isEmpty() {
											return false;
										}
									});
						}
					});
				}
			}
			synchronized (this) {
				if (exit) {
					return;
				}
				if (elementsChanged) {
					continue;
				}
				try {
					wait();
				} catch (InterruptedException e) {
				}
				if (exit) {
					return;
				}
			}
		}
	}

	public void setElements(Object[] tableElements) {
		synchronized (changeTableElements) {
			this.continueAddingElements = false;
			this.tableElements = tableElements;
		}
		synchronized (this) {
			this.elementsChanged = true;
			notifyAll();
		}
	}

	public void stop() {
		this.continueAddingElements = false;
		synchronized (this) {
			exit = true;
			elementsChanged = false;
			notifyAll();
		}
	}

}
