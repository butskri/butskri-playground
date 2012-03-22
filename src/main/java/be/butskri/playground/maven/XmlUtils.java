package be.butskri.playground.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XmlUtils {

	private XStream xstream;

	public static XmlUtils getInstance() {
		return new XmlUtils();
	}

	public XmlUtils() {
		xstream = new XStream() {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						// if (Project.class.equals(definedIn)) {
						return bevatField(definedIn, fieldName);
						// }
						// return super.shouldSerializeMember(definedIn,
						// fieldName);

					}

					private boolean bevatField(Class<?> clazz, String fieldName) {
						try {
							return clazz.getDeclaredField(fieldName) != null;
						} catch (SecurityException exception) {
							throw new RuntimeException(exception);
						} catch (NoSuchFieldException exception) {
							return false;
						}
					}
				};
			}
		};
		xstream.alias("project", Project.class);
		xstream.alias("dependency", Dependency.class);
	}

	public Project loadProject(File pomFile) {
		try {
			return (Project) xstream.fromXML(new FileInputStream(pomFile));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		} catch (RuntimeException e) {
			throw new RuntimeException("exception while loading file " + pomFile, e);
		}
	}

	public void writeToFile(File pomFile, Project project) {
		try {
			xstream.toXML(project, new FileOutputStream(pomFile));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
