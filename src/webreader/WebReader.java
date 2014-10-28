/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webreader;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCssErrorHandler;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.gargoylesoftware.htmlunit.WebClient; 
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import org.apache.commons.logging.LogFactory;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;

/**
 *
 * @author colleen-reynolds
 */
public class WebReader {



    public static void main(String[] args) throws Exception {
//        String message = "Yay Jake can copy and paste from the internet!";
//        sendEmail(message);
       
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
//        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
//        webClient.getOptions().setUseInsecureSSL(true);
//        page3 = webClient.getPage("Website");
//        HtmlForm loginForm = page3.getFormByName("loginForm");
//        HtmlTextInput username = loginForm.getInputByName("NameofUsernameElement");
//        HtmlPasswordInput pass = loginForm.getInputByName("NameofPassowordElement");
//        HtmlSubmitInput b = loginForm.getInputByValue("LoginButtonValue");
//
//        username.setValueAttribute("Actual Username");
//        pass.setValueAttribute("Actual Password");
//        HtmlPage page2;
//        page2 = b.click();
        
        System.out.println("program is running");
        //---Suppresses unneeded warnings for CSS and other things
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    
        String password = "testing";//---Insert password here
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

//    // Get the first page
    final HtmlPage page1 = webClient.getPage("https://moodle.umn.edu");
//    System.out.println(page1.asText());
    // Get the form that we are dealing with and within that form, 
    // find the submit button and the field that we want to change.
//    final HtmlForm form = page1.getFirstByXPath("//form[@action='/idp/umn/authn']");
    final HtmlForm form = page1.getElementByName("lform");

    final HtmlSubmitInput button = form.getInputByValue("Sign In");
    final HtmlTextInput userField = form.getInputByName("j_username");
    final HtmlPasswordInput passwordField = form.getInputByName("j_password");
    
    // Change the value of the text field
    userField.setValueAttribute("reyno511");
    passwordField.setValueAttribute(password);
    WebWindow window = page1.getEnclosingWindow();
    // Now submit the form by clicking the button and get back the second page.
//    final HtmlPage page2 = button.click();
        button.click();

        
        final HtmlPage page2 = webClient.getPage("https://ay14.moodle.umn.edu/my/");


//        DomElement element = page2.getElementById("yui_3_13_0_3_1414454332948_11");
//            DomElement element = page2.getFirstByXPath("//span[@class='content']");
//                System.out.println(page2.asText());
//            System.out.println(element);
//        String text = element.getTextContent();
//        System.out.println(text);
//String text = element.getTextContent();
        String grades = clean(page2.asText());
        System.out.println(grades.toString());
            
        
    webClient.closeAllWindows();
        
         
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
    
   private static String clean(String dirtyString)
   {
       String cleanString = "";
       List wordList = new LinkedList();
       String newWord = "";
       int beginningOfWord = 0;
       //---Some words are seperated by /br which is not a space
       //---but is always followed by an uppercase letter
       String newLine = System.getProperty("line.separator");
       
       for (int i = 0; i < dirtyString.length(); i++) {
           String convertCharToString = "";
           convertCharToString += dirtyString.charAt(i);
           
           if (dirtyString.charAt(i) == ' ' || convertCharToString.equals(newLine))
           {
               for (int j = beginningOfWord; j < i; j++) {
                   newWord += dirtyString.charAt(j);
               }
               beginningOfWord = i+1;
               wordList.add(newWord);
           }
           
               newWord = "";
       }
       
       for (int i = 0; i < wordList.size(); i++) {

           if (wordList.get(i).equals("My") && wordList.get(i + 1).equals("Grades")
                   && !wordList.get(i - 1).equals("Skip"))
           {
               
               for (int j = i + 2; j < wordList.size(); j++) {
                   if (wordList.get(j).equals("Skip"))
                   {   
                       return cleanString;
                   }
                   else
                   {
                       cleanString += wordList.get(j) + System.lineSeparator();
                   }
               }
           }
           
       }
       return cleanString;
   }
    }


    

