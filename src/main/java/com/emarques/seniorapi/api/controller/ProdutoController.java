package com.emarques.seniorapi.api.controller;

import com.emarques.seniorapi.domain.model.Produto;
import com.emarques.seniorapi.domain.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listar(){
        return ResponseEntity.ok(
                produtoService.listar()
        );
    }
}
