package org.saai.reservation.ui.events;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.saai.reservation.ui.dataobjects.OrderSummaryResponse;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.dataobjects.TransactionData;
import org.saai.reservation.ui.dataobjects.UserInfoResponse;
import org.saai.reservation.ui.services.BookingsClientService;
import org.saai.reservation.ui.services.OrderSummaryClientService;
import org.saai.reservation.ui.services.OrdersClientService;

import com.google.gson.Gson;

class Screen4 implements ActionListener {
	JFrame frame4 = new JFrame();

	TransactionData transactionData = null;
	UserInfoResponse userInfoResponse = null;
	OrderSummaryResponse orderSummaryResponse = null;
	JButton confirmPlan = null;
	JButton reSelectSeats = null;
	JButton cancel = null;
	JButton logoutButton = new JButton("LOGOUT");
	int orderId = -1;
	int showTimingId;

	int numberOfSeatsChosen = 0;

	void launchFrame4(TransactionData transactionData, UserInfoResponse userInfoResponse) {
		frame4 = new JFrame();
		frame4.setSize(2000, 1000);
		frame4.setVisible(true);

		JLabel empty_ToAdjustLogOut = new JLabel("");
		empty_ToAdjustLogOut.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 900));
		JPanel logoutPanel = new JPanel();
		logoutPanel.add(logoutButton);
		logoutButton.addActionListener(this);

		orderId = transactionData.getOrderId();
		showTimingId = transactionData.getShowTimingId();
		this.transactionData = transactionData;
		this.userInfoResponse = userInfoResponse;
		JPanel orderSummaryPanel = new JPanel();
		orderSummaryPanel.setBorder(BorderFactory.createEmptyBorder(250, 350, 500, 300));
		orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS));

		String response = getOrderSummary(orderId);
		Gson gson = new Gson();
		ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
		if (!serviceResponse.isHasErrors()) {
			orderSummaryResponse = gson.fromJson(serviceResponse.getSuccessMsg(), OrderSummaryResponse.class);
			String welcomeMessage = "Hi " + orderSummaryResponse.getFirstName() + " "
					+ orderSummaryResponse.getLastName() + ",";
			JLabel welcomeLabel = new JLabel(welcomeMessage);
			welcomeLabel.setFont(new Font("Serif", Font.BOLD, 50));

			JLabel orderLabel = new JLabel("Take a look at your order!");
			orderLabel.setFont(new Font("Serif", Font.BOLD, 40));

			String movieName = "Movie Name: " + orderSummaryResponse.getShowName();
			JLabel movieLabel = new JLabel(movieName);
			movieLabel.setFont(new Font("Serif", Font.BOLD, 30));

			String showDate = "Show Date: " + orderSummaryResponse.getShowDate();
			JLabel showDateLabel = new JLabel(showDate);
			showDateLabel.setFont(new Font("Serif", Font.BOLD, 30));

			String time = "Show Time: " + orderSummaryResponse.getShowTime();
			JLabel showTimeLabel = new JLabel(time);
			showTimeLabel.setFont(new Font("Serif", Font.BOLD, 30));

			String screenName = "Screen Name: " + orderSummaryResponse.getScreenName();
			JLabel screenNameLabel = new JLabel(screenName);
			screenNameLabel.setFont(new Font("Serif", Font.BOLD, 30));

			this.numberOfSeatsChosen = orderSummaryResponse.getNumberOfSeatsBooked();
			String seats = "Total Seats: " + orderSummaryResponse.getNumberOfSeatsBooked();
			JLabel seatsCountLabel = new JLabel(seats);
			seatsCountLabel.setFont(new Font("Serif", Font.BOLD, 30));

			String seatNumbers = "Seat Numbers:"
					+ Arrays.toString(orderSummaryResponse.getSeatNumbers()).replaceAll("\\[|\\]", "");
			JLabel seatNumbersLabel = new JLabel(seatNumbers);
			seatNumbersLabel.setFont(new Font("Serif", Font.BOLD, 30));

			orderSummaryPanel.add(welcomeLabel);
			orderSummaryPanel.add(orderLabel);
			orderSummaryPanel.add(movieLabel);
			orderSummaryPanel.add(showDateLabel);
			orderSummaryPanel.add(showTimeLabel);
			orderSummaryPanel.add(screenNameLabel);
			orderSummaryPanel.add(seatsCountLabel);
			orderSummaryPanel.add(seatNumbersLabel);
			orderSummaryPanel.setBackground(Color.PINK);

			JPanel move = new JPanel();
			confirmPlan = new JButton("Confirm");
			confirmPlan.setPreferredSize(new Dimension(200, 50));
			confirmPlan.addActionListener(this);

			cancel = new JButton("Cancel");
			cancel.setPreferredSize(new Dimension(200, 50));
			cancel.addActionListener(this);

			reSelectSeats = new JButton("Reselect seats");
			reSelectSeats.setPreferredSize(new Dimension(200, 50));
			reSelectSeats.addActionListener(this);
			move.add(confirmPlan);
			move.add(cancel);
			move.add(reSelectSeats);

			frame4.getContentPane().add(logoutPanel, BorderLayout.NORTH);
			frame4.getContentPane().add(orderSummaryPanel, BorderLayout.CENTER);
			frame4.getContentPane().add(move, BorderLayout.SOUTH);
			frame4.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		} else {
			String errorMessage = serviceResponse.getErrorList()[0].toString();
			JOptionPane.showMessageDialog(null, errorMessage);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {

			String response = cancelOrder(orderId);
			Gson gson = new Gson();
			ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
			if (!serviceResponse.isHasErrors()) {
				JOptionPane.showMessageDialog(null,
						"Your Order was cancelled! Hope we could serve you better next time!!");
				{
					frame4.setVisible(false);
					Screen2 screen2 = new Screen2();
					screen2.launchFrame2(userInfoResponse);
				}
			} else {
				String errorMessage = serviceResponse.getErrorList()[0].toString();
				JOptionPane.showMessageDialog(null, errorMessage);
			}
		} else if (e.getSource() == confirmPlan) {
			String response = confirmOrder(orderId);
			Gson gson = new Gson();
			ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
			if (!serviceResponse.isHasErrors()) {
				JOptionPane.showMessageDialog(null,
						"Your tickets have been generated! Have a great movie experience at our cinemas!!");
				{
					frame4.setVisible(false);
					Screen2 screen2 = new Screen2();
					screen2.launchFrame2(userInfoResponse);
				}
			} else {
				String errorMessage = serviceResponse.getErrorList()[0].toString();
				JOptionPane.showMessageDialog(null, errorMessage);
			}
		} else if (e.getSource() == reSelectSeats) {
			transactionData.setTransactionReselectSeats(true);
			String response = releaseCartSeats(orderId);
			Gson gson = new Gson();
			ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
			if (!serviceResponse.isHasErrors()) {
				frame4.dispose();
				Screen3 screen3 = new Screen3();
				screen3.launchFrame3(transactionData, userInfoResponse);
			} else {
				String errorMessage = serviceResponse.getErrorList()[0].toString();
				JOptionPane.showMessageDialog(null, errorMessage);
			}
		} else if (e.getSource() == logoutButton) {

			String response = cancelOrder(orderId);
			Gson gson = new Gson();
			ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
			if (!serviceResponse.isHasErrors()) {
				JOptionPane.showMessageDialog(null,
						"Your Order was cancelled! Hope we could serve you better next time!!");
				{
					frame4.setVisible(false);
					StartScreen screen1 = new StartScreen();
					screen1.launchFrame1();
				}
			} else {
				String errorMessage = serviceResponse.getErrorList()[0].toString();
				JOptionPane.showMessageDialog(null, errorMessage);
			}

		}

	}

	private String confirmOrder(int orderId) {
		String response = "";
		try {
			BookingsClientService bookingsClientService = new BookingsClientService();
			response = bookingsClientService.confirmOrderAvailableForBooking(orderId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	private String cancelOrder(int orderId) {
		String response = "";
		try {
			BookingsClientService bookingsClientService = new BookingsClientService();
			response = bookingsClientService.cancelOrderAvailableForBooking(orderId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	private String getOrderSummary(int orderId) {
		String response = "";
		try {
			OrderSummaryClientService orderSummaryClientService = new OrderSummaryClientService();
			response = orderSummaryClientService.getOrderSummaryByOrderId(orderId);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	private String releaseCartSeats(int orderId) {
		String response = "";
		try {
			OrdersClientService ordersClientService = new OrdersClientService();
			response = ordersClientService.deleteCartSeats(orderId);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

}
