package com.example.dao;

import com.example.entity.ClassInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface ClassInfoDao extends Mapper<ClassInfo> {


    @Select("select a.*, b.name AS teacherName,c.name AS zhuanyeName from class_info AS a LEFT JOIN teacher_info AS b ON a.teacherId = b.id \n" +
            "LEFT JOIN zhuanye_info as c ON a.zhuanyeId = c.id where a.name like concat('%', #{search}, '%')")
    List<ClassInfo> findSearch(@Param("search") String search);


    @Select("select a.*, b.name AS teacherName,c.name AS zhuanyeName from class_info AS a LEFT JOIN teacher_info AS b ON a.teacherId = b.id \n" +
            "LEFT JOIN zhuanye_info as c ON a.zhuanyeId = c.id")
    List<ClassInfo> findAll();

    @Select("select * from class_info where name = #{name} and teacherId = #{teacherId} limit 1")
    ClassInfo findByNameAndTeacher(@Param("name") String name, @Param("teacherId") Long teacherId);

    @Select("select * from class_info where zhuanyeId = #{id}")
    ClassInfo selectByzhuanyeId(Long id);

    @Select("select * from class_info where teacherId = #{id}")
    ClassInfo selectByteacherId(Long id);
}
