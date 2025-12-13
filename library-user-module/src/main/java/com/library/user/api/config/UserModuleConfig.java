package com.library.user.api.config;

import com.library.user.domain.repository.PermissionRepository;
import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.repository.UserRepositoryInterface;
import com.library.user.domain.service.UserDomainService;
import com.library.user.domain.service.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration for User module
 * Defines beans and module-specific configurations
 */
@Configuration
@EnableJpaAuditing
public class UserModuleConfig {

    /**
     * Bean for UserDomainService implementation
     */
    @Bean
    public UserDomainService userDomainService(
            UserRepositoryInterface userRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository) {
        return new UserDomainServiceImpl(userRepository, roleRepository, permissionRepository);
    }
}
