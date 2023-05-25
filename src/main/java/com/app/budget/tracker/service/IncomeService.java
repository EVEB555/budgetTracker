package com.app.budget.tracker.service;

import com.app.budget.tracker.model.Income;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class IncomeService {

    public IncomeService() {
    }

    public Income findIncome(){
        //nezinau ar reikalingas sitas
        return null;
    }

    public void saveIncome(Income income) {
        //seivins repositorijoje, nereikia nieko grazint
    }

    public void deleteIncome(UUID id){

    }

    public Income getIncome(UUID id){
        return null;
    }


}
