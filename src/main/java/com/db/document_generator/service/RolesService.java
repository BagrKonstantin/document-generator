package com.db.document_generator.service;

import com.db.document_generator.domain.Roles;
import com.db.document_generator.domain.User;
import com.db.document_generator.model.RolesDTO;
import com.db.document_generator.repos.RolesRepository;
import com.db.document_generator.repos.UserRepository;
import com.db.document_generator.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class RolesService {

    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;

    public RolesService(final RolesRepository rolesRepository,
            final UserRepository userRepository) {
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
    }

    public List<RolesDTO> findAll() {
        final List<Roles> roleses = rolesRepository.findAll(Sort.by("roleId"));
        return roleses.stream()
                .map(roles -> mapToDTO(roles, new RolesDTO()))
                .toList();
    }

    public RolesDTO get(final String roleId) {
        return rolesRepository.findById(roleId)
                .map(roles -> mapToDTO(roles, new RolesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final RolesDTO rolesDTO) {
        final Roles roles = new Roles();
        mapToEntity(rolesDTO, roles);
        roles.setRoleId(rolesDTO.getRoleId());
        return rolesRepository.save(roles).getRoleId();
    }

    public void update(final String roleId, final RolesDTO rolesDTO) {
        final Roles roles = rolesRepository.findById(roleId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(rolesDTO, roles);
        rolesRepository.save(roles);
    }

    public void delete(final String roleId) {
        rolesRepository.deleteById(roleId);
    }

    private RolesDTO mapToDTO(final Roles roles, final RolesDTO rolesDTO) {
        rolesDTO.setRoleId(roles.getRoleId());
        rolesDTO.setTitle(roles.getTitle());
        rolesDTO.setUserRolesUsers(roles.getUserRolesUsers().stream()
                .map(user -> user.getUserId())
                .toList());
        return rolesDTO;
    }

    private Roles mapToEntity(final RolesDTO rolesDTO, final Roles roles) {
        roles.setTitle(rolesDTO.getTitle());
        final List<User> userRolesUsers = userRepository.findAllById(
                rolesDTO.getUserRolesUsers() == null ? Collections.emptyList() : rolesDTO.getUserRolesUsers());
        if (userRolesUsers.size() != (rolesDTO.getUserRolesUsers() == null ? 0 : rolesDTO.getUserRolesUsers().size())) {
            throw new NotFoundException("one of userRolesUsers not found");
        }
        roles.setUserRolesUsers(userRolesUsers.stream().collect(Collectors.toSet()));
        return roles;
    }

    public boolean roleIdExists(final String roleId) {
        return rolesRepository.existsByRoleIdIgnoreCase(roleId);
    }

    public boolean titleExists(final String title) {
        return rolesRepository.existsByTitleIgnoreCase(title);
    }

}
