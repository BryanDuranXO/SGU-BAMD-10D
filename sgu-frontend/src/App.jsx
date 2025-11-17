import { useState, useEffect } from 'react'
import axios from 'axios'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import './App.css'

function App() {
  const ENV = import.meta.env;
  const API_URL = `http://${ENV.VITE_API_HOST}:${ENV.VITE_API_PORT}${ENV.VITE_API_BASE}`;

  const [nombre, setNombre] = useState('');
  const [materno, setMaterno] = useState('');
  const [paterno, setPaterno] = useState('');
  const [telefono, setTelefono] = useState('');
  const [correo, setCorreo] = useState('');

  const [users, setUsers] = useState([]);
  const [editUser, setEditUser] = useState(null);

  const formData = { nombre, paterno, materno, telefono, correo };


  const onFieldChange = (e) => {
    const { name, value } = e.target;

    if (name === "nombre") setNombre(value);
    if (name === "paterno") setPaterno(value);
    if (name === "materno") setMaterno(value);
    if (name === "telefono") setTelefono(value);
    if (name === "correo") setCorreo(value);
  };

  const fetchUsers = async () => {
    try {
      const response = await axios.get(`${API_URL}/`);
      setUsers(response.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const createUser = async (e) => {
    e.preventDefault();

    try {
      await axios.post(`${API_URL}/save`, formData, {
        headers: { "Content-Type": "application/json" }
      });

      fetchUsers();

      setNombre("");
      setPaterno("");
      setMaterno("");
      setTelefono("");
      setCorreo("");
    } catch (error) {
      console.log(error);
    }
  };

  const updateUser = async () => {
    try {
      await axios.put(`${API_URL}/${editUser.id}`, editUser);
      fetchUsers();
    } catch (error) {
      console.log(error);
    }
  };

  const deleteUser = async () => {
    try {
      await axios.delete(`${API_URL}/${editUser.id}`);
      fetchUsers();
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="container mt-5">

      <h1 className="mb-4">Crud de usuarios</h1>

      {/* FORMULARIO */}
      <form className="row g-3" onSubmit={createUser}>

        <div className="col-md-4">
          <input type="text" name="nombre" className="form-control"
            placeholder="Nombre" value={formData.nombre} onChange={onFieldChange} required />
        </div>

        <div className="col-md-4">
          <input type="text" name="paterno" className="form-control"
            placeholder="Apellido paterno" value={formData.paterno} onChange={onFieldChange} required />
        </div>

        <div className="col-md-4">
          <input type="text" name="materno" className="form-control"
            placeholder="Apellido materno" value={formData.materno} onChange={onFieldChange} required />
        </div>

        <div className="col-md-6">
          <input type="text" name="telefono" className="form-control"
            placeholder="Teléfono" value={formData.telefono} onChange={onFieldChange} required />
        </div>

        <div className="col-md-6">
          <input type="email" name="correo" className="form-control"
            placeholder="Correo" value={formData.correo} onChange={onFieldChange} required />
        </div>

        <div className="col-12">
          <button className="btn btn-primary w-100" type="submit">Registrar</button>
        </div>

      </form>

      {/* LISTA */}
      <div className="row mt-5 g-4">
        {users.map((u) => (
          <div key={u.id} className="col-md-4">
            <div className="card shadow-sm p-3">
              <h5 className="fw-bold">{u.nombre} {u.paterno} {u.materno}</h5>
              <p className="m-0">{u.correo}</p>
              <p className="m-0">{u.telefono}</p>

              <div className="mt-3 d-flex gap-2">
                <button
                  className="btn btn-warning w-50"
                  data-bs-toggle="modal"
                  data-bs-target="#editModal"
                  onClick={() => setEditUser({ ...u })}
                >
                  Actualizar
                </button>

                <button
                  className="btn btn-danger w-50"
                  data-bs-toggle="modal"
                  data-bs-target="#deleteModal"
                  onClick={() => setEditUser({ ...u })}
                >
                  Eliminar
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* MODAL EDITAR */}
      <div className="modal fade" id="editModal" tabIndex="-1">
        <div className="modal-dialog">
          <div className="modal-content">

            <div className="modal-header">
              <h5 className="modal-title">Editar usuario</h5>
              <button className="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div className="modal-body">
              {editUser && (
                <>
                  <input type="text" className="form-control mb-2"
                    value={editUser.nombre}
                    onChange={(e) => setEditUser({ ...editUser, nombre: e.target.value })} />

                  <input type="text" className="form-control mb-2"
                    value={editUser.paterno}
                    onChange={(e) => setEditUser({ ...editUser, paterno: e.target.value })} />

                  <input type="text" className="form-control mb-2"
                    value={editUser.materno}
                    onChange={(e) => setEditUser({ ...editUser, materno: e.target.value })} />

                  <input type="text" className="form-control mb-2"
                    value={editUser.telefono}
                    onChange={(e) => setEditUser({ ...editUser, telefono: e.target.value })} />

                  <input type="email" className="form-control"
                    value={editUser.correo}
                    onChange={(e) => setEditUser({ ...editUser, correo: e.target.value })} />
                </>
              )}
            </div>

            <div className="modal-footer">
              <button className="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
              <button className="btn btn-warning" onClick={updateUser} data-bs-dismiss="modal">Guardar cambios</button>
            </div>

          </div>
        </div>
      </div>

      {/* MODAL ELIMINAR */}
      <div className="modal fade" id="deleteModal" tabIndex="-1">
        <div className="modal-dialog">
          <div className="modal-content">

            <div className="modal-header">
              <h5 className="modal-title">Confirmar eliminación</h5>
              <button className="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div className="modal-body">
              ¿Eliminar a <strong>{editUser?.nombre}</strong>?
            </div>

            <div className="modal-footer">
              <button className="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
              <button className="btn btn-danger" onClick={deleteUser} data-bs-dismiss="modal">Eliminar</button>
            </div>

          </div>
        </div>
      </div>

    </div>
  );
}

export default App;
