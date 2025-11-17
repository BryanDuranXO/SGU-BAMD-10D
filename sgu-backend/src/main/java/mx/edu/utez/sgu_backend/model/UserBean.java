package mx.edu.utez.sgu_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UserBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "materno")
    private String materno;

    @Column(name = "paterno")
    private String paterno;

    @Column(name = "telefono", length = 10, unique = true)
    private String telefono;

    @Column(name = "correo", unique = true)
    private String correo;


    public UserBean() {
    }

    public UserBean(Long id, String nombre, String materno, String paterno, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.materno = materno;
        this.paterno = paterno;
        this.telefono = telefono;
        this.correo = correo;
    }

    public UserBean(String nombre, String materno, String paterno, String telefono, String correo) {
        this.nombre = nombre;
        this.materno = materno;
        this.paterno = paterno;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
