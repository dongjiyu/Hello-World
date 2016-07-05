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
	final static int ADD = 1; //�Ӽ��˳�����
	final static int SUB = 2;
	final static int MULT = 3;
	final static int DIV = 4;
	// ��Ļ
	private JTextField screen;
	// ��˳�����е�����еİ�ť
	final static String[] NUMBER_BUTTONS = { "��", "CE", "C", "+", "7", "8", "9", "-", "4",
			"5", "6", "*", "1", "2", "3", "/", "+/-", "0", ".", "=" };

	private double number1;//������1
	private double number2;//������2
	private double result;//������
	private int operation;//��������
	
	private boolean operated = false;//�ж��Ƿ�������ɵļ��㣬������һ�μ���������ٴ���������ʱ���ж�

	public Calculator() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		//��ʼ����ʾ��
		screen = new JTextField("0");
		screen.setPreferredSize(new Dimension(240, 35));
		screen.setFont(new Font("", Font.PLAIN, 20));
		screen.setHorizontalAlignment(JTextField.RIGHT);
		screen.setEditable(false);
		//��ʼ����ť���
		JPanel panel = new JPanel();
		GridLayout gl = new GridLayout(5, 4, 5, 5);
		panel.setLayout(gl);

		for (int i = 0; i < NUMBER_BUTTONS.length; i++) {
			JButton button = new JButton();
			button.setText(NUMBER_BUTTONS[i]);
			button.setFont(new Font("", Font.PLAIN, 15));
			button.addActionListener(this);//Ϊÿ����ť���ͬһ��actionlistener
			panel.add(button);
		}
		//�����ʼ��
		setTitle("������");
		setBounds(100, 100, 250, 320);
		BorderLayout layout = new BorderLayout(5,5);
		getContentPane().setLayout(layout);
		getContentPane().add(screen, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		String s = e.getActionCommand();

		if (Character.isDigit(s.charAt(0)) || s.equals(".")) {// �������Ϊ���ֻ�"."
			//�ж��Ƿ�������������������ǣ��������Ļ���ݣ����¿�ʼ������
			if (operated) {
				screen.setText("0");
				operated = false;
			}
			if (screen.getText().equals("0")) {// �����Ļ����Ϊ0
				if (s.equals("0")) {
					return;
				}
				if (s.equals(".")) {
					screen.setText("0.");
				} else {
					screen.setText(s);
				}
			} else {// ��Ļ���ݲ�Ϊ0
				if (!screen.getText().contains(".")) {// ������"."�������
					screen.setText(screen.getText() + s);
				} else if (!s.equals(".")) {// ����"."�������"."
					screen.setText(screen.getText() + s);
				}
			}
		} else {// ���������
			if (s.equals("+") || s.equals("-") || s.equals("*")
					|| s.equals("/")) {
				// �������Ϊ�Ӽ��˳����Ƚ���Ļ��������Ϊnumber1
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
				//���Ϊȫ�����
				number1 = 0;
				number2 = 0;
				result = 0;
				screen.setText("0");
			} else if (s.equals("CE")) {
				//�������
				if (number2 == 0) {
					number1 = 0;
				} else {
					number2 = 0;
				}
				screen.setText("0");
			} else if (s.equals("��")) {
				//�˸�0���ˣ������ַ�����ȥ���һλ
				if (!screen.getText().equals("0")) {
					if (screen.getText().length() == 1) {
						screen.setText("0");
					} else {
						screen.setText(screen.getText().substring(0,
								screen.getText().length() - 1));
					}
				}
			} else if (s.equals("+/-")) {
				//�����ţ�Ϊ������������ı�doubleֵ��ֱ�Ӷ��ַ���������ӻ���ɾ����-��
				if (screen.getText().equals("0")) {
					return;
				}
				if (screen.getText().contains("-")) {
					screen.setText(screen.getText().substring(1));
				} else {
					screen.setText("-" + screen.getText());
				}
			} else if (s.equals("=")) {
				//���ڣ��Ƚ���Ļ�ϵ���ֵ����Ϊnumber2
				number2 = Double.parseDouble(screen.getText());
				switch (operation) {//������������ų�����֧
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
				//���С������С��1�ĸ�10�η������ж�Ϊ������ֻ�����������
				if ((resultObject.doubleValue() - resultObject.intValue()) < 1e-10) {
					String resultString = resultObject.intValue() + "";
					screen.setText(resultString);
				} else { //��������ֱ�����doubleֵ
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
		calculator.setVisible(true); // ���ô���ɼ�
	}
}
