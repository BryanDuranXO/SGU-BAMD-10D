package mx.edu.utez.sgu_backend.service;

import mx.edu.utez.sgu_backend.config.ApiResponse;
import mx.edu.utez.sgu_backend.model.UserBean;
import mx.edu.utez.sgu_backend.model.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(userRepository.findAll(), HttpStatus.OK, "Obteniendo a todos los usuarios"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> create(UserBean user){
        Optional<UserBean> foundedEmail = userRepository.findByCorreo(user.getCorreo());
        Optional<UserBean> foundedTelefono = userRepository.findByTelefono(user.getTelefono());

        if(foundedEmail.isPresent() || foundedTelefono.isPresent()){
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "a información ingresada ya esta registrada", true), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse(userRepository.saveAndFlush(user), HttpStatus.CREATED, "Usuario registrado correctamente"), HttpStatus.CREATED);

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(Long id,UserBean user){
        Optional<UserBean> foundedUser = userRepository.findById(id);

        if(foundedUser.isPresent()){
            UserBean updatedUser = foundedUser.get();
            updatedUser.setNombre(user.getNombre());
            updatedUser.setMaterno(user.getMaterno());
            updatedUser.setPaterno(user.getPaterno());
            updatedUser.setTelefono(user.getTelefono());
            updatedUser.setCorreo(user.getCorreo());

            userRepository.save(updatedUser);

            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Usuario actualizado correctamente", false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", false), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(Long id){
        Optional<UserBean> foundedUser = userRepository.findById(id);
        if(foundedUser.isPresent()){
            userRepository.delete(foundedUser.get());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Usuario eliminado correctamente", false), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", false), HttpStatus.NOT_FOUND);

    }


}
