package kr.co.seok.service;

import kr.co.seok.dto.File;
import kr.co.seok.dto.request.FileSaveRequestDto;
import kr.co.seok.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    @Value("${spring.file.location}")
    private String filePath;

    @Autowired
    private FileRepository fileRepository;

    public void init() {
        try {
            Files.createDirectory(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Transactional
    public String save(MultipartFile file) {
        String result = null;
        try {
            int pos = file.getOriginalFilename().lastIndexOf(".");
            String format = file.getOriginalFilename().substring(pos);
            String fileName = file.getOriginalFilename().substring(0, pos);
            result = UUID.randomUUID() + "~" + fileName + format;
            Files.copy(file.getInputStream(), Paths.get(filePath).resolve(result));
            File saveFile = fileRepository.save(new FileSaveRequestDto(Paths.get(filePath).toString(), result).toEntity());
            result = saveFile.getFileName();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return result;
    }

    public Resource load(String filename) {
        try {
            Path file = Paths.get(filePath).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(filePath).toFile());
    }

    public List<File> loadAll(String fileName) throws Exception {
        return fileRepository.findByFileNameContains(fileName);
    }
}
