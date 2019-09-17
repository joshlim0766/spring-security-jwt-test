package josh0766.systemservice.authentication.service;

import josh0766.systemservice.authentication.model.SystemServiceUser;
import josh0766.systemservice.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        Optional<SystemServiceUser> userOptional = userRepository.findOneByUserId(username);
        if (userOptional.isPresent() == false) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        SystemServiceUser systemServiceUser = userOptional.get();

        User user = new User(systemServiceUser.getUserId(), systemServiceUser.getUserPassword(),
                systemServiceUser.getUserRoles().stream().map(role -> {
                    return new SimpleGrantedAuthority(role.getName());
                }).collect(Collectors.toCollection(HashSet::new)));

        return user;
    }
}
