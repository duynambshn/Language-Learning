package jp.helpnserve.LTS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import jp.helpnserve.LTS.UploadingFile.Storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class HelpAndServeLtsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HelpAndServeLtsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HelpAndServeLtsApplication.class);
	}

}
