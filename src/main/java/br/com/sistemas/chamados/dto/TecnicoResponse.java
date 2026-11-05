package br.com.sistemas.chamados.dto;

import br.com.sistemas.chamados.entity.Tecnico;

public record TecnicoResponse(Long id, String nome, String email, String telefone, String especialidade) {
    public static TecnicoResponse de(Tecnico t) {
        return new TecnicoResponse(t.getId(), t.getNome(), t.getEmail(), t.getTelefone(), t.getEspecialidade());
    }
}