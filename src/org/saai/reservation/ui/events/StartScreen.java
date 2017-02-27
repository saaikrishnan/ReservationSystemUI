package org.saai.reservation.ui.events;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.saai.reservation.ui.dataobjects.Login;
import org.saai.reservation.ui.dataobjects.LoginResponse;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.dataobjects.UserInfoResponse;
import org.saai.reservation.ui.dataobjects.Users;
import org.saai.reservation.ui.services.LoginClientService;
import org.saai.reservation.ui.services.UsersClientService;

import com.google.gson.Gson;

public class StartScreen extends JFrame implements ActionListener {

	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	JFrame frame3 = new JFrame();

	JTextField usernameField = null;
	JPasswordField passwordField = null;

	JTextField firstNameField = null;
	JPasswordField newPasswordField = null;
	JTextField lastNameField = null;
	JTextField emailIDField = null;

	JButton signUpButton = null;
	JButton loginButton = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		StartScreen screen1 = new StartScreen();
		screen1.launchFrame1();

	}

	public void launchFrame1() {

		JPanel logoPanel = new JPanel();
		JPanel title1Panel = new JPanel();
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		JPanel signUpPanel = new JPanel();
		signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));
		JPanel emptyFooter = new JPanel();
		JLabel title = null;
		try {
			title = new JLabel(new ImageIcon("SPI_Cinemas_logo.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		title.setFont(new Font("Serif", Font.BOLD, 35));
		logoPanel.setSize(10, 30);
		logoPanel.add(title);
		logoPanel.setBackground(Color.white);

		JLabel set1 = new JLabel("Welcome!");
		set1.setFont(new Font("Serif", Font.BOLD, 35));
		title1Panel.setSize(40, 40);
		title1Panel.add(set1);
		title1Panel.setBackground(Color.LIGHT_GRAY);

		// login
		JLabel noteUName = new JLabel("Username");
		noteUName.setFont(new Font("Serif", Font.BOLD, 20));

		JLabel notePassword = new JLabel("Password:");
		notePassword.setFont(new Font("Serif", Font.BOLD, 20));

		usernameField = new JTextField();
		passwordField = new JPasswordField();

		loginButton = new JButton("Login");
		loginButton.setFont(new Font("Serif", Font.BOLD, 20));
		loginButton.addActionListener(this);

		// loginPanel.setMaximumSize(new Dimension(200, 300));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(250, 200, 350, 500));
		loginPanel.add(noteUName);
		loginPanel.add(usernameField);
		loginPanel.add(notePassword);
		loginPanel.add(passwordField);
		loginPanel.add(loginButton);
		loginPanel.setBackground(Color.white);

		// Sign up:

		JLabel title2 = new JLabel("New User? Sign Up:");
		title2.setFont(new Font("Serif", Font.BOLD, 35));

		JLabel fName = new JLabel("First Name:");
		fName.setFont(new Font("Serif", Font.BOLD, 20));

		JLabel lName = new JLabel("Last Name:");
		lName.setFont(new Font("Serif", Font.BOLD, 20));

		JLabel emailID = new JLabel("Email ID:");
		emailID.setFont(new Font("Serif", Font.BOLD, 20));

		JLabel newPassword = new JLabel("New Password:");
		newPassword.setFont(new Font("Serif", Font.BOLD, 20));

		firstNameField = new JTextField();
		newPasswordField = new JPasswordField();
		lastNameField = new JTextField();
		emailIDField = new JTextField();

		signUpButton = new JButton("Sign up");
		signUpButton.setFont(new Font("Serif", Font.BOLD, 25));

		signUpButton.addActionListener(this);

		signUpPanel.setBorder(BorderFactory.createEmptyBorder(100, 70, 400, 50));
		signUpPanel.add(title2);
		signUpPanel.add(fName);
		signUpPanel.add(firstNameField);
		signUpPanel.add(lName);
		signUpPanel.add(lastNameField);
		signUpPanel.add(emailID);
		signUpPanel.add(emailIDField);
		signUpPanel.add(newPassword);
		signUpPanel.add(newPasswordField);
		signUpPanel.add(signUpButton);
		signUpPanel.setBackground(Color.pink);

		frame1.setVisible(true);
		frame1.setSize(2000, 1500);
		frame1.getContentPane().add(title1Panel, BorderLayout.NORTH);
		frame1.getContentPane().add(logoPanel, BorderLayout.WEST);
		frame1.getContentPane().add(loginPanel, BorderLayout.CENTER);
		frame1.getContentPane().add(signUpPanel, BorderLayout.EAST);
		frame1.getContentPane().add(emptyFooter, BorderLayout.SOUTH);

		frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame1.setSize(screenSize.width, screenSize.height);
		frame1.setBounds(0, 0, screenSize.width, screenSize.height);

	}

	private String authenticateUser() {
		String response = "";
		try {
			Login login = new Login();
			LoginClientService loginClientService = new LoginClientService();
			login.setEmailId(usernameField.getText().toString().trim());
			login.setPassword(passwordField.getText().toString().trim());
			response = loginClientService.loginUser(login);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	private String retrieveUserDetails(String emailId) {
		String response = "";
		try {
			UsersClientService usersClientService = new UsersClientService();
			response = usersClientService.getUserInfoFromEmailId(emailId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	private boolean validateLoginEntries() {
		boolean isValid = true;
		try {
			if (usernameField.getText().isEmpty())
				isValid = false;

			if (passwordField.getPassword().length < 6)
				isValid = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isValid;
	}

	private boolean validateSignUpEntries() {
		boolean isValid = true;
		try {
			if (firstNameField.getText().length() < 6)
				isValid = false;
			if (lastNameField.getText().isEmpty())
				isValid = false;
			if (emailIDField.getText().isEmpty())
				isValid = false;
			if ((newPasswordField.getPassword().length < 6) && (newPasswordField.getPassword().length > 12))
				isValid = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isValid;
	}

	private String signUpUser() {
		String response = null;
		try {
			Users user = new Users();
			user.setFirstName(firstNameField.getText().toString().trim());
			user.setLastName(lastNameField.getText().toString().trim());
			user.setEmailId(emailIDField.getText().toString().trim());
			user.setPassword(newPasswordField.getText().toString().trim());
			UsersClientService usersClientService = new UsersClientService();
			response = usersClientService.signupUser(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			boolean isValid = false;
			isValid = validateLoginEntries();
			if (isValid) {
				String response = authenticateUser();
				Gson gson = new Gson();
				ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
				if (!serviceResponse.isHasErrors()) {
					LoginResponse loginResponse = gson.fromJson(serviceResponse.getSuccessMsg(), LoginResponse.class);
					String userDataJsonString = retrieveUserDetails(loginResponse.getEmailId());
					serviceResponse = gson.fromJson(userDataJsonString, ServiceResponse.class);
					UserInfoResponse userInfoResponse = gson.fromJson(serviceResponse.getSuccessMsg(),
							UserInfoResponse.class);
					frame1.setVisible(false);
					Screen2 screen2 = new Screen2();
					screen2.launchFrame2(userInfoResponse);
				} else {
					String errorMessage = serviceResponse.getErrorList()[0].toString();
					JOptionPane.showMessageDialog(null, errorMessage);

				}

			} else {
				JOptionPane.showMessageDialog(null, "Username/Password cannot be less than 6 characters");
			}
		} else if (e.getSource() == signUpButton) {
			boolean isValid = false;
			isValid = validateSignUpEntries();
			if (isValid) {
				String response = signUpUser();
				Gson gson = new Gson();
				ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
				if (serviceResponse.isHasErrors()) {
					String errorMessage = serviceResponse.getErrorList()[0].toString();
					JOptionPane.showMessageDialog(null, errorMessage);
				} else {
					JOptionPane.showMessageDialog(null, "Sign Up was successful, proceed to login!");
					// launchFrame1();
				}
			} else {
				JOptionPane.showMessageDialog(null, "The entered fields require a valid data");
			}
		}
	}

}
