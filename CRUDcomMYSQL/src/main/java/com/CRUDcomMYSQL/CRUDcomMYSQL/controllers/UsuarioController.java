package com.CRUDcomMYSQL.CRUDcomMYSQL.controllers;

import com.CRUDcomMYSQL.CRUDcomMYSQL.interfaces.UsuarioRepository;
import com.CRUDcomMYSQL.CRUDcomMYSQL.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuarioParcial(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuarioExistente = optionalUsuario.get();

            if (usuarioAtualizado.getNome() != null) {
                usuarioExistente.setNome(usuarioAtualizado.getNome());
            }
            if (usuarioAtualizado.getEmail() != null) {
                usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            }

            Usuario usuarioAtualizadoSalvo = usuarioRepository.save(usuarioExistente);
            return ResponseEntity.ok(usuarioAtualizadoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
