package fr.mateoox600.mcb.utils;

import fr.mateoox600.mcb.MCB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Calendar;

public class Logger extends PrintStream {

    private File file;

    public Logger(@NotNull OutputStream out, @NotNull String logFile) throws IOException {
        super(out);
        File tempFile = new File(MCB.config.getDataFolder(), "/logs");
        if (!tempFile.exists()) tempFile.mkdirs();

        this.file = new File(tempFile, "/" + logFile);
        if (!file.exists()) file.createNewFile();

        else{
            Calendar calendar = Calendar.getInstance();
            this.file.renameTo(new File(this.file.getParent(), "logs-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR) + "  " + calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + ".log"));
            this.file = new File(MCB.config.getDataFolder(), "/logs/" + logFile);
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
        String message = s.startsWith("-no_prefix") ? s.substring(10) + "\n" : "[" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "] " + s + "\n";
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(message);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MCB.controlPanel.print(message);
        super.print(message);
    }

    @Override
    public void println(@Nullable String x) {
        print(x);
    }
}
