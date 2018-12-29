import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class EightPuzzle implements Comparable<EightPuzzle>{
	private int[] nums;
	private int g;
	private int f;
	private int h;
	private EightPuzzle parent;
	
	public EightPuzzle(int[] newNums) {
		nums = new int[9];
		for (int i = 0; i < 9; i++) {
			nums[i] = newNums[i];
		}
		g = 0;
		parent = null;
		computeHFromTarget(0);
		f = g + h;
	}
	public EightPuzzle(EightPuzzle EP) {
		nums = new int[9];
		for (int i = 0; i < 9; i++) {
			nums[i] = EP.nums[i];
		}
		g = EP.g;
		f = EP.f;
		h = EP.h;
		parent = EP.parent;
	}
	
	public boolean matchTarget() {
		int[] target = {1,2,3,4,5,6,7,8,0};
		return Arrays.equals(target, nums);
	}
	
	public int isContains(ArrayList<EightPuzzle> open) {
		for (int i = 0; i < open.size(); i++) {
			if (Arrays.equals(nums, open.get(i).nums)) {
				return i;
			}
		}
		return -1;
	}
	
	public void computeHFromTarget(int flag) {
		int total = 0;
		switch (flag % 4) {
		case 0:
			for (int i = 0; i < 9; i++) {
				if (nums[i] != 0 && nums[i] != i + 1) {
					total++;
				}
			}
			break;
		case 1:
			for (int i = 0; i < 9; i++) {
				int xi = i % 3;
				int yi = i / 3;
				if (nums[i] != 0) {
					int target = nums[i] - 1;
					int xt = target % 3;
					int yt = target / 3;
					total += (Math.abs(xi - xt) + Math.abs(yi - yt));
				} /*else {
					total += (Math.abs(xi - 2) +Math.abs(yi - 2));
				}*/
			}
			break;
		case 2:
			for (int i = 0; i < 9; i++) {
				if (nums[i] != 0 && nums[i] != i + 1) {
					total++;
				}
				else if (nums[i] == 0 && i != 8) {
					total++;
				}
			}
			break;
		case 3:
			for (int i = 0; i < 9; i++) {
				int xi = i % 3;
				int yi = i / 3;
				if (nums[i] != 0) {
					int target = nums[i] - 1;
					int xt = target % 3;
					int yt = target / 3;
					total += (Math.abs(xi - xt) + Math.abs(yi - yt));
				} else {
					total += (Math.abs(xi - 2) +Math.abs(yi - 2));
				}
			}
			break;
		default:
			break;
		}
		
		h = total;
	}
	
	public boolean movable(Direction dir) {
		int zeroPosition = 8;
		for (int i = 0; i < 9; i++) {
			if (nums[i] == 0) {
				zeroPosition = i;
				break;
			}
		}
		int zeroX = zeroPosition % 3;
		int zeroY = zeroPosition / 3;
		if (zeroX == 0 && dir == Direction.RIGHT) return false;
		if (zeroX == 2 && dir == Direction.LEFT) return false;
		if (zeroY == 0 && dir == Direction.DOWN) return false;
		if (zeroY == 2 && dir == Direction.UP) return false;
		return true;
	}
	
	public boolean move(Direction dir) {
		if (!movable(dir)) return false;
		int zeroPosition = 8;
		for (int i = 0; i < 9; i++) {
			if (nums[i] == 0) {
				zeroPosition = i;
				break;
			}
		}
		switch (dir) {
		case UP:
			nums[zeroPosition] = nums[zeroPosition + 3];
			nums[zeroPosition + 3] = 0;
			break;
		case DOWN:
			nums[zeroPosition] = nums[zeroPosition - 3];
			nums[zeroPosition - 3] = 0;
			break;
		case LEFT:
			nums[zeroPosition] = nums[zeroPosition + 1];
			nums[zeroPosition + 1] = 0;
			break;
		case RIGHT:
			nums[zeroPosition] = nums[zeroPosition - 1];
			nums[zeroPosition - 1] = 0;
		default:
			break;
		}
		return true;
	}
	
	public ArrayList<EightPuzzle> generateRoute() {
		EightPuzzle temp = this;
		ArrayList<EightPuzzle> route = new ArrayList<>();
		while (temp != null) {
			route.add(temp);
			temp = temp.parent;
		}
		return route;
	}
	
	@Override
	public String toString() {
		return "["+nums[0]+","+nums[1]+","+nums[2]+","
				+nums[3]+","+nums[4]+","+nums[5]+","
				+nums[6]+","+nums[7]+","+nums[8]+"], "
				+ "f="+f+", "+"g="+g+", "+"h="+h;
	}

	public int[] getNums() {
		return nums;
	}

	public void setNums(int[] nums) {
		this.nums = nums;
	}

	public int getG() {
		return g;
	}

	public void setG() {
		g += 1;
	}

	public int getF() {
		return f;
	}

	public void setF() {
		f = g + h;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public EightPuzzle getParent() {
		return parent;
	}

	public void setParent(EightPuzzle parent) {
		this.parent = parent;
	}

	@Override
	public int compareTo(EightPuzzle o) {
		return this.f - o.f;
	}
	
}
enum Direction{UP, DOWN, LEFT, RIGHT};
