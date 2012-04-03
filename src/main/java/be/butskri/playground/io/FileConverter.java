package be.butskri.playground.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileConverter {
	private static final String ENCODING_WINDOWS = "ISO-8859-15";
	private static final String ENCODING_UTF_8 = "UTF-8";
	private static final String ENCODING_CP1252 = "Cp1252";
	private static final File DIR = new File(".");
	private static final File utf8File = new File(DIR, "windows-encoding-output.txt");

	public static void main(String[] args) throws IOException {
		showFile(utf8File, ENCODING_UTF_8);
		showFile(utf8File, ENCODING_CP1252);
		showFile(utf8File, ENCODING_WINDOWS);
		showFile(utf8File, null);
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public static void convert(File inputFile, File outputFile, String inputEncoding, String outputEncoding) throws IOException {
		List<String> input = FileUtils.readLines(inputFile, inputEncoding);
		FileUtils.writeLines(outputFile, outputEncoding, input);
	}
	
	public static void convert(File inputFile, String inputEncoding, String outputEncoding) throws IOException {
		convert(inputFile, new File(inputFile.getParentFile(), inputFile.getName() + "." + outputEncoding), inputEncoding, outputEncoding);
	}
}
