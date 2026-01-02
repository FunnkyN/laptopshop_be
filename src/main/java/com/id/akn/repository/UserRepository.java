package com.id.akn.repository;

import com.id.akn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
	public List<User> findAllByOrderByCreatedAtDesc();

	@Query(value = "SELECT COUNT(*) FROM users", nativeQuery = true)
	Long totalUser();

	Optional<User> findFirstByRole(User.Role role);
}
