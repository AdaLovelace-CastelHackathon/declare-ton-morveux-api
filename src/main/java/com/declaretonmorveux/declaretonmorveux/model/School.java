package com.declaretonmorveux.declaretonmorveux.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "schools")
public class School {

    @Id
    private Long id;

    @Column
    private String name;

    private String longitude;

    private String latitude;

    @OneToMany(mappedBy = "school")
    @JsonIgnoreProperties("school")
    private List<Child> children;

}
