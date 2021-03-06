package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Payment;
import com.revature.session.Session;
import com.revature.utils.DBConnection;
import com.revature.utils.LogUtil;

public class PaymentOracle implements PaymentDAO {

	private static Logger log = Logger.getLogger(PaymentOracle.class);
	private static DBConnection dbConnection = DBConnection.getDBConnection();

	@Override
	public List<Payment> getPayments() {

		String getPaymentsSQL = "SELECT * FROM ALL_PAYMENTS";

		List<Payment> payments = new ArrayList<Payment>();

		try (Connection connection = dbConnection.getConnection()) {
			PreparedStatement getPaymentStmt = connection.prepareStatement(getPaymentsSQL);

			ResultSet rs = getPaymentStmt.executeQuery();

			while (rs.next()) {
				Integer customerId = rs.getInt("customer_id");
				Integer carId = rs.getInt("car_id");
				String paid = rs.getString("payment");
				Integer paymentId = rs.getInt("payment_id");

				Payment payment = new Payment(paid, carId, customerId);
				payment.setPaymentId(paymentId);
				payments.add(payment);
			}

			return payments;

		} catch (Exception e) {
			LogUtil.logException(e, PaymentOracle.class);
		}

		return null;
	}

	@Override
	public Integer addPayment(Payment payment) {
		Connection connection = dbConnection.getConnection();

		try {
			connection.setAutoCommit(false);

			String addPaymentSQL = "INSERT INTO ALL_PAYMENTS (PAYMENT_ID, PAYMENT, CUSTOMER_ID, CAR_ID) VALUES (PAYMENT_ID.NEXTVAL, ?,?,?)";

			PreparedStatement insertPaymentStmt = connection.prepareStatement(addPaymentSQL);

			insertPaymentStmt.setString(1, payment.getPaymentAmount());
			insertPaymentStmt.setInt(2, Integer.valueOf(payment.getCustomerId()));
			insertPaymentStmt.setInt(3, Integer.valueOf(payment.getCarId()));

			insertPaymentStmt.executeUpdate();

			ResultSet rs = insertPaymentStmt.getGeneratedKeys();

			if (rs.next()) {
				log.info("Payment added to balance");
				connection.commit();
			} else {
				log.info("Payment not submitted");
				connection.rollback();
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, connection, PaymentOracle.class);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LogUtil.logException(e, PaymentOracle.class);
			}
		}

		return null;
	}

}
