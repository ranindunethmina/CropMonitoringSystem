package lk.ijse.crop_monitoring_system.service.impl;

import lk.ijse.crop_monitoring_system.dto.UserDTO;
import lk.ijse.crop_monitoring_system.dto.AuthDTO;
import lk.ijse.crop_monitoring_system.dto.SignInDTO;
import lk.ijse.crop_monitoring_system.repository.UserRepository;
import lk.ijse.crop_monitoring_system.service.AuthService;
import lk.ijse.crop_monitoring_system.service.JWTService;
import lk.ijse.crop_monitoring_system.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final Mapping mapping;
    //utils
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthDTO signIn(SignInDTO signInDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));
        var userByEmail = userRepository.findByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(userByEmail);
        return AuthDTO.builder().token(generatedToken).build() ;
    }

    @Override
    public AuthDTO signUp(UserDTO signUp) {
        var savedUser = userRepository.save(mapping.convertToUser(signUp));
        var genToken = jwtService.generateToken(savedUser);
        return AuthDTO.builder().token(genToken).build();
    }

    @Override
    public AuthDTO refreshToken(String accessToken) {
        var userName = jwtService.extractUsername(accessToken);
        var userEntity =
                userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(userEntity);
        return AuthDTO.builder().token(refreshToken).build();
    }
}