package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.config.JwtProvider;
import com.bohdan.onlinefoodordering.model.Cart;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.model.UserRole;
import com.bohdan.onlinefoodordering.repository.CartRepository;
import com.bohdan.onlinefoodordering.repository.UserRepository;
import com.bohdan.onlinefoodordering.dto.auth.LoginRequestDto;
import com.bohdan.onlinefoodordering.dto.auth.AuthResponseDto;
import com.bohdan.onlinefoodordering.config.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> createUserHandler(@RequestBody User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("User with this email: " + user.getEmail() + " already exist");
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success!");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/singin")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody LoginRequestDto request) {
        String username = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(username, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success!");
        authResponse.setRole(UserRole.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );
    }
}
