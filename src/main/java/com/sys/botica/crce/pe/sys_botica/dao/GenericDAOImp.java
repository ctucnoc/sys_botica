package com.sys.botica.crce.pe.sys_botica.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class GenericDAOImp implements GenericDAO{
	
	final 
	DataSource dataSource;

	public GenericDAOImp(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Float> totalSale(Integer id) {
		log.info("crce totalSale -> {} " + id);
		Connection cn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Float priceTotal = null;
		try {
			cn = dataSource.getConnection();
			cs = cn.prepareCall("{call crce.sp_total_sale_price(?)}");
			cs.setInt(1, id);
			cs.execute();
			rs = cs.getResultSet();
			if (rs.next()) {
				priceTotal = rs.getFloat(1);
			}
			return Optional.of(priceTotal);
		} catch (Exception e) {
			log.info(e.getMessage());
			return Optional.empty();
		} finally {
			try {
				if (cs != null)
					cs.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (cn != null)
					cn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}
	}

}
