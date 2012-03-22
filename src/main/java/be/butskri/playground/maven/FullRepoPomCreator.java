package be.butskri.playground.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FullRepoPomCreator {

	private static final File REPOSITORY_DIR = new File("C:/Temp/repo");
	private static final File POM_FILE = new File("c:/temp/pom.xml");

	public static void main(String[] args) {
		FullRepoPomCreator pomCreator = new FullRepoPomCreator(REPOSITORY_DIR);
		pomCreator.exportTo(POM_FILE);
	}

	private final File repositoryDir;

	public FullRepoPomCreator(File repositoryDir) {
		this.repositoryDir = repositoryDir;
	}

	private void exportTo(File pomFile) {
		Project project = buildProject(new MavenRepository(repositoryDir));
		XmlUtils.getInstance().writeToFile(pomFile, project);
	}

	public Project buildProject(MavenRepository mavenRepository) {
		Project result = new Project();
		result.setModelVersion("4.0.0");
		result.setArtifactId("be.butskri");
		result.setGroupId("butskri-all");
		result.setPackaging("jar");
		result.setVersion("version");
		result.setDependencies(getAllDependencies(mavenRepository));
		return result;
	}

	private List<Dependency> getAllDependencies(MavenRepository mavenRepository) {
		List<Dependency> result = new ArrayList<Dependency>();
		for (Project project : mavenRepository.getAllePoms()) {
			result.add(Dependency.from(project));
		}
		return result;
	}

}
