/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.scope;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPScriptRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class ScopeParser {
	private final IDocument document;

	private int internalOffset;

	private class State {
		public ICompletionScope.Type type;
		public int start;
		public int length;

		public State(ICompletionScope.Type type, int start) {
			this.type = type;
			this.start = start;
		}

		public State(ICompletionScope.Type type, int start, int length) {
			this.type = type;
			this.start = start;
			this.length = length;
		}
	}

	private class SimpleBlockState extends State {
		public SimpleBlockState(Type type, int start) {
			super(type, start);
		}

	}

	private class ControlState extends State {
		public int head = 0;
		public boolean block = false;

		public ControlState(ICompletionScope.Type type, int start) {
			super(type, start);
		}
	}

	private class NamedState extends ControlState {
		public StringBuilder name = new StringBuilder();

		public NamedState(ICompletionScope.Type type, int start) {
			super(type, start);
		}
	}

	private Deque<State> states;
	private Deque<State> memory;
	private LinkedList<ITextRegion> buffer;
	private boolean collectName = false;

	@NonNull
	private final ICompletionScope parentScope;

	public ScopeParser(IDocument document) {
		this.document = document;
		this.parentScope = new CompletionScope(ICompletionScope.Type.FILE, 0,
				document != null ? document.getLength() : 0, null);
	}

	public ICompletionScope parse(int offset) {
		states = new LinkedList<>();
		states.push(new State(Type.FILE, parentScope.getOffset(), parentScope.getLength()));
		memory = new LinkedList<>();
		buffer = new LinkedList<>();
		if (document instanceof IStructuredDocument) {
			return parse((IStructuredDocument) document, parentScope, offset);
		}

		return parentScope;
	}

	protected ICompletionScope parse(IStructuredDocument document, ICompletionScope mainScope, int offset) {
		ICompletionScope scope = mainScope;
		internalOffset = offset;
		ITextRegion previousRegion = null;
		for (IStructuredDocumentRegion region : document.getStructuredDocumentRegions()) {
			if (region.getStartOffset() > offset) {
				break;
			}
			if (!region.getType().equals(PHPRegionContext.PHP_CONTENT)) {
				continue;
			}
			int pos = region.getStartOffset();
			for (Iterator<?> it = region.getRegions().iterator(); it.hasNext();) {
				ITextRegion sub = (ITextRegion) it.next();
				if (sub.getStart() + pos > offset) {
					break;
				}
				if (sub instanceof PHPScriptRegion) {
					PHPScriptRegion scriptRegion = (PHPScriptRegion) sub;
					try {
						ITextRegion[] phpTokens = scriptRegion.getPHPTokens(0, scriptRegion.getLength()); // read all

						for (ITextRegion phpToken : phpTokens) {
							if (phpToken.getType() != PHPRegionTypes.WHITESPACE) {
								buffer.add(phpToken);
							}
							State current = states.peek();
							int tokenStart = pos + sub.getStart() + phpToken.getStart();
							int tokenEnd = tokenStart + phpToken.getTextLength();
							switch (phpToken.getType()) {
							case PHPRegionTypes.WHITESPACE:
								continue;
							case PHPRegionTypes.PHP_LINE_COMMENT:
								pushState(new State(Type.COMMENT, tokenStart));
								popState(tokenEnd);
								break;
							case PHPRegionTypes.PHP_COMMENT_START:
								pushState(new State(Type.COMMENT, tokenStart));
								break;
							case PHPRegionTypes.PHP_COMMENT_END:
								if (current.type == Type.COMMENT) {
									popState(tokenEnd);
								}
								break;
							case PHPRegionTypes.PHPDOC_COMMENT_START:
								pushState(new State(Type.PHPDOC, tokenStart));
								break;
							case PHPRegionTypes.PHPDOC_COMMENT_END:
								if (current.type == Type.PHPDOC) {
									popState(tokenEnd);
								}
								break;
							case PHPRegionTypes.PHP_ABSTRACT:
							case PHPRegionTypes.PHP_CLASS:
								if (previousRegion != null
										&& previousRegion.getType() == PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM) {
									break;
								}
								if (current.type == Type.BLOCK || current.type == Type.FILE) {
									pushState(new NamedState(Type.CLASS, tokenStart));
								}
								break;
							case PHPRegionTypes.PHP_TRAIT:
								pushState(new NamedState(Type.TRAIT, tokenStart));
								break;
							case PHPRegionTypes.PHP_INTERFACE:
								pushState(new NamedState(Type.INTERFACE, tokenStart));
								break;
							case PHPRegionTypes.PHP_NAMESPACE:
								pushState(new NamedState(Type.NAMESPACE, tokenStart));
								break;
							case PHPRegionTypes.PHP_FUNCTION:
								if (current.type == Type.USE) {
									states.pop();
									pushState(new State(Type.USE_FUNCTION, current.start));
									current = states.peek();
								} else if (current.type == Type.TYPE_STATEMENT) {
									states.pop();
									pushState(new NamedState(Type.FUNCTION, current.start));
									current = states.peek();
								} else {
									pushState(new NamedState(Type.FUNCTION, tokenStart));
								}
								break;
							case PHPRegionTypes.PHP_CURLY_OPEN:
								if (current.type == Type.USE || current.type == Type.USE_CONST
										|| current.type == Type.USE_FUNCTION) {
									pushState(new State(Type.USE_GROUP, tokenStart));
								} else if (current.type == Type.TRAIT_USE) {
									pushState(new State(Type.TRAIT_PRECEDENCE, tokenStart));
								} else {
									pushState(new State(Type.BLOCK, tokenStart));
								}
								collectName = false;
								break;
							case PHPRegionTypes.PHP_IF:
								pushState(new ControlState(Type.IF, tokenStart));
								break;
							case PHPRegionTypes.PHP_SWITCH:
								pushState(new ControlState(Type.SWITCH, tokenStart));
								break;
							case PHPRegionTypes.PHP_FOR:
								pushState(new ControlState(Type.FOR, tokenStart));
								break;
							case PHPRegionTypes.PHP_FOREACH:
								pushState(new ControlState(Type.FOREACH, tokenStart));
								break;
							case PHPRegionTypes.PHP_WHILE:
								if (current.type != Type.DOWHILE) {
									pushState(new ControlState(Type.WHILE, tokenStart));
								}
								break;
							case PHPRegionTypes.PHP_USE:
								if (current.type == Type.BLOCK) {
									states.pop(); // for a moment
									if (states.peek().type == Type.CLASS || states.peek().type == Type.TRAIT) {
										states.push(current);
										pushState(new ControlState(Type.TRAIT_USE, tokenStart));
									} else {
										states.push(current);
										pushState(new ControlState(Type.USE, tokenStart));
									}
								} else if (current.type == Type.FILE) {
									pushState(new ControlState(Type.USE, tokenStart));
								}

								break;
							case PHPRegionTypes.PHP_TRY:
								pushState(new ControlState(Type.TRY, tokenStart));
								break;
							case PHPRegionTypes.PHP_CATCH:
								pushState(new ControlState(Type.CATCH, tokenStart));
								break;
							case PHPRegionTypes.PHP_FINALLY:
								pushState(new ControlState(Type.FINALLY, tokenStart));
								break;
							case PHPRegionTypes.PHP_DO:
								pushState(new ControlState(Type.DOWHILE, tokenStart));
								break;
							case PHPRegionTypes.PHP_CURLY_CLOSE:
								if (current.type == Type.BLOCK || current.type == Type.USE_GROUP
										|| current.type == Type.TRAIT_PRECEDENCE) {
									popState(tokenEnd);
									current = states.peek();
									if (current.type == Type.TRAIT_PRECEDENCE) {
										popState(tokenEnd);
										current = states.peek();
									}
								}
								switch (current.type) {
								case DOWHILE: // we waiting for HEAD ";"
									break;
								default:
									if (current instanceof ControlState) {
										popState(tokenEnd);
										break;
									}
								}
								break;
							case PHPRegionTypes.PHP_NS_SEPARATOR:
							case PHPRegionTypes.PHP_LABEL:
								if (current instanceof NamedState && collectName) {
									((NamedState) current).name
											.append(document.get(tokenStart, phpToken.getTextLength()));
								}
								break;
							case PHPRegionTypes.PHP_SEMICOLON:
								collectName = false;
								switch (current.type) {
								case NAMESPACE:
									current.length = parentScope.getLength() - current.start;
									pushState(new State(Type.BLOCK, tokenStart + 1));
									break;
								case TYPE_STATEMENT:
								case CONST:
								case FIELD:
								case FUNCTION:
								case USE:
								case TRAIT_USE:
									popState(tokenEnd);
									break;
								case WHILE:
								case IF:
								case FOR:
								case FOREACH:
								case DOWHILE:
									if (!((ControlState) (current)).block) {
										if (!buffer.isEmpty()) {
											pushState(new SimpleBlockState(Type.BLOCK,
													pos + sub.getStart() + buffer.get(0).getStart()));
										} else {
											pushState(new SimpleBlockState(Type.BLOCK, tokenStart));
										}
										popState(tokenEnd);
										((ControlState) (current)).block = true;
									}

									popState(tokenEnd);
									break;
								default:
								}
								break;
							case PHPRegionTypes.PHP_TOKEN:
								switch (document.getChar(tokenStart)) {
								case ':':
									switch (current.type) {
									case SWITCH:
									case WHILE:
									case FOR:
									case FOREACH:
									case IF:
										pushState(new State(Type.BLOCK, tokenStart));
										collectName = false;
										break;
									default:
									}
									break;
								case '(':
									switch (current.type) {
									case SWITCH:
									case WHILE:
									case FOR:
									case FOREACH:
									case DOWHILE:
									case CATCH:
									case FUNCTION:
									case IF:
										((ControlState) current).head = 1;
										pushState(new State(Type.HEAD, tokenStart));
										collectName = false;
										break;
									case HEAD:
										((ControlState) current).head++;
										break;
									default:
									}
								case ')':
									if (current.type == Type.HEAD) {
										if (((ControlState) current).head > 1) {
											((ControlState) current).head--;
										} else {
											popState(tokenStart + 1); // ignore whitespace
										}
									}
								}
								break;
							case PHPRegionTypes.PHP_ENDIF:
								if (current.type == Type.BLOCK) {
									states.pop(); // remove for a moment
									if (states.peek().type == Type.IF) {
										states.push(current);
										popState(tokenEnd);
										popState(tokenEnd);
									}
								}
								break;
							case PHPRegionTypes.PHP_ENDSWITCH:
								if (current.type == Type.BLOCK) {
									states.pop(); // remove for a moment
									if (states.peek().type == Type.SWITCH) {
										states.push(current);
										popState(tokenEnd);
										popState(tokenEnd);
									}
								}
								break;
							case PHPRegionTypes.PHP_ENDFOREACH:
								if (current.type == Type.BLOCK) {
									states.pop(); // remove for a moment
									if (states.peek().type == Type.FOREACH) {
										states.push(current);
										popState(tokenEnd);
										popState(tokenEnd);
									}
								}
								break;
							case PHPRegionTypes.PHP_ENDWHILE:
								if (current.type == Type.BLOCK) {
									states.pop(); // remove for a moment
									if (states.peek().type == Type.WHILE) {
										states.push(current);
										popState(tokenEnd);
										popState(tokenEnd);
									}
								}
								break;
							case PHPRegionTypes.PHP_ENDFOR:
								if (current.type == Type.BLOCK) {
									states.pop(); // remove for a moment
									if (states.peek().type == Type.FOR) {
										states.push(current);
										popState(tokenEnd);
										popState(tokenEnd);
									}
								}
								break;
							case PHPRegionTypes.PHP_VAR:
								pushState(new State(Type.FIELD, tokenStart));
								break;
							case PHPRegionTypes.PHP_VARIABLE:
								if (current.type == Type.TYPE_STATEMENT) {
									states.pop();
									pushState(new State(Type.FIELD, current.start));
									current = states.peek();
								}
								break;
							case PHPRegionTypes.PHP_CONST:
								if (current.type == Type.USE) {
									states.pop();
									pushState(new State(Type.USE_CONST, current.start));
									current = states.peek();
								} else {
									pushState(new State(Type.CONST, tokenStart));
								}
								break;

							case PHPRegionTypes.PHP_PRIVATE:
							case PHPRegionTypes.PHP_STATIC:
							case PHPRegionTypes.PHP_PROTECTED:
							case PHPRegionTypes.PHP_PUBLIC:
								if (current.type != Type.TYPE_STATEMENT) {
									pushState(new State(Type.TYPE_STATEMENT, tokenStart));
								}
								break;
							case PHPRegionTypes.PHP_EXTENDS:
							case PHPRegionTypes.PHP_IMPLEMENTS:
								collectName = false;
								break;
							}

							// System.out.println(phpToken);
							// System.out.println("'" + document.get(tokenStart, phpToken.getLength()) +
							// "'");
							previousRegion = phpToken;
						}
					} catch (BadLocationException e) {
						Logger.logException(e);
					}
				}
			}
		}
		while (states.size() > 1) { // unfinished states, recovery as end file
			popState(scope.getLength() + scope.getOffset());
		}
		if (!memory.isEmpty()) {
			for (State state : memory) {
				if (state instanceof NamedState) {
					String name = ((NamedState) state).name.length() == 0 ? null : ((NamedState) state).name.toString();
					scope = new CompletionScope(state.type, name, state.start, state.length, scope);
				} else {
					scope = new CompletionScope(state.type, state.start, state.length, scope);
				}

			}
		}
		memory.clear();
		states.clear();

		return scope;
	}

	private void popState(int end) {
		State state = states.pop();
		state.length = end - state.start;
		if (state.start <= internalOffset && end >= internalOffset) {
			memory.addFirst(state);
		}
		if (states.peek() instanceof SimpleBlockState) {
			popState(end);
			popState(end);
		}
	}

	private void pushState(State state) {
		if (state.type == Type.BLOCK && states.peek() instanceof ControlState) {
			((ControlState) states.peek()).block = true;
		}
		if (state instanceof ControlState && states.peek() instanceof ControlState
				&& !((ControlState) states.peek()).block) {
			// nested one-line block
			pushState(new SimpleBlockState(Type.BLOCK, state.start));
		}
		states.push(state);
		buffer.clear();
		if (state instanceof NamedState) {
			collectName = true;
		} else if (state instanceof ControlState) {
			collectName = false;
		}
	}

}
