package com.pentasecurity.isignplus.systemservice.authentication.service;

import com.pentasecurity.isignplus.systemservice.authentication.controller.dto.RoleDTO;
import com.pentasecurity.isignplus.systemservice.common.model.SystemServiceConfig;
import com.pentasecurity.isignplus.systemservice.common.repository.SystemServiceConfigRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {

    @Autowired
    private SystemServiceConfigRepository systemServiceConfigRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private Map<String, String> configMap = new Hashtable<>();

    private void getConfig (String key, Function<String, String> postProcess) {
        if (configMap.containsKey(key) == true) return;

        SystemServiceConfig config = systemServiceConfigRepository.findByKey(key);

        if (config != null) {
            String value = config.getValue();

            if (postProcess != null) {
                value = postProcess.apply(value);
            }

            configMap.put(config.getKey(), value);
        }
    }

    private Map<String, String> getConfig () {
        getConfig("JWT_SECRET_KEY", (value) -> Base64.getEncoder().encodeToString(value.getBytes()));
        getConfig("JWT_TOKEN_EXPIRATION", null);

        return configMap;
    }

    public String createToken (String userId, Collection<RoleDTO> roles) {
        Date now = new Date();

        Claims claims = Jwts.claims()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Integer.valueOf(getConfig().get("JWT_TOKEN_EXPIRATION"))))
                .setAudience(userId)
                .setIssuer("SystemService");

        claims.put("roles", roles.stream()
                .map(role -> role.getName())
                .collect(Collectors.toList())
        );

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, getConfig().get("JWT_SECRET_KEY"))
                .compact();
    }

    public Authentication getAuthentication (String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserId(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId (String token) {
        return Jwts.parser()
                .setSigningKey(getConfig().get("JWT_SECRET_KEY"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken (HttpServletRequest request) {
        return request.getHeader("X-Auth-Token");
    }

    public boolean validateToken (String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(getConfig().get("JWT_SECRET_KEY"))
                    .parseClaimsJws(token);

            return (claims.getBody().getExpiration().before(new Date()) == false);
        }
        catch (Exception e) {
            return false;
        }
    }
}
