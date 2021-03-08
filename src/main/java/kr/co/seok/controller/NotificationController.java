package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.config.security.JwtTokenProvider;
import kr.co.seok.dto.MatterMostNotification;
import kr.co.seok.dto.Member;
import kr.co.seok.dto.request.NotificationSaveRequestDto;
import kr.co.seok.dto.request.NotificationUpdateRequestDto;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.MemberService;
import kr.co.seok.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Api(value = "Notification-Controller", description = "This is MatterMost Notification controller capable of CRUD")
@RequestMapping(value = "api/v1/noti")
@RestController
public class NotificationController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    @ApiOperation(value = "enroll Notification")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> save(@RequestHeader(value = "X-AUTH-TOKEN") String token, @RequestBody NotificationSaveRequestDto notificationSaveRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            notificationSaveRequestDto.setMember(member);
            result.result = notificationService.save(notificationSaveRequestDto);
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
    @ApiOperation(value = "get notifications")
    public ResponseEntity<CommonResponse> getNotifications(@RequestParam(required = false) String searchText) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            searchText = searchText == null ? "" : searchText;
            result.result = notificationService.loadAll(searchText);
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
    @ApiOperation(value = "update notification")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> updateNotification(@RequestHeader(value = "X-AUTH-TOKEN") String token, @PathVariable Long id, @RequestBody NotificationUpdateRequestDto notificationUpdateRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            MatterMostNotification matterMostNotification = notificationService.findById(id);
            if(member.getRoles().contains("ROLE_ADMIN") || member.getMemberId().equals(matterMostNotification.getMember().getMemberId())){
                result.result = notificationService.update(id, notificationUpdateRequestDto);
                result.msg = "ok";
                response = new ResponseEntity<>(result, HttpStatus.OK);
            }else{
                result.result = "Noti를 수정할 권한이 없습니다.";
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
    @ApiOperation(value = "delete notification")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> deleteNotification(@RequestHeader(value = "X-AUTH-TOKEN") String token, @PathVariable Long id) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            MatterMostNotification matterMostNotification = notificationService.findById(id);
            if(member.getRoles().contains("ROLE_ADMIN") || member.getMemberId().equals(matterMostNotification.getMember().getMemberId())){
                notificationService.deleteById(id);
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
