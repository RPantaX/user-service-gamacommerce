package com.andres.springcloud.msvc.users.services;

import com.andres.springcloud.msvc.users.dto.UserRequest;
import com.andres.springcloud.msvc.users.dto.constants.Constants;
import com.andres.springcloud.msvc.users.dto.constants.UsersErrorEnum;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andres.springcloud.msvc.users.entities.Role;
import com.andres.springcloud.msvc.users.entities.User;
import com.andres.springcloud.msvc.users.repositories.RoleRepository;
import com.andres.springcloud.msvc.users.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ICompanyService iCompanyService;
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElse(null);
        if (user == null) {
            log.error("User with username {} not found", username);
            ValidateUtil.requerido(null, UsersErrorEnum.USER_NOT_FOUND_US00001);
        }
        return userRepository.findByUsername(username);
    }
    
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByCompanyId(Long companyId) {
        return userRepository.findAllByCompanyId(companyId);
    }

    @Transactional
    public User save(UserRequest userRequest) {
        //validate if user exists by username or email
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            ValidateUtil.evaluar(false, UsersErrorEnum.USERNAME_ALREADY_EXISTS_US00004);
        }
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            ValidateUtil.evaluar(false, UsersErrorEnum.USER_EMAIL_ALREADY_EXISTS_US00005);
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setKeycloakId(userRequest.getKeycloakId());
        user.setEmail(userRequest.getEmail().toUpperCase());
        user.setRoles(getRoles(userRequest));
        user.setUsername(userRequest.getUsername().toUpperCase());
        user.setEnabled(true);
        user.setState(pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.Constants.STATUS_ACTIVE);
        user.setCreatedAt(pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.Constants.getTimestamp());
        user.setModifiedByUser(Constants.getUserInSession().isEmpty() ? "SYSTEM" : Constants.getUserInSession());

            Boolean companyExists = iCompanyService.existById(userRequest.getCompanyId());
            if (Boolean.FALSE.equals(companyExists)) {
                ValidateUtil.requerido(null, UsersErrorEnum.COMPANY_NOT_FOUND_ERCO00011);
            }
            user.setCompany(iCompanyService.getCompanyReferenceById(userRequest.getCompanyId()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
        
        Optional<User> userOptional = this.findById(id);
        
        return userOptional.map(userDb -> {
            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());
            if (user.isEnabled() == null) {
                userDb.setEnabled(true);
            } else {
                userDb.setEnabled(user.isEnabled());
            }
            userDb.setRoles(getRolesForUpdated(user));
            
            return Optional.of(userRepository.save(userDb));
        }).orElseGet(() -> Optional.empty());

    }
    @Override
    public Boolean existByUserId(String keycloakId) {
        return userRepository.existsByKeycloakId(keycloakId);
    }

    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private List<Role> getRoles(UserRequest user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
            adminRoleOptional.ifPresent(roles::add);
        }
        return roles;
    }
    private List<Role> getRolesForUpdated(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
            adminRoleOptional.ifPresent(roles::add);
        }
        return roles;
    }
}
