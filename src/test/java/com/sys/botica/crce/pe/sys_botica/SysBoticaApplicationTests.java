package com.sys.botica.crce.pe.sys_botica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@SpringBootTest
class SysBoticaApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(SysBoticaUtil.saleTotal((float)30.456));
	}

}
