package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.dto.ChamadoDetalheResponse;
import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.dto.ComentarioRequest;
import br.com.sistemas.chamados.dto.ComentarioResponse;
import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Cliente;
import br.com.sistemas.chamados.entity.Comentario;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;
import br.com.sistemas.chamados.exception.RecursoNaoEncontradoException;
import br.com.sistemas.chamados.repository.ChamadoRepository;
import br.com.sistemas.chamados.repository.ClienteRepository;
import br.com.sistemas.chamados.repository.ComentarioRepository;

@Service
public class ChamadoService {

    private final ChamadoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ComentarioRepository comentarioRepository;

    public ChamadoService(ChamadoRepository repository,
                          ClienteRepository clienteRepository,
                          ComentarioRepository comentarioRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Transactional
    public ChamadoResponse criar(ChamadoRequest dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
            .orElseThrow(() -> new RecursoNaoEncontradoException(
                "Cliente " + dto.clienteId() + " não encontrado"));

        Chamado salvo = repository.save(
            new Chamado(dto.titulo(), dto.descricao(), dto.prioridade(), cliente));
        return ChamadoResponse.de(salvo);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listar(StatusChamado status, Prioridade prioridade,
                                        Long clienteId, String busca) {
        Specification<Chamado> spec = (root, query, cb) -> cb.conjunction();

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }
        if (prioridade != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("prioridade"), prioridade));
        }
        if (clienteId != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("cliente").get("id"), clienteId));
        }
        if (busca != null && !busca.isBlank()) {
            String termo = "%" + busca.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("titulo")), termo));
        }

        return repository.findAll(spec, Sort.by(Sort.Direction.DESC, "dataAbertura"))
            .stream().map(ChamadoResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ChamadoDetalheResponse detalhar(Long id) {
        Chamado chamado = buscarEntity(id);
        List<Comentario> comentarios =
            comentarioRepository.findByChamadoIdOrderByDataCriacaoAsc(id);
        return ChamadoDetalheResponse.de(chamado, comentarios);
    }

    @Transactional
    public ChamadoResponse atualizarStatus(Long id, StatusChamado novoStatus) {
        Chamado chamado = buscarEntity(id);
        chamado.setStatus(novoStatus);
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse atribuirTecnico(Long id, String tecnico) {
        Chamado chamado = buscarEntity(id);
        chamado.setTecnico(tecnico.trim());
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ComentarioResponse adicionarComentario(Long id, ComentarioRequest dto) {
        Chamado chamado = buscarEntity(id);
        Comentario salvo = comentarioRepository.save(
            new Comentario(chamado, dto.autor(), dto.texto()));
        return ComentarioResponse.de(salvo);
    }

    private Chamado buscarEntity(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(
                "Chamado " + id + " não encontrado"));
    }
}