/*
 * Copyright 2005 [ini4j] Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ini4j;

import java.io.*;
import java.util.prefs.*;

public class IniFile extends IniPreferences {
	private static final int MODE_RO = 1;
	private static final int MODE_WO = 1 << 1;

	//    public static int Mode {RO,WO,RW};

	private File _file;
	private int _mode;

	public IniFile(final File file) throws BackingStoreException {
		this(file, MODE_RO);
	}

	public IniFile(final File file, final int mode) throws BackingStoreException {
		super(new Ini());
		_file = file;
		_mode = mode;

		if (_mode == MODE_RO || _mode != MODE_WO && _file.exists())
			sync();
	}

	public void flush() throws BackingStoreException {
		if (_mode == MODE_RO)
			throw new BackingStoreException("read only instance"); //$NON-NLS-1$

		try {
			synchronized (lock) {
				getIni().store(new FileWriter(_file), _mode);
			}
		} catch (final Exception x) {
			throw new BackingStoreException(x);
		}
	}

	public File getFile() {
		return _file;
	}

	public int getMode() {
		return _mode;
	}

	public void sync() throws BackingStoreException {
		if (_mode == MODE_WO)
			throw new BackingStoreException("write only instance"); //$NON-NLS-1$

		try {
			synchronized (lock) {
				getIni().load(_file.toURL(), _mode);
			}
		} catch (final Exception x) {
			throw new BackingStoreException(x);
		}
	}
}
