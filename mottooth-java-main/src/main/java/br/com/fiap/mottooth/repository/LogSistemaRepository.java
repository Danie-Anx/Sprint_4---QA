package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.LogSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogSistemaRepository extends JpaRepository<LogSistema, Long> {
    List<LogSistema> findByUsuarioId(Long usuarioId);
    List<LogSistema> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
