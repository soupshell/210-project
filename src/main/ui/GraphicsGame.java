package ui;

import model.Event;
import model.EventLog;
import model.Repertoire;
import model.Song;
//import model.NoteBlock;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//graphical interpretation of the rhythm game editor.
//SOURCE: SimpleDrawingPlayer CPSC 210 course content.
//          OracleDocs JButton tutorial, JPanel tutorial
public class GraphicsGame extends JFrame implements ActionListener, WindowListener {
    private static final String JSON_STORE = "./data/rhythmgame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Repertoire selections;

    private JPanel menuOne;
    private JPanel menuTwo;
    private JPanel menuHelp;
    private JPanel selectList;
    private JPanel addMenu;
    private JPanel songMenu;

    private JButton saveButton;

    private String editable;

    //MODIFIES: this
    //EFFECTS: instantiate selections and add the two tutorial songs to it. run the opening menu.
    public GraphicsGame() throws FileNotFoundException {
        selections = new Repertoire();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        editable = "";

        EventLog.getInstance().clear();

        saveButton = new JButton("save to file");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        addWindowListener(this);
        initializeGraphics();
        run();
    }

    //MODIFIES: this
    //EFFECTS: constructs and displays the starter menu
    private void run() {
        menuOne = new JPanel(new GridBagLayout());

        menuOne.add(registerButton("frog","frog"));

        menuOne.add(registerButton("start","start"));

        menuOne.add(saveButton);

        menuOne.add(registerButton("load from file", "load"));

        menuOne.setVisible(true);
        add(menuOne);
    }

    //MODIFIES: this
    //EFFECTS: constructs and displays the second menu
    private void menuTwoAccess() {
        menuTwo = new JPanel(new GridBagLayout());

        menuTwo.add(registerButton("select a song","select"));
        menuTwo.add(registerButton("help", "help"));
        menuTwo.add(registerButton("back","back start"));

        menuTwo.setVisible(true);
        add(menuTwo);
    }

    //MODIFIES: this
    //EFFECTS: constructs and displays the selections menu
    private void selectListShow() {
        editable = "";

        selectList = new JPanel(new GridBagLayout());

        selectList.add(new JTextField(selections.getListName()));

        selectList.add(registerButton("back", "back select"));
        selectList.add(registerButton("add song", "add"));
        selectList.add(saveButton);

        generateSongListButtons();

        selectList.setVisible(true);
        add(selectList);
    }

    //MODIFIES: this
    //EFFECTS: constructs buttons for the repertoire display menu (select menu)
    private void generateSongListButtons() {
        int i = 0;
        while (i < selections.getSize()) {
            try {
                Song next = selections.getNthSong(i);
                selectList.add(registerButton(next.getSongName(), "hey" + Integer.toString(i)));
                selectList.add(registerButton("X",Integer.toString(i)));
                i++;
            } catch (Exception e) {
                break;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: constructs and displays the add menu
    private void addMenuShow() {
        GridBagLayout hey = new GridBagLayout();
        GridBagConstraints here = new GridBagConstraints();

        addMenu = new JPanel(hey);

        generateAlphabetButton();

        addMenu.add(registerButton("back","back add"));
        addMenu.add(registerButton("enter","enter"));

        JTextField e = new JTextField(editable);

        here.gridx = 10;
        here.gridy = 40;
        hey.setConstraints(e, here);
        addMenu.add(e);

        addMenu.setVisible(true);
        add(addMenu);
    }

    //MODIFIES: this
    //EFFECTS: generates keyboard buttons
    private void generateAlphabetButton() {
        int i = 1;
        while (i <= 6) {
            addMenu.add(registerButton(toLetteraf(i), Integer.toString(i)));
            i++;
        }

        while (i >= 7 && i <= 12) {
            addMenu.add(registerButton(toLettergl(i), Integer.toString(i)));
            i++;
        }

        while (i >= 13 && i <= 18) {
            addMenu.add(registerButton(toLettermr(i), Integer.toString(i)));
            i++;
        }

        while (i >= 19 && i <= 24) {
            addMenu.add(registerButton(toLettersx(i), Integer.toString(i)));
            i++;
        }

        while (i >= 25 && i <= 26) {
            addMenu.add(registerButton(toLettersx(i), Integer.toString(i)));
            i++;
        }
    }

    //MODIFIES: this
    //EFFECTS: constructs and displays a song menu
    public void notePageSetup(Song s) {
        GridBagLayout hey = new GridBagLayout();
        GridBagConstraints here = new GridBagConstraints();

        songMenu = new JPanel(hey);

        //generateAlphabetButton();
        // note editing sidebar - c1 c2 c3 c4, t??, h?? for length
        // for each note, column button, time button, hold length,

        songMenu.add(registerButton("back","back song"));
        //songMenu.add(registerButton("enter","enter note"));

        //JTextField e = new JTextField(editable);

        //here.gridx = 10;
        //here.gridy = 40;
        //hey.setConstraints(, here);
        //songMenu.add(e);

        songMenu.setVisible(true);
        add(songMenu);
    }

    //MODIFIES: this
    //EFFECTS: performs general button actions
    public void actionPerformed(ActionEvent a) {
        if ("save".equals(a.getActionCommand())) {
            saveRep();
        } else {
            actionPerformedMenuOne(a.getActionCommand());
            actionPerformedMenuTwo(a.getActionCommand());
            actionPerformedMenuHelp(a.getActionCommand());
            actionPerformedMenuSelect(a.getActionCommand());
            actionPerformedMenuAdd(a.getActionCommand());
            //actionPerformedMenuSong(a.getActionCommand());
        }
    }

    //MODIFIES: this
    //EFFECTS: performs button actions on the starter menu
    private void actionPerformedMenuOne(String s) {
        if (s.equals("start")) {
            menuOne.setVisible(false);
            menuTwoAccess();
        } else if (s.equals("load")) { //not start must be load
            loadRep();
        } else if (s.equals("frog")) {
            menuOne.setVisible(false);
            frog();
        }
    }

    //MODIFIES: this
    //EFFECTS: performs button actions on the second menu
    private void actionPerformedMenuTwo(String s) {
        if (s.equals("select")) {
            menuTwo.setVisible(false);
            selectListShow();
        } else if (s.equals("help")) {
            menuTwo.setVisible(false);
            help();
        } else if (s.equals("back start")) {
            menuTwo.setVisible(false);
            //menuOne.setVisible(true);
            run();
        }
    }

    //MODIFIES: this
    //EFFECTS: performs button actions on the select menu
    private void actionPerformedMenuSelect(String s) {
        if (s.equals("back select")) {
            selectList.setVisible(false);
            menuTwo.setVisible(true);
        } else if (s.equals("add")) {
            selectList.setVisible(false);
            addMenuShow();
        //} else if (s.substring(0, 3).equals("hey")) {
//            try {
//                int i = Integer.parseInt(s.substring(3));
//                //selections.getNthSong(i);
//                selectList.setVisible(false);
//                notePageSetup(selections.getNthSong(i));
//            } catch (Exception e) {
//                //do nothing
//            }
        } else {
            try {
                int i = Integer.parseInt(s);
                selections.deleteNthSong(i);
                selectList.setVisible(false);
                selectListShow();
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: performs button actions on the add menu
    private void actionPerformedMenuAdd(String s) {
        if (s.equals("enter")) {
            selections.addSong(new Song(editable));
            editable = "";
            addMenu.setVisible(false);
            selectListShow();
        } else if (s.equals("back add")) {
            addMenu.setVisible(false);
            selectListShow();
        } else {
            try {
                int i = Integer.parseInt(s);
                if (i <= 26 && i >= 1) {
                    editable = editable + toLetteraf(i) + toLettergl(i) + toLettermr(i) + toLettersx(i) + toLetteryz(i);
                }
                addMenu.setVisible(false);
                addMenuShow();
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: performs button actions on the help menu
    private void actionPerformedMenuHelp(String s) {
        if (s.equals("back two")) {
            menuHelp.setVisible(false);
            selectList.setVisible(true);
        }
    }

    private void actionPerformedMenuSong(String s) {
        if (s.equals("back song")) {
            songMenu.setVisible(false);
            selectListShow();
        }
    }

    //EFFECTS: displays help instructions
    private void help() {
        menuHelp = new JPanel(new GridBagLayout());

        menuHelp.add(registerButton("back","back two"));

        menuHelp.setVisible(true);
        add(menuHelp);
    }

    //EFFECTS: make a button with title as its display name and commandName as action command
    private JButton registerButton(String title, String commandName) {
        JButton jbutton = new JButton(title);
        jbutton.setBackground(Color.BLACK);
        jbutton.setForeground(Color.WHITE);
        jbutton.setActionCommand(commandName);
        jbutton.addActionListener(this);

        return jbutton;
    }

    //
    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout()); //jframe
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //EFFECTS: loads repertoire from file
    // Source: JsonSerializationDemo, CPSC 210 course content
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadRep() {
        try {
            selections = jsonReader.read();
            JTextField loadsu = new JTextField("Loaded " + selections.getListName() + " from " + JSON_STORE);
            menuOne.add(loadsu);
            menuOne.setVisible(false);
            menuOne.setVisible(true);
            //System.out.println("Loaded " + selections.getListName() + " from " + JSON_STORE);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE);
            JTextField loadfa = new JTextField("Unable to read from file: " + JSON_STORE);
            menuOne.add(loadfa);
            menuOne.setVisible(false);
            menuOne.setVisible(true);
        }
    }

    //EFFECTS: saves the repertoire to file
    // Source: JsonSerializationDemo, CPSC 210 course content
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void saveRep() {
        try {
            jsonWriter.open();
            jsonWriter.write(selections);
            jsonWriter.close();
            JTextField savesu = new JTextField("Saved " + selections.getListName() + " to " + JSON_STORE);
            menuOne.add(savesu);
            menuOne.setVisible(false);
            menuOne.setVisible(true);
            //System.out.println("Saved " + selections.getListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE);
            JTextField savefa = new JTextField("Unable to write to file: " + JSON_STORE);
            menuOne.add(savefa);
            menuOne.setVisible(false);
            menuOne.setVisible(true);
        }
    }

    //REQUIRES: i is between 1 and 6
    //EFFECTS: converts the number to its alphabet component
    private String toLetteraf(int i) {
        String result = "";
        switch (i) {
            case 1:
                result = "a";
                break;
            case 2:
                result = "b";
                break;
            case 3:
                result = "c";
                break;
            case 4:
                result = "d";
                break;
            case 5:
                result = "e";
                break;
            case 6:
                result = "f";
                break;
        }
        return result;
    }

    //REQUIRES: i is between 7 and 12
    //EFFECTS: converts the number to its alphabet component
    private String toLettergl(int i) {
        String result = "";
        switch (i) {
            case 7:
                result = "g";
                break;
            case 8:
                result = "h";
                break;
            case 9:
                result = "i";
                break;
            case 10:
                result = "j";
                break;
            case 11:
                result = "k";
                break;
            case 12:
                result = "l";
                break;
        }
        return result;
    }

    //REQUIRES: i is between 13 and 18
    //EFFECTS: converts the number to its alphabet component
    private String toLettermr(int i) {
        String result = "";
        switch (i) {
            case 13:
                result = "m";
                break;
            case 14:
                result = "n";
                break;
            case 15:
                result = "o";
                break;
            case 16:
                result = "p";
                break;
            case 17:
                result = "q";
                break;
            case 18:
                result = "r";
                break;
        }
        return result;
    }

    //REQUIRES: i is between 19 and 24
    //EFFECTS: converts the number to its alphabet component
    private String toLettersx(int i) {
        String result = "";
        switch (i) {
            case 19:
                result = "s";
                break;
            case 20:
                result = "t";
                break;
            case 21:
                result = "u";
                break;
            case 22:
                result = "v";
                break;
            case 23:
                result = "w";
                break;
            case 24:
                result = "x";
                break;
        }
        return result;
    }

    //REQUIRES: i = 25 or 26
    //EFFECTS: converts the number to its alphabet component
    private String toLetteryz(int i) {
        String result = "";
        switch (i) {
            case 25:
                result = "y";
                break;
            case 26:
                result = "z";
                break;
        }
        return result;
    }

    //EFFECTS: displays the frog image.
    private void frog() {
        JButton a = new JButton(new ImageIcon("data/frogpicture.jpg"));
        this.add(a);
    }

    public void windowOpened(WindowEvent e) {
        //
    }

    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.
     * @param e the event to be processed
     */
    public void windowClosing(WindowEvent e) {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }

        EventLog.getInstance().clear();
    }

    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     * @param e the event to be processed
     */
    public void windowClosed(WindowEvent e) {

        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }

        EventLog.getInstance().clear();
    }

    public void windowIconified(WindowEvent e) {
        //
    }

    public void windowDeiconified(WindowEvent e) {
        //
    }

    public void windowActivated(WindowEvent e) {
        //
    }

    public void windowDeactivated(WindowEvent e) {
        //
    }
}
