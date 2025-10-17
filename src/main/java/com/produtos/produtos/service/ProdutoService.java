package com.produtos.produtos.service;

import com.produtos.produtos.dto.request.ProdutoForm;
import com.produtos.produtos.dto.response.ProdutoDto;
import com.produtos.produtos.exception.PessoaUpdateException;
import com.produtos.produtos.exception.ProdutoAlreadyExistException;
import com.produtos.produtos.exception.ProdutoInsertException;
import com.produtos.produtos.exception.ProdutoNotFoundException;
import com.produtos.produtos.model.ProdutoModel;
import com.produtos.produtos.repository.ProdutoRepository;
import com.produtos.produtos.utils.ProdutoMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    private final ProdutoMapper produtoMapper;
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoMapper produtoMapper, ProdutoRepository produtoRepository) {
        this.produtoMapper = produtoMapper;
        this.produtoRepository = produtoRepository;
    }

    public ProdutoModel getProdutoModelByName(String name) {
        return produtoRepository.findByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new ProdutoNotFoundException(
                        String.format("O produto '%s' não foi encontrado", name)
                ));
    }

    public ProdutoDto getProdutoByName(String name) {
        ProdutoModel produtoModel = getProdutoModelByName(name);
        return produtoMapper.modelToDto(produtoModel);
    }

    @Transactional
    public ProdutoDto createProduto(ProdutoForm produtoForm) {
        if (produtoRepository.findByNameContainingIgnoreCase(produtoForm.getName()).isPresent()) {
            throw new ProdutoNotFoundException(
                    String.format("O produto '%s' ja está cadastrado ", produtoForm.getName())
            );
        }
        try {
            ProdutoModel newProdutoModel = produtoMapper.formToModel(produtoForm);
            produtoRepository.save(newProdutoModel);
            return produtoMapper.modelToDto(newProdutoModel);
        } catch (DataIntegrityViolationException err) {
            throw new ProdutoInsertException(
                    String.format("Falha ao cadastrar o produto '%s' ", produtoForm.getName())
            );
        }
    }

    @Transactional
    public ProdutoDto updateProduto(ProdutoForm produtoForm, String name) {
        if (produtoRepository.findByNameContainingIgnoreCase(name).isEmpty()) {
            throw new ProdutoAlreadyExistException(
                    String.format("o produto '%s' não foi encontrado", name)
            );
        }
        ProdutoModel produtoModel = getProdutoModelByName(name);
        try {
            ProdutoModel updateProdutoModel = produtoMapper.updateFormToModel(produtoModel, produtoForm);
            produtoRepository.save(updateProdutoModel);
            return produtoMapper.modelToDto(updateProdutoModel);
        } catch (ConstraintViolationException | DataIntegrityViolationException | OptimisticLockingFailureException e) {
            throw new PessoaUpdateException(
                    String.format("falha ao atualizar a pessoa ", produtoForm.getName())
            );
        }
    }

    @Transactional
    public void softDeleteProdutoByName(String name) {
        ProdutoModel produtoModel = getProdutoModelByName(name);
        produtoRepository.deleteById(produtoModel.getId());
    }

    public Set<ProdutoDto> getAllProdutoDto() {
        List<ProdutoModel> produtoModels = produtoRepository.findAll();
        if (produtoModels.isEmpty()) {
            throw new ProdutoNotFoundException(
                    String.format("Produtos não encontrados")
            );
        }
        return produtoModels.stream()
                .map(produtoMapper::modelToDto)
                .collect(Collectors.toSet());
    }

    public Set<ProdutoDto> createLote(List<ProdutoForm> produtoFormList) {
        List<ProdutoModel> produtoModels = produtoFormList.stream()
                .filter(produtoForm -> produtoRepository.findByNameContainingIgnoreCase(produtoForm.getName()).isEmpty())
                .map(produtoMapper::formToModel)
                .toList();
        List<ProdutoModel> saveProdutoModel = produtoRepository.saveAll(produtoModels);

        return saveProdutoModel.stream()
                .map(produtoMapper::modelToDto)
                .collect(Collectors.toSet());
    }

    public Set<ProdutoDto> updateLote(List<ProdutoForm> produtoFormList) {
        List<ProdutoModel> produtoModelsAttLista = new ArrayList<>();

        for (ProdutoForm form : produtoFormList) {
            try {
                ProdutoModel produtoModelExistente = getProdutoModelByName(form.getName());
                produtoMapper.updateFormToModel(produtoModelExistente, form);
                produtoModelsAttLista.add(produtoModelExistente);
            } catch (ProdutoNotFoundException e) {
                throw new ProdutoNotFoundException(
                        String.format("Produto(s) '%s' não encontrado(s)", form.getName())
                );
            }

        }
        List<ProdutoModel> produtoModelsUpdated = produtoRepository.saveAll(produtoModelsAttLista);

        return produtoModelsUpdated.stream()
                .map(produtoMapper::modelToDto)
                .collect(Collectors.toSet());
    }
}



