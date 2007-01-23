package org.eclipse.php.internal.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
	
    //	 Stream closers.
	// Use these until Java 5 will be supported. Then, it is best to move to Closeable interface.
	public static void closeStream(OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
		}
	}

	public static void closeStream(InputStream input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
		}
	}

}
