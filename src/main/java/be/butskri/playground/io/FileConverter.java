package be.butskri.playground.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileConverter {
	private static final File DIR = new File(".");
	private static final File utf8File = new File(DIR, "windows-encoding-output.txt");
	private static final File cp1252File = new File(DIR, "windows-encoding.txt");

	// private static final File inputFile = new File(DIR,
	// "utf-8-encoding.txt");
	// private static final File outputFile = new File(DIR,
	// "utf-8-encoding.txt");

	public static void main(String[] args) throws IOException {
		// convert(cp1252File, utf8File, "Cp1252", "UTF-8");

		showFile(utf8File, "UTF-8");
		showFile(utf8File, "Cp1252");
		showFile(utf8File, "ISO-8859-15");
		showFile(utf8File, null);
	}

	private static void showFile(File file, String encoding) throws IOException {
		System.out.println("reading " + file + " with " + encoding + " encoding");
		writeLines(FileUtils.readLines(file, encoding));
		System.out.println("");
	}

	private static void writeLines(List<String> readLines) {
		for (String line : readLines) {
			System.out.println(line);
		}
	}

	private static void convertUtf8ToCp1252Files(File dir, String... filenames) throws IOException {
		for (String filename : filenames) {
			convert(new File(dir, filename), new File(dir, filename + ".out"), "UTF-8", "Cp1252");
		}
	}

	private static void convert(File inputFile, File outputFile, String inputEncoding, String outputEncoding) throws IOException {
		List<String> input = FileUtils.readLines(inputFile, inputEncoding);
		FileUtils.writeLines(outputFile, outputEncoding, input);
	}
}
