/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.composer.core.ComposerCorePlugin;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;
import org.eclipse.php.composer.core.utils.DocumentStore;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ModelContainer {

	private List<IModelChangeListener> listeners;

	private ComposerRoot model;

	private boolean loaded;

	private DocumentStore store;

	public ModelContainer() {
		this.listeners = new ArrayList<IModelChangeListener>();
		initModel();
	}

	public void deserialize(IDocument document) throws JsonParseException, IOException {
		deserialize(new ByteArrayInputStream(document.get().getBytes()));
	}

	public void deserialize(InputStream inputStream) throws JsonParseException, IOException {
		try {
			loaded = false;
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			ComposerRoot updatedModel = null;
			try {
				updatedModel = ComposerJacksonMapper.getMapper().readValue(reader, ComposerRoot.class);
			} catch (JsonParseException e) {
				ComposerCorePlugin.logError(e);
			}
			if (updatedModel == null) {
				return;
			}
			model.setName(updatedModel.getNameProperty());
			model.setVersion(updatedModel.getVersionProperty());
			model.setRepositories(updatedModel.getRepositoriesProperty());
			model.setRequire(updatedModel.getRequireProperty());
			model.setConflict(updatedModel.getConflictProperty());
			model.setProvide(updatedModel.getProvideProperty());
			model.setReplace(updatedModel.getReplaceProperty());
			model.setRequireDev(updatedModel.getRequireDevProperty());
			model.setDescription(updatedModel.getDescriptionProperty());
			model.setAdditionalAttributes(updatedModel.any());
			loaded = true;
		} finally {
			modelReloaded();
		}
	}

	public void serialize() {
		try {
			ObjectMapper objectMapper = createObjectMapper();
			store.getOutput().write(objectMapper.writeValueAsString(model));
			store.write();
		} catch (CoreException e) {
			ComposerCorePlugin.logError(e);
		} catch (IOException e) {
			ComposerCorePlugin.logError(e);
		}
	}

	public String serializeToString() {
		try {
			ObjectMapper objectMapper = createObjectMapper();
			return objectMapper.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			ComposerCorePlugin.logError(e);
		}
		return null;
	}

	private ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = ComposerJacksonMapper.getMapper();
		objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		return objectMapper;
	}

	public void setOutput(DocumentStore store) {
		this.store = store;
	}

	public ComposerRoot getModel() {
		return model;
	}

	public void addModelListener(IModelChangeListener listener) {
		this.listeners.add(listener);
	}

	public void removeModelListener(IModelChangeListener listener) {
		this.listeners.remove(listener);
	}

	public boolean isLoaded() {
		return loaded;
	}

	private void modelReloaded() {
		for (IModelChangeListener l : listeners) {
			l.modelReloaded();
		}
	}

	private void initModel() {
		if (model == null) {
			model = new ComposerRoot();
			model.getNameProperty().setModel(this);
			model.getVersionProperty().setModel(this);
			model.getDescriptionProperty().setModel(this);
			model.getRepositoriesProperty().setModel(this);
			model.getRequireProperty().setModel(this);
			model.getRequireDevProperty().setModel(this);
			model.getConflictProperty().setModel(this);
			model.getProvideProperty().setModel(this);
			model.getReplaceProperty().setModel(this);
		}
	}

}
