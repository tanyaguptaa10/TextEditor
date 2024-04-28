package notepad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing. filechooser.*;
import java.io.*;

//import rsyntaxtextarea.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;


public class Notepad extends JFrame {
    private JTabbedPane tabbedPane;
    private int tabCount = 0;

    Notepad(){
        // Title & image
        setTitle("Tanya_Gupta_Personal_Notepad");
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("notepad/icons/notepad.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);
       // Menu Bar - File
        JMenuBar menubar  = new JMenuBar();
        menubar.setBackground(Color.WHITE);
        JMenu file = new JMenu("File");
        menubar.add(file);
        //FIle Menu items - New tab,new window
        //New Tab
        JMenuItem newTab = new JMenuItem("New Tab");
        newTab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));//for nemonacs 
        newTab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewTab();
            }
        });
        file.add(newTab);
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        addNewTab(); // Add initial tab
        // New Window
         JMenuItem newWindow = new JMenuItem("New Window");
         newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.SHIFT_MASK));//
        newWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Notepad(); // Open a new instance of Notepad when "New Tab" is clicked
            }
        });
          file.add(newWindow);
        setJMenuBar(menubar);
        //open - menubar
        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));//
         open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
         file.add(open);
            
            JMenuItem save = new JMenuItem("Save");
            save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));//
              save.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
           file.add(save);
         
         JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));//
         file.add(print);
         
         JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));//
         file.add(exit);
        
          // Menu Bar - Edit
        menubar.setBackground(Color.WHITE);
        JMenu edit = new JMenu("Edit");
        menubar.add(edit);
        setJMenuBar(menubar);  
        
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));//
        edit.add(copy);  
        
        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));//
        edit.add(paste); 
        
        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,ActionEvent.CTRL_MASK));//
        edit.add(cut); 
        
         JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));//
        edit.add(selectAll); 
        
        //Menu Bar - View
        menubar.setBackground(Color.WHITE);
        JMenu view = new JMenu("View");
        menubar.add(view);
        setJMenuBar(menubar);

        
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setVisible(true);
    }
    // addNEwtab function
    private void addNewTab() {
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        tabCount++;
        tabbedPane.addTab("Untitled " + tabCount, scrollPane);
    }
     private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                JTextArea textArea = new JTextArea(content.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                tabCount++;
                tabbedPane.addTab(file.getName(), scrollPane);
                  }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
 private void saveFile() {
        int tabIndex = tabbedPane.getSelectedIndex();
        Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
        JScrollPane scrollPane = (JScrollPane) selectedComponent;
        JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
        String content = textArea.getText();

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
              
    

    public static void main(String[] args) {
        new Notepad();
    }
}
 