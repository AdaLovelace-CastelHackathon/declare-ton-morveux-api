package com.declaretonmorveux.declaretonmorveux.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "children")
public class Child {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties("childrens")
    private School school;

    @Column(name = "is_sick", columnDefinition = "bool default false")
    private boolean isSick;

    @Column(name = "is_contagious", columnDefinition = "bool default false")
    private boolean isContagious;

    @Column(name = "last_declaration_date")
    private LocalDate lastDeclarationDate;

    @Override
    public String toString() {
        return "Child [firstName=" + firstName + ", id=" + id + ", isContagious=" + isContagious + ", isSick=" + isSick
                + ", lastDeclarationDate=" + lastDeclarationDate + ", lastName=" + lastName + ", parent=" + parent
                + ", school=" + school + "]";
    }

    
    
}
