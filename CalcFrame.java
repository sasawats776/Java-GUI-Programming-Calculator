import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.net.URL;

public class CalcFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = -1781606895222190322L;
	private	JTextField resultField;
			JPanel bases, words, inPan;
			JRadioButton dec, bin, oct, hex, qWord, bWord, word, byteBut;
			ButtonGroup group1, group2;
			JButton a, b, c, d, e, f, addButton, subButton, multButton, divButton, equalButton, backButton,
					one, two, three, four, five, six, seven, eight, nine, zero, cancel, plusMinus, clear, square, remain, recip, comma, quot , mod;
			JTextArea bits;
			String s, saver, operator;
			int base; //base that the calc is currently in
			long left; //number on left side of operator
			long right; //number on right side of operator
			boolean justSolved, numPushed;
			StringSelection stringSelection;
			Clipboard clpbrd;
	
	public CalcFrame(){
		saver = "";
		operator = "";
		left = 0;
		right = 0;
		base = 0;
		justSolved = false; numPushed = false;
		Font font = new Font("Ariel",Font.PLAIN, 15); //font for all buttons
		clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();

		inPan = new JPanel();
		inPan.setLayout(null);
		
		 //Creating bar to hold menu(names)
		JMenuBar menuBar= new JMenuBar();
		 
		 
		 // creating menu(names)
		JMenu menuBarContent1= new JMenu("View");
		JMenu menuBarContent2= new JMenu("Edit");
		JMenu menuBarContent3= new JMenu("Help");
		 
		 // increasing size of menu in menubar
		menuBarContent1.setFont(font);
		menuBarContent2.setFont(font);
		menuBarContent3.setFont(font);
		
		 //Add the names to the menu bar 
		menuBar.add(menuBarContent1);
		menuBar.add(menuBarContent2);
		menuBar.add(menuBarContent3);
		
		//adding menu items and their actions when clicked
		JMenuItem view = new JMenuItem((new AbstractAction("View") {
		    public void actionPerformed(ActionEvent e) {
		       inPan.setVisible(true);
		    }
		}));
		JMenuItem hide = new JMenuItem((new AbstractAction("Hide") {
		    public void actionPerformed(ActionEvent e) {
			       inPan.setVisible(false);
			    }
		}));
		JMenuItem copy = new JMenuItem((new AbstractAction("Copy") {
		    public void actionPerformed(ActionEvent e) {
		    	stringSelection = new StringSelection(resultField.getText());
		    	clpbrd.setContents(stringSelection, null);
			    }
		}));
		JMenuItem help = new JMenuItem((new AbstractAction("Help") {
		    public void actionPerformed(ActionEvent e) {
		    	 try {
		    	        Desktop.getDesktop().browse(new URL("https://support.microsoft.com/en-us/products/windows?os=windows-10").toURI());
		    	    } catch (Exception f) {
		    	        f.printStackTrace();
		    	    }
			    }
		}));
		JMenuItem about = new JMenuItem(new AbstractAction("About") {
		    public void actionPerformed(ActionEvent e) {
		        JFrame frame = new JFrame("About");
		        JTextArea label = new JTextArea(" This calculator was designed by\n Sasawat Satumtira and was based on\n the windows 7 desktop calculator.\n Last Edited: 11/12/2016 11:30PM");
		        frame.add(label);
		        frame.setSize(250,150);
		        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		        frame.setVisible(true);
		    }
		});
		//adding menu items to menus
		menuBarContent1.add(view);
		menuBarContent1.add(hide);
		menuBarContent2.add(copy);
		menuBarContent3.add(help);
		menuBarContent3.add(about);
		//set menubar as the frame's menubar
		this.setJMenuBar(menuBar);

		//where the numbers are displayed
		resultField = new JTextField("0");
		resultField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		resultField.setEditable(false);
		resultField.setBounds(20, 20, 715,70);
		resultField.setFont(new Font("Ariel",Font.PLAIN, 35));
		inPan.add(resultField);
		
		//where bits are displayed
		bits = new JTextArea();
		bits.setEditable(false);
		bits.setBounds(20, 100, 715, 90);
		bits.setFont(font);
		setBits("0");
		inPan.add(bits);
		
		//where user goes to select base
		bases = new JPanel();
		bases.setLayout(new GridLayout(4,1));
		bases.setBounds(20, 200, 150, 195);
		bases.setBorder(BorderFactory.createLineBorder(null,3));
		
		//there for looks
		words = new JPanel();
		words.setLayout(new GridLayout(4,1));
		words.setBounds(20, 410, 150, 195);
		words.setBorder(BorderFactory.createLineBorder(null,3));
		
		//creating radiobuttons for bases
		dec = new JRadioButton("Dec");
		dec.setSelected(true);
		dec.setFont(font);
		base = 10;
		bin = new JRadioButton("Bin");
		bin.setFont(font);
		oct = new JRadioButton("Oct");
		oct.setFont(font);
		hex = new JRadioButton("Hex");
		hex.setFont(font);
		dec.addActionListener(this);
		bin.addActionListener(this);
		oct.addActionListener(this);
		hex.addActionListener(this);
		
		group1 = new ButtonGroup();
		group1.add(dec);
		group1.add(bin);
		group1.add(oct);
		group1.add(hex);
		
		//adding radiobuttons to panel
		bases.add(dec);
		bases.add(bin);
		bases.add(oct);
		bases.add(hex);
		inPan.add(bases);
		
		qWord = new JRadioButton("Qword");
		qWord.setSelected(true);
		qWord.setVisible(true);
		qWord.setFont(font);
		bWord = new JRadioButton("Bword");
		bWord.setEnabled(false);
		bWord.setVisible(true);
		bWord.setFont(font);
		word = new JRadioButton("Word");
		word.setEnabled(false);
		word.setVisible(true);
		word.setFont(font);
		byteBut = new JRadioButton("Byte");
		byteBut.setEnabled(false);
		byteBut.setVisible(true);
		byteBut.setFont(font);
		
		group2 = new ButtonGroup();
		group2.add(qWord);
		group2.add(bWord);
		group2.add(word);
		group2.add(byteBut);
		
		words.add(qWord);
		words.add(bWord);
		words.add(word);
		words.add(byteBut);
		inPan.add(words);
		
		//creating individual buttons and placing them
		a = new JButton("A");
		a.setFont(font);
		a.setBounds(320, 200, 65, 55);
		inPan.add(a);
		
		b = new JButton("B");
		b.setFont(font);
		b.setBounds(320, 270, 65, 55);
		inPan.add(b);
		
		c = new JButton("C");
		c.setFont(font);
		c.setBounds(320, 340, 65, 55);
		inPan.add(c);
		
		d = new JButton("D");
		d.setFont(font);
		d.setBounds(320, 410, 65, 55);
		inPan.add(d);
		
		e = new JButton("E");
		e.setFont(font);
		e.setBounds(320, 480, 65, 55);
		inPan.add(e);
		
		f = new JButton("F");
		f.setFont(font);
		f.setBounds(320, 550, 65, 55);
		inPan.add(f);
		
		one = new JButton("1"); 
		one.setFont(font);
		one.setBounds(390, 480, 65, 55);
		inPan.add(one);
		
		two = new JButton("2"); 
		two.setFont(font);
		two.setBounds(460, 480, 65, 55);
		inPan.add(two);
		
		three = new JButton("3");
		three.setFont(font);
		three.setBounds(530, 480, 65, 55);
		inPan.add(three);
		
		four = new JButton("4");
		four.setFont(font);
		four.setBounds(390, 410, 65, 55);
		inPan.add(four);
		
		five = new JButton("5");
		five.setFont(font);
		five.setBounds(460, 410, 65, 55);
		inPan.add(five);
		
		six = new JButton("6");
		six.setFont(font);
		six.setBounds(530, 410, 65, 55);
		inPan.add(six);
		
		seven = new JButton("7");
		seven.setFont(font);
		seven.setBounds(390, 340, 65, 55);
		inPan.add(seven);
		
		eight = new JButton("8");
		eight.setFont(font);
		eight.setBounds(460, 340, 65, 55);
		inPan.add(eight);
		
		nine = new JButton("9");
		nine.setFont(font);
		nine.setBounds(530, 340, 65, 55);
		inPan.add(nine);
		
		zero = new JButton("0");
		zero.setFont(font);
		zero.setBounds(390, 550, 135, 55);
		inPan.add(zero);
		
		addButton = new JButton("+");
		addButton.setFont(font);
		addButton.setBounds(600, 550, 65, 55);
		inPan.add(addButton);
		
		subButton = new JButton("-");
		subButton.setFont(font);
		subButton.setBounds(600, 480, 65, 55);
		inPan.add(subButton);
		
		multButton = new JButton("*");
		multButton.setFont(font);
		multButton.setBounds(600, 410, 65, 55);
		inPan.add(multButton);
		
		divButton = new JButton("/");
		divButton.setFont(font);
		divButton.setBounds(600, 340, 65, 55);
		inPan.add(divButton);
		
		equalButton = new JButton("=");
		equalButton.setFont(font);
		equalButton.setBounds(670, 480, 65, 125);
		inPan.add(equalButton);
		
		backButton = new JButton("\u2190");
		backButton.setFont(font);
		backButton.setBounds(390, 270, 65, 55);
		inPan.add(backButton);
		
		cancel = new JButton("CE");
		cancel.setFont(font);
		cancel.setBounds(460, 270, 65, 55);
		inPan.add(cancel);
		
		clear = new JButton("C");
		clear.setFont(font);
		clear.setBounds(530, 270, 65, 55);
		inPan.add(clear);
		
		plusMinus = new JButton("\u00B1");
		plusMinus.setFont(font);
		plusMinus.setBounds(600, 270, 65, 55);
		inPan.add(plusMinus);
		
		square = new JButton("\u221A");
		square.setFont(font);
		square.setBounds(670, 270, 65, 55);	
		square.setEnabled(false);
		inPan.add(square);
		
		remain = new JButton("%");
		remain.setFont(font);
		remain.setBounds(670, 340, 65, 55);
		remain.setEnabled(false);
		inPan.add(remain);
		
		recip = new JButton("1/x");
		recip.setFont(font);
		recip.setBounds(670, 410, 65, 55);
		recip.setEnabled(false);
		inPan.add(recip);
		
		comma = new JButton(".");
		comma.setFont(font);
		comma.setBounds(530, 550, 65, 55);
		comma.setEnabled(false);
		inPan.add(comma);
		
		quot = new JButton("Quot");
		quot.setFont(font);
		quot.setEnabled(false);
		quot.setBounds(180, 200, 65, 55);
		inPan.add(quot);
		
		mod = new JButton("Mod");
		mod.setFont(font);
		mod.setBounds(250, 200, 65, 55);
		inPan.add(mod);
		
		//creating blanks and placing them
		JButton[] blank = new JButton[15];
		for(int i = 0; i < 15; i++){
			blank[i] = new JButton(" ");
			blank[i].setEnabled(false);
			if(i < 5){
				blank[i].setBounds(390 + (70*i), 200, 65, 55);
			}
			else if(i < 7){
				blank[i].setBounds(180 + (70*(i%5)), 270, 65, 55);
			}
			else if(i < 9){
				blank[i].setBounds(180 + (70*(i%7)), 340, 65, 55);
			}
			else if(i < 11){
				blank[i].setBounds(180 + (70*(i%9)), 410, 65, 55);
			}
			else if(i < 13){
				blank[i].setBounds(180 + (70*(i%11)), 480, 65, 55);
			}
			else if(i < 15){
				blank[i].setBounds(180 + (70*(i%13)), 550, 65, 55);
			}
			inPan.add(blank[i]);
		}
	
		//calc will start by default in decimal so there buttons are disabled at the start
		a.setEnabled(false);
		b.setEnabled(false);
		c.setEnabled(false);
		d.setEnabled(false);
		e.setEnabled(false);
		f.setEnabled(false);
		
		//adding buttons to actionlistener
		a.addActionListener(this);
		b.addActionListener(this);
		c.addActionListener(this);
		d.addActionListener(this);
		e.addActionListener(this);
		f.addActionListener(this);
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
		six.addActionListener(this);
		seven.addActionListener(this);
		eight.addActionListener(this);
		nine.addActionListener(this);
		zero.addActionListener(this);
		equalButton.addActionListener(this);
		addButton.addActionListener(this);
		subButton.addActionListener(this);
		multButton.addActionListener(this);
		divButton.addActionListener(this);
		backButton.addActionListener(this);
		cancel.addActionListener(this);
		clear.addActionListener(this);
		mod.addActionListener(this);
		plusMinus.addActionListener(this);
		
		this.add(inPan); //add panel to frame
		pack();
	}
	
	public void actionPerformed(ActionEvent g){
		s = resultField.getText();
		
		if(g.getSource() == a){
			numCheck();
			boundsCheck("A");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == b){
			numCheck();
			boundsCheck("B");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == c){
			numCheck();
			boundsCheck("C");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == d){
			numCheck();
			boundsCheck("D");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == e){
			numCheck();
			boundsCheck("E");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == f){
			numCheck();
			boundsCheck("F");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == one){
			numCheck();
			boundsCheck("1");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == two){
			numCheck();
			boundsCheck("2");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == three){
			numCheck();
			boundsCheck("3");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == four){
			numCheck();
			boundsCheck("4");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == five){
			numCheck();
			boundsCheck("5");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == six){
			numCheck();
			boundsCheck("6");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == seven){
			numCheck();
			boundsCheck("7");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == eight){
			numCheck();
			boundsCheck("8");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == nine){
			numCheck();
			boundsCheck("9");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == zero){
			numCheck();
			if(resultField.getText().equals("0")){ //user tries to enter a line of zeros, no
				resultField.setText("");
				boundsCheck("0");
			}
			else{
				boundsCheck("0"); //there are other numbers already entered
			}
			updateBits(resultField.getText());
		}
		else if(g.getSource() == addButton){
			opCheck();
			operator = "+";
			updateBits(resultField.getText());
		}
		else if(g.getSource() == subButton){
			opCheck();
			operator = "-";
			updateBits(resultField.getText());
		}
		else if(g.getSource() == multButton){
			opCheck();
			operator = "*";
			updateBits(resultField.getText());
		}
		else if(g.getSource() == divButton){
			opCheck();
			operator = "/";
			updateBits(resultField.getText());
		}
		else if(g.getSource() == equalButton){
			if(resultField.getText().equals("Cannot divide by zero")||resultField.getText().equals("Out of Bounds")){
				return;
			}
			else if(!operator.isEmpty() && !numPushed){ //if an operation is pushed and then equalButton is pushed immediately afterwards
				return;
			}
			
			right = Long.parseLong(resultField.getText(), base);
			
			if(!operator.isEmpty()){
				if(operator.equals("/") && right == 0){ //division by zero
					resultField.setText("Cannot divide by zero");
					updateBits("0");
				}
				else if(operator.equals("%") && right == 0){ //modulus by zero
					resultField.setText("Error");
					updateBits("0");
				}
				else{
					if(dec.isSelected()){
						resultField.setText(Long.toString(equate(left, right, operator)));
					}
					else if(bin.isSelected()){
						resultField.setText(Long.toBinaryString(equate(left, right, operator)));
					}
					else if(oct.isSelected()){
						resultField.setText(Long.toOctalString(equate(left, right, operator)));
					}
					else if(hex.isSelected()){
						resultField.setText(Long.toHexString(equate(left, right, operator)));
					}
					updateBits(resultField.getText());
					left = equate(left, right, operator);
				}
			}
			operator = "";
			saver = "";
			justSolved = true;
		}
		else if(g.getSource() == clear){
			s = "";
			saver = "";
			left = 0;
			right = 0;
			operator = "";
			justSolved = false;
			resultField.setText("0");
			updateBits(resultField.getText());
		}
		else if(g.getSource() == cancel){
			resultField.setText("0");
			updateBits("0");
		}
		else if(g.getSource() ==backButton){
			s = resultField.getText();
			if(s.equals("Out of Bounds")||s.equals("Cannot divide by zero")){
				resultField.setText("0");
				return;
			}
			if(s.isEmpty()){
				resultField.setText("0");
				return;
			}
			else if(s.length() == 1){
				resultField.setText("0");
				s = "0";
			}
			else{
				s = s.substring(0, s.length() - 1);
				resultField.setText(s);
			}
			updateBits(resultField.getText());
		}
		else if(g.getSource() == plusMinus){
			if(resultField.getText().equals("Out of Bounds") ||resultField.getText().equals("Cannot divide by zero")){
				return;
			}
			String text = resultField.getText();
			Long num;
			if(base == 10){
				num = -1 * Long.parseLong(text);
				resultField.setText(Long.toString(num));
			}
			else if(base == 2){
				num = -1 * Long.parseLong(text, 2);
				resultField.setText(Long.toBinaryString(num));
			}
			else if(base == 8){
				num = -1 * Long.parseLong(text, 8);
				resultField.setText(Long.toOctalString(num));
			}
			else if(base == 16){
				num = -1 * Long.parseLong(text, 16);
				resultField.setText(Long.toHexString(num));
			}
			updateBits(resultField.getText());
		}
		else if(g.getSource() == mod){
			opCheck();
			operator = "%";
			updateBits(resultField.getText());
		}
		else if(g.getSource() == dec || g.getSource() == bin || g.getSource() == oct || g.getSource() == hex){//conversions
			if(g.getSource() == dec){
				if(base == 10){
					
				}
				else if(base == 2){
					resultField.setText(Long.toString(Long.parseLong(resultField.getText(), 2)));
					bin.setSelected(false);
				}
				else if(base == 8){
					resultField.setText(Long.toString(Long.parseLong(resultField.getText(), 8)));
					oct.setSelected(false);
				}
				else if(base == 16){
					resultField.setText(Long.toString(Long.parseLong(resultField.getText(), 16)));
					hex.setSelected(false);
				}
				dec.setSelected(true);
				base = 10; //set new base
				a.setEnabled(false); //enable/disable buttons according to base
				b.setEnabled(false);
				c.setEnabled(false);
				d.setEnabled(false);
				e.setEnabled(false);
				f.setEnabled(false);
				zero.setEnabled(true);
				one.setEnabled(true);
				two.setEnabled(true);
				three.setEnabled(true);
				four.setEnabled(true);
				five.setEnabled(true);
				six.setEnabled(true);
				seven.setEnabled(true);
				eight.setEnabled(true);
				nine.setEnabled(true);
				updateBits(resultField.getText());
			}
			else if(g.getSource() == bin){
				if(base == 10){
					resultField.setText(Long.toBinaryString(Long.parseLong(resultField.getText())));
					oct.setSelected(false);
				}
				else if(base == 2){
					
				}
				else if(base == 8){
					resultField.setText(Long.toBinaryString(Long.parseLong(resultField.getText(), 8)));
					oct.setSelected(false);
				}
				else if(base == 16){
					resultField.setText(Long.toBinaryString(Long.parseLong(resultField.getText(), 16)));
					hex.setSelected(false);
				}
				bin.setSelected(true);
				base = 2;
				a.setEnabled(false);
				b.setEnabled(false);
				c.setEnabled(false);
				d.setEnabled(false);
				e.setEnabled(false);
				f.setEnabled(false);
				zero.setEnabled(true);
				one.setEnabled(true);
				two.setEnabled(false);
				three.setEnabled(false);
				four.setEnabled(false);
				five.setEnabled(false);
				six.setEnabled(false);
				seven.setEnabled(false);
				eight.setEnabled(false);
				nine.setEnabled(false);
				updateBits(resultField.getText());
			}
			else if(g.getSource() == oct){
				if(base == 10){
					resultField.setText(Long.toOctalString(Long.parseLong(resultField.getText())));
					dec.setSelected(false);
				}
				else if(base == 2){
					resultField.setText(Long.toOctalString(Long.parseLong(resultField.getText(), 2)));
					bin.setSelected(false);
				}
				else if(base == 8){
		
				}
				else if(base == 16){
					resultField.setText(Long.toOctalString(Long.parseLong(resultField.getText(), 16)));
					hex.setSelected(false);
				}
				oct.setSelected(true);
				base = 8;
				a.setEnabled(false);
				b.setEnabled(false);
				c.setEnabled(false);
				d.setEnabled(false);
				e.setEnabled(false);
				f.setEnabled(false);
				zero.setEnabled(true);
				one.setEnabled(true);
				two.setEnabled(true);
				three.setEnabled(true);
				four.setEnabled(true);
				five.setEnabled(true);
				six.setEnabled(true);
				seven.setEnabled(true);
				eight.setEnabled(false);
				nine.setEnabled(false);
				updateBits(resultField.getText());
			}
			else if(g.getSource() == hex){
				if(base == 10){
					resultField.setText(Long.toHexString(Long.parseLong(resultField.getText())));
					dec.setSelected(false);
				}
				else if(base == 2){
					resultField.setText(Long.toHexString(Long.parseLong(resultField.getText(), 2)));
					bin.setSelected(false);
				}
				else if(base == 8){
					resultField.setText(Long.toHexString(Long.parseLong(resultField.getText(), 8)));
					oct.setSelected(false);
				}
				else if(base == 16){
			
				}
				hex.setSelected(true);
				base = 16;
				a.setEnabled(true);
				b.setEnabled(true);
				c.setEnabled(true);
				d.setEnabled(true);
				e.setEnabled(true);
				f.setEnabled(true);
				zero.setEnabled(true);
				one.setEnabled(true);
				two.setEnabled(true);
				three.setEnabled(true);
				four.setEnabled(true);
				five.setEnabled(true);
				six.setEnabled(true);
				seven.setEnabled(true);
				eight.setEnabled(true);
				nine.setEnabled(true);
				updateBits(resultField.getText());
			}
		}
		
	}	
	public long equate(long num1, long num2, String s){//performs operations
		long result = 0;
	
		if(s.equals("+")){
			result = num1 + num2;
		}
		else if(s.equals("-")){
			result = num1 - num2;
		}
		else if(s.equals("*")){
			result = num1 * num2;
		}
		else if(s.equals("/")){
			result = num1 / num2;
		}
		else if(s.equals("%")){
			result = num1 % num2;
		}
		return result;
	}
	public void numCheck(){ //list of checks that are run through everytime a number/letter button are pushed
		if(justSolved){ //old answer from last operation is still there
			resultField.setText("");
			justSolved = false;
			s = "";
		}
		else if(!operator.isEmpty() && !saver.isEmpty() && !saver.equals(" ")){ //operation has been pushed, left side of operation still in field,
			resultField.setText("");                                            //saver is changed after first button is pushed afterwards to ensure that field is not continually reset
			saver = " ";
		}
		if(resultField.getText().equals("0")){ //only zero in field
			resultField.setText("");
		}
		if(resultField.getText().equals("Out of Bounds")||resultField.getText().equals("Cannot divide by zero")){ //error message still in field
			resultField.setText("");
		}
		numPushed = true;
	}
	public void opCheck(){//list of checks that are run through everytime an operation button are pushed
		if(resultField.getText().equals("Out of Bounds")||resultField.getText().equals("Cannot divide by zero")){//error message still in field
			resultField.setText("0");
			return;
		}
		if(!numPushed){ //a number button was not pushed before, therefore the user is just pushing operation buttons continually
			return;     //old operation will still be overwritten by new operation choice
		}
		if(resultField.getText().equals("0")){ //this is to account for the user just pushing an operation without having entered anything
			left = 0;
		}
		else if(saver.isEmpty()){ //no rightside, user has just chosen left side and operation
			saver = resultField.getText();
			if(dec.isSelected()){
				left = Long.parseLong(saver);
			}
			else if(bin.isSelected()){
				left = Long.parseLong(saver, 2);
			}
			else if(oct.isSelected()){
				left = Long.parseLong(saver, 8);
			}
			else if(hex.isSelected()){
				left = Long.parseLong(saver, 16);
			}
		}
		else if(!saver.isEmpty() && !operator.isEmpty() && left != 0){ //leftside and operation chosen
			if(dec.isSelected()){
				right = Long.parseLong(resultField.getText());
				resultField.setText(Long.toString(equate(left, right, operator)));
				left = equate(left, right, operator);
				right  = 0;
			}
			else if(bin.isSelected()){
				right = Long.parseLong(resultField.getText(), 2);
				resultField.setText(Long.toBinaryString(equate(left, right, operator)));
				left = equate(left, right, operator);
				right  = 0;
			}
			else if(oct.isSelected()){
				right = Long.parseLong(resultField.getText(), 8);
				resultField.setText(Long.toOctalString(equate(left, right, operator)));
				left = equate(left, right, operator);
				right  = 0;
			}
			else if(hex.isSelected()){
				right = Long.parseLong(resultField.getText(), 16);
				resultField.setText(Long.toHexString(equate(left, right, operator)));
				left = equate(left, right, operator);
				right  = 0;
			}
			justSolved = true;
		}
		numPushed = false;
	}
	public void updateBits(String s){ //converts strings to binary strings then sends them to setBits()
		try{
			setBits(Long.toBinaryString(Long.parseLong(s, base)));
		}
		catch(NumberFormatException n){
			resultField.setText("Out of Bounds");
		}
	}
	public void setBits(String s){ //sets the bit panel
		String filler = " ";
		String filler2 = "     ";
		
		for(int i = 0; i < 5; i++){
			if(i == 3){
				filler = filler + "               ";
			}
			else{
				filler = filler + "               " + "    ";
			}
		}
		
		for(int i = 0; i < 4; i++){
				filler2 = filler2 + "               ";
		}
		
		for(int i = 0; i < (4 - (s.length() % 4)); i++){
			s = "0" + s;
		}
		
		for(int i = s.length(); i < 65; i++){
			s = "0" + s;
		}
		
		bits.setText("     " + s.substring(0, 4) + "               " + s.substring(4, 8) + "               " + s.substring(8, 12) + "               " + s.substring(12, 16) + 
				"               " + s.substring(16, 20) + "               " + s.substring(20, 24) + "               " + s.substring(24, 28) + "               " + s.substring(28, 32) + "\n" +
				"     " + "63" + filler + "47" + filler2 + "32\n" + " " +
				"    " + s.substring(32, 36) + "               " + s.substring(36, 40) + "               " + s.substring(40, 44) + "               " + s.substring(44, 48) + 
				"               " + s.substring(48, 52) + "               " + s.substring(52, 56) + "               " + s.substring(56, 60) + "               " + s.substring(60, 64) +"\n" +
				"     " + "31" + filler + "15" + filler2 + "  " + "0");
	}
	public void boundsCheck(String s){ //checks if what user enters is within bounds of longs
		s = resultField.getText() + s;
		if(dec.isSelected()){
			try{
				resultField.setText(Long.toString(Long.parseLong(s)));
			}
			catch(NumberFormatException n){
				return;
			}
		}
		else if(bin.isSelected()){
			try{
				resultField.setText(Long.toBinaryString(Long.parseLong(s, 2)));
			}
			catch(NumberFormatException n){
				return;
			}
		}
		else if(oct.isSelected()){
			try{
				resultField.setText(Long.toOctalString(Long.parseLong(s, 8)));
			}
			catch(NumberFormatException n){
				return;
			}
		}
		else if(hex.isSelected()){
			try{
				resultField.setText(Long.toHexString(Long.parseLong(s, 16)));
			}
			catch(NumberFormatException n){
				return;
			}
		}
	}
}
