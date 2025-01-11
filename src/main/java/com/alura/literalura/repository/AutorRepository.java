package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND " +
            "(a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findByVivosPorAno(String ano);
}
