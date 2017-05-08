package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;

public class OWL2PDDL extends JFrame implements ActionListener{

	private static final long serialVersionUID = 5173308367703599691L;
	private JPanel contentPane;
	private JButton btnBrowser;
	private JButton btnTranslate;
	private JButton btnGenerateProblemFile;
	private JTextArea pddlDomainTextArea;
	private JList jListSelectedFile;
	private JScrollPane jspSelectedFiles;
	private List<String> fileList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String ontologyFileLocation = "E:\\OWL-S WEB SERVICES\\SWS-TC-1.1\\Ontology";
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OWL2PDDL frame = new OWL2PDDL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OWL2PDDL() {
		fileList = new ArrayList<String>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//button for choosing files
		btnBrowser = new JButton("Add File");
		btnBrowser.setBounds(149, 226, 71, 23);
		contentPane.add(btnBrowser);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 73, 2, 2);
		contentPane.add(scrollPane);
		
		/*
		 * Set up area for displaying selected owl-service files
		 */
		//set up label Selected Files
		JLabel lblSelectedFiless = new JLabel("Selected Files:");
		lblSelectedFiless.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectedFiless.setBounds(10, 5, 99, 23);
		contentPane.add(lblSelectedFiless);
		//set container jlist and scrollpane contains list of selected files
		//set jlist
		jListSelectedFile = new JList();
		jListSelectedFile.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jListSelectedFile.setLayoutOrientation(JList.VERTICAL_WRAP);
		jListSelectedFile.setVisible(true);
		jListSelectedFile.setVisibleRowCount(-1);
		jListSelectedFile.setAutoscrolls(true);
		//set scroll pane
		jspSelectedFiles = new JScrollPane();
		jspSelectedFiles.setBounds(10, 39, 293, 176);
		jspSelectedFiles.setViewportView(jListSelectedFile);
		contentPane.add(jspSelectedFiles);
		
		/*
		 * set up button translate to perform the translation from owl files to PDDL domain file
		 */
		btnTranslate = new JButton("Translate");
		btnTranslate.setBounds(329, 109, 89, 23);
		contentPane.add(btnTranslate);
		
		/*
		 * Set up area for displaying the generated pddl domain file
		 */
		//set up label
		JLabel lblPddlDomainFile = new JLabel("PDDL Domain File");
		lblPddlDomainFile.setBounds(480, 9, 112, 14);
		contentPane.add(lblPddlDomainFile);
		//set up container scrollpane
		JScrollPane jspPddlDomainFile = new JScrollPane();
		jspPddlDomainFile.setBounds(480, 39, 244, 176);
		contentPane.add(jspPddlDomainFile);
		//set up textare
		pddlDomainTextArea = new JTextArea();
		jspPddlDomainFile.setViewportView(pddlDomainTextArea);
		
		JLabel lblInitialState = new JLabel("Initial State:");
		lblInitialState.setBounds(10, 264, 99, 23);
		contentPane.add(lblInitialState);
		
		JList list = new JList();
		list.setBounds(90, 334, 1, 1);
		contentPane.add(list);
		
		JList list_1 = new JList();
		list_1.setBounds(10, 291, 293, 44);
		contentPane.add(list_1);
		
		btnGenerateProblemFile = new JButton("ProblemFile");
		btnGenerateProblemFile.setHorizontalAlignment(SwingConstants.LEADING);
		btnGenerateProblemFile.setBounds(329, 335, 89, 31);
		contentPane.add(btnGenerateProblemFile);
		
		JLabel lblGoal = new JLabel("Goal:");
		lblGoal.setBounds(10, 346, 46, 14);
		contentPane.add(lblGoal);
		
		JList list_2 = new JList();
		list_2.setBounds(40, 398, 1, 1);
		contentPane.add(list_2);
		
		JList list_3 = new JList();
		list_3.setBounds(10, 380, 293, 44);
		contentPane.add(list_3);
		
		
		JLabel lblProblemFile = new JLabel("Problem File:");
		lblProblemFile.setBounds(480, 264, 106, 23);
		contentPane.add(lblProblemFile);
		
		
		
		
		registeredAction();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o == btnBrowser){
			addFile();
			
		} else if (o == btnTranslate){
			StringBuilder sb = new StringBuilder();
			for (String filename : fileList){
				sb.append(TestJaxp.translate(filename));
			}
			pddlDomainTextArea.setText(sb.toString());
		} else if (o == btnGenerateProblemFile){
			
		}
		
	}
	public void registeredAction(){
		btnBrowser.addActionListener(this);
		btnTranslate.addActionListener(this);
		btnGenerateProblemFile.addActionListener(this);
	}
	
	public void addFile(){
		
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setCurrentDirectory(new File("E:\\OWL-S WEB SERVICES\\SWS-TC-1.1\\Services"));
		int result = chooser.showOpenDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION){
			File[] files = chooser.getSelectedFiles();
			for (File file : files){
				fileList.add(file.getAbsolutePath());
			}
			jListSelectedFile.setListData(fileList.toArray());
		}			
		
	}
}
