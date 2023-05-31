package com.example.lab7.controllers;


import com.example.lab7.entities.Usuario;
import com.example.lab7.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsuarioController {


    final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @PostMapping("/crear")
    public ResponseEntity<HashMap<String,Object>> crearUsuario (@RequestBody Usuario usuario){

        HashMap<String,Object> responseJson = new HashMap<>();

        if(usuario.getId()!=null){


            boolean flag = false;
            for (Usuario usuario1 : usuarioRepository.findAll()){
                if(Objects.equals(usuario1.getId(), usuario.getId())){
                    flag = true;
                    break;
                }

            }

            if(!flag){

                usuarioRepository.save(usuario);

                responseJson.put("id creado",usuario.getId());

                return ResponseEntity.ok(responseJson);

            }else{
                responseJson.put("error","El id del usuario a crear ya existe");
                return ResponseEntity.badRequest().body(responseJson);
            }

        }else{
            responseJson.put("error","No se ha especificado el id del usuario a crear");
            return ResponseEntity.badRequest().body(responseJson);
        }










    }



}
