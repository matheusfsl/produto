package com.produtos.produtos.exception;

public class ProdutoAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProdutoAlreadyExistException(String msg) {
        super(msg);
    }

    public ProdutoAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
