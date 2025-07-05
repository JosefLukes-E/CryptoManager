package com.engeto.CryptoManager.Service;

import com.engeto.CryptoManager.Model.Crypto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CryptoService {

    private List<Crypto> cryptos = new ArrayList<>();
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addCrypto(Crypto crypto) {
        cryptos.add(crypto);
    }

    public List<Crypto> listAllCrypto() {
        return new ArrayList<>(cryptos);
    }

    public Crypto getCryptoById(int id) {
        for (Crypto c : cryptos) {
            if (c.id().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void createInitialList() {
        Random random = new Random();
        Double priceRadndom = random.nextDouble() * 10;
        Double quantityRadndom = random.nextDouble() * 5;

        cryptos.add(new Crypto(atomicInteger.incrementAndGet(), "bitcoin", "BTC", priceRadndom, quantityRadndom));
        cryptos.add(new Crypto(atomicInteger.incrementAndGet(), "ethereum", "ETH", priceRadndom, quantityRadndom));
        cryptos.add(new Crypto(atomicInteger.incrementAndGet(), "dogecoin", "ELON", priceRadndom, quantityRadndom));
        cryptos.add(new Crypto(atomicInteger.incrementAndGet(), "monero", "MNR", priceRadndom, quantityRadndom));
    }

    public Double portfolioValue() {
        Double totalValue = 0.0;
        for (Crypto c : cryptos) {
            totalValue += c.price() * c.quantity();
        }
        return totalValue;
    }

    public boolean removeCryptoById(int id) {

        for (Crypto c : cryptos) {
            if (c.id().equals(id)) {
                cryptos.remove(c);
                return true;
            }
        }
        return false;
    }

    public void removeCryptoByInex(int id) {
        cryptos.remove(id);
    }
}
