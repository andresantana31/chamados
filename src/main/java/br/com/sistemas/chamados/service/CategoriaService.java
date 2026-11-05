package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.dto.CategoriaRequest;
import br.com.sistemas.chamados.dto.CategoriaResponse;
import br.com.sistemas.chamados.entity.Categoria;
import br.com.sistemas.chamados.exception.RegraNegocioException;
import br.com.sistemas.chamados.repository.CategoriaRepository;

@Service
public class CategoriaService {

   private final CategoriaRepository repository;

   public CategoriaService(CategoriaRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public CategoriaResponse criar(CategoriaRequest dto) {
      if (repository.existsByNome(dto.nome())) {
         throw new RegraNegocioException("Já existe categoria com o nome " + dto.nome());
      }
      Categoria salva = repository.save(new Categoria(dto.nome(), dto.descricao()));
      return CategoriaResponse.de(salva);
   }

   @Transactional(readOnly = true)
   public List<CategoriaResponse> listar() {
      return repository.findAll().stream().map(CategoriaResponse::de).toList();
   }
}