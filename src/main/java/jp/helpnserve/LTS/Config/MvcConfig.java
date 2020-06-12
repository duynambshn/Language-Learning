package jp.helpnserve.LTS.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/webjars/bootstrap/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.3.1/");
		registry.addResourceHandler("/resources/webjars/jquery/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.3.1/");
	}
}
