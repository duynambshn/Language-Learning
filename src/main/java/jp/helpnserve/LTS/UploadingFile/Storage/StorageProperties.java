package jp.helpnserve.LTS.UploadingFile.Storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	// windows env
	// private String location = "src\\main\\resources\\resources\\uploadDir"; //

	// centos evn
	// private String location =
	private String location = "/usr/share/tomcat9/webapps/word-training/WEB-INF/classes/resources/uploadDir";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
