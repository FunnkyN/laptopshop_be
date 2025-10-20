package com.id.akn.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	@Id
	@Column(nullable = false, unique = true)
	private String id;

	@ManyToOne
	@JsonIgnore
	private Cart cart;

	@ManyToOne
	private Laptop laptop;

	@ManyToOne
	private Color color;

	private short quantity;

	@Column(name="add_at")
	private LocalDateTime addAt;

	public void generateCompositeId() {
		if (this.cart.getUser().getId() != null && this.laptop != null && this.color != null) {
			this.id = this.cart.getUser().getId() + "_" + this.laptop.getId() +"_" + this.color.getId();
		}
	}
}
