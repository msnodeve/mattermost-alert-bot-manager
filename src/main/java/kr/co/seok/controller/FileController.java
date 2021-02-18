package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@Api(value = "Image-Controller", description = "This is image controller capable of CRUD")
@RequestMapping(value = "/api/v1/file")
@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping()
    @ApiOperation(value = "File upload")
    public ResponseEntity<CommonResponse> save(@RequestParam("file") MultipartFile file) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            result.result = fileService.save(file);
            result.msg = "created";
            response = new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping()
    public ResponseEntity<CommonResponse> getFiles(@RequestParam(required = false) String searchText) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try{
            searchText = searchText == null ? "" : searchText;
            result.result = fileService.loadAll(searchText);
            result.msg = "ok";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
