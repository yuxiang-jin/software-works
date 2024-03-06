package org.yuxiang.jin.officeauto.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yuxiang.jin.officeauto.dto.UserModule;
import org.yuxiang.jin.officeauto.service.OfficeautoService;

@Controller
@RequestMapping("/oa")
public class RequestController {
    //定义业务层对象
    //by type
    @Resource
    private OfficeautoService officeautoService;

    @RequestMapping(value = "/login")
    public String requestLogin() {
        System.out.println("登录成功了!");
        return "login";
    }

    @RequestMapping(value = "/main")
    public String requestMain(Model model) {
        try {
            //查询当前用户的权限模块信息
            List<UserModule> userModules = officeautoService.getUserPopedomModules();
            model.addAttribute("userPopedomModules", userModules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "main";
    }

    @RequestMapping(value = "/home")
    public String requestHome() {
        return "home";
    }
}
