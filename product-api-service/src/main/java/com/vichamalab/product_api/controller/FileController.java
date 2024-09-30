package com.vichamalab.product_api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.vichamalab.product_api.dto.UploadResponse;
import com.vichamalab.product_api.storage.StorageFileNotFoundException;
import com.vichamalab.product_api.storage.StorageService;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/files/")
@RequiredArgsConstructor
@Slf4j
public class FileController {
	private final StorageService storageService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<String> getFileList() {
		log.info(String.format("getFileList"));
		return storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(
						FileController.class, "serveFile", path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList());
	}
	
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	
	@PostMapping
	private ResponseEntity<UploadResponse> uploadFile(@RequestParam("archivo") MultipartFile file) {
		log.info(String.format("uploadFile"));
		UploadResponse response = new UploadResponse();
		
		try {
			storageService.store(file);
			response.setStatus(true);
			response.setFilename(file.getName());
			response.setMessage("El archivo fue cargado con exito");
		} catch(Exception ex) {
			response.setStatus(false);
			response.setMessage("El archivo no fue cargado con exito");
		}
		return  new ResponseEntity<UploadResponse>(response, HttpStatus.CREATED);
	}	

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
