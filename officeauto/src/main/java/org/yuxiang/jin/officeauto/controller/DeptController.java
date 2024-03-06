package org.yuxiang.jin.officeauto.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuxiang.jin.officeauto.domain.Dept;
import org.yuxiang.jin.officeauto.service.OfficeautoService;

@Controller
@RequestMapping("/oa/dept")
public class DeptController {
    @Resource
    private OfficeautoService officeautoService;

    @RequestMapping(value = "/selectAll")
    public String selectAll(Model model) {
        try {
            List<Dept> depts = officeautoService.getAllDepts();
            model.addAttribute("depts", depts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 为部门添加物理层视图资源
        return "main";
    }

    //异步方法，写数据回去
    @ResponseBody
    @RequestMapping(value="/getAllDeptsAndJobsAjax",produces = "application/json; charset=UTF-8")
    public Map<String,Object> getAllDeptsAndJobsAjax() {
        try {
            Thread.sleep(2000);
            Map<String, Object> allDeptsAndJobs = officeautoService.getAllDeptsAndJobsAjax();
            System.out.println("所有的部门和职位信息：" + allDeptsAndJobs);
            return allDeptsAndJobs;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
