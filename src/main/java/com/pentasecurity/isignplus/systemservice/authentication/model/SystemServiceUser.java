package com.pentasecurity.isignplus.systemservice.authentication.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        schema = "system_service"
)
@Data
@NoArgsConstructor
public class SystemServiceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_uid")
    private int uid;

    @Column(
            name = "user_id",
            unique = true,
            length = 32
    )
    private String userId;

    @Column(
            name = "user_password",
            length = 64
    )
    private String userPassword;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}
    )
    @JoinTable(
            name = "user_roles",
            schema = "system_service",
            joinColumns = @JoinColumn(name = "user_uid"),
            inverseJoinColumns = @JoinColumn(name = "role_uid")
    )
    private Set<Role> userRoles = new HashSet<>();

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
