package kr.co.seok.retrofit.service;

import kr.co.seok.retrofit.dto.MatterMostRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

public interface SendMessageService {
    @POST("{mm_url}")
    Call<String> sendMessage(@Path(value = "mm_url", encoded = true) String url, @Body Map<String, List<MatterMostRequestDto>> matterMostRequestDto);

}
