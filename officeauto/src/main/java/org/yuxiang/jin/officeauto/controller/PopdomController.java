package org.yuxiang.jin.officeauto.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yuxiang.jin.officeauto.domain.Module;
import org.yuxiang.jin.officeauto.domain.Role;
import org.yuxiang.jin.officeauto.service.OfficeautoService;

@Controller
@RequestMapping("/oa/popedom")
public class PopdomController {
    //定义业务层对象
    //by type
    @Resource
    private OfficeautoService officeautoService;

    @RequestMapping(value = "/mgrPopedom")
    public String mgrPopedom(Role role, Model model) {
        try {
            role = officeautoService.getRoleById(role.getRoleId());
            model.addAttribute("role", role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/role/bindPopedom/mgrPopedom";
    }

    @RequestMapping(value = "/getOperasByParent")
    public String getOperasByParent(@RequestParam("parentCode") String parentCode,
            @RequestParam("moduleName") String moduleName, Role role, Model model) {
        try {
            List<Module> sonModules = officeautoService.getModulesByParent(parentCode);
            //去控制界面的权限显示，查询出当前角色(role)在当前模块下(parentCode)拥有哪些操作权限
            List<String> roleModuleOperasCodes = officeautoService.getRoleModuleOperasCodes(role, parentCode);
            model.addAttribute("modules", sonModules);
            model.addAttribute("moduleName", moduleName);
            model.addAttribute("parentCode", parentCode);
            model.addAttribute("role", role);
            model.addAttribute("roleModuleOperasCodes", roleModuleOperasCodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/role/bindPopedom/operas";
    }

    @RequestMapping(value = "/bindPopedom")
    public String bindPopedom(@RequestParam("operaCodes") String operaCodes,
            @RequestParam("parentCode") String parentCode, Role role, Model model) {
        try {
            officeautoService.bindPopedom(operaCodes, role, parentCode);
            model.addAttribute("tip", "绑定成功");
            model.addAttribute("operaCodes", operaCodes);
            model.addAttribute("parentCode", parentCode);
            model.addAttribute("role", role);
        } catch (Exception e) {
            model.addAttribute("tip", "绑定失败");
            e.printStackTrace();
        }
        return "forward:/oa/popedom/getOperasByParent";
    }
}
