package jp.helpnserve.LTS.UploadingFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import jp.helpnserve.LTS.Model.Sound;
import jp.helpnserve.LTS.UploadingFile.Storage.StorageFileNotFoundException;
import jp.helpnserve.LTS.UploadingFile.Storage.StorageService;

//@Controller
@Service
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

//	@GetMapping("/")
//	public String listUploadedFiles(Model model) throws IOException {
//
//		model.addAttribute("files",
//				storageService.loadAll()
//						.map(path -> MvcUriComponentsBuilder
//								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
//								.build().toUri().toString())
//						.collect(Collectors.toList()));
//
//		return "uploadForm";
//	}
//
//	@GetMapping("/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//		Resource file = storageService.loadAsResource(filename);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}

//	@PostMapping("/")
//	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//
//		storageService.store(file);
//		redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");
//
//		return "redirect:/upload-default";
//	}

	public Boolean handleFileUpload(MultipartFile file, String soundId) {

		storageService.store(file, soundId);
		return true;
	}

	public Boolean handleDeleteDirectory(Sound oldSound) {

		storageService.deleteDir(oldSound);

		return true;
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}