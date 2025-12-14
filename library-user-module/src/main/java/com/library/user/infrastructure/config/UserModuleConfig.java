package com.library.user.infrastructure.config;

import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.service.UserDomainService;
import com.library.user.domain.service.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for User module
 * Defines beans and module-specific configurations
 */
@Configuration
public class UserModuleConfig {

    /**
     * Bean for UserDomainService implementation
     */
    @Bean
    public UserDomainService userDomainService(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        return new UserDomainServiceImpl(userRepository, roleRepository);
    }
}
