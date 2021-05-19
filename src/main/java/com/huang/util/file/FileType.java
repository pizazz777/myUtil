package com.huang.util.file;


import com.huang.exception.UploadException;
import com.huang.util.lang.StrUtil;

/**
 * @author administrator
 * @date 2020/07/16
 * @description: 类描述: 文件类型方法接口
 **/
public interface FileType {

    /**
     * 获取文件格式
     *
     * @return r
     */
    int getIntType();

    /**
     * 获取后缀名
     *
     * @return 后缀名
     */
    String getSuffix();

    /**
     * 获取文件格式
     *
     * @param fileName 文件名
     * @return 文件类型
     * @throws UploadException e
     */
    static FileTypeEnum getByName(String fileName) throws UploadException {
        try {
            String suffix = FileUtil.getSuffix(fileName);
            String enumName = getEnumName(suffix);
            return FileTypeEnum.valueOf(enumName);
        } catch (Exception e) {
            throw new UploadException("不支持此文件类型");
        }
    }

    /**
     * 获取文件格式
     *
     * @param suffix 后缀名
     * @return 文件格式
     */
    static FileTypeEnum getBySuffix(String suffix) {
        String enumName = getEnumName(suffix);
        return FileTypeEnum.valueOf(enumName);
    }

    /**
     * 获取文件格式Enum
     *
     * @return r
     * @throws UploadException e
     */
    static FileTypeEnum getByIntType(int fileType) throws UploadException {
        if (fileType == 0) {
            throw new UploadException("不支持此文件类型");
        }
        FileTypeEnum[] fileTypeEnums = FileTypeEnum.values();
        if (fileType > fileTypeEnums.length) {
            throw new UploadException("不支持此文件类型");
        }
        return FileTypeEnum.values()[fileType - 1];
    }

    /**
     * 通过 base64中数据描述获取文件类型
     *
     * @param base64Prefix 前缀
     * @return r
     * @throws UploadException e
     */
    static FileTypeEnum getByBase64Prefix(String base64Prefix) throws UploadException {
        if ("data:image/jpeg;".equalsIgnoreCase(base64Prefix)) {
            return FileTypeEnum.JPG;
        } else if ("data:image/x-icon;".equalsIgnoreCase(base64Prefix)) {
            return FileTypeEnum.ICO;
        } else if ("data:image/gif;".equalsIgnoreCase(base64Prefix)) {
            return FileTypeEnum.GIF;
        } else if ("data:image/png;".equalsIgnoreCase(base64Prefix)) {
            return FileTypeEnum.PNG;
        } else {
            throw new UploadException("上传图片格式不合法");
        }
    }

    /**
     * 通过后缀获取Enum的 name 值
     *
     * @param suffix 后缀名
     * @return r
     */
    static String getEnumName(String suffix) {
        String result = suffix;
        if (StrUtil.isStartWithNumber(result)) {
            result = "_" + result;
        }
        return result.replaceAll("\\.", "").toUpperCase();
    }
}
