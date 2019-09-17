package josh0766.systemservice.authentication.service;

import josh0766.systemservice.authentication.controller.dto.AuthenticationRequestDTO;
import josh0766.systemservice.authentication.controller.dto.AuthenticationTokenDTO;
import josh0766.systemservice.authentication.controller.dto.RoleDTO;
import josh0766.systemservice.authentication.controller.dto.UserDTO;
import josh0766.systemservice.authentication.model.SystemServiceUser;
import josh0766.systemservice.authentication.repository.RoleRepository;
import josh0766.systemservice.authentication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService (UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init () {
        /*
        log.info("TEST : " + passwordEncoder.encode("eden501!@^"));
        initSystemServiceRole();
        initSystemServiceUser();
        */
    }

    @Transactional(readOnly = true)
    public UserDTO findUser (String userId) {
        Optional<SystemServiceUser> systemServiceUserOptional = userRepository.findOneByUserId(userId);
        if (!systemServiceUserOptional.isPresent()) {
            // TODO: throw exception
        }

        UserDTO userDTO = new UserDTO();

        modelMapper.map(systemServiceUserOptional.get(), userDTO);

        return userDTO;
    }

    @Transactional(readOnly = true)
    public AuthenticationTokenDTO login (AuthenticationRequestDTO authenticationRequestDTO, HttpSession session) {
        String userId = authenticationRequestDTO.getUserId();
        String userPassword = authenticationRequestDTO.getUserPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, userPassword);

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        UserDetails user = userDetailsService.loadUserByUsername(userId);

        Set<RoleDTO> roleDTOs =  user.getAuthorities().stream()
                .map(authority -> RoleDTO.builder()
                    .name(authority.getAuthority())
                    .build())
                .collect(Collectors.toSet());

        return AuthenticationTokenDTO.builder()
                .userId(user.getUsername())
                .roles(roleDTOs)
                .token(jwtTokenProvider.createToken(user.getUsername(), roleDTOs.stream().collect(Collectors.toList())))
                .build();
    }
}
