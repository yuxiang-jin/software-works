package org.yuxiang.jin.officeauto.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yuxiang.jin.officeauto.domain.Dept;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public interface DeptRepository extends JpaRepository<Dept, Long> {
    @Query("select new Map(p.deptId as dept_id,p.deptName as dept_name) from Dept p")
    List<Map<String, Object>> findDepts();

}
