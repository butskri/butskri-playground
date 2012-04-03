package be.butskri.playground.io;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class FileUtils {

	public static String relativePath(File basePath, File file) {
		return relativePath("", basePath, file);
	}

	private static String relativePath(String buildPath, File basePath, File file) {
		if (isParent(basePath, file)) {
			return buildPath + calculateSimpleRelativePath("", basePath, file);
		}
		return relativePath("../" + buildPath, basePath.getParentFile(), file);
	}

	private static String calculateSimpleRelativePath(String buildPath, File basePath, File file) {
		if (basePath.equals(file)) {
			return StringUtils.removeEnd(buildPath, "/");
		}
		return calculateSimpleRelativePath(file.getName() + "/" + buildPath, basePath, file.getParentFile());
	}

	private static boolean isParent(File basePath, File file) {
		File parentFile = file.getParentFile();
		if (parentFile == null) {
			return false;
		} else if (basePath.equals(parentFile)) {
			return true;
		}
		return isParent(basePath, parentFile);
	}

	
}
