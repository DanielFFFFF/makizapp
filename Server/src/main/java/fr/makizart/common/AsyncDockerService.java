package fr.makizart.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AsyncDockerService {
    @Async
    public void runDockerContainer(String projectId) {

        System.out.println("Running docker on thread: " + Thread.currentThread().getName());

        System.out.println("Current working directory: " + System.getProperty("user.dir"));  // Log working directory

        try {
            String markerDirPath = "SpringBootServer/mind-markers/markers/" + projectId;

            Path path = Paths.get(markerDirPath).toAbsolutePath();

            // Print the absolute path
            System.out.println("Absolute Path: " + path);

            String volume = path + ":/app/src/images";
            // Prepare the Docker command
            String[] command = {"docker", "run", "--rm",
                    "-v", volume,
                    "mind-tracker-compiler"
            };

            // Run the Docker container
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Combine stdout and stderr
            Process process = processBuilder.start();

            System.out.println("Docker command started");

            // Capture the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println("Docker output: " + line);
            }

            process.waitFor();
            System.out.println("Docker command completed");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}