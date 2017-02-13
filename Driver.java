import javax.swing.JFrame;

public class Driver {
	public static void main(String[] args){
		System.out.println("Testing calculator");
		CalcFrame calc = new CalcFrame();
		calc.setSize(760,680);
		calc.setVisible(true);
		calc.setResizable(false);
		calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calc.setTitle("My Calculator");
	}
}
