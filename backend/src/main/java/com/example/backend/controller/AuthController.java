package com.example.backend.controller;

import com.example.backend.entity.EmployeeEntity;
import com.example.backend.entity.RoleEntity;
import com.example.backend.entity.enums.ERole;
import com.example.backend.dto.LoginRequestDto;
import com.example.backend.dto.SignupRequestDto;
import com.example.backend.entity.response.JwtResponse;
import com.example.backend.entity.response.MessageResponse;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.RoleRepository;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.service.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Контроллер для аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, EmployeeRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Метод для аутентификации пользователя.
     *
     * @param loginRequestDto Объект с данными для аутентификации
     * @return Возвращает ответ с токеном аутентификации и ролями пользователя
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse body = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getFio(), userDetails.getEmail(), roles);
        return ResponseEntity
                .ok(body);
    }

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param signUpRequestDto объект с данными для регистрации
     * @return Возвращает ответ об успешной регистрации или об ошибке
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            return ResponseEntity.badRequest().body(new
                    MessageResponse("Пользователь уже существует"));
        }

        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email уже используется"));
        }
        EmployeeEntity user = new EmployeeEntity(signUpRequestDto.getUsername(), signUpRequestDto.getFio(), signUpRequestDto.getEmail(),
                encoder.encode(signUpRequestDto.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();

        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован"));
    }
}

