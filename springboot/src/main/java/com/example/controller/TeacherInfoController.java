package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.TeacherInfo;
import com.example.service.TeacherInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/teacherInfo")
public class TeacherInfoController {

    @Resource
    private TeacherInfoService teacherInfoService;

    @PostMapping
    public Result add(@RequestBody TeacherInfo teacherInfo) {
        if (ObjectUtil.isEmpty(teacherInfo.getName())) {
            return Result.error("-1", "姓名不能为空");
        }
        if (ObjectUtil.isEmpty(teacherInfo.getSex())) {
            return Result.error("-1", "性别不能为空");
        }
        if (ObjectUtil.isEmpty(teacherInfo.getAge())) {
            return Result.error("-1", "年龄不能为空");
        }
        if (ObjectUtil.isEmpty(teacherInfo.getZhicheng())) {
            return Result.error("-1", "职称不能为空");
        }
        if (ObjectUtil.isEmpty(teacherInfo.getZhuanyeId())) {
            return Result.error("-1", "所属专业不能为空");
        }
        teacherInfoService.add(teacherInfo);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody TeacherInfo teacherInfo) {
        if (ObjectUtil.isEmpty(teacherInfo.getName())) {
            return Result.error("-1", "姓名不能为空");
        }
        if (ObjectUtil.isEmpty(teacherInfo.getSex())) {
            return Result.error("-1", "性别不能为空");
        }
        if (ObjectUtil.isEmpty(teacherInfo.getAge())) {
            return Result.error("-1", "年龄不能为空");
        }
        if (ObjectUtil.isEmpty(teacherInfo.getZhicheng())) {
            return Result.error("-1", "职称不能为空");
        }
        teacherInfoService.update(teacherInfo);
        return Result.success();
    }
    @GetMapping
    public Result findAll() {
        List<TeacherInfo> list = teacherInfoService.findAll();
        return Result.success(list);
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        teacherInfoService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<TeacherInfo> pageInfo = teacherInfoService.findPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }
    @GetMapping("/{search}")
    public Result findPageSearch(@PathVariable String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<TeacherInfo> pageInfo = teacherInfoService.findPageSearch(search, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
