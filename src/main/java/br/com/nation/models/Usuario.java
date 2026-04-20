package br.com.nation.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Usuario {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}
