package be.butskri.playground.io;

import static junit.framework.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class FileUtilsTest {

	@Test
	public void relativePathGeeftJuistePathTerugVoorFile() {
		File basePath = new File("c:/path/to/somewhere");
		File file = new File("c:/path/to/somewhere/else/file.txt");
		assertEquals("else/file.txt", FileUtils.relativePath(basePath, file));
	}
	
	@Test
	public void relativePathGeeftJuistePathTerugVoorDirectory() {
		File basePath = new File("c:/path/to/somewhere");
		File dir = new File("c:/path/to/somewhere/else/nested/");
		assertEquals("else/nested", FileUtils.relativePath(basePath, dir));
	}
	
	@Test
	public void relativePathGeeftIndienBaseNietJuisteBaseIs() {
		File basePath = new File("c:/path/to/somewhere");
		File file = new File("c:/path/to/elsewhere/deeper/file.txt");
		assertEquals("../elsewhere/deeper/file.txt", FileUtils.relativePath(basePath, file));
	}
	
}
