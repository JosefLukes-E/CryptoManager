package com.engeto.CryptoManager.Controller;

import com.engeto.CryptoManager.Model.Crypto;
import com.engeto.CryptoManager.Service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {
    @Autowired
    CryptoService cryptoService;

    @GetMapping
    public List<Crypto> listAllCryptoSorted(@RequestParam(defaultValue = "id", required = false) String sort) {
        cryptoService.createInitialList();
        Comparator<Crypto> comparator = switch (sort) {
            case "quantity" -> Comparator.comparing(Crypto::quantity);
            case "price" -> Comparator.comparing(Crypto::price);
            case "name" -> Comparator.comparing(Crypto::name);
            case null, default -> Comparator.comparing(Crypto::id);
        };
        return cryptoService.listAllCrypto().stream().sorted(comparator).toList();
    }

    @GetMapping("/{id}")
    public Crypto listCryptoId(@PathVariable int id) {
        cryptoService.createInitialList();
        return cryptoService.getCryptoById(id);
    }

    @GetMapping("/hello")
    public String baseurl() {
        return "Svět kryptoměn tě vítá!";
    }

    @GetMapping("/portfolio-value")
    public Double totalValue() {
        return cryptoService.portfolioValue();
    }

    @PostMapping
    public void addCryptoAPI(@RequestBody Crypto crypto) {
        cryptoService.addCrypto(crypto);
    }

    @PutMapping("/{id}")
    public boolean updateCryptoById(@RequestBody Crypto crypto, @PathVariable int id) {
        for (Crypto c : cryptoService.listAllCrypto()) {
            if (c.id().equals(id)) {
                cryptoService.removeCryptoById(id);
                cryptoService.addCrypto(crypto);
                return true;
            }
        }
        return false;
    }

    @GetMapping("removefirst")
    public void removeFirstCrypto() {
        cryptoService.removeCryptoByInex(0);
    }
}
