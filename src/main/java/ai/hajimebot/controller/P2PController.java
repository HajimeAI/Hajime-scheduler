package ai.hajimebot.controller;

import ai.hajimebot.global.LogEventUtils;
import ai.hajimebot.global.R;
import ai.hajimebot.pojo.EventBean;
import bsh.StringUtil;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@ApiIgnore
@RestController
@RequestMapping("/p2p/")
public class P2PController {
    @PostMapping("send_hash_event")
    @SaIgnore
    public R<?> doLogin(@RequestBody Map<String, Object> map, @RequestHeader(name = "X-Auth") String xAuth) {
        try {
            if (StrUtil.isEmpty(xAuth) || !"iYO/yDEaVm6iAg==".equals(xAuth)) {
                return R.fail("Auth failed");
            }

            EventBean eventBean = BeanUtil.toBean(map, EventBean.class);
            if (LogEventUtils.EVIDENCE.equals(eventBean.getKind())){
                SpringUtil.getApplicationContext().publishEvent(eventBean);
                return R.ok("event send success");
            }
        } catch (Exception e) {
            return R.fail("event send failed");
        }
        return R.fail("event send failed");
    }
}
