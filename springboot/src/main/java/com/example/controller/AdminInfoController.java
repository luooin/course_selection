package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.AdminInfo;
import com.example.service.AdminInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/adminInfo")
public class AdminInfoController {
    @Resource
    private AdminInfoService adminInfoService;

    @PostMapping
    public Result add(@RequestBody AdminInfo adminInfo) {
        if (ObjectUtil.isEmpty(adminInfo.getName())) {
            return Result.error("-1", "姓名不能为空");
        }
        if (ObjectUtil.isEmpty(adminInfo.getSex())) {
            return Result.error("-1", "性别不能为空");
        }
        if (ObjectUtil.isEmpty(adminInfo.getAge())) {
            return Result.error("-1", "年龄不能为空");
        }
        if (ObjectUtil.isEmpty(adminInfo.getPhone())) {
            return Result.error("-1", "电话不能为空");
        }
        adminInfoService.add(adminInfo);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody AdminInfo adminInfo) {
        if (ObjectUtil.isEmpty(adminInfo.getName())) {
            return Result.error("-1", "姓名不能为空");
        }
        if (ObjectUtil.isEmpty(adminInfo.getSex())) {
            return Result.error("-1", "性别不能为空");
        }
        if (ObjectUtil.isEmpty(adminInfo.getAge())) {
            return Result.error("-1", "年龄不能为空");
        }
        if (ObjectUtil.isEmpty(adminInfo.getPhone())) {
            return Result.error("-1", "电话不能为空");
        }
        adminInfoService.update(adminInfo);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<AdminInfo> list = adminInfoService.findAll();
        return Result.success(list);
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        adminInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<AdminInfo> info = adminInfoService.findPage(pageNum, pageSize);
        return Result.success(info);
    }

    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @PathVariable String name) {
        PageInfo<AdminInfo> info = adminInfoService.findPageName(pageNum, pageSize, name);
        return Result.success(info);
    }
}
