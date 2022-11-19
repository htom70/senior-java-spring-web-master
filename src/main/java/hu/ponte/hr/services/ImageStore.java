package hu.ponte.hr.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.model.Image;
import hu.ponte.hr.repository.ImageRepository;

@Service
public class ImageStore {

	Logger logger = LoggerFactory.getLogger(ImageStore.class);

	@Autowired private ImageRepository imageRepository;

	@Autowired private SignService signService;

	public void saveImage(MultipartFile file) {
		try {
			String sign = signService.sign(file.getBytes());
			Image image = new Image(file.getOriginalFilename(), file.getContentType(), sign, file.getBytes());
			imageRepository.save(image);
		} catch (IOException e) {
			logger.error("IO error occured");
		}
	}

	public List<ImageMeta> getMetaDatas() {
		List<Image> images = imageRepository.findAll();
		return images.stream()
				.map(e -> new ImageMeta(e.getId().toString(), e.getName(), e.getMimeType(), e.getContent().length,
						e.getDigitalSign())).collect(Collectors.toList());
	}

	public Optional<Image> findImageById(Long id) {
		return imageRepository.findById(id);
	}

}
