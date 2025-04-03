package me.deshark.lms.infrastructure.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final MinioClient minioClient;
    private static final String DEFAULT_BUCKET = "library-assets";

    /**
     * 上传文件到MinIO
     * @param file 上传的文件
     * @param objectName 存储的对象名称(如book-covers/9781234567890.jpg)
     * @return 文件访问URL
     */
    public String uploadFile(MultipartFile file, String objectName) {
        try {
            // 上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(DEFAULT_BUCKET)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            // 返回文件访问url
            return "url";
        } catch (Exception e) {
            throw new FileStorageException("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 删除MinIO中的文件
     * @param objectName 要删除的对象名称
     */
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(DEFAULT_BUCKET)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new FileStorageException("文件删除失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件访问URL
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    public String getFileUrl(String objectName) {
        try {
            return "url";
        } catch (Exception e) {
            throw new FileStorageException("获取文件URL失败: " + e.getMessage());
        }
    }
}
