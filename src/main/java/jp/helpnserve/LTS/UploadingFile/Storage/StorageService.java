package jp.helpnserve.LTS.UploadingFile.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import jp.helpnserve.LTS.Model.Sound;

public interface StorageService {

	void init();

	void store(MultipartFile file, String soundId);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll() throws IOException;

	void deleteDir(Sound sound);

}
