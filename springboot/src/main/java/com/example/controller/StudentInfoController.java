package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.AdminInfo;
import com.example.entity.StudentInfo;
import com.example.service.StudentInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/studentInfo")
public class StudentInfoController {

    @Resource
    private StudentInfoService studentInfoService;

    @PostMapping
    public Result add(@RequestBody StudentInfo studentInfo) {
        if (ObjectUtil.isEmpty(studentInfo.getName())) {
            return Result.error("-1", "姓名不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getSex())) {
            return Result.error("-1", "性别不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getAge())) {
            return Result.error("-1", "年龄不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getCode())) {
            return Result.error("-1", "学号不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getScore())) {
            return Result.error("-1", "学分不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getXueyuanId())) {
            return Result.error("-1", "所属学院不能为空");
        }
        studentInfoService.add(studentInfo);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody StudentInfo studentInfo) {
        if (ObjectUtil.isEmpty(studentInfo.getName())) {
            return Result.error("-1", "姓名不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getSex())) {
            return Result.error("-1", "性别不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getAge())) {
            return Result.error("-1", "年龄不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getCode())) {
            return Result.error("-1", "学号不能为空");
        }
        if (ObjectUtil.isEmpty(studentInfo.getScore())) {
            return Result.error("-1", "学分不能为空");
        }
        studentInfoService.update(studentInfo);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<StudentInfo> list = studentInfoService.finAll();
        return Result.success(list);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        studentInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<StudentInfo> info = studentInfoService.findPage(pageNum, pageSize);
        return Result.success(info);
    }

    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @PathVariable String name) {
        PageInfo<StudentInfo> info = studentInfoService.findPageName(pageNum, pageSize, name);
        return Result.success(info);
    }

}
