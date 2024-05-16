package config;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public class SSHExecutor {
    public SSHExecutor() {
    }


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
            boolean commandOutputStarted = false; // Flag to indicate if the command output has started
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                String data = new String(buffer, 0, bytesRead);
                if(commandOutputStarted) {
                    System.out.print(data);
                }
                if (!commandOutputStarted && data.contains("$")) {
                    commandOutputStarted = true;
                    latch.countDown();
                }

            }

            // Disconnect the channel and session
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
