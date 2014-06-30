package bacci.giovanni.tarseeker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class TarSeeker {

	/**
	 * Command line options (required)
	 */
	private final static String[] REQUIRED = { "-i", "-o" };

	/**
	 * Command line options (optionals)
	 */
	private final static String[] OPTIONALS = { "-h", "-e", "-f" };

	/**
	 * Help String
	 */
	private final static String HELP = String
			.format("USAGE:%n"
					+ "java -jar TarGzSeeker.jar [%1$s] [%2$s] [%3$s extension] [%4$s input_file] [%5$s output_file]%n"
					+ "%n"
					+ "OPTIONS:%n"
					+ "   %1$s\tprint this message (optional)%n"
					+ "   %2$s\tif the filter string is a file extension (optional)%n"
					+ "   %3$s\tfilter string. File/s with this string in their %n"
					+ "         name will be written in the ouput path (optional)%n"
					+ "   %4$s\tpath to the input file (required)%n"
					+ "   %5$s\tpath to the output file (required)%n",
					(Object[]) concatenate(OPTIONALS, REQUIRED));

	/**
	 * Input path
	 */
	private static String input = null;

	/**
	 * Output path
	 */
	private static String output = null;

	/**
	 * Entry should be filtered
	 */
	private static boolean filtered = false;

	/**
	 * String to find
	 */
	private static String toFind = null;

	/**
	 * Is extension?
	 */
	private static boolean isExtension = false;

	public static void main(String[] args) {
		optional(args);
		required(args);

		TarEntryCatcher filter = null;
		TarEntryCatcher catcher = null;

		OutputStream out = null;
		InputStream is = null;

		TarStreamer streamer = new TarStreamer();

		try {
			out = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot generate output file: " + output);
			System.exit(-1);
		}

		try {
			is = new FileInputStream(input);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot read input file: " + input);
			System.exit(-1);
		}

		catcher = new SimpleCatcher(out);
		if (filtered) {
			filter = new FilterCatcher(catcher, toFind, isExtension);
			streamer.setCatcher(filter);
		} else {
			streamer.setCatcher(catcher);
		}

		try {
			TarArchiveInputStream tar = TarArchiveStreamerFactory.getStream(is,
					input);
			if (tar != null) {
				streamer.streamEntries(tar);
			} else {
				System.err
						.println("Cannot generate a seeker for the file provided: "
								+ input);
				System.exit(-1);
			}
		} catch (IOException e) {
			System.err
					.println("I/O error occurs while seeking inside the tar file");
		}

	}

	private static void optional(String[] args) {
		List<String> argsList = Arrays.asList(args);
		if (argsList.contains(OPTIONALS[0])) {
			System.out.println(HELP);
			System.exit(0);
		}
		if (argsList.contains(OPTIONALS[1])) {
			isExtension = true;
		}
		int toFind_index = argsList.indexOf(OPTIONALS[2]);
		if ((toFind_index >= 0) && (args.length > toFind_index)) {
			toFind_index++;
			toFind = args[toFind_index];
			filtered = true;
		}
	}

	private static void required(String[] args) {
		List<String> argsList = Arrays.asList(args);
		int in_index = argsList.indexOf(REQUIRED[0]);
		int out_index = argsList.indexOf(REQUIRED[1]);

		if ((in_index >= 0) && (args.length > in_index)) {
			in_index++;
			input = args[in_index];
		} else {
			System.err.println("Error: no input path detected");
			System.err.println(HELP);
			System.exit(-1);
		}

		if ((out_index >= 0) && (args.length > out_index)) {
			out_index++;
			output = args[out_index];
		} else {
			System.err.println("Error: no output path detected");
			System.err.println(HELP);
			System.exit(-1);
		}
	}

	public static String[] concatenate(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

}
