package kr.co.seok.controller;

import io.swagger.annotations.Api;
import kr.co.seok.dto.Image;
import kr.co.seok.dto.request.ImageSaveRequestDto;
import kr.co.seok.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Api(value = "Image-Controller", description = "This is image controller capable of CRUD")
@RestController(value = "/api/v1/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping()
    public ResponseEntity<Image> save() {
        Image image = imageService.save(new ImageSaveRequestDto("asdfa"));
        URI location = URI.create(String.format("/image/%s", image.getImgUrl()));
        return ResponseEntity.created(location).body(image);
    }
}
