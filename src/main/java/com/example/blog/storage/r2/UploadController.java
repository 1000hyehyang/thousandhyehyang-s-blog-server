package com.example.blog.storage.r2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class UploadController {

    private final R2StorageService storageService;

    public UploadController(R2StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
        try {
            String url = storageService.upload(file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("업로드 실패: " + e.getMessage());
        }
    }
}
