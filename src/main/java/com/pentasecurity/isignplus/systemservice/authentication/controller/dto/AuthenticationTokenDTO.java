package com.pentasecurity.isignplus.systemservice.authentication.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationTokenDTO {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("roles")
    private Set<RoleDTO> roles;

    @JsonProperty("token")
    private String token;
}
