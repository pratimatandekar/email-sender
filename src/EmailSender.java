import jakarta.mail.*;
import jakarta.mail.internet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

public class EmailSender extends JFrame {
    private JTextField toField;
    private JTextArea messageArea;
    private JButton sendButton;

    // You can hardcode these or make them fields
    private final String fromEmail = "tandekarpratima9@gmail.com";
    private final String appPassword = "wdti lbsr nmca rhrg"; // App-specific password

    public EmailSender() {
        setTitle("Email Sender");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for fields
        JPanel panel = new JPanel(new GridLayout(3, 1));

        // To Email
        toField = new JTextField();
        panel.add(labeledField("To Email:", toField));

        // Message
        messageArea = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(labeledField("Message:", scrollPane));

        // Send Button
        sendButton = new JButton("Send Email");
        sendButton.addActionListener(e -> sendEmail());
        panel.add(sendButton);

        add(panel, BorderLayout.CENTER);
    }

    private JPanel labeledField(String label, Component field) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private void sendEmail() {
        String toEmail = toField.getText();
        String messageText = messageArea.getText();

        if (toEmail.isEmpty() || messageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Email from Java Swing App");
            msg.setText(messageText);

            Transport.send(msg);
            JOptionPane.showMessageDialog(this, "Email sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send email: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmailSender ui = new EmailSender();
            ui.setVisible(true);
        });
    }
}
