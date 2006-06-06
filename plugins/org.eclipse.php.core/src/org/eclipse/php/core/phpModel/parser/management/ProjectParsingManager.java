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
package org.eclipse.php.core.phpModel.parser.management;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.phpModel.parser.IParserClientFactory;
import org.eclipse.php.core.phpModel.parser.IProjectModelListener;
import org.eclipse.php.core.phpModel.parser.PHPLanguageManager;
import org.eclipse.php.core.phpModel.parser.PHPLanguageManagerProvider;
import org.eclipse.php.core.phpModel.parser.PHPParserManager;
import org.eclipse.php.core.phpModel.parser.ParserClient;
import org.eclipse.php.core.phpModel.parser.ParserClientComposite;
import org.eclipse.php.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.core.preferences.TaskPatternsProvider;
import org.eclipse.php.core.project.properties.handlers.PhpVersionChangedHandler;
import org.eclipse.php.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

class ProjectParsingManager implements IProjectModelListener {

	private Collection parserClientFactoryCollection = new HashSet();
	private PHPParserManager parserManager;
	private IProject project;
	private PhpVersionListener phpVersionListener;
	
	
	ProjectParsingManager(IProject project) {
		this.project = project;
		String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		setPHPVersion(phpVersion);
		phpVersionListener = new PhpVersionListener();
		PhpVersionChangedHandler.getInstance().addPhpVersionChangedListener(phpVersionListener);
	}

	private void setPHPVersion(String phpVersion) {
		PHPLanguageManager languageManager = PHPLanguageManagerProvider.instance().getPHPLanguageManager(phpVersion);
		parserManager = languageManager.createPHPParserManager();
	}
	
	private class PhpVersionListener implements IPreferencesPropagatorListener {

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String newVersion = (String) event.getNewValue();
			setPHPVersion(newVersion);
		}

		public IProject getProject() {
			return project;
		}
	}

	public void fileAdded(IFile file) {
		ParserClient parserClient = buildParserClient(file, IParserClientFactory.fileAdded);
		if (parserClient == null) {
			return;
		}

		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(file.getContents());
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
			return;
		}
		Pattern[] tasksPatterns = TaskPatternsProvider.getInstance().getPatternsForProject(file.getProject());
		try {
			parserManager.parse(inputStreamReader, file.getFullPath().toString(), file.getModificationStamp(), parserClient, tasksPatterns, UseAspTagsHandler.useAspTagsAsPhp(project));
		} catch (Exception e) {
			PHPCorePlugin.log(e);
			return;
		}
	}

	private ParserClient buildParserClient(IFile file, int fileAdded) {
		ParserClientComposite parserClientComposite = new ParserClientComposite();
		for (Iterator iter = this.parserClientFactoryCollection.iterator(); iter.hasNext();) {
			IParserClientFactory parserClientFactory = (IParserClientFactory) iter.next();
			if (parserClientFactory.isParsable(file.getFullPath().toString(), fileAdded)) {
				ParserClient parserClient = parserClientFactory.create();
				parserClientComposite.add(parserClient);
			}
		}
		if (parserClientComposite.isEmpty()) {
			return null;
		}
		
		return parserClientComposite;
	}

	public void fileRemoved(IFile file) {
	}

	public void fileChanged(IFile file, IStructuredDocument sDocument) {
		ParserClient parserClient = buildParserClient(file, IParserClientFactory.fileChanged);
		if (parserClient == null) {
			return;
		}
		try {
			StringReader reader = new StringReader(sDocument.get());
			Pattern[] tasksPatterns = TaskPatternsProvider.getInstance().getPatternsForProject(file.getProject());
			parserManager.parse(reader, file.getFullPath().toString(), file.getModificationStamp(), parserClient, tasksPatterns, UseAspTagsHandler.useAspTagsAsPhp(project));
		} catch (Exception e) {
			PHPCorePlugin.log(e);
			return;
		}
	}


	public void addParserClient(IParserClientFactory parserClientFactory) {
		parserClientFactoryCollection.add(parserClientFactory);
	}


	public void removeParserClient(IParserClientFactory parserClientFactory) {
		parserClientFactoryCollection.remove(parserClientFactory);
	}

	public IProject getProject() {
		return project;
	}
	
	public void dispose() {
		parserClientFactoryCollection.clear();
		parserClientFactoryCollection = null;
		parserManager = null;
		PhpVersionChangedHandler.getInstance().removePhpVersionChangedListener(phpVersionListener);
		phpVersionListener = null;
		project = null;
	}
}
