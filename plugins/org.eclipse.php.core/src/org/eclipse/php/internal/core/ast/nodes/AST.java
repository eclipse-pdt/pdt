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
package org.eclipse.php.internal.core.ast.nodes;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.ast.parser.ASTParser;
import org.eclipse.php.internal.core.ast.parser.AstLexer;
import org.eclipse.php.internal.core.ast.parser.PhpAstLexer4;
import org.eclipse.php.internal.core.ast.parser.PhpAstLexer5;
import org.eclipse.php.internal.core.ast.parser.PhpAstParser4;
import org.eclipse.php.internal.core.ast.parser.PhpAstParser5;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewriteFlattener;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.lr_parser;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.text.edits.TextEdit;

/**
 * Umbrella owner and abstract syntax tree node factory.
 * An <code>AST</code> instance serves as the common owner of any number of
 * AST nodes, and as the factory for creating new AST nodes owned by that 
 * instance.
 * <p>
 * Abstract syntax trees may be hand constructed by clients, using the
 * <code>new<i>TYPE</i></code> factory methods to create new nodes, and the
 * various <code>set<i>CHILD</i></code> methods 
 * (see {@link org.eclipse.php.internal.core.ast.nodes.ASTNode} and its subclasses)
 * to connect them together.
 * </p>
 * <p>
 * Each AST node belongs to a unique AST instance, called the owning AST.
 * The children of an AST node always have the same owner as their parent node.
 * If a node from one AST is to be added to a different AST, the subtree must
 * be cloned first to ensures that the added nodes have the correct owning AST.
 * </p>
 * <p>
 * There can be any number of AST nodes owned by a single AST instance that are
 * unparented. Each of these nodes is the root of a separate little tree of nodes.
 * The method <code>ASTNode.getProgramRoot()</code> navigates from any node to the root
 * of the tree that it is contained in. Ordinarily, an AST instance has one main
 * tree (rooted at a <code>Program</code>), with newly-created nodes appearing
 * as additional roots until they are parented somewhere under the main tree.
 * One can navigate from any node to its AST instance, but not conversely.
 * </p>
 * <p>
 * The class {@link ASTParser} parses a string
 * containing a PHP source code and returns an abstract syntax tree
 * for it. The resulting nodes carry source ranges relating the node back to
 * the original source characters.
 * </p>
 * <p>
 * Programs created by <code>ASTParser</code> from a
 * source document can be serialized after arbitrary modifications
 * with minimal loss of original formatting. Here is an example:
 * <pre>
 *  
 * Document doc = new Document("<?\n class X {} \n echo 'hello world';\n  ?>");
 * ASTParser parser = ASTParser.newParser(AST.PHP5);
 * parser.setSource(doc.get().toCharArray());
 * Program program = parser.createAST(null);
 * program.recordModifications();
 * AST ast = program.getAST();
 * EchoStatement echo = ast.newEchoStatement();
 * echo.setExpression(ast.newScalar(“Hello World“);
 * program.statements().add(echo);
 * TextEdit edits = program.rewrite(document, null);
 * UndoEdit undo = edits.apply(document);
 * 
 * </pre>
 * See also {@link ASTRewrite} for
 * an alternative way to describe and serialize changes to a
 * read-only AST.
 * </p>
 * <p>
 * Clients may create instances of this class using {@link #newAST(int)}, 
 * but this class is not intended to be subclassed.
 * </p>
 * 
 * @see ASTParser
 * @see ASTNode
 * @since 2.0
 */
public class AST {

	public final static String PHP4 = PHPVersion.PHP4;
	public final static String PHP5 = PHPVersion.PHP5;
	
	/**
	 * The scanner for this AST, it can ve 
	 */
	private final AstLexer lexer;
	
	/**
	 * 
	 */
	private final lr_parser parser;
	
	/**
	 * 
	 */
	public final String apiLevel;

	/**
	 * The event handler for this AST. 
	 * Initially an event handler that does not nothing.
	 * @since 3.0
	 */
	private NodeEventHandler eventHandler = new NodeEventHandler();
	
	/**
	 * Internal modification count; initially 0; increases monotonically
	 * <b>by one or more</b> as the AST is successively modified.
	 */
	private long modificationCount = 0;

	/**
	 * Internal original modification count; value is equals to <code>
	 * modificationCount</code> at the end of the parse (<code>ASTParser
	 * </code>). If this ast is not created with a parser then value is 0.
	 * @since 3.0
	 */
	private long originalModificationCount = 0;

	/**
	 * When disableEvents > 0, events are not reported and
	 * the modification count stays fixed.
	 * <p>
	 * This mechanism is used in lazy initialization of a node
	 * to prevent events from being reported for the modification
	 * of the node as well as for the creation of the missing child.
	 * </p>
	 * @since 3.0
	 */
	private int disableEvents = 0;

	/**
	 * Internal object unique to the AST instance. Readers must synchronize on
	 * this object when the modifying instance fields.
	 * @since 3.0
	 */
	private final Object internalASTLock = new Object();
	
	/**
	 * Default value of <code>flag<code> when a new node is created.
	 */
	private int defaultNodeFlag = 0;

	/**
	 * Internal ast rewriter used to record ast modification when record mode is enabled.
	 */
	InternalASTRewrite rewriter;
	
	/**
	 * The binding resolver for this AST. Initially a binding resolver that
	 * does not resolve names at all.
	 */
	private BindingResolver resolver = new BindingResolver();
	
	public AST(Reader reader, String apiLevel, boolean aspTagsAsPhp) throws IOException {
		this.apiLevel = apiLevel;
		this.lexer = getLexerInstance(reader, apiLevel, aspTagsAsPhp);
		this.parser = getParserInstance(apiLevel);
	}

	/**
	 * Constructs a scanner from a given reader
	 * @param reader
	 * @param phpVersion
	 * @param aspTagsAsPhp
	 * @return
	 * @throws IOException
	 */
	private AstLexer getLexerInstance(Reader reader, String phpVersion, boolean aspTagsAsPhp) throws IOException {
		if (PHPVersion.PHP4.equals(phpVersion)) {
			final PhpAstLexer4 lexer4 = getLexer4(reader);
			lexer4.setUseAspTagsAsPhp(aspTagsAsPhp);
			return lexer4;
		} else if (PHPVersion.PHP5.equals(phpVersion)) {
			final PhpAstLexer5 lexer5 = getLexer5(reader);
			lexer5.setUseAspTagsAsPhp(aspTagsAsPhp);
			return lexer5;
		} else {
			throw new IllegalArgumentException(CoreMessages.getString("ASTParser_1") + phpVersion);
		}
	}
	
	private PhpAstLexer5 getLexer5(Reader reader) throws IOException {
		final PhpAstLexer5 phpAstLexer5 = new PhpAstLexer5(reader);
		phpAstLexer5.setAST(this);
		return phpAstLexer5;
	}

	private PhpAstLexer4 getLexer4(Reader reader) throws IOException {
		final PhpAstLexer4 phpAstLexer4 = new PhpAstLexer4(reader);
		phpAstLexer4.setAST(this);
		return phpAstLexer4;
	}

	private lr_parser getParserInstance(String phpVersion) {
		if (PHPVersion.PHP4.equals(phpVersion)) {
			final PhpAstParser4 parser = new PhpAstParser4();
			parser.setAST(this);
			return parser;
		} else if (PHPVersion.PHP5.equals(phpVersion)) {
			final PhpAstParser5 parser = new PhpAstParser5();
			parser.setAST(this);
			return parser;
		} else {
			throw new IllegalArgumentException(CoreMessages.getString("ASTParser_1") + phpVersion);
		}
	}
	

	/**
	 * Returns the modification count for this AST. The modification count
	 * is a non-negative value that increases (by 1 or perhaps by more) as
	 * this AST or its nodes are changed. The initial value is unspecified.
	 * <p>
	 * The following things count as modifying an AST:
	 * <ul>
	 * <li>creating a new node owned by this AST,</li>
	 * <li>adding a child to a node owned by this AST,</li>
	 * <li>removing a child from a node owned by this AST,</li>
	 * <li>setting a non-node attribute of a node owned by this AST.</li>
	 * </ul>
	 * </p>
	 * Operations which do not entail creating or modifying existing nodes
	 * do not increase the modification count.
	 * <p>
	 * N.B. This method may be called several times in the course
	 * of a single client operation. The only promise is that the modification
	 * count increases monotonically as the AST or its nodes change; there is
	 * no promise that a modifying operation increases the count by exactly 1.
	 * </p>
	 *
	 * @return the current value (non-negative) of the modification counter of
	 *    this AST
	 */
	public long modificationCount() {
		return this.modificationCount;
	}

	/**
	 * Return the API level supported by this AST.
	 *
	 * @return level the API level; one of the <code>JLS*</code>LEVEL
     * declared on <code>AST</code>; assume this set is open-ended
     * @since 3.0
	 */
	public String apiLevel() {
		return this.apiLevel;
	}

	/**
	 * Indicates that this AST is about to be modified.
	 * <p>
	 * The following things count as modifying an AST:
	 * <ul>
	 * <li>creating a new node owned by this AST</li>
	 * <li>adding a child to a node owned by this AST</li>
	 * <li>removing a child from a node owned by this AST</li>
	 * <li>setting a non-node attribute of a node owned by this AST</li>.
	 * </ul>
	 * </p>
	 * <p>
	 * N.B. This method may be called several times in the course
	 * of a single client operation.
	 * </p>
	 */
	void modifying() {
		// when this method is called during lazy init, events are disabled
		// and the modification count will not be increased
		if (this.disableEvents > 0) {
			return;
		}
		// increase the modification count
		this.modificationCount++;
	}

	/**
     * Disable events.
	 * This method is thread-safe for AST readers.
	 *
	 * @see #reenableEvents()
     * @since 3.0
     */
	final void disableEvents() {
		synchronized (this.internalASTLock) {
			// guard against concurrent access by another reader
			this.disableEvents++;
		}
		// while disableEvents > 0 no events will be reported, and mod count will stay fixed
	}

	/**
     * Reenable events.
	 * This method is thread-safe for AST readers.
	 *
	 * @see #disableEvents()
     * @since 3.0
     */
	final void reenableEvents() {
		synchronized (this.internalASTLock) {
			// guard against concurrent access by another reader
			this.disableEvents--;
		}
	}

	/**
	 * Reports that the given node is about to lose a child.
	 *
	 * @param node the node about to be modified
	 * @param child the node about to be removed
	 * @param property the child or child list property descriptor
	 * @since 3.0
	 */
	void preRemoveChildEvent(ASTNode node, ASTNode child, StructuralPropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE DEL]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.preRemoveChildEvent(node, child, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has not been changed yet
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node jsut lost a child.
	 *
	 * @param node the node that was modified
	 * @param child the child node that was removed
	 * @param property the child or child list property descriptor
	 * @since 3.0
	 */
	void postRemoveChildEvent(ASTNode node, ASTNode child, StructuralPropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE DEL]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.postRemoveChildEvent(node, child, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has not been changed yet
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node is about have a child replaced.
	 *
	 * @param node the node about to be modified
	 * @param child the child node about to be removed
	 * @param newChild the replacement child
	 * @param property the child or child list property descriptor
	 * @since 3.0
	 */
	void preReplaceChildEvent(ASTNode node, ASTNode child, ASTNode newChild, StructuralPropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE REP]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.preReplaceChildEvent(node, child, newChild, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has not been changed yet
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node has just had a child replaced.
	 *
	 * @param node the node modified
	 * @param child the child removed
	 * @param newChild the replacement child
	 * @param property the child or child list property descriptor
	 * @since 3.0
	 */
	void postReplaceChildEvent(ASTNode node, ASTNode child, ASTNode newChild, StructuralPropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE REP]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.postReplaceChildEvent(node, child, newChild, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has not been changed yet
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node is about to gain a child.
	 *
	 * @param node the node that to be modified
	 * @param child the node that to be added as a child
	 * @param property the child or child list property descriptor
	 * @since 3.0
	 */
	void preAddChildEvent(ASTNode node, ASTNode child, StructuralPropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE ADD]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.preAddChildEvent(node, child, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has already been changed
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node has just gained a child.
	 *
	 * @param node the node that was modified
	 * @param child the node that was added as a child
	 * @param property the child or child list property descriptor
	 * @since 3.0
	 */
	void postAddChildEvent(ASTNode node, ASTNode child, StructuralPropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE ADD]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.postAddChildEvent(node, child, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has already been changed
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node is about to change the value of a
	 * non-child property.
	 *
	 * @param node the node to be modified
	 * @param property the property descriptor
	 * @since 3.0
	 */
	void preValueChangeEvent(ASTNode node, SimplePropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE CHANGE]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.preValueChangeEvent(node, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has already been changed
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node has just changed the value of a
	 * non-child property.
	 *
	 * @param node the node that was modified
	 * @param property the property descriptor
	 * @since 3.0
	 */
	void postValueChangeEvent(ASTNode node, SimplePropertyDescriptor property) {
		// IMPORTANT: this method is called by readers during lazy init
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE CHANGE]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.postValueChangeEvent(node, property);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has already been changed
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node is about to be cloned.
	 *
	 * @param node the node to be cloned
	 * @since 3.0
	 */
	void preCloneNodeEvent(ASTNode node) {
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE CLONE]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.preCloneNodeEvent(node);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has already been changed
		} finally {
			reenableEvents();
		}
	}

	/**
	 * Reports that the given node has just been cloned.
	 *
	 * @param node the node that was cloned
	 * @param clone the clone of <code>node</code>
	 * @since 3.0
	 */
	void postCloneNodeEvent(ASTNode node, ASTNode clone) {
		synchronized (this.internalASTLock) {
			// guard against concurrent access by a reader doing lazy init
			if (this.disableEvents > 0) {
				// doing lazy init OR already processing an event
				// System.out.println("[BOUNCE CLONE]");
				return;
			} else {
				disableEvents();
			}
		}
		try {
			this.eventHandler.postCloneNodeEvent(node, clone);
			// N.B. even if event handler blows up, the AST is not
			// corrupted since node has already been changed
		} finally {
			reenableEvents();
		}
	}
	
  	BindingResolver getBindingResolver() {
		return this.resolver;
	}

	/**
	 * Returns the event handler for this AST.
	 *
	 * @return the event handler for this AST
	 * @since 3.0
	 */
	NodeEventHandler getEventHandler() {
		return this.eventHandler;
	}

	/**
	 * Sets the event handler for this AST.
	 *
	 * @param eventHandler the event handler for this AST
	 * @since 3.0
	 */
	void setEventHandler(NodeEventHandler eventHandler) {
		if (this.eventHandler == null) {
			throw new IllegalArgumentException();
		}
		this.eventHandler = eventHandler;
	}

	/**
	 * Returns default node flags of new nodes of this AST.
	 *
	 * @return the default node flags of new nodes of this AST
	 * @since 3.0
	 */
	int getDefaultNodeFlag() {
		return this.defaultNodeFlag;
	}

	/**
	 * Sets default node flags of new nodes of this AST.
	 *
	 * @param flag node flags of new nodes of this AST
	 * @since 3.0
	 */
	void setDefaultNodeFlag(int flag) {
		this.defaultNodeFlag = flag;
	}

	/**
	 * Set <code>originalModificationCount</code> to the current modification count
	 *
	 * @since 3.0
	 */
	void setOriginalModificationCount(long count) {
		this.originalModificationCount = count;
	}

	/**
	 * Returns the type binding for a "well known" type.
	 * <p>
	 * Note that bindings are generally unavailable unless requested when the
	 * AST is being built.
	 * </p>
	 * <p>
	 * The following type names are supported:
	 * <ul>
	 * <li><code>"boolean"</code></li>
	 * <li><code>"byte"</code></li>
	 * <li><code>"char"</code></li>
	 * <li><code>"double"</code></li>
	 * <li><code>"float"</code></li>
	 * <li><code>"int"</code></li>
	 * <li><code>"long"</code></li>
	 * <li><code>"short"</code></li>
	 * <li><code>"void"</code></li>
	 * <li><code>"PHP.lang.Boolean"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.Byte"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.Character"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.Class"</code></li>
	 * <li><code>"PHP.lang.Cloneable"</code></li>
	 * <li><code>"PHP.lang.Double"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.Error"</code></li>
	 * <li><code>"PHP.lang.Exception"</code></li>
	 * <li><code>"PHP.lang.Float"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.Integer"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.Long"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.Object"</code></li>
	 * <li><code>"PHP.lang.RuntimeException"</code></li>
	 * <li><code>"PHP.lang.Short"</code> (since 3.1)</li>
	 * <li><code>"PHP.lang.String"</code></li>
	 * <li><code>"PHP.lang.StringBuffer"</code></li>
	 * <li><code>"PHP.lang.Throwable"</code></li>
	 * <li><code>"PHP.lang.Void"</code> (since 3.1)</li>
	 * <li><code>"PHP.io.Serializable"</code></li>
	 * </ul>
	 * </p>
	 *
	 * @param name the name of a well known type
	 * @return the corresponding type binding, or <code>null</code> if the
	 *   named type is not considered well known or if no binding can be found
	 *   for it
	 */
	public ITypeBinding resolveWellKnownType(String name) {
		if (name == null) {
			return null;
		}
		return getBindingResolver().resolveWellKnownType(name);
	}

	/**
	 * Sets the binding resolver for this AST.
	 *
	 * @param resolver the new binding resolver for this AST
	 */
	void setBindingResolver(BindingResolver resolver) {
		if (resolver == null) {
			throw new IllegalArgumentException();
		}
		this.resolver = resolver;
	}

	/**
     * Checks that this AST operation is not used when
     * building level JLS2 ASTs.

     * @exception UnsupportedOperationException
	 * @since 3.0
     */
	void unsupportedIn2() {
	  if (this.apiLevel == PHPVersion.PHP4) {
	  	throw new UnsupportedOperationException("Operation not supported in JLS2 AST"); //$NON-NLS-1$
	  }
	}

	/**
     * Checks that this AST operation is only used when
     * building level JLS2 ASTs.

     * @exception UnsupportedOperationException
	 * @since 3.0
     */
	void supportedOnlyIn2() {
	  if (this.apiLevel != PHPVersion.PHP4) {
	  	throw new UnsupportedOperationException("Operation not supported in JLS2 AST"); //$NON-NLS-1$
	  }
	}

	/**
	 * new Class[] {AST.class}
	 * @since 3.0
	 */
	private static final Class[] AST_CLASS = new Class[] {AST.class};

	/**
	 * new Object[] {this}
	 * @since 3.0
	 */
	private final Object[] THIS_AST= new Object[] {this};

	/*
	 * Must not collide with a value for ICompilationUnit constants
	 */
	static final int RESOLVED_BINDINGS = 0x80000000;

	/**
	 * Tag bit value. This represents internal state of the tree.
	 */
	private int bits;

	/**
	 * Creates an unparented node of the given node class
	 * (non-abstract subclass of {@link ASTNode}). 
	 * 
	 * @param nodeClass AST node class
	 * @return a new unparented node owned by this AST
	 * @exception IllegalArgumentException if <code>nodeClass</code> is
	 * <code>null</code> or is not a concrete node type class
	 * @since 3.0
	 */
	public ASTNode createInstance(Class nodeClass) {
		if (nodeClass == null) {
			throw new IllegalArgumentException();
		}
		try {
			// invoke constructor with signature Foo(AST)
			Constructor c = nodeClass.getDeclaredConstructor(AST_CLASS);
			Object result = c.newInstance(this.THIS_AST);
			return (ASTNode) result;
		} catch (NoSuchMethodException e) {
			// all AST node classes have a Foo(AST) constructor
			// therefore nodeClass is not legit
			throw new IllegalArgumentException();
		} catch (InstantiationException e) {
			// all concrete AST node classes can be instantiated
			// therefore nodeClass is not legit
			throw new IllegalArgumentException();
		} catch (IllegalAccessException e) {
			// all AST node classes have an accessible Foo(AST) constructor
			// therefore nodeClass is not legit
			throw new IllegalArgumentException();
		} catch (InvocationTargetException e) {
			// concrete AST node classes do not die in the constructor
			// therefore nodeClass is not legit
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Creates an unparented node of the given node type.
	 * This convenience method is equivalent to:
	 * <pre>
	 * createInstance(ASTNode.nodeClassForType(nodeType))
	 * </pre>
	 *
	 * @param nodeType AST node type, one of the node type
	 * constants declared on {@link ASTNode}
	 * @return a new unparented node owned by this AST
	 * @exception IllegalArgumentException if <code>nodeType</code> is
	 * not a legal AST node type
	 * @since 3.0
	 */
	public ASTNode createInstance(int nodeType) {
		// nodeClassForType throws IllegalArgumentException if nodeType is bogus
		Class nodeClass = ASTNode.nodeClassForType(nodeType);
		return createInstance(nodeClass);
	}

	//=============================== NAMES ===========================
	/**
	 * Creates and returns a new unparented simple name node for the given
	 * identifier. The identifier should be a legal PHP identifier.
	 *
	 * @param identifier the identifier
	 * @return a new unparented simple name node
	 * @exception IllegalArgumentException if the identifier is invalid
	 */
	public Identifier newIdentifier(String identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException();
		}
		Identifier result = new Identifier(this);
		result.setName(identifier);
		return result;
	}

	//=============================== TYPES ===========================
	/**
	 * Creates an unparented block node owned by this AST, for an empty list
	 * of statements.
	 *
	 * @return a new unparented, empty block node
	 */
	public Block newBlock() {
		return new Block(this);
	}
	
	
	/**
	 * Enables the recording of changes to the given compilation
	 * unit and its descendents. The compilation unit must have
	 * been created by <code>ASTParser</code> and still be in
	 * its original state. Once recording is on,
	 * arbitrary changes to the subtree rooted at the compilation
	 * unit are recorded internally. Once the modification has
	 * been completed, call <code>rewrite</code> to get an object
	 * representing the corresponding edits to the original
	 * source code string.
	 *
	 * @exception IllegalArgumentException if this compilation unit is
	 * marked as unmodifiable, or if this compilation unit has already
	 * been tampered with, or if recording has already been enabled,
	 * or if <code>root</code> is not owned by this AST
	 * @see CompilationUnit#recordModifications()
	 * @since 3.0
	 */
	void recordModifications(Program root) {
		if(this.modificationCount != this.originalModificationCount) {
			throw new IllegalArgumentException("AST is already modified"); //$NON-NLS-1$
		} else if(this.rewriter  != null) {
			throw new IllegalArgumentException("AST modifications are already recorded"); //$NON-NLS-1$
		} else if((root.getFlags() & ASTNode.PROTECT) != 0) {
			throw new IllegalArgumentException("Root node is unmodifiable"); //$NON-NLS-1$
		} else if(root.getAST() != this) {
			throw new IllegalArgumentException("Root node is not owned by this ast"); //$NON-NLS-1$
		}

		this.rewriter = new InternalASTRewrite(root);
		this.setEventHandler(this.rewriter);
	}

	/**
	 * Converts all modifications recorded into an object
	 * representing the corresponding text edits to the
	 * given document containing the original source
	 * code for the compilation unit that gave rise to
	 * this AST.
	 *
	 * @param document original document containing source code
	 * for the compilation unit
	 * @param options the table of formatter options
	 * (key type: <code>String</code>; value type: <code>String</code>);
	 * or <code>null</code> to use the standard global options
	 * {@link PHPCore#getOptions() PHPCore.getOptions()}.
	 * @return text edit object describing the changes to the
	 * document corresponding to the recorded AST modifications
	 * @exception IllegalArgumentException if the document passed is
	 * <code>null</code> or does not correspond to this AST
	 * @exception IllegalStateException if <code>recordModifications</code>
	 * was not called to enable recording
	 * @see CompilationUnit#rewrite(IDocument, Map)
	 * @since 3.0
	 */
	TextEdit rewrite(IDocument document, Map options) {
		if (document == null) {
			throw new IllegalArgumentException();
		}
		if (this.rewriter  == null) {
			throw new IllegalStateException("Modifications record is not enabled"); //$NON-NLS-1$
		}
		return this.rewriter.rewriteAST(document, options);
	}

	/**
	 * Returns true if the ast tree was created with bindings, false otherwise
	 *
	 * @return true if the ast tree was created with bindings, false otherwise
	 * @since 3.3
	 */
	public boolean hasResolvedBindings() {
		return (this.bits & RESOLVED_BINDINGS) != 0;
	}

	/**
	 * Returns true if the ast tree was created with statements recovery, false otherwise
	 *
	 * @return true if the ast tree was created with statements recovery, false otherwise
	 * @since 3.3
	 */
/*	public boolean hasStatementsRecovery() {
		return (this.bits & ICompilationUnit.ENABLE_STATEMENTS_RECOVERY) != 0;
	}
*/
	/**
	 * Returns true if the ast tree was created with bindings recovery, false otherwise
	 *
	 * @return true if the ast tree was created with bindings recovery, false otherwise
	 * @since 3.3
	 */
/*	public boolean hasBindingsRecovery() {
		return (this.bits & ICompilationUnit.ENABLE_BINDINGS_RECOVERY) != 0;
	}
*/
	void setFlag(int newValue) {
		this.bits |= newValue;
	}
	
}
