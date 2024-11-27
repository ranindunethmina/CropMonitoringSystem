package lk.ijse.crop_monitoring_system.service;

import lk.ijse.crop_monitoring_system.dto.UserDTO;
import lk.ijse.crop_monitoring_system.dto.AuthDTO;
import lk.ijse.crop_monitoring_system.dto.SignInDTO;

public interface AuthService {
    AuthDTO signIn(SignInDTO signInDTO);
    AuthDTO signUp(UserDTO signUp);
    AuthDTO refreshToken(String accessToken);
}