/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.elements.PHPUnitElementManager;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public class PHPUnitConnectionListener implements Runnable, ILaunchesListener2 {
	protected ILaunch launch;
	protected int port = 0;
	protected ServerSocket serverSocket;

	protected Socket socket;
	private Thread listenThread;
	private boolean autoRefresh;
	private Gson gson = new GsonBuilder().create();

	public PHPUnitConnectionListener(final int port, final ILaunch launch, boolean autoRefresh) {
		this.port = port;
		this.launch = launch;
		this.autoRefresh = autoRefresh;
	}

	protected void handleReport(final Socket socket) {
		if (PHPUnitView.getDefault().isRunning()) {
			try {
				final PHPUnitMessageParser parser = PHPUnitMessageParser.getInstance();
				parser.setDebugTarget(PHPUnitView.getDefault().getLaunch().getDebugTarget());
				final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line;
				Map<?, ?> value = null;
				parser.setInProgress(true);
				while ((line = reader.readLine()) != null && PHPUnitView.getDefault().isRunning()
						&& parser.isInProgress()) {
					try {
						value = gson.fromJson(line, LinkedTreeMap.class);
					} catch (final Throwable e) {
						value = null;
					}
					parser.parseMessage((Map<?, ?>) value, PHPUnitView.getDefault().getViewer());
					if (autoRefresh) {
						PHPUnitView.getDefault().refresh(PHPUnitElementManager.getInstance().getRoot());
					}
				}

			} catch (final IOException e) {
				PHPUnitPlugin.log(e);
			}
		}
	}

	@Override
	public void launchesAdded(final ILaunch[] launches) {
	}

	@Override
	public void launchesChanged(final ILaunch[] launches) {
	}

	@Override
	public void launchesRemoved(final ILaunch[] launches) {
	}

	@Override
	public void launchesTerminated(final ILaunch[] launches) {
		for (int i = 0; i < launches.length; ++i)
			if (launches[i] == launch)
				if (launches[i].isTerminated()) {
					if (!PHPUnitMessageParser.getInstance().isInProgress()) {
						PHPUnitView.getDefault().stopRunning(true);
					}
					DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(this);
				}
	}

	@Override
	public void run() {
		final ILaunch launch = PHPUnitView.getDefault().getLaunch();
		int tries = 3;
		String message = PHPUnitMessages.PHPUnitView_Run_Error;
		do
			try {
				serverSocket = new ServerSocket(port, 1);
				serverSocket.setSoTimeout(5000);
				serverSocket.setReuseAddress(true);
				socket = serverSocket.accept();
				handleReport(socket);
			} catch (final IOException e) {
				tries--;
				if (tries == 0) {
					PHPUnitPlugin.log(e);
					message = NLS.bind(PHPUnitMessages.PHPUnitView_Run_SocketError, port, e.getMessage());
				}
			} finally {
				shutdown(false);
			}
		while ((tries > 0 && socket == null) || (!launch.isTerminated() && "debug".equals(launch.getLaunchMode()))); //$NON-NLS-1$
		PHPUnitView.getDefault().stop(PHPUnitElementManager.getInstance().getRoot(), message);
	}

	public void shutdown(boolean terminateLaunch) {
		try {
			if (socket != null && !socket.isClosed())
				socket.close();
		} catch (final Exception e) {
		}
		try {
			if (serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		} catch (final IOException e) {
		}
		if (terminateLaunch && launch != null && !launch.isTerminated())
			try {
				launch.terminate();
			} catch (final DebugException e) {
			}

		PHPUnitMessageParser.getInstance().setInProgress(false);
	}

	public void start() {
		listenThread = new Thread(this, "PHPUnit PHPUnitConnectionListener"); //$NON-NLS-1$
		listenThread.start();
	}
}