package com.laozhang.diners.controller;

import com.laozhang.commons.model.domain.ResultInfo;
import com.laozhang.diners.service.DinersService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 食客服务控制层
 */
@RestController
@Api(tags = "食客相关接口")
public class DinersController {

    @Resource
    private DinersService dinersService;

    @Resource
    private HttpServletRequest request;

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @GetMapping("signin")
    public ResultInfo signIn(String account, String password) {
        return dinersService.signIn(account, password, request.getServletPath());
    }

}
