package com.springboot.healthypath.controller;

import java.io.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PythonController {
    @GetMapping("/run-python")
    public ResponseEntity<String> runPythonScript() {
        try {
            // Python 스크립트 실행
            String pythonScriptPath = "python/food.py"; // Python 스크립트 경로
            ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScriptPath);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return ResponseEntity.ok(output.toString());
            } else {
                return ResponseEntity.status(500).body("Python script execution failed:\n" + output);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/recipe")
    public String insertRecipe() {
        StringBuilder output = new StringBuilder();
        try {
            // Python 파일 경로 (절대 경로로 설정 필요)
            String pythonFilePath = "python/recipe.py"; 

            // 프로세스 실행
            Process process = new ProcessBuilder("python3", pythonFilePath).start();

            // Python 실행 결과 읽기
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            // 에러 스트림 처리 (선택)
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    output.append("ERROR: ").append(line).append(System.lineSeparator());
                }
            }

            // 프로세스가 종료될 때까지 대기
            int exitCode = process.waitFor();
            output.append("Python script finished with exit code: ").append(exitCode);

        } catch (Exception e) {
            return "Error while running Python script: " + e.getMessage();
        }
        return output.toString();
    }
}