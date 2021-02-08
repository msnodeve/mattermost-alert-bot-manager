package kr.co.seok.service;

import kr.co.seok.dto.Image;
import kr.co.seok.dto.request.FileSaveRequestDto;
import kr.co.seok.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public Image save(FileSaveRequestDto fileSaveRequestDto){
        return fileRepository.save(fileSaveRequestDto.toEntity());
    }

//    public Image findById(Long )
}
