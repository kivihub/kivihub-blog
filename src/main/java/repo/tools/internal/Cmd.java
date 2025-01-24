package repo.tools.internal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cmd {
    public static String Run(String cmd) {
        String output = "";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd}); // ignore_security_alert
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
                System.out.println(line);
            }

            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }
}
