package com.id.akn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Tên không được để trống")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không đúng định dạng")
	@Column(nullable = false)
	private String email;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Column(nullable = false)
	private String password;

	@Column(name = "phone_number",nullable = false)
	@NotBlank(message = "Số điện thoại không được để trống")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private LocalDate birthday;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<Address> addresses = new HashSet<>();

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Rating> ratings = new ArrayList<>();

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> reviews = new ArrayList<>();

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public enum Role {
		USER, ADMIN
	}
	public enum Gender {
		MALE, FEMALE
	}
}
