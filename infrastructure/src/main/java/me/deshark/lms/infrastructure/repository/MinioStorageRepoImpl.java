package me.deshark.lms.infrastructure.repository;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.exception.FileStorageException;
import me.deshark.lms.domain.model.common.FileData;
import me.deshark.lms.domain.repository.FileStorageRepo;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MinioStorageRepoImpl implements FileStorageRepo {

    private final MinioClient minioClient;
    private static final String DEFAULT_BUCKET = "library-assets";
    private static final String endpoint = "http://localhost:9000";

    /**
     * 上传文件到MinIO
     * @param file 上传的文件
     * @param objectName 存储的对象名称(如book-covers/9781234567890.jpg)
     */
    public void uploadFile(FileData file, String objectName) {
        try {
            // 上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(DEFAULT_BUCKET)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
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
        return buildFileUrl(objectName);
    }

    /**
     * 构建文件访问URL
     * @param objectName 对象名称
     * @return 完整的文件访问URL
     */
    private String buildFileUrl(String objectName) {
        // 构建完整的URL
        return String.format("%s/%s/%s", endpoint, DEFAULT_BUCKET, objectName);
    }
}
