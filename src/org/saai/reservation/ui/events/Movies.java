package org.saai.reservation.ui.events;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.saai.reservation.ui.dataobjects.IndividualShows;
import org.saai.reservation.ui.dataobjects.TransactionData;
import org.saai.reservation.ui.dataobjects.UserInfoResponse;

class Movies implements ActionListener {
	JFrame movieDetails = new JFrame();
	JComboBox<String> seatsDropDown = null;
	JButton backToFrame2 = new JButton();
	JButton logoutButton = new JButton("LOGOUT");
	int numberOfSeatsChosen = 0;
	UserInfoResponse userInfoResponse = null;
	TransactionData transactionData = new TransactionData();
	JButton showTimings[] = new JButton[4];

	
	
	void launchMovies(IndividualShows individualShows, UserInfoResponse userInfoResponse) {
		movieDetails = new JFrame();
		movieDetails.setVisible(true);
		movieDetails.setSize(2000, 1000);
		this.userInfoResponse = userInfoResponse;
		seatsDropDown = new JComboBox<String>();
		JPanel synopsisPanel = new JPanel();
		JPanel showDetailsPanel = new JPanel();
		JPanel navigatePanel = new JPanel();
		JPanel iconPanel = new JPanel();
		JPanel logoutPanel = new JPanel();
		
		logoutButton.addActionListener(this);
		logoutPanel.add(logoutButton);
		logoutPanel.setBackground(Color.pink);
		
		String fileName = individualShows.getShowId()+".png";
		JLabel poster = new JLabel(new ImageIcon(fileName));
		iconPanel.add(poster);
		iconPanel.setBackground(Color.pink);

		JLabel synopsisLabel = new JLabel("Synopsis:");
		synopsisLabel.setFont(new Font("Serif", Font.BOLD, 35));
		

		String synopsisContent = individualShows.getShowDescription();
		JLabel synopsis = new JLabel("<html>"+synopsisContent+"</html>");
		synopsis.setFont(new Font("Serif", Font.BOLD, 20));
		
		synopsisPanel.setLayout(new BoxLayout(synopsisPanel, BoxLayout.X_AXIS));
		synopsisPanel.add(synopsisLabel);
		synopsisPanel.add(synopsis);
		synopsisPanel.setBackground(Color.pink);

		showDetailsPanel.setLayout(new BoxLayout(showDetailsPanel, BoxLayout.Y_AXIS));

		JLabel showDetailHeadingLabel = new JLabel("Show Details:");
		showDetailHeadingLabel.setFont(new Font("Serif", Font.BOLD, 35));
		JLabel showDetailLabel[] = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			showDetailLabel[i] = new JLabel("");
			showTimings[i] = new JButton("");
		}
		StringBuilder showDetails = new StringBuilder();

		showDetails.append("Show Name: " + individualShows.getShowName());
		int l=0;
		for (int i = 0; i < individualShows.getShowDetails().size(); i++) {
			showDetails.append(" Show Date: " + individualShows.getShowDetails().get(i).getShowDate());
			showDetails.append(System.getProperty("line.separator"));
			for (int j = 0; j < individualShows.getShowDetails().get(i).getScreens().size(); j++) {
				// System.out.println("Screen ID:
				// "+individualShows.getShowDetails().get(i).getScreens().get(j).getScreenId());
				showDetails.append(
						" Screen Name: " + individualShows.getShowDetails().get(i).getScreens().get(j).getScreenName());
				for (int k = 0; k < individualShows.getShowDetails().get(i).getScreens().get(j).getShowTimings()
						.size(); k++) {
					int showTimeID = individualShows.getShowDetails().get(i).getScreens().get(j).getShowTimings().get(k)
							.getShowTimingId();
					showDetailLabel[k].setFont(new Font("Serif", Font.BOLD, 20));
					String showTime = individualShows.getShowDetails().get(i).getScreens().get(j).getShowTimings()
							.get(k).getShowTime();
					showTimings[l].setText(showTime);
					showTimings[l].setName(""+showTimeID);
					showTimings[l].addActionListener(this);
					l++;
					//showDetails.append("  Show Time: " + showTime);
					showDetails.append(" Tickets Available: " + individualShows.getShowDetails().get(i).getScreens()
							.get(j).getShowTimings().get(k).getTicketsAvailable());

				}
			}
		}
		String total = showDetails.toString();
		String showDate = "Show Date:";
		int splitIndex = total.indexOf(showDate);
		String part1 = total.substring(0, splitIndex);
		String part2 = total.substring(splitIndex, total.length());

		String part3 = "";
		String check = part2.substring(splitIndex + showDate.length());
		if (check.contains(showDate)) {
			splitIndex = part2.indexOf(showDate, part2.indexOf(showDate) + 1);
			part3 = part2.substring(splitIndex, part2.length());
			part2 = part2.substring(0, splitIndex);
		}

		showDetailLabel[0].setText(part1);
		showDetailLabel[1].setText(part2);
		showDetailLabel[2].setText(part3);

		showDetailsPanel.add(showDetailHeadingLabel);
		showDetailsPanel.add(showDetailLabel[0]);


		showDetailsPanel.add(showDetailLabel[1]);
		showDetailsPanel.add(showTimings[0]);
		if (part3 != "")
		{
			showDetailsPanel.add(showDetailLabel[2]);
			showDetailsPanel.add(showTimings[1]);
		}
		showDetailsPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		showDetailsPanel.setBackground(Color.pink);

		navigatePanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 150, 20));
		JLabel noOfSeatsLabel = new JLabel("Pick number of seats:");
		noOfSeatsLabel.setFont(new Font("Serif", Font.BOLD, 20));
		String[] noOfSeats = { "0", "1", "2", "3", "4" };
		seatsDropDown = new JComboBox<String>(noOfSeats);
		seatsDropDown.addActionListener(this);
		seatsDropDown.setVisible(true);

		backToFrame2 = new JButton("Back to choose a movie");
		backToFrame2.setPreferredSize(new Dimension(200, 50));
		backToFrame2.addActionListener(this);
		navigatePanel.add(noOfSeatsLabel);
		navigatePanel.add(seatsDropDown);
		navigatePanel.add(backToFrame2);
		navigatePanel.setBackground(Color.pink);

		movieDetails.getContentPane().add(iconPanel, BorderLayout.WEST);
		movieDetails.getContentPane().add(logoutPanel, BorderLayout.EAST);
		movieDetails.getContentPane().add(synopsisPanel, BorderLayout.NORTH);		
		movieDetails.getContentPane().add(showDetailsPanel, BorderLayout.CENTER);
		movieDetails.getContentPane().add(navigatePanel, BorderLayout.SOUTH);
		movieDetails.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backToFrame2) {
			movieDetails.setVisible(false);
			Screen2 screen2 = new Screen2();
			screen2.launchFrame2(userInfoResponse);
		} else if (e.getSource() == seatsDropDown) {
			numberOfSeatsChosen = Integer.parseInt(seatsDropDown.getSelectedItem().toString());
			transactionData.setNumberOfSeatsToBeBooked(numberOfSeatsChosen);
		} else if ((e.getSource() == showTimings[0])||(e.getSource() == showTimings[1])||(e.getSource() == showTimings[2])||(e.getSource() == showTimings[3])) {
			if(numberOfSeatsChosen==0){
				JOptionPane.showMessageDialog(null, "Choose number of Seats");
			}
			else
			{
				String source = e.getSource().toString();
				String showTimingIdString = source.substring(source.indexOf("[") + 1, source.indexOf(","));
				int showTimingId = Integer.parseInt(showTimingIdString);
				transactionData.setShowTimingId(showTimingId);
				movieDetails.setVisible(false);
				Screen3 seat = new Screen3();
				seat.launchFrame3(transactionData, userInfoResponse);
			}
		}
		else if(e.getSource()==logoutButton){
			movieDetails.setVisible(false);
			StartScreen screen1 = new StartScreen();
			screen1.launchFrame1();
		}

	}
}