package kr.co.seok.controller;

import io.swagger.annotations.Api;
import kr.co.seok.dto.Image;
import kr.co.seok.dto.request.FileSaveRequestDto;
import kr.co.seok.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Api(value = "Image-Controller", description = "This is image controller capable of CRUD")
@RestController(value = "/api/v1/image")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping()
    public ResponseEntity<Image> save() {
        Image image = fileService.save(new FileSaveRequestDto("asdfa"));
        URI location = URI.create(String.format("/image/%s", image.getImgUrl()));
        return ResponseEntity.created(location).body(image);
    }
}
