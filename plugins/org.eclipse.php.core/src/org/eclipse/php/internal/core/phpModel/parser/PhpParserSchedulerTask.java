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
package org.eclipse.php.internal.core.phpModel.parser;

import java.io.Reader;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * This task handles the scheduling of the model parser queue. <br>
 * The task can be suspended, resumed and stopped, depending on the que state.<br> 
 * Refer to the following link for more information about timing a thread: 
 * {@link http://java.sun.com/docs/books/tutorial/essential/threads/synchronization.html}
 * In addition the queue is handled in a Mutual exclusion way, so no double reference is possible for the que  
 *    
 * @author Roy Ganor, 2006
 */
public class PhpParserSchedulerTask implements Runnable {

	// variable needed for the stop operation 
	private volatile boolean threadAlive = true;

	// a limit size for the parser stack 
	private static final int BUFFER_MAX_SIZE = 10;

	// holds the stack of tasks
	private final LinkedList<ParserExecuter> buffer = new LinkedList<ParserExecuter>();

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
	 * Note: we want to reduce the number of parsing operations 
	 *       by removing all sequence tasks besides the last one 
	 * 
	 * inside the loop we have
	 * 1) released the next parsing operation
	 * 2) wait for 500 ms.
	 * 3) if there are new parsing operation requests - skip the current on
	 */
	public void run() {

		while (threadAlive) {
			try {

				// do a single operation of parsing
				final ParserExecuter release = release();

				// if release == null something with our stack lock mechanism is wrong!
				assert release != null;

				// do the job of parsing with the given information
				Thread.sleep(500);
				if (buffer.size() > 0) {
					final ParserExecuter top = buffer.getFirst();
					if (top.filename.equals(release.filename)) {
						continue;
					}
				}
				release.run();

			} catch (InterruptedException e) {
				// thread was stopped or canceled...
				// just go out!
			}
		}
	}

	/**
	 * CONSUMER - Releases the first item in the stack 
	 * @throws InterruptedException 
	 * @throws InterruptedException 
	 */
	protected synchronized ParserExecuter release() throws InterruptedException {

		// pop a new parser task properties from stack
		while (buffer.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// process of releasing was canceled
				// ignore operation
			}
		}

		// get the next item
		final ParserExecuter item = buffer.removeFirst();

		// notify that the stack is not full 
		notifyAll();

		return item;

	}

	/**
	 * PRODUCER - Schedules a new operation for the parser stack
	 * @param phpParser
	 * @param client
	 * @param reader
	 * @param lastModified
	 */
	public synchronized void schedule(PHPParserManager parserManager, PhpParser phpParser, ParserClient client, String filename, Reader reader, Pattern[] tasksPatterns, long lastModified, boolean useAspTagsAsPhp) {

		// check the top of the stack, if it is the file is already 
		// on stack just ignore the last one
		if (buffer.size() > 0) {
			final ParserExecuter top = buffer.getFirst();
			if (top.filename.equals(filename)) {
				buffer.removeFirst();
			}
		}

		// add it (safely)
		// if the stack is full - wait() for an empty place 
		while (buffer.size() >= BUFFER_MAX_SIZE) {
			try {
				wait();
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
		notifyAll();
	}
}
