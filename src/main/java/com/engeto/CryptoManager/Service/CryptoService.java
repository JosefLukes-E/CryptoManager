package com.engeto.CryptoManager.Service;

import com.engeto.CryptoManager.Model.Crypto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CryptoService {

    private List<Crypto> cryptos = new ArrayList<>();
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private Map<String, String> cryptoTypes = new HashMap<>();

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
        cryptoTypes.put("bitcoin", "BTC");
        cryptoTypes.put("ripple", "XRP");
        cryptoTypes.put("monero", "XMP");
        cryptoTypes.put("dogecoin", "ELN");
        cryptoTypes.put("ethereum", "ETH");
        for (int i = 0; i < 4; i++) {
            Double priceRadndom = random.nextDouble() * 10;
            Double quantityRadndom = random.nextDouble() * 5;
            List<String> list = new ArrayList<>(cryptoTypes.keySet().stream().toList());
            Collections.shuffle(list);
            String cryptoName = list.getFirst();
            cryptos.add(new Crypto(atomicInteger.incrementAndGet(), cryptoName, cryptoTypes.get(cryptoName), priceRadndom, quantityRadndom));
        }
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
