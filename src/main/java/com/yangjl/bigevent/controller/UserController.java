package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Result;
import com.yangjl.bigevent.entity.User;
import com.yangjl.bigevent.service.UserService;
import com.yangjl.bigevent.utils.JwtUtil;
import com.yangjl.bigevent.utils.Md5Util;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * POST 用户注册接口
     * @param username 5~16位非空字符组成的用户名
     * @param password 5~16位非空字符组成的密码
     * @return
     */
    @PostMapping("/register")
    public Result resigter(@Pattern(regexp = "^\\S{2,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已被占用");
        }
        userService.register(username, password);
        log.info("用户 {} 注册成功", username);
        return Result.success();
    }

    /**
     * POST 登录接口
     * @param username
     * @param password
     * @return token 令牌
     */
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{2,16}$") String username,
                        @Pattern(regexp = "^\\S{5,16}$") String password) {
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error("用户不存在");
        }

        String md5String = Md5Util.getMD5String(password);
        if(!user.getPassword().equals(md5String)) {
            return Result.error("密码错误");
        }

        Map<String, Object> claims = new HashMap<>(){{
            put("id", user.getId());
            put("username", user.getUsername());
        }};
        String token = JwtUtil.genToken(claims);
        redisTemplate.opsForValue().set("token_"+user.getId(), token, 1, TimeUnit.DAYS);
        log.info("用户 {} 登录成功", username);
        return Result.success(token);

    }

    /**
     * GET 获取用户详细信息
     * @param token 登录时下发的令牌
     * @return 用户的信息
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        log.info("用户: {}", user);
        return Result.success(user);
    }

    /**
     * PUT 更新用户基本信息
     * @param user 更新nickname,email
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        Map<String, Object> map = ThreadLocalUtil.get();
        if(!map.get("username").equals(user.getUsername())) {
            return Result.error("用户名不匹配");
        }
        userService.update(user);
        log.info("更新用户：{}", user);
        return Result.success();
    }

    /**
     * PATCH 更新用户头像
     * @param avatarUrl 头像URL
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        log.info("更新用户头像：{}", avatarUrl);
        return Result.success();
    }

    /**
     * PATCH 更新用户密码
     * @param params old_pwd:原密码
     *               new_pwd:新密码
     * @return
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        // 参数校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd)) {
            return Result.error("缺少必要的参数");
        }

        if(oldPwd.equals(newPwd)) {
            return Result.error("原密码与新密码相同");
        }

        if(!newPwd.matches("^\\S{5,16}$")) {
            return Result.error("密码必须由5~16位非空字符组成");
        }

        // 密码检查
        Map<String, Object> map = ThreadLocalUtil.get();
        User user = userService.findByUserName((String) map.get("username"));
        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }

        // 删除 redis 中原来的 token
        redisTemplate.delete("token_"+user.getId());

        // 修改密码
        userService.updatePwd(user.getId(), Md5Util.getMD5String(newPwd));
        log.info("用户 {} 的密码修改成功", map.get("username"));
        return Result.success();
    }
}
