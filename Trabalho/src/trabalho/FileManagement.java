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

	String list(String path) {
		File file = new File(path);

		String listOfFiles = "";

		String[] DirsList = file.list();
		// Transforma o vetor de String em uma String e reconhece se é diretório ou
		// arquivo e seu tipo.
		for (int i = 0; i < DirsList.length; i++) {
			DirsList[i] = DirsList[i].replace(".", ":.");
			String[] fileName = DirsList[i].split(":");
			if (fileName.length == 1)
				listOfFiles += "<DIR>\t" + DirsList[i] + ";";
			else {
				String fileType = fileName[1];
				listOfFiles += "<" + fileType.toUpperCase() + ">\t" + fileName[0] + fileName[1] + ";";
			}
		}

		return listOfFiles;
	}

	boolean del(String path) {
		File file = new File(path);
		String fileName = file.getName();

		if (fileName.contains("\"")) {
			path = path.replace("\"", "");
			return delete(path);
		} // if

		if (fileName.contains(" ")) {
			String[] files = fileName.split(" ");
			for (int i = 0; i < files.length - 1; i++) {
				files[i] = file.getParent() + "\\" + files[i];
				delete(files[i]);
			} // for
			files[files.length - 1] = file.getParent() + "\\" + files[files.length - 1];
			return delete(files[files.length - 1]);
		} // if

		return delete(path);
	}

	private boolean delete(String path) {

		File file = new File(path);

		if (file.isDirectory()) {
			String[] files = file.list();
			if (files.length > 0)
				delete(path, files.length - 1);
		}

		return file.delete();
	}

	private void delete(String path, int i) {

		if (i < 0)
			return;

		File file = new File(path);
		String files[] = file.list();

		delete(path + "\\" + files[i]);

		delete(path, --i);
	}
}
