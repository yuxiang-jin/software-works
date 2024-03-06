package org.yuxiang.jin.officeauto.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yuxiang.jin.officeauto.domain.Job;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public interface JobRepository extends JpaRepository<Job, String> {

    @Query("select new Map(j.jobCode as job_code, j.jobName as job_name) from Job j")
    List<Map<String, Object>> findJobs() throws Exception;
}
