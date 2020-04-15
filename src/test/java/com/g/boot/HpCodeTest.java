package com.g.boot;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HpCodeTest {

	@Test
	public void hpCheck() {
		String code = "";
		Random random = new Random();
		for(int i = 0; i < 5; i++) {
			code += random.nextInt(10);
		}
		System.out.println(code);
	}
	
}
