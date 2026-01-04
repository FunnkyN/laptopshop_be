package com.id.akn.controller;

import com.id.akn.response.AdminInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
	private UserService userService;
	

	@GetMapping("/admin")
	public ResponseEntity<AdminInfoResponse> getAdminInfoHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		User admin = userService.findAdminUser();
		AdminInfoResponse adminInfo = new AdminInfoResponse(admin.getId(), admin.getName());
		return new ResponseEntity<>(adminInfo, HttpStatus.OK);
	}
}
