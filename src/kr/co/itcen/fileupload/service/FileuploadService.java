package kr.co.itcen.fileupload.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.fileupload.exception.FileUploadException;
@Service
public class FileuploadService {
	private static final String SAVE_PATH="/uploads";
	private static final String URL_PREFIX="/images";
	
	public String restore(MultipartFile multipartFile)  {
		
		String url="";
		try {
			if(multipartFile == null)
				return url;
			String originalFilename =multipartFile.getOriginalFilename();
			String extName=originalFilename.substring(originalFilename.lastIndexOf('.')+1);
			String saveFileName= generateSaveFilename(extName);
			long fileSize=multipartFile.getSize();
			System.out.println("##########################"+originalFilename);
			System.out.println("##########################"+saveFileName);
			System.out.println("##########################"+fileSize);
			
			byte[] fileDate = multipartFile.getBytes();
			OutputStream os=new FileOutputStream(SAVE_PATH+"/"+saveFileName);
			os.write(fileDate);
			os.close();
			url=URL_PREFIX+"/"+saveFileName;
		} catch (IOException e) {
			throw new FileUploadException();
		}
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename="";
		Calendar calendar = Calendar.getInstance();//유니크한 이름을 주기위해 밀리초까지
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename+=(".")+extName;
		return filename;
	}

}
