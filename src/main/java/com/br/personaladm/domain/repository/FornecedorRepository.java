package com.br.personaladm.domain.repository;

import com.br.personaladm.domain.model.Fornecedor;
import com.br.personaladm.domain.repository.generic.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends GenericRepository<Fornecedor> {
    @Query("FROM Fornecedor WHERE LOWER(nome) LIKE LOWER('%'||:valor||'%') " +
            "OR LOWER(razaoSocial) LIKE LOWER('%'||:valor||'%') " +
            "OR LOWER(cnpj) LIKE LOWER('%'||:valor||'%') ORDER BY nome")
    List<Fornecedor> findFornecedoresByNomeOrRazaoSocialrCnpj(String valor);

    @Query("FROM Fornecedor WHERE LOWER(nome) LIKE LOWER('%'||:valor||'%') " +
            "OR LOWER(razaoSocial) LIKE LOWER('%'||:valor||'%') " +
            "OR LOWER(cnpj) LIKE LOWER('%'||:valor||'%')")
    Page<Fornecedor> findFornecedoresByNomeOrRazaoSocialrCnpjPage(String valor, Pageable pageable);

    Optional<Fornecedor> findFornecedorByCnpj(String cnpj);
}
