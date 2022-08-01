package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sys.botica.crce.pe.sys_botica.config.CloudinaryConfig;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.CloudinaryImgDTO;
import com.sys.botica.crce.pe.sys_botica.service.CloudinaryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

	final CloudinaryConfig cloudinaryConfig;

	public CloudinaryServiceImpl(CloudinaryConfig cloudinaryConfig) {
		this.cloudinaryConfig = cloudinaryConfig;
	}

	@Override
	public CloudinaryImgDTO upload(MultipartFile multipartFile) throws IOException {
		log.info("crce service cloudinary upload -> {} "+multipartFile.toString());
		Map result = init().uploader().upload(multipartFile.getBytes(),ObjectUtils.emptyMap());
		return CloudinaryImgDTO.builder()
				.fileName(multipartFile.getOriginalFilename())
				.url(result.get("url").toString())
				.publicId(result.get("public_id").toString())
				.build();
	}

	@Override
	public CloudinaryImgDTO delete(String id) throws IOException {
		log.info("crce service cloudinary delete -> {} "+id);
		init().uploader().destroy(id,ObjectUtils.emptyMap());
		return null;
	}

	private Cloudinary init() {
		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put(SysBoticaConstant.API_CLOUDINARY_NAME, cloudinaryConfig.getName());
		valuesMap.put(SysBoticaConstant.API_CLOUDINARY_KEY, cloudinaryConfig.getKey());
		valuesMap.put(SysBoticaConstant.API_CLOUDINARY_SECRET, cloudinaryConfig.getSecret());
		return new Cloudinary(valuesMap);
	}

}
