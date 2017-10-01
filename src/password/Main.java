package password;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.CodeSource;
//import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.namespace.QName;
import javax.xml.soap.Text;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField passCounter;
	private JTextField passLenght;
	private JTextField symbolsText;
	private JTextField input;

	private JCheckBox chckbxLowerCase;
	private JCheckBox chckbxUperCase;
	private JCheckBox chckbxNumbers;
	private JCheckBox chckbxSymbols;
	private JCheckBox chckbxUniqueCharacters;
	private JCheckBox chckbxSimilarCharacters;

	private ButtonGroup buttonGroup1;

	private JLabel labelPasswords;
	private JLabel lblLenght;

	private JButton button_0;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton btnGenerate;
	private int counter = 0;
	private final String DEFAULT_PASSWORD_SYMBOLS = "\'`\"!?,.:;$%&@~#()<>{}[]_*-+^=/|\\";
	private int limitedPassCounter = 1000000;
	PrintWriter writer;
	File jarFile;
	private Map<Integer, ArrayList<String>> hotKeyList = new HashMap<>();
	{

		try {
			// file = new
			// File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
			jarFile = new File(codeSource.getLocation().toURI().getPath());

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();

					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public Main() {

		initUI();
		initHotKeyList();

		addWindowListener(new WindowAdapter() {

			public void windowOpened(WindowEvent e) {
				input.requestFocus();
			}
		});

		input.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				calcGenerator();
				if ((arg0.getKeyChar() >= 'a' && arg0.getKeyChar() <= 'z')
						|| (arg0.getKeyChar() >= 'A' && arg0.getKeyChar() <= 'Z')) {

					checkChar(input, arg0);
				} else {
					input.setText("");
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});
		passCounter.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				calcGenerator();
				checkChar(passCounter, e);
				method2(1, passCounter, e);
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		symbolsText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				calcGenerator();
				checkChar(symbolsText, arg0);

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});
		passLenght.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				calcGenerator();
				checkChar(passLenght, e);
				method2(2, passLenght, e);
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		chckbxUniqueCharacters.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				if (chckbxUniqueCharacters.isSelected()) {
					int length = calcGenerator().checkString().length();
					String text = passLenght.getText();
					int parseInt = Integer.parseInt(text);
					if (parseInt > length) {
						passLenght.setText(length + "");
					}
				}

			}
		});
		chckbxLowerCase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calcGenerator();
			}
		});
		chckbxUperCase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calcGenerator();
			}
		});
		chckbxNumbers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calcGenerator();
			}
		});
		chckbxSymbols.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calcGenerator();
			}
		});

		button_0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calcGenerator();
				method(0, passCounter);
			}

		});
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calcGenerator();

				method(2, passCounter);
			}
		});

		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calcGenerator();

				method(1, passLenght);
			}
		});
		button_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calcGenerator();

				method(3, passLenght);
			}
		});

		btnGenerate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				PasswordGenerator generator = calcGenerator();

				PassList list = new PassList();
				try {
					// String FORMAT = "%02d_%02d_%02d";
					// String time = String.format(FORMAT,
					// TimeUnit.MILLISECONDS.toHours(milliseconds),
					// TimeUnit.MILLISECONDS.toMinutes(milliseconds)
					// - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
					// TimeUnit.MILLISECONDS.toSeconds(milliseconds)
					// - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
					String jarDir = jarFile.getParentFile().getPath();
					// writer = new PrintWriter(jarDir + "\\" +
					// DateFormat.getDateInstance().format(now)+ " " +
					// DateFormat.getTimeInstance().format(now) + ".txt", "UTF-8");
					// String d = DateFormat.getInstance().format(now).toString().replaceAll("/",
					// "_");

					// String name = DateFormat.getDateTimeInstance().format(now).replace(':',
					// ';').replace(':', ';')
					// .replace(':', ';') + ";" + (milliseconds + "").substring((milliseconds +
					// "").length() - 3);

					writer = new PrintWriter(jarDir + "\\" + generateName() + ".txt", "UTF-8");

					if (generator.checkString().length() > 0) {
						ArrayList<String> generatePassword = generator.GeneratePassword();
						if (Integer.parseInt(passCounter.getText()) > 0) {
							if (Integer.parseInt(passLenght.getText()) > 0) {

								if (generatePassword.size() > 0) {

									list.setPassList(generatePassword);
									TablePassword buttonDemo = new TablePassword();

									for (String string : generatePassword) {
										writer.println(string);
									}
								}
							} else {
								passLenght.setText("20");
								btnGenerate.doClick();
							}
						} else {
							passCounter.setText("1");
							btnGenerate.doClick();
						}
					}
					writer.close();

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

	}

	private String generateName() {

		String name = DateFormat.getDateTimeInstance().format(new Date()).replaceAll(":", ";");
		String mili = System.currentTimeMillis() + "";
		String substring = name.substring(0, name.length() - 3);
		substring += ";" + mili.substring(mili.length() - 3);
		substring += " " + name.substring(substring.length() - 3);
		name = substring;
		if (name.contains("PM")) {
			name = name.substring(0, name.length() - 3);
			String[] split = name.split(" ");
			for (int i = 0; i < split.length; i++) {
				if (split[i].contains(";")) {
					String[] split2 = split[i].split(";");
					int parseInt = Integer.parseInt(split2[0]);
					if (parseInt >= 1) {
						if ((name.contains(" " + parseInt + ";"))) {
							name = name.replace(" " + parseInt + ";", " " + (parseInt += 12) + ";");
						}
					}
				}

			}

		} else if (name.contains("AM")) {
			name = name.substring(0, name.length() - 3);
		}
		return name;
	}

	private void initUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();

		setResizable(false);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		chckbxLowerCase = new JCheckBox("Lower case");
		chckbxLowerCase.setSelected(true);

		chckbxUperCase = new JCheckBox("Uper case");
		chckbxUperCase.setSelected(true);

		chckbxNumbers = new JCheckBox("Numbers");
		chckbxNumbers.setSelected(true);

		chckbxSymbols = new JCheckBox("Symbols");

		chckbxUniqueCharacters = new JCheckBox("Unique characters");
		chckbxUniqueCharacters.setSelected(true);

		chckbxSimilarCharacters = new JCheckBox("Similar Characters");

		buttonGroup1 = new ButtonGroup();

		labelPasswords = new JLabel("Passwords:");

		lblLenght = new JLabel("Lenght:");

		button_0 = new JButton("-");

		button_1 = new JButton("-");

		button_2 = new JButton("+");

		button_3 = new JButton("+");

		btnGenerate = new JButton("GENERATE");

		int def = 20;
		int s = 110;

		JLabel g = new JLabel("g: generate");
		g.setBounds(6, s, 146, 23);
		contentPane.add(g);

		JLabel i = new JLabel("i: input");
		i.setBounds(6, (s += def), 146, 23);
		contentPane.add(i);

		JLabel l = new JLabel("l: length field");
		l.setBounds(6, (s += def), 146, 23);
		contentPane.add(l);

		JLabel p = new JLabel("p: passwords field");
		p.setBounds(6, (s += def), 146, 23);
		contentPane.add(p);

		JLabel jLabel4 = new JLabel("s: symbol text");
		jLabel4.setBounds(6, (s += def), 146, 23);
		contentPane.add(jLabel4);

		JLabel ch = new JLabel("ch: switch between Similar or unique Characters ");
		ch.setBounds(6, s + def + def + 12, 300, 23);
		contentPane.add(ch);

		s = 90;
		JLabel cl = new JLabel("cl: choose lower case");
		cl.setBounds(300, (s += def), 146, 23);
		contentPane.add(cl);

		JLabel cu = new JLabel("cu: choose uper case");
		cu.setBounds(300, (s += def), 146, 23);
		contentPane.add(cu);

		JLabel cn = new JLabel("cn: choose numbers");
		cn.setBounds(300, (s += def), 146, 23);
		contentPane.add(cn);

		JLabel cs = new JLabel("cs: choose symbole");
		cs.setBounds(300, (s += def), 146, 23);
		contentPane.add(cs);

		chckbxLowerCase.setBounds(6, 7, 97, 23);
		contentPane.add(chckbxLowerCase);

		chckbxUperCase.setBounds(105, 7, 97, 23);
		contentPane.add(chckbxUperCase);

		chckbxNumbers.setBounds(204, 7, 97, 23);
		contentPane.add(chckbxNumbers);

		chckbxSymbols.setBounds(6, 33, 83, 23);
		contentPane.add(chckbxSymbols);

		chckbxUniqueCharacters.setBounds(6, 59, 146, 23);
		contentPane.add(chckbxUniqueCharacters);

		chckbxSimilarCharacters.setBounds(6, 85, 146, 23);
		contentPane.add(chckbxSimilarCharacters);

		buttonGroup1.add(chckbxSimilarCharacters);
		buttonGroup1.add(chckbxUniqueCharacters);

		labelPasswords.setBounds(158, 55, 74, 30);
		contentPane.add(labelPasswords);

		lblLenght.setBounds(158, 85, 44, 23);
		contentPane.add(lblLenght);

		passCounter = new JTextField();
		passCounter.setText("1");
		passCounter.setBounds(233, 60, 86, 20);
		contentPane.add(passCounter);
		passCounter.setColumns(10);

		passLenght = new JTextField();
		passLenght.setText("20");
		passLenght.setBounds(212, 86, 107, 20);
		contentPane.add(passLenght);
		passLenght.setColumns(10);

		button_0.setBounds(330, 59, 47, 23);
		contentPane.add(button_0);

		button_1.setBounds(330, 85, 47, 23);
		contentPane.add(button_1);

		button_2.setBounds(387, 59, 47, 23);
		contentPane.add(button_2);

		button_3.setBounds(387, 85, 47, 23);
		contentPane.add(button_3);

		btnGenerate.setBounds(146, 135, 141, 41);
		contentPane.add(btnGenerate);

		symbolsText = new JTextField(DEFAULT_PASSWORD_SYMBOLS);
		symbolsText.setBounds(95, 34, 339, 20);
		symbolsText.setColumns(10);
		contentPane.add(symbolsText);

		input = new JTextField("");
		input.setBounds(146, 200, 141, 41);
		input.setColumns(10);
		contentPane.add(input);
	}

	private void initHotKeyList() {
		hotKeyList.put(1, new ArrayList<>(Arrays.asList("n", "N")));
		hotKeyList.put(2, new ArrayList<>(Arrays.asList("u", "U")));
		hotKeyList.put(3, new ArrayList<>(Arrays.asList("c", "C")));
		hotKeyList.put(4, new ArrayList<>(Arrays.asList("h", "H")));
		hotKeyList.put(5, new ArrayList<>(Arrays.asList("l", "L")));
		hotKeyList.put(6, new ArrayList<>(Arrays.asList("p", "P")));
		hotKeyList.put(7, new ArrayList<>(Arrays.asList("g", "G")));
		hotKeyList.put(8, new ArrayList<>(Arrays.asList("s", "S")));
		hotKeyList.put(9, new ArrayList<>(Arrays.asList("i", "I")));
	}

	private PasswordGenerator calcGenerator() {
		PasswordGenerator generator = new PasswordGenerator();

		String text = passLenght.getText();
		String text2 = passCounter.getText();

		int parseInt = 20;
		int parseInt2 = 1;

		try {
			if (text.length() < 1) {
				text = 0 + "";
				passLenght.setText(text);
			}
			parseInt = Integer.parseInt(getNumber(text));
		} catch (Exception e) {
			passLenght.setText("20");
			return calcGenerator();
		}
		try {
			if (text2.length() < 1) {
				text2 = 0 + "";
				passCounter.setText(text2);
			}
			parseInt2 = Integer.parseInt(getNumber(text2));
		} catch (Exception e) {
			passCounter.setText("1");
			return calcGenerator();
		}

		generator.setLenght(parseInt);
		generator.setNum(parseInt2);

		generator.isSimilarCharacters(chckbxSimilarCharacters.isSelected());
		generator.isSymbols(chckbxSymbols.isSelected());
		generator.isNumbers(chckbxNumbers.isSelected());
		generator.isUperCase(chckbxUperCase.isSelected());
		generator.isLowerCase(chckbxLowerCase.isSelected());
		generator.setSymbol(symbolsText.getText());

		return generator;
	}

	private String getNumber(String text) {
		String res = "";
		for (char c : text.toCharArray()) {
			if (c >= '0' && c <= '9') {
				res = res + c;
			}
		}
		return res;
	}

	private void method2(int i, JTextField textField, KeyEvent e) {
		char keyChar = e != null ? e.getKeyChar() : ' ';
		String text = textField.getText();

		if (keyChar <= '9' && keyChar >= '0') {
			int parseInt = Integer.parseInt(getNumber(text));
			if (parseInt < 1) {
				parseInt = 1;
			}

			switch (i) {
			case 1:

				if (parseInt > limitedPassCounter) {
					parseInt = limitedPassCounter;
				}

				break;
			case 2:
				if (chckbxUniqueCharacters.isSelected()) {
					int length = calcGenerator().checkString().length();
					if (parseInt > length) {
						parseInt = length;
					}

				} else {
					if (parseInt > limitedPassCounter) {
						parseInt = limitedPassCounter;
					}
				}

				break;

			}

			textField.setText(parseInt + "");
			return;
		}

		{
			String res = "";
			for (char c : text.toCharArray()) {
				if (c <= '9' && c >= '0') {
					res += c + "";
				}
			}
			textField.setText(res);
		}

	}

	private void method(int i, JTextField counter) {

		String passCounterText = counter.getText();

		int parseInt = Integer.parseInt(passCounterText);

		switch (i) {
		case 0:
		case 1:
			if (parseInt > 1) {

				parseInt--;
			}

			break;
		case 2:
			if (parseInt < limitedPassCounter) {

				parseInt++;
			}

			break;
		case 3:
			if (parseInt < calcGenerator().checkString().length()) {
				parseInt++;

			}

			break;

		}
		if (parseInt < 1) {
			parseInt = 1;
		}
		counter.setText(parseInt + "");

	}

	void focus(Object object) {
		String string = object.getClass().toString().toString();
		if (string.contains("JCheckBox")) {

			((AbstractButton) object).doClick();

		} else if (string.contains("JButton")) {

			((AbstractButton) object).doClick();

		} else if (string.contains("JTextField")) {
			((Component) object).requestFocus();

		}
		input.setText("");
	}

	private boolean hotKeys(JTextField textField) {
		boolean b = false;
		String text = textField.getText();

		ff: for (Integer i : hotKeyList.keySet()) {
			ArrayList<String> arrayList = hotKeyList.get(i);
			for (String string : arrayList) {
				if (text.endsWith("c" + string) || text.endsWith("C" + string)) {
					clearHotKey(textField, "C");
					clearHotKey(textField, "c");
					switch (i) {
					case 1:
						clearHotKey(textField, string);
						focus(chckbxNumbers);
						b = true;

						break;

					case 2:
						clearHotKey(textField, string);
						b = true;
						focus(chckbxUperCase);
						break;
					case 3:
						break;
					case 4:
						clearHotKey(textField, string);
						b = true;
						if (chckbxSimilarCharacters.isSelected()) {
							// clearHotKey(textField, string);
							focus(chckbxUniqueCharacters);
						} else {

							focus(chckbxSimilarCharacters);
						}
						break;
					case 5:
						clearHotKey(textField, string);
						b = true;
						focus(chckbxLowerCase);
						break;
					case 8:
						clearHotKey(textField, string);
						b = true;
						focus(chckbxSymbols);
						break;

					}
				} else if (text.endsWith(string)) {

					switch (i) {
					case 3:
						b = true;
						break;
					case 5:
						clearHotKey(textField, string);
						b = true;
						focus(passLenght);
						break;
					case 6:
						clearHotKey(textField, string);
						b = true;
						focus(passCounter);
						break;
					case 7:
						clearHotKey(textField, string);
						b = true;
						focus(btnGenerate);
						break;
					case 8:
						clearHotKey(textField, string);
						b = true;
						focus(symbolsText);
						break;
					case 9:
						clearHotKey(textField, string);
						b = true;
						focus(input);
						break;

					}
					break ff;
				}

			}
		}
		return b;
	}

	private void checkChar(JTextField textField, KeyEvent arg0) {
		if ((arg0.getKeyChar() >= 'a' && arg0.getKeyChar() <= 'z')
				|| (arg0.getKeyChar() >= 'A' && arg0.getKeyChar() <= 'Z')) {

			if (!hotKeys(textField)) {
				clearHotKey(textField, arg0.getKeyChar() + "");
			}

		}

	}

	private void clearHotKey(JTextField textField, String hotkey) {
		String text = textField.getText();

		if (text.contains(hotkey)) {
			text = text.replace(hotkey, "");
			textField.setText(text);

		}

	}

}