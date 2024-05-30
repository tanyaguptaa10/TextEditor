package notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;

//import rsyntaxtextarea.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Notepad extends JFrame {

    private JTabbedPane tabbedPane;
    private int tabCount = 0;
    private File currentFile; // Track the current file for each tab
    private Timer  autoSaveTimer;

    Notepad() {
        // Title & image
        setTitle("Tanya_Gupta_Personal_Notepad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(1950, 1050);
           setBounds(600, 200, 700,600);
        setLayout(new BorderLayout());
         Font titleFont = new Font("Arial", Font.BOLD, 28);
         
            JPanel titleBarPanel = new JPanel(new BorderLayout());
        titleBarPanel.setPreferredSize(new Dimension(getWidth(), 60));
 
        // image
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("notepad/icons/Notepad_icon.svg.png"));
        Image icon = notepadIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Set the desired icon size
        setIconImage(icon);
       
        // Menu Bar - File
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);
        JMenu file = new JMenu("File");
        menubar.add(file);
        //FIle Menu items - New tab,new window
        //New Tab
        JMenuItem newTab = new JMenuItem("New Tab");
        newTab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));//for nemonacs 
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
        newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));//
        newWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Notepad(); // Open a new instance of Notepad when "New Tab" is clicked
            }
        });
        file.add(newWindow);
        setJMenuBar(menubar);
        //open - menubar
        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));//
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        file.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));//
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        file.add(save);

        JMenuItem autoSave = new JMenuItem("Auto-Save");
        autoSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        autoSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleAutoSave();
            }
        });
        file.add(autoSave);
        
         JMenuItem saveas = new JMenuItem("Save_As");
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        saveas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });
        file.add(saveas);
       

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(exit);
//        
        // Menu Bar - Edit
        menubar.setBackground(Color.WHITE);
        JMenu edit = new JMenu("Edit");
        menubar.add(edit);
        setJMenuBar(menubar); 

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int tabIndex = tabbedPane.getSelectedIndex();
                Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
                RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
                RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();
                textArea.copy();
            }
        });
        edit.add(copy);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int tabIndex = tabbedPane.getSelectedIndex();
                Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
                RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
                RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();
                textArea.paste();
            }
        });
        edit.add(paste);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int tabIndex = tabbedPane.getSelectedIndex();
                Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
                RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
                RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();
                textArea.cut();
            }
        });
        edit.add(cut);

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int tabIndex = tabbedPane.getSelectedIndex();
                Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
                RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
                RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();
                textArea.selectAll();
            }
        });
        edit.add(selectAll);

        //Menu Bar - View
        menubar.setBackground(Color.WHITE);
        JMenu view = new JMenu("View");
        menubar.add(view);
        setJMenuBar(menubar);


        JMenuItem zoomIn = new JMenuItem("Zoom In");
        zoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the currently selected tab's RSyntaxTextArea
                int tabIndex = tabbedPane.getSelectedIndex();
                Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
                RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
                RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();

                // Increase the font size
                Font currentFont = textArea.getFont();
                int currentSize = currentFont.getSize();
                int newSize = currentSize + 2; // Increase the font size by 2 points
                textArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), newSize));
            }
        });
        view.add(zoomIn);

        JMenuItem zoomOut = new JMenuItem("Zoom Out");
        zoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the currently selected tab's RSyntaxTextArea
                int tabIndex = tabbedPane.getSelectedIndex();
                Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
                RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
                RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();

                // Decrease the font size
                Font currentFont = textArea.getFont();
                int currentSize = currentFont.getSize();
                int newSize = currentSize - 2; // Decrease the font size by 2 points
                textArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), newSize));
            }
        });
        view.add(zoomOut);



        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    // addNEwtab function
    private void addNewTab() {
//        JTextArea textArea = new JTextArea();
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        tabCount++;
//        tabbedPane.addTab("Untitled " + tabCount, scrollPane);
        RSyntaxTextArea textArea1 = new RSyntaxTextArea();
        textArea1.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); // Set syntax highlighting for Java
        textArea1.setCodeFoldingEnabled(true); // Enable code folding
        RTextScrollPane scrollPane = new RTextScrollPane(textArea1);
        tabCount++;
        tabbedPane.addTab("Untitled " + tabCount, scrollPane);
    }

    private void openFile() {

        // private void openFile() {
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
                RSyntaxTextArea textArea = new RSyntaxTextArea(content.toString());
                textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); // Set syntax highlighting for Java
                textArea.setCodeFoldingEnabled(true); // Enable code folding
                RTextScrollPane scrollPane = new RTextScrollPane(textArea);
                tabCount++;
                tabbedPane.addTab(file.getName(), scrollPane);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void save() {
        if (currentFile != null) {
            int tabIndex = tabbedPane.getSelectedIndex();
            Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
            RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
            RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();
            String content = textArea.getText();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                writer.write(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            saveAs();
        }
    }

    private void saveAs() {
        int tabIndex = tabbedPane.getSelectedIndex();
        Component selectedComponent = tabbedPane.getComponentAt(tabIndex);
        RTextScrollPane scrollPane = (RTextScrollPane) selectedComponent;
        RSyntaxTextArea textArea = (RSyntaxTextArea) scrollPane.getViewport().getView();
        String content = textArea.getText();

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                writer.write(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    private void toggleAutoSave() {
        if (autoSaveTimer == null) {
            autoSaveTimer = new Timer(3000, new ActionListener() { // Auto-save every 30 seconds
                public void actionPerformed(ActionEvent e) {
                    save();
                }
            });
            autoSaveTimer.start();
        } else {
            autoSaveTimer.stop();
            autoSaveTimer = null;
        }
    }

    public static void main(String[] args) {
        new Notepad();
    }
}



