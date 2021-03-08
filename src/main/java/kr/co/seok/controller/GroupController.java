package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.config.security.JwtTokenProvider;
import kr.co.seok.dto.MatterMostGroup;
import kr.co.seok.dto.Member;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.GroupService;
import kr.co.seok.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Api(value = "Group-Controller", description = "This is MatterMost Notification Group controller capable of CRUD")
@RequestMapping(value = "api/v1/group")
@RestController
public class GroupController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    @ApiOperation(value = "enroll group")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> save(@RequestHeader(value = "X-AUTH-TOKEN") String token, @RequestParam Long urlId, @RequestParam Long notiId, @RequestParam String time) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            result.result = groupService.save(urlId, notiId, time, member);
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
    @ApiOperation(value = "get groups")
    public ResponseEntity<CommonResponse> getGroups(@RequestParam(required = false) String searchTime) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            searchTime = searchTime == null ? "" : searchTime;
            result.result = groupService.loadAll(searchTime);
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
    @ApiOperation(value = "update group")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> updateGroup(@RequestHeader(value = "X-AUTH-TOKEN") String token, @RequestParam Long groupId, @RequestParam Long urlId, @RequestParam Long notiId, @RequestParam String time) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            MatterMostGroup matterMostGroup = groupService.findById(groupId);
            if(member.getRoles().contains("ROLE_ADMIN") || member.getMemberId().equals(matterMostGroup.getMember().getMemberId())) {
                result.result = groupService.update(groupId, urlId, notiId, time);
                result.msg = "ok";
                response = new ResponseEntity<>(result, HttpStatus.OK);
            }else{
                result.result = "Group을 수정할 권한이 없습니다.";
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
    @ApiOperation(value = "delete group")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> deleteNotification(@RequestHeader(value = "X-AUTH-TOKEN") String token, @PathVariable Long id) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
            MatterMostGroup matterMostGroup = groupService.findById(id);
            if(member.getRoles().contains("ROLE_ADMIN") || member.getMemberId().equals(matterMostGroup.getMember().getMemberId())){
                groupService.deleteById(id);
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
