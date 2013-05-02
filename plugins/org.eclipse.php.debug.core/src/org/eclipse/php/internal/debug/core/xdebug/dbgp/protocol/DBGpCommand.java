/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;

public class DBGpCommand {

	private static final String id = " -i "; //$NON-NLS-1$

	// -------------------------------------------------------------------------
	// status and feature management commands
	// -------------------------------------------------------------------------
	public static final String status = "status"; //$NON-NLS-1$
	public static final String featureGet = "feature_get"; //$NON-NLS-1$
	public static final String featureSet = "feature_set"; //$NON-NLS-1$

	// -------------------------------------------------------------------------
	// execution commands "cmd -i id"
	// -------------------------------------------------------------------------
	public static final String run = "run"; //$NON-NLS-1$
	public static final String stepInto = "step_into"; //$NON-NLS-1$
	public static final String stepOver = "step_over"; //$NON-NLS-1$
	public static final String StepOut = "step_out"; //$NON-NLS-1$
	public static final String stop = "stop"; //$NON-NLS-1$
	public static final String detach = "detach"; //$NON-NLS-1$

	// -------------------------------------------------------------------------
	// breakpoint cmds
	// -------------------------------------------------------------------------
	// breakpoint_set -i id [arguments] -- base64(expression)
	// arguments are:
	// -t type: line/call/return/conditional/exception/watch
	// -s state: enabled/disabled
	// -f filename
	// -n lineno
	// -m function
	// -x exception
	// -h hit_value
	// -o hit_condidtion
	// -r 0|1
	// expression in php for conditional breakpoints
	public static final String breakPointSet = "breakpoint_set"; //$NON-NLS-1$

	// breakpoint_get -i id -d bp_id (bp_id = breakpoint id);
	public static final String breakPointGet = "breakpoint_get"; //$NON-NLS-1$

	// breakpoint_update -i id -d bp_id [arguments]
	// -s
	// -n
	// -h
	// -o
	public static final String breakPointUpdate = "breakpoint_update"; //$NON-NLS-1$

	// breakpoint_remove -i id -d bp_id
	public static final String breakPointRemove = "breakpoint_remove"; //$NON-NLS-1$

	// breakpoint_list -i id
	public static final String breakPointList = "breakpoint_list"; //$NON-NLS-1$

	// -------------------------------------------------------------------------
	// stack commands
	// -------------------------------------------------------------------------
	// stack-depth -i id
	public static final String stackDepth = "stack-depth"; //$NON-NLS-1$

	// stack_get -i id -d depth (-d is optional)
	public static final String stackGet = "stack_get"; //$NON-NLS-1$

	// -------------------------------------------------------------------------
	// variable/streams management commands
	// -------------------------------------------------------------------------
	// property_set -i id -n property_long_name -d {NUM} -l data_length --
	// {DATA}
	public static final String propSet = "property_set"; //$NON-NLS-1$

	// property_get -i id -n property_long_name
	public static final String propGet = "property_get"; //$NON-NLS-1$

	// property_value -i id -n property_long_name
	public static final String propValue = "property_value"; //$NON-NLS-1$

	// context_get -i id -d depth
	public static final String contextGet = "context_get"; //$NON-NLS-1$

	// eval -i id -- {DATA}
	public static final String eval = "eval"; //$NON-NLS-1$

	// stdout/stderr -i id -c 0|1|2
	public static final String stdout = "stdout"; //$NON-NLS-1$
	public static final String stderr = "stderr"; //$NON-NLS-1$

	// break
	public static final String suspend = "break"; //$NON-NLS-1$

	Socket socket;
	OutputStreamWriter outStream;

	private static int trId = 0;
	private static Object idMonitor = new Object();

	private int lastIdSent;
	private String lastCmdSent;

	public String getLastCmdSent() {
		return lastCmdSent;
	}

	public DBGpCommand(Socket socket) {
		this.socket = socket;
		/*
		 * try { outStream = new OutputStreamWriter(socket.getOutputStream(),
		 * ENCODING); } catch (UnsupportedEncodingException e) {
		 * DBGpLogger.logException(null, this, e); // do nothing until we
		 * actually try to send a command } catch (IOException e) {
		 * DBGpLogger.logException(null, this, e); // do nothing until we
		 * actually try to send a command }
		 */
	}

	public int send(String command, String args, int cmdId, String encoding)
			throws IOException {
		try {
			return writeToStream(command, args, cmdId, encoding);
		} catch (IOException e) {
			DBGpLogger.logException(null, this, e);
			throw e;
		}
	}

	public static int getNextId() {
		synchronized (idMonitor) {
			trId++;
			if (trId < 0) {
				trId = 1;
			}
		}
		return trId;
	}

	private int writeToStream(String command, String args, int cmdId,
			String encoding) throws IOException {
		String fullCmd = command + id + cmdId;
		if (args != null) {
			fullCmd = fullCmd + " " + args; //$NON-NLS-1$
		}
		if (DBGpLogger.debugCmd()) {
			DBGpLogger.debug("cmd: " + fullCmd); //$NON-NLS-1$
		}

		synchronized (socket) {
			OutputStream os = socket.getOutputStream();
			// Bug: 226860
			// Want to avoid 2 writes as some tcpip implementations
			// may delay waiting for a response from the 1st write
			// we could set the tcpNoDelay option on the socket
			// but given this is the only write we can accept the
			// delay for a response to reduce bandwidth.
			//
			// implemented this way as we only want to send
			// a single \0 byte to terminate the command whereas
			// an encoding such as utf-16 would result in 2 bytes
			byte[] cmdBytes = fullCmd.getBytes(encoding);
			byte[] cmdWithTerm = new byte[cmdBytes.length + 1];
			System.arraycopy(cmdBytes, 0, cmdWithTerm, 0, cmdBytes.length);
			os.write(cmdWithTerm);
			os.flush();
			lastIdSent = cmdId;
			lastCmdSent = fullCmd;

			/*
			 * System.out.print("streamed:");
			 * System.out.write(fullCmd.getBytes(encoding));
			 * System.out.write(0); System.out.flush(); System.out.println();
			 * 
			 * outStream.write(command); outStream.write(id);
			 * outStream.write(Integer.toString(cmdId)); if (args != null) {
			 * outStream.write(" " + args); } outStream.write(0);
			 * outStream.flush();
			 */
		}
		return cmdId;
	}

	public int getLastIdSent() {
		int id;
		// we need to synchronise on the socket to ensure that if we get called
		// on a different thread, lastIdSent is at the latest value which occurs
		// after the last part of the write and the update takes place and is
		// controlled by the syncing of the socket.
		synchronized (socket) {
			id = lastIdSent;
		}
		return id;
	}

}
