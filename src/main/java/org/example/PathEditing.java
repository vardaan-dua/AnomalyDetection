package org.example;

public class PathEditing {
    public static String extractPath(String path , int remove){
        String[] parts = path.split("/");
        int numPartsToKeep = parts.length - remove ;
        StringBuilder extractedPath = new StringBuilder();
        for (int i = 0; i < numPartsToKeep; i++) {
            extractedPath.append(parts[i]);
            if (i < numPartsToKeep - 1) {
                extractedPath.append("/");
            }
        }
        return extractedPath.toString();
    }
}
