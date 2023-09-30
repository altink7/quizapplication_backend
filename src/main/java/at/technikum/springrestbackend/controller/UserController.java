package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserDTO;
import at.technikum.springrestbackend.mapper.InternalModelMapper;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private UserService userService;
    private InternalModelMapper mapper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        try{
            UserDTO userDTO = mapper.mapToDTO(userService.getUserById(userId), UserDTO.class);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        try{
            UserDTO userDTO = mapper.mapToDTO(userService.getUserByEmail(email), UserDTO.class);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers().stream()
                .map(user -> mapper.mapToDTO(user, UserDTO.class))
                .toList();

        if(userDTOs.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            User createdUser = userService.createUser(mapper.mapToEntity(userDTO, User.class));
            UserDTO createdUserDTO = mapper.mapToDTO(createdUser, UserDTO.class);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            User updatedUser = userService.updateUser(userId, mapper.mapToEntity(userDTO, User.class));
            UserDTO updatedUserDTO = mapper.mapToDTO(updatedUser, UserDTO.class);

            return ResponseEntity.ok(updatedUserDTO);

        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMapper(InternalModelMapper mapper) {
        this.mapper = mapper;
    }
}
