package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.jena.ontology.Ontology;

import parser.OntologyParser;
import parser.WebServiceParser;
import solver.PDDLSolver;

public class OWL2PDDL extends JFrame implements ActionListener, ListSelectionListener{

	private static final long serialVersionUID = 5173308367703599691L;
	private static final String conceptFileLocation = "E:\\OWL-S WEB SERVICES\\SWS-TC-1.1\\Ontology\\Concepts.owl";
	private static final List<String> concepts = OntologyParser.parseOntology(conceptFileLocation);
	private JPanel contentPane;
	private JButton btnBrowser;
	private JButton btnTranslate;
	private JButton btnGenerateProblemFile;
	private JButton btnSaveToFile;
	private JButton btnChooseInput;
	private JTextArea pddlDomainTextArea;
	private JList jListSelectedFile;
	private JScrollPane jspSelectedFiles;
	private List<String> fileList;
	private JButton btnSolve;
	private JList jlistInitialState;
	private JButton btnResetInput;
	private JScrollPane jspGoal;
	private JList jlistGoal;
	
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
		setBounds(100, 100, 948, 905);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//button for choosing files
		btnBrowser = new JButton("Add File");
		btnBrowser.setBounds(232, 5, 71, 23);
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
		jspSelectedFiles.setBounds(10, 39, 293, 129);
		jspSelectedFiles.setViewportView(jListSelectedFile);
		contentPane.add(jspSelectedFiles);
		
		/*
		 * set up button translate to perform the translation from owl files to PDDL domain file
		 */
		btnTranslate = new JButton("Translate");
		btnTranslate.setBounds(112, 179, 89, 23);
		contentPane.add(btnTranslate);
		
		/*
		 * Set up area for displaying the generated pddl domain file
		 */
		//set up label
		JLabel lblPddlDomainFile = new JLabel("PDDL Domain File");
		lblPddlDomainFile.setBounds(10, 213, 112, 14);
		contentPane.add(lblPddlDomainFile);
		//set up container scrollpane
		JScrollPane jspPddlDomainFile = new JScrollPane();
		jspPddlDomainFile.setBounds(10, 238, 293, 352);
		contentPane.add(jspPddlDomainFile);
		//set up text area
		pddlDomainTextArea = new JTextArea();
		jspPddlDomainFile.setViewportView(pddlDomainTextArea);
		
		
		btnGenerateProblemFile = new JButton("Generate");
		btnGenerateProblemFile.setHorizontalAlignment(SwingConstants.LEADING);
		btnGenerateProblemFile.setBounds(502, 229, 89, 23);
		contentPane.add(btnGenerateProblemFile);
	
		
		btnSaveToFile = new JButton("Save");
		btnSaveToFile.setBounds(121, 613, 80, 23);
		contentPane.add(btnSaveToFile);
		
		/*
		 * Set up area for creating initial state
		 */
		btnChooseInput = new JButton("Input");
		btnChooseInput.setBounds(372, 179, 59, 23);
		contentPane.add(btnChooseInput);
		
		JScrollPane jspInitialState = new JScrollPane();
		jspInitialState.setBounds(362, 5, 229, 163);
		contentPane.add(jspInitialState);
		
		jlistInitialState = new JList(concepts.toArray());
		jspInitialState.setViewportView(jlistInitialState);
		
		btnSolve = new JButton("Solve");
		btnSolve.setBounds(474, 586, 89, 23);
		contentPane.add(btnSolve);
		
		btnResetInput = new JButton("Reset");
		btnResetInput.setBounds(502, 179, 61, 23);
		contentPane.add(btnResetInput);
		
		jspGoal = new JScrollPane();
		jspGoal.setBounds(682, 5, 229, 163);
		contentPane.add(jspGoal);
		
		jlistGoal = new JList(concepts.toArray());
		jspGoal.setViewportView(jlistGoal);
		
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
				sb.append(WebServiceParser.translate(filename));
			}
			pddlDomainTextArea.setText(sb.toString());
		} else if (o == btnGenerateProblemFile){
			
		} else if (o == btnSaveToFile){
			saveFile();
		} else if (o == btnChooseInput){
			//chooseInitialState();
		} else if (o== btnSolve){
			solveProblem(); 
		}
		
	}
	public void registeredAction(){
		btnBrowser.addActionListener(this);
		btnTranslate.addActionListener(this);
		btnGenerateProblemFile.addActionListener(this);
		btnSaveToFile.addActionListener(this);
		btnChooseInput.addActionListener(this);
		btnSolve.addActionListener(this);
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
	
	public void saveFile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save to");   
		 
		int userSelection = fileChooser.showSaveDialog(this);		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    FileWriter fw = null;
		    BufferedWriter bw = null;
			try {
				fw = new FileWriter(fileToSave.getAbsolutePath());
				bw = new BufferedWriter(fw);
				bw.write(pddlDomainTextArea.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
		   
		}
	}
	
	public void chooseInitialState(){
		StringBuilder sb = new StringBuilder();
		for (Object s : jlistInitialState.getSelectedValuesList())
			sb.append(s.toString() + "\n");
		System.out.println(sb);
	}
	
	public void solveProblem(){
		List<String> input = jlistInitialState.getSelectedValuesList();
		List<String> output = jlistGoal.getSelectedValuesList();
		List<Service> services = new ArrayList<Service>();
		for (String fileLocation : fileList){
			Service s = WebServiceParser.parseService(fileLocation);
			services.add(s);
		}
		PDDLSolver.solve(input, output, services);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
