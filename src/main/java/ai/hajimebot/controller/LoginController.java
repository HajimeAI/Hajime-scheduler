package ai.hajimebot.controller;

import ai.hajimebot.entity.NodeVo;
import ai.hajimebot.entity.UserVo;
import ai.hajimebot.entity.table.NodeVoTableDef;
import ai.hajimebot.pojo.LoginUser;
import ai.hajimebot.pojo.LoginUserInfo;
import ai.hajimebot.service.NodeService;
import ai.hajimebot.service.UserService;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import ai.hajimebot.global.R;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/")
@Api(tags = "LoginController")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private NodeService nodeService;

    @PostMapping("doLogin")
    public R<?> doLogin(@RequestBody LoginUser loginUser) {
        if (!StrUtil.isBlank(loginUser.getUsername()) && !StrUtil.isBlank(loginUser.getPassword())) {

            UserVo userVo = userService.getUser(loginUser.getUsername(), loginUser.getPassword());
            if (BeanUtil.isNotEmpty(userVo)) {
                StpUtil.login(userVo.getId());
                List<String> roles = new ArrayList<>();
                roles.add("admin");
                return R.ok("login success", LoginUserInfo.builder()
                        .accessToken(StpUtil.getTokenValue())
                        .username(userVo.getUsername())
                        .roles(roles)
                        .refreshToken(StpUtil.getTokenValue())
                        .expires("2030/10/30 00:00:00")
                        .build());
            }
        }
        return R.fail("login failed");
    }

    @PostMapping("doLoginByWallet")
    public R<?> doLogin(@RequestBody Map<String, Object> map) {
        if (map.containsKey("publicKey") && !StrUtil.isBlank(StrUtil.toString(map.get("publicKey")))) {
            String publicKey = StrUtil.toString(map.get("publicKey"));
            NodeVo nodeVo = nodeService.getOne(QueryWrapper.create().where(NodeVoTableDef.NODE_VO.IMEI.eq(publicKey)));
            if (BeanUtil.isNotEmpty(nodeVo)) {
                UserVo userVo = UserVo.builder().username(nodeVo.getImei())
                        .password(nodeVo.getImei())
                        .status("1")
                        .role("normal")
                        .build();
                UserVo user = userService.getUser(nodeVo.getImei(), nodeVo.getImei());
                if (BeanUtil.isEmpty(user)) {
                    userService.save(userVo);
                } else {
                    userVo = user;
                }
                StpUtil.login(userVo.getId());
                List<String> roles = new ArrayList<>();
                roles.add(userVo.getRole());
                return R.ok("login success", LoginUserInfo.builder()
                        .accessToken(StpUtil.getTokenValue())
                        .username(userVo.getUsername())
                        .roles(roles)
                        .refreshToken(StpUtil.getTokenValue())
                        .expires("2030/10/30 00:00:00")
                        .build());
            }
        }
        return R.fail("login failed");
    }

    @GetMapping("isLogin")
    public String isLogin() {
        return "is login：" + StpUtil.isLogin();
    }

}

