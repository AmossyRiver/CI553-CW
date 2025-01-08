package clients.backDoor;

import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
/**
 * Implements the Customer view.
 */

public class BackDoorView implements Observer
{
  private static final String RESTOCK  = "Add";
  private static final String CLEAR    = "Clear";
  private static final String QUERY    = "Query";
  private static final String EDIT_PRICE = "Edit Price";
 
  private static final int H = 310;       // Height of window pixels
  private static final int W = 410;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  theInputNo = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtClear = new JButton( CLEAR );
  private final JButton     theBtRStock = new JButton( RESTOCK );
  private final JButton     theBtQuery = new JButton( QUERY );
  private final JButton     theBtEditPrice = new JButton( EDIT_PRICE );
  
  private StockReadWriter theStock     = null;
  private BackDoorController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public BackDoorView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock = mf.makeStockReadWriter();          // Database access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );
    
    Font f = new Font("Poppins",Font.PLAIN,12);
    Color w = new Color(0xFA, 0xFA, 0xFA, 0xFF);
    Color b = new Color(56, 56, 56, 0xFF);
    Color bd = new Color(122, 61, 169, 0xFF);// Font f is

    pageTitle.setBounds( 110, 0 , 270, 20 );
    pageTitle.setFont( f );
    pageTitle.setForeground(w);
    pageTitle.setText( "Staff check and manage stock" );                        
    cp.add( pageTitle );
    
    theBtQuery.setBounds( 16, 25, 80, 40 );
    theBtQuery.setBackground(b);                   // Background color
    theBtQuery.setForeground(w);                   // Text color
    theBtQuery.setFont( f );                       // Font
    theBtQuery.setBorder(BorderFactory.createLineBorder(bd, 2)); // Border
    theBtQuery.addActionListener(                   // Call back code
      e -> cont.doQuery( theInput.getText() ) );
    cp.add( theBtQuery );                           //  Add to canvas

    theBtRStock.setBounds( 16, 25+60, 80, 40 );   // Check Button
    theBtRStock.setBackground(b);                   // Background color
    theBtRStock.setForeground(w);                   // Text color
    theBtRStock.setFont( f );                       // Font
    theBtRStock.setBorder(BorderFactory.createLineBorder(bd, 2)); // Border
    theBtRStock.addActionListener(                  // Call back code
      e -> cont.doRStock( theInput.getText(),
                          theInputNo.getText() ) );
    cp.add( theBtRStock );                          //  Add to canvas

    theBtClear.setBounds( 16, 25+120, 80, 40 );    // Clear button
    theBtClear.setBackground(b);                    // Background color
    theBtClear.setForeground(w);                    // Text color
    theBtClear.setFont( f );                        // Font
    theBtClear.setBorder(BorderFactory.createLineBorder(bd, 2)); // Border
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas
    
    theBtEditPrice.setBounds( 16, 25+180, 80, 40 );   // Edit Price button
    theBtEditPrice.setBackground(b);                    // Background color
    theBtEditPrice.setForeground(w);                    // Text color
    theBtEditPrice.setFont( f );                        // Font
    theBtEditPrice.setBorder(BorderFactory.createLineBorder(bd, 2)); // Border
    theBtEditPrice.addActionListener(                 // Call back code
      e -> cont.doEditPrice(theInput.getText(),
                            theInputNo.getText()) ); 
    cp.add( theBtEditPrice );                         //  Add to canvas

 
    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setFont( f );                         // Font
    theAction.setForeground(w);                     // Text color
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 120, 40 );         // Input Area
    theInput.setFont( f );                          // Font
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theInputNo.setBounds( 260, 50, 120, 40 );       // Input Area
    theInputNo.setFont( f );                        // Font
    theInputNo.setText("0");                        // 0
    cp.add( theInputNo );                           //  Add to canvas

    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }
  
  public void setController( BackDoorController c )
  {
    cont = c;
  }

  /**
   * Update the view, called by notifyObservers(theAction) in model,
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )  
  {
    BackDoorModel model  = (BackDoorModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();
  }

}