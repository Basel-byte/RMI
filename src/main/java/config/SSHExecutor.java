package config;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public class SSHExecutor {
    public SSHExecutor() {

    }
    public void execute(String user, String host, String command) {


        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);

            // Disable strict host key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword("2542001");
            session.connect();


//            command = "source ~/.bashrc && echo $PATH && " + command;

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            // Execute command
            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }

            // Check if command execution was successful
            int exitStatus = channel.getExitStatus();
            if (exitStatus == 0) {
                System.out.println("Command executed successfully.");
            } else {
                System.err.println("Command execution failed with exit status: " + exitStatus);
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
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
