package com.duckydoc.appunti.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    @Autowired
    DocumentRepository fileRepository;

    @Autowired
    UserRepository repository;

    @Autowired
    public FileService(DocumentRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile file, String doc) throws IOException {
        Gson gson = new Gson();
        JsonObject data = new JsonParser().parse(doc).getAsJsonObject();

        Document fileEntity = new Document();
        User user = repository.findById(gson.fromJson(data.get("user"), User.class).getId());
        if (user == null) {
            user = gson.fromJson(data.get("user"), User.class);
            fileEntity.setUser(user);

        } else {
            System.out.println(user);
            fileEntity.setUser(user);

        }

        fileEntity.setTitle(gson.fromJson(data.get("title"), String.class));
        fileEntity.setnameFile(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setFormat(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());
        fileEntity.setPrice(gson.fromJson(data.get("price"), int.class));
        fileEntity.setDescription(gson.fromJson(data.get("desc"), String.class));
        fileEntity.setUniversity(gson.fromJson(data.get("university"), String.class));
        fileEntity.setCreationData(gson.fromJson(data.get("date"), int.class));
        fileEntity.setYear(gson.fromJson(data.get("year"), int.class));
        fileEntity.setCourse(gson.fromJson(data.get("course"), String.class));

        fileRepository.save(fileEntity);
    }

    public Optional<Document> getFile(long id) {
        return fileRepository.findById(id);
    }

    public List<Document> getAllFiles() {
        return (List<Document>) fileRepository.findAll();
    }
}