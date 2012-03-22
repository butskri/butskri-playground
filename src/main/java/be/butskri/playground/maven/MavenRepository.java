package be.butskri.playground.maven;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class MavenRepository {

	private final File basedir;

	public MavenRepository(File basedir) {
		this.basedir = basedir;
	}

	public List<Project> getAllePoms() {
		List<Project> result = new ArrayList<Project>();
		for (File pomFile : getAllePomFiles(basedir)) {
			System.out.println("reading pom " + pomFile);
			result.add(XmlUtils.getInstance().loadProject(pomFile));
		}
		return result;
	}

	private Collection<File> getAllePomFiles(File dir) {
		Collection<File> result = new ArrayList<File>();
		result.addAll(pomFilesInDir(dir));
		for (File subdir : subdirs(dir)) {
			result.addAll(getAllePomFiles(subdir));
		}
		return result;
	}

	private File[] subdirs(File dir) {
		return dir.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
	}

	private Collection<? extends File> pomFilesInDir(File dir) {
		return Arrays.asList(dir.listFiles((FilenameFilter) new WildcardFileFilter("*.pom")));
	}

	public boolean contains(Dependency dependency) {
		File groupDir = new File(basedir, dependency.getGroupId().replaceAll("\\.", "/"));
		File artifactDir = new File(groupDir, dependency.getArtifactId());
		File versionDir = new File(artifactDir, dependency.getVersion());
		File pomFile = new File(versionDir, dependency.getPomFileName());
		return pomFile.exists();
	}

}
