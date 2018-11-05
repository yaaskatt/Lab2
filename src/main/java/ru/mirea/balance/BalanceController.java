package ru.mirea.balance;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "balance/{id}", method = GET)
    @ResponseBody
    public List balance(@PathVariable int id) {
        return balanceService.balance(id);
    }

    @RequestMapping(value = "balance/{id}bal={bal}", method = PUT)
    @ResponseBody
    public void put(@PathVariable int id, @PathVariable double bal) {
        balanceService.put(id, bal);
    }

}
