package kr.co.seok.service;

import kr.co.seok.dto.MatterMostUrl;
import kr.co.seok.dto.request.UrlSaveRequestDto;
import kr.co.seok.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    public MatterMostUrl save(UrlSaveRequestDto urlSaveRequestDto) {
        return urlRepository.save(urlSaveRequestDto.toEntity());
    }

    public List<MatterMostUrl> loadAll(String urlName) throws Exception {
        return urlRepository.findByUrlAliasContains(urlName);
    }

    public MatterMostUrl update(Long id, UrlSaveRequestDto urlSaveRequestDto) throws Exception {
        MatterMostUrl matterMostUrl = urlRepository.findById(id).orElseThrow(() -> new Exception("해당되는 URL 이 없습니다."));
        matterMostUrl.setUrl(urlSaveRequestDto.getUrl());
        matterMostUrl.setUrlAlias(urlSaveRequestDto.getUrlAlias());
        matterMostUrl.setUrlId(id);
        return urlRepository.save(matterMostUrl);
    }
}
