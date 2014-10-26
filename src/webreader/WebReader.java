/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webreader;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.gargoylesoftware.htmlunit.WebClient; 
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;

/**
 *
 * @author colleen-reynolds
 */
public class WebReader {



    public static void main(String[] args) throws Exception {
        String message = "This is a test e-mail";
        sendEmail(message);
       
//        String url = "http://moodle.umn.edu/";
//        Document doc = Jsoup.connect(url).get();
//
//        String test = doc.title();
//        System.out.println(test);
//        
//        Elements txtArea = doc.select("#quickpost");
//        txtArea.text();
        
        
        
        
        //Could be how to submit username and password
//        HtmlPage page3;
//   page3 = webClient.getPage("Website");
//   HtmlForm loginForm = page3.getFormByName("loginForm");
//   HtmlTextInput username = loginForm.getInputByName("NameofUsernameElement");
//   HtmlPasswordInput pass = loginForm.getInputByName("NameofPassowordElement");
//   HtmlSubmitInput b = loginForm.getInputByValue("LoginButtonValue");
//
//   username.setValueAttribute("Actualy Username");
//   pass.setValueAttribute("Actual Password");
//   HtmlPage page2;
//   page2 = b.click();
        
//        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
////
////    // Get the first page
//    final HtmlPage page1 = webClient.getPage("http://www.google.com");
//    
//    // Get the form that we are dealing with and within that form, 
//    // find the submit button and the field that we want to change.
//    final HtmlForm form = page1.getFormByName("search");
//
//    final HtmlSubmitInput button = form.getInputByName("Sign In");
//    final HtmlTextInput userField = form.getInputByName("j_username");
//    final HtmlTextInput passwordField = form.getInputByName("j_password");
//
//    // Change the value of the text field
//    userField.setValueAttribute("reyno511");
//    passwordField.setValueAttribute("testing");
//
//    // Now submit the form by clicking the button and get back the second page.
////    final HtmlPage page2 = button.click();
//    button.click();

//    webClient.closeAllWindows();
        
        
        
         
        }
    
    private static void sendEmail(String inputMessage)
    {
        
        final String username = "xiixsnb@gmail.com";
        final String password = "testing";//---Put in password here
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
            
            }
        
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("xiixsnb@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("reyno511@umn.edu"));
            message.setSubject("This is a new email");
            message.setText(inputMessage);
            Transport.send(message);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println("E-mail was sent " + dateFormat.format(date));
        }
        catch(MessagingException e) {
            
            throw new RuntimeException(e);
            
        }
        //---Used code from https://www.youtube.com/watch?v=sHC8YgW21ho
        //---for the above method
    }
    }


    

