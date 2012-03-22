package be.butskri.playground.maven;

import java.io.File;

public class DependenciesDownloadedChecker {

	private static final File BASE_DIR = null;
	private static final File POM_FILE = null;

	public static void main(String[] args) {
		MavenRepository mavenRepository = new MavenRepository(BASE_DIR);
		Project project = XmlUtils.getInstance().loadProject(POM_FILE);
		for (Dependency dependency : project.getDependencies()) {
			if (!mavenRepository.contains(dependency)) {
				System.out.println(dependency + "not found");
			}
		}
	}
}
