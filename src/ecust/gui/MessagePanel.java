package ecust.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * ��Ϣ���
 * @author CommissarMa
 *
 */
public class MessagePanel extends JPanel {
	private JScrollPane scrollPane;
	private JTextArea textArea;

	/**
	 * Create the panel.
	 */
	public MessagePanel() {
		setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				textArea.setCaretPosition(textArea.getText().length());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		scrollPane = new JScrollPane(textArea);
		add(scrollPane);
		
		this.setVisible(false);//��ʼ���ɼ�
	}
	
	public JTextArea getTextArea(){
		return this.textArea;
	}

}
