package ee.nikolas.backend0626.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Log4j2
@RestController
public class FileController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload-local")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @AuthenticationPrincipal Jwt jwt) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String username = jwt.getSubject();
            log.info(username);
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            log.info(filename);
            Path userDir = uploadPath.resolve(username);
            log.info(userDir);
            Files.createDirectories(userDir);
            Path filePath = userDir.resolve(filename);
            log.info(filePath);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return ResponseEntity.ok("File uploaded: " + filePath.getFileName());

        } catch (IOException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename,
                                                 @AuthenticationPrincipal Jwt jwt) throws MalformedURLException {
        String username = jwt.getSubject();
        Path filePath = Paths.get(UPLOAD_DIR).resolve(username + "/" + filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
 }
