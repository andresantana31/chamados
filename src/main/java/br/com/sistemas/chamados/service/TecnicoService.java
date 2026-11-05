package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.dto.TecnicoRequest;
import br.com.sistemas.chamados.dto.TecnicoResponse;
import br.com.sistemas.chamados.entity.Tecnico;
import br.com.sistemas.chamados.exception.RegraNegocioException;
import br.com.sistemas.chamados.repository.TecnicoRepository;

@Service
public class TecnicoService {

   private final TecnicoRepository repository;

   public TecnicoService(TecnicoRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public TecnicoResponse criar(TecnicoRequest dto) {
      if (repository.existsByEmail(dto.email())) {
         throw new RegraNegocioException("Já existe técnico com o e-mail " + dto.email());
      }
      Tecnico salvo = repository.save(
         new Tecnico(dto.nome(), dto.email(), dto.telefone(), dto.especialidade()));
      return TecnicoResponse.de(salvo);
   }

   @Transactional(readOnly = true)
   public List<TecnicoResponse> listar() {
      return repository.findAll().stream().map(TecnicoResponse::de).toList();
   }
}