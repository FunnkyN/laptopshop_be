package com.id.akn.controller;

import com.id.akn.request.UserSignupDTO;
import com.id.akn.service.UserService;
import com.id.akn.serviceimpl.CustomUserDetail;
import com.id.akn.service.CaptchaService; // Import service Captcha
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.id.akn.config.JwtProvider;
import com.id.akn.exception.UserException;
import com.id.akn.model.Cart;
import com.id.akn.model.User;
import com.id.akn.request.Login;
import com.id.akn.response.AuthRes;
import com.id.akn.service.CartService;
import com.id.akn.service.GeminiService;

import lombok.AllArgsConstructor;
import vn.payos.PayOS;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	private UserService userService;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomUserDetail customUserDetail;
	private CartService cartService;
	private CaptchaService captchaService; // Inject CaptchaService

	

        // Bước 2: Logic tạo User
		User savedUser = userService.createUser(userSignupDTO);
		Cart cart = cartService.createCart(savedUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);

		AuthRes authRes = new AuthRes();
		authRes.setJwt(token);
		authRes.setMessage("Đăng ký thành công");
@PostMapping("/signup")
	public ResponseEntity<?> createUserHandler(@Valid @RequestBody UserSignupDTO userSignupDTO) throws UserException {
		
        // Bước 1: Verify Captcha
		boolean isCaptchaValid = captchaService.verify(userSignupDTO.getRecaptchaToken());
		if (!isCaptchaValid) {
			throw new BadCredentialsException("Captcha không hợp lệ hoặc đã hết hạn.");
		}
		return new ResponseEntity<>(authRes, HttpStatus.CREATED);
	}
    public ApiClientController(GeminiService geminiService, PayOS payOS) {
        this.geminiService = geminiService;
        this.payOS = payOS;
    }
}