package tn.dkSoft.MyTicket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.dkSoft.MyTicket.dto.UserDto;
import tn.dkSoft.MyTicket.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Service
@Slf4j
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        try {
            logger.info("Registering user: {}", userDto);
            UserDto registeredUser = userService.registerUser(userDto);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering user: " + e.getMessage(), e);
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Error registering user: " + e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            logger.info("Fetching all users");
            List<UserDto> userList = userService.getAllUsers();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching users: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching users: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{userId}/with-cart-and-cartitems")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        try {
            logger.info("Fetching user with ID: {}", userId);
            UserDto user = userService.getUserById(userId);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching user: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching user: " + e.getMessage(), e);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        try {
            logger.info("Updating user with ID: {}", userId);
            UserDto updatedUser = userService.updateUser(userId, userDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating user: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            logger.info("Deleting user with ID: {}", id);
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting user: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user: " + e.getMessage(), e);
        }
    }

}