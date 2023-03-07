package com.onlineexaminationportal.security;

import com.onlineexaminationportal.entity.Role;
import com.onlineexaminationportal.entity.User;
import com.onlineexaminationportal.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) { //or @Autowired
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail)
        );
        //UsernameNotFoundException is builtIn Exception in spring security core
        //when there are two classes with the same name in the same project then do not perform an import using import keyword
        //instead perform the import using the package name as given below

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles())
                //In mapRolesToAuthorities() method, we will convert the set of roles into Granted authorities  and supply it to the User class (present in spring security core)
        );   //now after supplying three parameters to this User (present in spring security core)  , we will get the userDetails object

    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList ());
    }
}
