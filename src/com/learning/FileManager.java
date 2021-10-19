package com.learning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {

    private File pointer;

    public FileManager() {
    }

    public void cd(String path) throws FileNotFoundException {
//        pointer = new File(path);

        if (pointer != null) {
            if (path.equals("..")) {
                path = pointer.getPath().substring(0, pointer.getPath().lastIndexOf(File.separator)+1);
                if (path.isEmpty()){
//                    path = ""
                }
//                System.out.println("UP "+ path);
            } else {
                String[] list = pointer.list();
                if (list != null) {
                    int index = -1;
                    for (int i = 0; i < list.length; i++) {
                        if (path.equals(list[i])) {
                            index = i;
                        }
                    }
                    if (index != -1) {
                        path = pointer.getAbsolutePath() + File.separator + path;
                    }
                }
            }
        }
        pointer = new File(path);

        if (pointer.exists()) {
            if (!pointer.isDirectory()) {
                throw new IllegalArgumentException("Unable to cd: file is not a directory");
            }
        } else {
            throw new FileNotFoundException("Unable to cd: file does not exist");
        }
    }

    public void pwd() throws FileNotFoundException {
        if (pointer == null) {
            throw new FileNotFoundException("Unable to pwd: pointer is not initialized");
        } else {
            System.out.println(pointer.getAbsolutePath());
        }
    }

    public void ls() throws FileNotFoundException {
        if (pointer == null || !pointer.exists()) {
            throw new FileNotFoundException("Unable to ls: pointer is not initialized");
        }
        if (pointer.listFiles() == null) {
            throw new IllegalStateException("Unable to ls: file list is null");
        }
        if (!pointer.isDirectory()) {
            throw new IllegalStateException("Unable to ls: pointer is not a directory");
        }
        Map<Boolean, List<File>> map = Stream.of(pointer.listFiles()).sorted(Comparator.comparing(File::getName)).collect(Collectors.partitioningBy(File::isDirectory));
        System.out.println("Directories:");

        for (File file : map.get(true)) {
            System.out.println(file.getName());
        }
        System.out.println("\nFiles:");
        for (File file : map.get(false)) {
            System.out.println(file.getName());
        }
    }

    public void cat(String fileName) throws FileNotFoundException {
        if (pointer == null || !pointer.exists()) {
            throw new FileNotFoundException("Unable to ls: pointer is not initialized");
        }
        File[] files = pointer.listFiles((dir, name) -> name.equals(fileName));
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("Unable to ls: fileName not found");
        }
        File file = files[0];
        if (!file.isFile()) {
            throw new IllegalArgumentException("Unable to ls: fileName is not a file");
        }
        Scanner fileScanner = new Scanner(file);
        String line;
        System.out.println(file.getName());
        while (fileScanner.hasNextLine() && (line = fileScanner.nextLine()) != null) {
            System.out.println(line);
        }
        System.out.println("End");
    }
}
