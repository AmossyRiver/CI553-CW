package clients.productlist;

import catalogue.Product;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Implements the View for the Products List Client.
 */
public class ProductsListView implements PropertyChangeListener {
    private static final int W = 510; // Width of the window
    private static final int H = 310; // Height of the window

    private final JLabel      pageTitle  = new JLabel();
    private final JTextArea displayArea = new JTextArea();
    private final ProductsListModel model;

    /**
     * Constructor for the Products List View.
     *
     * @param rpc   Container for the UI
     * @param model The model providing the product data
     * @param x     Position X for the window
     * @param y     Position Y for the window
     */
    public ProductsListView(RootPaneContainer rpc, ProductsListModel model, int x, int y) {
        this.model = model;
        model.addPropertyChangeListener(this); // Attach property change listener to the model

        Container cp = rpc.getContentPane();
        cp.setLayout(new BorderLayout()); // Main layout for the content pane
        Font f = new Font("Poppins",Font.PLAIN,12);  // Font f is
        Color w = new Color(0xFA, 0xFA, 0xFA, 0xFF);
        Color b = new Color(38, 38, 38, 0xFF);
        Color bd = new Color(122, 61, 169, 0xFF);

        JScrollPane scrollPane = new JScrollPane(displayArea); // Scrollable area
        scrollPane.setBackground(b);
        scrollPane.setForeground(w);
        scrollPane.setBounds( 10, 30, 480, 240 );
        scrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, bd));
        displayArea.setBackground(b);
        displayArea.setForeground(w);
        displayArea.setBorder(null);
        displayArea.setLineWrap(true); // Wrap lines
        displayArea.setEditable(false); // Prevent editing
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Use a monospaced font

        // Add components to the content pane
        pageTitle.setBounds( 110, 0 , 270, 20 );
        pageTitle.setFont(f);
        pageTitle.setForeground(w);
        pageTitle.setText("List of Products");
        cp.add(pageTitle, BorderLayout.NORTH); // Page title
        cp.add(scrollPane, BorderLayout.CENTER); // Product list display

        JFrame rootWindow = (JFrame) rpc;
        rootWindow.setSize(W, H);
        rootWindow.setLocation(x, y);
    }

    /**
     * Update the product list display whenever the model is updated.
     *
     * @param evt The property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("productList".equals(evt.getPropertyName())) {
            List<Product> products = model.getProductList(); // Fetch product list from model
            StringBuilder productDisplay = new StringBuilder();

            productDisplay.append(String.format("%-10s %-25s %-10s %-10s\n", "ProductNo", "Description", "Price", "Quantity"));
            productDisplay.append("----------------------------------------------------------\n");

            for (Product product : products) {
                productDisplay.append(String.format("%-10s %-25s $%-9.2f %-10d\n",
                        product.getProductNum(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity()));
            }

            displayArea.setText(productDisplay.toString()); // Update the display area text
        }
    }
}