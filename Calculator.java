import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener {
	final static int ADD = 1; //加减乘除常量
	final static int SUB = 2;
	final static int MULT = 3;
	final static int DIV = 4;
	// 屏幕
	private JTextField screen;
	// 按顺序排列的面板中的按钮
	final static String[] NUMBER_BUTTONS = { "←", "CE", "C", "+", "7", "8", "9", "-", "4",
			"5", "6", "*", "1", "2", "3", "/", "+/-", "0", ".", "=" };

	private double number1;//运算数1
	private double number2;//运算数2
	private double result;//运算结果
	private int operation;//操作符号
	
	private boolean operated = false;//判断是否已有完成的计算，用于在一次计算结束后，再次输入数据时的判断

	public Calculator() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		//初始化显示器
		screen = new JTextField("0");
		screen.setPreferredSize(new Dimension(240, 35));
		screen.setFont(new Font("", Font.PLAIN, 20));
		screen.setHorizontalAlignment(JTextField.RIGHT);
		screen.setEditable(false);
		//初始化按钮面板
		JPanel panel = new JPanel();
		GridLayout gl = new GridLayout(5, 4, 5, 5);
		panel.setLayout(gl);

		for (int i = 0; i < NUMBER_BUTTONS.length; i++) {
			JButton button = new JButton();
			button.setText(NUMBER_BUTTONS[i]);
			button.setFont(new Font("", Font.PLAIN, 15));
			button.addActionListener(this);//为每个按钮添加同一个actionlistener
			panel.add(button);
		}
		//窗体初始化
		setTitle("计算器");
		setBounds(100, 100, 250, 320);
		BorderLayout layout = new BorderLayout(5,5);
		getContentPane().setLayout(layout);
		getContentPane().add(screen, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		String s = e.getActionCommand();

		if (Character.isDigit(s.charAt(0)) || s.equals(".")) {// 如果输入为数字或"."
			//判断是否存在已完成算术，如果是，则清空屏幕内容，重新开始新运算
			if (operated) {
				screen.setText("0");
				operated = false;
			}
			if (screen.getText().equals("0")) {// 如果屏幕内容为0
				if (s.equals("0")) {
					return;
				}
				if (s.equals(".")) {
					screen.setText("0.");
				} else {
					screen.setText(s);
				}
			} else {// 屏幕内容不为0
				if (!screen.getText().contains(".")) {// 不包含"."，则添加
					screen.setText(screen.getText() + s);
				} else if (!s.equals(".")) {// 包含"."，则忽略"."
					screen.setText(screen.getText() + s);
				}
			}
		} else {// 输入非数字
			if (s.equals("+") || s.equals("-") || s.equals("*")
					|| s.equals("/")) {
				// 如果符号为加减乘除，先将屏幕上数保存为number1
				number1 = Double.parseDouble(screen.getText());
				if (s.equals("+")) {
					operation = ADD;
				}
				if (s.equals("-")) {
					operation = SUB;
				}
				if (s.equals("*")) {
					operation = MULT;
				}
				if (s.equals("/")) {
					operation = DIV;
				}
				screen.setText("0");
			} else if (s.equals("C")) {
				//如果为全部清除
				number1 = 0;
				number2 = 0;
				result = 0;
				screen.setText("0");
			} else if (s.equals("CE")) {
				//部分清除
				if (number2 == 0) {
					number1 = 0;
				} else {
					number2 = 0;
				}
				screen.setText("0");
			} else if (s.equals("←")) {
				//退格，0则不退，否则按字符串截去最后一位
				if (!screen.getText().equals("0")) {
					if (screen.getText().length() == 1) {
						screen.setText("0");
					} else {
						screen.setText(screen.getText().substring(0,
								screen.getText().length() - 1));
					}
				}
			} else if (s.equals("+/-")) {
				//正负号，为避免正负运算改变double值，直接对字符串进行添加或者删除“-”
				if (screen.getText().equals("0")) {
					return;
				}
				if (screen.getText().contains("-")) {
					screen.setText(screen.getText().substring(1));
				} else {
					screen.setText("-" + screen.getText());
				}
			} else if (s.equals("=")) {
				//等于，先将屏幕上的数值保存为number2
				number2 = Double.parseDouble(screen.getText());
				switch (operation) {//按操作运算符号常量分支
				case 1:
					result = number1 + number2;
					break;
				case 2:
					result = number1 - number2;
					break;
				case 3:
					result = number1 * number2;
					break;
				case 4:
					result = number1 / number2;
					break;
				default:
					result = number2;
				}
				Number resultObject = result;
				//如果小数部分小于1的负10次方，则判定为整数，只输出整数部分
				if ((resultObject.doubleValue() - resultObject.intValue()) < 1e-10) {
					String resultString = resultObject.intValue() + "";
					screen.setText(resultString);
				} else { //非整数则直接输出double值
					screen.setText(result + "");
				}
				number1 = result;
				number2 = 0;
				result = 0;
				operated = true;
			}
		}
	}

	public static void main(String[] args) {
		Calculator calculator = new Calculator();
		calculator.setVisible(true); // 设置窗体可见
	}
}
