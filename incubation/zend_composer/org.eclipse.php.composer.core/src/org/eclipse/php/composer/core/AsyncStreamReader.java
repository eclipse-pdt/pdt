/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
class AsyncStreamReader extends Thread {

	protected ILogDevice logDevice;
	private StringBuffer buffer;
	private InputStream inputStream;
	private boolean stop;

	public AsyncStreamReader(InputStream inputStream, StringBuffer buffer, ILogDevice logDevice) {
		this.inputStream = inputStream;
		this.buffer = buffer;
		this.logDevice = logDevice;
	}

	public String getBuffer() {
		return buffer.toString();
	}

	public void run() {
		try {
			readCommandOutput();
		} catch (Exception e) {
			ComposerCorePlugin.logError(e);
		}
	}

	public void stopReading() {
		stop = true;
	}

	protected void printToDisplayDevice(String line) {
		if (logDevice != null) {
			logDevice.log(line);
		}
	}

	private void readCommandOutput() throws IOException {
		BufferedReader out = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		int read = 0;
		char[] charBuff = new char[512];
		try {
			while ((stop == false) && ((read = out.read(charBuff)) != -1)) {
				line = new String(charBuff, 0, read);
				buffer.append(line);
				printToDisplayDevice(line);
			}
		} finally {
			out.close();
		}
	}

}