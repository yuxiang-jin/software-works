package org.yuxiang.jin.officeauto.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuxiang.jin.officeauto.commonutil.pager.PageModel;
import org.yuxiang.jin.officeauto.domain.Module;
import org.yuxiang.jin.officeauto.service.OfficeautoService;
import org.yuxiang.jin.officeauto.vo.TreeData;

/**
 * 模块菜单
 */
@Controller
@RequestMapping("/oa/module")
public class ModuleController {
    //by type
    @Resource
    private OfficeautoService officeautoService;

    @RequestMapping(value = "/mgrModule")
    public String mgrModule() {
        return "oa/module/mgrModule";
    }

    @ResponseBody
    @RequestMapping(value = "/loadAllModuleTrees", produces = "application/json; charset=UTF-8")
    public List<TreeData> loadAllModuleTrees() {
        try {
            return officeautoService.loadAllModuleTrees();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getModulesByParent")
    public String getModulesByParent(String parentCode, PageModel pageModel, Model model) {
        try {
            List<Module> sonModules = officeautoService.getModulesByParent(parentCode, pageModel);
            model.addAttribute("modules", sonModules);
            model.addAttribute("parentCode", parentCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/module/module";
    }

    @RequestMapping(value = "/deleteModules")
    public String deleteModules(String ids, Model model) {
        try {
            officeautoService.deleteModules(ids);
            model.addAttribute("tip", "删除成功");
        } catch (Exception e) {
            model.addAttribute("tip", "删除失败");
            e.printStackTrace();
        }
        return "forward:/oa/module/getModulesByParent";
    }

    @RequestMapping(value = "/addModule")
    public String addModule(String parentCode, Module module, Model model) {
        try {
            officeautoService.addModule(parentCode, module);
            model.addAttribute("tip", "添加成功");
            model.addAttribute("parentCode", parentCode);
            model.addAttribute("module", module);
        } catch (Exception e) {
            model.addAttribute("tip", "添加失败");
            e.printStackTrace();
        }
        return "oa/module/addModule";
    }

    @RequestMapping(value = "/updateModule")
    public String updateModule(Module module, Model model) {
        try {
            officeautoService.updateModule(module);
            model.addAttribute("tip", "修改成功");
        } catch (Exception e) {
            model.addAttribute("tip", "修改失败");
            e.printStackTrace();
        }
        return "oa/module/updateModule";
    }

    @RequestMapping(value = "/showAddModule")
    public String showAddModule(String parentCode, Model model) {
        model.addAttribute("parentCode", parentCode);
        return "oa/module/addModule";
    }

    @RequestMapping(value = "/showUpdateModule")
    public String showUpdateModule(Module module, Model model) {
        try {
            module = officeautoService.getModuleByCode(module.getModuleCode());
            model.addAttribute("module", module);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "oa/module/updateModule";
    }
}
