package com.produtos.produtos.controller;


import com.produtos.produtos.dto.request.ProdutoForm;
import com.produtos.produtos.dto.response.ProdutoDto;
import com.produtos.produtos.model.ProdutoModel;
import com.produtos.produtos.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProdutoDto> createProduto (@RequestBody ProdutoForm produtoForm){
        ProdutoDto produtoDto = produtoService.createProduto(produtoForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoDto);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProduto (@PathVariable String name){
        produtoService.softDeleteProdutoByName(name);
        return ResponseEntity.noContent().build();
    }
}
