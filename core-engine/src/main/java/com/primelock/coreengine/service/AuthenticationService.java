package com.primelock.coreengine.service;

import com.primelock.coreengine.domain.User;
import com.primelock.coreengine.dto.AuthenticationRequest;
import com.primelock.coreengine.dto.AuthenticationResponse;
import com.primelock.coreengine.dto.RegisterRequest;
import com.primelock.coreengine.repository.UserRepository;
import com.primelock.coreengine.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        //1. Map the DTO to our Database Entity
        User user = new User();
        user.setEmail(request.getEmail());
        //Hash the password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBptStartTime(request.getBptStartTime());
        user.setBptEndTime(request.getBptEndTime());

        //2. Save the User
        userRepository.save(user);

        //3. Generate the JWT Wristband
        String jwtToken = jwtService.generateToken(user);

        //4. Return the wristband to the frontend
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        // 1. Spring Security checks if the email and password match the database
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // 2. If we reach this line, the password was correct. Fetch the user.
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate a fresh JWT wristband
        String jwtToken = jwtService.generateToken(user);

        // 4. Return it
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
