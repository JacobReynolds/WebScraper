/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webreader;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient; 
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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


public class WebReader {



    public static void main(String[] args) throws Exception {
  
        
        
        System.out.println("program is running");
       
        //---Suppresses unneeded warnings for CSS and other things
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    
        //---Creating webClient and allowing access to website
        //---Also supresses some unneeded script warnings
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        
        //---Get the first page
        final HtmlPage page1 = webClient.getPage("https://moodle.umn.edu");

        //---Gets the login form and username and password fields
        final HtmlForm form = page1.getElementByName("lform");

        final HtmlSubmitInput button = form.getInputByValue("Sign In");
        final HtmlTextInput userField = form.getInputByName("j_username");
        final HtmlPasswordInput passwordField = form.getInputByName("j_password");
    
        //---Change the value of the text fields
        userField.setValueAttribute("reyno511");//---Insert username here
        passwordField.setValueAttribute("password");//---Insert password here
       
        //---Submits the form by clicking the button
        button.click();

        //---New page after clicking the button
        final HtmlPage page2 = webClient.getPage("https://ay14.moodle.umn.edu/my/");

        //---Sends the new page to be cleaned and parsed for grades
        //---clean(String string) returns a string
        String grades = clean(page2.asText());

        
        if (!grades.equals(fileReader()))
        {  
        //---Updates the grades in the saved file it compares them to    
        updateGrades(grades);
        
        //---Takes the new grades and sends them to the e-mail in the
        //---sendEmail function
        sendEmail(grades);
        }
        
        //---Closes windows for current session
        webClient.closeAllWindows();
        
        
        
         
        }
    
    private static void sendEmail(String inputMessage)
    {
        
        final String username = "xiixsnb@gmail.com";
        final String password = "password";//---Put in password here
        
        //---Sets server for gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        //---Creates a new session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
            
            }
        
        });
        //---Try catch loop for sending an email
        //---Sends error email to the sender if catch is engaged.
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("xiixsnb@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("reyno511@umn.edu"));
            message.setSubject("Your grades have been updated");
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
       //---Some words are seperated by a line seperator which is not a space
       String newLine = System.getProperty("line.separator");
       
       //---Gets the string, breaks it into a list for every word,
       //---then strips out only the grades.
       //Is currently hardcoded for my layout for moodle, will soon find a way
       //to make it more compatable with any type of moodle layout.
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
       
       //---Finds grades in the wordList
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
   
   public static String fileReader() {
       String answer = "";
       
       BufferedReader br = null;
 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("/Users/colleen-reynolds/Desktop/School/CSCI/Projects/WebScraper/Grades.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
                                answer += sCurrentLine + System.lineSeparator();
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
 
	
       
       
       
       return answer;
  }
   
   public static void updateGrades(String grades) throws FileNotFoundException, IOException
   {
       System.out.println("updating grades");
       BufferedReader br = null;
       BufferedWriter bw = null;
       
       br = new BufferedReader(new FileReader("/Users/colleen-reynolds/Desktop/School/CSCI/Projects/WebScraper/Grades.txt"));
       bw = new BufferedWriter(new FileWriter("/Users/colleen-reynolds/Desktop/School/CSCI/Projects/WebScraper/Grades.txt", false));
       
       bw.write(grades);
       bw.close();
       br.close();
   }
   
    }


    

