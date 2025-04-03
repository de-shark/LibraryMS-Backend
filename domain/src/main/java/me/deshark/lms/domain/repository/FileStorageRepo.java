package me.deshark.lms.domain.repository;

import me.deshark.lms.common.exception.FileStorageException;
import me.deshark.lms.domain.model.common.FileData;

public interface FileStorageRepo {

    /**
     * 上传文件
     * @param fileData 上传的文件
     * @param objectName 存储的对象名称
     */
    void uploadFile(FileData fileData, String objectName) throws FileStorageException;

    /**
     * 删除文件
     * @param objectName 要删除的对象名称
     */
    void deleteFile(String objectName) throws FileStorageException;

    /**
     * 获取文件访问URL
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    String getFileUrl(String objectName);
}
