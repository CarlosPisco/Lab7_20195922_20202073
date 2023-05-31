package com.example.lab7.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value="/acciones", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class AccioneController {
}
