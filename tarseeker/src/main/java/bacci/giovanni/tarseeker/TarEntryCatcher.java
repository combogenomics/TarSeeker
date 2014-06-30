package bacci.giovanni.tarseeker;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

/**
 * Decorator for entry catcher
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */
public abstract class TarEntryCatcher {

	private TarEntryCatcher catcher = null;

	public TarEntryCatcher(TarEntryCatcher catcher) {
		this.catcher = catcher;
	}
	
	public TarEntryCatcher() {
	}

	/**
	 * Catches a {@link TarArchiveEntry} and the {@link TarArchiveInputStream}
	 * pointing to it
	 * 
	 * @param entry
	 *            the entry
	 * @param tar
	 *            the archive input stream
	 */
	public void catchEntry(TarArchiveEntry entry, TarArchiveInputStream tar) {
		if (catcher != null) {
			catcher.catchEntry(entry, tar);
		}
	}

}
