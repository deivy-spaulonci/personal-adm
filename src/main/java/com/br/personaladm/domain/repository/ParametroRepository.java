package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.parametro.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {
    Parametro findParametroByChave(String chave);
}
