package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.HtmlTemplate;

@Repository
public interface HtmlTemplateRepository extends JpaRepository<HtmlTemplate, Long>{

	Optional<HtmlTemplate> findByCodeAndState(String code,String state);
}
