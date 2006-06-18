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
package org.eclipse.php.core.phpModel.parser;

import java.io.Reader;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.eclipse.php.core.Logger;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This task handles the schedulaing of the model parser queue. <br>
 * The task can be suspended, resumed and stopped, depending on the que state.<br> 
 * Refer to the following link for more information about timing a thread: 
 * {@link http://java.sun.com/docs/books/tutorial/essential/threads/synchronization.html}
 * In addition the que is handled in a Mutual exclusion way, so no double reference is possible for the que  
 *    
 * @author Roy Ganor, 2006
 */
public class PhpParserSchedulerTask implements Runnable {

	// variable needed for the stop operation 
	private volatile boolean threadAlive = true;
	
	// a limit size for the parser stack 
	private static final int BUFFER_MAX_SIZE = 100;
	
	// holds the stack of tasks
	private final LinkedList buffer = new LinkedList();

	// syncronizer for the buffer
	private volatile Object bufferSyncronizer = new Object();
	
	// this class is singleton - only one instance is allowed  
	protected static final PhpParserSchedulerTask instance = new PhpParserSchedulerTask();

	/**
	 * Private constructor, only one instance is given
	 */
	private PhpParserSchedulerTask() {
	}

	/**
	 * @return the instance 
	 */
	public static PhpParserSchedulerTask getInstance() {
		return instance;
	}

	/**
	 * Run the consumer operation 
	 */
	public void run() {

		while (threadAlive) {
			try {

				// do a single operation of parsing
				final ParserExecuter release = release();

				// if release == null something with our stack lock mechanism is wrong!
				assert release != null;

				// this operation sets the release state to be started 
				release.setAlive();
				
				// do the job of parsing with the given information
				final IWorkbench workbench = PlatformUI.getWorkbench();
				if (workbench != null)
					workbench.getDisplay().asyncExec(release);

				// waits until the parse action is done, no cross parsing is allowed
				release.join();

			} catch (InterruptedException e) {
				// thread was stoped or canceled...
				// just go out!
			}
		}
	}

	/**
	 * CONSUMER - Releases the first item in the stack 
	 * @throws InterruptedException 
	 * @throws InterruptedException 
	 */
	protected ParserExecuter release() throws InterruptedException {

		synchronized (bufferSyncronizer) {

			// pop a new parser task properties from stack
			while (buffer.size() == 0) {
				try {
					bufferSyncronizer.wait();
				} catch (InterruptedException e) {
					// process of releasing was canceled
					// ignore operation
				}
			}

			// get the next item
			final ParserExecuter item = (ParserExecuter) buffer.removeFirst();

			// notify that the stack is not full 
			bufferSyncronizer.notifyAll();

			return item;
			
		}

	}

	/**
	 * PRODUCER - Schedules a new operation for the parser stack
	 * @param phpParser
	 * @param client
	 * @param reader
	 * @param lastModified
	 */
	public synchronized void schedule(PHPParserManager parserManager, PhpParser phpParser, ParserClient client, String filename, Reader reader, Pattern[] tasksPatterns, long lastModified, boolean useAspTagsAsPhp) {

		synchronized (bufferSyncronizer) {
			
			// add it (saftly)
			// if the stack is full - wait() for an empty place 
			while (buffer.size() >= BUFFER_MAX_SIZE) {
				try {
					bufferSyncronizer.wait();
				} catch (InterruptedException e) {
					// process of scheduling was canceled
					// ignore operation
				}
			}

			// creates the new task properties
			final ParserExecuter parserProperties = new ParserExecuter(parserManager, phpParser, client, filename, reader, tasksPatterns, lastModified, useAspTagsAsPhp);

			// adds  the task to the head of the list
			buffer.addFirst(parserProperties);

			// now you can notify that the stack is not empty
			bufferSyncronizer.notifyAll();
			
		}
	}

	/**
	 * The task of parsing a php file 
	 */
	private static class ParserExecuter implements Runnable {

		private static final Object forJoin = new Object();
		private static boolean alive = false;		
		
		private final PHPParserManager parserManager;
		private PhpParser phpParser; // maybe we should re-create the parser
		private final ParserClient client;
		private final String filename;
		private final Reader reader;
		private final long lastModified;
		private final Pattern[] tasksPatterns;
		private final boolean useAspTagsAsPhp;

		public ParserExecuter(PHPParserManager parserManager, PhpParser phpParser, ParserClient client, String filename, Reader reader, Pattern[] tasksPatterns, long lastModified, boolean useAspTagsAsPhp) {
			this.parserManager = parserManager;
			this.phpParser = phpParser;
			this.client = client;
			this.filename = filename;
			this.reader = reader;
			this.tasksPatterns = tasksPatterns;
			this.lastModified = lastModified;
			this.useAspTagsAsPhp = useAspTagsAsPhp;
		}

		/**
		 * The parsing action - in a seperate (async) thread 
		 * @throws InterruptedException 
		 */
		public void run() {
			try {
				final CompletionLexer lexer = parserManager.createCompletionLexer(reader);
				lexer.setUseAspTagsAsPhp(useAspTagsAsPhp);
				lexer.setParserClient(client);
				lexer.setTasksPatterns(tasksPatterns);

				if (phpParser == null) {
					phpParser = parserManager.createPhpParser();
				}
				phpParser.setScanner(lexer);
				phpParser.setParserClient(client);

				client.startParsing(filename);
				phpParser.parse();

			} catch (Exception e) {
				Logger.logException(e);


			} finally {
				if (client != null && phpParser != null) {
					client.finishParsing(phpParser.getLength(), phpParser.getCurrentLine(), lastModified);
				}
				
				try {
					reader.close();
				} catch (Exception e) {
					Logger.logException(e);
				} finally {

					// notify for the join operation about the termination of the task
					synchronized (forJoin) {
						alive = false;
						forJoin.notifyAll();
					}
				}
			}
		}
		
		/**
		 * this implementation is the exactly the same as {@link Thread#join()} implementation
		 * The thread will {@link Thread#wait()} untill 
		 * {@link Object#notify()} or {@link Object#notifyAll()} will be invoked.
		 * @throws InterruptedException
		 */
		public final void join() throws InterruptedException {

			synchronized (forJoin) {

				// simple join() implementation - wait till notification
				while (alive) {
					forJoin.wait(0);
				}
			}
			
		}
		
		/**
		 * Sets this task state as started 
		 */
		public final void setAlive() {

			synchronized (forJoin) {

				// don't wake this task if it is already alive
				assert alive == false;

				// sets the alive variable 
				alive = true;
			}
			
		}
	}
}
