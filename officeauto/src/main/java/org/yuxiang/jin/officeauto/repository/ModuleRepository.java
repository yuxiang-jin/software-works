package org.yuxiang.jin.officeauto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yuxiang.jin.officeauto.domain.Module;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public interface ModuleRepository extends JpaRepository<Module, String>, JpaSpecificationExecutor<Module> {

    @Query("delete Module m where m.moduleCode like ?1")
    void removeModule(String moduleCode);

    @Query("select m from Module m where m.moduleCode like :parentCode and length(m.moduleCode) = :sonCodeLen")
    List<Module> findModules(@Param("parentCode") String parentCode, @Param("sonCodeLen") int sonCodeLen);

    @Query("select MAX(m.moduleCode) from Module m where m.moduleCode like :parentCode and length(m.moduleCode) = " +
            ":sonCodeLen")
    String findUniqueEntity(@Param("parentCode") String parentCode, @Param("sonCodeLen") int sonCodeLen);

}
