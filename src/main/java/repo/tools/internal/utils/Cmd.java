package repo.tools.internal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cmd {
    private final static Logger logger = LoggerFactory.getLogger(Cmd.class);

    public static String RunSilent(String cmd) {
        return Run(cmd, true);
    }

    public static String Run(String cmd, boolean silent) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd}); // ignore_security_alert
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                if (!silent) {
                    logger.info(line);
                }
            }

            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return output.toString();
    }

}
