package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.config.security.JwtTokenProvider;
import kr.co.seok.dto.MatterMostUrl;
import kr.co.seok.dto.Member;
import kr.co.seok.dto.request.UrlSaveRequestDto;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.MemberService;
import kr.co.seok.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Api(value = "Url-Controller", description = "This is MatterMost Url controller capable of CRUD")
@RequestMapping(value = "/api/v1/url")
@RestController
public class UrlController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private UrlService urlService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    @ApiOperation(value = "enroll URL")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> save(@RequestHeader(value = "X-AUTH-TOKEN") String token, @RequestBody UrlSaveRequestDto urlSaveRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            urlSaveRequestDto.setMember(member);
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> updateUrl(@RequestHeader(value = "X-AUTH-TOKEN") String token, @PathVariable Long id, @RequestBody UrlSaveRequestDto urlSaveRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            MatterMostUrl matterMostUrl = urlService.findById(id);
            if(member.getRoles().contains("ROLE_ADMIN") || member.getMemberId().equals(matterMostUrl.getMember().getMemberId())){
                result.result = urlService.update(id, urlSaveRequestDto);
                result.msg = "ok";
                response = new ResponseEntity<>(result, HttpStatus.OK);
            }else{
                result.result = "URL을 수정할 권한이 없습니다.";
                result.msg = "forbidden";
                response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete URL")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> deleteUrl(@RequestHeader(value = "X-AUTH-TOKEN") String token, @PathVariable Long id) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            MatterMostUrl matterMostUrl = urlService.findById(id);
            if(member.getRoles().contains("ROLE_ADMIN") || member.getMemberId().equals(matterMostUrl.getMember().getMemberId())){
                urlService.deleteById(id);
                result.result = "정상적으로 삭제되었습니다.";
                result.msg = "no content";
                response = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            }else{
                result.result = "URL을 삭제할 권한이 없습니다.";
                result.msg = "forbidden";
                response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
