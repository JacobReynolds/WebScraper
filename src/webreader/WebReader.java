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
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import org.apache.commons.logging.LogFactory;

public class WebReader 
extends Applet
{
    private List gradesList = new LinkedList();
    //Want to add Moodle username and password input 
    //Save it to an encrypted file so that it does not need to be entered again
    private String username, password;

    public void init()
    {   
        //---Sets size and background color
        System.setProperty("maroon", "#993333");
        setBackground(Color.getColor("maroon"));
        Dimension dimension = new Dimension(350, 350);
        setSize(dimension);
    }
     
    public void paint(Graphics g)
    {
        //---Draws outputs for applet and sets colors
        System.setProperty("gold", "#FFCC00");
        g.setColor(Color.getColor("gold"));
        g.drawString(String.valueOf("Program running"), 0, 15);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateOutput = dateFormat.format(date);
        g.drawString(String.valueOf(dateOutput), 0, 45);
        g.drawString(String.valueOf("Current grades:"),0,75);
        //---Draws grades onto the applet
        for (int i = 0; i < gradesList.size(); i++) {
            g.drawString(String.valueOf(gradesList.get(i)),0,105+(30*i));
         }
            
        //---Starts the program loop
        try {
            runProgram();
        } catch (IOException ex) {
            Logger.getLogger(WebReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(WebReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    private void runProgram() throws IOException, InterruptedException
    {
        //---get the HtmlPage with grades on it
        final HtmlPage page2 = getPage();

        //---Sends the new page to be cleaned and parsed for grades
        //---clean(String string) returns a string
        String grades = clean(page2.asText());
        File f = new File("Grades.txt");
        if (!f.exists())
        {
            updateGrades(grades);
        }
        else
        {
        if (!grades.equals(fileReader()))
        {  
        //---Updates the grades in the saved file it compares them to    
        updateGrades(grades);
        
        //---Takes the new grades and sends them to the e-mail in the
        //---sendEmail function
        sendEmail(grades);
            System.out.println("program end");
        }
        else
        {
            System.out.println("program end");
        }}
        Thread.sleep(10000);
        repaint();
    }
    
   
    private HtmlPage getPage() throws IOException
    {
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
       
        //---Closes windows for current session
        webClient.closeAllWindows();
        
        return page2;
    }
    
    private void sendEmail(String inputMessage)
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
    
   private String clean(String dirtyString)
   {
       String cleanString = "";
       List wordList = new LinkedList();
       String newWord = "";
       int beginningOfWord = 0;
       gradesList = new LinkedList();
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
                       gradesList.add(wordList.get(j));
                       cleanString += wordList.get(j) + System.lineSeparator();
                   }
               }
           }
           
       }
       return cleanString;
   }
   
   public String fileReader() {
       String answer = "";
       
       BufferedReader br = null;
 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("Grades.txt"));
 
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
   
   public void updateGrades(String grades) throws FileNotFoundException, IOException
   {
       System.out.println("updating grades");
       BufferedReader br = null;
       BufferedWriter bw = null;
       File f = new File("Grades.txt");
       if (f.exists())
       {
           br = new BufferedReader(new FileReader("Grades.txt"));
           bw = new BufferedWriter(new FileWriter("Grades.txt", false));
           bw.write(grades);
           bw.close();
           br.close();
       }
       else
       {
           f.createNewFile();
           br = new BufferedReader(new FileReader("Grades.txt"));
           bw = new BufferedWriter(new FileWriter("Grades.txt", false));
           bw.write(grades);
           bw.close();
           br.close();
       }
      
   }
   
}


    

