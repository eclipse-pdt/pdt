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
import java.util.stream.Collectors;

import org.eclipse.php.profile.core.data.*;
import org.eclipse.php.profile.core.engine.DefaultProfilerDB;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.core.engine.cachegrind.CacheGrindParser.CacheGrindParserListener;

public class CacheGrindModelParser {
	private static final String MAIN = "{main}"; //$NON-NLS-1$
	private CacheGrindParser source;
	private List<ProfilerData> models = new ArrayList<>();
	private ProfilerData model;
	private Map<String, Integer> fileIds = new HashMap<>();
	private Map<String, Integer> functionIds = new HashMap<>();

	private Map<String, Integer> callIds = new HashMap<>();

	private Map<Integer, ProfilerFileData> dataFile = new HashMap<>();
	private Map<Integer, ProfilerFunctionData> dataFunction = new HashMap<>();

	private Map<Integer, Integer> functionNames = new HashMap<>();
	private Map<String, Deque<Integer>> buffer = new HashMap<>();

	private class SubCall {
		public int index;
		public String name;
		public int position;
		public ProfilerFileData file;
		public int time;
		public int calls = 0;

		public SubCall(ProfilerFileData file, int index, String name) {
			this.file = file;
			this.index = index;
			this.name = name;
		}
	}

	private int lastFileId = -2;
	private int lastFunctionId = -2;
	private int lastCallId = 1;
	private int timeIndex = 0;
	private ProfilerFileData lastFile;
	private ProfilerFunctionData lastFunction;
	private ProfilerFileData nextFile;
	private SubCall nextFunction;
	private List<SubCall> subCalls = new LinkedList<>();

	private Deque<Integer> used = new ArrayDeque<>();
	private Map<Integer, List<ProfilerCallTraceLayer>> functionTrace = new HashMap<>();
	private int mainId = -1;

	private class ParserListener implements CacheGrindParserListener {

		@Override
		public void cmd(String cmd) {
			finishCurrent();
			model = new ProfilerData(new ProfilerGlobalData(), new LinkedList<>(), new ProfilerCallTrace());
			models.add(model);
			model.getGlobalData().setPath(cmd);
			model.getGlobalData().setOriginalURL(ProfilerGlobalData.URL_NOT_AVAILABLE_MSG);
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
			finishSubCalls();
			nextFunction = null;
			nextFile = null;
			lastFile = null;
			lastFunction = null;
			lastFile = regFile(id, name);
		}

		@Override
		public void function(int id, String name) {
			if (lastFile == null) {
				return;
			}
			lastFunction = null;
			nextFunction = null;
			nextFile = null;
			lastFunction = regFunction(lastFile, id, name, true);

			if (!functionTrace.containsKey(lastFunction.getID())) {
				functionTrace.put(lastFunction.getID(), new LinkedList<>());
			}
		}

		@Override
		public void cost(int pos, int[] events) {
			if (nextFunction != null) {
				nextFunction.position = pos;
				nextFunction.time = getTime(events);

				return;
			}
			if (lastFunction != null) {
				lastFunction.setLineNumber(pos);
				lastFunction.setOwnTimeMicroseconds(lastFunction.getOwnTimeMicroseconds() + getTime(events));
				lastFunction.setTotalTimeMicroseconds(lastFunction.getTotalTimeMicroseconds() + getTime(events));
			}
		}

		@Override
		public void calls(int count, int[] values) {
			if (nextFunction != null) {
				nextFunction.calls += count;
			}
		}

		@Override
		public void nextFile(int id, String name) {
			if (lastFile == null) {
				return;
			}
			nextFile = regFile(id, name);
		}

		@Override
		public void nextFunction(int id, String name) {
			if (lastFile == null) {
				return;
			}
			if (nextFile == null) {
				nextFile = lastFile;
			}
			nextFunction = new SubCall(nextFile, id, name);
			subCalls.add(nextFunction);

			nextFile = null;
		}

		@Override
		public void summary(int[] sum) {
			model.getGlobalData().setTimeMicroSeconds(getTime(sum));
		}
	}

	public CacheGrindModelParser(InputStream file) {
		source = new CacheGrindParser(file);
	}

	private ProfilerFileData regFile(int id, String name) {
		if (id == -1) {
			if (fileIds.containsKey(name)) {
				return dataFile.get(fileIds.get(name));
			} else {
				id = lastFileId--;
				ProfilerFileData tmp = new ProfilerFileData();
				tmp.setLocalName(Integer.toString(id));
				fileIds.put(name, id);
				dataFile.put(id, tmp);
				return tmp;
			}
		} else {
			ProfilerFileData tmp;
			if (dataFile.containsKey(id)) {
				tmp = dataFile.get(id);
			} else {
				tmp = new ProfilerFileData();
				tmp.setLocalName(Integer.toString(id));
				dataFile.put(id, tmp);
			}
			if (name != null) {
				fileIds.put(name, id);
			}
			return tmp;
		}
	}

	private ProfilerFunctionData regFunction(ProfilerFileData file, int id, String name, boolean force) {
		boolean main = false;
		if (id < 0) {
			if (!functionIds.containsKey(name)) {
				id = lastFunctionId--;
				functionIds.put(name, id);
				if (MAIN.equals(name)) {
					main = true;
				}
			} else {
				id = functionIds.get(name);
			}
		} else if (name != null) {
			if (MAIN.equals(name)) {
				main = true;
			}
			functionIds.put(name, id);
		}

		String realID = new StringBuilder(file.getLocalName()).append('_').append(id).toString();

		if (force) {
			ProfilerFunctionData tmp = new ProfilerFunctionData();
			tmp.setID(lastCallId++);
			tmp.setFileName(file.getLocalName());
			callIds.put(realID, tmp.getID());
			if (main) {
				mainId = tmp.getID();
			}
			dataFunction.put(tmp.getID(), tmp);
			functionNames.put(tmp.getID(), id);
			if (!buffer.containsKey(realID)) {
				buffer.put(realID, new ArrayDeque<>());
			}
			buffer.get(realID).push(tmp.getID());
			return tmp;
		} else {
			id = -1;
			if (buffer.containsKey(realID)) {
				Deque<Integer> d = buffer.get(realID);
				if (d.size() > 0) {
					id = d.pop();
				}
			}
			if (id < 0) {
				id = callIds.get(realID);
			}
			if (main) {
				mainId = id;
			}
			return dataFunction.get(id);
		}
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
		finishSubCalls();
		model.getGlobalData().setFileCount(fileIds.size());
		model.getGlobalData().setFileNames(fileIds.keySet().toArray(new String[0]));

		for (Entry<String, Integer> entry : fileIds.entrySet()) {
			ProfilerFileData file = dataFile.get(entry.getValue());
			file.setName(entry.getKey());
			file.setLocalName(null);
		}

		if (mainId > 0) {
			buildCallTree(mainId, dataFunction.get(mainId).getTotalTimeMicroseconds(), 1);
		}
		Map<Integer, String> flipNames = functionIds.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
		for (ProfilerFunctionData func : dataFunction.values()) {
			ProfilerFileData file = dataFile.get(Integer.valueOf(func.getLocalFileName()));
			String functionName = flipNames.get(functionNames.get(func.getID()));
			func.setLocalFileName(null);
			func.setFileName(file.getName());

			int i = functionName.indexOf("::"); //$NON-NLS-1$
			if (i == -1) {
				i = functionName.indexOf("->"); //$NON-NLS-1$
			}
			if (i != -1) {
				String className = functionName.substring(0, i);
				String fName = functionName.substring(i + 2);
				if (fName.equals(file.getName())) {
					func.setFunctionName(new StringBuilder(className).append(':').append(fName).toString());
				} else {
					if (!file.getName().equals("php:internal") && !className.equals("php")) { //$NON-NLS-1$ //$NON-NLS-2$
						func.setClassName(className);
					}
					func.setFunctionName(fName);
				}
			} else {
				func.setFunctionName(functionName);
			}

			if (file != null) {
				if (func.getID() == mainId) {
					func.setID(-1);
				}
				file.addFunction(func);
			}
		}

		for (ProfilerFileData file : dataFile.values()) {
			model.addFile(file);
		}

		fileIds.clear();
		functionIds.clear();
		callIds.clear();

		dataFile.clear();
		dataFunction.clear();

		functionNames.clear();
		buffer.clear();

		functionTrace.clear();
		used.clear();

		lastFile = null;
		lastFunction = null;
		nextFunction = null;
		nextFile = null;
		mainId = -1;
		lastCallId = 1;
		lastFunctionId = lastFileId = -2;
	}

	private void finishSubCalls() {
		if (lastFunction == null || subCalls.isEmpty()) {
			return;
		}

		ListIterator<SubCall> it = subCalls.listIterator(subCalls.size());
		while (it.hasPrevious()) {
			SubCall call = it.previous();
			lastFunction.setTotalTimeMicroseconds(lastFunction.getTotalTimeMicroseconds() + call.time);

			ProfilerFunctionData fnc = regFunction(call.file, call.index, call.name, false);
			ProfilerCallTraceLayer tr = new ProfilerCallTraceLayer();

			tr = new ProfilerCallTraceLayer();
			tr.setType(ProfilerCallTraceLayer.EXIT);
			tr.setTimestampMicroseconds(call.time);
			tr.setCalledID(fnc.getID());
			tr.setLine(call.position);
			functionTrace.get(lastFunction.getID()).add(0, tr);
		}
		subCalls.clear();
	}

	private int buildCallTree(int id, int duration, int line) {

		ProfilerCallTraceLayer lr = new ProfilerCallTraceLayer();
		lr.setCalledID(id == mainId ? -1 : id);
		lr.setType(ProfilerCallTraceLayer.ENTER);
		lr.setLine(line);
		model.getCallTrace().addLayer(lr);
		if (!used.contains(id)) {
			used.push(id);
			for (ProfilerCallTraceLayer sub : functionTrace.get(id)) {
				buildCallTree(sub.getCalledID(), sub.getTimestampMicroseconds(), sub.getLineNumber());
			}
			used.pop();
		} else {
			for (ProfilerCallTraceLayer sub : functionTrace.get(id)) {
				lr = new ProfilerCallTraceLayer();
				lr.setCalledID(sub.getCalledID() == mainId ? -1 : sub.getCalledID());
				lr.setType(ProfilerCallTraceLayer.ENTER);
				lr.setLine(sub.getLineNumber());
				model.getCallTrace().addLayer(lr);

				lr = new ProfilerCallTraceLayer();
				lr.setCalledID(sub.getCalledID() == mainId ? -1 : sub.getCalledID());
				lr.setType(ProfilerCallTraceLayer.EXIT);
				lr.setTimestampMicroseconds(sub.getTimestampMicroseconds());
				lr.setLine(sub.getLineNumber());
				model.getCallTrace().addLayer(lr);
			}
		}

		lr = new ProfilerCallTraceLayer();
		lr.setCalledID(id == mainId ? -1 : id);
		lr.setTimestampMicroseconds(duration);
		lr.setType(ProfilerCallTraceLayer.EXIT);
		lr.setLine(line);
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
