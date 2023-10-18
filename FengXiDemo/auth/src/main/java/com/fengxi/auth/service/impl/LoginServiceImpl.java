package com.fengxi.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fengxi.auth.dao.RoleMapper;
import com.fengxi.auth.dao.UserMapper;
import com.fengxi.auth.dto.CommonSettingDTO;
import com.fengxi.auth.dto.UserPageDTO;
import com.fengxi.auth.entity.DeyiRole;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.service.LoginService;
import com.fengxi.auth.utils.JwtUtil;
import com.fengxi.auth.utils.PageUtil;
import com.fengxi.auth.vo.ResultPageVO;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: LoginServiceImpl
 * @projectName demo
 * @date 2023/1/22 2:22:15
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private CommonSettingDTO commonSettingDTO;

    @Resource
    private HttpServletRequest request;

    @Override
    public DeyiUser login(DeyiUser user) {
        // 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticate)) {
            throw new BaseKnowException(10000, "登录失败");
        }

        DeyiUser deyiUser = userMapper.selectOne(new LambdaQueryWrapper<DeyiUser>()
                .eq(DeyiUser::getAccount, user.getAccount())
                .eq(DeyiUser::getIsDeleted, false)
        );
        deyiUser.setPassword(null);//置空密码
        // 生成token并返回前端
        String token = JwtUtil.createToken(deyiUser.getId(), deyiUser.getAccount(), commonSettingDTO.getTokenKey(), commonSettingDTO.getTokenTimeOut());
        deyiUser.setToken(token);

        // 写入权限菜单
//        if (!Objects.isNull(deyiUser.getRoleId())) {
//            List<MenuTreeVO> menuTreesByRoleId = menuService.getMenuTreesByRoleId(deyiUser.getRoleId());
//            deyiUser.setMenuTress(menuTreesByRoleId);
//        } else {
//            deyiUser.setMenuTress(new ArrayList<>());
//        }

        // 写入redis
        stringRedisTemplate.opsForValue().set(commonSettingDTO.getRedisUserKey() + ":" + deyiUser.getId(),
                JSONObject.toJSONString(deyiUser),
                commonSettingDTO.getTokenTimeOut(),
                TimeUnit.MINUTES
        );
        return deyiUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addUser(DeyiUser user) {

        List<DeyiUser> deyiUsers = userMapper.selectList(new LambdaQueryWrapper<DeyiUser>()
                .eq(DeyiUser::getIsDeleted, false)
                .eq(DeyiUser::getAccount, user.getAccount())
        );

        if (deyiUsers.size() > 0) {
            throw new BaseKnowException(10000, "当前账号已存在");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insert(user);
        }
        return "Success";
    }

    @Override
    public String logout() {
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseToken(token, commonSettingDTO.getTokenKey());
        String userId = claims.get("userId").toString();
        // 删除redis中的值
        stringRedisTemplate.delete(commonSettingDTO.getRedisUserKey() + ":" + userId);
        return "Success";
    }

    @Override
    public DeyiUser getCurrentUser() {
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseToken(token, commonSettingDTO.getTokenKey());
        String userId = claims.get("userId").toString();
        DeyiUser deyiUser = userMapper.selectById(userId);
        return deyiUser;
    }

    @Override
    public List<DeyiUser> getAllUser() {
        return userMapper.selectList(new LambdaQueryWrapper<DeyiUser>().eq(DeyiUser::getIsDeleted, false));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateUser(DeyiUser data) {

        DeyiUser user = userMapper.selectOne(new LambdaQueryWrapper<DeyiUser>()
                .eq(DeyiUser::getIsDeleted, false)
                .eq(DeyiUser::getAccount, data.getAccount())
        );

        if (user == null)
            throw new BaseKnowException(10000, "该用户不存在");

        if (data.getPassword() != null && !data.getPassword().equals("") && !data.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(data.getPassword()));
        }
//        user.setDepartmentIds(data.getDepartmentIds());
//        user.setAccount(data.getAccount());
//        user.setSex(data.getSex());
//        user.setNickName(data.getNickName());
//        user.setPhone(data.getPhone());
//        user.setRoleIds(data.getRoleIds());

        userMapper.update(new DeyiUser(), new LambdaUpdateWrapper<DeyiUser>()
                .eq(DeyiUser::getId, user.getId())
                .set(DeyiUser::getDepartmentIds, data.getDepartmentIds())
                .set(DeyiUser::getAccount, data.getAccount())
                .set(DeyiUser::getSex, data.getSex())
                .set(DeyiUser::getNickName, data.getNickName())
                .set(DeyiUser::getPhone, data.getPhone())
                .set(DeyiUser::getRoleIds, data.getRoleIds())
                .set(DeyiUser::getPassword, user.getPassword())
        );
        return "Success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteUser(Long id) {
        userMapper.update(new DeyiUser(), new LambdaUpdateWrapper<DeyiUser>()
                .set(DeyiUser::getIsDeleted, true)
                .eq(DeyiUser::getId, id));
        return "Success";
    }

    @Override
    public ResultPageVO<DeyiUser> queryUserData(UserPageDTO query) {
        if (query == null) {
            query = new UserPageDTO();
        }
        query.setPage(PageUtil.getStartPage(query.getPage(), query.getPageSize()));
        List<DeyiUser> deyiUsers = userMapper.queryUserData(query);

        // 放入角色
        for (DeyiUser deyiUser : deyiUsers) {
            if (!Objects.isNull(deyiUser.getRoleIds())) {
                List<Long> collect = Arrays.stream(deyiUser.getRoleIds().split(",")).map(Long::valueOf).collect(Collectors.toList());
                List<DeyiRole> deyiRoles = roleMapper.selectBatchIds(collect);
                deyiUser.setRoleList(deyiRoles.stream().map(DeyiRole::getRoleName).collect(Collectors.toList()));
            } else {
                deyiUser.setRoleList(new ArrayList<String>());
            }
        }

        Integer count = userMapper.queryUserCount(query);
        return ResultPageVO.returnData(deyiUsers, count);
    }

    @Override
    public String updatePassword(DeyiUser user) {
        DeyiUser currentUser = getCurrentUser();
        // 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(currentUser.getAccount(), user.getOldPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new BaseKnowException(10000, "密码错误");
        }
        // 修改密码
        userMapper.update(new DeyiUser(), new LambdaUpdateWrapper<DeyiUser>()
                .set(DeyiUser::getPassword, passwordEncoder.encode(user.getNewPassword()))
                .eq(DeyiUser::getId, currentUser.getId())
        );

        return "Success";
    }

}
