package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVO;
import com.atguigu.educenter.entity.vo.RegistVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author cxing
 * @since 2020-09-03
 */
@Service
public interface UcenterMemberService extends IService<UcenterMember> {

    String saveMember(RegistVO registVO);

    String login(LoginVO loginVO);

    UcenterMember getByOpenId(String openid);

    Integer getRegisterCounter(String day);
}
