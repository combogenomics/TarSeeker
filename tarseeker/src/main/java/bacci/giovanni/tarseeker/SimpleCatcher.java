package bacci.giovanni.tarseeker;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class SimpleCatcher extends TarEntryCatcher {
	/**
	 * Output writer
	 */
	private OutputStream writer = null;

	public SimpleCatcher(TarEntryCatcher catcher, OutputStream writer) {
		super(catcher);
		this.writer = writer;
	}
	
	public SimpleCatcher(OutputStream writer) {
		super();
		this.writer = writer;
	}

	public void catchEntry(TarArchiveEntry entry, TarArchiveInputStream tar) {
		super.catchEntry(entry, tar);
		int read = 0;
		int bufferLength = 1024;
		byte[] content = new byte[bufferLength];
		try {
			while ((read = tar.read(content)) > 0) {
				if (read < bufferLength) {
					content = Arrays.copyOf(content, read);
				}
				writer.write(content);
			}
		} catch (IOException e) {
			System.err.println("Error: cannot write output");
			System.exit(-1);
		}
	}
}
