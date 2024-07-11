package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.StudentInfoDao;
import com.example.dao.XueyuanInfoDao;
import com.example.dao.ZhuanyeInfoDao;
import com.example.entity.StudentInfo;
import com.example.entity.XueyuanInfo;
import com.example.entity.ZhuanyeInfo;
import com.example.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class XueyuanInfoService {

    @Resource
    private XueyuanInfoDao xueyuanInfoDao;
    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private StudentInfoDao studentInfoDao;

    public void add(XueyuanInfo xueyuanInfo) {
        XueyuanInfo info = xueyuanInfoDao.findByName(xueyuanInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException("-1", "该学院名称已存在");
        }
        xueyuanInfoDao.insertSelective(xueyuanInfo);
    }

    public List<XueyuanInfo> findAll() {
        return xueyuanInfoDao.selectAll();
    }

    public void update(XueyuanInfo xueyuanInfo) {
        XueyuanInfo info = xueyuanInfoDao.findByName(xueyuanInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException("-1", "学院名称已存在");
        }
        xueyuanInfoDao.updateByPrimaryKeySelective(xueyuanInfo);
    }

    public void deleteById(Long id) {
        ZhuanyeInfo zhuanyeInfo = zhuanyeInfoDao.selectByxueyuanId(id);
        StudentInfo studentInfo = studentInfoDao.selectByxueyuanId(id);
        if (ObjectUtil.isNotEmpty(zhuanyeInfo)) {
            throw new CustomException("-1", "该学院下已关联专业，无法删除");
        }
        if (ObjectUtil.isNotEmpty(studentInfo)) {
            throw new CustomException("-1", "该学院下已关联学生，无法删除");
        }
        xueyuanInfoDao.deleteByPrimaryKey(id);
    }

    public List<XueyuanInfo> find(String search) {
        return xueyuanInfoDao.find(search);
    }
}
