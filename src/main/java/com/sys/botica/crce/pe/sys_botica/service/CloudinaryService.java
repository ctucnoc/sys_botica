package com.sys.botica.crce.pe.sys_botica.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.sys.botica.crce.pe.sys_botica.dto.CloudinaryImgDTO;

public interface CloudinaryService {
	public CloudinaryImgDTO upload(MultipartFile multipartFile) throws IOException;
	public CloudinaryImgDTO delete(String id) throws IOException;
}
