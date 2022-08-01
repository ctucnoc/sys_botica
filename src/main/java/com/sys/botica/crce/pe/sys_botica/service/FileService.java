package com.sys.botica.crce.pe.sys_botica.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.sys.botica.crce.pe.sys_botica.dto.CloudinaryImgDTO;

public interface FileService {
	public CloudinaryImgDTO save(MultipartFile multipartFile,Long idProduct) throws IOException;
}
