package org.yuxiang.jin.officeauto.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oa_job")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 459497377750274376L;

    //职位代码，主键
    @Id
    @Column(name = "job_code", length = 100)
    private String jobCode;

    //职位名称
    @Column(name = "job_name", length = 50)
    private String jobName;

    //职位说明
    @Column(name = "job_remark", length = 300)
    private String jobRemark;

    public Job() {}

    public Job(String jobCode, String jobName, String jobRemark) {
        this.jobCode = jobCode;
        this.jobName = jobName;
        this.jobRemark = jobRemark;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobRemark() {
        return jobRemark;
    }

    public void setJobRemark(String jobRemark) {
        this.jobRemark = jobRemark;
    }
}
