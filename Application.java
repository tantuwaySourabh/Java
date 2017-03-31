

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.io.* ;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Font;
import javax.swing.JLabel;

public class Application {

	private JFrame frmEncryptionSoftware;
	private JTextField nameField;
	private JTextField codecField;
	private JTextField textPrivateKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmEncryptionSoftware.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws IOException {
		frmEncryptionSoftware = new JFrame();
		frmEncryptionSoftware.setTitle("Encryption Software");
		frmEncryptionSoftware.setBounds(100, 100, 450, 300);
		frmEncryptionSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEncryptionSoftware.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 262);
		frmEncryptionSoftware.getContentPane().add(panel);
		panel.setLayout(null);
		
		nameField = new JTextField();
		nameField.setBounds(76, 28, 86, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		codecField = new JTextField();
		codecField.setBounds(223, 28, 53, 20);
		panel.add(codecField);
		codecField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(77, 59, 347, 163);
		panel.add(textArea);
		
		final TextField processStatus = new TextField();
		processStatus.setFont(new Font("SansSerif", Font.PLAIN, 11));
		processStatus.setEditable(false);
		processStatus.setBounds(320, 234, 86, 22);
		panel.add(processStatus);
		processStatus.setText("Initialized...");
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processStatus.setText("Processing..");
				String fileName = nameField.getText() ;
				String codecName = codecField.getText() ;
				String privateKey=textPrivateKey.getText();
				processStatus.setText("Completed");
				try{
					String text = decrypt(fileName, codecName,privateKey) ;
					textArea.setText(text);
				}
				catch(FileNotFoundException fe)
				{
					textArea.setText("File not found in folder");
					processStatus.setText("Unsuccessful");
				}
				catch(IOException e)
				{
					processStatus.setText("Unsuccessful");
					System.out.println("IOException Occured") ;
				}
			}
		});
		btnDecrypt.setBounds(198, 233, 89, 23);
		panel.add(btnDecrypt);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processStatus.setText("Processing..");
				String fileName = nameField.getText() ;
				String codecName = codecField.getText() ;
				String encryptText = textArea.getText() ;
				String privateKey=textPrivateKey.getText();
				processStatus.setText("Completed");
				try{
					encrypt(fileName,codecName,encryptText,privateKey) ;
				}
				catch(IOException e) {
					processStatus.setText("Unsuccessful");
					System.out.println("IOException Occured");
				}
			}
		});
		btnEncrypt.setBounds(76, 233, 89, 23);
		panel.add(btnEncrypt);
		
		Label label = new Label("File Name");
		label.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label.setBounds(17, 26, 53, 22);
		panel.add(label);
		
		Label label_1 = new Label("File Text");
		label_1.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_1.setBounds(18, 66, 53, 22);
		panel.add(label_1);
		
		Label label_2 = new Label("Extension");
		label_2.setFont(new Font("SansSerif", Font.PLAIN, 11));
		label_2.setBounds(168, 26, 53, 22);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Codec");
		label_3.setBounds(295, 28, 44, 20);
		panel.add(label_3);
		
		textPrivateKey = new JTextField();
		textPrivateKey.setBounds(338, 28, 86, 20);
		panel.add(textPrivateKey);
		textPrivateKey.setColumns(10);
		
	}
	public void encrypt(String fileName,String codecName, String encryptText,String privateKey) throws IOException
	{
		String file = "C:\\Files\\"+fileName + "." + codecName ;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file))) ;
		encryptText = encryptText(encryptText,privateKey) ;
		pw.println(encryptText) ;
		pw.close();
	}
	public String decrypt(String fileName,String codecName,String privateKey) throws IOException
	{
		String file = "C:\\Files\\" + fileName + "." + codecName ;
		BufferedReader br = new BufferedReader(new FileReader(file)) ;
		String Text = "" ;
		String decryptText="";
		int[] key=new int[privateKey.length()];
		String ch;
		while((ch = br.readLine()) != null ){
			Text  = Text + ch ;
		}
		
		for(int x=0;x<privateKey.length();x++){
			int i=Text.charAt(x);
			key[x]=privateKey.charAt(x)-i;
		}
		
		for(int x=0,y=privateKey.length();y<Text.length();y++,x++){
			char charD=(char)(Text.charAt(y)+key[x%privateKey.length()]);
			decryptText=decryptText+charD;
		}
		br.close();
		return decryptText ;
	}
	public String encryptText(String encryptText,String privateKey)
	{
		String newText="";
		int[] randomKey=new int[privateKey.length()];
		
		for(int x=0;x<privateKey.length();x++){
			int i=(int)(1+Math.random()*9);
			randomKey[x]=i;
			
		}
		
		for(int x=0;x<privateKey.length();x++){
			int i=privateKey.charAt(x)-randomKey[x];
			newText=newText+(char)(i);
		}
		System.out.println(newText);
		for(int x=0;x<encryptText.length();x++){
			
			char ch=(char)(encryptText.charAt(x)-randomKey[x%privateKey.length()]);
			newText=newText+ch;
		}
		
		return newText;
	}
	
}