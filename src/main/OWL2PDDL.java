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

import javax.swing.DefaultListModel;
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

import parser.OntologyParser;
import parser.ProblemFileWriter;
import parser.WebServiceParser;
import solver.PDDLSolver;

public class OWL2PDDL extends JFrame implements ActionListener{

	private static final long serialVersionUID = 5173308367703599691L;
	private String conceptFileLocation;
	private List<String> concepts;
	private OntologyParser op;
	private JPanel contentPane;
	private JButton btnBrowser;
	private JButton btnTranslate;
	private JButton btnGenerateProblemFile;
	private JButton btnSaveToFile;
	private JTextArea pddlDomainTextArea;
	private JList jListSelectedFile;
	private JScrollPane jspSelectedFiles;
	private List<String> fileList;
	private JButton btnSolve;
	private JButton btnRemove;
	private JList jlistInitialState;
	private JScrollPane jspGoal;
	private JList jlistGoal;
	private JScrollPane jspSolution;
	private JTextArea solutionTextArea;
	private JScrollPane jspProblem;
	private JTextArea probTextArea;
	private void loadOntology(){
		conceptFileLocation = "SWS-TC-1.1\\Ontology\\Concepts.owl";
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		op = new OntologyParser();
		concepts = op.parseOntology(jarDir.getAbsolutePath() + File.separatorChar +  conceptFileLocation);
	}
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
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
		loadOntology();
		fileList = new ArrayList<String>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 884, 728);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//button for choosing files
		btnBrowser = new JButton("Add File");
		btnBrowser.setBounds(133, 5, 71, 23);
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
		JLabel lblPddlDomainFile = new JLabel("PDDL Domain");
		lblPddlDomainFile.setBounds(10, 213, 112, 14);
		contentPane.add(lblPddlDomainFile);
		//set up container scrollpane
		JScrollPane jspPddlDomainFile = new JScrollPane();
		jspPddlDomainFile.setBounds(10, 238, 420, 269);
		contentPane.add(jspPddlDomainFile);
		//set up text area
		pddlDomainTextArea = new JTextArea();
		jspPddlDomainFile.setViewportView(pddlDomainTextArea);
		
		
		btnGenerateProblemFile = new JButton("Generate");
		btnGenerateProblemFile.setHorizontalAlignment(SwingConstants.LEADING);
		btnGenerateProblemFile.setBounds(553, 179, 89, 23);
		contentPane.add(btnGenerateProblemFile);
	
		
		btnSaveToFile = new JButton("Save");
		btnSaveToFile.setBounds(10, 533, 80, 23);
		contentPane.add(btnSaveToFile);
		
		JScrollPane jspInitialState = new JScrollPane();
		jspInitialState.setBounds(347, 39, 229, 129);
		contentPane.add(jspInitialState);
		
		jlistInitialState = new JList(concepts.toArray());
		jspInitialState.setViewportView(jlistInitialState);
		
		btnSolve = new JButton("Solve");
		btnSolve.setBounds(391, 533, 89, 23);
		contentPane.add(btnSolve);
		
		jspGoal = new JScrollPane();
		jspGoal.setBounds(627, 39, 229, 129);
		contentPane.add(jspGoal);
		
		jlistGoal = new JList(concepts.toArray());
		jspGoal.setViewportView(jlistGoal);
		
		
		jspSolution = new JScrollPane();
		jspSolution.setBounds(102, 581, 712, 86);
		solutionTextArea = new JTextArea();
		jspSolution.setViewportView(solutionTextArea);
		contentPane.add(jspSolution);
		
		jspProblem = new JScrollPane();
		jspProblem.setBounds(454, 238, 405, 269);
		contentPane.add(jspProblem);
		
		probTextArea = new JTextArea();
		jspProblem.setViewportView(probTextArea);
		
		JLabel prbLabel = new JLabel("PDDL Problem");
		prbLabel.setBounds(454, 213, 89, 14);
		contentPane.add(prbLabel);
		
		btnRemove = new JButton("Remove");
		btnRemove.setBounds(215, 5, 80, 23);
		contentPane.add(btnRemove);
		
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
				sb.append(WebServiceParser.parseService(filename));
			}
			pddlDomainTextArea.setText(sb.toString());
		} else if (o == btnGenerateProblemFile){
			solutionTextArea.setText("");
			List<String> input = jlistInitialState.getSelectedValuesList();
			List<String> output = jlistGoal.getSelectedValuesList();
			probTextArea.setText(ProblemFileWriter.writeProblem(input, output));
		} else if (o == btnSaveToFile){
			saveFile();

		} else if (o== btnSolve){
			solveProblem(); 
		} else if (o == btnRemove){
			removeFilename();
		}
		
	}
	private void removeFilename() {
		List<String> removed = new ArrayList<String>();
		for (int i : jListSelectedFile.getSelectedIndices())
			removed.add(fileList.get(i));
		fileList.removeAll(removed);
		jListSelectedFile.setListData(fileList.toArray());
		
	}
	public void registeredAction(){
		btnBrowser.addActionListener(this);
		btnTranslate.addActionListener(this);
		btnGenerateProblemFile.addActionListener(this);
		btnSaveToFile.addActionListener(this);
		btnSolve.addActionListener(this);
		btnRemove.addActionListener(this);
	}
	
	public void addFile(){
		
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setCurrentDirectory(new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()));
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
		solutionTextArea.setText(PDDLSolver.solve(input, output, services, op.getOntModel()));
	}
}
