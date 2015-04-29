package fileName;

import java.io.File;

public class RenameMusicFile {

	public static void main(String[] args){

		// Directory path here
		String path = "/Users/Min/Desktop/11"; 

		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		System.out.println("hi"+listOfFiles.length);
		int count = 0;
		for (File files1 : listOfFiles) {
		      count++;
		      System.out.println(count + " - " + files1);
		}

		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isFile()){
				files = listOfFiles[i].getName();
				if(files.length()>3){
					boolean check = true;
					for(int j = 0; j<3 && check;j++)
						if(0 > files.charAt(j)-'0' || files.charAt(j)-'0' > 9)
							check = false;
					if(check)
						listOfFiles[i].renameTo(new File(path + files.substring(4)));
				}
			}
		}
	}
}
