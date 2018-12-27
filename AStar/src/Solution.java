import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Solution {
	private ArrayList<EightPuzzle> open = new ArrayList<>();
	private ArrayList<EightPuzzle> close = new ArrayList<>();
	private ArrayList<EightPuzzle> result;
	private EightPuzzle start;
	
	public Solution() {}

	public EightPuzzle createEightPuzzle() {
		open.clear();
		close.clear();
		result = null;
		start = null;
		int[] init = {1,2,3,4,5,6,7,8,0};
		start = new EightPuzzle(init);
		int step = 0;
		int lastStep = 1;
		while (step < 5000) {
			Random random = new Random();
			int dirNum = (int)random.nextInt(4);
			if ((dirNum == 0 && lastStep == 1)
					|| (dirNum == 1 && lastStep == 0)
					|| (dirNum == 2 && lastStep == 3)
					|| (dirNum == 3 && lastStep == 2)) {
				continue;
			}
			Direction dir = Direction.values()[dirNum];
			if (start.movable(dir)) {
				start.move(dir);
				lastStep = dirNum;
				step++;
			}
		}
		return start;
	}
	
	public EightPuzzle getStart() {
		return start;
	}
	
	private void nodeOperation(EightPuzzle opEightPuzzle) {
		opEightPuzzle.computeHFromTarget();
		opEightPuzzle.setG();
		opEightPuzzle.setF();
		if (opEightPuzzle.isContains(close) == -1) {
			int position = opEightPuzzle.isContains(open);
			if (position == -1) {
				open.add(opEightPuzzle);
			} else {
				if (opEightPuzzle.getG() < open.get(position).getG()) {
					open.remove(position);
					open.add(opEightPuzzle);
				}
			}
		}
	}
	
	public ArrayList<EightPuzzle> startSolve() {
		if (open.isEmpty()) {
			open.add(start);
		}
		return open;
	}
	
	public ArrayList<EightPuzzle> getBestNode() {
		EightPuzzle best = open.get(0);
		open.remove(0);
		close.add(best);
		for (int i = 0; i < 4; i++) {
			Direction dir = Direction.values()[i];
			if (best.movable(dir)) {
				EightPuzzle opEightPuzzle = new EightPuzzle(best);
				opEightPuzzle.move(dir);
				opEightPuzzle.setParent(best);
				nodeOperation(opEightPuzzle);
			}
		}
		Collections.sort(open);
		return open;
	}
	
	
	private void getRoute() {
		
		int size = result.size();
		System.out.println("共经过"+ (size - 1) +"步求得解");
		for (int i = size - 1; i >= 0; i--) {
			System.out.println("step:" + (size - i - 1));
			result.get(i).print();
		}
	}
	
	/*public void main(String[] args) {
		open.add(start);
		boolean soluted = false;
		while (!open.isEmpty()) {
			Collections.sort(open);
			
		if (!soluted) {
			System.out.println("求解失败!");
		}
	}
*/
}
