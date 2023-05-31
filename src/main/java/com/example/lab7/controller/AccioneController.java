package com.example.lab7.controller;

import com.example.lab7.entities.Accione;
import com.example.lab7.entities.Pago;
import com.example.lab7.entities.Usuario;
import com.example.lab7.repository.AccioneRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping(value="/acciones", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class AccioneController {
    final AccioneRepository accioneRepository;

    public AccioneController(AccioneRepository accioneRepository) {
        this.accioneRepository = accioneRepository;
    }


    @PostMapping("/save")
    public ResponseEntity<HashMap<String, Object>> registrarPago(@RequestBody Accione accione){
        HashMap<String,Object> responseJson = new HashMap<>();

        if(accione.getId()!=null && accione.getId()>0){


            boolean flag = false;
            for (Accione accione1 : accioneRepository.findAll()){
                if(Objects.equals(accione1.getId(), accione.getId())){
                    flag = true;
                    break;
                }

            }

            if(!flag){
                accioneRepository.save(accione);

                responseJson.put("id creado",accione.getId());

                accioneRepository.save(accione);
                return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);

            }else{
                responseJson.put("error","El id de la accion a crear ya existe");
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
        if(request.getMethod().equals("POST")){
            responseMap.put("Result", "Error");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }
}
