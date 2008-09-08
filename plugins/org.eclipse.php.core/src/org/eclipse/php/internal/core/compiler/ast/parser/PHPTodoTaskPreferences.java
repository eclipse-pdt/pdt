/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.compiler.task.TodoTask;
import org.eclipse.dltk.compiler.task.TodoTaskPreferences;

public class PHPTodoTaskPreferences extends TodoTaskPreferences {

	
	private static final String TAG_SEPARATOR = ","; //$NON-NLS-1$
	private static final String PRIORITY_SEPARATOR = ";"; //$NON-NLS-1$

	
	public PHPTodoTaskPreferences(Preferences store) {
		super(store);		
	}
	
	public static List<TodoTask> getDefaultTags() {
		
		final List<TodoTask> defaultTags = new ArrayList<TodoTask>();
		defaultTags.add(new TodoTask("@todo", TodoTask.PRIORITY_NORMAL)); //$NON-NLS-1$
		defaultTags.add(new TodoTask("TODO", TodoTask.PRIORITY_NORMAL)); //$NON-NLS-1$
		defaultTags.add(new TodoTask("FIXME", TodoTask.PRIORITY_HIGH)); //$NON-NLS-1$ 
		defaultTags.add(new TodoTask("XXX", TodoTask.PRIORITY_NORMAL)); //$NON-NLS-1$		
		return defaultTags;
	}
	
	private static String encodeTaskTags(List<TodoTask> elements) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < elements.size(); ++i) {
			final TodoTask task = elements.get(i);
			if (i > 0) {
				sb.append(TAG_SEPARATOR);
			}
			sb.append(task.name);
			sb.append(PRIORITY_SEPARATOR);
			sb.append(task.priority);
		}
		final String string = sb.toString();
		return string;
	}
	
	public static void initializeDefaultValues(Preferences store) {
		store.setDefault(ENABLED, true);
		store.setDefault(CASE_SENSITIVE, true);
		store.setDefault(TAGS, encodeTaskTags(getDefaultTags()));
	}

}
