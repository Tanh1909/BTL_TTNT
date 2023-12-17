import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Node {
    private List<List<Integer>> board=new ArrayList<>();
    private int g=0;
    private int f;
    private Node parents;
    public Node(){

    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParents() {
        return parents;
    }

    public void setParents(Node parents) {
        this.parents = parents;
    }

    //ham nhap state
    public void input(){
        Scanner sc=new Scanner(System.in);
        for(int i=0;i<3;i++){
            List<Integer> row=new ArrayList<>();
            for(int j=0;j<3;j++){
                row.add(sc.nextInt());
            }
            board.add(row);
        }
    }

    //ham xuat state
    public void output(){
        for(List<Integer> x: board){
            for(int item:x){
                System.out.print(item+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //ham kiem tra xem da den trang thai dich hay chua
    public boolean isGoalState(Node target){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board.get(i).get(j)!=target.board.get(i).get(j)){
                    return false;
                }
            }
        }
        return true;
    }
    //ham sinh ra cac trang thai moi
    public List<Node> generateState(){
        //Cac trang thai ke tiep cua nut hien tai
        List<Node> states=new ArrayList<>();
        //Các huong xuong, phai, len, trai
        int[] dx={1,0,-1,0};
        int[] dy={0,1,0,-1};
        //Xac dinh vi tri cua o so 0 cua node hien tai
        int x=0,y=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        //duyet qua 4 huong
        for (int d = 0; d < 4; ++d) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            //kiem tra xem co di duoc khong
            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                Node newState=new Node();
                newState.setParents(this);
                newState.setG(this.g+1);
                List<List<Integer>> t=new ArrayList<>();
                for(int i=0;i<3;i++){
                    List<Integer> row=new ArrayList<>();
                    for(int j=0;j<3;j++){
                        row.add(this.board.get(i).get(j));
                    }
                    t.add(row);
                }
                //swap
                int e1=t.get(x).get(y);
                int e2=t.get(nx).get(ny);
                t.get(x).set(y,e2);
                t.get(nx).set(ny,e1);
                newState.setBoard(t);
                //kiem tra trang thai ke tiep co khac parents hay khong
                if(parents==null||!newState.equals(parents)){
                    //neu no khac parents thi them vao danh sach states
                    states.add(newState);
                }

            }
        }
        return states;
    }
    // Tính toán hàm f(n)
    public void calculateF(Node target) {
        int h = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) != target.getBoard().get(i).get(j)) {
                    h++;
                }
            }
        }
        this.f= h+g;
    }

    @Override
    public boolean equals(Object o) {
        Node node=(Node) o;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board.get(i).get(j)!=node.board.get(i).get(j)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, g, parents);
    }
}
