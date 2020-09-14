package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-09-01
 */
@RestController
@RequestMapping("/banner/front")
public class BannerFrontController {

    @Autowired
    CrmBannerService bannerService;

    @GetMapping("/list")
    public R getBanner() {
        List<CrmBanner> bannerList = bannerService.listAllBanner();
        return R.ok().data("items", bannerList);
    }

}

