package com.example.lab7.controllers;


import com.example.lab7.entities.Solicitude;
import com.example.lab7.repository.SolicitudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    final SolicitudRepository solicitudRepository;

    public SolicitudController(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }


    @GetMapping("/lista")
    public List<Solicitude> listarSolicitudes(){
        return solicitudRepository.findAll();
    }

    @PostMapping("/registro")
    public ResponseEntity<HashMap<String,Object>> guardarSolicitud(@RequestBody Solicitude solicitude){

        HashMap<String,Object> responseJson = new HashMap<>();

        if(solicitude.getId()==null || solicitude.getId()<0){
            responseJson.put("error","Tiene que enviar un id, y este tiene que ser mayor de 0");
            return ResponseEntity.badRequest().body(responseJson);
        }else{

            boolean flag = false;
            for(Solicitude solicitude1 : solicitudRepository.findAll()){
                if(Objects.equals(solicitude1.getId(), solicitude.getId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){

                if(solicitude.getSolicitudEstado().equals("") && solicitude.getSolicitudMonto()!=null){
                    solicitude.setSolicitudEstado("pendiente");
                    solicitudRepository.save(solicitude);
                    responseJson.put("Monto solicitado",solicitude.getSolicitudMonto());
                    responseJson.put("id",solicitude.getId());
                    return ResponseEntity.ok(responseJson);

                }else{

                    if(!solicitude.getSolicitudEstado().equals("")){
                        responseJson.put("error estado","el campo solicitud estado debe estar vacio");
                    }


                    if(solicitude.getSolicitudMonto()==null){
                        responseJson.put("error monto","el campo solicitud monto no debe estar vacio");
                    }

                    return ResponseEntity.badRequest().body(responseJson);
                }

            }else{
                responseJson.put("error","El id ya existe");
                return ResponseEntity.badRequest().body(responseJson);
            }

        }


    }

}
