import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.UIManager;


public class EightPuzzleFrame extends Frame implements ActionListener, KeyListener {
	
	private static Solution solution;
	private EightPuzzle resultNode;
	
	MenuBar menubar=new MenuBar();
	Menu menu_file = new Menu("操作");
	MenuItem start = new MenuItem("生成8数码");
	MenuItem solve = new MenuItem("求解");
	MenuItem printPath = new MenuItem("生成路径");
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
		solve.addActionListener(this);
		printPath.addActionListener(this);
		exit.addActionListener(this);
		
		menu_file.add(start);
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
			button[i].addActionListener(this);
			button[i].addKeyListener(this);
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
		} else if (e.getSource() == solve) {
			ArrayList<EightPuzzle> solveList;
			solveList = solution.startSolve();
			String log = "正在求解，当前open表节点数：" + solveList.size() 
			+ "， 评估值最小节点为：" + solveList.get(0).getNums().toString() + "\n";
			textArea.append(log);
			while (!solveList.isEmpty()) {
				solveList = solution.getBestNode();
				if (!solveList.isEmpty() && solveList.get(0).matchTarget()) {
					resultNode = solveList.get(0);
					textArea.append("求解完成！\n");
					break;
				} else {
					log = "正在求解，当前open表节点数：" + solveList.size() 
					+ "， 评估值最小节点为：" + Array + "\n";
					textArea.append(log);
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
		solution = new Solution();
		EightPuzzleFrame eightPuzzleFrame = new EightPuzzleFrame();
	}
	
}
