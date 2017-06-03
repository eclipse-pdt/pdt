package org.eclipse.php.internal.debug.core.xdebug.cachegrind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.internal.debug.core.xdebug.cachegrind.Tree.Events;
import org.eclipse.php.internal.debug.core.xdebug.cachegrind.Tree.Header;
import org.eclipse.php.internal.debug.core.xdebug.cachegrind.Tree.InvocationEntry;
import org.eclipse.php.internal.debug.core.xdebug.cachegrind.Tree.Positions;

public class Parser {
	public static class ParserException extends Exception {
		private static final long serialVersionUID = -5104586272535325175L;

		public ParserException() {
			super();
		}

		public ParserException(String message) {
			super(message);
		}

		public ParserException(Throwable t) {
			super(t);
		}

		public ParserException(String message, Throwable t) {
			super(message, t);
		}
	}

	private File grindFile;

	public Parser(File grindFile) {
		this.grindFile = grindFile;
	}

	private Map<Boolean, Map<Integer, String>> compressedNames = new HashMap<>();

	private Map<Boolean, Map<String, Integer>> reversedNames = new HashMap<>();

	private Map<String, InvocationEntry> functions = new HashMap<>();

	public Tree parse() throws Exception {
		Tree tree = new Tree();

		try (BufferedReader br = new BufferedReader(new FileReader(grindFile))) {
			InvocationEntry entry = null;
			while (br.ready()) {
				String line = br.readLine();

				if (line.startsWith("fl=")) {
					String function = parseFunction(br.readLine());

					function = getCompressedName(function, false);
					if ("{main}".equals(function)) {
						br.readLine();
						parseHeader(tree.getHeader(), br.readLine());
						br.readLine();
					}

					String costLine = br.readLine();

					Integer[] cost = parseCost(costLine);

					if (!functions.containsKey(function)) {
						functions.put(function, parseLocation(tree, line, cost[0]));
					}
					entry = functions.get(function);
					entry.setFunctionName(function);
					entry.setFunctionIndex(getFunctionIndex(function, false));
					entry.setSummedSelfCost(entry.getSummedSelfCost() + cost[1]);
					entry.setSummedInclusiveCost(entry.getSummedInclusiveCost() + cost[1]);
				} else if (line.startsWith("cfn=")) {
					int functionIndex = getFunctionIndex(line);

					// Skip call line
					br.readLine();

					String costLine = br.readLine();

					Integer[] cost = parseCost(costLine);

					InvocationEntry callee = tree.getByIndex(functionIndex);
					if (entry != null && callee != null) {
						entry.addCallee(callee);
						entry.setSummedInclusiveCost(entry.getSummedInclusiveCost() + cost[1]);
					}
				} else if (line.contains(":")) {
					parseHeader(tree.getHeader(), line);
				}
			}
		}
		return tree;
	}

	private Integer[] parseCost(String line) {
		Pattern p = Pattern.compile("(\\d+) (\\d+)");
		Matcher m = p.matcher(line);
		Integer lineNo = 0;
		Integer cost = 0;
		if (m.matches()) {
			lineNo = Integer.valueOf(m.group(1));
			cost = Integer.valueOf(m.group(2));
		}

		return new Integer[] { lineNo, cost };
	}

	private InvocationEntry parseLocation(Tree tree, String line, int lineNo) {
		String function = getCompressedName(line.substring(3), true);

		InvocationEntry entry = tree.newInvocationEntry(function, lineNo, getFunctionIndex(function, true));
		return entry;
	}

	private void parseHeader(Header header, String line) throws ParserException {
		int pos = line.indexOf(":");
		String[] headerPair = new String[] { line.substring(0, pos), line.substring(pos + 1) };
		switch (headerPair[0]) {
		case "version":
			header.setVersion(Integer.valueOf(headerPair[1].trim()));
			break;

		case "creator":
			header.setCreator(headerPair[1].trim());
			break;

		case "cmd":
			header.setCommand(headerPair[1].trim());
			break;

		case "part":
			header.setPart(Integer.valueOf(headerPair[1].trim()));
			break;

		case "positions":
			switch (headerPair[1].trim()) {
			case "line":
				header.setPositions(Positions.LINE);
				break;
			default:
				throw new ParserException("Invalid positions type " + headerPair[1].trim());
			}
			break;

		case "events":
			switch (headerPair[1].trim()) {
			case "Time":
				header.setEvents(Events.TIME);
				break;

			default:
				throw new ParserException("Invalid event type " + headerPair[1].trim());
			}
			break;

		case "summary":
			header.setSummary(Long.valueOf(headerPair[1].trim()));
			break;

		default:
			throw new ParserException("Invalid header type " + headerPair[0].trim());
		}
	}

	private String getCompressedName(String function, boolean isFile) {
		if (!this.compressedNames.containsKey(isFile)) {
			this.compressedNames.put(isFile, new HashMap<>());
		}
		Map<Integer, String> map = this.compressedNames.get(isFile);

		Pattern p = Pattern.compile("\\((\\d+)\\)(.+)?");
		Matcher m = p.matcher(function);
		if (!m.find()) {
			return function.trim();
		}
		int functionIndex = Integer.valueOf(m.group(1));
		String functionName = m.group(2);
		if (functionName != null) {
			map.put(functionIndex, functionName.trim());
		} else if (!map.containsKey(functionIndex)) {
			return function;
		}
		return map.get(functionIndex);
	}

	private int getFunctionIndex(String line) {
		Pattern p = Pattern.compile("\\((\\d+)\\)(.+)?");
		Matcher m = p.matcher(line);
		if (!m.find()) {
			return -1;
		}
		int functionIndex = Integer.valueOf(m.group(1));

		return functionIndex;
	}

	private int getFunctionIndex(String functionName, boolean isFile) {
		int index = 0;

		if (!this.reversedNames.containsKey(isFile)) {
			this.reversedNames.put(isFile, new HashMap<>());
		}

		Map<String, Integer> revMap = this.reversedNames.get(isFile);
		if (revMap.containsKey(functionName)) {
			index = revMap.get(functionName);
		}

		if (!this.compressedNames.containsKey(isFile)) {
			this.compressedNames.put(isFile, new HashMap<>());
		}
		Map<Integer, String> map = this.compressedNames.get(isFile);

		if (map.containsValue(functionName)) {
			Set<Entry<Integer, String>> set = map.entrySet();
			for (Entry<Integer, String> entry : set) {
				if (entry.getValue().equals(functionName)) {
					index = entry.getKey();
					break;
				}
			}
		}

		if (index > 0) {
			revMap.put(functionName, index);
		}

		return index;
	}

	private String parseFunction(String in) throws Exception {
		Pattern p = Pattern.compile("fn=([^\r\n]+)?");
		Matcher m = p.matcher(in);

		if (m.find()) {
			return m.group(1).trim();
		}
		throw new Exception("Function pattern not found");
	}
}