package com.CRUDcomMYSQL.CRUDcomMYSQL.interfaces;

import com.CRUDcomMYSQL.CRUDcomMYSQL.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
