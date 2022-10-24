package com.example.factorize.function;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Factorize {
    private BigInteger n;
    private Map<BigInteger, Integer> m;
    private int lengthN;
    private Thread thread;
    private boolean exit = false;

    private void initialize(BigInteger n) {
        this.n = n;
        m = new HashMap<>();
        lengthN = String.valueOf(n).length();
        exit = false;
    }

    public void stop() {
        exit = true;
    }

    private boolean isExit() {
        return exit;
    }

    public boolean changeNumber(BigInteger number) {
        if (getStatus() == 100.0 || exit) {
            initialize(number);
            return true;
        }
        return false;
    }

    public Factorize(BigInteger n) {
        initialize(n);
    }

    public double getStatus() {
        boolean probablePrime = n.isProbablePrime((int) Math.log(n.doubleValue()));
        if (probablePrime) {
            return 100.0;
        }
        return Math.round(((double) (lengthN - String.valueOf(n).length()) / lengthN) * 100);
    }

    public void primeFactors() {
        thread = new Thread(() -> {
            int k = 0;
            for (BigInteger i = BigInteger.TWO; i.compareTo(n.sqrt()) < 0; i = i.add(BigInteger.ONE)) {
                if (isExit()) {
                    break;
                }
                while ((n.remainder(i)).compareTo(BigInteger.ZERO) == 0) {
                    m.putIfAbsent(i, 0);
                    m.put(i, m.get(i) + 1);
                    n = n.divide(i);
                    k++;

                    //update status
                    if (k % 5 == 0) {
                        getStatus();

                    }
                }
            }

            if (n.compareTo(BigInteger.TWO) > 0) {
                m.putIfAbsent(n, 1);
            }
        });
        thread.start();
    }

    public String getResultString() {
        var res = getResult();
        if (res == null) {
            return "";
        }

        res = new TreeMap<>(res);
        StringBuilder result = new StringBuilder();
        for (Map.Entry<BigInteger, Integer> entry : res.entrySet()) {
            result.append(entry.getKey()).append("^").append(entry.getValue()).append(" ");
        }
        return result.toString();
    }

    public Map<BigInteger, Integer> getResult() {
        try {
            while (thread.isAlive()) {
            }
        } catch (NullPointerException e) {
            return null;
        }

        return m;
    }
}