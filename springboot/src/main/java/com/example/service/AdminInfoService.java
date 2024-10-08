package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.AdminInfoDao;
import com.example.entity.Account;
import com.example.entity.AdminInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminInfoService {

    @Resource
    private AdminInfoDao adminInfoDao;


    public Account login(String name, String password) {

        // 通过用户名和密码去数据库里查一条记录出来
        AdminInfo adminInfo = adminInfoDao.findByNameAndPass(name, password);

        if (ObjectUtil.isEmpty(adminInfo)) {
            throw new CustomException("-1", "用户名、密码或角色错误");
        }

        return adminInfo;
    }

    public AdminInfo findById(Long id) {
        return adminInfoDao.selectByPrimaryKey(id);
    }

    public void update(AdminInfo adminInfo) {
        // 查询数据库是否有同名用户
        AdminInfo info = adminInfoDao.findByName(adminInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            // 如果有，提示用户名已经存在
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        adminInfoDao.updateByPrimaryKeySelective(adminInfo);
    }

    public void add(AdminInfo adminInfo) {
        // 判断是否能直接插入数据库
        // 1. 新增管理员，如果没有密码则默认初始密码

        // 2. 查询数据库是否有同名用户
        AdminInfo info = adminInfoDao.findByName(adminInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            // 如果有，提示用户名已经存在
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(adminInfo.getPassword())) {
            // 没有密码，初始化密码为123456
            adminInfo.setPassword("123456");
        }
        adminInfoDao.insertSelective(adminInfo);
    }

    public List<AdminInfo> findAll() {
        return adminInfoDao.selectAll();
    }

    public void deleteById(Long id) {
        adminInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<AdminInfo> findPage(Integer pageNum, Integer pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 下面查询就会自动根据pageNum和pageSize来查对应的数据
        List<AdminInfo> infos = adminInfoDao.selectAll();
        return PageInfo.of(infos);
    }

    public PageInfo<AdminInfo> findPageName(Integer pageNum, Integer pageSize, String name) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<AdminInfo> infos = adminInfoDao.findByNamePage(name);
        return PageInfo.of(infos);
    }
}
