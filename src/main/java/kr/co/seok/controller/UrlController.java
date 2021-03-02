package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.dto.request.UrlSaveRequestDto;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Api(value = "Url-Controller", description = "This is MatterMost Url controller capable of CRUD")
@RequestMapping(value = "/v1/url")
@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping()
    @ApiOperation(value = "enroll URL")
    public ResponseEntity<CommonResponse> save(@RequestBody UrlSaveRequestDto urlSaveRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            result.result = urlService.save(urlSaveRequestDto);
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
    @ApiOperation(value = "get URLs")
    public ResponseEntity<CommonResponse> getUrls(@RequestParam(required = false) String searchText) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            searchText = searchText == null ? "" : searchText;
            result.result = urlService.loadAll(searchText);
            result.msg = "ok";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update URL")
    public ResponseEntity<CommonResponse> updateUrl(@PathVariable Long id, @RequestBody UrlSaveRequestDto urlSaveRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            result.result = urlService.update(id, urlSaveRequestDto);
            result.msg = "ok";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
