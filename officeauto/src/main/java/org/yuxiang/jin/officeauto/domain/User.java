package org.yuxiang.jin.officeauto.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "oa_user", indexes = {@Index(columnList = "user_name", name = "idx_user_name")})
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {
    //用户id，主键，由英文字母和数字组成
    @Id
    @Column(name = "user_id", length = 50)
    private String userId;

    //用户密码，使用MD5算法加密
    @Column(name = "user_password", length = 50)
    private String userPassword;

    //用户姓名
    @Column(name = "user_name", length = 50)
    private String userName;

    //用户性别，1：男，2：女
    @Column(name = "user_sex")
    private Short userSex = 1;

    //所属部门，用户和部门是N-1的关系，通过外键关联
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Dept.class)
    @JoinColumn(name = "dept", referencedColumnName = "dept_id", foreignKey = @ForeignKey(name = "fk_user_dept"))
    private Dept dept;  //select u from User u where u.dept.deptId=?

    //所任职位，用户和职位是N-1的关系，通过外键关联
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Job.class)
    @JoinColumn(name = "job", referencedColumnName = "job_code", foreignKey = @ForeignKey(name = "fk_user_job"))
    private Job job;

    //用户邮箱
    @Column(name = "user_email", length = 50)
    private String userEmail;

    //用户电话号码
    @Column(name = "user_tel", length = 50)
    private String userTel;

    //用户手机号码
    @Column(name = "user_phone", length = 50)
    private String userPhone;

    //用户QQ号码
    @Column(name = "user_qqnum", length = 50)
    private String userQqnum;

    //用户预留的问题
    @Column(name="user_question")
    private Short userQuestion;

    //用户答案
    @Column(name = "user_answer", length = 200)
    private String userAnswer;

    //用户状态，0新建，1审核，2不通过审核，3冻结
    @Column(name="user_status")
    private Short userStatus = 0;

    //用户创建人，用户和用户创建人是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "creater", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_user_creater"))
    private User creater;

    //创建时间
    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    //用户修改人，用户和用户修改人是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "modifier", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_user_modifier"))
    private User modifier;

    //修改时间
    @Column(name="modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    //部门审核人，用户和部门审核人是N-1的关系
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "checker", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_user_checker"))
    private User checker;

    //审核时间
    @Column(name="check_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkDate;

    //用户角色，用户和角色是N-N的关系
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class, mappedBy = "users")
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String userId, String userPassword, String userName, Short userSex, Dept dept, Job job,
            String userEmail, String userTel, String userPhone, String userQqnum, Short userQuestion,
            String userAnswer, Short userStatus, User creater, Date createDate, User modifier, Date modifyDate,
            User checker, Date checkDate, Set<Role> roles) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userSex = userSex;
        this.dept = dept;
        this.job = job;
        this.userEmail = userEmail;
        this.userTel = userTel;
        this.userPhone = userPhone;
        this.userQqnum = userQqnum;
        this.userQuestion = userQuestion;
        this.userAnswer = userAnswer;
        this.userStatus = userStatus;
        this.creater = creater;
        this.createDate = createDate;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.checker = checker;
        this.checkDate = checkDate;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Short getUserSex() {
        return userSex;
    }

    public void setUserSex(Short userSex) {
        this.userSex = userSex;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserQqnum() {
        return userQqnum;
    }

    public void setUserQqnum(String userQqnum) {
        this.userQqnum = userQqnum;
    }

    public Short getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(Short userQuestion) {
        this.userQuestion = userQuestion;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Short getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Short userStatus) {
        this.userStatus = userStatus;
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

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
