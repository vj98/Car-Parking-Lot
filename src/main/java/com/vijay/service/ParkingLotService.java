package com.vijay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ParkingLotService {

    public static Integer id = 0;
    public static String lotSize = null;
    public static HashMap<String, String[]> slotsAllocated = null;
    public static HashMap<String, ArrayList<String>> colorToRegNo = null;
    public static HashMap<String, ArrayList<String>> colorToSlot = null;
    public static HashMap<String, String> regNoToSlot = null;
    public static PriorityQueue<Integer> availableSlot = null;
    private static ParkingLotService parkingLotService = null;

    public static ParkingLotService getInstance() {
        if (parkingLotService != null) {
            return parkingLotService;
        }
        parkingLotService = new ParkingLotService();
        return parkingLotService;
    }

    /**
     * Add to the parking lot and allocate the slot number
     * @param regNo
     * @param color
     * @return int
     */
    public static int park(String regNo, String color) {
        Integer curr = id;
        if (!availableSlot.isEmpty()) {
            id = availableSlot.poll();
        } else if (id + 1 <= Integer.parseInt(lotSize)) {
            id++;
            curr = id;
        } else {
            return -1;
        }

        String idd = id.toString();
        slotsAllocated.put(idd, new String[]{idd, regNo, color});

        if (!colorToRegNo.containsKey(color)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(regNo);
            colorToRegNo.put(color, temp);
        } else {
            colorToRegNo.get(color).add(regNo);
        }

        if (!colorToSlot.containsKey(color)) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(id.toString());
            colorToSlot.put(color, temp);
        } else {
            colorToSlot.get(color).add(id.toString());
        }

        regNoToSlot.put(regNo, idd);
        int temp = id;
        id = curr;

        return temp;
    }

    /**
     * from the given slot the car will remove
     * @param lotId
     * @return int
     */
    public static int leave(String lotId) {
        if (!availableSlot.contains(Integer.parseInt(lotId))) {
            availableSlot.add(Integer.parseInt(lotId));
        }

        if (slotsAllocated.containsKey(lotId)) {
            String[] detail = slotsAllocated.get(lotId);
            String parkId = detail[0];
            String regNo = detail[1];
            String color = detail[2];
            slotsAllocated.remove(lotId);
            colorToRegNo.get(color).remove(regNo);
            colorToSlot.get(color).remove(parkId);
            regNoToSlot.remove(regNo);
            return Integer.parseInt(lotId);
        }
        return -1;
    }

    /**
     *  Status of the allocation lot
     * @return List<String[]>
     */
    public static List<String[]> status() {
        List<String[]> list = new ArrayList<>();
        if (id > 0) {
            for (Integer i = 1; i <= id; i++) {
                if (slotsAllocated.containsKey(i.toString())) {
                    String[] detail = slotsAllocated.get(i.toString());
                    list.add(detail);
                }
            }
        }
        return list;
    }

    /**
     * Fetch the registration number from the given color
     * @param color
     * @return List<String>
     */
    public static List<String> getRegistrationNumbersFromColor(String color) {
        List<String> regNo = null;
        if (colorToRegNo.containsKey(color)) {
            regNo = colorToRegNo.get(color);
        }

        return regNo;
    }

    /**
     * Fetch the slot number from the given color
     * @param color
     * @return List<String>
     */
    public static List<String> getSlotNumbersFromColor(String color) {
        List<String> regNo = null;
        if (colorToSlot.containsKey(color)) {
            regNo = colorToSlot.get(color);
        }
        return regNo;
    }

    /**
     * Fetch the slot number from the registration number
     * @param regNo
     * @return String
     */
    public static String getSlotNumberFromRegNo(String regNo) {
        String slot = null;
        if (regNoToSlot.containsKey(regNo)) {
            slot = regNoToSlot.get(regNo);
        }
        return slot;
    }

    /**
     * Create the availability of the lots
     * @param lotsz
     * @return
     */
    public String createParkingLot(String lotsz) {
        lotSize = lotsz;
        slotsAllocated = new HashMap<>();
        colorToRegNo = new HashMap<>();
        colorToSlot = new HashMap<>();
        regNoToSlot = new HashMap<>();
        availableSlot = new PriorityQueue<>();
        return lotSize;
    }
}