package org.yuxiang.jin.officeauto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.yuxiang.jin.officeauto.domain.Role;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

}
