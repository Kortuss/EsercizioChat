import java.awt.*;
    import java.util.ArrayList;
    import java.util.List;
    import javax.swing.*;
    import javax.swing.border.EmptyBorder;
    public class ChatLog {
        public JPanel ChatPanel = null;
        Color PanelColor = null;
        List<JLabel> Messages = null;
        Font MainFont = null;

        public ChatLog() {
            ChatPanel = new JPanel();
            PanelColor = new Color(15858900);
            ChatPanel.setBackground(PanelColor);
            ChatPanel.setLayout(new BoxLayout(ChatPanel, BoxLayout.Y_AXIS));
            ChatPanel.setBorder(new EmptyBorder(10, 20, 0 ,0)); // top, left, bottom, right
            Messages = new ArrayList<>();            
        }

        public void Display_Data(String Message){
            JLabel message = new JLabel("<html>Nickname:" + Message + "</html>");
            message.setAlignmentX(Component.LEFT_ALIGNMENT);
            MainFont = message.getFont().deriveFont(Font.PLAIN, 15f);
            message.setFont(MainFont);
            Messages.add(message);
            ChatPanel.add(message,JPanel.TOP_ALIGNMENT);
            ChatPanel.add(Box.createVerticalStrut(10));
            ChatPanel.revalidate();
            ChatPanel.repaint();
        }

        public void Render(JFrame frame){
            frame.add(ChatPanel,BorderLayout.CENTER);
        }
        
    }
