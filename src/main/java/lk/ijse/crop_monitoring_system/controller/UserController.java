package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.UserDTO;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.UserNotFoundException;
import lk.ijse.crop_monitoring_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class UserController {
    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("id") String userId) {
        log.info("Received request to delete user: {}", userId);
        try {
            userService.deleteUser(userId);
            log.info("User deleted successfully: {}", userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Something went wrong while deleting vehicle.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getSelectedUser(@PathVariable ("id") String userId){
        log.info("Vehicle with ID: {} retrieved successfully", userId);
        return userService.getUser(userId);
    }

    @GetMapping(value = "allVehicle",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        log.info("Get all users successfully");
        return userService.getAllUsers();
    }

    @PatchMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable ("userId") String userId,@RequestBody UserDTO userDTO) {
        log.info("Received request to update user: {}", userId);
        try {
            if (userDTO == null && (userId == null || userId.isEmpty())) {
                log.warn("Received null userId for update");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.updateUser(userId, userDTO);
            log.info("User updated successfully: {}", userDTO.getEmail());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            log.warn("No user found with ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataPersistFailedException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something went wrong while updating user.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}