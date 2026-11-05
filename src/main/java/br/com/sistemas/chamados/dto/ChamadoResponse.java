package br.com.sistemas.chamados.dto;

import java.time.LocalDateTime;

import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;

public record ChamadoResponse(
    Long id,
    String titulo,
    String descricao,
    StatusChamado status,
    Prioridade prioridade,
    Long clienteId,
    String clienteNome,
    String tecnico,
    LocalDateTime dataAbertura
) {
    public static ChamadoResponse de(Chamado c) {
        return new ChamadoResponse(
            c.getId(), c.getTitulo(), c.getDescricao(), c.getStatus(), c.getPrioridade(),
            c.getCliente().getId(), c.getCliente().getNome(),
            c.getTecnico(), c.getDataAbertura());
    }
}