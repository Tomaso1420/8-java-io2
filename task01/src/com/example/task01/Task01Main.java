package com.example.task01;

import java.io.*;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(extractSoundName(new File("C:\\Coding\\javaprolects\\lab1\\8-java-io2\\task01\\src\\main\\resources\\3724.mp3")));

    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String title = null;

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("ffprobe", "-v", "error", "-of", "flat", "-show_format", file.getAbsolutePath());
        processBuilder.directory(file.getParentFile());

        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title")) {
                    title = line.split("=")[1].replace("\"", "");
                    break;
                }
            }
        }

        process.waitFor();

        return title;

    }
}