package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantPropertiesUtil;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import com.sun.jndi.toolkit.url.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @Author liuxing
 * @Date 2020/9/7 10:52
 * @Version 1.0
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    UcenterMemberService ucenterMemberService;

    @GetMapping("/login")
    public String getCode() {
        String url = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=atguigu#wechat_redirect";

        String redirectUrl = null;
        try {
            redirectUrl = UrlUtil.encode(ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        redirectUrl = String.format(url, ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl);

        return "redirect:" + redirectUrl;
    }


    //http://localhost:8150/api/ucenter/wx/callback?code=0418VRFa1QjmAz0fntJa1TxtuL28VRFw&state=atguigu
    @GetMapping("/callback")
    public String calback(String code,
                          String state) {
        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        // 通过 code 获取 access token 等信息
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String newUrl = String.format(url, ConstantPropertiesUtil.WX_OPEN_APP_ID, ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);
        String accessTokenInfo = null;
        try {
            accessTokenInfo = HttpClientUtils.get(newUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
        Object access_token = map.get("access_token");
//        Object refresh_token = map.get("refresh_token");
        Object openid = map.get("openid");

        // 保存或查找该用户
        UcenterMember member = ucenterMemberService.getByOpenId((String) openid);
        if (member == null) {
            String getInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String baseInfoUrl = String.format(getInfoUrl, access_token, openid);
            String baseInfo = null;
            try {
                baseInfo = HttpClientUtils.get(baseInfoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HashMap infoMap = gson.fromJson(baseInfo, HashMap.class);

            member = new UcenterMember();
            member.setOpenid((String) openid);
            member.setNickname((String) infoMap.get("nickname"));
            member.setAvatar((String) infoMap.get("headimgurl"));
            ucenterMemberService.save(member);
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }

}
