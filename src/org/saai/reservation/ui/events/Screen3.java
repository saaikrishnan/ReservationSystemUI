package org.saai.reservation.ui.events;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.saai.reservation.ui.dataobjects.CartTimeoutSeats;
import org.saai.reservation.ui.dataobjects.OrderUpdate;
import org.saai.reservation.ui.dataobjects.OrdersRequest;
import org.saai.reservation.ui.dataobjects.OrdersResponse;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.dataobjects.ShowSeatsCarts;
import org.saai.reservation.ui.dataobjects.TransactionData;
import org.saai.reservation.ui.dataobjects.UserInfoResponse;
import org.saai.reservation.ui.services.OrdersClientService;
import org.saai.reservation.ui.services.ShowsClientService;

import com.google.gson.Gson;

class Screen3 implements ActionListener {

	JFrame frame3 = new JFrame();
	JButton backToFrame2 = null;
	JButton buySeats = null;
	JLabel chosenSeatLabel = null;

	int numberOfSeatsChosen = 0;
	int seatCounter = 0;
	List<Integer> seatIDs = new ArrayList<Integer>();
	int timerCount = 60;
	int showTimingId;
	TransactionData transactionData = null;
	UserInfoResponse userInfoResponse = null;
	ShowSeatsCarts showSeatsCarts = null;
	// Simply setting the seatIds here:

	void launchFrame3(TransactionData transactionData, UserInfoResponse userInfoResponse) {
		numberOfSeatsChosen = transactionData.getNumberOfSeatsToBeBooked();
		showTimingId = transactionData.getShowTimingId();
		this.transactionData = transactionData;
		this.userInfoResponse = userInfoResponse;
		seatCounter = 0;
		frame3 = new JFrame();
		frame3.setSize(2000, 1000);
		frame3.setVisible(true);

		String response = getAllSeatDetails(showTimingId);
		Gson gson = new Gson();
		ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
		if (!serviceResponse.isHasErrors()) {

			ShowSeatsCarts showSeatsCarts = gson.fromJson(serviceResponse.getSuccessMsg(), ShowSeatsCarts.class);
			this.showSeatsCarts = showSeatsCarts;
			JPanel logoPanel = new JPanel();
			logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.LINE_AXIS));
			JLabel screen = new JLabel("(Screen this way)");
			screen.setBorder(new EmptyBorder(0, 450, 0, 0));
			screen.setFont(new Font("Serif", Font.BOLD, 35));

			JLabel timerLabel = new JLabel("");
			timerLabel.setBorder(new EmptyBorder(0, 50, 0, 0));
			timerLabel.setFont(new Font("Serif", Font.BOLD, 20));

			Timer timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (timerCount <= 0) {
						((Timer) e.getSource()).stop();
						int[] seatIds = seatIDs.stream().filter(i -> i != null).mapToInt(i -> i).toArray();
						if (timerCount != -1000) {
							emptyCartAsSessionTimeOut();
							JOptionPane.showMessageDialog(null, "TimeOut");
							if (seatIds.length == 0) {
								frame3.setVisible(false);
								StartScreen screen1 = new StartScreen();
								screen1.launchFrame1();
							} else {
								CartTimeoutSeats cartTimeoutSeats = new CartTimeoutSeats();
								cartTimeoutSeats.setSeatIds(seatIds);
								String response = clearCartSeatsOnTimeOut(cartTimeoutSeats);
								Gson gson = new Gson();
								ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
								if (!serviceResponse.isHasErrors()) {
									frame3.setVisible(false);
									StartScreen screen1 = new StartScreen();
									screen1.launchFrame1();
								} else {
									String errorMessage = serviceResponse.getErrorList()[0].toString();
									JOptionPane.showMessageDialog(null, errorMessage);
								}
							}

						}
					} else {
						timerLabel.setText("Timer: " + Integer.toString(timerCount));
						timerCount--;
					}
				}

			});

			logoPanel.setBorder(BorderFactory.createEmptyBorder(70, 70, 70, 70));
			logoPanel.setBackground(Color.pink);
			logoPanel.setSize(10, 30);
			logoPanel.add(screen);
			logoPanel.add(timerLabel);

			JPanel seatHeadingPanel = new JPanel();
			seatHeadingPanel.setLayout(new BoxLayout(seatHeadingPanel, BoxLayout.Y_AXIS));
			JLabel SeatHeading = new JLabel("Choose your seats");
			SeatHeading.setBorder(BorderFactory.createEmptyBorder(45, 40, 100, 40));
			SeatHeading.setFont(new Font("Serif", Font.BOLD, 30));
			seatHeadingPanel.setBackground(Color.pink);
			seatHeadingPanel.add(SeatHeading);

			JPanel rows = new JPanel();
			rows.setLayout(new FlowLayout());
			SeatsPane seats = new SeatsPane();
			rows.add(seats);
			// rows.setBorder(BorderFactory.createEmptyBorder(20, 100, 400,
			// 100));
			rows.setBorder(BorderFactory.createEmptyBorder(20, 70, 400, 70));
			rows.setBackground(Color.pink);

			JPanel selectedSeatsPanel = new JPanel();
			selectedSeatsPanel.setBorder(BorderFactory.createEmptyBorder(45, 30, 150, 40));
			selectedSeatsPanel.setLayout(new BoxLayout(selectedSeatsPanel, BoxLayout.Y_AXIS));

			JLabel selectedSeats = new JLabel("Selected seats");
			selectedSeats.setFont(new Font("Serif", Font.BOLD, 30));
			chosenSeatLabel = new JLabel();
			chosenSeatLabel.setFont(new Font("Serif", Font.BOLD, 15));

			selectedSeatsPanel.setBackground(Color.pink);
			selectedSeatsPanel.add(selectedSeats);
			selectedSeatsPanel.add(chosenSeatLabel);

			JPanel move = new JPanel();
			move.setBorder(BorderFactory.createEmptyBorder(100, 20, 100, 20));
			move.setBackground(Color.pink);
			backToFrame2 = new JButton("<- Back to choose a movie");
			// backToFrame2.setSize(new Dimension(100, 100));
			backToFrame2.setPreferredSize(new Dimension(200, 50));
			backToFrame2.addActionListener(this);
			buySeats = new JButton("Next to confirm plan ->");
			buySeats.setPreferredSize(new Dimension(200, 50));
			buySeats.addActionListener(this);
			move.add(backToFrame2);
			move.add(buySeats);

			frame3.getContentPane().add(logoPanel, BorderLayout.NORTH);
			frame3.getContentPane().add(seatHeadingPanel, BorderLayout.WEST);
			frame3.getContentPane().add(rows, BorderLayout.CENTER);
			frame3.getContentPane().add(move, BorderLayout.SOUTH);
			frame3.getContentPane().add(selectedSeatsPanel, BorderLayout.EAST);
			frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			// invalidationTimer.setRepeats(false);
			// invalidationTimer.restart();
			timer.start();
		} else {
			String errorMessage = serviceResponse.getErrorList()[0].toString();
			JOptionPane.showMessageDialog(null, errorMessage);
		}
	}

	public String clearCartSeatsOnTimeOut(CartTimeoutSeats cartTimeoutSeats) {
		String response = "";
		try {
			ShowsClientService showsClientService = new ShowsClientService();
			response = showsClientService.clearTimedOutSeatSelections(cartTimeoutSeats);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public class SeatsPane extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SeatsPane() {
			setLayout(new GridBagLayout());
			Map<String, String> map = new HashMap<String, String>();
			map.put("1", "A");
			map.put("2", "B");
			map.put("3", "C");
			map.put("4", "D");
			map.put("5", "E");

			int i = 0;

			GridBagConstraints gbc = new GridBagConstraints();
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 20; col++) {
					gbc.gridx = col;
					gbc.gridy = row;

					CellPane cellPane = new CellPane();
					int rownum = 1 + row;
					int colnum = 1 + col;
					// cellPane.setName(map.get(Integer.toString(rownum)) +
					// colnum);
					// change this set name value to the PSID value

					cellPane.setName(String.valueOf(showSeatsCarts.getSeats().get(i).getSeatId()));

					// Integer.parseInt(cellPane.getName());
					cellPane.setToolTipText(map.get(Integer.toString(rownum)) + colnum);
					Border border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
					cellPane.setBorder(border);
					// Have a if condition to check the status of cart and set
					// colour
					if (showSeatsCarts.getSeats().get(i).getCartStatus() == 1) {
						cellPane.setBackground(Color.RED);
					} else {
						cellPane.setBackground(Color.WHITE);
					}
					i++;
					add(cellPane, gbc);
				}
			}
		}
	}

	public class CellPane extends JPanel {
		/**
		 * 
		 */

		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		private Color defaultBackground;

		public CellPane() {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					timerCount = 60;
					String response = "";
					Color currentColor = getBackground();
					String source = e.getSource().toString();
					String seatName = source.substring(source.indexOf("[") + 1, source.indexOf(","));

					int cartStatus = 0;
					if (currentColor == Color.green) {
						setBackground(Color.white);
						cartStatus = 0;
						response = updateCartAndHoldSeatsForOtherUsers(seatName, cartStatus);
						Gson gson = new Gson();
						ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
						if (!serviceResponse.isHasErrors()) {
							seatIDs.remove(seatIDs.indexOf(Integer.parseInt(seatName)));
							--seatCounter;
							System.out.println(seatIDs.toString());
							chosenSeatLabel.setText(seatIDs.toString());
						} else {
							String errorMessage = serviceResponse.getErrorList()[0].toString();
							JOptionPane.showMessageDialog(null, errorMessage);
						}
					}

					if ((currentColor == Color.white) && (seatCounter < numberOfSeatsChosen)) {

						cartStatus = 1;
						response = updateCartAndHoldSeatsForOtherUsers(seatName, cartStatus);
						Gson gson = new Gson();
						ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
						if (!serviceResponse.isHasErrors()) {
							++seatCounter;
							setBackground(Color.green);
							seatIDs.add(Integer.parseInt(seatName));
							chosenSeatLabel.setText(seatIDs.toString());
						} else {
							setBackground(Color.red);
							String errorMessage = serviceResponse.getErrorList()[0].toString();
							JOptionPane.showMessageDialog(null, errorMessage);
						}
					}

				}

			});
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(40, 40);
		}

		public String updateCartAndHoldSeatsForOtherUsers(String seatName, int cartStatus) {

			String response = "";
			try {
				ShowsClientService showsClientService = new ShowsClientService();
				response = showsClientService.updateCartStatusOfSeat(seatName, cartStatus);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return response;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timerCount = 60;
		if (e.getSource() == backToFrame2) {
			timerCount = -1000;
			int[] seatIds = seatIDs.stream().filter(i -> i != null).mapToInt(i -> i).toArray();

			if (seatIds.length == 0) {
				frame3.setVisible(false);
				Screen2 screen2 = new Screen2();
				screen2.launchFrame2(userInfoResponse);
			} else {
				CartTimeoutSeats cartTimeoutSeats = new CartTimeoutSeats();
				cartTimeoutSeats.setSeatIds(seatIds);
				String response = clearCartSeatsOnTimeOut(cartTimeoutSeats);
				Gson gson = new Gson();
				ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
				if (!serviceResponse.isHasErrors()) {
					frame3.setVisible(false);
					Screen2 screen2 = new Screen2();
					screen2.launchFrame2(userInfoResponse);
				} else {
					String errorMessage = serviceResponse.getErrorList()[0].toString();
					JOptionPane.showMessageDialog(null, errorMessage);
				}
			}
		} else if (e.getSource() == buySeats) {
			{
				if (seatCounter < numberOfSeatsChosen) {
					JOptionPane.showMessageDialog(null, "Select " + numberOfSeatsChosen + " seats to proceed!");
				} else {
					timerCount = -1000;
					frame3.setVisible(false);
					Screen4 screen4 = new Screen4();
					int[] seatIds = seatIDs.stream().filter(i -> i != null).mapToInt(i -> i).toArray();
					if (transactionData.isTransactionReselectSeats()) {
						int orderId = transactionData.getOrderId();
						String response = updateOrder(seatIds, orderId);
						Gson gson = new Gson();
						ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
						if (!serviceResponse.isHasErrors()) {
							screen4.launchFrame4(transactionData, userInfoResponse);
						} else {
							String errorMessage = serviceResponse.getErrorList()[0].toString();
							JOptionPane.showMessageDialog(null, errorMessage);
						}
					} else {
						String response = createOrder(seatIds);
						Gson gson = new Gson();
						ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
						if (!serviceResponse.isHasErrors()) {
							OrdersResponse ordersResponse = gson.fromJson(serviceResponse.getSuccessMsg(),
									OrdersResponse.class);
							transactionData.setOrderId(ordersResponse.getOrderId());
							screen4.launchFrame4(transactionData, userInfoResponse);
						} else {
							String errorMessage = serviceResponse.getErrorList()[0].toString();
							JOptionPane.showMessageDialog(null, errorMessage);
						}
					}

				}
			}
		}
	}

	private String getAllSeatDetails(int showTimingId) {
		String response = "";
		try {
			ShowsClientService showsClientService = new ShowsClientService();
			response = showsClientService.listAllSeatsByShowTimingId(showTimingId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	private String updateOrder(int[] seatIds, int orderId) {
		String response = "";
		try {
			OrderUpdate orderUpdate = new OrderUpdate();
			OrdersClientService ordersClientService = new OrdersClientService();
			orderUpdate.setSeatIds(seatIds);
			response = ordersClientService.updateExistingOrder(orderUpdate, orderId);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	private String createOrder(int[] seatIds) {
		String response = "";
		try {
			int seatsCount = numberOfSeatsChosen;
			OrdersRequest ordersRequest = new OrdersRequest();
			OrdersClientService ordersClientService = new OrdersClientService();
			ordersRequest.setShowTimingId(showTimingId);
			ordersRequest.setUuid(userInfoResponse.getUuid());
			ordersRequest.setSeatIds(seatIds);
			response = ordersClientService.createNewOrder(ordersRequest, seatsCount);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public void emptyCartAsSessionTimeOut() {
		// Reference: seatIDs
	}
}
