package com.produtos.produtos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoForm {

    @NotBlank(message = "Esse campo não pode está vazio")
    @Size(min = 2, max = 100, message = "o campo name precisa estar entre {min} e {max} caracteres")
    private String name;

    @NotNull(message = "Esse campo não pode estar vazio")
    @Min(value = 0, message = "Esse campo não pode estar vazio")
    private double price;

    @NotBlank(message = "Esse campo não pode estar vazio")
    @Size(min = 20, max = 500, message ="O campo descrição precisa estar entre {min} e {max} caracteres")
    private String descricao;
}
