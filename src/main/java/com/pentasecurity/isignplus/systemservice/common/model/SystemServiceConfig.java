package com.pentasecurity.isignplus.systemservice.common.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(
        name = "config",
        schema = "system_service"
)
public class SystemServiceConfig {
    @Id
    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;
}
