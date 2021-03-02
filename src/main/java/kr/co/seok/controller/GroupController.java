package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Api(value = "Group-Controller", description = "This is MatterMost Notification Group controller capable of CRUD")
@RequestMapping(value = "/v1/group")
@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;



    @PostMapping()
    @ApiOperation(value = "enroll Group")
    public ResponseEntity<CommonResponse> save(@RequestParam Long urlId, @RequestParam Long notiId, @RequestParam String time) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            result.result = groupService.save(urlId, notiId, time);
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
    @ApiOperation(value = "update notification")
    public ResponseEntity<CommonResponse> updateUrl(@RequestParam Long groupId, @RequestParam Long urlId, @RequestParam Long notiId, @RequestParam String time) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            result.result = groupService.update(groupId, urlId, notiId, time);
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
