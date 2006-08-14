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

import java.io.IOException;

public class InvalidIniFormatException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Példány képzés adott üzenettel.
	 *
	 * Ez a konstruktor rendszerint új exception generálására használatos, amikor is
	 * valamely feltétel ellenõrzése hibát jelez.
	 * @param message hiba szöveges megnevezése
	 */
	public InvalidIniFormatException(final String message) {
		super(message);
	}

	/**
	 * Példány képzés adott kiváltó okkal.
	 *
	 * Ez a konstruktor minden további szöveges magyarázat nélkül egy exception tovább adására
	 * szolgál.
	 * @param cause a hibát kiváltó exception
	 */
	public InvalidIniFormatException(final Throwable cause) {
		super();
		initCause(cause);
	}
}
