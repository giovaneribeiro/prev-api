package br.com.prev.prevapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prev.prevapi.entity.Plano;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {

}
