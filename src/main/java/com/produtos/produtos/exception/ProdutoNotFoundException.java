package com.produtos.produtos.exception;

public class ProdutoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProdutoNotFoundException(String msg) {
        super(msg);
    }

    public ProdutoNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
