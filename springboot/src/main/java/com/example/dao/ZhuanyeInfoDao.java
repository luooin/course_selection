package com.example.dao;

import com.example.entity.ZhuanyeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface ZhuanyeInfoDao extends Mapper<ZhuanyeInfo> {

    @Select("select * from zhuanye_info where name = #{name}")
    ZhuanyeInfo findByName(@Param("name") String name);

    @Select("select a.*, b.name AS xueyuanName from zhuanye_info AS a LEFT JOIN xueyuan_info AS b ON a.xueyuanId = b.id where a.name like concat('%', #{search}, '%')")
    List<ZhuanyeInfo> findBySearch(@Param("search") String search);
    @Select("select * from zhuanye_info where xueyuanId = #{id}")
    ZhuanyeInfo selectByxueyuanId(Long id);
}
