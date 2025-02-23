package com.shell.webapplication.utils.app;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Component
public class ImageUtils {

    public String getImageType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (Objects.nonNull(contentType) && contentType.startsWith("image/")) {
            return contentType.substring(6);
        }
        throw new IllegalArgumentException("Invalid Image file");
    }

    public String getImageTypeByHeader(MultipartFile multipartFile) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();
        String fileSignature = bytesToHex(fileBytes, 0, 4);
        System.out.println(fileSignature);
        switch (fileSignature) {
            case "FFD8FFE0":
                return MediaType.IMAGE_JPEG_VALUE;
            case "89504E47":
                return MediaType.IMAGE_PNG_VALUE;
            case "47494638":
                return MediaType.IMAGE_GIF_VALUE;
            default:
                throw new IllegalArgumentException("Unsupported Image Format");
        }
    }

    public String bytesToHex(byte[] fileBytes, int offSet, int length) {
        StringBuilder hexString = new StringBuilder();
        for (int i = offSet; i < offSet + length; i++) {
            hexString.append(String.format("%02X", fileBytes[i]));
        }
        return hexString.toString();
    }

}
