package gui_Components;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class LoadingScreen {
	JFrame frame = new JFrame();
	private final int BATCH_SIZE = 15;
	private int batchingIndex = 0;
	private FileBar[] fileBarList = null;
	private JProgressBar progressBar;
	int progress = 0;
	public boolean visible = true;
	private JButton btnClose;
	private JButton btnOpenFile;
	
	public LoadingScreen(FileBar[] newFileBarList) {
		this.fileBarList = newFileBarList;
		
		frame = new JFrame("Parsing...");
		frame.setBounds(100, 100, 555, 447);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setVisible(true);
		
		progressBar = new JProgressBar(0, newFileBarList.length);
		progressBar.setValue(progress);
		progressBar.setStringPainted(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane);
		
		JPanel panelBottom = new JPanel();
		frame.getContentPane().add(panelBottom);
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));
		
		
		JPanel panelButtons = new JPanel();
		JPanel panelSpacing = new JPanel();
		panelBottom.add(panelSpacing);
		panelBottom.add(progressBar);
		panelBottom.add(panelButtons);
		
		btnOpenFile = new JButton("Open Document");
		btnOpenFile.setToolTipText("Open output excel document, available after parsing completes.");
		btnOpenFile.setEnabled(false);
		btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelLoading();
			}
		});
		btnClose.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelButtons.add(btnOpenFile);
		panelButtons.add(btnClose);
		
		
		JPanel scrollView = new JPanel();
		scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.PAGE_AXIS));
		scrollPane.setViewportView(scrollView);
		

		for(FileBar fb : this.fileBarList) {
			scrollView.add(fb);
			
		}
	}
	public int getFileCount() {
		return fileBarList.length;
	}
	public void cancelLoading() {
		visible = false;
		frame.dispose();
	}

	public void increaseProgress() {
		progress += 1;
		progressBar.setValue(progress);
	}
	
	public void endLoading(String xlsxFilePath) {
		btnClose.setText("Close");
		File xlsxDocument = new File(xlsxFilePath+".xlsx");
		if(xlsxDocument.exists()) {
			btnOpenFile.setEnabled(true);
			btnOpenFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop desktop = Desktop.getDesktop();  
						desktop.open(xlsxDocument);
					}
					catch (Exception e2) {
						System.out.println("Error opening file.");
						JOptionPane.showMessageDialog(null,
							    "Error opening file: \n" + e2.getMessage(),
							    "IOException",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		else 
			System.out.println("The xlsxDocument doesn't exist...");
	}
	
	//TODO: Does getBatch need to use BATCH_SIZE-1 , so files aren't ran twice?
	public FileBar[] getBatch() {
		int end = ( (batchingIndex + BATCH_SIZE) > this.fileBarList.length-1) ? (this.fileBarList.length) : (batchingIndex + BATCH_SIZE);
		return Arrays.copyOfRange(this.fileBarList, batchingIndex, end); //(Array, starting_index, num_of_items). 
	}
	
	public void nextBatch() {
		this.batchingIndex += BATCH_SIZE;
	}
	
	public Boolean isDone() {
		return (batchingIndex > fileBarList.length-1);
	}
}
