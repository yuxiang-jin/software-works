package org.yuxiang.jin.officeauto.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yuxiang.jin.officeauto.commonutil.pager.PageModel;
import org.yuxiang.jin.officeauto.domain.Role;
import org.yuxiang.jin.officeauto.domain.User;
import org.yuxiang.jin.officeauto.service.OfficeautoService;

@Controller
@RequestMapping(value = "/oa/role")
public class RoleController {
    //定义业务层对象
    //by type
    @Resource
    private OfficeautoService officeautoService;

    @RequestMapping(value = "/selectRole")
    public String selectRole(PageModel pageModel, Model model) {
        try {
            //TODO: 查询角色异常, Caused by: org.springframework.data.mapping.PropertyReferenceException: No property id
            // found for type Role!
            List<Role> roles = officeautoService.getRoleByPager(pageModel);
            model.addAttribute("roles", roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/role/role";
    }

    @RequestMapping(value = "/addRole")
    public String addRole(Role role, Model model) {
        try {
            officeautoService.addRole(role);
            model.addAttribute("tip", "添加成功");
            model.addAttribute("role", role);
        } catch (Exception e) {
            model.addAttribute("tip", "添加失败");
            e.printStackTrace();
        }
        return "oa/role/addRole";
    }

    @RequestMapping(value = "/deleteRole")
    public String deleteRole(String ids, Model model) {
        try {
            officeautoService.deleteRole(ids);
            model.addAttribute("tip", "删除成功");
        } catch (Exception e) {
            model.addAttribute("tip", "删除失败");
            e.printStackTrace();
        }
        return "forward:/oa/role/selectRole";
    }

    @RequestMapping(value = "/showAddRole")
    public String showAddRole() {
        return "oa/role/addRole";
    }

    @RequestMapping(value = "/showUpdateRole")
    public String showUpdateRole(Role role, Model model) {
        try {
            role = officeautoService.getRoleById(role.getRoleId());
            model.addAttribute("role", role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/role/updateRole";
    }

    @RequestMapping(value = "/updateRole")
    public String updateRole(Role role, Model model) {
        try {
            officeautoService.updateRole(role);
            model.addAttribute("tip", "修改成功");
            model.addAttribute("role", role);
        } catch (Exception e) {
            model.addAttribute("tip", "修改失败");
            e.printStackTrace();
        }
        return "oa/role/updateRole";
    }

    @RequestMapping(value = "/selectRoleUser")
    public String selectRoleUser(Role role, PageModel pageModel, Model model) {
        try {
            List<User> users = officeautoService.selectRoleUser(role, pageModel);
            role = officeautoService.getRoleById(role.getRoleId());
            model.addAttribute("users", users);
            model.addAttribute("role", role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/role/bindUser/roleUser";
    }

    @RequestMapping(value = "/showBindUser")
    public String selectNotRoleUser(Role role, PageModel pageModel, Model model) {
        try {
            //查询不属于这个角色的所有用户
            List<User> users = officeautoService.selectNotRoleUser(role, pageModel);
            role = officeautoService.getRoleById(role.getRoleId());
            model.addAttribute("users", users);
            model.addAttribute("role", role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/role/bindUser/bindUser";
    }

    @RequestMapping(value = "/bindUser")
    public String bindUser(Role role, String ids, Model model) {
        try {
            officeautoService.bindUser(role, ids);
            model.addAttribute("tip", "绑定成功");
            model.addAttribute("role", role);
        } catch (Exception e) {
            model.addAttribute("tip", "绑定失败");
            e.printStackTrace();
        }
        return "forward:/oa/role/showBindUser";
    }

    @RequestMapping(value = "/unbindUser")
    public String unbindUser(Role role, String ids, Model model) {
        try {
            officeautoService.unBindUser(role, ids);
            model.addAttribute("tip", "解绑成功");
            model.addAttribute("role", role);
        } catch (Exception e) {
            model.addAttribute("tip", "解绑失败");
            e.printStackTrace();
        }
        return "forward:/oa/role/selectRoleUser";
    }
}
