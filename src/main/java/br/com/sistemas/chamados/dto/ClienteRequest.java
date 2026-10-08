package br.com.sistemas.chamados.dto;

import jakarta.validation.Email;
import jakarta.validation.NotBlank;
import jakarta.validation.Size;

public record ClienteRequest{
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,

    @NotBlank(message = "E-mail é obrigatório")
    @Size(max = 100, message = "E-mail inválido")
    String email,

    @Size(max = 100, message = "Telefone deve ter no máximo 20 caracteres")
    String telefone,
}{}