package com.spring.webadmin.module.main;

import com.spring.common.utils.HttpUtil;
import com.spring.webadmin.anno.NoNeedLogin;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.tools.MonipdbUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@Api(tags = "项目启动")
@RequestMapping(Route.main)
public class MainController {
    @ApiOperation(value = "success run", notes = "项目启动-成功")
    @GetMapping(Route.main)
    @NoNeedLogin
    public String star(HttpServletRequest request) {
        String ip = HttpUtil.getRemoteAddr(request);
        String ipAddress = MonipdbUtils.ip2Address(ip);
        return "admin successful startup；your ip is " + ip + " " + ipAddress;
    }
}
