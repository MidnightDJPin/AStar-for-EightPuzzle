import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Solution {
	private static ArrayList<EightPuzzle> open = new ArrayList<>();
	private static ArrayList<EightPuzzle> close = new ArrayList<>();
	private static ArrayList<EightPuzzle> result;
	private static EightPuzzle start;

	private static void createEightPuzzle() {
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
		System.out.println("���Һ�ĳ�ʼ״̬Ϊ��");
		start.print();
	}
	
	private static void nodeOperation(EightPuzzle opEightPuzzle) {
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
	
	private static void printRoute() {
		int size = result.size();
		System.out.println("������"+ (size - 1) +"����ý�");
		for (int i = size - 1; i >= 0; i--) {
			System.out.println("step:" + (size - i - 1));
			result.get(i).print();
			/*try {
				Thread.sleep(3500);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}*/
		}
	}
	
	public static void main(String[] args) {
		createEightPuzzle();
		open.add(start);
		boolean soluted = false;
		while (!open.isEmpty()) {
			Collections.sort(open);
			EightPuzzle best = open.get(0);
			open.remove(0);
			close.add(best);
			if (best.matchTarget()) {
				result = best.generateRoute();
				printRoute();
				soluted = true;
				break;
			}
			for (int i = 0; i < 4; i++) {
				Direction dir = Direction.values()[i];
				if (best.movable(dir)) {
					EightPuzzle opEightPuzzle = new EightPuzzle(best);
					opEightPuzzle.move(dir);
					opEightPuzzle.setParent(best);
					nodeOperation(opEightPuzzle);
				}
			}
		}
		if (!soluted) {
			System.out.println("���ʧ��!");
		}
	}

}
