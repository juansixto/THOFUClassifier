package DemoClassify;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import Corpus.Corpus;
import Corpus.CorpusLoaderException;
import Corpus.TBODCorpusLoader;
import Corpus.TBODCorpusLoader.LabelSet;

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
	//private JLabel label ;  NOT USED, DELETE?
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
		generateClassify();
		
		
		this.frmSentenceClassify = new JFrame();
		this.frmSentenceClassify.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.frmSentenceClassify.getContentPane().setForeground(new Color(0, 0, 0));
		this.frmSentenceClassify.setForeground(new Color(0, 0, 0));
		this.frmSentenceClassify.setFont(new Font("Impact", Font.PLAIN, 12));
		this.frmSentenceClassify.setBackground(new Color(192, 192, 192));
		this.frmSentenceClassify.setTitle("THOFU Classify");
		this.frmSentenceClassify.setBounds(100, 100, 1195, 894);
		this.frmSentenceClassify.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.menuBar = new JMenuBar();
		this.menuBar.setForeground(new Color(0, 0, 0));
		this.frmSentenceClassify.setJMenuBar(this.menuBar);
		
		this.mnNewMenu = new JMenu("File");
		this.mnNewMenu.setForeground(new Color(0, 0, 0));
		this.menuBar.add(this.mnNewMenu);
		
		this.mntmExit = new JMenuItem("Exit");
		this.mnNewMenu.add(this.mntmExit);
		
		this.mnAbout = new JMenu("About");
		this.mnAbout.setForeground(new Color(0, 0, 0));
		this.menuBar.add(this.mnAbout);
		
		this.mntmAboutThis = new JMenuItem("About this...");
		this.mnAbout.add(this.mntmAboutThis);
		
		JPanel panel = new JPanel();
		panel.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		panel.setForeground(new Color(0, 0, 0));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout groupLayout = new GroupLayout(this.frmSentenceClassify.getContentPane());
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
		
		
		
		this.lblResultNull = new JLabel("Result :");
		this.lblResultNull.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		this.lblResultNull.setForeground(new Color(0, 0, 0));
		
		this.TextPanel = new JEditorPane();
		this.TextPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.TextPanel.setForeground(new Color(0, 0, 0));
		
		this.lblResultNegative = new JLabel("");
		this.lblResultNegative.setHorizontalTextPosition(SwingConstants.RIGHT);
		this.lblResultNegative.setMaximumSize(new Dimension(33, 14));
		this.lblResultNegative.setIcon(new ImageIcon("/DemoClassify/Icons/Good.gif"));
		this.lblResultNegative.setForeground(Color.BLACK);
		this.lblResultNegative.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		this.lblResult = new JLabel();	
		this.label_facilities = new JLabel();
		this.label_staff = new JLabel();
		this.label_service = new JLabel();
		this.label_room = new JLabel();
	
		this.label_1 = new JLabel();
		this.label_1.setIcon(this.iconTHOFU);
		
		this.btnNewButton = new JButton("Classify");
		this.btnNewButton.setIconTextGap(10);
		this.btnNewButton.setIcon(new ImageIcon(ClientIU.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		this.btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		this.btnNewButton.setForeground(new Color(0, 0, 0));
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClientIU.this.TextPanel.setText("");
				ClientIU.this.lblResultNegative.setText("-");
				ClientIU.this.lblResult.setIcon(null);
				ClientIU.this.editorPane.setText("");
				ClientIU.this.editorPane2.setText("");
				ClientIU.this.editorPane3.setText("");
				ClientIU.this.editorPane4.setText("");
				ClientIU.this.label_facilities.setIcon(null);
				ClientIU.this.label_room.setIcon(null);
				ClientIU.this.label_service.setIcon(null);
				ClientIU.this.label_staff.setIcon(null);
			}
		});
		btnClear.setIconTextGap(10);
		btnClear.setIcon(new ImageIcon(ClientIU.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose-pressed.gif")));
		btnClear.setForeground(Color.BLACK);
		btnClear.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		JLabel lblAboutRoom = new JLabel("About room");
		lblAboutRoom.setForeground(Color.BLACK);
		lblAboutRoom.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		this.lblAboutService = new JLabel("About service");
		this.lblAboutService.setForeground(Color.BLACK);
		this.lblAboutService.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		
		this.editorPane.setForeground(Color.BLACK);
		this.editorPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		this.editorPane2.setForeground(Color.BLACK);
		this.editorPane2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel label_2 = new JLabel("About staff");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
		JLabel label_3 = new JLabel("About facilities");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		
	
		this.editorPane3.setForeground(Color.BLACK);
		this.editorPane3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		this.editorPane4.setForeground(Color.BLACK);
		this.editorPane4.setBorder(new LineBorder(new Color(0, 0, 0)));
	
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(31)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(this.TextPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 583, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(565)
									.addComponent(this.lblResultNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(192))
								.addComponent(lblInsertSentence, Alignment.LEADING)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(39)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(this.btnNewButton, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(this.lblAboutService, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
												.addComponent(this.label_service, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(this.editorPane2, 0, 0, Short.MAX_VALUE))
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblAboutRoom, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
												.addComponent(this.label_room, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addGap(18)
											.addComponent(this.editorPane, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
												.addComponent(this.label_facilities, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(this.editorPane4))
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
												.addComponent(this.label_staff, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
											.addGap(18)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(this.lblResultNull)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(this.lblResult, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
												.addComponent(this.editorPane3, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE))))))))
					.addGap(692)
					.addComponent(this.label_1, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addComponent(lblInsertSentence)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(this.TextPanel, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(this.lblResultNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.lblResultNull)
						.addComponent(this.lblResult, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblAboutRoom)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.label_room, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.editorPane, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.label_staff, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.editorPane3, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(this.label_facilities, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
								.addComponent(this.editorPane2, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(this.lblAboutService)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(this.label_service, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addGap(70)
									.addComponent(this.label_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
							.addGap(8)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(this.btnNewButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
						.addComponent(this.editorPane4, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
					.addGap(50))
		);
		panel.setLayout(gl_panel);
		this.frmSentenceClassify.getContentPane().setLayout(groupLayout);
	}
	private void createEvents() {
		this.btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String Sentence = ClientIU.this.TextPanel.getText();
					Sentence = ClientIU.this.myCTG.generateSentence(Sentence);
					
					String resp = "";
					resp = ClientIU.this.ct.getClassify(Sentence);
					ClientIU.this.lblResultNegative.setText(resp);
					if (resp.contains("POSITIVE"))
					{	
						ClientIU.this.lblResult.setIcon(ClientIU.this.iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						ClientIU.this.lblResult.setIcon(ClientIU.this.iconPoor);
					}
					ClientIU.this.editorPane.setText(ClientIU.this.myCTG.roomSentences.toString());
					ClientIU.this.editorPane2.setText(ClientIU.this.myCTG.serviceSentences.toString());
					ClientIU.this.editorPane3.setText(ClientIU.this.myCTG.staffSentences.toString());
					ClientIU.this.editorPane4.setText(ClientIU.this.myCTG.facilitiesSentences.toString());
					Sentence = ClientIU.this.myCTG.generateSentence(ClientIU.this.editorPane.getText());
					resp = ClientIU.this.ct.getClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						ClientIU.this.label_room.setIcon(ClientIU.this.iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						ClientIU.this.label_room.setIcon(ClientIU.this.iconPoor);
					}
					Sentence = ClientIU.this.myCTG.generateSentence(ClientIU.this.editorPane2.getText());
					resp = ClientIU.this.ct.getClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						ClientIU.this.label_service.setIcon(ClientIU.this.iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						ClientIU.this.label_service.setIcon(ClientIU.this.iconPoor);
					}
					
					Sentence = ClientIU.this.myCTG.generateSentence(ClientIU.this.editorPane3.getText());
					resp = ClientIU.this.ct.getClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						ClientIU.this.label_staff.setIcon(ClientIU.this.iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						ClientIU.this.label_staff.setIcon(ClientIU.this.iconPoor);
					}
					
					Sentence = ClientIU.this.myCTG.generateSentence(ClientIU.this.editorPane4.getText());
					resp = ClientIU.this.ct.getClassify(Sentence);
					if (resp.contains("POSITIVE"))
					{	
						ClientIU.this.label_facilities.setIcon(ClientIU.this.iconGood);
					}
					else if (resp.contains("NEGATIVE"))
					{	
						ClientIU.this.label_facilities.setIcon(ClientIU.this.iconPoor);
					}
					
					
				    	
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
	
	}
	private void generateClassify() throws ClassNotFoundException, IOException, CorpusLoaderException {
		  
		
		  	//Load TBODCorpus
			final TBODCorpusLoader loader = new TBODCorpusLoader(LabelSet.TWO_LABEL);
			final Corpus corpus = loader.load();
			//List<myResult> ListRating = new ArrayList<myResult>(); NOT USED, DELETE?
			
			//Generate the corpus (.train y .test)
			this.myCTG = new CorpusTestGenerator();
			this.myCTG.generate(corpus);
			
			//Create the trainer
			this.ct = new ClassifyTrainer(ClassifyTrainer.PROP_FILE_PATH);
			this.ct.setTrainingExamples(CorpusTestGenerator.TRAIN_FILE_PATH);
			this.ct.setTest(CorpusTestGenerator.TEST_FILE_PATH);
	
	  }
}
