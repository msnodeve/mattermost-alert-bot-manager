package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.dto.MatterMostUrl;
import kr.co.seok.dto.Member;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.retrofit.RetrofitClient;
import kr.co.seok.retrofit.dto.MatterMostRequestDto;
import kr.co.seok.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@Api(value = "One-Time-Notification-Controller", description = "This is MatterMost Direct Notification controller")
@RequestMapping(value = "/v1/one-time")
@RestController
public class OneTimeNotificationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UrlService urlService;

    @PostMapping()
    @ApiOperation(value = "send direct message")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<CommonResponse> sendMessage(@RequestHeader(value = "X-AUTH-TOKEN") String token, @RequestParam Long[] urlIds, @RequestBody MatterMostRequestDto matterMostRequestDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            send(urlIds, matterMostRequestDto);
            result.result = "메세지 전송 성공";
            result.msg = "ok";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private void send(Long[] urlIds, MatterMostRequestDto matterMostRequestDto) {
        List<MatterMostUrl> matterMostUrls = urlService.findByUrlIds(urlIds);
        for (MatterMostUrl matterMostUrl : matterMostUrls) {
            Map<String, List<MatterMostRequestDto>> attachments = new HashMap<>();
            attachments.put("attachments", new ArrayList<>());
            attachments.get("attachments").add(matterMostRequestDto);
            Call<String> sendMessageCall = RetrofitClient.getSendMessageService().sendMessage(matterMostUrl.getUrl(), attachments);
            sendMessageCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    logger.info(this.getClass().toString() + " >>> " + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    logger.error(this.getClass().toString() + " >>> " + t.getMessage());
                }
            });
        }
    }
}
