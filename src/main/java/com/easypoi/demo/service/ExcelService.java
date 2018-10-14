package com.easypoi.demo.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ExcelService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${IMAGE_SERVER_URL}")
    private String url;

    /**
     * 上传资源到fastdfs资源服务器
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream()
                , file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return url + storePath.getFullPath();
    }
}
