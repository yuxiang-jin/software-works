package org.yuxiang.jin.officeauto.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "oa_role")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 6837526111700641932L;

    //角色编号，主键自增长
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    //角色名字
    @Column(name="role_name",length = 50)
    private String roleName;

    //角色备注信息
    @Column(name = "role_remark", length = 500)
    private String roleRemark;

    //角色创建人，用户和角色创建人是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name="creater",referencedColumnName = "user_id",foreignKey = @ForeignKey(name="fk_role_creater"))
    private User creater;

    //创建时间
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    //角色修改人，用户和角色修改人是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "modifier", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_role_modifier"))
    private User modifier;

    //修改时间
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    //用户，用户和角色是N-N的关系
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "oa_user_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(Long roleId, String roleName, String roleRemark, User creater, Date createDate,
            User modifier, Date modifyDate, Set<User> users) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleRemark = roleRemark;
        this.creater = creater;
        this.createDate = createDate;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.users = users;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
