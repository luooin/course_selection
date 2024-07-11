package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.ZhuanyeInfo;
import com.example.service.ZhuanyeInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/zhuanyeInfo")
public class ZhuanyeInfoController {

    @Resource
    private ZhuanyeInfoService zhuanyeInfoService;

    @PostMapping
    public Result add(@RequestBody ZhuanyeInfo zhuanyeInfo) {
        if (ObjectUtil.isEmpty(zhuanyeInfo.getName())) {
            return Result.error("-1", "专业名称不能为空");
        }
        if (ObjectUtil.isEmpty(zhuanyeInfo.getDepartment())) {
            return Result.error("-1", "系名不能为空");
        }
        if (ObjectUtil.isEmpty(zhuanyeInfo.getXueyuanId())) {
            return Result.error("-1", "所属学院不能为空");
        }
        zhuanyeInfoService.add(zhuanyeInfo);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ZhuanyeInfo zhuanyeInfo) {
        if (ObjectUtil.isEmpty(zhuanyeInfo.getName())) {
            return Result.error("-1", "专业名称不能为空");
        }
        if (ObjectUtil.isEmpty(zhuanyeInfo.getDepartment())) {
            return Result.error("-1", "系名不能为空");
        }

        zhuanyeInfoService.update(zhuanyeInfo);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<ZhuanyeInfo> list = zhuanyeInfoService.findAll();
        return Result.success(list);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        zhuanyeInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{search}")
    public Result findSearch(@PathVariable String search) {
        List<ZhuanyeInfo> list = zhuanyeInfoService.findSearch(search);
        return Result.success(list);
    }

}
