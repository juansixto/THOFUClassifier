package DemoClassify;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import Corpus.Corpus;
import Corpus.CorpusLoaderException;
import Corpus.TBODCorpusLoader;
import Corpus.TBODCorpusLoader.LabelSet;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.DropMode;
import java.awt.ComponentOrientation;
import javax.swing.JTextPane;
import java.awt.Component;
import javax.swing.JEditorPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;


import java.awt.Canvas;

public class ClientIU {

	private JFrame frmSentenceClassify;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmExit;
	private JMenu mnAbout;
	private JMenuItem mntmAboutThis;
	private JButton btnNewButton;
	private JLabel lblResultNull;
	private JEditorPane TextPanel;
	private CorpusTestGenerator myCTG;
	private ClassifyTrainer ct;
	private JLabel lblResult;
	private	JLabel lblResultNegative;
	private ImageIcon iconGood = new ImageIcon(System.getProperty("user.dir")+ "/Icons/Good.gif");
	private ImageIcon iconPoor = new ImageIcon(System.getProperty("user.dir")+ "/Icons/Poor.gif");
	private ImageIcon iconTHOFU = new ImageIcon(System.getProperty("user.dir")+ "/Icons/THOFU.png");
	private JLabel label ;
	private JLabel label_1;
	private JLabel lblAboutService;
	private JLabel label_facilities;
	private JLabel label_staff;
	private JLabel label_service;
	private JLabel label_room;
	private JEditorPane editorPane = new JEditorPane();
	private JEditorPane editorPane2 = new JEditorPane();
	private JEditorPane editorPane3 = new JEditorPane();
	private JEditorPane editorPane4 = new JEditorPane();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientIU window = new ClientIU();
					window.frmSentenceClassify.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws CorpusLoaderException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public ClientIU() throws ClassNotFoundException, IOException, CorpusLoaderException {
	
		initialize();
		createEvents();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws CorpusLoaderException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, IOException, CorpusLoaderException {
		GenerarClassify();
		
		
		frmSentenceClassify = new JFrame();
		frmSentenceClassify.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmSentenceClassify.getContentPane().setForeground(new Color(0, 0, 0));
		frmSentenceClassify.setForeground(new Color(0, 0, 0));
		frmSentenceClassify.setFont(new Font("Impact", Font.PLAIN, 12));
		frmSentenceClassify.setBackground(new Color(192, 192, 192));
		frmSentenceClassify.setTitle("THOFU Classify");
		frmSentenceClassify.setBounds(100, 100, 1195, 894);
		frmSentenceClassify.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menuBar.setForeground(new Color(0, 0, 0));
		frmSentenceClassify.setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("File");
		mnNewMenu.setForeground(new Color(0, 0, 0));
		menuBar.add(mnNewMenu);
		
		mntmExit = new JMenuItem("Exit");
		mnNewMenu.add(mntmExit);
		
		mnAbout = new JMenu("About");
		mnAbout.setForeground(new Color(0, 0, 0));
		menuBar.add(mnAbout);
		
		mntmAboutThis = new JMenuItem("About this...");
		mnAbout.add(mntmAboutThis);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		panel.setForeground(new Color(0, 0, 0));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout groupLayout = new GroupLayout(frmSentenceClassify.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 823, GroupLayout.PREFERRED_SIZE)
					.addGap(79))
		);
		
		final JLabel lblInsertSentence = new JLabel("Insert Review");
		lblInsertSentence.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblInsertSentence.setForeground(new Color(0, 0, 0));
		
		
		
		lblResultNull = new JLabel("Result :");
		lblResultNull.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblResultNull.setForeground(new Color(0, 0, 0));
		
		TextPanel = new JEditorPane();
		TextPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		TextPanel.setForeground(new Color(0, 0, 0));
		
		lblResultNegative = new JLabel("");
		lblResultNegative.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblResultNegative.setMaximumSize(new Dimension(33, 14));
		lblResultNegative.setIcon(new ImageIcon("/DemoClassify/Icons/Good.gif"));
		lblResultNegative.setForeground(Color.BLACK);
		lblResultNegative.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		lblResult = new JLabel();	
		label_facilities = new JLabel();
		label_staff = new JLabel();
		label_service = new JLabel();
		label_room = new JLabel();
	
		
		label_1 = new JLabel();
		label_1.setIcon(iconTHOFU);
		
		btnNewButton = new JButton("Classify");
		btnNewButton.setIconTextGap(10);
		btnNewButton.setIcon(new ImageIcon(ClientIU.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		btnNewButton.setForeground(new Color(0, 0, 0));
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TextPanel.setText("");
				lblResultNegative.setText("-");
				lblResult.setIcon(null);
				editorPane.setText("");
				editorPane2.setText("");
				editorPane3.setText("");
				editorPane4.setText("");
				label_facilities.setIcon(null);
				label_room.setIcon(null);
				label_service.setIcon(null);
				label_staff.setIcon(null);
			}
		});
		btnClear.setIconTextGap(10);
		btnClear.setIcon(new ImageIcon(ClientIU.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose-pressed.gif")));
		btnClear.setForeground(Color.BLACK);
		btnClear.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		JLabel lblAboutRoom = new JLabel("About room");
		lblAboutRoom.setForeground(Color.BLACK);
		lblAboutRoom.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		lblAboutService = new JLabel("About service");
		lblAboutService.setForeground(Color.BLACK);
		lblAboutService.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		
		editorPane.setForeground(Color.BLACK);
		editorPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		editorPane2.setForeground(Color.BLACK);
		editorPane2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel label_2 = new JLabel("About staff");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		JLabel label_3 = new JLabel("About facilities");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
	
		editorPane3.setForeground(Color.BLACK);
		editorPane3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		editorPane4.setForeground(Color.BLACK);
		editorPane4.setBorder(new LineBorder(new Color(0, 0, 0)));
	
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(31)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(TextPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 583, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(565)
									.addComponent(lblResultNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(192))
								.addComponent(lblInsertSentence, Alignment.LEADING)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(39)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblAboutService, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
												.addComponent(label_service, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(editorPane2, 0, 0, Short.MAX_VALUE))
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblAboutRoom, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
												.addComponent(label_room, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addGap(18)
											.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
												.addComponent(label_facilities, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(editorPane4))
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
												.addComponent(label_staff, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addGap(18)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lblResultNull)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
												.addComponent(editorPane3, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE))))))))
					.addGap(692)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addComponent(lblInsertSentence)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(TextPanel, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(lblResultNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblResultNull)
						.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblAboutRoom)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_room, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_staff, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(editorPane3, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_facilities, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
								.addComponent(editorPane2, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblAboutService)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_service, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addGap(70)
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
							.addGap(8)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
						.addComponent(editorPane4, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
					.addGap(50))
		);
		panel.setLayout(gl_panel);
		frmSentenceClassify.getContentPane().setLayout(groupLayout);
	}
	private void createEvents() {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String Sentence = TextPanel.getText();
					Sentence = myCTG.GenerateSentence(Sentence);
					
					String resp = "";
					resp = ct.GetClassify(Sentence);
					lblResultNegative.setText(resp);
					if (resp.contains("POSITIVE"))
					{	
						lblResult.setIcon(iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						lblResult.setIcon(iconPoor);
					}
					editorPane.setText(myCTG.RoomSentences.toString());
					editorPane2.setText(myCTG.ServiceSentences.toString());
					editorPane3.setText(myCTG.StaffSentences.toString());
					editorPane4.setText(myCTG.FacilitiesSentences.toString());
					Sentence = myCTG.GenerateSentence(editorPane.getText());
					resp = ct.GetClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						label_room.setIcon(iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						label_room.setIcon(iconPoor);
					}
					Sentence = myCTG.GenerateSentence(editorPane2.getText());
					resp = ct.GetClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						label_service.setIcon(iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						label_service.setIcon(iconPoor);
					}
					
					Sentence = myCTG.GenerateSentence(editorPane3.getText());
					resp = ct.GetClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						label_staff.setIcon(iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						label_staff.setIcon(iconPoor);
					}
					
					Sentence = myCTG.GenerateSentence(editorPane4.getText());
					resp = ct.GetClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						label_facilities.setIcon(iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						label_facilities.setIcon(iconPoor);
					}
					
					
				    	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	
	}
	private void GenerarClassify() throws ClassNotFoundException, IOException, CorpusLoaderException {
		  
		
		  	//Carga del TBODCorpus
			final TBODCorpusLoader loader = new TBODCorpusLoader(LabelSet.TWO_LABEL);
			final Corpus corpus = loader.load();
			List<myResult> ListRating = new ArrayList<myResult>();
			
			//Generaci�n del corpus (.train y .test)
			myCTG = new CorpusTestGenerator();
			myCTG.Generate(corpus);
			
			//Creaci�n del Trainer
			ct = new ClassifyTrainer("data/THOFUDemo.prop");
			ct.SetTrainingExamples("data/THOFUDemo.train");
			ct.SetTest("data/THOFUDemo.test");
	
	  }
}
