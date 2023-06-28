package com.app.budget.tracker;

import com.app.budget.tracker.controller.IncomeController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BudgetTrackerApplicationTests {

	@Autowired
	private IncomeController incomeController;

	@Test
	void contextLoads() {
		Assertions.assertThat(incomeController).isNotNull();
	}

}
