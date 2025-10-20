package com.id.akn.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems= new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "shipping_address_id")
	private Address shippingAddress;

	@Column(name="total_price")
	private float totalPrice;

	@Column(name="total_discounted_price")
	private float totalDiscountedPrice;

	@Column(name="total_item")
	private int totalItem;

	@Column(name="created_at")
	private LocalDateTime createdAt;

	@Column(name="payment_method", nullable=false)
	private PaymentMethod paymentMethod;

	@Column(name="payment_status", nullable=false)
	private PaymentStatus paymentStatus;

	@Column(name="delivery_date")
	private LocalDateTime deliveryDate;

	@Column(name="order_status")
	private OrderStatus orderStatus;

	public enum OrderStatus {
		PENDING,
		PLACED,
		CONFIRMED,
		SHIPPED,
		DELIVERED,
		CANCELLED
	}

	public enum PaymentMethod {
		VISA,
		NCB,
		MasterCard,
		JCB,
		EXIMBANK,
		COD,
		QR
	}

	public enum PaymentStatus {
		PENDING,
		PROCESSING,
		COMPLETED,
		FAILED
	}
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history