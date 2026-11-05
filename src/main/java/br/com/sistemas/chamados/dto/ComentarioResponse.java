package br.com.sistemas.chamados.dto;

import java.time.LocalDateTime;

import br.com.sistemas.chamados.entity.Comentario;

public record ComentarioResponse(Long id, String autor, String texto, LocalDateTime dataCriacao) {

    public static ComentarioResponse de(Comentario c) {
        return new ComentarioResponse(c.getId(), c.getAutor(), c.getTexto(), c.getDataCriacao());
    }
}