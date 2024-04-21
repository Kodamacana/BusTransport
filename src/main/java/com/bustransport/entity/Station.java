package com.bustransport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name= "STATION")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "county_id",nullable = false)
    private County county;

    @Column(name = "name",nullable = false, unique = true)
    private String name;


    @Column(name = "street",nullable = false)
    private String street;

    @Column(name = "coordinate",nullable = false)
    private long coordinate;
}
