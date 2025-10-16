package com.produtos.produtos.utils;

import com.produtos.produtos.dto.request.ProdutoForm;
import com.produtos.produtos.dto.response.ProdutoDto;
import com.produtos.produtos.model.ProdutoModel;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoModel formToModel (ProdutoForm produtoForm){
        if (produtoForm == null){
            return null;
        }
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setName(produtoForm.getName());
        produtoModel.setPrice(produtoForm.getPrice());
        produtoModel.setDescricao(produtoForm.getDescricao());

        return produtoModel;
    }

    public ProdutoDto modelToDto (ProdutoModel produtoModel){
        if(produtoModel == null){
            throw new IllegalArgumentException("ProdutoModel cannot be null");
        }

        ProdutoDto produtoDto = new ProdutoDto();

        produtoDto.setName(produtoModel.getName());
        produtoDto.setPrice(produtoModel.getPrice());
        produtoDto.setDescricao(produtoModel.getDescricao());

        return produtoDto;
    }

    public ProdutoModel updateFormToModel (ProdutoModel produtoModel, ProdutoForm produtoForm){
        produtoModel.setName(produtoForm.getName());
        produtoModel.setPrice(produtoForm.getPrice());
        produtoModel.setDescricao(produtoForm.getDescricao());

        return produtoModel;
    }
}
