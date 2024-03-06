package org.yuxiang.jin.officeauto.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuxiang.jin.officeauto.service.OfficeautoService;

@Controller
public class LoginController {
    //定义业务层对象
    //by type注入
    @Resource
    private OfficeautoService officeautoService;

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("tip", "欢迎访问智能办公系统");
        return "redirect:/oa/login";
    }

    //异步请求的响应结果
    @ResponseBody
    @RequestMapping(value = "/loginAjax", produces = "application/json; charset=UTF-8")
    public Map<String, Object> login(@RequestParam("userId") String userId, @RequestParam("passWord") String passWord,
            @RequestParam("vcode") String vcode, HttpSession session) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("passWord", passWord);
            params.put("vcode", vcode);
            params.put("session", session);
            Map<String, Object> result = officeautoService.login(params);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
