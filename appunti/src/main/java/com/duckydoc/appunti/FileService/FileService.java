package com.duckydoc.appunti.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.duckydoc.appunti.model.Document;
import com.duckydoc.appunti.model.User;
import com.duckydoc.appunti.repo.DocumentRepository;
import com.duckydoc.appunti.repo.UserRepository;

@Service
public class FileService {

    private final DocumentRepository fileRepository;

    @Autowired
    UserRepository repository;

    @Autowired
    public FileService(DocumentRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile file, int price, int creationData, String description, String university, int year,
            String course, long userId, String username, String title) throws IOException {
        Document fileEntity = new Document();
        User user = repository.findById(userId);
        if (user == null) {
            user = new User(userId, username);
            fileEntity.setUser(user);

        } else {
            fileEntity.setUser(user);

        }
        fileEntity.setTitle(title);
        fileEntity.setnameFile(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setFormat(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());
        fileEntity.setPrice(price);
        fileEntity.setDescription(description);
        fileEntity.setUniversity(university);
        fileEntity.setCreationData(creationData);
        fileEntity.setYear(year);
        fileEntity.setCourse(course);

        fileRepository.save(fileEntity);
    }

    public Optional<Document> getFile(Long id) {
        return fileRepository.findById(id);
    }

    public List<Document> getAllFiles() {
        return (List<Document>) fileRepository.findAll();
    }
}