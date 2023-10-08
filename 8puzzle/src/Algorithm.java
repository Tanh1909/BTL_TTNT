import java.util.*;

public class Algorithm {
    private PriorityQueue<Node> openList;
    private Set<Node> closedList;
    private Node target;
    private Node start;
    public Algorithm(){
        target=new Node();
        start=new Node();
        openList=new PriorityQueue<>((o1, o2) -> o1.getF()- o2.getF());
        closedList=new HashSet<>();
    }
    public Node solver(){
        openList.add(start);
        while (!openList.isEmpty()){
            Node currentState=openList.poll();
            currentState.calculateF(target);
            if(currentState.isGoalState(target)){
                return currentState;
            }
            else{
                List<Node> nextStates=currentState.generateState();
                for(Node next:nextStates){
                    next.calculateF(target);
                    next.setParents(currentState);
                    openList.add(next);
                }
            }
            closedList.add(currentState);

        }
        return null;
    }
    public void process(){
        System.out.println("NHẬP TRẠNG THÁI BẮT ĐẦU");
        start.input();
        System.out.println("NHẬP TRẠNG THÁI KẾT THÚC");
        target.input();

        if(isSolvable(start,target)){
            Node result=solver();
            int cost=result.getG();
            List<Node> results=new ArrayList<>();
            results.add(result);
            while(result.getParents()!=null){
                results.add(result.getParents());
                result=result.getParents();
            }
            Collections.reverse(results);
            for(Node x:results){
                x.output();
            }
            System.out.println("Số lần di chuyển: "+cost);
        }
        else {
            System.out.println("Không thể tới đích!");
        }

    }
    public static boolean isSolvable(Node start, Node target) {
        // Biểu diễn trạng thái ban đầu và mục tiêu dưới dạng các dãy số
        int[] flatStart = new int[9];
        int[] flatTarget = new int[9];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                flatStart[k] = start.getBoard().get(i).get(j);
                flatTarget[k] = target.getBoard().get(i).get(j);
                k++;
            }
        }

        // Tính tích hợp của số lần di chuyển cho cả trạng thái ban đầu và mục tiêu
        int inversionsInitial = countInversions(flatStart);
        int inversionsTarget = countInversions(flatTarget);

        // Kiểm tra tính chẵn/lẻ của số lần di chuyển cho cả hai trạng thái
        return (inversionsInitial % 2 == 0 && inversionsTarget % 2 == 0) ||
                (inversionsInitial % 2 != 0 && inversionsTarget % 2 != 0);
    }

    public static int countInversions(int[] array) {
        int inversions = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] != 0 && array[j] != 0 && array[i] > array[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }
    public static void main(String[] args) {
        Algorithm algorithm=new Algorithm();
        algorithm.process();
    }
}
