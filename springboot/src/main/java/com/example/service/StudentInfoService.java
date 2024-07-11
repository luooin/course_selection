package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.StudentInfoDao;
import com.example.dao.XueyuanInfoDao;
import com.example.entity.Account;
import com.example.entity.AdminInfo;
import com.example.entity.StudentInfo;
import com.example.entity.XueyuanInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class StudentInfoService {

    @Resource
    private StudentInfoDao studentInfoDao;
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;


    public Account login(String name, String password) {

        // 去数据库里根据用户名和密码查询学生信息
        StudentInfo studentInfo = studentInfoDao.findByNameAndPassword(name, password);
        if (ObjectUtil.isEmpty(studentInfo)) {
            throw new CustomException("-1", "用户名、密码或者角色选择错误");
        }

        return studentInfo;
    }

    public StudentInfo findById(Long id) {
        return studentInfoDao.selectByPrimaryKey(id);
    }

    public void update(StudentInfo studentInfo) {
        //判断是否有重名用户，有则提示重新输入
        StudentInfo info = studentInfoDao.findByName(studentInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        studentInfoDao.updateByPrimaryKeySelective(studentInfo);
    }

    public List<StudentInfo> finAll() {
        // 处理list，把学院id替换成对应的学院名称
        // 使用sql关联查询语句进行查询
        return studentInfoDao.findAllJoinXueyuan();

    }

    public void add(StudentInfo studentInfo) {
        // 1. 从数据库中根据用户名查询查一条信息，如果有说明用户名重复，提示重新输入
        StudentInfo info = studentInfoDao.findByName(studentInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        // 2. 如果没有填密码，初始化一个默认密码
        if (ObjectUtil.isEmpty(studentInfo.getPassword())) {
            studentInfo.setPassword("123456");
        }
        studentInfoDao.insertSelective(studentInfo);
    }

    public void deleteById(Long id) {
        studentInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<StudentInfo> findPageName(Integer pageNum, Integer pageSize, String name) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<StudentInfo> infos = studentInfoDao.findByNamePage(name);
        for (StudentInfo info : infos) {
            XueyuanInfo xueyuanInfo = xueyuanInfoDao.selectByPrimaryKey(info.getXueyuanId());
            if (ObjectUtil.isNotEmpty(xueyuanInfo)) {
                info.setXueyuanName(xueyuanInfo.getName());
            }
        }
        return PageInfo.of(infos);
    }

    public PageInfo<StudentInfo> findPage(Integer pageNum, Integer pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 下面查询就会自动根据pageNum和pageSize来查对应的数据
        List<StudentInfo> infos = studentInfoDao.selectAll();
        for (StudentInfo info : infos) {
            XueyuanInfo xueyuanInfo = xueyuanInfoDao.selectByPrimaryKey(info.getXueyuanId());
            if (ObjectUtil.isNotEmpty(xueyuanInfo)) {
                info.setXueyuanName(xueyuanInfo.getName());
            }
        }
        return PageInfo.of(infos);
    }
}
