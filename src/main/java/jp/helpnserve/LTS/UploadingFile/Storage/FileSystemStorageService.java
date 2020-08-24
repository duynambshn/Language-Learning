package jp.helpnserve.LTS.UploadingFile.Storage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.helpnserve.LTS.Model.Sound;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file, String soundId) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}

			createDir(soundId);
			System.out.println("tao duoc dir");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(soundId).resolve(file.getOriginalFilename()));
			System.out.println("tao duoc file");
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() throws IOException {
//		FileSystemUtils.deleteRecursively(rootLocation.toFile());
		FileUtils.cleanDirectory(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	public void createDir(String soundId) {
//		File TEMP_DIRECTORY = new File(System.getProperty("user.dir"));

//		File newDirectory = new File(TEMP_DIRECTORY, this.rootLocation.toString() + "\\" + soundId);
		File newDirectory = new File(this.rootLocation.toString() + "/" + soundId);
		newDirectory.mkdir();
	}

	@Override
	public void deleteDir(Sound oldSound) {
		File TEMP_DIRECTORY = new File(System.getProperty("user.dir"));

		File destination = new File(TEMP_DIRECTORY, this.rootLocation.toString() + "\\" + oldSound.getId());
		FileSystemUtils.deleteRecursively(destination);
	}
}
