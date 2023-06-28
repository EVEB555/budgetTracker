package com.app.budget.tracker.service;


import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.IncomeDTO;
import com.app.budget.tracker.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IncomeService {

    private final IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public void addIncomeItem(String category, BigDecimal amount){
        Income income = new Income();
        income.setCategory(category);
        income.setAmount(amount);
        LocalDate currentDate = LocalDate.now();
        income.setRecordDate(currentDate);
        repository.save(income);
    }

    public List<IncomeDTO> getAllIncomeItems(){
        List<Income> incomeList = repository.findAll();
        //var incomeList = repository.findAll();
        /*List<IncomeDTO> result = new ArrayList<>();
        for(Income income : all) {
            IncomeDTO item = new IncomeDTO();
            item.setId(income.getId());
            item.setCategory(income.getCategory());
            item.setAmount(income.getAmount());
            result.add(item);
        }*/

        return incomeList.stream().map(income -> {
            IncomeDTO item = new IncomeDTO();
            item.setId(income.getId());
            item.setCategory(income.getCategory());
            item.setAmount(income.getAmount());
            item.setRecordDate(income.getRecordDate());
            return item;
        }).toList();
    }

}
