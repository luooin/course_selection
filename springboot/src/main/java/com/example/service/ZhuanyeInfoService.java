package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.dao.ClassInfoDao;
import com.example.dao.TeacherInfoDao;
import com.example.dao.XueyuanInfoDao;
import com.example.dao.ZhuanyeInfoDao;
import com.example.entity.ClassInfo;
import com.example.entity.TeacherInfo;
import com.example.entity.XueyuanInfo;
import com.example.entity.ZhuanyeInfo;
import com.example.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ZhuanyeInfoService {

    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;
    @Resource
    private TeacherInfoDao teacherInfoDao;
    @Resource
    private ClassInfoDao classInfoDao;

    public void add(ZhuanyeInfo zhuanyeInfo) {
        // 判断当前这个专业的名称在数据库里有没有
        ZhuanyeInfo info = zhuanyeInfoDao.findByName(zhuanyeInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException("-1", "您输入的专业名称已存在");
        }
        zhuanyeInfoDao.insertSelective(zhuanyeInfo);
    }

    public List<ZhuanyeInfo> findAll() {

        // 方式一：通过Java逻辑来给xueyuanName赋值
        List<ZhuanyeInfo> list = zhuanyeInfoDao.selectAll();
        for (ZhuanyeInfo zhuanyeInfo : list) {
            XueyuanInfo xueyuanInfo = xueyuanInfoDao.selectByPrimaryKey(zhuanyeInfo.getXueyuanId());
            zhuanyeInfo.setXueyuanName(xueyuanInfo.getName());
        }
        return list;
    }

    public void update(ZhuanyeInfo zhuanyeInfo) {
        // 判断当前这个专业的名称在数据库里有没有
        ZhuanyeInfo info = zhuanyeInfoDao.findByName(zhuanyeInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException("-1", "您输入的专业名称已存在");
        }
        zhuanyeInfoDao.updateByPrimaryKeySelective(zhuanyeInfo);
    }

    public void deleteById(Long id) {
        TeacherInfo teacherInfo = teacherInfoDao.selectByzhuanyeId(id);
        ClassInfo classInfo = classInfoDao.selectByzhuanyeId(id);
        if (ObjectUtil.isNotEmpty(teacherInfo)) {
            throw new CustomException("-1", "当前专业已关联教师，无法删除");
        }
        if (ObjectUtil.isNotEmpty(classInfo)) {
            throw new CustomException("-1", "当前专业已关联课程，无法删除");
        }
        zhuanyeInfoDao.deleteByPrimaryKey(id);
    }

    public List<ZhuanyeInfo> findSearch(String search) {
        // 使用sql关联查询语句从数据库中查询xueyuanName
        return zhuanyeInfoDao.findBySearch(search);
    }
}
