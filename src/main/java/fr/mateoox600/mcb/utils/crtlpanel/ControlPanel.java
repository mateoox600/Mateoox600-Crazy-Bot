package fr.mateoox600.mcb.utils.crtlpanel;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;

public class ControlPanel extends JFrame {

    private final JTextPane console;

    public ControlPanel(String title) {

        // JFrame setup
        setTitle(title);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 500);

        // creating JPanel
        JPanel panel = new JPanel(new BorderLayout());

        // setting JPanel border
        panel.setBorder(BorderFactory.createTitledBorder("Console"));

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
                System.out.println("-no_prefix> " + input.getText());
                try {
                    System.out.println(parseCommand(input.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                input.setText("");
            }
        });

        // adding component
        add(panel);
        panel.add(consolePane, BorderLayout.CENTER);
        panel.add(input, BorderLayout.PAGE_END);

        // make the frame visible
        setVisible(true);

        /*for (int i = 0; i < 50; i++) {
            print("Iteration" + i + "\n");
        }*/

    }

    public void print(String s) {
        try {
            console.getDocument().insertString(console.getDocument().getLength(), s, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public String parseCommand(String command) throws IOException {
        String[] args = command.split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (command.startsWith("stop")) {
            MCB.stop();
        }else if (command.startsWith("sendmessage")){
            if (args.length > 1){
                TextChannel channel = MCB.jda.getTextChannelById(args[0]);
                if (channel == null) return "[ERROR] Command 'sendmessage' require a valide long channel id";
                channel.sendMessage("```" + String.join(" ", Arrays.copyOfRange(args, 1, args.length)) + "```").queue();
                return "[INFO] Send Message in " + channel.getName() + " of " + channel.getGuild().getName();
            }else{
                return "[ERROR] Command 'sendmessage' require channel id and message to send";
            }
        }else if (command.startsWith("tell")){
            if (args.length > 1){
                User user = MCB.jda.getUserById(args[0]);
                if (user == null) return "[ERROR] Command 'tell' require a valide long user id";
                String[] finalArgs = args;
                user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("```" + String.join(" ", Arrays.copyOfRange(finalArgs, 1, finalArgs.length)) + "```").queue());
                return "[INFO] Send Message to " + user.getName();
            }else{
                return "[ERROR] Command 'tell' require user id and message to send";
            }
        }
        return "[ERROR] Command doesn't exist";
    }

}
