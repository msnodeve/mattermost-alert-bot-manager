package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.config.security.JwtTokenProvider;
import kr.co.seok.dto.Member;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.FileService;
import kr.co.seok.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@Api(value = "Image-Controller", description = "This is image controller capable of CRUD")
@RequestMapping(value = "/v1/file")
@RestController
public class FileController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private FileService fileService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    @ApiOperation(value = "File upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> save(@RequestHeader(value = "X-AUTH-TOKEN") String token, @RequestParam("file") MultipartFile file) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            result.result = fileService.save(file, member);
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
