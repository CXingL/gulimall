package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author liuxing
 * @Date 2020/9/1 09:49
 * @Version 1.0
 */
@RestController
@RequestMapping("/banner/admin")
public class BannerAdminController {

    @Autowired
    CrmBannerService bannerService;

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@PathVariable Long id) {
        bannerService.removeById(id);
        return R.ok();
    }

    @GetMapping("/getBanner/{page}/{limit}")
    public R getBanner(@PathVariable("page") Long page,
                       @PathVariable("limit") Long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        this.bannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }
}
