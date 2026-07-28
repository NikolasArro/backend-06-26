package ee.nikolas.backend0626.services;

import java.io.IOException;
import java.util.stream.Stream;

import ee.nikolas.backend0626.entity.FileDB;
import ee.nikolas.backend0626.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public void store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB();
        fileDB.setName(fileName);
        fileDB.setType(file.getContentType());
        fileDB.setData(file.getBytes());

        fileDBRepository.save(fileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).orElseThrow();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
