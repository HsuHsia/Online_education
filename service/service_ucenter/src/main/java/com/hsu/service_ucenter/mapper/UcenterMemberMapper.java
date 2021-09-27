package com.hsu.service_ucenter.mapper;

import com.hsu.service_ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-13
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    int getRegisterInfo(String date);
}
