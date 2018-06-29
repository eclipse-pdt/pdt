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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CacheGrindParser {
	private InputStream fFile;

	public static interface CacheGrindParserListener {
		default void events(String[] events) {
		}

		default void version(String version) {
		}

		default void creator(String creator) {
		}

		default void unknown(String line) {
		}

		default void positions(String[] type) {
		}

		default void part(int part) {
		}

		default void cmd(String cmd) {
		}

		default void calls(int count, int[] values) {
		}

		default void pid(int pid) {
		}

		default void summary(int[] sum) {
		}

		default void totals(int[] sum) {
		}

		default void file(int id, String name) {
		}

		default void function(int id, String name) {
		}

		default void nextFile(int id, String name) {
		}

		default void nextFunction(int id, String name) {
		}

		default void cost(int pos, int[] events) {
		}

	}

	private static interface State {
		boolean valid(char letter);

		default void consume(CacheGrindParserListener listener) {
		}

		void reset();
	}

	private static abstract class TagState implements State {
		private final char[] name;
		private int pointer = 0;

		public TagState(char[] name) {
			this.name = name;
		}

		@Override
		public boolean valid(char letter) {
			if (pointer > name.length) {
				if (pointer == name.length + 1) {
					pointer++;
					return letter == ' ' || letter == '\t';
				}
				return next().valid(letter);
			} else if (pointer == name.length && letter == ':') {
				pointer++;
				return true;
			} else if (name[pointer] == letter) {
				pointer++;
				return true;
			}
			return false;
		}

		public abstract State next();

		public void reset() {
			pointer = 0;
			next().reset();
		}
	}

	private static abstract class BlockState implements State {
		private final char[] name;
		private int pointer = 0;

		public BlockState(char[] name) {
			this.name = name;
		}

		@Override
		public boolean valid(char letter) {
			if (pointer >= name.length) {
				return next().valid(letter);
			} else if (name.length > pointer && name[pointer] == letter) {
				pointer++;
				return true;
			}
			return false;
		}

		public abstract State next();

		public void reset() {
			pointer = 0;
			next().reset();
		}
	}

	private static class StringState implements State {

		private StringBuilder sb = new StringBuilder();

		@Override
		public boolean valid(char letter) {
			sb.append(letter);
			return true;
		}

		@Override
		public void reset() {
			sb.setLength(0);
		}

		public String result() {
			return sb.toString();
		}
	}

	private static class StringsState implements State {

		private StringBuilder sb = new StringBuilder();
		private List<String> strings = new ArrayList<>();

		@Override
		public boolean valid(char letter) {
			if (letter == ' ' || letter == '\t') {
				collect();
			} else {
				sb.append(letter);
			}
			return true;
		}

		@Override
		public void reset() {
			sb.setLength(0);
			strings.clear();
		}

		private void collect() {
			if (sb.length() > 0) {
				strings.add(sb.toString());
				sb.setLength(0);
			}
		}

		public String[] result() {
			collect();
			return strings.toArray(new String[0]);
		}
	}

	private static class NameState implements State {
		private static final int WAIT_INDEX = -1;
		private static final int WAIT_INDEX_NUM = -2;
		private static final int WAIT_SPACE = -4;
		private static final int WAIT_NAME = -8;
		private int index = WAIT_INDEX;
		private NumberState num = new NumberState();
		private StringState name = new StringState();

		@Override
		public boolean valid(char letter) {
			if (index == WAIT_INDEX) {
				if (letter == '(') {
					index = WAIT_INDEX_NUM;
					return true;
				} else {
					index = WAIT_NAME;
				}
			} else if (index == WAIT_INDEX_NUM) {
				if (letter == ')') {
					index = WAIT_SPACE;
					return true;
				} else {
					return num.valid(letter);
				}
			} else if (index == WAIT_SPACE) {
				if (letter == ' ' || letter == '\t') {
					index = this.num.result();
					return true;
				} else {
					return false;
				}
			}
			return name.valid(letter);
		}

		@Override
		public void reset() {
			num.reset();
			name.reset();
			index = -1;
		}

		public String name() {
			if (index == WAIT_NAME || index > 0) {
				String val = name.result();
				if (val.length() == 0) {
					return null;
				}
				return val;
			} else {
				return null;
			}
		}

		public int index() {
			if (index == WAIT_SPACE) {
				index = this.num.result();
			}
			if (index >= 0) {
				return index;
			} else {
				return -1;
			}
		}

	}

	private static class NumberState implements State {

		private StringBuilder sb = new StringBuilder();

		@Override
		public boolean valid(char letter) {
			if (Character.isDigit(letter) || (sb.length() == 0 && (letter == '+' || letter == '-'))) {
				sb.append(letter);
				return true;
			}
			return false;
		}

		@Override
		public void reset() {
			sb.setLength(0);
		}

		public int result() {
			if (sb.length() == 0) {
				return 0;
			}
			return Integer.valueOf(sb.toString());
		}

	}

	private static class NumbersState implements State {

		private StringBuilder sb = new StringBuilder();
		private List<Integer> numbers = new ArrayList<>();

		@Override
		public boolean valid(char letter) {
			if (letter == ' ' || letter == '\t') {
				collect();
				return true;
			} else if (Character.isDigit(letter) || (sb.length() == 0 && (letter == '+' || letter == '-'))) {
				sb.append(letter);
				return true;
			}

			return false;
		}

		@Override
		public void reset() {
			sb.setLength(0);
			numbers.clear();
		}

		private void collect() {
			if (sb.length() > 0) {
				numbers.add(Integer.valueOf(sb.toString()));
				sb.setLength(0);
			}
		}

		public int[] result() {
			collect();
			return numbers.stream().mapToInt(Integer::intValue).toArray();
		}
	}

	private static class CreatorState extends TagState {

		private StringState next = new StringState();

		public CreatorState() {
			super("creator".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.creator((String) next.result());
		}

		@Override
		public State next() {
			return next;
		}

	}

	private static class VersionState extends TagState {

		private StringState next = new StringState();

		public VersionState() {
			super("version".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.version((String) next.result());
		}

		@Override
		public State next() {
			return next;
		}

	}

	private static class CMDState extends TagState {

		private StringState next = new StringState();

		public CMDState() {
			super("cmd".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.cmd((String) next.result());
		}

		@Override
		public State next() {
			return next;
		}

	}

	private static class PIDState extends TagState {

		private NumberState next = new NumberState();

		public PIDState() {
			super("pid".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.pid(next.result());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class PartState extends TagState {

		private NumberState next = new NumberState();

		public PartState() {
			super("part".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.part(next.result());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class EventsState extends TagState {

		private StringsState next = new StringsState();

		public EventsState() {
			super("events".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.events(next.result());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class PositionsState extends TagState {

		private StringsState next = new StringsState();

		public PositionsState() {
			super("positions".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.positions(next.result());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class TotalsState extends TagState {

		private NumbersState next = new NumbersState();

		public TotalsState() {
			super("totals".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.totals(next.result());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class SummaryState extends TagState {

		private NumbersState next = new NumbersState();

		public SummaryState() {
			super("summary".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.summary(next.result());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class CallsState extends BlockState {

		private NumbersState next = new NumbersState();

		public CallsState() {
			super("calls=".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			int[] nums = next.result();
			if (nums.length > 1) {
				listener.calls(nums[0], Arrays.stream(nums).skip(1).toArray());
			} else {
				listener.unknown("calls="); //$NON-NLS-1$
			}
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class CostState extends BlockState {

		private NumbersState next = new NumbersState();

		public CostState() {
			super("".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			int[] nums = next.result();
			listener.cost(nums[0], Arrays.stream(nums).skip(1).toArray());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class FunctionState extends BlockState {

		private NameState next = new NameState();

		public FunctionState() {
			super("fn=".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.function(next.index(), next.name());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class FileState extends BlockState {

		private NameState next = new NameState();

		public FileState(String name) {
			super(name.toCharArray());
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.file(next.index(), next.name());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class NextFunctionState extends BlockState {

		private NameState next = new NameState();

		public NextFunctionState() {
			super("cfn=".toCharArray()); //$NON-NLS-1$
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.nextFunction(next.index(), next.name());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class NextFileState extends BlockState {

		private NameState next = new NameState();

		public NextFileState(String name) {
			super(name.toCharArray());
		}

		@Override
		public void consume(CacheGrindParserListener listener) {
			listener.nextFile(next.index(), next.name());
		}

		@Override
		public State next() {
			return next;
		}
	}

	private static class CommentState implements State {
		private boolean test = true;

		@Override
		public boolean valid(char letter) {
			if (test && letter != '#') {
				return false;
			} else {
				test = false;
			}
			return true;
		}

		@Override
		public void consume(CacheGrindParserListener listener) {

		}

		@Override
		public void reset() {
			test = true;
		}

	}

	private static final List<State> mainStates = new ArrayList<>();
	static {
		mainStates.add(new FunctionState());
		mainStates.add(new FileState("fl=")); //$NON-NLS-1$
		mainStates.add(new FileState("fi=")); //$NON-NLS-1$
		mainStates.add(new FileState("fe=")); //$NON-NLS-1$

		mainStates.add(new NextFunctionState());
		mainStates.add(new NextFileState("cfl=")); //$NON-NLS-1$
		mainStates.add(new NextFileState("cfi=")); //$NON-NLS-1$

		mainStates.add(new CostState());
		mainStates.add(new CallsState());
		mainStates.add(new CreatorState());
		mainStates.add(new VersionState());
		mainStates.add(new CMDState());
		mainStates.add(new PIDState());
		mainStates.add(new PartState());
		mainStates.add(new PositionsState());
		mainStates.add(new EventsState());
		mainStates.add(new SummaryState());
		mainStates.add(new TotalsState());
		mainStates.add(new CommentState());
	}

	public CacheGrindParser(InputStream file) {
		fFile = file;
	}

	public void parse(CacheGrindParserListener listener) throws IOException {
		try {
			Reader reader = new BufferedReader(new InputStreamReader(fFile));
			int letter = reader.read();
			StringBuilder buff = new StringBuilder();
			List<State> states = CacheGrindParser.mainStates;
			while (letter != -1) {
				if (letter == '\n') {
					if (states.size() != 1) {
						if (buff.length() > 0) {
							listener.unknown(buff.toString());
						}
					} else if (states.size() == 1) {
						states.get(0).consume(listener);
					}
					if (states.size() > 0) {
						states.forEach(s -> s.reset());
					}
					buff.setLength(0);
					states = CacheGrindParser.mainStates;
				} else if (states.size() > 0) {
					char tmp = (char) letter;
					states = states.stream().filter(s -> {
						if (s.valid(tmp)) {
							return true;
						}
						s.reset();
						return false;
					}).collect(Collectors.toList());
					buff.append(tmp);
				} else {
					buff.append((char) letter);
				}

				letter = reader.read();

			}
		} finally {
			CacheGrindParser.mainStates.forEach(s -> s.reset());
		}
	}

}
