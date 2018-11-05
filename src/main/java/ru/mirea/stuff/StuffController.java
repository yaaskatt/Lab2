package ru.mirea.stuff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
public class StuffController {

    @Autowired
    private StuffService stuffService;

    @RequestMapping(value = "stuff", method = GET)
    @ResponseBody
    public List stuff() {
        return stuffService.stuff();
    }

    @RequestMapping(value = "stuff/name={name}&price={price}", method = PUT)
    @ResponseBody
    public void put(@PathVariable String name, @PathVariable int price) {
        stuffService.put(name, price);
    }
}