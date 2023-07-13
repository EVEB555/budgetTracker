package com.app.budget.tracker.service;


import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.IncomeDTO;
import com.app.budget.tracker.repository.CategoryRepository;
import com.app.budget.tracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class IncomeService {

    private final IncomeRepository repository;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    public IncomeService(IncomeRepository repository, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    public void addIncomeItem(String category, BigDecimal amount) {
        Income income = new Income();
        Category existingCategory = categoryRepository.findByDescription(category);

        if (existingCategory != null && existingCategory.getDescription().equals(category)) {
            income.setCategory(existingCategory.getDescription());
        } else {
            categoryService.createCategory(category);
            income.setCategory(category);
        }

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
