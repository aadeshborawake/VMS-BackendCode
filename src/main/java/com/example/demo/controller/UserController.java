package com.example.demo.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.JwtTokenUtil;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.entity.ChangePasswordRequest;
import com.example.demo.entity.ChangePasswordResponse;
import com.example.demo.entity.User;
import com.example.demo.service.RegistrationService;
import com.example.demo.service.UserService;


@RestController 
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private RegistrationService registrationService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (user.getRoles() != null && user.getRoles().contains("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only admin can register with admin role");
            }

            User newUser = registrationService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
	    try {
	        User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
	        if (user != null) {
	            // Map the user entity to a DTO including role information
	            UserDTO userDTO = mapToUserDTO(user);
	            return ResponseEntity.ok(userDTO);
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
	    }
	}

	private UserDTO mapToUserDTO(User user) {
	    UserDTO userDTO = new UserDTO();
	    userDTO.setId(user.getId());
	    userDTO.setLogin(user.getUsername());
	    // Adjusting the roleName to match the expected format
	    userDTO.setRoleName(user.getRoles().isEmpty() ? "user" : user.getRoles().iterator().next().toLowerCase().substring(5));
	    return userDTO;
	}
	
	@GetMapping("/get-username")
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName(); // This will return the username from the current session
        }
        return null; // Handle if no user is authenticated
    }
	
	@PostMapping("/change-password")
	public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request) {
	    String username = request.getUsername(); // Get the username from the request body
	    String currentPassword = request.getCurrentPassword();
//	    System.out.println(request.getCurrentPassword());
	    String newPassword = request.getNewPassword();
//	    System.out.println(request.getNewPassword());
	    
	    try {
	        String message = userService.changePassword(username, currentPassword, newPassword);
	        return ResponseEntity.ok(new ChangePasswordResponse(message));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(new ChangePasswordResponse("An error occurred while changing password"));
	    }
	}

}    
