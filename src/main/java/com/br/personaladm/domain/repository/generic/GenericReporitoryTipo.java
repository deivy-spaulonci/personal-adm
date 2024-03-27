package com.br.personaladm.domain.repository.generic;

import com.br.personaladm.domain.model.tipo.generic.GenericEntityTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericReporitoryTipo<T extends GenericEntityTipo<T>>
        extends JpaRepository<T, Long> {
    List<T> findTByNomeContainingIgnoreCaseOrderByNome(String nome);
}
