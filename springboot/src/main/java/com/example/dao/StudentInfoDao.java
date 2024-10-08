package com.example.dao;

import com.example.entity.StudentInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface StudentInfoDao extends Mapper<StudentInfo> {

    @Select("select * from student_info where name = #{name} and password = #{password}")
    StudentInfo findByNameAndPassword(@Param("name") String name, @Param("password") String password);

    @Select("select * from student_info where name = #{name}")
    StudentInfo findByName(@Param("name") String name);

    @Select("SELECT a.*, b.name AS xueyuanName FROM student_info AS a LEFT JOIN xueyuan_info AS b ON a.xueyuanId = b.id")
    List<StudentInfo> findAllJoinXueyuan();

    @Select("select * from student_info where name like concat('%', #{name}, '%')")
    List<StudentInfo> findByNamePage(@Param("name") String name);
    @Select("select * from student_info where xueyuanId = #{id}")
    StudentInfo selectByxueyuanId(Long id);
}
