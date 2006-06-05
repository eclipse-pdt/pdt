/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.dialogs.openType.generic.filter;


public class RunnableFilter extends FilterDecorator {

	private IResultHandler resultHandler;

	public RunnableFilter(IFilter filter, IResultHandler resultHandler) {
		super(filter);
		this.resultHandler = resultHandler;
	}
	
	public Object[] filter(final Object[] elements) {
		new Thread(new Runnable() {

			public void run() {
				Object[] result = getFilter().filter(elements);
				resultHandler.handleResult(result);
			}
			
		}).start();
		
		return new Object[]{};
	}
}
