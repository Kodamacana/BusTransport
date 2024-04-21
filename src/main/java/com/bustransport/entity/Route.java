package com.bustransport.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name= "ROUTE")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="averageDuration",nullable = false)
    private int averageDuration;

    @Column(name = "startState")
    private long startState;

    @Column(name = "finalState")
    private long finalState;
}
