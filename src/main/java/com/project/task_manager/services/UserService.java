package com.project.task_manager.services;

import com.project.task_manager.domain.Role;
import com.project.task_manager.domain.User;
import com.project.task_manager.dto.RoleDTO;
import com.project.task_manager.dto.UserDTO;
import com.project.task_manager.dto.UserInsertDTO;
import com.project.task_manager.projections.UserDetailsProjection;
import com.project.task_manager.repositories.RoleRepository;
import com.project.task_manager.repositories.UserRepository;
import com.project.task_manager.services.exceptions.DatabaseException;
import com.project.task_manager.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException(username);
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> list = repository.findAll();
        return list.stream().map(UserDTO::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User result = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")
        );
        return new UserDTO(result);
    }
    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto,entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role roleUser = roleRepository.findByAuthority("ROLE_USER");
        if (roleUser != null) {
            entity.getRoles().add(roleUser);
        }
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id,UserDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto,entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado" + id);
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional
    public void promoteToAdmin(Long id) {
        User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        Role roleAdmin = roleRepository.findByAuthority("ROLE_ADMIN");
        if (roleAdmin != null) {
            entity.getRoles().add(roleAdmin);
            repository.save(entity);
        }
    }

    @Transactional
    public void removingAdmin(Long id) {
        User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        Role roleAdmin = roleRepository.findByAuthority("ROLE_ADMIN");
        if (roleAdmin != null && entity.getRoles().contains(roleAdmin)) {
            entity.getRoles().remove(roleAdmin);
            repository.save(entity);
        } else {
            throw new ResourceNotFoundException("Função ADMIN não encontrada para o usuário");
        }
    }


    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO roleDto: dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDto.getId());
            entity.getRoles().add(role);
        }

    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return repository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Email not found");
        }

    }
    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User user = authenticated();
        return new UserDTO(user);
    }
}