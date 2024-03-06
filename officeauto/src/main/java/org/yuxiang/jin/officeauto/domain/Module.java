package org.yuxiang.jin.officeauto.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "oa_module")
public class Module implements Serializable {

    private static final long serialVersionUID = 5139796285142133024L;

    //模块代码，主键。(0001...0002)四位为模块，(00010001..)八位为操作
    @Id
    @Column(name = "module_code", length = 100)
    private String moduleCode;

    //模块名称
    @Column(name = "module_name", length = 50)
    private String moduleName;

    //操作链接
    @Column(name = "module_url", length = 100)
    private String moduleUrl;

    //模块备注信息
    @Column(name = "module_remark", length = 500)
    private String moduleRemark;

    //模块修改人，用户和模块修改人是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "modifier", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_module_modifier"))
    private User modifier;

    //修改时间
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    //模块创建人，用户和模块创建人是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "creater", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_module_creater"))
    private User creater;

    //创建时间
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Module() {}

    public Module(String moduleCode, String moduleName, String moduleUrl, String moduleRemark, User modifier,
            Date modifyDate, User creater, Date createDate) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleUrl = moduleUrl;
        this.moduleRemark = moduleRemark;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.creater = creater;
        this.createDate = createDate;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String muduleCode) {
        this.moduleCode = muduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public String getModuleRemark() {
        return moduleRemark;
    }

    public void setModuleRemark(String moduleRemark) {
        this.moduleRemark = moduleRemark;
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
