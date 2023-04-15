package com.epam.mjc.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class FileReader {
    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        List<String> lines = null;

        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillProfile(profile, lines);
        return profile;
    }

    private void fillProfile(Profile profile, List<String> lines) {
        Map<String, Consumer<String>> map = getCommands(profile);
        String[] params;
        for (String line : lines) {
            params = line.split(": ");
            map.get(params[0]).accept(params[1]);
        }
    }

    private Map<String, Consumer<String>> getCommands(Profile profile) {
        Map<String, Consumer<String>> commands = new HashMap<>();
        commands.put("Name", profile::setName);
        commands.put("Age", s -> profile.setAge(Integer.parseInt(s)));
        commands.put("Email", profile::setEmail);
        commands.put("Phone", s -> profile.setPhone(Long.parseLong(s)));
        return commands;
    }
}

