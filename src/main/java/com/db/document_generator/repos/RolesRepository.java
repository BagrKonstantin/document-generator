package com.db.document_generator.repos;

import com.db.document_generator.domain.Roles;
import com.db.document_generator.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolesRepository extends JpaRepository<Roles, String> {

    List<Roles> findAllByUserRolesUsers(User user);

    boolean existsByRoleIdIgnoreCase(String roleId);

    boolean existsByTitleIgnoreCase(String title);

}
