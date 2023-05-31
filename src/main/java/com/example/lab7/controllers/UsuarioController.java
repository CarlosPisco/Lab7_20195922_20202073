package com.example.lab7.controllers;


import com.example.lab7.entities.Usuario;
import com.example.lab7.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsuarioController {

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    String formattedDate = now.format(formatter);


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

        if(usuario.getId()!=null && usuario.getId()>0){


            boolean flag = false;
            for (Usuario usuario1 : usuarioRepository.findAll()){
                if(Objects.equals(usuario1.getId(), usuario.getId())){
                    flag = true;
                    break;
                }

            }

            if(!flag){

                usuario.setFechaRegistro(Instant.parse(formattedDate));
                usuarioRepository.save(usuario);

                responseJson.put("id creado",usuario.getId());

                return ResponseEntity.ok(responseJson);

            }else{
                responseJson.put("error","El id del usuario a crear ya existe");
                return ResponseEntity.badRequest().body(responseJson);
            }

        }else{
            responseJson.put("error","El id brindado debe ser mayor igual a 0 y diferente de null");
            return ResponseEntity.badRequest().body(responseJson);
        }





    }
    //Exceptionhandlerpost
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){
            responseMap.put("Result", "Error");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }



}
