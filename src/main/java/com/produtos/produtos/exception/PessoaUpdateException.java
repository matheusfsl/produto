package com.produtos.produtos.exception;

public class PessoaUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PessoaUpdateException(String msg) {
        super(msg);
    }

    public PessoaUpdateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
