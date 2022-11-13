package com.github.dlots.webapp;

import java.io.File;
import java.util.Objects;

public class MainFiles {
    public static void printFilesRecursively(String path) {
        File f = new File(path);
        for (String next_path: Objects.requireNonNull(f.list())) {
            if (next_path.equals(".git") || next_path.equals(".idea")) {
                continue;
            }
            File next = new File(path + "/" + next_path);
            if (next.isDirectory()) {
                printFilesRecursively(next.getAbsolutePath());
            }
            if (next.isFile()) {
                System.out.println(next.getName());
            }
        }
    }

    public static void main(String[] args) {
        printFilesRecursively("E:\\IdeaProjects\\basejava");
    }
}
