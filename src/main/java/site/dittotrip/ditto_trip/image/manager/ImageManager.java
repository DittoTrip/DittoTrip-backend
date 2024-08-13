package site.dittotrip.ditto_trip.image.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageManager implements WebMvcConfigurer {

    @Value("${image.connect-path}")
    private String connectPath;

    @Value("${image.save-path")
    private String savePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath + "**")
                .addResourceLocations("file:/" + savePath);
    }
}
