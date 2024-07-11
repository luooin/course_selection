package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.ClassInfo;
import com.example.entity.XuankeInfo;
import com.example.exception.CustomException;
import com.example.service.ClassInfoService;
import com.example.service.XuankeInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/classInfo")
public class ClassInfoController {

    @Resource
    private ClassInfoService classInfoService;
    @Resource
    private XuankeInfoService xuankeInfoService;

    @PostMapping
    public Result add(@RequestBody ClassInfo classInfo) {
        if (ObjectUtil.isEmpty(classInfo.getName())) {
            return Result.error("-1", "课程名称不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getDescription())) {
            return Result.error("-1", "课程介绍不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getScore())) {
            return Result.error("-1", "课程学分不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getKaiban())) {
            return Result.error("-1", "开班人数不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getTime())) {
            return Result.error("-1", "上课时段不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getLocation())) {
            return Result.error("-1", "上课地点不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getZhuanyeId())) {
            return Result.error("-1", "所属专业不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getTeacherId())) {
            return Result.error("-1", "授课教师不能为空");
        }
        classInfoService.add(classInfo);
        return Result.success();
    }

    @PostMapping("/xuanke")
    public Result xuanke(@RequestBody ClassInfo classInfo, HttpServletRequest request) {

        Account user = (Account) request.getSession().getAttribute("user");
        if (ObjectUtil.isEmpty(user)) {
            throw new CustomException("-1", "登录已失效，请重新登录");
        }

        // 1. 判断一下该学生有没有选过这门课
        XuankeInfo info = xuankeInfoService.find(classInfo.getName(), classInfo.getTeacherId(), user.getId());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException("-1", "您已经选过该门课，请不要重复选择");
        }

        // 2. 把课程信息塞一份到选课信息表里
        XuankeInfo xuankeInfo = new XuankeInfo();
        BeanUtils.copyProperties(classInfo, xuankeInfo);
        xuankeInfo.setId(null);

        // 3. 把选课信息表里剩下的字段信息补全
        xuankeInfo.setStudentId(user.getId());
        xuankeInfo.setStatus("待开课");

        xuankeInfoService.add(xuankeInfo);

        // 3. 课程信息里的已选人数要加 1
        classInfo.setYixuan(classInfo.getYixuan() + 1);
        classInfoService.update(classInfo);

        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ClassInfo classInfo) {
        if (ObjectUtil.isEmpty(classInfo.getName())) {
            return Result.error("-1", "课程名称不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getDescription())) {
            return Result.error("-1", "课程介绍不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getScore())) {
            return Result.error("-1", "课程学分不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getKaiban())) {
            return Result.error("-1", "开班人数不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getTime())) {
            return Result.error("-1", "上课时段不能为空");
        }
        if (ObjectUtil.isEmpty(classInfo.getLocation())) {
            return Result.error("-1", "上课地点不能为空");
        }
        classInfoService.update(classInfo);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        classInfoService.delete(id);
        return Result.success();
    }

    @GetMapping("/{search}")
    public Result findSearch(@PathVariable String search) {
        List<ClassInfo> list = classInfoService.findSearch(search);
        return Result.success(list);
    }

    @GetMapping
    public Result findAll() {
        List<ClassInfo> list = classInfoService.findAll();
        return Result.success(list);
    }

}
