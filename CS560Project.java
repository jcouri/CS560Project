package FinalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BestPath {
	private Node list[];
	private Node queue[];
	private Node stack[];
	int head;
	int tail;
	int top;
	
	public BestPath() {
		list = new Node [234];
		queue = new Node [1000];
		stack = new Node [234];
		head = 0;
		tail = 0;
		top = 0;
		read();
		setup(226);
		enqueue(list[226]);
		list[226].total=list[226].cost;
		mark();
		move(list[8]);
		print();
	}
	
	class Node {
		int cost;
		int index;
		int total;
		int minCost;
		int minPath;
		int toT;
		int toTR;
		int toBR;
		int toB;
		int toBL;
		int toTL;
		boolean visited;
		Node T;
		Node B;
		Node TL;
		Node TR;
		Node BL;
		Node BR;
		Node(int i, int data) {
			cost = data;
			index = i;
			total = 999999;
			minCost = 999999;
			minPath = 9;
			visited = false;
			T = B = TL = TR = BL = BR = null;
		}
	}
	
	public void test(int i) {
		System.out.println("index: " + list[i].index);
		if (list[i].T == null){
			System.out.println("T: null");
		}
		else System.out.println("T: " + list[i].T.index);
		if (list[i].TR == null){
			System.out.println("TR: null");
		}
		else System.out.println("TR: " + list[i].TR.index);
		if (list[i].BR == null){
			System.out.println("BR: null");
		}
		else System.out.println("BR: " + list[i].BR.index);
		if (list[i].B == null){
			System.out.println("B: null");
		}
		else System.out.println("B: " + list[i].B.index);
		if (list[i].BL == null){
			System.out.println("BL: null");
		}
		else System.out.println("BL: " + list[i].BL.index);
		if (list[i].TL == null){
			System.out.println("TL: null");
		}
		else System.out.println("TL: " + list[i].TL.index);
	}
	
	public void test2(int i) {
		while (i<top){
			System.out.println(stack[i].index);
			i++;
		}
	}
	
	public void read() {
		String line;
    	try{
    		BufferedReader in = new BufferedReader(new FileReader("INPUT.TXT"));
    		while((line = in.readLine()) != null){
    			String value = line;
    			String [] split = value.split("\\s+");
    			int index = Integer.parseInt(split[0]);
    			int cost = Integer.parseInt(split[1]);
    			add(index, cost);
    		}
    		in.close();
    	}
    	catch(IOException e){
    		System.out.println("Cannot find file");
    	}
	}
	
	public void add(int i, int data) { 
		Node newNode = new Node(i, data);
		list[i] = newNode;
	}
	
	public void setup(int i) {
		if (list[i].index % 15 != 8 && list[i].index-8 > 0 && list[i].TR==null) {
			list[i].TR = list[list[i].index-7];
			list[list[i].index-7].BL = list[i];
			setup(list[i].index-7);
		}
		if (list[i].index - 15 > 0 && list[i].T==null) {
			list[i].T = list[list[i].index-15];
			list[list[i].index-15].B = list[i];
			setup(list[i].index-15);
		}
		if (list[i].index % 15 != 8 && list[i].index + 8 <= 233 &&list[i].BR==null) {
			list[i].BR = list[list[i].index+8];
			list[list[i].index+8].TL = list[i];
			setup(list[i].index+8);
		}
	}
	
	public void mark() {
		Node N = dequeue();
		if (N == null) return;
		if(N.T!=null){
			if (N.T.cost!=-1){
				if(N.T.total==999999) enqueue(N.T);
				N.toT = N.total + N.T.cost;
				if(N.toT<N.T.total){
					N.T.total=N.toT;
					enqueue(N.T);
				}
			}
		}
		if(N.TR!=null){
			if(N.TR.cost!=-1){
				if(N.TR.total==999999) enqueue(N.TR);
				N.toTR = N.total + N.TR.cost;
				if(N.toTR<N.TR.total){
					N.TR.total=N.toTR;
					enqueue(N.TR);
				}
			}
		}
		if(N.BR!=null){
			if (N.BR.cost!=-1){
				if(N.BR.total==999999) enqueue(N.BR);
				N.toBR = N.total + N.BR.cost;
				if(N.toBR<N.BR.total){
					N.BR.total=N.toBR;
					enqueue(N.BR);
				}
			}
		}
		if(N.B!=null){
			if (N.B.cost!=-1){
				if(N.B.total==999999) enqueue(N.B);
				N.toB = N.total + N.B.cost;
				if(N.toB<N.B.total){
					N.B.total=N.toB;
					enqueue(N.B);
				}
			}
		}
		if(N.BL!=null){
			if (N.BL.cost!=-1){
				if(N.BL.total==999999) enqueue(N.BL);
				N.toBL = N.total + N.BL.cost;
				if(N.toBL<N.BL.total){
					N.BL.total=N.toBL;
					enqueue(N.BL);
				}
			}
		}
		if(N.TL!=null){
			if (N.TL.cost!=-1){
				if(N.TL.total==999999) enqueue(N.TL);
				N.toTL = N.total + N.TL.cost;
				if(N.toTL<N.TL.total){
					N.TL.total=N.toTL;
					enqueue(N.TL);
				}
			}
		}
		mark();
	}
	
	public void move(Node N) {
		push(N);
		N.visited=true;
		if(N.T!=null){
			if (N.T.cost!=-1){
				if (!N.T.visited){
					if (N.T.total<N.minCost){
						N.minCost=N.T.total;
						N.minPath=0;
					}
				}
			}
		}
		if(N.TR!=null){
			if (N.TR.cost!=-1){
				if (!N.TR.visited){
					if (N.TR.total<N.minCost){
						N.minCost=N.TR.total;
						N.minPath=1;
					}
				}
			}
		}
		if(N.BR!=null){
			if (N.BR.cost!=-1){
				if (!N.BR.visited){
					if (N.BR.total<N.minCost){
						N.minCost=N.BR.total;
						N.minPath=2;
					}
				}
			}
		}
		if(N.B!=null){
			if (N.B.cost!=-1){
				if (!N.B.visited){
					if (N.B.total<N.minCost){
						N.minCost=N.B.total;
						N.minPath=3;
					}
				}
			}
		}
		if(N.BL!=null){
			if (N.BL.cost!=-1){
				if (!N.BL.visited){
					if (N.BL.total<N.minCost){
						N.minCost=N.BL.total;
						N.minPath=4;
					}
				}
			}
		}
		if(N.TL!=null){
			if (N.TL.cost!=-1){
				if (!N.TL.visited){
					if (N.TL.total<N.minCost){
						N.minCost=N.TL.total;
						N.minPath=5;
					}
				}
			}
		}
		if (N.minPath==0) move(N.T); 
		if (N.minPath==1) move(N.TR);
		if (N.minPath==2) move(N.BR);
		if (N.minPath==3) move(N.B);
		if (N.minPath==4) move(N.BL);
		if (N.minPath==5) move(N.TL);
	}
	
	public void enqueue(Node N){
		queue[tail] = N;
		tail++;
	}
	
	public Node dequeue(){
		if (head!=tail){
			Node N = queue[head];
			head++;
			return N;
		}
		return null;
	}
	
	public void push(Node N){
		stack[top] = N;
		top++;
	}
	
	public Node pop(){
		top--;
		Node N = stack[top];
		return N;
	}
	
	public void print(){
		while(top!=0){
			System.out.println(pop().index);
		}
		System.out.println("MINIMAL-COST PATH COSTS: " + list[8].total);
	}

	public static void main(String[] args) {
		new BestPath();
	}

}
