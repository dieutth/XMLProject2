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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class OWL2PDDL extends JFrame implements ActionListener{

	private static final long serialVersionUID = 5173308367703599691L;
	private JPanel contentPane;
	private JButton btnBrowser;
	private JButton btnTranslate;
	private JButton btnGenerateProblemFile;
	JScrollPane selectedFiles;
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
		
		JLabel lblNewLabel = new JLabel("Choose owl files:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 5, 99, 23);
		contentPane.add(lblNewLabel);
		
		btnBrowser = new JButton("Browser...");
		btnBrowser.setBounds(119, 5, 89, 23);
		contentPane.add(btnBrowser);
		
		JLabel lblSelectedFiless = new JLabel("Selected Files:");
		lblSelectedFiless.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectedFiless.setBounds(10, 39, 99, 23);
		contentPane.add(lblSelectedFiless);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 73, 2, 2);
		contentPane.add(scrollPane);
		
		selectedFiles = new JScrollPane();
		selectedFiles.setBounds(10, 73, 293, 167);
		contentPane.add(selectedFiles);
		
		btnTranslate = new JButton("Translate");
		btnTranslate.setBounds(329, 137, 89, 23);
		contentPane.add(btnTranslate);
		
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
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(480, 73, 244, 167);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(480, 287, 244, 167);
		contentPane.add(textArea_1);
		
		JLabel lblPddlDomainFile = new JLabel("PDDL Domain File");
		lblPddlDomainFile.setBounds(488, 43, 112, 14);
		contentPane.add(lblPddlDomainFile);
		
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
			for (String filename : fileList){
				TestJaxp.translate(filename);
			}
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
		}			
	}
}
