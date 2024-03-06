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
@Table(name="oa_dept")
public class Dept implements Serializable {
    private static final long serialVersionUID = 678100638005497362L;
    //部门编号，主键自增长
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dept_id")
    private Long deptId;

    //部门名称
    @Column(name = "dept_name", length = 50)
    private String deptName;

    //部门备注信息
    @Column(name = "dept_remark", length = 500)
    private String deptRemark;

    //修改人，用户和部门是N-1的关系，通过外键关联
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "modifier", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_dept_modifier"))
    private User modifier;

    //修改时间
    @Column(name="modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    //创建人，用户和部门是N-1的关系，通过外键关联
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "creater", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_dept_creater"))
    private User creater;

    //创建时间
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Dept() {}

    public Dept(Long deptId, String deptName, String deptRemark, User modifier, Date modifyDate, User creater, Date createDate) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptRemark = deptRemark;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.creater = creater;
        this.createDate = createDate;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
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

    @Override
    public String toString() {
        return "Dept{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", deptRemark='" + deptRemark + '\'' +
                ", modifier=" + modifier +
                ", modifyDate=" + modifyDate +
                ", creater=" + creater +
                ", createDate=" + createDate +
                '}';
    }
}
