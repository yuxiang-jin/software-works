package org.yuxiang.jin.officeauto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.yuxiang.jin.officeauto.domain.User;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query("select u.userId from User u where u.userId not in(select u.userId from User u inner join u.roles r where " +
            "r.roleId = ?1)")
    List<String> getRolesUsers(Long id);

    @Query("select u.userId from User u inner join u.roles r where r.roleId = ?1")
    List<String> findRoleUsers(Long id);

}
