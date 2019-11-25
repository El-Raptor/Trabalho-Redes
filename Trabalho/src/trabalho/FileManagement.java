package trabalho;

import java.io.File;

public class FileManagement {
	
	boolean createDir(String path) {	
		
		if (!path.contains("\"")) {
			
			File file = new File(path); 
			String newDir = file.getName(); // Pega o nome do atual diretório.
			String paths[] = newDir.split(" ");
			
			for (int i = 0; i < paths.length; i++) {
				paths[i] = file.getParent() + "\\" + paths[i];
				File file2 = new File(paths[i]);
				file2.mkdirs();
			} // for
			
			return true;
			
		} // if
		
		path = path.replace("\"", ""); // Retira as aspas da String path.
		File file = new File(path);
		
		return file.mkdirs();
	}
}
