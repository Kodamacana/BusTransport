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
@Table(name= "bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate", nullable = false, unique = true)
    private String plate;

    @Column(name = "color")
    private String color;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

}
