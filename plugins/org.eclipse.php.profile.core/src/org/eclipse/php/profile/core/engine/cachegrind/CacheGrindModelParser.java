/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Dawid Pakuła - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.engine.cachegrind;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.php.profile.core.data.*;
import org.eclipse.php.profile.core.engine.DefaultProfilerDB;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.core.engine.cachegrind.CacheGrindParser.CacheGrindParserListener;

public class CacheGrindModelParser {
	private CacheGrindParser source;
	private List<ProfilerData> models = new ArrayList<>();
	private ProfilerData model;
	private Map<Integer, String> fileNames = new HashMap<>();
	private Map<Integer, String> functions = new HashMap<>();
	private Map<String, Integer> functionIds = new HashMap<>();

	private Map<String, ProfilerFileData> dataFile = new HashMap<>();
	private Map<String, ProfilerFunctionData> dataFunction = new HashMap<>();
	private int lastFileId = -2;
	private int lastFunctionId = -2;
	private int timeIndex = 0;
	private ProfilerFileData lastFile;
	private ProfilerFunctionData lastFunction;
	private ProfilerFileData nextFile;
	private ProfilerFunctionData nextFunction;
	private Map<Integer, List<ProfilerCallTraceLayer>> functionTrace = new HashMap<>();
	private int mainId = -1;

	private class ParserListener implements CacheGrindParserListener {
		@Override
		public void cmd(String cmd) {
			finishCurrent();
			model = new ProfilerData(new ProfilerGlobalData(), new ArrayList<>(), new ProfilerCallTrace());
			models.add(model);
			model.getGlobalData().setPath(cmd);
			lastFileId = -1;
			lastFunctionId = -1;
		}

		@Override
		public void events(String[] events) {
			int pos = 0;
			for (String item : events) {
				if (item.equalsIgnoreCase("Time")) { //$NON-NLS-1$
					timeIndex = pos;
					break;
				}
				pos++;
			}
		}

		public int getTime(int[] values) {
			if (values.length <= timeIndex) {
				return 0;
			} else {
				return values[timeIndex] * 100;
			}
		}

		@Override
		public void file(int id, String name) {
			nextFunction = null;
			nextFile = null;
			lastFile = null;
			lastFunction = null;
			name = regFile(id, name);
			if (name == null) {
				return;
			}
			if (!dataFile.containsKey(name)) {
				lastFile = new ProfilerFileData(name);
				dataFile.put(name, lastFile);
			} else {
				lastFile = dataFile.get(name);
			}
		}

		@Override
		public void function(int id, String name) {
			if (lastFile == null) {
				return;
			}
			lastFunction = null;
			nextFunction = null;
			nextFile = null;
			name = regFunction(id, name);
			if (!dataFunction.containsKey(name)) {
				lastFunction = buildFunction(name, lastFile);
				dataFunction.put(name, lastFunction);
			} else {
				lastFunction = dataFunction.get(name);
			}
			if (name.equals("{main}")) { //$NON-NLS-1$
				mainId = id;
			}

			functionTrace.put(lastFunction.getID(), new ArrayList<>());
		}

		@Override
		public void cost(int pos, int[] events) {
			if (nextFunction != null) {
				lastFunction.setTotalTimeMicroseconds(lastFunction.getTotalTimeMicroseconds() + getTime(events));
				ProfilerCallTraceLayer tr = new ProfilerCallTraceLayer();

				tr = new ProfilerCallTraceLayer();
				tr.setType(ProfilerCallTraceLayer.EXIT);
				tr.setTimestampMicroseconds(getTime(events));
				tr.setCalledID(nextFunction.getID());
				functionTrace.get(lastFunction.getID()).add(tr);

				return;
			}
			if (lastFunction != null) {
				lastFunction.setLineNumber(pos);
				lastFunction.setOwnTimeMicroseconds(getTime(events));
				lastFunction.setTotalTimeMicroseconds(getTime(events));
			}
		}

		@Override
		public void calls(int count, int[] values) {
			if (nextFunction != null) {
				nextFunction.setCallsCount(nextFunction.getCallsCount() + count);
			}
		}

		protected ProfilerFunctionData buildFunction(String functionName, ProfilerFileData file) {
			ProfilerFunctionData funcData = new ProfilerFunctionData(file.getName());
			int i = functionName.indexOf("::"); //$NON-NLS-1$
			if (i == -1) {
				i = functionName.indexOf("->"); //$NON-NLS-1$
			}
			if (i != -1) {
				String className = functionName.substring(0, i);
				String fName = functionName.substring(i + 2);
				if (fName.equals(file.getName())) {
					funcData.setFunctionName(new StringBuilder(className).append(':').append(fName).toString());
				} else {
					if (!file.getName().equals("php:internal") && !className.equals("php")) { //$NON-NLS-1$ //$NON-NLS-2$
						funcData.setClassName(className);
					}
					funcData.setFunctionName(fName);
				}
			} else {
				funcData.setFunctionName(functionName);
			}
			funcData.setID(functionIds.get(functionName));

			return funcData;
		}

		@Override
		public void nextFile(int id, String name) {
			if (lastFile == null) {
				return;
			}
			name = regFile(id, name);
			if (!dataFile.containsKey(name)) {
				nextFile = new ProfilerFileData(name);
				dataFile.put(name, nextFile);
			} else {
				nextFile = dataFile.get(name);
			}
		}

		@Override
		public void nextFunction(int id, String name) {
			if (lastFile == null) {
				return;
			}
			name = regFunction(id, name);
			if (nextFile == null) {
				nextFile = lastFile;
			}

			if (!dataFunction.containsKey(name)) {
				nextFunction = buildFunction(name, nextFile);
				dataFunction.put(name, nextFunction);
			} else {
				nextFunction = dataFunction.get(name);
			}

			nextFile = null;
		}

		@Override
		public void summary(int[] sum) {
			model.getGlobalData().setTimeMicroSeconds(getTime(sum));
		}

		private String regFile(int id, String name) {
			if (name != null) {
				if (id < 0) {
					id = lastFileId--;
				}
				fileNames.put(id, name);
				return name;
			} else {
				return fileNames.get(id);
			}
		}

		private String regFunction(int id, String name) {
			if (name != null) {
				if (id < 0) {
					id = lastFunctionId--;
				}
				functions.put(id, name);
				functionIds.put(name, id);

				return name;
			} else {
				return functions.get(id);
			}
		}

	}

	public CacheGrindModelParser(InputStream file) {
		source = new CacheGrindParser(file);
	}

	public ProfilerData[] buildModel() throws IOException {
		model = new ProfilerData(new ProfilerGlobalData(), new ArrayList<>(), new ProfilerCallTrace());
		source.parse(new ParserListener());

		finishCurrent();

		return models.toArray(new ProfilerData[0]);
	}

	private void finishCurrent() {
		if (model.getGlobalData().getPath() == null) {
			return;
		}
		model.getGlobalData().setFileCount(fileNames.size());
		model.getGlobalData().setFileNames(fileNames.values().toArray(new String[0]));

		if (mainId != -1) {
			buildCallTree(mainId, dataFunction.get(functions.get(mainId)).getTotalTimeMicroseconds(),
					new ArrayDeque<>());
		}
		for (Entry<String, ProfilerFunctionData> entry : dataFunction.entrySet()) {
			ProfilerFileData file = dataFile.get(entry.getValue().getAbsoluteFileName());
			if (file != null) {
				if (entry.getValue().getID() == mainId) {
					entry.getValue().setID(-1);
				}
				file.addFunction(entry.getValue());
			}
		}
		for (Entry<String, ProfilerFileData> entry : dataFile.entrySet()) {
			model.addFile(entry.getValue());
		}

		fileNames.clear();
		functions.clear();
		functionIds.clear();
		dataFile.clear();
		dataFunction.clear();
		functionTrace.clear();
		lastFile = null;
		lastFunction = null;
		nextFunction = null;
		nextFile = null;
		mainId = -1;

	}

	private int buildCallTree(int id, int duration, Deque<Integer> used) {

		ProfilerCallTraceLayer lr = new ProfilerCallTraceLayer();
		lr.setCalledID(id == mainId ? -1 : id);
		lr.setType(ProfilerCallTraceLayer.ENTER);
		model.getCallTrace().addLayer(lr);
		if (!used.contains(id)) {
			used.push(id);
			for (ProfilerCallTraceLayer sub : functionTrace.get(id)) {
				buildCallTree(sub.getCalledID(), sub.getTimestampMicroseconds(), used);
			}
			used.pop();
		} else {
			for (ProfilerCallTraceLayer sub : functionTrace.get(id)) {
				lr = new ProfilerCallTraceLayer();
				lr.setCalledID(sub.getCalledID() == mainId ? -1 : sub.getCalledID());
				lr.setType(ProfilerCallTraceLayer.ENTER);
				model.getCallTrace().addLayer(lr);

				lr = new ProfilerCallTraceLayer();
				lr.setCalledID(sub.getCalledID() == mainId ? -1 : sub.getCalledID());
				lr.setType(ProfilerCallTraceLayer.EXIT);
				lr.setTimestampMicroseconds(sub.getTimestampMicroseconds());
				model.getCallTrace().addLayer(lr);
			}
		}

		lr = new ProfilerCallTraceLayer();
		lr.setCalledID(id == mainId ? -1 : id);
		lr.setTimestampMicroseconds(duration);
		lr.setType(ProfilerCallTraceLayer.EXIT);
		model.getCallTrace().addLayer(lr);

		return duration;
	}

	public static ProfilerDB[] build(FileInputStream stream) throws IOException {
		CacheGrindModelParser tmp = new CacheGrindModelParser(stream);
		List<ProfilerDB> db = new ArrayList<>();
		for (ProfilerData model : tmp.buildModel()) {
			db.add(new DefaultProfilerDB(model, new Date()));
		}
		return db.toArray(new ProfilerDB[0]);
	}
}
