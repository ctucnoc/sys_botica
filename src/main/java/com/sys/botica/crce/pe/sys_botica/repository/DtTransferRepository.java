package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.model.DtTransfer;

@Repository
public interface DtTransferRepository extends JpaRepository<DtTransfer, Long> {

	@Query(value = SysBoticaConstant.SP_SAVE_DT_TRANSFER, nativeQuery = true)
	public DtTransfer SaveSQL(String key_word, String state);

	@Query(value = SysBoticaConstant.SP_SEARCH_KEY_WORD_DT_TRANSFER, nativeQuery = true)
	public List<DtTransfer> findByKeyWordSQL(Integer idsubsidiary, String key_word, String state);
	
	Optional<DtTransfer> findByIdAndState(Long id, String state);
	
}
