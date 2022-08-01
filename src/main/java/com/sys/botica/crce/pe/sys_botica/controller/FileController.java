package com.sys.botica.crce.pe.sys_botica.controller;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.CloudinaryImgDTO;
import com.sys.botica.crce.pe.sys_botica.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_FILES)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class FileController {

	final
	FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_FILES_FILE + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<CloudinaryImgDTO> upload(@RequestParam MultipartFile multipartFile,@PathVariable Long id) throws IOException {
		log.info("crce upload -> {} "+multipartFile.toString());
		return ResponseEntity.ok(this.fileService.save(multipartFile,id));
	}
	
}
