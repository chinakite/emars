package com.ideamoment.emars.upload.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OssFileMapper {
    @Insert("insert into oss_files (c_local_path, c_file_name, c_oss_path)values(#{localPath}, #{fileName}, #{ossPath})")
    boolean insertOssFile(@Param("localPath") String localPath, @Param("fileName") String fileName, @Param("ossPath") String ossPath);
}
