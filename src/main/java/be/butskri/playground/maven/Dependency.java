package be.butskri.playground.maven;

public class Dependency {

	private String groupId;
	private String artifactId;
	private String version;
	private String type;
	private String scope;
	private boolean optional;

	public static Dependency from(Project pom) {
		Dependency result = new Dependency();
		if (pom.getGroupId() != null) {
			result.setGroupId(pom.getGroupId());
		} else {
			result.setGroupId(pom.getParent().getGroupId());
		}
		result.setArtifactId(pom.getArtifactId());
		result.setType(pom.getPackaging());
		if (pom.getVersion() != null) {
			result.setVersion(pom.getVersion());
		} else {
			result.setVersion(pom.getParent().getVersion());
		}
		return result;
	}

	public Dependency() {
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getPomFileName() {
		return artifactId + "-" + version + ".pom";
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public boolean isOptional() {
		return optional;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(groupId);
		result.append("/");
		result.append(artifactId);
		result.append("-");
		result.append(version);
		return result.toString();
	}

}
