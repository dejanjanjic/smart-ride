package net.etfbl.ip.smart_ride_backend.util;

import java.util.Random;

public class RandomGenerator {
    public static Double getRandomPosition(Double from, Double to){
        return from + Math.random() * (to - from);
    }
}
