package fr.mateoox600.mcb.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Calendar;

public class Logger extends PrintStream {

    private File file;

    public Logger(@NotNull OutputStream out, @NotNull String logFile) throws IOException {
        super(out);
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " ");
        File tempFile = new File(path.substring(0, path.length() - (path.split("/")[path.split("/").length - 1].length() + 1)) + "/data");
        if (!tempFile.exists()) tempFile.mkdirs();
        tempFile = new File(tempFile, "/logs");
        if (!tempFile.exists()) tempFile.mkdirs();
        this.file = new File(path.substring(0, path.length() - (path.split("/")[path.split("/").length - 1].length() + 1)) + "/data/logs/" + logFile);
        if (!this.file.exists()) this.file.createNewFile();
        else{
            Calendar calendar = Calendar.getInstance();
            this.file.renameTo(new File(this.file.getParent(), "logs-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR) + "  " + calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + ".log"));
            this.file = new File(path.substring(0, path.length() - (path.split("/")[path.split("/").length - 1].length() + 1)) + "/data/logs/" + logFile);
            if (!this.file.exists()) this.file.createNewFile();
        }
    }

    public void attach() {
        System.setOut(this);
        System.setErr(this);
    }

    @Override
    public void print(@Nullable String s) {
        Calendar calendar = Calendar.getInstance();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write("[" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "] " + s + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.print("[" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "] " + s + "\n");
    }

    @Override
    public void println(@Nullable String x) {
        print(x);
    }
}
