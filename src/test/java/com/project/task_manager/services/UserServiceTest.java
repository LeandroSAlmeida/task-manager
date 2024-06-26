package com.project.task_manager.services;

import com.project.task_manager.domain.Role;
import com.project.task_manager.domain.User;
import com.project.task_manager.dto.RoleDTO;
import com.project.task_manager.dto.UserDTO;
import com.project.task_manager.dto.UserInsertDTO;
import com.project.task_manager.repositories.RoleRepository;
import com.project.task_manager.repositories.UserRepository;
import com.project.task_manager.services.exceptions.EmailAlreadyExistsException;
import com.project.task_manager.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Nested
class UserServiceTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertUser() {
        UserInsertDTO dto = new UserInsertDTO();
        dto.setEmail("leandro123@gmail.com");
        dto.setFirstName("Leandro");
        dto.setLastName("Almeida");
        dto.setPassword("123456");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("leandro123@gmail.com");
        savedUser.setFirstName("Leandro");
        savedUser.setLastName("Almeida");
        savedUser.setPassword("encodedPassword");

        Role role = new Role(1L, "ROLE_USER");

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByAuthority("ROLE_USER")).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.insert(dto);

        assertThat(result.getEmail()).isEqualTo("leandro123@gmail.com");
        assertThat(result.getFirstName()).isEqualTo("Leandro");
        assertThat(result.getLastName()).isEqualTo("Almeida");

        verify(userRepository).findByEmail("leandro123@gmail.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testInsertUserEmailAlreadyExists() {
        UserInsertDTO dto = new UserInsertDTO();
        dto.setEmail("john.doe@example.com");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> userService.insert(dto))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("Email já está em uso");
    }

    @Test
    void testPromoteToAdmin() {
        User user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");

        Role adminRole = new Role(2L, "ROLE_ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByAuthority("ROLE_ADMIN")).thenReturn(adminRole);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.promoteToAdmin(1L);

        assertThat(user.getRoles()).contains(adminRole);
        verify(userRepository).findById(1L);
        verify(roleRepository).findByAuthority("ROLE_ADMIN");
        verify(userRepository).save(user);
    }

    @Test
    void testPromoteToAdminUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.promoteToAdmin(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário não encontrado");
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDTO result = userService.findById(1L);

        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        verify(userRepository).findById(1L);
    }

    @Test
    void testFindByIdUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado");
    }

    @Test
    void testFindAll() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("leandro@gmail.com");
        user1.setFirstName("Leandro");
        user1.setLastName("Almeida");
        user1.setPassword("123456");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("leandro123@gmail.com");
        user2.setFirstName("Leandro");
        user2.setLastName("Almeida");
        user2.setPassword("encodedPassword");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> result = userService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("leandro@gmail.com");
        assertThat(result.get(1).getEmail()).isEqualTo("leandro123@gmail.com");
        verify(userRepository).findAll();
    }


    @Test
    void testDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.delete(userId);

        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> userService.delete(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado");

        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }
}
