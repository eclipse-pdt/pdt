/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Dawid Pakuła - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.engine.cachegrind;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.eclipse.php.profile.core.data.*;
import org.eclipse.php.profile.core.engine.DefaultProfilerDB;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.core.engine.cachegrind.CacheGrindParser.CacheGrindParserListener;

public class CacheGrindModelParser {
	private static final String MAIN = "{main}"; //$NON-NLS-1$
	private static final String PHP_INTERNAL = "php:internal"; //$NON-NLS-1$
	private static final String PHP_CLASS = "php"; //$NON-NLS-1$
	private static final String PHP_FILE = "PHP"; //$NON-NLS-1$
	private CacheGrindParser source;
	private ProfilerData model;
	private List<ProfilerData> models = new ArrayList<>();

	private class File {
		public int index;
		public String name;
		public Map<Integer, Function> functions = new HashMap<>();

		public File(int index) {
			this.index = index;
		}
	}

	private class Function {
		public int index;
		public int id;
		public String name;
		public int position = -1;
		public int cost = 0;
		public int totalCost = 0;
		public int calls = 0;
		public Deque<Invocation> pendingInvocations = new ArrayDeque<>();

		public Function(int id, int index) {
			this.id = id;
			this.index = index;
		}

	}

	private class Invocation {
		public Function function;
		public List<Invocation> calls = new LinkedList<>();
		public int cost;
		public int position;
		public int count = 0;
	}

	private int fileCounter = 1;
	private int functionCounter = 1;
	private int fileFunctionCounter = 1;
	private int timeIndex = 0;

	private Map<String, Integer> fileIds = new HashMap<>();
	private Map<String, Integer> functionIds = new HashMap<>();
	private Map<Integer, String> functionNames = new HashMap<>();
	private Map<Integer, File> files = new HashMap<>();
	private Map<Integer, Invocation> invocations = new HashMap<>();

	private File lastFile;
	private File callFile;
	private Invocation lastFunction;
	private Invocation lastCall;
	private Invocation mainCall;

	private class ParserListener implements CacheGrindParserListener {

		@Override
		public void cmd(String cmd) {
			finishCurrent();
			model = new ProfilerData(new ProfilerGlobalData(), new LinkedList<>(), new ProfilerCallTrace());
			models.add(model);
			model.getGlobalData().setPath(cmd);
			model.getGlobalData().setOriginalURL(ProfilerGlobalData.URL_NOT_AVAILABLE_MSG);
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
			lastFunction = null;
			callFile = null;
			lastCall = null;

			lastFile = regFile(id, name);
		}

		@Override
		public void function(int id, String name) {
			if (lastFile == null) {
				return;
			}
			lastFunction = null;
			callFile = null;
			lastCall = null;

			boolean main = false;
			if (id < 0) {
				if (!functionIds.containsKey(name)) {
					id = functionCounter++;
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

			Function fnc;
			if (!lastFile.functions.containsKey(id)) {
				fnc = new Function(fileFunctionCounter++, id);
				lastFile.functions.put(id, fnc);
			} else {
				fnc = lastFile.functions.get(id);
			}
			if (name != null) {
				fnc.name = name;
				functionNames.put(id, name);
			}

			lastFunction = new Invocation();
			lastFunction.function = fnc;
			fnc.pendingInvocations.push(lastFunction);
			if (main) {
				mainCall = lastFunction;
			}
		}

		@Override
		public void cost(int pos, int[] events) {
			int cost = getTime(events);
			if (lastCall != null) {
				lastCall.position = pos;
				lastFunction.function.totalCost += cost;
				lastFunction.cost += cost;
			} else if (lastFunction != null) {
				lastFunction.function.position = pos;
				lastFunction.function.cost += cost;
				lastFunction.function.totalCost += cost;
				lastFunction.cost += cost;
				lastFunction.position = pos;
			}
		}

		@Override
		public void calls(int count, int[] values) {
			if (lastCall != null) {
				lastCall.count += count;
			}
		}

		@Override
		public void nextFile(int id, String name) {
			if (lastFile == null) {
				return;
			}
			callFile = regFile(id, name);
		}

		@Override
		public void nextFunction(int id, String name) {
			if (lastFile == null) {
				return;
			}
			if (callFile == null) {
				callFile = lastFile;
			}

			if (id < 0) {
				if (!functionIds.containsKey(name)) {
					id = functionCounter++;
					functionIds.put(name, id);
				} else {
					id = functionIds.get(name);
				}
			} else if (name != null) {
				functionIds.put(name, id);
			}

			Function fnc;
			if (!callFile.functions.containsKey(id)) {
				fnc = new Function(fileFunctionCounter++, id);
				callFile.functions.put(id, fnc);
			} else {
				fnc = callFile.functions.get(id);
			}
			if (name != null) {
				fnc.name = name;
			}
			lastCall = new Invocation();
			lastCall.function = fnc;
			lastFunction.calls.add(lastCall);

			callFile = null;
		}

		@Override
		public void summary(int[] sum) {
			model.getGlobalData().setTimeMicroSeconds(getTime(sum));
		}
	}

	public CacheGrindModelParser(InputStream file) {
		source = new CacheGrindParser(file);
	}

	private File regFile(int id, String name) {
		if (name != null && name.equals(PHP_INTERNAL)) {
			name = PHP_FILE;
		}
		if (id == -1) {
			if (fileIds.containsKey(name)) {
				return files.get(fileIds.get(name));
			} else {
				id = fileCounter--;

				File tmp = new File(id);
				tmp.name = name;
				fileIds.put(name, id);
				files.put(id, tmp);
				return tmp;
			}
		} else {
			File tmp;
			if (files.containsKey(id)) {
				tmp = files.get(id);
			} else {
				tmp = new File(id);
				files.put(id, tmp);
			}
			if (name != null) {
				tmp.name = name;
			}
			return tmp;
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
		if (mainCall == null) {
			mainCall = lastFunction;
		}
		finishSubCalls();
		if (mainCall != null) {
			buildCallTree(mainCall);
		}

		List<String> fileNames = new ArrayList<>(files.size());
		for (File file : files.values()) {
			if (file.name == null) {
				file.name = String.valueOf(file.index);
			}
			ProfilerFileData modelFile = new ProfilerFileData(file.name);
			for (Function fnc : file.functions.values()) {
				if (fnc.name == null) {
					fnc.name = functionNames.get(fnc.index);
					if (fnc.name == null) {
						fnc.name = String.valueOf(fnc.index);
					}
				}
				ProfilerFunctionData modelFnc = new ProfilerFunctionData(file.name);
				int i = fnc.name.indexOf("::"); //$NON-NLS-1$
				if (i == -1) {
					i = fnc.name.indexOf("->"); //$NON-NLS-1$
				}
				if (i != -1) {
					String className = fnc.name.substring(0, i);
					String fName = fnc.name.substring(i + 2);
					if (fName.equals(file.name)) {
						modelFnc.setFunctionName(new StringBuilder(className).append(':').append(fName).toString());
					} else {
						if (!file.name.equals(PHP_FILE) && !className.equals(PHP_CLASS)) {
							modelFnc.setClassName(className);
						}
						modelFnc.setFunctionName(fName);
					}
				} else {
					modelFnc.setFunctionName(fnc.name);
				}

				modelFnc.setLineNumber(fnc.position);
				modelFnc.setCallsCount(fnc.calls);
				modelFnc.setTotalTimeMicroseconds(fnc.totalCost);
				modelFnc.setOwnTimeMicroseconds(fnc.cost);
				if (mainCall != null) {
					modelFnc.setID(fnc.id == mainCall.function.id ? -1 : fnc.id);
				}

				modelFile.addFunction(modelFnc);
			}
			model.addFile(modelFile);
			fileNames.add(file.name);
		}

		model.getGlobalData().setFileCount(files.size());
		model.getGlobalData().setFileNames(fileNames.toArray(new String[0]));

		fileIds.clear();
		functionNames.clear();
		functionIds.clear();
		files.clear();
		invocations.clear();
		fileCounter = functionCounter = fileFunctionCounter = 1;
		timeIndex = 0;

		lastFile = null;
		lastFunction = null;
		mainCall = null;
	}

	private void finishSubCalls() {
		if (lastFunction == null || lastFunction.calls.isEmpty()) {
			return;
		}

		ListIterator<Invocation> it = lastFunction.calls.listIterator(lastFunction.calls.size());
		Invocation[] realCalls = new Invocation[lastFunction.calls.size()];
		while (it.hasPrevious()) {
			int index = it.previousIndex();
			Invocation call = it.previous();
			Invocation realCall = call.function.pendingInvocations.pop();
			realCall.position = call.position;
			realCalls[index] = realCall;
			realCall.function.calls += call.count;
		}
		lastFunction.calls = Arrays.asList(realCalls);
	}

	private void buildCallTree(Invocation inv) {

		ProfilerCallTraceLayer lr = new ProfilerCallTraceLayer();
		int id = inv == mainCall ? -1 : inv.function.id;
		lr.setCalledID(id);
		lr.setType(ProfilerCallTraceLayer.ENTER);
		lr.setLine(inv.position);
		model.getCallTrace().addLayer(lr);
		for (Invocation sub : inv.calls) {
			buildCallTree(sub);
		}

		lr = new ProfilerCallTraceLayer();
		lr.setCalledID(id);
		lr.setTimestampMicroseconds(inv.cost);
		lr.setType(ProfilerCallTraceLayer.EXIT);
		lr.setLine(inv.position);
		model.getCallTrace().addLayer(lr);
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
