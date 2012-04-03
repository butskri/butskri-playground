package be.butskri.playground.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import be.butskri.playground.io.FileUtils;

public class NewFullRepoPomCreator {

	private static final File BASE_DIR = new File("D:/Projects/Argenta-Okapi/sources");
	private static final File DEST_DIR = new File("D:/Projects/Argenta-Okapi/sources");

	public static void main(String[] args) {
		NewFullRepoPomCreator pomCreator = new NewFullRepoPomCreator(BASE_DIR, DEST_DIR);
		pomCreator.export();
	}

	private final File baseDir;
	private final File destDir;

	public NewFullRepoPomCreator(File baseDir,File destDir) {
		this.baseDir = baseDir;
		this.destDir = destDir;
	}

	private void export() {
		Project project = buildProject();
		XmlUtils.getInstance().writeToFile(pomFile(), project);
	}

	private File pomFile() {
		return new File(destDir, "pom.xml");
	}

	public Project buildProject() {
		Project result = new Project();
		result.setModelVersion("4.0.0");
		result.setArtifactId("be.butskri");
		result.setGroupId("butskri-all");
		result.setPackaging("jar");
		result.setVersion("version");
		result.setModules(getModules());
		return result;
	}

	private List<Module> getModules() {
		List<Module> result = new ArrayList<Module>();
		for (File pomFile: getAllPomFiles()) {
			result.add(getModule(pomFile));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Collection<File> getAllPomFiles() {
		Collection<File> result = org.apache.commons.io.FileUtils.listFiles(baseDir, FileFilterUtils.nameFileFilter("pom.xml"), TrueFileFilter.INSTANCE);
		result.remove(pomFile());
		return result;
	}

	private Module getModule(File pomFile) {
		String modulePath = FileUtils.relativePath(destDir, pomFile.getParentFile());
		System.out.println("found module: " + modulePath);
		return new Module(modulePath);
	}

}
