package bacci.giovanni.tarseeker;

import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class TarStreamer {

	/**
	 * {@link TarEntryCatcher}. Default to <code>null</code>
	 */
	private TarEntryCatcher catcher = null;

	/**
	 * Recursive method. This method stream over a series of tar entries calling
	 * the
	 * {@link TarEntryCatcher#catchEntry(TarArchiveEntry, TarArchiveInputStream)}
	 * method each time it finds a new tar entry. If one entry is an Archive
	 * this method will seek inside the archive calling itself recursively.
	 * 
	 * @param tar
	 *            the {@link TarArchiveInputStream}
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void streamEntries(TarArchiveInputStream tar) throws IOException {
		TarArchiveEntry entry = null;
		while ((entry = tar.getNextTarEntry()) != null) {
			String name = entry.getName();
			if (TarArchiveStreamerFactory.checkArchive(name)) {
				TarArchiveInputStream tarMod = TarArchiveStreamerFactory
						.getStream(tar, name);
				if (tarMod != null) {
					streamEntries(tarMod);
				} else {
					System.err.println("Warning entry name not recognised: "
							+ name);
				}
			} else {
				if (catcher != null) {
					catcher.catchEntry(entry, tar);
				}
			}
		}
	}

	/**
	 * @param catcher
	 *            the catcher to set
	 */
	public void setCatcher(TarEntryCatcher catcher) {
		this.catcher = catcher;
	}

}
