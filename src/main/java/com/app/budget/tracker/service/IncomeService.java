package com.app.budget.tracker.service;

import com.app.budget.tracker.model.IncomeDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class IncomeService {

    public IncomeService() {
    }

    public IncomeDTO findIncome(){
        //nezinau ar reikalingas sitas
        return null;
    }

    public void saveIncome(IncomeDTO incomeDTO) {
        //seivins repositorijoje, nereikia nieko grazint
    }

    public void deleteIncome(UUID id){

    }

    public IncomeDTO getIncome(UUID id){
        return null;
    }


}
