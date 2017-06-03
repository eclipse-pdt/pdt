package org.eclipse.php.internal.debug.core.xdebug.cachegrind;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	public enum Positions {
		LINE;
	}

	public enum Events {
		TIME;
	}

	public class Header {
		private int version;

		private String creator;

		private String command;

		private int part;

		private Positions positions;

		private Events events;

		private long summary;

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		public String getCommand() {
			return command;
		}

		public void setCommand(String command) {
			this.command = command;
		}

		public int getPart() {
			return part;
		}

		public void setPart(int part) {
			this.part = part;
		}

		public Positions getPositions() {
			return positions;
		}

		public void setPositions(Positions positions) {
			this.positions = positions;
		}

		public Events getEvents() {
			return events;
		}

		public void setEvents(Events events) {
			this.events = events;
		}

		public long getSummary() {
			return summary;
		}

		public void setSummary(long summary) {
			this.summary = summary;
		}
	}

	public class InvocationEntry {
		private String functionName;

		private String fileName;

		private int functionIndex;

		private int line;

		private int invocationCnt;

		private long summedSelfCost;

		private long summedInclusiveCost;

		private Object calledFromInformation;

		private Object subCallInformation;

		private List<InvocationEntry> callees;

		private Tree tree;

		public InvocationEntry(Tree tree) {
			super();
			this.callees = new ArrayList<>();
			this.tree = tree;
		}

		public String getFunctionName() {
			return functionName;
		}

		public void setFunctionName(String functionName) {
			this.functionName = functionName;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public int getFunctionIndex() {
			return this.functionIndex;
		}

		public void setFunctionIndex(int functionIndex) {
			this.functionIndex = functionIndex;
		}

		public int getLine() {
			return line;
		}

		public void setLine(int line) {
			this.line = line;
		}

		public int getInvocationCnt() {
			return invocationCnt;
		}

		public void setInvocationCnt(int invocationCnt) {
			this.invocationCnt = invocationCnt;
		}

		public long getSummedSelfCost() {
			return summedSelfCost;
		}

		public void setSummedSelfCost(long summedSelfCost) {
			this.summedSelfCost = summedSelfCost;
		}

		public long getSummedInclusiveCost() {
			return summedInclusiveCost;
		}

		public void setSummedInclusiveCost(long summedInclusiveCost) {
			this.summedInclusiveCost = summedInclusiveCost;
		}

		public Object getCalledFromInformation() {
			return calledFromInformation;
		}

		public void setCalledFromInformation(Object calledFromInformation) {
			this.calledFromInformation = calledFromInformation;
		}

		public Object getSubCallInformation() {
			return subCallInformation;
		}

		public void setSubCallInformation(Object subCallInformation) {
			this.subCallInformation = subCallInformation;
		}

		public void addCallee(InvocationEntry callee) {
			this.callees.add(callee);
		}

		public final List<InvocationEntry> getCallees() {
			return this.callees;
		}
	}

	private Header header;

	private List<InvocationEntry> invocationEntries;

	public Tree() {
		this.header = new Header();
		this.invocationEntries = new ArrayList<>();
	}

	public final Header getHeader() {
		return this.header;
	}

	public final List<InvocationEntry> getInvocationEntries() {
		return this.invocationEntries;
	}

	public void addInvocationEntry(InvocationEntry entry) {
		this.invocationEntries.add(entry);
	}

	public InvocationEntry newInvocationEntry(String fileName, int line, int functionIndex) {
		InvocationEntry entry = new InvocationEntry(this);
		entry.setFileName(fileName);
		entry.setLine(line);
		entry.setFunctionIndex(functionIndex);

		this.invocationEntries.add(entry);
		return entry;
	}

	public InvocationEntry getStart() {
		return getByFunctionName("{main}");
	}

	public InvocationEntry getByIndex(int index) {
		for (InvocationEntry entry : this.invocationEntries) {
			if (entry.getFunctionIndex() == index) {
				return entry;
			}
		}

		return null;
	}

	public InvocationEntry getByFunctionName(String functionName) {
		for (InvocationEntry entry : this.invocationEntries) {
			if (functionName.equals(entry.getFunctionName())) {
				return entry;
			}
		}
		return null;
	}
}