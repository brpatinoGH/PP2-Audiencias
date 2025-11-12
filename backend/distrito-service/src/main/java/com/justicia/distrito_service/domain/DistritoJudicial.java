package com.justicia.distrito_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "distrito_judicial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistritoJudicial extends BaseEntity {

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "estado", length = 50)
    private String estado;

}
