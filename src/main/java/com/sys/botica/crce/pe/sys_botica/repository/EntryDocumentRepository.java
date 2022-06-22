package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.EntryDocument;

@Repository
public interface EntryDocumentRepository extends JpaRepository<EntryDocument, Long> {
	public List<EntryDocument> findByState(String state);
	
	public Optional<EntryDocument> findByIdAndState(Long id, String state);
}
