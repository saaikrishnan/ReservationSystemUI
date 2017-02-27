package org.saai.reservation.ui.events;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.saai.reservation.ui.dataobjects.IndividualShows;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.dataobjects.Shows;
import org.saai.reservation.ui.dataobjects.ShowsAvailable;
import org.saai.reservation.ui.dataobjects.TransactionData;
import org.saai.reservation.ui.dataobjects.UserInfoResponse;
import org.saai.reservation.ui.services.ShowsClientService;

import com.google.gson.Gson;

class Screen2 implements MouseListener,ActionListener {

	JFrame frame2 = new JFrame();
	JButton logoutButton = new JButton("LOGOUT");
	TransactionData transactionData = new TransactionData();
	UserInfoResponse userInfoResponse = new UserInfoResponse();

	int seatCounter = 0;
	int numberOfSeatsChosen;

	void launchFrame2(UserInfoResponse userInfoResponse) {

		this.userInfoResponse = userInfoResponse;
		frame2 = new JFrame();
		frame2.setVisible(true);
		frame2.setSize(2000, 1000);
		seatCounter = 0;
		// numberOfSeatsChosen = 0;
		JPanel headingPanel = new JPanel();
		JPanel showNamesPanel = new JPanel();

		showNamesPanel.setLayout(new BoxLayout(showNamesPanel, BoxLayout.X_AXIS));

		// Heading
		JLabel movies = new JLabel("Pick a movie to watch:");
		movies.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 900));
		logoutButton.addActionListener(this);
		movies.setFont(new Font("Serif", Font.BOLD, 45));
		headingPanel.add(movies);
		headingPanel.add(logoutButton);
		headingPanel.setBackground(Color.LIGHT_GRAY);

		String response = listAllAvailableShows();
		Gson gson = new Gson();
		ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
		if (!serviceResponse.isHasErrors()) {
			ShowsAvailable showsAvailable = gson.fromJson(serviceResponse.getSuccessMsg(), ShowsAvailable.class);
			int numberOfAvailableMovies = showsAvailable.getShows().size();
			JLabel[] moviePosters = new JLabel[numberOfAvailableMovies];
			Iterator<Shows> iterate = showsAvailable.getShows().iterator();
			int i = 0;
			while (iterate.hasNext() && i < numberOfAvailableMovies) {
				Shows show = iterate.next();

				String fileName = show.getShowId() + ".png";
				moviePosters[i] = new JLabel(new ImageIcon(fileName));
				moviePosters[i].setName("" + show.getShowId());
				moviePosters[i].addMouseListener(this);
				showNamesPanel.add(moviePosters[i]);
			}
			showNamesPanel.setBackground(Color.red);


			frame2.getContentPane().add(headingPanel, BorderLayout.NORTH);
			frame2.getContentPane().add(showNamesPanel, BorderLayout.CENTER);
			frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		} else {
			String errorMessage = serviceResponse.getErrorList()[0].toString();
			JOptionPane.showMessageDialog(null, errorMessage);

		}

	}

	private String listAllAvailableShows() {
		String response = "";
		try {
			ShowsClientService showsClientService = new ShowsClientService();
			response = showsClientService.getListOfAllRunningShows();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String source = arg0.getSource().toString();
		String showIdString = source.substring(source.indexOf("[") + 1, source.indexOf(","));
		int showId = Integer.parseInt(showIdString);
		String response = getSelectedShowInformation(showId);
		Gson gson = new Gson();
		ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
		if (!serviceResponse.isHasErrors()) {
			IndividualShows individualShows = gson.fromJson(serviceResponse.getSuccessMsg(), IndividualShows.class);
			frame2.setVisible(false);
			Movies movies= new Movies();
			movies.launchMovies(individualShows, userInfoResponse);
		} else {
			String errorMessage = serviceResponse.getErrorList()[0].toString();
			JOptionPane.showMessageDialog(null, errorMessage);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}


	
	
	private String getSelectedShowInformation(int showId) {
		String response = "";
		try {
			ShowsClientService showsClientService = new ShowsClientService();
			response = showsClientService.getDetailsOfSelectedShow(showId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==logoutButton){
			frame2.setVisible(false);
			StartScreen screen1 = new StartScreen();
			screen1.launchFrame1();
		}
		
	}

}
