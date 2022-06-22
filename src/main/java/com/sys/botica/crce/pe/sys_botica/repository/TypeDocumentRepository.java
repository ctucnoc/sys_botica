package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.TypeDocument;

@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long>{
	public List<TypeDocument> findByState(String state);
	public Optional<TypeDocument> findByIdAndState(Long id,String state);
}
