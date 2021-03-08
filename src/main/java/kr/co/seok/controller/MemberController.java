package kr.co.seok.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.seok.config.security.JwtTokenProvider;
import kr.co.seok.dto.Member;
import kr.co.seok.dto.request.MemberSaveRequestDto;
import kr.co.seok.dto.response.CommonResponse;
import kr.co.seok.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@Api(value = "Member-Controller")
@RequestMapping(value = "api/v1/member")
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    @ApiOperation(value = "join Member")
    public ResponseEntity<CommonResponse> save(@RequestBody Map<String, String> joinDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            result.result = memberService.join(new MemberSaveRequestDto(joinDto.get("id"), passwordEncoder.encode(joinDto.get("password"))));
            result.msg = "created";
            response = new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            result.result = e.getMessage();
            result.msg = "error";
            response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping("/login")
    @ApiOperation(value = "login Member")
    public ResponseEntity<CommonResponse> login(@RequestBody Map<String, String> loginDto) {
        ResponseEntity<CommonResponse> response;
        final CommonResponse result = new CommonResponse();
        try {
            Member member = (Member) memberService.loadUserById(loginDto.get("id"));
            System.out.println(member.toString());
            if(passwordEncoder.matches(loginDto.get("password"), member.getPassword())){
                result.result = jwtTokenProvider.createToken(member.getMemberId().toString(), member.getRoles());
                result.msg = "created";
                response = new ResponseEntity<>(result, HttpStatus.CREATED);
            }else{
                result.result = "비밀번호가 일치하지 않습니다.";
                result.msg = "error";
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
