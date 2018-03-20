package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static boolean useCollection = true;

    static HashMap<String, Long> imsiToEndpoint = new HashMap<>();
    static HashMap<Long, List<String>> endpointToImsis = new HashMap<>();

    private static long getImsiToEndpoint(long imsi) {
        return imsiToEndpoint.get(String.valueOf(imsi));
    }

    private static void setImsiToEndpoint(long imsi, long endpoint) {
        imsiToEndpoint.put(String.valueOf(imsi), endpoint);

        if (!useCollection) {
            if (!endpointToImsis.containsKey(endpoint)) {
                endpointToImsis.put(endpoint, new LinkedList<>());
            }
            endpointToImsis.get(endpoint).add(String.valueOf(imsi));
        }
    }

    private static void removeImsiMapping(long endpointId) {
        if (useCollection) {
            /// OLD

            List<Long> ids = new LinkedList<>();
            ids.add(endpointId);
            imsiToEndpoint.values().removeAll(ids);
        } else {
            /// NEW
            if (endpointToImsis.containsKey(endpointId)) {
                endpointToImsis.get(endpointId).forEach(imsi -> imsiToEndpoint.remove(imsi));
                endpointToImsis.remove(endpointId);
            }
        }
    }

    public static long read(long imsi) {
        long start = System.nanoTime();
        getImsiToEndpoint(imsi);
        long end = System.nanoTime();

        return (end - start);
    }

    static void cleanAndFill(int endpointId) {
        removeImsiMapping(endpointId);
        fill(endpointId);
    }

    static void fill(int endpointId) {
        setImsiToEndpoint(206280040000000L + endpointId, endpointId);
        setImsiToEndpoint(306280040000000L + endpointId, endpointId);
        setImsiToEndpoint(406280040000000L + endpointId, endpointId);
        setImsiToEndpoint(506280040000000L + endpointId, endpointId);
    }

    public static void cleanAndFillMeasured(int endpointId) {
        long start = System.nanoTime();
        cleanAndFill(endpointId);
        long end = System.nanoTime();

        System.out.println(end - start);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100000; i++) {
            fill(i);
        }

        for (int i = 0; i < 100; i++) {
            cleanAndFillMeasured(i);
        }
    }

}
