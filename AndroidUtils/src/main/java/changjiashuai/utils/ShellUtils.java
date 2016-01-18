package changjiashuai.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Email: changjiashuai@gmail.com
 *
 * request root
 *
 * Created by CJS on 16/1/16 17:17.
 */
public class ShellUtils {

    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    private ShellUtils() {
        throw new AssertionError();
    }

    /**
     * check whether has root permission
     */
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    /**
     * execute shell command
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(command, isRoot, true);
    }

    /**
     *
     * @param command
     * @param isRoot
     * @param isNeedResultMessage
     * @return
     */
    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMessage) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMessage);
    }

    /**
     *
     * @param commands
     * @param isRoot
     * @return
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    /**
     *
     * @param commands
     * @param isRoot
     * @param isNeedResultMessage
     * @return
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMessage) {
        return execCommand(commands == null ? null : commands.toArray(new String[commands.size()]), isRoot, isNeedResultMessage);
    }

    /**
     *
     * @param commands
     * @param isRoot
     * @return
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    /**
     *
     * @param commands
     * @param isRoot
     * @param isNeedResultMessage
     * @return
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMessage) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMessage = null;
        StringBuilder errorMessage = null;
        DataOutputStream dataOutputStream = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                // donnot use dataOutputStream.writeBytes(command), avoid chinese charset error.
                dataOutputStream.write(command.getBytes());
                dataOutputStream.writeBytes(COMMAND_LINE_END);
                dataOutputStream.flush();
            }
            dataOutputStream.writeBytes(COMMAND_EXIT);
            dataOutputStream.flush();
            result = process.waitFor();
            // get command result
            if (isNeedResultMessage) {
                successMessage = new StringBuilder();
                errorMessage = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMessage.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMessage.append(s);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMessage == null ? null : successMessage.toString(), errorMessage == null ? null : errorMessage.toString());
    }

    public static class CommandResult {
        // result of command
        public int result;
        // success message of command result
        public String successMessage;
        // error message of command result
        public String errorMessage;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMessage, String errorMessage) {
            this.result = result;
            this.successMessage = successMessage;
            this.errorMessage = errorMessage;
        }
    }
}
