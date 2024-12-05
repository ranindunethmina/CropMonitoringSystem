package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.UserDTO;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.UserNotFoundException;
import lk.ijse.crop_monitoring_system.dto.AuthDTO;
import lk.ijse.crop_monitoring_system.dto.SignInDTO;
import lk.ijse.crop_monitoring_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "signup",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthDTO> signUp(@RequestBody UserDTO userDTO){
        log.info("Received request to save user");
        if (userDTO == null){
            log.warn("Received null userDTO");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                authService.signUp(userDTO);
                log.info("User saved successfully: {}", userDTO.getEmail());
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (UserNotFoundException e){
                log.info("User not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch (DataPersistFailedException e){
                log.error("failed due to data persistence issue.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error("Something went wrong while saving user.");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping(value = "signin")
    public ResponseEntity<AuthDTO> signIn(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authService.signIn(signInDTO));
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthDTO> refreshToken (@RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}