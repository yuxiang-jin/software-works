package org.yuxiang.jin.officeauto.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "oa_popedom")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Popedom implements Serializable {

    private static final long serialVersionUID = -1246107000138494011L;

    //权限编号，主键自增长
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "popedom_id")
    private Long popedomId;

    //模块，权限和模块是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Module.class)
    @JoinColumn(name = "module", referencedColumnName = "module_code", foreignKey = @ForeignKey(name = "fk_popedom_module"))
    private Module module;

    //操作，权限和操作是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Module.class)
    @JoinColumn(name = "opera", referencedColumnName = "module_code", foreignKey = @ForeignKey(name = "fk_popedom_opera"))
    private Module opera;

    //角色，权限和角色是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role", referencedColumnName = "role_id", foreignKey = @ForeignKey(name = "fk_popedom_role"))
    private Role role;

    //权限创建人，权限创建人与用户是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name="creater",referencedColumnName="user_id",foreignKey = @ForeignKey(name="fk_popedom_creater"))
    private User creater;

    //创建时间
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Popedom() {}

    public Popedom(Long popedomId, Module module, Module opera, Role role, User creater, Date createDate) {
        this.popedomId = popedomId;
        this.module = module;
        this.opera = opera;
        this.role = role;
        this.creater = creater;
        this.createDate = createDate;
    }

    public Long getPopedomId() {
        return popedomId;
    }

    public void setPopedomId(Long popedomId) {
        this.popedomId = popedomId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Module getOpera() {
        return opera;
    }

    public void setOpera(Module opera) {
        this.opera = opera;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
}
