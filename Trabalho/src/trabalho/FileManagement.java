package trabalho;

import java.io.File;

public class FileManagement {
	private String dirs[];
	/*
	 * private String path;
	 * 
	 * public FileManagement(String path) { this.path = path; }
	 */
	
	boolean createDir(String path) {
		
		path = path.replace("\\", "/");
		dirs = path.split("/");

		return createDir(dirs[0], 0);
		
	}
	
	private boolean createDir(String path, int i) {
		
		File file = new File(path);		
		boolean fileCreated = false;
		
		if (!file.exists())
			fileCreated = file.mkdir();	
		
		if (i < dirs.length-1) {
			return createDir(path + "\\" + dirs[++i], i);
		}
		
		return true;
	}
}
