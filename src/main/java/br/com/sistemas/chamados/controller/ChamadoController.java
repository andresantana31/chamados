package br.com.sistemas.chamados.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistemas.chamados.dto.ChamadoDetalheResponse;
import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.dto.ComentarioRequest;
import br.com.sistemas.chamados.dto.ComentarioResponse;
import br.com.sistemas.chamados.dto.StatusRequest;
import br.com.sistemas.chamados.dto.TecnicoRequest;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;
import br.com.sistemas.chamados.service.ChamadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponse criar(@Valid @RequestBody ChamadoRequest dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<ChamadoResponse> listar(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) Prioridade prioridade,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String busca) {
        return service.listar(status, prioridade, clienteId, busca);
    }

    @GetMapping("/{id}")
    public ChamadoDetalheResponse detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    @PatchMapping("/{id}/status")
    public ChamadoResponse atualizarStatus(@PathVariable Long id,
                                           @Valid @RequestBody StatusRequest dto) {
        return service.atualizarStatus(id, dto.status());
    }

    @PatchMapping("/{id}/tecnico")
    public ChamadoResponse atribuirTecnico(@PathVariable Long id,
                                           @Valid @RequestBody TecnicoRequest dto) {
        return service.atribuirTecnico(id, dto.tecnico());
    }

    @PostMapping("/{id}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioResponse comentar(@PathVariable Long id,
                                       @Valid @RequestBody ComentarioRequest dto) {
        return service.adicionarComentario(id, dto);
    }
}