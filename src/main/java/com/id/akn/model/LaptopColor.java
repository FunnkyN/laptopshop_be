package com.id.akn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "laptop_colors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaptopColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "laptop_id")
    @JsonIgnore
    private Laptop laptop;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(nullable = false)
    private short quantity;
}
