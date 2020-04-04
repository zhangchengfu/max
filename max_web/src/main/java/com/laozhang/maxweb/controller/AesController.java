package com.laozhang.maxweb.controller;

import com.laozhang.maxweb.base.AESCodec;
import com.laozhang.maxweb.base.ResponseDto;
import com.laozhang.maxweb.base.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/admin/sessions")
public class AesController {

    @GetMapping
    public ResponseDto getSign(@RequestParam("phone") final String phone) {
        String aeskey = "xZtgbhfoiaxqeyGo";
        String mobile = phone;
        String mobileStr = "mobile:" + mobile + ";";
        String sign = AESCodec.encrypt(mobileStr, aeskey);
        final Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("sign", sign);
        return ResponseUtil.wrapSuccess(jsonMap);
    }
}
