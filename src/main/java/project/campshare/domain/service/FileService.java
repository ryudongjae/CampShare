package project.campshare.domain.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.campshare.exception.IllegalMineTypeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService{
    public void checkImageMimeType(String mimeType) {
        if(!(mimeType.equals("image/jpg")|| mimeType.equals("image/jpeg")
                || mimeType.equals("image/png") || mimeType.equals("image/gif")));

    }

    public String addDirToSave(String filename, String dir) {
        StringBuilder builder = new StringBuilder();

        builder.append(dir).append("/").append(filename);

        return builder.toString();
    }

    public String fileNameConvert(String filename) {
        StringBuilder builder = new StringBuilder();
        UUID uuid = UUID.randomUUID();
        String extension = getExtension(filename);

        builder.append(uuid).append(".").append(extension);

        return builder.toString();
    }

    private String getExtension(String filename){
        int pos = filename.lastIndexOf(".");

        return  filename.substring(pos + 1);
    }
}
