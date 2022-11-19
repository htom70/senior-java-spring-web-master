package hu.ponte.hr.controller.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hu.ponte.hr.services.ImageStore;

@Component
@RequestMapping("api/file")
public class UploadController
{

    @Autowired
    private ImageStore imageStore;

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(@RequestParam("file") MultipartFile file) {
        imageStore.saveImage(file);
        return "ok";
    }
}
