package org.yuxiang.jin.officeauto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yuxiang.jin.officeauto.domain.Popedom;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public interface PopedomRepository extends JpaRepository<Popedom, Long> {

    @Query("select p.opera.moduleCode from Popedom p where p.role.roleId = :popedomId and p.module.moduleCode = " +
            ":parentCode")
    List<String> findByIdAndParentCode(@Param("popedomId") Long popedomId, @Param("parentCode") String parentCode);

    @Modifying
    @Query("delete Popedom p where p.role.roleId = :roleId and p.module.moduleCode = :moduleCode")
    void deleteByIdAndParentCode(@Param("roleId") Long roleId, @Param("moduleCode") String moduleCode);

    @Query("select distinct p.module.moduleCode from Popedom p where p.role.roleId in(select r.roleId from Role r " +
            "inner join r.users u where u.userId = ?1) order by p.module.moduleCode asc")
    List<String> getUserPopedomModuleCodes(String userId);

    @Query("select distinct p.opera.moduleCode from Popedom p where p.role.roleId in(select r.roleId from Role r " +
            "inner join r.users u where u.userId = ?1) order by p.opera.moduleCode asc")
    List<String> getUserPopedomOperasCodes(String userId);

}
