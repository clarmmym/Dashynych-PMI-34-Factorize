package com.example.factorize.controller;

import com.example.factorize.domain.Numbers;
import com.example.factorize.domain.User;
import com.example.factorize.function.Factorize;
import com.example.factorize.repos.NumberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Controller
public class MainController {
    private final Factorize factorize = new Factorize(BigInteger.ZERO);
    @Autowired
    private NumberRepo numberRepo;

    private final HttpClient client = HttpClient.newHttpClient();

    @Autowired
    private Environment env;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model
    ) {
        Iterable<Numbers> numbers = numberRepo.findAll();
        model.addAttribute("filter", filter);

        for (Numbers n : numberRepo.findAll()) {
            if (n.getBigInteger().equals(filter)) {
                model.addAttribute("numbers", List.of(n));

                return "main";
            }
        }
        model.addAttribute("numbers", numbers);
        return "main";
    }

    @PostMapping("/main")
    public String factorization(
            @AuthenticationPrincipal User user,
            @RequestParam String number,
            Model model
    ) {

        List<Numbers> nbs = numberRepo.findBybigInteger(number);
        model.addAttribute("message", "");

        if (nbs.size() != 0) {

            Numbers nbm = numberRepo.findBybigInteger(number).get(0);
            nbm.setListUser(user);
            numberRepo.save(nbm);

            model.addAttribute("numbers", List.of(nbm));

            return "main";
        }

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:" + env.getProperty("fact_port") + "/?number=" + number))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
        } catch (URISyntaxException e) {
            System.out.println("Cannot create request. Massage: " + e.getMessage());
            System.exit(0);
        }

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Cannot send a request. Message: " + e.getMessage());
            return "main";

        }

        if (response.statusCode() >= 300 || response.statusCode() < 200) {
            System.out.println("Request error. Status code: " + response.statusCode() + " " + response.body());
            return "main";

        }

        if (response.body().equals("")) {
            model.addAttribute("message", "Something happened. Try again");
            return "main";
        }

        Numbers nb = new Numbers(number, response.body(), user);

        numberRepo.save(nb);

        model.addAttribute("result", nb);


//        request = null;
//        String status;
//
//        try {
//            request = HttpRequest.newBuilder()
//                    .uri(new URI("http://localhost:" + env.getProperty("fact_port") + "/status?number=" + number))
//                    .GET()
//                    .build();
//        } catch (URISyntaxException e) {
//            System.out.println("Cannot create request. Massage: " + e.getMessage());
//        }
//
//        response = null;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            System.out.println("Cannot send a request. Message: " + e.getMessage());
//        }
//
//        assert response != null;
//        status = response.body();
//        model.addAttribute("status", status);


        return "main";
    }

    @GetMapping("/numberList")
    public String userList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("usersList", user.getFactorizeNumbers());

        return "numbersList";
    }
}