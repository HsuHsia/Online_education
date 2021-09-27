package com.hsu.service_ucenter.service;

import com.hsu.service_ucenter.entity.RegisterVo;
import com.hsu.service_ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-13
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getMemberByOpenId(String openid);

    int getRegisterInfo(String date);
}
