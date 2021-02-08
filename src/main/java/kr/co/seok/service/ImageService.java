package kr.co.seok.service;

import kr.co.seok.dto.Image;
import kr.co.seok.dto.request.ImageSaveRequestDto;
import kr.co.seok.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public Image save(ImageSaveRequestDto imageSaveRequestDto){
        return imageRepository.save(imageSaveRequestDto.toEntity());
    }

//    public Image findById(Long )
}
