package com.db.document_generator.service;

import com.db.document_generator.domain.User;
import com.db.document_generator.model.UserDTO;
import com.db.document_generator.repos.RolesRepository;
import com.db.document_generator.repos.UserRepository;
import com.db.document_generator.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public UserService(final UserRepository userRepository, final RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("userId"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long userId) {
        return userRepository.findById(userId)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getUserId();
    }

    public void update(final Long userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        rolesRepository.findAllByUserRolesUsers(user)
                .forEach(roles -> roles.getUserRolesUsers().remove(user));
        userRepository.delete(user);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setSurname(user.getSurname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setHashedPassword(user.getHashedPassword());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstname(userDTO.getFirstname());
        user.setSurname(userDTO.getSurname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setHashedPassword(userDTO.getHashedPassword());
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
