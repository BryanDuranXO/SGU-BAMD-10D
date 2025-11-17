package mx.edu.utez.sgu_backend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserBean,Long> {
    Optional<UserBean> findByTelefono(String telefono);
    Optional<UserBean>findByCorreo(String correo);
}
