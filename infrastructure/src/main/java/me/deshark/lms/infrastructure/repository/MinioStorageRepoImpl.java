package me.deshark.lms.infrastructure.repository;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
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
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(DEFAULT_BUCKET)
                            .object(objectName)
                            .expiry(60 * 60 * 24) // 24小时有效期
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
