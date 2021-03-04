package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.dto.request.NotificationSaveRequestDto;
import kr.co.seok.dto.request.NotificationUpdateRequestDto;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Api(value = "Notification-Controller", description = "This is MatterMost Notification controller capable of CRUD")
@RequestMapping(value = "/api/v1/noti")
@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping()
    @ApiOperation(value = "enroll Notification")
    public ResponseEntity<CommonResponse> save(@RequestBody NotificationSaveRequestDto notificationSaveRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
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
    public ResponseEntity<CommonResponse> updateUrl(@PathVariable Long id, @RequestBody NotificationUpdateRequestDto notificationUpdateRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            result.result = notificationService.update(id, notificationUpdateRequestDto);
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
