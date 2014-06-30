package bacci.giovanni.tarseeker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class FilterCatcher extends TarEntryCatcher {

	/**
	 * String to find
	 */
	private String toFind = null;

	/**
	 * Constructor
	 * 
	 * @param catcher
	 *            decorator pattern object
	 * @param toFind
	 *            string to find in the name of the tar entries
	 * @param isExtension
	 *            if the string to find is a file extension
	 */
	public FilterCatcher(TarEntryCatcher catcher, String toFind,
			boolean isExtension) {
		super(catcher);
		this.setFind(toFind, isExtension);
	}

	/**
	 * Constructor
	 * 
	 * @param toFind
	 *            string to find in the name of the tar entries
	 * @param isExtension
	 *            if the string to find is a file extension
	 */
	public FilterCatcher(String toFind, boolean isExtension) {
		super();
		this.setFind(toFind, isExtension);
	}

	/**
	 * Set this object filter as a regex
	 * 
	 * @param toFind
	 *            the string to find
	 * @param isExtension
	 *            if the string is a file extension
	 */
	private void setFind(String toFind, boolean isExtension) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("^.*");
		if (isExtension) {
			if (toFind.charAt(0) != '.') {
				buffer.append("\\.");
			}
			buffer.append(toFind);
			buffer.append("$");
		} else {
			buffer.append(toFind);
			buffer.append(".*$");
		}
		this.toFind = buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * bacci.giovanni.targzseeker.TarEntryCatcher#catchEntry(org.apache.commons
	 * .compress.archivers.tar.TarArchiveEntry,
	 * org.apache.commons.compress.archivers.tar.TarArchiveInputStream)
	 */
	@Override
	public void catchEntry(TarArchiveEntry entry, TarArchiveInputStream tar) {
		Pattern p = Pattern.compile(toFind);
		Matcher m = p.matcher(entry.getName());
		if (m.find()) {
			super.catchEntry(entry, tar);
		}
	}

}
