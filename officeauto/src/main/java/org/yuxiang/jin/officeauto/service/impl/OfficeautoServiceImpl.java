package org.yuxiang.jin.officeauto.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yuxiang.jin.officeauto.commonutil.OaContants;
import org.yuxiang.jin.officeauto.commonutil.OaException;
import org.yuxiang.jin.officeauto.commonutil.StringUtils;
import org.yuxiang.jin.officeauto.commonutil.UserHolder;
import org.yuxiang.jin.officeauto.commonutil.CommonContants;
import org.yuxiang.jin.officeauto.commonutil.pager.PageModel;
import org.yuxiang.jin.officeauto.domain.Dept;
import org.yuxiang.jin.officeauto.domain.Module;
import org.yuxiang.jin.officeauto.domain.Popedom;
import org.yuxiang.jin.officeauto.domain.Role;
import org.yuxiang.jin.officeauto.domain.User;
import org.yuxiang.jin.officeauto.dto.UserModule;
import org.yuxiang.jin.officeauto.repository.DeptRepository;
import org.yuxiang.jin.officeauto.repository.JobRepository;
import org.yuxiang.jin.officeauto.repository.ModuleRepository;
import org.yuxiang.jin.officeauto.repository.PopedomRepository;
import org.yuxiang.jin.officeauto.repository.RoleRepository;
import org.yuxiang.jin.officeauto.repository.UserRepository;
import org.yuxiang.jin.officeauto.service.OfficeautoService;
import org.yuxiang.jin.officeauto.vo.TreeData;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */

//配置业务层的bean
@Service("officeautoService")
//@Transactional(readOnly = false, rollbackFor = java.lang.RuntimeException.class)
@Transactional(readOnly = true)
public class OfficeautoServiceImpl implements OfficeautoService {
    //注入Repository对象
    @Autowired
    @Qualifier("deptRepository")
    private DeptRepository deptRepository;

    @Autowired
    @Qualifier("jobRepository")
    private JobRepository jobRepository;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("moduleRepository")
    private ModuleRepository moduleRepository;

    @Autowired
    @Qualifier("roleRepository")
    private RoleRepository roleRepository;

    @Autowired
    @Qualifier("popedomRepository")
    private PopedomRepository popedomRepository;

    /**
     * 查询所有的部门
     * @return 查询结果
     */
    @Transactional(readOnly = true)
    @Override
    public List<Dept> getAllDepts() {
        try {
            List<Dept> depts = deptRepository.findAll();
            //获取延迟加载的属性，会话此时并没有关闭
            for (Dept dept : depts) {
                if (dept.getCreater() != null) {
                    dept.getCreater().getUserName();
                }
                if (dept.getModifier() != null) {
                    dept.getModifier().getUserName();
                }
            }
            return depts;
        } catch (Exception e) {
            throw new OaException("查询部门失败了", e);
        }
    }

    /**
     * 异步登录的业务层接口方法
     * @param params 传入一个Map<String, Object>参数
     * @return 返回一个Map<String, Object>对象
     */
    @Override
    public Map<String, Object> login(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            //处理登录的业务逻辑
            //1.参数非空校验
            String userId = (String) params.get("userId");
            String passWord = (String) params.get("passWord");
            String vcode = (String) params.get("vcode");
            HttpSession session = (HttpSession) params.get("session");
            //参数有为空的
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(passWord) || StringUtils.isEmpty(vcode)) {
                result.put("status", 0);
                result.put("tip", "参数有为空的");
            }
            //参数不为空
            else {
                /*
                * 校验验证码是否正确
                * 获取session中当前用户对应的验证码
                * */
                String sysCode = (String) session.getAttribute(CommonContants.VERIFY_SESSION);
                //验证码正确了
                if (vcode.equalsIgnoreCase(sysCode)) {
                    //根据登录的用户名去查询用户
                    User user = getUserById(userId);
                    //判断登录名是否存在
                    if (user != null) {
                        //登录名存在，判断密码是否正确
                        if (user.getUserPassword().equals(passWord)) {
                            //判断用户是否已经被激活了
                            if (user.getUserStatus() == 1) {
                                // 登录成功
                                // 1.把登录成功的用户放入当前用户的session会话中
                                session.setAttribute(OaContants.USER_SESSION, user);
                                System.out.println("设置用户 ---------------->：" + user);
                                result.put("status", 1);
                                result.put("tip", "登录成功");
                                // 把登录成功的用户存入到UserHolder
                                UserHolder.addCurrentUser(user);
                                // 2.当用户登录进入系统的时候，就应该立即去查询该用户所拥有的全部操作权限 --> 存入到当前用户的session会话中
                                Map<String, List<String>> userAllOperasPopedomUrls = getUserAllOperasPopedomUrls();
                                session.setAttribute(OaContants.USER_ALL_OPERAS_POPEDOM_URLS, userAllOperasPopedomUrls);
                            } else {
                                result.put("status", 5);
                                result.put("tip", "您的账户未被激活，请联系管理员激活！");
                            }
                        }
                        //密码错误
                        else {
                            result.put("status", 2);
                            result.put("tip", "密码错误了");
                        }
                    }
                    //登录名不存在
                    else {
                        result.put("status", 3);
                        result.put("tip", "没有该账户");
                    }
                }
                //验证码不正确
                else {
                    result.put("status", 4);
                    result.put("tip", "验证码不正确");
                }
            }
            return result;
        } catch (Exception e) {
            throw new OaException("异步登录业务层抛出异常了", e);
        }

    }

    /**
     * @return 该用户所拥有的全部操作权限
     */
    private Map<String, List<String>> getUserAllOperasPopedomUrls() {
        try {
            //查询用户所拥有的所有操作权限编码 [000100010001,000100010002]
            List<String> userAllPopedomOperasCodes = popedomRepository.getUserPopedomOperasCodes(UserHolder.getCurrentUser().getUserId());

            if (userAllPopedomOperasCodes != null && userAllPopedomOperasCodes.size() > 0) {
                Map<String, List<String>> userAllOperasPopedomUrls = new HashMap<>();
                String moduleUrl = "";
                List<String> moduleOperaUrls = null;
                for (String operaCode : userAllPopedomOperasCodes) {
                    //先得到模块的编号
                    String parentModuleCode = operaCode.substring(0, operaCode.length() - OaContants.CODE_LEN);
                    //父模块地址
                    moduleUrl = getModuleByCode(parentModuleCode).getModuleUrl();
                    //判断map集合中是否已经存在该父模块地址
                    if (!userAllOperasPopedomUrls.containsKey(moduleUrl)) {
                        moduleOperaUrls = new ArrayList<>();
                        userAllOperasPopedomUrls.put(moduleUrl, moduleOperaUrls);
                    }
                    moduleOperaUrls.add(getModuleByCode(operaCode).getModuleUrl());
                }
                return userAllOperasPopedomUrls;
            }
            return null;
        } catch (Exception e) {
            throw new OaException("登录后查询用户的所有操作权限时出现异常", e);
        }
    }

    /**
     * 根据用户的主键查询用户信息，包含了延迟加载的部门和职位信息
     * @param userId 用户表的主键
     * @return 查询得到的用户
     */
    @Override
    public User getUserById(String userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                //get一下就可以加载延迟加载的属性
                if (user.getDept() != null) user.getDept().getDeptName();
                if (user.getJob() != null) user.getJob().getJobName();
                if (user.getCreater() != null) user.getCreater().getUserName();
                if (user.getModifier() != null)  user.getModifier().getUserName();
                if (user.getChecker() != null) user.getChecker().getUserName();
            }
            return user;
        } catch (Exception e) {
            throw new OaException("查询用户失败了", e);
        }
    }

    /**
     * 修改用户信息
     * @param user 要修改的用户信息的值
     * @param session HttpSession的实例
     */
    @Override
    public void updateSelf(User user, HttpSession session) {
        try {
            //1.持久化修改
            User sessionUser = userRepository.findById(user.getUserId()).orElse(null);
            if (sessionUser != null) {
                sessionUser.setModifyDate(new Date());
                sessionUser.setModifier(user);
                sessionUser.setUserName(user.getUserName());
                sessionUser.setUserEmail(user.getUserEmail());
                sessionUser.setUserTel(user.getUserTel());
                sessionUser.setUserPhone(user.getUserPhone());
                sessionUser.setUserQuestion(user.getUserQuestion());
                sessionUser.setUserAnswer(user.getUserAnswer());
                sessionUser.setUserQqnum(user.getUserQqnum());
                //get一下就可以加载延迟加载的属性
                if(sessionUser.getDept() != null) sessionUser.getDept().getDeptName();
                if(sessionUser.getJob() != null) sessionUser.getJob().getJobName();
                session.setAttribute(OaContants.USER_SESSION, sessionUser);
            }
        } catch (Exception e) {
            throw new OaException("修改用户失败了", e);
        }
    }

    /**
     * @return 异步加载部门与职位的JSON字符串信息，写回页面
     */
    @Override
    public Map<String, Object> getAllDeptsAndJobsAjax() {
        try {
            //1.定义一个Map对象封装最终查询出来的部门信息和职位信息
            Map<String, Object> deptJobDatas = new HashMap<>();
            /*
            * 查询部门： id name
            * deptsList = [ {id=1, name="开发部"}, {id=2, name="销售部"} ]
            * */
            List<Map<String, Object>> deptsList = deptRepository.findDepts();
            /*
            * 查询职位： id name
            * jobList = [ {id=1, name="java工程师"}, {id=2, name="咨询师"}]
            * */
            List<Map<String, Object>> jobList = jobRepository.findJobs();

            deptJobDatas.put("depts", deptsList);
            deptJobDatas.put("jobs", jobList);

            return deptJobDatas;
        } catch (Exception e) {
            throw new OaException("查询部门与职位信息异常了", e);
        }
    }

    /**
     * 分页查询用户信息
     * @param user 要查询的用户信息
     * @param pageModel 自定义的分页实体
     * @return 查询得到的用户，是一个列表
     */
    @Override
    public List<User> getUsersByPager(User user, PageModel pageModel) {
        try {
            Page<User> usersPager = userRepository.findAll((Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
                //本集合用于封装查询条件
                List<Predicate> predicates = new ArrayList<>();
                if (user != null) {
                    //是否传入了姓名来查询
                    if (!StringUtils.isEmpty(user.getUserName())) {
                        predicates.add(criteriaBuilder.like(root.get("userName"), "%" + user.getUserName() + "%"));
                    }
                    //是否传入了手机号码来查询
                    if (!StringUtils.isEmpty(user.getUserPhone())) {
                        predicates.add(criteriaBuilder.like(root.get("userPhone"), "%" + user.getUserPhone() + "%"));
                    }
                    //是否传入了部门来查询
                    if (user.getDept() != null && user.getDept().getDeptId() != null && user.getDept().getDeptId() != 0) {
                        root.join("dept", JoinType.INNER);
                        Path<Long> deptId = root.get("dept").get("deptId");
                        predicates.add(criteriaBuilder.equal(deptId, user.getDept().getDeptId()));
                    }
                    //是否传入了职位来查询
                    if (user.getJob() != null && !StringUtils.isEmpty(user.getJob().getJobCode())
                            && !user.getJob().getJobCode().equals("0")) {
                        root.join("job", JoinType.INNER);
                        Path<String> jobCode = root.get("job").get("jobCode");
                        predicates.add(criteriaBuilder.equal(jobCode, user.getJob().getJobCode()));
                    }
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
            }, PageRequest.of(pageModel.getPageIndex() - 1, pageModel.getPageSize()));
            pageModel.setRecordCount(usersPager.getTotalElements());
            //get一下即可获取每个用户的延迟加载属性
            List<User> users = usersPager.getContent();
            for (User u : users) {
                if(u.getDept() != null) u.getDept().getDeptName();
                if(u.getJob() != null) u.getJob().getJobName();
                if(u.getChecker()!=null) u.getChecker().getUserName();
            }
            return users;
        } catch (Exception e) {
            throw new OaException("查询用户信息异常了", e);
        }
    }

    /**
     * 批量删除用户
     * @param ids 要删除的用户的主键
     */
    @Transactional
    @Override
    public void deleteUserByIds(String ids) {
        try {
            List<User> users = new ArrayList<>();
            for (String id : ids.split(",")) {
                User user = new User();
                user.setUserId(id);
                users.add(user);
            }
            userRepository.deleteInBatch(users);
        } catch (Exception e) {
            throw new OaException("删除用户信息异常了", e);
        }

    }

    /**
     * 校验用户是否已经被注册
     * @param userId 需要校验的用户的主键
     * @return 校验结果
     */
    @Override
    public String isUserValidAjax(String userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            return user != null ? "success" : "error";
        } catch (Exception e) {
            throw new OaException("校验用户登录名是否注册异常了", e);
        }
    }

    /**
     * 添加用户
     * @param user 需要添加的用户信息
     */
    @Override
    public void addUser(User user) {
        try {
            user.setCreateDate(new Date());
            user.setCreater(UserHolder.getCurrentUser());
            userRepository.save(user);
        } catch (Exception e) {
            throw new OaException("添加用户信息异常了", e);
        }
    }

    /**
     * 根据userId修改用户信息
     * @param user 传入的用于修改的用户信息
     */
    @Transactional
    @Override
    public void updateUser(User user) {
        try {
            //1.持久化修改
            User repositoryUser = userRepository.findById(user.getUserId()).orElse(null);
            if (repositoryUser != null) {
                repositoryUser.setModifyDate(new Date());
                repositoryUser.setModifier(UserHolder.getCurrentUser());
                repositoryUser.setUserPassword(user.getUserPassword());
                repositoryUser.setUserName(user.getUserName());
                repositoryUser.setDept(user.getDept());
                repositoryUser.setJob(user.getJob());
                repositoryUser.setUserEmail(user.getUserEmail());
                repositoryUser.setUserSex(user.getUserSex());
                repositoryUser.setUserTel(user.getUserTel());
                repositoryUser.setUserPhone(user.getUserPhone());
                repositoryUser.setUserQuestion(user.getUserQuestion());
                repositoryUser.setUserAnswer(user.getUserAnswer());
                repositoryUser.setUserQqnum(user.getUserQqnum());
            }
        } catch (Exception e) {
            throw new OaException("修改用户失败了", e);
        }
    }

    /**
     * 激活用户
     * @param user 需要被激活的用户信息
     */
    @Transactional
    @Override
    public void activeUser(User user) {
        try {
            User repositoryUser = userRepository.findById(user.getUserId()).orElse(null);
            if (repositoryUser != null) {
                repositoryUser.setCheckDate(new Date());
                repositoryUser.setChecker(UserHolder.getCurrentUser());
                repositoryUser.setUserStatus(user.getUserStatus());
            }
        } catch (Exception e) {
            throw new OaException("激活用户失败了", e);
        }
    }

    /**
     * 加载所有的模块树
     * @return 已加载的模块树，是一个列表
     */
    @Override
    public List<TreeData> loadAllModuleTrees() {
        try {
            //查询所有的模块信息
            List<Module> modules = moduleRepository.findAll();
            //拼装TreeData需要的树节点
            List<TreeData> treeDatas = new ArrayList<>();
            for (Module module : modules) {
                TreeData treeData = new TreeData();
                treeData.setId(module.getModuleCode());
                treeData.setName(module.getModuleName());
                //长度为4的编号的父节点是0
                //其余节点的父节点是从开始位置一直截取到总长度减去步长的位置。00010001的父节点是0001，000100010001的父节点是00010001
                int moduleCodeLen = module.getModuleCode().length();
                String pid = moduleCodeLen == OaContants.CODE_LEN ? "0"
                        : module.getModuleCode().substring(0, moduleCodeLen - OaContants.CODE_LEN);
                treeData.setPid(pid);
                treeDatas.add(treeData);
            }
            return treeDatas;
        } catch (Exception e) {
            throw new OaException("加载模块树异常", e);
        }
    }

    /**
     * 分页，根据父节点查询所有的子模块
     * @param parentCode 父节点编码
     * @param pageModel 自定义的分页实体
     * @return 查询得到的所有子模块，是一个列表
     */

    @Override
    public List<Module> getModulesByParent(String parentCode, PageModel pageModel) {
        try {
            parentCode = parentCode == null ? "" : parentCode;
            List<Object> values = new ArrayList<>();
            values.add(parentCode + "%");
            //子节点的编号的长度是父节点编号长度+步长
            //子节点前几位的编号必须与父节点编码一致
            values.add(parentCode.length() + OaContants.CODE_LEN);
            Page<Module> modulesPage = moduleRepository.findAll((Specification<Module>) (root, criteriaQuery, criteriaBuilder) -> {
                //本集合用于封装查询条件
                List<Predicate> predicates = new ArrayList<>();
                //是否传入了模块编码来查询
                predicates.add(criteriaBuilder.like(root.get("moduleCode"), values.get(0) + ""));
                //传入的模块编码长度是否相等
                predicates.add(criteriaBuilder.equal(criteriaBuilder.length(root.get("moduleCode")), values.get(1)));
                return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
            }, PageRequest.of(pageModel.getPageIndex() - 1, pageModel.getPageSize()));
            pageModel.setRecordCount(modulesPage.getTotalElements());
            List<Module> sonModules = modulesPage.getContent();
            for (Module m : sonModules) {
                //get一下即可获取每个用户的延迟加载属性
                if(m.getCreater() != null) m.getCreater().getUserName();
                if(m.getModifier() != null) m.getModifier().getUserName();
            }
            return sonModules;
        } catch (Exception e) {
            throw new OaException("查询子模块异常", e);
        }
    }

    /**
     * 不分页，根据父节点查询所有的子模块
     * @param parentCode 父节点编码
     * @return 查询得到的所有子模块，是一个列表
     */
    @Override
    public List<Module> getModulesByParent(String parentCode) {
        try {
            parentCode = parentCode == null ? "" : parentCode;
            List<Module> sonModules = moduleRepository.findModules(parentCode, parentCode.length() + OaContants.CODE_LEN);
            for (Module m : sonModules) {
                //get一下即可获取每个用户的延迟加载属性
                if(m.getCreater() != null) m.getCreater().getUserName();
                if(m.getModifier() != null) m.getModifier().getUserName();
            }
            return sonModules;
        } catch (Exception e) {
            throw new OaException("查询子模块异常", e);
        }
    }

    /**
     * 批量删除菜单
     * @param ids 要删除模块的主键
     */
    @Transactional
    @Override
    public void deleteModules(String ids) {
        try {
            for (String moduleCode : ids.split(",")) {
                moduleRepository.removeModule(moduleCode);
            }
        } catch (Exception e) {
            throw new OaException("批量删除菜单异常", e);
        }
    }

    /**
     * 为当前父节点菜单添加子节点模块
     * @param parentCode 父节点的编码
     * @param module 子节点的模块信息
     */
    @Transactional
    @Override
    public void addModule(String parentCode, Module module) {
        try {
            module.setModuleCode(getNextSonCode(parentCode, OaContants.CODE_LEN));
            module.setCreateDate(new Date());
            module.setCreater(UserHolder.getCurrentUser());
            moduleRepository.save(module);
        } catch (Exception e) {
            throw new OaException("添加子菜单异常", e);
        }
    }

    /**
     * 维护编号：通用工具类（提供一个父节点，提供一张表，提供某个字段，找出该字段该父节点下的下一个子节点的编号）
     * @param parentCode 父节点的编码
     * @param codeLen 节点步长，默认值为4
     * @return 子节点的编码
     */
    public String getNextSonCode(String parentCode, int codeLen) {
        //判断父节点是否为null
        parentCode = parentCode == null ? "" : parentCode;
        //1.查询当前父节点下的最大子节点编号
        String maxSonCode = moduleRepository.findUniqueEntity(parentCode + "%", parentCode.length() + codeLen);
        System.out.println("当前最大子节点编号是：maxSonCode---->" + maxSonCode);
        //保存最终的下一个子节点编号
        String nextSonCode = "";
        //2.判断最大子节点编号是否存在，因为极有可能父节点此时一个子节点都没有
        if (StringUtils.isEmpty(maxSonCode)) {
            //子节点编号不存在
            StringBuilder preSuffix = new StringBuilder();
            //需要拼接3个0
            for (int i = 1; i < codeLen; i++) {
                preSuffix.append("0");
            }
            nextSonCode = parentCode + preSuffix + 1;
        } else {
            //子节点编号存在 --> 000100010005,截取当前子节点编号的步长
            String currentMaxSonCode = maxSonCode.substring(parentCode.length());
            //得到子节点步长编号的整型形式
            int maxSonCodeInt = Integer.parseInt(currentMaxSonCode);
            maxSonCodeInt++;
            //判断编号是否越界
            if ((maxSonCodeInt + "").length() > codeLen) {
                throw new OaException("编号越界了！");
            } else {
                //没有越界
                StringBuilder preSuffix = new StringBuilder();
                //需要拼接多少个0
                for (int i = 0; i < codeLen - (maxSonCodeInt + "").length(); i++) {
                    preSuffix.append("0");
                }
                nextSonCode = parentCode + preSuffix + maxSonCodeInt;
            }
        }
        return nextSonCode;
    }

    /**
     * 根据模块编码查询模块信息
     * @param moduleCode 要查询的模块的编码
     * @return 查询得到的模块信息
     */
    @Override
    public Module getModuleByCode(String moduleCode) {
        try {
            return moduleRepository.findById(moduleCode).orElse(null);
        } catch (Exception e) {
            throw new OaException("查询模块异常", e);
        }
    }

    /**
     * 修改模块
     * @param module 传入的要修改的模块信息值
     */
    @Transactional
    @Override
    public void updateModule(Module module) {
        Module repositoryModule = moduleRepository.findById(module.getModuleCode()).orElse(null);
        if (repositoryModule != null) {
            try {
                repositoryModule.setModifier(UserHolder.getCurrentUser());
                repositoryModule.setModifyDate(new Date());
                repositoryModule.setModuleName(module.getModuleName());
                repositoryModule.setModuleRemark(module.getModuleRemark());
                repositoryModule.setModuleUrl(module.getModuleUrl());
            } catch (Exception e) {
                throw new OaException("修改模块异常", e);
            }
        }
    }

    /**
     * 分页查询角色信息
     * @param pageModel 自定义的分页实体
     * @return 查询得到的角色信息，是一个列表
     */
    @Override
    public List<Role> getRoleByPager(PageModel pageModel) {
        try {
            //指定排序参数对象：根据id升序查询
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            /*
            * 封装分页实体
            * 参数1：pageIndex表示当前查询第几页（默认从0开始，0表示第一页）
            * 参数2：表示每页展示多少条数据，现在设置每页展示2条数据
            * 参数3：封装排序对象，根据该对象的参数指定根据id升序查询
            * */
            Pageable page = PageRequest.of(pageModel.getPageIndex() - 1, pageModel.getPageSize(), sort);
            Page<Role> rolePager = roleRepository.findAll(page);
            pageModel.setRecordCount(rolePager.getTotalElements());
            //get一下即可获取每个用户的延迟加载属性
            List<Role> roles = rolePager.getContent();
            for (Role r : roles) {
                if(r.getModifier() != null) r.getModifier().getUserName();
                if(r.getCreater() != null) r.getCreater().getUserName();
            }
            return roles;
        } catch (Exception e) {
            throw new OaException("查询角色异常", e);
        }
    }

    /**
     * 添加角色
     * @param role 要添加的角色信息
     */
    @Transactional
    @Override
    public void addRole(Role role) {
        try {
            role.setCreateDate(new Date());
            role.setCreater(UserHolder.getCurrentUser());
            roleRepository.save(role);
        } catch (Exception e) {
            throw new OaException("添加角色异常", e);
        }
    }

    /**
     * 批量删除角色
     * @param ids 要删除角色的id列表
     */
    @Transactional
    @Override
    public void deleteRole(String ids) {
        try {
            List<Role> roles = new ArrayList<>();
            for (String id : ids.split(",")) {
                Role role = new Role();
                role.setRoleId(Long.parseLong(id));
                roles.add(role);
            }
            roleRepository.deleteInBatch(roles);
        } catch (NumberFormatException e) {
            throw new OaException("批量删除角色异常", e);
        }
    }

    /**
     * 根据roleId查询角色
     * @param roleId 要查询的角色的主键
     * @return 查询得到的角色信息
     */
    @Override
    public Role getRoleById(Long roleId) {
        try {
            return roleRepository.findById(roleId).orElse(null);
        } catch (Exception e) {
            throw new OaException("根据id查询角色异常", e);
        }
    }

    /**
     * 修改角色
     * @param role 需要修改的角色信息
     */
    @Transactional
    @Override
    public void updateRole(Role role) {
        Role repositoryRole = roleRepository.findById(role.getRoleId()).orElse(null);
        if (repositoryRole != null) {
            repositoryRole.setRoleName(role.getRoleName());
            repositoryRole.setRoleRemark(role.getRoleRemark());
            repositoryRole.setModifier(UserHolder.getCurrentUser());
            repositoryRole.setModifyDate(new Date());
        }
    }

    /**
     * 分页查询属于这个角色的用户信息
     * @param role 某个角色的信息
     * @param pageModel 分页实体
     * @return 某个角色的用户信息，是一个列表
     */
    @Override
    public List<User> selectRoleUser(Role role, PageModel pageModel) {
        try {
            Page<User> usersPager = userRepository.findAll((Specification<User>) (root, criteriaQuery, criteriaBulder) -> {
                //本集合用于封装查询条件
                List<Predicate> predicates = new ArrayList<>();
                List<String> userIds = userRepository.findRoleUsers(role.getRoleId());
                predicates.add(root.get("userId").in(userIds));
                return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
            }, PageRequest.of(pageModel.getPageIndex() - 1, pageModel.getPageSize()));
            pageModel.setRecordCount(usersPager.getTotalElements());
            List<User> users = usersPager.getContent();
            for (User u : users) {
                //get一下即可获取用户的延迟加载属性
                if(u.getDept() != null) u.getDept().getDeptName();
                if(u.getJob() != null) u.getJob().getJobName();
                if(u.getChecker() != null) u.getChecker().getUserName();
            }
            return users;
        } catch (Exception e) {
            throw new OaException("查询属于该角色的所有用户信息异常", e);
        }
    }

    /**
     * 分页查询不属于某个角色的用户信息
     * @param role 某个角色的信息
     * @param pageModel 分页实体
     * @return 不属于某个角色的用户信息，是一个列表
     */
    @Override
    public List<User> selectNotRoleUser(Role role, PageModel pageModel) {
        try {
            Page<User> usersPager = userRepository.findAll((Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
                //本集合用于封装查询条件
                List<Predicate> predicates = new ArrayList<>();
                //先查询出不属于这个角色的用户
                List<String> userIds = userRepository.getRolesUsers(role.getRoleId());
                predicates.add(root.get("userId").in(userIds));
                return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
            }, PageRequest.of(pageModel.getPageIndex() - 1, pageModel.getPageSize()));
            pageModel.setRecordCount(usersPager.getTotalElements());
            List<User> users = usersPager.getContent();
            for (User u : users) {
                //get一下即可获取用户的延迟加载属性
                if(u.getDept() != null) u.getDept().getDeptName();
                if(u.getJob() != null) u.getJob().getJobName();
                if(u.getChecker() != null) u.getChecker().getUserName();
            }
            return users;
        } catch (Exception e) {
            throw new OaException("查询不属于该角色的所有用户信息异常", e);
        }
    }

    /**
     * 给用户绑定角色
     * @param role 要绑定的角色信息
     * @param ids 要绑定角色的用户的主键列表
     */
    @Transactional
    @Override
    public void bindUser(Role role, String ids) {
        try {
            // 给角色绑定一批用户：
            // 1.先查询出该角色
            Role repositoryRole = roleRepository.findById(role.getRoleId()).orElse(null);
            // 2.给角色的users添加需要绑定的用户
            if (repositoryRole != null) {
                for (String userId : ids.split(",")) {
                    User user = userRepository.findById(userId).orElse(null);
                    repositoryRole.getUsers().add(user);
                }
            }
        } catch (Exception e) {
            throw new OaException("绑定该角色的用户异常", e);
        }
    }

    /**
     * 给用户解绑角色
     * @param role 要解绑的角色信息
     * @param ids 要解绑角色的用户的主键列表
     */
    @Override
    public void unBindUser(Role role, String ids) {
        try {
            // 给角色解绑一批用户：
            // 1.先查询出该角色
            Role repositoryRole = roleRepository.findById(role.getRoleId()).orElse(null);
            // 2.给角色的users删除需要解绑的用户
            if (repositoryRole != null) {
                for (String userId : ids.split(",")) {
                    User user = userRepository.findById(userId).orElse(null);
                    repositoryRole.getUsers().remove(user);
                }
            }
        } catch (Exception e) {
            throw new OaException("解绑该角色的用户异常", e);
        }
    }

    /**
     * 查询当前角色在当前模块下拥有的操作权限编码
     * @param role 某个角色的信息
     * @param parentCode 某个模块编码
     * @return 操作权限编码，是一个列表
     */
    @Override
    public List<String> getRoleModuleOperasCodes(Role role, String parentCode) {
        try {
            return popedomRepository.findByIdAndParentCode(role.getRoleId(), parentCode);
        } catch (Exception e) {
            throw new OaException("查询当前角色在当前模块下拥有的操作权限编号异常", e);
        }
    }

    /**
     * 给角色绑定某个模块下的操作权限
     * @param operaCodes 要绑定的操作权限
     * @param role 某个角色的信息
     * @param parentCode 某个模块的模块编码
     */
    @Transactional
    @Override
    public void bindPopedom(String operaCodes, Role role, String parentCode) {
        try {
            //先清空此角色在此模块下的所有操作权限
            popedomRepository.deleteByIdAndParentCode(role.getRoleId(), parentCode);
            //更新新的角色模块权限
            if (!StringUtils.isEmpty(operaCodes)) {
                Module parentModule = getModuleByCode(parentCode);
                //添加一些更新的权限
                for (String moduleCode : operaCodes.split(",")) {
                    //创建一个权限对象
                    Popedom popedom = new Popedom();
                    popedom.setRole(role);
                    popedom.setModule(parentModule);
                    popedom.setOpera(getModuleByCode(moduleCode));
                    popedom.setCreateDate(new Date());
                    popedom.setCreater(UserHolder.getCurrentUser());
                    popedomRepository.save(popedom);
                }
            }
        } catch (Exception e) {
            throw new OaException("给角色绑定某个模块的操作权限异常", e);
        }
    }

    /**
     * 查询当前用户的权限模块
     * @return 当前用户的权限模块信息，是一个列表
     */
    @Override
    public List<UserModule> getUserPopedomModules() {
        try {
            //查询当前用户的权限模块：先查用户所有的角色，再查这些角色拥有的所有权限模块
            List<String> popedomModuleCodes = popedomRepository.getUserPopedomModuleCodes(UserHolder.getCurrentUser().getUserId());
            if (popedomModuleCodes != null && popedomModuleCodes.size() > 0) {
                /*
                * 定义一个Map集合用于保存用户的权限模块
                * Map<Module, List<Module>>
                * {系统管理=[用户管理,角色管理],假期管理=[查询信息,用户请假]}
                * */
                Map<Module, List<Module>> userModulesMap = new LinkedHashMap<>();
                Module firstModule = null;
                List<Module> secondModules = null;
                for (String moduleCode : popedomModuleCodes) {
                    //截取当前模块的一级模块编号
                    String firstCode = moduleCode.substring(0, OaContants.CODE_LEN);
                    //查询出一级模块对象
                    firstModule = getModuleByCode(firstCode);
                    firstModule.setModuleName(firstModule.getModuleName().replaceAll("-", ""));
                    //如果map集合中没有包含当前一级模块的key,说明是第一次添加一级模块
                    if (!userModulesMap.containsKey(firstModule)) {
                        secondModules = new ArrayList<>();
                        userModulesMap.put(firstModule, secondModules);
                    }
                    Module secondModule = getModuleByCode(moduleCode);
                    secondModule.setModuleName(secondModule.getModuleName().replaceAll("-", ""));
                    secondModules.add(secondModule);
                }

                List<UserModule> userModules = new ArrayList<>();
                for (Map.Entry<Module, List<Module>> entry : userModulesMap.entrySet()) {
                    Module key = entry.getKey();
                    List<Module> value = entry.getValue();
                    UserModule userModule = new UserModule();
                    userModule.setFirstModule(key);
                    userModule.setSecondModules(value);
                    userModules.add(userModule);
                }
                return userModules;
            }
            return null;
        } catch (Exception e) {
            throw new OaException("查询当前用户的权限模块异常", e);
        }
    }
}
