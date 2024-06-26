package com.project.task_manager.repositories;

import com.project.task_manager.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByAuthority() {
        Role userRole = roleRepository.findByAuthority("ROLE_USER");
        assertThat(userRole).isNotNull();
        assertThat(userRole.getAuthority()).isEqualTo("ROLE_USER");

        Role adminRole = roleRepository.findByAuthority("ROLE_ADMIN");
        assertThat(adminRole).isNotNull();
        assertThat(adminRole.getAuthority()).isEqualTo("ROLE_ADMIN");

        Role nonExistentRole = roleRepository.findByAuthority("ROLE_NON_EXISTENT");
        assertThat(nonExistentRole).isNull();
    }
}
