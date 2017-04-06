/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.wst.server.core.IServer;

/**
 * Thread used to ping server to test when it is started.
 */
public class PingThread {
	// delay before pinging starts
	private static final int PING_DELAY = 2000;

	// delay between pings
	private static final int PING_INTERVAL = 250;

	// maximum number of pings before giving up
	private int maxPings;

	private boolean stop = false;
	private String url;
	private IServer server;
	private PHPServerBehaviour behaviour;

	/**
	 * Create a new PingThread.
	 * 
	 * @param server
	 * @param url
	 * @param maxPings
	 *            the maximum number of times to try pinging, or -1 to continue
	 *            forever
	 * @param behaviour
	 */
	public PingThread(IServer server, String url, int maxPings, PHPServerBehaviour behaviour) {
		super();
		this.server = server;
		this.url = url;
		this.maxPings = maxPings;
		this.behaviour = behaviour;
		Thread t = new Thread("PHP Server Ping Thread") { //$NON-NLS-1$
			@Override
			public void run() {
				ping();
			}
		};
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Ping the server until it is started. Then set the server state to
	 * STATE_STARTED.
	 */
	protected void ping() {
		int count = 0;
		try {
			Thread.sleep(PING_DELAY);
		} catch (Exception e) {
			// ignore
		}
		while (!stop) {
			try {
				if (count == maxPings) {
					try {
						server.stop(false);
					} catch (Exception e) {
						Trace.trace(Trace.FINEST, "Ping: could not stop server"); //$NON-NLS-1$
					}
					stop = true;
					break;
				}
				count++;

				Trace.trace(Trace.FINEST, "Ping: pinging " + count); //$NON-NLS-1$
				URL pingUrl = new URL(url);
				URLConnection conn = pingUrl.openConnection();
				((HttpURLConnection) conn).setInstanceFollowRedirects(false);
				((HttpURLConnection) conn).getResponseCode();

				// ping worked - server is up
				if (!stop) {
					Trace.trace(Trace.FINEST, "Ping: success"); //$NON-NLS-1$
					Thread.sleep(200);
					behaviour.setServerStarted();
				}
				stop = true;
			} catch (FileNotFoundException fe) {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					// ignore
				}
				behaviour.setServerStarted();
				stop = true;
			} catch (Exception e) {
				Trace.trace(Trace.FINEST, "Ping: failed"); //$NON-NLS-1$
				// pinging failed
				if (!stop) {
					try {
						Thread.sleep(PING_INTERVAL);
					} catch (InterruptedException e2) {
						// ignore
					}
				}
			}
		}
	}

	/**
	 * Tell the pinging to stop.
	 */
	public void stop() {
		Trace.trace(Trace.FINEST, "Ping: stopping"); //$NON-NLS-1$
		stop = true;
	}
}
