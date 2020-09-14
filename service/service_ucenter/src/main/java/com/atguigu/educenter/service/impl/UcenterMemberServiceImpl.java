package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVO;
import com.atguigu.educenter.entity.vo.RegistVO;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-09-03
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String saveMember(RegistVO registVO) {
        String mobile = registVO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            throw new GuliException(20001, "注册失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        Integer count = baseMapper.selectCount(wrapper.eq("mobile", mobile));
        if (count > 0) {
            throw new GuliException(20001, "此手机号已注册");
        }

        String code = registVO.getCode();
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码不正确");
        }

        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(registVO.getMobile());
        ucenterMember.setPassword(MD5.encrypt(registVO.getPassword()));
        ucenterMember.setAvatar("https://upload.jianshu.io/users/upload_avatars/1821058/f2a247ca2423?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96/format/webp");
        ucenterMember.setIsDisabled(false);
        ucenterMember.setNickname(registVO.getNickName());
        baseMapper.insert(ucenterMember);

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    @Override
    public String login(LoginVO loginVO) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        if (!ucenterMember.getPassword().equals(MD5.encrypt(password))) {
            throw new GuliException(20001, "密码不正确");
        }

        if (ucenterMember.getIsDisabled()) {
            throw new GuliException(20001, "用户已被禁用");
        }

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    @Override
    public UcenterMember getByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        return baseMapper.selectOne(wrapper.eq("openid", openid));
    }

    @Override
    public Integer getRegisterCounter(String day) {
        Integer count = baseMapper.getRegisterCounter(day);
        return count;
    }
}
