Skip to content
This repository  
Search
Pull requests
Issues
Gist
 @samuliedvin
 Unwatch 2
  Star 0
  Fork 2 Jakkara/Yahtzee
 Code  Issues 0  Pull requests 0  Wiki  Pulse  Graphs
Branch: master Find file Copy pathYahtzee/guidemo.java
7c505e0  2 days ago
@Jakkara Jakkara Update guidemo.java
2 contributors @samuliedvin @Jakkara
RawBlameHistory     557 lines (459 sloc)  15 KB
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import java.awt.List;
import javax.swing.JTable;
import java.awt.Button;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;

public class guidemo {

	private JFrame frame;
	private JTable table;
	private JToggleButton button_0;
	private JToggleButton button_1;
	private JToggleButton button_2;
	private JToggleButton button_3;
	private JToggleButton button_4;
	private boolean[] valitut;
	private File kuva;
	private BufferedImage tausta;
	private static int player;
	private int throwCount;
	private boolean throwable;


	
	//Pistetaulut JTable-taulukkoa varten.
	
	String[] columnNames = {
			"Kädet",	
			"Pelaaja 1",
			"Pelaaja 2"
			};

	Object[][] data = {
			{"Ykköset", 	null, null},
			{"Kakkoset", 	null, null},
			{"Kolmoset", 	null, null},
			{"Neloset", 	null, null},
			{"Vitoset", 	null, null},
			{"Kutoset", 	null, null},
			{"Bonus:", 		null, null},
			{"Pari", 		null, null},
			{"Kaksi paria", null, null},
			{"Kolme samaa", null, null},
			{"Neljä samaa", null, null},
			{"Suora 1-5", 	null, null},
			{"Suora 2-6", 	null, null},
			{"Mökki", 		null, null},
			{"Sattuma", 	null, null},
			{"Yatzy",		null, null},
			{"Yhteensä", 	null, null}
	};
	private JMenuBar menuBar;
	private JMenuItem mntmUusiPeli;
	private JMenuItem mntmAvaa;
	private JMenuItem menuItem;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guidemo window = new guidemo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public guidemo() {
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Hand hand = new Hand();
		throwCount = 0;
		player = 1;
		throwable = true;
		
		//Valittu boolean[] -taulukko haluttujen noppien uudelleenheittoa varten
		valitut = new boolean[5];
		
		
		//Luodaan ikkuna
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kuva = new File("src/test.jpg");
		
		//Lisätään taustakuva
		try {
			tausta = ImageIO.read(kuva);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		
		
		/*
		 *  VALIKKO
		 */
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mntmUusiPeli = new JMenuItem("Uusi peli");
		mntmUusiPeli.addMouseListener(new MouseAdapter() {
			
			Object[] options = {"Peruuta",
                    "Ei",
                    "Kyllä"};
			@Override
			public void mouseClicked(MouseEvent e) {
			    JOptionPane.showOptionDialog(frame,
			    	    		"Haluatko tallentaa nykyisen pelin?",
			    	    	    "Uusi peli",
			    	    	    JOptionPane.YES_NO_CANCEL_OPTION,
			    	    	    JOptionPane.QUESTION_MESSAGE,
			    	    	    null,
			    	    	    options,
			    	    	    options[1]);
			    
			}
		});
		mntmUusiPeli.setFont(new Font("DIN", Font.PLAIN, 14));
		menuBar.add(mntmUusiPeli);
		
		mntmAvaa = new JMenuItem("Avaa");
		mntmAvaa.setFont(new Font("DIN", Font.PLAIN, 14));
		menuBar.add(mntmAvaa);
		
		menuItem = new JMenuItem("Tallenna");
		menuItem.setFont(new Font("DIN", Font.PLAIN, 14));
		menuBar.add(menuItem);
		frame.setContentPane(new ImagePanel(tausta));
		
		
		
		//Nopanheittopainike
		
		JButton btnNappula = new JButton("Heitä noppaa");
		btnNappula.setFont(new Font("DIN", Font.PLAIN, 13));
		btnNappula.setBounds(136, 388, 178, 29);
		btnNappula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (throwable) {
				hand.throwHand(valitut);
				int[] nopat = hand.getHand();
				button_0.setText("" + nopat[0]);
				button_1.setText("" + nopat[1]);
				button_2.setText("" + nopat[2]);
				button_3.setText("" + nopat[3]);
				button_4.setText("" + nopat[4]);
				
				throwCount++;
				if (throwCount == 3) {
					throwable = false;
					
				}
				
				}	
			}
		});
		
		frame.getContentPane().setLayout(null);
		
		//Otsikko
		JLabel lblYahzee = new JLabel("Yatzy");
		lblYahzee.setBounds(17, 6, 415, 53);
		lblYahzee.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblYahzee.setHorizontalAlignment(SwingConstants.CENTER);
		lblYahzee.setBackground(Color.WHITE);
		lblYahzee.setFont(new Font("DIN", Font.PLAIN, 42));
		frame.getContentPane().add(lblYahzee);
		
		
		btnNappula.setBackground(UIManager.getColor("ComboBox.disabledForeground"));
		frame.getContentPane().add(btnNappula);
		
		
		//Kumpi pelaaja vuorossa:
		JLabel lblKtesi = new JLabel("Vuorossa pelaaja " + player);
		lblKtesi.setHorizontalAlignment(SwingConstants.CENTER);
		lblKtesi.setFont(new Font("DIN", Font.PLAIN, 13));
		lblKtesi.setBounds(125, 416, 200, 29);
		frame.getContentPane().add(lblKtesi);
		
		
		
		//Luodaan taulukko ScrollPanen sisään:
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 72, 383, 292);
		frame.getContentPane().add(scrollPane);
		
		//Soluja ei saa pystyä muokkaamaan
		table = new JTable(data, columnNames) {
		        private static final long serialVersionUID = 1L;
		        public boolean isCellEditable(int row, int column) {                
		                return false;               
		        };
		};
		scrollPane.setViewportView(table);
		table.setToolTipText("Valitse haluamasi rivi");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.setFont(new Font("DIN", Font.PLAIN, 12));
		table.setCellSelectionEnabled(true);
		
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      
			      // Mikäli rivi ei ole tyhjä, tulee varoitus ja pelaaja saa sijoittaa pisteensä uudelleen.
			      if (data[row][column+player] == null) {
				    if (e.getClickCount() == 2) {
				     
				      
				      // Tsekataan pisteet valitun rivin perusteella.
				      
				      //Ykköset
				      if (row == 0) {
				    	  data[row][column+player] = hand.checkOne();
				      }
				      //Kakkoset
				      if (row == 1) {
				    	  data[row][column+player] = hand.checkTwo();
				      }
				      //Kolmoset
				      if (row == 2) {
				    	  data[row][column+player] = hand.checkThree();
				      }
				      //Neloset
				      if (row == 3) {
				    	  data[row][column+player] = hand.checkFour();
				      }
				      //Vitoset
				      if (row == 4) {
				    	  data[row][column+player] = hand.checkFive();
				      }
				      //Kutoset
				      if (row == 5) {
				    	  data[row][column+player] = hand.checkSix();
				      }
				      //Pari
				      if (row == 7) {
				    	  data[row][column+player] = hand.checkPair();
				      }
				      //Kaksi paria
				      if (row == 8) {
				    	  data[row][column+player] = hand.checkTwoPairs();
				      }
				      //Kolme samaa
				      if (row == 9) {
				    	  data[row][column+player] = hand.checkThreeofaKind();
				      }
				      //Neljä samaa
				      if (row == 10) {
				    	  data[row][column+player] = hand.checkFourofaKind();
				      }
				      //Suora 1-5
				      if (row == 11) {
				    	  data[row][column+player] = hand.checkSmallStraight();
				      }
				      //Suora 2-6
				      if (row == 12) {
				    	  data[row][column+player] = hand.checkLargeStraight();
				      }
				      //Mökki
				      if (row == 13) {
				    	  data[row][column+player] = hand.checkFullHouse();
				      }
				      //Sattuma
				      if (row == 14) {
				    	  data[row][column+player] = hand.checkChance();
				      }
				      //Yatzy
				      if (row == 15) {
				    	  data[row][column+player] = hand.checkYahtzee();
				      }
				      
				      
			    	  
			    	  button_0.setSelected(false);
			    	  button_1.setSelected(false);
			    	  button_2.setSelected(false);
			    	  button_3.setSelected(false);
			    	  button_4.setSelected(false);
			    	  
			    	  for (int i = 0; i< 5 ; i++) {
			    		  valitut[i] = false;
			    	  }
			    	  
			    	  int bonuslaskuri = 0;
			    	  
			    	  for (int i=0;i<6;i++) {
			    		  if (data[i][player] instanceof Integer){
			    			  bonuslaskuri=bonuslaskuri + (int)data[i][player];
			    		  }
			    	  }
			    	  
			    	  if (bonuslaskuri >= 63) {
			    		  data[6][player] = 50;
			    	  }
			    	  
			    	  
			    	  //Nollataan seuraavaa heittäjää varten muuttujat:
			    	  target.updateUI();
				      playerSwitch();
				      lblKtesi.setText("Vuorossa pelaaja " + player);
			    	  throwable = true;
			    	  throwCount = 0;
				    } 
			    } else {
			    	JOptionPane.showMessageDialog(frame,
			    	"Sinulla on jo pisteitä tässä kädessä, valitse uudelleen!",
			    	"Senkin petkuttaja!",
			    	JOptionPane.PLAIN_MESSAGE);
			    }
			  }
			});
		
		
		
		

		/* Yatzynopat buttoneina, jokaiseen on lisätty eventlistener 
		 * joka kertoo halutaanko noppa heittää vai ei
		 */
		
		//Noppa 1
		
		button_0 = new JToggleButton("");
		button_0.setFont(new Font("DIN", Font.PLAIN, 16));
		button_0.setBounds(33, 466, 50, 50);
		button_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	            	valitut[0] = true;
	            } 
	            else if(tBtn.isSelected() != true) valitut[0] = false;
	            
			}
		});
		frame.getContentPane().add(button_0);
		
		//Noppa 2
		
		button_1 = new JToggleButton("");	
		button_1.setFont(new Font("DIN", Font.PLAIN, 16));
		button_1.setBounds(116, 466, 50,50);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	            	valitut[1] = true;
	            } 
	            else if(tBtn.isSelected() != true) valitut[1] = false;
			}
		});
		frame.getContentPane().add(button_1);
		
		//Noppa 3
		
		button_2 = new JToggleButton("");
		button_2.setFont(new Font("DIN", Font.PLAIN, 16));
		button_2.setBounds(199, 466, 50,50);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	            	valitut[2] = true;
	            } else valitut[2] = false;
			}
		});
		frame.getContentPane().add(button_2);
		
		//Noppa 4
		
		button_3 = new JToggleButton("");
		button_3.setFont(new Font("DIN", Font.PLAIN, 16));
		button_3.setBounds(282, 466, 50,50);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	            	valitut[3] = true;
	            } else valitut[3] = false;
			}
		});
		frame.getContentPane().add(button_3);
		
		//Noppa 5
		
		button_4 = new JToggleButton("");
		button_4.setFont(new Font("DIN", Font.PLAIN, 16));
		button_4.setBounds(365, 466, 50,50);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	            	valitut[4] = true;
	            } else valitut[4] = false;
			}
		});
		frame.getContentPane().add(button_4);


		
		
		
		
	
		
	}
	
	/*
	 * Pelaajan vaihtometodi, vaihtaa pelaajan jonka sarakkeeseen noppien arvo tallennetaan
	 */

	public static void playerSwitch() {
		if (player == 1) {
			player = 2;
		} else {
			player = 1;
		}
		
	}
}



	
class ImagePanel extends JComponent {
	private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}	

//*********************
//* TALLENNUS JA LATAUS
//*********************

/**
 * @param p1points Pelaajan pisteet arraylistana
 * @param p2points -||-
 * @throws IOException
 * @author J
 * Tallentaa pistelistan tekstitiedostoon muodossa *,*,*,*,...
 */
public void saveScores(Object[][] data) throws IOException{
	ArrayList<Integer> p1points = new ArrayList<Integer>(); //siirretään pisteet käsittelyä varten matriisista listaan
	ArrayList<Integer> p2points = new ArrayList<Integer>();
	for (int i = 0; i < 15; i++){
		p1points.add((Integer) data[i][1]);
		p2points.add((Integer) data[i][2]);
	}
	
	String p1sheet = "C:/Users/Public/Documents/sheet1.txt"; //osoitetaan tallennusolio kohti oikeaa tiedostoa
	String p2sheet = "C:/Users/Public/Documents/sheet2.txt";
	
	Save manager1 = new Save(p1sheet); //molempia pelaajia vastaa oma tallennusolio
	Save manager2 = new Save(p2sheet);
	
	manager1.writeToFile(p1points); //kutsutaan tallennusmetodia molemmille pelaajille
	manager2.writeToFile(p2points);
}


/**
 * @param data 3*15 matriisi joka sisältää tulokset muotoa mjono, p1-piste, p2-piste
 * @throws FileNotFoundException 
 */
/**
 * @param data matriisi pelaajien pisteistä. 3*15, jossa rivin ensimmäinen on kuvaus pisteestä, toinen ja kolmas ovat pelaajien pisteet
 * @throws FileNotFoundException
 */
public void loadScores(Object[][] data) throws FileNotFoundException{
	ArrayList<Integer> p1points = new ArrayList<Integer>();
	ArrayList<Integer> p2points = new ArrayList<Integer>();
	String p1sheet = "C:/Users/Public/Documents/sheet1.txt";
	String p2sheet = "C:/Users/Public/Documents/sheet2.txt";
	Save manager1 = new Save(p1sheet);
	Save manager2 = new Save(p2sheet);
	p1points = manager1.readFromFile(p1sheet);
	p2points = manager2.readFromFile(p2sheet);
	for (int i = 0; i < 15; i++){
		data[i][1] = p1points.get(i);
		data[i][2] = p2points.get(i);
		}
	}
Status API Training Shop Blog About Pricing
© 2016 GitHub, Inc. Terms Privacy Security Contact Help

//*********************
//* TALLENNUS JA LATAUS
//*********************

/**
 * @param p1points Pelaajan pisteet arraylistana
 * @param p2points -||-
 * @throws IOException
 * @author J
 * Tallentaa pistelistan tekstitiedostoon muodossa *,*,*,*,...
 */
public void saveScores(Object[][] data) throws IOException{
	ArrayList<Integer> p1points = new ArrayList<Integer>(); //siirretään pisteet käsittelyä varten matriisista listaan
	ArrayList<Integer> p2points = new ArrayList<Integer>();
	for (int i = 0; i < 15; i++){
		p1points.add((Integer) data[i][1]);
		p2points.add((Integer) data[i][2]);
	}
	
	String p1sheet = "C:/Users/Public/Documents/sheet1.txt"; //osoitetaan tallennusolio kohti oikeaa tiedostoa
	String p2sheet = "C:/Users/Public/Documents/sheet2.txt";
	
	Save manager1 = new Save(p1sheet); //molempia pelaajia vastaa oma tallennusolio
	Save manager2 = new Save(p2sheet);
	
	manager1.writeToFile(p1points); //kutsutaan tallennusmetodia molemmille pelaajille
	manager2.writeToFile(p2points);
}


/**
 * @param data 3*15 matriisi joka sisältää tulokset muotoa mjono, p1-piste, p2-piste
 * @throws FileNotFoundException 
 */
/**
 * @param data matriisi pelaajien pisteistä. 3*15, jossa rivin ensimmäinen on kuvaus pisteestä, toinen ja kolmas ovat pelaajien pisteet
 * @throws FileNotFoundException
 */
public void loadScores(Object[][] data) throws FileNotFoundException{
	ArrayList<Integer> p1points = new ArrayList<Integer>();
	ArrayList<Integer> p2points = new ArrayList<Integer>();
	String p1sheet = "C:/Users/Public/Documents/sheet1.txt";
	String p2sheet = "C:/Users/Public/Documents/sheet2.txt";
	Save manager1 = new Save(p1sheet);
	Save manager2 = new Save(p2sheet);
	p1points = manager1.readFromFile(p1sheet);
	p2points = manager2.readFromFile(p2sheet);
	for (int i = 0; i < 15; i++){
		data[i][1] = p1points.get(i);
		data[i][2] = p2points.get(i);
		}
	}
