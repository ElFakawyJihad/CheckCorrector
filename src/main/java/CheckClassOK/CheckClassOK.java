package CheckClassOK;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CheckClassOK {
	private File unusedFile;
	private String path;
	private ArrayList<String> usedFileList;

	public CheckClassOK(ArrayList<String> usedFileList, String path) throws IOException {
		unusedFile = new File("FichierConforme.txt");
		if (!unusedFile.exists()) {
			unusedFile.createNewFile();
		}
		this.usedFileList = usedFileList;
		this.path = path;
	}

	public File[] listFileOK() throws IOException {
		File folder = new File(path);
		File[] file = folder.listFiles();
		int length = file.length;
		FileWriter fw = new FileWriter(unusedFile);
		BufferedWriter bw = new BufferedWriter(fw);
		String unused = "";
		File[] filesUnused = new File[length - usedFileList.size()];
		int j = 0;
		for (int i = 0; i < length; i++) {
			String nameFile = file[i].getName();
			if (!usedFileList.contains(nameFile)) {
				unused = nameFile + "\n";
				filesUnused[j] = file[i];
				j++;
			}
		}
		bw.write(unused);
		bw.close();
		fw.close();
		return filesUnused;
	}

	public File[] listFileKO() throws IOException {
		File folder = new File(path);
		File[] file = folder.listFiles();
		int length = file.length;
		FileWriter fw = new FileWriter(unusedFile);
		BufferedWriter bw = new BufferedWriter(fw);
		String unused = "";
		File[] filesUnused = new File[usedFileList.size()];
		int j = 0;
		for (int i = 0; i < length; i++) {
			String nameFile = file[i].getName();
			if (usedFileList.contains(nameFile)) {
				unused = nameFile + "\n";
				filesUnused[j] = file[i];
				j++;
			}
		}
		bw.write(unused);
		bw.close();
		fw.close();
		return filesUnused;
	}
}
