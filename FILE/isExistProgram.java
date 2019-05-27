package isExistProgram;

import java.io.File;
import java.io.IOException;

public class isExistProgram {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		isExistProgram prog = new isExistProgram();
		
		String fileName = "BATCH01.EXE";
		int isExist = prog.subDirList("./BATCH", fileName);
		if(isExist == 1) {
			System.out.println("FILE EXIST");
		}
	}
	
	public int subDirList(String source, String fileName){
		File dir = new File(source); 
		int result = 0;
		File[] fileList = dir.listFiles(); 
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					System.out.println("\t파일이름= " + file.getName());
					
					if(file.getName().equals(fileName)) {
						result = 1;
						break;
					}
				}else if(file.isDirectory()){
					System.out.println("디렉토리= " + file.getName());
					subDirList(file.getCanonicalPath().toString(), fileName); 
				}
			}
		}catch(IOException e){
			
		}
		
		return result;
	}

}
