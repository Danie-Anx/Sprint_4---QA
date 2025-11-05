package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.RegistroBateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroBateriaRepository extends JpaRepository<RegistroBateria, Long> {
    List<RegistroBateria> findByBeaconId(Long beaconId);
    List<RegistroBateria> findByBeaconIdAndDataHoraBetween(Long beaconId, LocalDateTime inicio, LocalDateTime fim);
    List<RegistroBateria> findByNivelBateriaLessThan(Integer nivel);
}
