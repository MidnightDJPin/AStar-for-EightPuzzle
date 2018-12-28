import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.text.StyledEditorKit.ForegroundAction;


public class EightPuzzleFrame extends Frame implements ActionListener, KeyListener {
	
	private static Solution solution;
	private EightPuzzle resultNode;
	
	MenuBar menubar=new MenuBar();
	Menu menu_file = new Menu("操作");
	MenuItem start = new MenuItem("新生成8数码");
	MenuItem restart = new MenuItem("重置");
	MenuItem solve = new MenuItem("求解");
	MenuItem printPath = new MenuItem("还原");
	MenuItem exit = new MenuItem("退出");
	Button[] button;
	TextArea textArea;
	Panel panel;

	
	public EightPuzzleFrame() {
		super.setMenuBar(menubar);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		start.addActionListener(this);
		restart.addActionListener(this);
		solve.addActionListener(this);
		printPath.addActionListener(this);
		exit.addActionListener(this);
		
		menu_file.add(start);
		menu_file.add(restart);
		menu_file.add(solve);
		menu_file.add(printPath);
		menu_file.add(exit);
		menubar.add(menu_file);
		
		panel = new Panel(new GridLayout(3, 3));
		button = new Button[9];
		for(int i = 0; i < 9; i++) {
			if(i == 9 - 1) {
				button[i] = new Button(" ");
			}else {
				button[i] = new Button("" + (i + 1));
			}
			button[i].setFont(new Font("Courier", 1, 20));
			panel.add(button[i]);
		}
		
		textArea = new TextArea();
		textArea.setEditable(false);
		
		this.add(BorderLayout.CENTER, panel);
		this.add(BorderLayout.EAST, textArea);
		this.setTitle("EightPuzzle");
		this.setVisible(true);
		this.setSize(800, 400);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width/2;
		int screenHeight = screenSize.height/2;
		int height = this.getHeight();
		int width = this.getWidth();
		this.setLocation(screenWidth-width/2, screenHeight-height/2);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == start) {
			resultNode = null;
			int[] puzzle = solution.createEightPuzzle().getNums();
			for (int i = 0; i < 9; i++) {
				if (puzzle[i] == 0) button[i].setLabel(" ");
				else button[i].setLabel(puzzle[i] +"");
			}
		} else if (e.getSource() == restart) {
			
		} else if (e.getSource() == solve) {
			textArea.setText("");
			ArrayList<EightPuzzle> solveList;
			solveList = solution.startSolve();
			String log = "当前open表节点数：" + solveList.size() 
			+ "， 评估值最小节点为：" + solveList.get(0).toString() + "\n";
			textArea.append(log);
			while (!solveList.isEmpty()) {
				solveList = solution.getBestNode();
				if (!solveList.isEmpty() && solveList.get(0).matchTarget()) {
					resultNode = solveList.get(0);
					textArea.append("求解完成！\n");
					break;
				} else {
					log = "当前open表节点数：" + solveList.size() 
					+ "， 评估值最小节点为：" + solveList.get(0).toString() + "\n";
					textArea.append(log);
				}
			}
		} else if (e.getSource() == printPath && resultNode != null) {
			ArrayList<EightPuzzle> path = resultNode.generateRoute();
			for (int i = path.size() - 1; i >= 0; i--) {
				try {
					Thread.sleep(1200);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				textArea.append(path.get(i).toString() + "\n");
				int[] puzzle = path.get(i).getNums();
				for (int j = 0; j < 9; j++) {
					if (puzzle[j] == 0) button[j].setLabel(" ");
					else button[j].setLabel(puzzle[j] +"");
				}
			}
		} else if (e.getSource() == exit) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		solution = new Solution();
		EightPuzzleFrame eightPuzzleFrame = new EightPuzzleFrame();
	}
	
}
