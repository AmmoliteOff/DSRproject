package org.dsr.practice.dao;

import org.dsr.practice.models.Debt;
import org.dsr.practice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.dsr.practice.repos.DebtsRepository;

import java.util.List;

@Component
public class DebtsDAO {
    DebtsRepository debtsRepository;
    public DebtsDAO(@Autowired DebtsRepository debtsRepository) {
        this.debtsRepository = debtsRepository;
    }

    public List<Debt> getDebtByUser(User user){
        return debtsRepository.findByUserId(user.getId());
    }
}
