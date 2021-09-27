package com.hsu.service_ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsu.commonutils.JwtUtils;
import com.hsu.commonutils.MD5;
import com.hsu.service_ucenter.entity.RegisterVo;
import com.hsu.service_ucenter.entity.UcenterMember;
import com.hsu.service_ucenter.mapper.UcenterMemberMapper;
import com.hsu.service_ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsu.servicebase.exceptionhandler.SummerException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author HsuHsia
 * @since 2021-09-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    // 登录
    @Override
    public String login(UcenterMember member) {
        String userPhoneNumber = member.getMobile();
        String pwd = member.getPassword();
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(userPhoneNumber)){
            throw new SummerException(20001, "用户名或密码错误");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapperPhone = new QueryWrapper<>();
        wrapperPhone.eq("mobile", userPhoneNumber);
        UcenterMember mobileMember = baseMapper.selectOne(wrapperPhone);
        if (mobileMember == null) {
            throw new SummerException(20001, "用户名或密码错误");
        }

        // 判断密码是否正确
        // 加密方式 MD5
        if (!MD5.encrypt(pwd).equals(mobileMember.getPassword())){
            throw new SummerException(20001, "用户名或密码错误");
        }

        // 判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new SummerException(20001, "此账号已被封禁");
        }
        // 使用工具类JwtUtils生成token字符串
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return token;
    }

    // 注册
    @Override
    public void register(RegisterVo registerVo) {
        String nickName = registerVo.getNickname();
        String password = registerVo.getPassword();
        String mobile = registerVo.getMobile();
        String verificationCode = registerVo.getCode();

        if (StringUtils.isEmpty(nickName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(verificationCode)){
            throw new SummerException(20001, "请完善注册信息");
        }

        // 判断验证码是否正确
        String redisVerificationCode = redisTemplate.opsForValue().get(mobile);
        System.out.println(redisVerificationCode);
        if (!verificationCode.equals(redisVerificationCode)) {
            throw new SummerException(20001,"验证码错误");
        }

        // 判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new SummerException(20001, "该手机号已注册");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setNickname(nickName);
        member.setAvatar("https://summer-hsu.oss-cn-chengdu.aliyuncs.com/2021/09/13/792356c0b7fa43689082c7c6577c0323default.png");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getMemberByOpenId(String openid) {
        QueryWrapper <UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public int getRegisterInfo(String date) {
        return baseMapper.getRegisterInfo(date);
    }
}
