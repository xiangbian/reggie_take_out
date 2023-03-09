package com.xiangbian.reggie.controller;


import com.xiangbian.reggie.common.R;
import com.xiangbian.reggie.entity.User;
import com.xiangbian.reggie.service.UserService;
import com.xiangbian.reggie.utils.SMSUtils;
import com.xiangbian.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            //随机生成4位数验证码
            String code = ValidateCodeUtils.generateValidateCode4String(4);
            //调用阿里云的短信服务API完成短信发送
           // SMSUtils.sendMessage("","",phone,code);
            log.info("验证码：" + code);

            //将生成的验证码保存到session
            session.setAttribute(phone,code);

            return R.success("验证码发送成功");
        }
        return R.error("发送失败");
    }

}
