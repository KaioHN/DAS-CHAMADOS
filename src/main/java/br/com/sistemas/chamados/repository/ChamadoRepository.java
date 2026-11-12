package br.com.sistemas.chamados.repository;

import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.enums.Prioridade;
import br.com.sistemas.chamados.enums.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    // Carrega cliente, categoria e técnico junto com o chamado (evita N+1 nas respostas).
    @Override
    @EntityGraph(attributePaths = {"cliente", "categoria", "tecnico"})
    Optional<Chamado> findById(Long id);

    @EntityGraph(attributePaths = {"cliente", "categoria", "tecnico"})
    @Query("""
        SELECT c from Chamado c
        WHERE (:status is null or c.status = :status)
          and (:prioridade is null or c.prioridade = :prioridade)
          and (:clienteId is null or c.cliente.id = :clienteId)
        """)
    Page<Chamado> filtrar(
            @Param("status") StatusChamado status,
            @Param("prioridade") Prioridade prioridade,
            @Param("clienteId") Long clienteId,
            Pageable pageable
            );

    @EntityGraph(attributePaths = {"cliente", "categoria", "tecnico"})
    Page<Chamado> findByCliente_Id(Long clienteId, Pageable pageable);
}
