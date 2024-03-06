package org.yuxiang.jin.officeauto.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.yuxiang.jin.officeauto.dto.UserModule;
import org.yuxiang.jin.officeauto.commonutil.pager.PageModel;
import org.yuxiang.jin.officeauto.domain.Dept;
import org.yuxiang.jin.officeauto.domain.Module;
import org.yuxiang.jin.officeauto.domain.Role;
import org.yuxiang.jin.officeauto.domain.User;
import org.yuxiang.jin.officeauto.vo.TreeData;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 * Office Auto 业务逻辑方法的定义
 */
public interface OfficeautoService {

    /**
     * 查询所有的部门
     * @return 查询结果
     */
    List<Dept> getAllDepts();


    /**
     * 异步登录的业务层接口方法
     * @param params 传入一个Map<String, Object>参数
     * @return 返回一个Map<String, Object>对象
     */
    Map<String, Object> login(Map<String, Object> params);

    /**
     * 根据用户的主键查询用户信息，包含了延迟加载的部门和职位信息
     * @param userId 用户表的主键
     * @return 查询得到的用户
     */
    User getUserById(String userId);

    /**
     * 修改用户信息
     * @param user 要修改的用户信息的值
     * @param session HttpSession的实例
     */
    void updateSelf(User user, HttpSession session);

    /**
     * @return 异步加载部门与职位的JSON字符串信息，写回页面
     */
    Map<String, Object> getAllDeptsAndJobsAjax();

    /**
     * 分页查询用户信息
     * @param user 要查询的用户信息
     * @param pageModel 自定义的分页实体
     * @return 查询得到的用户，是一个列表
     */
    List<User> getUsersByPager(User user, PageModel pageModel);

    /**
     * 批量删除用户
     * @param ids 要删除的用户的主键
     */
    void deleteUserByIds(String ids);

    /**
     * 校验用户是否已经被注册
     * @param userId 需要校验的用户的主键
     * @return 校验结果
     */
    String isUserValidAjax(String userId);

    /**
     * 添加用户
     * @param user 需要添加的用户信息
     */
    void addUser(User user);

    /**
     * 根据userId修改用户信息
     * @param user 传入的用于修改的用户信息
     */
    void updateUser(User user);

    /**
     * 激活用户
     * @param user 需要被激活的用户信息
     */
    void activeUser(User user);

    /**
     * 加载所有的模块树
     * @return 已加载的模块树，是一个列表
     */
    List<TreeData> loadAllModuleTrees();

    /**
     * 分页，根据父节点查询所有的子模块
     * @param parentCode 父节点编码
     * @param pageModel 自定义的分页实体
     * @return 查询得到的所有子模块，是一个列表
     */
    List<Module> getModulesByParent(String parentCode, PageModel pageModel);

    /**
     * 不分页，根据父节点查询所有的子模块
     * @param parentCode 父节点编码
     * @return 查询得到的所有子模块，是一个列表
     */
    List<Module> getModulesByParent(String parentCode);

    /**
     * 批量删除菜单
     * @param ids 要删除模块的主键
     */
    void deleteModules(String ids);

    /**
     * 为当前父节点菜单添加子节点模块
     * @param parentCode 父节点的编码
     * @param module 子节点的模块信息
     */
    void addModule(String parentCode, Module module);

    /**
     * 根据模块编码查询模块信息
     * @param moduleCode 要查询的模块的编码
     * @return 查询得到的模块信息
     */
    Module getModuleByCode(String moduleCode);

    /**
     * 修改模块
     * @param module 传入的要修改的模块信息值
     */
    void updateModule(Module module);

    /**
     * 分页查询角色信息
     * @param pageModel 自定义的分页实体
     * @return 查询得到的角色信息，是一个列表
     */
    List<Role> getRoleByPager(PageModel pageModel);

    /**
     * 添加角色
     * @param role 要添加的角色信息
     */
    void addRole(Role role);

    /**
     * 批量删除角色
     * @param ids 要删除角色的id列表
     */
    void deleteRole(String ids);

    /**
     * 根据roleId查询角色
     * @param roleId 要查询的角色的主键
     * @return 查询得到的角色信息
     */
    Role getRoleById(Long roleId);

    /**
     * 修改角色
     * @param role 需要修改的角色信息
     */
    void updateRole(Role role);

    /**
     * 分页查询属于这个角色的用户信息
     * @param role 某个角色的信息
     * @param pageModel 分页实体
     * @return 某个角色的用户信息，是一个列表
     */
    List<User> selectRoleUser(Role role, PageModel pageModel);

    /**
     * 分页查询不属于某个角色的用户信息
     * @param role 某个角色的信息
     * @param pageModel 分页实体
     * @return 不属于某个角色的用户信息，是一个列表
     */
    List<User> selectNotRoleUser(Role role, PageModel pageModel);

    /**
     * 给用户绑定角色
     * @param role 要绑定的角色信息
     * @param ids 要绑定角色的用户的主键列表
     */
    void bindUser(Role role, String ids);

    /**
     * 给用户解绑角色
     * @param role 要解绑的角色信息
     * @param ids 要解绑角色的用户的主键列表
     */
    void unBindUser(Role role, String ids);

    /**
     * 查询当前角色在当前模块下拥有的操作权限编码
     * @param role 某个角色的信息
     * @param parentCode 某个模块编码
     * @return 操作权限编码，是一个列表
     */
    List<String> getRoleModuleOperasCodes(Role role, String parentCode);

    /**
     * 给角色绑定某个模块下的操作权限
     * @param operaCodes 要绑定的操作权限
     * @param role 某个角色的信息
     * @param parentCode 某个模块的模块编码
     */
    void bindPopedom(String operaCodes, Role role, String parentCode);

    /**
     * 查询当前用户的权限模块
     * @return 当前用户的权限模块信息，是一个列表
     */
    List<UserModule> getUserPopedomModules();

}
