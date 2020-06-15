package jp.helpnserve.LTS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import jp.helpnserve.LTS.UploadingFile.Storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class HelpAndServeLtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpAndServeLtsApplication.class, args);
	}

}
