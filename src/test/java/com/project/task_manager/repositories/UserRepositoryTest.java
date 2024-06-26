package com.project.task_manager.repositories;

import com.project.task_manager.domain.Role;
import com.project.task_manager.domain.User;
import com.project.task_manager.projections.UserDetailsProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    private User user;
    private Role roleUser;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setFirstName("leandro");
        user.setLastName("almeida");
        user.setEmail("leandro123@gmail.com");
        user.setPassword("123456");

        roleUser = new Role();
        roleUser.setAuthority("ROLE_USER");

        user.getRoles().add(roleUser);

        entityManager.persist(roleUser);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("leandro123@gmail.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("leandro123@gmail.com");
    }

    @Test
    @Transactional
    @Rollback
    void testSearchUserAndRolesByEmail() {
        List<UserDetailsProjection> userDetails = userRepository.searchUserAndRolesByEmail("leandro123@gmail.com");
        UserDetailsProjection userDetailsProjection = userDetails.get(0);
        assertThat(userDetailsProjection.getUsername()).isEqualTo("leandro123@gmail.com");
        assertThat(userDetailsProjection.getAuthority()).isEqualTo("ROLE_USER");
    }
}
