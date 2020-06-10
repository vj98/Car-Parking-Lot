package com.vijay.commands;

import com.vijay.service.ParkingLotService;

import java.lang.reflect.Method;
import java.util.HashMap;


public class CommandsExecutor {
    public HashMap <String, Method> commandsHashMap = null;

    public CommandsExecutor() {
        commandsHashMap = new HashMap<String, Method>();
        try {
            populateCommandsHashMap();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void populateCommandsHashMap() throws NoSuchMethodException {
        commandsHashMap.put("create_parking_lot", ParkingLotService.class.getMethod("createParkingLot", String.class));
        commandsHashMap.put("park", ParkingLotService.class.getMethod("park", String.class, String.class));
        commandsHashMap.put("leave", ParkingLotService.class.getMethod("leave", String.class));
        commandsHashMap.put("status", ParkingLotService.class.getMethod("status"));
        commandsHashMap.put("registration_numbers_for_cars_with_colour", ParkingLotService.class.getMethod("getRegistrationNumbersFromColor", String.class));
        commandsHashMap.put("slot_numbers_for_cars_with_colour", ParkingLotService.class.getMethod("getSlotNumbersFromColor", String.class));
        commandsHashMap.put("slot_number_for_registration_number", ParkingLotService.class.getMethod("getSlotNumberFromRegNo", String.class));
    }
}
