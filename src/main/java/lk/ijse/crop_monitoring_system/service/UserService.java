package lk.ijse.crop_monitoring_system.service;

import lk.ijse.crop_monitoring_system.dto.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    void updateUser(String userId, UserDTO userDTO);
    void deleteUser(String userId);
    UserDTO getUser(String userId);
    List<UserDTO> getAllUsers();
}