package com.CRUDcomMYSQL.CRUDcomMYSQL;

import com.CRUDcomMYSQL.CRUDcomMYSQL.controllers.UsuarioController;
import com.CRUDcomMYSQL.CRUDcomMYSQL.interfaces.UsuarioRepository;
import com.CRUDcomMYSQL.CRUDcomMYSQL.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@SpringBootApplication
@Controller
public class CruDcomMysqlApplication {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static void main(String[] args) {
        SpringApplication.run(CruDcomMysqlApplication.class, args);
    }

    @GetMapping("/listarUsuarios")
    public ModelAndView usuarios() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        modelAndView.addObject("usuarios", usuarios);
        return modelAndView;
    }
}
