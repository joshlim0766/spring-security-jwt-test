package com.pentasecurity.isignplus.systemservice.authentication.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(
        name = "roles",
        schema = "system_service"
)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_uid")
    private int uid;

    @Column(name = "name")
    private String name;
}
