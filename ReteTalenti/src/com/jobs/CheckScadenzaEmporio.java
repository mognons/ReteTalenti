package com.jobs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.action.MessageAction;
import com.dao.AssistitiDao;
import com.dao.EntiDao;
import com.model.Assistito;
import com.model.Ente;
import com.model.Message;
import com.opensymphony.xwork2.ActionSupport;
import com.utilities.sendMail;

public class CheckScadenzaEmporio extends ActionSupport implements Job {

    private static final long serialVersionUID = 1L;
    private AssistitiDao a_dao = new AssistitiDao();
    private EntiDao e_dao = new EntiDao();
    private List<Assistito> assistiti;

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String mailRecipient;
		ResourceBundle rb = ResourceBundle.getBundle("com.properties.basicConfiguration");
		Boolean invioEmail = "true".equalsIgnoreCase(rb.getString("sendEccedenze"));
        
    	MessageAction mess = new MessageAction();
    	sendMail sm = new sendMail();
        try {
            assistiti = a_dao.getScadenzaEmporio();
            Iterator<Assistito> itr = assistiti.iterator();
            while (itr.hasNext()) {
            	Message messaggio = new Message();
            	Assistito assistito = itr.next();
            	System.out.println("assitito emporio: " + assistito.getEmporio());
                Ente emporio = e_dao.getEnte(assistito.getEmporio());
                String message_text = 	
                		"Il vostro assistito <b>" + assistito.getNome() + " " + assistito.getCognome() + "</b> "
        				+ "sta per <i>terminare</i> il suo periodo presso l'emporio <b>" + emporio.getDescrizione() + "</b>. "
        				+ "In caso di necessità è possibile contattare il responsabile dell'emporio "
        				+ " al numero di telefono <b>" + emporio.getResp_phone()
        				+ "</b> oppure via email all'indirizzo <b><a href='mailto:" + emporio.getResp_email() + "'>" 
        				+ emporio.getResp_email() +"</a></b>.";
//            	String mail_body = message_text.replaceAll("\\<[^>]*>","");
//            	mail_body = mail_body + "\n\nMessaggio inviato automaticamente dal sistema ReteTalenti.";
                
            	messaggio.setEnte(assistito.getEnte_assistente());
            	messaggio.setTag("ASSISTITI");
            	messaggio.setAction("FOLLOW_assistitiLink.action");
            	messaggio.setMessage_text(message_text);
            	messaggio.setKey1(assistito.getCod_fiscale());
            	messaggio.setKey2(0);
            	messaggio.setKey3(assistito.getData_scadenza());
            	messaggio.setStart_date(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            	messaggio.setEnd_date(assistito.getData_scadenza());
            	mess.createMessage(messaggio);
//            	mailRecipient = enteDestinatario.getResp_email();
            	/* Invio email */
            	try {
//            		if (invioEmail)
//        	    		sm.send("Segnalazione eccedenza", mail_body, mailRecipient);
            	} catch (Exception e) {
            		//
            	}
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return;
    }
}