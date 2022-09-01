package com.sys.botica.crce.pe.sys_botica.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.DailySaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.MonthSaleDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class GenericDAOImp implements GenericDAO {

	final DataSource dataSource;

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

	@Override
	public List<DailySaleDTO> findByDailySale(String date, Integer idsubsidiary) {
		log.info("crce findByDailySale -> {} " + date);
		Connection cn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		List<DailySaleDTO> list = null;
		try {
			cn = dataSource.getConnection();
			list = new ArrayList<>();
			cs = cn.prepareCall(SysBoticaConstant.SP_FINDBY_DAILY_SALE);
			cs.setString(1, date);
			cs.setString(2, SysBoticaConstant.STATE_ACTIVE);
			cs.setInt(3, idsubsidiary);
			cs.execute();
			rs = cs.getResultSet();
			while (rs.next()) {
				DailySaleDTO dailySaleDTO = new DailySaleDTO();
				dailySaleDTO.setName(rs.getString(1));
				dailySaleDTO.setY(rs.getInt(2));
				dailySaleDTO.setSelected(true);
				list.add(dailySaleDTO);
			}
			return list;
		} catch (Exception e) {
			log.info(e.getMessage());
			return list;
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

	@Override
	public List<MonthSaleDTO> findByMonthSale(Integer month, Integer idsubsidiary) {
		log.info("crce findByMonthSale -> {} " + month);
		Connection cn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		List<MonthSaleDTO> list = null;
		try {
			cn = dataSource.getConnection();
			list = new ArrayList<>();
			cs = cn.prepareCall(SysBoticaConstant.SP_FINDBY_MONTH_SALE);
			cs.setInt(1, month);
			cs.setString(2, SysBoticaConstant.STATE_ACTIVE);
			cs.setInt(3, idsubsidiary);
			cs.execute();
			rs = cs.getResultSet();
			while (rs.next()) {
				MonthSaleDTO monthSaleDTO = new MonthSaleDTO();
				monthSaleDTO.setName(rs.getString(1));
				monthSaleDTO.setY(rs.getInt(2));
				monthSaleDTO.setSelected(true);
				list.add(monthSaleDTO);
			}
			return list;
		} catch (Exception e) {
			log.info(e.getMessage());
			return list;
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
