package config;

import com.jcraft.jsch.*;
import org.apache.log4j.Logger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public class SSHExecutor {
    private static final Logger logger = Logger.getLogger(SSHExecutor.class.getName());
    public void executeSingleCommand(String user, String host, String password, String command, CountDownLatch latch) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);

            // Disable strict host key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);
            session.connect();

            // Open an interactive shell channel
            ChannelShell channel = (ChannelShell) session.openChannel("shell");

            // Set input/output streams for interaction
            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();

            channel.connect();

            // Send command to the shell
            out.write((command + "\n").getBytes());
            out.flush();
            // Read output from the shell
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                String data = new String(buffer, 0, bytesRead);
                if (data.contains("Graph initialized successfully!"))
                    latch.countDown();
                System.out.print(data);
            }

            // Disconnect the channel and session
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
