package br.com.sistemas.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TecnicoRequest(
    @NotBlank(message = "Técnico é obrigatório")
    @Size(max = 100, message = "Técnico deve ter no máximo 100 caracteres")
    String tecnico
) { }