package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMovimentacaoRepository extends JpaRepository<TipoMovimentacao, Long> {
    TipoMovimentacao findByDescricao(String descricao);
}
