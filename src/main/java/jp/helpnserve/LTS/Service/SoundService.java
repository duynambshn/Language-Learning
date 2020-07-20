package jp.helpnserve.LTS.Service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.helpnserve.LTS.Model.Sound;
import jp.helpnserve.LTS.Repository.SoundRepository;
import jp.helpnserve.LTS.UploadingFile.FileUploadController;

@Service
public class SoundService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	SoundRepository soundRepository;

	@Autowired
	private FileUploadController fileUploadController;

	public Sound CreateNewSound(MultipartFile input) {

		int maxId = soundRepository.getMaxId();

		// save file on server
		fileUploadController.handleFileUpload(input, Integer.toString(maxId));

		Sound sound = new Sound();
		sound.setId(maxId);
		sound.setSoundURL("/uploadDir/" + Integer.toString(maxId) + "/" + input.getOriginalFilename());
		sound = soundRepository.save(sound);

		return sound;
	}

}
