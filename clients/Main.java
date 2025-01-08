package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.packing.PackingController;
import clients.packing.PackingModel;
import clients.packing.PackingView;
import clients.productlist.ProductsListModel;
import clients.productlist.ProductsListView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Main Menu Client to pick and run a specific client (Customer, Cashier, Packing, BackDoor).
 */
public class Main {

    private final MiddleFactory mlf; // Factory object for shared logic

    /**
     * Constructor - Initializes the MiddleFactory
     */
    public Main() {
        this.mlf = new LocalMiddleFactory(); // Shared factory instance
    }

    /**
     * Entry point to display the menu.
     */
    public static void main(String[] args) {
        new Main().showMenu();
    }

    /**
     * Display the main GUI menu to choose a client.
     */
    public void showMenu() {
        // Create menu frame
        JFrame menuFrame = new JFrame("Client Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(450, 350);
        menuFrame.setLayout(new BorderLayout());
        menuFrame.setLocationRelativeTo(null); // Center the frame on the screen
        menuFrame.getContentPane().setBackground(new Color(0x2B, 0x2B, 0x2C, 0xFF));

        // Create header
        JLabel headerLabel = new JLabel("Choose a Client to Start", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        headerLabel.setForeground(new Color(0xFA, 0xFA, 0xFA, 0xFF)); // Set the font color to white
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 rows (5 clients + 1 exit button)
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.setBackground(new Color(0x2B, 0x2B, 0x2C, 0xFF));
        // Add buttons for each client
        JButton customerButton = new JButton("Start Customer Client");
        JButton cashierButton = new JButton("Start Cashier Client");
        JButton packingButton = new JButton("Start Packing Client");
        JButton backDoorButton = new JButton("Start BackDoor Client");
        JButton productsListButton = new JButton("Start Products List Client");
        JButton exitButton = new JButton("Exit Menu");
        
        // Add action listeners for each button
        customerButton.addActionListener(_ -> startCustomerClient());
        cashierButton.addActionListener(_ -> startCashierClient());
        packingButton.addActionListener(_ -> startPackingClient());
        backDoorButton.addActionListener(_ -> startBackDoorClient());
        productsListButton.addActionListener(_ -> startProductsListClient());
        exitButton.addActionListener(_ -> exitApplication(menuFrame));
        
        // Set buttons' background, text color, border color, and font style
        Color buttonBackgroundColor = new Color(0x38, 0x38, 0x38, 0xFF);
        Font buttonFont = new Font("Poppins", Font.BOLD, 14);
        Color buttonTextColor = new Color(0xFA, 0xFA, 0xFA, 0xFF); // Match header label text color
        Color buttonBorderColor = new Color(122, 61, 169, 0xFF); // New border color
        setupButtonProperties(customerButton, buttonBackgroundColor, buttonTextColor, buttonBorderColor, buttonFont);
        setupButtonProperties(cashierButton, buttonBackgroundColor, buttonTextColor, buttonBorderColor, buttonFont);
        setupButtonProperties(packingButton, buttonBackgroundColor, buttonTextColor, buttonBorderColor, buttonFont);
        setupButtonProperties(backDoorButton, buttonBackgroundColor, buttonTextColor, buttonBorderColor, buttonFont);
        setupButtonProperties(productsListButton, buttonBackgroundColor, buttonTextColor, buttonBorderColor, buttonFont);
        setupButtonProperties(exitButton, buttonBackgroundColor, buttonTextColor, buttonBorderColor, buttonFont);
        
        // Add buttons to panel
        buttonPanel.add(customerButton);
        buttonPanel.add(cashierButton);
        buttonPanel.add(packingButton);
        buttonPanel.add(backDoorButton);
        buttonPanel.add(productsListButton);
        buttonPanel.add(exitButton); // Exit button at the bottom

        // Add components to frame
        menuFrame.add(headerLabel, BorderLayout.NORTH);
        menuFrame.add(buttonPanel, BorderLayout.CENTER);

        menuFrame.setResizable(false); // Disable resizing and maximizing of the window
        // Show the frame
        menuFrame.setVisible(true);
    }
    
    /**
     * Sets the standard properties for a JButton.
     * 
     * @param button The JButton to configure.
     * @param background The background color of the button.
     * @param textColor The color of the text on the button.
     * @param borderColor The color of the button's border.
     * @param font The font style for the button text.
     */
    private void setupButtonProperties(JButton button, Color background, Color textColor, Color borderColor, Font font) {
        button.setBackground(background);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2, true)); // Rounded border
        button.setFont(font);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40)); // Uniform size
    }

    /**
     * Start the Customer Client.
     */
    private void startCustomerClient() {
        JFrame window = new JFrame();
        window.setTitle("Customer Client MVC");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        Dimension pos = PosOnScrn.getPos();
    
        // Apply styling to match the menu
        window.setSize(410, 310);
        window.getContentPane().setBackground(new Color(0x2B, 0x2B, 0x2C, 0xFF));
        window.setLocationRelativeTo(null); // Center the frame on the screen
        window.setResizable(false);
    
        CustomerModel model = new CustomerModel(mlf);
        CustomerView view = new CustomerView(window, mlf, pos.width, pos.height);
        CustomerController controller = new CustomerController(model, view);
        view.setController(controller);
    
        model.addObserver(view);
    
        window.setVisible(true);
    }

    /**
     * Start the Cashier Client.
     */
    private void startCashierClient() {
        JFrame window = new JFrame();
        window.setTitle("Cashier Client MVC");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        window.setSize(410, 310);
        window.getContentPane().setBackground(new Color(0x2B, 0x2B, 0x2C, 0xFF));
        window.setLocationRelativeTo(null); // Center the frame on the screen
        window.setResizable(false);

        CashierModel model = new CashierModel(mlf);
        CashierView view = new CashierView(window, mlf, pos.width, pos.height);
        CashierController controller = new CashierController(model, view);
        view.setController(controller);

        model.addObserver(view);
        window.setVisible(true);
        model.askForUpdate();
    }

    /**
     * Start the Packing Client.
     */
    private void startPackingClient() {
        JFrame window = new JFrame();
        window.setTitle("Packing Client MVC");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        window.setSize(410, 310);
        window.getContentPane().setBackground(new Color(0x2B, 0x2B, 0x2C, 0xFF));
        window.setLocationRelativeTo(null); // Center the frame on the screen
        window.setResizable(false);

        PackingModel model = new PackingModel(mlf);
        PackingView view = new PackingView(window, mlf, pos.width, pos.height);
        PackingController controller = new PackingController(model, view);
        view.setController(controller);

        model.addObserver(view);
        window.setVisible(true);
    }
    
    /**
     * Start the Products List Client.
     */
    private void startProductsListClient() {
        JFrame window = new JFrame();
        window.setTitle("Products List Client MVC");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        window.setSize(510, 310);
        window.getContentPane().setBackground(new Color(0x2B, 0x2B, 0x2C, 0xFF));
        window.setLocationRelativeTo(null); // Center the frame on the screen
        window.setResizable(false);

        ProductsListModel model = new ProductsListModel(mlf);
        ProductsListView view = new ProductsListView(window, model , pos.width, pos.height);


        model.addPropertyChangeListener(view);
        model.loadProducts();
        window.setVisible(true);
    }

    /**
     * Start the BackDoor Client.
     */
    private void startBackDoorClient() {
        JFrame window = new JFrame();
        window.setTitle("BackDoor Client MVC");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        window.setSize(410, 310);
        window.getContentPane().setBackground(new Color(0x2B, 0x2B, 0x2C, 0xFF));
        window.setLocationRelativeTo(null); // Center the frame on the screen
        window.setResizable(false);

        BackDoorModel model = new BackDoorModel(mlf);
        BackDoorView view = new BackDoorView(window, mlf, pos.width, pos.height);
        BackDoorController controller = new BackDoorController(model, view);
        view.setController(controller);

        model.addObserver(view);
        window.setVisible(true);
    }

    /**
     * Exit the application when the "Exit" button is clicked.
     *
     * @param menuFrame The main menu frame to close.
     */
    private void exitApplication(JFrame menuFrame) {
        // Set dialog styling
        UIManager.put("OptionPane.background", new Color(0x2B, 0x2B, 0x2C, 0xFF));
        UIManager.put("Panel.background", new Color(0x2B, 0x2B, 0x2C, 0xFF));
        UIManager.put("OptionPane.messageForeground", new Color(0xFA, 0xFA, 0xFA, 0xFF));
        UIManager.put("OptionPane.font", new Font("Poppins", Font.BOLD, 14));
        UIManager.put("Button.background", new Color(0x38, 0x38, 0x38, 0xFF));
        UIManager.put("Button.foreground", new Color(0xFA, 0xFA, 0xFA, 0xFF));
        UIManager.put("Button.font", new Font("Poppins", Font.BOLD, 11));
        UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(122, 61, 169, 0xFF)));
        
        int confirm = JOptionPane.showConfirmDialog(
                menuFrame,
                "Are you sure you want to exit the application?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
        );
    
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0); // Terminate the program
        }
    }
}