package br.com.sistemas.chamados.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Comentario;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;

public record ChamadoDetalheResponse(
    Long id,
    String titulo,
    String descricao,
    StatusChamado status,
    Prioridade prioridade,
    Long clienteId,
    String clienteNome,
    String tecnico,
    LocalDateTime dataAbertura,
    List<ComentarioResponse> comentarios
) {
    public static ChamadoDetalheResponse de(Chamado c, List<Comentario> comentarios) {
        return new ChamadoDetalheResponse(
            c.getId(), c.getTitulo(), c.getDescricao(), c.getStatus(), c.getPrioridade(),
            c.getCliente().getId(), c.getCliente().getNome(),
            c.getTecnico(), c.getDataAbertura(),
            comentarios.stream().map(ComentarioResponse::de).toList());
    }
}