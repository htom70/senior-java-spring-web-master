package hu.ponte.hr.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.ponte.hr.model.Image;
import hu.ponte.hr.services.ImageStore;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
		return imageStore.getMetaDatas();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		Optional<Image> optionalImage = imageStore.findImageById(Long.valueOf(id));
		if (optionalImage.isPresent()) {
			Image image = optionalImage.get();
			response.setContentType(image.getMimeType());
			InputStream inputStream = new ByteArrayInputStream(image.getContent());
			IOUtils.copy(inputStream, response.getOutputStream());
		}
	}
}
