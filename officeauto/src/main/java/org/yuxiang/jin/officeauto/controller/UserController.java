package org.yuxiang.jin.officeauto.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuxiang.jin.officeauto.commonutil.StringUtils;
import org.yuxiang.jin.officeauto.commonutil.pager.PageModel;
import org.yuxiang.jin.officeauto.commonutil.OaException;
import org.yuxiang.jin.officeauto.domain.User;
import org.yuxiang.jin.officeauto.service.OfficeautoService;

@Controller
@RequestMapping("/oa/user")
public class UserController {
    @Resource
    private OfficeautoService officeautoService;

    @RequestMapping(value = "/updateSelf")
    public String updateSelf(User user, Model model, HttpSession httpSession) {
        try {
            officeautoService.updateSelf(user, httpSession);
            model.addAttribute("tip", "修改成功");
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("tip", "修改失败");
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping(value = "/selectUser")
    public String selectUser(User user, HttpServletRequest request, PageModel pageModel, Model model) {
        try {
            //乱码处理：只需处理get请求的参数，post请求的参数不会乱码
            if (request.getMethod().toLowerCase().contains("get")) {
                if (user != null && !StringUtils.isEmpty(user.getUserName())) {
                    String name = user.getUserName();
                    //浏览器到后台乱码
                    name = new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    user.setUserName(name);
                } else {
                    throw new OaException("用户不存在！");
                }
            }
            List<User> users = officeautoService.getUsersByPager(user, pageModel);
            model.addAttribute("users", users);
        } catch (OaException e) {
            e.printStackTrace();
        }
        return "oa/user/user";
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(String ids, Model model) {
        try {
            //批量删除
            officeautoService.deleteUserByIds(ids);
            model.addAttribute("tip", "删除成功");
        } catch (Exception e) {
            model.addAttribute("tip", "删除失败");
            e.printStackTrace();
        }
        return "forward:/oa/user/selectUser";
    }

    @RequestMapping(value = "/showAddUser")
    public String showAddUser() {
        return "oa/user/addUser";
    }

    @ResponseBody
    @RequestMapping(value = "/isUserValidAjax")
    public String isUserValid(String userId) {
        try {
            return officeautoService.isUserValidAjax(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/addUser")
    public String addUser(User user, Model model) {
        try {
            officeautoService.addUser(user);
            model.addAttribute("tip", "添加成功");
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("tip", "添加失败");
            e.printStackTrace();
        }
        return "oa/user/addUser";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser(User user, Model model) {
        try {
            officeautoService.updateUser(user);
            model.addAttribute("tip", "修改成功");
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("tip", "修改失败");
            e.printStackTrace();
        }
        return "oa/user/updateUser";
    }

    @RequestMapping(value = "/activeUser")
    public String activeUser(User user, Model model) {
        try {
            officeautoService.activeUser(user);
            model.addAttribute("tip", user.getUserStatus() == 1 ? "激活成功" : "冻结成功");
        } catch (Exception e) {
            model.addAttribute("tip", user.getUserStatus() == 1 ? "激活失败" : "冻结失败");
            e.printStackTrace();
        }
        return "forward:/oa/user/selectUser";
    }

    @RequestMapping(value = "/showPreUser")
    public String showPreUser(String userId, Model model) {
        try {
            User user = officeautoService.getUserById(userId);
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/user/preUser";
    }

    @RequestMapping(value = "/showUpdateUser")
    public String showUpdateUser(String userId, Model model) {
        try {
            User user = officeautoService.getUserById(userId);
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/user/updateUser";
    }
}
