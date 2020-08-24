package fr.mateoox600.mcb.utils.controlpanel;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.reminders.utils.Reminder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ControlPanel extends JFrame {

    private final JTextPane console;
    private final JTextPane infoLabel;
    private final JTextPane remindersPanel;

    public ControlPanel(String title) {

        // JFrame setup
        setTitle(title);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 500);

        // creating Main JPanel
        JPanel panel = new JPanel(new GridLayout(1, 2));

        // creating console JPanel
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setBorder(BorderFactory.createTitledBorder("Console"));

        // creating info JPanel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));

        // creating info text
        infoLabel = new JTextPane();
        infoLabel.setFont(new Font(null, Font.PLAIN, 16));
        infoLabel.setEditable(false);

        // create reminders JTextPane
        remindersPanel = new JTextPane();
        remindersPanel.setFont(new Font(null, Font.PLAIN, 16));
        remindersPanel.setEditable(false);
        JScrollPane remindersScrollPane = new JScrollPane(remindersPanel);
        remindersScrollPane.setBorder(BorderFactory.createTitledBorder("Reminders"));

        // creating console
        console = new JTextPane();
        console.setFont(new Font(null, Font.PLAIN, 18));
        console.setEditable(false);
        JScrollPane consolePane = new JScrollPane(console);

        // creating console input
        JTextField input = new JTextField();
        input.setFont(new Font(null, Font.PLAIN, 20));
        input.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread thread = new Thread(() -> {
                    System.out.println("-no_prefix> " + input.getText());
                    parseCommand(input.getText());
                    input.setText("");
                });
                thread.start();
            }
        });

        // adding component
        add(panel);
        panel.add(consolePanel);
        consolePanel.add(consolePane, BorderLayout.CENTER);
        consolePanel.add(input, BorderLayout.PAGE_END);
        panel.add(infoPanel);
        infoPanel.add(infoLabel, BorderLayout.PAGE_START);
        infoPanel.add(remindersScrollPane, BorderLayout.CENTER);

        // make the frame visible
        setVisible(true);

        /*for (int i = 0; i < 50; i++) {
            print("Iteration" + i + "\n");
        }*/

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void print(String s) {
        try {
            console.getDocument().insertString(console.getDocument().getLength(), s, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void parseCommand(String command) {
        Thread thread = new Thread(() -> {
            String[] args = command.split("\\s+");
            args = Arrays.copyOfRange(args, 1, args.length);
            if (command.startsWith("stop")) {
                try {
                    MCB.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (command.startsWith("sendmessage")) {
                if (args.length > 1) {
                    TextChannel channel = MCB.jda.getTextChannelById(args[0]);
                    if (channel == null)
                        System.out.println("[ERROR] Command 'sendmessage' require a valid long channel id");
                    assert channel != null;
                    channel.sendMessage("```" + String.join(" ", Arrays.copyOfRange(args, 1, args.length)) + "```").queue();
                    System.out.println("[INFO] Send Message in " + channel.getName() + " of " + channel.getGuild().getName());
                } else {
                    System.out.println("[ERROR] Command 'sendmessage' require channel id and message to send");
                }
            } else if (command.startsWith("tell")) {
                if (args.length > 1) {
                    User user = MCB.jda.getUserById(args[0]);
                    if (user == null) System.out.println("[ERROR] Command 'tell' require a valid long user id");
                    String[] finalArgs = args;
                    assert user != null;
                    user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("```" + String.join(" ", Arrays.copyOfRange(finalArgs, 1, finalArgs.length)) + "```").queue());
                    System.out.println("[INFO] Send Message to " + user.getName());
                } else {
                    System.out.println("[ERROR] Command 'tell' require user id and message to send");
                }
            }
            System.out.println("[ERROR] Command doesn't exist");
        });
        thread.start();
    }

    public void startThread() {
        Timer InfoPanel = new Timer();
        InfoPanel.schedule(new TimerTask() {
            @Override
            public void run() {
                Thread thread = new Thread(() -> infoLabel.setText("Memory usage: " + (round((double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 / 1024, 2)) + "/" + (round((double) Runtime.getRuntime().totalMemory() / 1024 / 1024 / 1024, 2)) + "Go\nReminders number: " + MCB.remindersManager.reminders.size()));
                thread.start();
            }
        }, 1000, 1000);

        Timer remindersPanelTimer = new Timer();
        remindersPanelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Thread thread = new Thread(() -> {
                    remindersPanel.setText("");
                    for (Reminder reminder : MCB.remindersManager.reminders) {
                        try {
                            remindersPanel.getDocument().insertString(remindersPanel.getDocument().getLength(), "Text: " + reminder.getText() + " User: " + reminder.getUser().getName() + " Channel: " + reminder.getChannel().getName() + " of " + reminder.getChannel().getGuild().getName() + "\n", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }, 1000 * 30, 1000 * 30);
    }
}
