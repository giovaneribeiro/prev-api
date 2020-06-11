package br.com.prev.prevapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prev.prevapi.entity.Empresa;
import br.com.prev.prevapi.entity.EmpresaPlano;
import br.com.prev.prevapi.entity.EmpresaPlanoPk;
import br.com.prev.prevapi.entity.Plano;

@Repository
public interface EmpresaPlanoRepository extends JpaRepository<EmpresaPlano, EmpresaPlanoPk> {

    long countByCodigoPlano(Plano plano);

    List<EmpresaPlano> findByCodigoEmpresa(Empresa empresa);
}
