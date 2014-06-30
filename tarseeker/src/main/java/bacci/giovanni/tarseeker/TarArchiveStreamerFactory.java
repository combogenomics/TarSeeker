package bacci.giovanni.tarseeker;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2Utils;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZUtils;

public class TarArchiveStreamerFactory {

	/**
	 * Tar extensions
	 */
	private static final String[] TAREXT = { "tar" };

	/**
	 * This method constructs the correct {@link TarArchiveInputStream} object
	 * given the name of a file/entry and an {@link InputStream} object pointing
	 * to it
	 * @param is the input stream
	 * @param entryName the name of the file/entry
	 * @return a {@link TarArchiveInputStream}
	 * @throws IOException if an I/O error occurs
	 */
	public static TarArchiveInputStream getStream(InputStream is,
			String entryName) throws IOException {
		if (GzipUtils.isCompressedFilename(entryName)) {
			return new TarArchiveInputStream(new GzipCompressorInputStream(is));
		}
		if (BZip2Utils.isCompressedFilename(entryName)) {
			return new TarArchiveInputStream(
					new BZip2CompressorInputStream(is));
		}
		if (XZUtils.isCompressedFilename(entryName)) {
			return new TarArchiveInputStream(new XZCompressorInputStream(is));
		}
		return null;
	}

	/**
	 * Checks is the given file/entry name is an Archive
	 * @param entryName the file/entry name
	 * @return
	 */
	public static boolean checkArchive(String entryName) {
		if (GzipUtils.isCompressedFilename(entryName)) {
			return true;
		}
		if (BZip2Utils.isCompressedFilename(entryName)) {
			return true;
		}
		if (XZUtils.isCompressedFilename(entryName)) {
			return true;
		}
		for (int i = 0; i < TAREXT.length; i++) {
			if (entryName.endsWith(TAREXT[i]))
				return true;
		}
		return false;
	}
}
