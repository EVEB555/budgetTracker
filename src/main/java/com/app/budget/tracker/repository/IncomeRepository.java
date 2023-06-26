package com.app.budget.tracker.repository;


import com.app.budget.tracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IncomeRepository extends JpaRepository<Income, Long> {
}
