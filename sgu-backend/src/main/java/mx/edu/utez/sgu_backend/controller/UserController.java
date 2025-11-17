package mx.edu.utez.sgu_backend.controller;

import mx.edu.utez.sgu_backend.config.ApiResponse;
import mx.edu.utez.sgu_backend.model.UserBean;
import mx.edu.utez.sgu_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAllUsers() {
        return service.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserBean user) {
        return service.create(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UserBean user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        return service.delete(id);
    }
}
