package zirix.zxcc.cron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import antena.mailer.ADGoogleMailer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.mail.*;
import javax.mail.internet.*;


public class ZXAlerter {


	public static String OVERTIME_SUBJECT = "Tarefa com tempo excedido";
	public static String RISK_SUBJECT = "Tarefa com risco alto de tempo excedido";

	public static String SCHEDED_WORK_FLAG = "0";
	public static String STARTED_WORK_FLAG = "1";
	public static String FINISHED_WORK_FLAG = "2";

	public static String ALERT_PENDING = "0";
	public static String ALERT_TOEXPIRE_SENT = "1";
	public static String ALERT_EXPIRED_SENT = "2";

	private String username_; 
	private String password_; 


	public ZXAlerter(String username,String password) {

		username_ = username.toString();
		password_ = password.toString();
	}

	public static void main(String[] args) {

	  try {

		validateInput(args);

		ZXAlerter alerter = new ZXAlerter(args[0],args[1]);


		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/zxccmock?" + "user=zirix&password=pinguim01");

		// SCHEDED_WORKs
		PreparedStatement stmtTOEXPIRE = con.prepareStatement("SELECT NOW(),sched_time_stamp,restriction_id,work_id,work_name,cod_usuario from sched_work where work_state_id = " + SCHEDED_WORK_FLAG + " AND alert_status != " + ALERT_TOEXPIRE_SENT);
		ResultSet resTOEXPIRE = stmtTOEXPIRE.executeQuery();

		// STARTED_WORKs
		PreparedStatement stmtEXPIRED = con.prepareStatement("SELECT NOW(),sched_time_stamp,restriction_id,work_id,work_name,cod_usuario from sched_work where work_state_id = " + STARTED_WORK_FLAG + " AND alert_status != " + ALERT_EXPIRED_SENT);
		ResultSet resEXPIRED = stmtEXPIRED.executeQuery();

		while (resTOEXPIRE.next()) {

			String now = resTOEXPIRE.getString(1);
			Timestamp now_time = Timestamp.valueOf(now);

			String resctriction_id = resTOEXPIRE.getString(3);

			PreparedStatement stmt2 = con.prepareStatement("SELECT restriction_value from restriction_work where restriction_id = " + resctriction_id);
			ResultSet res2 = stmt2.executeQuery();

			Timestamp sched_time = resTOEXPIRE.getTimestamp(2);
			Integer work_id = resTOEXPIRE.getInt(4);
			String work_name = resTOEXPIRE.getString(5);
			Integer cod_usuario = resTOEXPIRE.getInt(6);

			res2.next();
			Time restriction_val = res2.getTime(1);

			if (alerter.evalTOEXPIRE(now_time,sched_time,restriction_val)) {

				PreparedStatement stmt3 = con.prepareStatement("SELECT email from user_group where user_group_id in (SELECT user_group_id from alert_group where alert_group_id = (SELECT alert_group_id from work_alert where work_alert_id = (SELECT work_alert_id from sched_work where work_id =? )))");
				stmt3.setInt(1,work_id);
				ResultSet res3 = stmt3.executeQuery();

				Vector<String> email_TO_List = new Vector<String>();
				while (res3.next()) 
					email_TO_List.add(res3.getString(1));	

				alerter.notify(work_id,work_name,cod_usuario,RISK_SUBJECT,email_TO_List);
				
				PreparedStatement stmt4 = con.prepareStatement("UPDATE sched_work set alert_status =? where work_id =?");
				stmt4.setInt(1,Integer.parseInt(ALERT_TOEXPIRE_SENT));
				stmt4.setInt(2,work_id);
				stmt4.executeUpdate();
			}
		}

		while (resEXPIRED.next()) {

			String now = resEXPIRED.getString(1);
			Timestamp now_time = Timestamp.valueOf(now);

			String resctriction_id = resEXPIRED.getString(3);

			PreparedStatement stmt2 = con.prepareStatement("SELECT restriction_value from restriction_work where restriction_id = " + resctriction_id);
			ResultSet res2 = stmt2.executeQuery();

			Timestamp sched_time = resEXPIRED.getTimestamp(2);
			Integer work_id = resEXPIRED.getInt(4);
			String work_name = resEXPIRED.getString(5);
			Integer cod_usuario = resEXPIRED.getInt(6);

			res2.next();
			Time restriction_val = res2.getTime(1);

			if (alerter.evalEXPIRED(now_time,sched_time,restriction_val)) {

				PreparedStatement stmt3 = con.prepareStatement("SELECT email from user_group where user_group_id in (SELECT user_group_id from alert_group where alert_group_id = (SELECT alert_group_id from work_alert where work_alert_id = (SELECT work_alert_id from sched_work where work_id =? )))");
				stmt3.setInt(1,work_id);
				ResultSet res3 = stmt3.executeQuery();

				Vector<String> email_TO_List = new Vector<String>();
				while (res3.next()) 
					email_TO_List.add(res3.getString(1));	

				alerter.notify(work_id,work_name,cod_usuario,OVERTIME_SUBJECT,email_TO_List);

				PreparedStatement stmt4 = con.prepareStatement("UPDATE sched_work set alert_status =? where work_id =?");
				stmt4.setInt(1,Integer.parseInt(ALERT_EXPIRED_SENT));
				stmt4.setInt(2,work_id);
				stmt4.executeUpdate();
			}
		}

	  } catch(Exception ex) {

		ex.printStackTrace();
	  }
	}

	public static void validateInput(String[] args) throws IllegalArgumentException {

		// USERNAME and PASSWORD
		if (args.length != 2)
			throw new IllegalArgumentException("Usage : ZXMailer [USERNAME] [PASSWORD] ... ");

	}

	/*
	 * avalia o cenario onde a tarefa se aproxima de 75% do tempo limite e ainda nao foi iniciada
	 */
	public boolean evalTOEXPIRE(Timestamp now,Timestamp sched,Time rest) {

		long sched_millis = sched.getTime();

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(sched_millis);

		cal.add(Calendar.HOUR,rest.getHours());
		cal.add(Calendar.MINUTE,rest.getMinutes());


		// somente um teste...
		if (now.after(new Timestamp(cal.getTimeInMillis())))
			return true;

		double diff_res = new Double(now.getTime()).doubleValue()/new Double(cal.getTimeInMillis()).doubleValue();

		if (diff_res >= .75)
			return true;

		return false;
	}

	/*
	 * avalia o cenario onde a tarefa nao foi concluida no tempo limite
	 */
	public boolean evalEXPIRED(Timestamp now,Timestamp sched,Time rest) {

		long sched_millis = sched.getTime();
		long rest_millis = rest.getTime();


		Timestamp sched_plus_rest = new Timestamp(sched_millis + rest_millis);

		if (now.after(sched_plus_rest))
			return true;

		return false;
	}


	/*
	 * envia e-mail de notificacao com o subject em questao
	 */
	public void notify(Integer work_id,String work_name,Integer cod_usuario,String subject,Vector<String> email_TO_List) throws MessagingException {

		ADGoogleMailer mailer = new ADGoogleMailer(username_,password_);

		String msg = "ZXCC MAILER : " + work_id + " | " + work_name + " | " + cod_usuario;

		for (int i=0;i < email_TO_List.size();i++)
			mailer.send(email_TO_List.elementAt(i),msg,subject);

	}
}
