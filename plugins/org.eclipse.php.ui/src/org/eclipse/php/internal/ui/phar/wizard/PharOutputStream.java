package org.eclipse.php.internal.ui.phar.wizard;

import java.io.FilterOutputStream;
import java.io.OutputStream;

import org.eclipse.php.internal.core.phar.IOutputArchiveEntry;
import org.eclipse.php.internal.core.phar.IStub;

public class PharOutputStream extends FilterOutputStream {

	public PharOutputStream(OutputStream out, IStub manifest) {
		super(out);
		// TODO Auto-generated constructor stub
	}

	public PharOutputStream(OutputStream out) {
		super(out);
	}

	public void putNextEntry(IOutputArchiveEntry newEntry) {
		// TODO Auto-generated method stub

	}

}
