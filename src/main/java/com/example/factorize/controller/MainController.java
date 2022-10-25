package com.example.factorize.controller;

import com.example.factorize.domain.Numbers;
import com.example.factorize.domain.User;
import com.example.factorize.function.Factorize;
import com.example.factorize.repos.NumberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;


@Controller
public class MainController {
    private final Factorize factorize = new Factorize(BigInteger.ZERO);
    @Autowired
    private NumberRepo numberRepo;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Numbers> numbers = numberRepo.findAll();
        model.addAttribute("numbers", numbers);
        return "main";
    }

    @PostMapping("/main")
    public String factorization(
            @AuthenticationPrincipal User user,
            @RequestParam String number,
            Model model
    ) {
        BigInteger integer;
        try {
            integer = new BigInteger(number);
        } catch (Exception e) {
            model.addAttribute("message", "Enter valid number");
            return "main";
        }
        List<Numbers> nbs = numberRepo.findBybigInteger(number);
        model.addAttribute("message", "");

        if (nbs.size() != 0) {
            model.addAttribute("numbers", nbs.get(0));
            return "main";
        }

        factorize.changeNumber(integer);
        factorize.primeFactors();
        Numbers nb = new Numbers(number, factorize.getResultString(), user);

        numberRepo.save(nb);
        model.addAttribute("users", List.of(user.getFactorizeNumbers()));


        Iterable<Numbers> numbers = numberRepo.findAll();
        model.addAttribute("numbers", numbers);

        return "main";
    }

    @PostMapping("filter")
    public String filter(
            @RequestParam(defaultValue = "") String filter,
            Model model
    ) {
        Numbers number = null;
        for (Numbers n : numberRepo.findAll()) {
            if (n.getBigInteger().equals(filter)) {
                number = n;
                break;
            }
        }
        model.addAttribute("numbers", number);

        return "main";
    }
}